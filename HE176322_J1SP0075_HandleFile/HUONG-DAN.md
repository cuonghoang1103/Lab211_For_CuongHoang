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
| `public static File[] getFileWithSizeGreaterThanInput(String path, int size)` | **bỏ `static`** (có comment `// brief:`, xem mục 9) | `service/FileService` |
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
├── model/      TextFile              path + ArrayList<String> lineList; countWords()
├── dto/        FileRequestDTO        path, size, content, lineList (main ──► controller)
│               FileResponseDTO       fileNameList + message        (controller ──► view)
├── repository/ TextFileRepository    HashMap<String, TextFile> textFileMap: addTextFile · getTextFile · appendContent
├── service/    FileService           5 hàm của đề (+ addTextFile, getFileNameList)
│               JavaFileFilter        implements FilenameFilter (đuôi .java)
│               SizeFileFilter        implements FileFilter     (length > n KB)
├── controller/ FileController        checkPath · getJavaFiles · getBigFiles · appendContent · countWords
├── view/       FileView              field responseDTO · setResponseDTO · display() (không tham số)
├── constants/  Message, Constants
├── utils/      FileUtils             isExist · isFile · listFileNames · listFiles · readLines · appendLine (static)
│               Validation            getChoice · getText · getSize · getExistPath
└── main/       Main                  menu + Scanner + validate + ĐỌC FILE (mục 5)
```

Luồng chung: `Main` (nhập, validate, đọc file) ─► `FileRequestDTO` ─► `FileController` ─►
`FileService` ─► `TextFileRepository` ─► `TextFile`; kết quả ─► `FileResponseDTO` ─►
`view.setResponseDTO(r)` + `view.display()` **đúng 1 lần** mỗi mục menu.

| Câu hỏi | Trả lời |
|---|---|
| Sao bài có `repository`? | Tờ checklist 1.1: *"Bắt buộc phải có repository"*. Dữ liệu của bài là **các file text `Main` đã đọc**: `TextFileRepository` giữ chúng trong `textFileMap` (khoá = đường dẫn) với CRUD đơn giản: `addTextFile` (Create), `getTextFile` (Read), `appendContent` (Update — ghi thêm vào file, việc ghi là của `FileUtils`). Không tính toán, không in, không đọc file. |
| Ai **đọc file**? | `Main` (tờ giấy 1.1: *"đọc từ file thực hiện ở Main"*): mục 5 gọi `FileUtils.readLines(path)` rồi đặt `lineList` vào `FileRequestDTO`. `countCharacter(path)` đếm trên file **repository đang giữ** ở đường dẫn đó. |
| Mục 2, 3 liệt kê thư mục ở đâu? | Ở **service** (qua `FileUtils.listFileNames/listFiles` + filter): liệt kê tên trong thư mục là **nghiệp vụ** của chính hàm đề `getAllFileNameJavaInDirectory(path)` (đề đưa vào `path`), không phải đọc **nội dung** file. |
| View nhận dữ liệu thế nào? | Qua **thuộc tính**: controller gọi `fileView.setResponseDTO(r)` rồi `fileView.display()` (không tham số) **1 lần** mỗi luồng. `display()` in `fileNameList` (nếu có) rồi `message`. |
| Validate ở đâu? | Ở `Main` qua `utils/Validation`: số menu, `n` là số (`Value of size is digit`), đường dẫn mục 3/4 phải tồn tại (hỏi lại). Controller chỉ được gọi khi dữ liệu đã đúng. |
| `FileUtils` vs `FileService`? | Guide: utils = *"đọc/ghi file"* (static) — **cách** chạm ổ đĩa. Service = **nghiệp vụ**: lọc cái gì, đếm cái gì, lỗi nào. |
| Mục 1: "Path to file" là **Exception**? | Đề ghi thế (Return value là 3 Exception). Controller **bắt**, đặt `e.getMessage()` vào `FileResponseDTO.message` rồi đưa **view** in — vì đó là kết quả, không phải lỗi. |

**Luồng mục 3:**

```
Main: inputSize (hỏi lại "Value of size is digit") ─► inputExistPath (hỏi lại "Path doesn't exist")
   ─► controller.getBigFiles(requestDTO)   (1 lần)
        service.getFileWithSizeGreaterThanInput(path, n)
            └─ FileUtils.listFiles(path, new SizeFileFilter(n))   ← File.listFiles gọi accept() từng file
        service.getFileNameList(fileArray) ─► FileResponseDTO(fileNameList, "Result n files!")
        ─► view.setResponseDTO(r) ─► view.display()   (1 lần)
