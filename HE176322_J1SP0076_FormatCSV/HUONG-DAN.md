# J1.S.P0076 — Format CSV

> Bản 21/09/2026 đã sửa theo **tờ checklist giấy 25 mục** của thầy và **đối chiếu lại đề gốc**
> (màn hình bây giờ khớp đúng khung "Expectation of User interface") — xem mục 9.1 và mục 10.

| | |
|---|---|
| Loại / LOC | Short Assignment · 100 LOC · 2 slot |
| Project | `HE176322_J1SP0076_FormatCSV` |
| Chạy | NetBeans: **File ▸ Open Project** → **F6** (thư mục chạy = gốc project) |
| Lớp chạy | `main.Main` |
| Dữ liệu mẫu | `import.csv` ở **gốc project** (ứng với `d:\import.csv` của đề) |
| Kiểm tự động | `python3 _tools/verify.py J1SP0076` → 3 kịch bản × 2 locale |

---

## 1. Đề bài nói gì

File CSV: `ID, Name, Email, Phone, Address`, có 2 lỗi:

| Cột | Sai | Đúng |
|---|---|---|
| Name | `Nguyen   van a` | `Nguyen Van A` (bỏ khoảng trắng thừa + **chữ đầu mỗi từ viết hoa**) |
| Address | `Cau Giay     - Ha    Noi    - Viet Nam` | `Cau Giay - Ha Noi - Viet Nam` (bỏ khoảng trắng thừa) |

Menu: **1 Import · 2 Format Address · 3 Format Name · 4 Export · 5 Exit**.

Màn hình đề (chép đúng chữ từ các khung của đề — mỗi khung là kết quả của một lựa chọn):

```
======= Format CSV Program =======
1. Import CSV
2. Format Address
3. Format Name
4. Export CSV
5. Exit
Please choice one option:
```

| Chọn | Khung của đề |
|---|---|
| 1 | `--------- Import CSV -------` · `Enter Path:d:\import.csv` · `Import: Done` |
| 2 | `--------- Format Address -------` · `Format: Done` |
| 3 | `--------- Format Name -------` · `Format: Done` |
| 4 | `--------- Export CSV ------` · `Enter Path:d:export.csv` · `Export: Done` |
| 5 | thoát |

**Đề bắt buộc:**

| Hàm của đề | Ở đâu | Ghi chú |
|---|---|---|
| `void importCSV(String path)` | đọc file: `Main.inputImport` → `FileUtils.readLines(path)`; set `dataCSV`: `CsvController/CsvService/CsvRepository.importCSV(requestDTO)` | tờ checklist 1.1: **đọc file ở Main** → tên giữ, tham số thành DTO (mục 9.1) |
| `void formatAddress(String dataCSV)` | `service/CsvService` | giữ **nguyên chữ ký**; kết quả **set** vào `dataCSV` |
| `String formatName(String dataCSV)` | `service/CsvService` | giữ **nguyên chữ ký**; set vào `dataCSV` **và** trả về |
| `void exportCSV(String path)` | `repository/CsvRepository` (service gọi) | giữ **nguyên chữ ký**; ghi `dataCSV` ra file mới qua `FileUtils.writeLines` |
| *"Use String manipulation"* | `utils/StringUtils` | `trim`, `replaceAll`, `split`, `toUpperCase` |

---

## 2. Kiến thức cần biết

### 2.1 Chạy tay dòng mẫu của đề

`1, Nguyen   van a, anv@gmail.com, 098889999, Cau Giay  -   Ha Noi - Viet Nam`

| Bước | Code | Kết quả |
|---|---|---|
| Tách 5 cột | `line.split(",", 5)` | `["1"," Nguyen   van a"," anv@gmail.com"," 098889999"," Cau Giay  -   Ha Noi - Viet Nam"]` |
| Cắt 2 đầu | `trim()` từng cột | `"Nguyen   van a"` |
| Bỏ khoảng thừa | `replaceAll("\\s+", " ")` | `"Nguyen van a"` |
| Viết hoa chữ đầu | `split(" ")` → `toUpperCase(charAt(0))` + `substring(1)` | `"Nguyen Van A"` |
| Ghép lại | `Customer.toString()` = `String.format("%s, %s, %s, %s, %s", ...)` | `1, Nguyen Van A, anv@gmail.com, 098889999, ...` |

