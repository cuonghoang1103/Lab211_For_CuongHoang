# J1.S.P0051 — Calculator + BMI

> Bài có **4 hàm đề bắt tên** (`calculate`, `calculateBMI`, `checkOperator`, `checkin`) và **2 enum**.
> Chỗ khó nhất khi review: `calculate(a, operator, b)` của đề có **3 tham số** — thầy cấm (V4). Xem §9.

| | |
|---|---|
| Loại / LOC | Short Assignment · 61 LOC · 1 slot |
| Project | `HE176322_J1SP0051_CalculatorBMI` |
| Chạy | NetBeans: **File ▸ Open Project** → **F6** |
| Lớp chạy | `main.Main` |
| Kiểm tự động | `python3 _tools/verify.py J1SP0051` → 7 kịch bản × 2 locale |

---

## 1. Đề bài nói gì

- Menu 3 mục: **Normal Calculator · BMI Calculator · Exit**.
- **Máy tính thường**: nhập số, rồi lặp *toán tử → số*; mỗi bước tính và **lưu vào bộ nhớ tạm** (in
  `Memory:`), **dừng khi toán tử là `=`** (in `Result:`). Toán tử hợp lệ: `+ - * / ^`.
- **BMI** = cân nặng (kg) / (cao (m) × cao (m)); màn hình hỏi chiều cao **cm**. 5 mức:
  < 19 Under-standard · 19–25 Standard · 25–30 Overweight · 30–40 Fat · > 40 Very fat.

Màn hình đề (chép đúng chữ):

```
========= Calculator Program =========
1. Normal Calculator
2. BMI Calculator
3. Exit
Please choice one option: 1
----- Normal Calculator -----
Enter number: 4
Enter Operator: +
Enter number: 4
Memory:8.0
Enter Operator: a
Please input (+, -, *, /, ^)
Enter Operator: +
Enter number: 16
Memory:24.0
Enter Operator: =
Result:24.0
```
```
----- BMI Calculator -----
Enter Weight(kg): a
BMI is digit
Enter Weight(kg): 70
Enter Height(cm): b
BMI is digit
Enter Height(cm): 170
BMI Number: 24.22
BMI Status: STANDARD
```

**Đề bắt buộc (Guidelines):**

| Thứ | Đề viết | Bài này đặt ở |
|---|---|---|
| `public double calculate(double a, Operator operator, double b)` | Function 1 | `service/CalculatorService.calculate(CalculatorRequestDTO)` — **giữ tên** + kiểu trả `double`, gói tham số, comment `// brief:` ngay trên hàm (§9) |
| `public BMI calculateBMI(double weight, double height)` | Function 2 | `service/CalculatorService.calculateBMI` — **giữ nguyên chữ ký** (2 tham số) |
| `Operator checkOperator(String operator)` public, sai → `null` | Guidelines | `utils/Validation.checkOperator` |
| `Double checkin(String inputVal)` public, không phải số → `null` | Guidelines | `utils/Validation.checkin` — **giữ đúng tên đề** (comment `// brief:`) |
| `Math.pow(a, b)` cho `^` | Guidelines | `case POWER` |
| `switch (enum)` | Guidelines | `switch (requestDTO.getOperator())` |
| `try catch` bắt `NumberFormatException, NullPointerException` | Guidelines | trong `checkin` |
| `if` bắt chia cho 0 → `ArithmeticException` | Guidelines | `case DIVIDE: if (b == 0) throw new ArithmeticException(...)` |
| enum `Operator`, enum `BMI` | Guidelines | `constants/Operator.java`, `constants/BMI.java` |

---

## 2. Kiến thức cần biết

### 2.1 Bộ nhớ tạm — chạy tay màn hình đề

| Gõ | Toán tử | Bộ nhớ trước | Tính | Bộ nhớ sau | In |
|---|---|---|---|---|---|
| `4` | — | — | số đầu vào bộ nhớ | 4 | — |
| `+`, `4` | ADD | 4 | 4 + 4 | 8 | `Memory:8.0` |
| `a` | — | — | `checkOperator` trả `null` | 8 | `Please input (+, -, *, /, ^)` |
| `+`, `16` | ADD | 8 | 8 + 16 | 24 | `Memory:24.0` |
| `=` | EQUAL | 24 | dừng | 24 | `Result:24.0` |

