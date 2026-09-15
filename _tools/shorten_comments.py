#!/usr/bin/env python3
"""Turn long Javadoc blocks into the lecturer's short one-line // comments.

The Guide sample writes one short // line above each method or block
("//Fuction 1: Add Doctor"), and the lecturer asked for "comment kiểu //
giải thích ngắn gọn". This script rewrites every /** ... */ block:

  * on a class / interface / enum: keep a short Javadoc (first sentence +
    @author HE176322), the way NetBeans creates a class;
  * everywhere else (fields, constructors, methods): one `// first sentence`
    line, wrapped at 90 columns when the sentence is long.

Line comments (//) and block comments inside methods are left untouched.
Run it on a project, then run lint.py / verify.py to prove nothing broke.

Usage: python3 shorten_comments.py <project_dir> [...]
"""
import glob
import re
import sys

WIDTH = 90
TYPE_LINE = re.compile(r"^\s*(public\s+)?(final\s+|abstract\s+)*(class|interface|enum)\s+\w+")


def first_sentence(lines):
    """First sentence of the description part of a Javadoc block."""
    text = []
    for raw in lines:
        s = raw.strip()
        if s.startswith("@"):
            break
        if s == "" and text:
            break
        if s:
            text.append(s)
    joined = " ".join(text)
    joined = re.sub(r"\{@\w+\s+([^}]*)\}", r"\1", joined)
    joined = re.sub(r"<[^>]+>", "", joined)
    # a sentence ends at . ! ? followed by a space or the end - never at a
    # colon, which would cut "REPOSITORY: holds the doctors" to "REPOSITORY."
    m = re.search(r"^(.+?[.!?])(\s|$)", joined)
    sentence = m.group(1) if m else joined
    sentence = sentence.rstrip(":")
    if not sentence.endswith((".", "!", "?")):
        sentence += "."
    return sentence


def wrap(sentence, indent):
    """Wrap a sentence into // lines no longer than WIDTH columns."""
    words = sentence.split()
    out, cur = [], ""
    for w in words:
        if len(indent) + 3 + len(cur) + len(w) + 1 > WIDTH and cur:
            out.append(indent + "// " + cur)
            cur = w
        else:
            cur = (cur + " " + w).strip()
    if cur:
        out.append(indent + "// " + cur)
    return out


def convert(text):
    lines = text.split("\n")
    out = []
    i = 0
    while i < len(lines):
        line = lines[i]
        m = re.match(r"^(\s*)/\*\*(.*)$", line)
        if not m:
            out.append(line)
            i += 1
            continue
        indent = m.group(1)
        body = []
        rest = m.group(2)
        if "*/" in rest:                       # one-line /** ... */
            body.append(rest.split("*/")[0])
            end = i
        else:
            if rest.strip():
                body.append(rest)
            end = i + 1
            while end < len(lines) and "*/" not in lines[end]:
                body.append(re.sub(r"^\s*\*\s?", "", lines[end]))
                end += 1
            if end < len(lines):
                last = lines[end].split("*/")[0]
                last = re.sub(r"^\s*\*\s?", "", last)
                if last.strip():
                    body.append(last)
        # what follows the comment (skip annotations / blank lines)
        j = end + 1
        while j < len(lines) and (lines[j].strip().startswith("@") or not lines[j].strip()):
            j += 1
        target = lines[j] if j < len(lines) else ""
        sentence = first_sentence(body)
        if TYPE_LINE.match(target):
            out.append(indent + "/**")
            for w in wrap(sentence, ""):
                out.append(indent + " * " + w[3:])
            out.append(indent + " *")
            out.append(indent + " * @author HE176322")
            out.append(indent + " */")
        else:
            out.extend(wrap(sentence, indent))
        i = end + 1
    return "\n".join(out)


def main(argv):
    for project in argv:
        for f in glob.glob(project.rstrip("/") + "/src/**/*.java", recursive=True):
            text = open(f, encoding="utf-8").read()
            new = convert(text)
            if new != text:
                open(f, "w", encoding="utf-8").write(new)
        print("shortened:", project)


if __name__ == "__main__":
    main(sys.argv[1:])
