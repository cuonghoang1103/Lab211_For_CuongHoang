"""Extra keystroke runs for J1.S.P0011 (Change base number system).

The brief gives no screen, only the four worked examples (535 DEC = 217 HEX,
217 HEX = 535 DEC, 27 DEC = 11011 BIN, 11011 BIN = 27 DEC). The reference
transcript shows exactly those four results, so it is kept; these runs add
the base pairs, validation messages and edge cases it misses. The expected
values are the brief's examples or computed here with Python's int(text, b)
/ format(), independent of the Java code.
"""

MENU = ("======= CHANGE BASE NUMBER SYSTEM =======\n"
        "1. Binary (base 2)\n"
        "2. Decimal (base 10)\n"
        "3. Hexadecimal (base 16)\n"
        "0. Exit\n"
        "=========================================\n")
IN = "Choose the INPUT base: "
OUT = "Choose the OUTPUT base: "
VAL = "Enter the input value: "
NAME = {1: "BIN", 2: "DEC", 3: "HEX"}
RADIX = {1: 2, 2: 10, 3: 16}


def write(n, radix):
    """Python oracle: an int written in a base, upper-case, with sign."""
    if n == 0:
        return "0"
    digits = "0123456789ABCDEF"
    s = ""
    m = abs(n)
    while m:
        s = digits[m % radix] + s
        m //= radix
    return ("-" if n < 0 else "") + s


def ok(i, o, text):
    """(stdin, screen) for one successful conversion."""
    n = int(text, RADIX[i])
    return ("%d\n%d\n%s\n" % (i, o, text),
            MENU + IN + OUT + VAL + "%s (%s) = %s (%s)\n" % (
                text, NAME[i], write(n, RADIX[o]), NAME[o]))


def err(i, o, text, line):
    return ("%d\n%d\n%s\n" % (i, o, text), MENU + IN + OUT + VAL + line + "\n")


def run(*steps):
    stdin = "".join(s[0] for s in steps) + "0\n"
    return (stdin, "".join(s[1] for s in steps) + MENU + IN + "Goodbye.")


BIG = "9223372036854775807"   # the largest long

RUNS = [
    # the four pairs the reference misses: BIN<->HEX, and same-base
    run(ok(1, 3, "11011"), ok(3, 1, "1B"), ok(1, 1, "101"), ok(3, 3, "0"),
        ok(3, 2, "-1A"), ok(2, 1, "+15")),
    # output-base validation (letter, out of range, 0 is not a base here),
    # then an empty value, then a real conversion
    ("2\nx\n5\n0\n3\n\n255\n0\n",
     MENU + IN + OUT + "You must input a number.\n"
     + OUT + "Please choose from 1 to 3.\n"
     + OUT + "Please choose from 1 to 3.\n"
     + OUT + VAL + "You must input something.\n"
     + VAL + "255 (DEC) = FF (HEX)\n" + MENU + IN + "Goodbye."),
    # invalid digits: a sign alone, a letter in decimal, a 2 in binary
    run(err(2, 1, "-", "- is not a valid DEC number."),
        err(2, 3, "12a", "12a is not a valid DEC number."),
        err(1, 2, "1012", "1012 is not a valid BIN number.")),
    # limits: the largest long works; one more, and 2^63 in binary, are
    # refused; a long run of leading zeros is not "too big"
    run(ok(2, 3, BIG),
        err(2, 3, "9223372036854775808", "The value is too big for this program."),
        err(1, 2, "1" + "0" * 63, "The value is too big for this program."),
        ok(1, 2, "0" * 80 + "1")),
]
