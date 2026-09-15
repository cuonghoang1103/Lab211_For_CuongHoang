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
| `public double calculate(double a, Operator operator, double b)` | Function 1 | `service/CalculatorService.calculate(CalculatorRequestDTO)` — **giữ tên**, gói tham số (§9) |
| `public BMI calculateBMI(double weight, double height)` | Function 2 | `service/CalculatorService.calculateBMI` — **giữ nguyên chữ ký** (2 tham số) |
| `Operator checkOperator(String operator)` public, sai → `null` | Guidelines | `utils/Validation.checkOperator` |
| `Double checkin(String inputVal)` public, không phải số → `null` | Guidelines | `utils/Validation.checkin` |
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

`a` trong `calculate(a, operator, b)` của đề **chính là bộ nhớ**; `b` là số vừa gõ.

### 2.2 Chia cho 0 — vì sao phải `if`

Với `double`, Java **không ném lỗi**: `4.0 / 0` = `Infinity`. Chỉ chia số **nguyên** mới tự ném
`ArithmeticException`. Nên đề bắt: *"Use if to catch ArithmeticException divided case 0"* → tự kiểm `b == 0`
và **tự ném** `ArithmeticException("Can not divide by zero")`. Main bắt, in, **bộ nhớ giữ nguyên**, hỏi
toán tử tiếp.

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
| `"Memory:" + 8.0` | `Double.toString` → `8.0` đúng màn hình đề |

---

## 3. Thiết kế

```
HE176322_J1SP0051_CalculatorBMI/src/
├── model/      CalculatorMemory       "bộ nhớ tạm" của đề: 1 field value (JavaBean)
├── dto/        CalculatorRequestDTO   number + operator            (main ──► controller)
│               CalculatorResponseDTO  giá trị bộ nhớ               (controller ──► view)
│               BMIRequestDTO          weight + height              (main ──► controller)
│               BMIResponseDTO         bmiNumber + status           (controller ──► view)
├── service/    CalculatorService      calculate · calculateBMI · calculateBMIIndex · bộ nhớ
├── controller/ CalculatorController   startCalculation · calculate · showResult · calculateBMI
├── view/       CalculatorView         in Memory: / Result: / BMI
├── constants/  Operator, BMI          2 enum của đề
│               Message, Constants     câu chữ · số menu, mốc BMI, CM_PER_METRE
├── utils/      Validation             checkin · checkOperator · getNumber · getBodyValue · getOperator · getChoice
└── main/       Main                   menu + vòng máy tính + Scanner
```

| Câu hỏi thiết kế | Trả lời |
|---|---|
| Sao có model `CalculatorMemory`? | Đề nói *"store results into the temporary memory"* — bộ nhớ là **một đối tượng có trạng thái**; service giữ nó, mỗi `calculate` ghi kết quả vào. |
| Sao không có repository? | Không có tập dữ liệu, không CRUD — chỉ 1 giá trị bộ nhớ. |
| Máy tính thường gọi controller **nhiều lần** — trái luật "1 lần"? | Màn hình đề in `Memory:` **xen giữa** các lần nhập → mỗi bước (toán tử + số) là một lượt gọi `controller.calculate(dto)`. `startCalculation` là bước đặt số đầu, `showResult` là bước `=`. Không có cách nào in `Memory:` giữa hai lần nhập mà chỉ gọi 1 lần. |
| BMI gọi mấy lần? | **1 lần**: `controller.calculateBMI(dto)`. |

**Luồng chạy (máy tính thường):**

```
Main: đọc số đầu ──► dto.setNumber ──► controller.startCalculation(dto) ──► service: memory = số đầu
loop: đọc toán tử
      ├─ "=" ──► controller.showResult()      ──► view "Result:24.0" → thoát vòng
      └─ khác ─► đọc số ──► controller.calculate(dto)
                               ├─ service.calculate(dto): a = memory, switch(operator), memory = kết quả
                               └─ view "Memory:8.0"
                          (chia 0: service ném ArithmeticException → Main in "Can not divide by zero")
```

