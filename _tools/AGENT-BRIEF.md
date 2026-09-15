# Brief for writing one LAB211 solution (Fall 2026, lecturer Nguyen Van An)

> ## ⚠️ ADDENDUM (supersedes anything below that conflicts) — the lecturer's IN-CLASS rules
> Recorded by the student in class 07/09 (QUY-TAC-THAY.md §9, codes V1–V10). lint.py enforces them.
> 1. **V4 — no method takes 3 parameters.** Business methods (controller, service, repository,
>    view, model, main helpers) take **at most 2** parameters; pack more data into a DTO/model.
>    utils may take 3 (the Guide sample `getChoice(input, min, max)`); constructors are exempt.
>    When a brief-mandated signature has 3+ parameters, keep the METHOD NAME, take a DTO/model
>    instead, and list it in HUONG-DAN §9.
> 2. **V5 — declare concrete collection types**: `ArrayList<X> x = new ArrayList<>()`,
>    `HashMap`/`LinkedHashMap`, never `List`/`Map`/`Set` — except where the brief dictates the
>    signature, and then that line (or the one above) carries a `// brief: ...` comment.
>    HUONG-DAN §7 must answer "tại sao ArrayList mà không phải List / khác nhau thế nào".
> 3. **V7 — Design Patterns are what this lecturer values MOST.** Apply the pattern(s) that fit
>    each lab (table in QUY-TAC-THAY.md §7): Strategy for any sort/search/calculation that has or
>    could have alternatives (every sorting lab: `SortStrategy` interface + one concrete class,
>    injected into the service through its constructor — see the golden P0001), Factory Method to
>    create the right subclass by type, Template Method for abstract base skeletons, Builder for
>    entities with many fields (>= 6), Facade = the controller. HUONG-DAN gets a subsection
>    "Design Pattern" presenting each pattern with the 4 GoF elements: Name · Problem · Solution
>    (which classes play which participant) · Consequences (benefit + cost), and the exact change
>    the lecturer can ask ("thêm một thuật toán nữa" → add one class).
> 4. **V8 — "code model trước rồi đến data"**: HUONG-DAN §4 typing order starts with **model**,
>    then data (dto, repository), then service, controller, view, constants as needed, main last.
> 5. **V10 — MVC "JSP"**: every model and DTO class is a **JavaBean**: private fields, a
>    **public no-argument constructor**, getters/setters (plus a full constructor if useful).
> 6. **V2/V3 — access modifiers and static are what he rejects most**: every field private; a
>    method public only if another class calls it, otherwise private; static only in utils,
>    constants, and main's helper METHODS. HUONG-DAN §7 justifies every non-private member and
>    every static with one sentence.
> The golden projects P0001 and P0055 have been updated to these rules — re-read them.

You write complete, verified NetBeans projects for the LAB211 labs assigned to
you, in the lecturer's architecture. A student who has failed this course five
times will study these to pass. Correctness and exact compliance with the
lecturer's rules matter more than anything else. Never claim a lab is done
unless `verify.py` prints ✓ for it.

Root: `/Users/admin/Documents/Source_Lab211_ForCuongThai/`

## 0. Read first (all of it, in this order)

1. `QUY-TAC-THAY.md` — the lecturer's rules, each quoted from his files. This is
   the contract. Especially §3 (architecture), §5 (comments), §8 (brief vs rules).
2. The two golden projects — read EVERY file, then copy their style exactly:
   * `HE176322_J1SP0055_DoctorManagement/` — CRUD lab with repository
   * `HE176322_J1SP0001_BubbleSort/` — algorithm lab with service, no repository
   and their `HUONG-DAN.md` (the study guide format you must reproduce).
3. `_tools/lint.py` (what is enforced), `_tools/verify.py` (how it is tested),
   `_tools/tests/J1SP0055.py`, `_tools/tests/J1SP0001.py` (test file format).

## 1. Inputs for each lab

* The brief as text: `/private/tmp/claude-501/-Users-admin-Downloads-api-backend/7690edf8-4823-4219-a1c4-b24cb01be340/scratchpad/briefs/NN_<CODE>.txt`
* Every `[IMAGE: url]` in the brief is part of the spec (often the whole expected
  screen). Download and LOOK at each one:
  `curl -s -A 'Mozilla/5.0' -o /tmp/<name>.png '<url>'` then open it with the Read tool.
  (The server answers 403 without the browser User-Agent.)
