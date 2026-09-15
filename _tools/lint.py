#!/usr/bin/env python3
"""Rule checker for the LAB211 projects — enforces the lecturer's Guide.xlsx
rules that javac cannot see.

Every rule below is traceable to QUY-TAC-THAY.md. A project that javac accepts
can still be refused at review ("không có cấu trúc / không có comment → không
review"), so these rules are checked mechanically instead of by eye.

Usage:  python3 lint.py <project_dir> [<project_dir> ...]
Exit status 0 only when no project has a violation.
"""
import os
import re
import sys

# ─── a tiny Java lexer: split each line into code / comment / strings ───────


class Line:
    __slots__ = ("no", "raw", "code", "has_comment", "strings", "in_block")

    def __init__(self, no, raw):
        self.no = no
        self.raw = raw
        self.code = ""          # code with string/char literals blanked out
        self.has_comment = False
        self.strings = []       # string literal contents found on this line
        self.in_block = False   # line is entirely inside a /* */ comment


def lex(text):
    """Return a list of Line objects for a Java source text."""
    lines = [Line(i + 1, raw) for i, raw in enumerate(text.split("\n"))]
    state = "code"               # code | block | string | char
    buf = []
    cur_str = []
    li = 0
    i = 0
    n = len(text)
    line = lines[0]
    only_block = True
    while i < n:
        ch = text[i]
        nxt = text[i + 1] if i + 1 < n else ""
        if ch == "\n":
            line.code = "".join(buf)
            line.in_block = only_block and state == "block" and not line.code.strip()
            buf = []
            li += 1
            line = lines[li]
            only_block = state == "block"
            if state == "string":      # unterminated string: bail out safely
                state = "code"
            i += 1
            continue
        if state == "code":
            if ch == "/" and nxt == "/":
                line.has_comment = True
                # skip to end of line
                j = text.find("\n", i)
                i = n if j == -1 else j
                continue
            if ch == "/" and nxt == "*":
                line.has_comment = True
                state = "block"
                i += 2
                continue
            if ch == '"':
                state = "string"
                cur_str = []
                buf.append('"')
                i += 1
                continue
            if ch == "'":
                state = "char"
                buf.append("'")
                i += 1
                continue
            buf.append(ch)
            only_block = False if ch.strip() else only_block
            i += 1
            continue
        if state == "block":
            line.has_comment = True
            if ch == "*" and nxt == "/":
                state = "code"
                i += 2
                continue
            i += 1
            continue
        if state == "string":
            if ch == "\\":
                cur_str.append(text[i:i + 2])
                i += 2
                continue
            if ch == '"':
                line.strings.append("".join(cur_str))
                buf.append('"')
                state = "code"
                i += 1
                continue
            cur_str.append(ch)
            i += 1
            continue
        if state == "char":
            if ch == "\\":
                i += 2
                continue
            if ch == "'":
                buf.append("'")
                state = "code"
            i += 1
            continue
    line.code = "".join(buf)
    return lines


# ─── project model ───────────────────────────────────────────────────────────

LAYERS = ["constants", "controller", "dto", "main", "model", "repository",
          "service", "utils", "view", "exceptions"]

FORBIDDEN_IMPORTS = {
    # package      : packages it must not import (Guide sheet `sample`)
    "main": ["model", "view", "repository", "service"],
    "controller": ["main", "model"],   # "chi import DTO, View, Service"
    "model": ["view", "controller", "service", "repository", "dto", "main"],
    "view": ["model", "repository", "service", "controller", "main"],
    "service": ["view", "controller", "main"],
    "repository": ["view", "controller", "main", "service"],
    "dto": ["view", "controller", "service", "repository", "main"],
    "utils": ["view", "controller", "service", "repository", "main"],
    "constants": ["view", "controller", "service", "repository", "main",
                  "model", "dto"],
}

MAX_LINE = 100
FORMAT_SPEC = re.compile(r"%[-#+ 0,(]*\d*(\.\d+)?[a-zA-Z%]")
JAVA_ESCAPE = re.compile(r"\\[ntrbf0'\"\\]")
CONTROL = re.compile(r"^\s*(\}\s*)?(if|else|for|while|do|switch|case|default|try|catch|finally)\b")
METHOD_DECL = re.compile(
    r"^\s*(public|protected|private)?\s*(static\s+)?(final\s+)?(abstract\s+)?"
    r"(synchronized\s+)?([\w<>\[\],.? ]+\s+)?(\w+)\s*\([^;]*$")
