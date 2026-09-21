"""Keystroke runs for J1.L.P0014 (Asset management - manager).

REPLACE_REFERENCE = True: the reference runs are chained through the .dat files (run 1
reads what run 0 wrote); verify.py gives every run a fresh copy of the project folder,
and this project ships the brief's four sample files, so every run starts from them.

An approval stamps the borrow with the current date and time, so a transcript cannot be
compared letter for letter. check() replaces every date-time that is not one of the
brief's sample stamps by <NOW>, checks each one is a real dd-MM-yyyy HH:mm:ss, then
compares the rest exactly.

21/09/2026 (the lecturer's paper checklist: ONE render per switch-case): Function 4 no
longer prints the asset before the new values (the brief only asks for the result of the
updating), and Function 5 prints nothing after a good approval (its one render is the
request list the brief shows before the choice; "After approve, the program returns to
the main screen"). Run C covers "Create another asset? Y", which runs Function 3 again.
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
    # A: guards, login (wrong / employee / manager), search, every create and update constraint, approve: unknown id, not enough stock, success (no line: back to the menu); borrow list
    (keys('3', '1', 'E160052', '000000', '1', 'E160001', '123456', '3', '1', 'e160052', '123456', '2', 'pro', '2', 'zzz', '2', '', 'mac', '3', 'A001', 'A3', 'a003', '', 'Dell projector', 'Bl,ack', 'Black', 'abc', '0', 'NaN', '750', '-1', '4.5', '-2', '2.5', '2', 'x', 'N', '4', 'A999', '4', 'a003', '', 'White', '-5', '', '', '6', '5', 'R999', '4', 'A001', '', '', '', '', '0', '5', 'r001', '4', 'A001', '', '', '', '', '10', '5', 'R001', '6', '9', '7'),
     check(r'''
========== BMLT ASSET - MANAGER ==========
1. Login
2. Search asset by name
3. Create new asset
4. Update asset's information
5. Approve the request of employee
6. Show list of borrow asset
7. Quit
==========================================
Your choice: --- Create new asset ---
You must login first.

========== BMLT ASSET - MANAGER ==========
1. Login
2. Search asset by name
3. Create new asset
4. Update asset's information
5. Approve the request of employee
6. Show list of borrow asset
7. Quit
==========================================
Your choice: --- Login ---
Employee ID: Password: Incorrect id or password

========== BMLT ASSET - MANAGER ==========
1. Login
2. Search asset by name
3. Create new asset
4. Update asset's information
5. Approve the request of employee
6. Show list of borrow asset
7. Quit
==========================================
Your choice: --- Login ---
Employee ID: Password: Successfully
Welcome, Nguyen Hong Hiep (Employee).

========== BMLT ASSET - MANAGER ==========
1. Login
2. Search asset by name
3. Create new asset
4. Update asset's information
5. Approve the request of employee
6. Show list of borrow asset
7. Quit
==========================================
Your choice: --- Create new asset ---
Nguyen Hong Hiep is not a manager. This function is for the manager only.

========== BMLT ASSET - MANAGER ==========
1. Login
2. Search asset by name
3. Create new asset
4. Update asset's information
5. Approve the request of employee
6. Show list of borrow asset
7. Quit
==========================================
Your choice: --- Login ---
Employee ID: Password: Successfully
Welcome, Hoa Doan (Manager).

========== BMLT ASSET - MANAGER ==========
1. Login
2. Search asset by name
3. Create new asset
4. Update asset's information
5. Approve the request of employee
6. Show list of borrow asset
7. Quit
==========================================
Your choice: --- Search asset by name ---
Enter a part of the asset name: Id     Name                  Color        Price   Weight   Qty
----------------------------------------------------------------------------
A001   Samsung projector     White       500.00     3.20    10
A002   Macbook pro 2016      Sliver     1000.00     2.20     5
----------------------------------------------------------------------------

========== BMLT ASSET - MANAGER ==========
1. Login
2. Search asset by name
3. Create new asset
4. Update asset's information
5. Approve the request of employee
6. Show list of borrow asset
7. Quit
==========================================
Your choice: --- Search asset by name ---
Enter a part of the asset name: No asset found.

========== BMLT ASSET - MANAGER ==========
1. Login
2. Search asset by name
3. Create new asset
4. Update asset's information
5. Approve the request of employee
6. Show list of borrow asset
7. Quit
==========================================
Your choice: --- Search asset by name ---
Enter a part of the asset name: Search text must not be empty.
Enter a part of the asset name: Id     Name                  Color        Price   Weight   Qty
----------------------------------------------------------------------------
A002   Macbook pro 2016      Sliver     1000.00     2.20     5
----------------------------------------------------------------------------

========== BMLT ASSET - MANAGER ==========
1. Login
2. Search asset by name
3. Create new asset
4. Update asset's information
5. Approve the request of employee
6. Show list of borrow asset
7. Quit
==========================================
Your choice: --- Create new asset ---
Enter asset id: Asset A001 already exists.
Enter asset id: Asset id must be the letter A and 3 digits, for example A003.
Enter asset id: Enter name: Name must be 1 to 40 characters without commas.
Enter name: Enter color: Color must be 1 to 40 characters without commas.
Enter color: Enter price: Price must be a number greater than 0.
Enter price: Price must be a number greater than 0.
Enter price: Price must be a number greater than 0.
Enter price: Enter weight: Weight must be a number greater than 0.
Enter weight: Enter quantity: Quantity must be a whole number from 0 to 1000000.
Enter quantity: Quantity must be a whole number from 0 to 1000000.
Enter quantity: Asset A003 has been created.
Id     Name                  Color        Price   Weight   Qty
----------------------------------------------------------------------------
A003   Dell projector        Black       750.00     4.50     2
----------------------------------------------------------------------------
Create another asset? (Y/N): Please enter Y or N.
Create another asset? (Y/N): 
========== BMLT ASSET - MANAGER ==========
1. Login
2. Search asset by name
3. Create new asset
4. Update asset's information
5. Approve the request of employee
6. Show list of borrow asset
7. Quit
==========================================
Your choice: --- Update asset information ---
Enter asset id: Asset does not exist

========== BMLT ASSET - MANAGER ==========
1. Login
2. Search asset by name
3. Create new asset
4. Update asset's information
5. Approve the request of employee
6. Show list of borrow asset
7. Quit
==========================================
Your choice: --- Update asset information ---
Enter asset id: Leave a field blank to keep the current value.
New name: New color: New price: Price must be a number greater than 0.
New price: New weight: New quantity: Asset A003 has been updated.
Id     Name                  Color        Price   Weight   Qty
----------------------------------------------------------------------------
A003   Dell projector        White       750.00     4.50     6
----------------------------------------------------------------------------

========== BMLT ASSET - MANAGER ==========
1. Login
2. Search asset by name
3. Create new asset
4. Update asset's information
5. Approve the request of employee
6. Show list of borrow asset
7. Quit
==========================================
Your choice: --- Approve the request of employee ---
Id     Asset  Asset name          Employee Employee name        Qty  Requested at
----------------------------------------------------------------------------
R001   A001   Samsung projector   E140449  Le Buu Nhan            1  23-12-2021 13:17:56
R002   A002   Macbook pro 2016    E160001  Nguyen Hong Hiep       1  24-12-2021 12:18:56
R003   A001   Samsung projector   E160798  Truong Le Minh         1  23-12-2021 11:19:56
R007   A002   Macbook pro 2016    E160240  Tran Dinh Khanh        1  24-12-2021 10:10:56
----------------------------------------------------------------------------
Enter request id to approve: Request R999 does not exist.

========== BMLT ASSET - MANAGER ==========
1. Login
2. Search asset by name
3. Create new asset
4. Update asset's information
5. Approve the request of employee
6. Show list of borrow asset
7. Quit
==========================================
Your choice: --- Update asset information ---
Enter asset id: Leave a field blank to keep the current value.
New name: New color: New price: New weight: New quantity: Asset A001 has been updated.
Id     Name                  Color        Price   Weight   Qty
----------------------------------------------------------------------------
A001   Samsung projector     White       500.00     3.20     0
----------------------------------------------------------------------------

========== BMLT ASSET - MANAGER ==========
1. Login
2. Search asset by name
3. Create new asset
4. Update asset's information
5. Approve the request of employee
6. Show list of borrow asset
7. Quit
==========================================
Your choice: --- Approve the request of employee ---
Id     Asset  Asset name          Employee Employee name        Qty  Requested at
----------------------------------------------------------------------------
R001   A001   Samsung projector   E140449  Le Buu Nhan            1  23-12-2021 13:17:56
R002   A002   Macbook pro 2016    E160001  Nguyen Hong Hiep       1  24-12-2021 12:18:56
R003   A001   Samsung projector   E160798  Truong Le Minh         1  23-12-2021 11:19:56
R007   A002   Macbook pro 2016    E160240  Tran Dinh Khanh        1  24-12-2021 10:10:56
----------------------------------------------------------------------------
Enter request id to approve: Not enough stock: A001 has 0 left but request R001 needs 1.

========== BMLT ASSET - MANAGER ==========
1. Login
2. Search asset by name
3. Create new asset
4. Update asset's information
5. Approve the request of employee
6. Show list of borrow asset
7. Quit
==========================================
Your choice: --- Update asset information ---
Enter asset id: Leave a field blank to keep the current value.
New name: New color: New price: New weight: New quantity: Asset A001 has been updated.
Id     Name                  Color        Price   Weight   Qty
----------------------------------------------------------------------------
A001   Samsung projector     White       500.00     3.20    10
----------------------------------------------------------------------------

========== BMLT ASSET - MANAGER ==========
1. Login
2. Search asset by name
3. Create new asset
4. Update asset's information
5. Approve the request of employee
6. Show list of borrow asset
7. Quit
==========================================
Your choice: --- Approve the request of employee ---
Id     Asset  Asset name          Employee Employee name        Qty  Requested at
----------------------------------------------------------------------------
R001   A001   Samsung projector   E140449  Le Buu Nhan            1  23-12-2021 13:17:56
R002   A002   Macbook pro 2016    E160001  Nguyen Hong Hiep       1  24-12-2021 12:18:56
R003   A001   Samsung projector   E160798  Truong Le Minh         1  23-12-2021 11:19:56
R007   A002   Macbook pro 2016    E160240  Tran Dinh Khanh        1  24-12-2021 10:10:56
----------------------------------------------------------------------------
Enter request id to approve: 
========== BMLT ASSET - MANAGER ==========
1. Login
2. Search asset by name
3. Create new asset
4. Update asset's information
5. Approve the request of employee
6. Show list of borrow asset
7. Quit
==========================================
Your choice: --- List of borrowed assets ---
Id     Asset  Asset name          Employee Employee name        Qty  Borrowed at
----------------------------------------------------------------------------
B001   A001   Samsung projector   E160001  Nguyen Hong Hiep       1  23-12-2021 15:13:46
B002   A001   Samsung projector   E160001  Nguyen Hong Hiep       2  25-12-2021 16:14:56
B003   A002   Macbook pro 2016    E160798  Truong Le Minh         3  15-12-2021 17:15:52
B007   A001   Samsung projector   E160240  Tran Dinh Khanh        2  26-12-2021 12:16:53
B008   A001   Samsung projector   E140449  Le Buu Nhan            1  <NOW>
----------------------------------------------------------------------------

========== BMLT ASSET - MANAGER ==========
1. Login
2. Search asset by name
3. Create new asset
4. Update asset's information
5. Approve the request of employee
6. Show list of borrow asset
7. Quit
==========================================
Your choice: Please choose from 1 to 7.
Your choice: Goodbye.''')),
    # B: guards before login, approve every request until none is left, borrow list, a failed login logs the manager out
    (keys('6', '5', '4', 'abc', '1', 'E160052', '123456', '5', 'R002', '5', 'R003', '5', 'R007', '5', 'R001', '5', '6', '1', 'E160001', 'wrong', '6', '7'),
     check(r'''
========== BMLT ASSET - MANAGER ==========
1. Login
2. Search asset by name
3. Create new asset
4. Update asset's information
5. Approve the request of employee
6. Show list of borrow asset
7. Quit
==========================================
Your choice: --- List of borrowed assets ---
You must login first.

========== BMLT ASSET - MANAGER ==========
1. Login
2. Search asset by name
3. Create new asset
4. Update asset's information
5. Approve the request of employee
6. Show list of borrow asset
7. Quit
==========================================
Your choice: --- Approve the request of employee ---
You must login first.

========== BMLT ASSET - MANAGER ==========
1. Login
2. Search asset by name
3. Create new asset
4. Update asset's information
5. Approve the request of employee
6. Show list of borrow asset
7. Quit
==========================================
Your choice: --- Update asset information ---
You must login first.

========== BMLT ASSET - MANAGER ==========
1. Login
2. Search asset by name
3. Create new asset
4. Update asset's information
5. Approve the request of employee
6. Show list of borrow asset
7. Quit
==========================================
Your choice: Please choose from 1 to 7.
Your choice: --- Login ---
Employee ID: Password: Successfully
Welcome, Hoa Doan (Manager).

========== BMLT ASSET - MANAGER ==========
1. Login
2. Search asset by name
3. Create new asset
4. Update asset's information
5. Approve the request of employee
6. Show list of borrow asset
7. Quit
==========================================
Your choice: --- Approve the request of employee ---
Id     Asset  Asset name          Employee Employee name        Qty  Requested at
----------------------------------------------------------------------------
R001   A001   Samsung projector   E140449  Le Buu Nhan            1  23-12-2021 13:17:56
R002   A002   Macbook pro 2016    E160001  Nguyen Hong Hiep       1  24-12-2021 12:18:56
R003   A001   Samsung projector   E160798  Truong Le Minh         1  23-12-2021 11:19:56
R007   A002   Macbook pro 2016    E160240  Tran Dinh Khanh        1  24-12-2021 10:10:56
----------------------------------------------------------------------------
Enter request id to approve: 
========== BMLT ASSET - MANAGER ==========
1. Login
2. Search asset by name
3. Create new asset
4. Update asset's information
5. Approve the request of employee
6. Show list of borrow asset
7. Quit
==========================================
Your choice: --- Approve the request of employee ---
Id     Asset  Asset name          Employee Employee name        Qty  Requested at
----------------------------------------------------------------------------
R001   A001   Samsung projector   E140449  Le Buu Nhan            1  23-12-2021 13:17:56
R003   A001   Samsung projector   E160798  Truong Le Minh         1  23-12-2021 11:19:56
R007   A002   Macbook pro 2016    E160240  Tran Dinh Khanh        1  24-12-2021 10:10:56
----------------------------------------------------------------------------
Enter request id to approve: 
========== BMLT ASSET - MANAGER ==========
1. Login
2. Search asset by name
3. Create new asset
4. Update asset's information
5. Approve the request of employee
6. Show list of borrow asset
7. Quit
==========================================
Your choice: --- Approve the request of employee ---
Id     Asset  Asset name          Employee Employee name        Qty  Requested at
----------------------------------------------------------------------------
R001   A001   Samsung projector   E140449  Le Buu Nhan            1  23-12-2021 13:17:56
R007   A002   Macbook pro 2016    E160240  Tran Dinh Khanh        1  24-12-2021 10:10:56
----------------------------------------------------------------------------
Enter request id to approve: 
========== BMLT ASSET - MANAGER ==========
1. Login
2. Search asset by name
3. Create new asset
4. Update asset's information
5. Approve the request of employee
6. Show list of borrow asset
7. Quit
==========================================
Your choice: --- Approve the request of employee ---
Id     Asset  Asset name          Employee Employee name        Qty  Requested at
----------------------------------------------------------------------------
R001   A001   Samsung projector   E140449  Le Buu Nhan            1  23-12-2021 13:17:56
----------------------------------------------------------------------------
Enter request id to approve: 
========== BMLT ASSET - MANAGER ==========
1. Login
2. Search asset by name
3. Create new asset
4. Update asset's information
5. Approve the request of employee
6. Show list of borrow asset
7. Quit
==========================================
Your choice: --- Approve the request of employee ---
There is no request to approve.

========== BMLT ASSET - MANAGER ==========
1. Login
2. Search asset by name
3. Create new asset
4. Update asset's information
5. Approve the request of employee
6. Show list of borrow asset
7. Quit
==========================================
Your choice: --- List of borrowed assets ---
Id     Asset  Asset name          Employee Employee name        Qty  Borrowed at
----------------------------------------------------------------------------
B001   A001   Samsung projector   E160001  Nguyen Hong Hiep       1  23-12-2021 15:13:46
B002   A001   Samsung projector   E160001  Nguyen Hong Hiep       2  25-12-2021 16:14:56
B003   A002   Macbook pro 2016    E160798  Truong Le Minh         3  15-12-2021 17:15:52
B007   A001   Samsung projector   E160240  Tran Dinh Khanh        2  26-12-2021 12:16:53
B008   A002   Macbook pro 2016    E160001  Nguyen Hong Hiep       1  <NOW>
B009   A001   Samsung projector   E160798  Truong Le Minh         1  <NOW>
B010   A002   Macbook pro 2016    E160240  Tran Dinh Khanh        1  <NOW>
B011   A001   Samsung projector   E140449  Le Buu Nhan            1  <NOW>
----------------------------------------------------------------------------

========== BMLT ASSET - MANAGER ==========
1. Login
2. Search asset by name
3. Create new asset
4. Update asset's information
5. Approve the request of employee
6. Show list of borrow asset
7. Quit
==========================================
Your choice: --- Login ---
Employee ID: Password: Incorrect id or password

========== BMLT ASSET - MANAGER ==========
1. Login
2. Search asset by name
3. Create new asset
4. Update asset's information
5. Approve the request of employee
6. Show list of borrow asset
7. Quit
==========================================
Your choice: --- List of borrowed assets ---
You must login first.

========== BMLT ASSET - MANAGER ==========
1. Login
2. Search asset by name
3. Create new asset
4. Update asset's information
5. Approve the request of employee
6. Show list of borrow asset
7. Quit
==========================================
Your choice: Goodbye.''')),
    # C: Y to 'Create another asset?' runs Function 3 again (title, manager check, a taken id asked again)
    (keys('1', 'E160052', '123456', '3', 'A003', 'Dell projector', 'Black', '750', '4.5', '2', 'Y', 'a003', 'A004', 'HP laptop', 'Silver', '1200', '1.8', '3', 'N', '2', 'p', '7'),
     check(r'''
========== BMLT ASSET - MANAGER ==========
1. Login
2. Search asset by name
3. Create new asset
4. Update asset's information
5. Approve the request of employee
6. Show list of borrow asset
7. Quit
==========================================
Your choice: --- Login ---
Employee ID: Password: Successfully
Welcome, Hoa Doan (Manager).

========== BMLT ASSET - MANAGER ==========
1. Login
2. Search asset by name
3. Create new asset
4. Update asset's information
5. Approve the request of employee
6. Show list of borrow asset
7. Quit
==========================================
Your choice: --- Create new asset ---
Enter asset id: Enter name: Enter color: Enter price: Enter weight: Enter quantity: Asset A003 has been created.
Id     Name                  Color        Price   Weight   Qty
----------------------------------------------------------------------------
A003   Dell projector        Black       750.00     4.50     2
----------------------------------------------------------------------------
Create another asset? (Y/N): --- Create new asset ---
Enter asset id: Asset A003 already exists.
Enter asset id: Enter name: Enter color: Enter price: Enter weight: Enter quantity: Asset A004 has been created.
Id     Name                  Color        Price   Weight   Qty
----------------------------------------------------------------------------
A004   HP laptop             Silver     1200.00     1.80     3
----------------------------------------------------------------------------
Create another asset? (Y/N): 
========== BMLT ASSET - MANAGER ==========
1. Login
2. Search asset by name
3. Create new asset
4. Update asset's information
5. Approve the request of employee
6. Show list of borrow asset
7. Quit
==========================================
Your choice: --- Search asset by name ---
Enter a part of the asset name: Id     Name                  Color        Price   Weight   Qty
----------------------------------------------------------------------------
A001   Samsung projector     White       500.00     3.20    10
A002   Macbook pro 2016      Sliver     1000.00     2.20     5
A004   HP laptop             Silver     1200.00     1.80     3
A003   Dell projector        Black       750.00     4.50     2
----------------------------------------------------------------------------

========== BMLT ASSET - MANAGER ==========
1. Login
2. Search asset by name
3. Create new asset
4. Update asset's information
5. Approve the request of employee
6. Show list of borrow asset
7. Quit
==========================================
Your choice: Goodbye.''')),
]
