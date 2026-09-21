# J1.S.P0072 — Login dùng mã hoá MD5 cho mật khẩu

> Bài CRUD nhỏ có **băm MD5** — chỗ thầy chắc chắn hỏi: *"MD5 là mã hoá hay băm? Sao không
> `new String(digestArray)`?"*. Khung giống P0057 (có `repository`), thêm `utils/MD5Utils` và **Builder**
> cho Account 7 thuộc tính (+ id). Theo tờ checklist giấy: **Main** nhập, validate và **băm MD5** (qua
> `MD5Utils`) rồi mới đặt vào RequestDTO; repository chỉ lưu/so **chuỗi băm**.

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
| `public int addAccount(7 String) throws Exception` | *"Student must implement methods"* | `AccountRepository.addAccount(AccountRequestDTO)` trả `int` id (comment `// brief:` chép chữ ký đề ngay trên hàm) |
| `public Boolean login(String username, String password)` | như trên | `AccountRepository.login(String username, String password)` — **đúng chữ ký đề**, trả **`Boolean`**; `password` là **chuỗi băm** Main đã tính |
| MD5 | *"Password use the MD5 encryption function"* | `utils/MD5Utils.hash(String)`, **gọi ở Main** (tờ giấy 1.1: *"mã hóa thực hiện ở Main"*) |

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
digestArray = digester.digest(text.getBytes(StandardCharsets.UTF_8));
for (byte digestByte : digestArray) {
    hexBuilder.append(String.format("%02x", digestByte));   // 0x07 → "07", 0xE1 → "e1"
}
```

| Cách viết | Kết quả |
|---|---|
| `new String(digestArray)` | ❌ **giải mã** 16 byte như thể là chữ → ký tự rác, byte lỗi bị thay bằng `�`, mất thông tin |
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

`if (!accountRepository.login(username, password))` (trong controller) tự **unboxing** `Boolean → boolean`.
Nếu hàm trả `null` thì chỗ unboxing ném `NullPointerException` → vì vậy `login` **không bao giờ trả null**
(chỉ `true`/`false`).

---

## 3. Thiết kế

```
HE176322_J1SP0072_LoginMD5/src/
├── model/      Account (JavaBean 8 thuộc tính), AccountBuilder (Builder: setId … setDob, build)
├── dto/        AccountRequestDTO  (giá trị ĐÃ KIỂM + chuỗi BĂM)        main ──► controller
│               AccountResponseDTO (message | username + name)         controller ──► view
├── repository/ AccountRepository  ArrayList<Account> accountList + nextId;
│                                  addAccount / login / findUsername / findName / changePassword
├── controller/ AccountController  addAccount / login / changePassword — mỗi hàm render 1 lần
├── view/       AccountView        field responseDTO + setResponseDTO + display() không tham số
├── utils/      Validation (kiểm chuỗi, ném lỗi), MD5Utils (băm) — chỉ Main gọi
├── constants/  Message, Constants
└── main/       Main (final + private ctor) — menu, Scanner, validate, BĂM MD5
```

| Lớp | Làm gì | Vì sao ở đây |
|---|---|---|
| `Account` | 1 tài khoản; `password` giữ **chuỗi băm** | model = mô tả đối tượng |
| `AccountBuilder` | lắp `Account` từng bước (`setX` trả về chính builder) | chỉ biết `Account` → package model |
| `AccountRepository` | giữ `accountList`, 2 hàm đề bắt + đổi mật khẩu; **chỉ so chuỗi băm** | tờ giấy 1.1: *"Bắt buộc phải có repository"*, *"chỉ chứa data và CRUD"* |
| `MD5Utils` | `hash(String)` | Guide: utils chứa *"mã hóa dữ liệu"*, static; tờ giấy: *mã hoá ở Main* |
| `Validation` | chuỗi → giá trị sạch hoặc ném `Exception(Message.X)` | tờ giấy: *Validate ở Main* (qua utils) |
| `AccountView` | in `responseDTO`: câu kết quả, hoặc màn *Wellcome* (kèm câu hỏi Y/N) | chỉ view/main được in |

**Luồng chạy** (Main → RequestDTO → Controller → Repository → Model; ResponseDTO → View **1 lần**):

```
Option 1  Main.inputAccount: in 7 câu hỏi, đọc 7 dòng ──► kiểm theo thứ tự đề (Validation, lỗi đầu tiên
          ném ra) ──► MD5Utils.hash(password) ──► AccountRequestDTO ──► controller.addAccount(dto)
             controller ──► repository.addAccount(dto): trùng username? → ném "Username [..] already exists."
                            chưa trùng → Builder → accountList.add → trả id
             controller ──► responseDTO.message = "Account [..] has been added with id 1." ──► view (1 lần)