`a` trong `calculate(a, operator, b)` của đề **chính là bộ nhớ** (trong code: `firstNumber`, lấy từ repository); `b` là số vừa gõ (`secondNumber`).

### 2.2 Chia cho 0 — vì sao phải `if`

Với `double`, Java **không ném lỗi**: `4.0 / 0` = `Infinity`. Chỉ chia số **nguyên** mới tự ném
`ArithmeticException`. Nên đề bắt: *"Use if to catch ArithmeticException divided case 0"* → tự kiểm `b == 0`
và **tự ném** `ArithmeticException("Can not divide by zero")`. `Main.runStep` bắt, in, **bộ nhớ giữ nguyên**
(chưa kịp `saveMemory`), hỏi toán tử tiếp.

### 2.3 BMI — chạy tay 70 kg, 170 cm

| Bước | Tính | Giá trị |
|---|---|---|
| đổi cm → m | 170 / 100 | 1.7 |
| bình phương | 1.7 × 1.7 | 2.89 |
| chia | 70 / 2.89 | 24.2214… |
| in 2 số lẻ | `String.format(Locale.US, "%.2f")` | `24.22` |
| xếp mức | 19 ≤ 24.22 < 25 | `STANDARD` |

Đề viết khoảng **chồng nhau** (*"between 19-25"*, *"between 25-30"*) — 25 thuộc mức nào? Bài dùng
**bậc thang `<`**: đúng bằng mốc thì vào **mức cao hơn** (25.00 → OVERWEIGHT, 30.00 → FAT, 40.00 → VERY FAT).

### 2.4 `Double` (wrapper) và `null`

`checkin` trả `Double` chứ không `double`: kiểu nguyên thuỷ không có giá trị nào nghĩa là "không phải
số" (0 cũng là số hợp lệ!). `null` = không phải số.

### 2.5 Java dùng trong bài

| API | Dùng làm gì |
|---|---|
| `Double.valueOf(s)` | chuỗi → số; sai → `NumberFormatException` |
| `Math.pow(a, b)` | lũy thừa |
| `Operator.values()` | duyệt enum để tìm toán tử có ký hiệu khớp |
| `String.format(Locale.US, "%.2f", x)` | luôn in **dấu chấm** (máy tiếng Việt in `24,22` nếu không ghim Locale) |
| `String.format("Memory:%s", 8.0)` | `%s` gọi `Double.toString` → `8.0` đúng màn hình đề, không phụ thuộc Locale (tờ checklist 3.8: không cộng chuỗi) |

---

## 3. Thiết kế

```
HE176322_J1SP0051_CalculatorBMI/src/
├── model/      CalculatorMemory       "bộ nhớ tạm" của đề: 1 field value (JavaBean)
├── repository/ CalculatorRepository   giữ CalculatorMemory memory + saveMemory / getMemory
├── dto/        CalculatorRequestDTO   number + operator                        (main ──► controller)
│               BMIRequestDTO          weight + height                          (main ──► controller)
│               CalculatorResponseDTO  memory | result | bmiNumber + bmiStatus  (controller ──► view)
├── service/    CalculatorService      calculate · calculateBMI · calculateBMIIndex · storeMemory · getMemory
├── controller/ CalculatorController   startCalculation · calculate · showResult · calculateBMI (mỗi luồng render 1 lần)
├── view/       CalculatorView         field responseDTO + setResponseDTO() + display() KHÔNG tham số
├── constants/  Operator, BMI          2 enum của đề
│               Message, Constants     câu chữ + định dạng · số menu, mốc BMI, CM_PER_METRE
├── utils/      Validation             checkin · checkOperator · getNumber · getBodyValue · getOperator · getChoice
└── main/       Main                   final + private Main(); menu + vòng máy tính (inputStep/runStep) + Scanner
```

