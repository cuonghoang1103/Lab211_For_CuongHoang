"""Keystroke runs for J1.S.P0071 (Task management).

REPLACE_REFERENCE (21/09/2026): the brief's table shows the Time column as ONE
number - "8.0" for the task of its Add screen, From 9.5 To 17.5 - i.e. the hours
of the task (Plan To - Plan From). The reference transcript printed "9.5-17.5",
so its two runs are copied below (runs 1 and 2) with the Time column the brief
shows; every other character of them is unchanged. Runs 3 and 4 are the extra
runs that reach the messages the reference runs miss.

Expected screens are written from the brief (menu, titles, prompts, table
header) and the reference program's wording for the error lines (the brief
names the rules but not their wording). Each add screen prints all seven
prompts, then the first failing rule, because the brief's addTask receives
the seven Strings and throws.
"""

REPLACE_REFERENCE = True

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
NO_TASK = TABLE + "There is no task yet.\n"


def add(name="Dev Program", typ="1", date="26-06-2015", frm="9.5", to="17.5",
        assignee="Dev", reviewer="Lead"):
    """Keystrokes for option 1 with the values in screen order."""
    return "1\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n" % (name, typ, date, frm, to, assignee, reviewer)


def row(task_id, name, typ, date, hours, assignee, reviewer):
    """One table row in the reference program's widths; hours = Plan To - Plan From."""
    return "%-4d%-20s%-12s%-14s%-12s%-12s%s\n" % (task_id, name, typ, date, hours,
                                                 assignee, reviewer)


RUNS = [
    # reference run 1: add 2 tasks, show, delete ID 2, add (gets ID 3), show
    (add()
     + add(name="Test Program", typ="2", date="28-08-2015", frm="8.0", to="9.0",
           assignee="Tester")
     + "3\n2\n2\n"
     + add(name="Design DB", typ="3", date="01-09-2015", frm="13.0", to="17.5",
           assignee="Architect")
     + "3\n4\n",
     MENU + ADD + "Task [1] has been added.\n"
     + MENU + ADD + "Task [2] has been added.\n"
     + MENU + TABLE + HEADER
     + row(1, "Dev Program", "Code", "26-06-2015", "8.0", "Dev", "Lead")
     + row(2, "Test Program", "Test", "28-08-2015", "1.0", "Tester", "Lead")
     + MENU + DEL + "Task [2] has been deleted.\n"
     + MENU + ADD + "Task [3] has been added.\n"
     + MENU + TABLE + HEADER
     + row(1, "Dev Program", "Code", "26-06-2015", "8.0", "Dev", "Lead")
     + row(3, "Design DB", "Design", "01-09-2015", "4.5", "Architect", "Lead")
     + MENU + "Goodbye."),
    # reference run 2: empty table, one add per broken rule, unknown and bad IDs
    ("3\n"
     + add(name="No Type", typ="9")
     + add(name="Not A Number", typ="Code")
     + add(name="Fake Date", date="31-02-2003")
     + add(name="Sloppy Date", date="1-2-2015")
     + add(name="Off Grid", frm="9.7")
     + add(name="After Hours", to="18.0")
     + add(name="Backwards", frm="17.5", to="9.5")
     + add(name="")
     + "2\n99\n2\nabc\n3\n4\n",
     MENU + NO_TASK
     + MENU + ADD + "Task Type [9] does not exist. It must be 1 to 4.\n"
     + MENU + ADD + "Task Type must be a number.\n"
     + MENU + ADD + "Date must be a real date in the format dd-MM-yyyy.\n"
     + MENU + ADD + "Date must be a real date in the format dd-MM-yyyy.\n"
     + MENU + ADD + "Plan From must be a whole or half hour: 8.0, 8.5, 9.0 ... 17.5.\n"
     + MENU + ADD + "Plan To must be between 8.0 and 17.5.\n"
     + MENU + ADD + "Plan From must be less than Plan To.\n"
     + MENU + ADD + "Requirement Name cannot be empty.\n"
     + MENU + DEL + "Task [99] does not exist.\n"
     + MENU + DEL + "ID must be a number.\n"
     + MENU + NO_TASK
     + MENU + "Goodbye."),
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
    # happy path at the edges: 8.0-8.5 and 17.0-17.5 (half an hour each), leap
    # day 29-02-2016, type 4; delete the first task, the table keeps ascending IDs
    (add(name="Morning", typ="4", date="29-02-2016", frm="8.0", to="8.5")
     + add(name="Evening", typ="3", date="31-12-2015", frm="17", to="17.5",
           assignee="Ann", reviewer="Bob")
     + "3\n2\n1\n3\n4\n",
     MENU + ADD + "Task [1] has been added.\n"
     + MENU + ADD + "Task [2] has been added.\n"
     + MENU + TABLE + HEADER
     + row(1, "Morning", "Review", "29-02-2016", "0.5", "Dev", "Lead")
     + row(2, "Evening", "Design", "31-12-2015", "0.5", "Ann", "Bob")
     + MENU + DEL + "Task [1] has been deleted.\n"
     + MENU + TABLE + HEADER
     + row(2, "Evening", "Design", "31-12-2015", "0.5", "Ann", "Bob")
     + MENU + "Goodbye."),
]
