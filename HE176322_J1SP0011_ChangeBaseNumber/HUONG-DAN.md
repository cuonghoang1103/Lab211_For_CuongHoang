# J1.S.P0011 — Change Base Number System (2, 10, 16)

> Bài thuật toán 100 LOC, 2 slot — **vẫn phải MVC** và **vẫn phải có repository** (tờ checklist 1.1). Hai
> thuật toán đổi hệ **viết tay** đúng như 4 ví dụ của đề (chạy tay ở mục 2), không dùng
> `Integer.parseInt(s, 16)` / `Integer.toString(n, 2)`.

| | |
|---|---|
| Loại / LOC | Short Assignment · 100 LOC · 2 slot |
| Project | `HE176322_J1SP0011_ChangeBaseNumber` |
| Chạy | NetBeans: **File ▸ Open Project** → **F6** |
| Lớp chạy | `main.Main` |
| Kiểm tự động | `python3 _tools/verify.py J1SP0011` → 5 kịch bản × 2 locale |

---

## 1. Đề bài nói gì

- Chọn **hệ vào** (1 = nhị phân, 2 = thập phân, 3 = thập lục phân), chọn **hệ ra**, nhập **giá trị**,
  in giá trị tương đương.
- **Lặp lại cho tới khi người dùng đóng chương trình.**
- Guidelines: 4 ví dụ tính tay — 535 DEC → HEX, 217 HEX → DEC, 27 DEC → BIN, 11011 BIN → DEC.

Đề **không có** màn hình mẫu (mục *Expectation of User interface* trong file đề để trống; 5 hình của đề là
1 hình minh hoạ + 4 hình ví dụ tính tay) và **không bắt tên hàm nào**; bài giữ đúng màn hình bản tham chiếu đã kiểm:

```
======= CHANGE BASE NUMBER SYSTEM =======
1. Binary (base 2)
2. Decimal (base 10)
3. Hexadecimal (base 16)
0. Exit
=========================================
Choose the INPUT base: 2
Choose the OUTPUT base: 3
Enter the input value: 535
535 (DEC) = 217 (HEX)
```

**Đề bắt buộc:**

| Thứ | Đề viết | Bài này đặt ở |
|---|---|---|
| Chọn hệ vào / hệ ra (1, 2, 3) | Function details | `Main.inputBaseIn` / `inputBaseOut` + enum `constants/Base.findByChoice` |
| Nhập giá trị | Function details | `Main.inputValue` + `Validation.getValue` (không trống) + `Validation.checkValue` (chữ số đúng hệ) |
| Đổi hệ: chia lấy dư (DEC → 2/16) | ví dụ 1, 3 | `PositionalBaseStrategy.convertFromDecimal` |
| Đổi hệ: tổng chữ số × cơ số^vị trí (2/16 → DEC) | ví dụ 2, 4 | `PositionalBaseStrategy.convertToDecimal` |
| Lặp tới khi đóng | Program Specifications | vòng `while (running)` trong `Main`, chọn `0` để thoát |

---

## 2. Kiến thức cần biết

### 2.1 Ý tưởng chung: mọi đường đều **qua thập phân**

```
chuỗi ở hệ vào ──convertToDecimal──► long (con số thật) ──convertFromDecimal──► chuỗi ở hệ ra
```

3 hệ, 9 cặp (2→16, 16→2, 10→10…) nhưng chỉ cần **2 thuật toán**. Thêm hệ 8 vẫn chỉ 2 thuật toán.

### 2.2 Ví dụ 1 (đề): 535 DEC → HEX — **chia lấy dư**, đọc dư từ **dưới lên**

| Bước | Phép chia | Thương | Dư | Chữ số |
|---|---|---|---|---|
| 1 | 535 : 16 | 33 | 7 | `7` |
| 2 | 33 : 16 | 2 | 1 | `1` |
| 3 | 2 : 16 | 0 | 2 | `2` → thương = 0, **dừng** |

Dư theo thứ tự ra: `7, 1, 2` → đọc ngược: **217** (HEX). Trong code: `append` từng dư vào
`StringBuilder` (`"712"`) rồi `reverse()` một lần → `"217"`.

