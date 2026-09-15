"""Extra keystroke runs for J1.S.P0065 (student classification).

The reference runs are kept (REPLACE_REFERENCE is False): they reproduce the
brief's screen exactly and already show all nine mark messages
("Maths/Chemistry/Physics is less than equal ten / greater than equal zero /
is digit"). This run adds what they miss: the bounds 0 and 6 and 4 (D, B, C
exactly on the edge), rounding BEFORE classifying (3.96 -> AVG 4.0 -> C;
7.5/7.5/7.6 -> AVG 7.5 -> B), a letter at a mark, lower-case y/n answers,
and percentages that need rounding (1/6 -> 16.7%, 2/6 -> 33.3%).
Expected screen written from the brief's rules, not captured.
"""

PROMPTS = "Name:Classes:Maths:Chemistry:Physics:"
MORE = "Do you want to enter more student information?(Y/N):"


def block(i, name, classes, avg, kind):
    return ("------ Student%d Info ------\nName:%s\nClasses:%s\nAVG:%s\nType:%s\n"
            % (i, name, classes, avg, kind))


RUNS = [
    ("A1\nFU1\n0\n0\n0\ny\n"
     "B1\nFU1\n6\n6\n6\nY\n"
     "C1\nFU2\n4\n4\n4\ny\n"
     "C2\nFU2\n3.96\n3.96\n3.96\nY\n"
     "X\nFU3\nabc\n10\n10\n9\ny\n"
     "Y\nFU3\n7.5\n7.5\n7.6\nn\n",
     "====== Management Student Program ======\n"
     + (PROMPTS + MORE) * 4
     + "Name:Classes:Maths:Maths is digit\nMaths:Chemistry:Physics:" + MORE
     + PROMPTS + MORE + "\n"
     + block(1, "A1", "FU1", "0.0", "D")
     + block(2, "B1", "FU1", "6.0", "B")
     + block(3, "C1", "FU2", "4.0", "C")
     + block(4, "C2", "FU2", "4.0", "C")
     + block(5, "X", "FU3", "9.7", "A")
     + block(6, "Y", "FU3", "7.5", "B")
     + "--------Classification Info -----\n"
     "A: 16.7%\nB: 33.3%\nC: 33.3%\nD: 16.7%"),
]
