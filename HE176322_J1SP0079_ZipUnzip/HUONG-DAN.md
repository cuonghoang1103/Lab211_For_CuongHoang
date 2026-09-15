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
| Kiểm tự động | `python3 _tools/verify.py J1SP0079` → 5 kịch bản × 2 locale |

---

## 1. Đề bài nói gì

- Menu: **Compression · Extraction · Exit**.
- **Nén**: nhập thư mục nguồn, thư mục đích, tên file zip → nén mọi file trong thư mục → in từng file + `Successfully`.
- **Giải nén**: nhập file zip, thư mục đích → giải nén → in từng file + `Successfully`.
- Dùng package **`java.util.zip`**.

**Đề bắt buộc** (Guidelines):

| Thứ | Đề viết | Bài này |
|---|---|---|
| `public static boolean compressTo(String pathSrc, String fileZipName, String pathCompress)` | Function 1 | `service/ZipService.compressTo(ArchiveJob job)` — giữ **tên** và kiểu trả `boolean`; 3 tham số gói vào model `ArchiveJob`, bỏ `static` (xem §9) |
| `public static boolean extractTo(String pathZipFile, String pathExtract)` | Function 2 | `service/ZipService.extractTo(ArchiveJob job)` — như trên |
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

`FileUtils.listAll` duyệt **sâu trước, sắp theo tên**; `relativeName` = đường dẫn tương đối, luôn dùng `/`:

| Duyệt | Tên mục | Ghi gì | In ra |
|---|---|---|---|
| `data/docs` | `docs/` | mục thư mục (không dữ liệu) | — |
| `data/docs/guide.txt` | `docs/guide.txt` | nội dung | `File name docs/guide.txt` |
| `data/hello.txt` | `hello.txt` | nội dung | `File name hello.txt` |
| `data/image.bin` | `image.bin` | nội dung | `File name image.bin` |

### 2.3 Zip slip — lỗ hổng bảo mật có thật

Tên mục là **chữ do người tạo zip đặt**, có thể là `../../pwned.txt`. Ghép thẳng vào thư mục đích thì file
bị ghi **ra ngoài** thư mục người dùng chọn. `ExtractTask` đổi đường dẫn đích sang **canonical** (đã giải `..`)
và bắt nó phải **nằm trong** thư mục đích (`FileUtils.isInside`), không thì dừng.

### 2.4 Hai chỗ tự bảo vệ nữa

| Tình huống | Bài làm gì |
|---|---|
| Thư mục đích nằm **trong** thư mục nguồn | bỏ qua chính file zip đang ghi (`isSameFile`) — không thì nó tự nén chính mình |
| Tên zip gõ `backup` | tự thêm `.zip` (`zipFileName`) |

---

## 3. Thiết kế

```
src/
├── model/       ArchiveJob          1 lần nén/giải nén: nguồn, đích, tên zip, danh sách file, lý do lỗi (JavaBean)
├── dto/         ZipRequestDTO       nguồn, đích, tên   (main ──► controller)
│                ZipResponseDTO      success, fileNames, error (controller ──► view)
├── service/     ArchiveTask         «abstract» Template Method: khung 4 bước
│                CompressTask        bước riêng của NÉN (ZipOutputStream)
│                ExtractTask         bước riêng của GIẢI NÉN (ZipFile)
│                ZipService          compressTo · extractTo (tên của đề) + đổi DTO ↔ model
├── controller/  ZipController       Facade: service ──► view
├── view/        ZipView             in màn hình Result
├── constants/   Message, Constants
├── utils/       Validation          getChoice, getNonBlank
│                FileUtils           listAll, relativeName, makeFolder, isSameFile, isInside, copyStream
└── main/        Main                menu + Scanner
```

| Câu hỏi thiết kế | Trả lời |
|---|---|
| Sao không có `repository`? | Không có tập dữ liệu nào được giữ trong bộ nhớ để CRUD — dữ liệu là file trên đĩa. |
| Model là gì? | `ArchiveJob` — **một việc nén/giải nén**: đi từ đâu, tới đâu, tên gì, những file nào đã qua. Nó thay cho 3 tham số chuỗi của đề. |
| Sao `copyStream` không đóng luồng? | Khi nén, luồng ra là **một** `ZipOutputStream` dùng chung cho mọi file — đóng nó sau file đầu là zip kết thúc luôn. |

**Luồng Compression:**

```
Main: đọc 3 ô (hỏi lại khi trống) ──► ZipRequestDTO ──► controller.compress(dto)   (1 lần)
   controller ──► service.compress(dto)
                    ├─ toJob(dto)                  → ArchiveJob
                    ├─ compressTo(job)             → compressTask.execute(job)   ← Template Method
                    │     checkSource → makeFolder(đích) → process (ghi zip) ; IOException → false + lý do
                    └─ toResponse(job, success)    → ZipResponseDTO
   controller ──► view.setResponse(...) ──► view.display()
```

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
| **S** | `ArchiveJob` giữ dữ liệu · `CompressTask` nén · `ExtractTask` giải nén · `FileUtils` thao tác đĩa · `ZipView` in |
| **O** | thao tác mới = lớp con mới của `ArchiveTask`, không sửa `execute` |
| **L** | `CompressTask`/`ExtractTask` thay được cho `ArchiveTask` (`ZipService` chỉ gọi `execute`) |
| **I** | `ArchiveTask` chỉ bắt lớp con viết đúng 2 hàm cần thiết |
| **D** | `ZipService` khai báo field kiểu trừu tượng `ArchiveTask` |

