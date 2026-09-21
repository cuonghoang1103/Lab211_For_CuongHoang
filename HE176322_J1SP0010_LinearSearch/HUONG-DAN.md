# J1.S.P0010 — Linear Search

> Bài thuật toán **vẫn phải làm MVC** — thầy: *"Các bài liên quan thuật toán như fibo, sắp xếp,…
> cũng phải làm MVC, không OOP/MVC → không review."* Bài này là **anh em của P0001 và P0006**: cùng
> model `NumberArray`, cùng mảng ngẫu nhiên `[0, n)`, như P0006 —
> chỉ khác **thuật toán** (hàm `searchByLinear` trong `SearchService`) và **không sắp xếp**.
>
> **Bản 21/09/2026 — sửa theo tờ checklist giấy 25 mục của thầy** (mục 10), cùng khuôn với P0001: thêm
> `repository/NumberRepository` giữ mảng (tờ giấy 1.1 *"Bắt buộc phải có repository"*), View nhận
> `responseDTO` qua thuộc tính, đổi tên `linearSearch` → `searchByLinear`, `values` → `valueArray`,
> `generateArray` → `generateValueArray`, `Main` thành `final` + constructor `private`. Màn hình chạy **không đổi**.

| | |
|---|---|
| Loại / LOC | Short Assignment · 50 LOC · 1 slot |
| Project | `HE176322_J1SP0010_LinearSearch` |
| Chạy | NetBeans: **File ▸ Open Project** → **F6** |
| Lớp chạy | `main.Main` |
| Kiểm tự động | `python3 _tools/verify.py J1SP0010` → 8 kịch bản × 2 locale |

---

## 1. Đề bài nói gì

- Nhập **số phần tử** (số nguyên dương) → sinh mảng **số ngẫu nhiên "in number range input"**.
- Nhập **giá trị cần tìm**.
- In **mảng** (giữ nguyên thứ tự sinh ra, **không sắp**) và **index** của giá trị — tìm **tuần tự** từng phần tử.

Màn hình đề (ảnh, chép đúng chữ):

```
Enter number of array:
10
Enter search value:
5
The array: [2, 2, 5, 2, 6, 9, 9, 8, 9, 8]
Found 5 at index: 2
```

**Đề bắt buộc:**

| Thứ | Đề viết | Bài này đặt ở |
|---|---|---|
| Thuật toán | *"checking every one of its elements, one at a time and in sequence, until the desired one is found"* | `SearchService.searchByLinear` |
| Tốt nhất / xấu nhất | *"best case … only one comparison"* · *"worst case … n comparisons"* | giải thích ở 2.3 |
| Câu chữ | `Enter number of array:` · `Enter search value:` · `The array: ` · `Found 5 at index: 2` | `constants/Message` |

---

## 2. Kiến thức cần biết

### 2.1 Linear search — ý tưởng

> Đi từ **index 0** sang phải, so từng phần tử với giá trị cần tìm. Gặp **cái đầu tiên bằng** → trả
> index đó, dừng ngay. Đi hết mảng mà không gặp → **không có** (`-1`).

### 2.2 Chạy tay màn hình đề — tìm `5` trong `[2, 2, 5, 2, 6, 9, 9, 8, 9, 8]`

| i | a[i] | a[i] == 5? | Làm gì |
|---|---|---|---|
| 0 | 2 | không | đi tiếp |
| 1 | 2 | không | đi tiếp |
| 2 | 5 | **có** | `return 2` → `Found 5 at index: 2` ✓ |

Tìm `7` (không có): i = 0 … 9 đều "không" → hết vòng `for` → `return NOT_FOUND` → `7 is not in the array.` — đúng **10 phép so sánh** (xấu nhất = n).

Tìm `2` (có 3 lần, ở 0, 1, 3): trả **0** — linear search luôn ra vị trí **đầu tiên**.

### 2.3 Độ phức tạp

| Trường hợp | Số phép so sánh | Ví dụ ở trên |
|---|---|---|
| Tốt nhất | **1** — giá trị ở đầu mảng | tìm `2` |
| Xấu nhất | **n** — không có, hoặc chỉ ở cuối | tìm `7` |
| Trung bình | khoảng n/2 → **O(n)** | |

So với binary search (P0006): linear **không cần mảng sắp**, nhưng chậm hơn khi n lớn (1000 bước so với 10).

### 2.4 Java dùng trong bài

