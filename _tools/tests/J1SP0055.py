"""Extra keystroke runs for J1.S.P0055 — the paths the reference runs miss.

Expected screens are written from the brief, not captured from the program.
"""

MENU = """========= Doctor Management ==========
1. Add Doctor
2. Update Doctor
3. Delete Doctor
4. Search Doctor
5. Exit
Please choose an option: """

RUNS = [
    # Update with an invalid availability: letters, then negative, then blank
    # (blank keeps the old value 3). Then search shows the name changed but
    # availability kept.
    ("1\nDOC 1\nNghia\nOrthopedics\n3\n"
     "2\nDOC 1\nNghia Tran\n\nabc\n-1\n\n"
     "4\nDOC\n"
     "5\n",
     MENU + "--------- Add Doctor ----------\n"
     "Enter Code: Enter Name: Enter Specialization: Enter Availability: "
     "Add doctor successfully.\n"
     + MENU + "--------- Update Doctor -------\n"
     "Enter Code: Enter Name: Enter Specialization: Enter Availability: "
     "Please input number\n"
     "Enter Availability: Availability must be greater than or equal to 0\n"
     "Enter Availability: Update doctor successfully.\n"
     + MENU + "---------- Search Doctor --------\n"
     "Enter text: --------- Result ------------\n"
     "Code      Name           Specialization      Availability\n"
     "DOC 1     Nghia Tran     Orthopedics         3\n"
     + MENU + "Goodbye."),
]
