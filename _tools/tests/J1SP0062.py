"""Extra keystroke runs for J1.S.P0062 (analyse a Windows file path).

The two reference runs (the brief's C:\\Windows\\test.txt and a path with
three folders and a dotted file name) match the brief's screen, so they stay
in force; these runs add what they miss.

The brief shows no error message. "Path must not be empty." is the reference
program's own; "Path must be like C:\\Windows\\test.txt" is new in this
version (the reference accepted "abc" and printed nonsense such as
"Disk: abc").
"""

HEAD = "===== Analysis Path Program =====\nPlease input Path:\n"
PROMPT = "Please input Path:\n"
EMPTY = "Path must not be empty.\n"
BAD = "Path must be like C:\\Windows\\test.txt\n"


def result(disk, ext, name, path, folders):
    return ("----- Result Analysis -----\n"
            "Disk: %s\nExtension: %s\nFile Name: %s\nPath: %s\nFolders: %s"
            % (disk, ext, name, path, folders))


RUNS = [
    # every validation message: blank, no drive, folder only (trailing \),
    # forward slashes, a forbidden character; then a file right on the disk
    ("\nabc\nC:\\Windows\\\nC:/Windows/test.txt\nC:\\a?b\\c.txt\nC:\\test.txt\n",
     HEAD + EMPTY + PROMPT + BAD + PROMPT + BAD + PROMPT + BAD + PROMPT + BAD
     + PROMPT + result("C:", "txt", "test", "C:", "[]")),
    # spaces inside folder names are legal; a file with no extension
    ("  E:\\Program Files\\Java\\README  \n",
     HEAD + result("E:", "", "README", "E:\\Program Files\\Java",
                   "[Program Files, Java]")),
    # a hidden file ".gitignore": the leading dot is NOT an extension
    ("D:\\code\\lab\\.gitignore\n",
     HEAD + result("D:", "", ".gitignore", "D:\\code\\lab", "[code, lab]")),
]
