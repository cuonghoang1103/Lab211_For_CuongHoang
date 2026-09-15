"""Extra keystroke runs for J1.S.P0071 (Task management) - the messages the
two reference runs do not reach.

Expected screens are written from the brief (menu, titles, prompts, table
header) and the reference program's wording for the error lines (the brief
names the rules but not their wording). Each add screen prints all seven
prompts, then the first failing rule, because the brief's addTask receives
the seven Strings and throws.
"""

MENU = ("========= Task program =========\n"
        "1. Add Task\n"
        "2. Delete task\n"
        "3. Display Task\n"
        "4. exit\n"
        "Please choose one option: ")
ADD = ("------------Add Task---------------\n"
       "Requirement Name: Task Type: Date: From: To: Assignee: Reviewer: ")
DEL = "---------Del Task------\nID:"
TABLE = "----------------------------------------- Task ---------------------------------------\n"
HEADER = "ID  Name                Task Type   Date          Time        Assignee    Reviewer\n"


def add(name="Dev Program", typ="1", date="26-06-2015", frm="9.5", to="17.5",
        assignee="Dev", reviewer="Lead"):
    """Keystrokes for option 1 with the values in screen order."""
    return "1\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n" % (name, typ, date, frm, to, assignee, reviewer)


RUNS = [
    # menu validation, then every add message the reference runs miss
    ("x\n5\n0\n"
     + add(assignee="")
     + add(reviewer="")
     + add(typ="")
     + add(date="")
     + add(date="26-06-2015rubbish")
     + add(date="29-02-2015")
     + add(frm="")
     + add(frm="abc")
     + add(frm="7.5")
     + add(frm="NaN")
     + add(to="")
     + add(frm="9.0", to="9.0")
     + "2\n\n"
     + "4\n",
     "========= Task program =========\n1. Add Task\n2. Delete task\n3. Display Task\n4. exit\n"
     "Please choose one option: You must input a number.\n"
     "Please choose one option: Please choose from 1 to 4.\n"
     "Please choose one option: Please choose from 1 to 4.\n"
     "Please choose one option: " + ADD + "Assignee cannot be empty.\n"
     + MENU + ADD + "Reviewer cannot be empty.\n"
     + MENU + ADD + "Task Type cannot be empty.\n"
     + MENU + ADD + "Date cannot be empty.\n"
     + MENU + ADD + "Date must be a real date in the format dd-MM-yyyy.\n"
     + MENU + ADD + "Date must be a real date in the format dd-MM-yyyy.\n"
     + MENU + ADD + "Plan From cannot be empty.\n"
     + MENU + ADD + "Plan From must be a number.\n"
     + MENU + ADD + "Plan From must be between 8.0 and 17.5.\n"
     + MENU + ADD + "Plan From must be between 8.0 and 17.5.\n"
     + MENU + ADD + "Plan To cannot be empty.\n"
     + MENU + ADD + "Plan From must be less than Plan To.\n"
     + MENU + DEL + "ID cannot be empty.\n"
     + MENU + "Goodbye."),
    # happy path at the edges: 8.0-8.5 and 17.0-17.5, leap day 29-02-2016,
    # type 4; delete the first task, the table keeps ascending IDs
    (add(name="Morning", typ="4", date="29-02-2016", frm="8.0", to="8.5")
     + add(name="Evening", typ="3", date="31-12-2015", frm="17", to="17.5",
           assignee="Ann", reviewer="Bob")
     + "3\n2\n1\n3\n4\n",
     MENU + ADD + "Task [1] has been added.\n"
     + MENU + ADD + "Task [2] has been added.\n"
     + MENU + TABLE + HEADER
     + "1   Morning             Review      29-02-2016    8.0-8.5     Dev         Lead\n"
     "2   Evening             Design      31-12-2015    17.0-17.5   Ann         Bob\n"
     + MENU + DEL + "Task [1] has been deleted.\n"
     + MENU + TABLE + HEADER
     + "2   Evening             Design      31-12-2015    17.0-17.5   Ann         Bob\n"
     + MENU + "Goodbye."),
]
