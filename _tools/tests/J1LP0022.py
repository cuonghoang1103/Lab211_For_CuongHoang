"""Keystroke runs for J1.L.P0022 (Candidate management).

REPLACE_REFERENCE = True (22/09/2026): the screen now follows the brief character
for character in three places the reference screen did not, so the reference
transcripts are replaced by the same runs with those three changes:
  1. the sentence under the menu starts with a space, like the brief:
     " (Please choose 1 to Create Experience Candidate, ...)."
  2. search: an empty line between the list of names and
     "Input Candidate name (First name or Last name): " (the brief's screen)
  3. search: an empty line before "The candidates found:" (the brief's screen)
Everything else of the three reference runs is unchanged (checked line by line
against the reference transcripts before they were copied here).

The two extra runs add the paths the reference runs miss: a letter as the
search type, a letter as the semester, the birth-year bounds 1899 / next year /
this year, lower-case y/n and ranks, and a search that matches on the LAST name.
Expected screens are written from the brief; the birth-year bound uses the real
current year, exactly like the program (java.time.Year.now()).
"""
import datetime

REPLACE_REFERENCE = True

YEAR = datetime.date.today().year


def keys(*lines):
    return "\n".join(lines) + "\n"


MENU = ("CANDIDATE MANAGEMENT SYSTEM\n1. Experience\n2. Fresher\n3. Internship\n"
        "4. Searching\n5. Exit\n (Please choose 1 to Create Experience Candidate, "
        "2 to Create Fresher Candidate, 3 to Internship Candidate, 4 to Searching "
        "and 5 to Exit program).\nEnter your choice: ")
COMMON = ("Input candidate id: Input first name: Input last name: Input birth date: "
          "Input address: Input phone: Input email: ")
BIRTH = "Birth date must be a number of 4 digits from 1900 to the current year.\n"
BANNERS = ("===========EXPERIENCE CANDIDATE============",
           "==========FRESHER CANDIDATE==============",
           "===========INTERN CANDIDATE==============")

