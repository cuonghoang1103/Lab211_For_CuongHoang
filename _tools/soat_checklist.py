#!/usr/bin/env python3
"""Audit LAB211 projects against the lecturer's PAPER checklist (2 sheets).

Every rule id below is the row number on the paper sheet (1.1 .. 3.8).
Severity:
  VI_PHAM  = the paper says it literally -> the lecturer can tick "not OK"
  RUI_RO   = defensible either way, but a strict reading of the paper flags it
Usage: python3 _tools/soat_checklist.py <project_dir> [...]   (readable report)
       python3 _tools/soat_checklist.py --json <project_dir>   (JSON)
"""
import json
import os
import re
import sys

sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from lint import lex  # noqa: E402  (tiny Java lexer: code / comment / strings per line)

KEYWORDS = {"abstract", "assert", "boolean", "break", "byte", "case", "catch", "char", "class",
            "const", "continue", "default", "do", "double", "else", "enum", "extends", "final",
            "finally", "float", "for", "goto", "if", "implements", "import", "instanceof", "int",
            "interface", "long", "native", "new", "package", "private", "protected", "public",
            "return", "short", "static", "strictfp", "super", "switch", "synchronized", "this",
            "throw", "throws", "transient", "try", "void", "volatile", "while", "var", "yield",
            "true", "false", "null"}
NOT_TYPE = {"return", "throw", "new", "else", "case", "package", "import", "break", "continue",
            "yield", "assert", "goto", "do", "if", "for", "while", "switch", "try", "catch",
            "finally", "synchronized", "instanceof", "default", "this", "super"}
LIST_TYPES = {"List", "ArrayList", "LinkedList", "Collection", "Vector", "Stack", "Queue",
              "Deque", "ArrayDeque", "PriorityQueue", "Iterable", "CopyOnWriteArrayList"}
SET_TYPES = {"Set", "HashSet", "TreeSet", "LinkedHashSet", "SortedSet", "NavigableSet", "EnumSet"}
MAP_TYPES = {"Map", "HashMap", "TreeMap", "LinkedHashMap", "Hashtable", "SortedMap",
             "NavigableMap", "EnumMap", "ConcurrentHashMap"}
VERBS = set("""get set is has can should add remove delete update find search check validate
calculate calc compute count create build make generate display show print render input enter
read write load save store parse convert format normalize sort swap merge split join filter apply
run execute start stop init initialize reset clear contains compare equals handle process perform
fill push pop peek insert append replace change move copy zip unzip compress decompress extract
analyze encrypt decrypt encode decode login logout register verify confirm choose select pick ask
prompt request open close exist exists list edit modify sum multiply divide subtract solve draw deal
shuffle attack damage fly heal grow die kill hit play rank report summarize group collect iterate
traverse visit walk scan match test ensure require notify send receive fetch put to main hash call
use take give keep mark lookup look resolve map reduce trim capitalize lower upper remove wrap
unwrap increase decrease increment decrement raise go try assign refresh sell buy order pay charge
book cancel borrow return rent sign log dump emit accept reject add plus minus times power
destroy create quit exit end finish complete begin enqueue dequeue reverse rotate transpose
determine detect identify classify estimate measure evaluate score grade rate average total
compose split separate tokenize count fire spawn release acquire lock unlock load unload import
export install uninstall list describe explain translate lookup search index""".split())

STR_LIT = '""'


def strip_generics(s):
    prev = None
    while prev != s:
        prev = s
        s = re.sub(r"<[^<>;=()]*>", "", s)
    return s


def camel_first_word(name):
    m = re.match(r"[a-z]+", name)
    return m.group(0) if m else name


def type_kind(tp):
    t = tp.strip()
    if t.endswith("]"):
        return "Array"
    base = re.sub(r"<.*", "", t).split(".")[-1].strip()
    if base in LIST_TYPES:
        return "List"
    if base in SET_TYPES:
        return "Set"
    if base in MAP_TYPES:
        return "Map"
    return None


def split_top(text, seps):
    """Split text on separators at paren-depth 0. Returns list of parts."""
    out, depth, cur, i = [], 0, "", 0
    while i < len(text):
        ch = text[i]
        if ch in "([{":
            depth += 1
        elif ch in ")]}":
            depth -= 1
        matched = None
        if depth == 0:
            for s in seps:
                if text.startswith(s, i):
                    matched = s
                    break
        if matched:
            out.append((cur, matched))
            cur = ""
            i += len(matched)
            continue
        cur += ch
        i += 1
    out.append((cur, None))
    return out


def fully_parenthesized(s):
    s = s.strip()
    if not (s.startswith("(") and s.endswith(")")):
        return False
    depth = 0
    for i, ch in enumerate(s):
        if ch == "(":
            depth += 1
        elif ch == ")":
            depth -= 1
            if depth == 0 and i != len(s) - 1:
                return False
    return True


REL = re.compile(r"(==|!=|<=|>=|(?<![<\-])<(?![<=])|(?<![>\-])>(?![>=]))")


def depth0(text):
    """Return text with everything inside parentheses/brackets replaced by spaces."""
    out, depth = [], 0
    for ch in text:
        if ch in "([{":
            depth += 1
            out.append(" " if depth > 1 else ch)
            continue
        if ch in ")]}":
            depth -= 1
            out.append(" " if depth > 0 else ch)
            continue
        out.append(ch if depth == 0 else " ")
    return "".join(out)


def has_rel0(s):
    d = strip_generics(depth0(s))
    d = d.replace("->", "  ")
    return bool(REL.search(d))


def has_add0(s):
    d = depth0(s)
    # binary + or - : previous non-space char is word/closing
    return bool(re.search(r"[\w)\]\"']\s*[+\-](?![+\-=])", d.replace("->", "  ")))


def has_mul0(s):
    d = depth0(s)
    return bool(re.search(r"[\w)\]\"']\s*[*/%](?!=)", d))


