# J1.S.P0050 — Equation Solver (bậc nhất, bậc hai, số chẵn/lẻ/chính phương)

> Bài thuật toán **vẫn phải MVC** và **vẫn phải có repository** (tờ checklist 1.1). Hai loại phương trình làm
> **cùng một chuỗi bước**, chỉ khác "hệ số nào", "giải thế nào" và "nhãn dòng lẻ" → đúng chỗ của **Template Method**.

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

> **Dấu cách cuối dòng trong file đề:** bản `.docx` có một dấu cách cuối ở `Solution: x = -1.250 `,
> `Number is Odd:5.0, -1.25 `, `Number is Even:4.0 `, `Odd Number(s):1.0, -0.5, -0.5 `, `Number is Even:4.0, 4.0 `,
> nhưng **không** có ở các dòng cùng loại `Solution: x1 = -0.500 and x2 = -0.500`, `Number is Perfect Square:…`.
> Cùng một lệnh in không thể lúc có lúc không → đó là dấu cách gõ thừa trong Word. Chương trình **không** in dấu cách
> cuối dòng (khớp bản tham chiếu đã kiểm theo đề).

**Đề bắt buộc:**

| Thứ | Đề viết | Bài này đặt ở |
|---|---|---|
| `public List<Float> calculateEquation(float a, float b)` | null = vô nghiệm, rỗng = vô số nghiệm | `EquationSolver.calculateEquation(float coefficientA, float coefficientB)` (lớp cha, **protected**) — cùng tên, cùng kiểu trả về, cùng kiểu tham số; tên tham số viết đủ nghĩa (tờ checklist 1.5) |
| `public List<Float> calculateQuadraticEquation(float a, float b, float c)` | 3 tham số | `QuadraticEquationSolver.calculateQuadraticEquation(Equation equation)` — đọc a, b, c từ phương trình **repository** đang giữ |
| `Float checkin(String floatString)` (gợi ý) | lớp `Number` | `utils/Validation.getFloat(String floatString)` (static), ngay trên có `// brief: public Float checkin(String floatString)` |
| `isOdd(float)`, `isPerfectSquare(float)` (gợi ý) | lớp `Number`; lẻ = `a % 2 != 0`; chính phương dùng `Math.sqrt` | `service/NumberChecker` (+ `isEven`) — giữ đúng tên đề |

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
| Δ = b² − 4ac | `(coefficientB * coefficientB) - (DELTA_FACTOR * coefficientA * coefficientC)` | 16 − 16 = **0** |
| Δ < 0 | vô nghiệm → `null` | — |
| Δ = 0 | nghiệm kép −b / 2a, **thêm 2 lần** | −4/8 = **−0.5, −0.5** |
| Δ > 0 | (−b ± √Δ) / 2a | — |

Vì sao thêm nghiệm kép **2 lần**? Màn hình đề in `x1 = -0.500 and x2 = -0.500` và liệt kê `-0.5` **hai lần** trong số lẻ.

### 2.3 Chia số — thứ tự "hệ số trước, nghiệm sau"

(4, 4, 1) → các số `4, 4, 1, -0.5, -0.5` (`Equation.getNumberList()`):

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
| `String.format("Number is Even:%s", …)` | ghép nhãn + dãy số, không cộng chuỗi (tờ checklist 3.8) |
| `Float.toString` (qua `StringBuilder.append`) | `5.0`, `-1.25`, `0.33333334` như màn hình đề |
| `Math.sqrt`, `Math.round`, `Math.floor` | chính phương |

---

## 3. Thiết kế

