# J1.S.P0060 — Calculate the total amount spent through the bills

> Bài ngắn nhất bộ, nhưng có **2 lớp đề đặt tên sẵn** (`Person`, `Wallet`) và **2 hàm đề bắt**
> (`calcTotal`, `payMoney`). Điểm thầy soi: **Wallet nằm TRONG Person** (quan hệ *has-a*) và mỗi
> hàm nằm **đúng tầng**.

| | |
|---|---|
| Loại / LOC | Short Assignment · 21 LOC · 1 slot |
| Project | `HE176322_J1SP0060_CalculateBill` |
| Chạy | NetBeans: **File ▸ Open Project** → **F6** |
| Lớp chạy | `main.Main` |
| Kiểm tự động | `python3 _tools/verify.py J1SP0060` → 6 kịch bản × 2 locale |

---

## 1. Đề bài nói gì

- Nhập **số hoá đơn**, **giá trị từng hoá đơn**, rồi **số tiền trong ví**.
- Tính **tổng hoá đơn**, so với tiền trong ví → in *mua được* hay *không mua được*, rồi thoát.

Màn hình đề (chép đúng chữ):

```
======= Shopping program ==========
input number of bill:2
input value of bill 1:100
input value of bill 2:200
input value of wallet:500
this is total of bill:300
You can buy it.
```

(lần 2: `200`, `200`, ví `200` → `this is total of bill:400` · `You can’t buy it.`)

**Đề bắt buộc** (mục Guidelines / Program Specifications):

| Thứ | Đề viết | Bài này đặt ở |
|---|---|---|
| Lớp `Person` = người dùng | *"Designing class Person represented the user"* | `model/Person` |
| Lớp `Wallet` **bên trong** `Person` | *"class Wallet … within class Person"* | `model/Wallet`, là **field** của `Person` |
| `public int calcTotal(int[] bills)` | *"Must create the function"* | `service/BillService` — **giữ nguyên chữ ký** |
| `public boolean payMoney(int total)` | *"Class Wallet contains … a function to compare"* | `model/Wallet` — **giữ nguyên chữ ký** |
| Câu kết quả | `You can buy it.` · `You can’t buy it.` | `constants/Message` — dấu `’` **chép đúng đề** |

---

## 2. Kiến thức cần biết

### 2.1 `calcTotal` — cộng dồn (accumulator)

> Đặt `total = 0`, đi qua **từng** hoá đơn, cộng vào `total`. Hết mảng → `total` là tổng.

Chạy tay ví dụ 1 của đề, `bills = {100, 200}`:

| Bước | `bill` | `total` sau bước |
|---|---|---|
| bắt đầu | — | `0` |
| 1 | `100` | `100` |
| 2 | `200` | `300` ← trả về |

`payMoney(300)` với ví `500`: `500 >= 300` → `true` → **You can buy it.**
Ví dụ 2: tổng `400`, ví `200`: `200 >= 400` → `false` → **You can’t buy it.**

### 2.2 Vì sao `>=` mà không `>`

Ví có đúng `100`, hoá đơn đúng `100` → **trả được** (hết sạch tiền, nhưng vẫn trả). Dùng `>` là báo sai
đúng ca biên này. Kịch bản test 4 kiểm đúng ca đó.

### 2.3 Composition — "Wallet within Person"

| Quan hệ | Viết | Đọc là | Bài này |
|---|---|---|---|
| **Composition / has-a** | `private Wallet wallet;` trong `Person` | người **có** một cái ví | ✅ đúng ý đề |
| Inheritance / is-a | `class Person extends Wallet` | người **là** một cái ví | ❌ vô nghĩa |

### 2.4 Tràn số `int` — vì sao có trần

`calcTotal` đề bắt trả **`int`** (tối đa ≈ 2,147 tỉ). Nếu cho 100 hoá đơn × 1 tỉ thì tổng tràn thành số âm.
Nên `Constants`: tối đa **100** hoá đơn × **10.000.000** mỗi hoá đơn = **1.000.000.000** — luôn vừa `int`.

### 2.5 Java dùng trong bài

