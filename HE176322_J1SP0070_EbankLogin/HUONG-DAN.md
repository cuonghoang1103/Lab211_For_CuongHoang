# J1.S.P0070 — Ebank Login (TienPhong Bank)

> Bài có **hai ngôn ngữ**, **regex** và **captcha ngẫu nhiên**. Chỗ thầy hay soi: đổi ngôn ngữ bằng
> `ResourceBundle` thật (không `if` tiếng Việt / tiếng Anh), regex mật khẩu đúng nghĩa "chữ **và** số",
> cái bẫy `contains("")` — và từ 21/09/2026, **tờ checklist giấy 25 mục** của thầy (mục 9).

| | |
|---|---|
| Loại / LOC | Short Assignment · 150 LOC · 3 slot |
| Project | `HE176322_J1SP0070_EbankLogin` |
| Chạy | NetBeans: **File ▸ Open Project** → **F6** |
| Lớp chạy | `main.Main` |
| File ngôn ngữ | `src/constants/Language_en.properties`, `src/constants/Language_vi.properties` (NetBeans tự chép sang `build/classes`) |
| Kiểm tự động | `python3 _tools/verify.py J1SP0070` → 6 kịch bản × 2 locale · tờ checklist 25 mục: 0 vi phạm |

---

## 1. Đề bài nói gì

- Màn hình mở đầu: `-------Login Program-------`, rồi `1. Vietnamese` · `2. English` · `3. Exit`, rồi hỏi
  `Please choice one option: `. Chọn 1 → giao diện tiếng Việt; chọn 2 → tiếng Anh; rồi **đăng nhập**.
- **Số tài khoản**: phải là số, **đúng 10 chữ số**. Sai → báo lỗi, nhập lại. Câu hỏi của đề có **2 dấu cách**:
  `So tai khoan:  0123456789`.
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
| 2 file `.properties` | *"En.properties and Vi.properties"* | `Language_en.properties`, `Language_vi.properties` (§10) |
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
Mã tiếng Việt là **`vi`** (mã ngôn ngữ) — **không phải `vn`** (mã quốc gia). Đặt `Language_vn.properties` thì chọn 1
vẫn ra tiếng Anh hoặc chết `MissingResourceException`, và không có lỗi nào chỉ ra tệp sai tên.
**Cả hai** file đều phải có: nếu thiếu `Language_en`, `getBundle` sẽ lùi về **ngôn ngữ mặc định của máy** — máy
thầy để tiếng Việt thì chọn English vẫn ra tiếng Việt. (`verify.py` chạy cả locale `vi_VN` để bắt đúng lỗi này.)

**Dấu cách cuối giá trị**: prompt `So tai khoan:  ` có 2 dấu cách cuối. Trong file viết `So tai khoan: \ ` —
`\ ` là một dấu cách **nhìn thấy được**, không bị trình soạn thảo xoá mất.

**Tạo file `.properties` trong NetBeans** (menu chuột phải thường KHÔNG hiện sẵn loại này):

1. Chuột phải package `constants` → **New ▸ Other…** (`Ctrl+N`) → Categories **Other** → File Types
   **Properties File** → Next → File Name `Language_vi` (không gõ đuôi) → Finish. Lặp lại với `Language_en`.
