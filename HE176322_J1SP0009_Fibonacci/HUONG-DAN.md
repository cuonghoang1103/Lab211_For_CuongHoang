# J1.S.P0009 — Fibonacci

> Thầy nêu **đúng tên bài này**: *"Các bài liên quan thuật toán như **fibo**, sắp xếp,… cũng phải làm
> MVC, không OOP/MVC → không review."* Chương trình chỉ in 2 dòng, nhưng vẫn đủ tầng: model · dto ·
> service · controller · view · constants · main.

| | |
|---|---|
| Loại / LOC | Short Assignment · 50 LOC · 1 slot |
| Project | `HE176322_J1SP0009_Fibonacci` |
| Chạy | NetBeans: **File ▸ Open Project** → **F6** |
| Lớp chạy | `main.Main` |
| Kiểm tự động | `python3 _tools/verify.py J1SP0009` → 1 kịch bản × 2 locale |

---

## 1. Đề bài nói gì

- In **45 số Fibonacci đầu tiên**: `0, 1, 1, 2, 3, 5, 8, …`
- **Bắt buộc dùng đệ quy** (*"Use recursion method to find 45 sequence Fibonacci"*).
- Không nhập gì từ bàn phím.

Màn hình đề (ảnh, bị cắt ở mép phải):

```
The 45 sequence fibonacci:
0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, 610, 987, 1597, …
```

Chương trình in đủ đến số thứ 45: `… 433494437, 701408733`.

**Đề bắt buộc:**

| Thứ | Đề viết | Bài này đặt ở |
|---|---|---|
| Đệ quy | *"Use recursion method"* | `FibonacciService.fibonacci` (private) — gọi lại chính nó |
| Định nghĩa | `F(0) = 0`, `F(1) = 1`, `F(n) = F(n-1) + F(n-2)` với n > 1 | `if (n <= 1) return n;` + `fibonacci(n - 1) + fibonacci(n - 2)` |
| 45 số | *"displays 45 sequence Fibonacci"* | `Constants.SEQUENCE_LENGTH = 45` |
| Tiêu đề | `The 45 sequence fibonacci:` | `Message.TITLE` |

---

## 2. Kiến thức cần biết

### 2.1 Đệ quy — hai phần bắt buộc

| Phần | Trong code | Thiếu thì sao |
|---|---|---|
| **Điểm dừng** (base case) | `if (n <= 1) return n;` → F(0)=0, F(1)=1 | gọi mãi tới `n` âm → `StackOverflowError` |
| **Bước đệ quy** | `fibonacci(n - 1) + fibonacci(n - 2)` | không phải đệ quy |

### 2.2 Đệ quy thường **quá chậm** — tại sao phải thêm memo

Cây gọi của `F(5)` bằng đệ quy thường:

```
F(5)
├── F(4)
│   ├── F(3)
│   │   ├── F(2) ── F(1), F(0)
│   │   └── F(1)
│   └── F(2) ── F(1), F(0)          ← F(2) tính LẠI
└── F(3)                             ← cả nhánh F(3) tính LẠI
    ├── F(2) ── F(1), F(0)
    └── F(1)
```

`F(5)` tốn **15** lời gọi. Số lời gọi để tính F(n) là `2·F(n+1) − 1` — tăng theo cấp số nhân (~1,6ⁿ):

| Tính | Đệ quy thường | Đệ quy + memo (bài này) |
|---|---|---|
| F(5) | 15 lời gọi | 9 |
| F(20) | 21 891 | 39 |
| **F(44)** (số thứ 45) | **2 269 806 339** | **87** |
| **Cả 45 số** F(0)…F(44), gọi lần lượt | **5 942 430 099** | **131** |

(Các con số đo bằng cách đếm lời gọi thật, xem mục 6.) Gần 6 tỉ lời gọi → chương trình **đứng hàng chục giây
đến vài phút**. Thầy chạy thử là thấy "treo".

### 2.3 Memoization — giữ đệ quy, chỉ **nhớ** kết quả

> Tính xong `F(n)` thì **cất vào** `HashMap` `memo`. Lần sau cần `F(n)` → **lấy ra**, không đệ quy lại.
> Vẫn là đúng hàm đệ quy của đề; mỗi `F(n)` chỉ tính **một lần** → `O(n)` lời gọi.

