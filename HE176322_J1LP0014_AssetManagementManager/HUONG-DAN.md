# J1.L.P0014 — Asset Management (chương trình của **Manager**)

> **Bài dài (Long Assignment)** · 500 LOC. Đề chia LOC theo chức năng: **Approve** chiếm tới 150 LOC.
> Muốn lấy trọn điểm cần:
> - 4 file `.dat` đọc/ghi được thật;
> - **đăng nhập bằng MD5**;
> - **chặn quyền** (chỉ Manager dùng được chức năng 3–6);
> - duyệt yêu cầu mượn: kiểm tồn kho, ghi đúng **3 file**;
> - giải thích được **kế thừa + đa hình** (đề: *"Must implement the polymorphism properties"*);
> - đạt **tờ checklist giấy 25 mục** (21/09/2026): có repository, Main tự nhập/validate/đọc file/băm MD5,
>   View nhận `AssetResponseDTO` qua thuộc tính và **render 1 lần cho 1 luồng** (mục 10).
>
> Bài anh em: **J1.L.P0015** (chương trình của Employee) dùng **cùng 4 file và cùng model**. Làm bài này trước thì P0015 nhanh gấp đôi.

| | |
|---|---|
| Loại / LOC | Long Assignment · 500 LOC |
| Project | `HE176322_J1LP0014_AssetManagementManager` |
| Chạy | NetBeans: **File ▸ Open Project** → **F6**. 4 file `asset.dat`, `employee.dat`, `request.dat`, `borrow.dat` nằm cạnh `build.xml` |
| Tài khoản thử | Manager `E160052` / `123456` · Employee `E160001` / `123456` (mọi mật khẩu mẫu đều là `123456`) |
| Lớp chạy | `main.Main` |
| Kiểm tự động | `python3 _tools/verify.py --netbeans J1LP0014` → 3 kịch bản × 2 locale |

---

## 1. Đề bài nói gì

| Function | Đề yêu cầu | Login? | Bài này |
|---|---|---|---|
| 0 · Cấu trúc | *"Classes, abstract classes, Interfaces"*; **assetID, employeeID không đổi sau khi tạo**; **phải có đa hình** | — | mục 2.1 |
| 1 · Login | nhập employeeID + password → **"Successfully"** hoặc **"Incorrect id or password"** | — | `Main` băm MD5 → `AuthService.login` (so 2 mã băm) |
| 2 · Search | tên **chứa** chuỗi, in đủ thông tin, **giảm dần** | không | `AssetService.searchByName` |
| 3 · Create | submenu, **kiểm ràng buộc**, thêm vào collection **và ghi asset.dat**, hỏi tiếp | **Manager** | `AssetService.createAsset` |
| 4 · Update | nhập id; không có → **"Asset does not exist"**; **bỏ trống = giữ**; in kết quả | **Manager** | `AssetService.updateAsset` |
| 5 · Approve | in danh sách request → chọn rID → **đủ hàng?** thiếu thì báo lỗi; đủ thì: **thêm borrow.dat**, **xoá dòng request.dat**, **trừ tồn asset.dat**; *"After approve, the program returns to the main screen"* | **Manager** | `ApprovalService.acceptRequest` |
| 6 · Show borrow | in `borrow.dat` | **Manager** | `ApprovalService.getBorrows` |
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
          «interface» IRecord { getId() }
            ▲              ▲                 ▲
   «abstract» Person     Asset      «abstract» Transaction
      ▲          ▲                      ▲            ▲
   Employee   Manager                Request       Borrow
```

| Đề bắt | Ở đâu | Giải thích |
|---|---|---|
| **Interface** | `model/IRecord` | mọi dòng của 4 file đều "có id". Nhờ vậy **một** lớp `FileRepository<T extends IRecord>` giữ được cả 4 file. Tên bắt đầu bằng **I** (tờ checklist 1.3) |
| **Abstract class** | `Person`, `Transaction` | không có "người chung chung" (phải là Employee hoặc Manager); không có "giao dịch chung chung" (phải là Request hoặc Borrow) |
| **Đa hình** | `Person.canManage()`, `getRole()`, `getTitle()` | `Manager.canManage()` trả `true`, `Employee` trả `false`. `AuthService.checkManager` chỉ hỏi `currentUser.canManage()` và **đối tượng tự trả lời** |
| **Đa hình (2)** | `FileRepository.parse/format` | `loadData(lineList)` gọi `parse(partArray)`; chạy bản của `AssetRepository`, `EmployeeRepository`… tuỳ kho |
| **Id không đổi** | `Asset`, `Person`: **không có** `setAssetId`/`setEmployeeId` | chỉ constructor gán được. `request.dat` và `borrow.dat` trỏ vào id này; đổi id thì các dòng đó mồ côi. Đề viết `assetID`/`employeeID`; tờ checklist 1.5 bắt **`Id`** → field `assetId`/`employeeId`, có comment `// brief: assetID` ngay trên |