| Câu hỏi thiết kế | Trả lời |
|---|---|
| Sao có model `CalculatorMemory`? | Đề nói *"store results into the temporary memory"* — bộ nhớ là **một đối tượng có trạng thái**; repository giữ nó, mỗi `calculate` ghi kết quả vào. |
| Sao bài **có repository**? | Tờ checklist 1.1: *"Bắt buộc phải có repository"*. `CalculatorRepository` giữ **trạng thái** mà phép tính làm việc — bộ nhớ tạm `CalculatorMemory` — với `saveMemory`/`getMemory`; **không** tính, **không** in. Có tính toán nên có `CalculatorService`: Controller → Service → Repository → Model. BMI không có gì cần giữ: service tính thẳng từ `weight`, `height` controller đưa (2 tham số < 3, đúng tờ giấy). |
| View nhận dữ liệu thế nào? | Qua **thuộc tính**, không qua tham số (tờ checklist 1.1): `CalculatorView` có field `responseDTO`; controller gọi `setResponseDTO(responseDTO)` rồi `display()` — **1 lần cho 1 luồng**. Bước tính set `memory`, `=` set `result`, BMI set `bmiNumber` + `bmiStatus`; view in cái nào khác `null`. |
| Validate ở đâu? | Ở **Main** qua `utils/Validation` (tờ checklist 1.1): `getChoice`, `getNumber`/`getBodyValue` (dựa trên `checkin` của đề), `getOperator` (dựa trên `checkOperator`). Chia cho 0 là luật **nghiệp vụ** → service ném `ArithmeticException`, `Main` in `e.getMessage()`. |
| Máy tính thường gọi controller **nhiều lần** — trái luật "1 lần"? | Màn hình đề in `Memory:` **xen giữa** các lần nhập → không thể gọi controller 1 lần cho cả phiên. Bài chia đúng theo chữ tờ giấy *"mỗi luồng tính là 1 switch - case ở Main"*: `startCalculation` cất số đầu (**không render**), rồi **mỗi toán tử là một luồng** = một `case` của `switch` trên enum trong `Main.runStep` (đề: *"Use case switch to switch (enum)"*) → gọi controller **1 lần**, view render **1 lần** (`Memory:` hoặc `Result:`). Hỏi thầy nếu thầy muốn tính cả phiên máy tính là một luồng. |
| BMI gọi mấy lần? | **1 lần**: `controller.calculateBMI(bmiRequestDTO)`, view render 1 lần. |

**Luồng chạy (máy tính thường):**

```
Main.inputFirstNumber: tiêu đề + số đầu ──► controller.startCalculation(requestDTO)
                          ──► service.storeMemory ──► repository.saveMemory(số đầu)      (không render)
loop: Main.inputStep: đọc toán tử (+ số, trừ khi "=") ──► Main.runStep: switch (operator)
      ├─ case EQUAL ──► controller.showResult()      ──► responseDTO.result ──► view "Result:24.0" → thoát vòng
      └─ default   ──► controller.calculate(requestDTO)
                          ├─ service.calculate: firstNumber = repository.getMemory(), switch(operator),
                          │                     repository.saveMemory(kết quả)
                          └─ responseDTO.memory ──► view.setResponseDTO + display() "Memory:8.0"   (1 lần)
                     (chia 0: service ném ArithmeticException → runStep in "Can not divide by zero", vòng chạy tiếp)
```

### 3.1 Design Pattern

| Pattern | Trong bài | 4 yếu tố GoF, nói gọn |
|---|---|---|
| **MVC** (thầy: "MVC JSP") | Controller ~ Servlet, View ~ JSP, model/DTO là JavaBean | **Problem**: nhập/tính/in trộn một chỗ. **Solution**: tách vai, dữ liệu đi qua DTO. **Consequences**: đổi cách in chỉ sửa View; nhiều lớp hơn. |
| **Facade** | `CalculatorController` | **Problem**: Main phải biết service, bộ nhớ, view và thứ tự gọi. **Solution**: controller là một cửa (`calculate(requestDTO)` tự gọi service rồi view). **Consequences**: Main gọn; controller không được ôm nghiệp vụ. |

**Vì sao không dùng Strategy cho 5 toán tử?** Đề **bắt** *"Use case switch to switch (enum)"* nên
`calculate` giữ `switch`. Nếu thầy hỏi "làm Strategy được không": tạo interface `OperationStrategy { double
apply(double a, double b); }`, mỗi toán tử 1 lớp (`AddOperation`…), `CalculatorService` giữ
`HashMap<Operator, OperationStrategy>` và gọi `map.get(op).apply(a, b)` thay cho `switch` → thêm toán tử
`%` = thêm 1 lớp + 1 dòng `put`, không sửa `calculate` (OCP). Cái giá: 5 lớp nhỏ, và **trái chữ của đề**.

### 3.2 SOLID

