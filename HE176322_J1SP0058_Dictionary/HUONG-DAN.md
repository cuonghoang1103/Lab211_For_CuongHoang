# J1.S.P0058 — Simple English - Vietnamese Dictionary

> Bài **đọc/ghi file** đầu tiên: từ điển nằm trong một `HashMap`, và **mỗi lần thêm/xoá là ghi đè cả
> file** — tắt máy mở lại vẫn còn từ. Khung giống hệt P0055, chỉ thêm `utils/FileUtils`.

| | |
|---|---|
| Loại / LOC | Short Assignment · 48 LOC · 1 slot |
| Project | `HE176322_J1SP0058_Dictionary` |
| Chạy | NetBeans: **File ▸ Open Project** → chọn thư mục → **F6** |
| Lớp chạy | `main.Main` |
| File dữ liệu | `dictionary.txt` ở **gốc project** (cạnh `build.xml`) — có sẵn 2 từ `hello`, `dog` |
| Kiểm tự động | `python3 _tools/verify.py --netbeans J1SP0058` → 3 kịch bản × 2 locale |

---

## 1. Đề bài nói gì

- Menu 4 mục: **Add Word · Delete Word · Translate · Exit**.
- **Add**: nhập cặp Anh–Việt → lưu vào từ điển **và file**. Từ đã có → **hỏi có muốn sửa nghĩa không**.
- **Delete**: nhập từ tiếng Anh → xoá cặp đó khỏi từ điển và file; không có → báo không tồn tại.
- **Translate**: nhập từ tiếng Anh → in nghĩa tiếng Việt; không có → "display empty".
- Mỗi lần **bật chương trình**: có file thì **nạp vào HashMap** (`loadData`), chưa có thì HashMap rỗng.

Màn hình đề (chép đúng chữ):

```
------------- Add -------------          ------------- Translate ------------
Enter English: Cat                      Enter English: Cat
Enter Vietnamese: Con Meo               Vietnamese: Con Meo
Successful
                                        ------------ Delete ----------------
                                        Enter English: Cat
                                        Successful
```

**Đề bắt buộc** (mục Guidelines + Suggestion):

| Thứ | Đề viết | Bài này đặt ở |
|---|---|---|
| `public boolean addWord(String eng, String vi)` | *"Must install the function"* | `DictionaryRepository.addWord` — **đúng chữ ký** |
| `public boolean removeWord(String eng)` | *"Must install the function"* | `DictionaryRepository.removeWord` — **đúng chữ ký** |
| `public String translate(String eng)` | *"Must install the function"* | `DictionaryRepository.translate` — **đúng chữ ký** |
| `loadData()` · `updateDatabase()` | Suggestion | `DictionaryRepository` (`updateDatabase` là `private`) |
| Lưu bằng HashMap | *"use the hash map to store a pair Eng - Vi"* | `LinkedHashMap<String, Word>` (là một `HashMap`) |
| Chữ trên màn hình | `Successful` · `Vietnamese: ` | `constants/Message` — **chép đúng từng chữ** |

---

## 2. Kiến thức cần biết

### 2.1 Từ điển = `HashMap` khoá là từ tiếng Anh

| Thao tác | Code | Số bước |
|---|---|---|
| Có từ này chưa? | `wordMap.containsKey(key)` | 1 |
| Thêm / sửa nghĩa | `wordMap.put(key, word)` — khoá đã có thì **đè** | 1 |
| Xoá | `wordMap.remove(key)` — trả `null` nếu không có | 1 |
| Dịch | `wordMap.get(key)` — trả `null` nếu không có | 1 |

Khoá là từ tiếng Anh **viết thường** (`toKey`): gõ `Cat`, `cat`, `CAT` đều ra cùng một từ.

### 2.2 Định dạng file — mỗi dòng một cặp

```
hello=xin chao
dog=con cho
```

Nạp lại bằng `line.split("=", 2)` — số **2** nghĩa là "cắt ở dấu `=` **đầu tiên** thôi":