Vì sao cả 45 số chỉ 131 lời gọi: service gọi `fibonacci(0)`, `fibonacci(1)`, … lần lượt. `F(0)`, `F(1)` mỗi cái
1 lời gọi (điểm dừng). Từ `F(2)` trở đi, `F(n-1)` và `F(n-2)` **đã có trong memo** → mỗi `F(n)` chỉ 3 lời gọi
(chính nó + 2 lần tra memo). Tổng `2 + 43 × 3 = 131`.

### 2.4 Kiểu dữ liệu — `long`, không `int`

| Số | Giá trị | `int` (max 2 147 483 647) |
|---|---|---|
| F(44) — số thứ 45 | 701 408 733 | vừa |
| F(46) | 1 836 311 903 | vừa |
| **F(47)** | 2 971 215 073 | **tràn → ra số âm** |

Đề chỉ cần đến F(44) nên `int` vẫn đúng — nhưng thầy bảo *"in 50 số"* là vỡ. `long` chịu được tới F(92).

### 2.5 Java dùng trong bài

| API | Dùng làm gì |
|---|---|
| `HashMap<Integer, Long>` · `containsKey` · `get` · `put` | bộ nhớ memo: n → F(n) |
| `ArrayList<Long>` | dãy số trong model |
| `StringBuilder.append` | nối `0, 1, 1, …` không tạo chuỗi mới mỗi lần |
| `String.format("%d", …)` | chèn 45 vào tiêu đề |

---

## 3. Thiết kế

```
HE176322_J1SP0009_Fibonacci/src/
├── model/      FibonacciSequence            dãy số (JavaBean, ArrayList<Long>) + addNumber/getSize/toString
├── dto/        FibonacciResponseDTO         count + chuỗi dãy số        (controller ──► view)
├── service/    FibonacciService             fibonacci(n) đệ quy + memo; dựng dãy n = 0..44
├── controller/ FibonacciController          service ──► view
├── view/       FibonacciView                in tiêu đề + dãy số
├── constants/  Message.java                 "The %d sequence fibonacci:"
│               Constants.java               SEQUENCE_LENGTH = 45, SEPARATOR = ", "
└── main/       Main                         tạo controller, gọi displaySequence() 1 lần
```

| Câu hỏi thiết kế | Trả lời |
|---|---|
| Sao **không có** `RequestDTO`, `utils/Validation`, Scanner? | Chương trình **không nhập gì**. Guide: RequestDTO chứa *"những gì cần truyền từ main vào controller"* — ở đây không có gì; Validation kiểm dữ liệu gõ vào — không có gì để kiểm. Tạo lớp rỗng là thừa (slide SOLID: *YAGNI*). |
| Sao có `service`, không `repository`? | Tính Fibonacci là **tính toán nghiệp vụ**; không lưu/CRUD gì. |
| Memo nằm đâu, sao không trong model? | `memo` là **chi tiết của thuật toán** (cách tính nhanh) → nằm trong `FibonacciService`. Model `FibonacciSequence` chỉ là **kết quả**: dãy số. |
| Sao controller import `Constants`? | Controller quyết định "chạy 45 số" và truyền cho service; service nhận `count` nên dùng lại được cho số khác. |

**Luồng chạy:**

```
Main ──► controller.displaySequence()
   controller ──► service.generateSequence(45)
                     └─ for n = 0..44: sequence.addNumber(fibonacci(n))
                                            ↑ đệ quy + memo
   controller ──► view.setResponse(response) ──► view.display()
```

### 3.1 Design Pattern trong bài

| Pattern | Ở đâu |
|---|---|
| **MVC** — thầy gọi là "MVC JSP" | controller điều hướng (như Servlet) · view hiển thị (như trang JSP) · model là JavaBean |
| **Facade** | controller: `Main` chỉ gọi `controller.displaySequence()`, không biết service/model/view phía sau |

> Bài chỉ 50 LOC nên **không thêm lớp pattern GoF** — ghi chú slide SOLID của thầy cảnh báo *"trừu tượng hoá sớm … vi phạm YAGNI"*. Đệ quy + memo nằm gọn trong **một hàm** `fibonacci` của `FibonacciService`.

**Thầy hỏi "dùng pattern gì cho dễ mở rộng?"** → trả lời đủ 4 yếu tố GoF (slide *"Elements of a Design Pattern"*):

