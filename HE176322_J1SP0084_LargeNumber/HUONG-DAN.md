# J1.S.P0084 — Large Number (nhân số lớn)

> Bài thuật toán **vẫn phải MVC** (thầy: *"các bài liên quan thuật toán … cũng phải làm MVC"*,
> QUY-TAC-THAY §2). Thuật toán ở **service**; **repository giữ hai số** mà thuật toán làm việc
> (tờ checklist giấy 1.1: *"Bắt buộc phải có repository"*).

| | |
|---|---|
| Loại / LOC | Short Assignment · 60 LOC · 1 slot |
| Project | `HE176322_J1SP0084_LargeNumber` |
| Chạy | NetBeans: **File ▸ Open Project** → **F6** |
| Lớp chạy | `main.Main` |
| Kiểm tự động | `python3 _tools/verify.py J1SP0084` → 9 kịch bản × 2 locale |
| Tờ checklist giấy của thầy | 25 mục — bài này đạt thế nào: **mục 10** |

---

## 1. Đề bài nói gì

> **Đề gốc thầy phát** (`J1.S.P0084.txt`): *"multiply two digit number inputted from keyboard then
> display result to screen. Note: size of inputted number is unlimited"*; Guidelines: *"Try to use
> array to store the result and two digit number"*. Màn hình, 3 bước (Function 1–3) và các ghi chú
> dưới đây lấy từ **bản đề mở rộng** trên trang CodeLab — không trái đề gốc.

- Nhập **2 số nguyên** từ bàn phím, in **tích**. Số chữ số **không giới hạn** — vượt xa `long`.
- Đọc mỗi số là **chuỗi**, đổi thành **mảng chữ số** 0–9.
- Nhân **kiểu tiểu học** (schoolbook): mảng kết quả dài `lenA + lenB`; `result[i + j] += A[i] × B[j]`, rồi
  **nhớ** (carry) từ phải sang trái.
- **Bỏ số 0 đầu** khi in; **nhập 0** thì kết quả là 0. **Cấm BigInteger**.

Màn hình đề:

```
Enter the first number : 123456789123456789
Enter the second number: 987654321987654321
123456789123456789 x 987654321987654321 = 121932631356500531347203169112635269
```

**Đề bắt buộc:**

| Thứ | Đề viết | Bài này đặt ở |
|---|---|---|
| Đọc số là String, không parse sang int/long | Function 1 | `utils/Validation.getDigits` kiểm **từng ký tự** |
| Chuỗi → mảng chữ số | Function 1 | `service/LargeNumberService.toLargeNumber` |
| Nhân schoolbook, mảng `lenA + lenB` | Function 2 | `LargeNumberService.multiplyDigits` (private) |
| Bỏ số 0 đầu, xử lý 0 | Function 3 + Notes | `model/LargeNumber.toString()` |
| Không dùng BigInteger | Guidelines | cả project không import `java.math` |

---

## 2. Kiến thức cần biết

### 2.1 Vì sao không dùng `long`

`long` lớn nhất ≈ 9,2 × 10¹⁸ (19 chữ số). Tích ở màn hình đề có **36 chữ số**. Parse sang `long` không
báo lỗi ồn ào mà **tràn âm thầm** → in ra một số sai rất tự tin.

### 2.2 Lưu chữ số **từ phải sang** (index 0 = hàng đơn vị)

`"123"` → `digits = {3, 2, 1}`. Nhờ vậy câu của đề *"chữ số i của A × chữ số j của B rơi vào vị trí
i + j"* đúng **nguyên văn**, không phải viết `length - 1 - i` khắp nơi.

### 2.3 Chạy tay ví dụ của đề: `123 × 45 = 5535`

A = `{3, 2, 1}`, B = `{5, 4}`, `result` dài 3 + 2 = **5** ô.

**Lượt 1 — cộng tích vào ô i + j (chưa nhớ):**

