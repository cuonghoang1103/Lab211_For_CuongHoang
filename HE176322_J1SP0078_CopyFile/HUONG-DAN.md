# J1.S.P0078 — Copy File (config.properties)

> Bài này **chấm ở chỗ nó hỏng**: không có file cấu hình, thiếu giá trị, thư mục nguồn không có, thư
> mục đích không tạo được… Mỗi trường hợp một câu thông báo trong sơ đồ đề — và bảng test mục 5 bấm đủ.

| | |
|---|---|
| Loại / LOC | Short Assignment · 100 LOC · 2 slot |
| Project | `HE176322_J1SP0078_CopyFile` |
| Chạy | NetBeans: **File ▸ Open Project** → **F6** (thư mục làm việc = gốc project) |
| Lớp chạy | `main.Main` |
| Dữ liệu mẫu | thư mục `source/` ở gốc project: `data1.csv`, `data2.csv`, `notes.txt`, `tone.wav` (256 byte nhị phân) |
| Kiểm tự động | `python3 _tools/verify.py J1SP0078` → 10 kịch bản × 2 locale |

---

## 1. Đề bài nói gì

- File `config.properties` có 3 dòng: `COPY_FOLDER=…` (thư mục nguồn), `DATA_TYPE=*.CSV,*.WAV` (loại file), `PATH=…` (thư mục đích).
- **Chưa có file** → in `File Configure is not found!`, hỏi `Copy Folder:` / `Data Type:` / `Path:`, **tạo file** rồi làm tiếp.
- **Có file** → kiểm: COPY_FOLDER chưa nhập / không tồn tại · DATA_TYPE chưa nhập · PATH chưa nhập / chưa có thì **tạo**.
- Kiểm có lỗi → in lỗi + `System shutdown` và **dừng chương trình**.
- Hợp lệ → chép các file đúng loại từ COPY_FOLDER sang PATH, in danh sách.

**Đề bắt buộc** (Guidelines):

| Thứ | Đề viết | Bài này đặt ở |
|---|---|---|
| `public Config readFileConfig(Config config) throws ExceptionHandle` | Function 1 | `repository/ConfigRepository` — đúng tên, tham số, kiểu trả về; `throws HandleException` (có `// brief:`) |
| `public void createFileConfig(Config config) throws ExceptionHandle` | Function 2 | `repository/ConfigRepository` — như trên |
| `public void checkConfig(Config config) throws ExceptionHandle` | Function 3 | `service/CopyService` — như trên |
| `public List<String> copyFile(Config config)` | Function 4 | `service/CopyService` — đúng chữ ký (có `// brief:`) |
| Lớp `Config`, lớp `ExceptionHandle` | trong chữ ký | `model/Config`, `exceptions/HandleException` — **đổi tên** theo tờ checklist 1.3 (tên lớp exception phải kết thúc bằng `Exception`), có `// brief: ExceptionHandle` ngay trên khai báo |
| Thông báo | 5 hộp trong sơ đồ | `constants/Message` — **chép đúng từng chữ** |

---

## 2. Kiến thức cần biết

### 2.1 Chép file: **luồng byte**, không bao giờ Reader/Writer

| | `FileInputStream`/`FileOutputStream` | `FileReader`/`FileWriter` |
|---|---|---|
| Đọc gì | **byte** nguyên xi | byte → **giải mã thành ký tự** theo bảng mã máy |
| File `.csv` (chữ) | đúng | đúng (vì toàn ASCII) |
| File `.wav` (âm thanh) | **đúng** | **hỏng**: byte ≥ 0x80 không phải chữ hợp lệ → bị thay bằng `?`/U+FFFD |

Đề ghi `DATA_TYPE=*.CSV,*.WAV` → có file âm thanh → **bắt buộc luồng byte**. `source/tone.wav` chứa đủ 256 giá
trị byte; `copyFile` chỉ liệt kê file khi **so từng byte** (`FileUtils.isSameContent`) thấy giống hệt — nên nếu ai đổi
sang `FileReader`, `tone.wav` biến khỏi danh sách và test đỏ.

### 2.2 Cái bẫy `Properties.load()` — vì sao đọc tay

