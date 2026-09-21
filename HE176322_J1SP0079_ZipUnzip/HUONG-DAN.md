# J1.S.P0079 — Zip / Unzip (java.util.zip)

> Chương trình nén **chạy không lỗi chưa chắc đã đúng**: quên đóng `ZipOutputStream` thì file `.zip` vẫn
> có, vẫn in `Successfully`, nhưng **không công cụ nào mở được**. Bài này chứng minh bằng cách giải nén lại
> chính file vừa nén (mục 5, test #1).

| | |
|---|---|
| Loại / LOC | Short Assignment · 130 LOC · 2 slot |
| Project | `HE176322_J1SP0079_ZipUnzip` |
| Chạy | NetBeans: **File ▸ Open Project** → **F6** (thư mục làm việc = gốc project) |
| Lớp chạy | `main.Main` |
| Dữ liệu mẫu | `data/hello.txt`, `data/image.bin` (256 byte nhị phân), `data/docs/guide.txt`; `slip-test.zip` (zip "độc" để thử zip slip) |
| Kiểm tự động | `python3 _tools/verify.py J1SP0079` → 6 kịch bản × 2 locale (có kịch bản Test.docx của thầy) |

---

## 1. Đề bài nói gì

- Menu: **Compression · Extraction · Exit**.
- **Nén**: nhập nguồn, thư mục đích, tên file zip → nén → in từng file + `Successfully`. Nguồn là **thư mục** (nén mọi
  file bên trong) **hoặc một tệp**: `Test.docx` trong thư mục đề của thầy gõ `Enter Source Folder: D:/Test2/FileName1.txt`
  rồi `Enter Name: FileZip` → có `D:/Test/FileZip.zip` chứa `FileName1.txt`; giải nén nó vào `D:/Test/` → `Successfully`.
- **Giải nén**: nhập file zip, thư mục đích → giải nén → in từng file + `Successfully`.
- Dùng package **`java.util.zip`**.

**Đề bắt buộc** (Guidelines):

| Thứ | Đề viết | Bài này |
|---|---|---|
| `public static boolean compressTo(String pathSrc, String fileZipName, String pathCompress)` | Function 1 | `service/ZipService.compressTo(ZipRequestDTO requestDTO)` — giữ **tên** và kiểu trả `boolean`; **3** giá trị đi trong RequestDTO (tờ giấy 1.1: tham số chỉ khi *< 3*), bỏ `static` (xem §9); comment `// brief:` chép chữ ký đề ngay trên hàm |
| `public static boolean extractTo(String pathZipFile, String pathExtract)` | Function 2 | `service/ZipService.extractTo(String pathZipFile, String pathExtract)` — **đúng 2 tham số của đề** (2 < 3 nên tờ giấy cho phép), kiểu trả `boolean`; bỏ `static` |
| Màn hình | tiêu đề, prompt, `File name …`, `Successfully` | `constants/Message` — chép đúng chữ |

---

## 2. Kiến thức cần biết

### 2.1 File zip gồm gì

```
[local header + dữ liệu file 1][local header + dữ liệu file 2] ... [CENTRAL DIRECTORY = mục lục]
                                                                     ▲ ZipOutputStream.close() ghi phần này
```

| Lớp `java.util.zip` | Dùng làm gì trong bài |
|---|---|
| `ZipOutputStream` | ghi zip: `putNextEntry(new ZipEntry(tên))` → ghi byte → `closeEntry()`; **`close()` ghi mục lục** |
| `ZipEntry` | một mục trong zip; tên kết thúc `/` = thư mục |
| `ZipFile` | mở zip **qua mục lục** → `entries()`, `getInputStream(entry)` |

**Đo thật** (mutation test lúc làm bài): bỏ try-with-resources quanh `ZipOutputStream` → file zip thiếu mục
lục. `ZipInputStream` (đọc tuần tự) **vẫn giải nén được** nên không phát hiện; `ZipFile` báo lỗi ngay → vì vậy
`ExtractTask` dùng **`ZipFile`**, và test #1 (nén → giải nén → nén lại) bắt được lỗi quên `close()`.

### 2.2 Tên mục trong zip — chạy tay với `data/`

`FileUtils.listAll` duyệt **sâu trước, sắp theo tên**; `getRelativeName` = đường dẫn tương đối, luôn dùng `/`:

| Duyệt | Tên mục | Ghi gì | In ra |
|---|---|---|---|
| `data/docs` | `docs/` | mục thư mục (không dữ liệu) | — |
| `data/docs/guide.txt` | `docs/guide.txt` | nội dung | `File name docs/guide.txt` |
| `data/hello.txt` | `hello.txt` | nội dung | `File name hello.txt` |
| `data/image.bin` | `image.bin` | nội dung | `File name image.bin` |

Nguồn là **một tệp** (`data/hello.txt`): danh sách chỉ có tệp đó, tên tính từ **thư mục chứa nó** → mục `hello.txt`,
in `File name hello.txt`.

### 2.3 Zip slip — lỗ hổng bảo mật có thật

Tên mục là **chữ do người tạo zip đặt**, có thể là `../../pwned.txt`. Ghép thẳng vào thư mục đích thì file
bị ghi **ra ngoài** thư mục người dùng chọn. `ExtractTask` đổi đường dẫn đích sang **canonical** (đã giải `..`)
và bắt nó phải **nằm trong** thư mục đích (`FileUtils.isInside`), không thì dừng.

### 2.4 Hai chỗ tự bảo vệ nữa

| Tình huống | Bài làm gì |
|---|---|
| Thư mục đích nằm **trong** thư mục nguồn | bỏ qua chính file zip đang ghi (`isSameFile`) — không thì nó tự nén chính mình |
| Tên zip gõ `backup` | tự thêm `.zip` (`getZipFileName`) |

---

## 3. Thiết kế

```
src/
├── model/       ArchiveJob          1 lần nén/giải nén: nguồn, đích, tên zip, fileNameList, lý do lỗi (JavaBean)
├── repository/  ArchiveRepository   ArrayList<ArchiveJob> jobList: addJob, getLastJob (dữ liệu của lần chạy)
├── dto/         ZipRequestDTO       nguồn, đích, tên   (main ──► controller)
│                ZipResponseDTO      success, fileNameList, error (controller ──► view)
├── service/     ArchiveTask         «abstract» Template Method: khung 4 bước
│                CompressTask        bước riêng của NÉN (ZipOutputStream) — thư mục hoặc 1 tệp
│                ExtractTask         bước riêng của GIẢI NÉN (ZipFile)
│                ZipService          compressTo · extractTo (tên của đề) + compress/extract cho controller
├── controller/  ZipController       Facade: service ──► view (1 lần render/luồng)
├── view/        ZipView             field responseDTO + setResponseDTO + display() không tham số
├── constants/   Message, Constants
├── utils/       Validation          getChoice, getNonBlank
│                FileUtils           listAll, getRelativeName, makeFolder, isSameFile, isInside, copyStream
└── main/        Main (final + private ctor) — menu + Scanner + validate
```

| Câu hỏi thiết kế | Trả lời |
|---|---|
| Sao có `repository`? | Tờ giấy 1.1: *"Bắt buộc phải có repository"*. Dữ liệu của chương trình là **các việc nén/giải nén** của lần chạy: `ArchiveRepository` giữ `jobList` và chỉ có CRUD đơn giản (`addJob`, `getLastJob`). Nén/giải nén là thuật toán → ở service: Controller → Service → Repository → Model. |
| Model là gì? | `ArchiveJob` — **một việc nén/giải nén**: đi từ đâu, tới đâu, tên gì, những file nào đã qua, lỗi gì. |
| Sao `copyStream` không đóng luồng? | Khi nén, luồng ra là **một** `ZipOutputStream` dùng chung cho mọi file — đóng nó sau file đầu là zip kết thúc luôn. |
| Sao lỗi nén không `throw` ra Main? | Đề bắt `compressTo`/`extractTo` **trả `boolean` trạng thái** (*"Return: zipping file status"*). `IOException` bị `ArchiveTask.execute` bắt, thành `false` + lý do; view in lý do rồi `Failed`. Lỗi **nhập** (menu, ô trống) thì `Validation` ném, Main in `e.getMessage()`. |

**Luồng Compression** (Main → RequestDTO → Controller → Service → Repository → Model; ResponseDTO → View **1 lần**):

```
Main.inputCompress: đọc 3 ô (hỏi lại khi trống) ──► ZipRequestDTO ──► controller.compress(dto)   (1 lần)
   controller ──► service.compress(dto)
                    ├─ compressTo(dto)
                    │     ├─ repository.addJob(nguồn, đích) → ArchiveJob (+ setZipName)
                    │     └─ compressTask.execute(job)    ← Template Method
                    │           checkSource → makeFolder(đích) → process (ghi zip) ; IOException → false + lý do
                    └─ toResponse(repository.getLastJob(), success) → ZipResponseDTO
   controller ──► view.setResponseDTO(dto) ──► view.display()
```

Extraction giống hệt, chỉ khác `extractTo(dto.getSourcePath(), dto.getDestinationPath())` — 2 tham số của đề.

### 3.1 Design Pattern — **Template Method**

| Yếu tố | Trong bài |
|---|---|
| **Name** | Template Method (nhóm Behavioral) |
| **Problem** | Nén và giải nén có **cùng khung**: kiểm nguồn → tạo thư mục đích nếu thiếu → xử lý → lỗi thì trả `false` + lý do. Viết 2 lần là lặp code, sửa khung phải sửa 2 chỗ. |
| **Solution** | `ArchiveTask` = **AbstractClass**: `execute(job)` là **template method** (`final` — lớp con không đổi được thứ tự), gọi 2 **primitive operation** `abstract`: `checkSource`, `process`. `CompressTask`, `ExtractTask` = **ConcreteClass**, chỉ viết 2 bước khác nhau. `ZipService` giữ 2 biến kiểu `ArchiveTask`. |
| **Consequences** | ✅ khung viết 1 lần; thêm thao tác mới (vd. "liệt kê file trong zip") = thêm 1 lớp con; ✅ đa hình: `compressTask.execute()` chạy `process` của đúng lớp con. ❌ lớp con bị ràng buộc vào khung của cha (muốn bỏ bước "tạo thư mục đích" phải sửa cha). |

**Pattern khác:** **Facade** = `ZipController` (Main chỉ thấy `compress`/`extract`); **Decorator** của Java IO:
`ZipOutputStream` bọc `FileOutputStream` — thêm việc nén mà vẫn là một `OutputStream`.

### 3.2 SOLID

| | Ở đâu |
|---|---|
| **S** | `ArchiveJob` giữ dữ liệu · `ArchiveRepository` lưu các việc · `CompressTask` nén · `ExtractTask` giải nén · `FileUtils` thao tác đĩa · `ZipView` in · `Main` nhập |
| **O** | thao tác mới = lớp con mới của `ArchiveTask`, không sửa `execute` |
| **L** | `CompressTask`/`ExtractTask` thay được cho `ArchiveTask` (`ZipService` chỉ gọi `execute`) |
| **I** | `ArchiveTask` chỉ bắt lớp con viết đúng 2 hàm cần thiết |
| **D** | `ZipService` khai báo field kiểu trừu tượng `ArchiveTask` |

---

## 4. Code từng bước

> Thầy: *"code cái **model trước rồi đến data**"*.

| Bước | File | Việc |
|---|---|---|
| 1 | `model/ArchiveJob.java` | 5 field `private` + ctor rỗng + get/set + `addFileName` + `toString` (`String.format`) |
| 2 | `repository/ArchiveRepository.java` | `jobList`; `addJob(sourcePath, destinationPath)`, `getLastJob()` |
| 3 | `dto/ZipRequestDTO`, `ZipResponseDTO` | JavaBean |
| 4 | `utils/FileUtils.java` | `makeFolder`, `listAll` (đệ quy), `getRelativeName`, `isSameFile`, `isInside`, `copyStream` |
| 5 | `service/ArchiveTask.java` | `execute` (final) + 2 hàm `abstract` |
| 6 | `service/CompressTask.java` | `checkSource` (thư mục **hoặc tệp**), `process` (**try-with-resources** `ZipOutputStream`), `getZipFileName` |
| 7 | `service/ExtractTask.java` | `checkSource`, `process` (`ZipFile`, chặn zip slip), `openZip` |
| 8 | `service/ZipService.java` | `compressTo`, `extractTo`, `compress`, `extract`, `toResponse` |
| 9 | `view/ZipView.java`, `controller/ZipController.java` | `setResponseDTO` + `display()`; controller render 1 lần/luồng |
| 10 | `constants`, `utils/Validation`, `main/Main` | `inputChoice`, `inputValue`, `inputCompress`, `inputExtract` |

**Bẫy hay gặp:**

1. Quên đóng `ZipOutputStream` → zip hỏng mà chương trình vẫn báo thành công (mục 2.1).
2. `copyStream` đóng luồng → file thứ 2 chết với `Stream closed`.
3. Tên mục dùng `\` (Windows) → giải nén trên máy khác ra file tên `docs\guide.txt`. `getRelativeName` luôn ra `/`.
4. Quên `/` cuối tên mục thư mục → thư mục rỗng biến mất khi giải nén.
5. Chỉ nhận **thư mục** ở ô *Source Folder* → trượt đúng bài test của thầy (`Test.docx` gõ đường dẫn **một tệp**).

---

## 5. Test trước khi gọi thầy

| # | Gõ | Phải thấy |
|---|---|---|
| 1 | `1` · `data` · `archive` · `backup` | `File name docs/guide.txt`, `hello.txt`, `image.bin`, `Successfully`; có `archive/backup.zip` |
| 2 | `2` · `archive/backup.zip` · `restored` | 3 dòng như trên, `Successfully` |
| 3 | `1` · `restored` · `archive` · `copy.ZIP` | 3 dòng như trên (không thêm `.zip` lần nữa) |
| 4 | `1` · `no-such-folder` · … | `Source folder does not exist: no-such-folder` · `Failed` |
| 5 | `1` · `data` · `data/hello.txt/inside` · `backup` | `Cannot create destination folder: data/hello.txt/inside` · `Failed` |
| 6 | `2` · `no-such.zip` · `out` | `Zip file does not exist: no-such.zip` · `Failed` |
| 7 | `2` · `data/hello.txt` · `void` | `Not a zip file: data/hello.txt` · `Failed` |
| 8 | `1` · `void` · `out` · `e` rồi `2` · `out/e.zip` · `out2` | nén thư mục rỗng: `Successfully` không dòng file; giải nén: `There is no file to extract in: out/e.zip` · `Failed` |
| 9 | `2` · `slip-test.zip` · `safe` | `Entry is outside the destination folder: ../../pwned.txt` · `Failed`, **không** có file `pwned.txt` ngoài `safe/` |
| 10 | `1` · `data` · `data` · `self` (2 lần) | lần 2 vẫn đúng 3 file — không nén `self.zip` vào chính nó |
| 11 | menu `abc`, Enter, `0`, `4` | `You must input a number.` ×2, `Please choose from 1 to 3.` ×2 |
| 12 | ô bất kỳ để trống / toàn dấu cách | `You must input a value.` rồi hỏi lại |
| 13 | (Test.docx) `1` · `data/hello.txt` · `Test` · `FileZip` rồi `2` · `Test/FileZip.zip` · `Test/` | `File name hello.txt` · `Successfully` hai lần; `Test/` còn `FileZip.zip` và `hello.txt` |

---

## 6. Debug

| Việc | Cách làm |
|---|---|
| Breakpoint | dòng `process(job);` trong `ArchiveTask.execute` |
| Chạy | **Ctrl+F5**, chọn `1` với `data` / `archive` / `backup` |
| Chứng minh đa hình | **F7** tại `process(job)` → nhảy vào `CompressTask.process` (chọn `2` thì vào `ExtractTask.process`) |
| Quan sát | **Variables**: `itemList` (danh sách đã duyệt), `name` (tên mục), `job.fileNameList`; `archiveRepository.jobList` trong `ZipService` |
| Zip slip | breakpoint ở `throw new IOException(String.format(Message.ENTRY_OUTSIDE ...` — test #9 dừng ở đó, **F8** rơi vào `catch` của `execute` |

---

## 7. Câu hỏi thầy hay hỏi

| Câu hỏi | Trả lời mẫu |
|---|---|
| 4 tính chất OOP ở đâu? | **Đóng gói**: field `private` + get/set trong `ArchiveJob`, DTO. **Kế thừa**: `CompressTask`, `ExtractTask` `extends ArchiveTask`. **Đa hình**: `ZipService` gọi `compressTask.execute(job)` → `execute` gọi `process` của đúng lớp con; `@Override toString()`. **Trừu tượng**: `abstract class ArchiveTask` với 2 hàm `abstract`. |
| Sao `checkSource`/`process` là `protected`? | Chỉ **lớp con** cần ghi đè, chỉ **lớp cha** (`execute`) gọi — `public` thì nơi khác gọi thẳng `process` bỏ qua kiểm nguồn; `private` thì lớp con không ghi đè được. |
| `compressTo`/`extractTo` chỉ được gọi trong `ZipService`, sao `public`? | **Đề ghi `public`** — đó là hợp đồng của đề; mọi hàm phụ khác (`toResponse`, `getZipFileName`, `openZip`) đều `private`. Field đều `private`; constructor `Message`, `Constants`, `Validation`, `FileUtils` `private`. |
| Sao `execute` là `final`? | Thứ tự các bước là **khung** — không cho lớp con đổi (đúng tinh thần Template Method). |
| Đề bắt `static compressTo(String, String, String)`, sao em khác? | Tờ giấy 1.1: service nhận *"param nếu số param < 3"* → 3 chuỗi đi trong `ZipRequestDTO` (Guide: *"có thể truyền nguyên vẹn DTO này vào services"*); `extractTo` chỉ 2 chuỗi nên **giữ đúng 2 tham số của đề**. `static` bỏ vì thầy dặn **static chỉ ở utils/constants/main**, và `ZipService` cần đối tượng (giữ repository). Giữ **tên** và kiểu `boolean`. |
| `compressTo` trả `boolean`, sao màn hình vẫn có danh sách file? | Danh sách được ghi vào `job` trong repository (`addFileName`); `boolean` là **trạng thái** đề yêu cầu. `toResponse(repository.getLastJob(), success)` gom cả hai cho view. |
| Sao bài có repository? | Tờ giấy 1.1 bắt buộc. `ArchiveRepository` giữ **dữ liệu của chương trình** — các `ArchiveJob` của lần chạy — với CRUD đơn giản; service lấy việc từ đó rồi chạy thuật toán nén/giải nén. |
| View nhận dữ liệu thế nào? | Qua **thuộc tính**: `private ZipResponseDTO responseDTO` + `setResponseDTO(...)`, rồi `display()` **không tham số** — đúng code mẫu của thầy; controller gọi `display()` **1 lần** mỗi luồng. |
| Validate ở đâu? | Ở **Main** qua `utils/Validation` (menu 1–3, ô không trống — hỏi lại ngay). Nguồn có tồn tại, có phải zip… là **kiểm trên đĩa** trong bước `checkSource`/`openZip` của task. |
| `FileUtils` static, bỏ thì sao? | Hàm không dùng dữ liệu đối tượng; bỏ `static` → `FileUtils.listAll(...)` lỗi biên dịch, phải bỏ `private` constructor và `new FileUtils()` trong task. |
| Sao `ArrayList<File>` mà không `List<File>`? | `List` là **interface** (hợp đồng), `ArrayList` là **lớp cài đặt** bằng mảng động — lấy theo chỉ số O(1), thêm cuối nhanh. Em chỉ cần duyệt theo thứ tự và thêm cuối, và thầy dặn khai báo kiểu cụ thể nên em dùng `ArrayList`. |
| Interface hay abstract class cho `ArchiveTask`? | **Abstract class** vì có **code chung** (`execute`) — interface (Java 8) không giữ được khung `final` như vậy. |
| Nén có ghi đè zip cũ không? | Có — `FileOutputStream` tạo lại file. |
| Độ phức tạp? | O(tổng số byte) cho cả nén và giải nén; duyệt thư mục O(số file). |

---

## 8. Thầy đổi yêu cầu tại chỗ

| Thầy bảo | Sửa | Không đụng |
|---|---|---|
| Thêm menu "Xem file trong zip" | lớp `ListTask extends ArchiveTask`, `ZipService.list`, `ZipController.list`, `Message`, `Constants`, `case` trong `Main` | `ArchiveTask`, `CompressTask`, `ExtractTask` |
| Chỉ nén file `.txt` | `CompressTask.process`: bỏ qua file không đuôi `.txt` | các lớp khác |
| Không nén thư mục con | `FileUtils.listAll` không đệ quy | service khác, view |
| In thêm kích thước file | `ArchiveJob` + `ZipResponseDTO` thêm danh sách cỡ, `ZipView` | `Main` |
| Mức nén cao nhất | `CompressTask.process`: `out.setLevel(Deflater.BEST_COMPRESSION)` | mọi file khác |

---

## 9. Chỗ khác với đề / bản cũ

| Chỗ | Đề / bản cũ | Bài này | Lý do |
|---|---|---|---|
| `compressTo` | `public static boolean compressTo(String, String, String)` | `public boolean compressTo(ArchiveJob job)` trong service | thầy V4 (≤ 2 tham số) + V3 (static chỉ utils/constants/main) — giữ tên và kiểu trả về |
| `extractTo` | `public static boolean extractTo(String, String)` | `public boolean extractTo(ArchiveJob job)` | cùng một kiểu với `compressTo`, static bỏ theo V3 |
| Menu | màn hình đề không đánh số | `1. Compression` … | đề bắt nhập 1/2/3; giống bản cũ |
| Màn hình lỗi | đề chỉ vẽ màn hình thành công | `<lý do>` + `Failed` | giống bản cũ; các câu lý do **không có trong đề** |
| Giải nén | bản cũ: `ZipInputStream` | `ZipFile` | bắt được zip chưa đóng (mục 2.1); thêm câu `Not a zip file: …`, `There is no file to extract in: …` (không có trong đề, bản cũ báo `Successfully` rỗng) |
| Kịch bản test | bản cũ: 3 lần chạy dùng chung thư mục, nén chính `src/` | `REPLACE_REFERENCE = True`, 6 kịch bản tự đủ trên `data/` + `slip-test.zip` ship sẵn (kịch bản thứ 6 = `Test.docx` của thầy) | `verify.py` cho mỗi lần chạy một thư mục mới, không có `src/` |
| Kiến trúc | `bo/ZipManager` static + field static `lastEntries` | MVC + Template Method | luật thầy; bỏ trạng thái static |
| Nguồn nén | chỉ nhận **thư mục** (tệp → `Source folder does not exist` + `Failed`) | thư mục **hoặc một tệp** | **đối chiếu đề**: `Test.docx` của thầy nén `D:/Test2/FileName1.txt`; thêm kịch bản này vào `_tools/tests/J1SP0079.py` |
| `extractTo` | `extractTo(ArchiveJob job)` | `extractTo(String pathZipFile, String pathExtract)` — đúng 2 tham số đề | tờ giấy 1.1 cho phép param khi < 3 |
| `compressTo` | `compressTo(ArchiveJob job)` | `compressTo(ZipRequestDTO requestDTO)` | 3 giá trị → DTO (tờ giấy 1.1, Guide) |
| Repository | không có (*"không có tập dữ liệu nào để CRUD"*) | `ArchiveRepository` giữ `jobList` | tờ giấy 1.1: *"Bắt buộc phải có repository"* |
| View | `setResponse(response)` | `setResponseDTO(responseDTO)` + `display()` | code mẫu của thầy |
| Tên | `fileNames`, `items`, `children`, `buffer`, `result`, `entries`, `relativeName`, `zipFileName` | `fileNameList`, `itemList`, `childArray`, `bufferArray`, `fileList`, `entryEnumeration`, `getRelativeName`, `getZipFileName` | tờ giấy 1.5 (đuôi `List`/`Array`), 1.4 (method bắt đầu bằng động từ) |
| `Main` | `public class Main` | `public final class Main` + `private Main()` | tờ giấy 3.4 |

**Nên hỏi thầy:** đề ghi `public static boolean compressTo(String pathSrc, String fileZipName, String pathCompress)`;
tờ checklist bắt service nhận **< 3 tham số** → em truyền `ZipRequestDTO`. Nếu thầy muốn giữ đúng 3 tham số của đề thì
chỉ cần đổi chữ ký `ZipService.compressTo` và một dòng gọi trong `ZipService.compress`.

---

## 10. Tờ checklist 25 mục — bài này đạt thế nào

| Mục | Chỗ trong code |
|---|---|
| **1.1** MVC + repository | `repository/ArchiveRepository` (bắt buộc có repository); luồng Controller → `ZipService` → repository → `ArchiveJob`; `ZipController` không import `model`; `ZipView` nhận `responseDTO` qua setter, `display()` không tham số, gọi **1 lần/luồng**; mỗi case trong `Main.main` gọi controller **1 lần**, không có lần gọi thêm |
| **1.3 / 1.4** | lớp là danh từ (`ArchiveTask`, `CompressTask`, `ArchiveRepository`); method bắt đầu bằng động từ (`getRelativeName`, `getZipFileName`, `addJob`, `getLastJob`) |
| **1.5** | `jobList`, `fileNameList`, `itemList`, `fileList`, `childArray`, `bufferArray` |
| **2.6 / 3.7** | biến local ở đầu block và khởi tạo luôn: `int choice = 0;`, `String line = "";`, `String name = "";` (`CompressTask.process`), `ZipEntry entry = null;`, `File target = null;` (`ExtractTask.process`) |
| **2.8** | 1 dòng trống trước mọi comment (cả comment của field trong `Message`/`Constants`/DTO/model), sau vùng khai báo biến, sau `}` của mỗi khối |
| **3.3** | `if ((choice < min) \|\| (choice > max))`, `if ((input == null) \|\| input.trim().isEmpty())` trong `Validation` |
| **3.4** | `public final class Main` + `private Main()`; `Validation`, `FileUtils`, `Constants`, `Message` cũng `final` + private constructor |
| **3.8** | không `+` nối chuỗi: `ArchiveJob.toString` dùng `Constants.JOB_FORMAT`, tên zip dùng `Constants.ZIP_NAME_FORMAT`, `isInside` dùng `Constants.FOLDER_FORMAT` |
