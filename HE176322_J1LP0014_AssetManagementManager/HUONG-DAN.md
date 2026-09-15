# J1.L.P0014 — Asset Management (chương trình của **Manager**)

> **Bài dài (Long Assignment)** · 500 LOC. Đề chia LOC theo chức năng: **Approve** chiếm tới 150 LOC.
> Muốn lấy trọn điểm cần:
> - 4 file `.dat` đọc/ghi được thật;
> - **đăng nhập bằng MD5**;
> - **chặn quyền** (chỉ Manager dùng được chức năng 3–6);
> - duyệt yêu cầu mượn: kiểm tồn kho, ghi đúng **3 file**;
> - giải thích được **kế thừa + đa hình** (đề: *"Must implement the polymorphism properties"*).
>
> Bài anh em: **J1.L.P0015** (chương trình của Employee) dùng **cùng 4 file và cùng model**. Làm bài này trước thì P0015 nhanh gấp đôi.

| | |
|---|---|
| Loại / LOC | Long Assignment · 500 LOC |
| Project | `HE176322_J1LP0014_AssetManagementManager` |
| Chạy | NetBeans: **File ▸ Open Project** → **F6**. 4 file `asset.dat`, `employee.dat`, `request.dat`, `borrow.dat` nằm cạnh `build.xml` |
| Tài khoản thử | Manager `E160052` / `123456` · Employee `E160001` / `123456` (mọi mật khẩu mẫu đều là `123456`) |
| Lớp chạy | `main.Main` |
| Kiểm tự động | `python3 _tools/verify.py --netbeans J1LP0014` → 2 kịch bản × 2 locale |

---

## 1. Đề bài nói gì

| Function | Đề yêu cầu | Login? | Bài này |
|---|---|---|---|
| 0 · Cấu trúc | *"Classes, abstract classes, Interfaces"*; **assetID, employeeID không đổi sau khi tạo**; **phải có đa hình** | — | mục 2.1 |
| 1 · Login | nhập employeeID + password → **"Successfully"** hoặc **"Incorrect id or password"** | — | `AuthService.login` (so MD5) |
| 2 · Search | tên **chứa** chuỗi, in đủ thông tin, **giảm dần** | không | `AssetService.searchByName` |
| 3 · Create | submenu, **kiểm ràng buộc**, thêm vào collection **và ghi asset.dat**, hỏi tiếp | **Manager** | `AssetService.createAsset` |
| 4 · Update | nhập id; không có → **"Asset does not exist"**; **bỏ trống = giữ**; in kết quả | **Manager** | `AssetService.updateAsset` |
| 5 · Approve | in danh sách request → chọn rID → **đủ hàng?** thiếu thì báo lỗi; đủ thì: **thêm borrow.dat**, **xoá dòng request.dat**, **trừ tồn asset.dat** | **Manager** | `ApproveService.approveRequest` |
| 6 · Show borrow | in `borrow.dat` | **Manager** | `ApproveService.getBorrows` |
| 7 | Quit | — | menu 7 |

**4 file dữ liệu** (đề cho sẵn, bài ghi lại **đúng dạng** `cột, cột, cột`):

| File | Cột | Ví dụ |
|---|---|---|
| `asset.dat` | assetID, name, color, price, weight, quantity | `A001, Samsung projector, White, 500.0, 3.2, 10` |
| `employee.dat` | employID, name, birthdate, **role**, sex, **password (MD5)** | `E160052, Hoa Doan, 05/06/1990, MA, male, e10adc39…` |
| `request.dat` | rID, assetID, employeeID, quantity, requestDateTime | `R001, A001, E140449, 1, 23-12-2021 13:17:56` |
| `borrow.dat` | bID, assetID, employeeID, quantity, borrowDateTime | `B001, A001, E160001, 1, 23-12-2021 15:13:46` |

---

## 2. Kiến thức cần biết

### 2.1 Cấu trúc dữ liệu (Function 0) — kế thừa, abstract, interface, đa hình

```
          «interface» Identifiable { getId() }
            ▲              ▲                 ▲
   «abstract» Person     Asset      «abstract» Transaction
      ▲          ▲                      ▲            ▲
   Employee   Manager                Request       Borrow
```

