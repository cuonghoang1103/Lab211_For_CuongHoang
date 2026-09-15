# J1.S.P0006 — Binary Search

> Bài thuật toán **vẫn phải làm MVC** — thầy: *"Các bài liên quan thuật toán như fibo, sắp xếp,…
> cũng phải làm MVC, không OOP/MVC → không review."* Bài này là **anh em của P0001** (cùng model
> `NumberArray`, cùng mảng ngẫu nhiên `[0, n)`), chỉ đổi Strategy sắp xếp thành **Strategy tìm kiếm**.
> Làm xong bài này thì P0010 (Linear Search) chỉ khác đúng 1 lớp.

| | |
|---|---|
| Loại / LOC | Short Assignment · 70 LOC · 1 slot |
| Project | `HE176322_J1SP0006_BinarySearch` |
| Chạy | NetBeans: **File ▸ Open Project** → **F6** |
| Lớp chạy | `main.Main` |
| Kiểm tự động | `python3 _tools/verify.py J1SP0006` → 7 kịch bản × 2 locale |

---

## 1. Đề bài nói gì

- Nhập **số phần tử** của mảng (số nguyên dương) → sinh mảng **số ngẫu nhiên "in number range input"**.
- Nhập **giá trị cần tìm**.
- **Sắp xếp** mảng, in mảng đã sắp, rồi in **vị trí (index)** của giá trị cần tìm — tìm bằng **binary search**.

Màn hình đề (ảnh, chép đúng chữ):

```
Enter number of array:
10
Enter search value:
4
Sorted array: [1, 1, 1, 1, 3, 4, 6, 8, 9, 9]
Found 4 at index: 5
```

**Đề bắt buộc:**

| Thứ | Đề viết | Bài này đặt ở |
|---|---|---|
| Sắp xếp trước khi tìm | Function details: *"Sort array"* | `SearchService.sortArray` (private) |
| Thuật toán binary search | Guidelines: *"get the middle element … go to the step 1 for the part … before/after middle element"* | `service/BinarySearchStrategy.search` |
| Điểm dừng | *"when searched element is found"* · *"when subarray has no elements"* | `return middle` · vòng `while (low <= high)` hết → `NOT_FOUND` |
| Câu chữ màn hình | `Enter number of array:` · `Enter search value:` · `Sorted array: ` · `Found 4 at index: 5` | `constants/Message` |

---

## 2. Kiến thức cần biết

### 2.1 Binary search — ý tưởng

> Mảng **đã sắp**. Nhìn phần tử **giữa**: bằng → xong. Giá trị cần tìm **nhỏ hơn** → chỉ còn nửa
> **trái** (trước giữa) có thể chứa nó. **Lớn hơn** → nửa **phải**. Mỗi bước **bỏ đi một nửa**. Khi phần
> còn lại **không còn phần tử** (`low > high`) → không có.

Code dùng 3 biến: `low` (đầu phần đang xét), `high` (cuối), `middle = low + (high - low) / 2`.

### 2.2 Chạy tay ví dụ 1 của đề — tìm `6` trong `{-1, 5, 6, 18, 19, 25, 46, 78, 102, 114}`

Index: `-1`→0 · `5`→1 · `6`→2 · `18`→3 · `19`→4 · `25`→5 · `46`→6 · `78`→7 · `102`→8 · `114`→9

| Bước | low | high | middle | a[middle] | So sánh | Làm gì |
|---|---|---|---|---|---|---|
| 1 | 0 | 9 | 0 + 9/2 = **4** | 19 | 6 < 19 | giữ nửa **trước**: `high = 3` |
| 2 | 0 | 3 | 0 + 3/2 = **1** | 5 | 6 > 5 | giữ nửa **sau**: `low = 2` |
| 3 | 2 | 3 | 2 + 1/2 = **2** | 6 | 6 == 6 | **tìm thấy → trả 2** |

