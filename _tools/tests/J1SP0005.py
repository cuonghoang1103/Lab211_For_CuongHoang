"""Keystroke runs for J1.S.P0005 (merge sort).

REPLACE_REFERENCE: the reference transcript printed a title line
"===== Merge Sort Program =====" and the prompt "Please input the number of array: "
while the brief's screen (the same picture as J1.S.P0001) shows only
"Enter number of array:" on its own line - the brief wins, so only these
runs count. The reference run's own keystrokes are kept as the last run.

The array is random, so each check asserts RELATIONSHIPS instead of fixed
numbers: the right count, every value in [0, n) (the brief's "random integer
in number range input": n = 10 gives 0..9), and the sorted line being exactly
the unsorted line in ascending order. The prompts and error lines ARE fixed
text and are compared exactly.
"""
import re

REPLACE_REFERENCE = True


def screen(n, errors):
    """Predicate for one run: `errors` are the error lines expected, in order,
    each followed by the prompt again; n is the size finally accepted."""
    def check(out):
        lines = out.split("\n")
        want = ["Enter number of array:"]
        for e in errors:
            want += [e, "Enter number of array:"]
        if lines[:len(want)] != want:
            return False, "prompt/error lines differ: %r" % lines[:len(want)]
        rest = lines[len(want):]
        if len(rest) != 2:
            return False, "expected exactly 2 result lines, got %r" % rest
        mu = re.fullmatch(r"Unsorted array: \[(.*)\]", rest[0])
        ms = re.fullmatch(r"Sorted array: \[(.*)\]", rest[1])
        if not mu or not ms:
            return False, "result lines not in the brief's format"
        before = [int(x) for x in mu.group(1).split(", ")]
        after = [int(x) for x in ms.group(1).split(", ")]
        if len(before) != n:
            return False, "expected %d numbers, got %d" % (n, len(before))
        if any(v < 0 or v >= n for v in before):
            return False, "a number is outside [0, %d)" % n
        if after != sorted(before):
            return False, "sorted line is not the unsorted line in order"
        return True, ""
    return check


RANGE = "Number must be between 1 and 1000."
NUMBER = "You must input a number."

RUNS = [
    # every validation message: letters, empty, negative, zero, too big
    ("abc\n\n-5\n0\n1001\n8\n", screen(8, [NUMBER, NUMBER, RANGE, RANGE, RANGE])),
    # the brief's own example size
    ("10\n", screen(10, [])),
    # smallest legal array: one element is already sorted
    ("1\n", screen(1, [])),
    # two elements: the smallest array the merge sort really has to work on
    ("2\n", screen(2, [])),
    # biggest legal array, full of duplicates (1000 values drawn from 0..999)
    ("1000\n", screen(1000, [])),
    # decimal number is not an integer
    ("3.5\n4\n", screen(4, [NUMBER])),
    # the reference solution's own keystrokes
    ("xyz\n1001\n150\n", screen(150, [NUMBER, RANGE])),
]