| Đề bắt | Ở đâu | Giải thích |
|---|---|---|
| **Interface** | `model/Identifiable` | mọi dòng của 4 file đều "có id". Nhờ vậy **một** lớp `FileRepository<T extends Identifiable>` đọc/ghi được cả 4 file |
| **Abstract class** | `Person`, `Transaction` | không có "người chung chung" (phải là Employee hoặc Manager); không có "giao dịch chung chung" (phải là Request hoặc Borrow) |
| **Đa hình** | `Person.canManage()`, `getRole()`, `getTitle()` | `Manager.canManage()` trả `true`, `Employee` trả `false`. `AuthService.checkManager` chỉ hỏi `currentUser.canManage()` và **đối tượng tự trả lời** |
| **Đa hình (2)** | `FileRepository.parse/format` | `load()` gọi `parse(parts)`; chạy bản của `AssetRepository`, `EmployeeRepository`… tuỳ kho |
| **Id không đổi** | `Asset`, `Person`: **không có** `setAssetID`/`setEmployeeID` | chỉ constructor gán được. `request.dat` và `borrow.dat` trỏ vào id này; đổi id thì các dòng đó mồ côi |

### 2.2 MD5 — mật khẩu trong file không phải chữ thật

`e10adc3949ba59abbe56e057f20f883e` là **MD5 của `123456`**. Băm chỉ đi được một chiều, không giải ngược được, nên đăng nhập làm như sau:

```java
MD5Utils.hash(passwordGõ).equalsIgnoreCase(person.getPassword())   // so HAI mã băm
```

| Câu hỏi | Trả lời |
|---|---|
| Sao `String.format("%02x", b)`? | mỗi byte thành **2** chữ số hex; byte 10 phải là `0a`. Dùng `Integer.toHexString` sẽ ra `a`, mã băm thiếu ký tự và không khớp |
| Sao id sai và mật khẩu sai cùng **một** câu? | đúng đề (*"Incorrect id or password"*), và **an toàn**: báo riêng "sai mật khẩu" là cho người lạ biết id nào tồn tại |
| MD5 có an toàn không? | Không (đã bị phá từ lâu); dùng vì dữ liệu mẫu của đề là MD5. Nói được câu này là điểm cộng |

### 2.3 Đọc/ghi file — `FileRepository` (Template Method)

| Bước | Ai làm | Code |
|---|---|---|
| mở file, đọc từng dòng, bỏ dòng trống, tách cột, `trim` | **lớp cha** `FileRepository.load()` (`final`) | viết **một lần** cho 4 file |
| một dòng → một đối tượng | **lớp con** `parse(parts)` | `AssetRepository`: `new Asset(…)`; `EmployeeRepository`: cột role `MA` → `new Manager`, còn lại `new Employee` |
| một đối tượng → một dòng | **lớp con** `format(item)` | `String.join(", ", …)` |
| ghi | lớp cha `save()` | **ghi đè** cả file (không nối đuôi) |

Luật chung:
- **Thiếu file = kho rỗng**, không báo lỗi.
- **Dòng hỏng thì bỏ riêng dòng đó**, các dòng khác vẫn đọc.
- **Mỗi thay đổi ghi file ngay** (`add`, `update`, `remove`). Lỡ tắt cửa sổ cũng không mất gì.

### 2.4 Id mới — "số lớn nhất + 1", không phải "số dòng + 1"

`request.dat` mẫu có R001, R002, R003, **R007** (4 dòng). Nếu lấy `size() + 1` thì ra R005; xoá vài dòng rồi sẽ sinh ra **id trùng**.
`FileRepository.nextId("B")` quét mọi id, lấy số lớn nhất (B007) rồi cộng 1, được **B008**.

### 2.5 Duyệt yêu cầu — 3 file đổi cùng lúc, thứ tự có chủ đích

```
Request R001 (A001 × 1) ─► asset A001 còn đủ? ── không ─► "Not enough stock: A001 has 0 left but request R001 needs 1."
                                  │ có
                                  ▼
  ① borrow.dat  thêm   B008, A001, E140449, 1, <giờ hiện tại>
  ② asset.dat   A001:  10 → 9
  ③ request.dat xoá    R001
```

| Nếu máy tắt giữa chừng sau bước… | Hậu quả |
|---|---|
| ① | có borrow, tồn chưa trừ, request còn: **thấy được** trong danh sách mượn, sửa được |
| ② | chỉ còn request "treo": manager thấy, không duyệt lại |
| Nếu làm **ngược** (xoá request trước) | mất request mà chưa ai mượn, **không để lại dấu vết** — tệ nhất |

Giờ mượn: `LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"))`, đúng dạng file mẫu.

