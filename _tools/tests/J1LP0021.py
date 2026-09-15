"""Extra keystroke runs for J1.L.P0021 (student management).

The reference runs (3) are kept: the brief is silent on prompts and error
wording, and the reference's main screen is the brief's own. These extra runs
reach what the reference misses: a lower-case course name, an id typed in
another case (duplicate / found), a blank id on Update/Delete, a wrong U/D
answer, update-semester letters / 0 (validated before anything is written),
a successful semester update, and the report afterwards.

Expected screens are written from the brief + reference wording, not captured.
"""

MENU = ("WELCOME TO STUDENT MANAGEMENT\n1. Create\n2. Find and Sort\n"
        "3. Update/Delete\n4. Report\n5. Exit\n"
        "(Please choose 1 to Create, 2 to Find and Sort, 3 to Update/Delete, "
        "4 to Report and 5 to Exit program).\nEnter your choice: ")
FORM = ("Enter id: Enter student name: Enter semester: "
        "Enter course name (Java, .Net, C/C++): ")

STUDENTS = [("S01", "Tran Van An", "1", "java"),
            ("S02", "Le Thi Binh", "2", ".NET"),
            ("S03", "Tran Van An", "3", "Java"),
            ("S04", "Pham Cuong", "1", "c/c++"),
            ("S05", "Do Dung", "2", "Java"),
            ("S06", "Hoang Em", "3", ".Net"),
            ("S07", "Vu Giang", "1", "Java"),
            ("S08", "Ngo Hoa", "2", "C/C++"),
            ("S09", "Bui Khanh", "3", "Java"),
            ("S10", "Dang Lan", "1", ".Net")]


def create_block():
    keys = "1\n"
    out = "---------- Create Student ----------\n"
    # S01 is added, then the same id typed in lower case is refused
    keys += "S01\nX\n1\nJava\n"
    out += FORM + "Student [S01] has been added.\n"
    out += "At least 10 students are required - 1 so far.\n"
    keys += "s01\nY\n1\nJava\n"
    out += FORM + "ID [s01] already exists.\n"
    out += "At least 10 students are required - 1 so far.\n"
    return keys, out


RUN_KEYS, RUN_OUT = create_block()
# the first student is S01 "X" (deleted later with D); nine more follow,
# course names typed in any case ("java", ".NET", "c/c++")
count = 1
for sid, name, sem, course in STUDENTS[1:]:
    RUN_KEYS += "%s\n%s\n%s\n%s\n" % (sid, name, sem, course)
    count += 1
    RUN_OUT += FORM + "Student [%s] has been added.\n" % sid
    if count < 10:
        RUN_OUT += "At least 10 students are required - %d so far.\n" % count
RUN_KEYS += "N\n"
RUN_OUT += "Do you want to continue (Y/N)? " + MENU

HEAD = ("ID       Student name         Semester   Course\n")

RUN_KEYS += (
    # Update/Delete: blank id
    "3\n\n"
    # id in lower case is found; wrong answer; U; name blank; semester
    # letters then 0; course blank -> semester error, nothing written
    "3\ns03\nx\nU\n\nabc\n0\n\n"
    # U again: new name, semester 5, course .net -> updated
    "3\nS03\nu\nTran Van Anh\n5\n.net\n"
    # D on the placeholder student S01 "X"
    "3\nS01\nd\n"
    # search "tran": only the updated student is left with that text
    "2\ntran\n"
    "4\n5\n")
RUN_OUT += (
    "---------- Update/Delete ----------\n"
    "Enter student id: ID cannot be empty.\n" + MENU +
    "---------- Update/Delete ----------\n"
    "Enter student id: " + HEAD +
    "S03      Tran Van An          3          Java\n"
    "Do you want to update (U) or delete (D) student? Please enter U or D.\n"
    "Do you want to update (U) or delete (D) student? "
    "Enter new student name (blank to keep Tran Van An): "
    "Enter new semester (blank to keep 3): You must input a number.\n"
    "Enter new semester (blank to keep 3): "
    "Enter new course name (blank to keep Java): "
    "Semester must be greater than 0.\n" + MENU +
    "---------- Update/Delete ----------\n"
    "Enter student id: " + HEAD +
    "S03      Tran Van An          3          Java\n"
    "Do you want to update (U) or delete (D) student? "
    "Enter new student name (blank to keep Tran Van An): "
    "Enter new semester (blank to keep 3): "
    "Enter new course name (blank to keep Java): "
    "Student [S03] has been updated.\n" + MENU +
    "---------- Update/Delete ----------\n"
    "Enter student id: " + HEAD +
    "S01      X                    1          Java\n"
    "Do you want to update (U) or delete (D) student? "
    "Student [S01] has been deleted.\n" + MENU +
    "---------- Find and Sort ----------\n"
    "Enter student name (or a part of it): "
    "Student name         Semester   Course\n"
    "Tran Van Anh         5          .Net\n" + MENU +
    "-------------- Report --------------\n"
    "Bui Khanh | Java | 1\n"
    "Dang Lan | .Net | 1\n"
    "Do Dung | Java | 1\n"
    "Hoang Em | .Net | 1\n"
    "Le Thi Binh | .Net | 1\n"
    "Ngo Hoa | C/C++ | 1\n"
    "Pham Cuong | C/C++ | 1\n"
    "Tran Van Anh | .Net | 1\n"
    "Vu Giang | Java | 1\n" + MENU + "Goodbye.")

RUNS = [
    (RUN_KEYS, MENU + RUN_OUT),
]
