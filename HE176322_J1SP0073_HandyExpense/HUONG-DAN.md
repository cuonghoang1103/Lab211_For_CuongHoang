# J1.S.P0073 — Handy Expense

> Bài CRUD **có tệp dữ liệu**: danh sách chi tiêu được đọc từ `expenses.txt` lúc mở chương trình và
> ghi lại sau **mỗi** lần thêm/xoá. Khung giống P0054/P0055, thêm `service` (ID tự tăng, tổng tiền) và
> `utils/FileUtils`, `utils/DateUtils`. Theo tờ checklist: **`Main` đọc tệp** (repository chỉ tách
> dòng thành `Expense`), View nhận **`ExpenseResponseDTO` qua thuộc tính** và in **1 lần mỗi luồng**.

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
| `void displayAll(List<Expense> list)` | in bảng | `ExpenseController.displayAll()` — lấy dòng + tổng từ `ExpenseService.getRowList()/getTotal()`, in bằng `ExpenseView.display()` (in là việc của view) |
| `boolean deleteExpense(List<Expense> list, Expense exp)` | nhận `Expense` | `ExpenseService.deleteExpense(ExpenseRequestDTO)` (trả `false` khi không có ID) → `ExpenseRepository.deleteExpense(Expense exp)` |
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

**Ai đọc, ai ghi** (tờ checklist 1.1: *"đọc từ file … thực hiện ở Main"*): `Main.readData()` gọi
`FileUtils.readLines` → đặt vào `ExpenseRequestDTO.lineList` → `controller.loadExpenses(requestDTO)` →
service → `ExpenseRepository.loadExpenses` **tách từng dòng** thành `Expense` (`parseExpense`). Ghi lại
tệp sau mỗi thêm/xoá vẫn do repository gọi `FileUtils.writeLines` (tờ giấy không nói "ghi").

| Quyết định | Vì sao |
|---|---|
| dấu `|` chứ không `,` | nội dung hay có dấu phẩy; `split("\\|", 4)` → mọi thứ sau `|` thứ ba là content |
| ghi **lại cả tệp** sau mỗi thay đổi | xoá một dòng giữa tệp chỉ có cách viết lại tệp |
| thêm/xoá rồi ghi hỏng → **hoàn tác** | bộ nhớ và tệp không bao giờ lệch nhau |
| dòng hỏng → **bỏ qua** | một dòng lỗi không làm chương trình chết |
| `String.valueOf(amount)` (= `Double.toString`) khi ghi, ghép bằng `String.join` | luôn dấu chấm — tệp đọc giống nhau trên mọi máy; không cộng chuỗi bằng `+` |

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
    ├── model/       Expense              id, Date, amount, content (JavaBean); toString() = 1 dòng bảng
    ├── dto/         ExpenseRequestDTO    date/amount/content, hoặc id, hoặc lineList của tệp (main ──► controller)
    │                ExpenseResponseDTO   message, rowList, total (controller ──► view)
    ├── repository/  ExpenseRepository    expenseList + loadExpenses(requestDTO)/findAll/findById/addExpense/deleteExpense + ghi tệp
    ├── service/     ExpenseService       ID = max + 1 (generateNextId), tổng tiền, các dòng bảng
    ├── controller/  ExpenseController    Facade: loadExpenses, addExpense, displayAll, deleteExpense — mỗi luồng display() 1 lần
    ├── view/        ExpenseView          field responseDTO + setResponseDTO + display() (message, hoặc bảng + Total)
    ├── constants/   Message, Constants   câu chữ; tên tệp, định dạng, regex ngày
    ├── utils/       Validation           getChoice, getInt, getDate, getAmount, getContent
    │                DateUtils            formatDate / parseDate (English, strict)
    │                FileUtils            readLines (Main gọi) / writeLines (repository gọi)
    │                FormatUtils          formatMoney: "0.##" → 100 / 100.1
    └── main/        Main                 final + private Main(); đọc tệp; menu + Scanner + validate
```

| Lớp | Vì sao ở đây |
|---|---|
| `ExpenseRepository` | Guide: *"Chứa data … các method CRUD"* — và đồng bộ với tệp |
| `ExpenseService` | ID tự tăng và **tổng tiền** là *"tính toán nghiệp vụ (tính tổng, report)"* → service |
| `FileUtils`, `DateUtils` | Guide: utils chứa *"validate, đọc/ghi file"*, static. `Main` gọi `readLines` (tờ checklist: đọc file ở Main); repository gọi `writeLines` |

**Luồng Delete** (Controller ↔ Service ↔ Repository ↔ Model):

```
Main: inputDelete(sc) ──► ExpenseRequestDTO(id) ──► controller.deleteExpense(requestDTO)   ← gọi 1 lần
   controller ──► service.deleteExpense(requestDTO)
                     ├─ repository.findById(id)        → null? return false
                     └─ repository.deleteExpense(exp)  → remove + ghi lại expenses.txt → true
   controller: false → throw new Exception(Message.DELETE_FAIL)          ("Delete an expense fail")
               true  → responseDTO.setMessage(Message.DELETE_SUCCESS)
                       → view.setResponseDTO(responseDTO) → view.display()   ← render 1 lần