| i (A[i]) | j (B[j]) | tích | ô i+j | result sau bước `[0,1,2,3,4]` |
|---|---|---|---|---|
| 0 (3) | 0 (5) | 15 | 0 | `15, 0, 0, 0, 0` |
| 0 (3) | 1 (4) | 12 | 1 | `15, 12, 0, 0, 0` |
| 1 (2) | 0 (5) | 10 | 1 | `15, 22, 0, 0, 0` |
| 1 (2) | 1 (4) | 8 | 2 | `15, 22, 8, 0, 0` |
| 2 (1) | 0 (5) | 5 | 2 | `15, 22, 13, 0, 0` |
| 2 (1) | 1 (4) | 4 | 3 | `15, 22, 13, 4, 0` |

**Lượt 2 — nhớ từ ô 0 lên:** `result[k+1] += result[k] / 10; result[k] %= 10`

| k | trước | chuyển lên ô k+1 | sau |
|---|---|---|---|
| 0 | 15 | 1 | `5, 23, 13, 4, 0` |
| 1 | 23 | 2 | `5, 3, 15, 4, 0` |
| 2 | 15 | 1 | `5, 3, 5, 5, 0` |
| 3 | 5 | 0 | `5, 3, 5, 5, 0` |

Đọc từ ô cao xuống: `0 5 5 3 5` → **bỏ 0 đầu** → `5535` ✔ (đúng hình của đề: ô xám số 0 ở đầu).

### 2.4 Vì sao `lenA + lenB` ô luôn đủ

Số m chữ số < 10ᵐ, số n chữ số < 10ⁿ → tích < 10ᵐ⁺ⁿ, tức **nhiều nhất m + n chữ số**.

### 2.5 Độ phức tạp

Hai vòng lồng m × n + một vòng nhớ m + n → **O(m × n)**. Hai số 1000 chữ số: 1 triệu phép nhân — tức thì.

### 2.6 Java dùng trong bài

| API | Dùng làm gì |
|---|---|
| `text.charAt(i) - '0'` | ký tự `'7'` → số 7 (mã ký tự liên tiếp nhau) |
| `Character.isDigit(c)` | kiểm từng ký tự là chữ số |
| `StringBuilder.append` | ghép chữ số thành chuỗi kết quả |

---

## 3. Thiết kế

```
HE176322_J1SP0084_LargeNumber/src/
├── model/      LargeNumber                int[] digitArray (đơn vị ở index 0), getDigit,
│                                          toString bỏ số 0 đầu
├── repository/ NumberRepository           GIỮ 2 số (LargeNumber firstNumber, secondNumber)
│                                          saveFirstNumber/saveSecondNumber · getFirstNumber/getSecondNumber
├── dto/        MultiplyRequestDTO         2 chuỗi số            (main ──► controller)
│               MultiplyResponseDTO        2 số + tích (chuỗi)   (controller ──► view)
├── service/    LargeNumberService         chuỗi → mảng, multiplyDigits ← thuật toán của đề ở ĐÂY
├── controller/ MultiplyController         service ──► view (setResponseDTO + display 1 lần)
├── view/       MultiplyView               field responseDTO + display() không tham số: in "A x B = R"
├── constants/  Message, Constants         câu chữ · BASE = 10
├── utils/      Validation                 getDigits(chuỗi) → chuỗi toàn chữ số hoặc ném lỗi
└── main/       Main                       final + private Main(); Scanner + gọi controller 1 lần
```

| Câu hỏi thiết kế | Trả lời |
|---|---|
| **Sao bài có `repository`?** | Tờ checklist giấy 1.1: *"**Bắt buộc phải có repository**"* — kể cả bài thuật toán. Repository giữ **dữ liệu đầu vào mà thuật toán làm việc**: hai số đã đổi thành mảng chữ số (`NumberRepository.firstNumber`, `secondNumber`), với `save…/get…` đơn giản. Service **cất** hai số vào repository (bước 1 của đề) rồi **lấy từ repository** ra nhân (bước 2). Luồng đúng hình thầy vẽ: Controller → Service → Repository → Model. |
| Sao nhân **không** nằm ở repository? | Checklist 1.1: *"Repository chỉ chứa data và CRUD methods đơn giản. Nếu có nghiệp vụ tính toán thì cần thêm Services"*. Nhân số là **"tính toán nghiệp vụ"** → `LargeNumberService`. |
| Sao `LargeNumber` không tự nhân? | Model mô tả **con số** (chữ số, in ra chữ). Cách nhân là **nghiệp vụ** → nằm ở service. |
| Sao `Validation` không dùng `Long.parseLong`? | Nó từ chối đúng những số bài này sinh ra để xử lý. Kiểm **từng ký tự** 0–9. |
| Controller có thấy `LargeNumber` không? | Không — DTO chỉ chở **chuỗi**. |
| **View nhận dữ liệu thế nào?** | Qua **thuộc tính**: `MultiplyView` có field `private MultiplyResponseDTO responseDTO` + `setResponseDTO(...)`; `display()` **không tham số**. Controller gọi `setResponseDTO` rồi `display()` **đúng 1 lần** (checklist 1.1). |
| **Validate ở đâu?** | Chỉ ở **`Main`**: `inputNumber` đọc dòng, `Validation.getDigits` kiểm; sai thì in `You must input digit.` và hỏi lại. Chuỗi đã sạch mới vào `MultiplyRequestDTO`. |