Tên file là `.properties` nên phản xạ là dùng `java.util.Properties`. Nhưng trong `.properties`, dấu `\` là **ký tự
thoát**: dòng `COPY_FOLDER=D:\Data` đọc ra thành **`D:Data`** (mất `\`, không báo lỗi) → chương trình báo
`Can't find folder Source` dù thư mục có thật. Bài này **tách mỗi dòng ở dấu `=` ĐẦU TIÊN**:

```java
int separator = text.indexOf("=");                // vị trí dấu = đầu tiên
String key   = text.substring(0, separator).trim();
String value = text.substring(separator + 1).trim(); // giữ nguyên \ và cả dấu = phía sau
```

Chạy tay dòng `PATH=out/a=b/c`: `indexOf` = 4 → key `PATH`, value `out/a=b/c` (dùng `split("=")` sẽ mất `b/c`).

### 2.3 DATA_TYPE → phần mở rộng

| DATA_TYPE gõ vào | `DataTypeFilter` hiểu thành | Khớp |
|---|---|---|
| `*.CSV,*.WAV` | `.csv`, `.wav` | `data1.csv`, `data2.csv`, `tone.wav` |
| `wav, txt` | `.wav`, `.txt` | `notes.txt`, `tone.wav` |
| `*.PDF` | `.pdf` | (không file nào) |

So **không phân biệt hoa thường**: đề ghi `*.CSV` mà màn hình kết quả là `1.csv`.

### 2.4 Java dùng trong bài

| API | Dùng làm gì |
|---|---|
| `File.isFile()` / `isDirectory()` | có file / có thư mục chưa |
| `File.mkdirs()` | tạo thư mục **kèm cả thư mục cha** còn thiếu (`mkdir` thì không) |
| `File.listFiles(FileFilter)` | liệt kê file trong thư mục, **lọc bằng Strategy** |
| `getCanonicalFile()` | đường dẫn thật — `source` và `./source` là một |
| try-with-resources | đóng luồng kể cả khi lỗi giữa chừng |

---

## 3. Thiết kế

```
HE176322_J1SP0078_CopyFile/
├── source/            dữ liệu mẫu để chép (đề cho D:\Data — không máy nào có)
└── src/
    ├── model/         Config                3 thiết lập (JavaBean)
    ├── dto/           ConfigRequestDTO      3 thiết lập gõ vào + lineList (Main đọc)  (main ──► controller)
    │                  CopyResponseDTO       fileNameList: tên file đã chép             (controller ──► view)
    ├── exceptions/    HandleException       ngoại lệ đề bắt (đề gọi ExceptionHandle), extends Exception
    ├── repository/    ConfigRepository      lineList · loadData · readFileConfig · createFileConfig
    ├── service/       CopyService           saveConfig · copyFiles · checkConfig · copyFile
    │                  DataTypeFilter        Strategy: lọc file theo DATA_TYPE
    ├── controller/    CopyController        Facade: createFileConfig · copyFile
    ├── view/          CopyView              field responseDTO · setResponseDTO · display() (hộp 5)
    ├── constants/     Message, Constants
    ├── utils/         Validation            getText, getChoice
    │                  FileUtils             đọc/ghi dòng, listFiles, mkdirs, copyBinary, isSameContent
    └── main/          Main                  menu + Scanner + form + ĐỌC config.properties
```

| Lớp | Vai | Vì sao ở đó |
|---|---|---|
| `ConfigRepository` | giữ **dữ liệu** của bài: các dòng `config.properties` do `Main` đọc (`lineList`); nơi **duy nhất** biết dòng có dạng `KEY=value` (`readFileConfig` tách, `createFileConfig` ghi qua `FileUtils`) | tờ checklist 1.1: *"Bắt buộc phải có repository"*, repository = data + CRUD đơn giản |
| `CopyService` | kiểm cấu hình, chép file | nghiệp vụ ngoài CRUD = service (Guide) |
| `FileUtils` | thao tác đĩa thuần (byte, dòng, thư mục) | Guide: utils *"đọc/ghi file"*, static |
| `HandleException` | mang câu lỗi của đề | đề đặt tên `ExceptionHandle`; tờ checklist 1.3 bắt tên kết thúc bằng `Exception` → `HandleException` (`// brief: ExceptionHandle`); package `exceptions` (HD) |

