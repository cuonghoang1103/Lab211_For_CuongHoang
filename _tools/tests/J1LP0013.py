"""Keystroke runs for J1.L.P0013 (Vehicle management).

REPLACE_REFERENCE = True: the reference runs are chained through vehicles.txt (run 1
loads what run 0 stored), but verify.py gives every run a fresh copy of the project
folder. This project ships a vehicles.txt with 5 vehicles, so every run below starts
from the same file and stands on its own.
"""
REPLACE_REFERENCE = True


def keys(*lines):
    return "\n".join(lines) + "\n"


RUNS = [
    # A: load the shipped vehicles.txt, show all, show by price (Tin tin tin), search by name (descending), empty search text, search by id
    (keys('1', '6', '1', '2', '3', '5', '1', 'i', '1', 'zzz', '1', '', 'cam', '2', 'c002', '2', 'C999', '2', 'c1', 'M002', '3', '8'),
     r'''
===== VEHICLE MANAGEMENT =====
1. Load data from file
2. Add new vehicle
3. Update vehicle by ID
4. Delete vehicle by ID
5. Search vehicle
6. Show vehicle list
7. Store data to file
8. Quit
==============================
Your choice: --- Load data from file ---
Loaded 5 vehicle(s) from vehicles.txt

===== VEHICLE MANAGEMENT =====
1. Load data from file
2. Add new vehicle
3. Update vehicle by ID
4. Delete vehicle by ID
5. Search vehicle
6. Show vehicle list
7. Store data to file
8. Quit
==============================
Your choice: 
===== SHOW VEHICLE LIST =====
1. Show all
2. Show all (descending by price)
3. Back to main menu
=============================
Your choice: --- All vehicles in the show room ---
ID     Name             Color           Price Brand      Kind       Details
----------------------------------------------------------------------------------------
C001   Camry            Black       35,000.00 Toyota     Car        Type: Travel, Year: 2020
M001   Exciter 150      Blue         2,500.00 Yamaha     Motorbike  Speed: 150.0km/h, License: Yes
C002   Ranger           White       48,000.00 Ford       Car        Type: Pickup, Year: 2022
M002   Vision           Red          1,200.00 Honda      Motorbike  Speed: 90.0km/h, License: No
C003   Civic            Silver      32,000.50 Honda      Car        Type: Sport, Year: 2021
----------------------------------------------------------------------------------------
Total: 5 vehicle(s)

===== SHOW VEHICLE LIST =====
1. Show all
2. Show all (descending by price)
3. Back to main menu
=============================
Your choice: --- All vehicles, most expensive first ---
ID     Name             Color           Price Brand      Kind       Details
----------------------------------------------------------------------------------------
C002   Ranger           White       48,000.00 Ford       Car        Type: Pickup, Year: 2022
C001   Camry            Black       35,000.00 Toyota     Car        Type: Travel, Year: 2020
C003   Civic            Silver      32,000.50 Honda      Car        Type: Sport, Year: 2021
M001   Exciter 150      Blue         2,500.00 Yamaha     Motorbike  Speed: 150.0km/h, License: Yes
Tin tin tin
M002   Vision           Red          1,200.00 Honda      Motorbike  Speed: 90.0km/h, License: No
Tin tin tin
----------------------------------------------------------------------------------------
Total: 5 vehicle(s)

===== SHOW VEHICLE LIST =====
1. Show all
2. Show all (descending by price)
3. Back to main menu
=============================
Your choice: 
===== VEHICLE MANAGEMENT =====
1. Load data from file
2. Add new vehicle
3. Update vehicle by ID
4. Delete vehicle by ID
5. Search vehicle
6. Show vehicle list
7. Store data to file
8. Quit
==============================
Your choice: 
===== SEARCH VEHICLE =====
1. Search by name
2. Search by id
3. Back to main menu
==========================
Your choice: --- Search vehicle by name ---
Enter a part of the name: ID     Name             Color           Price Brand      Kind       Details
----------------------------------------------------------------------------------------
M002   Vision           Red          1,200.00 Honda      Motorbike  Speed: 90.0km/h, License: No
M001   Exciter 150      Blue         2,500.00 Yamaha     Motorbike  Speed: 150.0km/h, License: Yes
C003   Civic            Silver      32,000.50 Honda      Car        Type: Sport, Year: 2021
----------------------------------------------------------------------------------------
Total: 3 vehicle(s)

===== SEARCH VEHICLE =====
1. Search by name
2. Search by id
3. Back to main menu
==========================
Your choice: --- Search vehicle by name ---
Enter a part of the name: No vehicle found.

===== SEARCH VEHICLE =====
1. Search by name
2. Search by id
3. Back to main menu
==========================
Your choice: --- Search vehicle by name ---
Enter a part of the name: Search text must not be empty.
Enter a part of the name: ID     Name             Color           Price Brand      Kind       Details
----------------------------------------------------------------------------------------
C001   Camry            Black       35,000.00 Toyota     Car        Type: Travel, Year: 2020
----------------------------------------------------------------------------------------
Total: 1 vehicle(s)

===== SEARCH VEHICLE =====
1. Search by name
2. Search by id
3. Back to main menu
==========================
Your choice: --- Search vehicle by id ---
Enter id: ID     Name             Color           Price Brand      Kind       Details
----------------------------------------------------------------------------------------
C002   Ranger           White       48,000.00 Ford       Car        Type: Pickup, Year: 2022

===== SEARCH VEHICLE =====
1. Search by name
2. Search by id
3. Back to main menu
==========================
Your choice: --- Search vehicle by id ---
Enter id: Vehicle does not exist

===== SEARCH VEHICLE =====
1. Search by name
2. Search by id
3. Back to main menu
==========================
Your choice: --- Search vehicle by id ---
Enter id: ID must be one letter followed by 3 digits, for example C001.
Enter id: ID     Name             Color           Price Brand      Kind       Details
----------------------------------------------------------------------------------------
M002   Vision           Red          1,200.00 Honda      Motorbike  Speed: 90.0km/h, License: No

===== SEARCH VEHICLE =====
1. Search by name
2. Search by id
3. Back to main menu
==========================
Your choice: 
===== VEHICLE MANAGEMENT =====
1. Load data from file
2. Add new vehicle
3. Update vehicle by ID
4. Delete vehicle by ID
5. Search vehicle
6. Show vehicle list
7. Store data to file
8. Quit
==============================
Your choice: Goodbye.'''),
    # B: no load: empty show room, menu errors, every add constraint, duplicate id, update with blanks, delete cancel/confirm, quit without storing
    (keys('abc', '9', '6', '1', '2', '3', '5', '1', 'car', '3', '3', 'C001', '2', '1', 'c1', 'C001', 'A', 'Camry', 'Bl4ck', 'Black', '-5', 'abc', 'NaN', '35000', '!', 'Toyota', 'Racing', 'travel', '1800', '2020.5', '2020', 'y', '2', 'c001', 'M001', 'Exciter 150', 'Blue', '2500', 'Yamaha', '500', '150', 'maybe', 'y', 'x', 'n', '3', 'c001', '', 'Silver', '', '', 'sport', '', '3', 'M001', 'Exciter', '', 'abc', '', '', '', 'n', '4', 'Z001', '4', 'M001', 'n', '4', 'm001', 'y', '6', '2', '3', '8', 'n'),
     r'''
===== VEHICLE MANAGEMENT =====
1. Load data from file
2. Add new vehicle
3. Update vehicle by ID
4. Delete vehicle by ID
5. Search vehicle
6. Show vehicle list
7. Store data to file
8. Quit
==============================
Your choice: Please choose from 1 to 8.
Your choice: Please choose from 1 to 8.
Your choice: 
===== SHOW VEHICLE LIST =====
1. Show all
2. Show all (descending by price)
3. Back to main menu
=============================
Your choice: --- All vehicles in the show room ---
The show room is empty.

===== SHOW VEHICLE LIST =====
1. Show all
2. Show all (descending by price)
3. Back to main menu
=============================
Your choice: --- All vehicles, most expensive first ---
The show room is empty.

===== SHOW VEHICLE LIST =====
1. Show all
2. Show all (descending by price)
3. Back to main menu
=============================
Your choice: 
===== VEHICLE MANAGEMENT =====
1. Load data from file
2. Add new vehicle
3. Update vehicle by ID
4. Delete vehicle by ID
5. Search vehicle
6. Show vehicle list
7. Store data to file
8. Quit
==============================
Your choice: 
===== SEARCH VEHICLE =====
1. Search by name
2. Search by id
3. Back to main menu
==========================
Your choice: --- Search vehicle by name ---
Enter a part of the name: No vehicle found.

===== SEARCH VEHICLE =====
1. Search by name
2. Search by id
3. Back to main menu
==========================
Your choice: 
===== VEHICLE MANAGEMENT =====
1. Load data from file
2. Add new vehicle
3. Update vehicle by ID
4. Delete vehicle by ID
5. Search vehicle
6. Show vehicle list
7. Store data to file
8. Quit
==============================
Your choice: --- Update vehicle by ID ---
Enter id: Vehicle does not exist

===== VEHICLE MANAGEMENT =====
1. Load data from file
2. Add new vehicle
3. Update vehicle by ID
4. Delete vehicle by ID
5. Search vehicle
6. Show vehicle list
7. Store data to file
8. Quit
==============================
Your choice: 
===== ADD NEW VEHICLE =====
1. Car
2. Motorbike
3. Back to main menu
===========================
Your choice: --- Add new Car ---
Enter id: ID must be one letter followed by 3 digits, for example C001.
Enter id: Enter name: Name must be 2 to 30 letters, digits, spaces or hyphens.
Enter name: Enter color: Color must be 2 to 15 letters.
Enter color: Enter price: Price must be a positive number.
Enter price: Price must be a positive number.
Enter price: Price must be a positive number.
Enter price: Enter brand: Brand must be 2 to 20 letters, digits, spaces or hyphens.
Enter brand: Enter type (Sport/Travel/Family/Pickup): Type must be one of: Sport, Travel, Family, Pickup.
Enter type (Sport/Travel/Family/Pickup): Enter year of manufacture: Year of manufacture must be from 1900 to 2100.
Enter year of manufacture: Year of manufacture must be from 1900 to 2100.
Enter year of manufacture: Add successfully!
Add another vehicle? (Y/N): 
===== ADD NEW VEHICLE =====
1. Car
2. Motorbike
3. Back to main menu
===========================
Your choice: --- Add new Motorbike ---
Enter id: Vehicle ID c001 already exists.
Enter id: Enter name: Enter color: Enter price: Enter brand: Enter speed (km/h): Speed must be a number from 1 to 400 km/h.
Enter speed (km/h): Require license? (Y/N): Please enter Y or N.
Require license? (Y/N): Add successfully!
Add another vehicle? (Y/N): Please enter Y or N.
Add another vehicle? (Y/N): 
===== VEHICLE MANAGEMENT =====
1. Load data from file
2. Add new vehicle
3. Update vehicle by ID
4. Delete vehicle by ID
5. Search vehicle
6. Show vehicle list
7. Store data to file
8. Quit
==============================
Your choice: --- Update vehicle by ID ---
Enter id: ID     Name             Color           Price Brand      Kind       Details
----------------------------------------------------------------------------------------
C001   Camry            Black       35,000.00 Toyota     Car        Type: Travel, Year: 2020
Leave a field blank to keep the current value.
New name: New color: New price: New brand: New type (Sport/Travel/Family/Pickup): New year of manufacture: Update successfully!
ID     Name             Color           Price Brand      Kind       Details
----------------------------------------------------------------------------------------
C001   Camry            Silver      35,000.00 Toyota     Car        Type: Sport, Year: 2020

===== VEHICLE MANAGEMENT =====
1. Load data from file
2. Add new vehicle
3. Update vehicle by ID
4. Delete vehicle by ID
5. Search vehicle
6. Show vehicle list
7. Store data to file
8. Quit
==============================
Your choice: --- Update vehicle by ID ---
Enter id: ID     Name             Color           Price Brand      Kind       Details
----------------------------------------------------------------------------------------
M001   Exciter 150      Blue         2,500.00 Yamaha     Motorbike  Speed: 150.0km/h, License: Yes
Leave a field blank to keep the current value.
New name: New color: New price: Price must be a positive number.
New price: New brand: New speed (km/h): New require license (Y/N): Update successfully!
ID     Name             Color           Price Brand      Kind       Details
----------------------------------------------------------------------------------------
M001   Exciter          Blue         2,500.00 Yamaha     Motorbike  Speed: 150.0km/h, License: No

===== VEHICLE MANAGEMENT =====
1. Load data from file
2. Add new vehicle
3. Update vehicle by ID
4. Delete vehicle by ID
5. Search vehicle
6. Show vehicle list
7. Store data to file
8. Quit
==============================
Your choice: --- Delete vehicle by ID ---
Enter id: Vehicle does not exist

===== VEHICLE MANAGEMENT =====
1. Load data from file
2. Add new vehicle
3. Update vehicle by ID
4. Delete vehicle by ID
5. Search vehicle
6. Show vehicle list
7. Store data to file
8. Quit
==============================
Your choice: --- Delete vehicle by ID ---
Enter id: ID     Name             Color           Price Brand      Kind       Details
----------------------------------------------------------------------------------------
M001   Exciter          Blue         2,500.00 Yamaha     Motorbike  Speed: 150.0km/h, License: No
Delete this vehicle? (Y/N): Delete cancelled.

===== VEHICLE MANAGEMENT =====
1. Load data from file
2. Add new vehicle
3. Update vehicle by ID
4. Delete vehicle by ID
5. Search vehicle
6. Show vehicle list
7. Store data to file
8. Quit
==============================
Your choice: --- Delete vehicle by ID ---
Enter id: ID     Name             Color           Price Brand      Kind       Details
----------------------------------------------------------------------------------------
M001   Exciter          Blue         2,500.00 Yamaha     Motorbike  Speed: 150.0km/h, License: No
Delete this vehicle? (Y/N): Delete successfully!

===== VEHICLE MANAGEMENT =====
1. Load data from file
2. Add new vehicle
3. Update vehicle by ID
4. Delete vehicle by ID
5. Search vehicle
6. Show vehicle list
7. Store data to file
8. Quit
==============================
Your choice: 
===== SHOW VEHICLE LIST =====
1. Show all
2. Show all (descending by price)
3. Back to main menu
=============================
Your choice: --- All vehicles, most expensive first ---
ID     Name             Color           Price Brand      Kind       Details
----------------------------------------------------------------------------------------
C001   Camry            Silver      35,000.00 Toyota     Car        Type: Sport, Year: 2020
----------------------------------------------------------------------------------------
Total: 1 vehicle(s)

===== SHOW VEHICLE LIST =====
1. Show all
2. Show all (descending by price)
3. Back to main menu
=============================
Your choice: 
===== VEHICLE MANAGEMENT =====
1. Load data from file
2. Add new vehicle
3. Update vehicle by ID
4. Delete vehicle by ID
5. Search vehicle
6. Show vehicle list
7. Store data to file
8. Quit
==============================
Your choice: There are unsaved changes. Store them before quitting? (Y/N): Goodbye.'''),
    # C: file round trip: load, delete, store, load again, add, quit and store
    (keys('1', '4', 'C001', 'y', '7', '1', '6', '1', '3', '2', '1', 'C001', 'Camry', 'Black', '35000', 'Toyota', 'Travel', '2020', 'n', '8', 'y'),
     r'''
===== VEHICLE MANAGEMENT =====
1. Load data from file
2. Add new vehicle
3. Update vehicle by ID
4. Delete vehicle by ID
5. Search vehicle
6. Show vehicle list
7. Store data to file
8. Quit
==============================
Your choice: --- Load data from file ---
Loaded 5 vehicle(s) from vehicles.txt

===== VEHICLE MANAGEMENT =====
1. Load data from file
2. Add new vehicle
3. Update vehicle by ID
4. Delete vehicle by ID
5. Search vehicle
6. Show vehicle list
7. Store data to file
8. Quit
==============================
Your choice: --- Delete vehicle by ID ---
Enter id: ID     Name             Color           Price Brand      Kind       Details
----------------------------------------------------------------------------------------
C001   Camry            Black       35,000.00 Toyota     Car        Type: Travel, Year: 2020
Delete this vehicle? (Y/N): Delete successfully!

===== VEHICLE MANAGEMENT =====
1. Load data from file
2. Add new vehicle
3. Update vehicle by ID
4. Delete vehicle by ID
5. Search vehicle
6. Show vehicle list
7. Store data to file
8. Quit
==============================
Your choice: --- Store data to file ---
Stored 4 vehicle(s) to vehicles.txt

===== VEHICLE MANAGEMENT =====
1. Load data from file
2. Add new vehicle
3. Update vehicle by ID
4. Delete vehicle by ID
5. Search vehicle
6. Show vehicle list
7. Store data to file
8. Quit
==============================
Your choice: --- Load data from file ---
Loaded 4 vehicle(s) from vehicles.txt

===== VEHICLE MANAGEMENT =====
1. Load data from file
2. Add new vehicle
3. Update vehicle by ID
4. Delete vehicle by ID
5. Search vehicle
6. Show vehicle list
7. Store data to file
8. Quit
==============================
Your choice: 
===== SHOW VEHICLE LIST =====
1. Show all
2. Show all (descending by price)
3. Back to main menu
=============================
Your choice: --- All vehicles in the show room ---
ID     Name             Color           Price Brand      Kind       Details
----------------------------------------------------------------------------------------
M001   Exciter 150      Blue         2,500.00 Yamaha     Motorbike  Speed: 150.0km/h, License: Yes
C002   Ranger           White       48,000.00 Ford       Car        Type: Pickup, Year: 2022
M002   Vision           Red          1,200.00 Honda      Motorbike  Speed: 90.0km/h, License: No
C003   Civic            Silver      32,000.50 Honda      Car        Type: Sport, Year: 2021
----------------------------------------------------------------------------------------
Total: 4 vehicle(s)

===== SHOW VEHICLE LIST =====
1. Show all
2. Show all (descending by price)
3. Back to main menu
=============================
Your choice: 
===== VEHICLE MANAGEMENT =====
1. Load data from file
2. Add new vehicle
3. Update vehicle by ID
4. Delete vehicle by ID
5. Search vehicle
6. Show vehicle list
7. Store data to file
8. Quit
==============================
Your choice: 
===== ADD NEW VEHICLE =====
1. Car
2. Motorbike
3. Back to main menu
===========================
Your choice: --- Add new Car ---
Enter id: Enter name: Enter color: Enter price: Enter brand: Enter type (Sport/Travel/Family/Pickup): Enter year of manufacture: Add successfully!
Add another vehicle? (Y/N): 
===== VEHICLE MANAGEMENT =====
1. Load data from file
2. Add new vehicle
3. Update vehicle by ID
4. Delete vehicle by ID
5. Search vehicle
6. Show vehicle list
7. Store data to file
8. Quit
==============================
Your choice: There are unsaved changes. Store them before quitting? (Y/N): --- Store data to file ---
Stored 5 vehicle(s) to vehicles.txt
Goodbye.'''),
]
