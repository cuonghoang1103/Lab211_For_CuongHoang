# J1.S.P0072 — Login dùng mã hoá MD5 cho mật khẩu

> Bài CRUD nhỏ có **băm MD5** — chỗ thầy chắc chắn hỏi: *"MD5 là mã hoá hay băm? Sao không
> `new String(digest)`?"*. Khung giống P0055 (có `repository`), thêm `utils/MD5Utils` và **Builder** cho
> Account 7 thuộc tính (+ id).

| | |
|---|---|
| Loại / LOC | Short Assignment · 150 LOC · 2 slot |
| Project | `HE176322_J1SP0072_LoginMD5` |
| Chạy | NetBeans: **File ▸ Open Project** → **F6** |
| Lớp chạy | `main.Main` |
| Kiểm tự động | `python3 _tools/verify.py J1SP0072` → 5 kịch bản × 2 locale |

---

## 1. Đề bài nói gì

- Account: `Username, Password, Name, Phone, Email, Address, Date Of Birth`.
- Menu: **1. Add User · 2. Login · 3) Exit** (đề viết `3)` — giữ nguyên).
- **Add**: username không rỗng, **chưa tồn tại**; password không rỗng; phone **10 hoặc 11 số**; email đúng dạng;
  DOB đúng `dd/MM/yyyy`; password **băm MD5** rồi mới lưu.
- **Login**: nhập account + password → đúng thì chào (`Hello <username>` + màn *Wellcome*), sai báo **login fail**.
- Màn hình đề còn có câu hỏi *"Hi SkyLine, do you want change password now? Y/N:"* và 3 ô
  `Old password: / new password: / renew password:` → bài làm luôn **đổi mật khẩu**.

**Đề bắt buộc** (Guidelines):

| Thứ | Đề viết | Bài này đặt ở |
|---|---|---|
| `public int addAccount(7 String) throws Exception` | *"Student must implement methods"* | `AccountRepository.addAccount(AccountRequestDTO)` trả `int` id |
| `public Boolean login(String username, String password)` | như trên | `AccountRepository.login(AccountRequestDTO)` trả **`Boolean`** |
| MD5 | *"Password use the MD5 encryption function"* | `utils/MD5Utils.hash(String)` |

---

## 2. Kiến thức cần biết

### 2.1 MD5 — băm, không phải mã hoá

| | Mã hoá (encryption) | Băm (hash) — MD5 |
|---|---|---|
| Đảo ngược được? | có (có khoá) | **không** |
| Kết quả | dài theo dữ liệu | luôn **16 byte** = 32 chữ số hex |
| Login kiểm thế nào | giải mã rồi so | **băm chuỗi vừa gõ rồi so 2 chuỗi băm** |

Ví dụ: `MD5("123456") = e10adc3949ba59abbe56e057f20f883e` (em có thể kiểm bằng debug).

### 2.2 byte[] → chuỗi hex (chỗ hay sai)

```java
byte[] digest = MessageDigest.getInstance("MD5").digest(text.getBytes(StandardCharsets.UTF_8));
for (byte b : digest) {
    hex.append(String.format("%02x", b));   // 0x07 → "07", 0xE1 → "e1"
}
```

| Cách viết | Kết quả |
|---|---|
| `new String(digest)` | ❌ **giải mã** 16 byte như thể là chữ → ký tự rác, byte lỗi bị thay bằng `�`, mất thông tin |
| `String.format("%x", b)` | ❌ `0x07` ra `7` → chuỗi 31 ký tự |
| `String.format("%02x", b)` | ✅ luôn 2 chữ số / byte → 32 ký tự |
| `getBytes()` không tham số | ❌ phụ thuộc bảng mã của máy — cùng mật khẩu, máy khác ra hash khác |

### 2.3 Ngày sinh `dd/MM/yyyy` — `setLenient(false)` + so ngược

