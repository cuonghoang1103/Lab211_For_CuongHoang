"""Keystroke runs for J1.S.P0078 (copy files driven by config.properties).

REPLACE_REFERENCE: the reference runs shared ONE working directory in order
(run 1 relied on the config file run 0 wrote) and its program created a sample
folder on first start ("Sample folder created: source") and had a 4th menu
item (Stream Test). verify.py gives every run a fresh copy of the project
root, so each run below is self-contained: the project ships the sample folder
source/ (data1.csv, data2.csv, notes.txt, tone.wav = 256 binary bytes) and NO
config.properties, so every run starts at the brief's box 2
"File Configure is not found!" unless option 2 writes a config first.

Screens follow the brief's diagram word for word. Prompts are print() without
a newline, so piped input shows them glued together.

Not reachable from the keyboard: "Can't read File Configure" (an unreadable
existing file) and "File Configure cannot create" (an unwritable project
folder) - see HUONG-DAN section 5 for the manual test.
"""

REPLACE_REFERENCE = True

MENU = ("============ Copy Program =========\n"
        "1. Copy File\n"
        "2. Input Configure File\n"
        "3. Exit\n"
        "Your choice: ")
FORM = ("---- Input Configure File -----\n"
        "Copy Folder:Data Type:Path:")
NOT_FOUND = "File Configure is not found!\n"
CHECK = "---- Check Configure File -----\n"
SHUT = "System shutdown"


def copied(*names):
    return ("Copy is running...\n"
            "------------ File Name ------------\n"
            + "".join(n + "\n" for n in names)
            + "Copy is finished...\n")


ALL3 = copied("data1.csv", "data2.csv", "tone.wav")

RUNS = [
    # box 2 -> box 5: no config, type it, copy (notes.txt is filtered out,
    # the binary tone.wav is copied byte-identical); option 1 again READS
    # the file just written and copies over the existing files; then exit
    ("1\nsource\n*.CSV,*.WAV\ndest\n1\n3\n",
     MENU + NOT_FOUND + FORM + CHECK + ALL3
     + MENU + CHECK + ALL3
     + MENU),
    # source folder does not exist -> box 4, program stops
    ("1\nnosuchfolder\n*.CSV\ndest\n",
     MENU + NOT_FOUND + FORM + CHECK + "Can't find folder Source\n" + SHUT),
    # option 2 writes a config with nothing in it -> first check fails
    ("2\n\n\n\n1\n",
     MENU + FORM + MENU + CHECK + "Folder Source is not input\n" + SHUT),
    # DATA_TYPE left blank
    ("2\nsource\n\ndest\n1\n",
     MENU + FORM + MENU + CHECK + "Data type is not input\n" + SHUT),
    # PATH left blank
    ("2\nsource\n*.CSV,*.WAV\n\n1\n",
     MENU + FORM + MENU + CHECK + "Folder Destination is not input\n" + SHUT),
    # PATH under a FILE: mkdirs cannot create it
    ("2\nsource\n*.CSV,*.WAV\nsource/data1.csv/nested\n1\n",
     MENU + FORM + MENU + CHECK + "Can't make folder Destination\n" + SHUT),
    # PATH is the source folder itself (refused: would empty the files)
    ("2\nsource\n*.CSV\n./source\n1\n",
     MENU + FORM + MENU + CHECK
     + "Folder Source and Folder Destination are the same\n" + SHUT),
    # menu validation: letters, empty, below, above, then Exit
    ("x\n\n0\n9\n3\n",
     MENU + "You must input a number.\n"
     + "Your choice: You must input a number.\n"
     + "Your choice: Please choose from 1 to 3.\n"
     + "Your choice: Please choose from 1 to 3.\n"
     + "Your choice: "),
    # DATA_TYPE without "*." and in lower case, spaces after the comma;
    # PATH with missing parents (created) and an "=" inside it (the value is
    # cut at the FIRST "=" only)
    ("2\nsource\nwav, txt\nout/a=b/c\n1\n3\n",
     MENU + FORM + MENU + CHECK + copied("notes.txt", "tone.wav") + MENU),
    # a type that matches nothing: an empty list, still finished
    ("1\nsource\n*.PDF\nempty\n3\n",
     MENU + NOT_FOUND + FORM + CHECK + copied() + MENU),
]