Main: catch (Exception e) → in e.getMessage()
```

**Luồng khởi động** (đọc tệp ở `Main`):

```
Main: readData() = FileUtils.readLines("expenses.txt") ──► ExpenseRequestDTO(lineList)
      controller.loadExpenses(requestDTO) ──► service ──► repository.loadExpenses(requestDTO)
                     └─ parseExpense(từng dòng): dòng hỏng → bỏ qua; dòng tốt → expenseList
Main: catch (IOException e) → in "Cannot read expenses.txt: ..." rồi vẫn vào menu (sổ rỗng)
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
| **S** | `Expense` mô tả · `Main` nhập + đọc tệp · `ExpenseRepository` lưu · `ExpenseService` luật · `ExpenseView` in · `FileUtils` tệp · `DateUtils` ngày |
| **O** | thêm cột mới không phải sửa `ExpenseController` (chỉ chuyển DTO) |
| **D** (một phần) | `Main` chỉ biết controller + DTO; controller chỉ biết service |

---

## 4. Code từng bước

> Thầy: *"code cái **model trước rồi đến data**"*.

| Bước | File | Việc |
|---|---|---|
| 1 | `model/Expense.java` | 4 field (mỗi field một comment, cách nhau 1 dòng trống) + ctor rỗng + ctor đủ + get/set + `toString` = 1 dòng bảng (`Constants.ROW_FORMAT`) |
| 2 | `dto/ExpenseRequestDTO`, `ExpenseResponseDTO` | JavaBean; Request: id, date, amount, content, `lineList`; Response: `message`, `rowList`, `total` |
| 3 | `constants/Constants`, `Message` | tên tệp, `DATE_FORMAT`, `DATE_REGEX`, định dạng bảng; câu chữ, `TOTAL_FORMAT` |
| 4 | `utils/DateUtils`, `FileUtils`, `FormatUtils` | `formatDate`/`parseDate`; `readLines`/`writeLines`; `formatMoney` |
| 5 | `repository/ExpenseRepository.java` | `loadExpenses(requestDTO)`, `findAll`, `findById`, `addExpense`, `deleteExpense`, `saveExpenses`, `formatLine`, `parseExpense` |
| 6 | `service/ExpenseService.java` | `generateNextId`, `getTotal`, `getRowList`, `addExpense`, `deleteExpense`, `loadExpenses` |
| 7 | `view/ExpenseView.java`, `controller/ExpenseController.java` | field `responseDTO` + `setResponseDTO` + `display()`; 4 hàm, mỗi luồng gọi `display()` **1 lần** |
| 8 | `utils/Validation.java` | 5 hàm kiểm |
| 9 | `main/Main.java` | `final` + `private Main()`; biến khai báo đầu block; `readData` → `loadExpenses`; menu; `inputExpense` (`inputDate`, `inputAmount`, `inputContent`); `inputDelete` (`inputId`) |

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
| Breakpoint | dòng `Expense expense = new Expense(generateNextId(), ...)` trong `ExpenseService.addExpense` |
| Chạy | **Ctrl+F5**, thêm 2 chi tiêu |
| Bước | **F7** vào `generateNextId()` → xem `maxId` tăng qua vòng `for`; **F7** vào `expenseRepository.addExpense` → `saveExpenses` → `FileUtils.writeLines` |
| Quan sát | tab **Variables**: `expenseList` (kích thước), `lineList` (chuỗi sẽ ghi); mở `expenses.txt` sau khi F8 qua `writeLines` |
| Xoá sai ID | breakpoint `if (exp == null)` trong `ExpenseService.deleteExpense`: nhập 9 → F8 thấy `return false`; về controller F8 vào `throw new Exception(Message.DELETE_FAIL)`, rơi vào `catch` của `Main` |

---

## 7. Câu hỏi thầy hay hỏi

### OOP

| Câu hỏi | Trả lời mẫu |
|---|---|
| 4 tính chất OOP? | **Đóng gói**: field `private` của `Expense`; `expenseList` `private` trong repository, bên ngoài chỉ nhận **bản sao** (`findAll`). **Kế thừa**: mọi lớp `extends Object`, ghi đè `toString()`. **Đa hình**: `expense.toString()` trong `ExpenseService.getRowList` chạy bản **ghi đè** của `Expense` (1 dòng bảng). **Trừu tượng**: `Main` gọi `controller.addExpense(dto)` không biết có tệp. |
| Sao `findAll` trả bản sao? | Nơi gọi sửa bản sao cũng không làm lệch sổ và tệp. |

