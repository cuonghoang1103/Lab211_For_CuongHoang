"""Extra keystroke runs for J1.S.P0054 (contact management).

The three reference runs cover: the brief's add screen (a wrong phone shows
the seven formats), all seven phone formats, display, "ID is digit",
"No found contact", delete, one-word names and the ID rule after deleting
the last contact. These runs add the menu validation, the blank-field
messages, 0/negative IDs, a name split at the FIRST space only, deleting a
contact in the MIDDLE (the next ID still follows the last one), and phones
that look close to a format but are not one of the seven.
Expected screens are written from the brief, not captured from the program.
"""

MENU = ("========= Contact program =========\n"
        "1. Add a Contact\n"
        "2. Display all Contact\n"
        "3. Delete a Contact\n"
        "4. Exit\n"
        "Please choice one option: Your choice: ")
CHOICE = "Please choice one option from 1 to 4.\n"
ADD = "-------- Add a Contact --------\n"
DISPLAY = ("--------------------------------- Display all Contact "
           "----------------------------\n")
DELETE = "------- Delete a Contact -------\n"
FORMATS = ("Please input Phone flow\n"
           "• 1234567890\n"
           "• 123-456-7890\n"
           "• 123-456-7890 x1234\n"
           "• 123-456-7890 ext1234\n"
           "• (123)-456-7890\n"
           "• 123.456.7890\n"
           "• 123 456 7890\n")
HEADER = "ID  Name              First Name  Last Name   Group   Address     Phone\n"


def row(i, name, first, last, group, address, phone):
    return "%-4s%-18s%-12s%-12s%-8s%-12s%s\n" % (i, name, first, last, group,
                                                 address, phone)


RUNS = [
    # menu validation: letters, blank, 0, 5 - then exit
    ("x\n\n0\n5\n4\n",
     MENU + CHOICE + "Please choice one option: Your choice: " + CHOICE
     + "Please choice one option: Your choice: " + CHOICE
     + "Please choice one option: Your choice: " + CHOICE
     + "Please choice one option: Your choice: "),
    # blank name/group/address are asked again; name split at the FIRST
    # space; phones close to a format are refused (dot + extension, 9 digits,
    # mixed separators, "ext" without a space)
    ("1\n\nRonaldo de Assis\n\nStar\n\nBrazil\n"
     "123.456.7890 x1234\n123456789\n123-456.7890\n123-456-7890ext1234\n"
     "123-456-7890 ext1234\n2\n4\n",
     MENU + ADD + "Enter Name: Name must not be blank.\nEnter Name: "
     + "Enter Group: Group must not be blank.\nEnter Group: "
     + "Enter Address: Address must not be blank.\nEnter Address: "
     + "Enter Phone: " + FORMATS + "Enter Phone: " + FORMATS
     + "Enter Phone: " + FORMATS + "Enter Phone: " + FORMATS
     + "Enter Phone: Successful\n"
     + MENU + DISPLAY + HEADER
     + row(1, "Ronaldo de Assis", "Ronaldo", "de Assis", "Star", "Brazil",
           "123-456-7890 ext1234")
     + MENU),
    # delete: 0 and -3 are "ID is digit"; delete the MIDDLE contact (2);
    # deleting 2 again is "No found contact"; the next contact gets last ID (3) + 1 = 4
    ("1\nIker Casillas\nStar\nSpain\n1234567890\n"
     "1\nJohn Terry\nStar\nEngland\n1234567890\n"
     "1\nRaul Gonzalez\nStar\nSpain\n1234567890\n"
     "3\n0\n-3\n2\n"
     "3\n2\n"
     "1\nXavi Hernandez\nStar\nSpain\n123.456.7890\n"
     "2\n4\n",
     MENU + ADD + "Enter Name: Enter Group: Enter Address: Enter Phone: Successful\n"
     + MENU + ADD + "Enter Name: Enter Group: Enter Address: Enter Phone: Successful\n"
     + MENU + ADD + "Enter Name: Enter Group: Enter Address: Enter Phone: Successful\n"
     + MENU + DELETE + "Enter ID: ID is digit\nEnter ID: ID is digit\n"
     + "Enter ID: Successful\n"
     + MENU + DELETE + "Enter ID: No found contact\n"
     + MENU + ADD + "Enter Name: Enter Group: Enter Address: Enter Phone: Successful\n"
     + MENU + DISPLAY + HEADER
     + row(1, "Iker Casillas", "Iker", "Casillas", "Star", "Spain", "1234567890")
     + row(3, "Raul Gonzalez", "Raul", "Gonzalez", "Star", "Spain", "1234567890")
     + row(4, "Xavi Hernandez", "Xavi", "Hernandez", "Star", "Spain", "123.456.7890")
     + MENU),
]
