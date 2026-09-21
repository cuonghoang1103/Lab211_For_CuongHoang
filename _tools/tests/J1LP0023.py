"""Keystroke runs for J1.L.P0023 (Fruit Shop).

REPLACE_REFERENCE = True: the reference solution saved fruits.txt / orders.txt and
its run 1 starts from the files run 0 left behind. The brief says "Only use
ArrayList and HashTable to store data", so this solution keeps everything in
memory and every run starts from an empty shop. Run A replays the keystrokes of
reference run 0.

22/09/2026 - the screen follows the brief character for character in three
places (checked against the .docx of the brief):
  1. the sentence under the menu starts with a space, like the brief:
     " (Please choose 1 to create product, ...)."
  2. the cart shown after "Do you want to order now (Y/N)": Y has its header and
     rows one space in, like the brief (" Product | Quantity | Price | Amount",
     " Coconut ..."); "Total: ..." stays at the margin. The reference screen had
     that space too; the version of 21/09 had removed it by mistake.
  3. View orders: an empty line between two customers, like the brief.
The rest of the three runs is unchanged.
"""
REPLACE_REFERENCE = True


def keys(*lines):
    return "\n".join(lines) + "\n"


RUNS = [
    # A: input of the reference run 0: create 3 fruits (duplicate id, price 0), two buyers, stock checks, view orders
    (keys('1', 'F001', 'Coconut', '2', '10', 'Vietnam', 'Y', 'F001', 'F002', 'Orange', '3', '5', 'US', 'Y', 'F003', 'Apple', '0', '4', '2', 'Thailand', 'N', '3', '1', '0', '-2', '3', 'N', '2', '99', '2', '2', 'Y', 'Marry Carie', '2', '3', '1', '8', '1', '7', 'Y', 'John Smith', '2', '4'),
     r'''
FRUIT SHOP SYSTEM
1. Create Fruit
2. View orders
3. Shopping (for buyer)
4. Exit
 (Please choose 1 to create product, 2 to view order, 3 for shopping, 4 to Exit program).
Your choice: Fruit ID: Fruit name: Price: Quantity: Origin: Fruit F001 has been created.
Do you want to continue (Y/N)? Fruit ID: Fruit ID F001 already exists.
Fruit ID: Fruit name: Price: Quantity: Origin: Fruit F002 has been created.
Do you want to continue (Y/N)? Fruit ID: Fruit name: Price: Price must be greater than 0.
Price: Quantity: Origin: Fruit F003 has been created.
Do you want to continue (Y/N)? List of Fruit:
| ++ Item ++ | ++ Fruit Name ++ | ++ Origin ++ | ++ Price ++ | ++ Quantity ++ |
           1  Coconut            Vietnam        2$            10
           2  Orange             US             3$            5
           3  Apple              Thailand       4$            2

FRUIT SHOP SYSTEM
1. Create Fruit
2. View orders
3. Shopping (for buyer)
4. Exit
 (Please choose 1 to create product, 2 to view order, 3 for shopping, 4 to Exit program).
Your choice: List of Fruit:
| ++ Item ++ | ++ Fruit Name ++ | ++ Origin ++ | ++ Price ++ |
           1  Coconut            Vietnam        2$
           2  Orange             US             3$
           3  Apple              Thailand       4$
Please choose item (0 to return to main screen): You selected: Coconut
Please input quantity: Quantity must be greater than 0.
Please input quantity: Quantity must be greater than 0.
Please input quantity: Do you want to order now (Y/N) List of Fruit:
| ++ Item ++ | ++ Fruit Name ++ | ++ Origin ++ | ++ Price ++ |
           1  Coconut            Vietnam        2$
           2  Orange             US             3$
           3  Apple              Thailand       4$
Please choose item (0 to return to main screen): You selected: Orange
Please input quantity: Only 5 Orange left in stock.
List of Fruit:
| ++ Item ++ | ++ Fruit Name ++ | ++ Origin ++ | ++ Price ++ |
           1  Coconut            Vietnam        2$
           2  Orange             US             3$
           3  Apple              Thailand       4$
Please choose item (0 to return to main screen): You selected: Orange
Please input quantity: Do you want to order now (Y/N)  Product | Quantity | Price | Amount
 Coconut                 3       2$       6$
 Orange                  2       3$       6$
Total: 12$
Input your name: Thank you Marry Carie, your order has been saved.

FRUIT SHOP SYSTEM
1. Create Fruit
2. View orders
3. Shopping (for buyer)
4. Exit
 (Please choose 1 to create product, 2 to view order, 3 for shopping, 4 to Exit program).
Your choice: Customer: Marry Carie
Product | Quantity | Price | Amount
1. Coconut                 3       2$       6$
2. Orange                  2       3$       6$
Total: 12$

FRUIT SHOP SYSTEM
1. Create Fruit
2. View orders
3. Shopping (for buyer)
4. Exit
 (Please choose 1 to create product, 2 to view order, 3 for shopping, 4 to Exit program).
Your choice: List of Fruit:
| ++ Item ++ | ++ Fruit Name ++ | ++ Origin ++ | ++ Price ++ |
           1  Coconut            Vietnam        2$
           2  Orange             US             3$
           3  Apple              Thailand       4$
Please choose item (0 to return to main screen): You selected: Coconut
Please input quantity: Only 7 Coconut left in stock.
List of Fruit:
| ++ Item ++ | ++ Fruit Name ++ | ++ Origin ++ | ++ Price ++ |
           1  Coconut            Vietnam        2$
           2  Orange             US             3$
           3  Apple              Thailand       4$
Please choose item (0 to return to main screen): You selected: Coconut
Please input quantity: Do you want to order now (Y/N)  Product | Quantity | Price | Amount
 Coconut                 7       2$      14$
Total: 14$
Input your name: Thank you John Smith, your order has been saved.

FRUIT SHOP SYSTEM
1. Create Fruit
2. View orders
3. Shopping (for buyer)
4. Exit
 (Please choose 1 to create product, 2 to view order, 3 for shopping, 4 to Exit program).
Your choice: Customer: Marry Carie
Product | Quantity | Price | Amount
1. Coconut                 3       2$       6$
2. Orange                  2       3$       6$
Total: 12$

Customer: John Smith
Product | Quantity | Price | Amount
1. Coconut                 7       2$      14$
Total: 14$

FRUIT SHOP SYSTEM
1. Create Fruit
2. View orders
3. Shopping (for buyer)
4. Exit
 (Please choose 1 to create product, 2 to view order, 3 for shopping, 4 to Exit program).
Your choice: Goodbye.'''),
    # B: every validation message: menu, empty shop, blank fields, NaN/Infinity, negative stock, Y/N, item range, out of stock, cancel, same customer twice
    (keys('abc', '9', '2', '3', '1', '', 'A1', '', 'Mango', 'abc', 'NaN', 'Infinity', '-3', '0', '2.5', '1.5', '-1', '3', '', 'Thai', 'x', 'y', 'a1', 'A2', 'Lemon', '1', '0', 'VN', 'n', '3', 'abc', '5', '0', '3', '2', '1', '1', '0', 'abc', '2', 'x', 'N', '1', '2', '0', '3', '1', '1', 'Y', '', 'Anna', '3', '1', '2', 'Y', 'Anna', '2', '3', '1', '1', '0', '1', 'A3', 'Kiwi', '4', '6', 'NZ', 'N', '4'),
     r'''
FRUIT SHOP SYSTEM
1. Create Fruit
2. View orders
3. Shopping (for buyer)
4. Exit
 (Please choose 1 to create product, 2 to view order, 3 for shopping, 4 to Exit program).
Your choice: You must input a number.
Your choice: Please choose from 1 to 4.
Your choice: There is no order yet.

FRUIT SHOP SYSTEM
1. Create Fruit
2. View orders
3. Shopping (for buyer)
4. Exit
 (Please choose 1 to create product, 2 to view order, 3 for shopping, 4 to Exit program).
Your choice: There is no fruit in the shop yet.

FRUIT SHOP SYSTEM
1. Create Fruit
2. View orders
3. Shopping (for buyer)
4. Exit
 (Please choose 1 to create product, 2 to view order, 3 for shopping, 4 to Exit program).
Your choice: Fruit ID: This field must not be empty.
Fruit ID: Fruit name: This field must not be empty.
Fruit name: Price: You must input a number.
Price: You must input a number.
Price: You must input a number.
Price: Price must be greater than 0.
Price: Price must be greater than 0.
Price: Quantity: You must input a number.
Quantity: Quantity must not be negative.
Quantity: Origin: This field must not be empty.
Origin: Fruit A1 has been created.
Do you want to continue (Y/N)? Please enter Y or N.
Do you want to continue (Y/N)? Fruit ID: Fruit ID a1 already exists.
Fruit ID: Fruit name: Price: Quantity: Origin: Fruit A2 has been created.
Do you want to continue (Y/N)? List of Fruit:
| ++ Item ++ | ++ Fruit Name ++ | ++ Origin ++ | ++ Price ++ | ++ Quantity ++ |
           1  Mango              Thai           2.5$          3
           2  Lemon              VN             1$            0

FRUIT SHOP SYSTEM
1. Create Fruit
2. View orders
3. Shopping (for buyer)
4. Exit
 (Please choose 1 to create product, 2 to view order, 3 for shopping, 4 to Exit program).
Your choice: List of Fruit:
| ++ Item ++ | ++ Fruit Name ++ | ++ Origin ++ | ++ Price ++ |
           1  Mango              Thai           2.5$
           2  Lemon              VN             1$
Please choose item (0 to return to main screen): You must input a number.
Please choose item (0 to return to main screen): Please choose from 0 to 2.
Please choose item (0 to return to main screen): 
FRUIT SHOP SYSTEM
1. Create Fruit
2. View orders
3. Shopping (for buyer)
4. Exit
 (Please choose 1 to create product, 2 to view order, 3 for shopping, 4 to Exit program).
Your choice: List of Fruit:
| ++ Item ++ | ++ Fruit Name ++ | ++ Origin ++ | ++ Price ++ |
           1  Mango              Thai           2.5$
           2  Lemon              VN             1$
Please choose item (0 to return to main screen): You selected: Lemon
Please input quantity: Lemon is out of stock.
List of Fruit:
| ++ Item ++ | ++ Fruit Name ++ | ++ Origin ++ | ++ Price ++ |
           1  Mango              Thai           2.5$
           2  Lemon              VN             1$
Please choose item (0 to return to main screen): You selected: Mango
Please input quantity: Quantity must be greater than 0.
Please input quantity: You must input a number.
Please input quantity: Do you want to order now (Y/N) Please enter Y or N.
Do you want to order now (Y/N) List of Fruit:
| ++ Item ++ | ++ Fruit Name ++ | ++ Origin ++ | ++ Price ++ |
           1  Mango              Thai           2.5$
           2  Lemon              VN             1$
Please choose item (0 to return to main screen): You selected: Mango
Please input quantity: Only 1 Mango left in stock.
List of Fruit:
| ++ Item ++ | ++ Fruit Name ++ | ++ Origin ++ | ++ Price ++ |
           1  Mango              Thai           2.5$
           2  Lemon              VN             1$
Please choose item (0 to return to main screen): Your order has been cancelled.

FRUIT SHOP SYSTEM
1. Create Fruit
2. View orders
3. Shopping (for buyer)
4. Exit
 (Please choose 1 to create product, 2 to view order, 3 for shopping, 4 to Exit program).
Your choice: List of Fruit:
| ++ Item ++ | ++ Fruit Name ++ | ++ Origin ++ | ++ Price ++ |
           1  Mango              Thai           2.5$
           2  Lemon              VN             1$
Please choose item (0 to return to main screen): You selected: Mango
Please input quantity: Do you want to order now (Y/N)  Product | Quantity | Price | Amount
 Mango                   1     2.5$     2.5$
Total: 2.5$
Input your name: This field must not be empty.
Input your name: Thank you Anna, your order has been saved.

FRUIT SHOP SYSTEM
1. Create Fruit
2. View orders
3. Shopping (for buyer)
4. Exit
 (Please choose 1 to create product, 2 to view order, 3 for shopping, 4 to Exit program).
Your choice: List of Fruit:
| ++ Item ++ | ++ Fruit Name ++ | ++ Origin ++ | ++ Price ++ |
           1  Mango              Thai           2.5$
           2  Lemon              VN             1$
Please choose item (0 to return to main screen): You selected: Mango
Please input quantity: Do you want to order now (Y/N)  Product | Quantity | Price | Amount
 Mango                   2     2.5$       5$
Total: 5$
Input your name: Thank you Anna, your order has been saved.

FRUIT SHOP SYSTEM
1. Create Fruit
2. View orders
3. Shopping (for buyer)
4. Exit
 (Please choose 1 to create product, 2 to view order, 3 for shopping, 4 to Exit program).
Your choice: Customer: Anna
Product | Quantity | Price | Amount
1. Mango                   3     2.5$     7.5$
Total: 7.5$

FRUIT SHOP SYSTEM
1. Create Fruit
2. View orders
3. Shopping (for buyer)
4. Exit
 (Please choose 1 to create product, 2 to view order, 3 for shopping, 4 to Exit program).
Your choice: List of Fruit:
| ++ Item ++ | ++ Fruit Name ++ | ++ Origin ++ | ++ Price ++ |
           1  Mango              Thai           2.5$
           2  Lemon              VN             1$
Please choose item (0 to return to main screen): You selected: Mango
Please input quantity: Mango is out of stock.
List of Fruit:
| ++ Item ++ | ++ Fruit Name ++ | ++ Origin ++ | ++ Price ++ |
           1  Mango              Thai           2.5$
           2  Lemon              VN             1$
Please choose item (0 to return to main screen): 
FRUIT SHOP SYSTEM
1. Create Fruit
2. View orders
3. Shopping (for buyer)
4. Exit
 (Please choose 1 to create product, 2 to view order, 3 for shopping, 4 to Exit program).
Your choice: Fruit ID: Fruit name: Price: Quantity: Origin: Fruit A3 has been created.
Do you want to continue (Y/N)? List of Fruit:
| ++ Item ++ | ++ Fruit Name ++ | ++ Origin ++ | ++ Price ++ | ++ Quantity ++ |
           1  Mango              Thai           2.5$          0
           2  Lemon              VN             1$            0
           3  Kiwi               NZ             4$            6

FRUIT SHOP SYSTEM
1. Create Fruit
2. View orders
3. Shopping (for buyer)
4. Exit
 (Please choose 1 to create product, 2 to view order, 3 for shopping, 4 to Exit program).
Your choice: Goodbye.'''),
    # C: decimal prices (1.25, 0.1), the same fruit chosen twice in one cart, two customers in order
    (keys('1', 'P1', 'Pear', '1.25', '10', 'China', 'Y', 'P2', 'Plum', '0.1', '10', 'Korea', 'N', '3', '1', '2', 'N', '1', '3', 'N', '2', '3', 'Y', 'Bob', '3', '2', '7', 'Y', 'Carol', '2', '4'),
     r'''
FRUIT SHOP SYSTEM
1. Create Fruit
2. View orders
3. Shopping (for buyer)
4. Exit
 (Please choose 1 to create product, 2 to view order, 3 for shopping, 4 to Exit program).
Your choice: Fruit ID: Fruit name: Price: Quantity: Origin: Fruit P1 has been created.
Do you want to continue (Y/N)? Fruit ID: Fruit name: Price: Quantity: Origin: Fruit P2 has been created.
Do you want to continue (Y/N)? List of Fruit:
| ++ Item ++ | ++ Fruit Name ++ | ++ Origin ++ | ++ Price ++ | ++ Quantity ++ |
           1  Pear               China          1.25$         10
           2  Plum               Korea          0.1$          10

FRUIT SHOP SYSTEM
1. Create Fruit
2. View orders
3. Shopping (for buyer)
4. Exit
 (Please choose 1 to create product, 2 to view order, 3 for shopping, 4 to Exit program).
Your choice: List of Fruit:
| ++ Item ++ | ++ Fruit Name ++ | ++ Origin ++ | ++ Price ++ |
           1  Pear               China          1.25$
           2  Plum               Korea          0.1$
Please choose item (0 to return to main screen): You selected: Pear
Please input quantity: Do you want to order now (Y/N) List of Fruit:
| ++ Item ++ | ++ Fruit Name ++ | ++ Origin ++ | ++ Price ++ |
           1  Pear               China          1.25$
           2  Plum               Korea          0.1$
Please choose item (0 to return to main screen): You selected: Pear
Please input quantity: Do you want to order now (Y/N) List of Fruit:
| ++ Item ++ | ++ Fruit Name ++ | ++ Origin ++ | ++ Price ++ |
           1  Pear               China          1.25$
           2  Plum               Korea          0.1$
Please choose item (0 to return to main screen): You selected: Plum
Please input quantity: Do you want to order now (Y/N)  Product | Quantity | Price | Amount
 Pear                    5    1.25$    6.25$
 Plum                    3     0.1$     0.3$
Total: 6.55$
Input your name: Thank you Bob, your order has been saved.

FRUIT SHOP SYSTEM
1. Create Fruit
2. View orders
3. Shopping (for buyer)
4. Exit
 (Please choose 1 to create product, 2 to view order, 3 for shopping, 4 to Exit program).
Your choice: List of Fruit:
| ++ Item ++ | ++ Fruit Name ++ | ++ Origin ++ | ++ Price ++ |
           1  Pear               China          1.25$
           2  Plum               Korea          0.1$
Please choose item (0 to return to main screen): You selected: Plum
Please input quantity: Do you want to order now (Y/N)  Product | Quantity | Price | Amount
 Plum                    7     0.1$     0.7$
Total: 0.7$
Input your name: Thank you Carol, your order has been saved.

FRUIT SHOP SYSTEM
1. Create Fruit
2. View orders
3. Shopping (for buyer)
4. Exit
 (Please choose 1 to create product, 2 to view order, 3 for shopping, 4 to Exit program).
Your choice: Customer: Bob
Product | Quantity | Price | Amount
1. Pear                    5    1.25$    6.25$
2. Plum                    3     0.1$     0.3$
Total: 6.55$

Customer: Carol
Product | Quantity | Price | Amount
1. Plum                    7     0.1$     0.7$
Total: 0.7$

FRUIT SHOP SYSTEM
1. Create Fruit
2. View orders
3. Shopping (for buyer)
4. Exit
 (Please choose 1 to create product, 2 to view order, 3 for shopping, 4 to Exit program).
Your choice: Goodbye.'''),
]
