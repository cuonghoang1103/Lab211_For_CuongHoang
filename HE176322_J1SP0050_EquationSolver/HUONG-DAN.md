# J1.S.P0050 — Equation Solver (bậc nhất, bậc hai, số chẵn/lẻ/chính phương)

> Bài thuật toán **vẫn phải MVC**. Hai loại phương trình làm **cùng một chuỗi bước**, chỉ khác
> "hệ số nào" và "giải thế nào" → đúng chỗ của **Template Method**.

| | |
|---|---|
| Loại / LOC | Short Assignment · 72 LOC · 1 slot |
| Project | `HE176322_J1SP0050_EquationSolver` |
| Chạy | NetBeans: **File ▸ Open Project** → **F6** |
| Lớp chạy | `main.Main` |
| Kiểm tự động | `python3 _tools/verify.py J1SP0050` → 4 kịch bản × 2 locale |

---

## 1. Đề bài nói gì

- Menu: **1. Calculate Superlative Equation** (ax + b = 0) · **2. Calculate Quadratic Equation** (ax² + bx + c = 0) · **3. Exit**.
- Nhập hệ số (sai → `Please input number`), in nghiệm với **3 chữ số thập phân**.
- Từ **các hệ số và các nghiệm**, in số lẻ, số chẵn, số chính phương.

Màn hình đề (chép đúng):

```
Solution: x = -1.250
Number is Odd:5.0, -1.25
Number is Even:4.0
Number is Perfect Square:4.0
```
```
Solution: x1 = -0.500 and x2 = -0.500
Odd Number(s):1.0, -0.5, -0.5
Number is Even:4.0, 4.0
Number is Perfect Square:4.0, 4.0, 1.0
```

**Đề bắt buộc:**

| Thứ | Đề viết | Bài này đặt ở |
|---|---|---|
| `public List<Float> calculateEquation(float a, float b)` | null = vô nghiệm, rỗng = vô số nghiệm | `EquationSolver.calculateEquation` (lớp cha, **protected**) |
| `public List<Float> calculateQuadraticEquation(float a, float b, float c)` | 3 tham số | `QuadraticEquationSolver.calculateQuadraticEquation(EquationRequestDTO)` |
| `Float checkin(String floatString)` (gợi ý) | lớp `Number` | `utils/Validation.checkin` (static) |
| `isOdd(float)`, `isPerfectSquare(float)` (gợi ý) | lớp `Number`; lẻ = `a % 2 != 0`; chính phương dùng `Math.sqrt` | `service/NumberChecker` (+ `isEven`) |

---

## 2. Kiến thức cần biết

### 2.1 Bậc nhất ax + b = 0

| Trường hợp | Kết quả | Trả về (quy ước của đề) |
|---|---|---|
| a ≠ 0 | x = −b / a | `[x]` |
| a = 0, b = 0 | mọi x đều đúng | **danh sách rỗng** → `The equation has infinitely many solutions.` |
| a = 0, b ≠ 0 | không x nào đúng | **null** → `The equation has no solution.` |

### 2.2 Bậc hai ax² + bx + c = 0 — chạy tay ví dụ của đề (4, 4, 1)

| Bước | Tính | Ví dụ |
|---|---|---|
| a = 0? | nếu có → chuyển sang bậc nhất bx + c = 0 | 4 ≠ 0 |
| Δ = b² − 4ac | | 16 − 16 = **0** |
| Δ < 0 | vô nghiệm → `null` | — |
| Δ = 0 | nghiệm kép −b / 2a, **thêm 2 lần** | −4/8 = **−0.5, −0.5** |
| Δ > 0 | (−b ± √Δ) / 2a | — |

Vì sao thêm nghiệm kép **2 lần**? Màn hình đề in `x1 = -0.500 and x2 = -0.500` và liệt kê `-0.5` **hai lần** trong số lẻ.

### 2.3 Chia số — thứ tự "hệ số trước, nghiệm sau"

(4, 4, 1) → các số `4, 4, 1, -0.5, -0.5`:

| Số | `% 2` | Lẻ/Chẵn | Chính phương? |
|---|---|---|---|
| 4.0 | 0 | chẵn | ✅ (2²) |
| 4.0 | 0 | chẵn | ✅ |
| 1.0 | 1 | **lẻ** | ✅ (1²) |
| -0.5 | -0.5 | **lẻ** (luật của đề: `% 2 != 0`) | ❌ không nguyên |
| -0.5 | -0.5 | lẻ | ❌ |

→ đúng 3 dòng của đề. Chính phương: **≥ 0**, **nguyên**, và `round(√x)² == x` (làm tròn tránh 4.9999).

### 2.4 Java dùng trong bài