### 2.6 Ràng buộc từng ô

| Ô | Luật | Thông báo |
|---|---|---|
| asset id | chữ **A** + 3 số, không trùng (gõ `a003` vẫn nhận, lưu `A003`) | `Asset id must be the letter A and 3 digits, for example A003.` · `Asset A001 already exists.` |
| name / color | 1–40 ký tự, **không dấu phẩy** (dấu phẩy là ký tự phân cách cột) | `Name must be …` / `Color must be …` |
| price / weight | số **> 0** (chặn cả `NaN`, `Infinity`) | `Price must be a number greater than 0.` |
| quantity | số **nguyên** 0…1 000 000 (**0 được**: mọi chiếc đang cho mượn thì tài sản vẫn tồn tại) | `Quantity must be a whole number from 0 to 1000000.` |
| employee id / request id | 1–10 ký tự, không cách, không phẩy | `… must be 1 to 10 characters without spaces.` |
| menu | 1–7 | `Please choose from 1 to 7.` |

---

## 3. Thiết kế

```
HE176322_J1LP0014_AssetManagementManager/
├── asset.dat  employee.dat  request.dat  borrow.dat     dữ liệu mẫu của đề
└── src/
    ├── constants/  Message, Constants              câu chữ; menu, file, cột, regex, giới hạn
    │               TextField, NumberField           «enum» luật từng ô (prompt + regex/khoảng + lỗi)
    ├── model/      Identifiable                     «interface» getId()
    │               Person ← Employee, Manager       «abstract» + đa hình canManage/getRole/getTitle
    │               Asset
    │               Transaction ← Request, Borrow    «abstract»
    ├── dto/        LoginRequestDTO, LoginResponseDTO
    │               AssetRequestDTO, AssetResponseDTO
    │               TransactionRequestDTO, TransactionResponseDTO
    ├── repository/ FileRepository<T>                «abstract» Template Method: load/save/find/add/update/remove/nextId
    │               AssetRepository, EmployeeRepository, RequestRepository, BorrowRepository
    ├── service/    AuthService                      Function 1 + phiên đăng nhập + checkManager()
    │               AssetService, AssetNameComparator   Function 2, 3, 4 (Strategy: tên Z→A)
    │               ApproveService                   Function 5, 6
    ├── controller/ ManagerController                Facade + lắp ráp; mọi hàm 3–6 gọi checkManager()
    ├── view/       AssetView                        bảng tài sản, bảng request/borrow, câu thông báo
    ├── utils/      Validation, FileUtils, MD5Utils
    └── main/       Main                             menu + Scanner
```

| Lớp | Làm gì | Không được làm |
|---|---|---|
| `Main` | menu, **đọc bàn phím**, gói DTO | import model/view/service/repository |
| `ManagerController` | lắp ráp; **chặn quyền** trước mỗi chức năng 3–6; DTO → service → view | Scanner, `System.out`, import model |
| `AuthService` | nhớ **ai đang đăng nhập** (`currentUser`) | in ra |
| `AssetService`, `ApproveService` | luật, đổi model ⇄ DTO, tra tên cho bảng | in ra, đọc phím |
| `*Repository` | đọc/ghi 4 file | kiểm luật nghiệp vụ |
| `Person`, `Asset`, … | mô tả dữ liệu, tự trả lời `canManage()` | in, đọc file |

**Luồng chặn quyền** (chức năng 3, 4, 5, 6):

```
Main: in tiêu đề → controller.checkManager()          ← hỏi TRƯỚC khi bắt gõ gì
          └─► AuthService: currentUser == null  → "You must login first."
                           !currentUser.canManage() → "Nguyen Hong Hiep is not a manager. …"
      rồi mới hỏi các ô → controller.createAsset(dto)
          └─► checkManager() LẦN NỮA (controller không tin Main) → service → view
```

### 3.1 Design Pattern trong bài

