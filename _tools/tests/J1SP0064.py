"""Keystroke runs for J1.S.P0064 (check phone, email, date).

REPLACE_REFERENCE (21/09/2026): in the brief's .docx screen the prompt runs
are written in black and the typed values in green, and the black prompts are
"Phone number: ", "Email: ", "Date: " - WITH one space at the end (6 of the 7
prompts; only "Phone number:abc" lacks it). The reference transcript printed
the prompts without that space, so it no longer matches the brief character
by character; its two runs are copied below with the space and only these
runs count.

The messages follow the brief's Guidelines ("Phone number must be number",
"Email must be correct format", "Date to correct format(dd/MM/yyyy)"), which
win over the typos of the screen ("must is number", "must is correct format",
"dd/mm/yyyy"). The "----- Result -----" block is the reference program's; the
brief is silent about what is shown after the last input.

The other runs add every other way each check can fail.
"""

REPLACE_REFERENCE = True

TITLE = "====== Validate Progaram ======\n"
P = "Phone number: "
E = "Email: "
D = "Date: "
NOT_NUMBER = "Phone number must be number\n"
NOT_10 = "Phone number must be 10 digits\n"
BAD_EMAIL = "Email must be correct format\n"
BAD_DATE = "Date to correct format(dd/MM/yyyy)\n"


def result(phone, email, date):
    return ("----- Result -----\nPhone number: %s\nEmail: %s\nDate: %s"
            % (phone, email, date))


RUNS = [
    # the reference run = the brief's own screen: 9 digits, letters, then a
    # good phone; a bad email; a bad date
    ("099999888\nabc\n0999998888\nabc\nnghianv@ftico.com\nabc\n15/06/2015\n",
     TITLE + P + NOT_10 + P + NOT_NUMBER + P + E + BAD_EMAIL + E + D + BAD_DATE
     + D + result("0999998888", "nghianv@ftico.com", "15/06/2015")),
    # the reference run with a day that does not exist (31/02)
    ("0999998888\na@b.com\n31/02/2015\n15/06/2015\n",
     TITLE + P + E + D + BAD_DATE + D + result("0999998888", "a@b.com", "15/06/2015")),
    # phone: empty, 11 digits, a letter inside, "+84", inner space;
    # then a valid one with spaces around it (trimmed)
    ("\n09999988881\n09a9998888\n+84999888\n0999 998888\n  0987654321  \n"
     "a@b.com\n01/01/2020\n",
     TITLE + P + NOT_NUMBER + P + NOT_10 + P + NOT_NUMBER + P + NOT_NUMBER
     + P + NOT_NUMBER + P + E + D + result("0987654321", "a@b.com", "01/01/2020")),
    # email: empty, no domain dot, no name, space inside, two @
    ("0999998888\n\na@b\n@b.com\na b@c.com\na@@b.com\nhe.176322+lab@fpt.edu.vn\n"
     "15/06/2015\n",
     TITLE + P + E + BAD_EMAIL + E + BAD_EMAIL + E + BAD_EMAIL + E + BAD_EMAIL
     + E + BAD_EMAIL + E + D + result("0999998888", "he.176322+lab@fpt.edu.vn",
                                      "15/06/2015")),
    # date: empty, dashes, single digits, trailing text, 29/02 of a non-leap
    # year, 31/04, month 13, day 00; then 29/02 of a leap year is accepted
    ("0999998888\na@b.com\n\n15-06-2015\n5/6/2015\n15/06/2015abc\n29/02/2015\n"
     "31/04/2015\n01/13/2015\n00/01/2015\n29/02/2016\n",
     TITLE + P + E + D + BAD_DATE + D + BAD_DATE + D + BAD_DATE + D + BAD_DATE
     + D + BAD_DATE + D + BAD_DATE + D + BAD_DATE + D + BAD_DATE + D
     + result("0999998888", "a@b.com", "29/02/2016")),
]