RUNS = [
    # reference run 0 (the verified keystrokes of the reference solution)
    (keys('1', 'E01', 'Aelbrecht', 'Stefan', '1988', 'Brussels', '0987654321', 'stefan@asante.com', '5', 'Java', 'Y', 'E02', 'Aguirre', 'Eva', '1990', 'Sao paulo', '0940394123', 'eva@asante.com', '3', 'C Sharp', 'Y', 'E03', 'Antosova', 'Adeleva', '1989', 'Rio de janero', '0984933112', 'adelave@janeo.com', '7', 'Testing', 'N', '2', 'F01', 'Barbosa', 'De Souza', '1999', 'Ha Noi', '0912345678', 'barbosa@fpt.edu.vn', '06/2021', 'Excellence', 'FPT University', 'Y', 'F02', 'Cabrera', 'Cornide', '2000', 'Da Nang', '0912345679', 'cabrera@fpt.edu.vn', '09/2022', 'Good', 'Da Nang University', 'N', '3', 'I01', 'Maria', 'Madeleine', '2002', 'Hue', '0912345680', 'maria@fpt.edu.vn', 'Software Engineering', '5', 'FPT University', 'Y', 'I02', 'Joana', 'Filipa', '2003', 'Can Tho', '0912345681', 'joana@fpt.edu.vn', 'Information Systems', '3', 'Hue University', 'N', '4', 'eva', '0', '5'),
     r'''CANDIDATE MANAGEMENT SYSTEM
1. Experience
2. Fresher
3. Internship
4. Searching
5. Exit
 (Please choose 1 to Create Experience Candidate, 2 to Create Fresher Candidate, 3 to Internship Candidate, 4 to Searching and 5 to Exit program).
Enter your choice: ---------- Create Experience Candidate ----------
Input candidate id: Input first name: Input last name: Input birth date: Input address: Input phone: Input email: Input year of experience: Input professional skill: Experience candidate [E01] has been created.
Do you want to continue (Y/N)? Input candidate id: Input first name: Input last name: Input birth date: Input address: Input phone: Input email: Input year of experience: Input professional skill: Experience candidate [E02] has been created.
Do you want to continue (Y/N)? Input candidate id: Input first name: Input last name: Input birth date: Input address: Input phone: Input email: Input year of experience: Input professional skill: Experience candidate [E03] has been created.
Do you want to continue (Y/N)? List of candidate:
===========EXPERIENCE CANDIDATE============
Aelbrecht Stefan | 1988 | Brussels | 0987654321 | stefan@asante.com | 0 | 5 | Java
Aguirre Eva | 1990 | Sao paulo | 0940394123 | eva@asante.com | 0 | 3 | C Sharp
Antosova Adeleva | 1989 | Rio de janero | 0984933112 | adelave@janeo.com | 0 | 7 | Testing
==========FRESHER CANDIDATE==============
===========INTERN CANDIDATE==============
CANDIDATE MANAGEMENT SYSTEM
1. Experience
2. Fresher
3. Internship
4. Searching
5. Exit
 (Please choose 1 to Create Experience Candidate, 2 to Create Fresher Candidate, 3 to Internship Candidate, 4 to Searching and 5 to Exit program).
Enter your choice: ---------- Create Fresher Candidate ----------
Input candidate id: Input first name: Input last name: Input birth date: Input address: Input phone: Input email: Input graduation date: Input rank of graduation (Excellence, Good, Fair, Poor): Input education: Fresher candidate [F01] has been created.
Do you want to continue (Y/N)? Input candidate id: Input first name: Input last name: Input birth date: Input address: Input phone: Input email: Input graduation date: Input rank of graduation (Excellence, Good, Fair, Poor): Input education: Fresher candidate [F02] has been created.
Do you want to continue (Y/N)? List of candidate:
===========EXPERIENCE CANDIDATE============
Aelbrecht Stefan | 1988 | Brussels | 0987654321 | stefan@asante.com | 0 | 5 | Java
Aguirre Eva | 1990 | Sao paulo | 0940394123 | eva@asante.com | 0 | 3 | C Sharp
Antosova Adeleva | 1989 | Rio de janero | 0984933112 | adelave@janeo.com | 0 | 7 | Testing
==========FRESHER CANDIDATE==============
Barbosa De Souza | 1999 | Ha Noi | 0912345678 | barbosa@fpt.edu.vn | 1 | 06/2021 | Excellence | FPT University
Cabrera Cornide | 2000 | Da Nang | 0912345679 | cabrera@fpt.edu.vn | 1 | 09/2022 | Good | Da Nang University
===========INTERN CANDIDATE==============
CANDIDATE MANAGEMENT SYSTEM
1. Experience
2. Fresher
3. Internship
4. Searching
5. Exit
 (Please choose 1 to Create Experience Candidate, 2 to Create Fresher Candidate, 3 to Internship Candidate, 4 to Searching and 5 to Exit program).
Enter your choice: ---------- Create Intern Candidate ----------
Input candidate id: Input first name: Input last name: Input birth date: Input address: Input phone: Input email: Input majors: Input semester: Input university name: Intern candidate [I01] has been created.
Do you want to continue (Y/N)? Input candidate id: Input first name: Input last name: Input birth date: Input address: Input phone: Input email: Input majors: Input semester: Input university name: Intern candidate [I02] has been created.
Do you want to continue (Y/N)? List of candidate:
===========EXPERIENCE CANDIDATE============
Aelbrecht Stefan | 1988 | Brussels | 0987654321 | stefan@asante.com | 0 | 5 | Java
Aguirre Eva | 1990 | Sao paulo | 0940394123 | eva@asante.com | 0 | 3 | C Sharp
Antosova Adeleva | 1989 | Rio de janero | 0984933112 | adelave@janeo.com | 0 | 7 | Testing
==========FRESHER CANDIDATE==============
Barbosa De Souza | 1999 | Ha Noi | 0912345678 | barbosa@fpt.edu.vn | 1 | 06/2021 | Excellence | FPT University
Cabrera Cornide | 2000 | Da Nang | 0912345679 | cabrera@fpt.edu.vn | 1 | 09/2022 | Good | Da Nang University
===========INTERN CANDIDATE==============
Maria Madeleine | 2002 | Hue | 0912345680 | maria@fpt.edu.vn | 2 | Software Engineering | 5 | FPT University
Joana Filipa | 2003 | Can Tho | 0912345681 | joana@fpt.edu.vn | 2 | Information Systems | 3 | Hue University
CANDIDATE MANAGEMENT SYSTEM
1. Experience
2. Fresher
3. Internship
4. Searching
5. Exit
 (Please choose 1 to Create Experience Candidate, 2 to Create Fresher Candidate, 3 to Internship Candidate, 4 to Searching and 5 to Exit program).
Enter your choice: List of candidate:
===========EXPERIENCE CANDIDATE============
Aelbrecht Stefan
Aguirre Eva
Antosova Adeleva
==========FRESHER CANDIDATE==============
Barbosa De Souza
Cabrera Cornide
===========INTERN CANDIDATE==============
Maria Madeleine
Joana Filipa

Input Candidate name (First name or Last name): Input type of candidate: 
The candidates found:
Aguirre Eva | 1990 | Sao paulo | 0940394123 | eva@asante.com | 0
Antosova Adeleva | 1989 | Rio de janero | 0984933112 | adelave@janeo.com | 0
CANDIDATE MANAGEMENT SYSTEM
1. Experience
2. Fresher
3. Internship
4. Searching
5. Exit
 (Please choose 1 to Create Experience Candidate, 2 to Create Fresher Candidate, 3 to Internship Candidate, 4 to Searching and 5 to Exit program).
Enter your choice: Goodbye.'''),
    # reference run 1 (the verified keystrokes of the reference solution)
    (keys('abc', '9', '4', '1', 'E01', 'Aelbrecht', 'Stefan', '19', '2999', 'nineteen', '1988', 'Brussels', '0912', '09-1234-5678', '0987654321', 'stefan', 'stefan@asante', 'stefan@asante.com', '-1', '101', 'five', '5', 'Java', 'maybe', 'Y', 'E01', 'Aelbrecht', 'Stefan', '1988', 'Brussels', '0987654321', 'stefan@asante.com', '5', 'Java', 'Y', '', 'No', 'Id', '1990', 'Hue', '0912345678', 'noid@fpt.edu.vn', '1', 'Java', 'Y', 'E09', '', 'NoFirstName', '1990', 'Hue', '0912345678', 'nf@fpt.edu.vn', '1', 'Java', 'Y', 'E09', 'NoLastName', '', '1990', 'Hue', '0912345678', 'nl@fpt.edu.vn', '1', 'Java', 'N', '2', 'F01', 'Barbosa', 'De Souza', '1999', 'Ha Noi', '0912345678', 'barbosa@fpt.edu.vn', '06/2021', 'Average', 'excellence', 'FPT University', 'N', '4', '', '0', '4', 'Zorro', '0', '4', 'stefan', '1', '4', 'stefan', '7', '0', '5'),
     r'''CANDIDATE MANAGEMENT SYSTEM
1. Experience
2. Fresher
3. Internship
4. Searching
5. Exit
 (Please choose 1 to Create Experience Candidate, 2 to Create Fresher Candidate, 3 to Internship Candidate, 4 to Searching and 5 to Exit program).
Enter your choice: You must input a number.
Enter your choice: Please choose from 1 to 5.
Enter your choice: The candidate list is empty.
CANDIDATE MANAGEMENT SYSTEM
1. Experience
2. Fresher
3. Internship
4. Searching
5. Exit
 (Please choose 1 to Create Experience Candidate, 2 to Create Fresher Candidate, 3 to Internship Candidate, 4 to Searching and 5 to Exit program).
Enter your choice: ---------- Create Experience Candidate ----------
Input candidate id: Input first name: Input last name: Input birth date: Birth date must be a number of 4 digits from 1900 to the current year.
Input birth date: Birth date must be a number of 4 digits from 1900 to the current year.
Input birth date: Birth date must be a number of 4 digits from 1900 to the current year.
Input birth date: Input address: Input phone: Phone must be a number with at least 10 digits.
Input phone: Phone must be a number with at least 10 digits.
Input phone: Input email: Email must have the format <account name>@<domain> (eg: annguyen@fpt.edu.vn).
Input email: Email must have the format <account name>@<domain> (eg: annguyen@fpt.edu.vn).
Input email: Input year of experience: Year of experience must be a number from 0 to 100.
Input year of experience: Year of experience must be a number from 0 to 100.
Input year of experience: Year of experience must be a number from 0 to 100.
Input year of experience: Input professional skill: Experience candidate [E01] has been created.
Do you want to continue (Y/N)? Please enter Y or N.
Do you want to continue (Y/N)? Input candidate id: Input first name: Input last name: Input birth date: Input address: Input phone: Input email: Input year of experience: Input professional skill: Candidate id [E01] already exists.
Do you want to continue (Y/N)? Input candidate id: Input first name: Input last name: Input birth date: Input address: Input phone: Input email: Input year of experience: Input professional skill: Candidate id cannot be empty.
Do you want to continue (Y/N)? Input candidate id: Input first name: Input last name: Input birth date: Input address: Input phone: Input email: Input year of experience: Input professional skill: First name cannot be empty.
Do you want to continue (Y/N)? Input candidate id: Input first name: Input last name: Input birth date: Input address: Input phone: Input email: Input year of experience: Input professional skill: Last name cannot be empty.
Do you want to continue (Y/N)? List of candidate:
===========EXPERIENCE CANDIDATE============
Aelbrecht Stefan | 1988 | Brussels | 0987654321 | stefan@asante.com | 0 | 5 | Java
==========FRESHER CANDIDATE==============
===========INTERN CANDIDATE==============
CANDIDATE MANAGEMENT SYSTEM
1. Experience
2. Fresher
3. Internship
4. Searching
5. Exit
 (Please choose 1 to Create Experience Candidate, 2 to Create Fresher Candidate, 3 to Internship Candidate, 4 to Searching and 5 to Exit program).
Enter your choice: ---------- Create Fresher Candidate ----------
Input candidate id: Input first name: Input last name: Input birth date: Input address: Input phone: Input email: Input graduation date: Input rank of graduation (Excellence, Good, Fair, Poor): Rank of graduation must be one of: Excellence, Good, Fair, Poor.
Input rank of graduation (Excellence, Good, Fair, Poor): Input education: Fresher candidate [F01] has been created.
Do you want to continue (Y/N)? List of candidate:
===========EXPERIENCE CANDIDATE============
Aelbrecht Stefan | 1988 | Brussels | 0987654321 | stefan@asante.com | 0 | 5 | Java
==========FRESHER CANDIDATE==============
Barbosa De Souza | 1999 | Ha Noi | 0912345678 | barbosa@fpt.edu.vn | 1 | 06/2021 | Excellence | FPT University
===========INTERN CANDIDATE==============
CANDIDATE MANAGEMENT SYSTEM
1. Experience
2. Fresher
3. Internship
4. Searching
5. Exit
 (Please choose 1 to Create Experience Candidate, 2 to Create Fresher Candidate, 3 to Internship Candidate, 4 to Searching and 5 to Exit program).
Enter your choice: List of candidate:
===========EXPERIENCE CANDIDATE============
Aelbrecht Stefan
==========FRESHER CANDIDATE==============
Barbosa De Souza
===========INTERN CANDIDATE==============

Input Candidate name (First name or Last name): Input type of candidate: Candidate name cannot be empty.
CANDIDATE MANAGEMENT SYSTEM
1. Experience
2. Fresher
3. Internship
4. Searching
5. Exit
 (Please choose 1 to Create Experience Candidate, 2 to Create Fresher Candidate, 3 to Internship Candidate, 4 to Searching and 5 to Exit program).
Enter your choice: List of candidate:
===========EXPERIENCE CANDIDATE============
Aelbrecht Stefan
==========FRESHER CANDIDATE==============
Barbosa De Souza
===========INTERN CANDIDATE==============

Input Candidate name (First name or Last name): Input type of candidate: No candidate found.
CANDIDATE MANAGEMENT SYSTEM
1. Experience
2. Fresher
3. Internship
4. Searching
5. Exit
 (Please choose 1 to Create Experience Candidate, 2 to Create Fresher Candidate, 3 to Internship Candidate, 4 to Searching and 5 to Exit program).
Enter your choice: List of candidate:
===========EXPERIENCE CANDIDATE============
Aelbrecht Stefan
==========FRESHER CANDIDATE==============
Barbosa De Souza
===========INTERN CANDIDATE==============

Input Candidate name (First name or Last name): Input type of candidate: No candidate found.
CANDIDATE MANAGEMENT SYSTEM
1. Experience
2. Fresher
3. Internship
4. Searching
5. Exit
 (Please choose 1 to Create Experience Candidate, 2 to Create Fresher Candidate, 3 to Internship Candidate, 4 to Searching and 5 to Exit program).
Enter your choice: List of candidate:
===========EXPERIENCE CANDIDATE============
Aelbrecht Stefan
==========FRESHER CANDIDATE==============
Barbosa De Souza
===========INTERN CANDIDATE==============

Input Candidate name (First name or Last name): Input type of candidate: Please choose from 0 to 2.
Input type of candidate: 
The candidates found:
Aelbrecht Stefan | 1988 | Brussels | 0987654321 | stefan@asante.com | 0
CANDIDATE MANAGEMENT SYSTEM
1. Experience
2. Fresher
3. Internship
4. Searching
5. Exit
 (Please choose 1 to Create Experience Candidate, 2 to Create Fresher Candidate, 3 to Internship Candidate, 4 to Searching and 5 to Exit program).
Enter your choice: Goodbye.'''),
    # reference run 2 (the verified keystrokes of the reference solution)
    (keys('1', 'E02', 'Aguirre', 'Eva', '1990', 'Sao paulo', '0940394123', 'eva@asante.com', '3', 'C Sharp', 'Y', 'E03', 'Antosova', 'Adeleva', '1989', 'Rio de janero', '0984933112', 'adelave@janeo.com', '7', 'Testing', 'N', '3', 'I01', 'Maria', 'Madeleine', '2002', 'Hue', '0912345680', 'maria@fpt.edu.vn', 'Software Engineering', '5', 'FPT University', 'N', '4', 'EVA', '0', '4', 'ari', '2', '4', 'ari', '0', '5'),
     r'''CANDIDATE MANAGEMENT SYSTEM
1. Experience
2. Fresher
3. Internship
4. Searching
5. Exit
 (Please choose 1 to Create Experience Candidate, 2 to Create Fresher Candidate, 3 to Internship Candidate, 4 to Searching and 5 to Exit program).
Enter your choice: ---------- Create Experience Candidate ----------
Input candidate id: Input first name: Input last name: Input birth date: Input address: Input phone: Input email: Input year of experience: Input professional skill: Experience candidate [E02] has been created.
Do you want to continue (Y/N)? Input candidate id: Input first name: Input last name: Input birth date: Input address: Input phone: Input email: Input year of experience: Input professional skill: Experience candidate [E03] has been created.
Do you want to continue (Y/N)? List of candidate:
===========EXPERIENCE CANDIDATE============
Aguirre Eva | 1990 | Sao paulo | 0940394123 | eva@asante.com | 0 | 3 | C Sharp
Antosova Adeleva | 1989 | Rio de janero | 0984933112 | adelave@janeo.com | 0 | 7 | Testing
==========FRESHER CANDIDATE==============
===========INTERN CANDIDATE==============
CANDIDATE MANAGEMENT SYSTEM
1. Experience
2. Fresher
3. Internship
4. Searching
5. Exit
 (Please choose 1 to Create Experience Candidate, 2 to Create Fresher Candidate, 3 to Internship Candidate, 4 to Searching and 5 to Exit program).
Enter your choice: ---------- Create Intern Candidate ----------
Input candidate id: Input first name: Input last name: Input birth date: Input address: Input phone: Input email: Input majors: Input semester: Input university name: Intern candidate [I01] has been created.
Do you want to continue (Y/N)? List of candidate:
===========EXPERIENCE CANDIDATE============
Aguirre Eva | 1990 | Sao paulo | 0940394123 | eva@asante.com | 0 | 3 | C Sharp
Antosova Adeleva | 1989 | Rio de janero | 0984933112 | adelave@janeo.com | 0 | 7 | Testing
==========FRESHER CANDIDATE==============
===========INTERN CANDIDATE==============
Maria Madeleine | 2002 | Hue | 0912345680 | maria@fpt.edu.vn | 2 | Software Engineering | 5 | FPT University
CANDIDATE MANAGEMENT SYSTEM
1. Experience
2. Fresher
3. Internship
4. Searching
5. Exit
 (Please choose 1 to Create Experience Candidate, 2 to Create Fresher Candidate, 3 to Internship Candidate, 4 to Searching and 5 to Exit program).
Enter your choice: List of candidate:
===========EXPERIENCE CANDIDATE============
Aguirre Eva
Antosova Adeleva
==========FRESHER CANDIDATE==============
===========INTERN CANDIDATE==============
Maria Madeleine

Input Candidate name (First name or Last name): Input type of candidate: 
The candidates found:
Aguirre Eva | 1990 | Sao paulo | 0940394123 | eva@asante.com | 0
Antosova Adeleva | 1989 | Rio de janero | 0984933112 | adelave@janeo.com | 0
CANDIDATE MANAGEMENT SYSTEM
1. Experience
2. Fresher
3. Internship
4. Searching
5. Exit
 (Please choose 1 to Create Experience Candidate, 2 to Create Fresher Candidate, 3 to Internship Candidate, 4 to Searching and 5 to Exit program).
Enter your choice: List of candidate:
===========EXPERIENCE CANDIDATE============
Aguirre Eva
Antosova Adeleva
==========FRESHER CANDIDATE==============
===========INTERN CANDIDATE==============
Maria Madeleine

Input Candidate name (First name or Last name): Input type of candidate: 
The candidates found:
Maria Madeleine | 2002 | Hue | 0912345680 | maria@fpt.edu.vn | 2
CANDIDATE MANAGEMENT SYSTEM
1. Experience
2. Fresher
3. Internship
4. Searching
5. Exit
 (Please choose 1 to Create Experience Candidate, 2 to Create Fresher Candidate, 3 to Internship Candidate, 4 to Searching and 5 to Exit program).
Enter your choice: List of candidate:
===========EXPERIENCE CANDIDATE============
Aguirre Eva
Antosova Adeleva
==========FRESHER CANDIDATE==============
===========INTERN CANDIDATE==============
Maria Madeleine

Input Candidate name (First name or Last name): Input type of candidate: No candidate found.
CANDIDATE MANAGEMENT SYSTEM
1. Experience
2. Fresher
3. Internship
4. Searching
5. Exit
 (Please choose 1 to Create Experience Candidate, 2 to Create Fresher Candidate, 3 to Internship Candidate, 4 to Searching and 5 to Exit program).
Enter your choice: Goodbye.'''),
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
     "\nInput Candidate name (First name or Last name): Input type of candidate: "
     "You must input a number.\nInput type of candidate: \nThe candidates found:\n"
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
