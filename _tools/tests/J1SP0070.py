"""Extra keystroke runs for J1.S.P0070 (Ebank login, two languages, captcha).

The reference runs are KEPT (REPLACE_REFERENCE stays False): their console is
the brief's own screen - the sheet's four bad account numbers and four bad
passwords, in Vietnamese and in English, then a captcha fed one character at a
time - and this program prints it identically.

The captcha is random, so every run here is a predicate: it reads the captcha
out of the console, rebuilds the WHOLE expected transcript from it (every
prompt, every error line), and compares exactly. Captcha attempts are chosen
so the outcome is known whatever the captcha is: "" (always refused - Java's
"X".contains("") is true, the program must refuse it anyway), lower-case
letters and symbols (never in an A-Z0-9 captcha), the full alphabet as ONE
string (longer than the captcha, never contained), then every single
character (one of them is always in the captcha).
"""
import re

VI = {
    'account.prompt': 'So tai khoan:  ',
    'account.error': 'So tai khoan phai la 1 so va phai co 10 chu so',
    'password.prompt': 'Mat khau: ',
    'password.error': 'Mat khau phai trong khoang 8-31 ky tu va phai chua ky tu va so',
    'captcha.label': 'Captcha: ',
    'captcha.prompt': 'Nhap 1 ky tu captcha: ',
    'captcha.error': 'Captcha sai',
    'login.success': 'Dang nhap thanh cong',
}
EN = {
    'account.prompt': 'Account number:  ',
    'account.error': 'Account number must is a number and must have 10 digits',
    'password.prompt': 'Password: ',
    'password.error': 'Password must be between 8 and 31 characters and must be alphanumeric',
    'captcha.label': 'Captcha incorrect: ',
    'captcha.prompt': 'Enter a Captcha incorrect characters: ',
    'captcha.error': 'Captcha incorrect',
    'login.success': 'Login successfully',
}
MENU = ('-------Login Program-------\n'
        '1. Vietnamese\n'
        '2. English\n'
        '3. Exit\n'
        'Please choice one option: ')
ALPHABET = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789'
FEED = ['', 'h', '!', ' ', ALPHABET] + list(ALPHABET)


def stdin(choice, accounts, passwords):
    return '\n'.join([choice] + accounts + passwords + FEED) + '\n'


def login_check(msg, menu_errors, accounts, passwords):
    """accounts/passwords: every line typed; all but the last are refused."""
    def check(out):
        m = re.search(re.escape(msg['captcha.label']) + r'([A-Z0-9]{5})\n', out)
        if not m:
            return False, 'no 5-character A-Z0-9 captcha was printed'
        captcha = m.group(1)
        exp = MENU
        for e in menu_errors:
            exp += e + '\n' + 'Please choice one option: '
        for _ in accounts[:-1]:
            exp += msg['account.prompt'] + msg['account.error'] + '\n'
        exp += msg['account.prompt']
        for _ in passwords[:-1]:
            exp += msg['password.prompt'] + msg['password.error'] + '\n'
        exp += msg['password.prompt']
        exp += msg['captcha.label'] + captcha + '\n'
        for fed in FEED:
            if fed and fed in captcha:
                exp += msg['captcha.prompt'] + msg['login.success']
                break
            exp += msg['captcha.prompt'] + msg['captcha.error'] + '\n'
        if out.strip() != exp.strip():
            return False, 'console differs from what captcha %s implies:\n%s' % (captcha, exp)
        return True, ''
    return check


# account numbers the brief's rule must refuse, then a good one
ACC = ['', '01234567890', '012345678a', ' 0123456789', 'abc0123456789xyz', '9876543210']
# passwords: 7 chars, symbol, space inside, 32 chars, then 31 chars (limit)
PWD = ['abc1234', 'abc12345!', 'abc 12345', 'a' * 31 + '1', 'a' * 30 + '1']

RUNS = [
    # English after two wrong menu choices; every account/password edge case;
    # the captcha: empty, lower case, symbol, space, too long, then letters
    ('0\nx\n2\n' + stdin('', ACC, PWD)[1:],
     login_check(EN, ['Please choose from 1 to 3.', 'You must input a number.'], ACC, PWD)),
    # Vietnamese, the first account and password already valid
    (stdin('1', ['0000000000'], ['A1bcdefg']),
     login_check(VI, [], ['0000000000'], ['A1bcdefg'])),
    # Exit straight away: nothing after the menu
    ('3\n', MENU),
]
