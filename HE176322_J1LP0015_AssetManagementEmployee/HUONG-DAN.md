# J1.L.P0015 — Asset Management Upgrade (chương trình của **Employee**)

> **Bài dài (Long Assignment)** · 200 LOC. Đề ghi **Login và Search = 0 LOC** vì hai chức năng này **đã có ở P0014**.
> Phần chấm là 3 chức năng mới: **Borrow**, **Cancel**, **Return** (mỗi cái khoảng 50 LOC) và cấu trúc dữ liệu (50 LOC).
>
> ⚠️ **Học P0014 trước.** Model, 4 repository, `FileUtils`, `MD5Utils`, `Validation` của bài này **chép nguyên** từ P0014.
> Đề cũng nói thế: *"Asset management will include 2 programs"*, hai chương trình đọc **cùng 4 file**.
> File này chỉ giải thích **phần mới**; phần chung (MD5, Template Method, đa hình, id không đổi) xem `HE176322_J1LP0014_AssetManagementManager/HUONG-DAN.md` mục 2.
>
> Theo **tờ checklist giấy 25 mục** (21/09/2026): có repository, Main tự nhập/validate/đọc 4 file/băm MD5, View nhận
> `AssetResponseDTO` qua thuộc tính và **render 1 lần cho 1 luồng** — mục 2.5 và mục 10.

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
| 0 · Cấu trúc | abstract, interface, **id không đổi**, **đa hình** | — | giống P0014 (`Person`/`Transaction` abstract, `IRecord`, `canManage()`) |
| 1 · Login | 0 LOC — như P0014 | — | `Main` băm MD5 → `AuthService.login` |
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
// BorrowService.getMyRequests(String employeeId) - employeeId do controller lấy từ phiên
for (Request request : requestRepository.findAll()) {
    if (request.getEmployeeId().equalsIgnoreCase(employeeId)) {       // chỉ dòng CỦA TÔI
        transactionDTOList.add(toTransactionDTO(request));
    }
}
```

| Chỗ | Vì sao quan trọng |
|---|---|
| `employeeId` **không do người dùng gõ**: controller lấy từ phiên (`authService.getCurrentEmployeeId()`) rồi gán vào DTO | người dùng không giả được id người khác |
| `findMyRequest`: tìm theo id **và** kiểm chủ | gõ `R001` (của Le Buu Nhan) thì báo `You have no request with id R001.`, **không** cho huỷ hộ người khác |
| Kiểm chủ **trước** câu xác nhận (`Main.inputMyRequestId` → `controller.checkMyRequest`, lần gọi **chỉ để kiểm**) | không hỏi "có chắc huỷ không?" cho một dòng mình không được đụng; gõ sai thì **hỏi lại id** ngay |

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
| id mới | `requestRepository.getNextId("R")`: số **lớn nhất** + 1 (R001, R002, R003, **R007** → **R008**) — xem P0014 mục 2.4 |
| giờ | `LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"))` |
| kiểm tồn khi gửi | xin nhiều hơn tồn hiện có (`99` khi A002 còn 5) → `Only 5 Macbook pro 2016 left in stock.` · đề không bắt; thêm vì yêu cầu như thế thì Manager chắc chắn không duyệt được |

### 2.5 Vòng "hỏi tiếp" (đề: *"Ask to continuous or go back to the main menu"*) — mỗi vòng là **một luồng**

Tờ checklist 1.1: *"rendering … chỉ được gọi 1 lần cho 1 luồng xử lý (Mỗi luồng tính là 1 switch - case ở Main)"*.
Nên **Y** không lặp bên trong case mà cho `main` chạy lại **đúng case đó** (bỏ qua menu):

```java
if (!repeat) {                                   // Y ở câu "continue" thì không in menu
    System.out.println(Message.MENU);
    choice = inputChoice(sc);
}
repeat = false;
...
case Constants.MENU_CANCEL:
    requestDTO = inputCancel(sc, controller);    // tiêu đề + showMyRequests() (render DUY NHẤT) + id của tôi
    if (inputYesNo(sc, String.format(Message.ASK_CANCEL, ...))) {   // đề: xác nhận trước khi huỷ
        controller.cancelRequest(requestDTO);    // huỷ, KHÔNG in (lần render đã dùng cho danh sách)
    }
    repeat = inputYesNo(sc, Message.ASK_CONTINUE);
    break;
```

