# J1.S.P0076 — Format CSV

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

**Đề bắt buộc:**

| Hàm của đề | Ở đâu | Ghi chú |
|---|---|---|
| `void importCSV(String path)` | `repository/CsvRepository` | đọc file → biến "global" `dataCSV` |
| `void formatAddress(String dataCSV)` | `service/CsvService` | kết quả **set** vào `dataCSV` |
| `String formatName(String dataCSV)` | `service/CsvService` | set vào `dataCSV` **và** trả về |
| `void exportCSV(String path)` | `repository/CsvRepository` | ghi `dataCSV` ra file mới |
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
| Viết hoa chữ đầu | `split(" ")` → `toUpperCase(charAt(0)) + substring(1)` | `"Nguyen Van A"` |
| Ghép lại | `Customer.toString()` nối bằng `", "` | `1, Nguyen Van A, anv@gmail.com, 098889999, ...` |

### 2.2 Hai chi tiết `split` thầy hay hỏi

| Chi tiết | Vì sao |
|---|---|
| `split(",", 5)` có **giới hạn 5** | dòng `4, pham van d, pvd@gmail.com, 0900000000,` có Address **rỗng**: `split(",")` thường **vứt** cột rỗng cuối → còn 4 cột; giới hạn 5 giữ lại. Address chứa dấu `,` cũng không bị cắt vụn |
| `"\\s+"` | một hoặc nhiều khoảng trắng/tab → thay bằng **1** dấu cách |

---

## 3. Thiết kế

```
src/
├── model/      Customer              1 dòng CSV: 5 cột (JavaBean) + toString() dạng CSV
├── dto/        CsvRequestDTO         path                 (main ──► controller)
│               CsvResponseDTO        dataCSV để in        (controller ──► view)
├── repository/ CsvRepository         giữ dataCSV · importCSV · exportCSV
├── service/    CsvService            formatAddress · formatName (+ đường tới repository)
│               CustomerFormatter     «interface» Strategy: format(Customer)
│               AddressFormatter · NameFormatter
├── controller/ CsvController         importCSV · formatAddress · formatName · exportCSV
├── view/       CsvView               showMessage · display (in dữ liệu)
├── constants/  Message, Constants
├── utils/      FileUtils (đọc/ghi) · StringUtils (xử lý chuỗi) · Validation
└── main/       Main
```

| Câu hỏi | Trả lời |
|---|---|
| Sao có `repository`? | Đề bắt giữ **biến global `dataCSV`** giữa các lần chọn menu → đó là *"data"* của Guide; import/export là nạp/lưu dữ liệu. |
| Sao có `service`? | Format = **xử lý nghiệp vụ ngoài CRUD** → service, nằm giữa controller và repository (Guide: *"Controller <-> Services <-> Repository"*). |
| Sao `CsvService.importCSV` chỉ gọi thẳng repository? | Giữ đúng luồng tầng: controller không nói chuyện trực tiếp với repository. |

**Luồng Format Name:**

```
Main: in tiêu đề ─► controller.formatName()
   ─► service.formatName(service.getDataCSV())
        formatData(data, nameFormatter): mỗi dòng → Customer → nameFormatter.format(c) → c.toString()
        repository.setDataCSV(kết quả)      ← "Set to global variable dataCSV"
   ─► view: "Format: Done" + in dữ liệu mới
```

### 3.1 Design Pattern

| Pattern | Name · Problem · Solution · Consequences |
|---|---|
| **Strategy** | **Problem**: Format Address và Format Name **duyệt file giống hệt nhau**, chỉ khác "sửa cột nào, sửa thế nào". **Solution**: `CustomerFormatter` = Strategy; `AddressFormatter`, `NameFormatter` = ConcreteStrategy; `CsvService.formatData(data, formatter)` = Context (1 vòng lặp duy nhất). **Consequences**: ✅ thêm "email viết thường" = 1 lớp mới, vòng lặp không đổi; ❌ thêm 3 file. |
| **Repository** (mẫu kiến trúc) | `CsvRepository` là nơi duy nhất giữ `dataCSV`. |
| **Facade** | `CsvController`: mỗi mục menu 1 hàm. |
| **MVC** (thầy: "MVC JSP") | `Customer`, DTO là JavaBean. |

---

## 4. Code từng bước

> Thầy: *"code cái **model trước rồi đến data**"*.

| Bước | File | Việc |
|---|---|---|
| 1 | `model/Customer.java` | 5 field, 2 constructor, get/set, `toString` |
| 2 | `dto/CsvRequestDTO`, `CsvResponseDTO` | JavaBean |
| 3 | `utils/FileUtils.java` | `isExist`, `readLines`, `writeLines` |
| 4 | `repository/CsvRepository.java` | `dataCSV`, `importCSV`, `exportCSV` |
| 5 | `utils/StringUtils.java` | `normalizeSpace`, `capitalizeWords` |
| 6 | `service/CustomerFormatter` + 2 lớp | Strategy |
| 7 | `service/CsvService.java` | `formatAddress`, `formatName`, `formatData`, `formatLine` |
| 8 | `view/CsvView`, `controller/CsvController` | |
| 9 | `constants/*`, `utils/Validation` | |
| 10 | `main/Main.java` | menu |

**Bẫy hay gặp:**