**Menu** — đề vẽ hộp `============ Copy Program =========` rỗng, nên bài tự đặt: `1. Copy File` (bài chính),
`2. Input Configure File` (nhập lại cấu hình — không thì phải xoá file tay), `3. Exit`.

**Luồng chức năng 1:**

```
Main: FileUtils.isFile("config.properties")?  ── không ──► hộp 2: in "File Configure is not found!", đọc 3 ô
          └─ inputConfig ─► controller.createFileConfig(requestDTO)   (không render)
                 service.saveConfig ─► repository.createFileConfig(config) ─► FileUtils.writeLines
                 lỗi ─► hộp 3: "File Configure cannot create"
Main: in "---- Check Configure File -----"                     (tiêu đề hộp 4)
Main: FileUtils.readLines("config.properties") ─► requestDTO.lineList   → lỗi: "Can't read File Configure"
Main: controller.copyFile(requestDTO)                            (1 lần)
   service.copyFiles(requestDTO)
       ├─ repository.loadData(requestDTO)                         (giữ các dòng Main đọc)
       ├─ repository.readFileConfig(config)                       (dòng ─► Config)
       ├─ checkConfig(config)                                     → lỗi đầu tiên gặp
       └─ copyFile(config)                                        → tên file đã chép
   controller ──► CopyResponseDTO(fileNameList) ──► view.setResponseDTO(r) ──► view.display()   (1 lần: hộp 5)
Main: catch (Exception e) → in e.getMessage() + "System shutdown", running = false
```

Vì sao chức năng 1 có **thể** gọi controller 2 lần: chỉ khi **chưa có** `config.properties`. Đề ghi
*"After input, program create file config and perform next steps"* và ảnh đề vẽ hộp 2 ─► hộp 3
(`File Configure cannot create`) **không có** tiêu đề `---- Check Configure File -----` — nên file phải được tạo
(`controller.createFileConfig`, **không render**, dùng lại đúng hàm của mục menu 2) **trước** khi `Main` in tiêu đề
hộp 4 và đọc file. Có file sẵn thì chỉ 1 lần gọi. View vẫn render **đúng 1 lần** mỗi luồng (tờ checklist 1.1).
**Nên hỏi thầy**: "chưa có file config thì em gọi `createFileConfig` rồi mới `copyFile` để màn hình đúng hộp 3 của
đề — thầy có chấp nhận không, hay muốn gộp làm 1 lần gọi (khi đó tiêu đề Check in ra trước câu `cannot create`)?"

### 3.1 Design Pattern

| Pattern | 4 yếu tố GoF |
|---|---|
| **Strategy** (Behavioral) | **Problem**: *chép file NÀO* là một luật có thể đổi (theo đuôi, theo cỡ, theo ngày…); nhét `if` vào vòng chép thì mỗi lần đổi phải sửa `copyFile`. **Solution**: `java.io.FileFilter` = **Strategy** (1 hàm `accept`), `DataTypeFilter` = **ConcreteStrategy**, `File.listFiles(filter)` (gọi trong `FileUtils.listFiles`) = **Context** — hỏi `accept` từng file mà không biết luật. `CopyService.copyFile` là nơi **chọn** strategy: `new DataTypeFilter(config.getDataType())`. **Consequences**: ✅ thêm luật = thêm 1 class, `FileUtils`/vòng chép đứng yên (OCP); ❌ thêm 1 file. |
| **Facade** | **Problem**: `Main` phải biết service, repository, view và thứ tự gọi. **Solution**: `CopyController.copyFile(dto)` là một cửa. **Consequences**: `Main` gọn; controller chỉ điều hướng. |
| **Repository** (mẫu kiến trúc) | `ConfigRepository` — đổi `config.properties` sang XML chỉ sửa 1 file. |
| **Decorator** (của Java IO) | `new BufferedInputStream(new FileInputStream(f))` — lớp ngoài **bọc** lớp trong, thêm bộ đệm mà không đổi giao diện `InputStream`. |

**Thầy bảo "chỉ chép file nhỏ hơn 1 KB"** → thêm `service/SizeFilter implements FileFilter`, sửa 1 dòng `new ...Filter` trong `copyFile`.

---

## 4. Code từng bước

> Thầy: *"code cái **model trước rồi đến data**"*.