* The previous reference solution for the same lab (old architecture, already
  verified against the brief, with keystroke runs). Print it with:
  ```bash
  cd /Users/admin/Downloads/api-backend/docs/codelab-authoring/lab211/solutions && python3 - <<'EOF'
  import importlib.util, sys, glob, inspect
  sys.path.insert(0, '.')
  import solkit
  for f in sorted(glob.glob('batch*.py')):
      spec = importlib.util.spec_from_file_location(f[:-3], f); m = importlib.util.module_from_spec(spec); spec.loader.exec_module(m)
  s = {s['lab']: s for s in solkit.SOLUTIONS}['J1.S.P0060']   # <- the lab
  for p, code in s['files']: print('#'*20, p); print(code)
  for i,(inp, exp) in enumerate(s['runs']):
      print('=====RUN', i, repr(inp)); print(inspect.getsource(exp) if callable(exp) else exp)
  EOF
  ```
  Read it for behaviour, messages and edge cases. Do NOT copy its architecture
  (entity/bo/ui, Scanner in Validator) — that is exactly what the lecturer rejects.
  NEVER edit anything under `/Users/admin/Downloads/api-backend`.

## 2. Screen / output — what is the source of truth

1. **The brief's expected screen (text or image) and Guidelines messages win.**
2. The reference runs are a second oracle. By default `verify.py` replays them
   against your program (main class `main.Main`) and demands identical output.
   * If the reference transcript matches the brief (or the brief is silent on
     those lines), keep your output IDENTICAL to it — same prompts, same spacing.
   * If the reference transcript contradicts the brief's screen (different
     prompt text, different labels, different format), follow the BRIEF, and in
     `_tools/tests/<CODE>.py` set `REPLACE_REFERENCE = True` and write runs that
     match the brief. Say in HUONG-DAN §9 what differs and why.
   * When the brief's screen and its Guidelines disagree, follow Guidelines and
     say so in HUONG-DAN §9.
3. **Always** write `_tools/tests/<CODE>.py` with extra runs so that EVERY menu
   option and EVERY validation/error message of the brief is exercised at least
   once (lecturer: "test tất cả các happy case cũng như hiển thị đủ các message
   validation"). Write the expected text FROM THE BRIEF before running; when the
   program disagrees, decide who is wrong by re-reading the brief — never paste
   program output into the test without checking it line by line.
   Random output → a predicate function `check(out) -> (ok, why)` that checks
   fixed lines exactly and random parts by relationship (see J1SP0001.py).
   A run must end with the program exiting normally (the script must choose
   Exit); stdin ending early crashes Scanner and fails the run.

## 3. Architecture (non-negotiable — lint enforces most of it)

Folder: `HE176322_<CODE>_<Name>/src/<package>/...` (name given in your task).

Always:
```
constants/  Message.java (every user-visible string), Constants.java (numbers,
            formats, regex, file names), enums (enums live here: Guide says
            Constants holds "hằng số, enum"; enums may have static lookups)
controller/ <Domain>Controller.java  — no static, no Scanner, no System.out,
            imports only DTO/view/service/repository/constants/exceptions (NOT model)
dto/        <Domain>RequestDTO (main -> controller), <Domain>ResponseDTO
            (controller -> view). A lab with no input may have no RequestDTO.
main/       Main.java — Scanner ONLY here, as a LOCAL variable passed to static
            helper methods; no static fields; never imports model/view/
            repository/service; each workflow calls the controller once
            (a pre-check call like checkExistDoctor is allowed; explain it)
model/      entity classes: private fields, constructors, get/set, toString();
            no static, no Scanner, no printing; may hold the object's own
            behaviour (Wallet.payMoney, Bee.damage, Shape.getArea)
utils/      Validation.java (+ FileUtils.java for file read/write, + other
            *Utils for hashing/captcha): public final class, private ctor,
            ONLY static methods; takes the String main read, returns the clean
            value or throws Exception(Message.X); never reads the keyboard
view/       <Domain>View.java — the only printer besides main; holds the
            ResponseDTO(s) via setter + display() (Guide sample style), plus
            showMessage(String) for one-line results
```
Add when needed:
* `repository/` — the program KEEPS a collection and does CRUD on it
  (add/update/delete/find/load/save). Converts model -> ResponseDTO.
* `service/` — business computation beyond CRUD: totals, areas, reports,
  algorithms (sort/search/convert/multiply), statistics. Called only from the
  controller; may use model and DTO; no I/O. Flow: Controller <-> Service <->
  Repository <-> Model.
* `exceptions/` — only when the brief asks for a custom exception class.

Main prints the menu, section titles and prompts (from Message); every error
from Validation/controller is caught in Main and printed with `e.getMessage()`.
Results are printed by the view, called from the controller.

## 4. Brief-mandated names ("Student must implement methods ... in startup code")

* Keep every mandated **method name** exactly (including odd ones like `Damage`,
  `checkln`/`checkIn`), and the declared return type where possible.
* Put it in the layer the lecturer's rules require (computation -> service,
  CRUD -> repository, printing -> view, input check -> utils/Validation).