| Pattern | Ở đâu | Name · Problem · Solution · Consequences |
|---|---|---|
| **Template Method** (Behavioral) — pattern chính | `FileRepository` | **Problem:** 4 file, việc đọc/ghi giống hệt nhau, chỉ khác "một dòng thành đối tượng gì". **Solution:** lớp cha giữ khung `load()`/`save()` (`final`), lớp con điền `parse()`/`format()`. **Consequences:** ✅ sửa cách đọc (bỏ dòng trống, bỏ dòng hỏng) **một lần** cho 4 file. ❌ phải hiểu generic `<T extends Identifiable>` |
| **Factory Method** | `EmployeeRepository.parse` | cột role quyết định `new Manager(…)` hay `new Employee(…)`: **chỗ duy nhất** so chuỗi role. Sau đó cả chương trình chỉ hỏi `canManage()` |
| **Strategy** | `AssetNameComparator` qua kiểu `Comparator<Asset>` | đổi thứ tự search = thay class |
| **Facade** | `ManagerController` | Main thấy 10 hàm, không biết có 3 service và 4 kho |
| **Repository** + **DTO** + **MVC** | các package | tách lưu / truyền / hiển thị |

### 3.2 SOLID trong bài

| Chữ | Ở đâu |
|---|---|
| **S** | `AuthService` (ai đăng nhập), `AssetService` (tài sản), `ApproveService` (duyệt), `FileRepository` (file), `MD5Utils` (băm), `AssetView` (in) |
| **O** | thêm vai trò "Director" = lớp con `Person` mới + 1 nhánh `parse`; `checkManager` **không sửa** |
| **L** | mọi chỗ nhận `Person` chạy đúng với `Employee` lẫn `Manager`; mọi `FileRepository<T>` dùng như nhau |
| **I** | `Identifiable` chỉ 1 hàm — đúng thứ `FileRepository` cần |
| **D** | 3 service nhận repository **qua constructor**; `ManagerController` là nơi duy nhất `new` repository và dùng **chung một kho** cho các service (AssetService và ApproveService cùng thấy tồn kho) |

---

## 4. Code từng bước

> Thầy: *"code cái **model trước rồi đến data**"*.

| Bước | File | Việc |
|---|---|---|
| 1 | `model/Identifiable` | interface 1 hàm |
| 2 | `model/Person` → `Employee`, `Manager` | abstract + 3 hàm abstract; **không** setter cho `employeeID` |
| 3 | `model/Asset` | 6 field; **không** setter cho `assetID` |
| 4 | `model/Transaction` → `Request`, `Borrow` | abstract, chỉ getter |
| 5 | `constants/*` | `Message`, `Constants` (tên file, chỉ số cột), `TextField`, `NumberField` |
| 6 | `utils/FileUtils`, `MD5Utils`, `Validation` | đọc/ghi dòng; băm; kiểm từng ô |
| 7 | `repository/FileRepository` | `load/save/findById/findAll/isEmpty/add/update/remove/nextId` |
| 8 | 4 repository con | `parse` + `format` |
| 9 | `dto/*` | JavaBean; `AssetRequestDTO` dùng `Double`/`Integer` (null = giữ) |
| 10 | `service/AuthService` | `loadEmployees`, `login`, `checkManager` |
| 11 | `service/AssetNameComparator`, `AssetService` | search, `checkNewId`, create, find, update |
| 12 | `service/ApproveService` | `getRequests`, `approveRequest` (3 file), `getBorrows` |
| 13 | `view/AssetView` | `displayAssets`, `displayAsset`, `displayRequests`, `displayBorrows`, `showMessage` |
| 14 | `controller/ManagerController` | lắp ráp + `loadData` + 10 hàm |
| 15 | `main/Main` | menu + 5 luồng + các hàm `input*` |

**Bẫy hay gặp**

1. So **chữ** mật khẩu với file: không bao giờ khớp, vì file lưu mã băm.
2. Quên `trim()` sau `split(",")`: `" Samsung projector"` có dấu cách đầu, tìm id `" A001"` không ra.
3. `nextId` bằng `size() + 1` (mục 2.4).
4. Trừ tồn **trước** khi kiểm: tồn thành số âm.
5. Chặn quyền chỉ ở Main: gọi thẳng controller là lọt. Bài này chặn **cả hai** nơi.
6. `sc.nextLine()` trong `try`: khi hết input, vòng hỏi lại quay vô hạn.

---

## 5. Test trước khi gọi thầy

> Thầy: *"test tất cả các happy case cũng như hiển thị đủ các message validation"*.

