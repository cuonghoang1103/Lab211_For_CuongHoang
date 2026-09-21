# J1.S.P0062 — Analyze file path

> Nhập **một đường dẫn tệp Windows**, tách ra **5 phần** bằng đúng các hàm chuỗi đề gợi ý
> (`indexOf`, `lastIndexOf`, `substring`, `split`). Có **5 hàm đề bắt** — đều **không tham số**, nên
> chúng là hành vi của **đối tượng đường dẫn** (`model/FilePath`).
>
> **Bản 21/09/2026 — sửa theo tờ checklist giấy 25 mục của thầy** (mục 10): thêm
> `repository/PathRepository` giữ đường dẫn (model `FilePath`) — tờ giấy 1.1 *"Bắt buộc phải có
> repository"*; View nhận `responseDTO` qua thuộc tính (`setResponseDTO`), `display()` không tham số;
> field `folders` → `folderArray` (1.5); câu in kết quả thành `String.format(Message.RESULT_…)` (3.8);
> `Main` thành `final` + constructor `private` (3.4). Đối chiếu lại đề từng ký tự: màn hình **không đổi**.

| | |
|---|---|
| Loại / LOC | Short Assignment · 26 LOC · 1 slot |
| Project | `HE176322_J1SP0062_AnalyzeFilePath` |
| Chạy | NetBeans: **File ▸ Open Project** → **F6** |
| Lớp chạy | `main.Main` |
| Kiểm tự động | `python3 _tools/verify.py J1SP0062` → 5 kịch bản × 2 locale |

---

## 1. Đề bài nói gì

- Nhập đường dẫn tới **một tệp**, in: ổ đĩa, phần mở rộng, tên tệp, đường dẫn thư mục, danh sách thư mục.

Màn hình đề (chép đúng chữ):

```
===== Analysis Path Program =====
Please input Path:
C:\Windows\test.txt
----- Result Analysis -----
Disk: C:
Extension: txt
File Name: test
Path: C:\Windows
Folders: [Windows]
```

**Đề bắt buộc** (mục Guidelines):

| Hàm đề bắt | Trả về | Bài này đặt ở |
|---|---|---|
| `public String getPath()` | đường dẫn thư mục, **không** có tên tệp | `model/FilePath` — **giữ nguyên chữ ký** |
| `public String getFileName()` | tên tệp **không** phần mở rộng | `model/FilePath` |
| `public String getExtension()` | phần mở rộng | `model/FilePath` |
| `public String getDisk()` | ổ đĩa | `model/FilePath` |
| `public String[] getFolders()` | các thư mục | `model/FilePath` |
| Gợi ý: *"Using functions LastIndexOf, IndexOf, SubString, Split"* | | dùng đúng 4 hàm này |

---

## 2. Kiến thức cần biết

### 2.1 Tách đường dẫn — chạy tay `C:\Windows\test.txt`

