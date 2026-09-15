# J1.L.P0015 — Asset Management Upgrade (chương trình của **Employee**)

> **Bài dài (Long Assignment)** · 200 LOC. Đề ghi **Login và Search = 0 LOC** vì hai chức năng này **đã có ở P0014**.
> Phần chấm là 3 chức năng mới: **Borrow**, **Cancel**, **Return** (mỗi cái khoảng 50 LOC) và cấu trúc dữ liệu (50 LOC).
>
> ⚠️ **Học P0014 trước.** Model, 4 repository, `FileUtils`, `MD5Utils`, `Validation` của bài này **chép nguyên** từ P0014.
> Đề cũng nói thế: *"Asset management will include 2 programs"*, hai chương trình đọc **cùng 4 file**.
> File này chỉ giải thích **phần mới**; phần chung (MD5, Template Method, đa hình, id không đổi) xem `HE176322_J1LP0014_AssetManagementManager/HUONG-DAN.md` mục 2.

| | |
|---|---|
| Loại / LOC | Long Assignment · 200 LOC |
| Project | `HE176322_J1LP0015_AssetManagementEmployee` |
| Chạy | NetBeans: **File ▸ Open Project** → **F6** (4 file `.dat` nằm cạnh `build.xml`) |
| Tài khoản thử | Employee `E160001` / `123456` (có R002, B001, B002) · Manager `E160052` / `123456` (**không** được mượn) |
| Lớp chạy | `main.Main` |
| Kiểm tự động | `python3 _tools/verify.py --netbeans J1LP0015` → 2 kịch bản × 2 locale |

---

## 1. Đề bài nói gì

| Function | Đề yêu cầu | Login? | Bài này |
|---|---|---|---|
| 0 · Cấu trúc | abstract, interface, **id không đổi**, **đa hình** | — | giống P0014 (`Person`/`Transaction` abstract, `Identifiable`, `canManage()`) |
| 1 · Login | 0 LOC — như P0014 | — | `AuthService.login` |
| 2 · Search | 0 LOC — như P0014 | không | `AssetService.searchByName` |
| 3 · Borrow | in danh sách tài sản (`asset.dat`) → nhập **assetID + quantity** → **thêm vào request.dat** → hỏi tiếp hay về menu | **Employee** | `BorrowService.sendRequest` |
| 4 · Cancel | in các request **của nhân viên này** → chọn rID → **xác nhận** → **xoá khỏi request.dat** → hỏi tiếp | **Employee** | `BorrowService.cancelRequest` |
| 5 · Return | in các borrow **của nhân viên này** → chọn bID → **xác nhận** → **xoá khỏi borrow.dat** → **cộng lại tồn asset.dat** → hỏi tiếp | **Employee** | `BorrowService.returnBorrow` |
| Others | Quit | — | menu 6 |

**Vòng đời một lần mượn** (hai chương trình chia nhau):

```
 P0015 (Employee)                P0014 (Manager)                  P0015 (Employee)
 3 · Borrow ──► request.dat ──► 5 · Approve ──► borrow.dat ──► 5 · Return ──► xoá borrow
     R008 (A002 × 2)               tồn A002 −2     B008              tồn A002 +2
        │
        └─ 4 · Cancel (khi CHƯA duyệt) ──► xoá R008
```

---

## 2. Kiến thức mới trong bài

### 2.1 Chặn quyền ngược với P0014 — vẫn là **đa hình**

| Chương trình | Hàm chặn | Luật |
|---|---|---|
| P0014 | `AuthService.checkManager()` | `!currentUser.canManage()` → `"… is not a manager. …"` |
| P0015 | `AuthService.checkEmployee()` | `currentUser.canManage()` → `"A manager cannot borrow assets. Please use the manager's program."` |

**Cùng một hàm đa hình** `Person.canManage()` trả lời cho cả hai chương trình. Không chỗ nào so chuỗi `"MA"`/`"EM"` (trừ lúc đọc file trong `EmployeeRepository.parse`).
Vì sao chặn Manager? Manager là người **duyệt**; tự gửi rồi tự duyệt yêu cầu của mình thì việc duyệt mất ý nghĩa.

