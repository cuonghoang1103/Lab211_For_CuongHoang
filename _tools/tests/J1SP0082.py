"""Keystroke runs for J1.S.P0082 (Playing cards).

The reference transcript matches the brief's screen (title, "Deck created
with 52 cards.", 52 numbered lines, footer, "Total: 52 cards") and adds the
brief's OPTIONAL shuffle/deal part, so the reference runs are kept and this
run is added on top.

The expected 52 lines are generated HERE from the brief's own Guidelines
table (suits Clubs, Diamonds, Hearts, Spades; ranks 2..10, Jack, Queen, King,
Ace; outer loop over suits, inner over ranks) - not captured from the program.
The dealt hand is random, so it is checked by relationships: 5 distinct real
cards, none of them still counted in the deck (47 left).
"""

SUITS = ["Clubs", "Diamonds", "Hearts", "Spades"]
RANKS = ["2", "3", "4", "5", "6", "7", "8", "9", "10",
         "Jack", "Queen", "King", "Ace"]
CARDS = ["%s of %s" % (r, s) for s in SUITS for r in RANKS]


def check(out):
    lines = out.split("\n")
    want = (["========= DECK OF CARDS =========", "Deck created with 52 cards."]
            + ["%d. %s" % (i + 1, c) for i, c in enumerate(CARDS)]
            + ["=================================", "Total: 52 cards",
               "--- After shuffling, dealing 5 cards ---"])
    for i, w in enumerate(want):
        if i >= len(lines) or lines[i] != w:
            return False, "line %d: expected %r, got %r" % (
                i + 1, w, lines[i] if i < len(lines) else None)
    rest = lines[len(want):]
    if len(rest) != 6:
        return False, "expected 5 dealt lines + 1 count line, got %r" % rest
    dealt = []
    for ln in rest[:5]:
        if not ln.startswith("- "):
            return False, "dealt line not in '- card' format: %r" % ln
        dealt.append(ln[2:])
    if len(set(dealt)) != 5:
        return False, "a card was dealt twice: %r" % dealt
    if any(c not in CARDS for c in dealt):
        return False, "a dealt card is not a real card: %r" % dealt
    if rest[5] != "Cards left in the deck: 47":
        return False, "wrong count line: %r" % rest[5]
    return True, ""


RUNS = [
    # the program reads nothing; the whole screen of the brief + the deal part
    ("", check),
]