| Dòng trong file | `split("=", 2)` | Kết quả |
|---|---|---|
| `dog=con cho` | `["dog", "con cho"]` | nạp |
| `Cat=Con Meo = meo` | `["Cat", "Con Meo = meo"]` | nạp — nghĩa được chứa `=` |
| *(dòng trống)* | `[""]` — 1 phần | bỏ qua |
| `abc` | `["abc"]` — 1 phần | bỏ qua |

Vì `=` là dấu ngăn cách, **từ tiếng Anh không được chứa `=`** → `Validation.getEnglish` chặn.

### 2.3 Đọc/ghi file trong Java

| API | Dùng làm gì |
|---|---|
| `new File(path).exists()` | kiểm file có chưa (đề: *"test has data files or not yet"*) |
| `BufferedReader` + `InputStreamReader(..., UTF_8)` | đọc từng dòng, **UTF-8** để giữ dấu tiếng Việt |
| `reader.readLine()` | trả `null` khi hết file → điều kiện dừng vòng `while` |
| `BufferedWriter` + `OutputStreamWriter(..., UTF_8)` | ghi đè cả file (mở không có `append`) |
| `try (...) { }` (try-with-resources) | **tự đóng file** kể cả khi lỗi |

### 2.4 "Trạng thái" `boolean` của `addWord` / `removeWord`

Đề bắt trả `boolean` = trạng thái. Bài này trả `false` khi **ghi file hỏng** — và khi đó **trả Map về
như cũ** (rollback), để bộ nhớ không bao giờ khác file:

| Tình huống | `addWord` | Map sau đó | File |
|---|---|---|---|
| Ghi file được | `true` | có từ mới | có từ mới |
| Ghi file hỏng (file chỉ-đọc) | `false` | **như trước** | như trước |

---

## 3. Thiết kế

```
HE176322_J1SP0058_Dictionary/
├── dictionary.txt                     dữ liệu (gốc project)
└── src/
    ├── model/       Word              1 cặp Anh–Việt (JavaBean) + toString() = 1 dòng file
    ├── dto/         WordRequestDTO    english, vietnamese, overwrite  (main ──► controller)
    │                WordResponseDTO   english, vietnamese             (controller ──► view)
    ├── repository/  DictionaryRepository  LinkedHashMap + loadData/addWord/removeWord/translate
    ├── controller/  DictionaryController  điều hướng repository ↔ view (Facade)
    ├── view/        DictionaryView    in nghĩa / "Successful"
    ├── utils/       FileUtils         isFileExist · readLines · writeLines (static)
    │                Validation        getChoice · getNonBlank · getEnglish · getYesNo (static)
    ├── constants/   Message · Constants
    └── main/        Main              menu + Scanner
```

| Lớp | Làm gì | Không được làm |
|---|---|---|
| `Main` | vòng menu, **đọc bàn phím**, hỏi Y/N, gói vào DTO | gọi model/view/file, biến static |
| `DictionaryController` | nhận DTO → gọi repository → đưa kết quả cho view | Scanner, `System.out`, static |
| `DictionaryRepository` | giữ Map, CRUD, giữ file **bằng** Map | in ra, đọc bàn phím |
| `FileUtils` | chuyển **dòng chữ** giữa file ↔ `ArrayList` | biết "từ" là gì |
| `Word` | mô tả 1 cặp từ | Scanner, printf, static |
| `DictionaryView` | in kết quả | tính toán |

**Luồng Add** (từ đã có):

```
Main: đọc English, Vietnamese ──► WordRequestDTO
Main: controller.checkExistWord(dto) == true ──► hỏi "(Y/N)?" ──► dto.setOverwrite(...)
Main: controller.addWord(dto)
        ├─ đã có + trả lời N  ──► view.showMessage("The old meaning is kept.")
        └─ repository.addWord(eng, vi)
               ├─ wordMap.put(...)
               ├─ updateDatabase() ──► FileUtils.writeLines(...)   ← ghi ĐÈ cả file
               └─ true ──► controller ──► view.showMessage("Successful")
```

### 3.1 Design Pattern trong bài