### Access modifier / static / kiểu

| Chỗ | Vì sao |
|---|---|
| field | `private` |
| hàm của repository (`loadExpenses`, `findAll`, `findById`, `addExpense`, `deleteExpense`) | `public` — service gọi; `saveExpenses`, `formatLine`, `parseExpense` **private** vì chỉ repository dùng |
| `ExpenseService` 5 hàm (`loadExpenses`, `addExpense`, `deleteExpense`, `getRowList`, `getTotal`) | `public` — controller gọi; `generateNextId` **private** |
| `ExpenseController` 4 hàm, `ExpenseView.setResponseDTO/display` | `public` — `Main` / controller gọi |
| `FileUtils`, `DateUtils`, `FormatUtils`, `Validation` | `public static` — utils; `DateUtils.createFormat` **private static** (chỉ dùng trong lớp) |
| hàm trong `Main` | `private static` |

| Câu hỏi | Trả lời mẫu |
|---|---|
| Sao **không** để `SimpleDateFormat` là field `static`? | Nó giữ trạng thái khi làm việc (không an toàn khi dùng chung) và cần gọi `setLenient(false)`; tạo mới mỗi lần trong `createFormat()` gọn và an toàn. |
| **Bỏ `static` ở `FileUtils.readLines`?** | Lỗi biên dịch ở `FileUtils.readLines(...)` trong `Main`; phải bỏ `private` constructor và `new FileUtils()` ở nơi gọi. |
| `addExpense` trả `boolean`? | Đề: *"Return values: Add expense status"*; lỗi ghi tệp đi bằng `throw`. |
| `displayAll` trả `void`? | In xong là hết việc — đúng đề. |
| `getTotal` trả `double`? | Tiền có phần lẻ. |
| **Sao `ArrayList` mà không `List`?** | `List` là interface, `ArrayList` là lớp cài bằng mảng động (duyệt theo thứ tự thêm, lấy theo chỉ số nhanh). Em khai báo kiểu cụ thể theo lời thầy; đề có ghi `List<Expense>` trong tham số nhưng tham số list đó **bị bỏ** (repository tự giữ list). |
| Sao bài có repository? | Tờ checklist 1.1: *"Bắt buộc phải có repository"*. `ExpenseRepository` giữ `expenseList` + CRUD đơn giản (`findAll`, `findById`, `addExpense`, `deleteExpense`) và ghi tệp; tính toán (ID tự tăng, tổng) nằm ở `ExpenseService`. |
| View nhận dữ liệu thế nào? | Qua **thuộc tính**: field `private ExpenseResponseDTO responseDTO` + `setResponseDTO(...)` + `display()` **không tham số**. Controller đặt `message` (thêm/xoá, sổ rỗng) hoặc `rowList` + `total` (bảng) rồi gọi `display()` **đúng 1 lần** mỗi luồng. |
| Validate ở đâu? | Ở **`Main`** qua `utils/Validation` (`getChoice`, `getDate`, `getAmount`, `getContent`, `getInt`) — sai thì in lý do và hỏi lại ngay ô đó. ID không tồn tại **không** phải lỗi gõ mà là kết quả nghiệp vụ → service trả `false`, controller ném `Delete an expense fail`. |
| Sao đọc tệp ở `Main` mà ghi tệp ở repository? | Tờ checklist 1.1: *"nhập dữ liệu/Validate/đọc từ file/mã hóa thực hiện ở Main"* — nên `Main.readData()` gọi `FileUtils.readLines` rồi đưa các dòng vào `ExpenseRequestDTO`. Tờ giấy không nói "ghi"; ghi phải chạy **sau mỗi** thêm/xoá, đúng lúc repository đổi `expenseList`, nên repository gọi `FileUtils.writeLines`. |
| Sao service `deleteExpense` trả `false` mà không ném lỗi? | Đề: *"Return values: Delete the expense status"* — `false` = không có ID đó. Controller đổi `false` thành `throw new Exception(Message.DELETE_FAIL)`, `Main` in `e.getMessage()`. |
| `Main` sao `final` và có `private Main()`? | Tờ checklist 3.4: class chỉ có hàm static thì phải `final` + private constructor. |
| Sao hàm đề bắt không nhận `list`? | Guide: repository **giữ** dữ liệu; truyền list qua lại là "truyền dữ liệu qua lại" mà HD cấm (*"Đóng gói — Không truyền dữ liệu qua lại"*). |

---

## 8. Thầy đổi yêu cầu tại chỗ

