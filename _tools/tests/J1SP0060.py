"""Keystroke runs for J1.S.P0060 (total of bills vs wallet).

REPLACE_REFERENCE: the reference transcript prints "You can't buy it." with a
straight apostrophe, while the brief's screen writes "You can’t buy it." with
a typographic one (U+2019). The brief wins, so the reference runs are
re-written here with the brief's character and only these runs count.

The brief shows no error message for bad input; the two messages below are
the reference program's own ("You must input a number." and the range line),
kept word for word.
"""

REPLACE_REFERENCE = True

TITLE = "======= Shopping program ==========\n"
N = "input number of bill:"
W = "input value of wallet:"


def bill(i):
    return "input value of bill %d:" % i


NUMBER = "You must input a number.\n"


def rng(lo, hi):
    return "Value must be between %d and %d.\n" % (lo, hi)


CAN = "You can buy it."
CANT = "You can’t buy it."

RUNS = [
    # the brief's first screen: 100 + 200 = 300 <= 500
    ("2\n100\n200\n500\n",
     TITLE + N + bill(1) + bill(2) + W
     + "this is total of bill:300\n" + CAN),
    # the brief's second screen: 200 + 200 = 400 > 200
    ("2\n200\n200\n200\n",
     TITLE + N + bill(1) + bill(2) + W
     + "this is total of bill:400\n" + CANT),
    # reference run: letters for a bill are refused, the same bill is asked again
    ("2\nabc\n100\n200\n500\n",
     TITLE + N + bill(1) + NUMBER + bill(1) + bill(2) + W
     + "this is total of bill:300\n" + CAN),
    # every validation message on every field; wallet == total still pays (>=)
    ("abc\n\n0\n101\n3.5\n1\n0\n10000001\nx\n100\n-1\n1000000001\ny\n100\n",
     TITLE
     + N + NUMBER + N + NUMBER + N + rng(1, 100) + N + rng(1, 100) + N + NUMBER + N
     + bill(1) + rng(1, 10000000) + bill(1) + rng(1, 10000000) + bill(1) + NUMBER
     + bill(1)
     + W + rng(0, 1000000000) + W + rng(0, 1000000000) + W + NUMBER + W
     + "this is total of bill:100\n" + CAN),
    # an empty wallet (0 is legal) cannot pay; three bills are summed
    ("3\n1\n2\n3\n0\n",
     TITLE + N + bill(1) + bill(2) + bill(3) + W
     + "this is total of bill:6\n" + CANT),
    # biggest legal input: 100 bills of 10,000,000 = 1,000,000,000, no overflow
    ("100\n" + "10000000\n" * 100 + "1000000000\n",
     TITLE + N + "".join(bill(i) for i in range(1, 101)) + W
     + "this is total of bill:1000000000\n" + CAN),
]