### 2.2 Hai chi tiết `split` thầy hay hỏi

| Chi tiết | Vì sao |
|---|---|
| `split(",", 5)` có **giới hạn 5** | dòng `4, pham van d, pvd@gmail.com, 0900000000,` có Address **rỗng**: `split(",")` thường **vứt** cột rỗng cuối → còn 4 cột; giới hạn 5 giữ lại. Address chứa dấu `,` cũng không bị cắt vụn |
| `"\\s+"` | một hoặc nhiều khoảng trắng/tab → thay bằng **1** dấu cách |

---

## 3. Thiết kế

```
src/
├── model/      Customer              1 dòng CSV: 5 cột (JavaBean) + toString() dạng CSV (String.format)
├── dto/        CsvRequestDTO         path + lineList (các dòng Main đã đọc)   (main ──► controller)
│               CsvResponseDTO        message ("Import: Done"…)                (controller ──► view)
├── repository/ CsvRepository         GIỮ dataCSV · importCSV(requestDTO) · exportCSV(path) · get/set
├── service/    CsvService            formatAddress · formatName (+ đường tới repository)
│               ICustomerFormatter    «interface» Strategy: format(Customer)
│               AddressFormatter · NameFormatter
├── controller/ CsvController         importCSV · formatAddress · formatName · exportCSV → view 1 lần
├── view/       CsvView               field responseDTO + setResponseDTO + display() không tham số
├── constants/  Message, Constants
├── utils/      FileUtils (Main đọc · repository ghi) · StringUtils (xử lý chuỗi) · Validation
└── main/       Main                  final + private Main(); nhập, ĐỌC FILE, gọi controller 1 lần/case
```

| Câu hỏi | Trả lời |
|---|---|
| Sao có `repository`? | Tờ checklist 1.1 *"Bắt buộc phải có repository"*; đề bắt giữ **biến global `dataCSV`** giữa các lần chọn menu → đó là *"data"* của Guide. `CsvRepository` chỉ giữ và CRUD đơn giản: nhận dòng (`importCSV`), trả (`getDataCSV`), thay (`setDataCSV`), lưu (`exportCSV`). |
| Sao có `service`? | Format = **xử lý nghiệp vụ ngoài CRUD** → service, nằm giữa controller và repository (Guide: *"Controller <-> Services <-> Repository"*). |
| Sao file đọc ở `Main` mà ghi ở repository? | Tờ checklist 1.1: *"Toàn bộ việc nhập dữ liệu/Validate/**đọc từ file**/mã hóa thực hiện ở Main"* → `Main.inputImport` gọi `FileUtils.readLines`. Tờ giấy không nói "ghi" → export vẫn là việc **lưu dữ liệu** của repository, phần ghi byte nằm trong `FileUtils.writeLines`. |
| Sao `CsvService.importCSV` chỉ gọi thẳng repository? | Giữ đúng luồng tầng: controller không nói chuyện trực tiếp với repository. |

**Luồng Import** (Main → RequestDTO → Controller → Service → Repository; ResponseDTO → View 1 lần):

```
Main: in tiêu đề, hỏi "Enter Path:" ─► FileUtils.readLines(path)   ← "Check file exist or not" + đọc
      ─► CsvRequestDTO{path, lineList} ─► controller.importCSV(requestDTO)       ← gọi 1 lần
   controller ─► csvService.importCSV(requestDTO) ─► csvRepository.importCSV(requestDTO)
                                                     dataCSV = các dòng nối bằng "\n"
   controller ─► CsvResponseDTO{message = "Import: Done"} ─► csvView.setResponseDTO ─► display()
```

**Luồng Format Name:**

```
Main: in tiêu đề ─► controller.formatName()                                  ← gọi 1 lần
   ─► csvService.formatName(csvService.getDataCSV())
        formatData(data, nameFormatter): mỗi dòng → Customer (model) → nameFormatter.format(c) → c.toString()
        csvRepository.setDataCSV(kết quả)      ← "Set to global variable dataCSV"
   ─► csvView.setResponseDTO({message = "Format: Done"}) ─► display()          ← render 1 lần
```