Option 2  Main.inputLogin: đọc Account, Password ──► MD5Utils.hash(password) ──► controller.login(dto)
             controller ──► repository.login(username, chuỗiBăm): false → ném "Login fail." (Main in)
                        ──► true → responseDTO.username/name ──► view: Wellcome / Hello / Hi .. Y/N: (1 lần)
          Main: switch (inputAnswer(sc))  ← câu hỏi Y/N là MENU CON, mỗi case = 1 luồng
             case "Y": inputNewPassword (kiểm new ≠ rỗng, new == renew; băm old/new)
                       ──► controller.changePassword(dto) ──► repository: old đúng? → lưu hash mới
                       ──► responseDTO.message = "Password has been changed." ──► view (1 lần)
             default : về menu
```

### 3.1 Design Pattern

**Builder** (Creational)

| Yếu tố | Trong bài này |
|---|---|
| **Name** | Builder |
| **Problem** | `Account` có 8 thuộc tính, **6 `String` liền nhau** (username, password, name, phone, email, address). Constructor 8 tham số gọi đảo phone ↔ email vẫn biên dịch được — sai âm thầm. |
| **Solution** | `AccountBuilder` = **Builder** (`setId`, `setUsername`, `setPassword`, `setName`, `setPhone`, `setEmail`, `setAddress`, `setDob` — mỗi hàm trả về chính builder để nối tiếp — và `build`). `Account` = **Product**. `AccountRepository.addAccount` = **Director**. |
| **Consequences** | ✅ Mỗi giá trị **có tên** khi gán; thêm thuộc tính = thêm 1 hàm `setX`. ❌ Thêm 1 lớp; builder không `static` (model cấm static) nên là lớp riêng. |

**Facade**: `AccountController` — `Main` chỉ thấy 3 hàm, không biết có repository, view.
**Repository**: `AccountRepository` là nơi duy nhất chạm vào `ArrayList<Account> accountList` và các chuỗi băm đã lưu.
**MVC (JSP)**: `Account`, 2 DTO là JavaBean.

### 3.2 SOLID

| | Ở đâu |
|---|---|
| **S** | `Account` giữ dữ liệu · `AccountBuilder` tạo · `AccountRepository` lưu/so chuỗi băm · `MD5Utils` băm · `Validation` kiểm · `AccountView` in · `Main` nhập |
| **O** | đổi MD5 → SHA-256 chỉ sửa `Constants.HASH_ALGORITHM`; `MD5Utils.hash` không đổi |
| **D** | `Main` chỉ biết controller + DTO + utils; controller chỉ biết repository + view, **không** import `model` |

---

## 4. Code từng bước

> Thầy: *"code cái **model trước rồi đến data**"*.

| Bước | File | Việc |
|---|---|---|
| 1 | `model/Account.java` | 8 field private + constructor rỗng + get/set + `toString` (`String.format`, không in hash) |
| 2 | `model/AccountBuilder.java` | field `account`; 8 hàm `setX` trả `this`; `build()` |
| 3 | `dto/AccountRequestDTO.java` | username, **password (băm)**, name, phone, email, address, **dob (`Date`)**, **oldPassword/newPassword (băm)** + get/set |
| 4 | `dto/AccountResponseDTO.java` | `message`, `username`, `name` + get/set |
| 5 | `constants/Message.java`, `Constants.java` | câu chữ đề (giữ `3) Exit`, `Please choice one option:`, `Wellcome`); regex, `"MD5"`, `"%02x"`, `YES`/`NO` |
| 6 | `utils/MD5Utils.java` | `hash(String)` |
| 7 | `utils/Validation.java` | `getText` · `getChoice` · `getRequired` · `getPhone` · `getEmail` · `getDob` · `getNewPassword` · `getAnswer` |
| 8 | `repository/AccountRepository.java` | `addAccount` · `login(String, String)` · `findUsername` · `findName` · `changePassword` · `findByUsername` (private) |
| 9 | `view/AccountView.java` | field `responseDTO` · `setResponseDTO` · `display()` |
| 10 | `controller/AccountController.java` | `addAccount` · `login` · `changePassword` — mỗi hàm `setResponseDTO` + `display()` **1 lần** |
| 11 | `main/Main.java` | menu, `inputChoice`, `inputText`, `inputAccount`, `inputLogin`, `inputAnswer`, `inputNewPassword` |

**Bẫy hay gặp:**

1. `new String(digestArray)` → xem 2.2.
2. Đặt **mật khẩu thô** vào DTO "cho tiện" rồi để repository băm → trái tờ giấy 1.1 (*mã hoá ở Main*); DTO và
   `Account.password` chỉ giữ **hash**.
3. Màn Add **không có dấu cách** sau dấu `:` (`Account:`), màn Login **có** (`Account: `) — chép đúng đề.
4. `login` trả `Boolean` mà `return null` ở nhánh nào đó → `if (!accountRepository.login(...))` văng NPE.
5. Kiểm "password rỗng" **sau** khi băm → không bao giờ bắt được (MD5 của `""` là
   `d41d8cd98f00b204e9800998ecf8427e`, không rỗng). Phải kiểm **trước**, rồi mới băm.

---

## 5. Test trước khi gọi thầy

| # | Chọn | Gõ | Phải thấy |
|---|---|---|---|
| 1 | 1 | `NghiaNV/nghia/SkyLine/0988666888/nghianv@t.com/Ha Noi/26/06/2016` | `Account [NghiaNV] has been added with id 1.` |
| 2 | 1 | Account trống | `Username cannot be empty.` |
| 3 | 1 | Account `nghianv` (đã có, khác hoa thường), 6 ô còn lại đúng | `Username [NghiaNV] already exists.` |
| 4 | 1 | Password trống / Name trống | `Password cannot be empty.` / `Name cannot be empty.` |
| 5 | 1 | Phone trống / `098866688` / `0988a66888` / 12 số | `Phone number cannot be empty.` / `Phone number must be 10 or 11 number.` |
| 6 | 1 | Email trống / `nghianv.t.com` / `nghianv@t` | `Email cannot be empty.` / `Email is not in the correct format.` |
| 7 | 1 | DOB trống / `31/02/2003` / `1/2/2015` / `26/06/2016xyz` | `Date of birth cannot be empty.` / `Date of birth must be a real date in the format dd/MM/yyyy.` |
| 8 | 2 | `Ghost/nghia` hoặc `NghiaNV/Nghia` | `Login fail.` |
| 9 | 2 | `NghiaNV/nghia` rồi `N` | màn `Wellcome`, `Hello NghiaNV`, `Hi SkyLine, ... Y/N:` rồi về menu |
| 10 | 2 | … `Y`, old sai, new = renew (không rỗng) | `Old password is not correct.` |
| 11 | 2 | … `y`, old đúng, new trống | `New password cannot be empty.` |
| 12 | 2 | … `Y`, new ≠ renew | `The two new passwords do not match.` |
| 13 | 2 | … `Y`, đổi đúng | `Password has been changed.` — login bằng mật khẩu cũ → `Login fail.` |
| 14 | menu | `abc` / `4` | `You must input a number.` / `Please choose from 1 to 3.` |

---

## 6. Debug

| Việc | Cách làm |
|---|---|
| Breakpoint | dòng `requestDTO.setPassword(MD5Utils.hash(password));` trong `Main.inputAccount` |
| Chạy | **Ctrl+F5**, thêm account với password `123456` |
| Bước | **F7** vào `MD5Utils.hash` → F8 qua `digester.digest(...)` → mở `digestArray` trong **Variables**: 16 phần tử byte (có số âm); F8 hết vòng `for` → `hexBuilder` = `e10adc3949ba59abbe56e057f20f883e` |
| Xem login | breakpoint trong `AccountRepository.login`, login sai mật khẩu: `account.getPassword()` và `password` là 2 chuỗi băm khác nhau → `false` |
| Watch | `accountList.size()`, `nextId` |

---

## 7. Câu hỏi thầy hay hỏi

### OOP

| Câu hỏi | Trả lời mẫu |
|---|---|
| 4 tính chất OOP ở đâu? | **Đóng gói**: 8 field `private` trong `Account`; hash chỉ đổi qua `setPassword` trong `changePassword`. **Kế thừa**: mọi lớp `extends Object`. **Đa hình**: `toString()` `@Override` trong `Account`; `equals` của `String` so hash. **Trừu tượng**: `Main` gọi `controller.login(requestDTO)` không biết có danh sách hay view. |

### Access modifier / static / kiểu trả về

| Câu hỏi | Trả lời mẫu |
|---|---|
| `findByUsername` sao `private`? | Chỉ repository dùng; không phải "hợp đồng". Hàm `public` là hàm lớp khác gọi: `addAccount/login/findUsername/findName/changePassword` (controller gọi), `setX` của builder (repository gọi), get/set. |
| Sao `MD5Utils.hash` là `static`? | Không giữ dữ liệu: cùng chuỗi vào → cùng hash. Guide: utils *"phải dùng static method"*. **Bỏ static** → `MD5Utils.hash(...)` lỗi biên dịch; phải bỏ `private` constructor và `new MD5Utils()` trong Main. |
| `static` còn ở đâu? | Hằng trong `Message`/`Constants` (một bản dùng chung); hàm phụ trong `Main` (vì `main` static; biến thì không — Guide cấm). Model, controller, view, repository: **không** static. |
| `addAccount` trả `int`? | Đề bắt trả **id account**; controller đặt `... with id 1.` vào `responseDTO`, view in. |
| `login` trả `Boolean` chứ không `boolean`? | **Đề bắt**. Khác nhau: `Boolean` là đối tượng, có thể `null`; `boolean` thì không. Em không bao giờ trả `null` để unboxing an toàn. |
| `hash` trả `String`? | Để lưu và so bằng `equals`; `byte[]` so bằng `==` là so địa chỉ. |
| Phone sao là `String`? | Số `0` đầu (`0988...`) mất nếu là `int`; 11 chữ số còn vượt `int`. |
| **Sao `ArrayList` mà không `List`?** | `List` là **interface**, `ArrayList` là **lớp cài đặt** bằng mảng động (lấy theo chỉ số nhanh; chèn/xoá giữa chậm). Em khai báo đúng lớp đang dùng. |

### Bảo mật / nghiệp vụ

| Câu hỏi | Trả lời mẫu |
|---|---|
| MD5 giải mã được không? | Không — là **hàm băm một chiều**. Login: **Main băm lại** chuỗi gõ, repository so 2 chuỗi băm. |
| Mã hoá ở đâu? Sao không để repository băm? | Ở **Main** (`MD5Utils.hash`), trước khi đặt vào `AccountRequestDTO` — tờ giấy 1.1: *"Toàn bộ việc nhập dữ liệu/Validate/đọc từ file/mã hóa thực hiện ở Main"*. Nhờ vậy mật khẩu thô không bao giờ ra khỏi Main; repository chỉ lưu/so chuỗi băm. |
| Validate ở đâu? | Ở **Main** qua `utils/Validation` (ném `Exception(Message.X)`, `main` bắt và in `e.getMessage()`). Riêng "username đã tồn tại" và "old password sai" cần **dữ liệu** nên repository kiểm và ném lỗi. |
| Sao bài có repository? | Tờ giấy 1.1: *"Bắt buộc phải có repository"*. `AccountRepository` giữ `accountList` (DB của đề) + CRUD đơn giản; không tính toán nên không cần service: Controller → Repository → Model. |
| View nhận dữ liệu thế nào? | Qua **thuộc tính**: `private AccountResponseDTO responseDTO` + `setResponseDTO(...)`, rồi `display()` **không tham số** — đúng code mẫu của thầy. Mỗi hàm controller gọi `display()` **đúng 1 lần**. Hết `showMessage(String)`. |
| MD5 có an toàn cho mật khẩu thật? | Không: quá nhanh, không có *salt* → hai người cùng mật khẩu ra cùng hash, tra bảng có sẵn là ra. Thực tế dùng bcrypt/Argon2. Đề bắt MD5 nên em dùng MD5. |
| Sao sai username và sai mật khẩu cùng một câu `Login fail.`? | Báo riêng thì kẻ gian biết username nào có thật. |
| Username không phân biệt hoa thường? | `NghiaNV` và `nghianv` là một người → không cho tạo trùng, login được cả hai cách gõ. Mật khẩu thì **phân biệt**. |
| Sao lỗi Add hiện **sau** khi hỏi đủ 7 ô? | Đề: `addAccount(7 chuỗi) throws Exception` — nhận đủ 7 chuỗi rồi mới kiểm; màn đề in 7 câu hỏi liền nhau. `Main.inputAccount` đọc đủ 7 dòng rồi mới kiểm theo thứ tự đề, lỗi đầu tiên ném ra. |
| Login gọi controller 2 lần? | Là **2 luồng**, mỗi luồng là **1 switch-case** (tờ giấy: *"Mỗi luồng tính là 1 switch - case ở Main"*): `case MENU_LOGIN` gọi `login` (render màn Wellcome 1 lần); câu hỏi *Y/N* là **menu con** `switch (inputAnswer(sc))`, `case YES` gọi `changePassword` (render 1 lần). Không case nào gọi controller 2 lần. |
| Pattern gì? | **Builder**, **Facade**, **Repository**, **MVC** — mục 3.1. |

---

## 8. Thầy đổi yêu cầu tại chỗ

| Thầy bảo | Sửa | Không đụng |
|---|---|---|
| Đổi MD5 → SHA-256 | `Constants.HASH_ALGORITHM = "SHA-256"` | mọi file khác |
| Phone 10 số thôi | `Constants.PHONE_REGEX = "^\\d{10}$"` + chữ `Message.PHONE_INVALID` | `Validation` |
| Password ≥ 6 ký tự | thêm `Validation.getPassword` + hằng + message; gọi trong `Main.inputAccount` và `Validation.getNewPassword` (trước khi băm) | model, view, repository |
| Username phân biệt hoa thường | `equalsIgnoreCase` → `equals` trong `findByUsername` | mọi file khác |
| Thêm menu "Hiện danh sách user" | `Message.MENU`, `Constants`, `AccountResponseDTO` (thêm `ArrayList<String> rowList`), `AccountRepository.getRowList` (dùng `toString()` của model), nhánh mới trong `AccountView.display`, `AccountController`, `case` trong `Main` | `Account`, `MD5Utils` |

---

## 9. Chỗ khác với đề / bản cũ

| Chỗ | Đề / bản cũ | Bài này | Lý do |
|---|---|---|---|
| Chữ ký `addAccount` | 7 tham số `String` | `addAccount(AccountRequestDTO)` — DTO giữ 7 giá trị Main đã kiểm (mật khẩu đã băm, DOB đã là `Date`); comment `// brief:` chép chữ ký đề | thầy: **không truyền 3 tham số 1 hàm** (V4); Guide: qua DTO |
| Chữ ký `login` | `login(String, String)` | **giữ đúng đề**: `AccountRepository.login(String username, String password)`, trả `Boolean` (bản cũ nhận DTO) | tờ giấy 1.1: repository nhận *"param nếu số param < 3"* — 2 tham số là được |
| Thuộc tính | 7 thuộc tính | thêm `id` | đề bắt `addAccount` trả *"id account"* |
| Lời chào | chỉ `Hello + Username`, màn hình lại có `Hi SkyLine, ...` | in **cả hai** (`Hello NghiaNV` rồi câu hỏi Y/N) | đúng cả mô tả lẫn màn hình đề; giữ bản tham chiếu |
| Đổi mật khẩu | chỉ có trên màn hình, không trong Guidelines | có (`changePassword`) | màn hình đề là bắt buộc |
| Câu hỏi Y/N | — | do **view** in (cần tên người dùng), **main** đọc câu trả lời | view không được đọc bàn phím, main không được chạm model |
| Kiến trúc | `bo/ui`, Scanner trong `Validator` | MVC theo Guide, Scanner **chỉ ở main**; `MD5` → `MD5Utils` (final, static) | luật thầy |
| MD5 | repository băm (`MD5Utils.hash` trong `addAccount`/`isPasswordCorrect`) | **Main** băm rồi mới đặt vào DTO; repository chỉ lưu/so chuỗi băm | tờ giấy 1.1: *mã hóa thực hiện ở Main* |
| Validate | repository gọi `Validation` trong `addAccount`/`changePassword` | **Main** gọi `Validation` (`inputAccount`, `inputNewPassword`) | tờ giấy 1.1: *Validate thực hiện ở Main* |
| Thứ tự lỗi khi **một lần gõ sai nhiều ô** | Add: "username đã tồn tại" báo trước lỗi phone/email/DOB; đổi mật khẩu: "old password sai" báo trước "new rỗng"/"không khớp" | Add: lỗi định dạng (Main) báo trước, "đã tồn tại" (repository) báo sau; đổi mật khẩu: "new rỗng"/"không khớp" (Main) trước, "old sai" (repository) sau | hệ quả bắt buộc của việc dời validate về Main — kiểm cần dữ liệu thì chỉ repository làm được. Gõ sai **một** ô thì màn hình y như cũ (10 kịch bản `verify.py` không đổi) |
| View | `setAccount(dto)` + `display()` + `showMessage(String)` (2 lần render trong `login`) | `setResponseDTO(dto)` + `display()` không tham số, **1 lần/luồng** | tờ giấy 1.1: *View nhận qua thuộc tính*, *render 1 lần/luồng* |
| Luồng Login | `Main.login` gọi `controller.login` rồi `controller.changePassword` | `case MENU_LOGIN` → `login`; menu con Y/N `case YES` → `changePassword` | *Mỗi luồng tính là 1 switch - case* |
| Tên | `accounts`, `digest`, `b`, `withX` | `accountList`, `digestArray`, `digestByte`, `setX` | tờ giấy 1.5 (đuôi `List`/`Array`), 1.4 (method bắt đầu bằng động từ) |
| `Main` | `public class Main` | `public final class Main` + `private Main()` | tờ giấy 3.4 |

