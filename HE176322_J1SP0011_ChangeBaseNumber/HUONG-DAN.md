# J1.S.P0011 — Change Base Number System (2, 10, 16)

> Bài thuật toán 100 LOC, 2 slot — **vẫn phải MVC**. Hai thuật toán đổi hệ **viết tay** đúng như 4 ví
> dụ của đề (chạy tay ở mục 2), không dùng `Integer.parseInt(s, 16)` / `Integer.toString(n, 2)`.

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

Đề **không có** màn hình mẫu; bài giữ đúng màn hình bản tham chiếu đã kiểm:

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
| Chọn hệ vào / hệ ra (1, 2, 3) | Function details | `Main.inputBaseIn` / `inputBaseOut` + enum `constants/Base.fromChoice` |
| Nhập giá trị | Function details | `Main.inputValue` + `Validation.getValue` |
| Đổi hệ: chia lấy dư (DEC → 2/16) | ví dụ 1, 3 | `PositionalBaseStrategy.fromDecimal` |
| Đổi hệ: tổng chữ số × cơ số^vị trí (2/16 → DEC) | ví dụ 2, 4 | `PositionalBaseStrategy.toDecimal` |
| Lặp tới khi đóng | Program Specifications | vòng `while (running)` trong `Main`, chọn `0` để thoát |

---

## 2. Kiến thức cần biết

### 2.1 Ý tưởng chung: mọi đường đều **qua thập phân**

```
chuỗi ở hệ vào ──toDecimal──► long (con số thật) ──fromDecimal──► chuỗi ở hệ ra
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

Chữ thường `ff` được nhận (đổi `toUpperCase()` trước).

### 2.7 Tràn số — kiểm **trước** khi nhân

`long` lớn nhất = 9223372036854775807. Java tràn **âm thầm** (ra số âm sai). Nên trước mỗi bước:
`power > Long.MAX_VALUE / radix` → không nhân tiếp được; `digit > (Long.MAX_VALUE - result) / power`
→ cộng vào sẽ vượt. Cả hai → `The value is too big for this program.` Số 0 đầu được bỏ trước
(`000…01` dài 81 ký tự vẫn ra 1, không bị báo "quá lớn").

### 2.8 Java dùng trong bài

| API | Dùng làm gì |
|---|---|
| `"0123456789ABCDEF".indexOf(c)` | ký tự → giá trị (−1 nếu không phải chữ số) |
| `DIGITS.charAt(dư)` | giá trị → ký tự |
| `StringBuilder.append` + `reverse()` | ghép dư rồi đảo 1 lần |
| enum `Base.values()[choice - 1]` | số menu → hệ |

---

## 3. Thiết kế

```
HE176322_J1SP0011_ChangeBaseNumber/src/
├── model/      BaseNumber              chuỗi chữ số + hệ của nó; toString "535 (DEC)"
├── dto/        ConvertRequestDTO       hệ vào + hệ ra + giá trị   (main ──► controller)
│               ConvertResponseDTO      "535 (DEC)" + "217 (HEX)"  (controller ──► view)
├── service/    BaseStrategy            «interface» toDecimal · fromDecimal
│               PositionalBaseStrategy  2 thuật toán tay của đề ← ở ĐÂY
│               ConvertService          vào → long → ra (Context)
├── controller/ ConvertController       cắm PositionalBaseStrategy; service ──► view
├── view/       ConvertView             in "A = B"
├── constants/  Base                    enum BIN/DEC/HEX: cơ số + tên ngắn + fromChoice (bảng tra)
│               Message, Constants      câu chữ · số menu, DIGITS, dấu
├── utils/      Validation              getChoice(chuỗi, min, max) · getValue(chuỗi)
└── main/       Main                    vòng menu + Scanner
```

| Câu hỏi thiết kế | Trả lời |
|---|---|
| Sao `Base` là enum mà không 3 hằng `int`? | Với `int`, `convert(v, 2, 16)` và gõ nhầm `convert(v, 3, 16)` đều biên dịch được. Enum chỉ có đúng 3 giá trị; mỗi hằng mang **cơ số** và **tên in** (`HEX`) — bảng tra theo hệ. |
| Sao model là `BaseNumber` (chuỗi + hệ)? | `"11"` là 3 ở BIN, 11 ở DEC — chuỗi một mình **không phải** con số; cặp (chuỗi, hệ) mới là. |
| Kiểm chữ số hợp lệ sao không ở `Validation`? | Chỉ khi biết hệ mới biết `2` hợp lệ hay không — việc đó thuộc thuật toán. Sai thì báo rồi **về menu** (giống bản tham chiếu). `Validation` chỉ kiểm menu và "không để trống". |
| Không repository? | Không lưu gì giữa các lần đổi. |

**Luồng chạy:**

```
Main (vòng): in menu ── inputBaseIn ── 0? → "Goodbye." thoát
             └─ inputBaseOut ── inputValue ──► ConvertRequestDTO ──► controller.convert(dto)
   controller ──► service.convert(dto)
                     ├─ input  = new BaseNumber(value, inputBase)
                     ├─ long v = baseStrategy.toDecimal(input)        ← ví dụ 2, 4
                     ├─ output = baseStrategy.fromDecimal(v, outputBase) ← ví dụ 1, 3
                     └─ response: input.toString(), output.toString()
   controller ──► view.display()  →  "535 (DEC) = 217 (HEX)"
