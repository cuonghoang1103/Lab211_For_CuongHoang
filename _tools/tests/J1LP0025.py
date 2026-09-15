"""Keystroke runs for J1.L.P0025 (normalize text: input.txt -> output.txt).

The brief has no screen at all; the reference program's menu and messages are
kept IDENTICAL (runs REF0, REF1, REF3, REF4 below are the reference runs,
copied verbatim).

REPLACE_REFERENCE: reference run 2 ("3\\n0\\n" printing output.txt) only
passes when an EARLIER run left output.txt on the disk, while reference run 0
needs output.txt to be absent. verify.py gives every run a fresh copy of the
project folder, so both cannot pass; run 2 is replaced by RUN_DISK below,
which writes the file itself first (1, 2, then 3). Nothing else differs.

No input.txt is shipped at the project root: REF0 shows the brief's
"file not found" exception on a fresh folder, and option 1 writes the sample.
"""

REPLACE_REFERENCE = True

REF0 = ('2\n3\n0\n',
        '============ TEXT NORMALIZER ============\n1. Create the sample input file (input.txt)\n2. Normalize input.txt into output.txt\n3. Show output.txt from the disk\n4. Normalize one line typed on the keyboard\n5. Show the rules on sample cases\n0. Exit\n========================================\nYour choice: Error: File not found: input.txt\n============ TEXT NORMALIZER ============\n1. Create the sample input file (input.txt)\n2. Normalize input.txt into output.txt\n3. Show output.txt from the disk\n4. Normalize one line typed on the keyboard\n5. Show the rules on sample cases\n0. Exit\n========================================\nYour choice: Error: File not found: output.txt\n============ TEXT NORMALIZER ============\n1. Create the sample input file (input.txt)\n2. Normalize input.txt into output.txt\n3. Show output.txt from the disk\n4. Normalize one line typed on the keyboard\n5. Show the rules on sample cases\n0. Exit\n========================================\nYour choice: Goodbye.')

REF1 = ('1\n2\n0\n',
        '============ TEXT NORMALIZER ============\n1. Create the sample input file (input.txt)\n2. Normalize input.txt into output.txt\n3. Show output.txt from the disk\n4. Normalize one line typed on the keyboard\n5. Show the rules on sample cases\n0. Exit\n========================================\nYour choice: Sample input written to input.txt (7 lines).\n============ TEXT NORMALIZER ============\n1. Create the sample input file (input.txt)\n2. Normalize input.txt into output.txt\n3. Show output.txt from the disk\n4. Normalize one line typed on the keyboard\n5. Show the rules on sample cases\n0. Exit\n========================================\nYour choice: --------------------------------------------------\nBEFORE - input.txt (7 lines)\n--------------------------------------------------\n1: [   as you can see , detecting whether a string is normalized can be quite efficient.A lot of the cost]\n2: [of normalizing in the “ second row ” is for the initialization of buffers .]\n3: []\n4: [\\tThe cost of which is amortized   when one is    processing larger strings.]\n5: [   ]\n6: [as it turns out,these buffers are rarely needed , so we may change the implementation]\n7: [at some point   to speed up the common case for small strings even further]\n--------------------------------------------------\nAFTER - output.txt (1 line)\n--------------------------------------------------\nAs you can see, detecting whether a string is normalized can be quite efficient. A lot of the cost of normalizing in the “second row” is for the initialization of buffers. The cost of which is amortized when one is processing larger strings. As it turns out, these buffers are rarely needed, so we may change the implementation at some point to speed up the common case for small strings even further.\n--------------------------------------------------\nNormalized document written to output.txt.\n============ TEXT NORMALIZER ============\n1. Create the sample input file (input.txt)\n2. Normalize input.txt into output.txt\n3. Show output.txt from the disk\n4. Normalize one line typed on the keyboard\n5. Show the rules on sample cases\n0. Exit\n========================================\nYour choice: Goodbye.')

REF3 = ('5\n0\n',
        '============ TEXT NORMALIZER ============\n1. Create the sample input file (input.txt)\n2. Normalize input.txt into output.txt\n3. Show output.txt from the disk\n4. Normalize one line typed on the keyboard\n5. Show the rules on sample cases\n0. Exit\n========================================\nYour choice: --------------------------------------------------\nRULES ON SAMPLE CASES\n--------------------------------------------------\n 1. leading and trailing spaces\n    IN : [   hello world   ]\n    OUT: [Hello world.]\n 2. a run of many spaces\n    IN : [a    b     c]\n    OUT: [A b c.]\n 3. tabs mixed with spaces\n    IN : [a \\t  b\\t\\tc]\n    OUT: [A b c.]\n 4. empty text\n    IN : []\n    OUT: []\n 5. spaces only\n    IN : [     ]\n    OUT: []\n 6. punctuation glued to the next word\n    IN : [one ,two.three:four]\n    OUT: [One, two. Three: four.]\n 7. space in front of punctuation\n    IN : [sentence one . sentence two]\n    OUT: [Sentence one. Sentence two.]\n 8. capitals in the middle\n    IN : [the QUICK brown FOX jumps]\n    OUT: [The quick brown fox jumps.]\n 9. single-letter words\n    IN : [a dog. i saw i and a cat]\n    OUT: [A dog. I saw i and a cat.]\n10. curly quotes with spaces inside\n    IN : [he said “  hello there  ” loudly]\n    OUT: [He said “hello there” loudly.]\n11. straight quotes with spaces inside\n    IN : [he said "  hello there  " loudly]\n    OUT: [He said "hello there" loudly.]\n12. comma at the very end\n    IN : [this ends with a comma ,]\n    OUT: [This ends with a comma.]\n13. a decimal number\n    IN : [the price is 3.14 dollars]\n    OUT: [The price is 3.14 dollars.]\n14. a non-breaking space\n    IN : [word<nbsp>word and more]\n    OUT: [Word word and more.]\n15. Vietnamese, non-ASCII\n    IN : [  chào   bạn ,tôi tên là cường.rất vui]\n    OUT: [Chào bạn, tôi tên là cường. Rất vui.]\n16. already normalized\n    IN : [This is fine. It is already correct.]\n    OUT: [This is fine. It is already correct.]\n--------------------------------------------------\n============ TEXT NORMALIZER ============\n1. Create the sample input file (input.txt)\n2. Normalize input.txt into output.txt\n3. Show output.txt from the disk\n4. Normalize one line typed on the keyboard\n5. Show the rules on sample cases\n0. Exit\n========================================\nYour choice: Goodbye.')