| API | Dùng làm gì |
|---|---|
| `Integer.parseInt(s)` | đổi chuỗi sang số; `"abc"`, `"3.5"`, `""` → `NumberFormatException` |
| `String.format("input value of bill %d:", i + 1)` | ghép số thứ tự vào prompt (người đếm từ 1, mảng đếm từ 0) |
| `for (int bill : bills)` | vòng for-each: đi qua từng phần tử, không cần chỉ số |
| `"’"` | ký tự `’` của đề, viết dạng escape để file mã nguồn không phụ thuộc bảng mã |

---

## 3. Thiết kế

```
HE176322_J1SP0060_CalculateBill/src/
├── model/      Wallet            tiền trong ví + payMoney(total)   ← hàm đề bắt #2
│               Person            bills[] + Wallet (has-a)
├── dto/        BillRequestDTO    bills[] + walletAmount   (main ──► controller)
│               BillResponseDTO   total + canBuy           (controller ──► view)
├── service/    BillService       checkBill + calcTotal(int[])      ← hàm đề bắt #1
├── controller/ BillController    service ──► view
├── view/       BillView          in 2 dòng kết quả
├── constants/  Message.java      câu chữ màn hình
│               Constants.java    trần/sàn của 3 ô nhập
├── utils/      Validation        getInt(chuỗi, min, max) → int hoặc ném lỗi
└── main/       Main              Scanner + 4 hàm nhập + gọi controller 1 lần
```

| Lớp | Làm gì | Vì sao ở đây |
|---|---|---|
| `Wallet` | giữ `amount`, trả lời `payMoney(total)` | đề: Wallet **chứa** hàm so sánh — hành vi **của chính cái ví** (như `Shape.getArea`) |
| `Person` | giữ hoá đơn + ví | đề: lớp đại diện người dùng, ví nằm **trong** nó |
| `BillService` | tạo `Person`, gọi `calcTotal`, hỏi ví `payMoney` | Guide: *"tính tổng…"* là **tính toán nghiệp vụ** → service |
| `BillController` | nhận DTO → service → view | Guide: controller *"chỉ import DTO, View, Service"* — không thấy `Person` |
| `BillView` | in `this is total of bill:…` + câu mua được/không | nơi duy nhất (cùng `Main`) được in |
| `Validation` | chuỗi → `int` trong khoảng, hoặc ném lỗi | Guide: utils *"phải dùng static method"* |

| Câu hỏi thiết kế | Trả lời |
|---|---|
| Sao không có `repository`? | Không lưu gì giữa các lần chạy, không thêm/sửa/xoá — không có CRUD. |
| Sao `calcTotal` không nằm trong `Person` như bản cũ? | Cộng tổng là **nghiệp vụ** (Guide: *"Tính tổng"* → service). Còn `payMoney` thì **đề chỉ định** nằm trong `Wallet`. |

**Luồng chạy:**

```
Main: đọc số hoá đơn, từng hoá đơn, ví (hỏi lại khi sai) ──► BillRequestDTO ──► controller.calculateBill(dto)
   controller ──► service.checkBill(dto)
                     ├─ person = new Person(bills, new Wallet(amount))
                     ├─ total  = calcTotal(person.getBills())
                     ├─ canBuy = person.getWallet().payMoney(total)
                     └─ return new BillResponseDTO(total, canBuy)
   controller ──► view.setResponse(response) ──► view.display()
```

### 3.1 Design Pattern trong bài