| # | Chọn | Gõ | Phải thấy |
|---|---|---|---|
| 1 | 3 / 4 / 5 / 6 khi chưa login | — | `You must login first.` (chưa hỏi gì) |
| 2 | 1 | `E160052` / `000000` | `Incorrect id or password` |
| 3 | 1 | `E160001` / `123456` → rồi 3 | `Successfully` · `Welcome, Nguyen Hong Hiep (Employee).` → `… is not a manager. …` |
| 4 | 1 | `e160052` / `123456` | `Successfully` · `Welcome, Hoa Doan (Manager).` (id gõ thường vẫn nhận) |
| 5 | 2 | `pro` / `zzz` / trống | Samsung projector, Macbook pro 2016 (**tên Z→A**) / `No asset found.` / `Search text must not be empty.` |
| 6 | 3 | id `A001` / `A3` | `Asset A001 already exists.` / lỗi dạng id — hỏi lại id |
| 7 | 3 | tên trống; màu `Bl,ack`; giá `abc`, `0`, `NaN`; nặng `-1`; số lượng `-2`, `2.5` | từng câu lỗi ở mục 2.6 |
| 8 | 3 | đủ ô đúng | `Asset A003 has been created.` + bảng; `Create another asset? (Y/N):` → `x` → `Please enter Y or N.` |
| 9 | 4 | `A999` | `Asset does not exist` |
| 10 | 4 | `a003` → trống, `White`, `-5` rồi trống, trống, `6` | chỉ màu và số lượng đổi; `Asset A003 has been updated.` |
| 11 | 5 | `R999` | `Request R999 does not exist.` |
| 12 | 4 → A001 quantity `0`, rồi 5 → `r001` | — | `Not enough stock: A001 has 0 left but request R001 needs 1.` |
| 13 | 4 → A001 quantity `10`, rồi 5 → `R001` | — | `Request R001 has been approved as borrow B008.`; `asset.dat` A001 còn 9; R001 biến khỏi `request.dat` |
| 14 | 6 | — | 5 dòng, dòng **B008** mang **giờ hiện tại** |
| 15 | 5 cho tới hết | — | `There is no request to approve.` |
| 16 | login sai khi đang là Manager, rồi 6 | — | `You must login first.` (đăng nhập sai = đăng xuất) |
| 17 | menu `9` / `abc` | — | `Please choose from 1 to 7.` |

---

## 6. Debug

| Việc | Cách làm |
|---|---|
| MD5 | breakpoint trong `AuthService.login` → Variables: `person.password` và giá trị `MD5Utils.hash(...)` phải **giống hệt** |
| Đa hình | breakpoint `if (!currentUser.canManage())` → **F7**: vào `Employee.canManage` hoặc `Manager.canManage` tuỳ ai đăng nhập |
| Template Method | breakpoint `items.add(parse(parts));` trong `FileRepository.load` → **F7**: vào `parse` của đúng kho đang đọc |
| Duyệt | breakpoint `borrowRepository.add(borrow);` → **F8** ba lần, sau mỗi lần mở file `.dat` tương ứng xem đổi |
| Id mới | breakpoint trong `nextId` → xem `max` lên 7 rồi trả `B008` |

---

## 7. Câu hỏi thầy hay hỏi

### OOP

| Câu hỏi | Trả lời mẫu |
|---|---|
| Đa hình ở đâu? (đề bắt) | `Person.canManage()`: `AuthService` gọi qua kiểu `Person`, **lúc chạy** mới biết là `Employee` (false) hay `Manager` (true). Thêm: `FileRepository.load()` gọi `parse()`, chạy bản của từng kho. |
| Abstract class khác interface? | `Person`/`Transaction` có **field và code chung**, chỉ `extends` một. `Identifiable` chỉ là **hợp đồng** "có id"; `Asset`, `Person`, `Transaction` cùng `implements` dù không họ hàng. |
| Làm sao id "không đổi sau khi tạo"? | **Không viết setter.** Chỉ constructor gán; ngoài lớp không có cách nào đổi. |
| Sao role không phải một field? | `getRole()` do **lớp con** trả lời (`Manager` → `MA`), nên file không bao giờ ghi một Manager thành `EM`. |
| Sao JavaBean có constructor rỗng mà id lại không có setter? | Thầy bắt model là JavaBean (constructor rỗng public). Id là thuộc tính **chỉ đọc**, vẫn đúng chuẩn JavaBean. |

### Collection, file, thuật toán