### 2.3 Ví dụ 3 (đề): 27 DEC → BIN

| Phép chia | Thương | Dư |
|---|---|---|
| 27 : 2 | 13 | 1 |
| 13 : 2 | 6 | 1 |
| 6 : 2 | 3 | 0 |
| 3 : 2 | 1 | 1 |
| 1 : 2 | 0 | 1 |

Dư `1,1,0,1,1` đọc ngược → **11011** (BIN).

### 2.4 Ví dụ 2 (đề): 217 HEX → DEC — **chữ số × 16^vị trí**, vị trí đếm **từ phải, bắt đầu 0**

| index (từ phải) | chữ số | power = 16^index | chữ số × power | tổng dồn |
|---|---|---|---|---|
| 0 | 7 | 1 | 7 | 7 |
| 1 | 1 | 16 | 16 | 23 |
| 2 | 2 | 256 | 512 | **535** |

> Hình của đề ghi `0*16⁰` ở ô cuối — **lỗi đánh máy** của đề (giá trị ô đó là 7). Tổng đúng
> 2·256 + 1·16 + 7·1 = 535, như đề kết luận.

### 2.5 Ví dụ 4 (đề): 11011 BIN → DEC

| index | 0 | 1 | 2 | 3 | 4 |
|---|---|---|---|---|---|
| chữ số (từ phải) | 1 | 1 | 0 | 1 | 1 |
| 2^index | 1 | 2 | 4 | 8 | 16 |
| tích | 1 | 2 | 0 | 8 | 16 |

Tổng = 1 + 2 + 0 + 8 + 16 = **27**.

### 2.6 Chữ số hex và kiểm chữ số hợp lệ

`Constants.DIGITS = "0123456789ABCDEF"` — vị trí trong chuỗi **là** giá trị: `indexOf('B') = 11`. Một
ký tự sai theo **hai kiểu**, phải bắt cả hai:

| Gõ | Hệ | `indexOf` | Lý do sai |
|---|---|---|---|
| `1G` | HEX | `G` → -1 | không phải chữ số nào |
| `1012` | BIN | `2` → 2 | là chữ số, nhưng **≥ cơ số 2** |

Chữ thường `ff` được nhận (đổi `toUpperCase()` trước). Việc kiểm này nằm ở `utils/Validation.checkValue(value, base)`,
`Main` gọi trước khi gọi controller (tờ checklist 1.1: *"Toàn bộ việc nhập dữ liệu/Validate … thực hiện ở Main"*).

### 2.7 Tràn số — kiểm **trước** khi nhân

`long` lớn nhất = 9223372036854775807. Java tràn **âm thầm** (ra số âm sai). Nên trước mỗi bước:
`power > (Long.MAX_VALUE / radix)` → không nhân tiếp được; `digit > ((Long.MAX_VALUE - result) / power)`
→ cộng vào sẽ vượt. Cả hai → `The value is too big for this program.` Số 0 đầu được bỏ trước
(`000…01` dài 81 ký tự vẫn ra 1, không bị báo "quá lớn").

### 2.8 Java dùng trong bài

| API | Dùng làm gì |
|---|---|
| `"0123456789ABCDEF".indexOf(c)` | ký tự → giá trị (−1 nếu không phải chữ số) |
| `DIGITS.charAt(dư)` | giá trị → ký tự |
| `StringBuilder.append` + `reverse()` | ghép dư rồi đảo 1 lần |
| enum `Base.findByChoice(choice)` = `values()[choice - 1]` | số menu → hệ |

---

## 3. Thiết kế

