#!/usr/bin/env python3
"""Verify LAB211 projects the way a marker would, then some.

For every project folder HE176322_<CODE>_<Name>:
  1. javac --release 8   (the lab machines run JDK 8: no newer API may sneak in)
     with -Xlint:all -Werror, so rawtypes/unchecked/fallthrough are errors too
  2. run main.Main with each scripted keystroke run and diff the console
     against the expected transcript, character for character, twice:
     under en_US and under vi_VN (the lab machine's locale prints 3,50 for 3.50
     unless the code pins Locale.US)
  3. lint.py — the lecturer's Guide.xlsx rules javac cannot see

Expected transcripts come from two places:
  * the 54 reference runs already verified against each brief
    (docs/codelab-authoring/lab211/solutions/batch*.py in the web repo)
  * extra runs in _tools/tests/<CODE>.py  (RUNS = [(stdin, expected), ...])
    where expected is a string, or a function(output) -> (ok, why)

Usage:
  python3 verify.py                 # every project
  python3 verify.py J1SP0055 J1SP0001
"""
import glob
import importlib.util
import os
import re
import shutil
import subprocess
import sys
import tempfile

HERE = os.path.dirname(os.path.abspath(__file__))
ROOT = os.path.dirname(HERE)
REF = "/Users/admin/Downloads/api-backend/docs/codelab-authoring/lab211/solutions"

sys.path.insert(0, HERE)
import lint  # noqa: E402


def code_of(folder):
    """HE176322_J1SP0055_DoctorManagement -> ('J1SP0055', 'J1.S.P0055')."""
    m = re.match(r"HE176322_(J1([SL])P(\d{4}))_", os.path.basename(folder))
    if not m:
        return None, None
    return m.group(1), "J1.%s.P%s" % (m.group(2), m.group(3))


def load_reference_runs():
    """lab code -> list of (stdin, expected) from the verified reference set."""
    if not os.path.isdir(REF):
        return {}
    sys.path.insert(0, REF)
    import solkit  # noqa: E402
    if not solkit.SOLUTIONS:
        for f in sorted(glob.glob(os.path.join(REF, "batch*.py"))):
            spec = importlib.util.spec_from_file_location(os.path.basename(f)[:-3], f)
            mod = importlib.util.module_from_spec(spec)
            spec.loader.exec_module(mod)
    return {s["lab"]: s["runs"] for s in solkit.SOLUTIONS}


def load_extra_runs(code):
    """(runs, replace_reference) from _tools/tests/<code>.py.

    REPLACE_REFERENCE = True means the reference transcript does NOT match the
    brief's own expected screen (the brief outranks it), so only these runs
    count for this lab.
    """
    path = os.path.join(HERE, "tests", code + ".py")
    if not os.path.exists(path):
        return [], False
    spec = importlib.util.spec_from_file_location("extra_" + code, path)
    mod = importlib.util.module_from_spec(spec)
    spec.loader.exec_module(mod)
    return list(getattr(mod, "RUNS", [])), bool(getattr(mod, "REPLACE_REFERENCE", False))


def compile_project(project, out):
    srcs = [p for p in glob.glob(os.path.join(project, "src", "**", "*.java"), recursive=True)]
    cmd = ["javac", "--release", "8", "-encoding", "UTF-8", "-Xlint:all,-options,-serial",
           "-Werror", "-d", out] + srcs
    r = subprocess.run(cmd, capture_output=True, text=True, timeout=300)
    # NetBeans copies every non-.java file under src/ (e.g. .properties for a
    # ResourceBundle) into the classes folder; do the same so the run matches.
    src_root = os.path.join(project, "src")
    for dp, _dn, fn in os.walk(src_root):
        for f in fn:
            if not f.endswith(".java"):
                rel = os.path.relpath(os.path.join(dp, f), src_root)
                os.makedirs(os.path.dirname(os.path.join(out, rel)), exist_ok=True)
                shutil.copy2(os.path.join(dp, f), os.path.join(out, rel))
    return r.returncode == 0, (r.stderr or r.stdout).strip()


def run_once(classes, workdir, stdin_text, locale):
    lang, _, country = locale.partition("_")
    cmd = ["java", "-Duser.language=" + lang, "-Duser.country=" + country,
           "-Dfile.encoding=UTF-8", "-cp", classes, "main.Main"]
    try:
        p = subprocess.run(cmd, input=stdin_text, capture_output=True, text=True,
                           timeout=90, cwd=workdir)
    except subprocess.TimeoutExpired:
        return None, "hết giờ — chương trình còn chờ nhập mà kịch bản đã hết"
    if p.returncode != 0:
        return None, "exit %d\n%s" % (p.returncode, (p.stderr or "").strip()[:800])
    if (p.stderr or "").strip():
        return None, "có stderr:\n" + p.stderr.strip()[:800]
    return (p.stdout or "").rstrip("\n"), ""


def fresh_workdir(project):
    """A throwaway copy of the project root (data files, no build output), so a
    run that writes files never contaminates the next run or the real folder."""
    wd = tempfile.mkdtemp(prefix="lab211-run-")
    for name in os.listdir(project):
        if name in ("src", "build", "dist", "nbproject", "HUONG-DAN.md", "README.md"):
            continue
        s = os.path.join(project, name)
        if os.path.isdir(s):
            shutil.copytree(s, os.path.join(wd, name))
        else:
            shutil.copy2(s, os.path.join(wd, name))
    return wd