### 3.1 Design Pattern

| Pattern | Trong bài | 4 yếu tố GoF, nói gọn |
|---|---|---|
| **MVC** (thầy: "MVC JSP") | Controller ~ Servlet, View ~ JSP, model/DTO là JavaBean | **Problem**: nhập/tính/in trộn một chỗ. **Solution**: tách vai, dữ liệu đi qua DTO. **Consequences**: đổi cách in chỉ sửa View; nhiều lớp hơn. |
| **Facade** | `CalculatorController` | **Problem**: Main phải biết service, bộ nhớ, view và thứ tự gọi. **Solution**: controller là một cửa (`calculate(dto)` tự gọi service rồi view). **Consequences**: Main gọn; controller không được ôm nghiệp vụ. |

**Vì sao không dùng Strategy cho 5 toán tử?** Đề **bắt** *"Use case switch to switch (enum)"* nên
`calculate` giữ `switch`. Nếu thầy hỏi "làm Strategy được không": tạo interface `OperationStrategy { double
apply(double a, double b); }`, mỗi toán tử 1 lớp (`AddOperation`…), `CalculatorService` giữ
`HashMap<Operator, OperationStrategy>` và gọi `map.get(op).apply(a, b)` thay cho `switch` → thêm toán tử
`%` = thêm 1 lớp + 1 dòng `put`, không sửa `calculate` (OCP). Cái giá: 5 lớp nhỏ, và **trái chữ của đề**.

### 3.2 SOLID

| Nguyên lý | Ở đâu |
|---|---|
| **S** | `CalculatorMemory` giữ giá trị · `CalculatorService` tính · `Validation` kiểm · `CalculatorView` in · `Main` nhập |
| **O** | thêm mức BMI = thêm hằng enum + 1 mốc; thêm toán tử = thêm hằng `Operator` + 1 `case` (có `default` an toàn) |
| **D** (một phần) | `Main` chỉ biết controller + DTO, không biết service/model |

---

## 4. Code từng bước

> Thầy: *"code cái **model trước rồi đến data**"* (V8). Hai enum là "hằng" nhưng DTO dùng `Operator`,
> nên gõ enum ngay sau model.

| Bước | File | Việc |
|---|---|---|
| 1 | `model/CalculatorMemory.java` | `private double value` + constructor rỗng + constructor đủ + get/set |
| 2 | `constants/Operator.java`, `BMI.java` | enum + field chữ + constructor `private` + getter |
| 3 | `dto/…RequestDTO`, `…ResponseDTO` (4 file) | JavaBean |
| 4 | `service/CalculatorService.java` | `storeMemory` · `getMemory` · **`calculate`** (switch) · `calculateBMIIndex` · **`calculateBMI`** (bậc thang) |
| 5 | `view/CalculatorView.java` | 2 setter + `displayMemory` · `displayResult` · `displayBMI` |
| 6 | `controller/CalculatorController.java` | 4 hàm |
| 7 | `constants/Message.java`, `Constants.java` | câu chữ đề; số menu, mốc 19/25/30/40, 100 cm |
| 8 | `utils/Validation.java` | **`checkin`**, **`checkOperator`** + 4 hàm ném lỗi |
| 9 | `main/Main.java` | menu `switch` + `normalCalculator` (vòng tới `=`) + `bmiCalculator` |

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
| Quan sát | tab **Variables**: `a` (= bộ nhớ 4.0), `b` (4.0), `requestDTO.operator` = `ADD`; **F8** → vào `case ADD`, `result = 8.0` |
| Chia 0 | gõ `/` `0` → F8 thấy nhảy vào `throw`; **F5** → rơi vào `catch (ArithmeticException e)` trong `Main.normalCalculator` |
| BMI | breakpoint `double bmi = calculateBMIIndex(...)` trong `calculateBMI`, xem `bmi = 24.22…`, F8 đi qua từng `if` |

