# J1SP0057 — User Management System (bản học thuộc)

## (a) Đề tóm 5 dòng
1. Menu 3 mục: Create a new account · Login system · Exit.
2. Tài khoản lưu trong file `user.dat` (gốc project), mỗi dòng `username password`.
3. Khởi động: **đọc `user.dat` nạp vào ArrayList**; Login **tìm trong ArrayList**.
4. Create: **kiểm file tồn tại** (chưa có thì tạo) rồi **ghi thêm vào cuối file**; username trùng thì báo lỗi.
5. Username ≥ 5 ký tự, password ≥ 6 ký tự, cả hai không có dấu cách (cả khi login): sai thì `You must enter least at 5/6 character, and no space!`.

## (b) Cây file
```
user.dat                           file dữ liệu, có sẵn tài khoản đề: NghiaNV1 space@123
src/
├── constants/Message.java         mọi chữ hiện ra (menu, câu nhắc, lỗi, thành công, Goodbye.)
├── constants/Constants.java       số menu 1-3, độ dài tối thiểu 5/6, tên file user.dat, dấu cách ngăn
├── controller/AccountController   nhận dữ liệu từ Main → gọi Repository → đưa kết quả cho View
├── dto/AccountRequestDTO          username + password người dùng nhập
├── dto/AccountResponseDTO         câu kết quả để View in
├── main/Main.java                 Scanner, đọc user.dat lúc khởi động, menu, nhập + validate
├── model/Account.java             1 tài khoản; toString() = dòng ghi vào file
├── repository/AccountRepository   giữ ArrayList accountList: nạp từ dòng file, thêm (ghi file), tìm
├── utils/FileUtils.java           đọc tất cả dòng của file; ghi thêm 1 dòng vào cuối file
├── utils/Validation.java          kiểm lựa chọn menu, username, password
└── view/AccountView.java          in câu kết quả
```
Không có `service`: bài chỉ thêm và tìm (CRUD), không có tính toán. Có `FileUtils` vì đề bắt đọc/ghi file.

## (c) Luồng chính
1. Khởi động: `Main` gọi `FileUtils.readLines("user.dat")` (file chưa có → danh sách rỗng) → `accountController.loadData(lineList)` → Repository tách mỗi dòng thành `Account`, thêm vào `accountList`.
2. `Main` in menu, `promptChoice` → `Validation.getChoice` (chữ → `You must input a number.`, ngoài 1-3 → `Please choose from 1 to 3.`).
3. Chọn 1 hoặc 2: `promptUsername` / `promptPassword` → `Validation` (sai → in lỗi đề, hỏi lại đúng ô đó) → gói `AccountRequestDTO`.
4. Create: Controller → `accountRepository.addAccount(dto)`: trùng username → ném `Username [..] already exists.`; không trùng → `FileUtils.appendLine` (kiểm file có chưa, chưa có thì tạo, ghi nối cuối) → thêm vào `accountList`.
5. Login: Controller → `accountRepository.find(dto)` duyệt `accountList`; `false` → ném `Invalid user name or password`.
6. Thành công: Controller tạo `AccountResponseDTO` (câu `Create account successfully!` / `Login successful!`) → `accountView.setAccountResponse(...)` → `display()`.
7. Lỗi: `Main` bắt `Exception` và in `e.getMessage()`; chọn 3 → in `Goodbye.` và thoát.

## (d) Bảng import
| Lớp | Import | Nói với thầy |
|---|---|---|
| `Main` | Constants, Message, AccountController, AccountRequestDTO, ArrayList, Scanner, FileUtils, Validation | Main nhập, validate và đọc file lúc khởi động, rồi chỉ gọi controller. |
| `AccountController` | Message, AccountRequestDTO, AccountResponseDTO, ArrayList, AccountRepository, AccountView | Không import model; ArrayList chỉ để chuyển các dòng file xuống repository. |
| `AccountRepository` | Constants, Message, AccountRequestDTO, ArrayList, Account, FileUtils | Chỉ repository đụng model Account và ghi file khi tạo tài khoản. |
| `AccountView` | AccountResponseDTO | View chỉ biết ResponseDTO, nhận qua setter rồi in. |
| `FileUtils` | Message, BufferedReader, File, FileReader, FileWriter, IOException, ArrayList | Đọc từng dòng bằng BufferedReader; ghi nối cuối bằng `FileWriter(file, true)`. |
| `Validation` | Constants, Message | Độ dài tối thiểu lấy từ Constants, câu lỗi từ Message. |
| `Account` | Constants | Dùng `Constants.SEPARATOR` để ghép dòng `username password`. |
| `AccountRequestDTO`, `AccountResponseDTO`, `Message`, `Constants` | (không) | Chỉ chở dữ liệu / chứa hằng. |

## (e) Phím test (bắt đầu với `user.dat` có sẵn `NghiaNV1 space@123`)
| # | Gõ | Phải thấy |
|---|---|---|
| 1 | `2` → `12` → `NghiaNV1` → `12` → `space@123` | đúng màn hình đề: lỗi 5 ký tự, lỗi 6 ký tự, `Login successful!` |
| 2 | `1` → `ab cde` → `abcd` → `HoangAn` → `abc 123` → `abc123` | 2 lần lỗi 5 ký tự, 1 lần lỗi 6 ký tự, `Create account successfully!`; mở `user.dat` thấy `HoangAn abc123` ở **cuối** |
| 3 | `2` → `HoangAn` → `abc123` | `Login successful!` |
| 4 | `2` → `HoangAn` → `wrong12` | `Invalid user name or password` |
| 5 | `2` → `Nobody1` → `abc123` | `Invalid user name or password` |
| 6 | `1` → `NghiaNV1` → `another123` | `Username [NghiaNV1] already exists.` |
| 7 | menu `x`, `4`, `0` | `You must input a number.`, `Please choose from 1 to 3.` (2 lần) |
| 8 | `1` → `abcde` → ` 12345` → `123456` | password có dấu cách bị từ chối; biên 5/6 ký tự được tạo |
| 9 | `3` | `Goodbye.`; chạy lại (F6), login `HoangAn`/`abc123` vẫn được — đã nạp từ file |
| 10 | xoá `user.dat`, F6, tạo tài khoản | chạy bình thường, `user.dat` được tạo mới |

## (f) Câu thầy hay hỏi
1. **Đọc file ở đâu, ghi file ở đâu?** — Đọc 1 lần lúc khởi động trong `Main` (qua `FileUtils.readLines`), nạp vào `accountList`. Ghi trong `AccountRepository.addAccount` qua `FileUtils.appendLine` khi tạo tài khoản.
2. **"Checking existing of user.dat before inserting" ở đâu?** — `FileUtils.appendLine`: `if (!file.exists()) file.createNewFile();` rồi mới ghi.
3. **Sao ghi được vào cuối file mà không xoá dữ liệu cũ?** — `new FileWriter(file, true)`: tham số `true` = chế độ append.
4. **Login tìm ở đâu?** — Tìm trong `accountList` (Collection), không đọc lại file — đúng MARKING "Search user name and password into Collection".
5. **try-with-resources để làm gì?** — Tự đóng file sau khi đọc/ghi, kể cả khi có lỗi.
6. **Kiểm "không dấu cách" thế nào?** — `input.contains(" ")`; cùng với `input.length() < 5` (hoặc 6) thì ném lỗi của đề.
7. **Sao không có service?** — Chỉ thêm và tìm tài khoản, không có tính toán nghiệp vụ.
8. **Sao FileUtils, Validation constructor private?** — Chỉ có hàm static, không cho `new`.