FIELD_DECL = re.compile(
    r"^    (public |protected |private )?(static )?(final )?"
    r"([\w<>\[\],.?]+(?:<[^;=()]*>)?)\s+(\w+)\s*(=.*)?;\s*$")
TYPE_DECL = re.compile(r"^\s*(public\s+)?(final\s+|abstract\s+)*(class|interface|enum)\s+(\w+)")
KEYWORDS_NOT_METHOD = {"if", "for", "while", "switch", "catch", "return", "new",
                       "else", "do", "try", "synchronized", "throw"}
MAGIC_OK = {"0", "1", "2", "-1", "0.0", "1.0", "2.0", "0L", "1L", "0f", "1f", "0.0f", "1.0f"}


class Violation:
    def __init__(self, rel, no, rule, msg):
        self.rel, self.no, self.rule, self.msg = rel, no, rule, msg

    def __str__(self):
        loc = "%s:%d" % (self.rel, self.no) if self.no else self.rel
        return "  [%s] %s — %s" % (self.rule, loc, self.msg)


def package_of(rel):
    parts = rel.split("/")
    return parts[1] if len(parts) > 2 else ""


def has_comment_near(lines, idx, lookback=1):
    """A comment on this line, the previous non-blank line(s), or the first
    line inside the block that this line opens."""
    if lines[idx].has_comment:
        return True
    j = idx - 1
    seen = 0
    while j >= 0 and seen < lookback:
        if lines[j].raw.strip() == "":
            j -= 1
            continue
        if lines[j].has_comment or lines[j].in_block:
            return True
        # annotations sit between a comment and its declaration
        if lines[j].code.strip().startswith("@"):
            j -= 1
            continue
        seen += 1
        j -= 1
    if lines[idx].code.rstrip().endswith("{"):
        k = idx + 1
        while k < len(lines) and lines[k].raw.strip() == "":
            k += 1
        if k < len(lines) and lines[k].has_comment:
            return True
    return False


def preceding_comment_block(lines, idx):
    """True when the declaration at idx is preceded by a comment (skipping
    annotations and blank lines)."""
    j = idx - 1
    while j >= 0:
        s = lines[j].raw.strip()
        if s == "" or lines[j].code.strip().startswith("@"):
            j -= 1
            continue
        return lines[j].has_comment or lines[j].in_block or s.endswith("*/")
    return False


def split_params(text):
    """Split a parameter list on top-level commas (generic <A, B> stays whole)."""
    text = text.strip()
    if not text:
        return []
    out, depth, cur = [], 0, ""
    for ch in text:
        if ch == "<":
            depth += 1
        elif ch == ">":
            depth -= 1
        if ch == "," and depth == 0:
            out.append(cur)
            cur = ""
        else:
            cur += ch
    out.append(cur)
    return [p for p in out if p.strip()]


