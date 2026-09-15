# J1.S.P0070 — Ebank Login (TienPhong Bank)

> Bài có **hai ngôn ngữ**, **regex** và **captcha ngẫu nhiên**. Chỗ thầy hay soi: đổi ngôn ngữ bằng
> `ResourceBundle` thật (không `if` tiếng Việt / tiếng Anh), regex mật khẩu đúng nghĩa "chữ **và** số",
> và cái bẫy `contains("")`.

| | |
|---|---|
| Loại / LOC | Short Assignment · 150 LOC · 3 slot |
| Project | `HE176322_J1SP0070_EbankLogin` |
| Chạy | NetBeans: **File ▸ Open Project** → **F6** |
| Lớp chạy | `main.Main` |
| File ngôn ngữ | `src/constants/Language_en.properties`, `src/constants/Language_vi.properties` (NetBeans tự chép sang `build/classes`) |
| Kiểm tự động | `python3 _tools/verify.py J1SP0070` → 6 kịch bản × 2 locale (3 của bản cũ + 3 thêm) |

---

## 1. Đề bài nói gì

- Menu: `1. Vietnamese` · `2. English` · `3. Exit`. Chọn 1 → giao diện tiếng Việt; chọn 2 → tiếng Anh; rồi **đăng nhập**.
- **Số tài khoản**: phải là số, **đúng 10 chữ số**. Sai → báo lỗi, nhập lại.
- **Mật khẩu**: **8–31** ký tự, phải có **chữ và số**. Sai → báo lỗi, nhập lại.
- **Captcha**: sinh ngẫu nhiên mỗi lần đăng nhập; người dùng gõ 1 hoặc nhiều ký tự; đúng nếu captcha **chứa** (`contains`) chuỗi gõ vào.

**Đề bắt buộc** (Guidelines):

| Thứ | Đề viết | Bài này đặt ở |
|---|---|---|
| lớp `Ebank` | *"Create the Ebank class"* | `service/Ebank` — giữ đúng tên |
| `void setLocate(Locate locate)` | Function 1 | `Ebank.setLocate(Locale locate)` — kiểu Java thật là `java.util.Locale` |
| `String checkAccountNumber(String)` | Function 2 | `Ebank` — đúng chữ ký |
| `String checkPassword(String)` | Function 3 | `Ebank` — đúng chữ ký |
| `String generateCaptcha()` | Function 4 | `utils/CaptchaUtils.generateCaptcha()` (static) — Guide cho main dùng *"CapchaUtils"* |
| `String checkCaptcha(String captchaInput, String captchaGenerate)` | Function 5 | `Ebank` — đúng chữ ký |
| 2 file `.properties` | *"En.properties and Vi.properties"* | `Language_en.properties`, `Language_vi.properties` (§9) |
| Câu chữ | màn hình tiếng Việt + tiếng Anh | chép **từng chữ**, kể cả tiếng Anh sai của đề (`must is a number`, `Captcha incorrect: H9MOA`) |

---

## 2. Kiến thức cần biết

### 2.1 `ResourceBundle` — đổi ngôn ngữ không cần `if`

```java
ResourceBundle bundle = ResourceBundle.getBundle("constants.Language", new Locale("vi"));
bundle.getString("password.prompt");   // "Mat khau: "
```

| Gọi với | Java tìm file | Kết quả |
|---|---|---|
| `new Locale("vi")` | `constants/Language_vi.properties` | tiếng Việt |
| `new Locale("en")` | `constants/Language_en.properties` | tiếng Anh |

Tên file **bắt buộc** dạng `<tên gốc>_<mã ngôn ngữ>.properties` thì `getBundle` mới tự chọn theo `Locale`.
**Cả hai** file đều phải có: nếu thiếu `Language_en`, `getBundle` sẽ lùi về **ngôn ngữ mặc định của máy** — máy
thầy để tiếng Việt thì chọn English vẫn ra tiếng Việt. (`verify.py` chạy cả locale `vi_VN` để bắt đúng lỗi này.)

**Dấu cách cuối giá trị**: prompt `So tai khoan:  ` có 2 dấu cách cuối. Trong file viết `So tai khoan: \ ` —
`\ ` là một dấu cách **nhìn thấy được**, không bị trình soạn thảo xoá mất.