```

**Luồng mục 5:**

```
Main: path ─► FileUtils.readLines(path)  ("Path doesn't exist" / "Cannot read file" in ở Main)
   ─► requestDTO(path, lineList) ─► controller.countWords(requestDTO)   (1 lần)
        service.addTextFile(requestDTO) ─► repository.addTextFile ─► new TextFile(path, lineList)
        service.countCharacter(path) ─► repository.getTextFile(path).countWords()
        ─► FileResponseDTO("Total:3") ─► view.display()
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
| 1 | `model/TextFile.java` | `path`, `ArrayList<String> lineList`, 2 constructor, get/set, `countWords()` |
| 2 | `dto/FileRequestDTO`, `FileResponseDTO` | JavaBean (`lineList`; `fileNameList` + `message`) |
| 3 | `utils/FileUtils.java` | `isExist`, `isFile`, `listFileNames`, `listFiles`, `readLines` (có `Path doesn't exist`), `appendLine` |
| 4 | `repository/TextFileRepository.java` | `textFileMap`, `addTextFile`, `getTextFile`, `appendContent` |
| 5 | `service/JavaFileFilter`, `SizeFileFilter` | 2 strategy |
| 6 | `service/FileService.java` | **5 hàm của đề** + `addTextFile` + `getFileNameList` |
| 7 | `view/FileView.java` | field `responseDTO`, `setResponseDTO`, `display()` |
| 8 | `controller/FileController.java` | 5 hàm, mỗi hàm 1 lần `display()` |
| 9 | `constants/Message`, `Constants` | chữ màn hình; số menu, `KILOBYTE = 1024L`, `.java`, `\\s+` |
| 10 | `utils/Validation.java` | `getChoice`, `getText`, `getSize`, `getExistPath` |
| 11 | `main/Main.java` | `final` + constructor `private`; menu + 5 hàm nhập (mục 5 đọc file) |

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
| Breakpoint | dòng `return file.isFile() && (file.length() > minBytes);` trong `SizeFileFilter.accept` |
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
| **Bỏ `static` ở `FileUtils.readLines`?** | `FileUtils.readLines(...)` trong `Main` lỗi biên dịch; phải bỏ constructor `private`, `new FileUtils()` trong `Main` rồi gọi qua đối tượng. |
| `Main` sao `final` + constructor `private`? | Tờ checklist 3.4: lớp chỉ có hàm `static` phải `final` và có constructor `private` — không ai `new Main()`, không ai kế thừa. |
| Sao đề ghi `static` cho `getFileWithSizeGreaterThanInput` mà em bỏ? | Thầy chỉ cho `static` ở utils/constants/main. Hàm này là nghiệp vụ → service, là hàm đối tượng. |
| Hàm 5 hàm service sao `public`? | Controller (lớp khác) gọi. |
| `checkInputPath` trả `void`? | Đề định nghĩa kết quả là **Exception** — không còn gì để return. |
| `appendContentToFile` trả `boolean` luôn `true`? | Đề ghi *"Recording status"*; thất bại đi bằng Exception nên tới được `return` là đã ghi xong. |
| `countCharacter` sao đếm từ? | Đề định nghĩa *"number of character which are separated by a whitespace"* = từ; tên hàm giữ theo đề. |
| **`List` hay `ArrayList`?** | `List` là **interface** (hợp đồng), `ArrayList` là **lớp cài đặt** bằng mảng động. Em khai báo `ArrayList` ở mọi chỗ; riêng kiểu trả về `List<String>` của `getAllFileNameJavaInDirectory` là **đề bắt** (có comment `// brief:`), bên trong vẫn là `ArrayList`. |
| Sao `FileFilter` cho kích thước, `FilenameFilter` cho đuôi? | `FilenameFilter` chỉ nhận tên; muốn biết `length()` phải có cả `File`. |
| SOLID? | **S**: `FileUtils` chạm ổ đĩa, `TextFileRepository` giữ dữ liệu, `FileService` quyết nghiệp vụ, `FileView` in. **O**: tiêu chí lọc mới = lớp mới. **L**: mọi `FileFilter` thay nhau được. **I**: mỗi filter chỉ 1 hàm `accept`. **D**: `FileUtils.listFiles` phụ thuộc interface `FileFilter`. |
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
| `getFileWithSizeGreaterThanInput` | đề: `public static` | hàm đối tượng trong `FileService` (comment `// brief:` ngay trên) | thầy: static chỉ ở utils/constants/main. **Nên hỏi thầy**: "đề ghi `static`, em bỏ theo luật static của thầy — thầy muốn giữ `static` như đề không?" |
| Thông báo size sai | ảnh đề: `Size is digit` | `Value of size is digit` | chữ của Function details (bản cũ cũng vậy) |
| Dữ liệu mẫu | bản cũ: chương trình tự tạo `test.txt` rỗng và in `Demo data file created: test.txt` | `test.txt` + `data/` **có sẵn ở gốc project** | theo cách các bài file khác của bộ (P0058, P0059); ảnh đề cho `Total:3` sau khi thêm `input data` → file mẫu 1 từ |
| Ghi vào thư mục / đọc thư mục | đề không nói | `Cannot write to file` / `Cannot read file` | lỗi thật khác "không tồn tại" |
| Size âm | đề chỉ bắt "numeric" | nhận (liệt kê mọi file) | đề không cấm, giữ như bản cũ |
| Kiến trúc | `bo/ui`, Scanner trong `Validator` | MVC Guide + Strategy (FileFilter) | luật thầy |
| Repository | bản trước: không có | `TextFileRepository` | tờ checklist 1.1 *"Bắt buộc phải có repository"* |
| Đọc file mục 5 | bản trước: `FileService.countCharacter` tự đọc | `Main` đọc (`FileUtils.readLines`), `countCharacter(path)` đếm trên file repository giữ | tờ checklist 1.1: đọc file ở `Main` |
| View | bản trước: `showMessage(String)`, `displayFileNames(String)` | 1 field `responseDTO` + `display()` không tham số | tờ checklist 1.1: View nhận qua thuộc tính, render 1 lần/luồng |

