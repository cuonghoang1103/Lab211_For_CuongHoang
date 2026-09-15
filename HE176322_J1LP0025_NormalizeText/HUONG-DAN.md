# J1.L.P0025 — Normalize Text

> **Bài dài (Long Assignment)** — chấm theo **% hoàn thành**. Mỗi luật chuẩn hoá của đề là **một lớp
> nhỏ**; cả chuỗi luật là pattern **Strategy** xếp thành dây chuyền. Hiểu thứ tự các luật là hiểu bài.

| | |
|---|---|
| Loại / LOC | Long Assignment · 450 LOC · 5 slot |
| Project | `HE176322_J1LP0025_NormalizeText` |
| Chạy | NetBeans: **File ▸ Open Project** → **F6** (file `input.txt`/`output.txt` nằm ở **thư mục project**, cạnh `build.xml`) |
| Lớp chạy | `main.Main` |
| Kiểm tự động | `python3 _tools/verify.py --netbeans J1LP0025` → 6 kịch bản × 2 locale |

---

## 1. Đề bài nói gì

Đọc `input.txt` → chuẩn hoá → ghi `output.txt`. Bảy luật:

| # | Luật (chữ của đề) | Lớp làm luật đó |
|---|---|---|
| 1 | *There are no blank line between lines* | `RemoveBlankLineRule` |
| 2 | *Only one space between words* | `OneSpaceRule` |
| 3 | *… other words are in lower case* | `LowerCaseRule` |
| 4 | *There are no space between comma or dot and word in front of it* | `NoSpaceBeforePunctuationRule` |
| 5 | *Only one space after comma (,), dot (.) and colon (:)* | `SpaceAfterPunctuationRule` |
| 6 | *No spaces before and after sentence or word phrases in quotes (“”)* | `QuoteRule` |
| 7 | *First character of word in first line is in Uppercase* | `FirstLetterUpperRule` |
| 8 | *First character of word after dot is in Uppercase* | `SentenceCapitalRule` |
| 9 | *Must have dot at the end of text* | `EndDotRule` |

**Đề bắt buộc** (Guidelines):

| Thứ | Đề viết | Bài này đặt ở |
|---|---|---|
| Đọc file bằng `BufferedReader`, dùng `StringBuffer…` | slot 1 | `utils/FileUtils.readLines` (BufferedReader), `StringBuilder` trong mọi luật |
| Dùng **Exception** khi đọc/ghi (file not found, cannot read/write) | Function details | `FileUtils` ném `Exception(Message.X)`; `Main` bắt, in `e.getMessage()` |
| Tên file `input.txt`, `output.txt` | Function details | `Constants.INPUT_FILE`, `Constants.OUTPUT_FILE` |
| Ghi kết quả ra file | slot 4 | `DocumentRepository.saveDocument` → `FileUtils.writeLines` |

Đề **không có màn hình**; bài giữ nguyên menu của bản tham chiếu (6 mục) để thầy xem được từng luật:
`1` tạo file mẫu · `2` chuẩn hoá input → output · `3` đọc lại output từ đĩa · `4` chuẩn hoá một dòng gõ tay ·
`5` xem luật chạy trên 16 ca khó · `0` thoát.

---

## 2. Kiến thức cần biết

### 2.1 Chạy tay — một đoạn của file mẫu qua dây chuyền

Đầu vào (dòng 1–3 của `input.txt` mẫu, `␤` = xuống dòng):
`   as you can see , detecting … efficient.A lot of the cost␤of normalizing in the “ second row ” is … buffers .␤`