Khớp đúng 3 bước của đề (*"middle element is 19 > 6"*, *"5 < 6"*, *"6 == 6"*).

### 2.3 Chạy tay ví dụ 2 của đề — tìm `103` (không có trong mảng)

| Bước | low | high | middle | a[middle] | So sánh | Làm gì |
|---|---|---|---|---|---|---|
| 1 | 0 | 9 | **4** | 19 | 103 > 19 | `low = 5` |
| 2 | 5 | 9 | 5 + 4/2 = **7** | 78 | 103 > 78 | `low = 8` |
| 3 | 8 | 9 | 8 + 1/2 = **8** | 102 | 103 > 102 | `low = 9` |
| 4 | 9 | 9 | **9** | 114 | 103 < 114 | `high = 8` |
| 5 | 9 | 8 | — | — | `low > high` | **phần còn lại rỗng → trả `NOT_FOUND` (-1)** |

Khớp đúng 5 bước của đề (*"Step 5 (searched value is absent)"*).

### 2.4 Chạy tay màn hình của đề — tìm `4` trong `[1, 1, 1, 1, 3, 4, 6, 8, 9, 9]`

| Bước | low | high | middle | a[middle] | Làm gì |
|---|---|---|---|---|---|
| 1 | 0 | 9 | 4 | 3 | 4 > 3 → `low = 5` |
| 2 | 5 | 9 | 7 | 8 | 4 < 8 → `high = 6` |
| 3 | 5 | 6 | 5 | 4 | **bằng → index 5** ✓ `Found 4 at index: 5` |

### 2.5 Độ phức tạp

| | Binary search | Linear search (P0010) |
|---|---|---|
| Xấu nhất | khoảng `log2(n) + 1` bước → **O(log n)** | `n` bước → **O(n)** |
| n = 1000 | **10** bước | 1000 bước |
| n = 1 000 000 | **20** bước (đề: *"log2(1 000 000) ≈ 20"*) | 1 000 000 bước |
| Điều kiện | mảng **phải đã sắp** | mảng bất kỳ |

Nói với thầy: *"n gấp đôi thì binary search chỉ thêm **1** bước."* Nhưng sắp xếp tốn `O(n log n)` — sắp chỉ để
tìm **một lần** thì đắt hơn tìm tuần tự; binary search có lời khi **tìm nhiều lần** trên cùng một mảng.

### 2.6 Java dùng trong bài

| API | Dùng làm gì |
|---|---|
| `new Random().nextInt(n)` | số ngẫu nhiên trong `[0, n)` — đúng "in number range input" (như P0001) |
| `Arrays.sort(int[])` | sắp tăng dần **ngay trên mảng đó** (dual-pivot quicksort, `O(n log n)`) |
| `Arrays.toString(arr)` | ra chữ `[1, 1, 3]` đúng định dạng màn hình đề |
| `Integer.parseInt(s)` | đổi chuỗi sang số; chuỗi sai (kể cả `3.5`, `99999999999`) → `NumberFormatException` |

---

## 3. Thiết kế

```
HE176322_J1SP0006_BinarySearch/src/
├── model/      NumberArray            mảng số (JavaBean) + getSize/getValue/toString
├── dto/        SearchRequestDTO       size + searchValue       (main ──► controller)
│               SearchResponseDTO      mảng đã sắp + value + index (controller ──► view)
├── service/    SearchStrategy         «interface» hợp đồng của mọi thuật toán tìm kiếm
│               BinarySearchStrategy   binary search ← thuật toán của đề ở ĐÂY
│               SearchService          sinh mảng + sắp + gọi strategy (Context)
├── controller/ SearchController       cắm BinarySearchStrategy vào service; service ──► view
├── view/       SearchView             in "Sorted array" + "Found … / … is not in the array."
├── constants/  Message.java           câu chữ màn hình
│               Constants.java         MIN_SIZE = 1, MAX_SIZE = 1000, NOT_FOUND = -1
├── utils/      Validation             getInt(chuỗi), getSize(chuỗi) → int hoặc ném lỗi
└── main/       Main                   Scanner + gọi controller 1 lần
```