**Luồng chạy:** Main → RequestDTO → Controller → Service → Repository → Model; ResponseDTO → View **1 lần**.

```
Main: đọc 2 số (hỏi lại khi sai) ──► MultiplyRequestDTO ──► controller.multiply(requestDTO)   (1 lần)
   controller ──► service.multiply(requestDTO)
                     ├─ numberRepository.saveFirstNumber(toLargeNumber(first))     ← bước 1 của đề
                     ├─ numberRepository.saveSecondNumber(toLargeNumber(second))
                     ├─ multiplyDigits(repo.getFirstNumber(), repo.getSecondNumber())
                     │                                    ← bước 2: thuật toán của đề chạy ở đây
                     └─ 3 × toString()                    ← bước 3: bỏ số 0 đầu
   controller ──► view.setResponseDTO(responseDTO) ──► view.display()   (1 lần)
```

### 3.1 Design Pattern trong bài

| Pattern | Ở đâu |
|---|---|
| **MVC** — thầy gọi là "MVC JSP" | controller điều hướng (như Servlet) · view hiển thị (như trang JSP) · model là JavaBean |
| **Facade** | controller: `Main` chỉ gọi `controller.multiply(requestDTO)`, không biết service/repository/model/view phía sau |

> Bài chỉ 60 LOC nên **không thêm lớp pattern GoF** — ghi chú slide SOLID của thầy cảnh báo *"trừu tượng hoá sớm … vi phạm YAGNI"*. Phép nhân schoolbook nằm gọn trong **một hàm** `multiplyDigits` của `LargeNumberService`.

**Thầy hỏi "dùng pattern gì cho dễ mở rộng?"** → trả lời đủ 4 yếu tố GoF (slide *"Elements of a Design Pattern"*):

| Yếu tố | Trả lời |
|---|---|
| **Name** | Strategy (nhóm Behavioral) |
| **Problem** | có nhiều thuật toán nhân số lớn (schoolbook, Karatsuba) cho cùng kết quả |
| **Solution** | tách `interface MultiplyStrategy { LargeNumber multiply(LargeNumber a, LargeNumber b); }`; mỗi cách làm là 1 lớp `implements` nó (`SchoolbookMultiplyStrategy`, `KaratsubaMultiplyStrategy`); `LargeNumberService` nhận strategy qua constructor |
| **Consequences** | ➕ thêm cách làm = thêm 1 lớp, service đứng yên (**O**) · ➖ thêm 2 file — chỉ đáng khi có từ 2 cách trở lên |

### 3.2 SOLID trong bài

| Nguyên lý | Ở đâu |
|---|---|
| **S** | `LargeNumber` giữ chữ số · `NumberRepository` giữ 2 số · `LargeNumberService` đổi chuỗi + nhân · `Validation` kiểm · `MultiplyView` in |
| **O** | đổi thuật toán chỉ sửa **một hàm** `multiplyDigits` |
| **L** | bài chưa có lớp con riêng — chỉ `extends Object` |
| **I** | không có interface — bài chưa cần |
| **D** | `Main` chỉ phụ thuộc controller + DTO |

---

## 4. Code từng bước

> Thầy: *"code cái **model trước rồi đến data**"* (V8).