| Pattern | Name · Problem · Solution · Consequences |
|---|---|
| **MVC** (kiến trúc — "MVC JSP") | **Problem**: nhập, xử lý, in, ghi file trộn một chỗ. **Solution**: `DictionaryController` ~ Servlet, `DictionaryView` ~ trang JSP, `Word` ~ JavaBean; dữ liệu đi qua DTO. **Consequences**: đổi cách in chỉ sửa View; nhiều lớp hơn. |
| **Facade** (Structural) | **Problem**: không có nó, `Main` phải biết repository, file, view và thứ tự gọi. **Solution**: `DictionaryController` là **một cửa**: `addWord(dto)` tự gọi repository (repository tự ghi file) rồi view. **Consequences**: ✅ `Main` không import repository/utils file, đổi cách lưu không đụng `Main`. ❌ controller phải giữ đúng vai điều hướng, không ôm nghiệp vụ. |
| **Repository** (mẫu dữ liệu, không thuộc 23 GoF) | **Problem**: Map và file phải luôn khớp nhau. **Solution**: chỉ `DictionaryRepository` được đụng vào cả hai; mọi thay đổi Map đều đi kèm `updateDatabase()`. **Consequences**: đổi định dạng file (`=` → `;`) chỉ sửa `Constants.SEPARATOR`. |

> Bài không có "họ thuật toán" hay "họ đối tượng" nên **không nhét** Strategy/Factory — slide SOLID
> (ghi chú slide 26) cảnh báo thêm trừu tượng *"chỉ vì SOLID nói vậy"* là vi phạm YAGNI.

### 3.2 SOLID trong bài

| Nguyên lý | Ở đâu |
|---|---|
| **S** | `Word` giữ 1 cặp · `FileUtils` chỉ đọc/ghi dòng · `DictionaryRepository` giữ dữ liệu · `DictionaryView` in · `Validation` kiểm |
| **O** | đổi nơi lưu (file → khác) chỉ sửa `DictionaryRepository`; `Main`, controller đứng yên |
| **D** (một phần) | `Main` chỉ biết `DictionaryController` + DTO, không biết repository hay file |

---

## 4. Code từng bước — thứ tự nên gõ

> Thầy: *"code cái **model trước rồi đến data**"*.

| Bước | File | Việc | Lưu ý |
|---|---|---|---|
| 1 | `model/Word.java` | `english`, `vietnamese` private + ctor rỗng + ctor đủ + get/set + `toString()` = `english=vietnamese` | JavaBean · **Alt+Insert** |
| 2 | `dto/WordRequestDTO.java`, `WordResponseDTO.java` | JavaBean; Response thêm `isFound()` | ctor rỗng `public` |
| 3 | `utils/FileUtils.java` | `isFileExist` · `readLines` · `writeLines` | `final` + ctor `private` + static; try-with-resources |
| 4 | `repository/DictionaryRepository.java` | `LinkedHashMap` + `loadData` · `isExistWord` · **`addWord` · `removeWord` · `translate`** · `updateDatabase` (private) · `toKey` (private) | **không** `System.out` |
| 5 | `constants/Message.java`, `Constants.java` | gõ dần khi bước trên cần: câu chữ đề, `DATA_FILE`, `SEPARATOR`, số menu | không viết chữ/số thẳng |
| 6 | `view/DictionaryView.java` | `setWord` · `display` · `showMessage` | |
| 7 | `controller/DictionaryController.java` | `loadData` · `checkExistWord` · `addWord` · `removeWord` · `translate` | **không** Scanner |
| 8 | `utils/Validation.java` | `getChoice` · `getNonBlank` · `getEnglish` · `getYesNo` | |
| 9 | `main/Main.java` | `loadData` 1 lần đầu chương trình + menu + các hàm nhập có vòng hỏi lại | Scanner **chỉ ở đây** |
| 10 | — | **Alt+Shift+F** từng file, **F6**, đi hết bảng test mục 5 | |

**Bẫy hay gặp:**

1. **Thêm vào Map mà quên ghi file** → tắt chương trình là mất từ. Mọi `put`/`remove` phải đi kèm `updateDatabase()`.
2. **`split("=")` không có số 2** → nghĩa chứa `=` bị cắt mất nửa sau.
3. **Không hạ chữ thường khoá** → thêm `Cat` rồi dịch `cat` ra "Empty".
4. **Quên đóng file** → nội dung chưa ghi xuống đĩa. Dùng try-with-resources.
5. **File dữ liệu để trong `src/`** → NetBeans chạy ở **gốc project**, không thấy file. Đặt cạnh `build.xml`.