### 2.2 Regex — chạy tay các ví dụ của đề

`String.matches(regex)` so **cả chuỗi** (từ đầu tới cuối).

**Số tài khoản** `[0-9]{10}`:

| Gõ | Khớp? | Vì sao |
|---|---|---|
| `1` | ✗ | 1 chữ số |
| `a` / `aaaaaaaaaa` | ✗ | không phải số |
| `123456789` | ✗ | 9 chữ số |
| `0123456789` | ✓ | đúng 10 số (giữ số 0 đầu → kiểu `String`, không `int`) |
| `abc0123456789xyz` | ✗ | `matches` bắt cả chuỗi; dùng `Pattern.find()` sẽ **lọt** |

**Mật khẩu** `(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z0-9]{8,31}`:

| Phần | Nghĩa |
|---|---|
| `(?=.*[A-Za-z])` | nhìn trước: **có ít nhất 1 chữ** |
| `(?=.*[0-9])` | nhìn trước: **có ít nhất 1 số** |
| `[A-Za-z0-9]{8,31}` | chỉ chữ/số, dài 8–31 |

| Gõ (đề) | Kết quả |
|---|---|
| `1` | ✗ ngắn |
| `12345678` | ✗ không có chữ |
| `aaaaaaaa` | ✗ không có số |
| `1111…1` (32 ký tự) | ✗ dài quá **và** không có chữ |
| `123456ab` | ✓ |

→ "alphanumeric" của đề nghĩa là **chữ VÀ số** — chính ví dụ `12345678`, `aaaaaaaa` bị từ chối nói lên điều đó.

### 2.3 Captcha

- `CaptchaUtils.generateCaptcha()`: 5 lần `random.nextInt(36)` → vị trí trong `"A…Z0…9"` → nối ký tự.
- `checkCaptcha`: `captchaGenerate.contains(captchaInput)`. **Bẫy**: `"H9MOA".contains("")` là **true** → nhấn Enter là vào. Bài chặn chuỗi rỗng **trước**.
- Chữ thường `h` **không** khớp `H` (`contains` phân biệt hoa thường).

---

## 3. Thiết kế

```
src/
├── constants/  Message.java             menu + KHOÁ của các câu dịch
│               Constants.java           số menu, mã ngôn ngữ, tên bundle, 2 regex, bảng chữ captcha
│               Language_en.properties   câu tiếng Anh
│               Language_vi.properties   câu tiếng Việt
├── model/      Account                  tài khoản đã đăng nhập (JavaBean)
├── dto/        LoginRequestDTO          locale, số TK, mật khẩu, captcha sinh, captcha gõ (main ──► controller)
├── service/    Ebank                    lớp đề bắt: setLocate + 3 hàm check + login
├── controller/ EbankController          Facade: Ebank ↔ view
├── view/       EbankView                showPrompt (print) · showMessage (println)
├── utils/      Validation (getChoice)   CaptchaUtils (generateCaptcha)
└── main/       Main                     menu + hộp thoại đăng nhập + Scanner
```

| Câu hỏi thiết kế | Trả lời |
|---|---|
| Sao prompt đăng nhập do **view** in, không phải main? | Câu chữ phụ thuộc ngôn ngữ, mà chỉ `Ebank` giữ ngôn ngữ. Giống **trang JSP** trong MVC-JSP: view hiển thị câu đã dịch (như thẻ `fmt:message`). Main chỉ in menu (tiếng Anh cố định). |
| Sao 3 hàm check không nằm trong `Validation`? | Kết quả của chúng là **câu lỗi theo ngôn ngữ đã chọn** — cần `ResourceBundle`, tức cần trạng thái; `Validation` là static, không giữ trạng thái. Đề cũng bắt chúng thuộc lớp `Ebank`. |
| Sao không có ResponseDTO? | Mọi kết quả của bài là **một dòng chữ** (lỗi, captcha, "đăng nhập thành công") → `showMessage(String)` như P0055; DTO một trường là thừa. |
| Sao không có repository? | Không có danh sách tài khoản nào được lưu — đề chỉ kiểm **định dạng**. |

**Luồng đăng nhập:**