| Gõ | Lenient mặc định | `setLenient(false)` | + `format(parse(s)).equals(s)` |
|---|---|---|---|
| `26/06/2016` | ✅ | ✅ | ✅ |
| `31/02/2003` | thành 03/03/2003 ❌ | bị chặn | bị chặn |
| `1/2/2015` | ✅ | ✅ | **bị chặn** |
| `26/06/2016xyz` | ✅ | ✅ | **bị chặn** |

### 2.4 Regex dùng trong bài (`Constants`)

| Hằng | Regex | Nghĩa |
|---|---|---|
| `PHONE_REGEX` | `^\d{10,11}$` | chỉ chữ số, đúng 10 hoặc 11 ký tự (giữ số `0` đầu → kiểu `String`) |
| `EMAIL_REGEX` | `^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$` | `ten@mien.duoi`, đuôi ≥ 2 chữ cái (`nghianv@t.com` ✅, `nghianv@t` ❌) |

### 2.5 `Boolean` (wrapper) — đề bắt kiểu trả về này

`if (controller.login(dto))` tự **unboxing** `Boolean → boolean`. Nếu hàm trả `null` thì chỗ unboxing ném
`NullPointerException` → vì vậy `login` **không bao giờ trả null** (chỉ `true`/`false`).

---

## 3. Thiết kế

```
HE176322_J1SP0072_LoginMD5/src/
├── model/      Account (JavaBean 8 thuộc tính), AccountBuilder (Builder)
├── dto/        AccountRequestDTO  (chuỗi gõ: 7 ô + 3 ô đổi mật khẩu)  main ──► controller
│               AccountResponseDTO (username, name)                   controller ──► view
├── repository/ AccountRepository  ArrayList<Account> + nextId; addAccount/login/findAccount/changePassword
├── controller/ AccountController  điều hướng repository ↔ view
├── view/       AccountView        màn Wellcome + câu thông báo
├── utils/      Validation (kiểm chuỗi), MD5Utils (băm)
├── constants/  Message, Constants
└── main/       Main               menu + Scanner
```

| Lớp | Làm gì | Vì sao ở đây |
|---|---|---|
| `Account` | 1 tài khoản; `password` giữ **chuỗi băm** | model = mô tả đối tượng |
| `AccountBuilder` | lắp `Account` từng bước | chỉ biết `Account` → package model |
| `AccountRepository` | giữ danh sách, 2 hàm đề bắt + đổi mật khẩu | Guide: *"Chứa data … CRUD"* |
| `MD5Utils` | `hash(String)` | Guide: utils chứa *"mã hóa dữ liệu"*, static |
| `Validation` | chuỗi → giá trị sạch hoặc ném lỗi | Guide: utils *"validate"* |
| `AccountView` | in màn chào (kèm câu hỏi Y/N), `Login fail.`, thông báo | chỉ view/main được in |

**Luồng Login + đổi mật khẩu:**

```
Main: đọc Account, Password ──► AccountRequestDTO ──► controller.login(dto)        ← workflow 1
   controller ──► repository.login(dto): tìm username (không phân biệt hoa thường),
                                         so MD5Utils.hash(password gõ) với hash đã lưu
        true  ──► view.setAccount(repository.findAccount(dto)); view.display()
                  → "------------ Wellcome -----------" / "Hello NghiaNV" / "Hi SkyLine, ... Y/N:"
        false ──► view.showMessage("Login fail.")
Main: nếu true, đọc câu trả lời; Y/y ──► đọc 3 mật khẩu ──► controller.changePassword(dto) ← workflow 2
   repository.changePassword: old đúng? → new không rỗng? → new == renew? → lưu hash mới
```

### 3.1 Design Pattern

**Builder** (Creational)

| Yếu tố | Trong bài này |
|---|---|
| **Name** | Builder |
| **Problem** | `Account` có 8 thuộc tính, **6 `String` liền nhau** (username, password, name, phone, email, address). Constructor 8 tham số gọi đảo phone ↔ email vẫn biên dịch được — sai âm thầm. |
| **Solution** | `AccountBuilder` = **Builder** (`withId`, `withUsername`, `withPassword`, `withName`, `withPhone`, `withEmail`, `withAddress`, `withDob`, `build`). `Account` = **Product**. `AccountRepository.addAccount` = **Director**. |
| **Consequences** | ✅ Mỗi giá trị **có tên** khi gán; thêm thuộc tính = thêm 1 hàm `withX`. ❌ Thêm 1 lớp; builder không `static` (model cấm static) nên là lớp riêng. |

