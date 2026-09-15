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
if (file.isFile() && countWordInFile(file.getPath(), word) > 0) → thêm tên
```
→ hai mục **không bao giờ mâu thuẫn** về cùng một file. Chỉ tìm trong thư mục đó (không vào thư mục con),
kết quả **sắp xếp A–Z** (thứ tự `listFiles()` khác nhau giữa các máy).

---

## 3. Thiết kế

```
src/
├── model/      TextFile              tên file + các dòng (JavaBean)
├── dto/        WordRequestDTO        path, word                     (main ──► controller)
│               WordResponseDTO       count / ArrayList tên file      (controller ──► view)
├── service/    WordMatcher           «interface» Strategy: countMatches(line, word)
│               WholeWordMatcher      nguyên từ, phân biệt hoa thường
│               WordService           2 hàm của đề (Context)
├── controller/ WordController        cắm WholeWordMatcher vào service; countWord · findFile
├── view/       WordView              displayCount · displayFileNames
├── constants/  Message, Constants
├── utils/      FileUtils (isExist · isFile · listFiles · readLines) · Validation
└── main/       Main
```

| Câu hỏi | Trả lời |
|---|---|
| Sao không có repository? | Không giữ dữ liệu nào trong bộ nhớ, không CRUD — đọc thẳng ổ đĩa mỗi lần. |
| Sao từ trống không bị chặn ở `Main`? | Đề giao việc báo lỗi cho chính `countWordInFile` (*"List of exception"*) → service ném `Word must not be blank.` |

**Luồng mục 2:**

```
Main: in tiêu đề ─► inputRequest (path, word) ─► controller.findFile(dto)   (1 lần)
   service.getFileNameContainsWordInDirectory(path, word)
       kiểm word · kiểm thư mục · FileUtils.listFiles · countWordInFile(từng file) > 0 ?
   ─► WordResponseDTO ─► view.displayFileNames()
```

### 3.1 Design Pattern

| Pattern | Name · Problem · Solution · Consequences |
|---|---|
| **Strategy** | **Problem**: "khớp" có thể là nguyên từ, không phân biệt hoa thường, chuỗi con… — thầy rất dễ bảo đổi. **Solution**: `WordMatcher` = Strategy; `WholeWordMatcher` = ConcreteStrategy; `WordService` = Context nhận strategy **qua constructor**; `WordController` chọn: `new WordService(new WholeWordMatcher())`. **Consequences**: ✅ đổi luật = thêm 1 lớp + sửa 1 dòng, cả 2 mục đổi theo; ❌ thêm 2 file. |
| **Facade** | `WordController`. |
| **MVC** (thầy: "MVC JSP") | `TextFile`, DTO là JavaBean. |

**Thầy bảo "không phân biệt hoa thường"**: tạo `IgnoreCaseWordMatcher implements WordMatcher`
(dùng `token.equalsIgnoreCase(word)`), sửa **1 dòng** trong constructor `WordController`.

---

## 4. Code từng bước

> Thầy: *"code cái **model trước rồi đến data**"*.

| Bước | File | Việc |
|---|---|---|
| 1 | `model/TextFile.java` | `name`, `ArrayList<String> lines`, constructor, get/set |
| 2 | `dto/WordRequestDTO`, `WordResponseDTO` | JavaBean |
| 3 | `utils/FileUtils.java` | `isExist`, `isFile`, `listFiles`, `readLines` |
| 4 | `service/WordMatcher`, `WholeWordMatcher` | Strategy |
| 5 | `service/WordService.java` | **2 hàm của đề** + `checkWord` |
| 6 | `view/WordView`, `controller/WordController` | |
| 7 | `constants/*`, `utils/Validation` | |
| 8 | `main/Main.java` | menu + `inputRequest` |

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
| Bước | ở `WordService.countWordInFile` bấm **F7** vào `wordMatcher.countMatches` → nhảy vào `WholeWordMatcher` (đa hình qua interface) |

---

## 7. Câu hỏi thầy hay hỏi

| Câu hỏi | Trả lời mẫu |
|---|---|
| 4 tính chất OOP? | **Đóng gói**: field `private` trong `TextFile`, `WordService.wordMatcher`. **Kế thừa**: `WholeWordMatcher implements WordMatcher`. **Đa hình**: service gọi `wordMatcher.countMatches` qua kiểu interface. **Trừu tượng**: interface chỉ nói "đếm được", không nói cách. |
| `checkWord` sao `private`? | Chỉ `WordService` dùng. |
| 2 hàm của đề sao `public`? | Đề ghi `public`; controller gọi. |
| `countWordInFile` trả `int`? | Số lần là số đếm nguyên. |
| **`List` hay `ArrayList`?** | `List` là interface, `ArrayList` là lớp cài đặt bằng mảng động. Kiểu trả về `List<String>` là **đề bắt** (comment `// brief:`); bên trong em tạo `ArrayList`, và DTO khai báo `ArrayList`. |
| `FileUtils` static? Bỏ thì sao? | Guide: utils static, final, constructor private. Bỏ `static` → `FileUtils.readLines(...)` lỗi biên dịch; phải `new FileUtils()` trong service. |
| Sao `WordService` nhận matcher qua constructor? | **Dependency Inversion**: service không biết lớp cụ thể; controller là nơi chọn. |
| `Bout:` là gì? | Chữ **của đề** (có lẽ gõ nhầm "Count") — giữ nguyên để khớp màn hình đề. |
| SOLID? | **S**: `FileUtils` đọc đĩa, matcher so từ, service điều phối, view in. **O**: luật khớp mới = lớp mới. **L**: mọi `WordMatcher` thay nhau được. **I**: interface 1 hàm. **D**: service phụ thuộc `WordMatcher`. |

---

## 8. Thầy đổi yêu cầu tại chỗ

| Thầy bảo | Sửa | Không đụng |
|---|---|---|
| Không phân biệt hoa thường | `IgnoreCaseWordMatcher` + 1 dòng ở `WordController` | `WordService`, `Main`, `View` |
| Đếm cả chuỗi con (`latest` có `test`) | `SubstringWordMatcher` (dùng `indexOf` có bước nhảy) | như trên |
| Tìm cả thư mục con | `WordService.getFileNameContainsWordInDirectory` gọi đệ quy, in đường dẫn | matcher |
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
