"""Keystroke runs for J1.S.P0076 (standardise a CSV file).

REPLACE_REFERENCE: the reference program wrote import.csv itself on its
first run ("Demo data file created: import.csv") and its run 1 imports
export.csv left behind by run 0, while verify.py gives every run a fresh
copy of the project root. Here import.csv is SHIPPED at the project root (the
brief's d:\\import.csv), so that line is gone and each run creates the files
it reads back. Every other screen line is the reference's / the brief's.

import.csv (project root): a header row, then 4 customers with the brief's
two faults - row 1 is the brief's own example, row 3 has no space after its
commas, row 4 has an EMPTY Address (the trailing field split(",") drops).
Expected screens are written from the brief, not captured from the program.
"""

REPLACE_REFERENCE = True

MENU = ("======= Format CSV Program =======\n"
        "1. Import CSV\n"
        "2. Format Address\n"
        "3. Format Name\n"
        "4. Export CSV\n"
        "5. Exit\n"
        "Please choice one option:")
IMPORT = "--------- Import CSV -------\nEnter Path:"
ADDRESS = "--------- Format Address -------\n"
NAME = "--------- Format Name -------\n"
EXPORT = "--------- Export CSV ------\nEnter Path:"
NODATA = "No CSV file has been imported\n"

RAW = ("ID, Name, Email, Phone, Address\n"
       "1, Nguyen   van a, anv@gmail.com, 098889999, Cau Giay     -   Ha    Noi    - Viet Nam\n"
       "2, tran  thi   b, ttb@gmail.com, 0912345678, Thanh   Xuan   - Ha Noi - Viet Nam\n"
       "3,le  van   c ,lvc@gmail.com,0987654321,Hai   Chau  - Da  Nang - Viet Nam\n"
       "4, pham van d, pvd@gmail.com, 0900000000,\n")
NAMED = ("ID, Name, Email, Phone, Address\n"
         "1, Nguyen Van A, anv@gmail.com, 098889999, Cau Giay     -   Ha    Noi    - Viet Nam\n"
         "2, Tran Thi B, ttb@gmail.com, 0912345678, Thanh   Xuan   - Ha Noi - Viet Nam\n"
         "3, Le Van C, lvc@gmail.com, 0987654321, Hai   Chau  - Da  Nang - Viet Nam\n"
         "4, Pham Van D, pvd@gmail.com, 0900000000, \n")
ADDRESSED = ("ID, Name, Email, Phone, Address\n"
             "1, Nguyen   van a, anv@gmail.com, 098889999, Cau Giay - Ha Noi - Viet Nam\n"
             "2, tran  thi   b, ttb@gmail.com, 0912345678, Thanh Xuan - Ha Noi - Viet Nam\n"
             "3, le  van   c, lvc@gmail.com, 0987654321, Hai Chau - Da Nang - Viet Nam\n"
             "4, pham van d, pvd@gmail.com, 0900000000, \n")
BOTH = ("ID, Name, Email, Phone, Address\n"
        "1, Nguyen Van A, anv@gmail.com, 098889999, Cau Giay - Ha Noi - Viet Nam\n"
        "2, Tran Thi B, ttb@gmail.com, 0912345678, Thanh Xuan - Ha Noi - Viet Nam\n"
        "3, Le Van C, lvc@gmail.com, 0987654321, Hai Chau - Da Nang - Viet Nam\n"
        "4, Pham Van D, pvd@gmail.com, 0900000000, \n")

RUNS = [
    # the brief's flow 1 -> 2 -> 3 -> 4 -> 5, then the exported file is
    # imported back to prove it holds both formats
    ("1\nimport.csv\n2\n3\n4\nexport.csv\n1\nexport.csv\n5\n",
     MENU + IMPORT + "Import: Done\n" + RAW
     + MENU + ADDRESS + "Format: Done\n" + ADDRESSED
     + MENU + NAME + "Format: Done\n" + BOTH
     + MENU + EXPORT + "Export: Done\n"
     + MENU + IMPORT + "Import: Done\n" + BOTH
     + MENU),

    # Format Name alone changes only the Name column (Address keeps its
    # spaces); exporting then re-importing the half-formatted data
    ("1\nimport.csv\n3\n4\nnames.csv\n1\nnames.csv\n5\n",
     MENU + IMPORT + "Import: Done\n" + RAW
     + MENU + NAME + "Format: Done\n" + NAMED
     + MENU + EXPORT + "Export: Done\n"
     + MENU + IMPORT + "Import: Done\n" + NAMED
     + MENU),

    # every error: menu letters / out of range; options 2, 3, 4 before any
    # import; import of a missing file and of a folder; export into a folder
    ("abc\n6\n0\n2\n3\n4\nout.csv\n1\nnosuch.csv\n1\ntest\n"
     "1\nimport.csv\n4\ntest\n5\n",
     MENU + "You must input a number.\n"
     "Please choice one option:Please choose from 1 to 5.\n"
     "Please choice one option:Please choose from 1 to 5.\n"
     "Please choice one option:" + ADDRESS + NODATA
     + MENU + NAME + NODATA
     + MENU + EXPORT + NODATA
     + MENU + IMPORT + "Path doesn't exist\n"
     + MENU + IMPORT + "Cannot read file\n"
     + MENU + IMPORT + "Import: Done\n" + RAW
     + MENU + EXPORT + "Cannot write file\n"
     + MENU),
]
