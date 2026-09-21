"""Keystroke runs for J1.S.P0068 (collection sort of students).

REPLACE_REFERENCE (21/09/2026): the brief's screen writes the line before
each student as "Please input student information " - WITH a space at the
end (the .docx text box keeps it: xml:space="preserve"). The reference
transcript printed it without that space, so the reference runs no longer
match the brief character by character; they are copied below with the
space and only these runs count.

Expected screens are written from the brief, not captured from the program.
"""

TITLE = "====== Collection Sort Program ======\n"
# the brief's line keeps its trailing space
INFO = "Please input student information \n"
ASK = "Do you want to enter more student information?(Y/N):"
RANGE = "Mark must be between 0.0 and 100.0.\n"
NUMBER = "You must input a number.\n"


def block(i, name, classes, mark):
    return ("-------------Student %d-------------\nName: %s\nClasses: %s\nMark: %s\n"
            % (i, name, classes, mark))


REPLACE_REFERENCE = True

RUNS = [
    # the reference run = the brief's own screen: Nghia, Lien -> Lien first
    ("Nghia\nFU1\n100\nY\nLien\nFU1\n100\nN\n",
     TITLE + INFO + "Name: Classes: Mark: " + ASK
     + INFO + "Name: Classes: Mark: " + ASK
     + block(1, "Lien", "FU1", "100.0") + block(2, "Nghia", "FU1", "100.0")),

    # the reference run with a wrong mark, then one out of range
    ("Nghia\nFU1\nabc\n200\n90\nN\n",
     TITLE + INFO + "Name: Classes: Mark: " + NUMBER + "Mark: " + RANGE
     + "Mark: " + ASK + block(1, "Nghia", "FU1", "90.0")),

    # every validation message: blank name, blank class, letters, negative,
    # too big, NaN; a wrong Y/N answer, lower-case y and n.
    # Names "Nghia", "an", "Binh": A-Z ignoring case gives an, Binh, Nghia
    # (a plain compareTo would put "an" last).
    ("\nNghia\n\nFU1\nabc\n-5\n101\nNaN\n7.5\nmaybe\ny\nan\nFU2\n8.25\ny\nBinh\nFU1\n0\nn\n",
     TITLE + INFO + "Name: Name must not be empty.\n"
     + "Name: Classes: Class must not be empty.\n"
     + "Classes: Mark: " + NUMBER + "Mark: " + RANGE + "Mark: " + RANGE
     + "Mark: " + NUMBER + "Mark: " + ASK + "Please answer Y or N.\n"
     + ASK + INFO + "Name: Classes: Mark: " + ASK
     + INFO + "Name: Classes: Mark: " + ASK
     + block(1, "an", "FU2", "8.25") + block(2, "Binh", "FU1", "0.0")
     + block(3, "Nghia", "FU1", "7.5")),

    # spaces around a name are trimmed; mark 100 is the upper bound; two
    # students with the same name keep the order they were typed (stable sort)
    ("Minh\nFU2\n6\nY\n  Lan  \nFU1\n100\nY\nMinh\nFU1\n5\nN\n",
     TITLE + INFO + "Name: Classes: Mark: " + ASK
     + INFO + "Name: Classes: Mark: " + ASK
     + INFO + "Name: Classes: Mark: " + ASK
     + block(1, "Lan", "FU1", "100.0") + block(2, "Minh", "FU2", "6.0")
     + block(3, "Minh", "FU1", "5.0")),
]
