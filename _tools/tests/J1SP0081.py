"""Extra keystroke runs for J1.S.P0081 (Bees).

The reference runs are kept (REPLACE_REFERENCE is False): they check the first
attack by its own arithmetic. These runs add the exact menu/validation lines,
the exact "create" table (it is not random: 30 bees at 100.00 %), and several
attacks in a row, where the damage compounds on the CURRENT health and dead
bees stay frozen.

Layout measured on the brief's sample: "1  Worker  100.00 %   Alive"
(number, two spaces, type padded to 8, health padded to 11) and
"1  Worker    12   88.00 %    Alive" (type padded to 9, damage right-aligned
in 3, three spaces, health padded to 11).

The attack predicate replays the program's arithmetic in Python floats (the
same IEEE doubles as Java): new = old * (100 - dmg) / 100.0 unless the bee
was already dead. Status is compared exactly; the printed health within
0.0051 (Java and Python may round an exact .xx5 tie differently).
"""
import re

MENU = ("============ BEE SIMULATION ============\n"
        "1. Create bee list\n"
        "2. Attack bees\n"
        "0. Exit\n"
        "========================================\n"
        "Your choice: ")
TYPES = ["Worker"] * 10 + ["Queen"] * 10 + ["Drone"] * 10
THRESHOLD = {"Worker": 70, "Queen": 20, "Drone": 50}


def created_table():
    """The exact screen of option 1 (brief, Function 1)."""
    lines = ["New bee list created: 10 Workers, 10 Queens, 10 Drones.",
             "No  Type    Health     Status",
             "-" * 30]
    for i, kind in enumerate(TYPES, 1):
        lines.append("%d  %-8s%-11s%s" % (i, kind, "100.00 %", "Alive"))
    lines += ["-" * 30, "Alive: 30   Dead: 0"]
    return "\n".join(lines) + "\n"


RUN_ROW = re.compile(r"^(\d+)  (Worker|Queen|Drone) *(\d+)   ([\d.]+) % *(Alive|Dead)$")


def attacks(n_attacks, recreate_after=False):
    """Predicate: option 1, then n attacks, (optionally option 1 again), exit."""
    def check(out):
        created = MENU + created_table()
        if not out.startswith(created):
            return False, "the create screen differs from the brief's layout"
        rest = out[len(created):]
        health = [100.0] * 30
        for a in range(n_attacks):
            head = (MENU + "Attacking all bees (random damage 0-80% each)...\n"
                    "No  Type     Dmg   Health     Status\n" + "-" * 37 + "\n")
            if not rest.startswith(head):
                return False, "attack %d: header lines differ" % (a + 1)
            rest = rest[len(head):]
            lines = rest.split("\n")
            rows, rest_lines = lines[:30], lines[30:]
            alive = 0
            for i, line in enumerate(rows):
                m = RUN_ROW.match(line)
                if not m or int(m.group(1)) != i + 1 or m.group(2) != TYPES[i]:
                    return False, "attack %d: bad row %r" % (a + 1, line)
                expected_line = "%d  %-9s%3s   %-11s%s" % (
                    i + 1, TYPES[i], m.group(3), m.group(4) + " %", m.group(5))
                if line != expected_line:
                    return False, "attack %d: spacing differs: %r" % (a + 1, line)
                dmg = int(m.group(3))
                if not 0 <= dmg <= 80:
                    return False, "damage %d outside [0, 80]" % dmg
                kind = TYPES[i]
                if health[i] >= THRESHOLD[kind]:
                    health[i] = health[i] * (100 - dmg) / 100.0
                # else: dead bee, frozen
                if abs(float(m.group(4)) - health[i]) > 0.0051:
                    return False, "attack %d, bee %d: health %s, expected %.4f" % (
                        a + 1, i + 1, m.group(4), health[i])
                want = "Dead" if health[i] < THRESHOLD[kind] else "Alive"
                if m.group(5) != want:
                    return False, "attack %d, bee %d at %r should be %s" % (
                        a + 1, i + 1, health[i], want)
                alive += want == "Alive"
            tail = ["-" * 37, "Alive: %d   Dead: %d" % (alive, 30 - alive)]
            if rest_lines[:2] != tail:
                return False, "attack %d: summary %r, expected %r" % (
                    a + 1, rest_lines[:2], tail)
            rest = "\n".join(rest_lines[2:])
        if recreate_after:
            if not rest.startswith(created):
                return False, "option 1 did not rebuild a fresh colony"
            rest = rest[len(created):]
        if rest != MENU + "Goodbye.":
            return False, "the run did not end with the menu and Goodbye.: %r" % rest[:200]
        return True, ""
    return check


RUNS = [
    # every menu message: letter, empty line, out of range both sides;
    # option 2 before option 1 is refused with a clear message
    ("x\n\n3\n-1\n2\n0\n",
     MENU + "You must input a number.\n"
     "Your choice: You must input a number.\n"
     "Your choice: Please choose from 0 to 2.\n"
     "Your choice: Please choose from 0 to 2.\n"
     "Your choice: There is no bee list yet. Choose 1 first.\n"
     + MENU + "Goodbye."),

    # option 1 exactly as the brief's Function 1 screen
    ("1\n0\n", MENU + created_table() + MENU + "Goodbye."),

    # five attacks in a row: damage compounds, dead bees stay frozen,
    # then option 1 clears the list and rebuilds 30 bees at 100 %
    ("1\n2\n2\n2\n2\n2\n1\n0\n", attacks(5, recreate_after=True)),

    # one more long run to meet more random boundary cases (e.g. a queen
    # hit for exactly 80 must stay Alive at 20.00 %)
    ("1\n2\n2\n2\n0\n", attacks(3)),
]