| Câu hỏi thiết kế | Trả lời |
|---|---|
| Sao có `service` mà không có `repository`? | Guide: repository = **giữ dữ liệu + CRUD**. Bài này không lưu gì. Tìm kiếm là **"tính toán nghiệp vụ"** → `service`. |
| Sao `NumberArray` không tự tìm/tự sắp? | Model chỉ mô tả **mảng** (đếm, lấy phần tử). Cách tìm là **nghiệp vụ** — tách ra để đổi thuật toán không phải sửa model. |
| Sao 2 giá trị gõ vào lại gói chung 1 DTO? | Thầy: *"không truyền 3 tham số 1 hàm"*; Guide: *"truyền data vào controller thông qua DTO"* → `searchArray(dto)` chỉ 1 tham số. |
| Sao view lại có `if`? | Chọn **câu nào để in** (thấy / không thấy) là việc hiển thị; view không tính gì, chỉ đọc `index` trong DTO. |

**Luồng chạy:**

```
Main: đọc size (hỏi lại khi sai), đọc searchValue ──► SearchRequestDTO ──► controller.searchArray(dto)
   controller ──► service.searchRandomArray(dto)
                     ├─ generateArray(size)          → NumberArray ngẫu nhiên [0, size)
                     ├─ sortArray(array)             → Arrays.sort (đề: "Sort array")
                     ├─ searchStrategy.search(...)   ← BinarySearchStrategy chạy ở đây
                     └─ response: sortedArray, searchValue, index
   controller ──► view.setResponse(response) ──► view.display()
```

### 3.1 Design Pattern — **Strategy**

Trình bày đúng 4 yếu tố của một pattern (slide Design Pattern, *"Elements of a Design Pattern"*):

| Yếu tố | Trong bài này |
|---|---|
| **Name** | Strategy (nhóm Behavioral) |
| **Problem** | Tìm kiếm có **nhiều thuật toán** (tuần tự, nhị phân lặp, nhị phân đệ quy, tìm vị trí đầu tiên…) và thầy hay bảo *"viết bằng đệ quy xem"* / *"đổi sang linear search"*. Nhét thẳng vào service thì mỗi lần đổi phải **mở service ra sửa**. |
| **Solution** | `SearchStrategy` = **Strategy** (interface `search`). `BinarySearchStrategy` = **ConcreteStrategy**. `SearchService` = **Context**: giữ `SearchStrategy` nhận qua constructor, gọi `searchStrategy.search(array, value)` mà không biết là thuật toán gì. `SearchController` là nơi **chọn**: `new SearchService(new BinarySearchStrategy())`. |
| **Consequences** | ✅ Thêm thuật toán = **thêm 1 class**, service/view/main đứng yên (**O**pen/Closed); service phụ thuộc interface (**D**ependency Inversion). ❌ Thêm 2 file so với viết thẳng; và strategy nhị phân **ngầm đòi mảng đã sắp** — Context (service) phải nhớ sắp trước. |

**Thầy bảo "viết binary search bằng đệ quy" — làm trong 3 phút:**
1. Tạo `service/RecursiveBinarySearchStrategy.java` `implements SearchStrategy`; `search()` gọi một hàm
   `private` đệ quy nhận `low`/`high` (gói vào 1 đối tượng hoặc giữ làm field để không quá 2 tham số).
2. Sửa **đúng 1 dòng** trong `SearchController`: `new SearchService(new RecursiveBinarySearchStrategy())`.

### 3.2 SOLID trong bài