| Sau luật | Kết quả (đoạn đáng xem) |
|---|---|
| 1 `RemoveBlankLineRule` | dòng trống biến mất, các dòng nối bằng **1 dấu cách**: `… of the cost of normalizing …` |
| 2 `OneSpaceRule` | bỏ 3 dấu cách đầu, mọi chuỗi cách/tab → 1 cách |
| 3 `LowerCaseRule` | `the cost of which …` (chữ `The` cũng thành `the`) |
| 4 `NoSpaceBeforePunctuationRule` | `see ,` → `see,` · `buffers .` → `buffers.` |
| 5 `SpaceAfterPunctuationRule` | `see,detecting`/`efficient.a` → `see, detecting` / `efficient. a` |
| 6 `QuoteRule` | `“ second row ”` → `“second row”` |
| 7 `FirstLetterUpperRule` | `as` → `As` |
| 8 `SentenceCapitalRule` | `efficient. a lot` → `efficient. A lot` |
| 9 `EndDotRule` | `… even further` → `… even further.` |

### 2.2 Vì sao **thứ tự** quan trọng

| Nếu đảo | Hỏng thế nào |
|---|---|
| 3 (lower) chạy **sau** 7–8 | chữ hoa vừa dựng lại bị hạ xuống hết |
| 8 chạy **trước** 4–5 | `efficient.a` chưa có cách → vẫn đúng, nhưng `buffers . the` thì dấu chấm còn đứng một mình, dễ sai khi sửa |
| 6 chạy **trước** 4 | `“hello ,” ` → chấm câu chưa kéo vào, còn khoảng trắng trước dấu đóng ngoặc |

Thứ tự nằm ở **một chỗ duy nhất**: `NormalizeController.createRules()`.

### 2.3 Mấy ca khó (menu 5 in ra hết)

| Vào | Ra | Luật lo |
|---|---|---|
| `a \t  b\t\tc` | `A b c.` | tab là khoảng trắng (`TextUtils.isSpace`) |
| `word␣(nbsp)word` | `Word word …` | `Character.isWhitespace` trả **false** với NBSP → liệt kê tay |
| `the price is 3.14 dollars` | `The price is 3.14 dollars.` | dấu chấm **thập phân** không thêm cách, không viết hoa |
| `this ends with a comma ,` | `This ends with a comma.` | dấu phẩy cuối được **thay** bằng chấm |
| `he said "  hello there  " loudly` | `He said "hello there" loudly.` | ngoặc thẳng: đếm chẵn/lẻ để biết mở hay đóng |
| (chuỗi rỗng / toàn cách) | (rỗng) | không tự sinh ra `.` |

### 2.4 Java dùng trong bài

| API | Dùng làm gì |
|---|---|
| `BufferedReader(InputStreamReader(FileInputStream, UTF_8))` | đọc từng dòng, **UTF-8** để dấu `“ ”` và tiếng Việt không vỡ trên Windows |
| `BufferedWriter` + `newLine()` | ghi file |
| try-with-resources `try (...) { }` | đóng file kể cả khi lỗi |
| `File.exists/isDirectory/canRead/canWrite` | phân loại lỗi để báo câu dễ hiểu |
| `StringBuilder` (bản không đồng bộ của `StringBuffer`) | dựng chuỗi mới từng ký tự |
| `Character.isLetter/isDigit/toUpperCase/toLowerCase` | quyết định từng ký tự, không phụ thuộc locale |

---

## 3. Thiết kế

```
HE176322_J1LP0025_NormalizeText/src/
├── constants/  Message.java             câu chữ + 16 ca mẫu (menu 5)
│               Constants.java           tên file, ký tự đặc biệt, số menu, file mẫu
├── model/      TextDocument             dòng gốc + văn bản đã chuẩn hoá; getFullText()
├── dto/        NormalizeRequestDTO      dòng gõ tay (main ──► controller)
│               DocumentResponseDTO      dòng gốc + kết quả  ──► view
│               CaseResponseDTO          trước/sau của 1 ca  ──► view
├── repository/ IDocumentRepository      «interface» load/save (DIP)
│               DocumentRepository       input.txt ⇄ TextDocument ⇄ output.txt (qua FileUtils)
├── service/    NormalizeRule            «interface» Strategy: String apply(String)
│               9 lớp *Rule              mỗi luật của đề một lớp
│               NormalizeService         Context: chạy text qua danh sách luật; điều phối đọc-sửa-ghi
├── controller/ NormalizeController      Facade: CHỌN luật + thứ tự (createRules), service ──► view
├── view/       NormalizeView            in các khung BEFORE/AFTER/ON DISK/ca mẫu; hiện \t, <nbsp>
├── utils/      FileUtils                đọc/ghi file, ném Exception (static)
│               TextUtils                isSpace, isBlank, isPunctuation, dropTrailingSpaces, skipSpaces
│               Validation               getChoice, getRawText (static)
└── main/       Main                     menu + Scanner + bắt mọi lỗi file
```