| Bước | File | Việc |
|---|---|---|
| 1 | `model/Config.java` | 3 field `private` + ctor rỗng + ctor đủ + get/set + `toString` |
| 2 | `dto/ConfigRequestDTO`, `CopyResponseDTO` | JavaBean |
| 3 | `exceptions/HandleException.java` | `extends Exception`, 2 constructor, `// brief: ExceptionHandle` |
| 4 | `utils/FileUtils.java` | `isFile`, `isFolder`, `makeFolder`, `isSamePath`, `readLines` (ném `Can't read File Configure`), `writeLines`, `listFiles`, `copyBinary`, `isSameContent` |
| 5 | `repository/ConfigRepository.java` | `lineList`, `loadData`, **`readFileConfig`** (+ `parseSetting`), **`createFileConfig`** |
| 6 | `service/DataTypeFilter.java` | tách DATA_TYPE (`toExtension`), `accept` |
| 7 | `service/CopyService.java` | `saveConfig`, `copyFiles`, **`checkConfig`**, **`copyFile`** (+ `copyOneFile`) |
| 8 | `view/CopyView.java` | field `responseDTO`, `setResponseDTO`, `display()` |
| 9 | `controller/CopyController.java` | `createFileConfig`, `copyFile` (1 lần `display()`) |
| 10 | `constants/Message`, `Constants` | chép chữ trong sơ đồ; `SETTING_FORMAT`, `EXTENSION_FORMAT` |
| 11 | `utils/Validation`, `main/Main` | `final` + constructor `private`; menu, form 3 ô, đọc `config.properties`, bắt `Exception` → dừng |

**Bẫy hay gặp:**

1. Dùng `FileReader/FileWriter` để chép → `.wav` hỏng mà `.csv` vẫn đúng, nên thử bằng file chữ **không phát hiện ra**.
2. Dùng `Properties.load` → `D:\Data` thành `D:Data` (mục 2.2).
3. `mkdir()` thay `mkdirs()` → `out/a/b` không tạo được vì thiếu `out/a`.
4. Nguồn trùng đích: `new FileOutputStream(file)` **cắt file về 0 byte** trước khi đọc — mất dữ liệu. `checkConfig` so đường dẫn chuẩn hoá và từ chối.
5. Quên đóng luồng → phần cuối trong bộ đệm không được ghi; dùng try-with-resources.

---

## 5. Test trước khi gọi thầy

> Chạy từ NetBeans (thư mục làm việc là gốc project). Muốn quay lại hộp 2 thì **xoá `config.properties`** ở gốc project.

| # | Gõ | Phải thấy |
|---|---|---|
| 1 | (chưa có config) `1` · `source` · `*.CSV,*.WAV` · `dest` | `File Configure is not found!` → form → `---- Check Configure File -----` → `Copy is running...` → `data1.csv`, `data2.csv`, `tone.wav` (**không** có `notes.txt`) → `Copy is finished...` |
| 2 | `1` lần nữa | không hỏi gì (đã có file) — chép đè, cùng danh sách |
| 3 | config mới: nguồn `nosuchfolder` | `Can't find folder Source` + `System shutdown`, chương trình dừng |
| 4 | `2` với cả 3 ô trống, rồi `1` | `Folder Source is not input` |
| 5 | `2`: `source` / trống / `dest`, rồi `1` | `Data type is not input` |
| 6 | `2`: `source` / `*.CSV,*.WAV` / trống, rồi `1` | `Folder Destination is not input` |
| 7 | `2`: … / `source/data1.csv/nested`, rồi `1` | `Can't make folder Destination` |
| 8 | `2`: `source` / `*.CSV` / `./source`, rồi `1` | `Folder Source and Folder Destination are the same` |
| 9 | `2`: `source` / `wav, txt` / `out/a=b/c`, rồi `1` | tạo `out/a=b/c`, chép `notes.txt`, `tone.wav` |
| 10 | `1` với `*.PDF` | danh sách rỗng, vẫn `Copy is finished...` |
| 11 | menu `x`, Enter, `0`, `9` | `You must input a number.` ×2, `Please choose from 1 to 3.` ×2 |
| 12 | *(tay)* tạo **thư mục** tên `config.properties` ở gốc project, chạy `1` | `File Configure is not found!` → form → `File Configure cannot create` + `System shutdown` |
| 13 | *(tay, macOS/Linux)* `chmod 000 config.properties`, chạy `1` | `Can't read File Configure` + `System shutdown` |

