"""Keystroke runs for J1.S.P0063 (input 3 persons, bubble sort by salary).

REPLACE_REFERENCE: the brief's result screen separates the three person
blocks with an EMPTY line; the reference transcript prints them back to back.
The brief wins, so only these runs count.

Message spelling: the brief's screen says "You must input digidt." but its
Guidelines say Exception("You must input digit.") - Guidelines win (the
reference agrees).

"You must input name." / "You must input address." are not in the brief; they
are the reference program's messages for a blank name/address, kept as is.
"Can't Sort Person" cannot be reached from the keyboard: main never hands the
service a missing person (see HUONG-DAN §7).
"""

REPLACE_REFERENCE = True

TITLE = "=====Management Person programer=====\n"
IN = "Input Information of Person\n"
NAME = "Please input name:"
ADDR = "Please input address:"
SAL = "Please input salary:"
DIGIT = "You must input digit.\n"
POSITIVE = "Salary is greater than zero\n"
EMPTY_SAL = "You must input Salary.\n"


def block(name, address, salary):
    return ("Information of Person you have entered:\n"
            "Name:%s\nAddress:%s\nSalary:%s\n\n" % (name, address, salary))


RUNS = [
    # the brief's own screen, key for key
    ("NghiaNV\nHa Noi\nabc\n-2000\n2000\n"
     "LienVT\nHa Noi\n500\n"
     "TuanNT\nHa Noi\n1000\n",
     TITLE
     + IN + NAME + ADDR + SAL + DIGIT + SAL + POSITIVE + SAL
     + IN + NAME + ADDR + SAL
     + IN + NAME + ADDR + SAL
     + block("LienVT", "Ha Noi", "500.0")
     + block("TuanNT", "Ha Noi", "1000.0")
     + block("NghiaNV", "Ha Noi", "2000.0")),
    # every salary message: blank, letters, scientific, zero, negative;
    # blank name and address are asked again; decimals and big numbers print
    # without scientific notation
    ("\nAn\n\nHa Noi\n\nabc\n1e3\nNaN\n0\n-5\n1500.75\n"
     "Binh\nHCM\n10000000\n"
     "Chi\nDa Nang\n12.5\n",
     TITLE
     + IN + NAME + "You must input name.\n" + NAME
     + ADDR + "You must input address.\n" + ADDR
     + SAL + EMPTY_SAL + SAL + DIGIT + SAL + DIGIT + SAL + DIGIT
     + SAL + POSITIVE + SAL + POSITIVE + SAL
     + IN + NAME + ADDR + SAL
     + IN + NAME + ADDR + SAL
     + block("Chi", "Da Nang", "12.5")
     + block("An", "Ha Noi", "1500.75")
     + block("Binh", "HCM", "10000000.0")),
    # already sorted input stays as is; equal salaries keep the typed order
    # (bubble sort swaps only on ">", so it is stable)
    ("A\nX\n100\nB\nY\n100\nC\nZ\n200\n",
     TITLE
     + IN + NAME + ADDR + SAL
     + IN + NAME + ADDR + SAL
     + IN + NAME + ADDR + SAL
     + block("A", "X", "100.0")
     + block("B", "Y", "100.0")
     + block("C", "Z", "200.0")),
    # reverse order: the worst case for bubble sort
    ("C\nZ\n3\nB\nY\n2\nA\nX\n1\n",
     TITLE
     + IN + NAME + ADDR + SAL
     + IN + NAME + ADDR + SAL
     + IN + NAME + ADDR + SAL
     + block("A", "X", "1.0")
     + block("B", "Y", "2.0")
     + block("C", "Z", "3.0")),
]