```
HE176322_J1SP0050_EquationSolver/src/
├── model/      Equation                   coefficientList + rootList (null/rỗng/list); getCoefficient(i), getNumberList()
├── repository/ EquationRepository         GIỮ phương trình (model): saveEquation (Create) · getEquation (Read)
├── dto/        EquationRequestDTO         coefficientA, coefficientB, coefficientC      (main ──► controller)
│               EquationResponseDTO        rootList + oddLabel + oddNumberList, evenNumberList, squareNumberList
│                                                                                        (controller ──► view)
├── service/    EquationSolver             «abstract» solve() = TEMPLATE METHOD; calculateEquation
│               SuperlativeEquationSolver  hệ số [a, b]; calculate → calculateEquation; nhãn "Number is Odd:%s"
│               QuadraticEquationSolver    hệ số [a, b, c]; calculate → calculateQuadraticEquation; nhãn "Odd Number(s):%s"
│               NumberChecker              isOdd, isEven, isPerfectSquare (lớp "Number" của đề)
├── controller/ EquationController         giữ 2 solver kiểu EquationSolver; setResponseDTO + display() 1 lần/luồng
├── view/       EquationView               field responseDTO; display() in dòng Solution + 3 dòng số
├── constants/  Message, Constants         câu chữ màn hình · INDEX_A/B/C, DELTA_FACTOR, SEPARATOR
├── utils/      Validation                 getChoice, getFloat (checkin của đề) — final + ctor private
└── main/       Main (final, ctor private) menu + Scanner + validate; mỗi case gọi controller 1 lần
```

| Câu hỏi thiết kế | Trả lời |
|---|---|
| Sao bài có `repository`? | Tờ checklist 1.1: *"**Bắt buộc phải có repository**"*. Repository = **dữ liệu** + CRUD đơn giản: ở đây là phương trình đang giải (model `Equation`: hệ số gõ vào, rồi nghiệm) với `saveEquation` / `getEquation`. **Giải** là nghiệp vụ nên nằm ở `service` — đúng tầng *Controller ↔ Services ↔ Repository ↔ Model*. |
| Sao nhãn "Number is Odd:" / "Odd Number(s):" khác nhau? | Hai màn hình của đề viết khác nhau — chép đúng. Mỗi solver con trả nhãn của màn hình mình (`getOddLabel()`), `solve()` đặt vào `responseDTO.oddLabel`; view chỉ in. |
| Controller có đụng model không? | Không. Controller chỉ import DTO, service, view (Guide) — không import cả `Message`. Service lấy `Equation` từ repository, giải, rồi đóng kết quả vào `EquationResponseDTO`. |
| Sao 3 hệ số gói 1 DTO? | Thầy: *"không truyền 3 tham số 1 hàm"*; Guide: data vào controller **qua DTO**. |

**Luồng option 2:**

```
Main: inputQuadratic(sc) ──► EquationRequestDTO ──► controller.calculateQuadraticEquation(requestDTO)   (gọi controller 1 lần)
   controller ──► quadraticSolver.solve(requestDTO)                 ← template method (lớp cha)
                     ├─ repository.saveEquation(getCoefficientList(requestDTO))   ← bước con: [a, b, c]
                     ├─ equation = repository.getEquation()                        (model)
                     ├─ calculate(equation)                         ← bước con: calculateQuadraticEquation
                     ├─ responseDTO: rootList + getOddLabel()       ← bước con: "Odd Number(s):%s"
                     └─ equation.getNumberList() → NumberChecker: isOdd / isPerfectSquare → 3 nhóm
   controller ──► view.setResponseDTO(responseDTO) ──► view.display()                                   (render 1 lần)
```

Tầng: **Main → RequestDTO → Controller → Service → Repository → Model**; kết quả **ResponseDTO → View**, render 1 lần.

### 3.1 Design Pattern — **Template Method**

| Yếu tố | Trong bài này |
|---|---|
| **Name** | Template Method (nhóm Behavioral) |
| **Problem** | Cả hai option đều: cất hệ số vào repository → giải → gom "hệ số rồi nghiệm" → chia lẻ/chẵn/chính phương → đóng gói kết quả. Viết hai lần là **lặp 20 dòng**, sửa một chỗ quên chỗ kia. |
| **Solution** | `EquationSolver` = **AbstractClass**: `public final EquationResponseDTO solve(requestDTO)` là **template method** chạy các bước theo thứ tự; ba bước để trống `protected abstract getCoefficientList(...)`, `calculate(...)`, `getOddLabel()`. `SuperlativeEquationSolver`, `QuadraticEquationSolver` = **ConcreteClass** điền ba bước đó. `calculateEquation` đặt ở lớp cha vì **cả hai** lớp con dùng (bậc hai khi a = 0). |
| **Consequences** | ✅ Khung chung viết **một lần**; thêm loại phương trình = **thêm 1 lớp con**; `final` chặn lớp con đổi thứ tự bước. ❌ Phải hiểu kế thừa + `protected`; lớp cha thêm bước là mọi lớp con bị ảnh hưởng. |