---

## 6. Debug

| Việc | Cách làm |
|---|---|
| Breakpoint | dòng `FileUtils.copyBinary(source, target);` trong `CopyService.copyOneFile` (được `copyFile` gọi cho từng file) |
| Chạy | **Ctrl+F5**, chọn `1` với cấu hình ở test #1 |
| Quan sát | **Variables**: `source` (file đang chép), `fileNameList` (tên đã chép); **F7** vào `copyBinary` xem `count` mỗi vòng |
| Chứng minh lọc | breakpoint trong `DataTypeFilter.accept` — `notes.txt` trả `false` |
| Chứng minh dừng | breakpoint ở `throw new HandleException(Message.SOURCE_NOT_FOUND)` → **F8** rơi vào `catch (Exception e)` của `Main` → `running = false` |

---

## 7. Câu hỏi thầy hay hỏi

| Câu hỏi | Trả lời mẫu |
|---|---|
| 4 tính chất OOP ở đâu? | **Đóng gói**: field `private` trong `Config`, 2 DTO. **Kế thừa**: `HandleException extends Exception`; `DataTypeFilter implements FileFilter`. **Đa hình**: `File.listFiles` gọi `accept` qua biến kiểu `FileFilter` → chạy bản của `DataTypeFilter`; `@Override toString()` trong `Config`. **Trừu tượng**: `Main` gọi `controller.copyFile(dto)` không biết có file config, repository hay filter. |
| Sao `HandleException` extends `Exception`, không `RuntimeException`? | Ngoại lệ **checked**: compiler bắt mọi nơi gọi `checkConfig` phải xử lý — config sai là chuyện **bình thường** của chương trình, không phải bug. |
| `readFileConfig` trả `Config` vì sao, khi đã sửa chính `config` truyền vào? | Đề bắt kiểu trả về đó; nó cho viết gọn `config = readFileConfig(new Config())`. |
| `copyFile` sao trả `List<String>` mà không `ArrayList`? | **Đề ghi chữ ký** `List<String> copyFile(Config)` nên em giữ (dòng có `// brief:`). Bên trong em khai báo `ArrayList<String> fileNameList = new ArrayList<>()`. `List` là **interface** (hợp đồng: add/get/size), `ArrayList` là **lớp cài đặt** bằng mảng động; trả về kiểu interface cho phép sau này đổi lớp cài đặt mà nơi gọi không phải sửa. Chỗ nào em tự quyết thì em dùng kiểu cụ thể `ArrayList`. |
| `copyFile` không có `throws` — file lỗi thì sao? | Báo bằng **vắng mặt**: file không chép được hoặc chép ra khác nguồn thì không có trong danh sách. Đó là giới hạn của chữ ký đề cho. |
| `checkConfig` trả `void`? | Kết quả là **có lỗi hay không**; có thì `throw`, không thì chạy tiếp — không còn gì để trả. |
| Sao `checkConfig` dừng ở lỗi đầu tiên? | Hộp lỗi trong đề là **danh mục**: "not input" và "can't find" không thể cùng đúng; kiểm "có tồn tại không" một tên rỗng là vô nghĩa. |
| Access modifier? | Field đều `private`. `isBlank`, `isSameFolder`, `copyOneFile` **private** — chỉ `CopyService` dùng; `parseSetting` private trong `ConfigRepository`, `toExtension` private trong `DataTypeFilter`. Hàm `public` là hàm lớp khác gọi: `checkConfig`/`copyFile` (**đề ghi `public`** trong chữ ký; controller dùng chúng qua `copyFiles`), `loadData`/`readFileConfig`/`createFileConfig` (service gọi), `accept` (JDK gọi). Constructor `Message`, `Constants`, `Validation`, `FileUtils` **private** — không cho `new`. |
| static ở đâu, bỏ thì sao? | Chỉ ở `utils` (Guide: *"phải dùng static method"*), hằng `constants`, hàm trong `Main`. `FileUtils.copyBinary` không dùng dữ liệu đối tượng nào. Bỏ `static` → `FileUtils.copyBinary(...)` lỗi biên dịch; phải bỏ `private` constructor, `new FileUtils()` trong service rồi gọi qua đối tượng. `Main` có biến static? **Không** — Scanner là biến cục bộ. |
| Ai kiểm "có file config chưa", ai **đọc** file? | `Main` (tờ checklist 1.1: *"đọc từ file ... thực hiện ở Main"*): `FileUtils.isFile(Constants.CONFIG_FILE)` quyết định hỏi form; `FileUtils.readLines(Constants.CONFIG_FILE)` đọc các dòng vào `ConfigRequestDTO.lineList`. Repository giữ các dòng đó; `readFileConfig` tách chúng thành `Config`. |
| Chép file (đọc byte nguồn) sao không ở `Main`? | Chép là **nghiệp vụ** `copyFile(Config)` của đề — luồng byte từ nguồn sang đích, không phải đọc **dữ liệu** vào chương trình; nên ở service, qua `FileUtils.copyBinary`. Dữ liệu của bài (`config.properties`) thì `Main` đọc. |
| Sao Main gọi controller 2 lần ở chức năng 1? | Chỉ khi **chưa có** file config: `createFileConfig` (tạo file, **không render** — lỗi thì in hộp 3 ngay sau form như ảnh đề) rồi `copyFile` (1 lần). Có file sẵn thì chỉ gọi `copyFile`. Xem mục 3 "Vì sao chức năng 1 có thể gọi controller 2 lần". |
| View nhận dữ liệu thế nào? | Qua **thuộc tính**: controller gọi `copyView.setResponseDTO(r)` rồi `copyView.display()` (không tham số) **1 lần**; `display()` in cả hộp 5 (`Copy is running...`, tiêu đề, tên file, `Copy is finished...`). Tiêu đề hộp 4 do `Main` in (như mọi tiêu đề mục), lỗi do `Main` in `e.getMessage()`. |
| Sao đổi tên `ExceptionHandle` → `HandleException`? | Tờ checklist 1.3: *"Tên của class exception kết thúc bằng Exception"*. Tên đề đặt vẫn ghi ở `// brief: ExceptionHandle` ngay trên khai báo lớp. **Nên hỏi thầy** nếu thầy muốn giữ đúng tên đề. |
| `Main` sao `final` + constructor `private`? | Tờ checklist 3.4: lớp chỉ có hàm `static` phải `final` và có constructor `private`. |
| SOLID? | **S**: repository giữ dữ liệu config, service lo nghiệp vụ, `FileUtils` lo byte, view lo in. **O**: luật lọc mới = class `FileFilter` mới. **L**: mọi `FileFilter` thay được cho nhau trong `listFiles`. **I**: `FileFilter` chỉ 1 hàm. **D**: `FileUtils.listFiles` phụ thuộc `FileFilter` (trừu tượng), không phụ thuộc `DataTypeFilter`. |
| Độ phức tạp? | Chép: O(tổng số byte); lọc: O(số file × số loại). |