| Nguyên lý | Ở đâu |
|---|---|
| **S** | `CalculatorMemory` là giá trị · `CalculatorRepository` giữ nó · `CalculatorService` tính · `Validation` kiểm · `CalculatorView` in · `Main` nhập |
| **O** | thêm mức BMI = thêm hằng enum + 1 mốc; thêm toán tử = thêm hằng `Operator` + 1 `case` (có `default` an toàn) |
| **D** (một phần) | `Main` chỉ biết controller + DTO + utils, không biết service/repository/model |

---

## 4. Code từng bước

> Thầy: *"code cái **model trước rồi đến data**"* (V8). Hai enum là "hằng" nhưng DTO dùng `Operator`,
> nên gõ enum ngay sau model.

| Bước | File | Việc |
|---|---|---|
| 1 | `model/CalculatorMemory.java` | `private double value` + constructor rỗng + constructor đủ + get/set |
| 2 | `constants/Operator.java`, `BMI.java` | enum + field chữ + constructor `private` + getter |
| 3 | `dto/CalculatorRequestDTO`, `BMIRequestDTO`, `CalculatorResponseDTO` (3 file) | JavaBean; Response 4 field `Double memory`, `Double result`, `Double bmiNumber`, `String bmiStatus` |
| 4 | `repository/CalculatorRepository.java` | field `CalculatorMemory memory` + `saveMemory(value)` + `getMemory()` — **không** tính |
| 5 | `service/CalculatorService.java` | field `calculatorRepository`; `storeMemory` · `getMemory` · **`calculate`** (switch) · `calculateBMIIndex` · **`calculateBMI`** (bậc thang) |
| 6 | `view/CalculatorView.java` | field `responseDTO` + `setResponseDTO` + `display()` (in cái nào khác `null`) |
| 7 | `controller/CalculatorController.java` | 4 hàm, mỗi hàm render **1 lần** (trừ `startCalculation`: không render) |
| 8 | `constants/Message.java`, `Constants.java` | câu chữ + định dạng đề (`Memory:%s`, `BMI Number: %.2f`…); số menu, mốc 19/25/30/40, 100 cm |
| 9 | `utils/Validation.java` | **`checkin`**, **`checkOperator`** + 4 hàm ném lỗi |
| 10 | `main/Main.java` | `final` + `private Main()`; menu `switch`; `inputFirstNumber` · `inputStep` · `runStep` (switch enum) · `inputBMI` |

**Bẫy hay gặp:**

1. Quên đổi **cm → m** → 70 kg / 170² = 0.0024 → ai cũng "UNDER-STANDARD".
2. Chia `double` cho 0 không tự ném lỗi → phải `if (b == 0)`.
3. `String.format("%.2f")` không ghim `Locale.US` → máy lab tiếng Việt in `24,22`.
4. Hỏi số **trước** toán tử trong vòng → gõ `=` vẫn bị bắt nhập thêm một số. Đúng thứ tự đề: **toán tử
   trước**, `=` thì dừng ngay.

---

## 5. Test trước khi gọi thầy

| # | Chọn | Gõ | Phải thấy |
|---|---|---|---|
| 1 | 1 | `4` `+` `4` `a` `+` `16` `=` | `Memory:8.0` · `Please input (+, -, *, /, ^)` · `Memory:24.0` · `Result:24.0` (màn hình đề) |
| 2 | 1 | `abc` rồi `10` | `Number is digit` rồi hỏi lại |
| 3 | 1 | `10` `-` `4` `*` `2.5` `x` `2` | `Memory:6.0` · `Memory:15.0` · `Memory:30.0` (`x` = nhân) |
| 4 | 1 | … `+` `q` `1` | `Number is digit`, rồi `Memory:31.0` |
| 5 | 1 | … `%` rồi `=` | `Please input (+, -, *, /, ^)` · `Result:31.0` |
| 6 | 1 | `2` `^` `10` `/` `0` `/` `4` `=` | `Memory:1024.0` · `Can not divide by zero` · `Memory:256.0` · `Result:256.0` |
| 7 | 1 | `7` `=` | `Result:7.0` |
| 8 | 2 | `a` `70` `b` `170` | `BMI is digit` ×2 · `BMI Number: 24.22` · `BMI Status: STANDARD` (màn hình đề) |
| 9 | 2 | cân `0`, `-5`; cao `0` | `BMI is digit` mỗi lần |
| 10 | 2 | cao `100`, cân `18.99` / `19` / `25` / `30` / `40` | UNDER-STANDARD · STANDARD · OVERWEIGHT · FAT - SHOULD LOSE WEIGHT · VERY FAT - SHOULD LOSE WEIGHT IMMEDIATELY |
| 11 | menu | `x` · `0` · `4` · *(trống)* · `9` | `Please input a number from 1 to 3.` |
| 12 | 3 | — | thoát |