2. Không thấy "Properties File": cũng trong **Other**, chọn **Empty File**, gõ tên **đủ đuôi** `Language_vi.properties`.
3. Cách chắc ăn: File Explorer → `src\constants\` → Notepad, **Save as type: All Files**, tên `Language_vi.properties`
   (không chọn All Files thì Windows gắn thêm `.txt`).

Kiểm: **Shift+F11** (Clean and Build) → tab **Files** → `build/classes/constants/` phải có đủ 2 file.

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

- `CaptchaUtils.generateCaptcha()`: 5 lần `random.nextInt(CAPTCHA_ALPHABET.length())` → vị trí trong `"A…Z0…9"` →
  `charAt` ra `char` → nối bằng `StringBuilder` (mục 3.8: không `String +=`).
- `Validation.isCaptchaMatch(captchaInput, captchaGenerate)`: `captchaGenerate.contains(captchaInput)`.
  **Bẫy**: `"H9MOA".contains("")` là **true** → nhấn Enter là vào. Hàm chặn chuỗi rỗng **trước**:
  `if ((captchaInput == null) || captchaInput.isEmpty())`. `Ebank.checkCaptcha` dùng lại đúng hàm này.
- Chữ thường `h` **không** khớp `H` (`contains` phân biệt hoa thường — có chủ đích, comment ghi rõ).

---

## 3. Thiết kế

```
src/
├── constants/  Message.java             menu + câu hỏi chọn + 2 câu lỗi menu + KHOÁ của 8 câu dịch
│               Constants.java           số menu, mã ngôn ngữ, tên bundle, 2 regex, bảng chữ captcha, VALID
│               Language_en.properties   câu tiếng Anh
│               Language_vi.properties   câu tiếng Việt
├── model/      Account                  tài khoản đã đăng nhập (JavaBean) — chỉ repository tạo
├── dto/        LoginRequestDTO          locale, số TK, mật khẩu, captcha sinh, captcha gõ (main ──► controller)
│               LoginResponseDTO         message: dòng View in cuối luồng (controller ──► view)
├── repository/ AccountRepository        ArrayList<Account> accountList + addAccount (tờ giấy: BẮT BUỘC)
├── service/    Ebank                    lớp đề bắt: setLocate + 3 hàm check + login (chốt cuối, lưu tài khoản)
├── controller/ EbankController          Facade: 1 hàm login — Ebank quyết, view in 1 lần
├── view/       EbankView                field LoginResponseDTO + setResponseDTO + display() KHÔNG tham số
├── utils/      Validation               getChoice · isMatch · isCaptchaMatch
│               CaptchaUtils             generateCaptcha
│               LanguageUtils            getText(locale, key) — đọc file .properties
└── main/       Main                     final + private ctor; Scanner; nhập + validate + hỏi lại tại chỗ
```

| Câu hỏi thiết kế | Trả lời |
|---|---|
| Sao có repository? | Tờ checklist mục 1.1: *"Bắt buộc phải có repository"*. `AccountRepository` giữ dữ liệu của bài — các tài khoản đã đăng nhập — và chỉ CRUD đơn giản. Tầng: Controller → Service (`Ebank`) → Repository → Model (`Account`). |
| Validate ở đâu? | Ở `Main`, qua `utils/Validation` — mục 1.1: *"Toàn bộ việc nhập dữ liệu/Validate/đọc từ file/mã hóa thực hiện ở Main"*. Main hỏi lại **ngay tại chỗ**; câu nhắc và câu lỗi theo ngôn ngữ lấy từ `.properties` qua `LanguageUtils`. |
| Thế 3 hàm check của đề dùng làm gì? | Nằm trong `Ebank` như đề bắt, do `controller.login` gọi làm **chốt cuối** trước khi lưu tài khoản. Chúng dùng chung regex trong `Constants` và `Validation.isMatch` / `isCaptchaMatch` → luật chỉ nằm **một** chỗ. |
| Sao Main chỉ gọi controller 1 lần? | Guide: *"Mỗi workflow chính chỉ gọi vào controller 1 lần duy nhất"*; mỗi `case` của `switch` là một luồng: nhập đủ vào DTO rồi mới `controller.login(requestDTO)`. |
| Sao View không có tham số? | Tờ giấy: View *"không nên truyền qua param mà phải nhận qua thuộc tính (Nên để ResponseDTO)"*, rendering *"chỉ được gọi 1 lần cho 1 luồng"*. Controller `setResponseDTO` rồi `display()` — như `setDoctorMap(...)` + `display()` trong mẫu P0055 của thầy. |
| Sao câu nhắc nhập do Main in? | View chỉ render **một lần** mỗi luồng (kết quả); câu nhắc là việc nhập liệu — mẫu Main của thầy cũng tự in câu nhắc và `e.getMessage()`. |

**Luồng đăng nhập:**

```
Main: println(MENU) → inputChoice (Validation.getChoice) → case 1/2
Main: requestDTO.setLocale(new Locale("vi"/"en"))
Main.inputAccountNumber (lặp): print(LanguageUtils.getText(locale, KEY_ACCOUNT_PROMPT)) → đọc
                                → Validation.isMatch(line, ACCOUNT_REGEX) ? trả về : in câu lỗi, hỏi lại
