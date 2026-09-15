# J1.S.P0075 — Handle File

| | |
|---|---|
| Loại / LOC | Short Assignment · 100 LOC · 2 slot |
| Project | `HE176322_J1SP0075_HandleFile` |
| Chạy | NetBeans: **File ▸ Open Project** → **F6** (thư mục chạy = thư mục project) |
| Lớp chạy | `main.Main` |
| Dữ liệu mẫu | `test.txt` (1 dòng `Hello`) và thư mục `data/` ở **gốc project** |
| Kiểm tự động | `python3 _tools/verify.py J1SP0075` → 2 kịch bản × 2 locale |

---

## 1. Đề bài nói gì

| Mục | Làm gì |
|---|---|
| 1 Check Path | nhập đường dẫn → `Path to file` / `Path to Directory` / `Path doesn't exist` |
| 2 Java files | nhập thư mục → in tên các file `.java`, rồi `Result n file!` |
| 3 Size > n | nhập **n (KB) trước**, rồi thư mục (sai thì hỏi lại) → in tên file > n KB, rồi `Result n files!` |
| 4 Write | nhập **nội dung trước**, rồi đường dẫn file (sai thì hỏi lại) → ghi thêm vào **cuối** file → `Write done` |
| 5 Count | nhập file → `Total:n` (số **từ**, cách nhau bởi khoảng trắng) |
| 6 Exit | |

**Đề bắt buộc:**

| Hàm của đề | Chữ ký trong bài | Ở đâu |
|---|---|---|
| `public void checkInputPath(String path) throws Exception` | giữ nguyên — trả lời bằng **3 Exception** như đề | `service/FileService` |
| `List<String> getAllFileNameJavaInDirectory(String path) throws Exception` | giữ nguyên (kiểu `List` do đề, có comment `// brief:`) | `service/FileService` |
| `public static File[] getFileWithSizeGreaterThanInput(String path, int size)` | **bỏ `static`** (xem mục 9) | `service/FileService` |
| `public boolean appendContentToFile(String path, String contentInput)` | giữ nguyên | `service/FileService` |
| `public int countCharacter(String path)` | giữ nguyên (đếm **từ**, tên do đề) | `service/FileService` |
| Lớp gợi ý: `BufferedReader/Writer, File, FileFilter, FileReader/Writer, FilenameFilter, IOException, ArrayList, List` | dùng **đủ** | `utils/FileUtils`, `service/*Filter` |
| `Path doesn't exist` · `Value of size is digit` · `Path to file` · `Path to Directory` | chép đúng | `constants/Message` |

---

## 2. Kiến thức cần biết

### 2.1 `java.io.File`

| Hàm | Trả về |
|---|---|
| `exists()` | có gì ở đường dẫn không (file **hoặc** thư mục) |
| `isFile()` / `isDirectory()` | loại |
| `length()` | kích thước **byte** → so với `n * 1024` |
| `list(FilenameFilter)` | mảng **tên**; **`null`** nếu không phải thư mục |
| `listFiles(FileFilter)` | mảng **File**; **`null`** nếu không phải thư mục |

`FilenameFilter.accept(dir, name)` nhận **tên** → hợp lọc theo đuôi `.java`.
`FileFilter.accept(file)` nhận **cả File** → hợp lọc theo `length()`.

### 2.2 Ghi thêm, không ghi đè

```java
new FileWriter(path, true)   // true = append; thiếu nó thì file bị XOÁ TRẮNG rồi mới ghi
```

### 2.3 Đếm từ — chạy tay

`test.txt` mẫu = `Hello`. Mục 4 thêm `input data` → file thành 2 dòng.

| Dòng | `trim().split("\\s+")` | Số từ |
|---|---|---|
| `Hello` | `[Hello]` | 1 |
| `input data` | `[input, data]` | 2 |
| **Tổng** | | **3** ← đúng `Total:3` của ảnh đề |

Dòng trống phải **bỏ qua**: `"".split("\\s+")` trả **1 phần tử rỗng** → sẽ đếm nhầm 1 từ.