| Yếu tố | Trả lời |
|---|---|
| **Name** | Strategy (nhóm Behavioral) |
| **Problem** | có nhiều cách tính F(n) (đệ quy thuần, đệ quy + memo, vòng lặp) cho cùng kết quả |
| **Solution** | tách `interface FibonacciStrategy { long fibonacci(int n); }`; mỗi cách làm là 1 lớp `implements` nó (`RecursiveFibonacci`, `MemoizedFibonacci`…); `FibonacciService` nhận strategy qua constructor |
| **Consequences** | ➕ thêm cách làm = thêm 1 lớp, service đứng yên (**O**) · ➖ thêm 2 file — chỉ đáng khi có từ 2 cách trở lên |

### 3.2 SOLID trong bài

| Nguyên lý | Ở đâu |
|---|---|
| **S** | `FibonacciSequence` giữ dãy · `FibonacciService` tính · `FibonacciView` in |
| **O** | đổi cách tính chỉ sửa **một hàm** `fibonacci` |
| **L** | bài chưa có lớp con riêng — chỉ `extends Object` |
| **I** | không có interface — bài chưa cần |
| **D** | `Main` chỉ phụ thuộc controller |

---

## 4. Code từng bước

> Thầy: *"code cái **model trước rồi đến data**"*.

| Bước | File | Việc |
|---|---|---|
| 1 | `model/FibonacciSequence.java` | `private ArrayList<Long> numbers` + constructor rỗng + get/set + `addNumber` · `getSize` · `toString` |
| 2 | `dto/FibonacciResponseDTO.java` | JavaBean: `count`, `sequence` + get/set |
| 3 | `service/FibonacciService.java` | field `memo`; `fibonacci(n)` đệ quy + memo (`private`); `generateSequence(count)` |
| 4 | `view/FibonacciView.java` | `setResponse` · `display` |
| 5 | `controller/FibonacciController.java` | `new FibonacciService()`; `displaySequence()` |
| 6 | `constants/Message.java`, `Constants.java` | tiêu đề; 45 và `", "` |
| 7 | `main/Main.java` | `new FibonacciController().displaySequence()` |

**Bẫy hay gặp:**

1. **Đệ quy thường** → đúng nhưng "treo" (mục 2.2). Thầy chạy thấy đứng là hỏi ngay.
2. `memo.put` **trước** khi tính, hoặc quên `put` → memo vô dụng.
3. Điểm dừng `if (n == 1)` thiếu `n == 0` → `fibonacci(0)` gọi `fibonacci(-1)` mãi → `StackOverflowError`.
4. In dấu phẩy **sau** mọi số → dòng kết thúc bằng `, ` thừa. `toString` chỉ chèn `", "` **trước** số thứ 2 trở đi.
5. Dùng `int` rồi thầy đổi 45 → 50 → số âm.

---

## 5. Test trước khi gọi thầy

| # | Làm | Phải thấy |
|---|---|---|
| 1 | **F6** | dòng 1: `The 45 sequence fibonacci:` |
| 2 | | dòng 2 bắt đầu `0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, 610, 987, 1597, …` (khớp ảnh đề) |
| 3 | | dòng 2 kết thúc `…, 433494437, 701408733` — **đúng 45 số**, không có dấu phẩy thừa |
| 4 | | in ra **tức thì** (nhờ memo) |

---

## 6. Debug

| Việc | Cách làm |
|---|---|
| Breakpoint | dòng `if (memo.containsKey(n))` trong `FibonacciService.fibonacci` |
| Chạy | **Ctrl+F5** |
| Quan sát | **Variables**: `n`, mở `memo` xem số phần tử tăng dần |
| Xem đệ quy | tab **Call Stack**: khi `n = 2` lần đầu, thấy 2 khung `fibonacci` chồng nhau; **F7** vào `fibonacci(n - 1)` để thấy hàm **gọi chính nó** |
| Chứng minh memo | đến `n = 3`: `memo` đã có `2`, F8 thấy `containsKey(2)` → `true` → `return memo.get(2)` không đệ quy nữa |
| Đếm lời gọi (nếu thầy hỏi con số) | tạm thêm field `private long calls;` và `calls++;` đầu hàm, xem trong Variables — ra **131** cho cả 45 số (đệ quy thường: 5 942 430 099) |

---

## 7. Câu hỏi thầy hay hỏi

### Thuật toán

