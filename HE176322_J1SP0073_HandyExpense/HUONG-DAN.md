# J1.S.P0073 — Handy Expense

> Bài CRUD **có tệp dữ liệu**: danh sách chi tiêu được đọc từ `expenses.txt` lúc mở chương trình và
> ghi lại sau **mỗi** lần thêm/xoá. Khung giống P0055, thêm `service` (ID tự tăng, tổng tiền) và
> `utils/FileUtils`, `utils/DateUtils`.

| | |
|---|---|
| Loại / LOC | Short Assignment · 100 LOC · 2 slot |
| Project | `HE176322_J1SP0073_HandyExpense` |
| Chạy | NetBeans: **File ▸ Open Project** → **F6** (tệp `expenses.txt` được tạo trong thư mục project) |
| Lớp chạy | `main.Main` |
| Kiểm tự động | `python3 _tools/verify.py J1SP0073` → 2 kịch bản × 2 locale |

---

## 1. Đề bài nói gì

- Menu: **1. Add an expense · 2. Display all expenses · 3. Delete an expense · 4. Quit**.
- Chi tiêu: `ID` (int, **tự tăng**: ID lớn nhất + 1, cái đầu là 1), `Date` (`11-Apr-2009`), `Amount`, `Content`.
- **Display**: bảng `ID Date Amount Content` + dòng **`Total:`**.
- **Delete**: nhập ID; không có → **`Delete an expense fail`**; có → **`Delete an expense successful`**.
- *"Write a file processing program"* → dữ liệu nằm trong tệp.

**Đề bắt buộc:**

| Thứ | Đề viết | Bài này đặt ở |
|---|---|---|
| `boolean addExpense(List<Expense> list, Date date, double amount, String content)` | 4 tham số | `ExpenseService.addExpense(ExpenseRequestDTO)` → `ExpenseRepository.addExpense(Expense)` |
| `void displayAll(List<Expense> list)` | in bảng | `ExpenseController.displayAll()` → `ExpenseView.displayAll()` (in là việc của view) |
| `boolean deleteExpense(List<Expense> list, Expense exp)` | nhận `Expense` | `ExpenseService.deleteExpense(ExpenseRequestDTO)` → `ExpenseRepository.deleteExpense(Expense exp)` |
| Hai câu thông báo | `Delete an expense fail` · `Delete an expense successful` | `Message.DELETE_FAIL/SUCCESS` — **chép đúng** (không "failed", không dấu chấm) |

---

## 2. Kiến thức cần biết

### 2.1 Ngày `dd-MMM-yyyy` — ba cái bẫy

| Bẫy | Nếu quên | Bài làm |
|---|---|---|
| `MMM` là **tên tháng** theo ngôn ngữ máy | máy lab tiếng Việt in `11-thg 4-2009`, và **không đọc được** `Apr` | `new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH)` |
| `SimpleDateFormat` mặc định **dễ dãi** | `31-Feb-2009` thành 3/3/2009 | `setLenient(false)` |
| đọc được phần đầu là thôi | `11-Apr-2009abc`, `11-Apr-09` lọt qua | regex `\d{1,2}-[A-Za-z]{3}-\d{4}` + kiểm `ParsePosition` dùng hết chuỗi |

Tất cả nằm trong **một** chỗ: `utils/DateUtils` — dùng cho bàn phím, màn hình **và** tệp.

### 2.2 ID = ID lớn nhất + 1 — chạy tay

| Việc | Danh sách ID | ID mới |
|---|---|---|
| thêm 3 lần | 1, 2, 3 | 1 → 2 → 3 |
| xoá 2 | 1, 3 | — |
| thêm | 1, 3, **4** | max 3 + 1 |
| (chạy khác) xoá 3 khi còn 1, 2, 3 → thêm | 1, 2, **3** | max 2 + 1 — ID 3 được **dùng lại** |

Tính từ danh sách (không giữ biến đếm) → **đúng cả sau khi đọc lại từ tệp**.

### 2.3 Tệp `expenses.txt`

Mỗi dòng: `id|dd-MMM-yyyy|amount|content`, ví dụ `1|11-Apr-2009|100.1|Tuition fee`.