| Thầy bảo | Sửa | Không đụng |
|---|---|---|
| Đổi định dạng ngày `dd/MM/yyyy` | `Constants.DATE_FORMAT`, `DATE_REGEX`, `Message.INVALID_DATE` | mọi file khác |
| Đổi tên tệp | `Constants.FILE_NAME` (+ `Message.LOAD_FAIL`) | mọi file khác |
| Thêm menu "Sửa chi tiêu" | `Message.MENU`, `Constants`, `ExpenseRepository.updateExpense`, `ExpenseService`, `ExpenseController`, 1 `case` + hàm nhập ở `Main` | `Expense`, `FileUtils`, `DateUtils` |
| Tổng theo tháng | 1 hàm mới ở `ExpenseService` + 1 field trong `ExpenseResponseDTO` + controller đặt + `display()` in | repository, `FileUtils` |

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
| Đọc tệp | bản cũ: `ExpenseRepository.loadExpenses()` tự gọi `FileUtils.readLines` | `Main.readData()` đọc → `ExpenseRequestDTO.lineList` → repository chỉ tách dòng (`parseExpense`) | tờ checklist 1.1: đọc file ở Main |
| View | bản cũ: `displayAll()` + `showMessage(String)`, controller gọi view 2–3 lần | field `responseDTO` + `setResponseDTO` + `display()` không tham số, 1 lần/luồng | tờ checklist 1.1 |
| `ExpenseResponseDTO` | bản cũ: 1 dòng bảng (id, date, amount, content + `toString`) | 1 câu trả lời: `message`, `rowList`, `total`; 1 dòng bảng là `Expense.toString()` | khuôn P0054; Guide: *"cần output gì thì thêm hàm toString()"* ở model |
| Tiêu đề Display | bản cũ: view in | `Main` in (như tiêu đề Add/Delete) | màn hình **không đổi** |
| Service `deleteExpense` | bản cũ: ném lỗi khi không có ID | trả `false`; controller ném `Delete an expense fail` | đúng *"Delete the expense status"* của đề; màn hình không đổi |
| Tên | `nextId`, `toLine`, `toExpense`, `getAllExpenses`, `lines`, `parts`, `result` | `generateNextId`, `formatLine`, `parseExpense`, `getRowList`, `lineList`, `partArray`, `rowList` | checklist 1.4 (method mở đầu bằng động từ), 1.5 |
| `Main` | `public class Main`, biến khai báo giữa block | `public final class Main` + `private Main()`; biến gom đầu block, khởi tạo luôn | checklist 3.4, 2.6, 3.7 |

---

## 10. Tờ checklist 25 mục — bài này đạt thế nào

| Mục | Chỗ trong code |
|---|---|
| **1.1** MVC + repository | `repository/ExpenseRepository` (`expenseList`). Luồng: `Main` → `ExpenseRequestDTO` → `ExpenseController` → `ExpenseService` → `ExpenseRepository` → `Expense`. Controller không import `model`; `Main` đọc tệp (`readData`) và validate; `ExpenseView` nhận `ExpenseResponseDTO` qua field, `display()` 1 lần/luồng; lỗi `throw new Exception(Message.X)`, `Main` in `e.getMessage()` |
| **1.4** tên method | `generateNextId`, `formatLine`, `parseExpense`, `getRowList`, `readData` — mở đầu bằng động từ |
| **1.5** tên biến | `expenseList`, `lineList`, `rowList`, `partArray`; `requestDTO`, `responseDTO`; `findById`, `getId` (không `ID`) |
| **2.6 / 3.7** khai báo đầu block + khởi tạo | `Main.main` (`requestDTO = null`, `running = true`, `choice = 0`), mọi `inputX` (`String line = "";`), `Validation.getAmount` (`double amount = 0;`), `DateUtils.parseDate` (`Date date = null;`), `ExpenseRepository.loadExpenses` (`Expense expense = null;`) |
| **2.8** dòng trống | trước mọi comment (cả comment field trong `Constants`, `Message`, DTO, model), sau vùng khai báo biến, sau `}` của mỗi khối trước câu lệnh tiếp |
| **3.3** ngoặc | `Validation.getChoice`: `if ((choice < min) \|\| (choice > max))`; `DateUtils.parseDate`: `if ((text == null) \|\| ...)`, `if ((date == null) \|\| (position.getIndex() != text.length()))` |
| **3.4** class chỉ có static | `public final class Main` + `private Main()`; `Validation`, `DateUtils`, `FileUtils`, `FormatUtils`, `Constants`, `Message` cũng `final` + private constructor |
| **3.8** không cộng chuỗi | `ExpenseRepository.formatLine` dùng `String.join`; `Expense.toString()` và dòng Total dùng `String.format` |
