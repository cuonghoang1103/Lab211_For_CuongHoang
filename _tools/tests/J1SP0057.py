"""Keystroke runs for J1.S.P0057 (user management system).

REPLACE_REFERENCE: the reference transcript prints "Loaded N account(s) from
user.dat." before the menu - a line that is not on the brief's screen - and
its runs 1-2 depend on the file written by run 0, while verify.py gives every
run a fresh copy of the project root. So only these runs count.

Every run starts from the shipped user.dat at the project root, holding the
brief's own example account:
    NghiaNV1 space@123
Expected screens are written from the brief, not captured from the program.
"""

REPLACE_REFERENCE = True

MENU = """====== USER MANAGEMENT SYSTEM ======
1. Create a new account
2. Login system
3. Exit
> Choose: """

USER_ERR = "You must enter least at 5 character, and no space!\n"
PASS_ERR = "You must enter least at 6 character, and no space!\n"

RUNS = [
    # the brief's login screen, character for character: short user name,
    # then short password, then "Login successful!"
    ("2\n12\nNghiaNV1\n12\nspace@123\n3\n",
     MENU + "Enter Username: " + USER_ERR
     + "Enter Username: Enter Password: " + PASS_ERR
     + "Enter Password: Login successful!\n"
     + MENU + "Goodbye."),

    # create: user name with a space, 4 characters, then legal; password with
    # a space, then legal; the new account is appended and can log in; a wrong
    # password and an unknown user both give the brief's failure message; an
    # existing user name cannot be created again
    ("1\nab cde\nabcd\nHoangAn\nabc 123\nabc123\n"
     "2\nHoangAn\nabc123\n"
     "2\nHoangAn\nwrong12\n"
     "2\nNobody1\nabc123\n"
     "1\nNghiaNV1\nanother123\n"
     "3\n",
     MENU + "Enter Username: " + USER_ERR
     + "Enter Username: " + USER_ERR
     + "Enter Username: Enter Password: " + PASS_ERR
     + "Enter Password: Create account successfully!\n"
     + MENU + "Enter Username: Enter Password: Login successful!\n"
     + MENU + "Enter Username: Enter Password: Invalid user name or password\n"
     + MENU + "Enter Username: Enter Password: Invalid user name or password\n"
     + MENU + "Enter Username: Enter Password: Username [NghiaNV1] already exists.\n"
     + MENU + "Goodbye."),

    # menu validation: letters, then out of range; exactly 5 and 6 characters
    # are legal (boundaries), leading space in a password is refused
    ("x\n4\n0\n1\nabcde\n 12345\n123456\n2\nabcde\n123456\n3\n",
     MENU + "You must input a number.\n"
     "> Choose: Please choose from 1 to 3.\n"
     "> Choose: Please choose from 1 to 3.\n"
     "> Choose: Enter Username: Enter Password: " + PASS_ERR
     + "Enter Password: Create account successfully!\n"
     + MENU + "Enter Username: Enter Password: Login successful!\n"
     + MENU + "Goodbye."),
]