```
Main: menu → choice 1/2 → dto.setLocale(new Locale("vi"/"en")) → controller.setLocate(dto) → Ebank.setLocate
Main.inputAccountNumber (lặp): controller.showPrompt(KEY_ACCOUNT_PROMPT) → đọc → controller.checkAccountNumber(dto)
                                  └─ Ebank.checkAccountNumber → "" (hợp lệ) | câu lỗi → controller throw → Main in, hỏi lại
Main.inputPassword (lặp):      như trên với checkPassword
Main: dto.setCaptchaGenerate(CaptchaUtils.generateCaptcha()) → controller.showCaptcha(dto)
Main (lặp): showPrompt(KEY_CAPTCHA_PROMPT) → đọc → controller.login(dto)
                                  ├─ Ebank.checkCaptcha → lỗi → throw → Main in "Captcha sai", hỏi lại
                                  └─ Ebank.login(dto) (tạo Account) → view "Dang nhap thanh cong"
```

### 3.1 Design Pattern

| Pattern | 4 yếu tố GoF |
|---|---|
| **Strategy** (Behavioral) | **Problem**: mọi câu chữ đổi theo ngôn ngữ; viết `if (vi) … else …` ở mỗi câu thì thêm ngôn ngữ thứ ba phải sửa khắp nơi. **Solution**: `ResourceBundle` (lớp trừu tượng của JDK) = **Strategy** "biến khoá thành chữ"; hai bundle nạp từ `Language_en`/`Language_vi` = **ConcreteStrategy**; `Ebank` = **Context**, luôn gọi `bundle.getString(key)` mà không biết ngôn ngữ; `setLocate` = đổi strategy. **Consequences**: ✅ thêm tiếng Nhật = thêm **1 file** `Language_ja.properties` + 1 dòng menu, không sửa hàm check nào (OCP); ❌ sai tên khoá chỉ lộ ra lúc chạy (`MissingResourceException`) — nên mọi khoá khai báo **một lần** trong `Message`. |
| **Factory Method** (của JDK) | `ResourceBundle.getBundle(tên, locale)` là hàm **tạo đúng đối tượng** theo `Locale` — nơi gọi không `new` bundle nào, không biết lớp cụ thể là `PropertyResourceBundle`. |
| **Facade** | `EbankController` — Main chỉ thấy `setLocate / showPrompt / checkAccountNumber / checkPassword / showCaptcha / login`, không biết `Ebank`, `ResourceBundle`, view. |

---

## 4. Code từng bước

> Thầy: *"code cái **model trước rồi đến data**"*.

| Bước | File | Việc |
|---|---|---|
| 1 | `model/Account.java` | 2 field `private` + ctor rỗng + ctor đủ + get/set + `toString` (không in mật khẩu) |
| 2 | `dto/LoginRequestDTO.java` | 5 field + get/set |
| 3 | `constants/Language_en.properties`, `Language_vi.properties` | 8 khoá, **chép chữ đề** |
| 4 | `constants/Message.java`, `Constants.java` | menu, 8 khoá; regex, mã ngôn ngữ, tên bundle |
| 5 | `service/Ebank.java` | `setLocate`, `getText`, `checkAccountNumber`, `checkPassword`, `checkCaptcha`, `login` |
| 6 | `utils/CaptchaUtils.java`, `Validation.java` | `generateCaptcha`, `getChoice` |
| 7 | `view/EbankView.java`, `controller/EbankController.java` | |
| 8 | `main/Main.java` | `switch` chọn ngôn ngữ; 3 vòng hỏi lại |

**Bẫy hay gặp:**

1. Đặt file `.properties` sai thư mục → `MissingResourceException`. Tên gốc `constants.Language` ⇒ file phải ở `src/constants/`.
2. Thiếu `Language_en.properties` (chỉ có file gốc) → máy tiếng Việt chọn English vẫn ra tiếng Việt.
3. Regex mật khẩu `[A-Za-z0-9]{8,31}` (thiếu 2 lookahead) → `12345678` lọt.
4. `{8,}` thay `{8,31}` → dòng 32 số `1` của đề… vẫn bị chặn nhờ lookahead chữ, nhưng `a` ×31 + `1` (32 ký tự) sẽ lọt.
5. Quên chặn chuỗi rỗng ở captcha → Enter là đăng nhập được.

---

## 5. Test trước khi gọi thầy

