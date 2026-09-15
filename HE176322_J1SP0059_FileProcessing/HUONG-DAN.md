# J1.S.P0059 — The program handles files

> Hai việc với file: (1) đọc danh sách người `tên;địa chỉ;lương`, lọc người lương ≥ số nhập,
> **sắp theo lương** và chỉ ra Max/Min; (2) chép **mỗi từ một lần** sang file mới. Sắp xếp dùng
> `Collections.sort` + `Comparator` — chính là **Strategy pattern** của JDK.

| | |
|---|---|
| Loại / LOC | Short Assignment · 73 LOC · 1 slot |
| Project | `HE176322_J1SP0059_FileProcessing` |
| Chạy | NetBeans: **File ▸ Open Project** → chọn thư mục → **F6** |
| Lớp chạy | `main.Main` |
| File mẫu | `test.txt` (7 người, có lương hỏng) và `story.txt` (câu có từ lặp) ở **gốc project** |
| Kiểm tự động | `python3 _tools/verify.py --netbeans J1SP0059` → 3 kịch bản × 2 locale |

---

## 1. Đề bài nói gì

- Menu: **Find person info · Copy Text to new file · Exit**.
- **Option 1**: nhập đường dẫn file + số tiền tối thiểu → in những người **lương ≥ số đó**, dạng cột,
  rồi dòng trống, `Max: <người nhiều tiền nhất>`, `Min: <người ít nhất>`. Lương trong file **sai định
  dạng / bỏ trống → 0**.
- **Option 2**: nhập file nguồn + tên file mới → ghi **mọi từ, mỗi từ đúng một lần** sang file mới → `Copy done...`.

Màn hình đề (chép đúng chữ — các cột cách nhau bằng **ký tự TAB**):

```
--------- Person info ---------
Enter Path:d:\test.txt
Enter Money:800
------------- Result ----------
Name		Address	Money
Nghia		Ha Noi		1000.0
Thanh		Ha Noi		1200.0
Phuong		Ha Noi		1300.0

Max: Phuong
Min: Nghia
```

**Đề bắt buộc** (Guidelines):

| Thứ | Đề viết | Bài này đặt ở |
|---|---|---|
| `public List<Person> getPerson(String path, double money) throws Exception` | *"Must set the function"* | `FileService.getPerson` — **đúng chữ ký** |
| Danh sách: *"least money on the head of list, most money on the last"* | | `Collections.sort(persons, personOrder)` với `SalaryComparator` |
| `public static boolean copyWordOneTimes(String source, String destination) throws Exception` | *"Must set the function"* | `FileUtils.copyWordOneTimes` — **đúng chữ ký, kể cả `static`** |
| Lỗi `Path doesn't exist` · `Can’t read file` · `Can’t write file` | | `constants/Message` — **chép đúng từng chữ** (dấu `’` cong như đề) |
| Dùng `BufferedReader/BufferedWriter/File/FileReader/FileWriter/IOException/ArrayList/List` · `Collections` · `Comparator` | Suggest | `FileUtils`, `FileService`, `SalaryComparator` |

---

## 2. Kiến thức cần biết

### 2.1 Tách một dòng bằng `split(";", -1)`

| Dòng trong `test.txt` | `split(";", -1)` | Lương đọc được |
|---|---|---|
| `Nghia;Ha Noi;1000` | `[Nghia, Ha Noi, 1000]` | 1000.0 |
| `Hoang;Da Nang;abc` | `[Hoang, Da Nang, abc]` | `abc` sai định dạng → **0** |
| `Minh;Hai Phong;` | `[Minh, Hai Phong, ""]` — **-1 giữ ô rỗng** | bỏ trống → **0** |
| `Tuan;Vinh;-50` | `[Tuan, Vinh, -50]` | âm — đề *"not less than 0"* → **0** |
| `the` (không có `;`) | `[the]` | thiếu ô → địa chỉ `""`, lương **0** |

Không có `-1`, `"Minh;Hai Phong;"` chỉ ra **2** phần — ô lương biến mất thay vì là ô rỗng.

### 2.2 Chạy tay ví dụ của đề: money = 800

| Bước | Danh sách |
|---|---|
| Đọc 7 dòng, đổi lương hỏng → 0 | Nghia 1000 · Thanh 1200 · Phuong 1300 · Hoang 0 · Lan 700 · Minh 0 · Tuan 0 |
| Giữ lương **≥ 800** | Nghia 1000 · Thanh 1200 · Phuong 1300 |
| `Collections.sort` theo lương tăng | Nghia 1000 · Thanh 1200 · Phuong 1300 |
| Đầu danh sách = Min, cuối = Max | **Min: Nghia · Max: Phuong** ✅ đúng màn hình đề |