**Facade**: `AccountController` — `Main` chỉ thấy 3 hàm, không biết có repository, MD5, view.
**Repository**: `AccountRepository` là nơi duy nhất chạm vào `ArrayList<Account>` và hash.
**MVC (JSP)**: `Account`, 2 DTO là JavaBean.

### 3.2 SOLID

| | Ở đâu |
|---|---|
| **S** | `Account` giữ dữ liệu · `AccountBuilder` tạo · `AccountRepository` lưu/kiểm đăng nhập · `MD5Utils` băm · `Validation` kiểm · `AccountView` in |
| **O** | đổi MD5 → SHA-256 chỉ sửa `Constants.HASH_ALGORITHM`; `MD5Utils.hash` không đổi |
| **D** | `Main` chỉ biết controller + DTO; mật khẩu so qua **một** hàm `isPasswordCorrect` (login và đổi mật khẩu dùng chung) |

---

## 4. Code từng bước

> Thầy: *"code cái **model trước rồi đến data**"*.

| Bước | File | Việc |
|---|---|---|
| 1 | `model/Account.java` | 8 field private + constructor rỗng + get/set + `toString` (không in hash) |
| 2 | `model/AccountBuilder.java` | field `account`; 8 hàm `withX`; `build()` |
| 3 | `dto/AccountRequestDTO.java` | 10 field **String** + get/set |
| 4 | `dto/AccountResponseDTO.java` | `username`, `name` + get/set |
| 5 | `constants/Message.java`, `Constants.java` | câu chữ đề (giữ `3) Exit`, `Please choice one option:`, `Wellcome`); regex, `"MD5"`, `"%02x"` |
| 6 | `utils/MD5Utils.java` | `hash(String)` |
| 7 | `utils/Validation.java` | `getText` · `getChoice` · `getRequired` · `getPhone` · `getEmail` · `getDob` · `isYes` |
| 8 | `repository/AccountRepository.java` | `addAccount` · `login` · `findAccount` · `changePassword` · `findByUsername` (private) · `isPasswordCorrect` (private) |
| 9 | `view/AccountView.java` | `setAccount` · `display` · `showMessage` |
| 10 | `controller/AccountController.java` | `addAccount` · `login` · `changePassword` |
| 11 | `main/Main.java` | menu, `inputChoice`, `addAccount`, `login`, `inputNewPassword` |

**Bẫy hay gặp:**

1. `new String(digest)` → xem 2.2.
2. Lưu **mật khẩu thô** "cho tiện" rồi băm khi so → trái đề; field `password` chỉ giữ **hash**.
3. Màn Add **không có dấu cách** sau dấu `:` (`Account:`), màn Login **có** (`Account: `) — chép đúng đề.
4. `login` trả `Boolean` mà `return null` ở nhánh nào đó → `if (controller.login(dto))` văng NPE.

---

## 5. Test trước khi gọi thầy