---

## 5. Test trước khi gọi thầy

> Thầy: *"test tất cả các happy case cũng như hiển thị đủ các message validation"*. Bắt đầu với
> `dictionary.txt` có sẵn `hello=xin chao`, `dog=con cho`.

| # | Chọn | Gõ | Phải thấy |
|---|---|---|---|
| 1 | 3 | `HELLO` | `Vietnamese: xin chao` (đã nạp từ file, không phân biệt hoa thường) |
| 2 | 1 | `Cat` / `Con Meo` | `Successful` — mở `dictionary.txt` thấy dòng `Cat=Con Meo` |
| 3 | 3 | `Cat` | `Vietnamese: Con Meo` |
| 4 | 1 | `dog` / `cho con` / `x` | câu hỏi `(Y/N)?` → `Please input Y or N.` rồi hỏi lại |
| 5 | | tiếp: `n` | `The old meaning is kept.` — dịch `dog` vẫn `con cho` |
| 6 | 1 | `DOG` / `con cho moi` / `Y` | `Successful` — dịch `dog` ra `con cho moi` |
| 7 | 2 | `Cat` | `Successful` — dòng `Cat=...` biến khỏi file |
| 8 | 2 | `zzz` | `Key does not exist in the dictionary.` |
| 9 | 3 | `cat` (đã xoá) | `Empty - this word is not in the dictionary.` |
| 10 | 1 | English để trống | `English word must not be empty.` rồi hỏi lại |
| 11 | 1 | English `a=b` | `English word must not contain '='.` |
| 12 | 1 | Vietnamese để trống | `Vietnamese word must not be empty.` |
| 13 | menu | `abc`, `5`, `0` | `You must input a number.` · `Value must be between 1 and 4.` |
| 14 | 4 | | `Bye` — **chạy lại F6**, dịch `dog` vẫn ra nghĩa đã sửa (file đã lưu) |
| 15 | — | xoá `dictionary.txt`, F6 | chạy bình thường với từ điển rỗng (nhánh "chưa có file") |
| 16 | — | chuột phải `dictionary.txt` ▸ Properties ▸ Read-only, rồi Add | `Can't write the dictionary file.` — từ **không** được thêm |

---

## 6. Debug — khi thầy bảo "debug cho thầy xem"

| Việc | Cách làm |
|---|---|
| Breakpoint | dòng `Word oldWord = wordMap.put(key, new Word(eng, vi));` trong `DictionaryRepository.addWord` |
| Chạy debug | **Ctrl+F5**, chọn 1, gõ `Cat` / `Con Meo` |
| Quan sát | tab **Variables**: `key` = `"cat"` (đã hạ chữ thường), mở `wordMap` xem số phần tử tăng |
| Bước | **F8** tới `updateDatabase();` → **F7** vào trong → **F7** vào `FileUtils.writeLines` → thấy vòng `for` ghi từng dòng |
| Nhánh "đã có" | thêm `dog`: `oldWord` **khác null** = nghĩa cũ — đó là thứ được trả lại nếu ghi file hỏng |
| Nạp file | breakpoint ở `String[] parts = line.split(...)` trong `loadData`, **F5** từng vòng, xem `parts` |

---

## 7. Câu hỏi thầy hay hỏi

### OOP

| Câu hỏi | Trả lời mẫu |
|---|---|
| 4 tính chất OOP ở đâu? | **Đóng gói**: `english`, `vietnamese` `private` trong `Word`; `wordMap` `private` trong repository — chỉ đổi qua `addWord/removeWord`. **Kế thừa**: `LinkedHashMap extends HashMap`; mọi lớp `extends Object` và em ghi đè `toString()`. **Đa hình**: `word.toString()` chạy bản của `Word` (ra `cat=con meo`). **Trừu tượng**: `Main` gọi `controller.addWord(dto)` mà không biết có file hay Map phía sau. |
| `Word.toString()` để làm gì? | Trả **một dòng của file** — model không được ghi file, nó trả chuỗi, repository đưa cho `FileUtils` ghi. |
| Sao `Word` có constructor rỗng? | Thầy dạy **MVC JSP**: model/DTO là **JavaBean** — field `private`, constructor rỗng `public`, get/set. |