| Nguyên lý | Ở đâu |
|---|---|
| **S** | `NumberArray` giữ số · `BinarySearchStrategy` tìm · `SearchService` điều phối · `SearchView` in · `Validation` kiểm |
| **O** | thêm thuật toán không sửa `SearchService` (xem 3.1) |
| **L** | mọi `XxxSearchStrategy` thay được cho nhau chỗ `SearchStrategy` — trả index hoặc `NOT_FOUND` như nhau |
| **I** | `SearchStrategy` chỉ có đúng 1 hàm `search` |
| **D** | `SearchService` phụ thuộc `SearchStrategy` (trừu tượng), nhận qua constructor |

---

## 4. Code từng bước

> Thầy: *"code cái **model trước rồi đến data**"*.

| Bước | File | Việc |
|---|---|---|
| 1 | `model/NumberArray.java` | field `private int[] values` + constructor rỗng + constructor đủ + get/set + `getSize` · `getValue` · `toString` |
| 2 | `dto/SearchRequestDTO.java`, `SearchResponseDTO.java` | JavaBean: constructor rỗng + get/set |
| 3 | `service/SearchStrategy.java` | interface 1 hàm `int search(NumberArray array, int value)` |
| 4 | `service/BinarySearchStrategy.java` | **thuật toán của đề**: `low`, `high`, `while (low <= high)`, `middle` |
| 5 | `service/SearchService.java` | field `searchStrategy` + `random`; `searchRandomArray`; `generateArray`, `sortArray` (private) |
| 6 | `view/SearchView.java` | `setResponse` · `display` |
| 7 | `controller/SearchController.java` | constructor cắm strategy; `searchArray(dto)` |
| 8 | `constants/Message.java`, `Constants.java` | câu chữ + giới hạn + `NOT_FOUND` (gõ dần khi bước trên cần) |
| 9 | `utils/Validation.java` | `getInt(String)` · `getSize(String)` — **tách 2 lỗi** |
| 10 | `main/Main.java` | `inputSize`, `inputSearchValue` (vòng hỏi lại) + gọi `controller.searchArray` **1 lần** |

**Bẫy hay gặp:**

1. **Quên sắp xếp** trước khi tìm → vẫn chạy, vẫn in số, nhưng **báo "không có" cho số có trong mảng**. Lỗi câm, rất khó thấy.
2. `while (low < high)` (thiếu `=`) → mảng 1 phần tử, hoặc phần còn đúng 1 phần tử, bị bỏ qua → báo sai "không có".
3. `high = middle` thay vì `middle - 1` → khi `low == high` mà khác giá trị, **lặp vô hạn**.
4. `(low + high) / 2` — đúng ở bài này, nhưng khi `low + high` vượt `Integer.MAX_VALUE` sẽ **tràn số âm**; viết `low + (high - low) / 2`.
5. In mảng **trước** khi sắp → dòng `Sorted array` không tăng dần. Service sắp **rồi mới** `toString()`.

---

## 5. Test trước khi gọi thầy

| # | Gõ | Phải thấy |
|---|---|---|
| 1 | size `abc` | `You must input a number.` rồi hỏi lại `Enter number of array:` |
| 2 | size *(Enter trống)* | `You must input a number.` |
| 3 | size `-5` / `0` / `1001` | `Number must be between 1 and 1000.` |
| 4 | search `x` / `3.5` / *(trống)* / `99999999999` | `You must input a number.` rồi hỏi lại `Enter search value:` |
| 5 | `10` rồi `4` | `Sorted array: [...]` **tăng dần**, 10 số trong 0–9; nếu có 4 → `Found 4 at index: k` với `a[k] = 4`, không có → `4 is not in the array.` |
| 6 | `1` rồi `0` | `Sorted array: [0]` · `Found 0 at index: 0` |
| 7 | `10` rồi `10` | `10 is not in the array.` (mọi số < 10) |
| 8 | `10` rồi `-1` | `-1 is not in the array.` |
| 9 | `1000` rồi `500` | 1000 số tăng dần, dòng kết quả đúng |

---

## 6. Debug

