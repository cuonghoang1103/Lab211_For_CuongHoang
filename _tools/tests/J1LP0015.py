"""Keystroke runs for J1.L.P0015 (Asset management - employee).

REPLACE_REFERENCE = True: the reference runs are chained through the .dat files (run 1
reads what run 0 wrote); verify.py gives every run a fresh copy of the project folder,
and this project ships the brief's four sample files, so every run starts from them.

A new request is stamped with the current date and time, so check() replaces every
date-time that is not one of the brief's sample stamps by <NOW>, checks each one is a
real dd-MM-yyyy HH:mm:ss, then compares the rest exactly.

21/09/2026 (the lecturer's paper checklist: ONE render per switch-case): the one render
of Functions 3-5 is the list the brief shows before the choice, so a request sent, a
request cancelled or a borrow returned prints no line of its own; Y to "continue" runs the
same function again (title and list again). A business error of sending goes back to the
menu; an id that is not his is asked again at once (check-only call).
"""
import re
from datetime import datetime

REPLACE_REFERENCE = True

SAMPLE = ['15-12-2021 17:15:52', '23-12-2021 11:19:56', '23-12-2021 13:17:56', '23-12-2021 15:13:46', '24-12-2021 10:10:56', '24-12-2021 12:18:56', '25-12-2021 16:14:56', '26-12-2021 12:16:53']
STAMP = re.compile(r"\d{2}-\d{2}-\d{4} \d{2}:\d{2}:\d{2}")


def keys(*lines):
    return "\n".join(lines) + "\n"


def check(expected):
    def run(out):
        fresh = [s for s in STAMP.findall(out) if s not in SAMPLE]
        for stamp in fresh:
            try:
                datetime.strptime(stamp, "%d-%m-%Y %H:%M:%S")
            except ValueError:
                return False, stamp + " is not a real date-time"
        got = STAMP.sub(lambda m: m.group(0) if m.group(0) in SAMPLE else "<NOW>", out)
        if got.strip() != expected.strip():
            return False, "output differs (fresh stamps shown as <NOW>)\n" + got
        return True, ""
    return run