| Câu hỏi | Trả lời mẫu |
|---|---|
| `<T extends Identifiable>` nghĩa là gì? | `FileRepository` làm việc với **bất kỳ** kiểu T nào, miễn T có `getId()`, nên `findById` gọi `item.getId()` được. |
| `ArrayList<? extends Transaction>` trong `ApproveService`? | Hàm nhận **hoặc** list Request **hoặc** list Borrow: một hàm in cho cả hai bảng. |
| Độ phức tạp duyệt? | Tìm request O(n), tìm asset O(m), ghi 3 file O(tổng số dòng). |
| Hai người cùng mở chương trình? | File không khoá; người ghi sau đè người ghi trước. Đề không yêu cầu; muốn chặn phải khoá file hoặc dùng CSDL. |
| Ngày giờ lấy thế nào? | `LocalDateTime.now()` + `DateTimeFormatter("dd-MM-yyyy HH:mm:ss")`, đúng dạng file mẫu. |

### Access modifier, static, tham số

| Câu hỏi | Trả lời mẫu |
|---|---|
| `static` ở đâu? | `Validation`, `FileUtils`, `MD5Utils` (utils); hằng trong `Message`/`Constants`; **hàm** trong `Main`. Không biến static. |
| `protected` ở đâu? | constructor của `Person`, `Transaction`, `FileRepository` (chỉ lớp con gọi); `parse`/`format` (bước lớp con điền). |
| `final` trên `load()`/`save()`? | lớp con **không được** đổi khung đọc/ghi; chỉ được điền `parse`/`format`. |
| Hàm nào quá 2 tham số? | Chỉ `Validation.getChoice(input, min, max)` (utils được 3) và các **constructor** (`Asset` 6, `ApproveService` 4, `NumberField` 6 — được miễn). |

---

## 8. Thầy đổi yêu cầu tại chỗ

| Thầy bảo | Sửa file | Không phải đụng |
|---|---|---|
| Thêm **xoá tài sản** | `AssetService.deleteAsset` (**chặn nếu còn request/borrow trỏ vào**) + `ManagerController` + 1 case menu | repository (đã có `remove`) |
| Search theo **giá** giảm dần | class `AssetPriceComparator` + 1 dòng constructor `AssetService` | mọi file khác |
| Thêm vai trò **Director** cũng được quản lý | `model/Director extends Person` (`canManage` = true) + 1 nhánh trong `EmployeeRepository.parse` | `AuthService`, controller, main |
| Đổi mật khẩu | `AuthService.changePassword` → `person.setPassword(MD5Utils.hash(mới))` + `employeeRepository.update(person)` | model |
| Từ chối (reject) request | `ApproveService.rejectRequest` → `requestRepository.remove` | các file khác |
| Hiện tồn kho sau khi duyệt | `ApproveService.approveRequest` trả thêm tồn + 1 `Message` | repository |

---

## 9. Chỗ khác với đề / bản cũ

| Chỗ | Đề / bản cũ | Bài này | Lý do |
|---|---|---|---|
| Quit | *"7. Others- Quit"* (gõ gì khác cũng thoát) | **7. Quit**, số khác báo `Please choose from 1 to 7.` | gõ nhầm không thoát ngang; mọi thay đổi đã ghi file nên thoát không mất gì |
| Search "descending" | không nói giảm theo gì (bản cũ: theo giá) | **tên Z→A** | kết quả của một phép tìm theo tên; đồng bộ với L.P0013 |
| Login sai khi đang đăng nhập | bản cũ: giữ phiên cũ | **đăng xuất** | người đứng máy vừa gõ sai không được dùng tiếp quyền Manager |
| Id trùng khi tạo | bản cũ: báo rồi hỏi "Create another?" | báo và **hỏi lại id** | không bắt chọn lại menu |
| Prompt khi sửa | bản cũ: `Name [Dell projector]:` | `New name:` sau khi in dòng tài sản | hàm nhập 2 tham số (luật thầy) |
| Thông báo duyệt | bản cũ kèm tồn còn lại | `Request R001 has been approved as borrow B008.` | đề: *"After approve, the program returns to the main screen"* |
| Tạo file mẫu | bản cũ: lớp `SampleData` tự ghi | **4 file `.dat` kèm sẵn** trong project | ít một lớp; thiếu file thì kho rỗng, không lỗi |
| Dòng hỏng trong file | bản cũ: văng lỗi | bỏ dòng đó | đề: mọi lỗi phải được xử lý |
| Kiểm tự động | 2 kịch bản tham chiếu nối nhau qua file | 2 kịch bản riêng, giờ mới tạo hiện là `<NOW>` | `verify.py` chạy mỗi kịch bản trong thư mục mới |
| Kiến trúc | `entity/bo/ui`, Scanner static, controller in ra, `List<T>` | MVC Guide + Template Method + Factory Method + Strategy | luật thầy |