---

## 10. Tờ checklist 25 mục — bài này đạt thế nào

| Mục | Chỗ trong code |
|---|---|
| 1.1 MVC + repository | `repository/TextFileRepository` (`textFileMap`); `Main` đọc file mục 5 bằng `FileUtils.readLines`; mỗi hàm của `FileController` gọi `fileView.display()` đúng 1 lần; controller không import `model` |
| 1.1 View | `FileView` chỉ có `setResponseDTO` + `display()` không tham số |
| 1.5 Tên | `lineList`, `fileNameList`, `textFileMap`, `fileArray`, `fileNameArray` |
| 2.6 / 3.7 | biến khai báo đầu block và khởi tạo luôn: `String line = "";` (vòng hỏi của `Main`), `int choice = 0;` (`Validation.getChoice`), `String text = "";` (`TextFile.countWords`) |
| 2.8 | dòng trống sau vùng khai báo, trước mọi comment, giữa các khối `if` (vd `FileService.checkInputPath`) |
| 3.3 | `if ((choice < min) \|\| (choice > max))`, `file.isFile() && (file.length() > minBytes)` |
| 3.4 | `public final class Main` + `private Main() { }`; `FileUtils`, `Validation`, `Constants`, `Message` cũng vậy |
| Còn lại (rủi ro chấp nhận) | `String[] args` của `main`; tham số setter/constructor trùng tên field (`this.x = x`) |