Với money = 0: ba người lương 0 đứng đầu **theo đúng thứ tự trong file** (Hoang, Minh, Tuan) vì
`Collections.sort` là sắp xếp **ổn định** (stable) → Min: Hoang.

### 2.3 "Mỗi từ một lần" = `LinkedHashSet`

`story.txt`: `the cat and the dog` / `the dog sees the cat`

| Từ đọc được | `words.add(...)` | Set sau đó |
|---|---|---|
| the, cat, and | thêm | the, cat, and |
| the | **đã có → bỏ qua** | |
| dog | thêm | the, cat, and, dog |
| the, dog | bỏ qua | |
| sees | thêm | the, cat, and, dog, **sees** |
| the, cat | bỏ qua | → file mới 5 dòng |

`Set` tự từ chối phần tử trùng; **Linked** giữ thứ tự gặp đầu tiên. Tách từ bằng `split("\\s+")` (một
hoặc nhiều khoảng trắng/tab). Phân biệt hoa thường, dấu câu dính theo từ (`Hello,` ≠ `Hello`).

### 2.4 Java dùng trong bài

| API | Dùng làm gì |
|---|---|
| `new File(path).exists()` | không có → `Path doesn't exist` |
| `new BufferedReader(new FileReader(file))` | đọc từng dòng; là **thư mục** hoặc không có quyền → `IOException` → `Can’t read file` |
| `new BufferedWriter(new FileWriter(path))` | ghi file mới; thư mục không tồn tại → `Can’t write file` |
| `Double.parseDouble(s)` | chuỗi sai → `NumberFormatException` → lương 0 |
| `Collections.sort(list, comparator)` | sắp xếp **ổn định**, thứ tự do `Comparator` quyết |
| `String.format(Locale.US, "%.1f", 1000)` | ra `1000.0` — **Locale.US** để máy tiếng Việt không in `1000,0` |

---

## 3. Thiết kế

```
HE176322_J1SP0059_FileProcessing/
├── test.txt · story.txt                    dữ liệu mẫu (gốc project)
└── src/
    ├── model/       Person                 name, address, salary (JavaBean) — tên lớp do đề đặt
    ├── dto/         FileRequestDTO         path, money, source, destination   (main ──► controller)
    │                PersonResponseDTO      1 dòng bảng + toString() định dạng TAB
    │                ReportResponseDTO      các dòng + maxName + minName         (controller ──► view)
    ├── service/     SalaryComparator       «ConcreteStrategy» lương tăng dần
    │                FileService            getPerson · findPerson · copyText (Context của Strategy)
    ├── controller/  FileController         cắm SalaryComparator vào service; service ──► view
    ├── view/        FileView               in bảng + Max/Min, "Copy done..."
    ├── utils/       FileUtils              readLines · writeLines · copyWordOneTimes (static)
    │                Validation             getChoice · getNonBlank · getMoney (static)
    ├── constants/   Message · Constants
    └── main/        Main                   menu + Scanner
```

| Câu hỏi thiết kế | Trả lời |
|---|---|
| Sao có `service` mà không có `repository`? | Guide: repository = **giữ một tập dữ liệu + CRUD**. Bài này không giữ gì giữa hai lần chọn menu — mỗi lần tìm là **đọc lại file**. Lọc + sắp + Max/Min là **"report"** → `service`. |
| Sao `copyWordOneTimes` ở `utils`? | Đề bắt nó **`static`**, mà luật thầy chỉ cho static ở `utils`/`constants`/`main`; và việc của nó là **chép file sang file** — đúng vai `FileUtils` (*"đọc/ghi file"*). |
| Sao có cả `getPerson` và `findPerson`? | `getPerson` giữ **đúng chữ ký đề** (trả `List<Person>`). Controller **không được thấy model** → `findPerson` gọi `getPerson` rồi đổi sang `ReportResponseDTO`. |
| Sao Max/Min tính ở service chứ không ở view? | View *"chỉ hiển thị"*. Danh sách đã sắp nên Min = `get(0)`, Max = `get(size - 1)` — service điền sẵn vào DTO. |

**Luồng Option 1:**