| Tình huống | Chuyện gì xảy ra |
|---|---|
| gõ id không phải của mình | `You have no request with id R001.` → **hỏi lại id** (lần gọi chỉ để kiểm) |
| trả lời N ở câu xác nhận | không huỷ, không in gì → hỏi `Do you want to continue (Y/N)?` |
| huỷ / trả / gửi xong | **không in dòng nào** (đề không bắt; render duy nhất là danh sách) → hỏi tiếp; chọn Y thì danh sách mới hiện lại — dòng vừa huỷ/trả đã biến mất |
| huỷ hết rồi mà vẫn chọn Y | vòng sau `showMyRequests()` ném `You have no request.` → về menu |
| gửi yêu cầu lỗi (`Asset does not exist`, vượt tồn) | Main in lỗi → về menu (như mọi lỗi nghiệp vụ) |

---

## 3. Thiết kế

```
HE176322_J1LP0015_AssetManagementEmployee/
├── asset.dat  employee.dat  request.dat  borrow.dat     cùng dữ liệu mẫu của đề
└── src/
    ├── constants/  Message, Constants, TextField, NumberField          ← MỚI (menu 1–6, câu chữ của Employee)
    ├── model/      IRecord, Person, Employee, Manager,
    │               Asset, Transaction, Request, Borrow                  ← chép từ P0014
    ├── dto/        AssetDTO, PersonDTO, AssetResponseDTO                ← chép từ P0014
    │               AssetRequestDTO (4 danh sách dòng, id + mã băm, keyword, assetId, quantity,
    │               requestId, borrowId), TransactionDTO (không có cột nhân viên)  ← MỚI
    ├── repository/ FileRepository<T> (Template Method) + AssetRepository,
    │               EmployeeRepository, RequestRepository, BorrowRepository  ← chép từ P0014
    ├── service/    AuthService (checkEmployee, getCurrentEmployeeId)    ← MỚI
    │               AssetService (search, getAllAssets)                  ← MỚI
    │               BorrowService (send / cancel / return)               ← MỚI
    │               AssetNameComparator                                  ← chép từ P0014
    ├── controller/ EmployeeController                                   ← MỚI
    ├── view/       AssetView (responseDTO + display(); bảng request/borrow 5 cột)  ← MỚI
    ├── utils/      Validation, FileUtils, MD5Utils                      ← chép từ P0014
    └── main/       Main (final + ctor private)                          ← MỚI
```

| Lớp | Làm gì | Không được làm |
|---|---|---|
| `Main` | menu, đọc phím, **validate**, **đọc 4 file** (`FileUtils`), **băm MD5**, câu xác nhận, "hỏi tiếp" (`repeat`) | import model/view/service/repository |
| `EmployeeController` | chặn quyền; **gán employeeId từ phiên** vào DTO; service → `AssetResponseDTO` → view **1 lần** | Scanner, in, import model |
| `BorrowService` | gửi, lọc "của tôi", huỷ, trả, tra tên tài sản | in, đọc phím |
| `AuthService` | nhớ ai đăng nhập; `checkEmployee()` | in |
| `*Repository` | giữ dữ liệu + CRUD; tách dòng Main đưa; ghi file qua `FileUtils` | đọc file |
| `AssetView` | in theo field `responseDTO`; `display()` không tham số | nhận dữ liệu qua tham số |

**Mỗi luồng (1 `case`) gọi controller mấy lần, render mấy lần:**

| Case | Main gọi controller | Render |
|---|---|---|
| 1 Login | `login` | 1: `Successfully` + `Welcome, … (…)` |
| 2 Search | `searchAsset` | 1: bảng |
| 3 Borrow | `showAssets` *(danh sách đề bắt hiện TRƯỚC khi nhập — render duy nhất)* → **`sendRequest`** *(không in)* | 1 |
| 4 Cancel | `showMyRequests` *(render duy nhất)* → `checkMyRequest` *(chỉ để kiểm, hỏi lại tới khi là id của mình)* → **`cancelRequest`** *(chỉ khi xác nhận Y; không in)* | 1 |
| 5 Return | `showMyBorrows` *(render duy nhất)* → `checkMyBorrow` *(chỉ để kiểm)* → **`returnBorrow`** *(chỉ khi Y; không in)* | 1 |

Chặn quyền nằm ngay trong `showAssets/showMyRequests/showMyBorrows` (gọi `checkEmployee()` **trước** khi lấy danh sách),
nên chưa đăng nhập hay là Manager thì dừng trước câu hỏi đầu tiên.