| API | Dùng làm gì |
|---|---|
| `Float.valueOf(s)` | chuỗi → `Float`; sai → `NumberFormatException` |
| `String.format(Locale.US, "%.3f", x)` | `-1.250` — Locale.US để máy tiếng Việt không in `-1,250` |
| `Float.toString` (qua `StringBuilder.append`) | `5.0`, `-1.25`, `0.33333334` như màn hình đề |
| `Math.sqrt`, `Math.round`, `Math.floor` | chính phương |

---

## 3. Thiết kế

```
HE176322_J1SP0050_EquationSolver/src/
├── model/      Equation                   hệ số + nghiệm (null/rỗng/list), getNumbers()
├── dto/        EquationRequestDTO         a, b, c        (main ──► controller)
│               EquationResponseDTO        nghiệm + 3 nhóm số (controller ──► view)
├── service/    EquationSolver             «abstract» solve() = TEMPLATE METHOD; calculateEquation
│               SuperlativeEquationSolver  hệ số [a, b]; calculate → calculateEquation
│               QuadraticEquationSolver    hệ số [a, b, c]; calculate → calculateQuadraticEquation
│               NumberChecker              isOdd, isEven, isPerfectSquare (lớp "Number" của đề)
├── controller/ EquationController         giữ 2 solver kiểu EquationSolver; chọn nhãn dòng lẻ
├── view/       EquationView               in dòng Solution + 3 dòng số
├── constants/  Message, Constants
├── utils/      Validation                 getChoice, checkin
└── main/       Main                       menu + Scanner
```

| Câu hỏi thiết kế | Trả lời |
|---|---|
| Sao không có `repository`? | Không lưu gì giữa các lần chọn, không CRUD. Giải phương trình là *"tính toán nghiệp vụ"* → `service`. |
| Sao nhãn "Number is Odd:" / "Odd Number(s):" khác nhau? | Hai màn hình của đề viết khác nhau — chép đúng. Controller chọn nhãn cho view (`setOddLabel`). |

**Luồng option 2:**

```
Main: inputQuadratic(sc) ──► EquationRequestDTO ──► controller.calculateQuadraticEquation(dto)
   controller ──► quadraticSolver.solve(dto)             ← template method (lớp cha)
                     ├─ getCoefficients(dto)             ← bước con: [a, b, c]
                     ├─ calculate(dto)                    ← bước con: calculateQuadraticEquation
                     ├─ equation.getNumbers()             hệ số rồi nghiệm
                     └─ NumberChecker: isOdd / isPerfectSquare → 3 nhóm
   controller ──► view.setResponse; view.setOddLabel("Odd Number(s):"); view.display()
```

### 3.1 Design Pattern — **Template Method**

| Yếu tố | Trong bài này |
|---|---|
| **Name** | Template Method (nhóm Behavioral) |
| **Problem** | Cả hai option đều: lấy hệ số → giải → gom "hệ số rồi nghiệm" → chia lẻ/chẵn/chính phương → đóng gói kết quả. Viết hai lần là **lặp 20 dòng**, sửa một chỗ quên chỗ kia. |
| **Solution** | `EquationSolver` = **AbstractClass**: `public final EquationResponseDTO solve(dto)` là **template method** chạy các bước theo thứ tự; hai bước để trống `protected abstract getCoefficients(...)` và `calculate(...)`. `SuperlativeEquationSolver`, `QuadraticEquationSolver` = **ConcreteClass** điền hai bước đó. `calculateEquation` đặt ở lớp cha vì **cả hai** lớp con dùng (bậc hai khi a = 0). |
| **Consequences** | ✅ Khung chung viết **một lần**; thêm loại phương trình = **thêm 1 lớp con**; `final` chặn lớp con đổi thứ tự bước. ❌ Phải hiểu kế thừa + `protected`; lớp cha thêm bước là mọi lớp con bị ảnh hưởng. |

Kèm theo: **Facade** (`EquationController`), **MVC JSP** (`Equation`, DTO là JavaBean). Đa hình: controller
khai báo `private EquationSolver superlativeSolver` (kiểu cha) — `solve()` gọi đúng bước của lớp thật.

**Thầy bảo "thêm phương trình bậc ba":** tạo `CubicEquationSolver extends EquationSolver` (điền
`getCoefficients` [a, b, c, d] + `calculate`), thêm `setD` vào DTO, 1 field + 1 hàm ở controller, 1 `case` ở `Main`.
`solve()`, `NumberChecker`, `EquationView` **không sửa**.

### 3.2 SOLID

