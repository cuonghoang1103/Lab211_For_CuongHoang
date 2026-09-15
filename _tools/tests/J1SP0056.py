"""Keystroke runs for J1.S.P0056 (worker management).

REPLACE_REFERENCE: the reference transcripts print the salary as "1100.0",
while the brief's table shows "1100". The brief wins, so the three reference
runs are copied below with the salary written as the brief writes it (whole
values without decimals) - every other character unchanged - and only these
runs count.

Added runs cover what the reference misses: a non-number at "Enter Salary: "
and at the up/down amount, the age bounds 18/50 (accepted) and 17/51
(refused), the brief's own sample data ("W 1" raised twice, "W 3" cut once),
a salary with decimals (999.5), and a cut that succeeds.

The Date column is "today", so a fixed transcript is impossible. Each run is
a predicate: the expected screen is written with {DATE}; the check accepts
the date of today OR of yesterday (a run that crosses midnight), and every
other character must match exactly.
"""
import datetime

REPLACE_REFERENCE = True

MENU = ("======== Worker Management =========\n"
        "1. Add Worker\n2. Up salary\n3. Down salary\n"
        "4. Display Information salary\n5. Exit\n"
        "Enter your choice:")
ADD = "--------- Add Worker ----------\n"
ADD_PROMPTS = "Enter Code:Enter Name:Enter Age:Enter Salary: Enter work location:"
CHG = "------- Up/Down Salary --------\nEnter Code:Enter Salary:"
DISPLAY = "--------------------Display Information Salary-----------------------\n"
HEADER = "Code    Name        Age   Salary      Status  Date\n"
ADJUSTED = "Salary has been adjusted.\n"


def dated(template):
    """Predicate: the output equals the template with {DATE} = today or
    yesterday, formatted dd/MM/yyyy like the brief's table."""
    def check(out):
        today = datetime.date.today()
        wants = []
        for day in (today, today - datetime.timedelta(days=1)):
            wants.append(template.replace("{DATE}", day.strftime("%d/%m/%Y")))
        if out.strip() in (w.strip() for w in wants):
            return True, ""
        return False, "output differs\n--- EXPECTED ---\n" + wants[0]
    return check


RUNS = [
    # the brief's own sample: W 1 raised to 1100 then 1500, W 3 cut to 1300
    ("1\nW 1\nNghia\n20\n1000\nHanoi\n"
     "1\nW 3\nLien\n20\n1400\nDanang\n"
     "2\nW 1\n100\n2\nW 1\n400\n3\nW 3\n100\n4\n5\n",
     dated(MENU + ADD + ADD_PROMPTS + "Worker [W 1] has been added.\n"
           + MENU + ADD + ADD_PROMPTS + "Worker [W 3] has been added.\n"
           + MENU + CHG + ADJUSTED
           + MENU + CHG + ADJUSTED
           + MENU + CHG + ADJUSTED
           + MENU + DISPLAY + HEADER
           + "W 1     Nghia       20    1100        UP      {DATE}\n"
           + "W 1     Nghia       20    1500        UP      {DATE}\n"
           + "W 3     Lien        20    1300        DOWN    {DATE}\n"
           + MENU + "Goodbye.")),
    # age bounds: 17 and 51 refused, 18 and 50 accepted; a letter at the
    # salary prompt is asked again
    ("1\nA\nAn\n17\n500\nHue\n"
     "1\nB\nBinh\n51\n500\nHue\n"
     "1\nC\nChi\n18\nabc\n500\nHue\n"
     "1\nD\nDung\n50\n700.5\nHue\n"
     "5\n",
     dated(MENU + ADD + ADD_PROMPTS + "Age must be in range 18 to 50\n"
           + MENU + ADD + ADD_PROMPTS + "Age must be in range 18 to 50\n"
           + MENU + ADD + "Enter Code:Enter Name:Enter Age:Enter Salary: "
           + "You must input a number.\n"
           + "Enter Salary: Enter work location:Worker [C] has been added.\n"
           + MENU + ADD + ADD_PROMPTS + "Worker [D] has been added.\n"
           + MENU + "Goodbye.")),
    # amount that is not a number is asked again; zero amount refused on a
    # cut; a legal cut keeps decimals; display shows the one line
    ("1\nW2\nAnh\n30\n1000\nHue\n"
     "3\nW2\nxyz\n0\n"
     "3\nW2\n0.5\n4\n5\n",
     dated(MENU + ADD + ADD_PROMPTS + "Worker [W2] has been added.\n"
           + MENU + CHG + "You must input a number.\n"
           + "Enter Salary:Amount of money must be > 0\n"
           + MENU + CHG + ADJUSTED
           + MENU + DISPLAY + HEADER
           + "W2      Anh         30    999.5       DOWN    {DATE}\n"
           + MENU + "Goodbye.")),
]