| API | Dùng làm gì |
|---|---|
| `new Random().nextInt(n)` | số ngẫu nhiên trong `[0, n)` |
| `Arrays.toString(arr)` | ra chữ `[2, 2, 5]` đúng định dạng màn hình |
| `Integer.parseInt(s)` | đổi chuỗi sang số; sai → `NumberFormatException` |

---

## 3. Thiết kế

```
HE176322_J1SP0010_LinearSearch/src/
├── model/      NumberArray            mảng số (JavaBean, int[] valueArray) + getSize/getValue/toString
├── repository/ NumberRepository       GIỮ mảng (model): saveNumberArray (Create) · getNumberArray (Read)
├── dto/        SearchRequestDTO       size + searchValue                  (main ──► controller)
│               SearchResponseDTO      numberArray (chữ) + value + index   (controller ──► view)
├── service/    SearchService          sinh mảng, cất vào repository + searchByLinear ← thuật toán của đề ở ĐÂY
├── controller/ SearchController       service ──► view (setResponseDTO + display 1 lần)
├── view/       SearchView             field responseDTO; display() in "The array" + "Found … / … is not in the array."
├── constants/  Message.java           câu chữ màn hình
│               Constants.java         MIN_SIZE = 1, MAX_SIZE = 1000, NOT_FOUND = -1
├── utils/      Validation             getInt(chuỗi), getSize(chuỗi) — final + ctor private
└── main/       Main (final, ctor private)  Scanner + validate + gọi controller 1 lần
```

| Câu hỏi thiết kế | Trả lời |
|---|---|
| Sao bài có `repository`? | Tờ checklist 1.1: *"**Bắt buộc phải có repository**"*. Repository = **dữ liệu** + CRUD đơn giản: ở đây là mảng số thuật toán làm việc trên đó (model `NumberArray`), với `saveNumberArray` / `getNumberArray`. Việc **tìm** là nghiệp vụ nên nằm ở `SearchService` — đúng tầng *Controller ↔ Services ↔ Repository ↔ Model*. Cùng khuôn với P0001. |
| Controller có đụng model không? | Không. Controller chỉ import DTO, service, view (Guide). Service đọc mảng từ repository, tìm, rồi đóng kết quả vào `SearchResponseDTO`. |
| Sao không sắp như P0006? | Đề P0010 in *"the array"* (ảnh: không tăng dần) — linear search chạy trên mảng **bất kỳ**. |
| Sao 2 giá trị gõ vào gói 1 DTO? | Thầy: *"không truyền 3 tham số 1 hàm"*; Guide: data vào controller **qua DTO**. |

**Luồng chạy:**

```
Main: đọc + validate size, searchValue ──► SearchRequestDTO ──► controller.searchArray(requestDTO)   (gọi controller 1 lần)
   controller ──► service.searchRandomArray(requestDTO)
                     ├─ repository.saveNumberArray(generateValueArray(size))   → mảng ngẫu nhiên [0, size)
                     ├─ numberArray = repository.getNumberArray()               (model)
                     ├─ searchByLinear(numberArray, searchValue)   ← thuật toán của đề chạy ở đây
                     └─ responseDTO: numberArray (chữ), searchValue, index
   controller ──► view.setResponseDTO(responseDTO) ──► view.display()                                (render 1 lần)
```

Tầng: **Main → RequestDTO → Controller → Service → Repository → Model**; kết quả **ResponseDTO → View**, render 1 lần.

### 3.1 Design Pattern trong bài

| Pattern | Ở đâu |
|---|---|
| **MVC** — thầy gọi là "MVC JSP" | controller điều hướng (như Servlet) · view hiển thị (như trang JSP) · model là JavaBean |
| **Facade** | controller: `Main` chỉ gọi `controller.searchArray(requestDTO)`, không biết service/repository/model/view phía sau |

> Bài chỉ 50 LOC nên **không thêm lớp pattern GoF** — ghi chú slide SOLID của thầy cảnh báo *"trừu tượng hoá sớm … vi phạm YAGNI"*. Thuật toán nằm gọn trong **một hàm** `searchByLinear` của `SearchService`.

**Thầy hỏi "dùng pattern gì cho dễ mở rộng?"** → trả lời đủ 4 yếu tố GoF (slide *"Elements of a Design Pattern"*):

