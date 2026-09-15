"""Keystroke runs for J1.S.P0059 (the program handles files).

REPLACE_REFERENCE: the reference transcript prints the table with fixed-width
columns ("Name       Address              Money") while the brief's screen uses
tab characters ("Name\\t\\tAddress\\tMoney", "Nghia\\t\\tHa Noi\\t\\t1000.0");
and its run 1 depends on words.txt written by run 0, while verify.py gives
every run a fresh copy of the project root. The brief wins, so only these
runs count.

Every run starts from the data files shipped at the project root:
  test.txt   Nghia;Ha Noi;1000 / Thanh;Ha Noi;1200 / Phuong;Ha Noi;1300 /
             Hoang;Da Nang;abc / Lan;Hue;700 / Minh;Hai Phong; / Tuan;Vinh;-50
  story.txt  "the cat and the dog" / "the dog sees the cat"
and the folder test/ (created by make_netbeans.py) serves as "a path that
exists but cannot be read / written as a file".
Expected screens are written from the brief, not captured from the program.
"""

REPLACE_REFERENCE = True

MENU = """========== File Processing =========
1. Find person info
2. Copy Text to new file
3. Exit
Enter your choice: """

PERSON = "--------- Person info ---------\nEnter Path:Enter Money:"
RESULT = "------------- Result ----------\n"
HEADER = "Name\t\tAddress\tMoney\n"
COPY = "------------- Copy text --------------\nEnter Source: Enter new file name: "

RUNS = [
    # the brief's Person-info screen character for character (money 800),
    # then the brief's copy screen; the new file is then read back through
    # option 1 to prove it holds each word exactly once, in first-seen order
    # (a word line has no ";" -> empty address, salary 0)
    ("1\ntest.txt\n800\n2\nstory.txt\nunique.txt\n1\nunique.txt\n0\n3\n",
     MENU + PERSON + RESULT + HEADER
     + "Nghia\t\tHa Noi\t\t1000.0\n"
     "Thanh\t\tHa Noi\t\t1200.0\n"
     "Phuong\t\tHa Noi\t\t1300.0\n"
     "\n"
     "Max: Phuong\n"
     "Min: Nghia\n"
     + MENU + COPY + "Copy done...\n"
     + MENU + PERSON + RESULT + HEADER
     + "the\t\t\t\t0.0\n"
     "cat\t\t\t\t0.0\n"
     "and\t\t\t\t0.0\n"
     "dog\t\t\t\t0.0\n"
     "sees\t\t\t\t0.0\n"
     "\n"
     "Max: sees\n"
     "Min: the\n"
     + MENU + "Goodbye."),

    # wrong salaries in the file ("abc", missing, negative) become 0 and are
    # listed first, in file order; money validation (blank path, letters,
    # negative); nobody earns 5000
    ("1\n\ntest.txt\nabc\n-1\n0\n1\ntest.txt\n5000\n3\n",
     MENU + "--------- Person info ---------\n"
     "Enter Path:You must input something.\n"
     "Enter Path:Enter Money:You must input a number.\n"
     "Enter Money:Money must not be less than 0.\n"
     "Enter Money:" + RESULT + HEADER
     + "Hoang\t\tDa Nang\t\t0.0\n"
     "Minh\t\tHai Phong\t\t0.0\n"
     "Tuan\t\tVinh\t\t0.0\n"
     "Lan\t\tHue\t\t700.0\n"
     "Nghia\t\tHa Noi\t\t1000.0\n"
     "Thanh\t\tHa Noi\t\t1200.0\n"
     "Phuong\t\tHa Noi\t\t1300.0\n"
     "\n"
     "Max: Phuong\n"
     "Min: Hoang\n"
     + MENU + PERSON + RESULT + "No person found.\n"
     + MENU + "Goodbye."),

    # every error of the brief: menu letters / out of range; "Path doesn't
    # exist" and "Can't read file" for both functions; "Can't write file";
    # a blank destination is asked again
    ("x\n4\n1\nnofile.txt\n100\n1\ntest\n100\n"
     "2\nnofile.txt\nout.txt\n2\ntest\nout.txt\n"
     "2\nstory.txt\n\nno_such_dir/out.txt\n3\n",
     MENU + "You must input a number.\n"
     "Enter your choice: Please choose from 1 to 3.\n"
     "Enter your choice: " + PERSON + "Path doesn't exist\n"
     + MENU + PERSON + "Can’t read file\n"
     + MENU + COPY + "Path doesn't exist\n"
     + MENU + COPY + "Can’t read file\n"
     + MENU + "------------- Copy text --------------\n"
     "Enter Source: Enter new file name: You must input something.\n"
     "Enter new file name: Can’t write file\n"
     + MENU + "Goodbye."),
]