| # | Chọn | Gõ | Phải thấy |
|---|---|---|---|
| 1 | 1 | `NghiaNV/nghia/SkyLine/0988666888/nghianv@t.com/Ha Noi/26/06/2016` | `Account [NghiaNV] has been added with id 1.` |
| 2 | 1 | Account trống | `Username cannot be empty.` |
| 3 | 1 | Account `nghianv` (đã có, khác hoa thường) | `Username [NghiaNV] already exists.` |
| 4 | 1 | Password trống / Name trống | `Password cannot be empty.` / `Name cannot be empty.` |
| 5 | 1 | Phone trống / `098866688` / `0988a66888` / 12 số | `Phone number cannot be empty.` / `Phone number must be 10 or 11 number.` |
| 6 | 1 | Email trống / `nghianv.t.com` / `nghianv@t` | `Email cannot be empty.` / `Email is not in the correct format.` |
| 7 | 1 | DOB trống / `31/02/2003` / `1/2/2015` / `26/06/2016xyz` | `Date of birth cannot be empty.` / `Date of birth must be a real date in the format dd/MM/yyyy.` |
| 8 | 2 | `Ghost/nghia` hoặc `NghiaNV/Nghia` | `Login fail.` |
| 9 | 2 | `NghiaNV/nghia` rồi `N` | màn `Wellcome`, `Hello NghiaNV`, `Hi SkyLine, ... Y/N:` rồi về menu |
| 10 | 2 | … `Y`, old sai | `Old password is not correct.` |
| 11 | 2 | … `y`, old đúng, new trống | `New password cannot be empty.` |
| 12 | 2 | … `Y`, new ≠ renew | `The two new passwords do not match.` |
| 13 | 2 | … `Y`, đổi đúng | `Password has been changed.` — login bằng mật khẩu cũ → `Login fail.` |
| 14 | menu | `abc` / `4` | `You must input a number.` / `Please choose from 1 to 3.` |

---

## 6. Debug

| Việc | Cách làm |
|---|---|
| Breakpoint | dòng `.withPassword(MD5Utils.hash(password))` trong `AccountRepository.addAccount` |
| Chạy | **Ctrl+F5**, thêm account với password `123456` |
| Bước | **F7** vào `MD5Utils.hash` → F8 qua `digester.digest(...)` → mở `digest` trong **Variables**: 16 phần tử byte (có số âm); F8 hết vòng `for` → `hex` = `e10adc3949ba59abbe56e057f20f883e` |
| Xem login | breakpoint trong `isPasswordCorrect`, login sai mật khẩu: so 2 chuỗi hash khác nhau → `false` |
| Watch | `accounts.size()`, `nextId` |

---

## 7. Câu hỏi thầy hay hỏi

### OOP

| Câu hỏi | Trả lời mẫu |
|---|---|
| 4 tính chất OOP ở đâu? | **Đóng gói**: 8 field `private` trong `Account`; hash chỉ đổi qua `setPassword` trong `changePassword`. **Kế thừa**: mọi lớp `extends Object`. **Đa hình**: `toString()` `@Override` trong `Account`; `equals` của `String` so hash. **Trừu tượng**: `Main` gọi `controller.login(dto)` không biết có MD5 hay danh sách. |

### Access modifier / static / kiểu trả về

| Câu hỏi | Trả lời mẫu |
|---|---|
| `findByUsername`, `isPasswordCorrect` sao `private`? | Chỉ repository dùng; không phải "hợp đồng". Hàm `public` là hàm lớp khác gọi: `addAccount/login/findAccount/changePassword` (controller gọi), `withX` (repository gọi), get/set. |
| Sao `MD5Utils.hash` là `static`? | Không giữ dữ liệu: cùng chuỗi vào → cùng hash. Guide: utils *"phải dùng static method"*. **Bỏ static** → `MD5Utils.hash(...)` lỗi biên dịch; phải bỏ `private` constructor và `new MD5Utils()` trong repository. |
| `static` còn ở đâu? | Hằng trong `Message`/`Constants` (một bản dùng chung); hàm phụ trong `Main` (vì `main` static; biến thì không — Guide cấm). Model, controller, view, repository: **không** static. |
| `addAccount` trả `int`? | Đề bắt trả **id account**; controller in `... with id 1.` |
| `login` trả `Boolean` chứ không `boolean`? | **Đề bắt**. Khác nhau: `Boolean` là đối tượng, có thể `null`; `boolean` thì không. Em không bao giờ trả `null` để unboxing an toàn. |
| `hash` trả `String`? | Để lưu và so bằng `equals`; `byte[]` so bằng `==` là so địa chỉ. |
| Phone sao là `String`? | Số `0` đầu (`0988...`) mất nếu là `int`; 11 chữ số còn vượt `int`. |
| **Sao `ArrayList` mà không `List`?** | `List` là **interface**, `ArrayList` là **lớp cài đặt** bằng mảng động (lấy theo chỉ số nhanh; chèn/xoá giữa chậm). Em khai báo đúng lớp đang dùng. |

