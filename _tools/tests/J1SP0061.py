"""Extra keystroke runs for J1.S.P0061 (Rectangle / Circle / Triangle).

The reference runs are kept (REPLACE_REFERENCE is False): run 0 is the brief's
own screen character for character, run 1 the impossible-triangle retry.
These runs add every validation message and the decimal / flat-triangle
edges. Expected screens are written from the brief and the formulas:
Rectangle 2.5 x 4 -> area 10.0, perimeter 13.0; Circle r=1 -> area PI,
perimeter 2*PI; Triangle 3-4-5 -> Heron p=6, sqrt(6*3*2*1) = 6.0.
"""

TITLE = "=====Calculator Shape Program=====\n"
WIDTH = "Please input side width of Rectangle:\n"
LENGTH = "Please input length of Rectangle:\n"
RADIUS = "Please input radius of Circle:\n"
SIDE_A = "Please input side A of Triangle:\n"
SIDE_B = "Please input side B of Triangle:\n"
SIDE_C = "Please input side C of Triangle:\n"
NUMBER = "You must input a number.\n"
POSITIVE = "Value must be greater than zero.\n"
TRIANGLE = "These three sides cannot form a triangle. Please input again.\n"

RESULT_345 = ("-----Triangle-----\n"
              "Side A: 3.0\n"
              "Side B: 4.0\n"
              "Side C: 5.0\n"
              "Area:6.0\n"
              "Perimeter:12.0")

RUNS = [
    # every validation message: letters, empty line, zero, negative, NaN,
    # Infinity; a decimal is accepted; a flat triangle (1+2 = 3) and an
    # impossible one (1, 2, 10) are refused and ALL three sides re-asked
    ("abc\n\n0\n-3\nNaN\n2.5\n4\nInfinity\n1\n1\n2\n3\n1\n2\n10\n3\n4\n5\n",
     TITLE
     + WIDTH + NUMBER + WIDTH + NUMBER + WIDTH + POSITIVE + WIDTH + POSITIVE
     + WIDTH + NUMBER + WIDTH
     + LENGTH
     + RADIUS + NUMBER + RADIUS
     + SIDE_A + SIDE_B + SIDE_C + TRIANGLE
     + SIDE_A + SIDE_B + SIDE_C + TRIANGLE
     + SIDE_A + SIDE_B + SIDE_C
     + "-----Rectangle-----\n"
     "Width: 2.5\n"
     "Length: 4.0\n"
     "Area: 10.0\n"
     "Perimeter: 13.0\n"
     "-----Circle-----\n"
     "Radius: 1.0\n"
     "Area:3.141592653589793\n"
     "Perimeter:6.283185307179586\n"
     + RESULT_345),

    # an error in a triangle side is re-asked for THAT side only, before
    # the triangle check runs
    ("1\n1\n1\n3\nx\n4\n-5\n5\n",
     TITLE + WIDTH + LENGTH + RADIUS
     + SIDE_A + SIDE_B + NUMBER + SIDE_B + SIDE_C + POSITIVE + SIDE_C
     + "-----Rectangle-----\n"
     "Width: 1.0\n"
     "Length: 1.0\n"
     "Area: 1.0\n"
     "Perimeter: 4.0\n"
     "-----Circle-----\n"
     "Radius: 1.0\n"
     "Area:3.141592653589793\n"
     "Perimeter:6.283185307179586\n"
     + RESULT_345),
]