| Quyết định | Vì sao |
|---|---|
| dấu `|` chứ không `,` | nội dung hay có dấu phẩy; `split("\\|", 4)` → mọi thứ sau `|` thứ ba là content |
| ghi **lại cả tệp** sau mỗi thay đổi | xoá một dòng giữa tệp chỉ có cách viết lại tệp |
| thêm/xoá rồi ghi hỏng → **hoàn tác** | bộ nhớ và tệp không bao giờ lệch nhau |
| dòng hỏng → **bỏ qua** | một dòng lỗi không làm chương trình chết |
| `Double.toString` khi ghi | luôn dấu chấm — tệp đọc giống nhau trên mọi máy |

### 2.4 Java dùng trong bài

| API | Dùng làm gì |
|---|---|
| `BufferedReader` + `FileReader`, `PrintWriter` | đọc/ghi từng dòng (`FileUtils`) |
| try-with-resources `try (...) { }` | tự đóng tệp kể cả khi lỗi |
| `SimpleDateFormat`, `ParsePosition`, `Locale.ENGLISH` | ngày (`DateUtils`) |
| `new DecimalFormat("0.##", new DecimalFormatSymbols(Locale.US))` | `FormatUtils.formatMoney`: `100`, `Total: 550` như đề; `100.1` khi có lẻ; dấu chấm chứ không `100,1` |

---

## 3. Thiết kế

```
HE176322_J1SP0073_HandyExpense/
├── expenses.txt              (tạo ra khi thêm chi tiêu đầu tiên)
└── src/
    ├── model/       Expense              id, Date, amount, content (JavaBean)
    ├── dto/         ExpenseRequestDTO    date/amount/content hoặc id (main ──► controller)
    │                ExpenseResponseDTO   1 dòng bảng, ngày đã thành chữ (controller ──► view)
    ├── repository/  ExpenseRepository    ArrayList + load/findAll/findById/addExpense/deleteExpense + ghi tệp
    ├── service/     ExpenseService       ID = max + 1, tổng tiền, đổi model → DTO
    ├── controller/  ExpenseController    Facade: loadExpenses, addExpense, displayAll, deleteExpense
    ├── view/        ExpenseView          displayAll (bảng + Total), showMessage
    ├── constants/   Message, Constants   câu chữ; tên tệp, định dạng, regex ngày
    ├── utils/       Validation           getChoice, getInt, getDate, getAmount, getContent
    │                DateUtils            formatDate / parseDate (English, strict)
    │                FileUtils            readLines / writeLines
    │                FormatUtils          formatMoney: "0.##" → 100 / 100.1
    └── main/        Main                 menu + Scanner
```

| Lớp | Vì sao ở đây |
|---|---|
| `ExpenseRepository` | Guide: *"Chứa data … các method CRUD"* — và đồng bộ với tệp |
| `ExpenseService` | ID tự tăng và **tổng tiền** là *"tính toán nghiệp vụ (tính tổng, report)"* → service |
| `FileUtils`, `DateUtils` | Guide: utils chứa *"validate, đọc/ghi file"*, static |

**Luồng Delete** (Controller ↔ Service ↔ Repository ↔ Model):

```
Main: inputDelete(sc) ──► ExpenseRequestDTO(id) ──► controller.deleteExpense(dto)
   controller ──► service.deleteExpense(dto)
                     ├─ repository.findById(id)        → null? throw "Delete an expense fail"
                     └─ repository.deleteExpense(exp)  → remove + ghi lại expenses.txt
   controller ──► view.showMessage("Delete an expense successful")
Main: catch (Exception e) → in e.getMessage()
```

### 3.1 Design Pattern

