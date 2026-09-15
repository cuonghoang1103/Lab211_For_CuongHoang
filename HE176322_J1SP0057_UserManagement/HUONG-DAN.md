# J1.S.P0057 — User Management System

> Bài **file + Collection**: tài khoản nằm trong `user.dat`, lúc khởi động được **nạp vào một
> `ArrayList`**, đăng nhập **tìm trong ArrayList**, tạo mới thì **ghi thêm vào cuối file**. Bốn ý này
> chính là 5 dòng **MARKING** của đề.

| | |
|---|---|
| Loại / LOC | Short Assignment · 56 LOC · 1 slot |
| Project | `HE176322_J1SP0057_UserManagement` |
| Chạy | NetBeans: **File ▸ Open Project** → chọn thư mục → **F6** |
| Lớp chạy | `main.Main` |
| File dữ liệu | `user.dat` ở **gốc project** — có sẵn tài khoản ví dụ của đề `NghiaNV1 space@123` |
| Kiểm tự động | `python3 _tools/verify.py --netbeans J1SP0057` → 3 kịch bản × 2 locale |

---

## 1. Đề bài nói gì

- Menu 3 mục: **Create a new account · Login system · Exit**.
- **Create**: nhập username + password → kiểm hợp lệ → **ghi thêm vào cuối `user.dat`** (file có rồi thì ghi tiếp).
- **Login**: nhập username + password → đúng thì `Login successful!`, sai thì `Invalid user name or password`.
- Username **≥ 5 ký tự, không dấu cách**, không trùng; password **≥ 6 ký tự, không dấu cách** (cả khi login).

Màn hình đề (chép đúng chữ):

```
====== USER MANAGEMENT SYSTEM ======
1. Create a new account
2. Login system
3. Exit
> Choose:

Enter Username: 12
You must enter least at 5 character, and no space!
Enter Username: NghiaNV1
Enter Password: 12
You must enter least at 6 character, and no space!
Enter Password: space@123
Login successful!
```

**Đề bắt buộc** (Guidelines + MARKING):

| Thứ | Đề viết | Bài này đặt ở |
|---|---|---|
| `addAccount(Account acc) throws Exception` | *"Student should implement methods"* | `AccountRepository.addAccount(AccountRequestDTO)` — giữ **tên**, `void`, `throws` |
| `Account find(Account acc) throws Exception` | *"Student should implement methods"* | `AccountRepository.find(AccountRequestDTO)` → trả `AccountResponseDTO` |
| MARKING *"Checking existing of user.dat file before inserting"* | | `addAccount`: `if (!FileUtils.isFileExist(...)) FileUtils.createFile(...)` |
| MARKING *"Adding a user account into user.dat file"* | | `FileUtils.appendLine` (mở file chế độ **append**) |
| MARKING *"Loading user account from user.dat into Collection"* | | `AccountRepository.loadData` → `ArrayList<Account>` |
| MARKING *"Search user name and password into Collection"* | | `AccountRepository.find` duyệt `accountList` |
| Thông báo | `You must enter least at 5/6 character, and no space!` · `Login successful!` · `Invalid user name or password` | `constants/Message` — **chép đúng từng chữ** |

---

## 2. Kiến thức cần biết

### 2.1 Kiểm "≥ n ký tự và không dấu cách"

```java
input.length() < minLength || !input.matches("\\S*")   // \S = mọi ký tự KHÔNG phải khoảng trắng
```

| Gõ | length | có khoảng trắng? | Username (≥5) | Password (≥6) |
|---|---|---|---|---|
| `12` | 2 | không | ❌ ngắn | ❌ ngắn |
| `abcd` | 4 | không | ❌ ngắn | ❌ |
| `abcde` | 5 | không | ✅ (biên) | ❌ |
| `ab cde` | 6 | **có** | ❌ | ❌ |
| ` 12345` | 6 | **có** (đầu dòng) | ❌ | ❌ |
| `space@123` | 9 | không | ✅ | ✅ |

**Không `trim()` trước khi kiểm** — nếu trim thì ` 12345` thành `12345` và lọt qua, trái đề *"no spaces"*.

### 2.2 Định dạng `user.dat` — mỗi dòng một tài khoản

```
NghiaNV1 space@123
HoangAn abc123
```

Ngăn cách bằng **1 dấu cách** — an toàn **chính vì** username/password bị cấm có dấu cách. Nạp lại
bằng `line.trim().split(" ")`: đúng 2 phần thì nạp, khác thì bỏ qua dòng hỏng.

### 2.3 Ghi thêm vào cuối file (append)