Kèm theo: **Facade** (`EquationController`), **MVC JSP** (`Equation`, DTO là JavaBean). Đa hình: controller
khai báo `private EquationSolver superlativeSolver` (kiểu cha) — `solve()` gọi đúng bước của lớp thật.

**Thầy bảo "thêm phương trình bậc ba":** tạo `CubicEquationSolver extends EquationSolver` (điền
`getCoefficientList` [a, b, c, d] + `calculate` + `getOddLabel`), thêm `coefficientD` vào DTO và `INDEX_D` vào
`Constants`, 1 field + 1 hàm ở controller, 1 `case` ở `Main`. `solve()`, `NumberChecker`, `EquationRepository`,
`EquationView` **không sửa**.

### 3.2 SOLID

| | Ở đâu |
|---|---|
| **S** | `Equation` giữ số · `EquationRepository` cất phương trình · `EquationSolver` khung · lớp con giải · `NumberChecker` phân loại số · `EquationView` in |
| **O** | loại phương trình mới = lớp con mới |
| **L** | cả hai lớp con thay được `EquationSolver` trong controller mà `solve()` vẫn đúng |
| **I** | `NumberChecker` chỉ 3 hàm nhỏ liên quan tới số |
| **D** | controller làm việc qua kiểu trừu tượng `EquationSolver` |

---

## 4. Code từng bước

> Thầy: *"code cái **model trước rồi đến data**"*.

| Bước | File | Việc |
|---|---|---|
| 1 | `model/Equation.java` | `ArrayList<Float> coefficientList`, `rootList`; `getCoefficient`, `getNumberList()` |
| 2 | `repository/EquationRepository.java` | field `equation`; `saveEquation` (Create), `getEquation` (Read) |
| 3 | `dto/EquationRequestDTO`, `EquationResponseDTO` | JavaBean; các danh sách đuôi `List` |
| 4 | `service/NumberChecker.java` | `isOdd` (`(number % 2) != 0`), `isEven`, `isPerfectSquare` |
| 5 | `service/EquationSolver.java` | `solve` (final), 3 hàm `abstract`, `calculateEquation` |
| 6 | `service/SuperlativeEquationSolver`, `QuadraticEquationSolver` | `@Override` 3 bước; Δ |
| 7 | `constants/` | menu, prompt, `%.3f`, nhãn `...:%s`; `INDEX_A/B/C`, `DELTA_FACTOR = 4`, `SEPARATOR` |
| 8 | `view/EquationView.java` | field `responseDTO` + `setResponseDTO`; `display()`; `formatSolution` (null/rỗng/1/2 nghiệm), `joinNumbers` |
| 9 | `controller/EquationController.java` | 2 solver + view; mỗi hàm: `setResponseDTO` rồi `display()` |
| 10 | `utils/Validation.java`, `main/Main.java` | `getFloat`; `inputCoefficient` hỏi lại |

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
| Breakpoint | dòng `rootList = calculate(equation);` trong `EquationSolver.solve` |
| Chạy | **Ctrl+F5**, chọn 2, nhập 4/4/1 |
| Bước | **F7** vào `calculate` → nhảy vào `QuadraticEquationSolver` (đa hình); **F7** tiếp vào `calculateQuadraticEquation`, xem `delta` = 0.0 |
| Quan sát | trước breakpoint: `equation.coefficientList` = [4.0, 4.0, 1.0] (lấy từ repository); sau vòng `for`: `responseDTO.oddNumberList` = [1.0, -0.5, -0.5], `evenNumberList` = [4.0, 4.0] |
| So sánh | chạy lại option 1 cùng breakpoint → lần này F7 vào `SuperlativeEquationSolver` — chỉ cho thầy **cùng một dòng, hai lớp khác nhau** |

---

## 7. Câu hỏi thầy hay hỏi

### OOP