### 2.2 MD5 — mật khẩu trong file không phải chữ thật

`e10adc3949ba59abbe56e057f20f883e` là **MD5 của `123456`**. Băm chỉ đi được một chiều, không giải ngược được, nên đăng nhập làm như sau:

```java
// Main (tờ checklist: "mã hoá thực hiện ở Main") - chữ gõ vào không đi đâu nữa
requestDTO.setPassword(MD5Utils.hash(inputText(sc, TextField.PASSWORD)));
// AuthService.login - so HAI mã băm
person.getPassword().equalsIgnoreCase(requestDTO.getPassword())
```

| Câu hỏi | Trả lời |
|---|---|
| Sao `String.format("%02x", b)`? | mỗi byte thành **2** chữ số hex; byte 10 phải là `0a`. Dùng `Integer.toHexString` sẽ ra `a`, mã băm thiếu ký tự và không khớp |
| Sao id sai và mật khẩu sai cùng **một** câu? | đúng đề (*"Incorrect id or password"*), và **an toàn**: báo riêng "sai mật khẩu" là cho người lạ biết id nào tồn tại |
| MD5 có an toàn không? | Không (đã bị phá từ lâu); dùng vì dữ liệu mẫu của đề là MD5. Nói được câu này là điểm cộng |

### 2.3 Đọc/ghi file — Main đọc, `FileRepository` giữ (Template Method)

Tờ checklist 1.1: *"Toàn bộ việc nhập dữ liệu/Validate/**đọc từ file**/mã hóa thực hiện ở Main"*. Nên **đọc** nằm ở Main,
**ghi** nằm ở repository (qua `utils/FileUtils`):

| Bước | Ai làm | Code |
|---|---|---|
| mở file, đọc từng dòng | **Main** `loadData` → `FileUtils.readLines(Constants.ASSET_FILE)` | 4 lần, gói vào `AssetRequestDTO` (`assetLineList`, `employeeLineList`, `requestLineList`, `borrowLineList`) rồi **1 lần** `controller.loadData(requestDTO)` |
| tách cột, `trim`, bỏ dòng sai số cột | **lớp cha** `FileRepository.loadData(lineList)` (`final`) | viết **một lần** cho 4 file |
| một dòng → một đối tượng | **lớp con** `parse(partArray)` | `AssetRepository`: `new Asset(…)`; `EmployeeRepository`: cột role `MA` → `new Manager`, còn lại `new Employee` |
| một đối tượng → một dòng | **lớp con** `format(item)` | `String.join(", ", …)` |
| ghi | lớp cha `saveData()` → `FileUtils.writeLines` | **ghi đè** cả file (không nối đuôi) |

Luật chung:
- **Thiếu file = kho rỗng**, không báo lỗi (`FileUtils.readLines` trả danh sách rỗng).
- **Dòng hỏng thì bỏ riêng dòng đó**, các dòng khác vẫn đọc.
- **Mỗi thay đổi ghi file ngay** (`add`, `update`, `remove`). Lỡ tắt cửa sổ cũng không mất gì.

### 2.4 Id mới — "số lớn nhất + 1", không phải "số dòng + 1"

`request.dat` mẫu có R001, R002, R003, **R007** (4 dòng). Nếu lấy `size() + 1` thì ra R005; xoá vài dòng rồi sẽ sinh ra **id trùng**.
`FileRepository.getNextId("B")` quét mọi id, lấy số lớn nhất (B007) rồi cộng 1, được **B008**.

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