| Bước | File | Việc |
|---|---|---|
| 1 | `model/LargeNumber.java` | `private int[] digitArray` + constructor rỗng (số 0) + constructor đủ + get/set + `getLength` · `getDigit` · `toString` |
| 2 | `dto/MultiplyRequestDTO.java`, `MultiplyResponseDTO.java` | JavaBean |
| 3 | `repository/NumberRepository.java` | 2 field `LargeNumber firstNumber`, `secondNumber` (constructor: số 0) + `saveFirstNumber` · `saveSecondNumber` · `getFirstNumber` · `getSecondNumber` |
| 4 | `service/LargeNumberService.java` | constructor `new NumberRepository()`; `multiply(requestDTO)`; `toLargeNumber` (chuỗi → mảng); **`multiplyDigits`** (2 vòng lồng + vòng nhớ, `private`) |
| 5 | `view/MultiplyView.java` | field `responseDTO` · `setResponseDTO` · `display()` không tham số |
| 6 | `controller/MultiplyController.java` | `new LargeNumberService()`; `multiply(requestDTO)` = `setResponseDTO` + `display()` 1 lần |
| 7 | `constants/Message.java`, `Constants.java` | prompt, lỗi, `BASE = 10` |
| 8 | `utils/Validation.java` | `getDigits` kiểm từng ký tự |
| 9 | `main/Main.java` | `public final class Main` + `private Main()`; `inputNumber(sc, prompt)` + gọi controller **1 lần** |

**Bẫy hay gặp:**

1. Lưu chữ số **trái sang phải** mà vẫn viết `resultArray[i + j]` → sai vị trí. Hoặc đảo khi đọc, hoặc tính
   `(lenA-1-i) + (lenB-1-j)`.
2. Bỏ số 0 đầu bằng "dừng ở chữ số khác 0 đầu tiên" → nhập `0 × 5` in ra **dòng trống**. Vòng phải
   dừng ở **index 0** (`top > 0`).
3. Nhớ ngay trong vòng nhân vẫn đúng nhưng dễ sai; tách **một vòng nhớ** riêng sau cùng thì gọn.
4. `Integer.parseInt` để kiểm "có phải số" → số 20 chữ số bị báo sai.
5. Viết `k < resultArray.length - 1` hay `top > 0 && digitArray[top] == 0` không ngoặc → sai
   checklist 3.3. Viết `k < (resultArray.length - 1)` và `(top > 0) && (digitArray[top] == 0)`.

---

## 5. Test trước khi gọi thầy

| # | Gõ | Phải thấy |
|---|---|---|
| 1 | `123456789123456789` / `987654321987654321` | `… = 121932631356500531347203169112635269` (màn hình đề) |
| 2 | `123` / `45` | `123 x 45 = 5535` (ví dụ đề) |
| 3 | *(Enter trống)* | `You must input digit.` rồi hỏi lại |
| 4 | `-5` · `12.5` · `1 2` · `abc` | mỗi lần `You must input digit.` |
| 5 | số thứ hai `x9` | `You must input digit.` rồi hỏi lại **đúng** "Enter the second number: " |
| 6 | `0` / `0` | `0 x 0 = 0` |
| 7 | `000` / `000123` | `0 x 123 = 0` (bỏ số 0 đầu cả 2 số) |
| 8 | `99999999999999999999` × chính nó | `9999999999999999999800000000000000000001` (dùng hết m+n ô) |
| 9 | `9` / `9` | `9 x 9 = 81` |

---

## 6. Debug

| Việc | Cách làm |
|---|---|
| Breakpoint | dòng `resultArray[i + j] += first.getDigit(i) * second.getDigit(j);` |
| Chạy | **Ctrl+F5**, nhập `123` và `45` |
| Quan sát | tab **Variables**: `i`, `j`, mở mảng `resultArray` — so với bảng mục 2.3 |
| Vòng nhớ | breakpoint `resultArray[k + 1] += …` — xem 15 → 5, ô sau +1 |
| Repository | breakpoint `firstNumber = numberRepository.getFirstNumber();` — mở `numberRepository` → `firstNumber.digitArray` = `{3, 2, 1}` (đơn vị trước) |
| F7 | ở `LargeNumberService.multiply` bấm **F7** vào `multiplyDigits(...)` → vào 2 vòng lồng |
| Bỏ 0 đầu | breakpoint trong `LargeNumber.toString`, xem `top` giảm từ 4 xuống 3 |

