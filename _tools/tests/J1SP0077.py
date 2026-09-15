"""Keystroke runs for J1.S.P0077 (count a word / find files by content).

REPLACE_REFERENCE: the reference program created its data/ folder on the
first run ("Sample folder created: data") and its run 1 expects that folder
to survive from run 0, while verify.py gives every run a fresh copy of the
project root. Here data/ is SHIPPED at the project root, so that line is
gone; every other screen line is the reference's / the brief's ("Bout: ",
"------------ File Name ------------").

data/ (project root):
  notes.txt   "The test plan is ready." / "test, test and Test again." /
              "Testing is not the same word as test." /
              "The latest test result is here."   -> "test" x5, "Test" x1
  readme.txt  "Run the test suite before shipping." / "See the notes ..."
  report.txt  no "test" at all
A match is a whole word, case-sensitive: "latest" and "Testing" never count.
Expected screens are written from the brief, not captured from the program.
"""

REPLACE_REFERENCE = True

MENU = ("============ Word Program =========\n"
        "1. Count Word In File\n"
        "2. Find File By Word\n"
        "3. Exit\n"
        "Your choice: ")
COUNT = "-------- Count Word --------\nEnter Path:Enter Word:"
FIND = "-------- Find File By Word --------\nEnter Path:Enter Word:"
NAMES = "------------ File Name ------------\n"

RUNS = [
    # option 1: exact case, other case, all caps, blank word, missing file,
    # a folder; option 2: two files, one file, none, missing folder
    ("1\ndata/notes.txt\ntest\n1\ndata/notes.txt\nTest\n1\ndata/notes.txt\nTEST\n"
     "1\ndata/notes.txt\n\n1\ndata/nosuch.txt\ntest\n1\ndata\ntest\n"
     "2\ndata\ntest\n2\ndata\nTest\n2\ndata\nzebra\n2\nnosuchfolder\ntest\n3\n",
     MENU + COUNT + "Bout: 5\n"
     + MENU + COUNT + "Bout: 1\n"
     + MENU + COUNT + "Bout: 0\n"
     + MENU + COUNT + "Word must not be blank.\n"
     + MENU + COUNT + "File not found: data/nosuch.txt\n"
     + MENU + COUNT + "Not a file: data\n"
     + MENU + FIND + NAMES + "notes.txt\nreadme.txt\n"
     + MENU + FIND + NAMES + "notes.txt\n"
     + MENU + FIND + NAMES + "(no file contains this word)\n"
     + MENU + FIND + "Folder not found: nosuchfolder\n"
     + MENU),

    # menu errors; option 2 on a FILE, with a blank word, and on a folder
    # holding only an empty file (test/.gitkeep); option 1 on readme.txt
    ("x\n4\n0\n2\ndata/notes.txt\ntest\n2\ndata\n\n2\ntest\ntest\n"
     "1\ndata/readme.txt\ntest\n3\n",
     MENU + "You must input a number.\n"
     "Your choice: Please choose from 1 to 3.\n"
     "Your choice: Please choose from 1 to 3.\n"
     "Your choice: " + FIND + "Not a folder: data/notes.txt\n"
     + MENU + FIND + "Word must not be blank.\n"
     + MENU + FIND + NAMES + "(no file contains this word)\n"
     + MENU + COUNT + "Bout: 1\n"
     + MENU),
]