| Việc | Cách làm |
|---|---|
| Breakpoint | dòng `int middleValue = array.getValue(middle);` trong `BinarySearchStrategy.search` |
| Chạy | **Ctrl+F5**, nhập `10` rồi một số có trong dòng Sorted |
| Quan sát | tab **Variables**: `low`, `high`, `middle`, `middleValue`, `value` |
| Bước | **F5** mỗi vòng — chỉ cho thầy `low`/`high` **khép dần** đúng như bảng 2.2; ở `SearchService` bấm **F7** vào `searchStrategy.search(...)` để thấy nó nhảy vào `BinarySearchStrategy` (đa hình qua interface) |
| Chứng minh "không có" | nhập giá trị `10`: thấy `low` vượt `high`, vòng `while` thoát, `return Constants.NOT_FOUND` |

---

## 7. Câu hỏi thầy hay hỏi

### Thuật toán

| Câu hỏi | Trả lời mẫu |
|---|---|
| Sao phải sắp trước? | Bước "giữ nửa trái/phải" chỉ đúng khi mọi số bên trái ≤ giữa ≤ mọi số bên phải — tức mảng **đã sắp**. |
| Khi nào dừng? | Hai chỗ, đúng đề: **bằng** (`return middle`) và **phần còn lại rỗng** (`low > high` → `NOT_FOUND`). |
| Mảng có số trùng thì index là cái nào? | Cái mà `middle` **chạm trúng đầu tiên** — không nhất thiết là vị trí đầu. VD `[1, 1, 1, 1, 3, 4, 6, 8, 9, 9]` tìm `1`: bước 1 `middle = 4` (3) → `high = 3`; bước 2 `middle = 1` → trả **1**, dù `1` có ở index 0. Đề chỉ yêu cầu "index of search number", nên đúng. Muốn vị trí **đầu tiên** → mục 8. |
| Độ phức tạp? | `O(log n)` — n = 1000 tối đa 10 bước; n = 1 triệu tối đa 20 bước. |
| Sao `low + (high - low) / 2`? | Bằng `(low + high) / 2` về toán, nhưng **không tràn số** khi `low + high` quá `Integer.MAX_VALUE`. |
| Lặp và đệ quy khác gì? | Đề cho cả hai. Lặp không tốn ngăn xếp; đệ quy mỗi lần gọi đẩy 1 khung lên stack (sâu tối đa ~log n nên vẫn an toàn). |
| Sao không tự viết sort mà dùng `Arrays.sort`? | Chủ đề bài là **tìm kiếm**; sắp chỉ là điều kiện. Các thuật toán sắp là bài P0001–P0005. Thầy muốn tự viết → cắm `SortStrategy` của P0001 vào (mục 8). |

### OOP / Java