| | Ở đâu |
|---|---|
| **S** | `Equation` giữ số · `EquationSolver` khung · lớp con giải · `NumberChecker` phân loại số · `EquationView` in |
| **O** | loại phương trình mới = lớp con mới |
| **L** | cả hai lớp con thay được `EquationSolver` trong controller mà `solve()` vẫn đúng |
| **I** | `NumberChecker` chỉ 3 hàm nhỏ liên quan tới số |
| **D** | controller làm việc qua kiểu trừu tượng `EquationSolver` |

---

## 4. Code từng bước

> Thầy: *"code cái **model trước rồi đến data**"*.

| Bước | File | Việc |
|---|---|---|
| 1 | `model/Equation.java` | `ArrayList<Float> coefficients`, `roots`; `getNumbers()` |
| 2 | `dto/EquationRequestDTO`, `EquationResponseDTO` | JavaBean |
| 3 | `service/NumberChecker.java` | `isOdd` (`% 2 != 0`), `isEven`, `isPerfectSquare` |
| 4 | `service/EquationSolver.java` | `solve` (final), 2 hàm `abstract`, `calculateEquation` |
| 5 | `service/SuperlativeEquationSolver`, `QuadraticEquationSolver` | `@Override` 2 bước; Δ |
| 6 | `constants/` | menu, prompt, `%.3f`, nhãn; `DELTA_FACTOR = 4`, `SEPARATOR` |
| 7 | `view/EquationView.java` | `formatSolution` (null/rỗng/1/2 nghiệm), `join` |
| 8 | `controller/EquationController.java` | 2 solver + view |
| 9 | `utils/Validation.java`, `main/Main.java` | `checkin`; `inputCoefficient` hỏi lại |

**Bẫy hay gặp:**

1. Bậc hai với a = 0 mà vẫn chia `2a` → `Infinity`/`NaN`. Phải chuyển sang bậc nhất.
2. Nhầm `null` với danh sách rỗng — đề dùng **hai** trạng thái khác nhau.
3. `"%.3f"` không có `Locale.US` → máy lab tiếng Việt in `-1,250`.
4. `(long) Math.sqrt(x)` cắt cụt → 25 có thể thành "không chính phương". Dùng `Math.round`.

---

## 5. Test trước khi gọi thầy

| # | Chọn | Gõ | Phải thấy |
|---|---|---|---|
| 1 | menu | `9` / `x` | `Please choose from 1 to 3.` / `Please input number` |
| 2 | 1 | A `a` rồi `4`, B `5` | `Please input number`; `x = -1.250`; Odd `5.0, -1.25`; Even `4.0`; Square `4.0` |
| 3 | 1 | `0`, `0` | `infinitely many solutions`; Even/Square `0.0, 0.0` |
| 4 | 1 | `0`, `5` | `The equation has no solution.` |
| 5 | 1 | `9`, `-3` | `x = 0.333`; Odd `9.0, -3.0, 0.33333334`; Square `9.0` |
| 6 | 1 | `-4`, `8` | `x = 2.000`; Square **trống** (-4 âm không chính phương) |
| 7 | 2 | `4`,`4`,`1` | màn hình thứ hai của đề |
| 8 | 2 | `1`,`-3`,`2` | `x1 = 2.000 and x2 = 1.000` |
| 9 | 2 | `1`,`1`,`1` | `The equation has no solution.` (Δ < 0) |
| 10 | 2 | `0`,`4`,`-16` | `Solution: x = 4.000` (a = 0 → bậc nhất) |
| 11 | 2 | `0`,`0`,`0` | `infinitely many solutions` |
| 12 | 2 | B `q`, C `z` | `Please input number` ở từng ô |
| 13 | 3 | | `Goodbye.` |

---

## 6. Debug

| Việc | Cách làm |
|---|---|
| Breakpoint | dòng `List<Float> roots = calculate(request);` trong `EquationSolver.solve` |
| Chạy | **Ctrl+F5**, chọn 2, nhập 4/4/1 |
| Bước | **F7** vào `calculate` → nhảy vào `QuadraticEquationSolver` (đa hình); **F7** tiếp vào `calculateQuadraticEquation`, xem `delta` = 0.0 |
| Quan sát | sau vòng `for`: `response.oddNumbers` = [1.0, -0.5, -0.5], `evenNumbers` = [4.0, 4.0] |
| So sánh | chạy lại option 1 cùng breakpoint → lần này F7 vào `SuperlativeEquationSolver` — chỉ cho thầy **cùng một dòng, hai lớp khác nhau** |

---

## 7. Câu hỏi thầy hay hỏi

### OOP