### 2.2 "Chỉ thấy của mình" — lọc theo người đăng nhập

```java
// BorrowService.getMyRequests
for (Request request : requestRepository.findAll()) {
    if (request.getEmployeeID().equalsIgnoreCase(requestDTO.getEmployeeID())) {  // chỉ dòng CỦA TÔI
        rows.add(toResponse(request));
    }
}
```

| Chỗ | Vì sao quan trọng |
|---|---|
| `employeeID` **không do người dùng gõ**: controller lấy từ phiên (`authService.getCurrentEmployeeID()`) rồi gán vào DTO | người dùng không giả được id người khác |
| `findMyRequest`: tìm theo id **và** kiểm chủ | gõ `R001` (của Le Buu Nhan) thì báo `You have no request with id R001.`, **không** cho huỷ hộ người khác |
| Kiểm chủ **trước** câu xác nhận | không hỏi "có chắc huỷ không?" cho một dòng mình không được đụng |

### 2.3 Trả tài sản — 2 file đổi, thứ tự có chủ đích

```java
borrowRepository.remove(borrow);                         // ① xoá dòng B001 khỏi borrow.dat
asset.setQuantity(asset.getQuantity() + borrow.getQuantity());
assetRepository.update(asset);                           // ② A001: 10 → 11
```

| Nếu máy tắt giữa ① và ② | Hậu quả |
|---|---|
| thứ tự của bài (xoá borrow **trước**) | tồn **thiếu** 1 chiếc: kiểm kê ra, cộng tay lại được |
| thứ tự ngược (cộng tồn trước) | tồn đã cộng mà borrow **còn**: trả lại **lần nữa** được, tồn tăng mãi, tài sản "đẻ" ra từ không khí |

### 2.4 Gửi yêu cầu — id mới và giờ hiện tại

| Việc | Code |
|---|---|
| id mới | `requestRepository.nextId("R")`: số **lớn nhất** + 1 (R001, R002, R003, **R007** → **R008**) — xem P0014 mục 2.4 |
| giờ | `LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"))` |
| kiểm tồn khi gửi | xin nhiều hơn tồn hiện có (`99` khi A002 còn 5) → `Only 5 Macbook pro 2016 left in stock.` · đề không bắt; thêm vì yêu cầu như thế thì Manager chắc chắn không duyệt được |

### 2.5 Vòng "hỏi tiếp" (đề: *"Ask to continuous or go back to the main menu"*)

```java
do {
    controller.showMyRequests();       // hết request → throw "You have no request." → thoát cả hàm
    ... nhập id ...
    try { kiểm chủ → xác nhận → huỷ } catch (Exception e) { in lỗi }   // lỗi chỉ kết thúc LƯỢT này
} while (inputYesNo(sc, "Do you want to continue (Y/N)? "));
```

| Tình huống | Chuyện gì xảy ra |
|---|---|
| gõ id không phải của mình | in lỗi → hỏi `Do you want to continue (Y/N)?` |
| trả lời N ở câu xác nhận | `Nothing was cancelled.` → hỏi tiếp |
| huỷ hết rồi mà vẫn chọn Y | vòng sau `showMyRequests()` ném `You have no request.` → về menu |

---

## 3. Thiết kế