```
HE176322_J1SP0011_ChangeBaseNumber/src/
├── model/      BaseNumber              chuỗi chữ số + hệ của nó; toString "535 (DEC)"
├── repository/ BaseNumberRepository    GIỮ số cần đổi (model): saveBaseNumber (Create) · getBaseNumber (Read)
├── dto/        ConvertRequestDTO       hệ vào + hệ ra + giá trị   (main ──► controller)
│               ConvertResponseDTO      "535 (DEC)" + "217 (HEX)"  (controller ──► view)
├── service/    IBaseStrategy           «interface» convertToDecimal · convertFromDecimal
│               PositionalBaseStrategy  2 thuật toán tay của đề ← ở ĐÂY
│               ConvertService          cất số vào repository → long → hệ ra (Context)
├── controller/ ConvertController       cắm PositionalBaseStrategy; setResponseDTO + display() 1 lần
├── view/       ConvertView             field responseDTO; display() in "A = B"
├── constants/  Base                    enum BIN/DEC/HEX: cơ số + tên ngắn + findByChoice (bảng tra)
│               Message, Constants      câu chữ · số menu, DIGITS, dấu
├── utils/      Validation              getChoice(chuỗi, min, max) · getValue(chuỗi) · checkValue(giá trị, hệ)
└── main/       Main (final, ctor private)  vòng menu + Scanner + validate; mỗi vòng gọi controller 1 lần
```

| Câu hỏi thiết kế | Trả lời |
|---|---|
| Sao bài có `repository`? | Tờ checklist 1.1: *"**Bắt buộc phải có repository**"*. Repository = **dữ liệu** + CRUD đơn giản: ở đây là **dữ liệu đầu vào** của thuật toán — số người dùng gõ, cùng hệ của nó (model `BaseNumber`) — với `saveBaseNumber` / `getBaseNumber`. **Đổi hệ** là nghiệp vụ nên nằm ở `service` — đúng tầng *Controller ↔ Services ↔ Repository ↔ Model*. |
| Sao `Base` là enum mà không 3 hằng `int`? | Với `int`, `convert(v, 2, 16)` và gõ nhầm `convert(v, 3, 16)` đều biên dịch được. Enum chỉ có đúng 3 giá trị; mỗi hằng mang **cơ số** và **tên in** (`HEX`) — bảng tra theo hệ. |
| Sao model là `BaseNumber` (chuỗi + hệ)? | `"11"` là 3 ở BIN, 11 ở DEC — chuỗi một mình **không phải** con số; cặp (chuỗi, hệ) mới là. |
| Kiểm chữ số hợp lệ ở đâu? | Ở **`Main`**, qua `Validation.checkValue(value, base)` — tờ checklist 1.1: *"Toàn bộ việc nhập dữ liệu/Validate … thực hiện ở Main"*. Hàm nhận **hệ vào** nên biết `2` có hợp lệ ở BIN không. Sai thì `Main` in lý do rồi **về menu** (giống bản tham chiếu); đúng thì mới gọi controller. |
| Controller có đụng model không? | Không. Controller chỉ import DTO, service, view. Service cất số vào repository, lấy lại `BaseNumber`, đổi hệ, rồi đóng chữ vào `ConvertResponseDTO`. |
| Lỗi "quá lớn" đi đường nào? | Tràn `long` chỉ biết được **khi đang tính** → `PositionalBaseStrategy` `throw new Exception(Message.TOO_BIG)`, controller để lỗi đi tiếp (`throws Exception`), `Main` bắt và in `e.getMessage()` (đúng mẫu Main của thầy). |

**Luồng chạy:**

```
Main (vòng): in menu ── inputBaseIn ── 0? → "Goodbye." thoát
             └─ inputRequest: inputBaseOut ── inputValue ──► ConvertRequestDTO
                Validation.checkValue(value, inputBase)   ← sai: in lý do, về menu
                controller.convert(requestDTO)            (gọi controller 1 lần)
   controller ──► service.convert(requestDTO)
                     ├─ repository.saveBaseNumber(requestDTO); input = repository.getBaseNumber()
                     ├─ long v = baseStrategy.convertToDecimal(input)             ← ví dụ 2, 4
                     ├─ output = baseStrategy.convertFromDecimal(v, outputBase)   ← ví dụ 1, 3
                     └─ responseDTO: input.toString(), output.toString()
   controller ──► view.setResponseDTO(responseDTO) ──► view.display()  →  "535 (DEC) = 217 (HEX)"   (render 1 lần)
Main: catch → in "1G is not a valid HEX number." / "The value is too big …" rồi quay lại menu
```

