"""Keystroke runs for J1.S.P0065 (student classification).

REPLACE_REFERENCE (21/09/2026): on the brief's screen the first result line
"------ Student1 Info ------" comes RIGHT AFTER "...(Y/N):N" - no blank line
between them. The "N" + Enter the user types already ends that line, so the
program must not print a line break of its own. The reference transcript
expected one (the old Main called System.out.println() before the results),
which on a real console is a blank line the brief does not have. The four
reference runs are copied below without that line break; only these runs
count. The captured output shows "(Y/N):------ Student1 Info ------" because
the typed "N" is not echoed into stdout - same as P0068.

The last run adds what the reference misses: the bounds 0 and 6 and 4 (D, B,
C exactly on the edge), rounding BEFORE classifying (3.96 -> AVG 4.0 -> C;
7.5/7.5/7.6 -> AVG 7.5 -> B), a letter at a mark, lower-case y/n answers,
and percentages that need rounding (1/6 -> 16.7%, 2/6 -> 33.3%).
Expected screens written from the brief's rules, not captured.
"""

PROMPTS = "Name:Classes:Maths:Chemistry:Physics:"
MORE = "Do you want to enter more student information?(Y/N):"


def block(i, name, classes, avg, kind):
    return ("------ Student%d Info ------\nName:%s\nClasses:%s\nAVG:%s\nType:%s\n"
            % (i, name, classes, avg, kind))


REPLACE_REFERENCE = True

RUNS = [
    # reference run 1: the brief's own screen: all nine mark messages, then a second student
    ('Nghia\nFU1\n11\n-1\n\n10\n11\n-1\n\n10\n11\n-1\n\n10\nY\nNghia 2\nFU1\n10\n10\n10\nN\n',
     '====== Management Student Program ======\nName:Classes:Maths:Maths is less than equal ten\nMaths:Maths is greater than equal zero\nMaths:Maths is digit\nMaths:Chemistry:Chemistry is less than equal ten\nChemistry:Chemistry is greater than equal zero\nChemistry:Chemistry is digit\nChemistry:Physics:Physics is less than equal ten\nPhysics:Physics is greater than equal zero\nPhysics:Physics is digit\nPhysics:Do you want to enter more student information?(Y/N):Name:Classes:Maths:Chemistry:Physics:Do you want to enter more student information?(Y/N):------ Student1 Info ------\nName:Nghia\nClasses:FU1\nAVG:10.0\nType:A\n------ Student2 Info ------\nName:Nghia 2\nClasses:FU1\nAVG:10.0\nType:A\n--------Classification Info -----\nA: 100.0%\nB: 0.0%\nC: 0.0%\nD: 0.0%'),
    # reference run 2: one student of each type, and a wrong Y/N answer asked again silently
    ('An\nFU1\n10\n10\n10\nY\nBinh\nFU2\n7\n7\n7\nY\nChi\nFU3\n5\n5\n5\nY\nDung\nFU4\n3\n3\n3\nmaybe\nN\n',
     '====== Management Student Program ======\nName:Classes:Maths:Chemistry:Physics:Do you want to enter more student information?(Y/N):Name:Classes:Maths:Chemistry:Physics:Do you want to enter more student information?(Y/N):Name:Classes:Maths:Chemistry:Physics:Do you want to enter more student information?(Y/N):Name:Classes:Maths:Chemistry:Physics:Do you want to enter more student information?(Y/N):Do you want to enter more student information?(Y/N):------ Student1 Info ------\nName:An\nClasses:FU1\nAVG:10.0\nType:A\n------ Student2 Info ------\nName:Binh\nClasses:FU2\nAVG:7.0\nType:B\n------ Student3 Info ------\nName:Chi\nClasses:FU3\nAVG:5.0\nType:C\n------ Student4 Info ------\nName:Dung\nClasses:FU4\nAVG:3.0\nType:D\n--------Classification Info -----\nA: 25.0%\nB: 25.0%\nC: 25.0%\nD: 25.0%'),
    # reference run 3: 7.6 is A, 7.5 is B (not above 7.5), 3.9 is D
    ('Ha\nFU1\n7.6\n7.6\n7.6\nY\nKhanh\nFU1\n7.5\n7.5\n7.5\nY\nLinh\nFU1\n3.9\n3.9\n3.9\nN\n',
     '====== Management Student Program ======\nName:Classes:Maths:Chemistry:Physics:Do you want to enter more student information?(Y/N):Name:Classes:Maths:Chemistry:Physics:Do you want to enter more student information?(Y/N):Name:Classes:Maths:Chemistry:Physics:Do you want to enter more student information?(Y/N):------ Student1 Info ------\nName:Ha\nClasses:FU1\nAVG:7.6\nType:A\n------ Student2 Info ------\nName:Khanh\nClasses:FU1\nAVG:7.5\nType:B\n------ Student3 Info ------\nName:Linh\nClasses:FU1\nAVG:3.9\nType:D\n--------Classification Info -----\nA: 33.3%\nB: 33.3%\nC: 0.0%\nD: 33.3%'),
    # reference run 4: 7.4/7.6/7.6: the average 7.533 is rounded to 7.5 first, so B
    ('Mai\nFU1\n7.4\n7.6\n7.6\nN\n',
     '====== Management Student Program ======\nName:Classes:Maths:Chemistry:Physics:Do you want to enter more student information?(Y/N):------ Student1 Info ------\nName:Mai\nClasses:FU1\nAVG:7.5\nType:B\n--------Classification Info -----\nA: 0.0%\nB: 100.0%\nC: 0.0%\nD: 0.0%'),
    # extra run: the bounds, rounding before classifying, a letter, y/n
    ("A1\nFU1\n0\n0\n0\ny\n"
     "B1\nFU1\n6\n6\n6\nY\n"
     "C1\nFU2\n4\n4\n4\ny\n"
     "C2\nFU2\n3.96\n3.96\n3.96\nY\n"
     "X\nFU3\nabc\n10\n10\n9\ny\n"
     "Y\nFU3\n7.5\n7.5\n7.6\nn\n",
     "====== Management Student Program ======\n"
     + (PROMPTS + MORE) * 4
     + "Name:Classes:Maths:Maths is digit\nMaths:Chemistry:Physics:" + MORE
     + PROMPTS + MORE
     + block(1, "A1", "FU1", "0.0", "D")
     + block(2, "B1", "FU1", "6.0", "B")
     + block(3, "C1", "FU2", "4.0", "C")
     + block(4, "C2", "FU2", "4.0", "C")
     + block(5, "X", "FU3", "9.7", "A")
     + block(6, "Y", "FU3", "7.5", "B")
     + "--------Classification Info -----\n"
     "A: 16.7%\nB: 33.3%\nC: 33.3%\nD: 16.7%"),
]