---

## 7. Câu hỏi thầy hay hỏi

### Tham số, kiểu trả về

| Câu hỏi | Trả lời mẫu |
|---|---|
| **Đề bắt `calculate(double a, Operator operator, double b)` sao em chỉ có 1 tham số?** | Thầy dặn **không truyền 3 tham số một hàm**. Em giữ **đúng tên** `calculate`, gói `operator` và `b` vào `CalculatorRequestDTO`; còn `a` chính là **bộ nhớ tạm** mà đề nói — service tự lấy từ `CalculatorMemory`. Kết quả vẫn trả `double` như đề. |
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
| `memory` trong service sao `private`? | Chỉ `CalculatorService` được đổi bộ nhớ — qua `storeMemory`/`calculate`. |
| `calculateBMIIndex` sao `public`? | Controller gọi để lấy **con số** BMI (còn `calculateBMI` trả **mức**). |
| Enum `Operator` có `static` không? | Không cần. `Operator.values()` là hàm có sẵn của mọi enum. |
| Constructor enum sao `private`? | Chỉ các hằng khai báo sẵn tồn tại; không ai `new Operator(...)` được. |

### OOP, SOLID, bắt lỗi

| Câu hỏi | Trả lời mẫu |
|---|---|
| 4 tính chất OOP ở đâu? | **Đóng gói**: `value` `private` trong `CalculatorMemory`, field DTO `private` + get/set. **Kế thừa**: mọi lớp `extends Object`; mọi enum ngầm `extends java.lang.Enum`; `ArithmeticException extends RuntimeException`. **Đa hình**: `catch (Exception e)` bắt được mọi lớp con, `e.getMessage()` cho đúng câu. **Trừu tượng**: Main gọi `controller.calculateBMI(dto)` mà không biết công thức. |
| Sao `catch (NumberFormatException \| NullPointerException e)`? | Đề bắt bắt đúng 2 lỗi: chuỗi không phải số, và chuỗi `null`. Multi-catch (Java 7) gộp 2 nhánh cùng xử lý. |
| `ArithmeticException` là checked hay unchecked? | **Unchecked** (con của `RuntimeException`) — nên `calculate` không phải khai `throws`; Main vẫn bắt nó để in câu và **không dừng** máy tính. |
| Gõ `NaN` / `Infinity`? | `Double.valueOf` nhận, nhưng không phải dữ liệu để tính → `checkin` trả `null` → `Number is digit`. |
| Sao `x` được coi là nhân? | Guidelines viết *"+, -, x, /, ^, ="*, màn hình viết `*` — nhận cả hai. |
| Tại sao cân/cao = 0 bị từ chối? | Cao 0 → chia 0 → `Infinity`; cân 0/âm không phải số đo. Dùng chung câu `BMI is digit` vì đề chỉ có 1 câu cho BMI. |
| Sao không có `List`/`ArrayList` nào? | Bài không có danh sách. Nếu có, em khai báo kiểu cụ thể `ArrayList` (thầy dặn): `List` là interface, `ArrayList` là lớp cài đặt bằng mảng động — lấy theo chỉ số nhanh, chèn giữa chậm. |

---

## 8. Thầy đổi yêu cầu tại chỗ

| Thầy bảo | Sửa | Không đụng |
|---|---|---|
| Thêm toán tử `%` | `Operator` thêm `MODULO("%")`, `case MODULO` trong `calculate` (kiểm chia 0), sửa câu `INVALID_OPERATOR` | Main, controller, view |
| Đổi mốc BMI (ví dụ 18.5) | `Constants.BMI_STANDARD_MIN` | mọi file khác |
| Thêm mức "Severely thin" < 16 | hằng mới trong `BMI` + mốc trong `Constants` + 1 `if` trong `calculateBMI` | Main, view |
| In `Memory:` 2 số lẻ | `CalculatorView.displayMemory/displayResult` dùng `String.format(Locale.US, …)` + định dạng trong Constants | service |
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