| Yếu tố | Trả lời |
|---|---|
| **Name** | Strategy (nhóm Behavioral) |
| **Problem** | nhiều thuật toán tìm (linear, binary) cùng làm một việc |
| **Solution** | tách `interface ISearchStrategy { int search(NumberArray numberArray, int searchValue); }` (tờ checklist 1.3: interface bắt đầu bằng `I`); mỗi cách làm là 1 lớp `implements` nó (`LinearSearchStrategy`, `BinarySearchStrategy`); `SearchService` nhận strategy qua constructor |
| **Consequences** | ➕ thêm cách làm = thêm 1 lớp, service đứng yên (**O**) · ➖ thêm 2 file — chỉ đáng khi có từ 2 cách trở lên (bài P0006 Binary search làm đúng như vậy) |

### 3.2 SOLID trong bài

| Nguyên lý | Ở đâu |
|---|---|
| **S** | `NumberArray` mô tả mảng · `NumberRepository` giữ mảng · `SearchService` sinh + tìm · `SearchView` in · `Validation` kiểm |
| **O** | đổi thuật toán chỉ sửa **một hàm** `searchByLinear` |
| **L** | bài chưa có lớp con riêng — chỉ `extends Object` |
| **I** | không có interface — bài chưa cần |
| **D** | `Main` chỉ phụ thuộc controller + DTO |

---

## 4. Code từng bước

> Thầy: *"code cái **model trước rồi đến data**"*.

| Bước | File | Việc |
|---|---|---|
| 1 | `model/NumberArray.java` | `private int[] valueArray` + constructor rỗng + constructor đủ + get/set + `getSize` · `getValue` · `toString` |
| 2 | `repository/NumberRepository.java` | field `numberArray`; `saveNumberArray(valueArray)` · `getNumberArray()` — không tìm, không in |
| 3 | `dto/SearchRequestDTO.java`, `SearchResponseDTO.java` | JavaBean: constructor rỗng + get/set |
| 4 | `service/SearchService.java` | field `numberRepository`, `random`; `searchRandomArray`; **`searchByLinear`** (`private`): `for` + `if (== searchValue) return i`; `generateValueArray` |
| 5 | `view/SearchView.java` | field `responseDTO`; `setResponseDTO` · `display()` không tham số |
| 6 | `controller/SearchController.java` | `new SearchService()`, `new SearchView()`; `searchArray(requestDTO)` |
| 7 | `constants/Message.java`, `Constants.java` | câu chữ + giới hạn + `NOT_FOUND` |
| 8 | `utils/Validation.java` | `getInt` · `getSize` |
| 9 | `main/Main.java` | `public final class Main` + `private Main() { }`; `inputSize`, `inputSearchValue` + gọi controller **1 lần** |

**Bẫy hay gặp:**

1. Viết `return NOT_FOUND` **bên trong** `for` (nhánh `else`) → chỉ so phần tử đầu rồi báo "không có".
2. Không `return` ngay khi gặp → trả vị trí **cuối cùng** thay vì đầu tiên.
3. `i <= numberArray.getSize()` → `ArrayIndexOutOfBoundsException` ở vòng cuối.

---

## 5. Test trước khi gọi thầy

| # | Gõ | Phải thấy |
|---|---|---|
| 1 | size `abc` / *(trống)* | `You must input a number.` rồi hỏi lại `Enter number of array:` |
| 2 | size `-5` / `0` / `1001` | `Number must be between 1 and 1000.` |
| 3 | search `x` / `3.5` / *(trống)* / `99999999999` | `You must input a number.` rồi hỏi lại `Enter search value:` |
| 4 | `10` rồi `5` | `The array: [...]` 10 số trong 0–9; có 5 → `Found 5 at index: k` với k là vị trí **đầu tiên** của 5 |
| 5 | `1` rồi `0` | `The array: [0]` · `Found 0 at index: 0` |
| 6 | `10` rồi `10` / `-1` | `10 is not in the array.` / `-1 is not in the array.` |
| 7 | `1000` rồi `500` | 1000 số, dòng kết quả đúng |

---

## 6. Debug

| Việc | Cách làm |
|---|---|
| Breakpoint | dòng `if (numberArray.getValue(i) == searchValue)` trong `SearchService.searchByLinear` |
| Chạy | **Ctrl+F5**, nhập `10` rồi `5` |
| Quan sát | **Variables**: `i`, `searchValue`; mở `numberArray` → `valueArray` |
| Bước | **F8** mỗi vòng, `i` tăng dần; gặp số bằng thì thấy `return i`. Ở `searchRandomArray` bấm **F7** vào `searchByLinear(...)` để vào thuật toán |
| Xem repository | breakpoint ở `numberArray = numberRepository.getNumberArray();` → mở `numberRepository` ▸ `numberArray` ▸ `valueArray`: đúng mảng vừa sinh |
| Chứng minh "không có" | nhập `10`: `i` chạy đủ 0…9 rồi ra khỏi `for`, tới `return Constants.NOT_FOUND` |

