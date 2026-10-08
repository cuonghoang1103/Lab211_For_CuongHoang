# J1.S.P0073 — Handy Expense (bản học thuộc)

## (a) Đề tóm 5 dòng
1. Menu: 1. Add an expense · 2. Display all expenses · 3. Delete an expense · 4. Quit.
2. Chi tiêu gồm `ID` (tự tăng = ID lớn nhất + 1, cái đầu là 1), `Date` dạng `11-Apr-2009`, `Amount`, `Content`.
3. Display: bảng `ID Date Amount Content` + dòng `Total:` (tổng tiền).
4. Delete: nhập ID; không có → `Delete an expense fail`; có → `Delete an expense successful`.
5. "File processing program": dữ liệu lưu ở `expenses.txt` — mở chương trình thì đọc, thêm/xoá thì ghi lại.

## (b) Cây file
```
src/
├── constants/Message.java        mọi chữ hiện ra màn hình (menu, câu nhắc, lỗi, kết quả)
├── constants/Constants.java      số menu, tên file, dấu "|", vị trí cột, định dạng ngày/tiền
├── model/Expense.java            1 chi tiêu: id, date, amount, content
├── dto/ExpenseRequestDTO.java    Main gửi xuống controller: date, amount, content
├── dto/ExpenseResponseDTO.java   service gửi lên view: 1 dòng bảng (toString in theo cột)
├── repository/ExpenseRepository.java  giữ expenseList, tìm/thêm/xoá, ghi lại file
├── service/ExpenseService.java   tính toán: ID = max + 1, tổng tiền, định dạng tiền
├── controller/ExpenseController.java  nhận DTO, gọi service, đưa dữ liệu cho view
├── view/ExpenseView.java         in bảng + Total
├── utils/Validation.java         kiểm số, menu, ngày, tiền, chuỗi rỗng (sai thì throw)
├── utils/FileUtils.java          readLines / writeLines của file text
└── main/Main.java                Scanner, menu, hỏi từng ô, đọc file lúc khởi động
```
File `expenses.txt` tự tạo trong thư mục project khi thêm chi tiêu đầu tiên. Mỗi dòng: `1|11-Apr-2009|100.1|Tuition fee`.

## (c) Luồng chức năng chính (Add)
1. `Main` in menu, `promptChoice` → `Validation.getChoice` (sai thì in lỗi, hỏi lại `Your choice:`).
2. Chọn 1: `promptDate/promptAmount/promptContent` → `Validation.getDate/getAmount/getContent`; sai ô nào hỏi lại ô đó.
3. `Main` gói 3 giá trị vào `ExpenseRequestDTO` → `expenseController.addExpense(dto)`.
4. Controller → `expenseService.addExpense(dto)`: tạo `Expense` với `generateNextId()` (max + 1).
5. Service → `expenseRepository.addExpense(expense)`: thêm vào `expenseList` rồi `saveExpenses()` → `FileUtils.writeLines`.
6. Không lỗi → `Main` in `Add an expense successful`. Display: service đổi `Expense` → `ExpenseResponseDTO` + tính tổng → controller `setExpenseList/setTotal` → `ExpenseView.display()`.

## (d) Bảng import
| Lớp | Import | Nói với thầy |
|---|---|---|
| Main | Constants, Message, ExpenseController, ExpenseRequestDTO, Scanner, FileUtils, Validation | Main nhập + validate + đọc file, chỉ biết controller và RequestDTO. |
| ExpenseController | Message, ExpenseRequestDTO, ExpenseResponseDTO, List, ExpenseService, ExpenseView | Controller không import model, chỉ chuyển DTO giữa service và view. |
| ExpenseService | Constants, 2 DTO, DecimalFormat, DecimalFormatSymbols, ArrayList, List, Locale, Expense, ExpenseRepository | Service có tính toán (ID tự tăng, tổng tiền) nên mới có; nó đổi model sang ResponseDTO. |
| ExpenseRepository | Constants, Message, ArrayList, List, Expense, FileUtils | Repository giữ danh sách và ghi file sau mỗi thay đổi. |
| ExpenseView | ExpenseResponseDTO, List | View chỉ biết ResponseDTO, chỉ in. |
| Expense / RequestDTO / ResponseDTO | (không import) | Chỉ là lớp chứa dữ liệu. |
| Validation | Constants, Message, ParseException, SimpleDateFormat, Date, Locale | Kiểm dữ liệu, sai thì `throw new Exception(Message.X)`. |
| FileUtils | BufferedReader, File, FileReader, IOException, PrintWriter, ArrayList, List | Đề bắt xử lý file nên mới có FileUtils. |
| Message / Constants | (không import) | Chỉ chứa hằng số, private constructor. |

## (e) Phím test (xoá `expenses.txt` trước)
| # | Gõ | Phải thấy |
|---|---|---|
| 1 | `x` rồi `9` | `You must input a number.` rồi `Please input a number in [1, 4].` |
| 2 | `2` | `There is no expense to display.` |
| 3 | `1`, `31-Feb-2009` | `Date must be in format dd-MMM-yyyy, e.g. 11-Apr-2009.` hỏi lại ngày |
| 4 | `11-apr-2009`, `abc`, `0`, `100.10`, (Enter trống), `Tuition fee` | `Amount must be a number.` · `Amount must be greater than 0.` · `This field must not be empty.` · `Add an expense successful` |
| 5 | `1` `20-Apr-2009` `250.20` `Rent house`; `1` `30-Apr-2009` `200.30` `Food` | 2 lần `Add an expense successful` |
| 6 | `2` | ID 1,2,3 (ngày in `11-Apr-2009`), `Total: 550.6` |
| 7 | `3`, `abc`, `9` | `You must input a number.` rồi `Delete an expense fail` |
| 8 | `3`, `2` rồi `2` | `Delete an expense successful`; bảng còn 1 và 3, `Total: 300.4` |
| 9 | `1` thêm 1 cái rồi `2` | ID mới = 4 (max 3 + 1) |
| 10 | `4`, chạy lại, `2` | `Bye.`; mở lại vẫn còn dữ liệu (đọc từ `expenses.txt`) |

## (f) Câu thầy hay hỏi
1. **Sao có service?** Có tính toán ngoài CRUD: ID = ID lớn nhất + 1 và tính tổng tiền (`getTotal`).
2. **Sao có FileUtils?** Đề ghi "file processing program" — dữ liệu phải lưu file; đọc ở Main lúc mở, repository ghi lại sau mỗi thêm/xoá.
3. **Sao ID không dùng biến đếm?** Đọc lại từ file thì biến đếm về 0 → trùng ID; tính max + 1 từ danh sách luôn đúng.
4. **Sao `Locale.ENGLISH` khi đọc ngày, `Locale.US` khi in tiền?** Máy lab tiếng Việt: tháng thành "thg 4", tiền thành `100,1`. Khoá locale để màn hình giống đề trên mọi máy.
5. **`setLenient(false)` để làm gì?** Không có nó `31-Feb-2009` bị đổi thành 3/3/2009; có nó thì báo sai.
6. **Controller có import model không?** Không. Controller chỉ nhận RequestDTO và đưa List<ResponseDTO> cho view; đổi model → DTO là việc của service.
7. **Lỗi đi thế nào?** Validation/controller `throw new Exception(Message.X)`; Main `catch` và in `e.getMessage()`. Nhập sai thì vòng `while(true)` trong `promptX` hỏi lại đúng ô đó.
8. **Sao dùng dấu `|` trong file?** Nội dung hay có dấu phẩy; `split("\\|", 4)` nên mọi thứ sau dấu `|` thứ ba vẫn là content.