RUNS = [
    # A: guard, wrong and good login, search, borrow (bad id, unknown asset, bad and too big quantity -> menu; success -> continue?), cancel (not mine asked again, N, Y to continue, Y), return (not mine asked again, Y) and the stock that grows back
    (keys('3', '1', 'E160001', '000000', '1', 'E160001', '123456', '2', 'pro', '2', 'zzz', '3', 'A9', 'A099', '1', '3', 'A002', '0', 'abc', '99', '3', 'a002', '2', 'x', 'N', '4', 'R001', 'r008', 'n', 'Y', 'R002', 'y', 'N', '5', 'B003', 'b001', 'y', 'N', '2', 'samsung', '9', '6'),
     check(r'''
========== BMLT ASSET - EMPLOYEE ==========
1. Login
2. Search asset by name
3. Borrow the assets
4. Cancel request
5. Return asset
6. Quit
===========================================
Your choice: --- Borrow the assets ---
You must login first.

========== BMLT ASSET - EMPLOYEE ==========
1. Login
2. Search asset by name
3. Borrow the assets
4. Cancel request
5. Return asset
6. Quit
===========================================
Your choice: --- Login ---
Employee ID: Password: Incorrect id or password

========== BMLT ASSET - EMPLOYEE ==========
1. Login
2. Search asset by name
3. Borrow the assets
4. Cancel request
5. Return asset
6. Quit
===========================================
Your choice: --- Login ---
Employee ID: Password: Successfully
Welcome, Nguyen Hong Hiep (Employee).

========== BMLT ASSET - EMPLOYEE ==========
1. Login
2. Search asset by name
3. Borrow the assets
4. Cancel request
5. Return asset
6. Quit
===========================================
Your choice: --- Search asset by name ---
Enter a part of the asset name: Id     Name                  Color        Price   Weight   Qty
------------------------------------------------------------
A001   Samsung projector     White       500.00     3.20    10
A002   Macbook pro 2016      Sliver     1000.00     2.20     5
------------------------------------------------------------

========== BMLT ASSET - EMPLOYEE ==========
1. Login
2. Search asset by name
3. Borrow the assets
4. Cancel request
5. Return asset
6. Quit
===========================================
Your choice: --- Search asset by name ---
Enter a part of the asset name: No asset found.

========== BMLT ASSET - EMPLOYEE ==========
1. Login
2. Search asset by name
3. Borrow the assets
4. Cancel request
5. Return asset
6. Quit
===========================================
Your choice: --- Borrow the assets ---
Id     Name                  Color        Price   Weight   Qty
------------------------------------------------------------
A001   Samsung projector     White       500.00     3.20    10
A002   Macbook pro 2016      Sliver     1000.00     2.20     5
------------------------------------------------------------
Enter asset id: Asset id must be the letter A and 3 digits, for example A001.
Enter asset id: Enter quantity: Asset does not exist

========== BMLT ASSET - EMPLOYEE ==========
1. Login
2. Search asset by name
3. Borrow the assets
4. Cancel request
5. Return asset
6. Quit
===========================================
Your choice: --- Borrow the assets ---
Id     Name                  Color        Price   Weight   Qty
------------------------------------------------------------
A001   Samsung projector     White       500.00     3.20    10
A002   Macbook pro 2016      Sliver     1000.00     2.20     5
------------------------------------------------------------
Enter asset id: Enter quantity: Quantity must be a whole number from 1 to 1000000.
Enter quantity: Quantity must be a whole number from 1 to 1000000.
Enter quantity: Only 5 Macbook pro 2016 left in stock.

========== BMLT ASSET - EMPLOYEE ==========
1. Login
2. Search asset by name
3. Borrow the assets
4. Cancel request
5. Return asset
6. Quit
===========================================
Your choice: --- Borrow the assets ---
Id     Name                  Color        Price   Weight   Qty
------------------------------------------------------------
A001   Samsung projector     White       500.00     3.20    10
A002   Macbook pro 2016      Sliver     1000.00     2.20     5
------------------------------------------------------------
Enter asset id: Enter quantity: Do you want to continue (Y/N)? Please enter Y or N.
Do you want to continue (Y/N)? 
========== BMLT ASSET - EMPLOYEE ==========
1. Login
2. Search asset by name
3. Borrow the assets
4. Cancel request
5. Return asset
6. Quit
===========================================
Your choice: --- Cancel request ---
Id     Asset  Asset name              Qty  Requested at
------------------------------------------------------------
R002   A002   Macbook pro 2016          1  24-12-2021 12:18:56
R008   A002   Macbook pro 2016          2  <NOW>
------------------------------------------------------------
Enter request id to cancel: You have no request with id R001.
Enter request id to cancel: Do you want to cancel request R008? (Y/N): Do you want to continue (Y/N)? --- Cancel request ---
Id     Asset  Asset name              Qty  Requested at
------------------------------------------------------------
R002   A002   Macbook pro 2016          1  24-12-2021 12:18:56
R008   A002   Macbook pro 2016          2  <NOW>
------------------------------------------------------------
Enter request id to cancel: Do you want to cancel request R002? (Y/N): Do you want to continue (Y/N)? 
========== BMLT ASSET - EMPLOYEE ==========
1. Login
2. Search asset by name
3. Borrow the assets
4. Cancel request
5. Return asset
6. Quit
===========================================
Your choice: --- Return asset ---
Id     Asset  Asset name              Qty  Borrowed at
------------------------------------------------------------
B001   A001   Samsung projector         1  23-12-2021 15:13:46
B002   A001   Samsung projector         2  25-12-2021 16:14:56
------------------------------------------------------------
Enter borrow id to return: You have no borrowed asset with id B003.
Enter borrow id to return: Do you want to return borrow B001? (Y/N): Do you want to continue (Y/N)? 
========== BMLT ASSET - EMPLOYEE ==========
1. Login
2. Search asset by name
3. Borrow the assets
4. Cancel request
5. Return asset
6. Quit
===========================================
Your choice: --- Search asset by name ---
Enter a part of the asset name: Id     Name                  Color        Price   Weight   Qty
------------------------------------------------------------
A001   Samsung projector     White       500.00     3.20    11
------------------------------------------------------------

========== BMLT ASSET - EMPLOYEE ==========
1. Login
2. Search asset by name
3. Borrow the assets
4. Cancel request
5. Return asset
6. Quit
===========================================
Your choice: Please choose from 1 to 6.
Your choice: Goodbye.''')),
    # B: guards before login, the manager may not borrow, cancel and return until nothing is left
    (keys('4', '5', '1', 'E160052', '123456', '3', '1', 'E160240', '123456', '4', 'R007', 'y', 'Y', '5', 'B007', 'Y', 'Y', '6'),
     check(r'''
========== BMLT ASSET - EMPLOYEE ==========
1. Login
2. Search asset by name
3. Borrow the assets
4. Cancel request
5. Return asset
6. Quit
===========================================
Your choice: --- Cancel request ---
You must login first.

========== BMLT ASSET - EMPLOYEE ==========
1. Login
2. Search asset by name
3. Borrow the assets
4. Cancel request
5. Return asset
6. Quit
===========================================
Your choice: --- Return asset ---
You must login first.

========== BMLT ASSET - EMPLOYEE ==========
1. Login
2. Search asset by name
3. Borrow the assets
4. Cancel request
5. Return asset
6. Quit
===========================================
Your choice: --- Login ---
Employee ID: Password: Successfully
Welcome, Hoa Doan (Manager).

========== BMLT ASSET - EMPLOYEE ==========
1. Login
2. Search asset by name
3. Borrow the assets
4. Cancel request
5. Return asset
6. Quit
===========================================
Your choice: --- Borrow the assets ---
A manager cannot borrow assets. Please use the manager's program.

========== BMLT ASSET - EMPLOYEE ==========
1. Login
2. Search asset by name
3. Borrow the assets
4. Cancel request
5. Return asset
6. Quit
===========================================
Your choice: --- Login ---
Employee ID: Password: Successfully
Welcome, Tran Dinh Khanh (Employee).

========== BMLT ASSET - EMPLOYEE ==========
1. Login
2. Search asset by name
3. Borrow the assets
4. Cancel request
5. Return asset
6. Quit
===========================================
Your choice: --- Cancel request ---
Id     Asset  Asset name              Qty  Requested at
------------------------------------------------------------
R007   A002   Macbook pro 2016          1  24-12-2021 10:10:56
------------------------------------------------------------
Enter request id to cancel: Do you want to cancel request R007? (Y/N): Do you want to continue (Y/N)? --- Cancel request ---
You have no request.

========== BMLT ASSET - EMPLOYEE ==========
1. Login
2. Search asset by name
3. Borrow the assets
4. Cancel request
5. Return asset
6. Quit
===========================================
Your choice: --- Return asset ---
Id     Asset  Asset name              Qty  Borrowed at
------------------------------------------------------------
B007   A001   Samsung projector         2  26-12-2021 12:16:53
------------------------------------------------------------
Enter borrow id to return: Do you want to return borrow B007? (Y/N): Do you want to continue (Y/N)? --- Return asset ---
You have no borrowed asset.

========== BMLT ASSET - EMPLOYEE ==========
1. Login
2. Search asset by name
3. Borrow the assets
4. Cancel request
5. Return asset
6. Quit
===========================================
Your choice: Goodbye.''')),
]