| # | Gõ | Phải thấy |
|---|---|---|
| 1 | `1`, rồi số TK `1`, `a`, `aaaaaaaaaa`, `123456789` | mỗi lần `So tai khoan phai la 1 so va phai co 10 chu so` |
| 2 | `0123456789` | chuyển sang `Mat khau: ` |
| 3 | mật khẩu `1`, `12345678`, `aaaaaaaa`, 32 số `1` | mỗi lần `Mat khau phai trong khoang 8-31 ky tu va phai chua ky tu va so` |
| 4 | `123456ab` | `Captcha: XXXXX` (5 ký tự A–Z/0–9) |
| 5 | Enter (trống) | `Captcha sai` |
| 6 | chữ thường, `!`, cả chuỗi `ABC…789` | `Captcha sai` |
| 7 | 1 ký tự có trong captcha (hoặc 2–3 ký tự liền nhau của nó) | `Dang nhap thanh cong`, chương trình kết thúc |
| 8 | lặp #1–#7 với `2` | câu tiếng Anh: `Account number must is a number and must have 10 digits`, `Password must be …`, `Captcha incorrect: XXXXX`, `Enter a Captcha incorrect characters: `, `Captcha incorrect`, `Login successfully` |
| 9 | số TK trống, `01234567890` (11), `012345678a`, ` 0123456789` (dấu cách đầu), `abc0123456789xyz` | đều báo lỗi |
| 10 | mật khẩu `abc1234` (7), `abc12345!`, `abc 12345`, `a`×31+`1` (32) | đều báo lỗi; `a`×30+`1` (31) được nhận |
| 11 | menu `0`, `x` | `Please choose from 1 to 3.`, `You must input a number.` |
| 12 | menu `3` | kết thúc, không in gì thêm |

---

## 6. Debug

| Việc | Cách làm |
|---|---|
| Breakpoint | dòng `bundle = ResourceBundle.getBundle(...)` trong `Ebank.setLocate` |
| Chạy | **Ctrl+F5**, chọn `1` |
| Quan sát | **Variables**: `locate` = `vi`; F8 xong mở `bundle` → thấy các khoá/giá trị tiếng Việt |
| Captcha | breakpoint trong `Ebank.checkCaptcha`: xem `captchaInput`, `captchaGenerate`; gõ Enter → thấy nhánh `isEmpty()` trả lỗi |
| Regex | breakpoint trong `checkPassword`, gõ `12345678` → `matches` trả `false` |

---

## 7. Câu hỏi thầy hay hỏi

| Câu hỏi | Trả lời mẫu |
|---|---|
| 4 tính chất OOP ở đâu? | **Đóng gói**: field `private` + get/set trong `Account`, `LoginRequestDTO`; `bundle` là `private` trong `Ebank`, chỉ đổi qua `setLocate`. **Kế thừa**: mọi lớp `extends Object`, em ghi đè `toString()` trong `Account`; `PropertyResourceBundle extends ResourceBundle`. **Đa hình**: `bundle.getString()` gọi trên biến kiểu `ResourceBundle` — chạy bản của bundle tiếng Việt hay tiếng Anh tuỳ đối tượng thật. **Trừu tượng**: `Main` gọi `controller.checkPassword(dto)` không biết có regex hay bundle. |
| Sao `checkAccountNumber` trả `String` mà không `boolean`? | Đề bắt: trả **câu thông báo**. Hợp lệ thì trả `""` (`Constants.VALID`) — **không trả `null`** để nơi gọi `error.isEmpty()` không bao giờ bị `NullPointerException`. |
| `setLocate` trả `void`? | Nó **đổi trạng thái** (bundle) của `Ebank`, không có kết quả gì để trả. |
| Sao `generateCaptcha` là static trong utils, còn check không static? | Sinh captcha không cần dữ liệu đối tượng nào → static đúng chỗ (Guide cho main dùng *CapchaUtils*). Check cần `bundle` (trạng thái của `Ebank`) → phải là hàm đối tượng. **Bỏ static** ở `generateCaptcha` → `CaptchaUtils.generateCaptcha()` lỗi biên dịch; phải bỏ `private` constructor, `new CaptchaUtils()` trong main rồi gọi qua đối tượng. |
| Access modifier? | Field đều `private`. Hàm `public` của `Ebank` đều được `EbankController` gọi. Constructor của `Message`, `Constants`, `Validation`, `CaptchaUtils` `private`. Hàm trong `Main` `private static` (Guide: *"cấm static với biến, có thể dùng với hàm"*), Scanner là biến cục bộ. |
| Sao số tài khoản là `String`? | Giữ số `0` đầu (`0123456789`) và không bị tràn `int` (10 chữ số > 2.147.483.647). |
| Main gọi controller nhiều lần — trái luật "1 lần"? | Đề (Function 6) bắt **kiểm từng ô ngay khi gõ** và **hỏi lại tại chỗ**; câu lỗi theo ngôn ngữ chỉ `Ebank` biết. Mỗi lần gọi là **một bước kiểm** như `checkExistDoctor` ở P0055; việc đăng nhập (`login`) vẫn gọi đúng 1 lần khi captcha đúng. |
| `matches` khác `find`? | `matches` so **cả chuỗi**; `find` tìm **một đoạn** khớp — `abc0123456789xyz` sẽ lọt với `find`. |
| Captcha dùng `contains` có an toàn không? | Không — 1 ký tự đúng là qua. Em làm vì **đề bắt `contains`**, và em chặn thêm chuỗi rỗng. Thực tế phải so **cả chuỗi**. |
| SOLID? | **S**: `Ebank` giữ luật + ngôn ngữ, `CaptchaUtils` sinh captcha, view in, `.properties` giữ chữ. **O**: ngôn ngữ mới = file mới (§3.1). **L**: bundle nào cũng thay được cho nhau trong `Ebank`. **D**: `Ebank` phụ thuộc lớp trừu tượng `ResourceBundle`, không phụ thuộc file cụ thể. |

