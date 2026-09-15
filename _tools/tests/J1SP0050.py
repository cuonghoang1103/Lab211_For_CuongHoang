"""Extra keystroke runs for J1.S.P0050 (equations, odd/even/square numbers).

The reference runs are kept (REPLACE_REFERENCE is False): they reproduce the
brief's two screens exactly. This run adds what they miss: both menu errors
("Please choose from 1 to 3." and "Please input number"), a non-number at
B and at C, a quadratic with two different roots, a quadratic with a = 0 that
is true for every x, and a negative coefficient that must NOT be listed as a
perfect square. Expected screen written from the brief's rules.
"""

MENU = ("========= Equation Program =========\n"
        "1. Calculate Superlative Equation\n"
        "2. Calculate Quadratic Equation\n"
        "3. Exit\n"
        "Please choice one option: ")

RUNS = [
    ("9\nx\n2\n1\nq\n-5\nz\n6\n"
     "2\n0\n0\n0\n"
     "1\n-4\n8\n"
     "3\n",
     MENU + "Please choose from 1 to 3.\n"
     "Please choice one option: Please input number\n"
     "Please choice one option: ----- Calculate Quadratic Equation -----\n"
     "Enter A: Enter B: Please input number\n"
     "Enter B: Enter C: Please input number\n"
     "Enter C: Solution: x1 = 3.000 and x2 = 2.000\n"
     "Odd Number(s):1.0, -5.0, 3.0\n"
     "Number is Even:6.0, 2.0\n"
     "Number is Perfect Square:1.0\n"
     + MENU + "----- Calculate Quadratic Equation -----\n"
     "Enter A: Enter B: Enter C: The equation has infinitely many solutions.\n"
     "Odd Number(s):\n"
     "Number is Even:0.0, 0.0, 0.0\n"
     "Number is Perfect Square:0.0, 0.0, 0.0\n"
     + MENU + "----- Calculate Equation -----\n"
     "Enter A: Enter B: Solution: x = 2.000\n"
     "Number is Odd:\n"
     "Number is Even:-4.0, 8.0, 2.0\n"
     "Number is Perfect Square:\n"
     + MENU + "Goodbye."),
]