Duyệt xong **không in dòng nào**, quay về menu (đề: *"After approve, the program returns to the main screen"*):
lần render duy nhất của chức năng 5 là **danh sách request** đề bắt hiện trước khi chọn (mục 3, bảng "mỗi luồng").
Muốn thấy kết quả: chọn **6** — dòng B008 mang giờ hiện tại.

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
    ├── model/      IRecord                          «interface» getId()
    │               Person ← Employee, Manager       «abstract» + đa hình canManage/getRole/getTitle
    │               Asset
    │               Transaction ← Request, Borrow    «abstract»
    ├── dto/        AssetRequestDTO                  MỌI thứ Main gửi: 4 danh sách dòng, id + mã băm, từ khoá, 6 ô tài sản, id request
    │               AssetResponseDTO                 MỌI thứ View in: message, assetList, requestList, borrowList
    │               AssetDTO, TransactionDTO, PersonDTO   bản sao 1 dòng / người đăng nhập (controller không chạm model)
    ├── repository/ FileRepository<T>                «abstract» Template Method: loadData/findById/findAll/isEmpty/add/update/remove/getNextId (+ saveData private)
    │               AssetRepository, EmployeeRepository, RequestRepository, BorrowRepository
    ├── service/    AuthService                      Function 1 + phiên đăng nhập + checkManager()
    │               AssetService, AssetNameComparator   Function 2, 3, 4 (Strategy: tên Z→A)
    │               ApprovalService                  Function 5, 6
    ├── controller/ ManagerController                Facade + lắp ráp; mọi hàm 3–6 gọi checkManager()
    ├── view/       AssetView                        field responseDTO + setResponseDTO + display() không tham số
    ├── utils/      Validation, FileUtils, MD5Utils
    └── main/       Main                             final + private ctor; menu, Scanner, validate, đọc 4 file, băm MD5
```

| Lớp | Làm gì | Không được làm |
|---|---|---|
| `Main` | menu, **đọc bàn phím**, **validate** (`Validation`), **đọc 4 file** (`FileUtils`), **băm MD5** (`MD5Utils`), gói `AssetRequestDTO` | import model/view/service/repository |
| `ManagerController` | lắp ráp; **chặn quyền** trước mỗi chức năng 3–6; DTO → service → `AssetResponseDTO` → view **1 lần** | Scanner, `System.out`, import model |
| `AuthService` | nhớ **ai đang đăng nhập** (`currentUser`) | in ra |
| `AssetService`, `ApprovalService` | luật, đổi model → `AssetDTO`/`TransactionDTO`, tra tên cho bảng | in ra, đọc phím |
| `*Repository` | **giữ dữ liệu** (`itemList`) + CRUD đơn giản; tách dòng Main đưa; ghi file qua `FileUtils` | đọc file, kiểm luật nghiệp vụ |
| `AssetView` | in theo `responseDTO` (field), `display()` không tham số | nhận dữ liệu qua tham số |
| `Person`, `Asset`, … | mô tả dữ liệu, tự trả lời `canManage()` | in, đọc file |

**Luồng chặn quyền** (chức năng 3, 4, 5, 6):

```
Main: in tiêu đề → controller.checkManager()          ← hỏi TRƯỚC khi bắt gõ gì (chỉ để kiểm: ném lỗi, không in)
          └─► AuthService: currentUser == null  → "You must login first."
                           !currentUser.canManage() → "Nguyen Hong Hiep is not a manager. …"
      rồi mới hỏi các ô → controller.createAsset(requestDTO)
          └─► checkManager() LẦN NỮA (controller không tin Main) → service → repository → model
              ◄── ArrayList<AssetDTO> ── responseDTO.setAssetList + setMessage
              → assetView.setResponseDTO(responseDTO); assetView.display();   ← 1 lần cho cả luồng