| Câu hỏi | Trả lời mẫu |
|---|---|
| 4 tính chất OOP ở đâu? | **Đóng gói**: `values` `private` trong `NumberArray`, đọc qua `getValue`. **Kế thừa**: `BinarySearchStrategy implements SearchStrategy`; mọi lớp `extends Object` và em ghi đè `toString()`. **Đa hình**: `SearchService` gọi `searchStrategy.search(...)` — biến kiểu interface, chạy bản của `BinarySearchStrategy`. **Trừu tượng**: interface `SearchStrategy` chỉ nói *"tìm được"*, không nói cách tìm. |
| `search` trả `int` vì sao? | Index là **vị trí** — số nguyên; và cần một giá trị "không có" → `-1` (`Constants.NOT_FOUND`), vì không index thật nào âm. |
| Sao không trả `boolean`? | Đề đòi in **index**, `boolean` chỉ nói có/không. |
| `generateArray`, `sortArray` sao `private`? | Chỉ `SearchService` dùng; không phải "hợp đồng" của lớp. |
| `searchRandomArray`, `searchArray`, `display` sao `public`? | Lớp khác gọi: controller gọi service, main gọi controller, controller gọi view. |
| Field `searchStrategy`, `random` sao `private`? | Không lớp nào được đổi thuật toán hay nguồn số ngẫu nhiên sau khi service đã tạo. |
| Sao `Validation.getInt` static? Bỏ đi thì sao? | Không dùng dữ liệu đối tượng nào — cùng chuỗi luôn ra cùng số. Bỏ `static` thì `Validation.getInt(...)` báo lỗi biên dịch; phải bỏ `private` constructor, tạo `Validation v = new Validation();` trong `Main` rồi gọi `v.getInt(...)`. |
| Hằng trong `Constants`/`Message` sao `public static final`? | `public` vì mọi tầng đọc; `static` vì là của **lớp**, không cần đối tượng; `final` vì không ai được đổi. |
| Hàm `inputSize` trong `Main` sao `private static`? | `main` là static nên chỉ gọi thẳng được hàm static; `private` vì chỉ `Main` dùng. Guide cho phép static với **hàm** ở main, cấm với **biến**. |
| Sao `NumberArray` có constructor rỗng? | Thầy dạy **MVC kiểu JSP**: model là **JavaBean** — field `private`, **constructor rỗng `public`**, getter/setter. |
| Mảng `int[]` chứ không `ArrayList`? | Kích thước biết trước và không đổi; `Arrays.sort`/`Arrays.toString` làm việc thẳng trên `int[]`, không phải đóng hộp `Integer`. (Nếu dùng danh sách, em khai `ArrayList<Integer>` — kiểu cụ thể: `List` chỉ là interface/hợp đồng, `ArrayList` là lớp cài bằng mảng động.) |

---

## 8. Thầy đổi yêu cầu tại chỗ

| Thầy bảo | Sửa | Không đụng |
|---|---|---|
| Viết binary search **đệ quy** | thêm `RecursiveBinarySearchStrategy` + 1 dòng `SearchController` | service, main, view, model |
| Đổi sang **linear search** | thêm `LinearSearchStrategy` + 1 dòng `SearchController` (có thể bỏ `sortArray`) | main, view |
| Tìm **vị trí đầu tiên** khi trùng | `BinarySearchStrategy`: khi bằng thì nhớ `middle` rồi `high = middle - 1`, đi tiếp | mọi file khác |
| **Tự viết** sắp xếp | chép `SortStrategy` + `BubbleSortStrategy` của P0001 vào `service/`; `SearchService` nhận thêm `SortStrategy` qua constructor, `sortArray` gọi `sortStrategy.sort(array)` | main, view |
| In thêm **số bước** đã so sánh | strategy đếm bước → field mới trong `SearchResponseDTO` → `SearchView` in thêm dòng | main |
| Số ngẫu nhiên trong `[1, n]` | `generateArray`: `random.nextInt(size) + 1` | mọi file khác |

---

## 9. Chỗ khác với đề / lời giải cũ

| Chỗ | Đề / bản cũ | Bài này | Lý do |
|---|---|---|---|
| Không tìm thấy | Đề **không có** màn hình | `4 is not in the array.` | câu của lời giải cũ; đề im lặng nên giữ câu đó |
| Dòng thêm | Bản cũ in thêm `… appears 2 time(s) …` và `Binary search used … comparison(s)…` | **không** in | màn hình đề chỉ có 2 dòng kết quả |
| Khoảng số | Bản cũ `[1, n]` | `[0, n)` | "in number range input" — giống P0001; ảnh đề n = 10 cho số 1–9, không mâu thuẫn |
| Kiến trúc | `bo/ui/utils`, Scanner trong `Validator` | MVC theo Guide, Scanner **chỉ ở `main`** | luật thầy |
| Thuật toán | hàm `search` trong `BinarySearchManager` | **Strategy**: `SearchStrategy` + `BinarySearchStrategy` | thầy đánh giá cao Design Pattern |
| Giới hạn size | bản cũ 1–100000 | 1–1000, câu `Number must be between 1 and 1000.` | đồng bộ P0001; tránh in cả trang số |