### 3.1 Design Pattern

| Pattern | Name · Problem · Solution · Consequences |
|---|---|
| **Strategy** | **Problem**: Format Address và Format Name **duyệt file giống hệt nhau**, chỉ khác "sửa cột nào, sửa thế nào". **Solution**: `ICustomerFormatter` = Strategy (tên có `I` — tờ checklist 1.3); `AddressFormatter`, `NameFormatter` = ConcreteStrategy; `CsvService.formatData(data, formatter)` = Context (1 vòng lặp duy nhất). **Consequences**: ✅ thêm "email viết thường" = 1 lớp mới, vòng lặp không đổi; ❌ thêm 3 file. |
| **Repository** (mẫu kiến trúc) | `CsvRepository` là nơi duy nhất giữ `dataCSV`. |
| **Facade** | `CsvController`: mỗi mục menu 1 hàm. |
| **MVC** (thầy: "MVC JSP") | `Customer`, DTO là JavaBean. |

---

## 4. Code từng bước

> Thầy: *"code cái **model trước rồi đến data**"*.

| Bước | File | Việc |
|---|---|---|
| 1 | `model/Customer.java` | 5 field, constructor rỗng, get/set, `toString` bằng `String.format(Constants.CUSTOMER_FORMAT, ...)` |
| 2 | `dto/CsvRequestDTO`, `CsvResponseDTO` | JavaBean: `path` + `lineList`; `message` |
| 3 | `utils/FileUtils.java` | `readLines` (kiểm tồn tại rồi đọc — Main gọi), `writeLines` (repository gọi) |
| 4 | `repository/CsvRepository.java` | `dataCSV`, `getDataCSV`/`setDataCSV`, `importCSV(requestDTO)`, `exportCSV(path)` |
| 5 | `utils/StringUtils.java` | `normalizeSpace`, `capitalizeWords` |
| 6 | `service/ICustomerFormatter` + 2 lớp | Strategy |
| 7 | `service/CsvService.java` | `importCSV`, `exportCSV`, `getDataCSV`, `formatAddress`, `formatName`, `formatData`, `formatLine` |
| 8 | `view/CsvView`, `controller/CsvController` | view: field `responseDTO` + `setResponseDTO` + `display()`; controller: mỗi hàm gọi `showResult` **1 lần** |
| 9 | `constants/*`, `utils/Validation` | |
| 10 | `main/Main.java` | `final` + `private Main()`; menu, `inputImport` (đọc file), `inputExport` |

**Bẫy hay gặp:**

1. `split(",")` không giới hạn → mất cột Address rỗng → `ArrayIndexOutOfBoundsException`.
2. `charAt(0)` trên chuỗi rỗng → lỗi; phải bỏ khoảng thừa **trước** rồi mới tách từ (không còn từ rỗng).
3. Format trước khi Import → `dataCSV` là `null` → phải báo `No CSV file has been imported`.
4. Khai báo biến giữa khối (`String line = sc.nextLine();` trong vòng lặp) → tờ checklist 2.6/3.7 đánh trượt. Khai báo **đầu hàm** và **khởi tạo luôn** (`String line = "";`, `String[] lineArray = new String[0];`), sau đó chỉ **gán**.
5. View có hàm nhận tham số (`showMessage(String)`) hoặc controller gọi view 2 lần trong 1 luồng → tờ checklist 1.1 đánh trượt.

---

## 5. Test trước khi gọi thầy