def analyse_expr(expr):
    """Return list of (level, msg) for checklist 3.3."""
    res = []
    e = expr.strip()
    if not e:
        return res
    parts = split_top(e, ["&&", "||"])
    ops = {p[1] for p in parts if p[1]}
    if ops:
        if len(ops) > 1:
            res.append(("VI_PHAM", "trộn && và || cùng mức, không có ngoặc"))
        for chunk, _ in parts:
            c = chunk.strip()
            while c.startswith("!"):
                c = c[1:].strip()
            if has_rel0(c) and not fully_parenthesized(c):
                res.append(("VI_PHAM", "phép so sánh '%s' đứng cạnh &&/|| mà không có ngoặc riêng"
                            % c[:40]))
                break
    # ternary: binary expression before '?'
    d = depth0(e)
    q = d.find("?")
    if q > 0 and ":" in d[q:]:
        cond = e[:q].strip()
        cond = re.sub(r"^.*?(=|return)\s*", "", cond) if re.search(r"(^|\s)(=|return)\s", cond) else cond
        if (has_rel0(cond) or has_add0(cond)) and not fully_parenthesized(cond):
            res.append(("VI_PHAM", "biểu thức trước '?' phải có ngoặc: (x >= 0) ? x : -x"))
    # arithmetic mixing (+/- with * / %)
    for chunk, _ in parts:
        c = chunk
        if has_add0(c) and has_mul0(c):
            res.append(("RUI_RO", "trộn +/- với * / % cùng mức, nên thêm ngoặc cho tường minh"))
            break
    # comparison with arithmetic operand
    for chunk, _ in parts:
        c = chunk.strip()
        while c.startswith("!"):
            c = c[1:].strip()
        if fully_parenthesized(c):
            c = c[1:-1]
        d = strip_generics(depth0(c)).replace("->", "  ")
        m = REL.search(d)
        if m and (has_add0(c) or has_mul0(c)):
            res.append(("RUI_RO", "so sánh có vế là phép tính (vd i < n - 1), nên viết i < (n - 1)"))
            break
    return res


LOCAL_DECL = re.compile(
    r"^\s*(?:final\s+)?(?P<type>[A-Za-z_][\w.]*(?:\s*<[^;=()]*>)?(?:\s*\[\s*\])*)\s+"
    r"(?P<name>[a-zA-Z_$][\w$]*)\s*(?P<arr>(?:\[\s*\])*)\s*(?P<rest>=.*|;.*|,.*)$")
FIELD_DECL = re.compile(
    r"^\s*(?P<mods>(?:(?:public|protected|private|static|final|transient|volatile)\s+)*)"
    r"(?P<type>[A-Za-z_][\w.]*(?:\s*<[^;=()]*>)?(?:\s*\[\s*\])*)\s+"
    r"(?P<name>[a-zA-Z_$][\w$]*)\s*(?P<arr>(?:\[\s*\])*)\s*(?P<rest>=.*|;.*|,.*)$")
TYPE_DECL = re.compile(
    r"^\s*(?P<mods>(?:(?:public|protected|private|static|final|abstract)\s+)*)"
    r"(?P<kind>class|interface|enum)\s+(?P<name>\w+)(?P<rest>.*)$")
METHOD_HEAD = re.compile(
    r"^\s*(?P<mods>(?:(?:public|protected|private|static|final|abstract|synchronized|native|default)\s+)*)"
    r"(?:<[^>]+>\s+)?(?:(?P<ret>[\w.]+(?:\s*<[^()]*>)?(?:\s*\[\s*\])*)\s+)?(?P<name>\w+)\s*\((?P<params>.*)$")


def _prev_code_ends(codes, i):
    """True when the previous non-blank code line ends a statement/declaration, i.e. line i
    starts a NEW member rather than continuing a wrapped header."""
    j = i - 1
    while j >= 0 and not codes[j].strip():
        j -= 1
    if j < 0:
        return True
    t = codes[j].strip()
    return t.endswith((";", "{", "}")) or t.startswith("@")


def params_of(text):
    text = text.strip()
    if not text:
        return []
    out = []
    for chunk, _ in split_top(strip_generics(text), [","]):
        c = chunk.strip()
        if not c:
            continue
        c = re.sub(r"^(final\s+|@\w+\s+)+", "", c)
        m = re.match(r"(?P<type>[\w.]+(?:\s*\[\s*\])*(?:\.\.\.)?)\s+(?P<name>\w+)(?P<arr>(\[\s*\])*)$", c)
        if m:
            out.append((m.group("type") + m.group("arr"), m.group("name")))
    return out


class Finding:
    def __init__(self, rule, level, rel, line, msg):
        self.rule, self.level, self.rel, self.line, self.msg = rule, level, rel, line, msg

    def js(self):
        return {"rule": self.rule, "level": self.level, "file": self.rel, "line": self.line,
                "msg": self.msg}