---

## 8. Thầy đổi yêu cầu tại chỗ

| Thầy bảo | Sửa | Không đụng |
|---|---|---|
| Chép cả thư mục con | `FileUtils.listFiles` đệ quy + `DataTypeFilter.accept` nhận thư mục | controller, view, main |
| Không ghi đè file đã có ở đích | `CopyService.copyOneFile`: `if (target.exists()) { return false; }` | các lớp khác |
| In thêm kích thước file | `CopyResponseDTO` thêm danh sách cỡ, `CopyView.display` | repository |
| Thêm khoá `MAX_SIZE` trong config | `Config`, `ConfigRequestDTO`, `Constants`, `Message`, `ConfigRepository` (đọc/ghi), `Main` (form), filter mới | `CopyController` |
| Đổi câu thông báo | chỉ `Message` | mọi file khác |

---

## 9. Chỗ khác với đề / bản cũ

| Chỗ | Đề / bản cũ | Bài này | Lý do |
|---|---|---|---|
| Prompt form | Đặc tả: `Enter Copy Folder:` · sơ đồ + Function 2: `Copy Folder:` | `Copy Folder:` / `Data Type:` / `Path:` | theo **màn hình** (sơ đồ) và Function 2, giống bản cũ |
| Menu | đề vẽ hộp rỗng | `1. Copy File`, `2. Input Configure File`, `3. Exit` | bản cũ có thêm `3. Stream Test` (thí nghiệm byte/ký tự, không thuộc đề) — bỏ cho gọn, thay bằng so từng byte sau khi chép |
| Nguồn trùng đích | đề không nói | `Folder Source and Folder Destination are the same` | tránh mất dữ liệu (câu này **không có trong đề**, như bản cũ) |
| Dữ liệu mẫu | bản cũ tự tạo `source/` lần chạy đầu và in `Sample folder created: source` | ship sẵn `source/` ở gốc project, không in gì | mọi kịch bản test chạy độc lập; màn hình không có dòng lạ ngoài đề |
| `config.properties` | — | **không ship sẵn** | để lần chạy đầu đi đúng hộp 2 của đề (`File Configure is not found!`) |
| Kịch bản test | bản cũ: 10 lần chạy dùng chung 1 thư mục | `REPLACE_REFERENCE = True`, 10 kịch bản tự đủ | `verify.py` cho mỗi lần chạy một thư mục mới |
| Kiến trúc | `bo/CopyManager` + Scanner trong `Validator` | MVC Guide: repository + service + `FileUtils`, Scanner chỉ ở `main` | luật thầy |
| `ExceptionHandle` | bản cũ để trong `utils` | `exceptions/HandleException` | HD: package `exceptions`; tờ checklist 1.3: tên exception kết thúc bằng `Exception` (`// brief: ExceptionHandle`) — **hỏi thầy** nếu muốn giữ tên đề |
| Đọc `config.properties` | bản trước: repository tự đọc; controller có `isConfigExist` | `Main` kiểm + đọc (`FileUtils.isFile/readLines`), repository giữ `lineList` | tờ checklist 1.1: đọc file ở `Main` |
| Hộp 3 `File Configure cannot create` | bản trước: in **sau** tiêu đề `---- Check Configure File -----` | in **ngay sau form**, không có tiêu đề Check — đúng ảnh đề (hộp 2 ─► hộp 3) | tạo file trước (`createFileConfig`), rồi mới in tiêu đề hộp 4 |
| View | bản trước: `showMessage(String)` ×2 + `display()` trong 1 luồng | 1 field `responseDTO` + `display()` không tham số, 1 lần | tờ checklist 1.1: render 1 lần/luồng; `Copy is running...` in cùng lần render (sau khi chép xong — màn hình giống hệt vì lúc chép không in gì) |

