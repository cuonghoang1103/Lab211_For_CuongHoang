# J1.S.P0072 — Login dùng MD5 — bản học thuộc

## (a) Đề tóm 5 dòng
1. Menu lặp: `1. Add User` · `2. Login` · `3) Exit`.
2. Add: Account, Password, Name, Phone (10–11 số), Email (đúng dạng), Address, DOB (`dd/MM/yyyy`); username không trùng.
3. Mật khẩu **băm MD5** rồi mới lưu.
4. Login đúng → `Wellcome`, `Hello <username>`, hỏi `Y/N` đổi mật khẩu; sai → `Login fail.`
5. Đổi mật khẩu: Old password đúng, new không rỗng, renew khớp new.

## (b) Cây file
```
src/
├── constants/Message.java             mọi câu chữ: menu, câu hỏi, lỗi (sai gì + nhập thế nào), kết quả
├── constants/Constants.java           số menu, định dạng ngày, regex phone/email, "MD5", "Y"
├── model/Account.java                 tài khoản: id + 7 thuộc tính, password là chuỗi băm
├── dto/AccountRequestDTO.java         Main gửi dữ liệu (mật khẩu đã băm) vào controller
├── dto/AccountResponseDTO.java        câu kết quả controller gửi sang view
├── repository/AccountRepository.java  accountList: addAccount, login, findUsername, findName, changePassword
├── controller/AccountController.java  gọi repository, đưa kết quả sang view
├── view/AccountView.java              displayMessage (xuống dòng), displayWelcome (không xuống dòng vì kết bằng Y/N:)
├── utils/Validation.java              getChoice, getString, getPhone, getEmail, getDate, checkNewPassword
├── utils/MD5Utils.java                hash(text) → 32 ký tự hex
└── main/Main.java                     Scanner, menu, hỏi từng ô (sai hỏi lại đúng ô đó), băm MD5
```
Không có `service` vì bài chỉ CRUD, không có tính toán nghiệp vụ.

## (c) Luồng chính
1. `Main` in `MENU`, `promptChoice` → `Validation.getChoice`.
2. Add: `promptString/promptPhone/promptEmail/promptDate` → `Validation.*` (sai: `throw`, Main in lỗi, hỏi lại ô đó) → `MD5Utils.hash(password)` → `AccountRequestDTO`.
3. `accountController.addAccount(dto)` → `AccountRepository.addAccount` (trùng username thì `throw`; không thì `new Account(++lastId, ...)`) → trả id.
4. Controller ghép `Account [..] has been added with id ..` vào `AccountResponseDTO` → `AccountView.displayMessage()`.
5. Login: đọc Account/Password → băm → `accountController.login` → `repository.login(username, hash)` trả `Boolean`; sai `throw LOGIN_FAIL` (Main in, về menu); đúng → `displayWelcome()`.
6. Main đọc `Y/N`; `Y` → Old password (băm), `promptNewPassword` (Validation.checkNewPassword) → băm → `changePassword` → repository so hash cũ, lưu hash mới → `Password has been changed.`

## (d) Bảng import
| Lớp | Import | Nói với thầy |
|---|---|---|
| Main | Constants, Message, AccountController, AccountRequestDTO, Date, Scanner, MD5Utils, Validation | Nhập, validate và băm đều ở Main; chỉ gửi DTO cho controller. |
| AccountController | Message, AccountRequestDTO, AccountResponseDTO, AccountRepository, AccountView | Không import model; ghép câu kết quả từ Message. |
| AccountRepository | Message, AccountRequestDTO, ArrayList, List, Account | Chỉ repository tạo `Account` từ DTO, lưu và so chuỗi băm. |
| AccountView | AccountResponseDTO | View chỉ in ResponseDTO. |
| Validation | Constants, Message, SimpleDateFormat, Date | Sai thì `throw new Exception(Message.X)`. |
| MD5Utils | Constants, StandardCharsets, MessageDigest | `MessageDigest.getInstance("MD5")`, mỗi byte → `%02x`. |
| Account, AccountRequestDTO | Date | ngày sinh kiểu `Date`. |

## (e) Phím test (theo thứ tự, một lần chạy)
| # | Gõ | Phải thấy |
|---|---|---|
| 1 | `abc`, `4` | `You must input a number.`, `Please choose from 1 to 3.` |
| 2 | `1`, Account trống | `Username cannot be empty. ...` rồi hỏi lại `Account:` |
| 3 | `NghiaNV`, Password trống rồi `nghia`, Name trống rồi `SkyLine` | lỗi rồi hỏi lại đúng ô |
| 4 | Phone trống, `098866688`, `0988a66888`, rồi `0988666888` | lỗi phone, hỏi lại |
| 5 | Email `nghianv.t.com`, `nghianv@t`, rồi `nghianv@t.com`; Address `Ha Noi` | lỗi email, hỏi lại |
| 6 | DOB `31/02/2003`, `1/2/2015`, rồi `26/06/2016` | lỗi ngày, rồi `Account [NghiaNV] has been added with id 1.` |
| 7 | `1`, `nghianv` + 6 ô đúng | `Username [NghiaNV] already exists. ...` |
| 8 | `2`, `Ghost` / `nghia` | `Login fail. ...` |
| 9 | `2`, `NghiaNV` / `nghia`, `Y`, old `wrong`, new `abc` x2 | `Old password is not correct. ...` |
| 10 | `2`, `NghiaNV`/`nghia`, `y`, old `nghia`, new trống; rồi `abc`/`abd`; rồi `nghia2`/`nghia2` | lỗi rỗng, lỗi không khớp, `Password has been changed.` |
| 11 | `2`, `NghiaNV`/`nghia` | `Login fail. ...` (mật khẩu cũ hết dùng) |
| 12 | `2`, `NghiaNV`/`nghia2`, `N` | màn Wellcome, về menu |
| 13 | `3` | `Goodbye.` |

## (f) Câu thầy hay hỏi
1. **MD5 là mã hoá hay băm?** Băm một chiều, không giải mã được; login thì băm chuỗi vừa gõ rồi so 2 chuỗi băm.
2. **Sao không `new String(digestArray)`?** 16 byte không phải chữ → ra ký tự rác; phải đổi từng byte sang 2 chữ số hex `%02x`.
3. **Băm ở đâu?** Ở Main, trước khi đặt vào DTO — mật khẩu thô không ra khỏi Main, repository chỉ lưu/so hash.
4. **Sao không có service?** Chỉ thêm/tìm/sửa (CRUD), không tính toán → Controller gọi thẳng Repository.
5. **Sao `login` trả `Boolean`?** Đề bắt chữ ký `Boolean login(String, String)`; em không bao giờ trả `null` nên tự unboxing an toàn.
6. **Sao kiểm "password rỗng" trước khi băm?** MD5 của chuỗi rỗng vẫn là 32 ký tự, băm xong thì không bắt được rỗng nữa.
7. **Sao sai username và sai mật khẩu chung một câu?** Báo riêng thì người lạ biết username nào có thật.
8. **Có Builder không?** Không. `Account` tạo bằng constructor đủ tham số trong repository; `StringBuilder` trong MD5Utils chỉ là lớp nối chuỗi của Java.