def check_file(root, rel, text, v, ctx):
    pkg = package_of(rel)
    lines = lex(text)
    codes = [l.code for l in lines]
    joined = "\n".join(codes)

    if "To change this license header" in text:
        v.append(Violation(rel, 1, "HEADER", "còn header mẫu của NetBeans — xoá đi"))
    if "\t" in text:
        v.append(Violation(rel, 0, "FORMAT", "có ký tự TAB — dùng 4 dấu cách (Alt+Shift+F)"))

    # declared package must match folder
    m = re.search(r"^\s*package\s+([\w.]+)\s*;", joined, re.M)
    if not m or m.group(1) != pkg:
        v.append(Violation(rel, 1, "PACKAGE", "package khai báo không khớp thư mục '%s'" % pkg))

    # imports between layers
    for l in lines:
        mi = re.match(r"\s*import\s+(static\s+)?([\w.]+)", l.code)
        if not mi:
            continue
        target = mi.group(2).split(".")[0]
        if target in FORBIDDEN_IMPORTS.get(pkg, []):
            v.append(Violation(rel, l.no, "LAYER",
                               "package '%s' không được import '%s' (Guide.xlsx)" % (pkg, target)))
        if mi.group(2) == "java.util.Scanner" and pkg != "main":
            v.append(Violation(rel, l.no, "SCANNER", "Scanner chỉ được dùng ở main"))

    for l in lines:
        c = l.code
        if re.search(r"\bScanner\b", c) and pkg != "main":
            v.append(Violation(rel, l.no, "SCANNER", "Scanner chỉ được dùng ở main"))
        if re.search(r"\bSystem\s*\.\s*(out|err)\b", c) and pkg not in ("main", "view"):
            v.append(Violation(rel, l.no, "PRINT",
                               "không được print ngoài view và main (Guide.xlsx)"))
        if re.search(r"\bSystem\s*\.\s*exit\s*\(", c):
            v.append(Violation(rel, l.no, "EXIT", "không dùng System.exit — thoát bằng cờ/return"))
        if re.search(r"\.\s*(nextInt|nextDouble|nextFloat|nextLong|next|nextBoolean)\s*\(", c) \
                and "Scanner" in joined:
            v.append(Violation(rel, l.no, "INPUT", "chỉ đọc bằng nextLine() rồi parse"))
        if len(l.raw) > MAX_LINE:
            v.append(Violation(rel, l.no, "FORMAT", "dòng dài %d ký tự (> %d)" % (len(l.raw), MAX_LINE)))
        if re.search(r"\b(if|for|while|switch|catch)\(", c):
            v.append(Violation(rel, l.no, "FORMAT", "thiếu dấu cách sau từ khoá: 'if (' "))
        if re.search(r"\)\{", c) or re.search(r"\belse\{", c) or re.search(r"\}else\b", c) \
                or re.search(r"\btry\{", c):
            v.append(Violation(rel, l.no, "FORMAT", "thiếu dấu cách quanh ngoặc nhọn"))
        s = c.strip()
        if re.match(r"(if|else\s+if|for|while)\s*\(.*\)\s*[^{\s].*;$", s) or re.match(r"else\s+[^{i].*;$", s):
            v.append(Violation(rel, l.no, "BRACES", "if/for/while/else luôn phải có { } (CC §7.4)"))
        # hardcoded user-facing text (everything must live in constants)
        if pkg != "constants":
            for lit in l.strings:
                probe = JAVA_ESCAPE.sub("", FORMAT_SPEC.sub("", lit))
                if re.search(r"[A-Za-z]", probe):
                    v.append(Violation(rel, l.no, "HARDCODE",
                                       "chuỗi \"%s\" phải khai báo ở constants (Message/Constants)" % lit[:40]))
        # magic numbers (CheckStyle MagicNumber: -1,0,1,2 are fine)
        if pkg != "constants" and not s.startswith("@"):
            stripped = re.sub(r'"[^"]*"', '""', c)
            for num in re.findall(r"(?<![\w.])(\d+\.\d+[fFdD]?|\d+[lLfFdD]?)(?![\w.])", stripped):
                if num not in MAGIC_OK and not re.fullmatch(r"[012]", num):
                    v.append(Violation(rel, l.no, "MAGIC",
                                       "số %s viết thẳng — đặt tên hằng trong Constants (CC §10.3)" % num))

    # static rules
    type_kinds = [(m.group(3), m.group(4)) for m in re.finditer(TYPE_DECL.pattern, joined, re.M)]
    is_enum = any(k == "enum" for k, _ in type_kinds)
    is_interface = any(k == "interface" for k, _ in type_kinds)
    for l in lines:
        c = l.code
        if not re.search(r"\bstatic\b", c):
            continue
        if pkg in ("constants", "utils"):
            continue
        if pkg == "main":
            if not re.search(r"\(", c) or re.search(r"=", c.split("(")[0]):
                v.append(Violation(rel, l.no, "STATIC",
                                   "main: cấm static với biến, chỉ được static với hàm (Guide.xlsx)"))
            continue
        v.append(Violation(rel, l.no, "STATIC",
                           "package '%s' không được dùng static (Guide.xlsx)" % pkg))

    if pkg == "utils":
        if not re.search(r"public\s+final\s+class", joined):
            v.append(Violation(rel, 1, "UTILS", "lớp utils phải là 'public final class'"))
        if not re.search(r"private\s+\w+\s*\(\s*\)", joined):
            v.append(Violation(rel, 1, "UTILS", "lớp utils phải có private constructor"))
        for l in lines:
            md = METHOD_DECL.match(l.code)
            if md and md.group(1) == "public" and not md.group(2) and "(" in l.code \
                    and md.group(7) not in KEYWORDS_NOT_METHOD and not re.search(r"\bclass\b", l.code):
                v.append(Violation(rel, l.no, "UTILS", "hàm utils phải static (Guide.xlsx)"))
    if pkg == "constants" and not is_enum and not is_interface:
        if not re.search(r"public\s+final\s+class", joined):
            v.append(Violation(rel, 1, "CONST", "lớp hằng phải là 'public final class'"))
        if not re.search(r"private\s+\w+\s*\(\s*\)", joined):
            v.append(Violation(rel, 1, "CONST", "lớp hằng phải có private constructor"))

    # fields must be private (encapsulation) — except the explicit brief exceptions
    depth = 0
    for idx, l in enumerate(lines):
        c = l.code
        if depth == 1 and pkg not in ("constants",) and not is_interface:
            fm = FIELD_DECL.match(c)
            if fm and fm.group(5) not in KEYWORDS_NOT_METHOD and "(" not in c.split("=")[0]:
                mod = (fm.group(1) or "").strip()
                name = fm.group(5)
                allowed = ctx.get("protected_ok", set())
                if mod != "private" and not (mod == "protected" and name in allowed):
                    v.append(Violation(rel, l.no, "FIELD",
                                       "trường '%s' phải private (đóng gói)" % name))
                if not has_comment_near(lines, idx):
                    v.append(Violation(rel, l.no, "COMMENT", "trường '%s' thiếu comment" % name))
        depth += c.count("{") - c.count("}")

    # comments: every type, every method/constructor, every branch/loop/case/try
    for idx, l in enumerate(lines):
        c = l.code
        s = c.strip()
        tm = TYPE_DECL.match(c)
        if tm:
            if not preceding_comment_block(lines, idx):
                v.append(Violation(rel, l.no, "COMMENT",
                                   "%s %s thiếu Javadoc đầu lớp" % (tm.group(3), tm.group(4))))
            continue
        md = METHOD_DECL.match(c)
        if md and md.group(7) not in KEYWORDS_NOT_METHOD and not s.startswith(("return", "new", "throw", "else", "}")) \
                and re.match(r"^    \s*(public|protected|private|static|final|abstract|synchronized|\w)", c) \
                and not re.search(r"=|\.\w+\s*\(|^\s*\w+\s*\(.*\)\s*;", s) \
                and (s.endswith("{") or s.endswith(",") or s.endswith(";") and "abstract" in s or s.endswith(")") or "throws" in s):
            # a declaration, not a call
            if re.match(r"^(\w[\w<>\[\],.? ]*\s+)?\w+\s*\(", s) and not preceding_comment_block(lines, idx):
                v.append(Violation(rel, l.no, "COMMENT", "hàm/constructor '%s' thiếu comment" % md.group(7)))
            continue
        cm = CONTROL.match(c)
        if cm:
            kw = cm.group(2)
            if not has_comment_near(lines, idx):
                v.append(Violation(rel, l.no, "COMMENT",
                                   "'%s' thiếu comment (thầy: comment cho mọi block/rẽ nhánh)" % kw))
            if kw == "switch":
                # find matching block and ensure it has a default
                depth2 = 0
                found_default = False
                for k in range(idx, len(lines)):
                    depth2 += lines[k].code.count("{") - lines[k].code.count("}")
                    if re.match(r"^\s*default\s*:", lines[k].code):
                        found_default = True
                    if depth2 <= 0 and k > idx:
                        break
                if not found_default:
                    v.append(Violation(rel, l.no, "SWITCH", "switch thiếu default (CC §7.8)"))

    # @Override on the usual overriding methods
    for idx, l in enumerate(lines):
        mo = re.match(r"^\s*public\s+[\w<>]+\s+(toString|equals|hashCode|compare|compareTo)\s*\(", l.code)
        if mo:
            j = idx - 1
            while j >= 0 and lines[j].raw.strip() == "":
                j -= 1
            if "@Override" not in lines[j].code:
                v.append(Violation(rel, l.no, "OVERRIDE", "thiếu @Override trên %s()" % mo.group(1)))

    # V4 (lecturer, in class): "khong duoc truyen 3 tham so 1 ham".
    # Business methods take at most 2 parameters; utils may take 3 exactly like
    # the Guide sample getChoice(input, min, max); constructors are exempt like
    # the Guide sample Doctor(code, name, specialization, availability).
    class_names = {name for _, name in type_kinds}
    limit = 3 if pkg == "utils" else 2
    for m in re.finditer(r"(?:public|protected|private|static|final|abstract|\s)\s*"
                         r"(?:<[^>]+>\s+)?([\w<>\[\],.? ]+?)\s+(\w+)\s*\(([^()]*)\)\s*"
                         r"(?:throws\s+[\w.,\s]+)?\{", joined):
        name = m.group(2)
        if name in KEYWORDS_NOT_METHOD or name in class_names:
            continue
        rettype = m.group(1).strip().split()[-1] if m.group(1).strip() else ""
        if rettype in ("new", "return", "else", "throw") or not rettype:
            continue
        params = split_params(m.group(3))
        if len(params) > limit:
            no = joined[:m.start(2)].count("\n") + 1
            v.append(Violation(rel, no, "PARAMS",
                               "hàm '%s' có %d tham số — thầy: không truyền 3 tham số 1 hàm; "
                               "gói vào DTO/model" % (name, len(params))))

    # V5 (lecturer, in class): declare ArrayList/HashMap, not List/Map - unless
    # the brief itself dictates the signature, marked by a "brief" comment.
    for idx, l in enumerate(lines):
        c = l.code
        if c.strip().startswith("import"):
            continue
        if re.search(r"\b(List|Map|Set|Collection|Queue|Deque)\s*<", c):
            near = l.raw + (lines[idx - 1].raw if idx > 0 else "")
            if not re.search(r"//.*\bbrief\b|brief:", near, re.I):
                v.append(Violation(rel, l.no, "COLLECTION",
                                   "dùng kiểu cụ thể ArrayList/HashMap/LinkedHashMap (thầy: List/Map "
                                   "là dấu hiệu AI) — chỉ giữ List/Map khi đề bắt, kèm comment '// brief: ...'"))

    # V10 (lecturer): MVC of JSP -> model and DTO classes are JavaBeans:
    # a public no-argument constructor plus getters/setters.
    if pkg in ("model", "dto") and not is_enum and not is_interface:
        for kind, name in type_kinds:
            if kind != "class":
                continue
            decl = re.search(r"\b(abstract\s+)?class\s+%s\b" % re.escape(name), joined)
            if decl and decl.group(1):
                continue
            if not re.search(r"public\s+%s\s*\(\s*\)" % re.escape(name), joined):
                v.append(Violation(rel, 1, "JAVABEAN",
                                   "lớp %s thiếu constructor rỗng public (JavaBean — MVC JSP)" % name))
            break

    ctx.setdefault("scanner_used", False)
    if re.search(r"\bScanner\b", joined):
        ctx["scanner_used"] = True