REF4 = ('4\n   these   are\tmixed ,and  spaces  \n4\n\n4\n     \n4\nhe said “  hello there  ” loudly\n9\nx\n0\n',
        '============ TEXT NORMALIZER ============\n1. Create the sample input file (input.txt)\n2. Normalize input.txt into output.txt\n3. Show output.txt from the disk\n4. Normalize one line typed on the keyboard\n5. Show the rules on sample cases\n0. Exit\n========================================\nYour choice: Type the line to normalize:\nIN : [   these   are\\tmixed ,and  spaces  ]\nOUT: [These are mixed, and spaces.]\n============ TEXT NORMALIZER ============\n1. Create the sample input file (input.txt)\n2. Normalize input.txt into output.txt\n3. Show output.txt from the disk\n4. Normalize one line typed on the keyboard\n5. Show the rules on sample cases\n0. Exit\n========================================\nYour choice: Type the line to normalize:\nIN : []\nOUT: []\n============ TEXT NORMALIZER ============\n1. Create the sample input file (input.txt)\n2. Normalize input.txt into output.txt\n3. Show output.txt from the disk\n4. Normalize one line typed on the keyboard\n5. Show the rules on sample cases\n0. Exit\n========================================\nYour choice: Type the line to normalize:\nIN : [     ]\nOUT: []\n============ TEXT NORMALIZER ============\n1. Create the sample input file (input.txt)\n2. Normalize input.txt into output.txt\n3. Show output.txt from the disk\n4. Normalize one line typed on the keyboard\n5. Show the rules on sample cases\n0. Exit\n========================================\nYour choice: Type the line to normalize:\nIN : [he said “  hello there  ” loudly]\nOUT: [He said “hello there” loudly.]\n============ TEXT NORMALIZER ============\n1. Create the sample input file (input.txt)\n2. Normalize input.txt into output.txt\n3. Show output.txt from the disk\n4. Normalize one line typed on the keyboard\n5. Show the rules on sample cases\n0. Exit\n========================================\nYour choice: Please choose from 0 to 5.\nYour choice: You must input a number.\nYour choice: Goodbye.')

MENU = ("============ TEXT NORMALIZER ============\n"
        "1. Create the sample input file (input.txt)\n"
        "2. Normalize input.txt into output.txt\n"
        "3. Show output.txt from the disk\n"
        "4. Normalize one line typed on the keyboard\n"
        "5. Show the rules on sample cases\n"
        "0. Exit\n"
        "========================================\n"
        "Your choice: ")
RULE = "-" * 50 + "\n"
SAMPLE_OUT = ("As you can see, detecting whether a string is normalized can be "
              "quite efficient. A lot of the cost of normalizing in the "
              "“second row” is for the initialization of buffers. The "
              "cost of which is amortized when one is processing larger "
              "strings. As it turns out, these buffers are rarely needed, so "
              "we may change the implementation at some point to speed up the "
              "common case for small strings even further.")

# replaces reference run 2: write the sample, normalize, then read
# output.txt back from the disk in the same run
RUN_DISK = ("1\n2\n3\n0\n",
            REF1[1].rsplit("Your choice: Goodbye.", 1)[0]
            + "Your choice: " + RULE + "ON DISK - output.txt (1 line)\n" + RULE
            + SAMPLE_OUT + "\n" + RULE + MENU + "Goodbye.")

P1 = ("as you can see, detecting whether a string is normalized can be quite "
      "efficient. A lot of the cost of normalizing in the “second row” "
      "is for the initialization of buffers. The cost of which is amortized "
      "when one is processing larger strings.")
P2 = ("As it turns out, these buffers are rarely needed, so we may change the "
      "implementation at some point to speed up the common case for small "
      "strings even further")

# the brief's own example, one paragraph per typed line: first letter raised,
# final dot added, everything else already normal and left alone; plus a
# colon case (space before removed, one space after) and upper case words
RUN_BRIEF = ("4\n" + P1 + "\n4\n" + P2 + "\n4\nnote :this is   IT\n0\n",
             MENU + "Type the line to normalize:\n"
             "IN : [" + P1 + "]\n"
             "OUT: [A" + P1[1:] + "]\n"
             + MENU + "Type the line to normalize:\n"
             "IN : [" + P2 + "]\n"
             "OUT: [" + P2 + ".]\n"
             + MENU + "Type the line to normalize:\n"
             "IN : [note :this is   IT]\n"
             "OUT: [Note: this is it.]\n"
             + MENU + "Goodbye.")

RUNS = [REF0, REF1, REF3, REF4, RUN_DISK, RUN_BRIEF]