### Access modifier, static, kiểu trả về

| Câu hỏi | Trả lời mẫu |
|---|---|
| Field sao `private`? | Không lớp nào sửa thẳng `wordMap`; sửa phải qua `addWord` — nơi luôn ghi file kèm. |
| `updateDatabase()`, `toKey()` sao `private`? | Chỉ `DictionaryRepository` gọi. Để `public` thì lớp khác có thể ghi file lúc Map đang dở. |
| Sao `addWord/removeWord/translate/loadData/isExistWord` `public`? | Controller (lớp khác) gọi — và 3 hàm đầu **đề bắt `public`**. |
| Hàm của `DictionaryController` sao `public`? | `Main` gọi. Hàm nhập trong `Main` (`inputEnglish`…) `private` vì chỉ `Main` dùng. |
| Sao `FileUtils`, `Validation` là `static`? | Không dùng dữ liệu riêng của đối tượng nào: cùng đường dẫn/chuỗi vào → cùng kết quả. Guide: utils *"phải dùng static method"*. |
| **Bỏ `static` thì sao?** | `FileUtils.readLines(...)` báo lỗi biên dịch. Muốn chạy phải bỏ `private` constructor, tạo `FileUtils fileUtils = new FileUtils();` trong repository rồi gọi `fileUtils.readLines(...)`. |
| Hàm trong `Main` sao `static`? | `main` là `static`, gọi thẳng được hàm `static`. Thầy: *"cấm static với biến, có thể dùng với hàm"* → `Scanner sc` là biến **cục bộ**. |
| `addWord` trả `boolean` vì sao? | Đề: *"Return value: the status add word"* — `false` = ghi file hỏng (Map đã được trả lại như cũ). |
| `translate` trả `String`, không thấy thì sao? | Trả `null` — giá trị duy nhất **không thể** là một nghĩa thật; `WordResponseDTO.isFound()` kiểm `null`. |
| `loadData` trả `void`? | Nó **đổ dữ liệu vào `wordMap`** (field) — không có gì để trả; lỗi đi bằng `throw`. |
| `checkExistWord` trả `boolean` cho `Main` — trái luật "gọi controller 1 lần"? | Là **bước kiểm tra trước** (như `checkExistDoctor` của P0055) để biết có phải hỏi Y/N. Việc thêm vẫn chỉ gọi `addWord` **đúng 1 lần**. |

### Collection

| Câu hỏi | Trả lời mẫu |
|---|---|
| **Sao `LinkedHashMap` mà không `Map`/`HashMap`?** | `Map` là **interface** (hợp đồng `put/get/remove`), `HashMap` là lớp cài đặt bằng bảng băm — tra theo khoá rất nhanh nhưng **không giữ thứ tự**. `LinkedHashMap` **kế thừa** `HashMap` và nhớ thứ tự thêm → file ghi ra không bị xáo trộn mỗi lần lưu. Code phụ thuộc thật vào thứ tự đó nên em khai báo đúng kiểu `LinkedHashMap`. |
| `List` khác `ArrayList`? | `List` là interface; `ArrayList` là lớp cài đặt bằng **mảng động** (lấy theo chỉ số nhanh, chèn giữa chậm). `readLines` trả `ArrayList<String>` vì nơi gọi chỉ duyệt từ đầu tới cuối. |
| Sao không có hàm nào 3 tham số? | Thầy: *"không truyền 3 tham số 1 hàm"*. `addWord(eng, vi)` là 2 (đúng chữ ký đề); controller nhận 1 `WordRequestDTO`. `Validation.getChoice(input, min, max)` 3 tham số là **hàm utils theo đúng mẫu Guide**. |

### Thiết kế / file

