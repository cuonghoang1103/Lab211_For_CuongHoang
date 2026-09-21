# J1.S.P0077 — Search File By Content

| | |
|---|---|
| Loại / LOC | Short Assignment · 100 LOC · 2 slot |
| Project | `HE176322_J1SP0077_SearchFileByContent` |
| Chạy | NetBeans: **File ▸ Open Project** → **F6** (thư mục chạy = gốc project) |
| Lớp chạy | `main.Main` |
| Dữ liệu mẫu | thư mục `data/` ở **gốc project**: `notes.txt`, `readme.txt`, `report.txt` |
| Kiểm tự động | `python3 _tools/verify.py J1SP0077` → 2 kịch bản × 2 locale |

---

## 1. Đề bài nói gì

| Mục | Làm gì |
|---|---|
| 1 Count Word In File | nhập file `.txt` + một từ → `Bout: n` (số lần xuất hiện) |
| 2 Find File By Word | nhập thư mục + một từ → tiêu đề `File Name` rồi tên các file có chứa từ đó |
| 3 Exit | |

**Đề bắt buộc:**

| Hàm của đề | Ở đâu |
|---|---|
| `public int countWordInFile(String fileSource, String word) throws Exception` | `service/WordService` — đúng chữ ký |
| `public List<String> getFileNameContainsWordInDirectory(String source, String word) throws Exception` | `service/WordService` — đúng chữ ký (`List` do đề, có comment `// brief:`) |
| Màn hình `Enter Path:` · `Enter Word:` · `Bout: 12` · `------------ File Name ------------` | `constants/Message` |

---

## 2. Kiến thức cần biết

### 2.1 Thế nào là "một lần xuất hiện"

Đề không nói rõ → bài chọn: **nguyên từ, phân biệt hoa thường**; dấu câu không thuộc từ.

`notes.txt`:

| Dòng | Tách từ `split("[^\\p{L}\\p{N}]+")` | `test` |
|---|---|---|
| `The test plan is ready.` | The · test · plan · is · ready | 1 |
| `test, test and Test again.` | test · test · and · Test · again | 2 |
| `Testing is not the same word as test.` | Testing · … · test | 1 |
| `The latest test result is here.` | The · latest · test · … | 1 |
| **Tổng** | | **5** |

`latest` và `Testing` **không** tính (tìm chuỗi con bằng `indexOf` sẽ tính nhầm). `Test` ≠ `test`.
`\p{L}` = mọi chữ cái (kể cả tiếng Việt), `\p{N}` = chữ số.

### 2.2 Mục 2 dựng trên mục 1

```java
if (countWord(textFile, word) > 0) → thêm tên      // countWord: cùng hàm mục 1 dùng để đếm
```
→ hai mục **không bao giờ mâu thuẫn** về cùng một file. Chỉ tìm trong thư mục đó (`FileUtils.readFolder`
chỉ đọc các mục là **file**, không vào thư mục con), kết quả **sắp xếp A–Z** (thứ tự `listFiles()` khác
nhau giữa các máy).

---

## 3. Thiết kế

```
src/
├── model/      TextFile              name + ArrayList<String> lineList (JavaBean)
├── dto/        WordRequestDTO        path, word, lineList (mục 1), fileLineMap (mục 2)   (main ──► controller)
│               WordResponseDTO       message "Bout: n" / fileNameList                     (controller ──► view)
├── repository/ TextFileRepository    textFileMap (mục 1) · folderMap (mục 2): addTextFile · getTextFile · addFolder · getTextFileList
├── service/    IWordMatcher          «interface» Strategy: countMatches(line, word)
│               WholeWordMatcher      nguyên từ, phân biệt hoa thường
│               WordService           2 hàm của đề (Context) + addTextFile · addFolder · countWord
├── controller/ WordController        cắm WholeWordMatcher vào service; countWord · findFile
├── view/       WordView              field responseDTO · setResponseDTO · display() (không tham số)
├── constants/  Message, Constants
├── utils/      FileUtils (readTextFile · readFolder; isExist · isFile · readLines là private) · Validation
└── main/       Main                  menu + Scanner + validate + ĐỌC FILE/THƯ MỤC
```

Luồng chung: `Main` (nhập, kiểm từ, đọc file) ─► `WordRequestDTO` ─► `WordController` ─► `WordService`
─► `TextFileRepository` ─► `TextFile`; kết quả ─► `WordResponseDTO` ─► `view.setResponseDTO(r)` +
`view.display()` **đúng 1 lần** mỗi mục menu.