**Luồng menu 2** (Controller ↔ Service ↔ Repository ↔ Model):

```
Main ──► controller.normalizeFile()
            └─► service.normalizeFile()
                   ├─ repository.loadDocument()  → FileUtils.readLines("input.txt") → TextDocument
                   ├─ normalize(document.getFullText())   ← 9 luật chạy lần lượt
                   └─ repository.saveDocument(document) → FileUtils.writeLines("output.txt")
            └─► view.setDocument(dto); view.displayNormalized()
Main: catch (Exception e) → in "Error: File not found: input.txt" …
```

### 3.1 Design Pattern — **Strategy** xếp thành dây chuyền

| Yếu tố | Trong bài |
|---|---|
| **Name** | Strategy (Behavioral), dùng thành **danh sách chạy nối tiếp** (kiểu *Pipes and Filters*) |
| **Problem** | Đề có 7 luật, thầy hay bảo *"thêm luật: không quá 2 câu hỏi liên tiếp"* hoặc *"bỏ luật chữ thường đi"*. Viết cả 7 luật trong một hàm dài thì sửa một luật dễ làm vỡ luật khác, và không test riêng được. |
| **Solution** | `NormalizeRule` = **Strategy** (`String apply(String)`). 9 lớp `*Rule` = **ConcreteStrategy**. `NormalizeService` = **Context**: giữ `ArrayList<NormalizeRule> rules` nhận qua **constructor**, `for (rule : rules) result = rule.apply(result);`. `NormalizeController.createRules()` là nơi **chọn** luật và thứ tự. |
| **Consequences** | ✅ Thêm luật = **thêm 1 lớp + 1 dòng** `rules.add(...)`; bỏ luật = xoá 1 dòng; mỗi luật đọc được riêng (**S**, **O**, **D**). ❌ Nhiều lớp nhỏ; **thứ tự** giữa các luật trở thành một ràng buộc phải hiểu (mục 2.2). |

> Giống **Chain of Responsibility** ở chỗ nối thành chuỗi, nhưng khác: trong CoR chỉ **một** mắt xích xử
> lý rồi dừng; ở đây **mọi** luật đều chạy — nên gọi đúng là Strategy + dây chuyền.

**Thầy bảo "thêm luật: bỏ dấu cách trước dấu `!` và `?`"**: tạo `service/NoSpaceBeforeMarkRule implements NormalizeRule`,
thêm `rules.add(new NoSpaceBeforeMarkRule());` sau `NoSpaceBeforePunctuationRule` trong `createRules()`. Không sửa lớp nào khác.

**Pattern khác**: **MVC** (thầy bắt — controller ~ Servlet, view ~ JSP, model ~ JavaBean) · **Facade** = `NormalizeController`
(Main chỉ gọi `normalizeFile()`, không biết có repository, 9 luật, FileUtils) · **Repository** (mẫu kiến trúc) =
`IDocumentRepository`/`DocumentRepository` · **Decorator** của Java IO: `BufferedReader` bọc `InputStreamReader` bọc
`FileInputStream`, mỗi lớp thêm một khả năng (bộ đệm, giải mã UTF-8).

### 3.2 SOLID