def analyse_file(rel, text, F, info):
    pkg = rel.split("/")[1] if rel.count("/") >= 2 else ""
    lines = lex(text)
    n = len(lines)
    codes = [l.code for l in lines]
    raw = [l.raw for l in lines]
    fileinfo = {"pkg": pkg, "classes": [], "methods": [], "fields": [], "imports": []}
    info["files"][rel] = fileinfo

    def add(rule, level, no, msg):
        F.append(Finding(rule, level, rel, no, msg))

    # ---------- imports ----------
    for l in lines:
        mi = re.match(r"\s*import\s+(static\s+)?([\w.]+(?:\.\*)?)\s*;", l.code)
        if mi:
            fileinfo["imports"].append((l.no, mi.group(2)))
    body_code = "\n".join(c for c in codes if not re.match(r"\s*(import|package)\b", c))
    for no, imp in fileinfo["imports"]:
        simple = imp.split(".")[-1]
        if simple == "*":
            continue
        if not re.search(r"\b%s\b" % re.escape(simple), body_code):
            add("3.6", "VI_PHAM", no, "import '%s' không dùng tới" % imp)

    # ---------- line-level formatting ----------
    prev_code_line = ""
    for idx, l in enumerate(lines):
        c = l.code
        s = c.strip()
        code_len = len(c.rstrip())
        if code_len > 100:
            add("2.3", "VI_PHAM", l.no, "dòng code dài %d ký tự (> 100)" % code_len)
        elif len(l.raw.rstrip()) > 100:
            add("2.3", "RUI_RO", l.no, "dòng dài %d ký tự tính cả comment" % len(l.raw.rstrip()))
        if s == "{":
            add("2.1", "VI_PHAM", l.no, "'{' đứng riêng một dòng — phải ở CUỐI dòng khai báo")
        if re.search(r";\s*\}", c) and not re.search(r"\{[^{}]*;\s*\}", c.split("//")[0]) is None:
            pass
        if re.search(r"[^\s{]\s*\}\s*$", c) and not re.search(r"\{.*\}", c) and s != "}":
            if not s.startswith("}"):
                add("2.1", "VI_PHAM", l.no, "'}' phải đứng ĐẦU dòng")
        # one statement per line (2.7) and single-line blocks
        c_nofor = c
        mf = re.search(r"\bfor\s*\(", c)
        if mf:
            dd, q = 1, mf.end()
            while q < len(c) and dd:
                dd += {"(": 1, ")": -1}.get(c[q], 0)
                q += 1
            c_nofor = c[:mf.start()] + "for()" + c[q:]
        if c_nofor.count(";") > 1:
            add("2.7", "VI_PHAM", l.no, "nhiều hơn 1 câu lệnh trên một dòng")
        elif re.search(r"\{\s*[^{}\s][^{}]*;\s*\}", c) and not re.search(r"=\s*\{", c) \
                and not re.search(r"new\s+\w+(\[\])+\s*\{", c):
            add("2.7", "VI_PHAM", l.no, "khối { ...; } viết trên một dòng — tách ra nhiều dòng")
        # braces (2.2)
        if re.match(r"^\s*(\}\s*)?(if|else\s+if|for|while)\s*\(", c) and not s.endswith("{") \
                and not s.endswith(";") and s.count("(") == s.count(")") and not s.endswith("&&") \
                and not s.endswith("||"):
            # a full header on one line with no brace -> body on the next line without {}
            nxt = idx + 1
            while nxt < n and not lines[nxt].code.strip():
                nxt += 1
            if nxt < n and not lines[nxt].code.strip().startswith("{"):
                add("2.2", "VI_PHAM", l.no, "if/for/while không có { } (kể cả 1 dòng cũng phải có)")
        if re.match(r"^\s*(\}\s*)?else\s*$", c) or re.match(r"^\s*do\s*$", c):
            add("2.2", "VI_PHAM", l.no, "else/do không có { }")
        m1 = re.match(r"^\s*(\}\s*)?(if|else\s+if|for|while)\s*\((.*)\)\s*[^{\s].*;\s*$", c)
        if m1 and s.count("(") == s.count(")") and not re.match(r"^\}\s*while\s*\(.*\)\s*;", s):
            add("2.2", "VI_PHAM", l.no, "if/for/while một dòng không có { }")
        if re.match(r"^\s*(\}\s*)?else\s+[^{i\s].*;\s*$", c):
            add("2.2", "VI_PHAM", l.no, "else một dòng không có { }")
        # wrapping (2.3)
        if re.match(r"^\s*(&&|\|\|)", c):
            add("2.3", "VI_PHAM", l.no, "xuống dòng TRƯỚC &&/|| — thầy: break SAU toán tử logic")
        if re.search(r"[^+\-*/]\s*[+\-*/]\s*$", c) and not re.search(r"(\+\+|--)\s*$", c) \
                and not s.startswith("*") and not s.startswith("//"):
            add("2.3", "VI_PHAM", l.no, "xuống dòng SAU toán tử + - * / — thầy: break TRƯỚC toán hạng")
        # spaces (2.9)
        if re.search(r"\b(if|for|while|switch|catch|synchronized)\(", c):
            add("2.9", "VI_PHAM", l.no, "thiếu dấu cách trước '(' sau từ khoá")
        cc = re.sub(r"'[^']*'", "''", c)
        if re.search(r",(?=[^\s])", cc):
            add("2.9", "VI_PHAM", l.no, "thiếu dấu cách sau ','")
        cc = re.sub(r"\b\d+(\.\d+)?[eE][-+]?\d+\b", "0", cc)
        cc = re.sub(r"<[\w<>, ?\[\]]*>\s*\(", "(", cc)
        ops_bad = re.search(r"(?<=[\w)\]\"])(==|!=|<=|>=|&&|\|\||\+=|-=|\*=|/=|%=)|"
                            r"(==|!=|<=|>=|&&|\|\||\+=|-=|\*=|/=|%=)(?=[\w(\"!])", cc)
        asg_bad = re.search(r"(?<=[\w)\]\"])=(?=[^=\s])|(?<=[^=!<>+\-*/%&|^\s])=(?=[\w(\"'\-])", cc)
        ar_bad = re.search(r"(?<=[\w)\]\"])[+*/%](?=[\w(\"'])", cc) and not re.search(r"\+\+", cc)
        mi_bad = re.search(r"(?<=[\w)\]\"])-(?=[\w(\"'])", cc) and not re.search(r"--|->", cc)
        lt_bad = re.search(r"(?<=[a-z0-9_)\]])<(?=[a-z0-9_(])|(?<=[a-z0-9_)\]])>(?=[a-z0-9_(])", cc)
        if (ops_bad or asg_bad or ar_bad or mi_bad or lt_bad) and not s.startswith("*") \
                and not s.startswith("import") and not s.startswith("package"):
            add("2.9", "VI_PHAM", l.no, "thiếu dấu cách quanh toán tử: '%s'" % s[:60])
        if re.search(r"\((int|long|short|byte|char|float|double|boolean|String|Integer|Double)\)"
                     r"(?=[\w(])", cc):
            add("2.9", "VI_PHAM", l.no, "ép kiểu phải có dấu cách sau: (int) x")
        mfor = re.search(r"\bfor\s*\((.*)\)", cc)
        if mfor and re.search(r";(?=\S)", mfor.group(1)):
            add("2.9", "VI_PHAM", l.no, "trong for(...) phải có dấu cách sau ';'")
        # array style (2.5)
        if re.search(r"\b[A-Za-z_][\w<>]*\s+[a-zA-Z_]\w*\s*\[\s*\]\s*[=;,)]", c) and \
                not re.search(r"\bnew\b\s+\w+\s*\[", c.split("=")[0]):
            add("2.5", "VI_PHAM", l.no, "khai báo mảng kiểu C 'int a[]' — phải 'int[] a'")
        # string equality (3.5)
        if re.search(r'(==|!=)\s*""|""\s*(==|!=)', c):
            add("3.5", "VI_PHAM", l.no, "so sánh chuỗi bằng ==/!= — phải dùng equals()")
        prev_code_line = c if s else prev_code_line

    # ---------- structure: types, members, bodies ----------
    depth = 0
    type_stack = []   # (name, kind, depth_of_body)
    i = 0
    members = []
    while i < n:
        c = codes[i]
        s = c.strip()
        tm = TYPE_DECL.match(c)
        if tm and "(" not in tm.group("rest").split("{")[0]:
            cls = {"name": tm.group("name"), "kind": tm.group("kind"), "line": i + 1,
                   "mods": tm.group("mods"), "rest": tm.group("rest"), "body_depth": depth + 1,
                   "static_methods": 0, "inst_methods": 0, "ctors": [], "fields": []}
            fileinfo["classes"].append(cls)
            type_stack.append(cls)
        elif type_stack and depth == type_stack[-1]["body_depth"] and s and not s.startswith("@") \
                and not s.startswith("}") and _prev_code_ends(codes, i):
            cls = type_stack[-1]
            # member at class level: field or method
            hdr = c
            j = i
            # gather multi-line header until '{' or ';'
            while not re.search(r"[{;]\s*$", codes[j]) and j + 1 < n and j - i < 6:
                j += 1
                hdr += " " + codes[j].strip()
            mh = METHOD_HEAD.match(hdr)
            fd = FIELD_DECL.match(hdr) if (not mh or "=" in hdr.split("(")[0]) and \
                not re.match(r"^\s*[A-Z_][A-Z0-9_]*\s*\(", hdr) else None
            if fd and "(" in hdr.split("=")[0]:
                fd = None
            if fd and fd.group("name") not in KEYWORDS and fd.group("type") not in NOT_TYPE:
                mods = fd.group("mods")
                fld = {"name": fd.group("name"), "type": fd.group("type") + fd.group("arr"),
                       "line": i + 1, "mods": mods, "cls": cls["name"],
                       "rest": fd.group("rest")}
                cls["fields"].append(fld)
                fileinfo["fields"].append(fld)
                # multi decl
                rest = strip_generics(fd.group("rest"))
                if re.match(r",", rest.strip()) or (rest.startswith("=") and
                                                  "," in depth0(rest.split(";")[0])[1:]
                                                  and not rest.strip().startswith("= {")):
                    add("2.4", "VI_PHAM", i + 1, "nhiều biến khai báo trên một dòng")
            elif mh and mh.group("name") not in KEYWORDS and cls["kind"] != "enum" or \
                    (mh and cls["kind"] == "enum" and mh.group("ret")):
                name = mh.group("name")
                is_ctor = name == cls["name"] and not mh.group("ret")
                if not mh.group("ret") and not is_ctor:
                    i = j + 1
                    continue
                ptxt = hdr[hdr.find("(") + 1:]
                # cut at matching ')'
                dd, k = 1, 0
                while k < len(ptxt) and dd:
                    if ptxt[k] == "(":
                        dd += 1
                    elif ptxt[k] == ")":
                        dd -= 1
                    k += 1
                params = params_of(ptxt[:k - 1])
                meth = {"name": name, "ctor": is_ctor, "line": i + 1, "mods": mh.group("mods"),
                        "ret": mh.group("ret"), "params": params, "cls": cls["name"],
                        "cls_kind": cls["kind"], "abstract": hdr.rstrip().endswith(";"),
                        "override": False, "body": (None, None)}
                back = i - 1
                while back >= 0 and (not codes[back].strip() or codes[back].strip().startswith("@")):
                    if "@Override" in codes[back]:
                        meth["override"] = True
                    back -= 1
                if is_ctor:
                    cls["ctors"].append(meth)
                elif "static" in (mh.group("mods") or ""):
                    cls["static_methods"] += 1
                else:
                    cls["inst_methods"] += 1
                # body range
                if not meth["abstract"] and cls["kind"] != "interface":
                    d2 = 0
                    start = j
                    k2 = i
                    started = False
                    while k2 < n:
                        d2 += codes[k2].count("{") - codes[k2].count("}")
                        if codes[k2].count("{"):
                            started = True
                        if started and d2 <= 0:
                            break
                        k2 += 1
                    meth["body"] = (start, k2)
                members.append(meth)
                fileinfo["methods"].append(meth)
        depth += c.count("{") - c.count("}")
        while type_stack and depth < type_stack[-1]["body_depth"]:
            type_stack.pop()
        i += 1

    # ---------- naming 1.2 / 1.3 / 1.4 ----------
    mp = re.search(r"^\s*package\s+([\w.]+)\s*;", "\n".join(codes), re.M)
    if mp and mp.group(1) != mp.group(1).lower():
        add("1.2", "VI_PHAM", 1, "package phải viết thường")
    for cls in fileinfo["classes"]:
        nm = cls["name"]
        if not nm[0].isupper():
            add("1.3", "VI_PHAM", cls["line"], "tên class phải bắt đầu bằng chữ hoa")
        if cls["kind"] == "interface" and not re.match(r"I[A-Z]", nm):
            add("1.3", "VI_PHAM", cls["line"], "interface '%s' phải bắt đầu bằng 'I' (vd I%s)" % (nm, nm))
        if re.search(r"\bextends\s+(\w*Exception|Throwable|Error)\b", cls["rest"]) and \
                not nm.endswith("Exception"):
            add("1.3", "VI_PHAM", cls["line"], "class exception '%s' phải KẾT THÚC bằng 'Exception'" % nm)
        core = nm[1:] if re.match(r"I[A-Z][a-z]", nm) else nm
        if re.search(r"ID(?![a-z])", core) and not nm.isupper():
            add("1.5", "VI_PHAM", cls["line"], "viết 'Id' chứ không 'ID' (%s)" % nm)
    for m in fileinfo["methods"]:
        if m["ctor"]:
            continue
        nm = m["name"]
        if not nm[0].islower():
            add("1.4", "VI_PHAM", m["line"], "tên method '%s' phải bắt đầu bằng chữ thường" % nm)
        elif camel_first_word(nm) not in VERBS:
            add("1.4", "RUI_RO", m["line"], "tên method '%s' không mở đầu bằng động từ quen thuộc — xem lại" % nm)
        if re.search(r"ID(?![A-Z_])|ID[A-Z]", nm):
            add("1.5", "VI_PHAM", m["line"], "viết 'Id' chứ không 'ID' (%s)" % nm)

    # ---------- 3.4 static-only classes ----------
    for cls in fileinfo["classes"]:
        if cls["kind"] != "class":
            continue
        only_static_members = cls["static_methods"] > 0 and cls["inst_methods"] == 0 and \
            all("static" in (f["mods"] or "") for f in cls["fields"])
        if only_static_members and cls["name"] != "Main":
            if "final" not in cls["mods"]:
                add("3.4", "VI_PHAM", cls["line"], "class chỉ có hàm static phải khai báo 'final'")
            if not any("private" in (c["mods"] or "") for c in cls["ctors"]):
                add("3.4", "VI_PHAM", cls["line"], "class chỉ có hàm static phải có private constructor")
        if cls["name"] == "Main" and cls["static_methods"] > 0 and cls["inst_methods"] == 0:
            if "final" not in cls["mods"] or not any("private" in (c["mods"] or "") for c in cls["ctors"]):
                add("3.4", "RUI_RO", cls["line"],
                    "Main chỉ có hàm static — đọc chữ tờ giấy thì cũng phải 'final' + private constructor")

    # ---------- 2.10 constants outside Constants/Message ----------
    base = os.path.basename(rel)
    for f in fileinfo["fields"]:
        mods = f["mods"] or ""
        if "static" in mods and "final" in mods:
            if not re.fullmatch(r"[A-Z][A-Z0-9_]*", f["name"]) and f["name"] != "serialVersionUID":
                add("2.10", "VI_PHAM", f["line"], "hằng '%s' phải VIET_HOA_CACH_GACH" % f["name"])
            if pkg != "constants" and f["name"] != "serialVersionUID":
                add("2.10", "RUI_RO", f["line"],
                    "hằng '%s' nằm ngoài Constants.java/Message.java" % f["name"])
        elif pkg == "constants" and base in ("Constants.java", "Message.java") and \
                f["name"] != "serialVersionUID":
            add("2.10", "VI_PHAM", f["line"], "'%s' trong %s thiếu 'static final'" % (f["name"], base))

    field_names = {f["name"]: f for f in fileinfo["fields"]}
    str_names = {f["name"] for f in fileinfo["fields"] if f["type"].strip() == "String"}

    # ---------- naming of fields ----------
    def check_var_name(name, tp, no, where):
        if name in ("serialVersionUID",):
            return
        kind = type_kind(tp)
        if kind and not name.endswith(kind):
            if name == "args" and tp.startswith("String"):
                add("1.5", "RUI_RO", no, "'String[] args' của main không kết thúc bằng Array (Java chuẩn, "
                                         "nhưng đọc chữ tờ giấy thì là lệch)")
            else:
                add("1.5", "VI_PHAM", no, "%s '%s' kiểu %s phải kết thúc bằng '%s'"
                    % (where, name, tp.strip(), kind))
        if re.search(r"ID(?![A-Z_])|ID[A-Z]", name) and not name.isupper():
            add("1.5", "VI_PHAM", no, "viết 'Id' chứ không 'ID' (%s)" % name)
        if not name[0].islower() and not name.isupper():
            add("1.5", "VI_PHAM", no, "tên biến '%s' phải bắt đầu bằng chữ thường" % name)
        if len(name) == 1 and name not in ("i", "j", "k", "e"):
            add("1.5", "RUI_RO", no, "tên biến 1 chữ '%s' — thầy đòi 'có ý nghĩa'" % name)

    for f in fileinfo["fields"]:
        mods = f["mods"] or ""
        if not ("static" in mods and "final" in mods):
            check_var_name(f["name"], f["type"], f["line"], "trường")

    # ---------- per-method body analysis ----------
    for m in fileinfo["methods"]:
        for tp, pn in m["params"]:
            check_var_name(pn, tp, m["line"], "tham số")
            if m["ctor"] or (m["name"].startswith("set") and len(m["params"]) == 1):
                if pn in field_names:
                    add("3.2", "RUI_RO", m["line"],
                        "tham số '%s' trùng tên field (setter/constructor kiểu this.x = x — IDE sinh, "
                        "thường được chấp nhận)" % pn)
            elif pn in field_names:
                add("3.2", "RUI_RO", m["line"], "tham số '%s' trùng tên field '%s' (không phải setter)" % (pn, pn))
        if m["body"][0] is None:
            continue
        b0, b1 = m["body"]
        body_idx = list(range(b0 + 1, b1))
        body_text = "\n".join(codes[k] for k in body_idx)
        # unused params
        if not m["abstract"] and not m["override"] and not (m["name"] == "main" and m["params"]
                                                            and m["params"][0][1] == "args"):
            for tp, pn in m["params"]:
                if not re.search(r"\b%s\b" % re.escape(pn), body_text):
                    add("3.6", "VI_PHAM", m["line"], "tham số '%s' của %s() khai báo mà không dùng"
                        % (pn, m["name"]))
        local_str = set(str_names) | {pn for tp, pn in m["params"] if tp == "String"}
        # block-structured walk
        stack = [{"seen_stmt": False, "last": "open", "decls": set(), "kind": "method"}]
        prev_terminated = True
        prev_kind = "open"      # open | decl | stmt | comment | blank | close
        prev_nonblank_kind = "open"
        blank_before = False
        locals_seen = {}
        for k in body_idx:
            l = lines[k]
            c = l.code
            s = c.strip()
            if not s:
                if l.has_comment and not l.in_block and raw[k].strip().startswith(("//", "/*")):
                    # standalone comment
                    if not blank_before and prev_kind in ("stmt", "decl", "close"):
                        if prev_kind == "decl":
                            add("2.8", "VI_PHAM", l.no,
                                "thiếu dòng trống giữa vùng khai báo biến và phần còn lại")
                        else:
                            add("2.8", "VI_PHAM", l.no, "thiếu dòng trống TRƯỚC comment")
                    prev_kind = "comment"
                    blank_before = False
                elif l.in_block or l.has_comment:
                    prev_kind = "comment"
                    blank_before = False
                else:
                    blank_before = True
                    prev_kind = "blank" if prev_kind != "comment" else prev_kind
                    if prev_kind == "blank":
                        pass
                continue
            continuation = not prev_terminated
            opens = c.count("{")
            closes = c.count("}")
            if not continuation:
                if s.startswith("}"):
                    lead = len(re.match(r"^(\}\s*)+", s).group(0).replace(" ", ""))
                    for _ in range(lead):
                        if len(stack) > 1:
                            stack.pop()
                    rem = s.lstrip("} ").strip()
                    if rem and s.endswith("{"):
                        stack.append({"seen_stmt": False, "decls": set(), "kind": "blk"})
                    this_kind = "close" if not rem else ("open" if s.endswith("{") else "stmt")
                elif re.match(r"^(case\b.*|default\s*):\s*$", s) or re.match(r"^case\b.*:", s):
                    stack[-1]["seen_stmt"] = False
                    this_kind = "open"
                else:
                    ld = LOCAL_DECL.match(c)
                    is_decl = bool(ld) and ld.group("type") not in NOT_TYPE and \
                        ld.group("name") not in KEYWORDS and not re.match(r"^\s*\w+\s*\(", c)
                    if is_decl:
                        tp = ld.group("type") + ld.group("arr")
                        nm = ld.group("name")
                        rest = ld.group("rest")
                        if stack[-1]["seen_stmt"]:
                            add("2.6", "VI_PHAM", l.no,
                                "biến '%s' khai báo GIỮA block — phải gom lên ĐẦU block" % nm)
                        if rest.strip().startswith(";"):
                            add("3.7", "VI_PHAM", l.no, "biến '%s' khai báo mà không khởi tạo" % nm)
                        r2 = strip_generics(rest)
                        if r2.strip().startswith(",") or ("," in depth0(r2.split(";")[0]) and
                                                          not re.search(r"=\s*\{", r2) and
                                                          not re.search(r"\(", r2.split(",")[0])):
                            if re.match(r"\s*(=\s*[^,(]*)?,\s*[a-zA-Z_]\w*\s*(=|;|,)", r2):
                                add("2.4", "VI_PHAM", l.no, "nhiều biến khai báo trên một dòng")
                        check_var_name(nm, tp, l.no, "biến")
                        if nm in field_names:
                            add("3.2", "VI_PHAM", l.no, "biến local '%s' trùng tên field" % nm)
                        if nm in locals_seen:
                            add("3.2", "RUI_RO", l.no, "biến '%s' khai báo lại (đã có ở dòng %d)"
                                % (nm, locals_seen[nm]))
                        locals_seen[nm] = l.no
                        if tp == "String":
                            local_str.add(nm)
                        # unused local
                        after = "\n".join(codes[q] for q in range(k + 1, b1))
                        same = re.sub(r"^\s*(final\s+)?[\w.<>\[\], ]+?\s+%s\b" % re.escape(nm), "", c)
                        if not re.search(r"\b%s\b" % re.escape(nm), after + "\n" + same):
                            add("3.6", "VI_PHAM", l.no, "biến '%s' khai báo mà không dùng" % nm)
                        this_kind = "decl"
                        if prev_kind == "stmt" and not blank_before:
                            pass
                    else:
                        stack[-1]["seen_stmt"] = True
                        this_kind = "stmt"
                        # blank line between decl region and first statement
                        if prev_kind == "decl" and not blank_before:
                            add("2.8", "VI_PHAM", l.no,
                                "thiếu dòng trống giữa vùng khai báo biến và phần còn lại")
                        if prev_kind == "close" and not blank_before and \
                                not re.match(r"^(else|catch|finally|while\b.*;|break;|continue;|return;)", s):
                            add("2.8", "RUI_RO", l.no, "nên có dòng trống giữa hai khối xử lý logic")
                        if opens > closes and s.endswith("{"):
                            stack.append({"seen_stmt": False, "decls": set(), "kind": "blk"})
                            this_kind = "open"
                    # for-each variable naming
                    fe = re.match(r"^\s*for\s*\(\s*(?:final\s+)?([\w.<>\[\], ]+?)\s+(\w+)\s*:", c)
                    if fe:
                        check_var_name(fe.group(2), fe.group(1), l.no, "biến lặp")
                        if fe.group(2) in field_names:
                            add("3.2", "VI_PHAM", l.no, "biến lặp '%s' trùng tên field" % fe.group(2))
                    fi = re.match(r"^\s*for\s*\(\s*(?:final\s+)?([\w.<>\[\]]+)\s+(\w+)\s*=", c)
                    if fi and fi.group(2) in field_names:
                        add("3.2", "VI_PHAM", l.no, "biến lặp '%s' trùng tên field" % fi.group(2))
                    ct = re.match(r"^\s*\}?\s*catch\s*\(\s*[\w.| ]+\s+(\w+)\s*\)", c)
                    if ct and ct.group(1) in field_names:
                        add("3.2", "VI_PHAM", l.no, "biến catch '%s' trùng tên field" % ct.group(1))
            else:
                this_kind = prev_kind if prev_kind in ("decl", "stmt") else "stmt"
                if opens > closes and s.endswith("{"):
                    stack[-1]["seen_stmt"] = True
                    stack.append({"seen_stmt": False, "decls": set(), "kind": "blk"})
                    this_kind = "open"
                if closes > opens and s.startswith("}"):
                    for _ in range(closes - opens):
                        if len(stack) > 1:
                            stack.pop()
            prev_terminated = bool(re.search(r"[;{}:]\s*$", s)) or s.startswith("@")
            prev_kind = this_kind
            blank_before = False

        # 3.3 parentheses: if/while/for-condition/return/assignment
        joined = "\n".join(codes[k] for k in body_idx)
        for mm in re.finditer(r"\b(if|while)\s*\(", joined):
            st = mm.end()
            dd, q = 1, st
            while q < len(joined) and dd:
                if joined[q] == "(":
                    dd += 1
                elif joined[q] == ")":
                    dd -= 1
                q += 1
            cond = joined[st:q - 1].replace("\n", " ")
            lineno = b0 + 2 + joined[:mm.start()].count("\n")
            for lvl, msg in analyse_expr(cond):
                add("3.3", lvl, lineno, msg)
            # String == comparisons
            for side in re.finditer(r"(\w+)\s*(==|!=)\s*(\w+)", cond):
                a, b = side.group(1), side.group(3)
                if (a in local_str and b != "null") or (b in local_str and a != "null"):
                    add("3.5", "VI_PHAM", lineno, "so sánh String '%s %s %s' bằng ==" % (a, side.group(2), b))
        for mm in re.finditer(r"(?:\breturn\s+|[^=!<>]=\s*)([^;{]+);", joined):
            expr = mm.group(1)
            if expr.strip().startswith("new ") or "->" in expr:
                continue
            lineno = b0 + 2 + joined[:mm.start()].count("\n")
            for lvl, msg in analyse_expr(expr):
                add("3.3", lvl, lineno, msg)
        for mm in re.finditer(r"\bfor\s*\(([^;]*);([^;]*);", joined):
            lineno = b0 + 2 + joined[:mm.start()].count("\n")
            for lvl, msg in analyse_expr(mm.group(2)):
                add("3.3", lvl, lineno, msg)
        # 3.8 string concatenation
        for k in body_idx:
            c = codes[k]
            m1 = re.search(r"\b(\w+)\s*\+=", c)
            if m1 and (m1.group(1) in local_str or re.search(r'\+=\s*""', c)):
                add("3.8", "VI_PHAM", k + 1, "cộng chuỗi bằng '+=' — phải dùng StringBuilder")
            m2 = re.search(r"\b(\w+)\s*=\s*\1\s*\+", c)
            if m2 and m2.group(1) in local_str:
                add("3.8", "VI_PHAM", k + 1, "cộng chuỗi 's = s + ...' — phải dùng StringBuilder")
            elif re.search(r'""\s*\+|\+\s*""', c) or any(
                    re.search(r"\b%s\s*\+(?!\+)|\+\s*%s\b" % (re.escape(v), re.escape(v)), c)
                    for v in local_str):
                add("3.8", "RUI_RO", k + 1, "nối chuỗi bằng '+' — đọc chặt '3.8' thì nên dùng "
                                            "StringBuilder / String.format")

    # private methods never called; private fields never read
    alltext = "\n".join(codes)
    for m in fileinfo["methods"]:
        if "private" in (m["mods"] or "") and not m["ctor"]:
            calls = len(re.findall(r"\b%s\s*\(" % re.escape(m["name"]), alltext))
            if calls <= 1:
                add("3.6", "VI_PHAM", m["line"], "hàm private '%s' không được gọi ở đâu" % m["name"])
    for f in fileinfo["fields"]:
        if "private" in (f["mods"] or ""):
            occ = [mm for mm in re.finditer(r"\b%s\b" % re.escape(f["name"]), alltext)]
            if len(occ) <= 1:
                add("3.6", "VI_PHAM", f["line"], "field '%s' khai báo mà không dùng" % f["name"])
            else:
                reads = [mm for mm in occ[1:] if not re.match(r"\s*=(?!=)", alltext[mm.end():])]
                if not reads:
                    add("3.6", "VI_PHAM", f["line"], "field '%s' chỉ được GÁN mà không bao giờ được ĐỌC"
                        % f["name"])

    # 2.8 between members (methods/ctors) at class level
    for m in fileinfo["methods"]:
        k = m["line"] - 1
        k -= 1
        while k >= 0 and (lines[k].has_comment and not codes[k].strip() or codes[k].strip().startswith("@")
                          or lines[k].in_block):
            k -= 1
        if k >= 0 and raw[k].strip() and not codes[k].rstrip().endswith("{"):
            add("2.8", "VI_PHAM", m["line"], "thiếu dòng trống giữa các method (trước '%s')" % m["name"])
    # 2.8 before comments at class level (field comments packed together)
    depth = 0
    for k in range(n):
        c = codes[k]
        if depth >= 1 and not c.strip() and lines[k].has_comment and not lines[k].in_block and \
                raw[k].strip().startswith("//"):
            pk = k - 1
            if pk >= 0 and raw[pk].strip() and codes[pk].strip() and \
                    not codes[pk].rstrip().endswith("{") and \
                    not any(m["body"][0] is not None and m["body"][0] < k < m["body"][1]
                            for m in fileinfo["methods"]):
                add("2.8", "VI_PHAM", k + 1, "thiếu dòng trống TRƯỚC comment (vùng khai báo field)")
        depth += c.count("{") - c.count("}")