---

## 7. Câu hỏi thầy hay hỏi

### Thuật toán

| Câu hỏi | Trả lời mẫu |
|---|---|
| Số trùng thì trả index nào? | **Đầu tiên** — đi từ trái, gặp là dừng. |
| Tốt nhất / xấu nhất? | 1 phép so sánh (ở đầu) / n phép (không có hoặc ở cuối) → `O(n)`. |
| Khác binary search? | Linear không cần sắp, `O(n)`; binary cần sắp, `O(log n)`. Tìm **một lần** trên mảng chưa sắp thì linear rẻ hơn (sắp đã tốn `O(n log n)`). |
| Muốn in **mọi** vị trí? | Đổi `searchByLinear` trả danh sách: `ArrayList<Integer> indexList` (mục 8). |

### OOP / Java

| Câu hỏi | Trả lời mẫu |
|---|---|
| 4 tính chất OOP ở đâu? | **Đóng gói**: `valueArray` `private` trong `NumberArray`, đọc qua `getValue`; `numberArray` `private` trong `NumberRepository`. **Kế thừa**: mọi lớp ngầm `extends Object`; `NumberArray` ghi đè `toString()`. **Đa hình**: `toString()` có `@Override`. **Trừu tượng**: `Main` chỉ gọi `controller.searchArray(requestDTO)` — không biết thuật toán tìm nào chạy bên trong. |
| `searchByLinear` trả `int` vì sao? | Index là vị trí; `-1` (`Constants.NOT_FOUND`) nghĩa "không có" vì index thật không bao giờ âm. |
| Sao đổi tên `linearSearch` → `searchByLinear`? | Tờ checklist 1.4: *"Tên method bắt đầu bằng động từ"*. `linearSearch` mở đầu bằng tính từ; `searchByLinear` = "tìm theo cách tuần tự" — cùng kiểu `sortByBubble` của P0001. |
| `generateValueArray` sao `private`? | Chỉ `SearchService` dùng. |
| `searchArray`, `searchRandomArray`, `display` sao `public`? | Lớp khác gọi (main → controller → service/view). |
| Sao `Validation.getInt` static? Bỏ đi thì sao? | Không dùng dữ liệu đối tượng. Bỏ `static` → `Validation.getInt(...)` lỗi biên dịch; phải bỏ `private` constructor, `new Validation()` trong `Main`. |
| `inputSize` trong `Main` sao `private static`? | `main` static chỉ gọi thẳng được hàm static; `private` vì chỉ `Main` dùng. Guide: static được cho **hàm** ở main, cấm cho **biến**. |
| Sao `NumberArray` có constructor rỗng? | **MVC kiểu JSP**: model là JavaBean (constructor rỗng `public` + get/set). |
| `ArrayList` hay `List`? | Bài này dùng `int[]` (kích thước cố định). Khi cần danh sách em khai **`ArrayList<Integer>`**: `List` là interface (hợp đồng), `ArrayList` là lớp cài bằng **mảng động** — thầy muốn thấy em biết mình dùng lớp nào. |
| Sao tên mảng đều đuôi `Array`? | Tờ checklist 1.5: *"tên biến kiểu Array kết thúc bằng Array"* → `valueArray` (`int[]`), `numberArray`. Riêng `String[] args` của `main` giữ đúng chữ ký Java. |
| Sao `Main` là `final` và có `private Main() { }`? | Tờ checklist 3.4: *"Class chỉ có static method thì phải có private constructor, và khai báo class là final"*. |
| View nhận dữ liệu thế nào? | Qua **thuộc tính**: controller gọi `searchView.setResponseDTO(responseDTO)` rồi `searchView.display()` — `display()` **không tham số**, gọi **1 lần** cho cả luồng (tờ checklist 1.1). |
| Validate ở đâu? | Ở `Main` qua `utils/Validation` (`getSize`, `getInt`): sai thì `Validation` ném `Exception(Message…)`, `Main` bắt và in `e.getMessage()` rồi hỏi lại. Controller/service chỉ nhận số đã hợp lệ trong `SearchRequestDTO`. |

---

## 8. Thầy đổi yêu cầu tại chỗ