| Câu hỏi | Trả lời |
|---|---|
| Sao bài có `repository`? | Tờ checklist 1.1: *"Bắt buộc phải có repository"*. Dữ liệu của bài là **các file text `Main` đã đọc**: `textFileMap` (đường dẫn file → `TextFile`, mục 1) và `folderMap` (đường dẫn thư mục → các `TextFile` bên trong, mục 2). Chỉ CRUD đơn giản: `addTextFile`, `getTextFile`, `addFolder`, `getTextFileList` — không đếm, không in, không đọc file. |
| Ai **đọc file**? | `Main` (tờ giấy 1.1: *"đọc từ file thực hiện ở Main"*): mục 1 `FileUtils.readTextFile(path)` → `lineList`; mục 2 `FileUtils.readFolder(path)` → `fileLineMap` (tên file → các dòng). Hai hàm của đề đếm/tìm trên file **repository đang giữ** ở đường dẫn đó. |
| Validate ở đâu? | Ở `Main` qua `utils/Validation`: số menu, từ không trống (`Validation.checkWord` — kiểm **trước** khi đụng file, như bản cũ). Lỗi đường dẫn (`File not found`, `Not a file`, `Folder not found`, `Not a folder`) do `FileUtils` ném khi `Main` đọc. |
| Service còn `checkWord` riêng? | Còn — `private`, là *"List of exception"* của chính hàm đề: ai gọi thẳng `countWordInFile(path, "")` vẫn bị từ chối. Qua `Main` thì từ trống đã bị chặn trước. |
| View nhận dữ liệu thế nào? | Qua **thuộc tính**: controller gọi `wordView.setResponseDTO(r)` rồi `wordView.display()` (không tham số) **1 lần** mỗi luồng. `display()` in `message` (mục 1) hoặc tiêu đề + `fileNameList` (mục 2). |

**Luồng mục 2:**

```
Main: in tiêu đề ─► inputRequest (path, word; kiểm từ) ─► FileUtils.readFolder(path) ─► fileLineMap
   ─► controller.findFile(requestDTO)   (1 lần)
        service.addFolder(requestDTO) ─► repository.addFolder ─► mỗi file thành 1 TextFile(name, lineList)
        service.getFileNameContainsWordInDirectory(path, word)
            repository.getTextFileList(path) · countWord(từng TextFile) > 0 ? · sắp xếp tên
        ─► WordResponseDTO(fileNameList) ─► view.setResponseDTO(r) ─► view.display()
```

### 3.1 Design Pattern

| Pattern | Name · Problem · Solution · Consequences |
|---|---|
| **Strategy** | **Problem**: "khớp" có thể là nguyên từ, không phân biệt hoa thường, chuỗi con… — thầy rất dễ bảo đổi. **Solution**: `IWordMatcher` = Strategy; `WholeWordMatcher` = ConcreteStrategy; `WordService` = Context nhận strategy **qua constructor**; `WordController` chọn: `new WordService(new WholeWordMatcher())`. **Consequences**: ✅ đổi luật = thêm 1 lớp + sửa 1 dòng, cả 2 mục đổi theo; ❌ thêm 2 file. |
| **Facade** | `WordController`. |
| **MVC** (thầy: "MVC JSP") | `TextFile`, DTO là JavaBean. |

**Thầy bảo "không phân biệt hoa thường"**: tạo `IgnoreCaseWordMatcher implements IWordMatcher`
(dùng `token.equalsIgnoreCase(word)`), sửa **1 dòng** trong constructor `WordController`.

---

## 4. Code từng bước

> Thầy: *"code cái **model trước rồi đến data**"*.

| Bước | File | Việc |
|---|---|---|
| 1 | `model/TextFile.java` | `name`, `ArrayList<String> lineList`, constructor, get/set |
| 2 | `dto/WordRequestDTO`, `WordResponseDTO` | JavaBean (`lineList`, `fileLineMap`; `message`, `fileNameList`) |
| 3 | `repository/TextFileRepository.java` | `textFileMap`, `folderMap`, `addTextFile`, `getTextFile`, `addFolder`, `getTextFileList` |
| 4 | `utils/FileUtils.java` | `readTextFile`, `readFolder` (+ `isExist`, `isFile`, `readLines` private) |
| 5 | `service/IWordMatcher`, `WholeWordMatcher` | Strategy |
| 6 | `service/WordService.java` | **2 hàm của đề** + `addTextFile`, `addFolder`, `countWord`, `checkWord` |
| 7 | `view/WordView`, `controller/WordController` | `setResponseDTO` + `display()`; mỗi hàm controller 1 lần `display()` |
| 8 | `constants/*`, `utils/Validation` | `getText`, `getChoice`, `checkWord` |
| 9 | `main/Main.java` | `final` + constructor `private`; menu + `inputRequest`, `inputCountWord`, `inputFindFile` |

