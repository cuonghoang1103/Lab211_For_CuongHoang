"""Keystroke runs for J1.S.P0007 (undirected graph, adjacency matrix).

REPLACE_REFERENCE: the reference program prints the whole matrix and the
neighbours of both points BEFORE / after the prompts, and "This is  not an
edge". The brief's screen (image 1) is only:
    Enter the start point: / 2 / Enter the end point: / 5 / This is  an edge
so the brief wins and only these runs count.

The expected answers are written from the brief's FIGURE, not from the
program: edges 1-4, 2-4, 2-5, 3-5, 4-5, undirected (the matrix is symmetric),
no loops (the diagonal is 0). Every one of the 25 ordered pairs is checked.
"""

REPLACE_REFERENCE = True

START = "Enter the start point:\n"
END = "Enter the end point:\n"
IS = "This is  an edge"
NOT = "This is not an edge"
NUMBER = "You must input a number.\n"
RANGE = "Value must be between 1 and 5.\n"

FIGURE = {(1, 4), (2, 4), (2, 5), (3, 5), (4, 5)}


def answer(a, b):
    """The figure's answer for the pair (a, b), in either direction."""
    return IS if (a, b) in FIGURE or (b, a) in FIGURE else NOT


RUNS = [
    # every validation message on the start point: letters, blank, decimal,
    # zero, too big, negative - then a legal pair (the brief's own 2, 5)
    ("two\n\n2.5\n0\n6\n-1\n2\n5\n",
     START + NUMBER + START + NUMBER + START + NUMBER + START + RANGE
     + START + RANGE + START + RANGE + START + END + IS),
    # the same messages on the end point
    ("1\nabc\n9\n3\n",
     START + END + NUMBER + END + RANGE + END + NOT),
]

# all 25 ordered pairs, answers taken from the figure
for a in range(1, 6):
    for b in range(1, 6):
        RUNS.append(("%d\n%d\n" % (a, b), START + END + answer(a, b)))