---

## 3. Thiết kế

```
src/
├── model/      TextFile              đường dẫn + các dòng; countWords()
├── dto/        FileRequestDTO        path, size, content      (main ──► controller)
│               FileResponseDTO       ArrayList<String> tên file (controller ──► view)
├── service/    FileService           5 hàm của đề
│               JavaFileFilter        implements FilenameFilter (đuôi .java)
│               SizeFileFilter        implements FileFilter     (length > n KB)
├── controller/ FileController        checkPath · getJavaFiles · getBigFiles · appendContent · countWords
├── view/       FileView              displayFileNames(định dạng dòng tổng) · showMessage
├── constants/  Message, Constants
├── utils/      FileUtils             đọc/ghi/liệt kê file (static)
│               Validation            getChoice · getText · getSize · getExistPath
└── main/       Main                  menu + Scanner
```

| Câu hỏi | Trả lời |
|---|---|
| Sao không có `repository`? | Không có tập dữ liệu nào được **giữ trong bộ nhớ** và CRUD — mỗi mục đọc thẳng ổ đĩa. |
| `FileUtils` vs `FileService`? | Guide: utils = *"đọc/ghi file"* (static) — **cách** chạm ổ đĩa. Service = **nghiệp vụ**: lọc cái gì, đếm cái gì, lỗi nào. |
| Mục 1: "Path to file" là **Exception**? | Đề ghi thế (Return value là 3 Exception). Controller **bắt** và đưa `e.getMessage()` sang **view** in — vì đó là kết quả, không phải lỗi. |

**Luồng mục 3:**

```
Main: inputSize (hỏi lại "Value of size is digit") ─► inputExistPath (hỏi lại "Path doesn't exist")
   ─► controller.getBigFiles(dto)   (1 lần)
        service.getFileWithSizeGreaterThanInput(path, n)
            └─ FileUtils.listFiles(path, new SizeFileFilter(n))   ← File.listFiles gọi accept() từng file
        service.getFileNames(files) ─► FileResponseDTO ─► view.displayFileNames("Result %d files!")
```

### 3.1 Design Pattern

| Pattern | Name · Problem · Solution · Consequences |
|---|---|
| **Strategy** (có sẵn trong JDK) | **Problem**: "liệt kê file" giống nhau, chỉ khác **tiêu chí giữ lại** (đuôi `.java`, kích thước). **Solution**: `FilenameFilter`/`FileFilter` = Strategy; `JavaFileFilter`, `SizeFileFilter` = ConcreteStrategy; `File.list/listFiles` (qua `FileUtils`) = Context, gọi `accept()` cho từng mục. **Consequences**: ✅ tiêu chí mới = 1 lớp mới, `FileUtils` không đổi; ❌ thêm lớp nhỏ. |
| **Facade** | `FileController`: `Main` chỉ gọi 1 hàm mỗi mục. |
| **MVC** (thầy: "MVC JSP") | Controller ~ Servlet, View ~ JSP, `TextFile`/DTO là JavaBean. |

**Thầy bảo "liệt kê file .txt" / "file sửa hôm nay"**: tạo `TxtFileFilter implements FilenameFilter`
(hoặc `TodayFileFilter implements FileFilter`), service gọi `FileUtils.listFileNames(path, new TxtFileFilter())`.

---

## 4. Code từng bước

> Thầy: *"code cái **model trước rồi đến data**"*.

| Bước | File | Việc |
|---|---|---|
| 1 | `model/TextFile.java` | `path`, `ArrayList<String> lines`, 2 constructor, get/set, `countWords()` |
| 2 | `dto/FileRequestDTO`, `FileResponseDTO` | JavaBean |
| 3 | `utils/FileUtils.java` | `isExist`, `isFile`, `listFileNames`, `listFiles`, `readLines`, `appendLine` |
| 4 | `service/JavaFileFilter`, `SizeFileFilter` | 2 strategy |
| 5 | `service/FileService.java` | **5 hàm của đề** + `getFileNames` |
| 6 | `view/FileView.java` | `setResponse`, `displayFileNames`, `showMessage` |
| 7 | `controller/FileController.java` | 5 hàm |
| 8 | `constants/Message`, `Constants` | chữ màn hình; số menu, `KILOBYTE = 1024L`, `.java`, `\\s+` |
| 9 | `utils/Validation.java` | `getChoice`, `getText`, `getSize`, `getExistPath` |
| 10 | `main/Main.java` | menu + 5 hàm nhập |