| Thầy bảo | Sửa | Không đụng |
|---|---|---|
| Đổi sang **binary search** | `SearchService`: sắp mảng (`Arrays.sort`) rồi viết `searchByBinary` thay `searchByLinear` + đổi nhãn | main, model, repository |
| Trả vị trí **cuối cùng** | `searchByLinear`: vòng `for` từ `size - 1` về 0 | controller, view, main |
| In **mọi** vị trí | `searchByLinear` trả `ArrayList<Integer> indexList`; `SearchResponseDTO` đổi field `index` → chuỗi danh sách; `SearchView` in | main |
| In số **phép so sánh** | `searchByLinear` đếm → field mới trong `SearchResponseDTO` → view in thêm dòng | main |

---

## 9. Chỗ khác với đề / lời giải cũ

| Chỗ | Đề / bản cũ | Bài này | Lý do |
|---|---|---|---|
| Prompt | Bản cũ: tiêu đề `===== Linear Search Program =====`, `Please input the number of array: `, `Please input the search number: ` | `Enter number of array:` · `Enter search value:` (xuống dòng) | **đúng ảnh đề** |
| Thứ tự | Bản cũ in mảng **trước** khi hỏi giá trị | hỏi cả hai rồi mới in | đúng ảnh đề |
| Nhãn / kết quả | Bản cũ `Array: `, `Number 7 found at index 3.` | `The array: `, `Found 5 at index: 2` | đúng ảnh đề |
| Không tìm thấy | Đề **không có** màn hình | `7 is not in the array.` | cùng câu với P0006; đề im lặng |
| Dòng thêm | Bản cũ in `It appears 3 times, at indexes [...]` | không in | ảnh đề chỉ có 2 dòng kết quả |
| Khoảng số | Bản cũ `[0, 20)` cố định | `[0, n)` | "in number range input"; ảnh n = 10 → 2..9 |
| Kiến trúc | `bo/ui/utils`, Scanner trong `Validator` | MVC theo Guide | luật thầy |
| Repository | Bản trước: *"không lưu gì, không CRUD → không repository"* | có `NumberRepository` giữ mảng | tờ checklist 1.1: *"Bắt buộc phải có repository"* |
| Tên | `linearSearch`, `generateArray`, `values`, `setResponse`, `class Main` | `searchByLinear`, `generateValueArray`, `valueArray`, `setResponseDTO`, `final class Main` + `private Main()` | tờ checklist 1.4 (động từ), 1.5 (đuôi `Array`), 3.4 |
| Nhãn mảng | `LABEL_ARRAY + mảng` (nối `+`) | `String.format(Message.THE_ARRAY, …)` với `"The array: %s"` | tờ checklist 3.8 (không cộng chuỗi); chữ in ra y hệt |

---

## 10. Tờ checklist 25 mục — bài này đạt thế nào

| Mục | Chỗ trong code |
|---|---|
| 1.1 MVC + repository | `repository/NumberRepository` giữ model `NumberArray`; `SearchService` cất/đọc mảng qua repository; controller chỉ import DTO/service/view; `SearchView` nhận `responseDTO` qua `setResponseDTO`, `display()` gọi **1 lần**; mọi nhập + validate ở `Main` |
| 1.4 method = động từ | `searchByLinear`, `searchRandomArray`, `generateValueArray`, `saveNumberArray`, `inputSize` |
| 1.5 tên biến | `valueArray` (`int[]`), `numberArray`, `requestDTO`/`responseDTO`; không có `ID` |
| 2.6 + 3.7 khai báo đầu block, có khởi tạo | `Main.inputSize`/`inputSearchValue`: `String line = "";` ở đầu, trong `while` chỉ `line = sc.nextLine();`; `searchRandomArray`: `responseDTO`, `numberArray = null` ở đầu |
| 2.8 dòng trống | trước mọi comment (kể cả comment field trong `Constants`, `Message`, DTO), sau vùng khai báo, sau `}` của `for` trước `return` |
| 3.3 ngoặc | `Validation.getSize`: `if ((size < Constants.MIN_SIZE) \|\| (size > Constants.MAX_SIZE))` |
| 3.4 | `public final class Main` + `private Main() { }`; `Validation`, `Constants`, `Message` cũng `final` + ctor private |
| 3.8 | không cộng chuỗi: `String.format(Message.THE_ARRAY, …)` |

Kiểm lại: `python3 _tools/verify.py J1SP0010` · `python3 _tools/lint.py HE176322_J1SP0010_*` · `checklist_audit.py` → 0 `VI_PHAM`.
`RUI_RO` còn lại chỉ là `String[] args` và tham số setter/constructor trùng tên field (`this.size = size`) — kiểu IDE sinh, được chấp nhận.