---

## 10. Tờ checklist 25 mục — bài này đạt thế nào

| Mục | Chỗ trong code |
|---|---|
| 1.1 MVC + repository | `repository/ConfigRepository` (`lineList`); `Main` kiểm + đọc `config.properties` bằng `FileUtils`; `CopyController.copyFile` gọi `copyView.display()` đúng 1 lần; controller không import `model` |
| 1.1 View | `CopyView` chỉ có `setResponseDTO` + `display()` không tham số |
| 1.3 / 1.5 Tên | `HandleException` (`// brief: ExceptionHandle`); `lineList`, `settingList`, `fileNameList`, `extensionList`, `fileList`, `fileArray`, `bufferArray` |
| 2.6 / 3.7 | biến khai báo đầu block và khởi tạo luôn: `String line = "";`, `int choice = 0;`, `byte[] bufferArray = new byte[Constants.BUFFER_SIZE]; int count = 0;` (`FileUtils.copyBinary`), `String key = ""; String value = "";` (`ConfigRepository.parseSetting`) |
| 2.8 | dòng trống sau vùng khai báo, trước mọi comment, giữa các khối `if` (vd `CopyService.checkConfig`) |
| 3.3 | `if ((choice < min) \|\| (choice > max))`, `(value == null) \|\| value.trim().isEmpty()`, `... \|\| (separator < 0)` |
| 3.4 | `public final class Main` + `private Main() { }`; `FileUtils`, `Validation`, `Constants`, `Message` cũng vậy |
| 3.8 | không nối chuỗi bằng `+`: `String.format(Constants.SETTING_FORMAT, ...)`, `String.format(Constants.EXTENSION_FORMAT, ...)` |
| Còn lại (rủi ro chấp nhận) | `String[] args` của `main`; tham số setter/constructor trùng tên field (`this.x = x`); chức năng 1 gọi controller 2 lần **khi chưa có file config** (lý do ở mục 3) |
