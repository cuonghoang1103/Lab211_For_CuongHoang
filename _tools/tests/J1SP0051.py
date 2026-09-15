"""Extra keystroke runs for J1.S.P0051 (Calculator + BMI).

The reference transcript matches the brief's screens (menu, "Please choice
one option: ", "Enter number: ", "Enter Operator: ", "Memory:8.0",
"Please input (+, -, *, /, ^)", "Result:24.0", "BMI is digit",
"BMI Number: 24.22", "BMI Status: STANDARD"), so its runs are kept; these add
the paths it misses. Expected text is written from the brief.
"""

MENU = ("========= Calculator Program =========\n"
        "1. Normal Calculator\n"
        "2. BMI Calculator\n"
        "3. Exit\n"
        "Please choice one option: ")
NORMAL = "----- Normal Calculator -----\n"
BMI = "----- BMI Calculator -----\n"
NUM = "Enter number: "
OP = "Enter Operator: "
W = "Enter Weight(kg): "
H = "Enter Height(cm): "
CHOICE_ERR = "Please input a number from 1 to 3.\n"


def bmi(weight_line_errors, number, status):
    """One BMI run with height 100 cm (1 m), so BMI == weight exactly."""
    return (BMI + (W + "BMI is digit\n") * weight_line_errors + W + H
            + "BMI Number: %s\nBMI Status: %s\n" % (number, status))


RUNS = [
    # 0. operators -, *, x (the Guidelines' spelling of *), a wrong number
    #    at start and after an operator, a wrong operator, then "="
    ("1\nabc\n10\n-\n4\n*\n2.5\nx\n2\n+\nq\n1\n%\n=\n3\n",
     MENU + NORMAL + NUM + "Number is digit\n" + NUM
     + OP + NUM + "Memory:6.0\n"
     + OP + NUM + "Memory:15.0\n"
     + OP + NUM + "Memory:30.0\n"
     + OP + NUM + "Number is digit\n" + NUM + "Memory:31.0\n"
     + OP + "Please input (+, -, *, /, ^)\n"
     + OP + "Result:31.0\n"
     + MENU),

    # 1. "=" right after the first number: the result is that number
    ("1\n7\n=\n3\n", MENU + NORMAL + NUM + OP + "Result:7.0\n" + MENU),

    # 2. menu validation: letters, 0, 4, empty line
    ("x\n0\n4\n\n3\n",
     MENU + CHOICE_ERR + "Please choice one option: " + CHOICE_ERR
     + "Please choice one option: " + CHOICE_ERR
     + "Please choice one option: " + CHOICE_ERR + "Please choice one option: "),

    # 3. BMI: weight 0 and -5 refused, height 0 refused; then every band at
    #    its boundary with height 100 cm (BMI = weight): 18.99, 19, 25, 30, 40
    ("2\n0\n-5\n18.99\n0\n100\n"
     "2\n19\n100\n2\n25\n100\n2\n30\n100\n2\n40\n100\n3\n",
     MENU + BMI + W + "BMI is digit\n" + W + "BMI is digit\n" + W
     + H + "BMI is digit\n" + H
     + "BMI Number: 18.99\nBMI Status: UNDER-STANDARD\n"
     + MENU + bmi(0, "19.00", "STANDARD")
     + MENU + bmi(0, "25.00", "OVERWEIGHT")
     + MENU + bmi(0, "30.00", "FAT - SHOULD LOSE WEIGHT")
     + MENU + bmi(0, "40.00", "VERY FAT - SHOULD LOSE WEIGHT IMMEDIATELY")
     + MENU),
]