**Bẫy hay gặp:**

1. Đếm bằng `indexOf` → `latest` bị tính là `test`; từ trống → vòng lặp không bao giờ dừng.
2. `listFiles()` trả `null` khi không đọc được → phải kiểm.
3. Quên `isFile()` → cố đọc thư mục con như file.

---

## 5. Test trước khi gọi thầy

| # | Gõ | Phải thấy |
|---|---|---|
| 1 | `1` · `data/notes.txt` · `test` | `Bout: 5` |
| 2 | … `Test` / `TEST` | `Bout: 1` / `Bout: 0` |
| 3 | … từ trống | `Word must not be blank.` |
| 4 | `1` · `data/nosuch.txt` | `File not found: data/nosuch.txt` |
| 5 | `1` · `data` | `Not a file: data` |
| 6 | `2` · `data` · `test` | `notes.txt`, `readme.txt` |
| 7 | `2` · `data` · `Test` | `notes.txt` |
| 8 | `2` · `data` · `zebra` | `(no file contains this word)` |
| 9 | `2` · `nosuchfolder` | `Folder not found: nosuchfolder` |
| 10 | `2` · `data/notes.txt` | `Not a folder: data/notes.txt` |
| 11 | menu `x`, `4` | `You must input a number.`, `Please choose from 1 to 3.` |

---

## 6. Debug

| Việc | Cách làm |
|---|---|
| Breakpoint | dòng `if (token.equals(word))` trong `WholeWordMatcher.countMatches` |
| Chạy | **Ctrl+F5**, `1` · `data/notes.txt` · `test` |
| Quan sát | `token` lần lượt `The`, `test`, …; `count` tăng; đến `latest` thì **không** tăng |
| Bước | ở `WordService.countWord` bấm **F7** vào `wordMatcher.countMatches` → nhảy vào `WholeWordMatcher` (đa hình qua interface) |

---

## 7. Câu hỏi thầy hay hỏi

| Câu hỏi | Trả lời mẫu |
|---|---|
| 4 tính chất OOP? | **Đóng gói**: field `private` trong `TextFile`, `WordService.wordMatcher`. **Kế thừa**: `WholeWordMatcher implements IWordMatcher`. **Đa hình**: service gọi `wordMatcher.countMatches` qua kiểu interface. **Trừu tượng**: interface chỉ nói "đếm được", không nói cách. |
| `checkWord` sao `private`? | Chỉ `WordService` dùng (`Validation.checkWord` là bản `public static` cho `Main`). |
| Interface sao tên `IWordMatcher`? | Tờ checklist 1.3: *"Tên của interface bắt đầu bằng I"*. |
| `Main` sao `final` + constructor `private`? | Tờ checklist 3.4: lớp chỉ có hàm `static` phải `final` và có constructor `private`. |
| 2 hàm của đề sao `public`? | Đề ghi `public`; controller gọi. |
| `countWordInFile` trả `int`? | Số lần là số đếm nguyên. |
| **`List` hay `ArrayList`?** | `List` là interface, `ArrayList` là lớp cài đặt bằng mảng động. Kiểu trả về `List<String>` là **đề bắt** (comment `// brief:`); bên trong em tạo `ArrayList`, và DTO khai báo `ArrayList`. |
| `FileUtils` static? Bỏ thì sao? | Guide: utils static, final, constructor private. Bỏ `static` → `FileUtils.readTextFile(...)` trong `Main` lỗi biên dịch; phải `new FileUtils()` trong `Main`. |
| Sao `WordService` nhận matcher qua constructor? | **Dependency Inversion**: service không biết lớp cụ thể; controller là nơi chọn. |
| `Bout:` là gì? | Chữ **của đề** (có lẽ gõ nhầm "Count") — giữ nguyên để khớp màn hình đề. |
| SOLID? | **S**: `FileUtils` đọc đĩa, repository giữ file, matcher so từ, service điều phối, view in. **O**: luật khớp mới = lớp mới. **L**: mọi `IWordMatcher` thay nhau được. **I**: interface 1 hàm. **D**: service phụ thuộc `IWordMatcher`. |