---

## 7. Câu hỏi thầy hay hỏi

### Thuật toán

| Câu hỏi | Trả lời mẫu |
|---|---|
| Vì sao mảng kết quả dài `lenA + lenB`? | Tích của số m chữ số và n chữ số < 10ᵐ⁺ⁿ → tối đa m + n chữ số (mục 2.4). |
| Một ô có tràn `int` không? | Mỗi lần cộng tối đa 81; muốn tràn `int` (~2,1 tỉ) cần hơn 26 triệu lần cộng vào một ô — tức số hàng chục triệu chữ số. Bài này thoải mái. |
| Độ phức tạp? | O(m × n). |
| Sao lưu đơn vị ở index 0? | Để chữ số i × chữ số j rơi vào ô `i + j` đúng như đề viết. |
| Nhập `0`? | `toString` giữ lại ít nhất 1 chữ số → in `0`. |
| Số âm? | Đề nói "numbers" nhưng thuật toán chữ số không có dấu; bài từ chối `-` bằng `You must input digit.` (giống bản tham chiếu). Muốn có dấu: tách dấu, nhân phần số, dấu = âm khi đúng một số âm. |

### OOP / Java

| Câu hỏi | Trả lời mẫu |
|---|---|
| 4 tính chất OOP ở đâu? | **Đóng gói**: `digitArray` `private` trong `LargeNumber`, đọc qua `getDigit`. **Kế thừa**: mọi lớp ngầm `extends Object`; `LargeNumber` ghi đè `toString()`. **Đa hình**: `toString()` có `@Override` — in tích tự bỏ số 0 đầu. **Trừu tượng**: `Main` chỉ gọi `controller.multiply(requestDTO)`. |
| `multiplyDigits` trả `LargeNumber` mà không `void`? | Tích là **số mới**; hai số vào không đổi. (`multiply` của service trả `MultiplyResponseDTO` — gói sẵn 3 chuỗi cho view.) |
| `saveFirstNumber` trả `void`? | Chỉ **cất** số vào repository; lấy ra bằng `getFirstNumber()`. |
| Sao tên `digitArray` mà không `digits`? | Checklist 1.5: *"tên biến kiểu Array kết thúc bằng Array"*. |
| `getDigits` (Validation) trả `String` mà không `long`? | Số có thể dài hơn mọi kiểu số của Java. |
| `toLargeNumber` sao `private`? | Chỉ `LargeNumberService` dùng. |
| `getDigit`, `getLength` sao `public`? | `LargeNumberService` (lớp khác, package `service`) gọi. |
| `Validation.getDigits` static — bỏ đi thì sao? | Không dùng dữ liệu đối tượng nào. Bỏ `static` thì `Validation.getDigits(...)` lỗi biên dịch; phải bỏ constructor `private`, `new Validation()` trong Main rồi gọi qua đối tượng. |
| `Main.inputNumber` static — sao được? | Guide: main *"cấm static với biến, có thể dùng với hàm"*; `main()` là static nên hàm nó gọi trực tiếp cũng phải static. Scanner là biến **cục bộ** truyền vào. |
| Sao `inputNumber(sc, prompt)` có 2 tham số? | Dùng chung cho 2 lần hỏi, chỉ khác câu hỏi. Thầy cấm **3** tham số (V4) — 2 là được. |
| Sao `int[]` mà không `ArrayList<Integer>`? | Đề: *"use arrays"*; kích thước biết trước (`lenA + lenB`); `int` không phải bọc `Integer`. |
| Constructor rỗng `LargeNumber()` để làm gì? | JavaBean — MVC kiểu JSP (V10); nó cho số 0 (1 ô chữ số 0). |

---

## 8. Thầy đổi yêu cầu tại chỗ