```
HE176322_J1LP0015_AssetManagementEmployee/
├── asset.dat  employee.dat  request.dat  borrow.dat     cùng dữ liệu mẫu của đề
└── src/
    ├── constants/  Message, Constants, TextField, NumberField          ← MỚI (menu 1–6, câu chữ của Employee)
    ├── model/      Identifiable, Person, Employee, Manager,
    │               Asset, Transaction, Request, Borrow                  ← chép từ P0014
    ├── dto/        LoginRequestDTO, LoginResponseDTO, AssetResponseDTO  ← chép từ P0014
    │               AssetRequestDTO (keyword), TransactionRequestDTO (id, assetID, quantity, employeeID),
    │               TransactionResponseDTO (không có cột nhân viên)      ← MỚI
    ├── repository/ FileRepository<T> + 4 kho                            ← chép từ P0014
    ├── service/    AuthService (checkEmployee, getCurrentEmployeeID)    ← MỚI
    │               AssetService (search, getAllAssets)                  ← MỚI
    │               BorrowService (send / cancel / return)               ← MỚI
    │               AssetNameComparator                                  ← chép từ P0014
    ├── controller/ EmployeeController                                   ← MỚI
    ├── view/       AssetView (bảng request/borrow 5 cột)                ← MỚI
    ├── utils/      Validation, FileUtils, MD5Utils                      ← chép từ P0014
    └── main/       Main                                                 ← MỚI
```

| Lớp | Làm gì | Không được làm |
|---|---|---|
| `Main` | menu, đọc phím, vòng "hỏi tiếp", câu xác nhận | import model/service/repository |
| `EmployeeController` | chặn quyền; **gán employeeID từ phiên** vào DTO; service → view | Scanner, in, import model |
| `BorrowService` | gửi, lọc "của tôi", huỷ, trả, tra tên tài sản | in, đọc phím |
| `AuthService` | nhớ ai đăng nhập; `checkEmployee()` | in |

### 3.1 Design Pattern

| Pattern | Ở đâu |
|---|---|
| **Template Method** | `FileRepository` (khung đọc/ghi; lớp con `parse`/`format`) — y như P0014 |
| **Factory Method** | `EmployeeRepository.parse`: role → `Manager`/`Employee` |
| **Strategy** | `AssetNameComparator` (thứ tự search) |
| **Facade** | `EmployeeController` |
| **Repository + DTO + MVC** | các package |
| **Tái sử dụng** (không phải GoF, nhưng là điểm đáng nói) | 17 file **dùng chung nguyên văn** với P0014. Tầng model/repository/utils không biết gì về "manager" hay "employee", nên hai chương trình khác nhau chỉ ở service, controller, view, main |

### 3.2 SOLID

| Chữ | Ở đâu |
|---|---|
| **S** | `BorrowService` chỉ lo request/borrow của nhân viên; `AuthService` chỉ lo phiên; `AssetService` chỉ lo xem tài sản |
| **O** | P0015 **thêm** service mới, **không sửa** một dòng model/repository của P0014 |
| **L** | `Request` và `Borrow` đều là `Transaction`: `toResponse(Transaction)` dùng cho cả hai |
| **I** | `Identifiable` một hàm |
| **D** | service nhận repository qua constructor; `AssetService` và `BorrowService` **dùng chung một** `AssetRepository` (trả xong thì search thấy tồn mới ngay) |

---

## 4. Code từng bước

| Bước | File | Việc |
|---|---|---|
| 1 | chép từ P0014 | `model/*`, `repository/*`, `utils/*`, 3 DTO, `AssetNameComparator`, 4 file `.dat` |
| 2 | `constants/Message`, `Constants` | menu 1–6, câu chữ mới, `REQUEST_PREFIX = "R"`, bảng 5 cột |
| 3 | `constants/TextField`, `NumberField` | ô: employee id, password, keyword, asset id, request id, borrow id; số: quantity ≥ 1 |
| 4 | `dto/AssetRequestDTO`, `TransactionRequestDTO`, `TransactionResponseDTO` | JavaBean |
| 5 | `service/AuthService` | `login`, `checkEmployee`, `getCurrentEmployeeID` |
| 6 | `service/AssetService` | `searchByName`, `getAllAssets` |
| 7 | `service/BorrowService` | `sendRequest`, `getMyRequests`, `checkMyRequest`, `cancelRequest`, `getMyBorrows`, `checkMyBorrow`, `returnBorrow` + `findMyRequest/findMyBorrow/toResponse` |
| 8 | `view/AssetView` | `displayAssets`, `displayRequests`, `displayBorrows`, `showMessage` |
| 9 | `controller/EmployeeController` | mỗi hàm 3–5: `checkEmployee()` → gán `employeeID` → service → view |
| 10 | `main/Main` | 3 vòng `do … while (hỏi tiếp)` |