* Where the brief's parameter list is impossible in that layer (e.g. passes a
  model object into the controller), keep the name, adapt the parameters to the
  Guide's DTO flow, and list the change in HUONG-DAN §9 with the reason.
* Mandated class names (Person, Wallet, Shape, Bee, MyStack, Card, Deck,
  EastAsiaCountries...) are kept exactly, in `model/` unless they are enums.

## 5. Code rules

* Java 8 only (the lab has JDK 8; `--release 8` makes newer API a compile
  error): no `var`, no `switch` arrows, no `List.of`/`Map.of`, no
  `String.repeat/isBlank/strip/lines`, no `Files.readString`.
* Read input only with `sc.nextLine()` then parse. Never `nextInt()`.
* Never `System.exit` — leave loops with a boolean flag / return.
* Pin decimals: `String.format(Locale.US, ...)` whenever a %f/%.2f is printed.
* Comments (lint enforces; lecturer: "comment ít nhất cho function và
  block/rẽ nhánh"): Javadoc on every class (what + why in this package + `@author HE176322`),
  every method/constructor (what, `@param`, `@return` with WHY that type,
  `@throws`), a comment on every field, and a one-line comment on/just above/as
  the first line inside EVERY `if`, `else`, `for`, `while`, `do`, `switch`,
  `case`, `default`, `try`, `catch`. Comments in English, explaining intent.
* Formatting = NetBeans Alt+Shift+F: 4 spaces, `if (`, `) {`, `} else {`,
  braces always, one statement per line, lines <= 100 chars (aim 80).
* No magic numbers outside constants (CheckStyle MagicNumber: -1,0,1,2 are ok).
* Every switch has `default`. `@Override` on every override.
* Sun order inside a class: static constants, fields, constructors, methods.
* Data files the program reads live at the PROJECT ROOT (next to build.xml);
  file names come from Constants. If the reference program created demo data
  on first run and its runs depend on it, do the same (in utils/FileUtils or
  the repository), and ship the data file at the project root when the brief
  provides one.

## 6. SOLID / design patterns (lecturer: "Implement và hiểu SOLID được cộng LOC")

* S everywhere (that is what the layers are).
* O/L with class families (abstract base + subclasses, no instanceof chains
  where polymorphism works — except where the brief demands instanceof).
* Use a pattern only where the lab naturally needs it (Strategy for several
  interchangeable sort/search orders, Factory for creating the right subclass
  by type, Template Method for a base class skeleton). No empty abstractions.
* J1.L.P0022 (Candidate): the lecturer says it needs FULL SOLID — interfaces for
  repository (DIP), abstract Candidate (LSP/OCP), small interfaces (ISP).
* HUONG-DAN §7 must say exactly where each principle/pattern is, by file+method.

## 7. The study guide `HUONG-DAN.md` (Vietnamese, per lab)

Same 9 sections, same style as the golden guides: short, tables, no walls of
text, bold only for key points. Sections:
0 header table · 1 Đề bài nói gì (+ bảng "Đề bắt buộc") · 2 Kiến thức cần biết
(algorithm explained step by step with a worked table using the brief's example;
Java APIs used) · 3 Thiết kế (tree + table class→role→why + flow) · 4 Code từng
bước (typing order table + common traps) · 5 Test trước khi gọi thầy (table:
#, gõ, phải thấy — every happy path and every message) · 6 Debug (breakpoint
where, F7/F8, what to watch) · 7 Câu hỏi thầy hay hỏi (OOP 4 pillars pointing
at files; access modifiers; static + "bỏ static thì sao"; return types; SOLID;
algorithm/complexity; tricky edge cases) · 8 Thầy đổi yêu cầu tại chỗ (table:
request → files to edit → files untouched) · 9 Chỗ khác với đề / bản cũ (table).
Every fact in the guide must match the code you wrote (method names, messages,
file names). Do not invent lecturer rules; cite QUY-TAC-THAY.md when you say
"thầy".

## 8. Finish each lab

```bash
cd /Users/admin/Documents/Source_Lab211_ForCuongThai
python3 _tools/make_netbeans.py HE176322_<CODE>_<Name>
python3 _tools/verify.py --netbeans <CODE>
```
Iterate until ✓. Do not edit `_tools/*.py` or other labs' folders. If a lint
rule looks wrong for a legitimate construct, do NOT work around it by
obfuscation — report it in your final message. A `.lint-allow` file with
`protected_ok=field1,field2` is allowed ONLY for fields the brief itself
declares `protected`.

Final message: per lab — ✓/✗, number of runs, which brief messages are covered,
every deviation from the brief or the reference (and why), anything you are
unsure of. Be factual; do not overstate.