---

## 6. Debug

| Việc | Cách làm |
|---|---|
| Breakpoint | dòng `switch (requestDTO.getOperator())` trong `CalculatorService.calculate` |
| Chạy | **Ctrl+F5**, chọn 1, gõ `4` `+` `4` |
| Quan sát | tab **Variables**: `firstNumber` (= bộ nhớ 4.0), `secondNumber` (4.0), `requestDTO.operator` = `ADD`; **F8** → vào `case ADD`, `result = 8.0` |
| Chia 0 | gõ `/` `0` → F8 thấy nhảy vào `throw`; **F5** → rơi vào `catch (ArithmeticException e)` trong `Main.runStep` |
| BMI | breakpoint `double bmi = calculateBMIIndex(...)` trong `calculateBMI`, xem `bmi = 24.22…`, F8 đi qua từng `if` |

---

## 7. Câu hỏi thầy hay hỏi

### Tham số, kiểu trả về

| Câu hỏi | Trả lời mẫu |
|---|---|
| **Đề bắt `calculate(double a, Operator operator, double b)` sao em chỉ có 1 tham số?** | Thầy dặn **không truyền 3 tham số một hàm** (tờ giấy 1.1 cũng ghi service nhận data từ controller qua param khi *số param < 3*). Em giữ **đúng tên** `calculate`, gói `operator` và `b` vào `CalculatorRequestDTO`; còn `a` chính là **bộ nhớ tạm** mà đề nói — service tự lấy từ repository (`firstNumber`). Kết quả vẫn trả `double` như đề; dòng `// brief:` trên hàm ghi chữ ký gốc. |
| `calculateBMI(weight, height)` sao giữ 2 tham số? | 2 tham số là được phép; giữ nguyên chữ ký đề cho dễ đối chiếu. |
| `calculateBMI` trả `BMI` (enum) vì sao? | Đề: *"Return Value: Enum contained in BMI status"*; enum so sánh/`switch` được, không sai chính tả như chuỗi. |
| `checkin` trả `Double` mà không `double`? | Cần `null` = "không phải số" (mục 2.4). |
| `checkOperator` trả `null` khi sai? | Đề bắt vậy. Hàm `getOperator` bọc lại: `null` → ném `Exception("Please input (+, -, *, /, ^)")` để Main in. |
| Hàm controller trả `void`? | Kết quả đã chuyển cho view in; lỗi đi bằng exception. |

### Access modifier, static

| Câu hỏi | Trả lời mẫu |
|---|---|
| `checkin`, `checkOperator` sao `public static`? | `public`: đề bắt, và `Main` (package khác) gọi qua `getNumber`/`getOperator`. `static`: không dùng dữ liệu đối tượng — cùng chuỗi vào ra cùng kết quả; Guide: utils *"phải dùng static method"*. |
| Bỏ `static` ở `Validation` thì sao? | `Validation.getNumber(...)` lỗi biên dịch; phải bỏ constructor `private`, tạo `new Validation()` trong Main rồi gọi qua đối tượng. |
| `memory` trong repository sao `private`? | Chỉ `CalculatorRepository` được đụng bộ nhớ — qua `saveMemory`/`getMemory`; service đổi nó qua `storeMemory`/`calculate`. |
| `calculateBMIIndex` sao `public`? | Controller gọi để lấy **con số** BMI (còn `calculateBMI` trả **mức**). |
| Enum `Operator` có `static` không? | Không cần. `Operator.values()` là hàm có sẵn của mọi enum. |
| Constructor enum sao `private`? | Chỉ các hằng khai báo sẵn tồn tại; không ai `new Operator(...)` được. |

### OOP, SOLID, bắt lỗi