| Câu hỏi | Trả lời mẫu |
|---|---|
| Đệ quy là gì, cần gì? | Hàm **gọi chính nó** với bài toán nhỏ hơn; bắt buộc có **điểm dừng** (`n <= 1`). |
| Sao đệ quy thường chậm? | Tính lại cùng một giá trị rất nhiều lần (cây 2.2): F(44) ≈ 2,27 tỉ lời gọi → `O(1,6ⁿ)`. |
| Memo khác vòng lặp thế nào? | Memo **vẫn là đệ quy** (đúng yêu cầu đề), chỉ nhớ kết quả → `O(n)`. Vòng lặp cũng `O(n)` nhưng không phải đệ quy — đề không cho. |
| Sao `HashMap` mà không `Map`? | `Map` là **interface** (hợp đồng put/get), `HashMap` là **lớp cài** bằng bảng băm — tra theo khoá `O(1)`. Em khai đúng kiểu cụ thể mình dùng. (Dùng `long[]` cũng được nhưng phải biết trước kích thước.) |
| Sao `ArrayList<Long>` mà không `List<Long>`? | `List` là interface; `ArrayList` là lớp cài bằng **mảng động**: thêm cuối và lấy theo chỉ số nhanh — đúng hai việc model cần. |
| Sao `Long` (hoa) trong `ArrayList`, còn hàm trả `long`? | Collection chỉ chứa **đối tượng** → `Long` (lớp bọc); Java tự đổi `long` ↔ `Long` (autoboxing). |
| Đệ quy sâu bao nhiêu? | Tối đa ~45 khung trên stack — an toàn. |

### OOP / Java

| Câu hỏi | Trả lời mẫu |
|---|---|
| 4 tính chất OOP ở đâu? | **Đóng gói**: `numbers` `private` trong `FibonacciSequence`, `memo` `private` trong `FibonacciService`. **Kế thừa**: mọi lớp ngầm `extends Object`; `FibonacciSequence` ghi đè `toString()`. **Đa hình**: `toString()` có `@Override`. **Trừu tượng**: `Main` chỉ gọi `controller.displaySequence()`, không biết có đệ quy hay memo. |
| `memo` sao `private`, không `static`? | `private`: không lớp nào được sửa bảng nhớ. Không `static`: memo là **của đối tượng service**; Guide cấm static ngoài utils/constants/hàm main. |
| `fibonacci` sao `public`? | Service (lớp khác) gọi — là hợp đồng của interface. |
| `displaySequence` trả `void` vì sao? | Kết quả đã đưa sang view in; không còn gì trả về. |
| `generateSequence` trả DTO vì sao? | Controller không được thấy model (Guide *"chỉ import DTO, View, Service"*) → service đóng gói kết quả vào DTO. |
| Hằng sao `public static final`? | `public`: mọi tầng đọc; `static`: của lớp, không cần đối tượng; `final`: không đổi. |
| Sao `Main` không có Scanner / hàm static phụ? | Không nhập gì; `main` chỉ gọi controller **1 lần** (Guide). |

---

## 8. Thầy đổi yêu cầu tại chỗ

| Thầy bảo | Sửa | Không đụng |
|---|---|---|
| In **50 / 60 số** | `Constants.SEQUENCE_LENGTH` (và `long` đã chịu tới F(92)) | mọi file khác |
| Bỏ memo, **đệ quy thuần** | thêm `PlainRecursiveFibonacci` + 1 dòng controller | service, view, main |
| Viết bằng **vòng lặp** | thêm `IterativeFibonacci` + 1 dòng controller | service, view, main |
| **Nhập n** từ bàn phím | thêm `FibonacciRequestDTO` (count), `utils/Validation.getCount`, `Main` đọc bằng Scanner; `displaySequence(dto)` truyền `dto.getCount()` | model |
| Mỗi số **một dòng** | `Constants.SEPARATOR = "\n"` | mọi file khác |

---

## 9. Chỗ khác với đề / lời giải cũ

| Chỗ | Đề / bản cũ | Bài này | Lý do |
|---|---|---|---|
| Tiêu đề | Bản cũ: `===== Fibonacci Program =====` + `The first 45 Fibonacci numbers:` | `The 45 sequence fibonacci:` | **đúng ảnh đề** |
| Cuối dòng | Ảnh đề bị cắt sau `1597,` | kết thúc `701408733`, không dấu phẩy thừa | ảnh không cho thấy cuối dòng; dấu phẩy thừa là lỗi trình bày |
| Đệ quy | Đề: *"use recursion"* | đệ quy **+ memo** | đệ quy thuần cần ~5,9 tỉ lời gọi; memo giữ nguyên đệ quy |
| Không có `RequestDTO`, `utils/` | Khung chung có | bỏ | không có dữ liệu nhập — lớp rỗng là thừa |
| Kiến trúc | `bo/ui`, hằng static trong `Main` | MVC theo Guide | luật thầy (`Main` cấm biến static) |