| Nguyên lý | Ở đâu |
|---|---|
| **S** | mỗi `*Rule` một luật; `FileUtils` chỉ file; `DocumentRepository` chỉ load/save; `NormalizeView` chỉ in |
| **O** | thêm luật không sửa `NormalizeService` |
| **L** | mọi `*Rule` thay được chỗ `NormalizeRule`: nhận chuỗi, trả chuỗi, không ném lỗi |
| **I** | `NormalizeRule` đúng **1** hàm |
| **D** | `NormalizeService` phụ thuộc `NormalizeRule` + `IDocumentRepository` (trừu tượng), nhận qua constructor |

---

## 4. Code từng bước

> Thầy: *"code cái **model trước rồi đến data**"*.

| Bước | File | Việc |
|---|---|---|
| 1 | `model/TextDocument.java` | `lines`, `normalizedText` + ctor rỗng/đủ + get/set + `getLineCount` + `getFullText` |
| 2 | `dto/*` | 3 DTO JavaBean |
| 3 | `utils/FileUtils.java` | `readLines`, `writeLines` + 4 thông báo lỗi |
| 4 | `repository/IDocumentRepository` → `DocumentRepository` | `createSample/loadDocument/saveDocument/readOutput` |
| 5 | `service/NormalizeRule` | interface 1 hàm |
| 6 | `utils/TextUtils` | 5 hàm ký tự dùng chung |
| 7 | `service/*Rule` ×9 | **mỗi luật của đề** — gõ theo bảng mục 1 |
| 8 | `service/NormalizeService` | `normalize` (private) + 5 hàm cho 5 mục menu |
| 9 | `view/NormalizeView` | 3 setter + 5 `display…` + `visible`, `countLines` (private) |
| 10 | `controller/NormalizeController` | `createRules()` + 5 hàm |
| 11 | `constants/Message`, `Constants` | gõ dần |
| 12 | `utils/Validation`, `main/Main` | menu + `normalizeTypedLine` |

**Bẫy hay gặp**

1. `text.split(" ")` để gộp dấu cách → ra phần tử rỗng, nối lại vẫn thừa cách. Dùng cờ `pendingSpace` (`OneSpaceRule`).
2. `trim()` dòng gõ tay trước khi chuẩn hoá → không còn gì để chứng minh luật 2. `Validation.getRawText` **không** trim.
3. `new FileReader(f)` dùng bảng mã mặc định của máy → `“ ”` thành rác trên Windows. Luôn ghi rõ `UTF_8`.
4. NetBeans chạy với thư mục làm việc = **thư mục project** → `input.txt` phải đặt cạnh `build.xml`, không phải trong `src/`.

---

## 5. Test trước khi gọi thầy

| # | Gõ | Phải thấy |
|---|---|---|
| 1 | `2` (thư mục mới, chưa có input.txt) | `Error: File not found: input.txt` |
| 2 | `3` (chưa có output.txt) | `Error: File not found: output.txt` |
| 3 | `1` | `Sample input written to input.txt (7 lines).` |
| 4 | `2` | khung **BEFORE** 7 dòng (thấy `\t`, dòng trống, `[   ]`), khung **AFTER** 1 dòng, `Normalized document written to output.txt.` |
| 5 | `3` | khung **ON DISK - output.txt (1 line)** đúng văn bản vừa ghi |
| 6 | `4` rồi `   these   are\tmixed ,and  spaces  ` | `OUT: [These are mixed, and spaces.]` |
| 7 | `4` rồi dòng trống / toàn dấu cách | `OUT: []` |
| 8 | `4` rồi đoạn 1 ví dụ của đề | chỉ `as` → `As`, còn lại giữ nguyên |
| 9 | `4` rồi `note :this is   IT` | `OUT: [Note: this is it.]` (luật dấu hai chấm) |
| 10 | `5` | 16 ca, mỗi ca IN/OUT |
| 11 | `9` rồi `x` | `Please choose from 0 to 5.` · `You must input a number.` |
| 12 | tạo **thư mục** tên `input.txt` rồi `2` | `Error: Not a file: input.txt` (thử tay) |
| 13 | đặt `output.txt` chỉ-đọc rồi `2` | `Error: Cannot write the file: output.txt` (thử tay) |
| 14 | `0` | `Goodbye.` |