---

## 8. Thầy đổi yêu cầu tại chỗ

| Thầy bảo | Sửa | Không đụng |
|---|---|---|
| Không phân biệt hoa thường | `IgnoreCaseWordMatcher` + 1 dòng ở `WordController` | `WordService`, `Main`, `View` |
| Đếm cả chuỗi con (`latest` có `test`) | `SubstringWordMatcher` (dùng `indexOf` có bước nhảy) | như trên |
| Tìm cả thư mục con | `FileUtils.readFolder` duyệt đệ quy, khoá map là đường dẫn tương đối | matcher, service |
| In kèm số lần ở mục 2 | `WordResponseDTO` thêm field, `WordView` in | `Main` |

---

## 9. Chỗ khác với đề / bản cũ

| Chỗ | Đề / bản cũ | Bài này | Lý do |
|---|---|---|---|
| `Enter Word: content` (có dấu cách) ở mục 2 | đề: mục 1 không cách, mục 2 có | `Enter Word:` cả hai | đề không nhất quán; giữ như bản cũ |
| Lời nhắc menu | đề không có | `Your choice: ` | như bản cũ |
| Không tìm thấy file | đề không nói | `(no file contains this word)` | tiêu đề trống trông như lỗi (bản cũ) |
| Dữ liệu mẫu | bản cũ tự tạo `data/` + in `Sample folder created: data` | `data/` **có sẵn ở gốc project** | theo cách các bài file khác của bộ |
| Luật khớp | đề không nói | nguyên từ, phân biệt hoa thường | như bản cũ; đổi được bằng Strategy |
| Kiến trúc | `bo/ui`, Scanner trong `Validator` | MVC Guide + Strategy | luật thầy |
| Repository | bản trước: không có | `TextFileRepository` | tờ checklist 1.1 *"Bắt buộc phải có repository"* |
| Đọc file | bản trước: `WordService` tự đọc file/thư mục | `Main` đọc (`FileUtils.readTextFile/readFolder`), 2 hàm đề làm trên file repository giữ | tờ checklist 1.1: đọc file ở `Main` |
| Từ trống | bản trước: chỉ service chặn | `Main` chặn trước (`Validation.checkWord`), service vẫn giữ lỗi của đề | tờ checklist 1.1: validate ở `Main`; thứ tự lỗi như cũ |
| View | bản trước: `displayCount()`, `displayFileNames()` | 1 field `responseDTO` + `display()` không tham số | tờ checklist 1.1: render 1 lần/luồng |
| Tên interface | bản trước: `WordMatcher` | `IWordMatcher` | tờ checklist 1.3 |

---

## 10. Tờ checklist 25 mục — bài này đạt thế nào

| Mục | Chỗ trong code |
|---|---|
| 1.1 MVC + repository | `repository/TextFileRepository` (`textFileMap`, `folderMap`); `Main` đọc file/thư mục bằng `FileUtils`; mỗi hàm của `WordController` gọi `wordView.display()` đúng 1 lần; controller không import `model` |
| 1.1 View | `WordView` chỉ có `setResponseDTO` + `display()` không tham số |
| 1.3 / 1.5 Tên | `IWordMatcher`; `lineList`, `fileLineMap`, `fileNameList`, `textFileList`, `textFileMap`, `folderMap`, `fileArray` |
| 2.6 / 3.7 | biến khai báo đầu block và khởi tạo luôn: `String line = "";` (`Main.inputChoice`, `FileUtils.readLines`), `int choice = 0;`, `File[] fileArray = new File[0];` (`FileUtils.readFolder`) |
| 2.8 | dòng trống sau vùng khai báo, trước mọi comment, giữa các khối `if`/`for` (vd `WordService.getFileNameContainsWordInDirectory`) |
| 3.3 | `if ((choice < min) \|\| (choice > max))`, `if ((word == null) \|\| word.isEmpty())` |
| 3.4 | `public final class Main` + `private Main() { }`; `FileUtils`, `Validation`, `Constants`, `Message` cũng vậy |
| Còn lại (rủi ro chấp nhận) | `String[] args` của `main`; tham số setter/constructor trùng tên field (`this.x = x`) |
