"""Extra keystroke runs for J1.S.P0084 (Large number).

The reference transcript matches the brief's screen ("Enter the first
number : ", "Enter the second number: ", "A x B = R"), so the reference runs
are kept and these are added. The expected products are computed here with
Python's own unbounded int - an oracle independent of the Java code.
"""

P1 = "Enter the first number : "
P2 = "Enter the second number: "
ERR = "You must input digit."


def screen(a, b, errors1=0, errors2=0):
    """Expected screen when a and b are finally accepted after the given
    number of refused lines for each prompt."""
    out = (P1 + ERR + "\n") * errors1 + P1
    out += (P2 + ERR + "\n") * errors2 + P2
    return out + "%d x %d = %d" % (int(a), int(b), int(a) * int(b))


BIG1 = "1234567890123456789012345678901234567890"
BIG2 = "98765432109876543210"
NINES = "9" * 20

RUNS = [
    # the brief's hand example: 123 x 45 = 5535
    ("123\n45\n", screen("123", "45")),
    # every way to be "not digits": empty, sign, decimal, space inside,
    # letters - then a valid pair far beyond long
    ("\n-5\n12.5\n1 2\nabc\n" + BIG1 + "\n" + BIG2 + "\n",
     screen(BIG1, BIG2, errors1=5)),
    # wrong second number too; 99..9 squared fills all m + n cells
    (NINES + "\nx9\n" + NINES + "\n", screen(NINES, NINES, errors2=1)),
    # zero input (the brief's Notes) and leading zeros on both sides
    ("000\n000123\n", screen("0", "123")),
    ("0\n0\n", screen("0", "0")),
    # one-digit numbers: the carry reaches the last cell
    ("9\n9\n", screen("9", "9")),
]