---

## 6. Debug

| Việc | Cách làm |
|---|---|
| Breakpoint | dòng `result = rule.apply(result);` trong `NormalizeService.normalize` |
| Chạy | **Ctrl+F5**, chọn `1` rồi `2` |
| Quan sát | tab **Variables**: `rule` (xem nó là lớp nào) và `result` — mỗi lần **F8** thấy văn bản sau thêm một luật |
| Bước vào | **F7** ở dòng đó → nhảy vào đúng lớp `*Rule` đang chạy (đa hình qua interface) |
| Lỗi file | breakpoint `throw new Exception(String.format(Message.FILE_NOT_FOUND, path));` trong `FileUtils.readLines`, chọn `2` khi chưa có file, **F8** tới `catch` trong `Main` |

---

## 7. Câu hỏi thầy hay hỏi

### OOP

| Câu hỏi | Trả lời mẫu |
|---|---|
| 4 tính chất OOP ở đâu? | **Đóng gói**: field `private` trong `TextDocument`, DTO. **Kế thừa**: 9 lớp `implements NormalizeRule`, `DocumentRepository implements IDocumentRepository`; ghi đè `toString()`. **Đa hình**: `rule.apply(result)` — một dòng code, chạy 9 hàm khác nhau tuỳ đối tượng thật. **Trừu tượng**: `NormalizeRule` chỉ nói "biến chuỗi thành chuỗi", không nói cách. |
| Interface hay abstract class cho luật? | Các luật **không chung dòng code nào** (phần chung đã tách vào `TextUtils`) → interface. |

### Access modifier, static, kiểu trả về

| Câu hỏi | Trả lời mẫu |
|---|---|
| Hàm nào `public`? | `apply` (service gọi), hàm service (controller gọi), hàm repository (service gọi), hàm view (controller gọi), hàm `FileUtils/TextUtils/Validation` (nhiều lớp gọi), getter/setter. `normalize`, `createRules`, `isSpaceNeeded`, `isDecimalPoint`, `visible`, `countLines` là `private` — chỉ lớp của nó dùng. |
| `static` ở đâu, vì sao? | `FileUtils`, `TextUtils`, `Validation` — Guide: utils *"phải dùng static method"*; kết quả chỉ phụ thuộc tham số. Hằng trong `Message/Constants`. Hàm trong `Main`. Không nơi nào khác. |
| **Bỏ `static` ở `FileUtils`?** | `FileUtils.readLines(...)` báo lỗi biên dịch; phải bỏ `private` constructor, `new FileUtils()` trong `DocumentRepository` rồi gọi qua đối tượng. |
| **Sao `ArrayList` mà không `List`?** | `List` là **interface**, `ArrayList` là **lớp cài đặt** bằng mảng động: lấy theo chỉ số O(1) (view in dòng `i + 1`), thêm cuối nhanh; `LinkedList` cài cùng hợp đồng bằng nút liên kết, lấy theo chỉ số O(n). Thứ tự luật và dòng file là **có chỉ số**, nên em khai báo đúng `ArrayList`. |
| `apply` trả `String`, không `void`? | `String` của Java **bất biến** — không sửa tại chỗ được, luật nào cũng phải trả chuỗi mới cho luật sau. |
| `readLines` trả `ArrayList<String>`? | Giữ **từng dòng** như trên đĩa để khung BEFORE đánh số được; nối thành 1 chuỗi là việc của `TextDocument.getFullText()`. |
| Sao `FileUtils` ném `Exception` mà không tự in lỗi? | Đề bắt *"use Exception to handle"*; utils không được in (chỉ view/main). `Main` là nơi bắt duy nhất. |

### Luật & chỗ khó