| Câu hỏi | Trả lời mẫu |
|---|---|
| 4 tính chất OOP? | **Đóng gói**: field `private` trong `Equation`, `EquationRepository`, DTO. **Kế thừa**: `QuadraticEquationSolver extends EquationSolver`. **Đa hình**: controller gọi `superlativeSolver.solve(requestDTO)` với biến kiểu `EquationSolver`, `calculate`/`getOddLabel` chạy bản của lớp con; `@Override`. **Trừu tượng**: `abstract class EquationSolver` có 3 hàm `abstract`. |
| abstract class khác interface? | Abstract class **chứa code chung** (`solve`, `calculateEquation`, field `numberChecker`, `equationRepository`) — interface không có. Ở đây hai lớp con **dùng chung** khung → abstract class. |
| Sao `solve` là `final`? | Khung là "hợp đồng": lớp con chỉ điền bước, không được đổi thứ tự. |

### Access modifier / static / kiểu

| Chỗ | Vì sao |
|---|---|
| field (`numberChecker`, `equationRepository`, hệ số, `rootList`…) | `private` |
| constructor `EquationSolver()` | `protected` — lớp trừu tượng, chỉ lớp con gọi (qua `super()` ngầm) |
| `solve` | `public final` — controller gọi; không cho ghi đè |
| `getCoefficientList`, `calculate`, `getOddLabel` | `protected abstract` — **chỉ lớp con** cần thấy để ghi đè |
| `calculateEquation` | **`protected`** (đề viết `public`) — chỉ 2 lớp con gọi; không lớp ngoài nào gọi |
| `calculateQuadraticEquation` | **`private`** — chỉ `calculate()` của chính lớp đó gọi; tên giữ đúng đề |
| `EquationRepository.saveEquation/getEquation` | `public` — `EquationSolver` (gói `service`) gọi |
| `NumberChecker.isOdd/isEven/isPerfectSquare` | `public` — `EquationSolver` gọi; **không static** vì là nghiệp vụ trong `service` (static chỉ ở utils/constants/main) |
| `Validation.getFloat/getChoice` | `public static` — utils |
| hàm trong `Main` | `private static` |

| Câu hỏi | Trả lời mẫu |
|---|---|
| Sao bài có repository? | Tờ checklist 1.1 *"Bắt buộc phải có repository"*. `EquationRepository` giữ **dữ liệu đầu vào** của thuật toán — phương trình (hệ số gõ vào, rồi nghiệm) — chỉ `saveEquation`/`getEquation`, không tính, không in. `EquationSolver.solve` cất hệ số vào đó rồi **lấy lại từ đó** để giải. |
| View nhận dữ liệu thế nào? | Qua **thuộc tính**: controller gọi `equationView.setResponseDTO(responseDTO)` rồi `equationView.display()` — `display()` **không tham số**, gọi **1 lần** cho mỗi luồng (mỗi `case` ở `Main`). Nhãn dòng lẻ cũng đi trong `responseDTO` (`oddLabel`), không còn `setOddLabel` riêng. |
| Validate ở đâu? | Ở `Main` qua `utils/Validation` (`getChoice`, `getFloat`): sai thì `Validation` ném `Exception(Message.INVALID_NUMBER / INVALID_RANGE)`, `Main` bắt, in `e.getMessage()` rồi hỏi lại **ngay ô đó**. Controller/service chỉ nhận số đã hợp lệ trong `EquationRequestDTO`. |
| Sao đổi `checkin` → `getFloat`? | Tờ checklist 1.4: *"Tên method bắt đầu bằng động từ"* — `checkin` đọc như danh từ ("check-in"). Đề chỉ **gợi ý** (Recommend) tên này; em đặt `getFloat` cùng kiểu `getChoice`, giữ nguyên chữ ký đề ở comment `// brief: public Float checkin(String floatString)`. **Nếu thầy muốn giữ tên đề: đổi lại `checkin` ở `Validation` và `Main.inputCoefficient`.** |
| Sao tham số là `coefficientA`, đề viết `a`? | Tờ checklist 1.5: *"Tên biến … có ý nghĩa"*. Chữ ký Java chỉ gồm **tên hàm + kiểu tham số** → `calculateEquation(float, float)` vẫn y đề; comment `// brief:` chép nguyên chữ ký đề ngay trên hàm. |
| `checkin`/`getFloat` trả `Float` (lớp bọc) mà không `float`? | Giữ kiểu đề; lỗi báo bằng `throw` (quy ước Validation của Guide) nên không cần `null`. |
| `calculateEquation` trả `List<Float>` dù thầy dặn dùng `ArrayList`? | **Chữ ký đề bắt**, có comment `// brief:`. Bên trong vẫn tạo `new ArrayList<>()`. `List` là interface, `ArrayList` là lớp cài đặt bằng mảng động; chỗ nào đề không bắt, em khai báo `ArrayList`. |
| Sao tên danh sách đều đuôi `List`? | Tờ checklist 1.5: *"tên biến kiểu collection kết thúc bằng List"* → `coefficientList`, `rootList`, `numberList`, `oddNumberList`… |
| Sao `-1.25` là số lẻ? | Luật của đề: `a % 2 != 0`; `-1.25 % 2 = -1.25`. Toán học thì "lẻ" chỉ cho số nguyên — em làm đúng luật đề và màn hình đề. |
| **Bỏ `static` ở `getFloat`?** | Lỗi biên dịch ở `Validation.getFloat(...)`; phải bỏ `private` constructor, tạo đối tượng trong `Main`. |
| Sao `Main` là `final` và có `private Main() { }`? | Tờ checklist 3.4: *"Class chỉ có static method thì phải có private constructor, và khai báo class là final"*. |
| Độ phức tạp? | O(1) cho mỗi phương trình; phân loại O(số lượng số) ≤ 5. |