NUM = "You must input a number.\n"

RUNS += [
    # reference run 0: W1 raised twice, W3 cut once
    ("1\nW1\nNghia\n20\n1000\nHanoi\n1\nW3\nLien\n20\n1400\nDanang\n"
     "2\nW1\n100\n2\nW1\n400\n3\nW3\n100\n4\n5\n",
     dated(MENU + ADD + ADD_PROMPTS + "Worker [W1] has been added.\n"
           + MENU + ADD + ADD_PROMPTS + "Worker [W3] has been added.\n"
           + MENU + CHG + ADJUSTED + MENU + CHG + ADJUSTED + MENU + CHG + ADJUSTED
           + MENU + DISPLAY + HEADER
           + "W1      Nghia       20    1100        UP      {DATE}\n"
           + "W1      Nghia       20    1500        UP      {DATE}\n"
           + "W3      Lien        20    1300        DOWN    {DATE}\n"
           + MENU + "Goodbye.")),
    # reference run 1: every error message of the brief
    ("9\nabc\n4\n1\nW1\nNghia\n20\n1000\nHanoi\n1\nW1\nOther\n30\n900\nHue\n"
     "1\n\nNoCode\n30\n900\nHue\n1\nW9\nOld\n60\n900\nHue\n"
     "1\nW8\nZero\nabc\n25\n0\nHue\n2\nW2\n100\n2\nW1\n-5\n3\nW1\n2000\n5\n",
     dated(MENU + "Please choose from 1 to 5.\n"
           + "Enter your choice:" + NUM
           + "Enter your choice:" + DISPLAY + "No salary has been adjusted yet.\n"
           + MENU + ADD + ADD_PROMPTS + "Worker [W1] has been added.\n"
           + MENU + ADD + ADD_PROMPTS + "Code [W1] already exists.\n"
           + MENU + ADD + ADD_PROMPTS + "Code cannot be null.\n"
           + MENU + ADD + ADD_PROMPTS + "Age must be in range 18 to 50\n"
           + MENU + ADD + "Enter Code:Enter Name:Enter Age:" + NUM
           + "Enter Age:Enter Salary: Enter work location:"
           + "Salary must be greater than 0\n"
           + MENU + CHG + "Code [W2] does not exist.\n"
           + MENU + CHG + "Amount of money must be > 0\n"
           + MENU + CHG + "Salary must be greater than 0\n"
           + MENU + "Goodbye.")),
    # reference run 2: sort by code, same-code lines stay in time order
    ("1\nW3\nLien\n30\n1000\nHue\n1\nW1\nNghia\n20\n1000\nHanoi\n"
     "1\nW2\nAnh\n40\n1000\nDanang\n2\nW3\n30\n2\nW1\n10\n3\nW2\n20\n"
     "2\nW1\n11\n3\nW3\n33\n4\n5\n",
     dated(MENU + ADD + ADD_PROMPTS + "Worker [W3] has been added.\n"
           + MENU + ADD + ADD_PROMPTS + "Worker [W1] has been added.\n"
           + MENU + ADD + ADD_PROMPTS + "Worker [W2] has been added.\n"
           + (MENU + CHG + ADJUSTED) * 5
           + MENU + DISPLAY + HEADER
           + "W1      Nghia       20    1010        UP      {DATE}\n"
           + "W1      Nghia       20    1021        UP      {DATE}\n"
           + "W2      Anh         40    980         DOWN    {DATE}\n"
           + "W3      Lien        30    1030        UP      {DATE}\n"
           + "W3      Lien        30    997         DOWN    {DATE}\n"
           + MENU + "Goodbye.")),
]