| API | Dùng làm gì |
|---|---|
| `new File(path).exists()` / `isFile()` | kiểm `user.dat` có chưa (MARKING) |
| `new File(path).createNewFile()` | tạo file rỗng lần đầu |
| `new FileOutputStream(path, true)` | **`true` = append**: ghi tiếp cuối file, không xoá dòng cũ |
| `BufferedReader.readLine()` | đọc từng dòng, `null` = hết file |
| `try (...) { }` | tự đóng file kể cả khi lỗi |

### 2.4 Một câu báo lỗi cho cả hai trường hợp login

Sai username hay sai password đều in `Invalid user name or password` — nói rõ "username đúng, sai
password" là **tặng kẻ gian nửa đáp án**. Câu của đề vốn đã cẩn thận như vậy.

---

## 3. Thiết kế

```
HE176322_J1SP0057_UserManagement/
├── user.dat                          dữ liệu (gốc project)
└── src/
    ├── model/       Account              username, password (JavaBean) + isMatch + toString() = 1 dòng file
    ├── dto/         AccountRequestDTO    username, password   (main ──► controller)
    │                AccountResponseDTO   username             (controller ──► view)
    ├── repository/  AccountRepository    ArrayList<Account> + loadData · addAccount · find
    ├── controller/  AccountController    điều hướng repository ↔ view (Facade)
    ├── view/        AccountView          in "Login successful!" / câu kết quả
    ├── utils/       FileUtils            isFileExist · createFile · readLines · appendLine (static)
    │                Validation           getChoice · getUsername · getPassword (static)
    ├── constants/   Message · Constants
    └── main/        Main                 menu + Scanner
```

| Lớp | Làm gì | Không được làm |
|---|---|---|
| `Main` | vòng menu, **đọc bàn phím**, gói vào DTO | gọi model/view/file, biến static |
| `AccountController` | nhận DTO → gọi repository → đưa kết quả cho view | Scanner, `System.out`, static |
| `AccountRepository` | giữ `ArrayList<Account>`, nạp/thêm/tìm | in ra, đọc bàn phím |
| `FileUtils` | chuyển **dòng chữ** giữa `user.dat` ↔ chương trình | biết "tài khoản" là gì |
| `Account` | mô tả 1 tài khoản, tự so khớp (`isMatch`) | Scanner, printf, static |
| `AccountView` | in kết quả | tính toán |

**Luồng Create:**

```
Main: inputAccount(sc) = inputUsername + inputPassword ──► AccountRequestDTO ──► controller.addAccount(dto)
   controller ──► repository.addAccount(dto)
                    ├─ isExistUsername(...)?  → throw "Username [x] already exists."
                    ├─ !FileUtils.isFileExist("user.dat") → FileUtils.createFile(...)   ← MARKING
                    ├─ FileUtils.appendLine("user.dat", account.toString())             ← ghi CUỐI file
                    └─ accountList.add(account)                                         ← chỉ sau khi file nhận
   controller ──► view.showMessage("Create account successfully!")
Main: catch (Exception e) → in e.getMessage()
```

**Luồng Login:** `controller.login(dto)` → `repository.find(dto)` duyệt `accountList`, `account.isMatch(typed)`
→ trả `AccountResponseDTO` → `view.setAccount(...)` → `view.display()` in `Login successful!`; không thấy → `throw`.

### 3.1 Design Pattern trong bài

| Pattern | Name · Problem · Solution · Consequences |
|---|---|
| **MVC** (kiến trúc — "MVC JSP") | **Problem**: nhập, kiểm, lưu file, in trộn một chỗ. **Solution**: `AccountController` ~ Servlet, `AccountView` ~ trang JSP, `Account` ~ JavaBean; dữ liệu đi qua DTO. **Consequences**: đổi cách in chỉ sửa View; nhiều lớp hơn. |
| **Facade** (Structural) | **Problem**: `Main` sẽ phải biết danh sách, file `user.dat`, view và thứ tự gọi. **Solution**: `AccountController` là **một cửa**: `addAccount(dto)`, `login(dto)`. **Consequences**: ✅ `Main` không import repository/file; đổi `user.dat` sang file nhị phân không đụng `Main`. ❌ controller phải giữ vai điều hướng, không ôm nghiệp vụ. |
| **Repository** (mẫu dữ liệu, không thuộc 23 GoF) | **Problem**: danh sách trong bộ nhớ và `user.dat` phải khớp. **Solution**: chỉ `AccountRepository` đụng cả hai; thêm vào list **sau khi** file ghi xong. **Consequences**: đổi cách lưu chỉ sửa repository (+ `FileUtils`). |