**Bẫy hay gặp:**

1. `new FileWriter(path)` thiếu `true` → mất nội dung cũ.
2. `list()`/`listFiles()` trả **`null`** khi đường dẫn là file → quên kiểm là `NullPointerException`.
3. `size * 1024` kiểu `int` tràn số khi n lớn → nhân với `1024L`.
4. Thư mục chạy của NetBeans là **gốc project** → gõ `test.txt`, `data` là đủ.

---

## 5. Test trước khi gọi thầy

| # | Gõ | Phải thấy |
|---|---|---|
| 1 | `1` · `data` | `Path to Directory` |
| 2 | `1` · `test.txt` | `Path to file` |
| 3 | `1` · `abc` | `Path doesn't exist` |
| 4 | `2` · `data` | `Main.java`, `Student.java`, `Result 2 file!` (không có `sub/Other.java`) |
| 5 | `2` · `test` | `Result 0 file!` |
| 6 | `2` · `abc` | `Path doesn't exist` |
| 7 | `3` · `a` | `Value of size is digit` rồi hỏi lại |
| 8 | `3` · `2` · `nosuch` · `data` | `Path doesn't exist`, hỏi lại; rồi `report.txt`, `Result 1 files!` |
| 9 | `3` · `0` · `data` | 4 file, `Result 4 files!` |
| 10 | `4` · `input data` · `text.txt` · `test.txt` | `Path doesn't exist`, hỏi lại; `Write done` |
| 11 | `5` · `test.txt` | `Total:3` |
| 12 | `5` · `data` | `Cannot read file` |
| 13 | `4` · … · `data` | `Cannot write to file` |
| 14 | menu `x`, `7` | `You must input a number.`, `Please choose from 1 to 6.` |

> Chạy lại mục 11 sau nhiều lần mục 4 thì số tăng — file thật đã bị ghi. Muốn về như cũ: sửa
> `test.txt` còn đúng 1 dòng `Hello`.

---

## 6. Debug

| Việc | Cách làm |
|---|---|
| Breakpoint | dòng `return file.isFile() && file.length() > minBytes;` trong `SizeFileFilter.accept` |
| Chạy | **Ctrl+F5**, chọn `3`, n = `2`, path `data` |
| Quan sát | mỗi lần dừng là một file: xem `file`, `file.length()`, `minBytes` (= 2048) |
| Bước | ở `FileUtils.listFiles` bấm **F7** vào `listFiles(filter)` → JDK gọi ngược `accept()` của em (Strategy) |
| Mục 1 | breakpoint ở `catch` trong `FileController.checkPath` → thấy `e.getMessage()` là `Path to file` |

---

## 7. Câu hỏi thầy hay hỏi