```

**Mỗi luồng (1 `case` ở Main) gọi controller mấy lần, render mấy lần** — tờ checklist 1.1: *"rendering … chỉ được
gọi 1 lần cho 1 luồng xử lý (Mỗi luồng tính là 1 switch - case ở Main)"*:

| Case | Main gọi controller | Render |
|---|---|---|
| 1 Login | `login` | 1: `Successfully` + `Welcome, … (…)` (một `message`) |
| 2 Search | `searchAsset` | 1: bảng |
| 3 Create | `checkManager` *(chỉ để kiểm)* → `checkNewAssetId` *(chỉ để kiểm, hỏi lại tới khi id chưa dùng)* → **`createAsset`** | 1: câu tạo xong + bảng 1 dòng |
| 4 Update | `checkManager` *(chỉ để kiểm)* → `checkAssetExist` *(chỉ để kiểm: đề bắt báo `Asset does not exist` trước khi hỏi giá trị mới)* → **`updateAsset`** | 1: câu sửa xong + bảng 1 dòng (đề: *"print out the result of the updating"*) |
| 5 Approve | `showRequests` *(danh sách đề bắt hiện TRƯỚC khi chọn — render duy nhất)* → **`acceptRequest`** *(không in: về menu)* | 1: danh sách |
| 6 Borrows | `showBorrows` | 1: bảng |

Lần gọi "chỉ để kiểm" nằm trong hàm `input…` của Main (như `checkExistDoctor` của P0055): **ném lỗi, không render**.
`Create another asset? (Y/N)` → **Y** chạy lại **case 3** (không in menu) — mỗi vòng là một luồng riêng.

### 3.1 Design Pattern trong bài

| Pattern | Ở đâu | Name · Problem · Solution · Consequences |
|---|---|---|
| **Template Method** (Behavioral) — pattern chính | `FileRepository` | **Problem:** 4 file, việc tách dòng/ghi giống hệt nhau, chỉ khác "một dòng thành đối tượng gì". **Solution:** lớp cha giữ khung `loadData()` (`final`)/`saveData()`, lớp con điền `parse()`/`format()`. **Consequences:** ✅ sửa cách tách (bỏ dòng trống, bỏ dòng hỏng) **một lần** cho 4 file. ❌ phải hiểu generic `<T extends IRecord>` |
| **Factory Method** | `EmployeeRepository.parse` | cột role quyết định `new Manager(…)` hay `new Employee(…)`: **chỗ duy nhất** so chuỗi role. Sau đó cả chương trình chỉ hỏi `canManage()` |
| **Strategy** | `AssetNameComparator` qua kiểu `Comparator<Asset>` | đổi thứ tự search = thay class |
| **Facade** | `ManagerController` | Main thấy 11 hàm, không biết có 3 service và 4 kho |
| **Repository** + **DTO** + **MVC** | các package | tách lưu / truyền / hiển thị |

### 3.2 SOLID trong bài

| Chữ | Ở đâu |
|---|---|
| **S** | `AuthService` (ai đăng nhập), `AssetService` (tài sản), `ApprovalService` (duyệt), `FileRepository` (dữ liệu 1 file), `FileUtils` (đọc/ghi), `MD5Utils` (băm), `AssetView` (in) |
| **O** | thêm vai trò "Director" = lớp con `Person` mới + 1 nhánh `parse`; `checkManager` **không sửa** |
| **L** | mọi chỗ nhận `Person` chạy đúng với `Employee` lẫn `Manager`; mọi `FileRepository<T>` dùng như nhau |
| **I** | `IRecord` chỉ 1 hàm — đúng thứ `FileRepository` cần |
| **D** | 3 service nhận repository **qua constructor**; `ManagerController` là nơi duy nhất `new` repository và dùng **chung một kho** cho các service (AssetService và ApprovalService cùng thấy tồn kho) |

---

## 4. Code từng bước

> Thầy: *"code cái **model trước rồi đến data**"*.

| Bước | File | Việc |
|---|---|---|
| 1 | `model/IRecord` | interface 1 hàm (tên bắt đầu bằng **I**) |
| 2 | `model/Person` → `Employee`, `Manager` | abstract + 3 hàm abstract; **không** setter cho `employeeId` |
| 3 | `model/Asset` | 6 field; **không** setter cho `assetId` |
| 4 | `model/Transaction` → `Request`, `Borrow` | abstract, chỉ getter |
| 5 | `constants/*` | `Message`, `Constants` (tên file, chỉ số cột), `TextField`, `NumberField` |
| 6 | `utils/FileUtils`, `MD5Utils`, `Validation` | đọc/ghi dòng (thiếu file = rỗng); băm; kiểm từng ô |
| 7 | `repository/FileRepository` | `loadData(lineList)/findById/findAll/isEmpty/add/update/remove/getNextId` + `saveData` (private) |
| 8 | 4 repository con | `parse` + `format` (số cột truyền lên `super`) |
| 9 | `dto/*` | JavaBean; `AssetRequestDTO` dùng `Double`/`Integer` (null = giữ); `AssetResponseDTO` 4 field cho View |
| 10 | `service/AuthService` | `loadData`, `login`, `checkManager` |
| 11 | `service/AssetNameComparator`, `AssetService` | `loadData`, search, `checkNewId`, create, `checkAssetExist`, update |
| 12 | `service/ApprovalService` | `loadData`, `getRequests`, `acceptRequest` (3 file), `getBorrows` |
| 13 | `view/AssetView` | field `responseDTO` + `setResponseDTO` + `display()` (private `displayAssets`, `displayTransactions`) |
| 14 | `controller/ManagerController` | lắp ráp + `loadData` + 10 hàm, mỗi hàm render tối đa 1 lần |
| 15 | `main/Main` | `final` + ctor `private`; `loadData` (đọc 4 file) + menu + 6 luồng + các hàm `input*` |

**Bẫy hay gặp**

1. So **chữ** mật khẩu với file: không bao giờ khớp, vì file lưu mã băm. Main băm **trước** khi gói DTO.
2. Quên `trim()` sau `split(",")`: `" Samsung projector"` có dấu cách đầu, tìm id `" A001"` không ra.
3. `getNextId` bằng `size() + 1` (mục 2.4).
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
| 10 | 4 | `a003` → trống, `White`, `-5` rồi trống, trống, `6` | **không** in dòng cũ; chỉ màu và số lượng đổi; `Asset A003 has been updated.` + bảng 1 dòng |
| 11 | 5 | `R999` | `Request R999 does not exist.` |
| 12 | 4 → A001 quantity `0`, rồi 5 → `r001` | — | `Not enough stock: A001 has 0 left but request R001 needs 1.` |
| 13 | 4 → A001 quantity `10`, rồi 5 → `R001` | — | **không in gì**, về menu; `asset.dat` A001 còn 9; R001 biến khỏi `request.dat` |
| 14 | 6 | — | 5 dòng, dòng **B008** mang **giờ hiện tại** |
| 15 | 5 cho tới hết | — | `There is no request to approve.` |
| 16 | login sai khi đang là Manager, rồi 6 | — | `You must login first.` (đăng nhập sai = đăng xuất) |
| 17 | menu `9` / `abc` | — | `Please choose from 1 to 7.` |
| 18 | 3 → tạo A003 → `Y` → `a003`, `A004`… → `N` | — | vòng 2 in lại `--- Create new asset ---`, `Asset A003 already exists.`, rồi `Asset A004 has been created.` |

---

## 6. Debug

| Việc | Cách làm |
|---|---|
| MD5 | breakpoint trong `AuthService.login` → Variables: `person.password` và `requestDTO.password` (Main đã băm) phải **giống hệt** |
| Đa hình | breakpoint `if (!currentUser.canManage())` → **F7**: vào `Employee.canManage` hoặc `Manager.canManage` tuỳ ai đăng nhập |
| Template Method | breakpoint `itemList.add(parse(partArray));` trong `FileRepository.loadData` → **F7**: vào `parse` của đúng kho đang tách |
| Duyệt | breakpoint `borrowRepository.add(borrow);` → **F8** ba lần, sau mỗi lần mở file `.dat` tương ứng xem đổi |
| Id mới | breakpoint trong `getNextId` → xem `max` lên 7 rồi trả `B008` |
| Render 1 lần | breakpoint `assetView.display();` → mỗi luồng dừng **một** lần (chức năng 5: chỉ ở `showRequests`) |

---

## 7. Câu hỏi thầy hay hỏi

### OOP

| Câu hỏi | Trả lời mẫu |
|---|---|
| Đa hình ở đâu? (đề bắt) | `Person.canManage()`: `AuthService` gọi qua kiểu `Person`, **lúc chạy** mới biết là `Employee` (false) hay `Manager` (true). Thêm: `FileRepository.loadData()` gọi `parse()`, chạy bản của từng kho. |
| Abstract class khác interface? | `Person`/`Transaction` có **field và code chung**, chỉ `extends` một. `IRecord` chỉ là **hợp đồng** "có id"; `Asset`, `Person`, `Transaction` cùng `implements` dù không họ hàng. |
| Làm sao id "không đổi sau khi tạo"? | **Không viết setter.** Chỉ constructor gán; ngoài lớp không có cách nào đổi. |
| Sao role không phải một field? | `getRole()` do **lớp con** trả lời (`Manager` → `MA`), nên file không bao giờ ghi một Manager thành `EM`. |
| Sao JavaBean có constructor rỗng mà id lại không có setter? | Thầy bắt model là JavaBean (constructor rỗng public). Id là thuộc tính **chỉ đọc**, vẫn đúng chuẩn JavaBean. |

### Collection, file, thuật toán

| Câu hỏi | Trả lời mẫu |
|---|---|
| `<T extends IRecord>` nghĩa là gì? | `FileRepository` làm việc với **bất kỳ** kiểu T nào, miễn T có `getId()`, nên `findById` gọi `item.getId()` được. |
| `ArrayList<? extends Transaction>` trong `ApprovalService`? | Hàm nhận **hoặc** list Request **hoặc** list Borrow: một hàm đổi cả hai sang `TransactionDTO`. |
| Độ phức tạp duyệt? | Tìm request O(n), tìm asset O(m), ghi 3 file O(tổng số dòng). |
| Hai người cùng mở chương trình? | File không khoá; người ghi sau đè người ghi trước. Đề không yêu cầu; muốn chặn phải khoá file hoặc dùng CSDL. |
| Ngày giờ lấy thế nào? | `LocalDateTime.now()` + `DateTimeFormatter("dd-MM-yyyy HH:mm:ss")`, đúng dạng file mẫu. |

### Access modifier, static, tham số

| Câu hỏi | Trả lời mẫu |
|---|---|
| `static` ở đâu? | `Validation`, `FileUtils`, `MD5Utils` (utils); hằng trong `Message`/`Constants`; **hàm** trong `Main`. Không biến static. |
| `protected` ở đâu? | constructor của `Person`, `Transaction`, `FileRepository` (chỉ lớp con gọi); `parse`/`format` (bước lớp con điền). |
| `final` trên `loadData()`? `saveData()` private? | lớp con **không được** đổi khung tách dòng; ghi file chỉ do `add/update/remove` gọi. Lớp con chỉ điền `parse`/`format`. |
| Hàm nào quá 2 tham số? | Chỉ `Validation.getChoice(input, min, max)` (utils được 3) và các **constructor** (`Asset` 6, `ApprovalService` 4, `NumberField` 6 — được miễn). |

### Tờ checklist giấy (21/09/2026)

| Câu hỏi | Trả lời mẫu |
|---|---|
| Sao bài có repository? | Tờ checklist 1.1: *"Bắt buộc phải có repository"*. 4 kho con của `FileRepository` giữ dữ liệu (`itemList`) + CRUD đơn giản (`findById/findAll/add/update/remove`); tính toán (đủ hàng, trừ tồn, sinh id borrow, tra tên) nằm ở `ApprovalService`/`AssetService` → Controller → Service → Repository → Model. |
| Repository có đọc file không? | **Không.** Main đọc (`FileUtils.readLines`) → `AssetRequestDTO.assetLineList…` → `controller.loadData` → service → `repository.loadData(lineList)` chỉ **tách dòng**. **Ghi** thì repository gọi `FileUtils.writeLines` (tờ giấy chỉ nói "đọc"). |
| View nhận dữ liệu thế nào? | Qua **thuộc tính**: `AssetView` có field `responseDTO`, controller gọi `setResponseDTO(responseDTO)` rồi `display()` (không tham số) — **1 lần cho 1 luồng**. `display()` in cái gì khác `null`: `message`, `assetList`, `requestList`, `borrowList`. |
| Validate ở đâu? MD5 ở đâu? | Ở **Main** qua `utils/Validation` (`getChoice`, `checkText`, `checkNumber`, `getYesNo`) và `utils/MD5Utils.hash` — mật khẩu gõ vào được băm **trước** khi vào DTO. Controller chỉ còn luật **nghiệp vụ** (`throw new Exception(Message.X)`, Main in `e.getMessage()`). |
| Chức năng 3/4 gọi controller 3 lần — trái luật "1 lần"? | `checkManager` và `checkNewAssetId`/`checkAssetExist` là lần gọi **chỉ để kiểm** (ném lỗi, **không** render) trong `Main.inputCreate/inputUpdate/inputNewAssetId/inputExistingAssetId`: đề bắt *"Manager must login to use this function"* (báo trước khi bắt gõ) và *"If asset does not exist, the notification…"* (báo ngay sau khi gõ id). Việc tạo/sửa vẫn là **1** lần gọi, view render **1** lần. |
| Chức năng 5 gọi 2 lần? | Đề bắt *"System will show list of borrow request"* **trước khi** chọn id, mà View không đọc phím, Main không được in kết quả → `showRequests` là lần render **duy nhất** của luồng; `acceptRequest` chỉ làm (ném lỗi nếu có), **không** in — đúng *"After approve, the program returns to the main screen"*. |
| Sao `assetId` mà đề viết `assetID`? | Tờ checklist 1.5: *"khi refer đến ID thì thống nhất viết là Id"*. Tên đề giữ trong comment `// brief: assetID` ngay trên field (`Asset`, `Person`, `Transaction`, constructor `Request`/`Borrow`). |
| Sao interface tên `IRecord`? | Tờ checklist 1.3: *"Tên của interface bắt đầu bằng I"* — bản cũ `Identifiable`. |
| Sao `ApprovalService.acceptRequest` mà không `ApproveService.approveRequest`? | 1.3: tên class mở đầu bằng **danh từ** (`Approval`); 1.4: tên method mở đầu bằng **động từ** — `accept` (= duyệt) nằm trong bộ động từ công cụ soát nhận ra. Menu vẫn ghi đúng đề `Approve the request of employee`. |

---

## 8. Thầy đổi yêu cầu tại chỗ

| Thầy bảo | Sửa file | Không phải đụng |
|---|---|---|
| Thêm **xoá tài sản** | `AssetService.deleteAsset` (**chặn nếu còn request/borrow trỏ vào**) + `ManagerController` + 1 case menu | repository (đã có `remove`) |
| Search theo **giá** giảm dần | class `AssetPriceComparator` + 1 dòng constructor `AssetService` | mọi file khác |
| Thêm vai trò **Director** cũng được quản lý | `model/Director extends Person` (`canManage` = true) + 1 nhánh trong `EmployeeRepository.parse` | `AuthService`, controller, main |
| Đổi mật khẩu | Main băm mật khẩu mới vào `requestDTO`; `AuthService.changePassword` → `person.setPassword(requestDTO.getPassword())` + `employeeRepository.update(person)` | model |
| Từ chối (reject) request | `ApprovalService.rejectRequest` → `requestRepository.remove` + 1 case menu | các file khác |
| Hiện kết quả sau khi duyệt | chỉ khi thầy cho luồng 5 render **2 lần**: `acceptRequest` trả id borrow → controller `setMessage` + `display()` | repository |

---

## 9. Chỗ khác với đề / bản cũ

| Chỗ | Đề / bản cũ | Bài này | Lý do |
|---|---|---|---|
| Quit | *"7. Others- Quit"* (gõ gì khác cũng thoát) | **7. Quit**, số khác báo `Please choose from 1 to 7.` | gõ nhầm không thoát ngang; mọi thay đổi đã ghi file nên thoát không mất gì |
| Search "descending" | không nói giảm theo gì (bản cũ: theo giá) | **tên Z→A** | kết quả của một phép tìm theo tên; đồng bộ với L.P0013 |
| Login sai khi đang đăng nhập | bản cũ: giữ phiên cũ | **đăng xuất** | người đứng máy vừa gõ sai không được dùng tiếp quyền Manager |
| Id trùng khi tạo | bản cũ: báo rồi hỏi "Create another?" | báo và **hỏi lại id** | không bắt chọn lại menu |
| Prompt khi sửa | bản cũ: `Name [Dell projector]:` | `New name:` (dòng tài sản **không** in trước — xem dòng *Sửa tài sản (4)*) | hàm nhập 2 tham số (luật thầy) |
| Thông báo duyệt | bản cũ kèm tồn còn lại | **không in** (xem dòng *Duyệt (5)*) | đề: *"After approve, the program returns to the main screen"* |
| Tạo file mẫu | bản cũ: lớp `SampleData` tự ghi | **4 file `.dat` kèm sẵn** trong project | ít một lớp; thiếu file thì kho rỗng, không lỗi |
| Dòng hỏng trong file | bản cũ: văng lỗi | bỏ dòng đó | đề: mọi lỗi phải được xử lý |
| Kiểm tự động | 2 kịch bản tham chiếu nối nhau qua file | 3 kịch bản riêng (C: `Create another asset? Y`), giờ mới tạo hiện là `<NOW>` | `verify.py` chạy mỗi kịch bản trong thư mục mới |
| Kiến trúc | `entity/bo/ui`, Scanner static, controller in ra, `List<T>` | MVC Guide + Template Method + Factory Method + Strategy | luật thầy |
| Bản 15/09 → 21/09 (tờ checklist giấy) | repository tự đọc file (`load()` + `FileUtils.exists`); View `showMessage(String)`/`displayAssets(list)`… nhận **tham số**, controller gọi view 2 lần/luồng; `AuthService` băm MD5; `Identifiable`; `assetID/employeeID/rID/bID`; `ApproveService.approveRequest`, `nextId`; 6 DTO; `Main` không `final`; biến khai báo giữa block, thiếu dòng trống/ngoặc | Main đọc 4 file → `lineList` → repository tách; `AssetView` nhận `AssetResponseDTO` qua `setResponseDTO` + `display()` **1 lần/luồng**; Main băm MD5; `IRecord`; `assetId/employeeId/requestId/borrowId` (+ `// brief:`); `ApprovalService.acceptRequest`, `getNextId`; `AssetRequestDTO` + `AssetResponseDTO` + 3 DTO dòng; `public final class Main` + ctor `private` | tờ checklist giấy thầy phát 21/09/2026 — 0 vi phạm |
| Sửa tài sản (4) | in dòng tài sản hiện tại rồi mới hỏi giá trị mới | **không** in trước; chỉ in kết quả sau khi sửa | 1 render/luồng; đề chỉ bắt *"print out the result of the updating"* |
| Duyệt (5) | in `Request R001 has been approved as borrow B008.` | **không in**, về menu | 1 render/luồng — render đó là danh sách đề bắt hiện trước khi chọn; đề: *"After approve, the program returns to the main screen"* |
| Tạo tiếp (3) | vòng `do…while` trong Main, gọi controller mỗi vòng | `Y` chạy lại **case 3** (in lại tiêu đề, kiểm manager lại) | mỗi luồng = 1 case: 1 lần `createAsset`, 1 lần render |

⚠️ **Hỏi thầy trước khi gõ (chỗ đề và tờ giấy đá nhau):**
1. *"Đề đặt `assetID`, `employeeID` (và cột `rID`, `bID`), tờ checklist bắt viết `Id` — em đặt `assetId`, `employeeId`,
   `requestId`, `borrowId` và ghi tên đề trong comment `// brief:`. Thầy muốn giữ tên đề không ạ?"*
2. *"Chức năng 5 đề bắt hiện danh sách request trước khi chọn, tờ giấy bắt render 1 lần/luồng — em để danh sách là lần
   render duy nhất, duyệt xong không in gì mà về menu. Thầy có muốn in thêm câu 'đã duyệt' (luồng render 2 lần) không ạ?"*
3. *"Chức năng 3/4 em gọi controller thêm lần chỉ để kiểm (chưa đăng nhập/không phải manager; id trùng; id không tồn
   tại) vì đề bắt báo ngay — như vậy đúng ý thầy chưa ạ?"*

---

## 10. Tờ checklist 25 mục — bài này đạt thế nào

`python3 _tools/soat_checklist.py HE176322_J1LP0014_AssetManagementManager` → **0 vi phạm**. Rủi ro còn lại: `String[] args`;
tham số setter/constructor trùng tên field (`this.x = x`); `Main.inputNewAssetId` gọi `checkNewAssetId` trong vòng hỏi
lại — lần gọi **chỉ để kiểm** (đề: ràng buộc id phải được kiểm; hỏi lại id ngay).

| Mục | Chỉ vào đâu |
|---|---|
| **1.1** MVC + repository | `repository/FileRepository` + 4 kho con (bắt buộc có repository); `ManagerController` không import `model`; `AssetView` nhận `responseDTO` qua setter, `display()` không tham số, **1 lần/luồng** (bảng "mỗi luồng" ở mục 3); `Main` đọc file/validate/băm MD5 |
| **1.3** interface / class | `model/IRecord` (bắt đầu bằng **I**); `ApprovalService` (danh từ); không có lớp exception tự viết |
| **1.4** method | động từ: `getNextId` (bản cũ `nextId`), `acceptRequest`, `checkAssetExist`, `loadData`, `saveData`… |
| **1.5** tên collection / Id | `itemList`, `lineList`, `partArray`, `digestArray`, `foundList`, `assetDTOList`, `transactionDTOList`, `assetLineList…`; `assetId`/`employeeId`/`requestId` (đề `assetID` → comment `// brief:`) |
| **2.6 / 3.7** khai báo đầu block + khởi tạo | `Main.main`: `requestDTO = null`, `running = true`, `repeat = false`, `choice = 0`; mọi `input…`: `String line = ""`; `Validation`: `int choice = 0`, `double value = 0`; `ApprovalService.acceptRequest`: `asset = null`, `borrow = null`; `EmployeeRepository.parse`: 5 biến ở đầu |
| **2.8** dòng trống | giữa mọi field (cả `Constants`, `Message`, enum, DTO), sau vùng khai báo biến, trước mọi comment đứng sau dòng code, sau `}` trước câu lệnh tiếp |
| **3.3** ngoặc | `Validation.getChoice`: `if ((choice < min) \|\| (choice > max))`; `checkNumber`: `field.isWholeNumber() && (value != Math.floor(value))`; `AuthService.login`: `(person == null) \|\| …`; `(asset == null) ? … : …` |
| **3.4** lớp chỉ có static | `Main`, `Validation`, `FileUtils`, `MD5Utils`, `Constants`, `Message`: `final` + constructor `private` |
| **3.8** cộng chuỗi | không có `+=`; dòng bảng dùng `String.format(Constants.ASSET_ROW, …)`, MD5 dùng `StringBuilder` |