| Pattern | Trong bài | 4 yếu tố GoF, nói gọn |
|---|---|---|
| **Repository** (mẫu kiến trúc dữ liệu, không thuộc 23 GoF) | `ExpenseRepository` | **Problem**: danh sách và tệp phải luôn khớp; nếu nhiều lớp cùng sửa list/tệp thì dễ lệch. **Solution**: **một** lớp giữ `ArrayList<Expense>`, mọi thêm/xoá đi qua nó và nó tự ghi tệp (hoàn tác nếu ghi hỏng). **Consequences**: ✅ đổi sang tệp nhị phân / CSDL chỉ sửa lớp này (+ `FileUtils`); ❌ phải ghi lại cả tệp mỗi lần — chấp nhận được với sổ nhỏ. |
| **Facade** | `ExpenseController` | **Problem**: `Main` sẽ phải biết service, repository, view, tệp. **Solution**: controller là một cửa 4 hàm. **Consequences**: `Main` gọn; controller không được ôm nghiệp vụ. |
| **MVC** (JSP) | `ExpenseController` ~ Servlet · `ExpenseView` ~ JSP · `Expense` ~ JavaBean | thầy bắt |

> Bài CRUD này **không có** thuật toán thay thế được (Strategy) hay họ lớp con (Factory/Template) — nhét
> vào chỉ để có là vi phạm YAGNI (ghi chú slide 26 SOLID). Nếu thầy hỏi "muốn đổi cách lưu thì sao?":
> tách interface `ExpenseStorage` (`load`, `save`) + `TextFileStorage`, repository nhận storage qua
> constructor → đó là **Strategy** cho cách lưu (và **D** của SOLID).

### 3.2 SOLID

| | Ở đâu |
|---|---|
| **S** | `Expense` mô tả · `ExpenseRepository` lưu · `ExpenseService` luật · `ExpenseView` in · `FileUtils` tệp · `DateUtils` ngày |
| **O** | thêm cột mới không phải sửa `ExpenseController` (chỉ chuyển DTO) |
| **D** (một phần) | `Main` chỉ biết controller + DTO; controller chỉ biết service |

---

## 4. Code từng bước

> Thầy: *"code cái **model trước rồi đến data**"*.

| Bước | File | Việc |
|---|---|---|
| 1 | `model/Expense.java` | 4 field + ctor rỗng + ctor đủ + get/set + `toString` |
| 2 | `dto/ExpenseRequestDTO`, `ExpenseResponseDTO` | JavaBean; Response có `toString` định dạng cột |
| 3 | `constants/Constants`, `Message` | tên tệp, `DATE_FORMAT`, `DATE_REGEX`, định dạng bảng; câu chữ |
| 4 | `utils/DateUtils`, `FileUtils`, `FormatUtils` | `formatDate`/`parseDate`; `readLines`/`writeLines`; `formatMoney` |
| 5 | `repository/ExpenseRepository.java` | `loadExpenses`, `findAll`, `findById`, `addExpense`, `deleteExpense`, `saveExpenses`, `toLine`, `toExpense` |
| 6 | `service/ExpenseService.java` | `nextId`, `getTotal`, `addExpense`, `deleteExpense`, `getAllExpenses` |
| 7 | `view/ExpenseView.java`, `controller/ExpenseController.java` | `displayAll`, `showMessage`; 4 hàm |
| 8 | `utils/Validation.java` | 5 hàm kiểm |
| 9 | `main/Main.java` | `loadExpenses` đầu chương trình; menu; `inputExpense` 3 vòng hỏi lại; `inputDelete` |

**Bẫy hay gặp:**

1. Quên `Locale.ENGLISH` → chạy trên máy mình (tiếng Anh) đúng, lên máy lab tiếng Việt thì hỏng.
2. Giữ biến đếm ID trong bộ nhớ → sau khi mở lại chương trình ID bắt đầu lại từ 1, **trùng ID trong tệp**.
3. `PrintWriter` **nuốt lỗi ghi** → phải hỏi `checkError()`.
4. Tệp nằm ở **thư mục project** (cạnh `build.xml`) vì NetBeans chạy chương trình ở đó.

---

## 5. Test trước khi gọi thầy

> Xoá `expenses.txt` trước khi test để bắt đầu từ sổ rỗng.