ANT = ("/Applications/NetBeans/Apache NetBeans 17.app/Contents/Resources/NetBeans/"
       "netbeans/extide/ant/bin/ant")


def netbeans_build(project):
    """Clean and Build exactly as NetBeans does, on a throwaway copy."""
    if not os.path.exists(ANT):
        return True, "(bỏ qua: không thấy ant của NetBeans)"
    tmp = tempfile.mkdtemp(prefix="lab211-nb-")
    dst = os.path.join(tmp, os.path.basename(project))
    shutil.copytree(project, dst, ignore=shutil.ignore_patterns("build", "dist"))
    try:
        r = subprocess.run([ANT, "-q", "clean", "jar"], cwd=dst, capture_output=True,
                           text=True, timeout=300)
        return r.returncode == 0 and "BUILD SUCCESSFUL" in r.stdout, (r.stdout + r.stderr)[-1500:]
    finally:
        shutil.rmtree(tmp, ignore_errors=True)


def verify_project(project, ref_runs):
    code, lab = code_of(project)
    name = os.path.basename(project)
    report = []
    ok = True
    extra, replace = load_extra_runs(code)
    runs = ([] if replace else list(ref_runs.get(lab, []))) + extra
    classes = tempfile.mkdtemp(prefix="lab211-cls-")
    try:
        good, msg = compile_project(project, classes)
        if not good:
            return False, ["JAVAC (--release 8, -Werror):\n" + msg[:3000]], 0
        if not runs:
            ok = False
            report.append("KHÔNG CÓ KỊCH BẢN TEST nào — chưa chứng minh được gì")
        for locale in ("en_US", "vi_VN"):
            for i, (stdin_text, expected) in enumerate(runs):
                wd = fresh_workdir(project)
                try:
                    got, err = run_once(classes, wd, stdin_text, locale)
                finally:
                    shutil.rmtree(wd, ignore_errors=True)
                if got is None:
                    ok = False
                    report.append("RUN %d [%s]: %s" % (i, locale, err))
                    continue
                if callable(expected):
                    good, why = expected(got)
                    if not good:
                        ok = False
                        report.append("RUN %d [%s]: %s\n--- ACTUAL ---\n%s" % (i, locale, why, got))
                elif expected is None:
                    ok = False
                    report.append("RUN %d: expected=None — chưa có kỳ vọng" % i)
                elif got.strip() != expected.strip():
                    ok = False
                    report.append("RUN %d [%s]: màn hình khác kỳ vọng\n%s" % (
                        i, locale, first_diff(expected.strip(), got.strip())))
    finally:
        shutil.rmtree(classes, ignore_errors=True)
    return ok, report, len(runs)


def first_diff(expected, got):
    e = expected.split("\n")
    g = got.split("\n")
    for i in range(max(len(e), len(g))):
        a = e[i] if i < len(e) else "<hết>"
        b = g[i] if i < len(g) else "<hết>"
        if a != b:
            ctx_lo = max(0, i - 3)
            return ("dòng %d\n  KỲ VỌNG: %r\n  THỰC TẾ: %r\n--- ngữ cảnh (thực tế) ---\n%s"
                    % (i + 1, a, b, "\n".join(g[ctx_lo:i + 2])))
    return "(khác ở khoảng trắng cuối)"


def main(argv):
    do_nb = "--netbeans" in argv
    argv = [a for a in argv if a != "--netbeans"]
    wanted = set(a.upper() for a in argv)
    projects = sorted(p for p in glob.glob(os.path.join(ROOT, "HE176322_*")) if os.path.isdir(p))
    if wanted:
        projects = [p for p in projects if code_of(p)[0] in wanted]
    ref_runs = load_reference_runs()
    total_ok = 0
    failures = []
    for project in projects:
        name = os.path.basename(project)
        ok, report, nruns = verify_project(project, ref_runs)
        ctx = {}
        allow = os.path.join(project, ".lint-allow")
        if os.path.exists(allow):
            for line in open(allow):
                if line.startswith("protected_ok="):
                    ctx["protected_ok"] = set(x.strip() for x in line.split("=", 1)[1].split(","))
        lint_v = lint.check_project(project, ctx)
        if lint_v:
            ok = False
            report.append("LINT (%d vi phạm):\n%s" % (len(lint_v), "\n".join(str(x) for x in lint_v[:60])))
        if do_nb:
            if not os.path.exists(os.path.join(project, "nbproject", "project.xml")):
                ok = False
                report.append("NETBEANS: thiếu nbproject/ — chạy _tools/make_netbeans.py")
            else:
                nb_ok, nb_log = netbeans_build(project)
                if not nb_ok:
                    ok = False
                    report.append("NETBEANS Clean and Build THẤT BẠI:\n" + nb_log)
        if ok:
            total_ok += 1
            print("✓ %-55s %2d kịch bản × 2 locale, lint sạch" % (name, nruns))
        else:
            failures.append(name)
            print("✗ %s" % name)
            for r in report:
                print("   " + r.replace("\n", "\n   "))
    print("\nTỔNG: %d/%d project đạt" % (total_ok, len(projects)))
    if failures:
        print("CHƯA ĐẠT: " + ", ".join(failures))
    return 0 if not failures else 1


if __name__ == "__main__":
    sys.exit(main(sys.argv[1:]))