### 3.1 Design Pattern

| Pattern | Ở đâu |
|---|---|
| **Template Method** | `FileRepository` (khung đọc/ghi; lớp con `parse`/`format`) — y như P0014 |
| **Factory Method** | `EmployeeRepository.parse`: role → `Manager`/`Employee` |
| **Strategy** | `AssetNameComparator` (thứ tự search) |
| **Facade** | `EmployeeController` |
| **Repository + DTO + MVC** | các package |
| **Tái sử dụng** (không phải GoF, nhưng là điểm đáng nói) | 20 file **dùng chung nguyên văn** với P0014 (8 model, 5 repository, 3 utils, `AssetDTO`, `PersonDTO`, `AssetResponseDTO`, `AssetNameComparator`). Tầng model/repository/utils không biết gì về "manager" hay "employee", nên hai chương trình khác nhau chỉ ở constants, service, controller, view, main |

### 3.2 SOLID

| Chữ | Ở đâu |
|---|---|
| **S** | `BorrowService` chỉ lo request/borrow của nhân viên; `AuthService` chỉ lo phiên; `AssetService` chỉ lo xem tài sản |
| **O** | P0015 **thêm** service mới, **không sửa** một dòng model/repository của P0014 |
| **L** | `Request` và `Borrow` đều là `Transaction`: `toTransactionDTO(Transaction)` dùng cho cả hai |
| **I** | `IRecord` một hàm |
| **D** | service nhận repository qua constructor; `AssetService` và `BorrowService` **dùng chung một** `AssetRepository` (trả xong thì search thấy tồn mới ngay) |

---

## 4. Code từng bước

| Bước | File | Việc |
|---|---|---|
| 1 | chép từ P0014 | `model/*`, `repository/*`, `utils/*`, `AssetDTO`, `PersonDTO`, `AssetResponseDTO`, `AssetNameComparator`, 4 file `.dat` |
| 2 | `constants/Message`, `Constants` | menu 1–6, câu chữ mới, `REQUEST_PREFIX = "R"`, bảng 5 cột |
| 3 | `constants/TextField`, `NumberField` | ô: employee id, password, keyword, asset id, request id, borrow id; số: quantity ≥ 1 |
| 4 | `dto/AssetRequestDTO`, `TransactionDTO` | JavaBean |
| 5 | `service/AuthService` | `loadData`, `login`, `checkEmployee`, `getCurrentEmployeeId` |
| 6 | `service/AssetService` | `loadData`, `searchByName`, `getAllAssets` |
| 7 | `service/BorrowService` | `loadData`, `sendRequest`, `getMyRequests`, `checkMyRequest`, `cancelRequest`, `getMyBorrows`, `checkMyBorrow`, `returnBorrow` + `findMyRequest/findMyBorrow/toTransactionDTO` |
| 8 | `view/AssetView` | field `responseDTO` + `setResponseDTO` + `display()` (private `displayAssets`, `displayTransactions`) |
| 9 | `controller/EmployeeController` | mỗi hàm 3–5: `checkEmployee()` → gán `employeeId` → service → view (tối đa 1 lần) |
| 10 | `main/Main` | `loadData` (đọc 4 file) + menu + `repeat` cho "hỏi tiếp" + các hàm `input*` |

**Bẫy hay gặp**

1. Cho người dùng **gõ** `employeeId` khi gửi yêu cầu: gửi hộ, huỷ hộ người khác được.
2. Tìm request theo id trong **cả file** rồi xoá luôn: huỷ được request của người khác.
3. Trả tài sản mà **quên cộng tồn**, hoặc cộng tồn **trước** khi xoá borrow (mục 2.3).
4. Hai `AssetRepository` khác nhau cho hai service: trả xong mà search vẫn thấy tồn cũ.
5. Coi "gõ gì khác Y" là N ở câu xác nhận: gõ nhầm là huỷ nhầm. Bài này hỏi lại tới khi gõ Y hoặc N.
6. Lặp `do … while` **bên trong** case rồi gọi controller mỗi vòng: một luồng render nhiều lần (sai tờ checklist 1.1).
   Bài này cho **Y** chạy lại cả case (`repeat`).

---

## 5. Test trước khi gọi thầy

