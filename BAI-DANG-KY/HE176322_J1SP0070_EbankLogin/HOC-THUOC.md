# J1.S.P0070 — Ebank Login (TienPhong Bank) — bản học thuộc

## (a) Đề tóm 5 dòng
1. Menu: `1. Vietnamese` · `2. English` · `3. Exit` → chọn ngôn ngữ (KHÔNG lặp menu, đăng nhập đúng 1 lượt).
2. Số tài khoản: đúng **10 chữ số**, sai báo lỗi và hỏi lại.
3. Mật khẩu: **8–31** ký tự, chỉ chữ/số, phải có **cả chữ và số**, sai hỏi lại.
4. Captcha 5 ký tự ngẫu nhiên (A–Z, 0–9); gõ 1 hay nhiều ký tự, đúng nếu captcha **chứa** chuỗi gõ (rỗng = sai).
5. Đổi ngôn ngữ bằng `ResourceBundle` + 2 file `Language_vi.properties` / `Language_en.properties`.

## (b) Cây file
```
src/
├── constants/Message.java            menu, câu hỏi menu, 2 lỗi menu, KHOÁ của 8 câu dịch
├── constants/Constants.java          số menu, mã ngôn ngữ vi/en, tên bundle, 2 regex, chữ captcha, độ dài 5
├── constants/Language_vi.properties  8 câu tiếng Việt (không dấu, chép đề)
├── constants/Language_en.properties  8 câu tiếng Anh (chép đề, kể cả "must is a number")
├── model/Account.java                tài khoản đã đăng nhập (số TK, mật khẩu)
├── dto/LoginRequestDTO.java          Main gửi số TK + mật khẩu vào controller
├── dto/LoginResponseDTO.java         câu kết quả controller gửi sang view
├── repository/AccountRepository.java accountList + addAccount
├── service/Ebank.java                lớp ĐỀ BẮT: setLocate, checkAccountNumber, checkPassword, generateCaptcha, checkCaptcha, login
├── controller/EbankController.java   gọi Ebank, đưa kết quả sang view
├── view/EbankView.java               in câu "Dang nhap thanh cong" / "Login successfully"
├── utils/Validation.java             getChoice (menu phải là số 1–3)
└── main/Main.java                    Scanner, menu, hỏi từng ô, hỏi lại khi sai
```

## (c) Luồng chính
1. `Main` in `MENU`, `promptChoice` → `Validation.getChoice` (sai thì in lỗi, hỏi lại).
2. Chọn 1/2 → `Main.login` → `ebankController.setLocate(new Locale("vi"/"en"))` → `Ebank` nạp đúng file `.properties`.
3. `promptAccountNumber`: in câu hỏi (`getText(KEY)`), đọc dòng → `ebankController.checkAccountNumber` → `Ebank` trả `""` (đúng) hoặc câu lỗi → in lỗi, hỏi lại.
4. `promptPassword` y như vậy với `checkPassword`.
5. `promptCaptcha`: `generateCaptcha` in 1 lần, hỏi đến khi `checkCaptcha` trả `""`.
6. Đủ dữ liệu → `LoginRequestDTO` → `ebankController.login` → `Ebank.login` → `AccountRepository.addAccount` (tạo `Account`) → trả `LoginResponseDTO`.
7. Controller `setLoginResponseDTO` + `display()` → View in câu thành công. Chương trình kết thúc.

## (d) Bảng import
| Lớp | Import | Nói với thầy |
|---|---|---|
| Main | Constants, Message, EbankController, LoginRequestDTO, Locale, Scanner, Validation | Main nhập liệu nên có Scanner; chỉ nói chuyện với controller qua DTO. |
| EbankController | LoginRequestDTO, Locale, Ebank, EbankView | Controller không import model, chỉ DTO + service + view. |
| Ebank | Constants, Message, LoginRequestDTO, LoginResponseDTO, Locale, Random, ResourceBundle, AccountRepository | Service giữ luật: regex, captcha ngẫu nhiên, chọn file ngôn ngữ. |
| AccountRepository | LoginRequestDTO, ArrayList, List, Account | Chỉ repository tạo model từ DTO và lưu vào list. |
| EbankView | LoginResponseDTO | View chỉ biết ResponseDTO để in. |
| Validation | Message | Sai thì `throw new Exception(Message.X)`. |
| Account, 2 DTO, Message, Constants | (không) | Lớp dữ liệu / hằng số. |

## (e) Phím test (theo thứ tự)
| # | Gõ | Phải thấy |
|---|---|---|
| 1 | `0` rồi `x` | `Please choose from 1 to 3.` rồi `You must input a number.` |
| 2 | `1` | `So tai khoan:  ` (2 dấu cách) |
| 3 | `abc`, `123456789`, `01234567890`, ` 0123456789` | mỗi lần `So tai khoan phai la 1 so va phai co 10 chu so` |
| 4 | `0123456789` | `Mat khau: ` |
| 5 | `12345678`, `aaaaaaaa`, `abc12345!`, 32 ký tự | mỗi lần `Mat khau phai trong khoang 8-31 ky tu va phai chua ky tu va so` |
| 6 | `123456ab` | `Captcha: XXXXX` |
| 7 | Enter trống, chữ thường `n`, `!` | `Captcha sai` |
| 8 | 1 ký tự có trong captcha | `Dang nhap thanh cong`, kết thúc |
| 9 | chạy lại, chọn `2`, làm lại 3–8 | câu tiếng Anh: `Account number:  `, `Captcha incorrect: XXXXX`, `Login successfully` |
| 10 | chạy lại, chọn `3` | kết thúc, không in gì |

## (f) Câu thầy hay hỏi
1. **Đổi ngôn ngữ thế nào?** `ResourceBundle.getBundle("constants.Language", locale)` tự chọn file `Language_vi` hay `Language_en` theo `Locale`; không có `if` tiếng Việt/tiếng Anh.
2. **Sao check trả `String`?** Đề bắt: đúng trả `""`, sai trả câu lỗi theo ngôn ngữ — Main thấy chuỗi rỗng thì đi tiếp.
3. **Sao chặn chuỗi rỗng ở captcha?** `"ABCDE".contains("")` luôn `true`, không chặn thì Enter là vào.
4. **Regex mật khẩu?** `(?=.*[A-Za-z])` có ít nhất 1 chữ, `(?=.*[0-9])` có ít nhất 1 số, `[A-Za-z0-9]{8,31}` độ dài.
5. **Sao số tài khoản là String?** Giữ số 0 đầu, và 10 chữ số vượt `int`.
6. **Sao không có vòng lặp menu?** Đề: chọn ngôn ngữ rồi đăng nhập 1 lần; chọn 3 thì thoát.
7. **Sao `Ebank` nằm ở service?** Nó là lớp đề bắt, chứa luật nghiệp vụ (kiểm tra, sinh captcha) chứ không phải CRUD.
8. **`StringBuilder` trong `generateCaptcha` có phải Builder pattern không?** Không — đó là lớp nối chuỗi có sẵn của Java; bài không dùng pattern nào.
