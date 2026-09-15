"""Keystroke runs for J1.S.P0008 (letter and character count).

REPLACE_REFERENCE: the reference program printed a title, the prompt
"Please input a string: " and four totals ("Characters (with spaces): 16",
"Letters: 10", "Words: 3"...). The brief's screen is completely different:

    Enter your content:
    hello world
    {hello=1, world=1}
    {w=1, d=1, e=1, r=1, o=2, l=3, h=1}

i.e. a count PER WORD and a count PER CHARACTER - the brief wins, so only
these runs count.

Order of the second line: the picture's order {w, d, e, r, o, l, h} is the
bucket order of the JDK 7 HashMap (computed: Java 7's hash spreading puts
w,d,e,r,o,l,h in buckets 0,2,3,5,9,10,14). JDK 8 - the lab's JDK - prints the
same HashMap as {r=1, d=1, e=1, w=1, h=1, l=3, o=2} and the words as
{world=1, hello=1}. Since no JDK 8 program can reproduce the picture's
order, the program uses LinkedHashMap (order of first appearance), which
also gives the picture's FIRST line exactly. The counts are identical.
See HUONG-DAN section 9.

Expected screens below are written by hand from that rule, not captured.
"""

REPLACE_REFERENCE = True

PROMPT = "Enter your content:"
EMPTY = "Input must not be empty."

RUNS = [
    # the brief's own example
    ("hello world\n",
     PROMPT + "\n"
     "{hello=1, world=1}\n"
     "{h=1, e=1, l=3, o=2, w=1, r=1, d=1}"),
    # the only validation message: an empty line, then a line of spaces;
    # then several spaces between words count as ONE separator
    ("\n   \na    b\n",
     PROMPT + "\n" + EMPTY + "\n"
     + PROMPT + "\n" + EMPTY + "\n"
     + PROMPT + "\n"
     "{a=1, b=1}\n"
     "{a=1, b=1}"),
    # case is kept, punctuation stays part of the word and is a character
    ("Hello hello, world!\n",
     PROMPT + "\n"
     "{Hello=1, hello,=1, world!=1}\n"
     "{H=1, e=2, l=5, o=3, h=1, ,=1, w=1, r=1, d=1, !=1}"),
    # a repeated word and a TAB separator (StringTokenizer splits on it too)
    ("the cat\tthe hat\n",
     PROMPT + "\n"
     "{the=2, cat=1, hat=1}\n"
     "{t=4, h=3, e=2, c=1, a=2}"),
    # the reference solution's own keystrokes
    ("Hello World 123!\n",
     PROMPT + "\n"
     "{Hello=1, World=1, 123!=1}\n"
     "{H=1, e=1, l=3, o=2, W=1, r=1, d=1, 1=1, 2=1, 3=1, !=1}"),
]