**Bẫy hay gặp**

1. Cho người dùng **gõ** employeeID khi gửi yêu cầu: gửi hộ, huỷ hộ người khác được.
2. Tìm request theo id trong **cả file** rồi xoá luôn: huỷ được request của người khác.
3. Trả tài sản mà **quên cộng tồn**, hoặc cộng tồn **trước** khi xoá borrow (mục 2.3).
4. Hai `AssetRepository` khác nhau cho hai service: trả xong mà search vẫn thấy tồn cũ.
5. Coi "gõ gì khác Y" là N ở câu xác nhận: gõ nhầm là huỷ nhầm. Bài này hỏi lại tới khi gõ Y hoặc N.

---

## 5. Test trước khi gọi thầy

| # | Chọn | Gõ | Phải thấy |
|---|---|---|---|
| 1 | 3 / 4 / 5 khi chưa login | — | `You must login first.` |
| 2 | 1 | `E160001` / `000000` | `Incorrect id or password` |
| 3 | 1 | `E160052` / `123456` → rồi 3 | `Welcome, Hoa Doan (Manager).` → `A manager cannot borrow assets. …` |
| 4 | 1 | `E160001` / `123456` | `Successfully` · `Welcome, Nguyen Hong Hiep (Employee).` |
| 5 | 2 | `pro` / `zzz` | Samsung projector, Macbook pro 2016 / `No asset found.` |
| 6 | 3 | id `A9` / `A099` | lỗi dạng id / `Asset does not exist` → hỏi tiếp |
| 7 | 3 | `A002`, số lượng `0`, `abc`, `99` | `Quantity must be a whole number from 1 to 1000000.` ×2 → `Only 5 Macbook pro 2016 left in stock.` |
| 8 | 3 | `a002`, `2` | `Request R008 has been sent.`; `request.dat` có dòng R008 mang giờ hiện tại |
| 9 | 4 | `R001` | `You have no request with id R001.` (R001 là của người khác) |
| 10 | 4 | `r008` → `n` | `Nothing was cancelled.` |
| 11 | 4 | `R002` → `y` | `Request R002 has been cancelled.` |
| 12 | 5 | `B003` | `You have no borrowed asset with id B003.` |
| 13 | 5 | `b001` → `y`, rồi 2 → `samsung` | `Borrow B001 has been returned.` → Samsung projector **Qty 11** |
| 14 | login `E160240`, 4 → `R007` → `y` → `Y` | — | huỷ xong → `You have no request.` → về menu |
| 15 | 5 → `B007` → `Y` → `Y` | — | trả xong → `You have no borrowed asset.`; A001 tăng 2 |
| 16 | menu `9` | — | `Please choose from 1 to 6.` |

---

## 6. Debug

| Việc | Cách làm |
|---|---|
| Đa hình ở chặn quyền | breakpoint `if (currentUser.canManage())` trong `AuthService.checkEmployee` → **F7**: vào `Manager.canManage` (true) khi đăng nhập E160052 |
| "Chỉ của tôi" | breakpoint trong `findMyRequest` → gõ `R001` → Variables: `request.employeeID = E140449` ≠ `requestDTO.employeeID = E160001` |
| Trả tài sản | breakpoint `borrowRepository.remove(borrow);` trong `returnBorrow` → **F8** hai lần, mở `borrow.dat` rồi `asset.dat` xem từng file đổi |
| Id mới | breakpoint trong `FileRepository.nextId` → thấy `max` = 7 → trả `R008` |

---

## 7. Câu hỏi thầy hay hỏi

