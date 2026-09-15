# J1.S.P0010 — Linear Search

> Bài thuật toán **vẫn phải làm MVC** — thầy: *"Các bài liên quan thuật toán như fibo, sắp xếp,…
> cũng phải làm MVC, không OOP/MVC → không review."* Bài này là **anh em của P0001 và P0006**: cùng
> model `NumberArray`, cùng mảng ngẫu nhiên `[0, n)`, như P0006 —
> chỉ khác **thuật toán** (hàm `linearSearch` trong `SearchService`) và **không sắp xếp**.

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
| Thuật toán | *"checking every one of its elements, one at a time and in sequence, until the desired one is found"* | `SearchService.linearSearch` |
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
├── model/      NumberArray            mảng số (JavaBean) + getSize/getValue/toString
├── dto/        SearchRequestDTO       size + searchValue            (main ──► controller)
│               SearchResponseDTO      mảng + value + index          (controller ──► view)
├── service/    SearchService          sinh mảng + linearSearch ← thuật toán của đề ở ĐÂY
├── controller/ SearchController       service ──► view
├── view/       SearchView             in "The array" + "Found … / … is not in the array."
├── constants/  Message.java           câu chữ màn hình
│               Constants.java         MIN_SIZE = 1, MAX_SIZE = 1000, NOT_FOUND = -1
├── utils/      Validation             getInt(chuỗi), getSize(chuỗi)
└── main/       Main                   Scanner + gọi controller 1 lần
```

| Câu hỏi thiết kế | Trả lời |
|---|---|
| Sao có `service` mà không `repository`? | Không lưu gì, không CRUD; tìm kiếm là **tính toán nghiệp vụ** → `service` (Guide). |
| Sao không sắp như P0006? | Đề P0010 in *"the array"* (ảnh: không tăng dần) — linear search chạy trên mảng **bất kỳ**. |
| Sao 2 giá trị gõ vào gói 1 DTO? | Thầy: *"không truyền 3 tham số 1 hàm"*; Guide: data vào controller **qua DTO**. |

**Luồng chạy:**

```
Main: đọc size, đọc searchValue ──► SearchRequestDTO ──► controller.searchArray(dto)
   controller ──► service.searchRandomArray(dto)
                     ├─ generateArray(size)          → NumberArray ngẫu nhiên [0, size)
                     ├─ linearSearch(array, value)   ← thuật toán của đề chạy ở đây
                     └─ response: array, searchValue, index
   controller ──► view.setResponse(response) ──► view.display()
