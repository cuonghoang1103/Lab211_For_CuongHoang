"""Keystroke runs for J1.S.P0073 (Handy Expense).

REPLACE_REFERENCE (two reasons). 1) The reference prints amounts as
"100.10" / "Total: 550.60", while the brief's table shows "100" and
"Total: 550": the brief wins, so amounts are written with pattern "0.##"
(whole values without decimals, others with theirs). 2) The reference's run 1 starts with two expenses already in
expenses.txt - it depends on the file its run 0 left behind. verify.py gives
every run a fresh copy of the project folder (no data file), so that run can
never pass on its own. Its run 0 needs no file and is copied below
with only the amounts re-written as the brief writes them; the persistence it proved is checked by hand (HUONG-DAN section 5).

Run 1 covers what run 0 misses: amount 0 and negative, blank content (twice),
a lower-case month, two wrongly shaped dates, a letter as the ID to delete,
and "ID = ID Max + 1" after deleting the LAST expense (the ID 2 is given
again, because the largest ID left is 1). Expected screens written from the
brief and from the reference's own messages.
"""

REPLACE_REFERENCE = True

MENU = ("=======Handy Expense program======\n"
        "1. Add an expense\n2. Display all expenses\n3. Delete an expense\n"
        "4. Quit\nYour choice: ")
ADD = "-------- Add an expense--------\n"
DISPLAY = "---------Display all expenses------------\n"
DELETE = "--------Delete an expense------\n"
HEADER = "ID   Date          Amount       Content\n"
DATE_ERR = "Date must be in format dd-MMM-yyyy, e.g. 11-Apr-2009.\n"
OK = "Add an expense successful\n"

RUNS = [
    # the reference's run 0 (needs no data file), amounts as in the brief
    ("x\n9\n2\n1\n31-Feb-2009\n11-Apr-2009\nabc\n100.10\nTuition fee\n"
     "1\n20-Apr-2009\n250.20\nRent house\n1\n30-Apr-2009\n200.30\nFood\n"
     "2\n3\n9\n3\n2\n2\n4\n",
     MENU + "You must input a number.\n"
     "Your choice: Please input a number in [1, 4].\n"
     "Your choice: " + DISPLAY + "There is no expense to display.\n"
     + MENU + ADD + "Enter Date: " + DATE_ERR
     + "Enter Date: Enter Amount: Amount must be a number.\n"
     "Enter Amount: Enter Content: " + OK
     + MENU + ADD + "Enter Date: Enter Amount: Enter Content: " + OK
     + MENU + ADD + "Enter Date: Enter Amount: Enter Content: " + OK
     + MENU + DISPLAY + HEADER
     + "1    11-Apr-2009   100.1        Tuition fee\n"
     "2    20-Apr-2009   250.2        Rent house\n"
     "3    30-Apr-2009   200.3        Food\n"
     "Total: 550.6\n"
     + MENU + DELETE + "Enter ID: Delete an expense fail\n"
     + MENU + DELETE + "Enter ID: Delete an expense successful\n"
     + MENU + DISPLAY + HEADER
     + "1    11-Apr-2009   100.1        Tuition fee\n"
     "3    30-Apr-2009   200.3        Food\n"
     "Total: 300.4\n"
     + MENU + "Bye."),
    ("1\n11-apr-2009\n0\n-5\n100\n\n   \nTuition fee\n"
     "1\n2009-04-20\n11-Apr-09\n20-Apr-2009\n250\nRent | house\n"
     "3\nabc\n2\n2\n"
     "1\n30-Apr-2009\n200\nFood\n2\n4\n",
     MENU + ADD + "Enter Date: Enter Amount: Amount must be greater than 0.\n"
     "Enter Amount: Amount must be greater than 0.\n"
     "Enter Amount: Enter Content: This field must not be empty.\n"
     "Enter Content: This field must not be empty.\n"
     "Enter Content: " + OK
     + MENU + ADD + "Enter Date: " + DATE_ERR + "Enter Date: " + DATE_ERR
     + "Enter Date: Enter Amount: Enter Content: " + OK
     + MENU + DELETE + "Enter ID: You must input a number.\n"
     "Enter ID: Delete an expense successful\n"
     + MENU + DISPLAY + HEADER
     + "1    11-Apr-2009   100          Tuition fee\n"
     "Total: 100\n"
     + MENU + ADD + "Enter Date: Enter Amount: Enter Content: " + OK
     + MENU + DISPLAY + HEADER
     + "1    11-Apr-2009   100          Tuition fee\n"
     "2    30-Apr-2009   200          Food\n"
     "Total: 300\n"
     + MENU + "Bye."),
]
