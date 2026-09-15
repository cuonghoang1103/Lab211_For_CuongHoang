"""Extra keystroke runs for J1.S.P0069 (write / read a text file).

The reference runs (write test.txt then read it; N then read a missing file)
match the brief's screen, so they are kept and these runs are added on top.
Expected screens are written from the brief, not captured from the program.
Every run starts in a fresh copy of the project root, so files written by
one run never leak into another.
"""

TITLE = "============ Writer Program ===============\n"
ASK_WRITE = "Do you want to write file? (Y/N or y/n):"
ASK_READ = "Do you want to read file? (Y/N or y/n):"
PATH = "Please enter file path: "
HINT = "Save file with content <save> or <SAVE>\nPlease enter file content:\n"
YN = "Please answer Y or N.\n"
EMPTY = "Path must not be empty.\n"

RUNS = [
    # every validation message: a wrong Y/N answer and a blank path at both
    # questions; lower-case y; multi-line content with an empty line and a
    # line "Save is a word" (not the stop word); ends with upper-case SAVE
    ("x\ny\n\nnotes.txt\nLine one\nSave is a word\n\nSAVE\nmaybe\ny\n  \nnotes.txt\n",
     TITLE + ASK_WRITE + YN + ASK_WRITE + PATH + EMPTY + PATH + HINT
     + ASK_READ + YN + ASK_READ + PATH + EMPTY + PATH
     + "Line one\nSave is a word\n\nRead file successfully."),

    # writeFile returns false: the folder does not exist; then n ends
    ("Y\nno_such_folder/out.txt\nhello\nsave\nn\n",
     TITLE + ASK_WRITE + PATH + HINT + "Could not write the file.\n" + ASK_READ),

    # N and N: nothing is written, nothing is read
    ("N\nN\n",
     TITLE + ASK_WRITE + ASK_READ),

    # save typed at once: an empty file is written and read back as empty
    ("Y\nempty.txt\nsave\nY\nempty.txt\n",
     TITLE + ASK_WRITE + PATH + HINT + ASK_READ + PATH + "\nRead file successfully."),

    # a folder is not a file that can be read
    ("N\nY\ntest\n",
     TITLE + ASK_WRITE + ASK_READ + PATH + "File does not exist."),
]