---

## 10. Tờ checklist 25 mục — bài này đạt thế nào

| Mục | Chỗ trong code |
|---|---|
| **1.1** MVC + repository | `repository/AccountRepository` (bắt buộc có repository); `AccountController` không import `model`; `AccountView` nhận `responseDTO` qua setter, `display()` không tham số, gọi **1 lần/luồng**; `Main` nhập + validate + **băm MD5** (`MD5Utils.hash` trong `inputAccount`, `inputLogin`, `inputNewPassword`); mỗi switch-case gọi controller 1 lần (menu con Y/N là switch riêng) |
| **1.3 / 1.4** | `MD5Utils`, `Validation` là danh từ; builder `setId` … `setDob` (động từ), không còn `withX` |
| **1.5** | `accountList`, `digestArray`, `digestByte`; `id`/`nextId`/`FIRST_ID` viết `Id` (không `ID`) |
| **2.6 / 3.7** | biến local ở đầu block và khởi tạo luôn: `int choice = 0;`, `String line = "";`, 7 chuỗi `""` đầu `Main.inputAccount`, `Date dob = null;` trong `Validation.getDob`, `MessageDigest digester = null;` trong `MD5Utils.hash` |
| **2.8** | 1 dòng trống trước mọi comment (cả comment của field trong `Message`/`Constants`/DTO/model), sau vùng khai báo biến, sau `}` của mỗi khối |
| **3.3** | `if ((choice < min) \|\| (choice > max))` trong `Validation.getChoice` |
| **3.4** | `public final class Main` + `private Main()`; `Validation`, `MD5Utils`, `Constants`, `Message` cũng `final` + private constructor |
| **3.8** | không `String +=`; `Account.toString()` dùng `String.format(Constants.ACCOUNT_FORMAT, …)`, hex dùng `StringBuilder` |
