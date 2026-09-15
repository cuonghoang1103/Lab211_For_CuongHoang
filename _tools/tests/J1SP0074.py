"""Keystroke runs for J1.S.P0074 (matrix calculator).

REPLACE_REFERENCE: the reference transcript uses its own menu ("Matrix
Calculator", "4. Exit"), asks all four sizes first ("Enter number of rows of
matrix 1: "), prompts "Element [1][1]: " and prints the result as aligned
columns. The brief's screen image shows "=======Calculator program======",
"4. Quit", "Your choice:", "Enter Row Matrix 1:", "Enter Matrix1[1][1]:" and
the result block "[2][3][2]" / "+" / ... / "=" / ... - the brief wins.

The three happy runs replay the brief's own three screens (addition with the
"a" typo, subtraction 3x3, multiplication 3x4 * 4x2) with the values the
image's result block shows. Messages the brief does not word (menu, size,
size mismatch, "Bye.") are the reference program's own wording.
Expected screens are written from the brief, not captured from the program.
"""

REPLACE_REFERENCE = True

MENU = ("=======Calculator program======\n"
        "1. Addition Matrix\n"
        "2. Subtraction Matrix\n"
        "3. Multiplication Matrix\n"
        "4. Quit\n"
        "Your choice:")


def prompts(n, rows, cols):
    """The value prompts of matrix n, without the values (stdin is not echoed)."""
    return "".join("Enter Matrix%d[%d][%d]:" % (n, r, c)
                   for r in range(1, rows + 1) for c in range(1, cols + 1))


def keys(values):
    return "".join("%s\n" % v for v in values)


RESULT = "-------- Result --------\n"
VALUE_ERR = "Values of matrix must be the number\n"

RUNS = [
    # brief screen 2: addition 2x3, the typo "a" on Matrix1[2][1]
    ("1\n2\n3\n" + keys([2, 3, 2, "a", 1, 2, 2]) + "2\n3\n"
     + keys([1, 32, 3, 2, 21, 23]) + "4\n",
     MENU + "-------- Addition --------\n"
     "Enter Row Matrix 1:Enter Column Matrix 1:"
     "Enter Matrix1[1][1]:Enter Matrix1[1][2]:Enter Matrix1[1][3]:"
     "Enter Matrix1[2][1]:" + VALUE_ERR
     + "Enter Matrix1[2][1]:Enter Matrix1[2][2]:Enter Matrix1[2][3]:"
     "Enter Row Matrix 2:Enter Column Matrix 2:" + prompts(2, 2, 3)
     + RESULT
     + "[2][3][2]\n[1][2][2]\n+\n[1][32][3]\n[2][21][23]\n=\n"
     "[3][35][5]\n[3][23][25]\n"
     + MENU + "Bye."),

    # brief screen 3: subtraction 3x3, 1..9 minus 10..18
    ("2\n3\n3\n" + keys(range(1, 10)) + "3\n3\n" + keys(range(10, 19)) + "4\n",
     MENU + "-------- Subtraction --------\n"
     "Enter Row Matrix 1:Enter Column Matrix 1:" + prompts(1, 3, 3)
     + "Enter Row Matrix 2:Enter Column Matrix 2:" + prompts(2, 3, 3)
     + RESULT
     + "[1][2][3]\n[4][5][6]\n[7][8][9]\n-\n"
     "[10][11][12]\n[13][14][15]\n[16][17][18]\n=\n"
     "[-9][-9][-9]\n[-9][-9][-9]\n[-9][-9][-9]\n"
     + MENU + "Bye."),

    # brief screen 4: multiplication (3x4) * (4x2), the image's result block
    ("3\n3\n4\n" + keys([2, 3, 4, 5, 1, 1, 1, 5, 1, 1, 1, 5]) + "4\n2\n"
     + keys([0, 1000, 0, 100, 0, 10, 0, 10]) + "4\n",
     MENU + "-------- Multiplication --------\n"
     "Enter Row Matrix 1:Enter Column Matrix 1:" + prompts(1, 3, 4)
     + "Enter Row Matrix 2:Enter Column Matrix 2:" + prompts(2, 4, 2)
     + RESULT
     + "[2][3][4][5]\n[1][1][1][5]\n[1][1][1][5]\n*\n"
     "[0][1000]\n[0][100]\n[0][10]\n[0][10]\n=\n"
     "[0][2390]\n[0][1160]\n[0][1160]\n"
     + MENU + "Bye."),

    # every other message: menu letters / out of range; size letters, 0, 21;
    # value "1.5" and empty; addition with different shapes stops BEFORE the
    # values of matrix 2; multiplication with cols1 != rows2 likewise;
    # 1x1 multiplication with negative numbers
    ("x\n9\n0\n"
     "1\nabc\n0\n21\n1\n2\n5\n6\n2\n1\n"
     "2\n2\n2\n1\n2\n3\n4\n2\n3\n"
     "3\n2\n3\n1\n2\n3\n4\n5\n6\n2\n3\n"
     "3\n1\n1\n1.5\n\n-3\n1\n1\n-4\n"
     "4\n",
     MENU + "Your choice must be a number.\n"
     + "Your choice:Please input a number in [1, 4].\n"
     + "Your choice:Please input a number in [1, 4].\n"
     + "Your choice:-------- Addition --------\n"
     "Enter Row Matrix 1:Size must be a positive number.\n"
     "Enter Row Matrix 1:Please input a number in [1, 20].\n"
     "Enter Row Matrix 1:Please input a number in [1, 20].\n"
     "Enter Row Matrix 1:Enter Column Matrix 1:" + prompts(1, 1, 2)
     + "Enter Row Matrix 2:Enter Column Matrix 2:"
     "Two matrixes must have the same number of rows and columns.\n"
     + MENU + "-------- Subtraction --------\n"
     "Enter Row Matrix 1:Enter Column Matrix 1:" + prompts(1, 2, 2)
     + "Enter Row Matrix 2:Enter Column Matrix 2:"
     "Two matrixes must have the same number of rows and columns.\n"
     + MENU + "-------- Multiplication --------\n"
     "Enter Row Matrix 1:Enter Column Matrix 1:" + prompts(1, 2, 3)
     + "Enter Row Matrix 2:Enter Column Matrix 2:"
     "Number of columns of matrix 1 must equal number of rows of matrix 2.\n"
     + MENU + "-------- Multiplication --------\n"
     "Enter Row Matrix 1:Enter Column Matrix 1:"
     "Enter Matrix1[1][1]:" + VALUE_ERR + "Enter Matrix1[1][1]:" + VALUE_ERR
     + "Enter Matrix1[1][1]:Enter Row Matrix 2:Enter Column Matrix 2:"
     "Enter Matrix2[1][1]:" + RESULT + "[-3]\n*\n[-4]\n=\n[12]\n"
     + MENU + "Bye."),
]