> Không có họ đối tượng/thuật toán nên **không nhét** Strategy/Factory; `Account` chỉ 2 trường nên
> không cần Builder (YAGNI — ghi chú slide 26 SOLID).

### 3.2 SOLID trong bài

| Nguyên lý | Ở đâu |
|---|---|
| **S** | `Account` giữ 1 tài khoản · `FileUtils` chỉ đọc/ghi dòng · `AccountRepository` giữ dữ liệu · `AccountView` in · `Validation` kiểm |
| **O** | thêm quy tắc mật khẩu chỉ sửa `Validation`; đổi cách lưu chỉ sửa repository — `Main`, controller đứng yên |
| **D** (một phần) | `Main` chỉ biết `AccountController` + DTO |

---

## 4. Code từng bước — thứ tự nên gõ

> Thầy: *"code cái **model trước rồi đến data**"*.

| Bước | File | Việc | Lưu ý |
|---|---|---|---|
| 1 | `model/Account.java` | 2 field private + ctor rỗng + ctor đủ + get/set + `isMatch` + `toString()` = `username password` | JavaBean · **Alt+Insert** |
| 2 | `dto/AccountRequestDTO.java`, `AccountResponseDTO.java` | JavaBean (Response chỉ có username — mật khẩu không bao giờ đi về phía màn hình) | ctor rỗng `public` |
| 3 | `utils/FileUtils.java` | `isFileExist` · `createFile` · `readLines` · `appendLine` | `final` + ctor `private` + static |
| 4 | `repository/AccountRepository.java` | `ArrayList<Account>` + `loadData` · `isExistUsername` (private) · **`addAccount` · `find`** | **không** `System.out` |
| 5 | `constants/Message.java`, `Constants.java` | câu của đề, `USERNAME_MIN_LENGTH = 5`, `PASSWORD_MIN_LENGTH = 6`, `DATA_FILE`, `SEPARATOR` | gõ dần khi bước trên cần |
| 6 | `view/AccountView.java` | `setAccount` · `display` · `showMessage` | |
| 7 | `controller/AccountController.java` | `loadData` · `addAccount` · `login` | **không** Scanner |
| 8 | `utils/Validation.java` | `getChoice` · `getUsername` · `getPassword` · `checkField` (private) | **không trim** trước khi kiểm |
| 9 | `main/Main.java` | `loadData` 1 lần + menu + `inputUsername/inputPassword/inputAccount` | Scanner **chỉ ở đây** |
| 10 | — | **Alt+Shift+F**, **F6**, đi hết bảng test mục 5 | |

**Bẫy hay gặp:**

1. **`new FileWriter(path)` thiếu `true`** → mỗi lần tạo tài khoản **xoá sạch** file cũ.
2. **`trim()` trước khi kiểm** → ` 12345` lọt qua dù có dấu cách.
3. **Thêm vào list trước khi ghi file** → ghi file hỏng mà list vẫn có, đăng nhập được một tài khoản không tồn tại trong `user.dat`.
4. **Login đọc lại file mỗi lần** → vẫn chạy, nhưng trái MARKING *"Search ... into Collection"*.
5. **`user.dat` để trong `src/`** → NetBeans chạy ở gốc project, không thấy.

---

## 5. Test trước khi gọi thầy

> Bắt đầu với `user.dat` có sẵn `NghiaNV1 space@123`.

| # | Chọn | Gõ | Phải thấy |
|---|---|---|---|
| 1 | 2 | `12` → `NghiaNV1` → `12` → `space@123` | đúng màn hình đề: 2 câu lỗi rồi `Login successful!` |
| 2 | 1 | `ab cde` | `You must enter least at 5 character, and no space!` |
| 3 | 1 | `abcd` | câu lỗi 5 ký tự |
| 4 | 1 | `HoangAn` / `abc 123` | `You must enter least at 6 character, and no space!` |
| 5 | 1 | `HoangAn` / `abc123` | `Create account successfully!` — mở `user.dat` thấy dòng mới **ở cuối** |
| 6 | 2 | `HoangAn` / `abc123` | `Login successful!` |
| 7 | 2 | `HoangAn` / `wrong12` | `Invalid user name or password` |
| 8 | 2 | `Nobody1` / `abc123` | `Invalid user name or password` |
| 9 | 1 | `NghiaNV1` / `another123` | `Username [NghiaNV1] already exists.` |
| 10 | 1 | `abcde` / ` 12345` / `123456` | password có dấu cách đầu bị từ chối; `abcde`/`123456` (biên 5/6) được tạo |
| 11 | menu | `x`, `4`, `0` | `You must input a number.` · `Please choose from 1 to 3.` |
| 12 | 3 | | `Goodbye.` — **F6 lại**, login `HoangAn` vẫn được (đã nạp từ file) |
| 13 | — | xoá `user.dat`, F6, tạo tài khoản | chạy bình thường; `user.dat` được **tạo mới** (nhánh MARKING "checking existing") |