| # | Chọn | Gõ | Phải thấy |
|---|---|---|---|
| 1 | menu | `x` / `9` | `You must input a number.` / `Please input a number in [1, 4].` |
| 2 | 2 | (sổ rỗng) | `There is no expense to display.` |
| 3 | 1 | `31-Feb-2009`, `2009-04-20`, `11-Apr-09` | `Date must be in format dd-MMM-yyyy, e.g. 11-Apr-2009.` |
| 4 | 1 | `11-apr-2009` (chữ thường) | được nhận, in `11-Apr-2009` |
| 5 | 1 | Amount `abc` / `0` / `-5` | `Amount must be a number.` / `Amount must be greater than 0.` |
| 6 | 1 | Content để trống / toàn dấu cách | `This field must not be empty.` |
| 7 | 1 ×3 | 3 chi tiêu của đề | `Add an expense successful` |
| 8 | 2 | `100`/`250`/`200` | bảng ID 1, 2, 3 + `Total: 550` — số tròn **không** có `.00`; nhập `100.1` thì in `100.1` |
| 9 | 3 | `9` | `Delete an expense fail` |
| 10 | 3 | `abc` | `You must input a number.` rồi hỏi lại |
| 11 | 3 | `2` | `Delete an expense successful`; bảng còn 1 và 3 |
| 12 | 3 → 1 | xoá ID lớn nhất rồi thêm | ID mới = ID lớn nhất còn lại + 1 |
| 13 | 4, rồi **chạy lại** F6, chọn 2 | | bảng **vẫn còn** dữ liệu cũ (đọc từ `expenses.txt`) — test tự động không làm được bước này, phải làm tay |
| 14 | 4 | | `Bye.` |

---

## 6. Debug

| Việc | Cách làm |
|---|---|
| Breakpoint | dòng `Expense expense = new Expense(nextId(), ...)` trong `ExpenseService.addExpense` |
| Chạy | **Ctrl+F5**, thêm 2 chi tiêu |
| Bước | **F7** vào `nextId()` → xem `maxId` tăng qua vòng `for`; **F7** vào `expenseRepository.addExpense` → `saveExpenses` → `FileUtils.writeLines` |
| Quan sát | tab **Variables**: `expenseList` (kích thước), `lines` (chuỗi sẽ ghi); mở `expenses.txt` sau khi F8 qua `writeLines` |
| Xoá sai ID | breakpoint `if (exp == null)` trong `deleteExpense`: nhập 9 → F8 vào `throw`, rơi vào `catch` của `Main` |

---

## 7. Câu hỏi thầy hay hỏi

### OOP

| Câu hỏi | Trả lời mẫu |
|---|---|
| 4 tính chất OOP? | **Đóng gói**: field `private` của `Expense`; `expenseList` `private` trong repository, bên ngoài chỉ nhận **bản sao** (`findAll`). **Kế thừa**: mọi lớp `extends Object`, ghi đè `toString()`. **Đa hình**: `println(expense)` gọi `toString()` của `ExpenseResponseDTO`. **Trừu tượng**: `Main` gọi `controller.addExpense(dto)` không biết có tệp. |
| Sao `findAll` trả bản sao? | Nơi gọi sửa bản sao cũng không làm lệch sổ và tệp. |

### Access modifier / static / kiểu

| Chỗ | Vì sao |
|---|---|
| field | `private` |
| hàm của repository (`loadExpenses`, `findAll`, `findById`, `addExpense`, `deleteExpense`) | `public` — service gọi; `saveExpenses`, `toLine`, `toExpense` **private** vì chỉ repository dùng |
| `ExpenseService` 5 hàm | `public` — controller gọi; `nextId`, `toResponse` **private** |
| `ExpenseController` 4 hàm, `ExpenseView.displayAll/showMessage/setX` | `public` — `Main` / controller gọi |
| `FileUtils`, `DateUtils`, `FormatUtils`, `Validation` | `public static` — utils; `DateUtils.createFormat` **private static** (chỉ dùng trong lớp) |
| hàm trong `Main` | `private static` |