| Câu hỏi | Trả lời mẫu |
|---|---|
| Đề nói "không có cách trước và sau câu trong ngoặc" — sao `“second row” is` vẫn có cách sau ngoặc? | Nghĩa là cách **ngay bên trong** dấu ngoặc. Ví dụ của chính đề giữ `the “second row” is` — ví dụ là trọng tài. |
| Sao `3.14` không thành `3. 14`? | `SpaceAfterPunctuationRule.isSpaceNeeded`: dấu chấm kẹp giữa 2 chữ số là **dấu thập phân**. |
| Sao thêm `?` và `!` vào dấu kết câu? | Để không sinh ra `?.` ở cuối và viết hoa đúng sau câu hỏi — ghi ở mục 9. |
| Độ phức tạp? | Mỗi luật duyệt chuỗi 1 lần: O(n) · 9 luật → O(9n) = **O(n)**. |

---

## 8. Thầy đổi yêu cầu tại chỗ

| Thầy bảo | Sửa file | Không đụng |
|---|---|---|
| Thêm 1 luật mới | 1 lớp `*Rule` mới + 1 dòng `createRules()` | service, view, main, các luật cũ |
| Bỏ luật chữ thường | xoá `rules.add(new LowerCaseRule());` | mọi file khác |
| Giữ nguyên xuống dòng (không gộp 1 dòng) | `RemoveBlankLineRule` nối bằng `LINE_BREAK`, `OneSpaceRule` không coi `\n` là cách, `saveDocument` tách dòng | service, controller |
| Đổi tên file | `Constants.INPUT_FILE/OUTPUT_FILE` + `Message.MENU` | mọi file khác |
| Chỉ chạy đúng đề: đọc-sửa-ghi, không menu | `Main.main` gọi `controller.normalizeFile()` một lần | tất cả phần còn lại |

---

## 9. Chỗ khác với đề / bản cũ

| Chỗ | Đề / bản cũ | Bài này | Lý do |
|---|---|---|---|
| Màn hình | đề: không có | giữ **y nguyên** menu + thông báo của bản tham chiếu | quy tắc bộ lời giải (đề im lặng) |
| Kịch bản test | bản tham chiếu: 5 kịch bản | 4 kịch bản giữ nguyên + 2 mới; `REPLACE_REFERENCE = True` | kịch bản 2 của bản cũ chỉ đúng khi kịch bản trước **để lại** `output.txt`, còn kịch bản 0 lại cần **không** có file — `verify.py` chạy mỗi kịch bản trên thư mục sạch nên không thể qua cả hai; thay bằng `1,2,3,0` |
| Ví dụ đầu ra của đề | mở đầu bằng `“As` và `buffers, the cost of which` | `As …` và `buffers. The cost of which` | dấu `“` thừa và việc đổi `.` thành `,` không thuộc luật nào của đề — lỗi gõ của đề |
| Dấu kết câu | đề chỉ nói dấu chấm | `.`, `?`, `!` | không sinh `?.`; viết hoa sau câu hỏi |
| Dấu thập phân, NBSP, ngoặc thẳng `"` | đề không nói | xử lý riêng | ca thật của văn bản; menu 5 minh hoạ |
| Dấu `,` hoặc `:` cuối văn bản | — | **thay** bằng `.` | tránh `,.` |
| `input.txt` | đề: chương trình đọc file | **không** gửi kèm; menu 1 tạo file mẫu | để thấy ngay lỗi *file not found* mà đề bắt xử lý |
| Kiến trúc | bản cũ: `entity/bo/ui`, 1 lớp `TextNormalizer` 6 hàm, controller in ra, Scanner static | 9 lớp luật (Strategy), `FileUtils` ở utils, load/save ở repository, in ở view, Scanner chỉ ở main | luật thầy + Design Pattern |
| Tiêu đề BEFORE | bản cũ luôn in `lines` | `1 line` / `N lines` | đúng ngữ pháp (bản cũ chỉ in 7 dòng, không chạm) |
