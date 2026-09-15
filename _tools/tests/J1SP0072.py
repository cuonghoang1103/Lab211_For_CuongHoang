"""Extra keystroke runs for J1.S.P0072 (login with MD5) - the messages and
paths the two reference runs do not reach.

Screens come from the brief (menu "3) Exit", "Please choice one option:",
Add User prompts without a space after the colon, Login prompts with one,
"Wellcome", "Hi <name>, do you want change password now? Y/N:", the three
change-password prompts). Each Add User screen prints all seven prompts,
then the first failing rule, because the brief's addAccount receives the
seven Strings and throws.
"""

MENU = ("============ Login Program =========\n"
        "1. Add User\n"
        "2. Login\n"
        "3) Exit\n"
        "Please choice one option:")
ADD = "---------- Add User --------\nAccount:Password:Name:Phone:Email:Address:DOB:"
LOGIN = "------------- Login ----------------\nAccount: Password: "


def add(user="NghiaNV", pw="nghia", name="SkyLine", phone="0988666888",
        email="nghianv@t.com", address="Ha Noi", dob="26/06/2016"):
    """Keystrokes for option 1 in screen order."""
    return "1\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n" % (user, pw, name, phone, email, address, dob)


def welcome(user, name):
    return ("------------ Wellcome -----------\nHello %s\n"
            "Hi %s, do you want change password now? Y/N:" % (user, name))


RUNS = [
    # menu validation and every add message the reference runs miss
    ("abc\n4\n"
     + add(name="")
     + add(phone="")
     + add(phone="098866688812")
     + add(email="")
     + add(email="nghianv@t")
     + add(dob="")
     + add(dob="26/06/2016xyz")
     + "3\n",
     "============ Login Program =========\n1. Add User\n2. Login\n3) Exit\n"
     "Please choice one option:You must input a number.\n"
     "Please choice one option:Please choose from 1 to 3.\n"
     "Please choice one option:" + ADD + "Name cannot be empty.\n"
     + MENU + ADD + "Phone number cannot be empty.\n"
     + MENU + ADD + "Phone number must be 10 or 11 number.\n"
     + MENU + ADD + "Email cannot be empty.\n"
     + MENU + ADD + "Email is not in the correct format.\n"
     + MENU + ADD + "Date of birth cannot be empty.\n"
     + MENU + ADD + "Date of birth must be a real date in the format dd/MM/yyyy.\n"
     + MENU + "Goodbye."),
    # 11-digit phone, empty address, leap-day DOB; login with a lower-case
    # "y"; blank new password; then a real change and the old password fails
    (add(user="an", pw="p@ss", name="An", phone="09886668881", address="",
         dob="29/02/2000")
     + "2\nan\np@ss\ny\np@ss\n\n\n"
     + "2\nAN\np@ss\nY\np@ss\nnew1\nnew1\n"
     + "2\nan\np@ss\n"
     + "2\nan\nnew1\nno\n"
     + "3\n",
     MENU + ADD + "Account [an] has been added with id 1.\n"
     + MENU + LOGIN + welcome("an", "An")
     + "Old password:new password:renew password:New password cannot be empty.\n"
     + MENU + LOGIN + welcome("an", "An")
     + "Old password:new password:renew password:Password has been changed.\n"
     + MENU + LOGIN + "Login fail.\n"
     + MENU + LOGIN + welcome("an", "An")
     + MENU + "Goodbye."),
    # a second account gets id 2; two users with the same password both work
    (add() + add(user="Other", pw="nghia", name="Other Name", email="o@t.com")
     + "2\nOther\nnghia\nN\n2\nNghiaNV\nnghia\nN\n3\n",
     MENU + ADD + "Account [NghiaNV] has been added with id 1.\n"
     + MENU + ADD + "Account [Other] has been added with id 2.\n"
     + MENU + LOGIN + welcome("Other", "Other Name")
     + MENU + LOGIN + welcome("NghiaNV", "SkyLine")
     + MENU + "Goodbye."),
]