| Câu hỏi | Trả lời mẫu |
|---|---|
| Sao ghi file sau **mỗi** lần thêm/xoá, không đợi Exit? | Đề: *"put into hashmap then overwrites the data on file"*. Người dùng tắt cửa sổ thay vì chọn 4 vẫn không mất từ. |
| Sao ghi **đè** cả file mà không ghi thêm dòng? | Xoá/sửa nghĩa không làm được bằng cách ghi thêm — ghi lại toàn bộ Map là cách đơn giản, luôn đúng. |
| Sao UTF-8? | Nghĩa là tiếng Việt; máy lab Windows mặc định mã khác → mất dấu khi mở lại. |
| Pattern gì? | **MVC** + controller là **Facade** + `DictionaryRepository` theo mẫu **Repository** — mục 3.1. |
| Độ phức tạp? | `put/get/remove/containsKey` của HashMap ~ **O(1)**; `updateDatabase` ghi cả file **O(n)** với n = số từ. |

---

## 8. Thầy đổi yêu cầu tại chỗ

| Thầy bảo | Sửa file | Không phải đụng |
|---|---|---|
| Thêm menu **"Hiện tất cả từ"** | `Message.MENU`, `Constants` (số menu), `DictionaryRepository.getAll()` trả `ArrayList<WordResponseDTO>`, `DictionaryController.showAll()`, `DictionaryView` (hàm in danh sách), thêm `case` ở `Main` | `Word`, `FileUtils` |
| Từ đã có thì **báo lỗi**, không hỏi Y/N | bỏ khối `if (controller.checkExistWord(dto))` ở `Main`; `DictionaryController.addWord` đổi nhánh "đã có" thành `throw new Exception(Message.X)` | repository, file |
| Phân biệt hoa thường | `toKey` trả `eng.trim()` (bỏ `toLowerCase`) | mọi file khác |
| Đổi dấu ngăn cách thành `;` | `Constants.SEPARATOR`, `Message.ENGLISH_SEPARATOR` | code |
| Đổi tên file | `Constants.DATA_FILE` | code |
| Chỉ lưu file khi **Exit** | `addWord/removeWord` bỏ `updateDatabase()`; thêm `DictionaryController.save()` gọi ở `case MENU_EXIT` | `Word`, `FileUtils` |

---

## 9. Chỗ khác với đề / bản cũ

| Chỗ | Đề / bản cũ | Bài này | Lý do |
|---|---|---|---|
| Số thứ tự menu | đề in `Add Word` (bản text mất số) | `1. Add Word` … `4. Exit` | người dùng chọn bằng số `1`–`4` (đề in các phím `1`,`2`,`3`,`4`) |
| Không tìm thấy khi dịch | đề: *"display empty"* | `Empty - this word is not in the dictionary.` | hiện chữ "Empty" cho thấy rõ, không để dòng trống khó hiểu |
| Xoá từ không có | đề: *"notification does not exist in the db key"* | `Key does not exist in the dictionary.` | đề không cho câu nguyên văn |
| Từ đã có khi Add | bản cũ: từ chối | **hỏi Y/N** sửa nghĩa (sau khi nhập nghĩa mới) | Suggestion của đề: *"If yes, ask the users want to update the mean"* |
| Câu kết quả | bản cũ: `Added.` · `Deleted.` · `Cat = con meo` | `Successful` · `Vietnamese: Con Meo` | **đúng màn hình đề** → test dùng `REPLACE_REFERENCE = True` |
| Dòng `Loaded 0 word(s).` | bản cũ in khi khởi động | không in | màn hình đề bắt đầu bằng menu |
| Lỗi nhập, lỗi file, câu hỏi Y/N, `Bye` | đề không cho | câu trong `Message` | đề im lặng; `Bye` giữ như bản cũ |
| `loadData`, `updateDatabase` | đề chỉ gợi ý tên | ở `DictionaryRepository`; `updateDatabase` `private` | chỉ repository được quyết khi nào file theo Map |
| Ghi file | đề không nói | **UTF-8**, rollback khi ghi hỏng | giữ dấu tiếng Việt; Map không bao giờ khác file |
| Kiến trúc | bản cũ: `bo/ui/utils`, Scanner trong `Validator` | MVC theo Guide, Scanner **chỉ ở `main`** | luật thầy |