---

## 6. Debug

| Việc | Cách làm |
|---|---|
| Breakpoint | dòng `FileUtils.appendLine(Constants.DATA_FILE, account.toString());` trong `AccountRepository.addAccount` |
| Chạy debug | **Ctrl+F5**, chọn 1, tạo `HoangAn` / `abc123` |
| Quan sát | tab **Variables**: `account` (mở ra xem 2 field), `accountList` — **chưa** có HoangAn |
| Bước | **F7** vào `appendLine` → thấy `new FileOutputStream(path, true)`; **F8** ra ngoài, qua dòng `accountList.add(account)` → list tăng 1 |
| Login | breakpoint ở `if (account.isMatch(typed))` trong `find`, **F8** từng vòng, xem `account` đang so với `typed` |
| Nạp file | breakpoint trong vòng `for` của `loadData`, xem `parts` của từng dòng |

---

## 7. Câu hỏi thầy hay hỏi

### OOP

| Câu hỏi | Trả lời mẫu |
|---|---|
| 4 tính chất OOP ở đâu? | **Đóng gói**: `username`, `password` `private` trong `Account`; `accountList` `private` — chỉ thêm qua `addAccount`. **Kế thừa**: mọi lớp `extends Object`, em ghi đè `toString()`. **Đa hình**: `account.toString()` chạy bản của `Account` (ra `NghiaNV1 space@123`). **Trừu tượng**: `Main` gọi `controller.login(dto)` mà không biết có list hay file phía sau. |
| Sao `isMatch` nằm trong `Account`? | Là **hành vi của chính đối tượng**: "tài khoản này có khớp tài khoản kia không". Guide cho model chứa *"thuộc tính và function của đối tượng"*. |
| Sao `Account` có constructor rỗng? | Thầy dạy **MVC JSP**: model/DTO là **JavaBean** — field `private`, constructor rỗng `public`, get/set. |

### Access modifier, static, kiểu trả về

| Câu hỏi | Trả lời mẫu |
|---|---|
| Field sao `private`? | Không ai sửa thẳng `accountList` hay `password`; thêm tài khoản phải qua `addAccount` — nơi luôn ghi file kèm. |
| `isExistUsername` sao `private`? | Chỉ `addAccount` trong cùng lớp gọi. |
| `loadData/addAccount/find` sao `public`? | `AccountController` (lớp khác) gọi. `isMatch` `public` vì `AccountRepository` gọi. |
| `checkField` trong `Validation` sao `private`? | Chỉ `getUsername/getPassword` dùng; lớp khác gọi 2 hàm có tên rõ nghĩa đó. |
| Sao `FileUtils`, `Validation` `static`? | Không giữ dữ liệu riêng: cùng đường dẫn/chuỗi vào → cùng kết quả. Guide: utils *"phải dùng static method"*, `final`, constructor `private`. |
| **Bỏ `static` thì sao?** | `FileUtils.appendLine(...)` báo lỗi biên dịch. Phải bỏ `private` constructor, tạo `FileUtils fileUtils = new FileUtils();` trong repository rồi gọi `fileUtils.appendLine(...)`. |
| Hàm trong `Main` sao `static`? | `main` là `static`; thầy: *"cấm static với biến, có thể dùng với hàm"* → `Scanner sc` là biến cục bộ, truyền vào hàm. |
| `addAccount` trả `void`? | Đề: *"Return value: The list exceptions"* — thành công không cần giá trị, thất bại báo bằng `throw`. |
| `find` trả gì, sao không trả `Account`? | Đề: trả *"User account"*. Nhưng controller **không được import model** (Guide), nên repository trả `AccountResponseDTO` (chỉ username — mật khẩu không đi về view). |
| `isExistUsername` trả `boolean`? | Nơi gọi chỉ cần có/không. |

### Collection

