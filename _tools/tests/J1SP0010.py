"""Keystroke runs for J1.S.P0010 (linear search).

REPLACE_REFERENCE: the reference transcript printed a title
"===== Linear Search Program =====", the prompts "Please input the number of
array: " / "Please input the search number: ", the label "Array: ", printed
the array BEFORE asking for the value, and wrote "Number 7 found at index 3.".
The brief's screen is exactly:

    Enter number of array:
    10
    Enter search value:
    5
    The array: [2, 2, 5, 2, 6, 9, 9, 8, 9, 8]
    Found 5 at index: 2

so the brief wins and only these runs count. The reference keystrokes
("12\\n7\\n", "12\\n999\\n") are kept as runs below.

The brief shows no "absent" line; the program prints "<v> is not in the
array." - the same wording as P0006 (see HUONG-DAN section 9).

The array is random, so each check asserts RELATIONSHIPS: the right count,
every value in [0, n), and the reported index being the FIRST position of
the value (what a left-to-right linear search returns). Prompts and error
lines are fixed text and compared exactly.
"""
import re

REPLACE_REFERENCE = True

SIZE_PROMPT = "Enter number of array:"
SEARCH_PROMPT = "Enter search value:"
RANGE = "Number must be between 1 and 1000."
NUMBER = "You must input a number."


def screen(n, value, size_errors=(), search_errors=(), expect=None):
    """Predicate for one run (same shape as J1SP0006.screen)."""
    def check(out):
        lines = out.split("\n")
        want = [SIZE_PROMPT]
        for e in size_errors:
            want += [e, SIZE_PROMPT]
        want.append(SEARCH_PROMPT)
        for e in search_errors:
            want += [e, SEARCH_PROMPT]
        if lines[:len(want)] != want:
            return False, "prompt/error lines differ: %r" % lines[:len(want)]
        rest = lines[len(want):]
        if len(rest) != 2:
            return False, "expected exactly 2 result lines, got %r" % rest
        m = re.fullmatch(r"The array: \[(.*)\]", rest[0])
        if not m:
            return False, "first result line not in the brief's format: %r" % rest[0]
        array = [int(x) for x in m.group(1).split(", ")]
        if len(array) != n:
            return False, "expected %d numbers, got %d" % (n, len(array))
        if any(v < 0 or v >= n for v in array):
            return False, "a number is outside [0, %d)" % n
        if value in array:
            line = "Found %d at index: %d" % (value, array.index(value))
            if expect == "absent":
                return False, "value %d should be impossible in [0, %d)" % (value, n)
        else:
            line = "%d is not in the array." % value
            if expect == "found":
                return False, "value %d should be in the array" % value
        if rest[1] != line:
            return False, "last line %r, expected %r" % (rest[1], line)
        return True, ""
    return check


RUNS = [
    # every size message (letters, empty, negative, zero, too big), then
    # every search-value message (letters, decimal, empty, too big for int)
    ("abc\n\n-5\n0\n1001\n8\nx\n3.5\n\n99999999999\n3\n",
     screen(8, 3, [NUMBER, NUMBER, RANGE, RANGE, RANGE],
            [NUMBER, NUMBER, NUMBER, NUMBER])),
    # the brief's own screen: 10 numbers, search 5
    ("10\n5\n", screen(10, 5)),
    # one element: the array is [0], 0 is found at index 0
    ("1\n0\n", screen(1, 0, expect="found")),
    # a value no element can have (numbers are < 10): absent
    ("10\n10\n", screen(10, 10, expect="absent")),
    # a negative value: absent
    ("10\n-1\n", screen(10, -1, expect="absent")),
    # biggest legal array
    ("1000\n500\n", screen(1000, 500)),
    # the reference solution's own keystrokes
    ("12\n7\n", screen(12, 7)),
    ("12\n999\n", screen(12, 999, expect="absent")),
]