| # | Chọn | Gõ | Phải thấy |
|---|---|---|---|
| 1 | 3 / 4 / 5 khi chưa login | — | `You must login first.` |
| 2 | 1 | `E160001` / `000000` | `Incorrect id or password` |
| 3 | 1 | `E160052` / `123456` → rồi 3 | `Welcome, Hoa Doan (Manager).` → `A manager cannot borrow assets. …` |
| 4 | 1 | `E160001` / `123456` | `Successfully` · `Welcome, Nguyen Hong Hiep (Employee).` |
| 5 | 2 | `pro` / `zzz` | Samsung projector, Macbook pro 2016 / `No asset found.` |
| 6 | 3 | id `A9` / `A099`, số lượng `1` | lỗi dạng id → hỏi lại / `Asset does not exist` → về menu |
| 7 | 3 | `A002`, số lượng `0`, `abc`, `99` | `Quantity must be a whole number from 1 to 1000000.` ×2 → `Only 5 Macbook pro 2016 left in stock.` → về menu |
| 8 | 3 | `a002`, `2` → `x` → `N` | **không in dòng nào** → `Do you want to continue (Y/N)?` → `Please enter Y or N.`; `request.dat` có dòng R008 mang giờ hiện tại |
| 9 | 4 | `R001` | `You have no request with id R001.` (R001 là của người khác) → **hỏi lại id** |
| 10 | 4 | `r008` → `n` → `Y` | không huỷ, không in gì → hỏi tiếp → `Y`: tiêu đề + danh sách hiện lại (R008 vẫn còn) |
| 11 | 4 | `R002` → `y` → `N` | huỷ, **không in dòng nào**; `request.dat` mất R002 |
| 12 | 5 | `B003` | `You have no borrowed asset with id B003.` → **hỏi lại id** |
| 13 | 5 | `b001` → `y` → `N`, rồi 2 → `samsung` | trả, không in → Samsung projector **Qty 11** |
| 14 | login `E160240`, 4 → `R007` → `y` → `Y` | — | huỷ xong → tiêu đề + `You have no request.` → về menu |
| 15 | 5 → `B007` → `Y` → `Y` | — | trả xong → tiêu đề + `You have no borrowed asset.`; A001 tăng 2 |
| 16 | menu `9` | — | `Please choose from 1 to 6.` |

---

## 6. Debug

| Việc | Cách làm |
|---|---|
| Đa hình ở chặn quyền | breakpoint `if (currentUser.canManage())` trong `AuthService.checkEmployee` → **F7**: vào `Manager.canManage` (true) khi đăng nhập E160052 |
| "Chỉ của tôi" | breakpoint trong `findMyRequest` → gõ `R001` → Variables: `request.employeeId = E140449` ≠ `requestDTO.employeeId = E160001` |
| Trả tài sản | breakpoint `borrowRepository.remove(borrow);` trong `returnBorrow` → **F8** hai lần, mở `borrow.dat` rồi `asset.dat` xem từng file đổi |
| Id mới | breakpoint trong `FileRepository.getNextId` → thấy `max` = 7 → trả `R008` |
| Render 1 lần | breakpoint `assetView.display();` → mỗi vòng của chức năng 3–5 dừng **một** lần (ở `show…`) |

---

## 7. Câu hỏi thầy hay hỏi

| Câu hỏi | Trả lời mẫu |
|---|---|
| Đề ghi Login/Search 0 LOC là sao? | Hai chức năng này đã viết ở P0014. Bài này **dùng lại** nguyên model, repository, utils; chỉ viết service/controller/view/main mới. |
| Đa hình ở đâu? | `Person.canManage()`: `checkEmployee` gọi qua kiểu `Person`; lúc chạy Manager trả true (bị chặn), Employee trả false. Và `FileRepository.loadData()` gọi `parse()` của từng kho. |
| Làm sao nhân viên không huỷ được yêu cầu của người khác? | Mục 2.2: employeeId lấy từ phiên, không gõ; `findMyRequest` kiểm **cả id lẫn chủ**. |
| Sao Manager không được mượn? | Manager là người duyệt; tự gửi rồi tự duyệt yêu cầu của mình thì việc duyệt mất ý nghĩa. Muốn cho mượn thì sửa đúng một chỗ: bỏ nhánh trong `checkEmployee`. |
| Sao trả tài sản xoá borrow **trước** rồi mới cộng tồn? | Mục 2.3: nếu hỏng giữa chừng, thiếu 1 chiếc thì sửa được; thừa vô hạn thì không. |
| Hai chương trình mở cùng lúc? | Mỗi thao tác đọc bộ nhớ đã nạp lúc khởi động và ghi đè cả file, nên chương trình ghi sau thắng. Đề không yêu cầu; muốn đúng phải nạp lại file trước mỗi thao tác hoặc dùng CSDL. |
| Hàm nào quá 2 tham số? | `Validation.getChoice(input, min, max)` (utils được 3) và các constructor (`BorrowService` 3, `Asset` 6). |
| `static` ở đâu? | utils, hằng, enum, **hàm** trong `Main`. Không biến static. |