| Câu hỏi | Trả lời mẫu |
|---|---|
| 4 tính chất OOP? | **Đóng gói**: field `private` trong `Equation`, DTO. **Kế thừa**: `QuadraticEquationSolver extends EquationSolver`. **Đa hình**: controller gọi `superlativeSolver.solve(dto)` với biến kiểu `EquationSolver`, `calculate` chạy bản của lớp con; `@Override`. **Trừu tượng**: `abstract class EquationSolver` có 2 hàm `abstract`. |
| abstract class khác interface? | Abstract class **chứa code chung** (`solve`, `calculateEquation`, field `numberChecker`) — interface không có. Ở đây hai lớp con **dùng chung** khung → abstract class. |
| Sao `solve` là `final`? | Khung là "hợp đồng": lớp con chỉ điền bước, không được đổi thứ tự. |

### Access modifier / static / kiểu

| Chỗ | Vì sao |
|---|---|
| field (`numberChecker`, a/b/c, roots…) | `private` |
| `solve` | `public final` — controller gọi; không cho ghi đè |
| `getCoefficients`, `calculate` | `protected abstract` — **chỉ lớp con** cần thấy để ghi đè |
| `calculateEquation` | **`protected`** (đề viết `public`) — chỉ 2 lớp con gọi; không lớp ngoài nào gọi |
| `calculateQuadraticEquation` | **`private`** — chỉ `calculate()` của chính lớp đó gọi; tên giữ đúng đề |
| `NumberChecker.isOdd/isEven/isPerfectSquare` | `public` — `EquationSolver` gọi; **không static** vì là nghiệp vụ trong `service` (static chỉ ở utils/constants/main) |
| `Validation.checkin/getChoice` | `public static` — utils |
| hàm trong `Main` | `private static` |

| Câu hỏi | Trả lời mẫu |
|---|---|
| `checkin` trả `Float` (lớp bọc) mà không `float`? | Giữ kiểu đề; lỗi báo bằng `throw` (quy ước Validation của Guide) nên không cần `null`. |
| `calculateEquation` trả `List<Float>` dù thầy dặn dùng `ArrayList`? | **Chữ ký đề bắt**, có comment `// brief:`. Bên trong vẫn tạo `new ArrayList<>()`. `List` là interface, `ArrayList` là lớp cài đặt bằng mảng động; chỗ nào đề không bắt, em khai báo `ArrayList`. |
| Sao `-1.25` là số lẻ? | Luật của đề: `a % 2 != 0`; `-1.25 % 2 = -1.25`. Toán học thì "lẻ" chỉ cho số nguyên — em làm đúng luật đề và màn hình đề. |
| **Bỏ `static` ở `checkin`?** | Lỗi biên dịch ở `Validation.checkin(...)`; phải bỏ `private` constructor, tạo đối tượng trong `Main`. |
| Độ phức tạp? | O(1) cho mỗi phương trình; phân loại O(số lượng số) ≤ 5. |

---

## 8. Thầy đổi yêu cầu tại chỗ

| Thầy bảo | Sửa | Không đụng |
|---|---|---|
| Thêm phương trình bậc ba | lớp con mới + DTO (`d`) + controller + `Main` + `Message` | `solve`, `NumberChecker`, view |
| In 2 chữ số thập phân | `Message.ONE_SOLUTION/TWO_SOLUTIONS` | mọi file khác |
| Chỉ coi số **nguyên** lẻ là lẻ | `NumberChecker.isOdd` (thêm điều kiện `number == Math.floor(number)`) | mọi file khác |
| Thêm dòng "số nguyên tố" | `NumberChecker.isPrime`, field trong `EquationResponseDTO`, 1 `if` trong `solve`, 1 dòng trong `EquationView`, nhãn `Message` | solver con, controller, `Main` |

---

## 9. Chỗ khác với đề / bản cũ

| Chỗ | Đề / bản cũ | Bài này | Lý do |
|---|---|---|---|
| `calculateQuadraticEquation(a, b, c)` | 3 tham số | nhận `EquationRequestDTO` | luật V4 |
| Access của 2 hàm đề bắt | `public` | `protected` / `private` | V2: chỉ lớp con / chính lớp đó gọi |
| Lớp `Number` (gợi ý) | `Number` | `NumberChecker` (service) + `Validation.checkin` (utils) | tên `Number` che `java.lang.Number`; kiểm chuỗi là việc của utils, phân loại số là nghiệp vụ |
| Chọn menu `2.0` | bản cũ đọc float nên nhận | báo `Please input number` | menu là số nguyên (như mẫu Guide `getChoice`) |
| Vô nghiệm / vô số nghiệm | đề chỉ nói null/rỗng | `The equation has no solution.` / `...infinitely many solutions.` | câu của bản tham chiếu |
| Kiến trúc | `bo/ui`, `static` field trong `Main` | MVC Guide + **Template Method** | luật thầy (V3, V7) |