| # | Gõ | Phải thấy |
|---|---|---|
| 1 | `1` · `import.csv` | `Import: Done` |
| 2 | `2` | `Format: Done` |
| 3 | `3` | `Format: Done` |
| 4 | `4` · `export.csv` | `Export: Done` — mở `export.csv` ở gốc project: `1, Nguyen Van A, anv@gmail.com, 098889999, Cau Giay - Ha Noi - Viet Nam` … (cả 2 cột đã chuẩn) |
| 5 | chỉ `1` → `3` → `4` · `names.csv` | mở `names.csv`: Name đã chuẩn (`Tran Thi B`), Address **còn** khoảng thừa |
| 6 | `2` / `3` / `4` trước khi import | `No CSV file has been imported` |
| 7 | `1` · `nosuch.csv` | `Path doesn't exist` |
| 8 | `1` · `test` (thư mục) | `Cannot read file` |
| 9 | `4` · `test` | `Cannot write file` |
| 10 | menu `abc`, `6` | `You must input a number.`, `Please choose from 1 to 5.` |

---

## 6. Debug

| Việc | Cách làm |
|---|---|
| Breakpoint | dòng `formatter.format(customer);` trong `CsvService.formatLine` |
| Chạy | **Ctrl+F5**, `1` → `import.csv`, rồi `3` |
| Quan sát | mở `customer` xem `name` trước/sau; `fieldArray` xem 5 cột |
| Bước | **F7** vào `format` → nhảy vào `NameFormatter` (đa hình); **F7** tiếp vào `StringUtils.capitalizeWords` |
| Dòng 4 | dừng ở dòng `4, pham van d, ...` → `fieldArray.length` vẫn là **5** nhờ `split(",", 5)` |
| Đọc file | breakpoint ở `requestDTO.setLineList(...)` trong `Main.inputImport` → xem `lineList` đủ 5 dòng |

---

## 7. Câu hỏi thầy hay hỏi

### Kiến trúc theo tờ checklist

| Câu hỏi | Trả lời mẫu |
|---|---|
| Sao bài có repository? | Tờ checklist 1.1 *"Bắt buộc phải có repository"*. `CsvRepository` giữ **dữ liệu** của chương trình — biến `dataCSV` mà đề gọi là "global variable" — và chỉ có CRUD đơn giản. Format (tính toán) ở `CsvService`: luồng **Controller → Service → Repository → Model**. |
| View nhận dữ liệu thế nào? | **Qua thuộc tính**, không qua tham số: `CsvView` có field `private CsvResponseDTO responseDTO` + setter `setResponseDTO(...)`; `display()` không tham số. Controller gọi `setResponseDTO` rồi `display()` **đúng 1 lần** mỗi luồng (hàm `showResult`). |
| Validate ở đâu? Đọc file ở đâu? | Ở **Main**: `inputChoice` đưa dòng cho `Validation.getChoice`; `inputImport` hỏi đường dẫn rồi gọi `FileUtils.readLines` — file không có thì `FileUtils` ném `Exception(Message.PATH_NOT_EXIST)`, Main bắt và in `e.getMessage()`. Controller chỉ nhận `CsvRequestDTO` đã có sẵn các dòng. |
| Sao `importCSV` không nhận `String path` như đề? | Nhận `path` thì phải **đọc file ngay trong hàm đó**, mà tờ checklist bắt đọc file ở Main. Nên Main đọc, còn `importCSV(requestDTO)` làm nửa còn lại của đề: *"Set to the global variable dataCSV"*. Tên hàm giữ nguyên. |
| Sao `Main` là `final` và có `private Main()`? | Tờ checklist 3.4: class chỉ có hàm `static` phải có private constructor và là `final`. |
| Sao controller import `constants.Message`? | Câu `Import: Done`… phải lấy từ `Message` (Guide: *"không hardcode"*). Controller đặt nó vào `CsvResponseDTO` rồi đưa view — không in gì. |

### OOP / Java