### Tờ checklist giấy (21/09/2026)

| Câu hỏi | Trả lời mẫu |
|---|---|
| Sao bài có repository? | Tờ checklist 1.1: *"Bắt buộc phải có repository"*. 4 kho con của `FileRepository` giữ dữ liệu (`itemList`) + CRUD (`findById/findAll/add/update/remove`); lọc "của tôi", kiểm tồn, sinh id, cộng tồn nằm ở `BorrowService` → Controller → Service → Repository → Model. |
| Repository có đọc file không? | **Không.** Main đọc (`FileUtils.readLines`) → `AssetRequestDTO.assetLineList…` → `controller.loadData` → service → `repository.loadData(lineList)`. **Ghi** thì repository gọi `FileUtils.writeLines`. |
| View nhận dữ liệu thế nào? | Qua **thuộc tính**: field `responseDTO` + `setResponseDTO(...)` + `display()` không tham số, **1 lần cho 1 luồng**. |
| Validate ở đâu? MD5 ở đâu? | Ở **Main**: `Validation.getChoice/checkText/checkNumber/getYesNo`; `MD5Utils.hash` băm mật khẩu trước khi vào DTO. |
| Chức năng 3–5 gọi controller 2–3 lần? | Đề bắt **hiện danh sách trước** khi nhập/chọn, mà View không đọc phím, Main không được in kết quả → `show…` là lần render **duy nhất**; `checkMyRequest/checkMyBorrow` là lần gọi **chỉ để kiểm** (ném lỗi, không in) vì phải biết id là của mình **trước** câu xác nhận; `sendRequest/cancelRequest/returnBorrow` chỉ làm, **không in** — đề chỉ bắt *"Ask to continuous or go back to the main menu"*. |
| Sao gửi/huỷ/trả xong không báo gì? | Mỗi luồng render 1 lần (tờ checklist), và lần đó đã dùng cho danh sách đề bắt hiện. Chọn **Y** ở câu hỏi tiếp thì danh sách mới hiện lại. |
| Sao `employeeId` mà đề viết `employeeID`? | Tờ checklist 1.5 bắt `Id`; tên đề giữ trong comment `// brief: employeeID` ngay trên field (`Person`, `Transaction`). |
| Sao interface tên `IRecord`? | Tờ checklist 1.3: *"Tên của interface bắt đầu bằng I"* — bản cũ `Identifiable`. |

---

## 8. Thầy đổi yêu cầu tại chỗ

| Thầy bảo | Sửa file | Không phải đụng |
|---|---|---|
| Cho Manager mượn luôn | bỏ nhánh `canManage()` trong `AuthService.checkEmployee` | mọi file khác |
| Không cho gửi khi đã có request cùng tài sản đang chờ | `BorrowService.sendRequest`: quét `requestRepository.findAll()` + 1 `Message` | main, view |
| Trả **một phần** (mượn 2 trả 1) | nhập thêm số lượng; `returnBorrow` giảm quantity của borrow thay vì xoá. ⚠️ `Transaction` hiện không có `setQuantity`, phải thêm | repository |
| Xem lịch sử mượn của tôi | `BorrowService.getMyBorrows` đã có → thêm 1 case menu | service |
| Nạp lại file trước mỗi thao tác (2 chương trình chạy cùng lúc) | `Main`: gọi `loadData(controller)` đầu mỗi case 3–5 (Main đọc lại 4 file) | model, service |
| Báo "đã gửi/huỷ/trả" | chỉ khi thầy cho luồng render **2 lần**: hàm controller `setMessage` + `display()` | repository |

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
| Bản 15/09 → 21/09 (tờ checklist giấy) | repository tự đọc file; View `showMessage`/`displayX(list)` nhận **tham số**; `AuthService` băm MD5; `Identifiable`; `assetID/employeeID`; `nextId`; `Main` lặp `do…while` và gọi controller 3–4 lần/vòng; 6 DTO; `Main` không `final` | Main đọc 4 file → `lineList`; View nhận `AssetResponseDTO` qua `setResponseDTO` + `display()` **1 lần/luồng**; Main băm MD5; `IRecord`; `assetId/employeeId` (+ `// brief:`); `getNextId`; **Y** chạy lại case (`repeat`); `AssetRequestDTO` + `AssetResponseDTO` + 3 DTO dòng; `public final class Main` + ctor `private` | tờ checklist giấy thầy phát 21/09/2026 — 0 vi phạm |
| Gửi / huỷ / trả xong | in `Request R008 has been sent.` / `… has been cancelled.` / `Borrow B001 has been returned.`; N ở xác nhận in `Nothing was cancelled.` | **không in** | 1 render/luồng, dùng cho danh sách đề bắt hiện trước; đề không bắt câu báo |
| Id không phải của mình | in lỗi → hỏi `continue` | **hỏi lại id** ngay | lần gọi chỉ để kiểm nằm trong vòng hỏi lại (như id trùng ở P0014) |
| Lỗi khi gửi (asset không có, vượt tồn) | in lỗi → hỏi `continue` | in lỗi → **về menu** | mọi lỗi nghiệp vụ đều do Main bắt ở `catch` rồi quay menu |
| Chọn Y ở câu hỏi tiếp | lặp trong hàm, không in lại tiêu đề | chạy lại case: **in lại tiêu đề** + danh sách | mỗi vòng là một luồng |