| Câu hỏi | Trả lời mẫu |
|---|---|
| Đề ghi Login/Search 0 LOC là sao? | Hai chức năng này đã viết ở P0014. Bài này **dùng lại** nguyên model, repository, utils; chỉ viết service/controller/view/main mới. |
| Đa hình ở đâu? | `Person.canManage()`: `checkEmployee` gọi qua kiểu `Person`; lúc chạy Manager trả true (bị chặn), Employee trả false. Và `FileRepository.load()` gọi `parse()` của từng kho. |
| Làm sao nhân viên không huỷ được yêu cầu của người khác? | Mục 2.2: employeeID lấy từ phiên, không gõ; `findMyRequest` kiểm **cả id lẫn chủ**. |
| Sao Manager không được mượn? | Manager là người duyệt; tự gửi rồi tự duyệt yêu cầu của mình thì việc duyệt mất ý nghĩa. Muốn cho mượn thì sửa đúng một chỗ: bỏ nhánh trong `checkEmployee`. |
| Sao trả tài sản xoá borrow **trước** rồi mới cộng tồn? | Mục 2.3: nếu hỏng giữa chừng, thiếu 1 chiếc thì sửa được; thừa vô hạn thì không. |
| Hai chương trình mở cùng lúc? | Mỗi thao tác đọc bộ nhớ đã nạp lúc khởi động và ghi đè cả file, nên chương trình ghi sau thắng. Đề không yêu cầu; muốn đúng phải nạp lại file trước mỗi thao tác hoặc dùng CSDL. |
| Hàm nào quá 2 tham số? | `Validation.getChoice(input, min, max)` (utils được 3) và các constructor (`BorrowService` 3, `Asset` 6). |
| `static` ở đâu? | utils, hằng, enum, **hàm** trong `Main`. Không biến static. |

---

## 8. Thầy đổi yêu cầu tại chỗ

| Thầy bảo | Sửa file | Không phải đụng |
|---|---|---|
| Cho Manager mượn luôn | bỏ nhánh `canManage()` trong `AuthService.checkEmployee` | mọi file khác |
| Không cho gửi khi đã có request cùng tài sản đang chờ | `BorrowService.sendRequest`: quét `requestRepository.findAll()` + 1 `Message` | main, view |
| Trả **một phần** (mượn 2 trả 1) | nhập thêm số lượng; `returnBorrow` giảm quantity của borrow thay vì xoá. ⚠️ `Transaction` hiện không có `setQuantity`, phải thêm | repository |
| Xem lịch sử mượn của tôi | `BorrowService.getMyBorrows` đã có → thêm 1 case menu | service |
| Nạp lại file trước mỗi thao tác (2 chương trình chạy cùng lúc) | `EmployeeController`: gọi `loadData()` đầu mỗi hàm | model, service |

---

## 9. Chỗ khác với đề / bản cũ

| Chỗ | Đề / bản cũ | Bài này | Lý do |
|---|---|---|---|
| Quit | *"Others- Quit"* | **6. Quit**, số khác báo lỗi | gõ nhầm không thoát ngang (mọi thay đổi đã ghi file) |
| Function 5 | tiêu đề đề ghi *"Return request"*, menu ghi *"Return asset"* | dùng **Return asset** | đúng việc làm; menu đề cũng ghi thế |
| Kiểm tồn khi gửi | đề không bắt | có (`Only 5 … left in stock.`) | yêu cầu vượt tồn thì Manager chắc chắn không duyệt được |
| Câu xác nhận | bản cũ: `Cancel request R008 (Y/N)?` | `Do you want to cancel request R008? (Y/N): ` | cùng kiểu với câu hỏi của đề |
| Login sai | bản cũ: giữ phiên cũ | **đăng xuất** | như P0014 |
| Bảng request/borrow | có cột nhân viên (P0014) | bỏ cột nhân viên | nhân viên chỉ thấy dòng của mình |
| Tạo file mẫu | bản cũ: lớp `SampleData` | 4 file `.dat` kèm sẵn | như P0014 |
| Kiểm tự động | 2 kịch bản nối nhau qua file | 2 kịch bản riêng, giờ mới tạo hiện là `<NOW>` | `verify.py` chạy mỗi kịch bản trong thư mục mới |
| Kiến trúc | `entity/bo/ui`, Scanner static, controller in ra | MVC Guide, chung tầng dưới với P0014 | luật thầy |