---

## 8. Thầy đổi yêu cầu tại chỗ

| Thầy bảo | Sửa | Không đụng |
|---|---|---|
| Thêm tiếng Nhật | thêm `Language_ja.properties`; `Message.MENU` + `Constants` (mã `ja`, số menu); 1 `case` trong `Main` | `Ebank`, controller, view |
| Captcha 6 ký tự | `Constants.CAPTCHA_LENGTH` | mọi file khác |
| Mật khẩu phải có ký tự đặc biệt | `Constants.PASSWORD_REGEX` thêm `(?=.*[^A-Za-z0-9])` và bỏ giới hạn `[A-Za-z0-9]`; câu lỗi trong 2 file `.properties` | `Ebank` |
| Captcha phải gõ **đủ** | `Ebank.checkCaptcha`: `captchaGenerate.equals(captchaInput)` | các lớp khác |
| Đổi câu lỗi | chỉ 2 file `.properties` | mã Java |
| In số tài khoản sau khi đăng nhập | `Ebank` thêm getter `account`; controller in qua view | `Main` |

---

## 9. Chỗ khác với đề / bản cũ

| Chỗ | Đề / bản cũ | Bài này | Lý do |
|---|---|---|---|
| Tên file ngôn ngữ | Đề: `En.properties`, `Vi.properties` | `Language_en.properties`, `Language_vi.properties` | `getBundle(tên, Locale)` chỉ tìm được file dạng `<tên>_<mã>`; Guidelines cũng gọi là *"Language.properties"* |
| Bản cũ | `ListResourceBundle` (lớp Java) | file `.properties` thật | đề bắt `.properties`; NetBeans chép chúng sang `build/classes` |
| `setLocate(Locate)` | kiểu `Locate` không tồn tại | `setLocate(Locale locate)` | giữ tên hàm (sai chính tả) của đề, kiểu Java thật |
| `generateCaptcha` | đề: hàm của `Ebank` | `utils/CaptchaUtils` (static) | Guide cho main dùng *CapchaUtils*; không dùng trạng thái |
| Tên lớp | Guide viết `CapchaUtils` | `CaptchaUtils` | đúng chính tả tiếng Anh |
| Sau khi đăng nhập | đề không nói | in `Dang nhap thanh cong` / `Login successfully` rồi **kết thúc** | giống bản cũ; câu này **không có trong đề** |
| Tiếng Anh sai của đề | `must is a number`, `Captcha incorrect: H9MOA` | giữ nguyên | thầy so màn hình với đề |
| Kiến trúc | `bo/Ebank`, Scanner trong `Validator`, `ui` | MVC Guide, Scanner chỉ ở `main` | luật thầy |