⚠️ **Hỏi thầy trước khi gõ (chỗ đề và tờ giấy đá nhau):**
1. *"Chức năng 3–5 đề bắt hiện danh sách trước khi nhập, tờ giấy bắt render 1 lần/luồng — em để danh sách là lần render
   duy nhất, gửi/huỷ/trả xong không in câu báo. Thầy có muốn thêm câu báo (luồng render 2 lần) không ạ?"*
2. *"Đề đặt `assetID`, `employeeID` (cột `rID`, `bID`), tờ checklist bắt `Id` — em đặt `assetId`, `employeeId`, `requestId`,
   `borrowId` và ghi tên đề trong comment `// brief:`. Thầy muốn giữ tên đề không ạ?"*

---

## 10. Tờ checklist 25 mục — bài này đạt thế nào

`python3 _tools/soat_checklist.py HE176322_J1LP0015_AssetManagementEmployee` → **0 vi phạm**. Rủi ro còn lại: `String[] args`;
tham số setter/constructor trùng tên field (`this.x = x`); `Main.inputMyRequestId`/`inputMyBorrowId` gọi controller trong
vòng hỏi lại — lần gọi **chỉ để kiểm** (id phải là của mình trước câu xác nhận).

| Mục | Chỉ vào đâu |
|---|---|
| **1.1** MVC + repository | `repository/FileRepository` + 4 kho con (bắt buộc có repository); `EmployeeController` không import `model`; `AssetView` nhận `responseDTO` qua setter, `display()` không tham số, **1 lần/luồng** (bảng "mỗi luồng" mục 3); `Main` đọc file/validate/băm MD5 |
| **1.3** interface / class | `model/IRecord` (bắt đầu bằng **I**); không có lớp exception tự viết |
| **1.5** tên collection / Id | `itemList`, `lineList`, `partArray`, `digestArray`, `transactionDTOList`, `assetDTOList`, `assetLineList…`; `employeeId`/`requestId`/`borrowId`, `getCurrentEmployeeId` (đề `employeeID` → comment `// brief:`) |
| **2.6 / 3.7** khai báo đầu block + khởi tạo | `Main.main`: `requestDTO = null`, `running = true`, `repeat = false`, `choice = 0`; mọi `input…`: `String line = ""`; `BorrowService.sendRequest`: `request = null`; `Validation`: `int choice = 0`, `double value = 0` |
| **2.8** dòng trống | giữa mọi field (cả `Constants`, `Message`, enum, DTO), sau vùng khai báo biến, trước mọi comment đứng sau dòng code, sau `}` trước câu lệnh tiếp |
| **3.3** ngoặc | `BorrowService.findMyRequest`: `if ((request == null) \|\| !…)`; `Validation.getChoice`: `(choice < min) \|\| (choice > max)`; `(asset == null) ? … : …` |
| **3.4** lớp chỉ có static | `Main`, `Validation`, `FileUtils`, `MD5Utils`, `Constants`, `Message`: `final` + constructor `private` |
| **3.8** cộng chuỗi | không có `+=`; dòng bảng dùng `String.format`, MD5 dùng `StringBuilder` |