---

## 8. Thầy đổi yêu cầu tại chỗ

| Thầy bảo | Sửa | Không đụng |
|---|---|---|
| Thêm phương trình bậc ba | lớp con mới + DTO (`coefficientD`) + `Constants.INDEX_D` + controller + `Main` + `Message` | `solve`, `NumberChecker`, repository, view |
| In 2 chữ số thập phân | `Message.ONE_SOLUTION/TWO_SOLUTIONS` | mọi file khác |
| Chỉ coi số **nguyên** lẻ là lẻ | `NumberChecker.isOdd` (thêm điều kiện `(number == Math.floor(number))`) | mọi file khác |
| Thêm dòng "số nguyên tố" | `NumberChecker.isPrime`, field `primeNumberList` trong `EquationResponseDTO`, 1 `if` trong `solve`, 1 dòng trong `EquationView`, nhãn `Message` | solver con, controller, `Main`, repository |

---

## 9. Chỗ khác với đề / bản cũ

| Chỗ | Đề / bản cũ | Bài này | Lý do |
|---|---|---|---|
| `calculateQuadraticEquation(a, b, c)` | 3 tham số | nhận `Equation` (model repository giữ) | luật V4 + service lấy dữ liệu từ repository |
| Access của 2 hàm đề bắt | `public` | `protected` / `private` | V2: chỉ lớp con / chính lớp đó gọi |
| Lớp `Number` (gợi ý) | `Number` | `NumberChecker` (service) + `Validation.getFloat` (utils) | tên `Number` che `java.lang.Number`; kiểm chuỗi là việc của utils, phân loại số là nghiệp vụ |
| `checkin` (gợi ý) | `checkin` | `getFloat` + `// brief: public Float checkin(String floatString)` | tờ checklist 1.4 (động từ) — **hỏi thầy** nếu thầy muốn giữ tên đề |
| Tên tham số `a, b, c` | `a, b, c` | `coefficientA/B/C` (DTO, `calculateEquation`) | tờ checklist 1.5 (tên có ý nghĩa); chữ ký Java không đổi |
| Chọn menu `2.0` | bản cũ đọc float nên nhận | báo `Please input number` | menu là số nguyên (như mẫu Guide `getChoice`) |
| Vô nghiệm / vô số nghiệm | đề chỉ nói null/rỗng | `The equation has no solution.` / `...infinitely many solutions.` | câu của bản tham chiếu |
| Thoát | đề không có màn hình thoát | in `Goodbye.` | câu của bản tham chiếu; đề không cấm |
| Kiến trúc | `bo/ui`, `static` field trong `Main` | MVC Guide + **Template Method** | luật thầy (V3, V7) |
| Repository | Bản trước: *"không lưu gì, không CRUD → không repository"* | có `EquationRepository` giữ phương trình | tờ checklist 1.1: *"Bắt buộc phải có repository"* |
| View | `setResponse(dto)` + `setOddLabel(String)` (controller import `Message`) | `setResponseDTO(responseDTO)` + `display()`; nhãn dòng lẻ nằm trong `responseDTO.oddLabel` do solver đặt | tờ checklist 1.1 (View nhận qua thuộc tính, render 1 lần); Guide: controller chỉ import DTO/View/Service |
| Tên | `roots`, `oddNumbers`, `coefficients`, `numbers`, `join`, `class Main` | `rootList`, `oddNumberList`, `coefficientList`, `numberList`, `joinNumbers`, `final class Main` + `private Main()` | tờ checklist 1.5 (đuôi `List`), 1.4, 3.4 |
| Nối chuỗi | `oddLabel + join(...)`, `coefficients + " -> " + roots` | `String.format(Message.LABEL_EVEN, …)` với `"Number is Even:%s"`; `String.format(Constants.EQUATION_FORMAT, …)` | tờ checklist 3.8; chữ in ra y hệt |
| Khai báo | `int choice = inputChoice(sc);` trong `while`, `String line = sc.nextLine();` trong vòng lặp, `int choice;` chưa khởi tạo | khai báo ở đầu block + khởi tạo (`int choice = 0;`, `String line = "";`), trong vòng lặp chỉ gán | tờ checklist 2.6, 3.7 |