1. `split(",")` không giới hạn → mất cột Address rỗng → `ArrayIndexOutOfBoundsException`.
2. `charAt(0)` trên chuỗi rỗng → lỗi; phải bỏ khoảng thừa **trước** rồi mới tách từ (không còn từ rỗng).
3. Format trước khi Import → `dataCSV` là `null` → phải báo `No CSV file has been imported`.

---

## 5. Test trước khi gọi thầy

| # | Gõ | Phải thấy |
|---|---|---|
| 1 | `1` · `import.csv` | `Import: Done` + dữ liệu gốc |
| 2 | `2` | `Format: Done` + Address hết khoảng thừa, Name chưa đổi |
| 3 | `3` | `Format: Done` + `Nguyen Van A`, `Tran Thi B`, `Le Van C`, `Pham Van D` |
| 4 | `4` · `export.csv` | `Export: Done` |
| 5 | `1` · `export.csv` | dữ liệu đã chuẩn hoá cả 2 cột |
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
| Quan sát | mở `customer` xem `name` trước/sau; `fields` xem 5 cột |
| Bước | **F7** vào `format` → nhảy vào `NameFormatter` (đa hình); **F7** tiếp vào `StringUtils.capitalizeWords` |
| Dòng 4 | dừng ở dòng `4, pham van d, ...` → `fields.length` vẫn là **5** nhờ `split(",", 5)` |

---

## 7. Câu hỏi thầy hay hỏi

| Câu hỏi | Trả lời mẫu |
|---|---|
| 4 tính chất OOP? | **Đóng gói**: 5 field `private` của `Customer`; `dataCSV` `private` trong repository. **Kế thừa**: 2 formatter `implements CustomerFormatter`. **Đa hình**: `formatter.format(customer)` chạy bản của lớp cụ thể; `toString()` ghi đè. **Trừu tượng**: `CustomerFormatter` chỉ nói "sửa được một dòng". |
| `dataCSV` sao để trong repository mà không trong service? | Nó là **dữ liệu được giữ** — Guide: repository *"chứa data"*. |
| `formatData`, `formatLine` sao `private`? | Chỉ `CsvService` dùng. |
| Field `addressFormatter` kiểu `CustomerFormatter`, không phải `AddressFormatter`? | Service chỉ phụ thuộc **interface** (Dependency Inversion). |
| `formatName` trả `String` còn `formatAddress` `void`? | Đề khai báo thế; cả hai đều set `dataCSV`. |
| `StringUtils` sao `static`? | Hàm dùng chung, không có dữ liệu đối tượng (Guide: utils static). **Bỏ `static`** → phải `new StringUtils()` trong 2 formatter, bỏ constructor `private`. |
| Sao Phone là `String`? | `098889999` — số `0` đầu sẽ mất nếu là `int`. |
| `ArrayList` vs `List`? | `List` là interface, `ArrayList` là lớp cài đặt bằng mảng động. Em khai báo đúng kiểu `ArrayList` (thầy dặn), vì chỉ duyệt tuần tự và thêm vào cuối. |
| "nGUYEN" thành gì? | `NGUYEN` — đề chỉ bảo **viết hoa chữ đầu**; muốn `Nguyen` thì thêm `toLowerCase()` phần còn lại (mục 8). |
| SOLID? | **S**: `FileUtils` đọc/ghi, `StringUtils` xử lý chuỗi, repository giữ dữ liệu, formatter sửa 1 cột. **O**: formatter mới không sửa `formatData`. **L/I**: 2 formatter thay nhau, interface 1 hàm. **D**: service phụ thuộc `CustomerFormatter`. |

---

## 8. Thầy đổi yêu cầu tại chỗ

| Thầy bảo | Sửa | Không đụng |
|---|---|---|
| Thêm "Format Email" (chữ thường) | `EmailFormatter`, 1 hàm service, 1 hàm controller, menu | `formatData`, repository |
| Phần sau chữ đầu viết thường | `StringUtils.capitalizeWords`: `substring(1).toLowerCase()` | mọi file khác |
| Export tách bằng `;` | `Constants.FIELD_JOIN` | logic |
| File có dòng tiêu đề không được sửa | `formatData` bỏ qua dòng 0 | formatter |

---

## 9. Chỗ khác với đề / bản cũ

| Chỗ | Đề / bản cũ | Bài này | Lý do |
|---|---|---|---|
| In dữ liệu sau Import/Format | đề chỉ in `Import: Done` / `Format: Done` | in thêm toàn bộ dữ liệu (như bản cũ) | để người chấm **thấy** kết quả chuẩn hoá; 4 dòng của đề vẫn giữ nguyên |
| `--------- Format Address -------Format: Done` | đề in dính 1 dòng | 2 dòng | lỗi dàn trang của đề (bản cũ cũng 2 dòng) |
| File mẫu | bản cũ tự tạo `import.csv` + in `Demo data file created` | `import.csv` **có sẵn ở gốc project** | theo cách các bài file khác của bộ |
| Thông báo lỗi | đề không có | `Path doesn't exist`, `Cannot read file`, `Cannot write file`, `No CSV file has been imported` | cần cho các trường hợp sai |
| Mọi cột được `trim` và ghép lại bằng `", "` | đề chỉ nói Name/Address | vậy | dòng `3,le  van   c ,...` không có dấu cách sau `,` |
| Kiến trúc | 1 lớp `CSVFormatter` | repository + service + Strategy | luật thầy |
