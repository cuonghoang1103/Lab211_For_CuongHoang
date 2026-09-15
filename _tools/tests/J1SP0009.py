"""Keystroke runs for J1.S.P0009 (Fibonacci, recursion).

REPLACE_REFERENCE: the reference transcript printed "===== Fibonacci Program
=====" and "The first 45 Fibonacci numbers:" while the brief's screen shows
the title "The 45 sequence fibonacci:" followed by "0, 1, 1, 2, 3, ..." -
the brief wins, so only this run counts.

The brief's picture is cut at the right edge ("..., 987, 1597,"), so it does
not show how the line ends; the program ends with the 45th number
701408733 and no trailing comma (HUONG-DAN section 9).

The expected text is built here from the definition F(0)=0, F(1)=1,
F(n)=F(n-1)+F(n-2), not copied from the program. The program reads nothing,
so stdin is empty. verify.py's 90-second timeout also proves the recursion
is not the plain exponential one (5.9 billion calls).
"""

REPLACE_REFERENCE = True


def fibonacci_numbers(count):
    """F(0) .. F(count - 1)."""
    numbers = [0, 1]
    while len(numbers) < count:
        numbers.append(numbers[-1] + numbers[-2])
    return numbers[:count]


NUMBERS = fibonacci_numbers(45)
assert NUMBERS[-1] == 701408733

RUNS = [
    ("", "The 45 sequence fibonacci:\n" + ", ".join(str(x) for x in NUMBERS)),
]