```
Main: đọc path (hỏi lại khi trống), money (hỏi lại khi sai/âm) ──► FileRequestDTO ──► controller.findPerson(dto)
   controller ──► service.findPerson(dto)
                    └─ getPerson(path, money)
                          ├─ FileUtils.readLines(path)    → "Path doesn't exist" / "Can’t read file"
                          ├─ toPerson(line) + toSalary    → lương hỏng = 0
                          ├─ giữ salary >= money
                          └─ Collections.sort(persons, personOrder)   ← Strategy
                    ├─ đổi Person → PersonResponseDTO
                    └─ minName = đầu, maxName = cuối
   controller ──► view.setReport(report) ──► view.display()
```

### 3.1 Design Pattern — **Strategy** (thầy đánh giá cao Design Pattern)

| Yếu tố | Trong bài này |
|---|---|
| **Name** | Strategy (nhóm Behavioral) |
| **Problem** | Cần sắp danh sách người **theo một thứ tự** — hôm nay theo lương, thầy có thể bảo *"sắp theo tên"*, *"lương bằng nhau thì theo tên"*. Viết cứng phép so sánh vào vòng sắp xếp thì mỗi lần đổi phải **mở service ra sửa**. |
| **Solution** | `java.util.Comparator<Person>` = **Strategy** (interface `compare`). `SalaryComparator` = **ConcreteStrategy**. `Collections.sort` = thuật toán dùng strategy; `FileService` = **Context**: giữ `Comparator<Person> personOrder` nhận **qua constructor**. `FileController` là nơi **chọn**: `new FileService(new SalaryComparator())`. |
| **Consequences** | ✅ Đổi thứ tự = **thêm 1 class** `implements Comparator<Person>` + sửa 1 dòng ở controller; `FileService`, `Main`, view đứng yên (**O**/**D** của SOLID). ❌ Thêm 1 file. ⚠️ Đề định nghĩa Max = **cuối**, Min = **đầu** danh sách, nên strategy nào cắm vào cũng phải giữ "ít tiền trước" — một comparator giảm dần sẽ làm Max/Min đổi chỗ. |

Ngoài ra: **MVC** ("MVC JSP" — controller ~ Servlet, view ~ JSP, `Person` ~ JavaBean) và controller
đóng vai **Facade** (một cửa cho `Main`: `findPerson(dto)`, `copyText(dto)`).

**Thầy bảo "lương bằng nhau thì sắp theo tên" — làm trong 2 phút:**
1. Tạo `service/SalaryThenNameComparator.java` `implements Comparator<Person>`: so lương, bằng thì `first.getName().compareTo(second.getName())`.
2. Sửa **đúng 1 dòng** trong `FileController`: `new FileService(new SalaryThenNameComparator())`.

### 3.2 SOLID trong bài

| Nguyên lý | Ở đâu |
|---|---|
| **S** | `Person` giữ dữ liệu · `FileUtils` đọc/ghi · `FileService` lọc/sắp/tính Max-Min · `SalaryComparator` chỉ so 2 người · `FileView` in |
| **O** | thứ tự mới = lớp `Comparator` mới, không sửa `FileService` |
| **L** | mọi `Comparator<Person>` giữ "ít tiền trước" thay được `SalaryComparator` mà chương trình vẫn đúng |
| **I** | `Comparator` chỉ bắt viết đúng 1 hàm `compare` |
| **D** | `FileService` phụ thuộc `Comparator<Person>` (trừu tượng), nhận qua constructor |

---

## 4. Code từng bước — thứ tự nên gõ

> Thầy: *"code cái **model trước rồi đến data**"*.

| Bước | File | Việc | Lưu ý |
|---|---|---|---|
| 1 | `model/Person.java` | 3 field private (`salary` là `double`) + ctor rỗng + ctor đủ + get/set + `toString()` | JavaBean · **Alt+Insert** |
| 2 | `dto/FileRequestDTO.java` | path, money, source, destination | ctor rỗng + get/set |
| 3 | `dto/PersonResponseDTO.java`, `ReportResponseDTO.java` | 1 dòng bảng (`toString` = `ROW_FORMAT`, `Locale.US`) · danh sách + maxName + minName | |
| 4 | `utils/FileUtils.java` | `readLines` · `writeLines` · **`copyWordOneTimes`** | `final` + ctor `private` + static |
| 5 | `service/SalaryComparator.java` | `compare` = `Double.compare(lương1, lương2)` | `@Override` |
| 6 | `service/FileService.java` | field `personOrder` + ctor; **`getPerson`** · `findPerson` · `copyText` · `toPerson`/`getPart`/`toSalary` (private) | |
| 7 | `constants/Message.java`, `Constants.java` | câu của đề (TAB `\t`, dấu `’` = `\u2019`), `SEPARATOR`, chỉ số cột, `ROW_FORMAT` | gõ dần khi bước trên cần |
| 8 | `view/FileView.java` | `setReport` · `display` · `showMessage` | |
| 9 | `controller/FileController.java` | ctor cắm `SalaryComparator`; `findPerson` · `copyText` | **không** Scanner |
| 10 | `utils/Validation.java` | `getChoice` · `getNonBlank` · `getMoney` | |
| 11 | `main/Main.java` | menu + `inputText` · `inputMoney` · `findPerson` · `copyText` | Scanner **chỉ ở đây** |
| 12 | — | **Alt+Shift+F**, **F6**, đi hết bảng test mục 5 | |

**Bẫy hay gặp:**

1. **In bằng dấu cách thay vì TAB** → khác màn hình đề. Header là `Name\t\tAddress\tMoney`, dòng là `%s\t\t%s\t\t%.1f`.
2. **Sắp giảm dần** → Max/Min ngược. Đề: ít tiền ở **đầu**, nhiều tiền ở **cuối**.
3. **Để `NumberFormatException` của một dòng lương hỏng thoát ra** → cả file không đọc được. Bắt **trong `toSalary`**, trả 0.
4. **`split(";")` thiếu `-1`** → dòng `Minh;Hai Phong;` mất ô lương.
5. **`%.1f` không `Locale.US`** → máy lab tiếng Việt in `1000,0`.
6. **Nhập tay `d:\test.txt`** như đề → máy em không có ổ D. Gõ `test.txt` (file ở gốc project).

---

## 5. Test trước khi gọi thầy

| # | Chọn | Gõ | Phải thấy |
|---|---|---|---|
| 1 | 1 | `test.txt` / `800` | **đúng màn hình đề**: Nghia, Thanh, Phuong · `Max: Phuong` · `Min: Nghia` |
| 2 | 1 | `test.txt` / `0` | 7 người; Hoang, Minh, Tuan (lương hỏng/trống/âm) = `0.0` đứng đầu; `Min: Hoang` |
| 3 | 1 | `test.txt` / `5000` | `No person found.` |
| 4 | 1 | path để trống | `You must input something.` rồi hỏi lại |
| 5 | 1 | money `abc` | `You must input a number.` rồi hỏi lại |
| 6 | 1 | money `-1` | `Money must not be less than 0.` |
| 7 | 1 | `nofile.txt` / `100` | `Path doesn't exist` |
| 8 | 1 | `test` (là **thư mục**) / `100` | `Can’t read file` |
| 9 | 2 | `story.txt` / `unique.txt` | `Copy done...` — mở `unique.txt`: `the cat and dog sees`, mỗi từ 1 dòng |
| 10 | 2 | `nofile.txt` / `out.txt` | `Path doesn't exist` |
| 11 | 2 | `test` / `out.txt` | `Can’t read file` |
| 12 | 2 | `story.txt` / để trống / `no_such_dir/out.txt` | `You must input something.` rồi `Can’t write file` |
| 13 | menu | `x`, `4` | `You must input a number.` · `Please choose from 1 to 3.` |
| 14 | 3 | | `Goodbye.` |

---

## 6. Debug

| Việc | Cách làm |
|---|---|
| Breakpoint | dòng `if (person.getSalary() >= money) {` trong `FileService.getPerson` |
| Chạy debug | **Ctrl+F5**, chọn 1, `test.txt`, `800` |
| Quan sát | tab **Variables**: `line`, `person` (mở ra xem `salary` — dòng `Hoang` là `0.0`), `persons` tăng dần |
| Bước | **F7** vào `toPerson` → `toSalary`: với `abc`, **F8** thấy nhảy vào `catch` rồi `return 0` |
| Sắp xếp | breakpoint trong `SalaryComparator.compare` → **F5** nhiều lần: thấy `Collections.sort` gọi strategy để so từng cặp |
| Copy | breakpoint ở `words.add(word);` trong `FileUtils.copyWordOneTimes`, xem `words` không tăng khi gặp `the` lần 2 |

---

## 7. Câu hỏi thầy hay hỏi

### OOP

| Câu hỏi | Trả lời mẫu |
|---|---|
| 4 tính chất OOP ở đâu? | **Đóng gói**: 3 field `private` trong `Person`, đổi qua setter. **Kế thừa**: `SalaryComparator implements Comparator<Person>`; mọi lớp `extends Object` và em ghi đè `toString()`. **Đa hình**: `Collections.sort` gọi `compare` qua biến kiểu `Comparator` — chạy bản của `SalaryComparator`; `println(person)` gọi `toString()` của `PersonResponseDTO`. **Trừu tượng**: `FileService` chỉ biết `Comparator`, không biết so theo gì. |
| Sao `salary` là `double`, không `String`? | Phải **so sánh và sắp** lương; dạng chuỗi thì `"1000"` đứng trước `"700"`. |
| Sao `Person` có constructor rỗng? | **MVC JSP**: model/DTO là **JavaBean** — field `private`, constructor rỗng `public`, get/set. `toPerson` dùng chính constructor rỗng + setter. |

### Access modifier, static, kiểu trả về

| Câu hỏi | Trả lời mẫu |
|---|---|
| `getPerson` sao `public` dù chỉ `findPerson` gọi? | **Đề bắt** đúng chữ ký `public List<Person> getPerson(...)` — đó là hợp đồng đề chấm. |
| `findPerson`, `copyText` sao `public`? | `FileController` (lớp khác) gọi. |
| `toPerson`, `getPart`, `toSalary` sao `private`? | Chỉ `FileService` dùng; không phải hợp đồng của lớp. |
| `compare` sao `public`? | Nó **ghi đè** hàm của interface `Comparator` — hàm interface luôn `public`, không được hạ quyền. |
| Sao `copyWordOneTimes` là `static`? | **Đề bắt** `public static boolean`. Nó không dùng dữ liệu đối tượng nào (chỉ 2 đường dẫn vào) — và em đặt nó ở `utils`, nơi duy nhất thầy cho static. |
| Sao `FileUtils`, `Validation` `static`? | Cùng đường dẫn/chuỗi vào → cùng kết quả, không có trạng thái. Guide: *"phải dùng static method"*, `final`, constructor `private`. |
| **Bỏ `static` thì sao?** | `FileUtils.readLines(...)` báo lỗi biên dịch; phải bỏ `private` constructor, tạo `FileUtils fileUtils = new FileUtils();` trong `FileService` rồi gọi qua đối tượng. Riêng `copyWordOneTimes` thì **trái chữ ký đề**. |
| Hàm trong `Main` sao `static`? | `main` là `static`; thầy: *"cấm static với biến, có thể dùng với hàm"* → `Scanner sc` là biến cục bộ. |
| `getPerson` trả `List<Person>`? | Chữ ký đề. Bên trong em tạo `ArrayList<Person>` — `ArrayList` **là một** `List`, trả lên kiểu cha được. |
| `copyWordOneTimes` trả `boolean` mà luôn `true`? | Đề: *"Output: copy status"*; mọi thất bại đề liệt kê bằng `Exception`, nên đến được `return` là đã chép xong. Controller vẫn kiểm `if (...)` rồi mới in `Copy done...`. |
| `compare` trả `int`? | Hợp đồng `Comparator`: âm = đứng trước, 0 = bằng, dương = đứng sau. |

### Collection

| Câu hỏi | Trả lời mẫu |
|---|---|
| **Sao chỗ này `List`, chỗ kia `ArrayList`?** | `List` là **interface**, `ArrayList` là **lớp cài đặt** bằng mảng động. Em khai báo kiểu cụ thể `ArrayList` ở mọi chỗ **tự chọn**; chỉ `getPerson` (và biến nhận kết quả của nó) giữ `List` vì **đề bắt chữ ký** — dòng đó có comment `// brief:`. |
| Sao `LinkedHashSet` cho copy? | `Set` tự bỏ phần tử trùng ("mỗi từ một lần"); `HashSet` làm xáo thứ tự, `LinkedHashSet` giữ thứ tự gặp đầu tiên → file ra dễ kiểm. |
| Sao không hàm nào 3 tham số? | Thầy: *"không truyền 3 tham số 1 hàm"* → path/money/source/destination gói trong `FileRequestDTO`. `getPerson(path, money)` và `copyWordOneTimes(source, destination)` đúng 2 tham số của đề. |

### Thuật toán / file

| Câu hỏi | Trả lời mẫu |
|---|---|
| Độ phức tạp? | Đọc + lọc **O(n)**; `Collections.sort` **O(n log n)**; copy từ: mỗi `add` vào Set ~ **O(1)** → **O(số từ)**. |
| Lương bằng nhau thì thứ tự? | `Collections.sort` **ổn định** → giữ thứ tự trong file. |
| Money nhập sai sao hỏi lại, còn lương trong file sai thì thành 0? | Quy tắc "sai → 0" của đề nói về **dữ liệu trong file** (không hỏi lại được). Người ở bàn phím thì hỏi lại được — lặng lẽ tìm từ 0 sẽ ra kết quả sai mà trông như đúng. |
| Phân biệt "Path doesn't exist" và "Can’t read file" thế nào? | `!file.exists()` → không có gì ở đó. Có mà `FileReader` ném `IOException` (thư mục, không quyền đọc) → đọc không được. |
| Pattern gì? | **Strategy** (mục 3.1) + **MVC** + controller **Facade**. |

---

## 8. Thầy đổi yêu cầu tại chỗ

| Thầy bảo | Sửa file | Không phải đụng |
|---|---|---|
| Lương bằng nhau thì sắp theo **tên** | thêm `service/SalaryThenNameComparator` + 1 dòng ở `FileController` | `FileService`, `Main`, view |
| In thêm **tổng lương / lương trung bình** | `FileService.findPerson` tính → field mới trong `ReportResponseDTO` → `FileView.display` + câu trong `Message` | `FileUtils`, `Main` |
| Copy **không phân biệt hoa thường** | `copyWordOneTimes`: `words.add(word.toLowerCase())` | mọi file khác |
| Copy bỏ **dấu câu** | `Constants.WORD_SEPARATOR` = `"[^\\p{L}\\p{N}]+"` | code |
| Ghi các từ **trên một dòng** | `copyWordOneTimes`: nối bằng `" "` rồi `writeLines` một phần tử | service, controller |
| Chỉ lấy người lương **lớn hơn hẳn** (>) | `getPerson`: `>=` → `>` | mọi file khác |

---

## 9. Chỗ khác với đề / bản cũ

| Chỗ | Đề / bản cũ | Bài này | Lý do |
|---|---|---|---|
| Bảng kết quả | bản cũ: cột độ rộng cố định (`%-10s %-15s %10.1f`) | **TAB** đúng như đề: `Name\t\tAddress\tMoney` | **màn hình đề** thắng → test dùng `REPLACE_REFERENCE = True` (thêm lý do: run 1 của bản cũ dựa vào file run 0 ghi, còn `verify.py` chạy mỗi run trên bản sao sạch) |
| Số menu, prompt chọn | đề in `Find person info` (bản text mất số), không có prompt | `1. …` `3. Exit` + `Enter your choice: ` | giữ như bản cũ; người dùng gõ `1`/`2`/`3` như đề |
| Tiền nhập sai ở bàn phím | đề: *"The amount not less than 0, wrong format, it defaults to 0"* | **hỏi lại** (`You must input a number.` / `Money must not be less than 0.`); lương sai **trong file** mới thành 0 | câu đề không nói rõ áp cho bàn phím hay file; giữ cách của bản cũ — xem mục 7 |
| Lương âm trong file | đề không nói | thành 0 | đề: *"not less than 0"* |
| Không ai đủ lương | đề không nói | `No person found.` (không in Max/Min) | giữ như bản cũ |
| `getPerson` ở đâu | đề không nói lớp | `FileService` (+ `findPerson` đổi sang DTO) | tính toán/report → service; controller không được thấy `Person` |
| `copyWordOneTimes` ở đâu | đề: `public static` | `utils/FileUtils` | static chỉ được ở utils; việc của nó là chép file |
| File mẫu | bản cũ tự sinh `test.txt` lúc chạy | **giao sẵn** `test.txt`, `story.txt` ở gốc project | ít code hơn; đường dẫn `d:\test.txt` của đề chỉ có trên máy tác giả |
| Số tiền in ra | đề: `1000.0` | `%.1f` + `Locale.US` | khớp đề; lương lớn không bị in kiểu `1.5E7`. Lương lẻ bị làm tròn 1 chữ số (`1234.56` → `1234.6`) |
| Kiến trúc | bản cũ: `bo/ui/utils`, Scanner trong `Validator` | MVC theo Guide, Scanner **chỉ ở `main`** | luật thầy |