| Câu hỏi | Trả lời mẫu |
|---|---|
| **Sao `ArrayList` mà không `List`?** | `List` là **interface** (hợp đồng: `add/get/size`…), `ArrayList` là **lớp cài đặt** bằng mảng động — thêm cuối và duyệt nhanh; `LinkedList` cài cùng hợp đồng bằng danh sách liên kết. Em chỉ thêm cuối và duyệt từ đầu, đúng thế mạnh của `ArrayList`, nên khai báo kiểu cụ thể `ArrayList<Account>`. |
| Sao không dùng `HashMap<String, Account>`? | Được — tra username nhanh hơn (O(1) thay vì O(n)). Đề chỉ nói *"Collection"*; vài chục tài khoản thì `ArrayList` đơn giản hơn và giữ đúng thứ tự dòng trong file. Đổi sang Map chỉ sửa repository. |
| Sao không hàm nào 3 tham số? | Thầy: *"không truyền 3 tham số 1 hàm"* → username + password gói trong `AccountRequestDTO`. `getChoice(input, min, max)` là hàm utils theo **mẫu Guide**. |

### Thiết kế / file

| Câu hỏi | Trả lời mẫu |
|---|---|
| Create và Login đều gọi `inputAccount` — sao? | Hai màn hình hỏi **đúng hai câu giống nhau** với cùng quy tắc (đề: login cũng ≥5/≥6) → viết một lần. |
| Sao nạp file 1 lần lúc khởi động? | MARKING: *"Loading user account from user.dat into Collection"* rồi *"Search ... into Collection"*. Tạo mới thì thêm vào **cả** file lẫn list nên list luôn khớp. |
| Tài khoản mới vào list khi nào? | **Sau** khi `appendLine` thành công — file hỏng thì list không đổi. |
| Pattern gì? | **MVC** + controller là **Facade** + mẫu **Repository** — mục 3.1. |
| Mật khẩu lưu dạng chữ thường có an toàn không? | Không — thực tế phải **băm** (MD5/SHA-256, để ở `utils/HashUtils`). Đề không yêu cầu; nếu thầy bảo thì xem mục 8. |

---

## 8. Thầy đổi yêu cầu tại chỗ

| Thầy bảo | Sửa file | Không phải đụng |
|---|---|---|
| **Băm mật khẩu** trước khi lưu | thêm `utils/HashUtils.java` (static, `MessageDigest` SHA-256); `AccountRepository.addAccount` + `find` băm `requestDTO.getPassword()` trước khi tạo `Account` | `Main`, controller, view |
| Báo **"username trùng" ngay** sau khi gõ username | `AccountController.checkExistUsername(dto)` (+ `isExistUsername` thành `public`), `Main.createAccount` gọi sau `inputUsername` | `FileUtils`, `Account` |
| Login in **"Welcome NghiaNV1"** | `Message` thêm câu `%s`; `AccountView.display()` in `account.getUsername()` | repository |
| Thêm menu **đổi mật khẩu** | `Message.MENU`, `Constants`, `AccountRepository.changePassword` (ghi đè cả file), `FileUtils.writeLines`, controller, `case` mới ở `Main` | `Account` |
| Mật khẩu phải có chữ số | `Validation.getPassword` thêm regex; `Message` câu lỗi | mọi file khác |
| Dùng file **nhị phân** (đề cho phép) | chỉ `FileUtils` (`ObjectOutputStream`) + `Account implements Serializable` | `Main`, controller, view |

---

## 9. Chỗ khác với đề / bản cũ

| Chỗ | Đề / bản cũ | Bài này | Lý do |
|---|---|---|---|
| Chữ ký `addAccount(Account acc)` | đề nhận `Account` | `addAccount(AccountRequestDTO)` | Guide: *"Truyền data vào controller thông qua DTO param"*; controller không được import model |
| `Account find(Account acc)` | đề trả `Account` | `AccountResponseDTO find(AccountRequestDTO)` | view chỉ được thấy DTO (Model và View không giao tiếp) |
| Câu login thành công | đặc tả: *"Login successfully"* · màn hình: `Login successful!` | `Login successful!` | **màn hình đề** thắng |
| Câu tạo thành công / trùng username / lỗi file / menu | đề không cho | `Create account successfully!` · `Username [x] already exists.` · `Please choose from 1 to 3.` … | giữ đúng như bản cũ khi đề im lặng |
| Dòng `Loaded N account(s) from user.dat.` | bản cũ in khi khởi động | không in | màn hình đề không có; test dùng `REPLACE_REFERENCE = True` (thêm lý do: run 1–2 của bản cũ dựa vào file run 0 ghi, còn `verify.py` chạy mỗi run trên bản sao sạch) |
| `user.dat` giao kèm | bản cũ: không | có `NghiaNV1 space@123` | chạy được ngay màn hình login của đề; nhánh "chưa có file" thử tay (mục 5 #13) |
| Kiến trúc | bản cũ: `bo/ui/utils`, Scanner trong `Validator` | MVC theo Guide, Scanner **chỉ ở `main`** | luật thầy |