Tầng: **Main → RequestDTO → Controller → Service → Repository → Model**; kết quả **ResponseDTO → View**, render 1 lần.

### 3.1 Design Pattern — **Strategy** (+ bảng tra enum)

| Yếu tố | Trong bài này |
|---|---|
| **Name** | Strategy (Behavioral) |
| **Problem** | Đổi hệ có hơn một cách: **tay** (đề bắt — chia lấy dư, tổng lũy thừa), **thư viện** (`Long.parseLong(s, r)` / `Long.toString(n, r)`), hoặc **gom nhóm bit** (BIN↔HEX mỗi 4 bit = 1 chữ số hex). Viết thẳng vào service thì đổi cách phải sửa service. |
| **Solution** | `IBaseStrategy` = **Strategy** (`convertToDecimal`, `convertFromDecimal`). `PositionalBaseStrategy` = **ConcreteStrategy** (2 thuật toán tay). `ConvertService` = **Context**, nhận strategy qua constructor. `ConvertController` **chọn**: `new ConvertService(new PositionalBaseStrategy())`. Enum `Base` là **bảng tra theo hệ** (cơ số, tên, số menu) — thuật toán không có `if (base == 16)` nào. |
| **Consequences** | ✅ Thêm cách đổi = **thêm 1 class** + sửa 1 dòng controller (OCP, DIP). ✅ Thêm **hệ 8** = thêm 1 hằng `OCTAL(8, "OCT")` + 1 dòng menu + `BASE_MAX = 4` — thuật toán không đổi một chữ. ❌ Thêm 1 interface so với viết thẳng. |

**Thầy bảo "dùng thư viện xem"**: tạo `LibraryBaseStrategy implements IBaseStrategy` với
`Long.parseLong(value, base.getRadix())` và `Long.toString(v, radix).toUpperCase()`, sửa 1 dòng controller.

Pattern khác: **MVC**, **Facade** = `ConvertController`.

### 3.2 SOLID

| Nguyên lý | Ở đâu |
|---|---|
| **S** | `BaseNumber` mô tả số viết ở 1 hệ · `BaseNumberRepository` giữ số · `PositionalBaseStrategy` đổi · `ConvertService` điều phối · `Validation` kiểm menu + chữ số · `ConvertView` in |
| **O** | thêm hệ = thêm hằng enum; thêm thuật toán = thêm lớp strategy |
| **L** | mọi `XxxBaseStrategy` thay được cho nhau |
| **I** | `IBaseStrategy` chỉ 2 hàm, lớp nào cũng cần cả 2 |
| **D** | `ConvertService` phụ thuộc `IBaseStrategy` (trừu tượng) |

---

## 4. Code từng bước

> Thầy: *"code cái **model trước rồi đến data**"* (V8). `Base` là kiểu field của model nên gõ trước.

| Bước | File | Việc |
|---|---|---|
| 1 | `constants/Base.java` | enum 3 hằng (cơ số, tên) + `findByChoice` |
| 2 | `model/BaseNumber.java` | `value` + `base` `private`, constructor rỗng + đủ, get/set, `toString` |
| 3 | `repository/BaseNumberRepository.java` | field `baseNumber`; `saveBaseNumber(requestDTO)`, `getBaseNumber()` |
| 4 | `dto/ConvertRequestDTO.java`, `ConvertResponseDTO.java` | JavaBean |
| 5 | `service/IBaseStrategy.java` | interface 2 hàm |
| 6 | `service/PositionalBaseStrategy.java` | **convertToDecimal** (tổng lũy thừa, kiểm tràn) · **convertFromDecimal** (chia lấy dư) · `removeLeadingZeros` `private` |
| 7 | `service/ConvertService.java` | `convert(requestDTO)`: repository → strategy → responseDTO |
| 8 | `view/ConvertView.java` | field `responseDTO` + `setResponseDTO` · `display` |
| 9 | `controller/ConvertController.java` | cắm strategy; `convert(requestDTO)` |
| 10 | `constants/Message.java`, `Constants.java` | menu, câu lỗi; `DIGITS`, số menu, dấu |
| 11 | `utils/Validation.java` | `getChoice` (tách 2 lỗi) · `getValue` · `checkValue` (dấu, chữ số đúng hệ) |
| 12 | `main/Main.java` | vòng menu + `inputRequest` + 3 hàm nhập; `checkValue` rồi gọi controller trong 1 `try` |