Bài có **một** phép tính cố định do đề chỉ định (cộng tổng, so ví) — không có "nhiều cách làm để thay nhau",
nên **không** nhét Strategy/Factory cho có (slide 26 SOLID: *"abstraction 'chỉ vì SOLID nói vậy' … vi
phạm YAGNI"*). Pattern có thật trong bài:

| Yếu tố | **MVC** (kiến trúc — thầy gọi "MVC JSP") | **Facade** |
|---|---|---|
| **Name** | Model–View–Controller | Facade (nhóm Structural) |
| **Problem** | nhập, tính, in trộn trong `main` → sửa câu in là đụng phép tính | `Main` sẽ phải biết `BillService`, `BillView` và thứ tự gọi chúng |
| **Solution** | `Person`/`Wallet` ~ JavaBean (Model) · `BillView` ~ trang JSP (View) · `BillController` ~ Servlet (Controller); dữ liệu đi qua DTO | `BillController.calculateBill(dto)` là **một cửa**: tự gọi service rồi view |
| **Consequences** | ✅ đổi cách in chỉ sửa `BillView`; ❌ nhiều file hơn viết gộp | ✅ `Main` chỉ biết 1 lớp; ❌ controller phải giữ đúng vai điều hướng, không ôm phép tính |

### 3.2 SOLID trong bài

| Nguyên lý | Ở đâu |
|---|---|
| **S** | `Wallet` chỉ lo tiền trong ví · `BillService` chỉ lo tính · `BillView` chỉ lo in · `Validation` chỉ lo kiểm |
| **O** | thêm câu kết quả mới (vd. in số tiền còn thiếu) chỉ thêm field ở `BillResponseDTO` + dòng in ở `BillView` — `Wallet`, `Main` đứng yên |
| **L / I / D** | bài không có họ lớp con hay interface — **không cố gượng**. Nói thật với thầy: *"bài nhỏ, em chỉ áp S và O; L/I/D em áp ở bài có kế thừa (Shape, Bee…)"* |

---

## 4. Code từng bước

> Thầy: *"code cái **model trước rồi đến data**"*.

| Bước | File | Việc |
|---|---|---|
| 1 | `model/Wallet.java` | `private int amount` + constructor rỗng + constructor đủ + get/set + **`payMoney`** + `toString` |
| 2 | `model/Person.java` | `private int[] bills`, `private Wallet wallet` + 2 constructor + get/set + `toString` |
| 3 | `dto/BillRequestDTO.java`, `BillResponseDTO.java` | JavaBean: constructor rỗng + get/set |
| 4 | `service/BillService.java` | **`calcTotal`** + `checkBill` |
| 5 | `view/BillView.java` | `setResponse` · `display` (if/else 2 câu) |
| 6 | `controller/BillController.java` | constructor tạo service + view; `calculateBill(dto)` |
| 7 | `constants/Message.java`, `Constants.java` | câu chữ + 6 giới hạn (gõ dần khi bước trên cần) |
| 8 | `utils/Validation.java` | `getInt(input, min, max)` — **tách 2 lỗi** |
| 9 | `main/Main.java` | `inputNumberOfBill` · `inputBills` · `inputBill` · `inputWallet` + gọi controller **1 lần** |

**Bẫy hay gặp:**

1. Viết `amount > total` → ví vừa đủ tiền lại báo *không mua được*. Phải `>=`.
2. Prompt `input value of bill 0:` — quên `i + 1`.
3. Hoá đơn sai thì phải hỏi lại **đúng hoá đơn đó** (`input value of bill 1:` lần nữa), không bắt nhập lại từ đầu —
   vì thế `inputBill` có vòng lặp riêng.
4. Gõ `'` thẳng thay vì `’` → khác chữ đề. `Message.CANNOT_BUY` dùng `"’"`.

---

## 5. Test trước khi gọi thầy

| # | Gõ | Phải thấy |
|---|---|---|
| 1 | `2` · `100` · `200` · ví `500` | `this is total of bill:300` · `You can buy it.` |
| 2 | `2` · `200` · `200` · ví `200` | `this is total of bill:400` · `You can’t buy it.` |
| 3 | số hoá đơn `abc` · *(trống)* · `3.5` | `You must input a number.` rồi hỏi lại |
| 4 | số hoá đơn `0` · `101` | `Value must be between 1 and 100.` |
| 5 | hoá đơn `0` · `10000001` | `Value must be between 1 and 10000000.` rồi hỏi lại **đúng hoá đơn đó** |
| 6 | hoá đơn `x` | `You must input a number.` |
| 7 | ví `-1` · `1000000001` | `Value must be between 0 and 1000000000.` |
| 8 | ví `y` | `You must input a number.` |
| 9 | 1 hoá đơn `100`, ví `100` | `You can buy it.` (ca biên `>=`) |
| 10 | 3 hoá đơn `1 2 3`, ví `0` | tổng `6` · `You can’t buy it.` |
| 11 | 100 hoá đơn × `10000000`, ví `1000000000` | tổng `1000000000` — **không tràn** · `You can buy it.` |

---

## 6. Debug

| Việc | Cách làm |
|---|---|
| Breakpoint | dòng `total += bill;` trong `BillService.calcTotal` và dòng `return amount >= total;` trong `Wallet.payMoney` |
| Chạy | **Ctrl+F5**, nhập ví dụ 1 của đề |
| Quan sát | tab **Variables**: `bill`, `total` tăng `0 → 100 → 300` |
| Bước | ở `checkBill` bấm **F7** vào `calcTotal(...)`, rồi **F7** vào `payMoney(total)` để thấy nó nhảy sang **model** `Wallet`; **F8** đi từng dòng; **Ctrl+F7** ra ngoài |
| Chứng minh has-a | mở biến `person` → thấy field `wallet` → trong đó `amount = 500` |

---

## 7. Câu hỏi thầy hay hỏi

### OOP

| Câu hỏi | Trả lời mẫu |
|---|---|
| 4 tính chất OOP ở đâu? | **Đóng gói**: `amount` `private` trong `Wallet`, chỉ đọc qua `getAmount`/`payMoney`. **Kế thừa**: mọi lớp `extends Object`; `Wallet`, `Person` ghi đè `toString()`. **Đa hình**: `@Override toString()` — `Arrays.toString(bills) + " " + wallet` tự gọi bản `toString` của `Wallet`. **Trừu tượng**: `Main` gọi `controller.calculateBill(dto)` mà không biết có `Person`, `Wallet`. |
| `Person` và `Wallet` quan hệ gì? | **Composition (has-a)**: `Person` có field `Wallet`. Không phải kế thừa — người không "là" cái ví. |
| Sao `payMoney` nằm trong `Wallet` mà không ở service? | Đề ghi Wallet *"contains … a function to compare it with the total"* — đó là **hành vi của chính cái ví**, dùng dữ liệu riêng của nó (`amount`). |
| Sao `Person`, `Wallet`, DTO có constructor rỗng? | Thầy dạy **MVC JSP**: model/DTO là **JavaBean** — field `private`, constructor rỗng `public`, get/set. |

### Access modifier, static, kiểu trả về, tham số

| Câu hỏi | Trả lời mẫu |
|---|---|
| Mọi field sao `private`? | Đóng gói: chỉ đổi qua setter/hàm của lớp. Thầy: *"Access modifier dùng sai linh tinh là reject"*. |
| `calcTotal`, `checkBill` sao `public`? | `checkBill` do `BillController` (package khác) gọi; `calcTotal` là **hàm đề bắt** `public int calcTotal(int[] bills)`. |
| `payMoney` sao `public`? | Đề ghi `public boolean payMoney`; `BillService` (package khác) gọi. |
| Getter/setter sao `public`? | Là "cửa" JavaBean để lớp khác đọc/ghi field `private`. |
| Hàm nhập trong `Main` sao `private static`? | `private`: chỉ `main()` gọi. `static`: `main()` là static nên chỉ gọi thẳng được hàm static; thầy cho *"static với hàm"* ở main, cấm static **biến**. |
| Sao `Validation.getInt` static? Bỏ đi thì sao? | Không dùng dữ liệu của đối tượng nào; Guide bắt utils *"phải dùng static method"*. Bỏ `static` → `Validation.getInt(...)` lỗi biên dịch; phải bỏ `private` constructor, `new Validation()` trong `Main` rồi gọi qua đối tượng. |
| Hằng trong `Message`/`Constants` sao `public static final`? | `public`: mọi lớp dùng; `static`: một bản chung, gọi bằng tên lớp; `final`: không ai sửa được. |
| `calcTotal` trả `int` vì sao? | Đề bắt; hoá đơn là số nguyên; `Constants` giữ tổng không vượt `int`. |
| `payMoney` trả `boolean` vì sao? | Câu hỏi chỉ có **có/không** — view chọn câu in theo nó. |
| `calculateBill` trả `void` vì sao? | Kết quả đã được đưa sang view in ra — không còn gì để trả. |
| `getInt(input, min, max)` 3 tham số — trái luật *"không truyền 3 tham số 1 hàm"*? | Luật áp cho hàm nghiệp vụ; hàm **utils** kiểu `getChoice(input, min, max)` là **đúng mẫu Guide của thầy** (QUY-TAC-THAY §9 V4). Hàm nghiệp vụ nào trong bài cũng ≤ 2 tham số, dữ liệu đi bằng DTO. |
| Sao có 4 hàm nhập trong `Main` gần giống nhau, không gộp 1 hàm `inputInt(sc, prompt, min, max)`? | Gộp thì **4 tham số** — trái luật thầy. Mỗi hàm 1–2 tham số, đọc là hiểu ngay nó hỏi ô nào. |
| Bài có dùng `List`/`ArrayList` không? | Không — số hoá đơn biết **trước** khi nhập, và đề bắt `int[]`, nên dùng **mảng**. Nếu thầy hỏi: `List` là **interface** (hợp đồng), `ArrayList` là **lớp cài đặt** bằng mảng động; em khai báo kiểu cụ thể `ArrayList` khi cần danh sách co giãn. |

### Thuật toán & ca biên

| Câu hỏi | Trả lời mẫu |
|---|---|
| Độ phức tạp `calcTotal`? | `O(n)` — mỗi hoá đơn cộng đúng 1 lần. |
| Ví vừa đúng bằng tổng? | Mua được (`>=`). |
| Sao hoá đơn ≥ 1 mà ví ≥ 0? | Hoá đơn 0 đồng không phải hoá đơn; ví rỗng thì hợp lệ (chỉ là không mua được). |
| Nhập 99999999 hoá đơn? | `Value must be between 1 and 100.` — trần chặn tràn bộ nhớ và tràn `int`. |

---

## 8. Thầy đổi yêu cầu tại chỗ

| Thầy bảo | Sửa file | Không phải đụng |
|---|---|---|
| In thêm **số tiền còn lại / còn thiếu** | `BillService.checkBill` (tính `amount - total`), thêm field vào `BillResponseDTO`, `Message` + `BillView.display` | `Wallet`, `Person`, `Main`, `BillController` |
| Mua xong thì **trừ tiền trong ví** | `Wallet.payMoney`: nếu đủ thì `amount -= total` | mọi file khác |
| Hoá đơn là số **thực** (`100.5`) | `int` → `double` ở `Person`, 2 DTO, `calcTotal`, `payMoney`; `Validation` thêm `getDouble`; in bằng `String.format(Locale.US, ...)` | `BillController` |
| Cho **giảm giá 10%** khi tổng > 1000 | thêm bước trong `BillService.checkBill` + hằng trong `Constants` | `Wallet`, `Main`, `View` |
| Đổi câu *"You can buy it."* | chỉ `Message.CAN_BUY` | mọi file khác |

---

## 9. Chỗ khác với đề / bản cũ

| Chỗ | Đề / bản cũ | Bài này | Lý do |
|---|---|---|---|
| Câu không đủ tiền | bản cũ: `You can't buy it.` (dấu `'` thẳng) | `You can’t buy it.` (dấu `’` như đề) | **màn hình đề thắng** — nên file test đặt `REPLACE_REFERENCE = True` và viết lại các kịch bản của bản cũ với dấu `’` |
| Chỗ đặt `calcTotal` | bản cũ: trong `Person` | `BillService` | Guide: *"Tính tổng"* là việc của service; chữ ký giữ nguyên |
| Giới hạn giá trị hoá đơn | bản cũ: `0 … 1.000.000.000` | `1 … 10.000.000` | 100 × 1 tỉ tràn `int` (đề bắt `calcTotal` trả `int`); hoá đơn 0 đồng vô nghĩa |
| Thông báo lỗi nhập | đề **không** cho | `You must input a number.` · `Value must be between %d and %d.` | lấy đúng chữ bản cũ |
| Kiến trúc | `entity/ui/utils`, Scanner trong `Validator` | MVC theo Guide, Scanner **chỉ ở `main`** | luật thầy |