| Câu hỏi | Trả lời mẫu |
|---|---|
| 4 tính chất OOP ở đâu? | **Đóng gói**: `value` `private` trong `CalculatorMemory`, field DTO `private` + get/set. **Kế thừa**: mọi lớp `extends Object`; mọi enum ngầm `extends java.lang.Enum`; `ArithmeticException extends RuntimeException`. **Đa hình**: `catch (Exception e)` bắt được mọi lớp con, `e.getMessage()` cho đúng câu. **Trừu tượng**: Main gọi `controller.calculateBMI(bmiRequestDTO)` mà không biết công thức. |
| Sao `catch (NumberFormatException \| NullPointerException e)`? | Đề bắt bắt đúng 2 lỗi: chuỗi không phải số, và chuỗi `null`. Multi-catch (Java 7) gộp 2 nhánh cùng xử lý. |
| `ArithmeticException` là checked hay unchecked? | **Unchecked** (con của `RuntimeException`) — nên `calculate` không phải khai `throws`; `Main.runStep` vẫn bắt nó để in câu và **không dừng** máy tính. |
| Gõ `NaN` / `Infinity`? | `Double.valueOf` nhận, nhưng không phải dữ liệu để tính → `checkin` trả `null` → `Number is digit`. |
| Sao `x` được coi là nhân? | Guidelines viết *"+, -, x, /, ^, ="*, màn hình viết `*` — nhận cả hai. |
| Tại sao cân/cao = 0 bị từ chối? | Cao 0 → chia 0 → `Infinity`; cân 0/âm không phải số đo. Dùng chung câu `BMI is digit` vì đề chỉ có 1 câu cho BMI. |
| Sao không có `List`/`ArrayList` nào? | Bài không có danh sách. Nếu có, em khai báo kiểu cụ thể `ArrayList` (thầy dặn): `List` là interface, `ArrayList` là lớp cài đặt bằng mảng động — lấy theo chỉ số nhanh, chèn giữa chậm. |

### Tờ checklist 25 mục — bài này đạt thế nào

| Mục | Chỉ vào đâu |
|---|---|
| **1.1** MVC + repository | `repository/CalculatorRepository` (bắt buộc có repository) giữ `CalculatorMemory`; luồng `CalculatorController` → `CalculatorService` → `CalculatorRepository` → `CalculatorMemory`; controller không import `model`; `CalculatorView` nhận `responseDTO` qua setter, `display()` không tham số, gọi **1 lần/luồng**; case 2 gọi controller 1 lần; case 1: `startCalculation` (không render) + mỗi toán tử một `case` trong `Main.runStep` (lý do ở mục 3) |
| **1.4** tên method | `inputFirstNumber`, `inputStep`, `runStep`, `inputBMI` (bản trước `normalCalculator`, `bmiCalculator` — không mở đầu bằng động từ); `checkin` **giữ tên đề** (`// brief:`) |
| **1.5** tên biến có nghĩa | `firstNumber`, `secondNumber` trong `CalculatorService.calculate` (bản trước `a`, `b`) |
| **2.6 / 3.7** khai báo đầu block + khởi tạo | `Main.main`: `calculatorRequestDTO = null`, `bmiRequestDTO = null`, `running = true`, `calculating = false`, `choice = 0`; `Main.inputX`: `String line = ""`; `CalculatorService.calculate`: `result = 0`; `Validation`: `int choice = 0`, `String text = ""` |
| **2.8** dòng trống | giữa các field và hằng enum, sau vùng khai báo biến, trước mọi comment đứng sau dòng code, giữa các `case`, sau `}` trước câu lệnh tiếp |
| **3.3** ngoặc | `Validation.getChoice`: `if ((choice < min) \|\| (choice > max))`; `getBodyValue`: `if ((value == null) \|\| (value <= 0))` |
| **3.4** lớp chỉ có static | `Main`, `Validation`, `Constants`, `Message`: `final` + `private` constructor |
| **3.8** cộng chuỗi | `CalculatorView.display` dùng `String.format(Message.LABEL_MEMORY, …)` … (bản trước `LABEL_MEMORY + value`) |

---

## 8. Thầy đổi yêu cầu tại chỗ

