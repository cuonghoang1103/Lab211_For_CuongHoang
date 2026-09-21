"""Keystroke runs for J1.L.P0021 (student management).

REPLACE_REFERENCE (21/09/2026, the lecturer's paper checklist 1.1: "rendering
khi goi view chi duoc goi 1 lan cho 1 luong xu ly (moi luong = 1 switch-case)"
and Guide.xlsx: "Moi workflow chinh (Create student, ...) chi goi vao
controller 1 lan duy nhat"). The reference screen cannot be kept on two
points, so its 3 runs are re-written below with the same keystrokes:

  * Create: "Student [id] has been added." used to follow each student (one
    render per student). Now main keeps the checked students and the
    controller stores them in ONE call at the end, so the "added" lines come
    together after the answer N. Every refusal (blank id, duplicate id, ...)
    and the "At least 10 ... so far." line stay where they were. The count is
    the WHOLE list (students already stored + students kept in this Create),
    as in the reference: main reads the stored count with one read-only call
    at the start of the flow, so once the list holds 10 students the question
    (Y/N) follows every new student.
  * Update/Delete: the table of the student found by id (a render BEFORE the
    question U/D) is gone - the id is checked by a check-only call that prints
    nothing - and the prompts say "(blank to keep the old one)" instead of
    repeating the old value (main no longer receives it).

Everything else - menu, prompts, messages, sort order, report - is the
reference wording, character for character. Expected screens are written
from the brief + reference wording, not captured.
"""

REPLACE_REFERENCE = True

MENU = ("WELCOME TO STUDENT MANAGEMENT\n1. Create\n2. Find and Sort\n"
        "3. Update/Delete\n4. Report\n5. Exit\n"
        "(Please choose 1 to Create, 2 to Find and Sort, 3 to Update/Delete, "
        "4 to Report and 5 to Exit program).\nEnter your choice: ")
FORM = ("Enter id: Enter student name: Enter semester: "
        "Enter course name (Java, .Net, C/C++): ")
CREATE = "---------- Create Student ----------\n"
FIND = ("---------- Find and Sort ----------\n"
        "Enter student name (or a part of it): ")
UPDEL = ("---------- Update/Delete ----------\n"
         "Enter student id: ")
REPORT = "-------------- Report --------------\n"
ASK_UD = "Do you want to update (U) or delete (D) student? "
NEW_VALUES = ("Enter new student name (blank to keep the old one): "
              "Enter new semester (blank to keep the old one): "
              "Enter new course name (blank to keep the old one): ")
CONTINUE = "Do you want to continue (Y/N)? "
HEAD = "Student name         Semester   Course\n"


def need(count):
    return "At least 10 students are required - %d so far.\n" % count


def added(ids):
    return "".join("Student [%s] has been added.\n" % sid for sid in ids)


def keys_of(students):
    return "".join("%s\n%s\n%s\n%s\n" % s for s in students)


# ---- reference run 0: ten students, report, find "nguyen van" -------------
REF0_STUDENTS = [("S001", "Nguyen Van A", "1", "Java"),
                 ("S002", "Nguyen Van A", "2", "Java"),
                 ("S003", "Nguyen Van B", "1", ".Net"),
                 ("S004", "Nguyen Van B", "3", "Java"),
                 ("S005", "Tran Thi C", "1", "C/C++"),
                 ("S006", "Le Van D", "2", "Java"),
                 ("S007", "Le Van D", "3", "Java"),
                 ("S008", "Le Van D", "1", ".Net"),
                 ("S009", "Pham Thi E", "2", "C/C++"),
                 ("S010", "Nguyen Van A", "3", ".Net")]
REF0_IDS = [s[0] for s in REF0_STUDENTS]


def first_ten(count_from=0):
    """FORM + 'so far' for students 1..9, FORM for the tenth."""
    out = ""
    for k in range(1, 10):
        out += FORM + need(count_from + k)
    return out + FORM


REF0 = ("1\n" + keys_of(REF0_STUDENTS) + "N\n4\n2\nnguyen van\n5\n",
        MENU + CREATE + first_ten() + CONTINUE + added(REF0_IDS)
        + MENU + REPORT
        + "Le Van D | .Net | 1\nLe Van D | Java | 2\nNguyen Van A | .Net | 1\n"
        "Nguyen Van A | Java | 2\nNguyen Van B | .Net | 1\nNguyen Van B | Java | 1\n"
        "Pham Thi E | C/C++ | 1\nTran Thi C | C/C++ | 1\n"
        + MENU + FIND + HEAD
        + "Nguyen Van A         1          Java\n"
        "Nguyen Van A         2          Java\n"
        "Nguyen Van A         3          .Net\n"
        "Nguyen Van B         1          .Net\n"
        "Nguyen Van B         3          Java\n"
        + MENU + "Goodbye.")