---

## 10. Tờ checklist 25 mục — bài này đạt thế nào

| Mục | Chỗ trong code |
|---|---|
| 1.1 MVC + repository | `repository/EquationRepository` giữ model `Equation`; `EquationSolver.solve` cất hệ số (`saveEquation`) rồi lấy lại (`getEquation`) để giải; controller chỉ import DTO/service/view; `EquationView` nhận `responseDTO` qua `setResponseDTO`, `display()` gọi **1 lần** mỗi luồng; mọi nhập + validate ở `Main` |
| 1.3 / 1.4 tên | lớp là danh từ (`EquationSolver`, `NumberChecker`, `EquationRepository`); method là động từ: `solve`, `calculateEquation`, `saveEquation`, `formatSolution`, `joinNumbers`, `getFloat`, `inputCoefficient` |
| 1.5 tên biến | `coefficientList`, `rootList`, `numberList`, `oddNumberList`/`evenNumberList`/`squareNumberList`; `coefficientA/B/C` thay `a/b/c`; không có `ID` |
| 2.6 + 3.7 khai báo đầu block, có khởi tạo | `Main.main`: `int choice = 0;` ở đầu, trong `while` chỉ `choice = inputChoice(sc);`; `inputChoice`/`inputCoefficient`: `String line = "";`; `calculateQuadraticEquation`: `delta = 0`, `root = 0`, `sqrtDelta = 0` ở đầu; `Validation`: `int choice = 0;`, `Float value = null;` |
| 2.8 dòng trống | trước mọi comment (kể cả comment field trong `Constants`, `Message`, DTO), sau vùng khai báo, giữa các `case`, sau `}` của `if`/`for` trước câu lệnh tiếp |
| 3.3 ngoặc | `Validation.getChoice`: `if ((choice < min) \|\| (choice > max))`; `delta = (coefficientB * coefficientB) - (Constants.DELTA_FACTOR * coefficientA * coefficientC)`; `(number % 2) != 0`; `(root * root) == (long) number` |
| 3.4 | `public final class Main` + `private Main() { }`; `Validation`, `Constants`, `Message` cũng `final` + ctor private |
| 3.8 | không cộng chuỗi: `String.format(Message.LABEL_…, joinNumbers(…))`, `StringBuilder` trong `joinNumbers` |

Kiểm lại: `python3 _tools/verify.py J1SP0050` · `python3 _tools/lint.py HE176322_J1SP0050_*` · `python3 _tools/soat_checklist.py HE176322_J1SP0050_*` → 0 `VI_PHAM`.
`RUI_RO` còn lại chỉ là `String[] args` và tham số setter/constructor trùng tên field (`this.rootList = rootList`) — kiểu IDE sinh, được chấp nhận.
Hai override `calculate` ghi `@Override // brief: …` trên cùng dòng: lint cần chữ `brief` ngay dòng trên kiểu `List<Float>`, còn tờ checklist 2.8 cấm comment đứng riêng sát ngay dưới dòng code `@Override`.