| Câu hỏi | Trả lời mẫu |
|---|---|
| 4 tính chất OOP? | **Đóng gói**: field `private` trong `TextFile`, `SizeFileFilter.minBytes`. **Kế thừa**: `JavaFileFilter implements FilenameFilter`, `SizeFileFilter implements FileFilter`. **Đa hình**: JDK gọi `filter.accept(...)` qua kiểu interface, chạy bản của lớp em. **Trừu tượng**: `Main` gọi `controller.getBigFiles(dto)` không biết file được lọc thế nào. |
| `FileUtils` sao `static` + `final` + constructor `private`? | Guide: utils *"phải dùng static method"*, *"final"*, *"private constructor"* — hàm không dùng dữ liệu đối tượng. |
| **Bỏ `static` ở `FileUtils.readLines`?** | `FileUtils.readLines(...)` lỗi biên dịch; phải bỏ constructor `private`, `new FileUtils()` trong `FileService` rồi gọi qua đối tượng. |
| Sao đề ghi `static` cho `getFileWithSizeGreaterThanInput` mà em bỏ? | Thầy chỉ cho `static` ở utils/constants/main. Hàm này là nghiệp vụ → service, là hàm đối tượng. |
| Hàm 5 hàm service sao `public`? | Controller (lớp khác) gọi. |
| `checkInputPath` trả `void`? | Đề định nghĩa kết quả là **Exception** — không còn gì để return. |
| `appendContentToFile` trả `boolean` luôn `true`? | Đề ghi *"Recording status"*; thất bại đi bằng Exception nên tới được `return` là đã ghi xong. |
| `countCharacter` sao đếm từ? | Đề định nghĩa *"number of character which are separated by a whitespace"* = từ; tên hàm giữ theo đề. |
| **`List` hay `ArrayList`?** | `List` là **interface** (hợp đồng), `ArrayList` là **lớp cài đặt** bằng mảng động. Em khai báo `ArrayList` ở mọi chỗ; riêng kiểu trả về `List<String>` của `getAllFileNameJavaInDirectory` là **đề bắt** (có comment `// brief:`), bên trong vẫn là `ArrayList`. |
| Sao `FileFilter` cho kích thước, `FilenameFilter` cho đuôi? | `FilenameFilter` chỉ nhận tên; muốn biết `length()` phải có cả `File`. |
| SOLID? | **S**: `FileUtils` chạm ổ đĩa, `FileService` quyết nghiệp vụ, `FileView` in. **O**: tiêu chí lọc mới = lớp mới. **L**: mọi `FileFilter` thay nhau được. **I**: mỗi filter chỉ 1 hàm `accept`. **D**: `FileUtils.listFiles` phụ thuộc interface `FileFilter`. |
| Mục 3/4 gọi controller nhiều lần khi nhập sai? | Không: đường dẫn được kiểm bằng `Validation.getExistPath` **trong `Main`**, controller chỉ được gọi **1 lần** với đường dẫn đúng. |

---

## 8. Thầy đổi yêu cầu tại chỗ

| Thầy bảo | Sửa | Không đụng |
|---|---|---|
| Liệt kê file `.txt` | `TxtFileFilter` + 1 hàm service + mục menu | `FileUtils` |
| Tìm cả thư mục con | `FileUtils.listFiles` duyệt đệ quy | filter, service |
| Đếm ký tự thật (không phải từ) | `TextFile.countWords` → cộng `line.length()` | mọi file khác |
| Size nhập theo MB | `Constants.KILOBYTE` → `1024L * 1024`, sửa prompt trong `Message` | logic |

---

## 9. Chỗ khác với đề / bản cũ

| Chỗ | Đề / bản cũ | Bài này | Lý do |
|---|---|---|---|
| `getFileWithSizeGreaterThanInput` | đề: `public static` | hàm đối tượng trong `FileService` | thầy: static chỉ ở utils/constants/main |
| Thông báo size sai | ảnh đề: `Size is digit` | `Value of size is digit` | chữ của Function details (bản cũ cũng vậy) |
| Dữ liệu mẫu | bản cũ: chương trình tự tạo `test.txt` rỗng và in `Demo data file created: test.txt` | `test.txt` + `data/` **có sẵn ở gốc project** | theo cách các bài file khác của bộ (P0058, P0059); ảnh đề cho `Total:3` sau khi thêm `input data` → file mẫu 1 từ |
| Ghi vào thư mục / đọc thư mục | đề không nói | `Cannot write to file` / `Cannot read file` | lỗi thật khác "không tồn tại" |
| Size âm | đề chỉ bắt "numeric" | nhận (liệt kê mọi file) | đề không cấm, giữ như bản cũ |
| Kiến trúc | `bo/ui`, Scanner trong `Validator` | MVC Guide + Strategy (FileFilter) | luật thầy |