# ---- reference run 1: empty list, every refusal of Create, delete ---------
REF1_KEYS = ("abc\n9\n2\nany\n3\nS001\n4\n"
             "1\n\nNo Id\n1\nJava\nS001\n\n1\nJava\nS001\nBad Semester\n0\nJava\n"
             "S001\nBad Course\n1\nPascal\nS001\nTyped Semester\nabc\n1\nJava\n"
             "S001\nDuplicate Id\n1\nJava\n"
             + keys_of(REF0_STUDENTS[1:]) + "N\n"
             "2\nZorro\n2\n\n3\nS999\n3\nS002\nD\n2\nnguyen van a\n4\n5\n")
REF1_CREATE = (CREATE
               + FORM + "ID cannot be empty.\n" + need(0)
               + FORM + "Student name cannot be empty.\n" + need(0)
               + FORM + "Semester must be greater than 0.\n" + need(0)
               + FORM + "Course must be one of: Java, .Net, C/C++.\n" + need(0)
               + "Enter id: Enter student name: Enter semester: You must input a number.\n"
               "Enter semester: Enter course name (Java, .Net, C/C++): " + need(1)
               + FORM + "ID [S001] already exists.\n" + need(1))
for k in range(2, 10):
    REF1_CREATE += FORM + need(k)
REF1_CREATE += FORM + CONTINUE + added(REF0_IDS)
REF1 = (REF1_KEYS,
        MENU + "You must input a number.\nEnter your choice: "
        "Please choose from 1 to 5.\nEnter your choice: "
        + FIND + "The student list is empty.\n"
        + MENU + UPDEL + "The student list is empty.\n"
        + MENU + REPORT + "The student list is empty.\n"
        + MENU + REF1_CREATE
        + MENU + FIND + "No student found.\n"
        + MENU + FIND + "Search keyword cannot be empty.\n"
        + MENU + UPDEL + "ID [S999] does not exist.\n"
        + MENU + UPDEL + ASK_UD + "Student [S002] has been deleted.\n"
        + MENU + FIND + HEAD + "Nguyen Van A         3          .Net\n"
        + MENU + REPORT
        + "Le Van D | .Net | 1\nLe Van D | Java | 2\nNguyen Van A | .Net | 1\n"
        "Nguyen Van B | .Net | 1\nNguyen Van B | Java | 1\nPham Thi E | C/C++ | 1\n"
        "Tran Thi C | C/C++ | 1\nTyped Semester | Java | 1\n"
        + MENU + "Goodbye.")

# ---- reference run 2: Y then an eleventh student, sort, update ------------
REF2_STUDENTS = [("S001", "Vu Van Z", "1", "Java"),
                 ("S002", "anh nguyen", "2", "Java"),
                 ("S003", "Tran Van M", "3", ".Net"),
                 ("S004", "Bui Thi B", "1", "C/C++"),
                 ("S005", "ANH NGUYEN", "2", "Java"),
                 ("S006", "Do Van K", "3", "Java"),
                 ("S007", "Cao Thi H", "1", "Java"),
                 ("S008", "Ly Van P", "2", "C/C++"),
                 ("S009", "Ha Thi Q", "3", "Java"),
                 ("S010", "Bui Thi B", "2", "Java")]
REF2 = ("1\n" + keys_of(REF2_STUDENTS) + "Y\nS011\nBui Thi B\n3\n.Net\nx\nN\n"
        "2\nB\n2\nan\n3\nS001\nU\nChanged Name\n\nWrong\n3\nS001\nU\n\n\nC/C++\n"
        "2\nvu van z\n4\n5\n",
        MENU + CREATE + first_ten() + CONTINUE + FORM + CONTINUE
        + "Please enter Y or N.\n" + CONTINUE
        + added([s[0] for s in REF2_STUDENTS] + ["S011"])
        + MENU + FIND + HEAD
        + "Bui Thi B            1          C/C++\n"
        "Bui Thi B            2          Java\n"
        "Bui Thi B            3          .Net\n"
        + MENU + FIND + HEAD
        + "anh nguyen           2          Java\n"
        "ANH NGUYEN           2          Java\n"
        "Do Van K             3          Java\n"
        "Ly Van P             2          C/C++\n"
        "Tran Van M           3          .Net\n"
        "Vu Van Z             1          Java\n"
        + MENU + UPDEL + ASK_UD + NEW_VALUES
        + "Course must be one of: Java, .Net, C/C++.\n"
        + MENU + UPDEL + ASK_UD + NEW_VALUES + "Student [S001] has been updated.\n"
        + MENU + FIND + HEAD + "Vu Van Z             1          C/C++\n"
        + MENU + REPORT
        + "anh nguyen | Java | 2\nBui Thi B | .Net | 1\nBui Thi B | C/C++ | 1\n"
        "Bui Thi B | Java | 1\nCao Thi H | Java | 1\nDo Van K | Java | 1\n"
        "Ha Thi Q | Java | 1\nLy Van P | C/C++ | 1\nTran Van M | .Net | 1\n"
        "Vu Van Z | C/C++ | 1\n"
        + MENU + "Goodbye.")

