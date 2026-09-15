"""Extra keystroke runs for J1.L.P0022 (Candidate management).

The three reference runs match the brief's screens (menu, banners, search
prompts, "The candidates found:" and the " | " result line), so they are kept
(REPLACE_REFERENCE stays False) and these runs add the paths they miss:
a letter as the search type, a letter as the semester, the birth-year bounds
1899 / next year / this year, lower-case y/n and ranks, and a search that
matches on the LAST name.

Expected screens are written from the brief; the birth-year bound uses the
real current year, exactly like the program (java.time.Year.now()).
"""
import datetime

YEAR = datetime.date.today().year

MENU = ("CANDIDATE MANAGEMENT SYSTEM\n1. Experience\n2. Fresher\n3. Internship\n"
        "4. Searching\n5. Exit\n(Please choose 1 to Create Experience Candidate, "
        "2 to Create Fresher Candidate, 3 to Internship Candidate, 4 to Searching "
        "and 5 to Exit program).\nEnter your choice: ")
COMMON = ("Input candidate id: Input first name: Input last name: Input birth date: "
          "Input address: Input phone: Input email: ")
BIRTH = "Birth date must be a number of 4 digits from 1900 to the current year.\n"
BANNERS = ("===========EXPERIENCE CANDIDATE============",
           "==========FRESHER CANDIDATE==============",
           "===========INTERN CANDIDATE==============")

RUNS = [
    # Intern: birth 1899, next year, then this year (legal); semester "abc";
    # lower-case "y" continues, lower-case "n" stops; a Fresher with rank
    # "good" is stored as "Good"; search matches the LAST name "Nguyen" typed
    # as "NGUY"; a letter as the type, then 2.
    ("3\nI01\nAn\nNguyen\n1899\n%d\n%d\nHue\n0912345680\nan@fpt.edu.vn\n"
     "SE\nabc\n5\nFPT University\ny\n"
     "I02\nBinh\nTran\n2003\nHue\n0912345681\nbinh@fpt.edu.vn\nIA\n3\nHue U\nn\n"
     "2\nF01\nCuong\nNguyen\n2000\nHa Noi\n0912345682\ncuong@fpt.edu.vn\n"
     "06/2022\ngood\nFPT University\nN\n"
     "4\nNGUY\nx\n2\n5\n" % (YEAR + 1, YEAR),
     MENU + "---------- Create Intern Candidate ----------\n"
     "Input candidate id: Input first name: Input last name: Input birth date: "
     + BIRTH + "Input birth date: " + BIRTH + "Input birth date: "
     "Input address: Input phone: Input email: "
     "Input majors: Input semester: You must input a number.\n"
     "Input semester: Input university name: Intern candidate [I01] has been created.\n"
     "Do you want to continue (Y/N)? " + COMMON
     + "Input majors: Input semester: Input university name: "
     "Intern candidate [I02] has been created.\n"
     "Do you want to continue (Y/N)? List of candidate:\n"
     + BANNERS[0] + "\n" + BANNERS[1] + "\n" + BANNERS[2] + "\n"
     "An Nguyen | %d | Hue | 0912345680 | an@fpt.edu.vn | 2 | SE | 5 | FPT University\n"
     "Binh Tran | 2003 | Hue | 0912345681 | binh@fpt.edu.vn | 2 | IA | 3 | Hue U\n" % YEAR
     + MENU + "---------- Create Fresher Candidate ----------\n" + COMMON
     + "Input graduation date: Input rank of graduation (Excellence, Good, Fair, Poor): "
     "Input education: Fresher candidate [F01] has been created.\n"
     "Do you want to continue (Y/N)? List of candidate:\n"
     + BANNERS[0] + "\n" + BANNERS[1] + "\n"
     "Cuong Nguyen | 2000 | Ha Noi | 0912345682 | cuong@fpt.edu.vn | 1 | 06/2022 | Good"
     " | FPT University\n" + BANNERS[2] + "\n"
     "An Nguyen | %d | Hue | 0912345680 | an@fpt.edu.vn | 2 | SE | 5 | FPT University\n"
     "Binh Tran | 2003 | Hue | 0912345681 | binh@fpt.edu.vn | 2 | IA | 3 | Hue U\n" % YEAR
     + MENU + "List of candidate:\n" + BANNERS[0] + "\n" + BANNERS[1] + "\n"
     "Cuong Nguyen\n" + BANNERS[2] + "\nAn Nguyen\nBinh Tran\n"
     "Input Candidate name (First name or Last name): Input type of candidate: "
     "You must input a number.\nInput type of candidate: The candidates found:\n"
     "An Nguyen | %d | Hue | 0912345680 | an@fpt.edu.vn | 2\n" % YEAR
     + MENU + "Goodbye."),
    # phone with letters and 9 digits, email without '@' and with an empty
    # domain label, experience exactly 0 and exactly 100 (both legal)
    ("1\nE01\nA\nB\n1990\nX\nabcdefghij\n091234567\n0912345678\na.fpt.edu.vn\na@fpt..vn\n"
     "a@fpt.vn\n0\nJava\nY\nE02\nC\nD\n1990\nX\n0912345679\nc@fpt.vn\n100\nC\nN\n5\n",
     MENU + "---------- Create Experience Candidate ----------\n"
     "Input candidate id: Input first name: Input last name: Input birth date: "
     "Input address: Input phone: Phone must be a number with at least 10 digits.\n"
     "Input phone: Phone must be a number with at least 10 digits.\n"
     "Input phone: Input email: Email must have the format <account name>@<domain> "
     "(eg: annguyen@fpt.edu.vn).\nInput email: Email must have the format "
     "<account name>@<domain> (eg: annguyen@fpt.edu.vn).\nInput email: "
     "Input year of experience: Input professional skill: "
     "Experience candidate [E01] has been created.\n"
     "Do you want to continue (Y/N)? " + COMMON
     + "Input year of experience: Input professional skill: "
     "Experience candidate [E02] has been created.\n"
     "Do you want to continue (Y/N)? List of candidate:\n" + BANNERS[0] + "\n"
     "A B | 1990 | X | 0912345678 | a@fpt.vn | 0 | 0 | Java\n"
     "C D | 1990 | X | 0912345679 | c@fpt.vn | 0 | 100 | C\n"
     + BANNERS[1] + "\n" + BANNERS[2] + "\n" + MENU + "Goodbye."),
]