| Thầy bảo | Sửa | Không đụng |
|---|---|---|
| Thêm **cộng** 2 số lớn | thêm hàm cộng trong service (từng ô + nhớ), thêm field vào ResponseDTO, 1 dòng Message + view | Validation, Main (nếu vẫn 2 số) |
| Cho phép **số âm** | `Validation.getDigits` nhận 1 dấu `-` đầu; service tách dấu, nhân phần số, ghép dấu | `multiplyDigits` |
| Thuật toán khác (Karatsuba) | thay thân `multiplyDigits` — hoặc tách **Strategy** (mục 3.1) nếu cần giữ cả hai | main, view |
| In thêm **số chữ số** của tích | field `productLength` trong ResponseDTO, dòng Message, view | `multiplyDigits` |

---

## 9. Chỗ khác với đề / bản cũ

| Chỗ | Đề / bản cũ | Bài này | Lý do |
|---|---|---|---|
| Kiến trúc | bản cũ `entity/ui`, `BigNumber.multiply` trong model, Scanner trong `Validator` | MVC theo Guide; nhân ở **service** | luật thầy; thuật toán = nghiệp vụ |
| **Repository** | bản trước: không có (*"không lưu gì, không CRUD"*) | `repository/NumberRepository` giữ 2 số; service cất vào rồi lấy ra nhân | tờ checklist 1.1: *"Bắt buộc phải có repository"* |
| View | bản trước: `setResponse(...)`, field `response` | `setResponseDTO(...)`, field `responseDTO`, `display()` không tham số | checklist 1.1 — View nhận qua thuộc tính |
| Tên mảng | bản trước: `digits`, `result` (+ `getDigits/setDigits` của model) | `digitArray`, `resultArray` (+ `getDigitArray/setDigitArray`) | checklist 1.5 — mảng kết thúc bằng `Array` |
| Ngoặc | bản trước: `top > 0 && digits[top] == 0`, `k < result.length - 1`, `input == null \|\| …` | `(top > 0) && (digitArray[top] == 0)`, `k < (resultArray.length - 1)`, `(input == null) \|\| …` | checklist 3.3 |
| Khai báo | bản trước: `StringBuilder text` giữa hàm, `String text` sau `if`, `String line` trong vòng lặp | gom lên đầu block, khởi tạo luôn (`String line = "";`) | checklist 2.6, 3.7 |
| `Main` | bản trước: `public class Main`, biến `dto` | `public final class Main` + `private Main()`, biến `requestDTO` | checklist 3.4 |

---

## 10. Tờ checklist 25 mục — bài này đạt thế nào

| Mục | Chỉ vào đâu |
|---|---|
| **1.1** MVC + repository | `repository/NumberRepository.java` giữ `firstNumber`, `secondNumber`; `LargeNumberService.multiply` cất vào rồi lấy ra; `MultiplyController.multiply` gọi `setResponseDTO` + `display()` **1 lần**; `Main` gọi controller **1 lần** |
| **1.5** tên biến | `digitArray`, `resultArray` (mảng → `Array`), `requestDTO`, `responseDTO`; không còn `ID` |
| **2.6 / 3.7** khai báo đầu block + khởi tạo | `LargeNumberService.multiply` (4 biến ở đầu, `= null` rồi mới gán), `LargeNumber.toString` (`StringBuilder text`, `int top`), `Validation.getDigits` (`String text = "";`), `Main.inputNumber` (`String line = "";`) |
| **2.8** dòng trống | trước mọi comment (kể cả comment field trong DTO, `Message`), sau vùng khai báo biến, sau mỗi `}` |
| **3.3** ngoặc tường minh | `LargeNumber.toString`: `while ((top > 0) && (digitArray[top] == 0))`; `multiplyDigits`: `k < (resultArray.length - 1)`; `Validation.getDigits`: `if ((input == null) \|\| input.trim().isEmpty())` |
| **3.4** lớp chỉ có static | `Main`, `Validation`, `Message`, `Constants`: `final` + constructor `private` |
| Dòng trống giữa input và kết quả | chữ đề bị dính dòng, không rõ | không có dòng trống | giữ nguyên bản tham chiếu đã kiểm |
| Số âm, số thập phân | đề không nói | từ chối `You must input digit.` | giống bản tham chiếu; thuật toán chữ số không dấu |
| In lại số đã nhập | — | in dạng **đã bỏ số 0 đầu** (`0012345` → `12345`) | câu hỏi và đáp án cùng một dạng số (giống bản tham chiếu) |