```

### 3.1 Design Pattern trong bài

| Pattern | Ở đâu |
|---|---|
| **MVC** — thầy gọi là "MVC JSP" | controller điều hướng (như Servlet) · view hiển thị (như trang JSP) · model là JavaBean |
| **Facade** | controller: `Main` chỉ gọi `controller.searchArray(dto)`, không biết service/model/view phía sau |

> Bài chỉ 50 LOC nên **không thêm lớp pattern GoF** — ghi chú slide SOLID của thầy cảnh báo *"trừu tượng hoá sớm … vi phạm YAGNI"*. Thuật toán nằm gọn trong **một hàm** `linearSearch` của `SearchService`.

**Thầy hỏi "dùng pattern gì cho dễ mở rộng?"** → trả lời đủ 4 yếu tố GoF (slide *"Elements of a Design Pattern"*):

| Yếu tố | Trả lời |
|---|---|
| **Name** | Strategy (nhóm Behavioral) |
| **Problem** | nhiều thuật toán tìm (linear, binary) cùng làm một việc |
| **Solution** | tách `interface SearchStrategy { int search(NumberArray array, int value); }`; mỗi cách làm là 1 lớp `implements` nó (`LinearSearchStrategy`, `BinarySearchStrategy`); `SearchService` nhận strategy qua constructor |
| **Consequences** | ➕ thêm cách làm = thêm 1 lớp, service đứng yên (**O**) · ➖ thêm 2 file — chỉ đáng khi có từ 2 cách trở lên (bài P0006 Binary search làm đúng như vậy) |

### 3.2 SOLID trong bài

| Nguyên lý | Ở đâu |
|---|---|
| **S** | `NumberArray` giữ số · `SearchService` sinh + tìm · `SearchView` in · `Validation` kiểm |
| **O** | đổi thuật toán chỉ sửa **một hàm** `linearSearch` |
| **L** | bài chưa có lớp con riêng — chỉ `extends Object` |
| **I** | không có interface — bài chưa cần |
| **D** | `Main` chỉ phụ thuộc controller + DTO |

---

## 4. Code từng bước

> Thầy: *"code cái **model trước rồi đến data**"*.

| Bước | File | Việc |
|---|---|---|
| 1 | `model/NumberArray.java` | `private int[] values` + constructor rỗng + constructor đủ + get/set + `getSize` · `getValue` · `toString` |
| 2 | `dto/SearchRequestDTO.java`, `SearchResponseDTO.java` | JavaBean: constructor rỗng + get/set |
| 3 | `service/SearchService.java` | field `random`; `searchRandomArray`; **`linearSearch`** (`private`): `for` + `if (== value) return i`; `generateArray` |
| 4 | `view/SearchView.java` | `setResponse` · `display` |
| 5 | `controller/SearchController.java` | `new SearchService()`; `searchArray(dto)` |
| 6 | `constants/Message.java`, `Constants.java` | câu chữ + giới hạn + `NOT_FOUND` |
| 7 | `utils/Validation.java` | `getInt` · `getSize` |
| 8 | `main/Main.java` | `inputSize`, `inputSearchValue` + gọi controller **1 lần** |

**Bẫy hay gặp:**

1. Viết `return NOT_FOUND` **bên trong** `for` (nhánh `else`) → chỉ so phần tử đầu rồi báo "không có".
2. Không `return` ngay khi gặp → trả vị trí **cuối cùng** thay vì đầu tiên.
3. `i <= array.getSize()` → `ArrayIndexOutOfBoundsException` ở vòng cuối.

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
| Breakpoint | dòng `if (array.getValue(i) == value)` trong `SearchService.linearSearch` |
| Chạy | **Ctrl+F5**, nhập `10` rồi `5` |
| Quan sát | **Variables**: `i`, `value`; mở `array` → `values` |
| Bước | **F8** mỗi vòng, `i` tăng dần; gặp số bằng thì thấy `return i`. Ở `searchRandomArray` bấm **F7** vào `linearSearch(...)` để vào thuật toán |
| Chứng minh "không có" | nhập `10`: `i` chạy đủ 0…9 rồi ra khỏi `for`, tới `return Constants.NOT_FOUND` |

---

## 7. Câu hỏi thầy hay hỏi

### Thuật toán

| Câu hỏi | Trả lời mẫu |
|---|---|
| Số trùng thì trả index nào? | **Đầu tiên** — đi từ trái, gặp là dừng. |
| Tốt nhất / xấu nhất? | 1 phép so sánh (ở đầu) / n phép (không có hoặc ở cuối) → `O(n)`. |
| Khác binary search? | Linear không cần sắp, `O(n)`; binary cần sắp, `O(log n)`. Tìm **một lần** trên mảng chưa sắp thì linear rẻ hơn (sắp đã tốn `O(n log n)`). |
| Muốn in **mọi** vị trí? | Đổi `linearSearch` trả danh sách: `ArrayList<Integer>` (mục 8). |

### OOP / Java

| Câu hỏi | Trả lời mẫu |
|---|---|
| 4 tính chất OOP ở đâu? | **Đóng gói**: `values` `private` trong `NumberArray`, đọc qua `getValue`. **Kế thừa**: mọi lớp ngầm `extends Object`; `NumberArray` ghi đè `toString()`. **Đa hình**: `toString()` có `@Override`. **Trừu tượng**: `Main` chỉ gọi `controller.searchArray(dto)` — không biết thuật toán tìm nào chạy bên trong. |
| `search` trả `int` vì sao? | Index là vị trí; `-1` (`Constants.NOT_FOUND`) nghĩa "không có" vì index thật không bao giờ âm. |
| `generateArray` sao `private`? | Chỉ `SearchService` dùng. |
| `searchArray`, `searchRandomArray`, `display` sao `public`? | Lớp khác gọi (main → controller → service/view). |
| Sao `Validation.getInt` static? Bỏ đi thì sao? | Không dùng dữ liệu đối tượng. Bỏ `static` → `Validation.getInt(...)` lỗi biên dịch; phải bỏ `private` constructor, `new Validation()` trong `Main`. |
| `inputSize` trong `Main` sao `private static`? | `main` static chỉ gọi thẳng được hàm static; `private` vì chỉ `Main` dùng. Guide: static được cho **hàm** ở main, cấm cho **biến**. |
| Sao `NumberArray` có constructor rỗng? | **MVC kiểu JSP**: model là JavaBean (constructor rỗng `public` + get/set). |
| `ArrayList` hay `List`? | Bài này dùng `int[]` (kích thước cố định). Khi cần danh sách em khai **`ArrayList<Integer>`**: `List` là interface (hợp đồng), `ArrayList` là lớp cài bằng **mảng động** — thầy muốn thấy em biết mình dùng lớp nào. |

---

## 8. Thầy đổi yêu cầu tại chỗ

| Thầy bảo | Sửa | Không đụng |
|---|---|---|
| Đổi sang **binary search** | `SearchService`: sắp mảng (`Arrays.sort`) rồi viết `binarySearch` thay `linearSearch` + đổi nhãn | main, model |
| Trả vị trí **cuối cùng** | `linearSearch`: vòng `for` từ `size - 1` về 0 | controller, view, main |
| In **mọi** vị trí | `linearSearch` trả `ArrayList<Integer>`; `SearchResponseDTO` đổi field `index` → chuỗi danh sách; `SearchView` in | main |
| In số **phép so sánh** | `linearSearch` đếm → field mới trong `SearchResponseDTO` → view in thêm dòng | main |

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