---

## 4. Code từng bước

> Thầy: *"code cái **model trước rồi đến data**"*.

| Bước | File | Việc |
|---|---|---|
| 1 | `model/ArchiveJob.java` | 5 field `private` + ctor rỗng + get/set + `addFileName` + `toString` |
| 2 | `dto/ZipRequestDTO`, `ZipResponseDTO` | JavaBean |
| 3 | `utils/FileUtils.java` | `makeFolder`, `listAll` (đệ quy), `relativeName`, `isSameFile`, `isInside`, `copyStream` |
| 4 | `service/ArchiveTask.java` | `execute` (final) + 2 hàm `abstract` |
| 5 | `service/CompressTask.java` | `checkSource`, `process` (**try-with-resources** `ZipOutputStream`), `zipFileName` |
| 6 | `service/ExtractTask.java` | `checkSource`, `process` (`ZipFile`, chặn zip slip), `openZip` |
| 7 | `service/ZipService.java` | `compressTo`, `extractTo`, `compress`, `extract`, `toJob`, `toResponse` |
| 8 | `view/ZipView.java`, `controller/ZipController.java` | |
| 9 | `constants`, `utils/Validation`, `main/Main` | |

**Bẫy hay gặp:**

1. Quên đóng `ZipOutputStream` → zip hỏng mà chương trình vẫn báo thành công (mục 2.1).
2. `copyStream` đóng luồng → file thứ 2 chết với `Stream closed`.
3. Tên mục dùng `\` (Windows) → giải nén trên máy khác ra file tên `docs\guide.txt`. `relativeName` luôn ra `/`.
4. Quên `/` cuối tên mục thư mục → thư mục rỗng biến mất khi giải nén.

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

---

## 6. Debug

| Việc | Cách làm |
|---|---|
| Breakpoint | dòng `process(job);` trong `ArchiveTask.execute` |
| Chạy | **Ctrl+F5**, chọn `1` với `data` / `archive` / `backup` |
| Chứng minh đa hình | **F7** tại `process(job)` → nhảy vào `CompressTask.process` (chọn `2` thì vào `ExtractTask.process`) |
| Quan sát | **Variables**: `items` (danh sách đã duyệt), `name` (tên mục), `job.fileNames` |
| Zip slip | breakpoint ở `throw new IOException(String.format(Message.ENTRY_OUTSIDE ...` — test #9 dừng ở đó, **F8** rơi vào `catch` của `execute` |

---

## 7. Câu hỏi thầy hay hỏi

| Câu hỏi | Trả lời mẫu |
|---|---|
| 4 tính chất OOP ở đâu? | **Đóng gói**: field `private` + get/set trong `ArchiveJob`, DTO. **Kế thừa**: `CompressTask`, `ExtractTask` `extends ArchiveTask`. **Đa hình**: `ZipService` gọi `compressTask.execute(job)` → `execute` gọi `process` của đúng lớp con; `@Override toString()`. **Trừu tượng**: `abstract class ArchiveTask` với 2 hàm `abstract`. |
| Sao `checkSource`/`process` là `protected`? | Chỉ **lớp con** cần ghi đè, chỉ **lớp cha** (`execute`) gọi — `public` thì nơi khác gọi thẳng `process` bỏ qua kiểm nguồn; `private` thì lớp con không ghi đè được. |
| `compressTo`/`extractTo` chỉ được gọi trong `ZipService`, sao `public`? | **Đề ghi `public`** — đó là hợp đồng của đề; mọi hàm phụ khác (`toJob`, `toResponse`, `zipFileName`, `openZip`) đều `private`. Field đều `private`; constructor `Message`, `Constants`, `Validation`, `FileUtils` `private`. |
| Sao `execute` là `final`? | Thứ tự các bước là **khung** — không cho lớp con đổi (đúng tinh thần Template Method). |
| Đề bắt `static compressTo(String, String, String)`, sao em khác? | Thầy dặn: **không truyền 3 tham số** và **static chỉ ở utils/constants/main** → giữ **tên** và kiểu `boolean`, gói 3 chuỗi vào `ArchiveJob`, hàm là hàm đối tượng trong service. Bỏ `static` được vì `ZipService` có đối tượng do controller tạo. |
| `compressTo` trả `boolean`, sao màn hình vẫn có danh sách file? | Danh sách được ghi vào `job` (`addFileName`); `boolean` là **trạng thái** đề yêu cầu. `toResponse(job, success)` gom cả hai cho view. |
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
| Kịch bản test | bản cũ: 3 lần chạy dùng chung thư mục, nén chính `src/` | `REPLACE_REFERENCE = True`, 5 kịch bản tự đủ trên `data/` + `slip-test.zip` ship sẵn | `verify.py` cho mỗi lần chạy một thư mục mới, không có `src/` |
| Kiến trúc | `bo/ZipManager` static + field static `lastEntries` | MVC + Template Method | luật thầy; bỏ trạng thái static |