| Câu hỏi | Trả lời mẫu |
|---|---|
| 4 tính chất OOP? | **Đóng gói**: 5 field `private` của `Customer`; `dataCSV` `private` trong repository. **Kế thừa**: 2 formatter `implements ICustomerFormatter`. **Đa hình**: `formatter.format(customer)` chạy bản của lớp cụ thể; `toString()` ghi đè. **Trừu tượng**: `ICustomerFormatter` chỉ nói "sửa được một dòng". |
| `dataCSV` sao để trong repository mà không trong service? | Nó là **dữ liệu được giữ** — Guide: repository *"chứa data"*. |
| `formatData`, `formatLine` sao `private`? | Chỉ `CsvService` dùng. |
| Field `addressFormatter` kiểu `ICustomerFormatter`, không phải `AddressFormatter`? | Service chỉ phụ thuộc **interface** (Dependency Inversion). |
| `formatName` trả `String` còn `formatAddress` `void`? | Đề khai báo thế; cả hai đều set `dataCSV`. |
| `StringUtils` sao `static`? | Hàm dùng chung, không có dữ liệu đối tượng (Guide: utils static). **Bỏ `static`** → phải `new StringUtils()` trong 2 formatter, bỏ constructor `private`. |
| Sao Phone là `String`? | `098889999` — số `0` đầu sẽ mất nếu là `int`. |
| `ArrayList` vs `List`? | `List` là interface, `ArrayList` là lớp cài đặt bằng mảng động. Em khai báo đúng kiểu `ArrayList` (thầy dặn), vì chỉ duyệt tuần tự và thêm vào cuối. Tên biến kết thúc bằng `List` (`lineList`) — tờ checklist 1.5. |
| "nGUYEN" thành gì? | `NGUYEN` — đề chỉ bảo **viết hoa chữ đầu**; muốn `Nguyen` thì thêm `toLowerCase()` phần còn lại (mục 8). |
| SOLID? | **S**: `FileUtils` đọc/ghi, `StringUtils` xử lý chuỗi, repository giữ dữ liệu, formatter sửa 1 cột. **O**: formatter mới không sửa `formatData`. **L/I**: 2 formatter thay nhau, interface 1 hàm. **D**: service phụ thuộc `ICustomerFormatter`. |

---

## 8. Thầy đổi yêu cầu tại chỗ

| Thầy bảo | Sửa | Không đụng |
|---|---|---|
| Thêm "Format Email" (chữ thường) | `EmailFormatter`, 1 hàm service, 1 hàm controller, menu | `formatData`, repository |
| Phần sau chữ đầu viết thường | `StringUtils.capitalizeWords`: `substring(1).toLowerCase()` | mọi file khác |
| Export tách bằng `;` | `Constants.CUSTOMER_FORMAT` = `"%s;%s;%s;%s;%s"` | logic |
| File có dòng tiêu đề không được sửa | `formatData` bỏ qua dòng 0 | formatter |
| In lại dữ liệu sau khi format | thêm field `dataCSV` vào `CsvResponseDTO`, controller set `csvService.getDataCSV()`, `CsvView.display()` in thêm | service, repository |

---

## 9. Chỗ khác với đề / bản cũ

| Chỗ | Đề / bản cũ | Bài này | Lý do |
|---|---|---|---|
| In dữ liệu sau Import/Format | đề chỉ in `Import: Done` / `Format: Done`; bản cũ và bản trước 21/09 in thêm toàn bộ dữ liệu | **chỉ in đúng dòng của đề** (từ 21/09) | đối chiếu đề gốc: màn hình phải khớp khung "Expectation of User interface"; muốn xem kết quả thì mở file đã export |
| `--------- Format Address -------Format: Done` | đề in dính 1 dòng | 2 dòng | lỗi dàn trang của đề (khung hẹp làm chữ xuống dòng; bản cũ cũng 2 dòng) |
| File mẫu | bản cũ tự tạo `import.csv` + in `Demo data file created` | `import.csv` **có sẵn ở gốc project** | theo cách các bài file khác của bộ |
| Thông báo lỗi | đề không có | `Path doesn't exist`, `Cannot read file`, `Cannot write file`, `No CSV file has been imported` | cần cho các trường hợp sai |
| Mọi cột được `trim` và ghép lại bằng `", "` | đề chỉ nói Name/Address | vậy | dòng `3,le  van   c ,...` không có dấu cách sau `,` |
| Kiến trúc | 1 lớp `CSVFormatter` | repository + service + Strategy | luật thầy |

### 9.1 Đổi theo tờ checklist giấy (21/09/2026)