def analyse_project(root):
    F = []
    info = {"files": {}}
    src = os.path.join(root, "src")
    files = []
    for dp, _dn, fn in os.walk(src):
        for f in fn:
            if f.endswith(".java"):
                files.append(os.path.relpath(os.path.join(dp, f), root).replace(os.sep, "/"))
    for rel in sorted(files):
        with open(os.path.join(root, rel), encoding="utf-8") as fh:
            analyse_file(rel, fh.read(), F, info)
    pkgs = {r.split("/")[1] for r in files if r.count("/") >= 2}
    arch = {"has_repository": "repository" in pkgs, "has_service": "service" in pkgs,
            "packages": sorted(pkgs)}
    if "repository" not in pkgs:
        F.append(Finding("1.1", "VI_PHAM", "src/", 0,
                         "KHÔNG có package repository — tờ checklist: 'Bắt buộc phải có repository'"))
    for rel, fi in info["files"].items():
        pkg = fi["pkg"]
        for no, imp in fi["imports"]:
            top = imp.split(".")[0]
            if pkg == "controller" and top == "model":
                F.append(Finding("1.1", "VI_PHAM", rel, no,
                                 "controller import model — 'Controller ... không làm việc với Model'"))
            if pkg == "controller" and top == "utils":
                F.append(Finding("1.1", "RUI_RO", rel, no,
                                 "controller import utils — Guide: controller 'chỉ import DTO, View, Service'"))
            if pkg == "main" and top in ("model", "view", "service", "repository"):
                F.append(Finding("1.1", "VI_PHAM", rel, no,
                                 "main import %s — 'Main chỉ làm việc với Controller, DTO, Utils'" % top))
            if pkg == "view" and top in ("model", "service", "repository", "controller"):
                F.append(Finding("1.1", "VI_PHAM", rel, no, "view import %s — View chỉ nhận ResponseDTO" % top))
        if pkg == "view":
            has_dto_field = any("DTO" in f["type"] for f in fi["fields"])
            if not has_dto_field:
                F.append(Finding("1.1", "RUI_RO", rel, 0,
                                 "view không có thuộc tính ResponseDTO — tờ giấy: 'nhận qua thuộc tính (ResponseDTO)'"))
            for m in fi["methods"]:
                if m["ctor"] or "private" in (m["mods"] or ""):
                    continue
                if m["params"] and not (m["name"].startswith("set") and len(m["params"]) == 1):
                    F.append(Finding("1.1", "VI_PHAM", rel, m["line"],
                                     "view.%s(%s) nhận dữ liệu qua THAM SỐ — tờ giấy: 'Không nên truyền qua "
                                     "param mà phải nhận qua thuộc tính'" %
                                     (m["name"], ", ".join(t for t, _ in m["params"]))))
        if pkg == "controller":
            view_fields = [f["name"] for f in fi["fields"] if f["type"].endswith("View")]
            for m in fi["methods"]:
                if m["ctor"] or m["body"][0] is None:
                    continue
                if "public" in (m["mods"] or ""):
                    for tp, pn in m["params"]:
                        if not tp.endswith("DTO"):
                            F.append(Finding("1.1", "RUI_RO", rel, m["line"],
                                             "controller.%s nhận '%s %s' — tờ giấy: 'Controller nhận input từ "
                                             "main qua DTO'" % (m["name"], tp, pn)))
                    for tp, pn in m["params"]:
                        if tp.split(".")[-1][0].isupper() and not tp.endswith("DTO") and tp not in (
                                "String", "Integer", "Double", "Locale", "Object") and \
                                "model." in " ".join(i for _, i in fi["imports"]) and \
                                any(i.endswith("." + tp) and i.startswith("model.") for _, i in fi["imports"]):
                            F.append(Finding("1.1", "VI_PHAM", rel, m["line"],
                                             "controller.%s nhận MODEL '%s' — Controller không làm việc với Model"
                                             % (m["name"], tp)))
                b0, b1 = m["body"]
                body = "\n".join(l for l in open(os.path.join(root, rel), encoding="utf-8").read()
                                 .split("\n")[b0 + 1:b1])
                renders = []
                for vf in view_fields:
                    for mm in re.finditer(r"\b%s\s*\.\s*(\w+)\s*\(" % re.escape(vf), body):
                        if not mm.group(1).startswith("set"):
                            renders.append(mm.group(1))
                if len(renders) > 1:
                    F.append(Finding("1.1", "VI_PHAM", rel, m["line"],
                                     "controller.%s gọi view %d lần (%s) — tờ giấy: 'rendering chỉ được gọi 1 "
                                     "lần cho 1 luồng xử lý'" % (m["name"], len(renders), ", ".join(renders))))
                if renders and re.search(r"\b(for|while)\s*\(", body):
                    F.append(Finding("1.1", "RUI_RO", rel, m["line"],
                                     "controller.%s có vòng lặp và gọi view — kiểm xem view có bị gọi trong vòng lặp"
                                     % m["name"]))
        if pkg == "main":
            text = open(os.path.join(root, rel), encoding="utf-8").read()
            ctrl_vars = re.findall(r"\b(\w+Controller)\s+(\w+)\s*[=;,)]", text)
            names = {v for _, v in ctrl_vars}
            for m in fi["methods"]:
                if m["body"][0] is None:
                    continue
                b0, b1 = m["body"]
                body_lines = text.split("\n")[b0 + 1:b1]
                body = "\n".join(body_lines)
                calls = []
                for v in names:
                    calls += [(mm.group(1), body[:mm.start()].count("\n") + b0 + 2)
                              for mm in re.finditer(r"\b%s\s*\.\s*(\w+)\s*\(" % re.escape(v), body)]
                # split by case labels
                if re.search(r"\bcase\b", body):
                    segs = re.split(r"\n\s*(?:case\b[^:]*|default)\s*:", body)
                    for sg in segs[1:]:
                        cs = [mm.group(1) for v in names for mm in re.finditer(r"\b%s\s*\.\s*(\w+)\s*\(" % re.escape(v), sg)]
                        if len(cs) > 1:
                            F.append(Finding("1.1", "VI_PHAM", rel, m["line"],
                                             "một case trong %s() gọi controller %d lần (%s) — Guide: 'Mỗi workflow "
                                             "chỉ gọi vào controller 1 lần duy nhất'" % (m["name"], len(cs), ", ".join(cs))))
                elif len(calls) > 1 and m["name"] != "main":
                    F.append(Finding("1.1", "VI_PHAM", rel, m["line"],
                                     "%s() gọi controller %d lần (%s)" % (m["name"], len(calls),
                                                                          ", ".join(c for c, _ in calls))))
                if calls and re.search(r"\b(while|for|do)\b", body) and m["name"] != "main":
                    F.append(Finding("1.1", "RUI_RO", rel, m["line"],
                                     "%s() gọi controller trong vòng lặp hỏi lại (%s)" %
                                     (m["name"], ", ".join(sorted({c for c, _ in calls})))))
    return F, info, arch


def main(argv):
    as_json = "--json" in argv
    roots = [a for a in argv if a != "--json"]
    out = {}
    for root in roots:
        root = root.rstrip("/")
        F, info, arch = analyse_project(root)
        out[os.path.basename(root)] = {"arch": arch, "findings": [f.js() for f in F]}
    if as_json:
        json.dump(out, sys.stdout, ensure_ascii=False, indent=1)
        return
    for name, res in out.items():
        found = res["findings"]
        bad = [f for f in found if f["level"] == "VI_PHAM"]
        risk = [f for f in found if f["level"] == "RUI_RO"]
        print("== %s: %d VI PHAM, %d rui ro" % (name, len(bad), len(risk)))
        for f in bad + risk:
            print("  [%s %s] %s:%s  %s" % (f["level"], f["rule"], f["file"], f["line"], f["msg"]))
        if not bad:
            print("  -> 0 vi pham: du dieu kien dien O ca 25 muc (rui ro thi doc lai, tu quyet)")


if __name__ == "__main__":
    main(sys.argv[1:])
