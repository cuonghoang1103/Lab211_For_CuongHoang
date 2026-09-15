"""Extra keystroke runs for J1.S.P0085 (Employee management) - the messages
and paths the two reference runs do not reach.

Screens come from the brief (menu with its closing line, "Please select an
option: ", the add labels padded to 11 + ": ", "=> Employee E001 added
successfully.", "Enter name (or part): ", the two table layouts with
salaries as 1500.00, "Employees sorted by salary (ascending):"). Wording the
brief does not give (update, remove, errors) follows the reference program.
"""

MENU = ("========= EMPLOYEE MANAGEMENT =========\n"
        "1. Add employees\n"
        "2. Update employees\n"
        "3. Remove employees\n"
        "4. Search employees\n"
        "5. Sort employees by salary\n"
        "6. Exit\n"
        "=======================================\n"
        "Please select an option: ")
LABELS = ["Id         : ", "First name : ", "Last name  : ", "Phone      : ",
          "Email      : ", "Address    : ", "DOB        : ", "Sex        : ",
          "Salary     : ", "Agency     : "]
ALL = "".join(LABELS)
SEARCH_HEAD = ("Id     First name  Last name     Salary  Agency\n"
               "-----------------------------------------------\n")
SORT_HEAD = ("Employees sorted by salary (ascending):\n"
             "Id     Name                Salary  Agency\n"
             "-----------------------------------------\n")


def add(i, first, last, salary, agency="Sales", sex="Male"):
    return "1\n%s\n%s\n%s\n0901234567\n%s@example.com\nDa Nang\n1994-05-20\n%s\n%s\n%s\n" % (
        i, first, last, first.lower(), sex, salary, agency)


def added(i):
    return "-- Add employee --\n" + ALL + "=> Employee %s added successfully.\n" % i


RUNS = [
    # menu letters; add with blank Id, blank first name, bad values the
    # reference misses (date with rubbish, impossible date, salary letters,
    # Infinity, zero), lower-case "female" stored as "Female"
    ("abc\n"
     "1\n\nE001\n\nJohn\nSmith\n0901234567\njohn.smith@example.com\n12 Le Loi, Da Nang\n"
     "1994-05-20xyz\n1994-02-30\n1994-05-20\nfemale\nabc\nInfinity\n0\n1500\n\nSales\n"
     "4\n\nJO\n"
     "6\n",
     "========= EMPLOYEE MANAGEMENT =========\n1. Add employees\n2. Update employees\n"
     "3. Remove employees\n4. Search employees\n5. Sort employees by salary\n6. Exit\n"
     "=======================================\n"
     "Please select an option: You must input a number.\n"
     "Please select an option: -- Add employee --\n"
     "Id         : This field is required.\n"
     "Id         : First name : This field is required.\n"
     "First name : Last name  : Phone      : Email      : Address    : "
     "DOB        : DOB must be a real date in yyyy-MM-dd format.\n"
     "DOB        : DOB must be a real date in yyyy-MM-dd format.\n"
     "DOB        : Sex        : Salary     : Salary must be a number.\n"
     "Salary     : Salary must be a number.\n"
     "Salary     : Salary must be greater than 0.\n"
     "Salary     : Agency     : This field is required.\n"
     "Agency     : => Employee E001 added successfully.\n"
     + MENU + "-- Search employees --\n"
     "Enter name (or part): Please type something to search for.\n"
     "Enter name (or part): " + SEARCH_HEAD
     + "E001   John        Smith        1500.00  Sales\n"
     + MENU + "Goodbye."),
    # search matching several people; equal salaries keep their order
    # (stable sort); Id lookups ignore case; update: a bad phone is refused
    # then Enter keeps the old one, a new salary and sex are stored
    (add("E001", "John", "Smith", "1500")
     + add("E002", "Anna", "Johnson", "1200", "HR", "Female")
     + add("E003", "Bob", "Lee", "1500", "IT")
     + "4\njo\n"
     + "5\n"
     + "2\ne002\nAnnie\n\n09ab\n\n\n\n\n\n2500.5\n\n"
     + "5\n"
     + "3\ne001\n"
     + "5\n6\n",
     MENU + added("E001") + MENU + added("E002") + MENU + added("E003")
     + MENU + "-- Search employees --\nEnter name (or part): " + SEARCH_HEAD
     + "E001   John        Smith        1500.00  Sales\n"
     "E002   Anna        Johnson      1200.00  HR\n"
     + MENU + SORT_HEAD
     + "E002   Anna Johnson       1200.00  HR\n"
     "E001   John Smith         1500.00  Sales\n"
     "E003   Bob Lee            1500.00  IT\n"
     + MENU + "-- Update employee --\n"
     "Id         : Press Enter to keep the value in brackets.\n"
     "First name : [Anna] Last name  : [Johnson] "
     "Phone      : [0901234567] Phone must contain digits only.\n"
     "Phone      : [0901234567] Email      : [anna@example.com] "
     "Address    : [Da Nang] DOB        : [1994-05-20] Sex        : [Female] "
     "Salary     : [1200.00] Agency     : [HR] => Employee E002 updated successfully.\n"
     "   Employee{id=E002, name=Annie Johnson, phone=0901234567, email=anna@example.com, "
     "address=Da Nang, dob=1994-05-20, sex=Female, salary=2500.50, agency=HR}\n"
     + MENU + SORT_HEAD
     + "E001   John Smith         1500.00  Sales\n"
     "E003   Bob Lee            1500.00  IT\n"
     "E002   Annie Johnson      2500.50  HR\n"
     + MENU + "-- Remove employee --\nId         : => Employee e001 removed successfully.\n"
     + MENU + SORT_HEAD
     + "E003   Bob Lee            1500.00  IT\n"
     "E002   Annie Johnson      2500.50  HR\n"
     + MENU + "Goodbye."),
]