def check_project(root, ctx=None):
    ctx = ctx or {}
    v = []
    src = os.path.join(root, "src")
    if not os.path.isdir(src):
        return [Violation(root, 0, "STRUCTURE", "không có thư mục src/")]
    files = []
    for dp, _dn, fn in os.walk(src):
        for f in fn:
            if f.endswith(".java"):
                full = os.path.join(dp, f)
                files.append(os.path.relpath(full, root).replace(os.sep, "/"))
    pkgs = {package_of(f) for f in files}
    for f in files:
        p = package_of(f)
        if p not in LAYERS:
            v.append(Violation(f, 0, "STRUCTURE", "package '%s' không thuộc kiến trúc Guide" % p))
    for need in ("constants", "controller", "dto", "main", "model", "view"):
        if need not in pkgs:
            v.append(Violation("src/" + need, 0, "STRUCTURE", "thiếu package %s/ (Guide.xlsx)" % need))
    if "src/main/Main.java" not in files:
        v.append(Violation("src/main/Main.java", 0, "STRUCTURE", "thiếu main/Main.java"))
    if "src/constants/Message.java" not in files:
        v.append(Violation("src/constants/Message.java", 0, "STRUCTURE", "thiếu constants/Message.java"))
    for f in sorted(files):
        with open(os.path.join(root, f), encoding="utf-8") as fh:
            check_file(root, f, fh.read(), v, ctx)
    if ctx.get("scanner_used") and "src/utils/Validation.java" not in files:
        v.append(Violation("src/utils/Validation.java", 0, "STRUCTURE",
                           "bài có nhập liệu nhưng thiếu utils/Validation.java"))
    return v


def main(argv):
    bad = 0
    for root in argv:
        root = root.rstrip("/")
        ctx = {}
        conf = os.path.join(root, ".lint-allow")
        if os.path.exists(conf):
            for line in open(conf):
                line = line.strip()
                if line.startswith("protected_ok="):
                    ctx["protected_ok"] = set(x.strip() for x in line.split("=", 1)[1].split(","))
        v = check_project(root, ctx)
        name = os.path.basename(root)
        if v:
            bad += 1
            print("✗ %s — %d vi phạm" % (name, len(v)))
            for x in v:
                print(x)
        else:
            print("✓ %s — sạch" % name)
    return 1 if bad else 0


if __name__ == "__main__":
    sys.exit(main(sys.argv[1:]))