| Câu hỏi | Trả lời mẫu |
|---|---|
| Sao **không** để `SimpleDateFormat` là field `static`? | Nó giữ trạng thái khi làm việc (không an toàn khi dùng chung) và cần gọi `setLenient(false)`; tạo mới mỗi lần trong `createFormat()` gọn và an toàn. |
| **Bỏ `static` ở `FileUtils.readLines`?** | Lỗi biên dịch ở `FileUtils.readLines(...)`; phải bỏ `private` constructor và `new FileUtils()` ở repository. |
| `addExpense` trả `boolean`? | Đề: *"Return values: Add expense status"*; lỗi ghi tệp đi bằng `throw`. |
| `displayAll` trả `void`? | In xong là hết việc — đúng đề. |
| `getTotal` trả `double`? | Tiền có phần lẻ. |
| **Sao `ArrayList` mà không `List`?** | `List` là interface, `ArrayList` là lớp cài bằng mảng động (duyệt theo thứ tự thêm, lấy theo chỉ số nhanh). Em khai báo kiểu cụ thể theo lời thầy; đề có ghi `List<Expense>` trong tham số nhưng tham số list đó **bị bỏ** (repository tự giữ list). |
| Sao hàm đề bắt không nhận `list`? | Guide: repository **giữ** dữ liệu; truyền list qua lại là "truyền dữ liệu qua lại" mà HD cấm (*"Đóng gói — Không truyền dữ liệu qua lại"*). |

---

## 8. Thầy đổi yêu cầu tại chỗ

| Thầy bảo | Sửa | Không đụng |
|---|---|---|
| Đổi định dạng ngày `dd/MM/yyyy` | `Constants.DATE_FORMAT`, `DATE_REGEX`, `Message.INVALID_DATE` | mọi file khác |
| Đổi tên tệp | `Constants.FILE_NAME` (+ `Message.LOAD_FAIL`) | mọi file khác |
| Thêm menu "Sửa chi tiêu" | `Message.MENU`, `Constants`, `ExpenseRepository.updateExpense`, `ExpenseService`, `ExpenseController`, 1 `case` + hàm nhập ở `Main` | `Expense`, `FileUtils`, `DateUtils` |
| Tổng theo tháng | 1 hàm mới ở `ExpenseService` + view + controller | repository, `FileUtils` |

---

## 9. Chỗ khác với đề / bản cũ

| Chỗ | Đề / bản cũ | Bài này | Lý do |
|---|---|---|---|
| `addExpense(list, date, amount, content)` | 4 tham số | `addExpense(ExpenseRequestDTO)` | luật V4; list do repository giữ |
| `deleteExpense(list, Expense exp)` | nhận list | repository: `deleteExpense(Expense exp)`; service: `deleteExpense(dto)` | V4 + Guide (dữ liệu vào qua DTO) |
| `displayAll(list)` | 1 hàm in | controller `displayAll()` + view `displayAll()` | in là việc của view |
| Kiểu `date` | Đặc tả: String; Guidelines: `Date` | `java.util.Date` | theo chữ ký hàm của Guidelines; ngày sai bị chặn ngay |
| Số tiền, Total | đề in `100`, `Total: 550`; bản tham chiếu in `100.00`, `550.60` | mẫu `"0.##"`: `100`, `Total: 550`; `100.1` khi có lẻ | **màn hình đề thắng**; run 0 của bản tham chiếu được chép lại với số tiền viết như đề |
| Menu mục 3/4 | đề: "Remove"/"Exit" chỗ này, "Delete"/"Quit" chỗ kia | `3. Delete an expense`, `4. Quit` | theo màn hình "Expectation of User interface" |
| Enter ID | bản cũ: chỉ nhận 1..2147483647 | nhận mọi số nguyên; không có → `Delete an expense fail` | đúng câu của đề cho ID không tồn tại |
| Kịch bản test | bản cũ: run 1 dựa vào tệp run 0 để lại | `REPLACE_REFERENCE = True`: giữ nguyên run 0, viết run mới | verify chạy mỗi run trên bản sao sạch; tính lưu tệp kiểm tay (mục 5 #13) |
| Kiến trúc | `bo/ui`, Scanner static trong `Validator` | MVC Guide, `FileUtils`/`DateUtils` trong utils | luật thầy |
