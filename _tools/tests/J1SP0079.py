"""Keystroke runs for J1.S.P0079 (zip and unzip with java.util.zip).

REPLACE_REFERENCE: the reference runs shared ONE working directory (run 1
unzipped the archive run 0 wrote, run 2 used a malicious zip that run 0's
checker planted) and zipped the project's own src/ folder. verify.py gives
every run a fresh copy of the project root WITHOUT src/, so each run below is
self-contained. The project ships:
  data/hello.txt, data/image.bin (256 binary bytes), data/docs/guide.txt
  slip-test.zip  = readme.txt + an entry named ../../pwned.txt

The console of the reference is kept: numbered menu, "Please choice one
option: ", the brief's titles/prompts/result lines, and "<reason>" + "Failed"
when a job fails (the brief shows only the success screen).

Proving the zip is a REAL zip from stdout alone: run 0 zips data/, unzips the
result into another folder, then zips THAT folder again.
The three listings must be identical. Extraction opens the zip with
java.util.zip.ZipFile, which reads the central directory that
ZipOutputStream.close() writes - so an archive whose stream was never closed
is refused ("Not a zip file") and run 0 fails. (Measured: with the
try-with-resources removed, ZipInputStream would still have unzipped it.)
"""

REPLACE_REFERENCE = True

MENU = ("========= Zipper program =========\n"
        "1. Compression\n"
        "2. Extraction\n"
        "3. Exit\n"
        "Please choice one option: ")
COMP = ("---------- Compression --------\n"
        "Enter Source Folder: Enter Destination Folder: Enter Name: ")
EXTR = ("---------- Extraction ---------\n"
        "Enter Source file: Enter Destination Folder: ")
RESULT = "------------ Result -----------\n"
LIST = ("File name docs/guide.txt\n"
        "File name hello.txt\n"
        "File name image.bin\n"
        "Successfully\n")


def failed(reason):
    return RESULT + reason + "\nFailed\n"


RUNS = [
    # round trip: zip data/ -> archive/backup.zip (".zip" added), unzip it
    # into restored/, zip restored/ again with a name that already ends in
    # .zip - the same three files every time, sub-folder path kept
    ("1\ndata\narchive\nbackup\n"
     "2\narchive/backup.zip\nrestored\n"
     "1\nrestored\narchive\ncopy.ZIP\n3\n",
     MENU + COMP + RESULT + LIST
     + MENU + EXTR + RESULT + LIST
     + MENU + COMP + RESULT + LIST
     + MENU),
    # every failure reason
    ("1\nno-such-folder\narchive\nbackup\n"
     "1\ndata\ndata/hello.txt/inside\nbackup\n"
     "2\nno-such.zip\nout\n"
     "2\ndata/hello.txt\nvoid\n"
     "1\nvoid\nout\ne\n"
     "2\nout/e.zip\nout2\n3\n",
     MENU + COMP + failed("Source folder does not exist: no-such-folder")
     + MENU + COMP + failed("Cannot create destination folder: data/hello.txt/inside")
     + MENU + EXTR + failed("Zip file does not exist: no-such.zip")
     + MENU + EXTR + failed("Not a zip file: data/hello.txt")
     # the failed extraction above left the EMPTY folder void/: zipping it
     # gives a valid zip with no file, and extracting that zip is refused
     + MENU + COMP + RESULT + "Successfully\n"
     + MENU + EXTR + failed("There is no file to extract in: out/e.zip")
     + MENU),
    # zip slip: the entry ../../pwned.txt is refused
    ("2\nslip-test.zip\nsafe\n3\n",
     MENU + EXTR + failed("Entry is outside the destination folder: ../../pwned.txt")
     + MENU),
    # the zip is written INSIDE the folder being zipped, twice: the second
    # time the existing self.zip must not be zipped into itself
    ("1\ndata\ndata\nself\n1\ndata\ndata\nself\n3\n",
     MENU + COMP + RESULT + LIST + MENU + COMP + RESULT + LIST + MENU),
    # menu validation and blank answers are asked again
    ("abc\n\n0\n4\n1\n\ndata\n  \nout\n\nz\n3\n",
     MENU + "You must input a number.\n"
     + "Please choice one option: You must input a number.\n"
     + "Please choice one option: Please choose from 1 to 3.\n"
     + "Please choice one option: Please choose from 1 to 3.\n"
     + "Please choice one option: ---------- Compression --------\n"
     + "Enter Source Folder: You must input a value.\n"
     + "Enter Source Folder: Enter Destination Folder: You must input a value.\n"
     + "Enter Destination Folder: Enter Name: You must input a value.\n"
     + "Enter Name: " + RESULT + LIST + MENU),
]