Main: catch → in "1G is not a valid HEX number." rồi quay lại menu
```

### 3.1 Design Pattern — **Strategy** (+ bảng tra enum)

| Yếu tố | Trong bài này |
|---|---|
| **Name** | Strategy (Behavioral) |
| **Problem** | Đổi hệ có hơn một cách: **tay** (đề bắt — chia lấy dư, tổng lũy thừa), **thư viện** (`Long.parseLong(s, r)` / `Long.toString(n, r)`), hoặc **gom nhóm bit** (BIN↔HEX mỗi 4 bit = 1 chữ số hex). Viết thẳng vào service thì đổi cách phải sửa service. |
| **Solution** | `BaseStrategy` = **Strategy** (`toDecimal`, `fromDecimal`). `PositionalBaseStrategy` = **ConcreteStrategy** (2 thuật toán tay). `ConvertService` = **Context**, nhận strategy qua constructor. `ConvertController` **chọn**: `new ConvertService(new PositionalBaseStrategy())`. Enum `Base` là **bảng tra theo hệ** (cơ số, tên, số menu) — thuật toán không có `if (base == 16)` nào. |
| **Consequences** | ✅ Thêm cách đổi = **thêm 1 class** + sửa 1 dòng controller (OCP, DIP). ✅ Thêm **hệ 8** = thêm 1 hằng `OCTAL(8, "OCT")` + 1 dòng menu + `BASE_MAX = 4` — thuật toán không đổi một chữ. ❌ Thêm 1 interface so với viết thẳng. |

**Thầy bảo "dùng thư viện xem"**: tạo `LibraryBaseStrategy implements BaseStrategy` với
`Long.parseLong(value, base.getRadix())` và `Long.toString(v, radix).toUpperCase()`, sửa 1 dòng controller.

Pattern khác: **MVC**, **Facade** = `ConvertController`.

### 3.2 SOLID

| Nguyên lý | Ở đâu |
|---|---|
| **S** | `BaseNumber` mô tả số viết ở 1 hệ · `PositionalBaseStrategy` đổi · `ConvertService` điều phối · `Validation` kiểm menu · `ConvertView` in |
| **O** | thêm hệ = thêm hằng enum; thêm thuật toán = thêm lớp strategy |
| **L** | mọi `XxxBaseStrategy` thay được cho nhau |
| **I** | `BaseStrategy` chỉ 2 hàm, lớp nào cũng cần cả 2 |
| **D** | `ConvertService` phụ thuộc `BaseStrategy` (trừu tượng) |

---

## 4. Code từng bước

> Thầy: *"code cái **model trước rồi đến data**"* (V8). `Base` là kiểu field của model nên gõ trước.

| Bước | File | Việc |
|---|---|---|
| 1 | `constants/Base.java` | enum 3 hằng (cơ số, tên) + `fromChoice` |
| 2 | `model/BaseNumber.java` | `value` + `base` `private`, constructor rỗng + đủ, get/set, `toString` |
| 3 | `dto/ConvertRequestDTO.java`, `ConvertResponseDTO.java` | JavaBean |
| 4 | `service/BaseStrategy.java` | interface 2 hàm |
| 5 | `service/PositionalBaseStrategy.java` | **toDecimal** (tổng lũy thừa, kiểm chữ số, kiểm tràn) · **fromDecimal** (chia lấy dư) · 3 hàm `private` |
| 6 | `service/ConvertService.java` | `convert(dto)` |
| 7 | `view/ConvertView.java` | `setResponse` · `display` |
| 8 | `controller/ConvertController.java` | cắm strategy; `convert(dto)` |
| 9 | `constants/Message.java`, `Constants.java` | menu, câu lỗi; `DIGITS`, số menu, dấu |
| 10 | `utils/Validation.java` | `getChoice` (tách 2 lỗi) · `getValue` |
| 11 | `main/Main.java` | vòng menu + 3 hàm nhập |

**Bẫy hay gặp:**

1. Quên **đọc dư ngược** → 535 ra `712` thay vì `217`.
2. `fromDecimal(0)`: vòng `while (left > 0)` chạy 0 lần → chuỗi rỗng. Phải trả `"0"` riêng.
3. Chỉ kiểm `indexOf < 0` → `2` ở hệ BIN lọt qua, in số sai. Phải kiểm cả `>= radix`.
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
| Breakpoint 1 | `result = result + digit * power;` trong `toDecimal` |
| Chạy | **Ctrl+F5**, chọn 3 → 2, gõ `217` |
| Quan sát | tab **Variables**: `index`, `digit`, `power`, `result` — so với bảng mục 2.4 (7, 23, 535) |
| Breakpoint 2 | `digits.append(...)` trong `fromDecimal`, chọn 2 → 3, gõ `535` — `left` 535 → 33 → 2 → 0, `digits` `"7"`,`"71"`,`"712"`, sau `reverse` `"217"` |
| Đa hình | ở `ConvertService.convert` bấm **F7** vào `baseStrategy.toDecimal(input)` → nhảy vào `PositionalBaseStrategy` |
| Lỗi chữ số | gõ `1G` (HEX): F8 trong `digitValue` thấy `digit = -1` → `throw` → rơi vào `catch` của `Main` |

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
| 4 tính chất OOP ở đâu? | **Đóng gói**: `value`, `base` `private` trong `BaseNumber`; `radix`, `label` `private final` trong enum. **Kế thừa**: `PositionalBaseStrategy implements BaseStrategy`; `BaseNumber` ghi đè `toString()` của `Object`. **Đa hình**: service gọi `baseStrategy.toDecimal(...)` qua biến interface. **Trừu tượng**: interface `BaseStrategy` — service không biết đổi bằng tay hay thư viện. |
| `toDecimal` trả `long` mà không `int`? | `int` chỉ tới ~2,1 tỉ; `long` tới ~9,2 × 10¹⁸ — đổi được nhiều số hơn. |
| `fromDecimal` trả `BaseNumber` mà không `String`? | Kết quả là **số viết ở một hệ** — cần cả chuỗi lẫn hệ để in `217 (HEX)`. |
| `digitValue`, `stripLeadingZeros`, `invalid` sao `private`? | Chỉ `PositionalBaseStrategy` dùng — không phải hợp đồng. |
| `Base.fromChoice` sao `static`? | Nó hỏi **cả kiểu enum** "hằng nào ứng với số 2?", không thuộc riêng hằng nào. `static` được phép trong `constants` (V3). Bỏ `static` thì phải có sẵn một hằng mới gọi được — vô lý. |
| `Validation.getChoice` có 3 tham số — trái V4? | Utils được 3 tham số, **đúng mẫu Guide** `getChoice(input, min, max)` (QUY-TAC-THAY V4). Hàm nghiệp vụ không hàm nào quá 2 — 3 dữ liệu (hệ vào, hệ ra, giá trị) gói trong `ConvertRequestDTO`. |
| Bỏ `static` ở `Validation` thì sao? | `Validation.getChoice(...)` lỗi biên dịch; phải bỏ constructor `private`, `new Validation()` trong Main. |
| Sao Main có 2 hàm `inputBaseIn`/`inputBaseOut` gần giống nhau? | Khác câu hỏi và khoảng (0–3 vs 1–3); gộp thành 1 hàm sẽ cần 4 tham số (`sc, prompt, min, max`) — thầy cấm. |
| Sao không `List`/`Map`? | Không cần danh sách. Tra hệ bằng enum `values()` (mảng có sẵn). Nếu cần, em khai báo `ArrayList`/`HashMap` cụ thể: `List` là interface, `ArrayList` là lớp cài đặt bằng mảng động. |

---

## 8. Thầy đổi yêu cầu tại chỗ

| Thầy bảo | Sửa | Không đụng |
|---|---|---|
| Thêm **hệ 8** | `Base` thêm `OCTAL(8, "OCT")`, `Message.MENU` thêm dòng `4. Octal (base 8)`, `Constants.BASE_MAX = 4` | **strategy, service, controller, view** |
| Dùng thư viện | thêm `LibraryBaseStrategy` + 1 dòng `ConvertController` | service, main, view |
| Giá trị sai thì **hỏi lại** thay vì về menu | Main: đưa `controller.convert(dto)` vào vòng `while` quanh `inputValue` | strategy |
| In thêm **các bước chia** | strategy ghi các dòng vào một `ArrayList<String>`, thêm field vào `ConvertResponseDTO`, view in | Main |
| Thoát bằng chữ `q` | `Validation`/Main nhận `q` trước khi parse | service |

---

## 9. Chỗ khác với đề / bản cũ

| Chỗ | Đề / bản cũ | Bài này | Lý do |
|---|---|---|---|
| Màn hình | đề không có | giữ đúng từng chữ bản tham chiếu | đề im lặng → bản tham chiếu là chuẩn |
| Thoát | đề: "until users close" | chọn `0` ở hệ vào → `Goodbye.` | giống bản tham chiếu; không `System.exit` |
| `toDecimal` | bản cũ: Horner (`result*r + d`) | **tổng chữ số × r^index** | đúng cách tính trong hình ví dụ 2, 4 của đề (dễ đối chiếu khi thầy hỏi) |
| Hình ví dụ 2 | ghi `0*16⁰` | tính `7*16⁰` | lỗi đánh máy của đề; tổng 535 của đề chỉ đúng với 7 |
| Kiểm chữ số | bản cũ ở `bo` | ở `PositionalBaseStrategy`; sai → về menu | chỉ thuật toán biết chữ số của hệ; giữ hành vi bản tham chiếu |
| Số âm, dấu `+` | đề không nói | nhận | giống bản tham chiếu |
| Kiến trúc | `entity/bo/ui`, Scanner static trong `Validator`, `convert(value, from, to)` 3 tham số | MVC Guide + Strategy; 3 dữ liệu trong DTO | luật thầy (V4, V7) |