Màn hình chỉ đổi đúng một chỗ — **bỏ phần in dữ liệu** (dòng đầu bảng trên, để khớp đề); mọi câu chữ khác giữ nguyên (`verify.py` so từng dòng, cả en_US và vi_VN). Nội dung file export **không đổi một byte** (đã so lại).

| Chỗ | Bản trước | Bây giờ | Mục checklist |
|---|---|---|---|
| Đọc file | `CsvRepository.importCSV(String path)` tự mở file | `Main.inputImport` → `FileUtils.readLines(path)` → `CsvRequestDTO.lineList` → `importCSV(requestDTO)` | 1.1 (đọc file ở Main) |
| View | `showMessage(String)` + `setResponse` + `display()`; controller gọi view 2 lần/luồng | field `responseDTO` + `setResponseDTO(...)` + `display()`; controller render **1 lần** (`showResult`) | 1.1 |
| ResponseDTO | `dataCSV` (cả file) | `message` (1 dòng kết quả) | 1.1 |
| Interface | `CustomerFormatter` | `ICustomerFormatter` | 1.3 |
| Tên biến | `String[] lines/fields/words`, `ArrayList<String> lines` | `lineArray`, `fieldArray`, `wordArray`, `lineList` | 1.5 |
| Main | `public class Main` | `public final class Main` + `private Main()` | 3.4 |
| Khai báo biến | `int choice = inputChoice(sc);`, `String line = sc.nextLine();` giữa vòng; `int choice;` chưa gán | khai báo đầu khối + khởi tạo (`int choice = 0;`, `String line = "";`), trong vòng chỉ gán | 2.6, 3.7 |
| Ngoặc | `choice < min \|\| choice > max` | `(choice < min) \|\| (choice > max)` | 3.3 |
| `Customer.toString()` | `id + ", " + name + ...` | `String.format(Constants.CUSTOMER_FORMAT, ...)` | 3.8 |
| `Customer` | thêm constructor 5 tham số | chỉ constructor rỗng + setter (constructor đủ không còn ai gọi → bỏ, tránh code chết) | 3.6 |
| Dòng trống | comment các hằng/field dính nhau; không trống sau `}` | 1 dòng trống trước mỗi comment, sau vùng khai báo, sau mỗi `}` | 2.8 |

---

## 10. Tờ checklist 25 mục — bài này đạt thế nào

| Mục | Chỉ vào đâu |
|---|---|
| 1.1 | `repository/CsvRepository` (bắt buộc có) giữ `dataCSV`; `Main` đọc file (`inputImport` → `FileUtils.readLines`), chỉ import controller/dto/utils/constants; `CsvController` không import `model`; `CsvView` nhận `responseDTO` qua setter, `display()` gọi 1 lần/luồng; mỗi `case` gọi controller 1 lần |
| 1.3 / 1.4 | interface `ICustomerFormatter`; hàm mở đầu bằng động từ: `importCSV`, `formatAddress`, `normalizeSpace`, `capitalizeWords`, `showResult`, `readLines` |
| 1.5 | `ArrayList<String> lineList` (`CsvRequestDTO`, `FileUtils`, `CsvRepository.exportCSV`); `String[] lineArray/fieldArray/wordArray` (`CsvService`, `StringUtils`); `id` (không `ID`) trong `Customer` |
| 2.6 + 3.7 | `int choice = 0;` (`Main.main`), `String line = "";` (`Main.inputChoice`, `FileUtils.readLines`), `String[] lineArray = new String[0];` (`CsvService.formatData`), `Customer customer = new Customer();` (`CsvService.formatLine`) |
| 2.8 | 1 dòng trống giữa các hằng/field có comment, sau vùng khai báo biến, sau mỗi `}` trước câu lệnh kế, giữa các `case` |
| 3.3 | `if ((choice < min) \|\| (choice > max))` (`Validation.getChoice`) |
| 3.4 | `public final class Main` + `private Main()`; `FileUtils`, `StringUtils`, `Validation`, `Message`, `Constants` cũng `final` + private constructor |
| Soát máy | `checklist_audit.py` → **0 VI_PHAM**; còn RUI_RO: `String[] args` của `main` và tham số setter trùng tên field (`this.path = path` — IDE sinh) |
