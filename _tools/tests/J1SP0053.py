"""Keystroke runs for J1.S.P0053 (bubble sort menu, ascending / descending).

REPLACE_REFERENCE: the reference transcript contradicts the brief's screens:
its menu reads "===== Array Sort Program =====" / "1. Input items of the
array", its prompt "Please choose one option: ", it asks "Length of array: "
and prints "[-7, 3, 5, 12]" instead of the brief's "Enter Number: ",
"Enter Number 1: " and "[1]->[3]->[5]" / "[5]<-[3]<-[1]". The brief wins, so
only these runs count.

Expected screens are written from the brief, not captured from the program.
"""

REPLACE_REFERENCE = True

MENU = ("========= Bubble Sort program =========\n"
        "1. Input Element\n"
        "2. Sort Ascending\n"
        "3. Sort Descending\n"
        "4. Exit\n"
        "Please choice one option:\n")
INPUT = "----- Input Element -----\nInput Length Of Array\n"
LEN_ERR = "Please input numberand number is greater than zero\n"
NUM_ERR = "Please input number\n"
EMPTY = "Please input the array first (option 1).\n"
ASC = "----- Ascending -----\n"
DESC = "----- Descending -----\n"


def elements(n):
    """Prompts of n elements (the typed values are not echoed)."""
    return "".join("Enter Number %d: " % i for i in range(1, n + 1))


RUNS = [
    # the brief's own screens: a, -1, then 3 elements 5 1 3; ascending;
    # descending; exit
    ("1\na\n-1\n3\n5\n1\n3\n2\n3\n4\n",
     MENU + INPUT + "Enter Number: " + LEN_ERR + "Enter Number: " + LEN_ERR
     + "Enter Number: " + elements(3)
     + MENU + ASC + "[1]->[3]->[5]\n"
     + MENU + DESC + "[5]<-[3]<-[1]\n"
     + MENU),

    # menu errors (letter, 9, 0); options 2 and 3 before option 1; length 0
    # and above the cap; element errors (letters, decimal, empty); negative
    # numbers, zero and duplicates; then option 1 again replaces the array
    ("x\n9\n0\n2\n3\n1\n0\n1001\n4\nabc\n2.5\n\n-7\n0\n12\n-7\n2\n3\n1\n1\n42\n2\n4\n",
     MENU + NUM_ERR + "Please choice one option:\n"
     + "Please choose from 1 to 4.\nPlease choice one option:\n"
     + "Please choose from 1 to 4.\nPlease choice one option:\n"
     + EMPTY + MENU + EMPTY
     + MENU + INPUT + "Enter Number: " + LEN_ERR
     + "Enter Number: Length must not be greater than 1000.\n"
     + "Enter Number: Enter Number 1: " + NUM_ERR + "Enter Number 1: " + NUM_ERR
     + "Enter Number 1: " + NUM_ERR + "Enter Number 1: Enter Number 2: "
     + "Enter Number 3: Enter Number 4: "
     + MENU + ASC + "[-7]->[-7]->[0]->[12]\n"
     + MENU + DESC + "[12]<-[0]<-[-7]<-[-7]\n"
     + MENU + INPUT + "Enter Number: Enter Number 1: "
     + MENU + ASC + "[42]\n"
     + MENU),

    # reverse-sorted input: ascending must fully reverse it, descending must
    # stop after one pass; sorting twice shows the typed array is unchanged
    ("1\n5\n5\n4\n3\n2\n1\n2\n3\n2\n4\n",
     MENU + INPUT + "Enter Number: " + elements(5)
     + MENU + ASC + "[1]->[2]->[3]->[4]->[5]\n"
     + MENU + DESC + "[5]<-[4]<-[3]<-[2]<-[1]\n"
     + MENU + ASC + "[1]->[2]->[3]->[4]->[5]\n"
     + MENU),
]