Main.inputPassword (lặp):       như trên với PASSWORD_REGEX
Main: setCaptchaGenerate(CaptchaUtils.generateCaptcha()) → in nhãn + captcha (2 lệnh)
Main.inputCaptcha (lặp):        Validation.isCaptchaMatch ? trả về : in "Captcha sai", hỏi lại
Main: controller.login(requestDTO)                                   ◄── LẦN GỌI DUY NHẤT
  controller: responseDTO = ebank.login(requestDTO)
    Ebank: setLocate(locale) → findFirstError (3 hàm check của đề) → rỗng
    Ebank: accountRepository.addAccount(requestDTO)  → new Account(...) → accountList.add
    Ebank: responseDTO.setMessage("Dang nhap thanh cong")
  controller: ebankView.setResponseDTO(responseDTO); ebankView.display()   ◄── RENDER 1 LẦN
```

### 3.1 Design Pattern

| Pattern | 4 yếu tố GoF |
|---|---|
| **Strategy** (Behavioral) | **Problem**: mọi câu chữ đổi theo ngôn ngữ; viết `if (vi) … else …` ở mỗi câu thì thêm ngôn ngữ thứ ba phải sửa khắp nơi. **Solution**: `ResourceBundle` (lớp trừu tượng của JDK) = **Strategy** "biến khoá thành chữ"; hai bundle nạp từ `Language_en`/`Language_vi` = **ConcreteStrategy**; `LanguageUtils.getText(locale, key)` luôn gọi `bundle.getString(key)` mà không biết ngôn ngữ; `Locale` trong DTO / `Ebank.setLocate` = chọn strategy. **Consequences**: ✅ thêm tiếng Nhật = thêm **1 file** `Language_ja.properties` + 1 dòng menu, không sửa hàm check nào (OCP); ❌ sai tên khoá chỉ lộ ra lúc chạy (`MissingResourceException`) — nên mọi khoá khai báo **một lần** trong `Message`. |
| **Factory Method** (của JDK) | `ResourceBundle.getBundle(tên, locale)` **tạo đúng đối tượng** theo `Locale` — nơi gọi không `new` bundle nào, không biết lớp cụ thể là `PropertyResourceBundle`. |
| **Facade** | `EbankController` — Main chỉ thấy **một** hàm `login(requestDTO)`, không biết `Ebank`, `AccountRepository`, view. |

`Validation`, `CaptchaUtils`, `LanguageUtils` **không phải Singleton**: chúng là *Utility Class* (`public final class`
+ constructor `private` + toàn static) — không có đối tượng nào; Singleton có đúng một đối tượng + `getInstance()`.

---

## 4. Code từng bước

> Thầy: *"code cái **model trước rồi đến data**"*.

| Bước | File | Việc |
|---|---|---|
| 1 | `model/Account.java` | 2 field `private` + ctor rỗng + ctor đủ + get/set (**comment từng cái**) + `toString()` trả `accountNumber` (không in mật khẩu) |
| 2 | `dto/LoginRequestDTO.java`, `LoginResponseDTO.java` | 5 field + get/set; 1 field `message` + get/set |
| 3 | `constants/Language_en.properties`, `Language_vi.properties` | 8 khoá, **chép chữ đề**, `\ ` cuối các câu nhắc (§2.1 cách tạo file) |
| 4 | `constants/Message.java`, `Constants.java` | menu có dòng `-------Login Program-------`, `INPUT_CHOICE`, 8 khoá; regex, mã ngôn ngữ, tên bundle, `VALID` |
| 5 | `repository/AccountRepository.java` | `ArrayList<Account> accountList` + `addAccount(requestDTO)` |
| 6 | `utils/Validation.java`, `CaptchaUtils.java`, `LanguageUtils.java` | `getChoice`, `isMatch`, `isCaptchaMatch`; `generateCaptcha`; `getText` |
| 7 | `service/Ebank.java` | field `locale` + `accountRepository`; `setLocate`, 3 hàm check, `login`, `findFirstError` |
| 8 | `view/EbankView.java`, `controller/EbankController.java` | View: field + setter + `display()`; controller: `login` gọi Ebank rồi view **1 lần** |
| 9 | `main/Main.java` | `final` + ctor `private`; menu; `switch`; `inputChoice`, `inputLogin`, 3 hàm nhập hỏi lại tại chỗ |
| 10 | Alt+Shift+F rồi **tự soát 25 mục** (§9) | dòng trống trước mọi comment, khai báo đầu block, ngoặc quanh so sánh — máy không làm hộ |

**Bẫy hay gặp:**

1. Đặt file `.properties` sai thư mục → `MissingResourceException`. Tên gốc `constants.Language` ⇒ file phải ở `src/constants/`.
2. Đặt tên `Language_vn` thay vì `Language_vi` (§2.1).
3. Thiếu `Language_en.properties` (chỉ có file gốc) → máy tiếng Việt chọn English vẫn ra tiếng Việt.
4. Regex mật khẩu `[A-Za-z0-9]{8,31}` (thiếu 2 lookahead) → `12345678` lọt.
5. `{8,}` thay `{8,31}` → `a` ×31 + `1` (32 ký tự) sẽ lọt.
6. Quên chặn chuỗi rỗng ở captcha → Enter là đăng nhập được.
7. `sc.nextLine()` đặt **trong** `try` có `catch (Exception e)` → hết input thì vòng hỏi lại quay vô hạn. Đọc dòng **ngoài** `try`.

---

## 5. Test trước khi gọi thầy

| # | Gõ | Phải thấy |
|---|---|---|
| 1 | `1` | menu có `-------Login Program-------` và `Please choice one option: `; rồi `So tai khoan:  ` (2 dấu cách) |
| 2 | số TK `1`, `a`, `aaaaaaaaaa`, `123456789` | mỗi lần `So tai khoan phai la 1 so va phai co 10 chu so` |
| 3 | `0123456789` | chuyển sang `Mat khau: ` |
| 4 | mật khẩu `1`, `12345678`, `aaaaaaaa`, 32 số `1` | mỗi lần `Mat khau phai trong khoang 8-31 ky tu va phai chua ky tu va so` |
| 5 | `123456ab` | `Captcha: XXXXX` (5 ký tự A–Z/0–9) |
| 6 | Enter (trống) | `Captcha sai` |
| 7 | chữ thường, `!`, cả chuỗi `ABC…789` | `Captcha sai` |
| 8 | 1 ký tự có trong captcha (hoặc 2–3 ký tự liền nhau của nó) | `Dang nhap thanh cong`, chương trình kết thúc |
| 9 | lặp #1–#8 với `2` | câu tiếng Anh: `Account number:  `, `Account number must is a number and must have 10 digits`, `Password must be …`, `Captcha incorrect: XXXXX`, `Enter a Captcha incorrect characters: `, `Captcha incorrect`, `Login successfully` |
| 10 | số TK trống, `01234567890` (11), `012345678a`, ` 0123456789` (dấu cách đầu), `abc0123456789xyz` | đều báo lỗi |
| 11 | mật khẩu `abc1234` (7), `abc12345!`, `abc 12345`, `a`×31+`1` (32) | đều báo lỗi; `a`×30+`1` (31) được nhận |
| 12 | menu `0`, `9`, `x` | `Please choose from 1 to 3.`, `Please choose from 1 to 3.`, `You must input a number.` |
| 13 | menu `3` | kết thúc, không in gì thêm |

Màn hình chạy thật: `man-hinh-chay.txt`.

---

## 6. Debug

| Việc | Cách làm |
|---|---|
| Breakpoint 1 | dòng `ResourceBundle bundle = ResourceBundle.getBundle(...)` trong `LanguageUtils.getText` |
| Chạy | **Ctrl+F5**, chọn `1` → dừng khi Main lấy câu `So tai khoan` |
| Quan sát | **Variables**: `locale` = `vi`, `key` = `"account.prompt"`; **F8** rồi mở `bundle` → Type `PropertyResourceBundle`, bên trong là các câu tiếng Việt |
| Câu nói kèm | "Đây là chỗ Strategy chọn chiến lược theo `locale`; biến khai kiểu `ResourceBundle` mà chạy bằng `PropertyResourceBundle` — đa hình." |
| Breakpoint 2 | dòng `if ((captchaInput == null) \|\| captchaInput.isEmpty())` trong `Validation.isCaptchaMatch`; tới bước captcha nhấn Enter → `captchaInput = ""`, **F8** nhảy vào `return false` — chặn trước khi tới `contains` |
| Regex | breakpoint trong `Validation.isMatch`, gõ `12345678` ở ô mật khẩu → `matches` trả `false` |

---

## 7. Câu hỏi thầy hay hỏi

| Câu hỏi | Trả lời mẫu |
|---|---|
| Sao bài có repository? | Tờ checklist 1.1 bắt buộc. `AccountRepository` giữ các tài khoản đã đăng nhập, chỉ CRUD đơn giản; tầng đi Controller → Service → Repository → Model. Muốn thêm "xem lịch sử đăng nhập" chỉ thêm hàm ở đây. |
| Validate ở đâu? 3 hàm check của đề dùng làm gì? | Validate ở `Main` qua `utils/Validation` (tờ giấy 1.1), hỏi lại tại chỗ. 3 hàm check trong `Ebank`, `controller.login` gọi làm chốt cuối trước khi lưu — dùng chung regex và `Validation`, luật nằm một chỗ. |
| Sao Main chỉ gọi controller 1 lần? Sao View không tham số? | Guide: mỗi workflow gọi controller 1 lần. Tờ giấy: View nhận qua thuộc tính, render 1 lần/luồng → `setResponseDTO` + `display()`. |
| 4 tính chất OOP ở đâu? | **Đóng gói**: field `private` + get/set trong `Account` và 2 DTO; `locale` `private` trong `Ebank`, chỉ đổi qua `setLocate`. **Kế thừa**: mọi lớp `extends Object`, `Account` ghi đè `toString()`; `PropertyResourceBundle extends ResourceBundle`. **Đa hình**: `bundle.getString()` gọi trên biến kiểu `ResourceBundle` — chạy bản tiếng Việt hay tiếng Anh tuỳ đối tượng thật. **Trừu tượng**: `Main` gọi `controller.login(requestDTO)` không biết bên trong có `Ebank`, repository hay view. |
| Sao `checkAccountNumber` trả `String` mà không `boolean`? | Đề bắt: trả **câu thông báo**. Hợp lệ thì trả `""` (`Constants.VALID`) — **không trả `null`** để nơi gọi `error.isEmpty()` không bao giờ bị `NullPointerException`. |
| `setLocate` trả `void`? | Nó **đổi trạng thái** (`locale`) của `Ebank`, không có kết quả gì để trả. |
| Sao `generateCaptcha` là static trong utils, còn check không static? | Sinh captcha không cần dữ liệu đối tượng nào → static, gọi qua tên lớp (mục 3.1). Check cần `locale` (trạng thái của `Ebank`) → hàm đối tượng. **Bỏ static** ở `generateCaptcha` → `CaptchaUtils.generateCaptcha()` lỗi biên dịch; phải bỏ `private` constructor, `new CaptchaUtils()` rồi gọi qua đối tượng. |
| Access modifier? | Field đều `private`. Hàm `public` của `Ebank` đều được gọi từ ngoài (`controller`, hoặc là hàm đề bắt); `findFirstError` `private`. Constructor của `Main`, `Message`, `Constants`, 3 lớp utils `private`. Hàm trong `Main` `private static`; `Scanner` là biến cục bộ. |
| Sao số tài khoản là `String`? | Giữ số `0` đầu (`0123456789`) và không bị tràn `int` (10 chữ số > 2.147.483.647). |
| `matches` khác `find`? | `matches` so **cả chuỗi**; `find` tìm **một đoạn** khớp — `abc0123456789xyz` sẽ lọt với `find`. |
| Captcha dùng `contains` có an toàn không? | Không — 1 ký tự đúng là qua. Em làm vì **đề bắt `contains`**, và em chặn thêm chuỗi rỗng. Thực tế phải so **cả chuỗi**. |
| SOLID? | **S**: `Ebank` giữ luật đăng nhập, `AccountRepository` giữ dữ liệu, `CaptchaUtils` sinh captcha, `LanguageUtils` đọc chữ, view in. **O**: ngôn ngữ mới = file mới (§3.1). **L**: bundle nào cũng thay được cho nhau. **D**: code phụ thuộc lớp trừu tượng `ResourceBundle`, không phụ thuộc file cụ thể. |

---

## 8. Thầy đổi yêu cầu tại chỗ

| Thầy bảo | Sửa | Không đụng |
|---|---|---|
| Thêm tiếng Nhật | thêm `Language_ja.properties`; `Message.MENU` + `Constants` (mã `ja`, số menu); 1 `case` trong `Main` | `Ebank`, controller, view, repository |
| Captcha 6 ký tự | `Constants.CAPTCHA_LENGTH` | mọi file khác |
| Mật khẩu phải có ký tự đặc biệt | `Constants.PASSWORD_REGEX` thêm `(?=.*[^A-Za-z0-9])` và bỏ giới hạn `[A-Za-z0-9]`; câu lỗi trong 2 file `.properties` | `Ebank`, `Main` |
| Captcha phải gõ **đủ** | `Validation.isCaptchaMatch`: `captchaGenerate.equals(captchaInput)` | các lớp khác |
| Sinh captcha mới mỗi lần gõ sai | chuyển dòng sinh captcha và 2 lệnh in vào trong vòng lặp của `Main.inputCaptcha` | các lớp khác |
| Đổi câu lỗi | chỉ 2 file `.properties` | mã Java |
| In số tài khoản sau khi đăng nhập | `LoginResponseDTO` thêm field `accountNumber`; `Ebank.login` điền; `EbankView.display` in thêm | `Main`, controller |

---

## 9. Tờ checklist 25 mục — bài này đạt thế nào

| Mục | Chỗ trong code |
|---|---|
| 1.1 MVC + repository + View 1 lần | `Main` chỉ dùng controller, DTO, utils; `EbankController` chỉ import dto/service/view; `AccountRepository`; `EbankView.display()` gọi đúng 1 lần trong `EbankController.login` |
| 1.3 / 1.4 tên class, method | lớp danh từ; method động từ (`checkPassword`, `addAccount`, `generateCaptcha`, `findFirstError`); `setLocate` giữ tên đề |
| 1.5 tên biến | `accountList` (ArrayList), `requestDTO`, `responseDTO`; `String[] args` giữ nguyên |
| 1.6 comment | Javadoc + `@author` đầu mỗi lớp; `//` trên mọi method (kể cả getter/setter) và mọi khối |
| 2.6 / 3.7 khai báo | `String line = "";`, `int choice = 0;`, `String error = "";` ở đầu hàm; trong vòng lặp chỉ gán |
| 2.8 dòng trống | trước mọi comment, sau vùng khai báo, giữa các khối `if` |
| 3.3 ngoặc | `(captchaInput == null) \|\| captchaInput.isEmpty()`, `(choice < min) \|\| (choice > max)` |
| 3.4 final + private ctor | `Main`, `Message`, `Constants`, `Validation`, `CaptchaUtils`, `LanguageUtils` |
| 3.8 nối chuỗi | `StringBuilder` trong `generateCaptcha`; nhãn + captcha in bằng 2 lệnh |

