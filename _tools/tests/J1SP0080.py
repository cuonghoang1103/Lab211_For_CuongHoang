"""Keystroke run for J1.S.P0080 (Shapes) - the program reads no input.

REPLACE_REFERENCE: the reference program used its own column widths
("No  Shape                                Area     Volume", area ending at
column 45) while the brief's sample report puts the numbers elsewhere
("1  Circle [r=2.00]                12.57          -", area ending at column
39). The brief wins, so the expected screen below is the brief's sample,
character for character (minus the NetBeans line "BUILD SUCCESSFUL ...",
which the IDE prints, not the program).

Numbers checked by hand from the brief's formulas:
Circle pi*2^2 = 12.566 -> 12.57; Square 3^2 = 9.00; Triangle 4*5/2 = 10.00;
Sphere 4*pi*2^2 = 50.265 -> 50.27, (4/3)*pi*2^3 = 33.510 -> 33.51;
Cube 6*3^2 = 54.00, 3^3 = 27.00;
Tetrahedron sqrt(3)*4^2 = 27.713 -> 27.71, 4^3/(6*sqrt(2)) = 7.542 -> 7.54.
The vi_VN locale run proves the decimals stay "12.57", not "12,57".
"""

REPLACE_REFERENCE = True

LINE = "=" * 61

RUNS = [
    ("",
     LINE + "\n"
     "No  Shape                          Area        Volume\n"
     + LINE + "\n"
     "1  Circle [r=2.00]                12.57          -\n"
     "2  Square [side=3.00]              9.00          -\n"
     "3  Triangle [base=4.00, h=5.00]   10.00          -\n"
     "4  Sphere [r=2.00]                50.27        33.51\n"
     "5  Cube [side=3.00]               54.00        27.00\n"
     "6  Tetrahedron [side=4.00]        27.71         7.54\n"
     + LINE),
]
