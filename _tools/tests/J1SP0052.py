"""Extra keystroke runs for J1.S.P0052 (East Asia countries).

The two reference runs cover: the brief's input screen, option 2 on an empty
list, "You must input a number.", "Total area must be greater than 0.",
option 2 after input, search (exact and partial), search with no match,
sort, and the 11-country cap refused BEFORE the four questions.
These runs add: the menu validation, blank fields, 0 / NaN / Infinity areas,
sort on an empty list, a case-insensitive sort, and the brief's own two
countries (VN then IDN): option 2 shows the LAST one entered.
Expected screens are written from the brief, not captured from the program.
"""

MENU = ("                               MENU\n"
        + "=" * 74 + "\n"
        "1. Input the information of 11 countries in East Asia\n"
        "2. Display the information of country you've just input\n"
        "3. Search the information of country by user-entered name\n"
        "4. Display the information of countries sorted name in ascending order\n"
        "5. Exit\n"
        + "=" * 74 + "\n"
        "Enter your choice : ")
CHOICE = "Please choose an option from 1 to 5.\n"
ASK4 = ("Enter code of country:\nEnter name of country:\nEnter total Area:\n"
        "Enter terrain of country:\n")
HEADER = "ID              Name            Total Area      Terrain\n"
EMPTY = "There is no country in the list.\n"


def row(code, name, area, terrain):
    return "%-16s%-16s%-16s%s\n" % (code, name, area, terrain)


RUNS = [
    # menu validation, then option 4 on an empty list, then exit
    ("abc\n\n0\n6\n4\n5\n",
     MENU + CHOICE + "Enter your choice : " + CHOICE
     + "Enter your choice : " + CHOICE + "Enter your choice : " + CHOICE
     + "Enter your choice : " + EMPTY + MENU),
    # blank code/name/terrain are asked again; area 0, NaN, Infinity refused
    ("1\n\nVN\n\nViet Nam\n0\nNaN\nInfinity\n331698\n\nNice\n2\n5\n",
     MENU + "Enter code of country:\nThis field must not be blank.\n"
     + "Enter code of country:\nEnter name of country:\n"
     + "This field must not be blank.\nEnter name of country:\n"
     + "Enter total Area:\nTotal area must be greater than 0.\n"
     + "Enter total Area:\nYou must input a number.\n"
     + "Enter total Area:\nYou must input a number.\n"
     + "Enter total Area:\nEnter terrain of country:\n"
     + "This field must not be blank.\nEnter terrain of country:\n"
     + "Successful\n"
     + MENU + HEADER + row("VN", "Viet Nam", "331698.0", "Nice") + MENU),
    # the brief's two countries: option 2 shows the last one entered (IDN);
    # a lower-case name sorts by letter, not by ASCII (case ignored);
    # a blank search name is asked again; search ignores case
    ("1\nVN\nViet Nam\n331698\nNice\n"
     "1\nIDN\nIndonesia\n1860360\nNice\n"
     "1\nJP\njapan\n377975\nMountain\n"
     "2\n4\n3\n\nVIET\n5\n",
     MENU + ASK4 + "Successful\n" + MENU + ASK4 + "Successful\n"
     + MENU + ASK4 + "Successful\n"
     + MENU + HEADER + row("JP", "japan", "377975.0", "Mountain")
     + MENU + HEADER + row("IDN", "Indonesia", "1860360.0", "Nice")
     + row("JP", "japan", "377975.0", "Mountain")
     + row("VN", "Viet Nam", "331698.0", "Nice")
     + MENU + "Enter the name you want to search for:\n"
     + "This field must not be blank.\nEnter the name you want to search for:\n"
     + HEADER + row("VN", "Viet Nam", "331698.0", "Nice") + MENU),
]