---

## 10. Chỗ khác với đề / bản cũ

| Chỗ | Đề / bản cũ | Bài này | Lý do |
|---|---|---|---|
| Tên file ngôn ngữ | Đề: `En.properties`, `Vi.properties` | `Language_en.properties`, `Language_vi.properties` | `getBundle(tên, Locale)` chỉ tìm được file dạng `<tên>_<mã>`; Guidelines cũng gọi là *"Language.properties"* |
| Bản cũ | `ListResourceBundle` (lớp Java) | file `.properties` thật | đề bắt `.properties`; NetBeans chép chúng sang `build/classes` |
| `setLocate(Locate)` | kiểu `Locate` không tồn tại | `setLocate(Locale locate)` | giữ tên hàm (sai chính tả) của đề, kiểu Java thật |
| `generateCaptcha` | đề: hàm của `Ebank` | `utils/CaptchaUtils` (static) | Guide cho main dùng *CapchaUtils*; không dùng trạng thái |
| Tên lớp | Guide viết `CapchaUtils` | `CaptchaUtils` | đúng chính tả tiếng Anh |
| Sau khi đăng nhập | đề không nói | in `Dang nhap thanh cong` / `Login successfully` rồi **kết thúc** | View có kết quả của luồng; câu này **không có trong đề** |
| Tiếng Anh sai của đề | `must is a number`, `Captcha incorrect: H9MOA` | giữ nguyên | thầy so màn hình với đề |
| Kiến trúc | `bo/Ebank`, Scanner trong `Validator`, `ui` | MVC Guide, Scanner chỉ ở `main` | luật thầy |
| Bản 20/09 (trước tờ checklist) | không repository; View `showPrompt(String)`/`showMessage(String)`; Main gọi controller mỗi lần gõ (`checkAccountNumber`, `checkPassword`, `showCaptcha`…); `Main` không `final` | có `AccountRepository`; View nhận `LoginResponseDTO` qua thuộc tính; Main tự validate, gọi `controller.login` 1 lần; `public final class Main` + ctor `private` | tờ checklist giấy thầy phát 21/09/2026 |

⚠️ **Chỗ đề và tờ giấy đá nhau — hỏi thầy một câu trước khi gõ.** Đề muốn `Ebank` kiểm từng ô và hỏi lại ngay; tờ
giấy muốn validate ở Main, mỗi luồng gọi controller 1 lần, View in 1 lần. Bài này theo **tờ giấy** và giữ đủ 4 hàm đề
bắt trong `Ebank` làm chốt cuối. Câu hỏi: *"Thầy ơi, bài P0070 đề bắt hỏi lại từng ô. Em validate ở Main theo checklist,
cuối luồng mới gọi controller một lần để Ebank kiểm lại và lưu tài khoản — như vậy đúng ý thầy chưa ạ?"* Nếu thầy muốn
`Ebank` kiểm ngay lúc gõ: Main gọi thêm controller **chỉ để kiểm** (ném lỗi, không render) mỗi ô — vẫn giữ repository và
View nhận ResponseDTO.