# ---- extra run: what the reference misses ---------------------------------
# a lower-case course name, an id typed in another case (duplicate / found),
# a blank id on Update/Delete, a wrong U/D answer, update-semester letters / 0
# (checked before anything is written), a successful update typed with a
# lower-case id (the answer shows the stored id), a delete, the report, then a
# SECOND and a THIRD Create: the students already stored count (the brief's
# "number of students" is the whole list) - a stored id is refused and "9 so
# far" is shown, the tenth student brings the question (Y/N), and once the list
# holds 10 or more the question follows every new student, even a refused one.
STUDENTS = [("S01", "X", "1", "Java"),
            ("S02", "Le Thi Binh", "2", ".NET"),
            ("S03", "Tran Van An", "3", "Java"),
            ("S04", "Pham Cuong", "1", "c/c++"),
            ("S05", "Do Dung", "2", "Java"),
            ("S06", "Hoang Em", "3", ".Net"),
            ("S07", "Vu Giang", "1", "Java"),
            ("S08", "Ngo Hoa", "2", "C/C++"),
            ("S09", "Bui Khanh", "3", "Java"),
            ("S10", "Dang Lan", "1", ".Net")]

EXTRA_KEYS = ("1\n" + keys_of(STUDENTS[:1]) + "s01\nY\n1\nJava\n"
              + keys_of(STUDENTS[1:]) + "N\n"
              "3\n\n"
              "3\ns03\nx\nU\n\nabc\n0\n\n"
              "3\ns03\nu\nTran Van Anh\n5\n.net\n"
              "3\nS01\nd\n"
              "2\ntran\n"
              "4\n"
              "1\nS02\nDup\n1\nJava\nT01\nSecond One\n1\njava\nY\n"
              "T02\nSecond Two\n2\n.net\nx\nN\n"
              "1\nt01\nDup\n1\nJava\nY\nT03\nThird\n3\nC/C++\nN\n"
              "4\n"
              "5\n")
EXTRA_CREATE = (CREATE + FORM + need(1) + FORM + "ID [s01] already exists.\n" + need(1))
for k in range(2, 10):
    EXTRA_CREATE += FORM + need(k)
EXTRA_CREATE += FORM + CONTINUE + added([s[0] for s in STUDENTS])
EXTRA = (EXTRA_KEYS,
         MENU + EXTRA_CREATE
         + MENU + UPDEL + "ID cannot be empty.\n"
         + MENU + UPDEL + ASK_UD + "Please enter U or D.\n" + ASK_UD
         + "Enter new student name (blank to keep the old one): "
         "Enter new semester (blank to keep the old one): You must input a number.\n"
         "Enter new semester (blank to keep the old one): "
         "Enter new course name (blank to keep the old one): "
         "Semester must be greater than 0.\n"
         + MENU + UPDEL + ASK_UD + NEW_VALUES + "Student [S03] has been updated.\n"
         + MENU + UPDEL + ASK_UD + "Student [S01] has been deleted.\n"
         + MENU + FIND + HEAD + "Tran Van Anh         5          .Net\n"
         + MENU + REPORT
         + "Bui Khanh | Java | 1\nDang Lan | .Net | 1\nDo Dung | Java | 1\n"
         "Hoang Em | .Net | 1\nLe Thi Binh | .Net | 1\nNgo Hoa | C/C++ | 1\n"
         "Pham Cuong | C/C++ | 1\nTran Van Anh | .Net | 1\nVu Giang | Java | 1\n"
         # second Create: 9 stored, the tenth student brings the question
         + MENU + CREATE + FORM + "ID [S02] already exists.\n" + need(9)
         + FORM + CONTINUE + FORM + CONTINUE + "Please enter Y or N.\n" + CONTINUE
         + added(["T01", "T02"])
         # third Create: 11 stored, the question follows every student
         + MENU + CREATE + FORM + "ID [t01] already exists.\n" + CONTINUE
         + FORM + CONTINUE + added(["T03"])
         + MENU + REPORT
         + "Bui Khanh | Java | 1\nDang Lan | .Net | 1\nDo Dung | Java | 1\n"
         "Hoang Em | .Net | 1\nLe Thi Binh | .Net | 1\nNgo Hoa | C/C++ | 1\n"
         "Pham Cuong | C/C++ | 1\nSecond One | Java | 1\nSecond Two | .Net | 1\n"
         "Third | C/C++ | 1\nTran Van Anh | .Net | 1\nVu Giang | Java | 1\n"
         + MENU + "Goodbye.")

RUNS = [REF0, REF1, REF2, EXTRA]