**Bẫy hay gặp:**

1. Quên **đọc dư ngược** → 535 ra `712` thay vì `217`.
2. `convertFromDecimal(0)`: vòng `while (left > 0)` chạy 0 lần → chuỗi rỗng. Phải trả `"0"` riêng.
3. Chỉ kiểm `indexOf < 0` → `2` ở hệ BIN lọt qua, in số sai. `Validation.checkValue` kiểm cả `>= radix`.
4. Đếm vị trí **từ trái** → 217 thành 2·1 + 1·16 + 7·256. Vị trí đếm **từ phải**, bắt đầu 0.

---

## 5. Test trước khi gọi thầy

| # | Vào / Ra | Gõ | Phải thấy |
|---|---|---|---|
| 1 | 2 / 3 | `535` | `535 (DEC) = 217 (HEX)` (ví dụ 1) |
| 2 | 3 / 2 | `217` | `217 (HEX) = 535 (DEC)` (ví dụ 2) |
| 3 | 2 / 1 | `27` | `27 (DEC) = 11011 (BIN)` (ví dụ 3) |
| 4 | 1 / 2 | `11011` | `11011 (BIN) = 27 (DEC)` (ví dụ 4) |
| 5 | 1 / 3 · 3 / 1 | `11011` · `1B` | `= 1B (HEX)` · `= 11011 (BIN)` |
| 6 | 1/1 · 3/3 · 2/2 | `101` · `0` · `007` | `= 101 (BIN)` · `= 0 (HEX)` · `007 (DEC) = 7 (DEC)` |
| 7 | 3 / 1 · 3 / 2 | `ff` · `-1A` | `= 11111111 (BIN)` · `= -26 (DEC)` |
| 8 | 2 / 1 | `-27` · `+15` | `= -11011 (BIN)` · `= 1111 (BIN)` |
| 9 | menu vào | `x` · `5` | `You must input a number.` · `Please choose from 0 to 3.` |
| 10 | menu ra | `x` · `5` · `0` | `You must input a number.` · `Please choose from 1 to 3.` (×2) |
| 11 | giá trị | *(trống)* | `You must input something.` rồi hỏi lại |
| 12 | 3 / 2 · 1 / 2 · 2 / 3 | `1G` · `2` · `12a` | `… is not a valid HEX / BIN / DEC number.` rồi về menu |
| 13 | 2 / 1 | `-` | `- is not a valid DEC number.` |
| 14 | 2 / 3 | `9223372036854775807` | `= 7FFFFFFFFFFFFFFF (HEX)` |
| 15 | 2 / 3 · 1 / 2 | `9223372036854775808` · `1` + 63 số `0` | `The value is too big for this program.` |
| 16 | vào | `0` | `Goodbye.` và thoát |

---

## 6. Debug

| Việc | Cách làm |
|---|---|
| Breakpoint 1 | `result = result + (digit * power);` trong `convertToDecimal` |
| Chạy | **Ctrl+F5**, chọn 3 → 2, gõ `217` |
| Quan sát | tab **Variables**: `index`, `digit`, `power`, `result` — so với bảng mục 2.4 (7, 23, 535) |
| Breakpoint 2 | `digits.append(...)` trong `convertFromDecimal`, chọn 2 → 3, gõ `535` — `left` 535 → 33 → 2 → 0, `digits` `"7"`,`"71"`,`"712"`, sau `reverse` `"217"` |
| Đa hình | ở `ConvertService.convert` bấm **F7** vào `baseStrategy.convertToDecimal(input)` → nhảy vào `PositionalBaseStrategy` |
| Repository | ở `ConvertService.convert` xem `input` sau `getBaseNumber()`: chính là `value` + `base` vừa `saveBaseNumber` |
| Lỗi chữ số | gõ `1G` (HEX): F8 trong `Validation.checkValue` thấy `digit = -1` → `throw` → rơi vào `catch` của `Main`, controller **không** được gọi |