| Thầy bảo | Sửa | Không đụng |
|---|---|---|
| Thêm toán tử `%` | `Operator` thêm `MODULO("%")`, `case MODULO` trong `calculate` (kiểm chia 0), sửa câu `INVALID_OPERATOR` | Main, controller, view |
| Đổi mốc BMI (ví dụ 18.5) | `Constants.BMI_STANDARD_MIN` | mọi file khác |
| Thêm mức "Severely thin" < 16 | hằng mới trong `BMI` + mốc trong `Constants` + 1 `if` trong `calculateBMI` | Main, view |
| In `Memory:` 2 số lẻ | `Message.LABEL_MEMORY/LABEL_RESULT` đổi `%s` → `%.2f`, `CalculatorView.display` thêm `Locale.US` | service |
| Chiều cao nhập **mét** | `calculateBMIIndex` bỏ chia `CM_PER_METRE`, sửa `INPUT_HEIGHT` | các file khác |
| Thêm menu "Hiện bộ nhớ" | `Message.MENU`, `Constants` số menu, `case` ở Main gọi `controller.showResult()` | service |

---

## 9. Chỗ khác với đề / bản cũ

| Chỗ | Đề / bản cũ | Bài này | Lý do |
|---|---|---|---|
| Chữ ký `calculate` | `calculate(double a, Operator operator, double b)` | `calculate(CalculatorRequestDTO)`; `a` = bộ nhớ, `operator` + `b` trong DTO | thầy cấm 3 tham số (V4); giữ **tên** và kiểu trả `double` |
| Chỗ đặt `checkin`, `checkOperator` | đề không nói lớp | `utils/Validation` | kiểm dữ liệu nhập = utils (Guide) |
| Chỗ đặt `calculate`, `calculateBMI` | "startup code" | `service/CalculatorService` | tính toán = service (Guide) |
| enum `Operator`, `BMI` | bản cũ trong `entity/` | `constants/` | Guide: Constants chứa *"hằng số, enum"* |
| Câu `Number is digit`, `Please input a number from 1 to 3.`, `Can not divide by zero` | đề không ghi | giữ đúng bản tham chiếu | đề im lặng → giữ nguyên bản đã kiểm |
| Menu chọn 3 | — | thoát **không in gì** | giống bản tham chiếu, đề không có câu tạm biệt |
| Mốc BMI chồng nhau | "between 19-25", "25-30" | bằng mốc → mức cao hơn | phải chọn một cách; giống bản tham chiếu |
| Menu nhận `1.0` | bản cũ nhận (parse double) | từ chối (`Integer.parseInt`) | lựa chọn menu là số nguyên |
| `NaN`/`Infinity` | bản cũ nhận | từ chối | *"Number field must be numeric data"* |
| Kiến trúc | `entity/bo/ui`, Scanner trong `Validator`, `static CALCULATOR` trong Main | MVC Guide, Scanner cục bộ trong Main | luật thầy |
| `checkin` | đề đặt tên `checkin` | giữ `checkin` + comment `// brief:` | tên đề bắt; tờ checklist 1.4 muốn tên mở đầu bằng động từ — `checkin` = *check in(put)*. Hỏi thầy nếu thầy muốn đổi thành `checkInput` |
| Repository (21/09) | bản trước: **không có** (*"chỉ 1 giá trị bộ nhớ"*); bộ nhớ là field của service | `CalculatorRepository` giữ `CalculatorMemory` (`saveMemory`/`getMemory`); service lấy/ghi bộ nhớ qua nó | tờ checklist 1.1: *"Bắt buộc phải có repository"* |
| View + ResponseDTO (21/09) | bản trước: 2 field + `setCalculatorResponse`/`setBmiResponse` + `displayMemory`/`displayResult`/`displayBMI`; 2 ResponseDTO | 1 field `responseDTO` + `setResponseDTO` + `display()`; **một** `CalculatorResponseDTO` (`memory`/`result`/`bmiNumber`/`bmiStatus`); bỏ `BMIResponseDTO` | tờ checklist 1.1: view nhận qua **thuộc tính**, render **1 lần/luồng**; `display()` in theo cái controller đã set |
| `Main` (21/09) | bản trước: `normalCalculator` gọi controller 3 chỗ trong 1 hàm; `bmiCalculator`; biến khai giữa block; không `final` | `inputFirstNumber` + vòng `inputStep`/`runStep` (mỗi `case` enum gọi controller 1 lần); `inputBMI` trả DTO, case gọi controller 1 lần; biến khai đầu hàm + khởi tạo; `final` + `private Main()` | tờ checklist 1.1, 1.4, 2.6, 3.4, 3.7 |
| Tên biến (21/09) | bản trước: `a`, `b` trong `calculate` | `firstNumber`, `secondNumber` | tờ checklist 1.5 (tên có nghĩa); `// brief:` ghi chữ ký gốc của đề |
