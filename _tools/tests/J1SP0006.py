"""Keystroke runs for J1.S.P0006 (binary search).

REPLACE_REFERENCE: the reference transcript draws numbers from 1..n and adds
lines the brief's screen does not have ("... appears 2 time(s) ...",
"Binary search used 3 comparison(s); a linear scan would have used ...").
The brief's screen is exactly:

    Enter number of array:
    10
    Enter search value:
    4
    Sorted array: [1, 1, 1, 1, 3, 4, 6, 8, 9, 9]
    Found 4 at index: 5

so the brief wins and only these runs count. The reference run's own
keystrokes ("abc\\n0\\n10\\n4\\n") are kept as one of the runs below.

The brief shows no "absent" line; the program prints "<v> is not in the
array." (the reference solution's wording) - see HUONG-DAN section 9.

The array is random, so each check asserts RELATIONSHIPS: the right count,
every value in [0, n) ("random integer in number range input", like
P0001), the array printed sorted, and the reported index being EXACTLY the
index the brief's algorithm reaches (middle = low + (high - low) / 2) - so a
linear search, or a search that returns the first duplicate, fails the
check. Prompts and error lines are fixed text and compared exactly.
"""
import re

REPLACE_REFERENCE = True

SIZE_PROMPT = "Enter number of array:"
SEARCH_PROMPT = "Enter search value:"
RANGE = "Number must be between 1 and 1000."
NUMBER = "You must input a number."


def binary_search(array, value):
    """The brief's algorithm, iterative, the same middle as the Java code."""
    low, high = 0, len(array) - 1
    while low <= high:
        middle = low + (high - low) // 2
        if array[middle] == value:
            return middle
        if value < array[middle]:
            high = middle - 1
        else:
            low = middle + 1
    return -1


def screen(n, value, size_errors=(), search_errors=(), expect=None):
    """Predicate for one run.

    size_errors / search_errors: error lines expected, in order, each followed
    by the same prompt again. expect: None (either outcome is legal for a
    random array), "found" or "absent" (forced by the chosen value)."""
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
        m = re.fullmatch(r"Sorted array: \[(.*)\]", rest[0])
        if not m:
            return False, "first result line not in the brief's format: %r" % rest[0]
        array = [int(x) for x in m.group(1).split(", ")]
        if len(array) != n:
            return False, "expected %d numbers, got %d" % (n, len(array))
        if any(v < 0 or v >= n for v in array):
            return False, "a number is outside [0, %d)" % n
        if array != sorted(array):
            return False, "the 'Sorted array' line is not sorted"
        index = binary_search(array, value)
        if index >= 0:
            line = "Found %d at index: %d" % (value, index)
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
    # the brief's own screen: 10 numbers, search 4
    ("10\n4\n", screen(10, 4)),
    # one element: the array is [0], 0 is found at index 0 in one step
    ("1\n0\n", screen(1, 0, expect="found")),
    # a value bigger than every element (numbers are < 10): absent
    ("10\n10\n", screen(10, 10, expect="absent")),
    # a negative value, smaller than every element: absent
    ("10\n-1\n", screen(10, -1, expect="absent")),
    # biggest legal array: at most 10 halvings
    ("1000\n500\n", screen(1000, 500)),
    # the reference solution's own keystrokes
    ("abc\n0\n10\n4\n", screen(10, 4, [NUMBER, RANGE])),
]
