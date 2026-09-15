"""Keystroke runs for J1.S.P0058 (English - Vietnamese dictionary).

REPLACE_REFERENCE: the reference transcript contradicts the brief's screens:
it prints "Loaded 0 word(s).", "Added." / "Deleted." instead of the brief's
"Successful", "Cat = con meo" instead of "Vietnamese: Con Meo", and it refuses
a known word instead of asking whether to update its meaning (the brief's
Suggestion). The brief wins, so only these runs count.

Every run starts from the shipped data file dictionary.txt at the project root:
    hello=xin chao
    dog=con cho
Expected screens are written from the brief, not captured from the program.
"""

REPLACE_REFERENCE = True

MENU = """======== Dictionary program ========
1. Add Word
2. Delete Word
3. Translate
4. Exit
Your choice: """

ADD = "------------- Add -------------\n"
DELETE = "------------ Delete ----------------\n"
TRANSLATE = "------------- Translate ------------\n"
ASK = "This word already exists. Do you want to update its meaning (Y/N)? "

RUNS = [
    # the brief's four screens in order: add Cat, translate Cat, delete Cat,
    # then translate again -> not found ("display empty"), exit
    ("1\nCat\nCon Meo\n3\nCat\n2\nCat\n3\ncat\n4\n",
     MENU + ADD + "Enter English: Enter Vietnamese: Successful\n"
     + MENU + TRANSLATE + "Enter English: Vietnamese: Con Meo\n"
     + MENU + DELETE + "Enter English: Successful\n"
     + MENU + TRANSLATE + "Enter English: Empty - this word is not in the dictionary.\n"
     + MENU + "Bye"),

    # loadData: words of the shipped file are there at start, any case;
    # adding a known word asks Y/N: wrong answer, then N keeps the meaning,
    # then Y replaces it
    ("3\nHELLO\n1\ndog\ncho con\nx\nn\n3\nDog\n1\nDOG\ncon cho moi\nY\n3\ndog\n4\n",
     MENU + TRANSLATE + "Enter English: Vietnamese: xin chao\n"
     + MENU + ADD + "Enter English: Enter Vietnamese: " + ASK + "Please input Y or N.\n"
     + ASK + "The old meaning is kept.\n"
     + MENU + TRANSLATE + "Enter English: Vietnamese: con cho\n"
     + MENU + ADD + "Enter English: Enter Vietnamese: " + ASK + "Successful\n"
     + MENU + TRANSLATE + "Enter English: Vietnamese: con cho moi\n"
     + MENU + "Bye"),

    # every validation message: menu letters / out of range, blank English,
    # English with the file separator "=", blank Vietnamese; delete of an
    # unknown word; the deleted shipped word is gone afterwards
    ("abc\n5\n0\n1\n\na=b\nCat\n\nCon Meo\n2\nzzz\n2\nhello\n3\nhello\n4\n",
     MENU + "You must input a number.\n"
     "Your choice: Value must be between 1 and 4.\n"
     "Your choice: Value must be between 1 and 4.\n"
     "Your choice: " + ADD + "Enter English: English word must not be empty.\n"
     "Enter English: English word must not contain '='.\n"
     "Enter English: Enter Vietnamese: Vietnamese word must not be empty.\n"
     "Enter Vietnamese: Successful\n"
     + MENU + DELETE + "Enter English: Key does not exist in the dictionary.\n"
     + MENU + DELETE + "Enter English: Successful\n"
     + MENU + TRANSLATE + "Enter English: Empty - this word is not in the dictionary.\n"
     + MENU + "Bye"),
]
