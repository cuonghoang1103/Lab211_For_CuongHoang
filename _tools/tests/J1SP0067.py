"""Extra keystroke runs for J1.S.P0067 (analyse the user input string).

The reference runs are kept: they already print the brief's required
"Number of characters" line and the CORRECT perfect squares ([] for the
brief's example - the brief's screen shows [321, 22], which are not squares).
Expected screens are written from the brief and the definitions, not
captured from the program.
"""

HEAD = "===== Analysis String program ====\nInput String: "


def result(length, square, odd, even, all_n, upper, lower, special, all_c):
    return ("-----Result Analysis------\n"
            "Number of characters: %d\n"
            "Perfect Square Numbers: %s\nOdd Numbers: %s\nEven Numbers: %s\n"
            "All Numbers: %s\nUppercase Characters: %s\nLowercase Characters: %s\n"
            "Special Characters: %s\nAll Characters: %s"
            % (length, square, odd, even, all_n, upper, lower, special, all_c))


RUNS = [
    # both validation messages; then spaces around the input are trimmed;
    # 0 and 1 and 49 are perfect squares; 0 is even; space, comma and
    # underscore are special characters
    ("   \n12345678901x\n  Ab 0,1_49Z  \n",
     HEAD + "Input must not be empty.\n"
     + "Input String: Each number in the string must be at most 2147483647.\n"
     + "Input String: "
     + result(10, "[0, 1, 49]", "[1, 49]", "[0]", "[0, 1, 49]",
              "AZ", "b", " ,_", "Ab ,_Z")),

    # no digits, no specials: every number list is empty
    ("hello\n",
     HEAD + result(5, "[]", "[]", "[]", "[]", "", "hello", "", "hello")),

    # the largest perfect square that fits an int (46340^2) and
    # Integer.MAX_VALUE itself (odd, not a square)
    ("x2147395600y2147483647\n",
     HEAD + result(22, "[2147395600]", "[2147483647]", "[2147395600]",
                   "[2147395600, 2147483647]", "", "xy", "", "xy")),

    # leading zeros: "007" is the number 7
    ("007a\n",
     HEAD + result(4, "[]", "[7]", "[]", "[7]", "", "a", "", "a")),
]