---

## 7. Câu hỏi thầy hay hỏi

### Thuật toán

| Câu hỏi | Trả lời mẫu |
|---|---|
| Sao đi qua thập phân? | 2 thuật toán cho mọi cặp hệ, thay vì 1 quy tắc cho mỗi cặp. `long` là **con số**, hệ chỉ tồn tại ở chuỗi hai đầu. |
| Sao đọc dư ngược? | Phép chia đầu cho **chữ số hàng đơn vị** (phải nhất); chữ số cao nhất ra sau cùng. |
| Độ phức tạp? | O(số chữ số) cho cả hai chiều (mỗi chữ số một phép nhân/chia). |
| Đổi BIN → HEX nhanh hơn? | Gom 4 bit thành 1 chữ số hex (`1 1011` → `1B`) — viết được thành một Strategy khác. |
| Giá trị lớn nhất? | 9223372036854775807 (`Long.MAX_VALUE`); lớn hơn → báo `too big` (kiểm **trước** khi nhân vì tràn là âm thầm). |
| Số âm? | Tách dấu `-` ra, đổi phần chữ số, gắn dấu lại (`-27` → `-11011`). Không làm bù 2 (two's complement). |

### OOP / Java

| Câu hỏi | Trả lời mẫu |
|---|---|
| 4 tính chất OOP ở đâu? | **Đóng gói**: `value`, `base` `private` trong `BaseNumber`; `baseNumber` `private` trong repository; `radix`, `label` `private final` trong enum. **Kế thừa**: `PositionalBaseStrategy implements IBaseStrategy`; `BaseNumber` ghi đè `toString()` của `Object`. **Đa hình**: service gọi `baseStrategy.convertToDecimal(...)` qua biến interface. **Trừu tượng**: interface `IBaseStrategy` — service không biết đổi bằng tay hay thư viện. |
| Sao bài có repository? | Tờ checklist 1.1 *"Bắt buộc phải có repository"*. `BaseNumberRepository` giữ **dữ liệu đầu vào** của thuật toán (số + hệ vào) — chỉ `saveBaseNumber`/`getBaseNumber`, không đổi hệ, không in. `ConvertService` cất vào rồi **lấy lại từ đó** để đổi. |
| View nhận dữ liệu thế nào? | Qua **thuộc tính**: controller gọi `convertView.setResponseDTO(responseDTO)` rồi `convertView.display()` — `display()` **không tham số**, gọi **1 lần** mỗi vòng. |
| Validate ở đâu? | Ở `Main` qua `utils/Validation`: `getChoice` (menu), `getValue` (không trống — sai thì hỏi lại ô đó), `checkValue` (chữ số đúng hệ — sai thì in lý do rồi về menu). Controller/service chỉ nhận giá trị đã hợp lệ; lỗi còn lại duy nhất là **tràn** (chỉ biết khi tính) — service ném, `Main` in. |
| Sao interface tên `IBaseStrategy`, hàm tên `convertToDecimal`? | Tờ checklist 1.3: *"Tên của interface bắt đầu bằng I"*; 1.4: *"Tên method bắt đầu bằng động từ"* — `toDecimal`/`fromDecimal`/`fromChoice` không mở đầu bằng động từ → `convertToDecimal`/`convertFromDecimal`/`findByChoice`. Đề không bắt tên nào nên không có gì phải hỏi thầy. |
| `convertToDecimal` trả `long` mà không `int`? | `int` chỉ tới ~2,1 tỉ; `long` tới ~9,2 × 10¹⁸ — đổi được nhiều số hơn. |
| `convertFromDecimal` trả `BaseNumber` mà không `String`? | Kết quả là **số viết ở một hệ** — cần cả chuỗi lẫn hệ để in `217 (HEX)`. |
| `removeLeadingZeros` sao `private`? | Chỉ `PositionalBaseStrategy` dùng — không phải hợp đồng. |
| `Base.findByChoice` sao `static`? | Nó hỏi **cả kiểu enum** "hằng nào ứng với số 2?", không thuộc riêng hằng nào. `static` được phép trong `constants` (V3). Bỏ `static` thì phải có sẵn một hằng mới gọi được — vô lý. |
| `Validation.getChoice` có 3 tham số — trái V4? | Utils được 3 tham số, **đúng mẫu Guide** `getChoice(input, min, max)` (QUY-TAC-THAY V4). Hàm nghiệp vụ không hàm nào quá 2 — 3 dữ liệu (hệ vào, hệ ra, giá trị) gói trong `ConvertRequestDTO`. |
| Bỏ `static` ở `Validation` thì sao? | `Validation.getChoice(...)` lỗi biên dịch; phải bỏ constructor `private`, `new Validation()` trong Main. |
| Sao Main có 2 hàm `inputBaseIn`/`inputBaseOut` gần giống nhau? | Khác câu hỏi và khoảng (0–3 vs 1–3); gộp thành 1 hàm sẽ cần 4 tham số (`sc, prompt, min, max`) — thầy cấm. |
| Sao `Main` là `final` và có `private Main() { }`? | Tờ checklist 3.4: *"Class chỉ có static method thì phải có private constructor, và khai báo class là final"*. |
| Sao không `List`/`Map`? | Không cần danh sách. Tra hệ bằng enum `values()` (mảng có sẵn). Nếu cần, em khai báo `ArrayList`/`HashMap` cụ thể: `List` là interface, `ArrayList` là lớp cài đặt bằng mảng động. |

---

## 8. Thầy đổi yêu cầu tại chỗ

| Thầy bảo | Sửa | Không đụng |
|---|---|---|
| Thêm **hệ 8** | `Base` thêm `OCTAL(8, "OCT")`, `Message.MENU` thêm dòng `4. Octal (base 8)`, `Constants.BASE_MAX = 4` | **strategy, service, repository, controller, view, Validation** |
| Dùng thư viện | thêm `LibraryBaseStrategy implements IBaseStrategy` + 1 dòng `ConvertController` | service, main, view |
| Giá trị sai thì **hỏi lại** thay vì về menu | Main: gọi `Validation.checkValue` ngay trong vòng `while` của `inputValue` (truyền thêm hệ vào) | strategy, service |
| In thêm **các bước chia** | strategy ghi các dòng vào một `ArrayList<String> stepList`, thêm field vào `ConvertResponseDTO`, view in | Main |
| Thoát bằng chữ `q` | `Validation`/Main nhận `q` trước khi parse | service |

---

## 9. Chỗ khác với đề / bản cũ

| Chỗ | Đề / bản cũ | Bài này | Lý do |
|---|---|---|---|
| Màn hình | đề không có | giữ đúng từng chữ bản tham chiếu | đề im lặng → bản tham chiếu là chuẩn |
| Thoát | đề: "until users close" | chọn `0` ở hệ vào → `Goodbye.` | giống bản tham chiếu; không `System.exit` |
| `convertToDecimal` | bản cũ: Horner (`result*r + d`) | **tổng chữ số × r^index** | đúng cách tính trong hình ví dụ 2, 4 của đề (dễ đối chiếu khi thầy hỏi) |
| Hình ví dụ 2 | ghi `0*16⁰` | tính `7*16⁰` | lỗi đánh máy của đề; tổng 535 của đề chỉ đúng với 7 |
| Kiểm chữ số | bản cũ ở `bo`; bản trước ở `PositionalBaseStrategy` | `Validation.checkValue`, `Main` gọi trước controller; sai → về menu | tờ checklist 1.1: validate ở Main; màn hình không đổi |
| Số âm, dấu `+` | đề không nói | nhận | giống bản tham chiếu |
| Kiến trúc | `entity/bo/ui`, Scanner static trong `Validator`, `convert(value, from, to)` 3 tham số | MVC Guide + Strategy; 3 dữ liệu trong DTO | luật thầy (V4, V7) |
| Repository | Bản trước: *"không lưu gì giữa các lần đổi → không repository"* | có `BaseNumberRepository` giữ số cần đổi | tờ checklist 1.1: *"Bắt buộc phải có repository"* |
| View | `setResponse(response)` | field `responseDTO` + `setResponseDTO` + `display()` | tờ checklist 1.1 (nhận qua thuộc tính, render 1 lần) |
| Tên | `BaseStrategy`, `toDecimal`, `fromDecimal`, `fromChoice`, `digitValue`, `stripLeadingZeros`, `invalid`, `class Main` | `IBaseStrategy`, `convertToDecimal`, `convertFromDecimal`, `findByChoice`, (bỏ — việc kiểm sang `Validation`), `removeLeadingZeros`, `final class Main` + `private Main()` | tờ checklist 1.3, 1.4, 3.4 |
| Khai báo / ngoặc | `int input = …`, `String line = …` giữa block; `int choice;` chưa khởi tạo; `digit < 0 \|\| digit >= radix` | khai báo ở đầu block + khởi tạo; `((digit < 0) \|\| (digit >= base.getRadix()))`, `(power > (Long.MAX_VALUE / radix))`, `result + (digit * power)` | tờ checklist 2.6, 3.7, 3.3 |

---

## 10. Tờ checklist 25 mục — bài này đạt thế nào

| Mục | Chỗ trong code |
|---|---|
| 1.1 MVC + repository | `repository/BaseNumberRepository` giữ model `BaseNumber`; `ConvertService.convert` cất (`saveBaseNumber`) rồi lấy lại (`getBaseNumber`) để đổi; controller chỉ import DTO/service/view; `ConvertView` nhận `responseDTO` qua `setResponseDTO`, `display()` gọi **1 lần** mỗi vòng; mọi nhập + validate (kể cả chữ số đúng hệ) ở `Main` |
| 1.3 / 1.4 tên | interface `IBaseStrategy`; method là động từ: `convertToDecimal`, `convertFromDecimal`, `findByChoice`, `removeLeadingZeros`, `saveBaseNumber`, `checkValue`, `inputRequest` |
| 1.5 tên biến | `requestDTO`/`responseDTO`, `baseNumber`; không có `ID`; không có collection/mảng tự khai |
| 2.6 + 3.7 khai báo đầu block, có khởi tạo | `Main.main`: `requestDTO = null`, `choice = 0` ở đầu, trong `while` chỉ gán; `inputBaseIn`/`inputBaseOut`/`inputValue`: `String line = "";`; `convertToDecimal`: `digits = ""`, `result = 0`, `power = 1`, `digit = 0` ở đầu; `Validation.getChoice`: `int choice = 0;` |
| 2.8 dòng trống | trước mọi comment (kể cả comment field trong `Constants`, `Message`, DTO, model, hằng enum `Base`), sau vùng khai báo, sau `}` của `if`/`for`/`while` trước câu lệnh tiếp |
| 3.3 ngoặc | `if ((digit < 0) \|\| (digit >= base.getRadix()))`; `while ((start < (text.length() - 1)) && (text.charAt(start) == '0'))`; `if (power > (Long.MAX_VALUE / radix))`; `result = result + (digit * power);` |
| 3.4 | `public final class Main` + `private Main() { }`; `Validation`, `Constants`, `Message` cũng `final` + ctor private |
| 3.8 | không cộng chuỗi: `StringBuilder` + `reverse()` trong `convertFromDecimal`; `String.format(Message.RESULT, …)`, `String.format(Message.VALUE_FORMAT, …)` |

Kiểm lại: `python3 _tools/verify.py J1SP0011` · `python3 _tools/lint.py HE176322_J1SP0011_*` · `python3 _tools/soat_checklist.py HE176322_J1SP0011_*` → 0 `VI_PHAM`.
`RUI_RO` còn lại chỉ là `String[] args` và tham số setter/constructor trùng tên field (`this.value = value`, `this.baseStrategy = baseStrategy`) — kiểu IDE sinh, được chấp nhận. Màn hình chạy không đổi (`man-hinh-chay.txt` khớp đầu ra thật).
