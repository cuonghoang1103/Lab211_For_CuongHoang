"""Keystroke runs for J1.S.P0083 (MyStack).

REPLACE_REFERENCE: the reference program prints its own wording ("Value to
push: ", "Pushed 10", "Top is 30", "Stack (bottom -> top): ...", "Bye") while
the brief's sample run shows "Enter value: ", "Pushed 10.   Stack (top ->
bottom): [10]", "Get (top) = 30           (not removed)" and "Goodbye!" -
the brief wins, so only these runs count.

The brief's sample shows the menu ONCE, then only "Choose: " for every
following choice; the runs below expect exactly that.
"""

REPLACE_REFERENCE = True

MENU = ("======= STACK DEMO (MyStack) =======\n"
        "1.Push   2.Pop   3.Get(peek)   4.Display   0.Exit\n"
        "====================================\n")
CHOOSE = "Choose: "
VALUE = "Enter value: "
NUMBER = "You must input a number."
RANGE = "Value must be between 0 and 4."
EMPTY = "Stack is empty."

RUNS = [
    # 0. the brief's own sample run, character for character
    ("1\n10\n1\n20\n1\n30\n3\n2\n0\n",
     MENU
     + CHOOSE + VALUE + "Pushed 10.   Stack (top -> bottom): [10]\n"
     + CHOOSE + VALUE + "Pushed 20.   Stack (top -> bottom): [20, 10]\n"
     + CHOOSE + VALUE + "Pushed 30.   Stack (top -> bottom): [30, 20, 10]\n"
     + CHOOSE + "Get (top) = 30           (not removed)\n"
     + CHOOSE + "Popped 30.   Stack (top -> bottom): [20, 10]\n"
     + CHOOSE + "Goodbye!"),

    # 1. the brief's operation trace: push 10, 20, 30, get, pop, pop
    #    returns 30 then 20; Display shows the one value left
    ("1\n10\n1\n20\n1\n30\n3\n2\n2\n4\n0\n",
     MENU
     + CHOOSE + VALUE + "Pushed 10.   Stack (top -> bottom): [10]\n"
     + CHOOSE + VALUE + "Pushed 20.   Stack (top -> bottom): [20, 10]\n"
     + CHOOSE + VALUE + "Pushed 30.   Stack (top -> bottom): [30, 20, 10]\n"
     + CHOOSE + "Get (top) = 30           (not removed)\n"
     + CHOOSE + "Popped 30.   Stack (top -> bottom): [20, 10]\n"
     + CHOOSE + "Popped 20.   Stack (top -> bottom): [10]\n"
     + CHOOSE + "Stack (top -> bottom): [10]\n"
     + CHOOSE + "Goodbye!"),

    # 2. empty stack: pop, get and display must not crash (brief, Edge cases)
    ("2\n3\n4\n0\n",
     MENU
     + CHOOSE + EMPTY + "\n"
     + CHOOSE + EMPTY + "\n"
     + CHOOSE + "Stack (top -> bottom): []\n"
     + CHOOSE + "Goodbye!"),

    # 3. every menu validation message: letter, empty line, out of range
    #    (both sides), decimal; then Exit
    ("x\n\n9\n-1\n2.5\n0\n",
     MENU
     + CHOOSE + NUMBER + "\n"
     + CHOOSE + NUMBER + "\n"
     + CHOOSE + RANGE + "\n"
     + CHOOSE + RANGE + "\n"
     + CHOOSE + NUMBER + "\n"
     + CHOOSE + "Goodbye!"),

    # 4. value validation: letters, empty, decimal, too big for an int are
    #    re-asked; a negative number is a legal value; popping the last value
    #    leaves "[]", and one more pop reports the empty stack
    ("1\nabc\n\n1.5\n99999999999\n-7\n2\n2\n0\n",
     MENU
     + CHOOSE + VALUE + NUMBER + "\n"
     + VALUE + NUMBER + "\n"
     + VALUE + NUMBER + "\n"
     + VALUE + NUMBER + "\n"
     + VALUE + "Pushed -7.   Stack (top -> bottom): [-7]\n"
     + CHOOSE + "Popped -7.   Stack (top -> bottom): []\n"
     + CHOOSE + EMPTY + "\n"
     + CHOOSE + "Goodbye!"),

    # 5. LIFO after mixing: push 1, 2, pop (2), push 3 -> top is 3, then 1
    ("1\n1\n1\n2\n2\n1\n3\n4\n3\n0\n",
     MENU
     + CHOOSE + VALUE + "Pushed 1.   Stack (top -> bottom): [1]\n"
     + CHOOSE + VALUE + "Pushed 2.   Stack (top -> bottom): [2, 1]\n"
     + CHOOSE + "Popped 2.   Stack (top -> bottom): [1]\n"
     + CHOOSE + VALUE + "Pushed 3.   Stack (top -> bottom): [3, 1]\n"
     + CHOOSE + "Stack (top -> bottom): [3, 1]\n"
     + CHOOSE + "Get (top) = 3           (not removed)\n"
     + CHOOSE + "Goodbye!"),
]