Vị trí: `C`=0 `:`=1 `\`=2 `W`…`s`=3..9 `\`=10 `t`…`t`=11..18.

| Hàm | Cách tính | Kết quả |
|---|---|---|
| `getDisk` | `substring(0, indexOf('\\'))` = `substring(0, 2)` | `C:` |
| `getPath` | `substring(0, lastIndexOf('\\'))` = `substring(0, 10)` | `C:\Windows` |
| tên đủ (hàm phụ `getNameWithExtension`) | `substring(lastIndexOf('\\') + 1)` = `substring(11)` | `test.txt` |
| `getFileName` | tên đủ, cắt trước **dấu chấm cuối** (`lastIndexOf('.')` = 4) | `test` |
| `getExtension` | tên đủ, sau dấu chấm cuối | `txt` |
| `getFolders` | `substring(3, 10)` = `Windows`, rồi `split("\\\\")` | `[Windows]` |

### 2.2 Ba chi tiết thầy hay soi

| Chi tiết | Code | Vì sao |
|---|---|---|
| **Dấu chấm cuối** của **tên tệp** | `name.lastIndexOf(Constants.DOT)` trên `test.txt`, không trên cả đường dẫn | `report.final.docx` → tên `report.final`, đuôi `docx`; thư mục có dấu chấm không bị nhầm |
| `split` cần `"\\\\"` | `Constants.BACKSLASH_REGEX` | `split` nhận **regex**; `\` là ký tự đặc biệt của regex → phải `\\` trong regex → `"\\\\"` trong chuỗi Java |
| `lastDot > 0` (không phải `>= 0`) | `getFileName`/`getExtension` | `.gitignore`: chấm ở vị trí 0 là tệp ẩn, **không** phải phần mở rộng |

### 2.3 Kiểm đường dẫn — vì sao có regex

Bản cũ nhận `abc` và in `Disk: abc` — vô nghĩa. Bài này kiểm bằng `Constants.PATH_PATTERN`
`[A-Za-z]:(\\[^\\/:*?"<>|]+)+`: một chữ ổ đĩa + `:`, rồi **một hoặc nhiều** đoạn `\tên`, tên không chứa
ký tự Windows cấm. Nhờ vậy 5 hàm luôn có ổ đĩa và tên tệp để tìm.

| Gõ | Kết quả |
|---|---|
| *(trống)* | `Path must not be empty.` |
| `abc` · `C:\Windows\` · `C:/Windows/test.txt` · `C:\a?b\c.txt` | `Path must be like C:\Windows\test.txt` |

### 2.4 Java dùng trong bài

| API | Dùng làm gì |
|---|---|
| `indexOf(ch)` / `lastIndexOf(ch)` | vị trí `\` hoặc `.` **đầu tiên** / **cuối cùng**; không có → `-1` |
| `substring(a, b)` / `substring(a)` | cắt từ `a` đến **trước** `b` / đến hết |
| `split(regex)` | cắt chuỗi thành mảng |
| `Arrays.toString(arr)` | ra `[Windows]`, `[Data, Java, Lab211]` đúng dạng đề |
| `String.matches(regex)` | kiểm **cả chuỗi** đúng dạng |

---

## 3. Thiết kế

```
HE176322_J1SP0062_AnalyzeFilePath/src/
├── model/      FilePath           fullPath (JavaBean) + 5 hàm đề bắt  ← thuật toán ở ĐÂY
├── dto/        PathRequestDTO     fullPath                  (main ──► controller)
│               PathResponseDTO    5 câu trả lời (folderArray là String[])  (controller ──► view)
├── repository/ PathRepository     giữ FilePath: saveFilePath(fullPath) / getFilePath()
├── service/    PathService        cất đường dẫn vào repository, lấy FilePath, chép 5 câu trả lời vào DTO
├── controller/ PathController     service ──► view (setResponseDTO + display 1 lần)
├── view/       PathView           field responseDTO; display() in khối "Result Analysis"
├── constants/  Message.java       câu chữ màn hình (RESULT_DISK = "Disk: %s"…)
│               Constants.java     BACKSLASH, DOT, BACKSLASH_REGEX, PATH_PATTERN
├── utils/      Validation         getFilePath(chuỗi) → đường dẫn hoặc ném lỗi
└── main/       Main               Scanner + gọi controller 1 lần
```

| Câu hỏi thiết kế | Trả lời |
|---|---|
| Sao 5 hàm nằm ở **model** mà không ở service? | Đề ghi chữ ký **không tham số** (`public String getPath()`) → hàm phải thuộc **đối tượng đã giữ sẵn đường dẫn**. Đó là hành vi của chính đường dẫn (như `Shape.getArea`). Guide cho model chứa *"thuộc tính và function của đối tượng đang mô tả"*. |
| Vậy service làm gì? | Controller **không được** thấy model (*"chỉ import DTO, View, Service"*) → service cất đường dẫn vào repository, lấy lại `FilePath` rồi chép 5 kết quả sang `PathResponseDTO`. |
| Sao bài có `repository`? | Tờ checklist 1.1: *"**Bắt buộc phải có repository**"*. Repository = **dữ liệu** + CRUD đơn giản: ở đây là đường dẫn chương trình làm việc trên đó (model `FilePath`), với `saveFilePath` / `getFilePath`. Không tách chuỗi, không in — đúng tầng *Controller ↔ Services ↔ Repository ↔ Model*. |
| Sao 5 hàm tính lại mỗi lần gọi thay vì tính 1 lần trong constructor như bản cũ? | Mỗi hàm đề bắt **tự làm việc của nó** bằng `indexOf/substring` — thầy bảo debug `getExtension` thì F7 vào là thấy ngay phép tính. Chuỗi ngắn, tính lại không đáng kể. |

**Luồng chạy:**

```
Main: đọc + validate đường dẫn (hỏi lại khi sai) ──► PathRequestDTO ──► controller.analyzePath(requestDTO)   (gọi 1 lần)
   controller ──► service.analyzePath(requestDTO)
                     ├─ repository.saveFilePath(fullPath)        → new FilePath(fullPath)
                     ├─ filePath = repository.getFilePath()      (model)
                     └─ responseDTO.setDisk(filePath.getDisk()) … setFolderArray(filePath.getFolders())
   controller ──► view.setResponseDTO(responseDTO) ──► view.display()                                      (render 1 lần)
```

### 3.1 Design Pattern trong bài

Bài có **một** cách phân tích cố định do đề chỉ định — không có thuật toán thay thế nhau hay họ lớp con,
nên **không** nhét Strategy/Factory cho có (slide 26 SOLID: abstraction *"chỉ vì SOLID nói vậy"* vi phạm
YAGNI). Pattern có thật:

| Yếu tố | **MVC** (thầy gọi "MVC JSP") | **Facade** |
|---|---|---|
| **Name** | Model–View–Controller | Facade (Structural) |
| **Problem** | đọc, tách chuỗi, in trộn trong `main` → đổi cách in là đụng phép tách | `Main` phải biết `PathService`, `PathView` và thứ tự gọi |
| **Solution** | `FilePath` ~ JavaBean (Model) · `PathView` ~ trang JSP · `PathController` ~ Servlet; dữ liệu đi qua DTO | `PathController.analyzePath(dto)` là **một cửa**: gọi service rồi view |
| **Consequences** | ✅ đổi dạng in chỉ sửa `PathView`; ❌ nhiều file | ✅ `Main` chỉ biết 1 lớp; ❌ controller phải giữ đúng vai điều hướng |

### 3.2 SOLID trong bài

| Nguyên lý | Ở đâu |
|---|---|
| **S** | `FilePath` chỉ biết tách đường dẫn · `PathRepository` chỉ giữ đường dẫn · `PathView` chỉ in · `Validation` chỉ kiểm · `PathService` chỉ chép sang DTO |
| **O** | thêm câu trả lời thứ 6 (vd. "tên tệp đầy đủ") = thêm 1 hàm ở `FilePath` + 1 field DTO + 1 dòng in; 5 hàm cũ đứng yên |
| **L / I / D** | bài không có kế thừa/interface — **không cố gượng**; nói thật với thầy là áp ở bài có họ lớp |

---

## 4. Code từng bước

> Thầy: *"code cái **model trước rồi đến data**"*.

| Bước | File | Việc |
|---|---|---|
| 1 | `model/FilePath.java` | `private String fullPath` + constructor rỗng + constructor đủ + get/set + **5 hàm đề bắt** + `getNameWithExtension` (private) + `toString` |
| 2 | `dto/PathRequestDTO.java`, `PathResponseDTO.java` | JavaBean: constructor rỗng + get/set (`folderArray`) |
| 3 | `repository/PathRepository.java` | field `filePath` · `saveFilePath(fullPath)` · `getFilePath()` |
| 4 | `service/PathService.java` | `analyzePath(requestDTO)` |
| 5 | `view/PathView.java` | field `responseDTO` · `setResponseDTO` · `display()` |
| 6 | `controller/PathController.java` | `analyzePath(requestDTO)` |
| 7 | `constants/Message.java`, `Constants.java` | câu chữ (`RESULT_DISK = "Disk: %s"`…) + `BACKSLASH`, `DOT`, `BACKSLASH_REGEX`, `PATH_PATTERN` |
| 8 | `utils/Validation.java` | `getFilePath` — trống / sai dạng |
| 9 | `main/Main.java` | `final` + `private Main()`; `inputPath` (vòng hỏi lại) + gọi controller **1 lần** |

**Bẫy hay gặp:**

1. `split("\\")` → `PatternSyntaxException`. Phải `"\\\\"`.
2. Tìm dấu chấm trên **cả đường dẫn** → `C:\my.folder\readme` bị tách sai. Tìm trên **tên tệp**.
3. `C:\test.txt` (không thư mục): `substring(first + 1, last)` với `first == last` ra `""`, `"".split(...)` ra
   `[""]` → in `Folders: []` có phần tử rỗng. Vì vậy `getFolders` trả `new String[0]` khi `first == last`.
4. Prompt dùng `println` (đề cho gõ ở **dòng dưới**), không `print`.

---

## 5. Test trước khi gọi thầy

| # | Gõ | Phải thấy |
|---|---|---|
| 1 | `C:\Windows\test.txt` | đúng màn hình đề: `C:` · `txt` · `test` · `C:\Windows` · `[Windows]` |
| 2 | `D:\Data\Java\Lab211\report.final.docx` | `D:` · `docx` · `report.final` · `D:\Data\Java\Lab211` · `[Data, Java, Lab211]` |
| 3 | *(trống)* | `Path must not be empty.` rồi hỏi lại |
| 4 | `abc` · `C:\Windows\` · `C:/Windows/test.txt` · `C:\a?b\c.txt` | `Path must be like C:\Windows\test.txt` (mỗi lần) |
| 5 | `C:\test.txt` | `Path: C:` · `Folders: []` |
| 6 | `  E:\Program Files\Java\README  ` | cắt khoảng trắng; `Extension: ` (trống) · `File Name: README` · `[Program Files, Java]` |
| 7 | `D:\code\lab\.gitignore` | `Extension: ` (trống) · `File Name: .gitignore` |

---

## 6. Debug

| Việc | Cách làm |
|---|---|
| Breakpoint | dòng `return fullPath.substring(0, firstSlash);` trong `FilePath.getDisk`, và dòng `int lastDot = ...` trong `getExtension` |
| Chạy | **Ctrl+F5**, nhập `C:\Windows\test.txt` |
| Quan sát | tab **Variables**: `firstSlash = 2`, `lastSlash = 10`, `name = "test.txt"`, `lastDot = 4` |
| Bước | ở `PathService.analyzePath` bấm **F7** vào từng `filePath.getXxx()` để vào model; **F8** từng dòng; **Ctrl+F7** ra |
| Watch | thêm biểu thức `fullPath.lastIndexOf('\\')` vào **Watches** để thầy thấy con số |

---

## 7. Câu hỏi thầy hay hỏi

### OOP

| Câu hỏi | Trả lời mẫu |
|---|---|
| 4 tính chất OOP ở đâu? | **Đóng gói**: `fullPath` `private` trong `FilePath`, bên ngoài chỉ hỏi qua 5 hàm. **Kế thừa**: `FilePath` `extends Object` ngầm, ghi đè `toString()`. **Đa hình**: `@Override toString()`. **Trừu tượng**: `PathService` gọi `getDisk()` mà không cần biết bên trong dùng `indexOf`; `Main` gọi `controller.analyzePath(dto)` mà không biết có `FilePath`. |
| Sao `FilePath` có constructor rỗng? | **MVC JSP**: model/DTO là **JavaBean** — field `private`, constructor rỗng `public`, get/set. |

### Access modifier, static, kiểu trả về

| Câu hỏi | Trả lời mẫu |
|---|---|
| 5 hàm `getXxx` sao `public`? | Đề ghi `public`; `PathService` (package khác) gọi. |
| `getNameWithExtension` sao `private`? | Chỉ `getFileName`/`getExtension` trong cùng lớp dùng — không phải "hợp đồng" của lớp. |
| `analyzePath` (service, controller), `saveFilePath`/`getFilePath`, `setResponseDTO`, `display` sao `public`? | Được lớp ở **package khác** gọi (main gọi controller; controller gọi service/view; service gọi repository). |
| Hằng `Constants.BACKSLASH` sao `public static final`? | Model, Validation cùng dùng; một bản chung gọi bằng tên lớp; không ai sửa được. |
| `Validation.getFilePath` sao static? Bỏ thì sao? | Không dùng dữ liệu đối tượng nào; Guide bắt utils static. Bỏ `static` → phải bỏ `private` constructor, `new Validation()` trong `Main` rồi gọi qua đối tượng. |
| `inputPath` trong `Main` sao `private static`? | `private`: chỉ `main()` gọi; `static`: `main()` static; thầy cho static **hàm** ở main, cấm static **biến** (Scanner là biến cục bộ). |
| `getFolders` trả `String[]` vì sao? | Đề bắt; nhiều thư mục; `split` trả sẵn `String[]`. |
| `getDisk` trả `String`, không `char`? | Ổ đĩa là `C:` — 2 ký tự; đề bắt `String`. |
| Sao mảng mà không `ArrayList`/`List`? | Đề bắt `String[]`, `split` trả mảng, số thư mục không đổi sau khi tách. `List` là **interface** (hợp đồng), `ArrayList` là **lớp** cài bằng mảng co giãn — khi cần danh sách thêm/bớt thì em khai kiểu cụ thể `ArrayList<String>`. |
| Hàm nào có quá 2 tham số không? | Không — mọi hàm 0–1 tham số (luật thầy V4). |
| View nhận dữ liệu thế nào? | Qua **thuộc tính**: controller gọi `pathView.setResponseDTO(responseDTO)` rồi `pathView.display()` — `display()` **không tham số**, gọi **1 lần** cho cả luồng (tờ checklist 1.1). |
| Validate ở đâu? | Ở `Main` qua `utils/Validation.getFilePath`: sai thì ném `Exception(Message…)`, `Main` bắt, in `e.getMessage()` rồi hỏi lại. Controller/service chỉ nhận đường dẫn đã hợp lệ trong `PathRequestDTO`. |
| Sao `Main` là `final` và có `private Main() { }`? | Tờ checklist 3.4: *"Class chỉ có static method thì phải có private contructor, và khai báo class là final"*. |
| Sao DTO đặt `folderArray` mà hàm đề vẫn là `getFolders()`? | Tờ checklist 1.5: *"tên biến kiểu Array kết thúc bằng Array"* — áp cho **biến/field**. `getFolders()` là **tên hàm đề bắt**, mở đầu bằng động từ `get` → giữ nguyên chữ đề. |

### Ca biên

| Câu hỏi | Trả lời mẫu |
|---|---|
| Tệp không có đuôi (`README`)? | `Extension:` trống, `File Name: README`. |
| `.gitignore`? | Chấm ở vị trí 0 → tệp ẩn, không phải đuôi (`lastDot > 0`). |
| Đường dẫn mạng `\\server\a.txt` hoặc Linux `/home/a.txt`? | Bị từ chối bởi `PATH_PATTERN` — đề là đường dẫn Windows có ổ đĩa. |
| Độ phức tạp? | `O(n)` theo độ dài chuỗi — mỗi `indexOf`/`substring` duyệt chuỗi một lần. |

---

## 8. Thầy đổi yêu cầu tại chỗ

| Thầy bảo | Sửa file | Không phải đụng |
|---|---|---|
| In thêm **tên tệp đầy đủ** (`test.txt`) | đổi `getNameWithExtension` sang `public` (hoặc thêm hàm), thêm field `PathResponseDTO`, dòng in `PathView`, nhãn `Message` | `Main`, `PathController`, `Validation` |
| Nhận cả `/` (đường dẫn kiểu Linux) | `Constants.PATH_PATTERN` + `FilePath` (đổi `/` → `\` trước khi tách) | view, controller |
| In thư mục **mỗi dòng một cái** | chỉ `PathView.display` (vòng `for`) | model, service |
| Kiểm **tệp có tồn tại** thật không | `Validation` thêm `new File(path).exists()` + 1 câu trong `Message` | model, view |

---

## 9. Chỗ khác với đề / bản cũ

| Chỗ | Đề / bản cũ | Bài này | Lý do |
|---|---|---|---|
| Kiểm dạng đường dẫn | bản cũ chỉ kiểm trống — nhận `abc`, in `Disk: abc` | thêm `Path must be like C:\Windows\test.txt` | 5 hàm luôn có ổ đĩa + tên tệp; đề không cho câu lỗi, câu này **là của bài** |
| Câu `Path must not be empty.` | đề **không** cho | giữ chữ bản cũ | |
| Nơi tính 5 phần | bản cũ: tính 1 lần trong constructor, lưu 6 field `final` | mỗi hàm tự tính từ `fullPath` | hàm đề bắt tự làm việc của nó; model là JavaBean có setter |
| `.gitignore` | bản cũ: đuôi `gitignore`, tên rỗng | tên `.gitignore`, đuôi trống | chấm đầu là tệp ẩn |
| Kiến trúc | `entity/ui/utils`, Scanner trong `Validator` | MVC theo Guide, Scanner **chỉ ở `main`** | luật thầy |
| Repository | bản trước 21/09: không có (*"không lưu gì"*) | `repository/PathRepository` giữ `FilePath` | tờ checklist 1.1 *"Bắt buộc phải có repository"* |
| View | bản trước 21/09: `setResponse(response)` | `setResponseDTO(responseDTO)` + `display()` không tham số | tờ checklist 1.1 — nhận qua thuộc tính ResponseDTO |
| Tên field mảng | `folders` | `folderArray` | tờ checklist 1.5 |
| Dòng kết quả | `Message.LABEL_DISK + response.getDisk()` | `String.format(Message.RESULT_DISK, …)` | tờ checklist 3.8 — không cộng chuỗi |

Các kịch bản của bản cũ khớp màn hình đề nên **vẫn được chạy** (không `REPLACE_REFERENCE`), cộng thêm 3
kịch bản mới trong `_tools/tests/J1SP0062.py`.

---

## 10. Tờ checklist 25 mục — bài này đạt thế nào

| Mục | Chỗ trong code |
|---|---|
| 1.1 MVC + repository | `repository/PathRepository` giữ model `FilePath`; `PathService` cất/đọc đường dẫn qua repository rồi mới lấy 5 câu trả lời; controller chỉ import DTO/service/view; `PathView` nhận `responseDTO` qua `setResponseDTO`, `display()` gọi **1 lần**; nhập + validate ở `Main` |
| 1.4 method = động từ | `analyzePath`, `saveFilePath`, `getFilePath`, `inputPath`, 5 hàm `getXxx` của đề |
| 1.5 tên biến | `folderArray` (`String[]` trong `PathResponseDTO`); `requestDTO`/`responseDTO`; không có `ID` |
| 2.6 + 3.7 khai báo đầu block, có khởi tạo | `Main.main`: `sc`, `controller`, `requestDTO` ở đầu; `Main.inputPath`: `String line = "";` ở đầu, trong `while` chỉ `line = sc.nextLine();`; `PathService.analyzePath`: `responseDTO`, `filePath = null` ở đầu |
| 2.8 dòng trống | trước mọi comment (kể cả comment field trong `Constants`, `Message`, DTO), sau vùng khai báo, sau `}` của `if` trước `return` |
| 3.3 ngoặc | `FilePath.getFolders`: `if ((firstSlash < 0) \|\| (firstSlash == lastSlash))`; `Validation.getFilePath`: `(input == null) ? "" : input.trim()` |
| 3.4 | `public final class Main` + `private Main() { }`; `Validation`, `Constants`, `Message` cũng `final` + ctor private |
| 3.8 | không cộng chuỗi: `PathView` in bằng `String.format(Message.RESULT_…, …)` |

Tên đề giữ nguyên (không trái tờ giấy): 5 hàm `getPath`, `getFileName`, `getExtension`, `getDisk`,
`getFolders` — đều mở đầu bằng động từ `get`, không tham số, đúng chữ ký đề.

Kiểm lại: `python3 _tools/verify.py J1SP0062` · `python3 _tools/lint.py HE176322_J1SP0062_*` · `python3 _tools/soat_checklist.py HE176322_J1SP0062_*` → 0 `VI_PHAM`.
`RUI_RO` còn lại chỉ là `String[] args` và tham số setter/constructor trùng tên field (`this.path = path`) — kiểu IDE sinh, được chấp nhận.
