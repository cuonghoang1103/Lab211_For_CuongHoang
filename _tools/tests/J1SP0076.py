"""Keystroke runs for J1.S.P0076 (standardise a CSV file).

REPLACE_REFERENCE: the reference program wrote import.csv itself on its
first run ("Demo data file created: import.csv") and its run 1 imports
export.csv left behind by run 0, while verify.py gives every run a fresh
copy of the project root. Here import.csv is SHIPPED at the project root (the
brief's d:\\import.csv), so that line is gone and each run creates the files
it reads back.

21/09/2026 (paper checklist + "match the brief 100%"): the program no longer
prints the whole CSV after Import / Format. The brief's "Expectation of User
interface" shows only "Import: Done" / "Format: Done" / "Export: Done" under
each title, so that is all the screen shows now. The formatting itself is
checked by opening the exported file (HUONG-DAN §5), not on screen.

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

RUNS = [
    # the brief's flow 1 -> 2 -> 3 -> 4 -> 5, then the exported file is
    # imported back to prove it was written
    ("1\nimport.csv\n2\n3\n4\nexport.csv\n1\nexport.csv\n5\n",
     MENU + IMPORT + "Import: Done\n"
     + MENU + ADDRESS + "Format: Done\n"
     + MENU + NAME + "Format: Done\n"
     + MENU + EXPORT + "Export: Done\n"
     + MENU + IMPORT + "Import: Done\n"
     + MENU),

    # Format Name alone, export, then the new file is imported back
    ("1\nimport.csv\n3\n4\nnames.csv\n1\nnames.csv\n5\n",
     MENU + IMPORT + "Import: Done\n"
     + MENU + NAME + "Format: Done\n"
     + MENU + EXPORT + "Export: Done\n"
     + MENU + IMPORT + "Import: Done\n"
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
     + MENU + IMPORT + "Import: Done\n"
     + MENU + EXPORT + "Cannot write file\n"
     + MENU),
]
