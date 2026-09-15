"""Keystroke runs for J1.S.P0075 (handle file program).

REPLACE_REFERENCE: the reference runs depend on the reference project's own
folders (src/bo, src/ui) and on test.txt left behind by an earlier run,
while verify.py gives every run a fresh copy of the project root without
src/. So these runs replace them. Screen texts stay the reference's, which
match the brief's image (menu, titles, "Enter Path:", "Result 0 file!",
"Result 7 files!", "Write done", "Total:3").

Data shipped at the project root (every run starts from it):
  test.txt           one line "Hello"  -> the brief's option 4 + 5 flow
                     (append "input data", then Total:3) matches the image
  data/Main.java     22 bytes      data/Student.java   50 bytes
  data/notes.txt     25 bytes      data/report.txt     2680 bytes (40 lines
                     of 11 words)  data/sub/Other.java (inside a sub-folder:
                     must NOT be listed - only the folder itself is searched)
and test/ (made by make_netbeans.py) is an existing folder with no .java.
Expected screens are written from the brief, not captured from the program.
"""

REPLACE_REFERENCE = True

MENU = ("============ File Processing =========\n"
        "1. Check Path\n"
        "2. Get file name with type java\n"
        "3. Get file with size greater than input\n"
        "4. Write more content to file\n"
        "5. Read file and count characters\n"
        "6. Exit\n"
        "Please choice one option:")
CHECK = "---------- Check Path ---------\nEnter Path:"
JAVA = "------- Get file name with type java --------\nEnter Path:"
BIG = "--------- Get file with size greater than input --------\n"
WRITE = "------ Write more content to file ----\nEnter Content:"
COUNT = "---- Read file an count characters ----\nEnter Path:"
NOPATH = "Path doesn't exist\n"

RUNS = [
    # the brief's six screens, in the image's order
    ("1\ndata\n1\ntest.txt\n1\nabc\n"
     "2\ndata\n2\ntest\n"
     "3\na\n2\nnosuch\ndata\n"
     "4\ninput data\ntext.txt\ntest.txt\n"
     "5\ntest.txt\n6\n",
     MENU + CHECK + "Path to Directory\n"
     + MENU + CHECK + "Path to file\n"
     + MENU + CHECK + NOPATH
     + MENU + JAVA + "Main.java\nStudent.java\nResult 2 file!\n"
     + MENU + JAVA + "Result 0 file!\n"
     + MENU + BIG + "Enter Size(Integer):Value of size is digit\n"
     "Enter Size(Integer):Enter Path:" + NOPATH + "Enter Path:"
     "report.txt\nResult 1 files!\n"
     + MENU + WRITE + "Enter Path:" + NOPATH + "Enter Path:Write done\n"
     + MENU + COUNT + "Total:3\n"
     + MENU),

    # menu errors; option 2 on a missing path and on a FILE (no listing);
    # option 3 with n = 0 lists every file of data/ but not the sub-folder;
    # option 5 on a missing path, on a folder, and on the 440-word file;
    # option 4 into a folder fails; an empty line appended adds no word
    ("x\n7\n0\n"
     "2\nabc\n2\ntest.txt\n"
     "3\n0\ndata\n"
     "5\nabc\n5\ndata\n5\ndata/report.txt\n"
     "4\nsome text\ndata\n"
     "4\n\ntest.txt\n5\ntest.txt\n6\n",
     MENU + "You must input a number.\n"
     "Please choice one option:Please choose from 1 to 6.\n"
     "Please choice one option:Please choose from 1 to 6.\n"
     "Please choice one option:" + JAVA + NOPATH
     + MENU + JAVA + "Result 0 file!\n"
     + MENU + BIG + "Enter Size(Integer):Enter Path:"
     "Main.java\nStudent.java\nnotes.txt\nreport.txt\nResult 4 files!\n"
     + MENU + COUNT + NOPATH
     + MENU + COUNT + "Cannot read file\n"
     + MENU + COUNT + "Total:440\n"
     + MENU + WRITE + "Enter Path:Cannot write to file\n"
     + MENU + WRITE + "Enter Path:Write done\n"
     + MENU + COUNT + "Total:1\n"
     + MENU),
]