### Bảo mật / nghiệp vụ

| Câu hỏi | Trả lời mẫu |
|---|---|
| MD5 giải mã được không? | Không — là **hàm băm một chiều**. Login **băm lại** chuỗi gõ và so hash. |
| MD5 có an toàn cho mật khẩu thật? | Không: quá nhanh, không có *salt* → hai người cùng mật khẩu ra cùng hash, tra bảng có sẵn là ra. Thực tế dùng bcrypt/Argon2. Đề bắt MD5 nên em dùng MD5. |
| Sao sai username và sai mật khẩu cùng một câu `Login fail.`? | Báo riêng thì kẻ gian biết username nào có thật. |
| Username không phân biệt hoa thường? | `NghiaNV` và `nghianv` là một người → không cho tạo trùng, login được cả hai cách gõ. Mật khẩu thì **phân biệt**. |
| Sao lỗi Add hiện **sau** khi hỏi đủ 7 ô? | Đề: `addAccount(7 chuỗi) throws Exception` — kiểm nằm trong `addAccount`. |
| Login gọi controller 2 lần? | Là **2 workflow**: đăng nhập (`login`) và đổi mật khẩu (`changePassword`, chỉ khi trả lời Y). Mỗi cái gọi controller **1 lần**. |
| Pattern gì? | **Builder**, **Facade**, **Repository**, **MVC** — mục 3.1. |

---

## 8. Thầy đổi yêu cầu tại chỗ

| Thầy bảo | Sửa | Không đụng |
|---|---|---|
| Đổi MD5 → SHA-256 | `Constants.HASH_ALGORITHM = "SHA-256"` | mọi file khác |
| Phone 10 số thôi | `Constants.PHONE_REGEX = "^\\d{10}$"` + chữ `Message.PHONE_INVALID` | `Validation` |
| Password ≥ 6 ký tự | thêm `Validation.getPassword` + hằng + message; gọi trong `addAccount` và `changePassword` | model, view |
| Username phân biệt hoa thường | `equalsIgnoreCase` → `equals` trong `findByUsername` | mọi file khác |
| Thêm menu "Hiện danh sách user" | `Message.MENU`, `Constants`, `AccountResponseDTO` (thêm cột), `AccountRepository.getAccounts`, `AccountView`, `AccountController`, `case` trong `Main` | `Account`, `MD5Utils` |

---

## 9. Chỗ khác với đề / bản cũ

| Chỗ | Đề / bản cũ | Bài này | Lý do |
|---|---|---|---|
| Chữ ký `addAccount` | 7 tham số `String` | `addAccount(AccountRequestDTO)` — DTO giữ 7 chuỗi | thầy: **không truyền 3 tham số 1 hàm** (V4); Guide: qua DTO |
| Chữ ký `login` | `login(String, String)` | `login(AccountRequestDTO)`, vẫn trả `Boolean` | Guide: dữ liệu vào qua DTO |
| Thuộc tính | 7 thuộc tính | thêm `id` | đề bắt `addAccount` trả *"id account"* |
| Lời chào | chỉ `Hello + Username`, màn hình lại có `Hi SkyLine, ...` | in **cả hai** (`Hello NghiaNV` rồi câu hỏi Y/N) | đúng cả mô tả lẫn màn hình đề; giữ bản tham chiếu |
| Đổi mật khẩu | chỉ có trên màn hình, không trong Guidelines | có (`changePassword`) | màn hình đề là bắt buộc |
| Câu hỏi Y/N | — | do **view** in (cần tên người dùng), **main** đọc câu trả lời | view không được đọc bàn phím, main không được chạm model |
| Kiến trúc | `bo/ui`, Scanner trong `Validator` | MVC theo Guide, Scanner **chỉ ở main**; `MD5` → `MD5Utils` (final, static) | luật thầy |
