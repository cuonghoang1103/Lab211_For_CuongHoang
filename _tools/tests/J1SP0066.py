"""Extra keystroke runs for J1.S.P0066 (car showroom).

The two reference runs already replay the brief's six screens (Car break,
Sell Car, Color Car does not exist, Price greater than zero, Price is digit,
Car can't sell today) plus "Price is not enough" and a real colour the car is
not painted in. These runs add what they miss: the Y/N validation, prices
that parseDouble would wrongly accept, case-insensitive lookups, the exact
price limits of the $100 rule, and a request wrong in several ways at once.
Expected screens are written from the brief, not captured from the program.
"""

HEAD = "===== Showroom car program =====\nInput Information of Car\n"
ASK = "Name: Color: Price: Today: \n"
MORE = "Do you want find more?(Y/N):"
SELL = "Sell Car\n"


def refuse(reason):
    return "Can’t sell Car\n" + reason + "\n"


RUNS = [
    # Y/N validation: blank and "maybe" are refused, lower-case y/n accepted
    ("BMW\nno color\n2400\nTHURSDAY\n\nmaybe\ny\n"
     "bmw\nNO COLOR\n2400\nthursday\nn\n",
     HEAD + ASK + SELL + MORE + "Please input Y or N.\n"
     + MORE + "Please input Y or N.\n" + MORE
     + ASK + SELL + MORE),
    # prices Double.parseDouble would accept but are not digits; blank price
    ("BMW\nRED\nNaN\nMONDAY\nY\n"
     "BMW\nRED\n1e4\nMONDAY\nY\n"
     "BMW\nRED\n5d\nMONDAY\nY\n"
     "BMW\nRED\n\nMONDAY\nY\n"
     "BMW\nRED\n0\nMONDAY\nN\n",
     HEAD + ASK + refuse("Price is digit") + MORE
     + ASK + refuse("Price is digit") + MORE
     + ASK + refuse("Price is digit") + MORE
     + ASK + refuse("Price is digit") + MORE
     + ASK + refuse("Price greater than zero") + MORE),
    # $100 rule on MERCEDES (cheapest 5000): 4900 sells, 4899.99 does not;
    # a painted car must reach its colour's own price (AUDI ORANGE 4500);
    # paying more than the price is fine (options are added on top)
    ("MERCEDES\nno color\n4900\nTUESDAY\nY\n"
     "MERCEDES\nno color\n4899.99\nTUESDAY\nY\n"
     "AUDI\nORANGE\n4500\nMONDAY\nY\n"
     "AUDI\nORANGE\n4499\nMONDAY\nY\n"
     "AUDI\nWHITE\n9999\nSUNDAY\nN\n",
     HEAD + ASK + SELL + MORE
     + ASK + refuse("Price is not enough") + MORE
     + ASK + SELL + MORE
     + ASK + refuse("Price is not enough") + MORE
     + ASK + SELL + MORE),
    # several mistakes at once: only the first reason in the brief's order
    # (car, colour, price, day) is shown; blank name is "Car break"
    ("\ncolor\na\nFRIDAY\nY\n"
     "AUDI\nPINK\na\nnoday\nY\n"
     "AUDI\nWHITE\n-1\nnoday\nY\n"
     "AUDI\nWHITE\n5500\nnoday\nN\n",
     HEAD + ASK + refuse("Car break") + MORE
     + ASK + refuse("Color Car does not exist") + MORE
     + ASK + refuse("Price greater than zero") + MORE
     + ASK + refuse("Car can't sell today") + MORE),
]
