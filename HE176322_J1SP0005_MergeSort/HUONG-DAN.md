# J1.S.P0005 — Merge Sort

> Bài anh em của **P0001 Bubble Sort** và **P0004 Quick Sort**: cùng khung MVC, cùng màn hình, cùng
> Strategy. Chỉ khác **một lớp**: `MergeSortStrategy` — bài **đệ quy chia để trị**. Thuật toán vẫn
> phải làm MVC — thầy: *"Các bài liên quan thuật toán như fibo, sắp xếp,… cũng phải làm MVC."* Bản
> 21/09/2026 đã sửa theo **tờ checklist giấy 25 mục** của thầy — xem mục 10 cuối bài.

| | |
|---|---|
| Loại / LOC | Short Assignment · 70 LOC · 1 slot |
| Project | `HE176322_J1SP0005_MergeSort` |
| Chạy | NetBeans: **File ▸ Open Project** → **F6** |
| Lớp chạy | `main.Main` |
| Kiểm tự động | `python3 _tools/verify.py J1SP0005` → 7 kịch bản × 2 locale |

---

## 1. Đề bài nói gì

- Nhập **số phần tử** của mảng (số nguyên dương).
- Sinh mảng **số ngẫu nhiên "in number range input"** — nhập `10` → các số từ `0` đến `9`.
- In mảng **trước** và **sau** khi sắp xếp bằng **merge sort**.

Màn hình đề (ảnh của đề, chép đúng chữ — giống hệt ảnh của P0001):

```
Enter number of array:
10
Unsorted array: [2, 6, 3, 6, 8, 6, 1, 2, 9, 8]
Sorted array: [1, 2, 2, 3, 6, 6, 6, 8, 8, 9]
```

**Đề bắt buộc:**

| Thứ | Đề viết | Bài này đặt ở |
|---|---|---|
| Prompt | `Enter number of array:` (ảnh) | `Message.INPUT_SIZE` |
| Hai dòng kết quả | `Unsorted array: [...]` · `Sorted array: [...]` | `Message.UNSORTED_ARRAY` / `SORTED_ARRAY` (`"…: %s"`), in ở `SortView.display()` |
| Số ngẫu nhiên | *"random integer in number range input"* | `SortService.generateValueArray` → `[0, n)` |
| Bước 1 | chia mảng tới khi **mỗi phần 1 phần tử** (1 phần tử coi là đã sắp) | `MergeSortStrategy.sortByMerge(left, right)` |
| Bước 2 | **trộn** lặp lại các phần đã sắp tới khi còn 1 danh sách | `MergeSortStrategy.merge(left, right)` |

Đề **không bắt** tên lớp hay tên hàm nào — mọi tên trong bài là của lời giải, đặt theo tờ checklist.

---

## 2. Kiến thức cần biết

### 2.1 Merge sort — ý tưởng (đúng lời đề: chia để trị)

> 1. **Chia**: cắt đôi đoạn ở giữa, rồi cắt tiếp mỗi nửa… tới khi mỗi mảnh chỉ còn **1 số**.
> 2. **Trộn**: ghép từng cặp mảnh **đã sắp** thành một mảnh sắp dài hơn, tới khi còn 1 mảnh.

**Trộn 2 dãy đã sắp** là việc chính: đặt 2 ngón tay ở đầu 2 dãy, mỗi lần lấy số **nhỏ hơn** trong
hai số đang chỉ, nhích ngón tay đó sang phải. Một dãy hết thì chép nốt dãy kia.

### 2.2 Chạy tay ví dụ của đề: `{38, 27, 43, 3, 9, 82, 10}`

`mid = (left + right) / 2` → nửa trái nhận số dư (7 số → 4 | 3), đúng như hình của đề.
Thứ tự dưới đây là **thứ tự máy chạy thật** (đệ quy xong nhánh trái mới sang nhánh phải).

| # | Lời gọi | Việc | Kết quả |
|---|---|---|---|
| 1 | `sortByMerge(0, 6)` | chia, `mid = 3` | `38 27 43 3` \| `9 82 10` |
| 2 | `sortByMerge(0, 3)` | chia, `mid = 1` | `38 27` \| `43 3` |
| 3 | `sortByMerge(0, 1)` | chia, `mid = 0` | `38` \| `27` (mỗi mảnh 1 số → dừng) |
| 4 | `merge(0, 1)` | trộn `38` + `27` | `27 38` |
| 5 | `sortByMerge(2, 3)` → `merge(2, 3)` | chia `43` \| `3`, trộn | `3 43` |
| 6 | `merge(0, 3)` | trộn `27 38` + `3 43` | `3 27 38 43` |
| 7 | `sortByMerge(4, 6)` | chia, `mid = 5` | `9 82` \| `10` |
| 8 | `sortByMerge(4, 5)` → `merge(4, 5)` | chia `9` \| `82`, trộn | `9 82` |
| 9 | `merge(4, 6)` | trộn `9 82` + `10` | `9 10 82` |
| 10 | `merge(0, 6)` | trộn `3 27 38 43` + `9 10 82` | `3 9 10 27 38 43 82` |

Khớp từng mảnh với hình cây của đề.

**Trộn bước 10 chi tiết** (`i` chỉ nửa trái, `j` chỉ nửa phải, `mergedArray` là mảng phụ):

| k | So `trái[i]` với `phải[j]` | Lấy | `mergedArray` |
|---|---|---|---|
| 0 | `3 ≤ 9` | 3 (trái) | `3` |
| 1 | `27 > 9` | 9 (phải) | `3 9` |
| 2 | `27 > 10` | 10 (phải) | `3 9 10` |
| 3 | `27 ≤ 82` | 27 (trái) | `3 9 10 27` |
| 4 | `38 ≤ 82` | 38 (trái) | `3 9 10 27 38` |
| 5 | `43 ≤ 82` | 43 (trái) | `3 9 10 27 38 43` |
| — | nửa trái **hết** | vòng `while (j <= right)` chép nốt 82 | `3 9 10 27 38 43 82` |

Cuối cùng vòng `for` chép `mergedArray` **đè lại** ô `0..6` của mảng.

### 2.3 Chi tiết thầy hay soi

| Chi tiết | Code | Vì sao |
|---|---|---|
| Điểm dừng đệ quy | `if (left >= right) { return; }` | đoạn 1 số (hoặc rỗng) đã sắp — đề: *"one element is considered as sorted"* |
| `mid` khai báo ở **đầu** hàm | `int mid = (left + right) / 2;` đứng trước `if (left >= right)` | tờ checklist 2.6 + 3.7: biến khai báo ở đầu block và khởi tạo luôn; tính `mid` khi đoạn chỉ 1 số là vô hại |
| Cần **mảng phụ** | `int[] mergedArray = new int[right - left + 1];` | ghi thẳng vào mảng sẽ **đè** số của nửa trái chưa đọc (đề: *"additional O(n) space"*) |
| So `<=` khi trộn | `if (workingArray.getValue(i) <= workingArray.getValue(j))` | số **bằng nhau** lấy bên **trái** trước → giữ thứ tự cũ → **ổn định** (đề: *"stable"*) |
| Ngoặc từng phép so sánh | `while ((i <= mid) && (j <= right))` | tờ checklist 3.3 |
| 2 vòng `while` chép nốt | `while (i <= mid)` · `while (j <= right)` | vòng chính dừng khi **một** nửa hết; nửa kia còn số phải chép — chỉ **một** trong hai vòng thực sự chạy |
| `merge` chỉ 2 tham số | `merge(int left, int right)` tính lại `mid = (left + right) / 2` | thầy: *"không được truyền 3 tham số 1 hàm"* (V4); cùng công thức nên cùng điểm cắt với `sortByMerge` |
| Mảng ở field | `workingArray = numberArray;` trong `sort` | để hàm đệ quy chỉ cần `left`, `right` (V4); field **khác tên** tham số (tờ checklist 3.2) |

### 2.4 Độ phức tạp

| | Thời gian | Bộ nhớ thêm |
|---|---|---|
| **Mọi trường hợp** | **O(n log n)** — chia luôn ở giữa, bất kể dữ liệu → `log₂ n` tầng, mỗi tầng trộn tổng `n` số | **O(n)** mảng phụ + ngăn xếp đệ quy `log n` |

Merge sort **không có dữ liệu xấu** (khác quick sort có thể O(n²)), và **ổn định**. Đổi lại tốn
mảng phụ và nhiều lần chép.

### 2.5 Java dùng trong bài

| API / khái niệm | Dùng làm gì |
|---|---|
| **Đệ quy** | `sortByMerge` tự gọi lại với nửa trái, nửa phải |
| `new int[k]` | mảng phụ `mergedArray` cho mỗi lần trộn |
| `workingArray = numberArray` | gán tham số vào field **khác tên** — hàm đệ quy dùng field; khác tên để tham số không "che" field (tờ checklist 3.2) |
| `new Random().nextInt(n)` | số ngẫu nhiên trong `[0, n)` |
| `Arrays.toString(arr)` | ra chữ `[2, 6, 3]` đúng định dạng màn hình |
| `Integer.parseInt(s)` | chuỗi → số; sai → `NumberFormatException` |
| `String.format("Sorted array: %s", chuỗi)` | ghép nhãn với mảng **không** cộng chuỗi (tờ checklist 3.8) |

---

## 3. Thiết kế

```
HE176322_J1SP0005_MergeSort/src/
├── model/       NumberArray        JavaBean: int[] valueArray + getSize/getValue/setValue/toString
├── repository/  NumberRepository   GIỮ mảng số: NumberArray numberArray + saveNumberArray/getNumberArray
├── dto/         SortRequestDTO     size                          (main ──► controller)
│                SortResponseDTO    unsortedArray, sortedArray    (controller ──► view)
├── service/     ISortStrategy      interface: void sort(NumberArray)                    ← Strategy
│                MergeSortStrategy  sort() + sortByMerge(left, right) đệ quy + merge()   ← ConcreteStrategy
│                SortService        sinh số + cất vào repository + gọi strategy          ← Context
├── controller/  SortController     new SortService(new MergeSortStrategy()) → view, render 1 lần
├── view/        SortView           thuộc tính responseDTO + setResponseDTO + display()
├── constants/   Message, Constants câu chữ; MIN_SIZE = 1, MAX_SIZE = 1000
├── utils/       Validation         getSize(chuỗi) → int hoặc ném lỗi
└── main/        Main               final + private Main(); Scanner + gọi controller 1 lần
```

| Câu hỏi thiết kế | Trả lời |
|---|---|
| Sao bài thuật toán lại có `repository`? | Tờ checklist 1.1: *"**Bắt buộc phải có repository**"*. `NumberRepository` giữ **dữ liệu mà thuật toán làm việc** (mảng số) với CRUD đơn giản: `saveNumberArray` (Create), `getNumberArray` (Read). Không sắp, không in. |
| Vậy thuật toán nằm đâu? | Ở `service` (Guide: tính toán nghiệp vụ → Services, *"Services nằm giữa Controller và Repo"*). `SortService` **lấy mảng từ repository** rồi giao cho strategy sắp. |
| Sao `MergeSortStrategy` có field `workingArray`? | Đệ quy cần **mảng + 2 đầu đoạn** = 3 thứ, nhưng thầy cấm hàm 3 tham số (V4). `sort(numberArray)` gán mảng vào field `private`, hàm đệ quy chỉ nhận `left`, `right`. |
| Sao `NumberArray` có `setValue` mà không có `swap`? | Merge sort **chép** số từ mảng phụ về, không đổi chỗ. Model chỉ giữ hàm bài thật sự dùng. |
| Sao controller không đụng `NumberArray`? | Tờ checklist 1.1: controller *"không làm việc với Model"*; Guide: *"chỉ import DTO, View, Service"*. |

### 3.1 Design Pattern — Strategy

Thầy đánh giá cao nhất **Design Pattern** (QUY-TAC-THAY §9, V7). Trình bày đủ 4 yếu tố (slide Design
Pattern, *"Elements of a Design Pattern"*):

| Yếu tố | Trong bài này |
|---|---|
| **Name** | Strategy (nhóm Behavioral) |
| **Problem** | Có **nhiều thuật toán sắp xếp** (bubble, selection, insertion, quick, merge…) cùng làm một việc. Viết thẳng một thuật toán vào service thì đổi thuật toán = sửa service. |
| **Solution** | `ISortStrategy` (**Strategy**, interface 1 hàm `sort`; tên bắt đầu bằng `I` — tờ checklist 1.3) · `MergeSortStrategy` (**ConcreteStrategy**) · `SortService` (**Context**: giữ field `ISortStrategy`, nhận qua constructor, gọi `sortStrategy.sort(numberArray)`) · `SortController` là nơi **chọn** strategy: `new SortService(new MergeSortStrategy())`. |
| **Consequences** | ➕ Thêm thuật toán = **thêm 1 lớp** + sửa **1 dòng** ở controller; `SortService`, `Main`, `View` không đụng (**O** của SOLID). `SortService` phụ thuộc interface, không phụ thuộc lớp cụ thể (**D**). Mảng phụ, đệ quy, field `workingArray` **giấu kín** trong strategy. ➖ Thêm 2 file so với viết thẳng. Riêng bài này: field `workingArray` làm một đối tượng `MergeSortStrategy` **không dùng được cho 2 mảng cùng lúc** (2 luồng) — chương trình console một luồng nên không sao. |

Ngoài ra: **MVC** (kiến trúc của thầy), `SortController` đóng vai **Facade** — `Main` chỉ gọi
`controller.sortArray(requestDTO)`, không biết có service/strategy/repository/view phía sau — và
**Repository**: `NumberRepository` là chỗ duy nhất giữ dữ liệu.

**Thầy bảo "đổi sang quick sort" — làm trong 2 phút:**
1. Tạo `service/QuickSortStrategy.java` `implements ISortStrategy`, viết `sort()` (cần thêm `swap`
   vào `NumberArray`).
2. Sửa **đúng 1 dòng** trong `SortController`: `new SortService(new QuickSortStrategy())`.

**Luồng chạy** (Main → RequestDTO → Controller → Service → Repository → Model; ResponseDTO → View 1 lần):

```
Main: đọc size (hỏi lại khi sai) ──► SortRequestDTO ──► controller.sortArray(requestDTO)   ← gọi 1 lần
   controller ──► sortService.sortRandomArray(requestDTO)
                     ├─ generateValueArray(size)                 → int[] ngẫu nhiên trong [0, size)
                     ├─ numberRepository.saveNumberArray(...)    → repository gói vào NumberArray (model)
                     ├─ numberRepository.getNumberArray()        ← service lấy dữ liệu TỪ repository
                     ├─ responseDTO.setUnsortedArray(...)        ← chụp TRƯỚC khi sắp
                     ├─ sortStrategy.sort(numberArray)           → MergeSortStrategy.sort
                     │                                              ├─ workingArray = numberArray
                     │                                              └─ sortByMerge(0, size - 1)
                     │                                                   ├─ sortByMerge(left, mid)
                     │                                                   ├─ sortByMerge(mid + 1, right)
                     │                                                   └─ merge(left, right)
                     └─ responseDTO.setSortedArray(...)
   controller ──► sortView.setResponseDTO(responseDTO) ──► sortView.display()   ← render 1 lần
```

### 3.2 SOLID trong bài

| Nguyên lý | Ở đâu |
|---|---|
| **S** | mỗi lớp 1 việc: `NumberArray` mô tả mảng, `NumberRepository` giữ mảng, `MergeSortStrategy` sắp, `SortService` điều phối, `SortView` in, `Validation` kiểm; trong strategy: `sortByMerge` chỉ **chia**, `merge` chỉ **trộn** |
| **O** | thêm thuật toán = thêm lớp `implements ISortStrategy`, không sửa `SortService` |
| **L** | mọi `ISortStrategy` thay được cho nhau: đều sắp tăng dần, kết quả nằm trong chính mảng truyền vào |
| **I** | `ISortStrategy` chỉ có **1 hàm** |
| **D** | `SortService` giữ field kiểu **interface** `ISortStrategy`, nhận qua constructor |

---

## 4. Code từng bước

> Thầy (QUY-TAC-THAY §9, V8): *"code cái model trước rồi đến data"*.

| Bước | File | Việc |
|---|---|---|
| 1 | `model/NumberArray.java` | field `int[] valueArray` · ctor rỗng + ctor đủ · get/set `valueArray` · `getSize` · `getValue` · `setValue` · `toString` |
| 2 | `repository/NumberRepository.java` | field `NumberArray numberArray` · ctor (mảng rỗng) · `saveNumberArray(int[] valueArray)` · `getNumberArray()` |
| 3 | `dto/SortRequestDTO.java`, `SortResponseDTO.java` | ctor rỗng + field + get/set (JavaBean) |
| 4 | `service/ISortStrategy.java` | interface: `void sort(NumberArray numberArray);` |
| 5 | `service/MergeSortStrategy.java` | field `workingArray` · `sort()` gán field rồi `sortByMerge(0, size - 1)` · **`sortByMerge(left, right)`**: `mid` / dừng / 2 lời gọi / `merge` · **`merge(left, right)`**: mảng phụ `mergedArray` + 3 vòng `while` + `for` chép lại |
| 6 | `service/SortService.java` | field `sortStrategy`, `numberRepository`, `random` + ctor nhận strategy · `generateValueArray` · `sortRandomArray` |
| 7 | `controller/SortController.java` | `new SortService(new MergeSortStrategy())` · `sortArray(requestDTO)`: `setResponseDTO` rồi `display()` **1 lần** |
| 8 | `view/SortView.java` | field `responseDTO` · `setResponseDTO` · `display()` **không tham số** |
| 9 | `constants/Message.java`, `Constants.java` | prompt, 2 lỗi, 2 dòng kết quả `"…: %s"` · `MIN_SIZE`, `MAX_SIZE` |
| 10 | `utils/Validation.java` | `getSize(String)` — **tách 2 lỗi** |
| 11 | `main/Main.java` | `public final class` + `private Main()`; `inputSize` (vòng hỏi lại) + gọi `controller.sortArray` **1 lần** |

**Bẫy hay gặp:**

1. Quên điểm dừng `if (left >= right) return;` → đệ quy vô tận → `StackOverflowError`.
2. Chia sai: `sortByMerge(left, mid - 1)` + `sortByMerge(mid, right)` → với 2 số (`mid = left`) gọi lại
   đúng đoạn cũ → vô tận. Đúng là `left..mid` và `mid + 1..right`.
3. Quên 2 vòng `while` chép nốt → mất số, mảng sau ngắn hơn/sai.
4. Chép `mergedArray` về sai chỗ: phải `setValue(left + k, ...)`, **không** phải `setValue(k, ...)`.
5. In mảng "trước" **sau khi** đã sắp → hai dòng giống nhau: phải `setUnsortedArray` trước `sortStrategy.sort`.
6. View có hàm nhận tham số (`display(dto)`, `showMessage(String)`) → tờ checklist 1.1 đánh trượt:
   View nhận dữ liệu **qua thuộc tính**.

---

## 5. Test trước khi gọi thầy

| # | Gõ | Phải thấy |
|---|---|---|
| 1 | `abc` | `You must input a number.` rồi hỏi lại |
| 2 | *(Enter trống)* | `You must input a number.` |
| 3 | `-5` | `Number must be between 1 and 1000.` |
| 4 | `0` | `Number must be between 1 and 1000.` |
| 5 | `1001` | `Number must be between 1 and 1000.` |
| 6 | `3.5` | `You must input a number.` |
| 7 | `10` | 10 số từ 0–9, dòng Sorted tăng dần, **cùng các số** với dòng Unsorted |
| 8 | `1` | mảng 1 phần tử, hai dòng giống nhau (`sortByMerge(0, 0)` dừng ngay) |
| 9 | `2` | 2 số, dòng Sorted tăng dần |
| 10 | `1000` | dòng Sorted tăng dần, nhiều số trùng |

---

## 6. Debug

| Việc | Cách làm |
|---|---|
| Breakpoint | dòng đầu của `merge` (`int mid = (left + right) / 2;`) trong `MergeSortStrategy` |
| Chạy | **Ctrl+F5**, nhập `7` |
| Vào thuật toán | breakpoint ở `sortStrategy.sort(numberArray);` trong `SortService`, **F7** → nhảy vào `MergeSortStrategy.sort` (đa hình qua interface) |
| Quan sát | tab **Variables**: `left`, `right`, `mid`, `i`, `j`, `k`, mở `mergedArray`; mở `this` → `workingArray` → `valueArray` để thấy đoạn `left..right` **đổi** sau vòng `for` cuối |
| Đệ quy | cửa sổ **Call Stack** (Window ▸ Debugging ▸ Call Stack): thấy chồng `sortByMerge` → `sortByMerge` → `merge` — chỉ cho thầy **thứ tự** chia xong mới trộn |
| Bước | **F5** (Continue) mỗi lần tới breakpoint = 1 lần trộn (đúng các dòng 4, 5, 6, 8, 9, 10 của bảng 2.2); **F8** trong `merge` để thấy từng số được lấy |
| Thấy repository | **F7** vào `numberRepository.saveNumberArray(...)` — mảng vừa sinh được gói thành `NumberArray` và cất lại |

---

## 7. Câu hỏi thầy hay hỏi

### Thuật toán

| Câu hỏi | Trả lời mẫu |
|---|---|
| Vì sao 1 phần tử coi là đã sắp? | Không có cặp nào để sai thứ tự — đây là **điểm dừng** đệ quy. |
| Vì sao cần mảng phụ? | Trộn tại chỗ sẽ ghi đè số chưa đọc. Mảng phụ giữ kết quả, xong mới chép về. |
| Độ phức tạp? | **Luôn O(n log n)**: `log₂ n` tầng chia, mỗi tầng trộn tổng `n` số. Bộ nhớ thêm **O(n)**. |
| Có ổn định không? | **Có** — nhờ `<=`: số bằng nhau lấy bên trái trước. Đổi thành `<` thì mất ổn định. |
| Khác quick sort? | Merge **chia mù** ở giữa rồi **làm việc khi trộn**; quick **làm việc khi chia** (phân hoạch) rồi không trộn. Merge luôn O(n log n) + ổn định nhưng tốn mảng phụ; quick thường nhanh hơn, không mảng phụ, nhưng xấu nhất O(n²). |
| Sao `merge` tính lại `mid`? | Để hàm chỉ 2 tham số (V4). Cùng công thức `(left + right) / 2` nên ra **đúng** điểm cắt `sortByMerge` đã dùng. |
| Sắp giảm dần sửa gì? | Trong `merge`: `<=` → `>=`. |

### Kiến trúc theo tờ checklist

| Câu hỏi | Trả lời mẫu |
|---|---|
| Sao bài có repository? | Tờ checklist 1.1 *"Bắt buộc phải có repository"*. `NumberRepository` giữ **dữ liệu** (mảng số) và chỉ có CRUD đơn giản: `saveNumberArray` tạo `NumberArray` từ các số vừa sinh, `getNumberArray` trả nó ra. Tính toán ở `SortService` + strategy — luồng **Controller → Service → Repository → Model**. |
| View nhận dữ liệu thế nào? | **Qua thuộc tính**: `SortView` có field `private SortResponseDTO responseDTO` + setter `setResponseDTO(...)`; `display()` không tham số. Controller gọi `setResponseDTO(responseDTO)` rồi `display()` **đúng 1 lần**. |
| Validate ở đâu? | Ở **Main**: `Main.inputSize` đọc `sc.nextLine()`, đưa cho `Validation.getSize` — sai thì ném `Exception(Message.X)`, Main bắt, in `e.getMessage()` rồi hỏi lại. Controller/service chỉ nhận `SortRequestDTO` đã sạch. |
| Sao `Main` là `final` và có `private Main()`? | Tờ checklist 3.4: lớp chỉ có hàm `static` phải có private constructor và khai báo `final`. |
| Sao interface tên `ISortStrategy`? | Tờ checklist 1.3: *"Tên của interface bắt đầu bằng "I""*. |
| Sao hàm đệ quy tên `sortByMerge`, không phải `mergeSort`? | Tờ checklist 1.4: tên method **bắt đầu bằng động từ** — `sortByMerge` = "sắp bằng cách trộn", cùng kiểu `sortByBubble` (P0001), `sortByQuick` (P0004). `merge` vốn là động từ nên giữ. |

### OOP / Java

| Câu hỏi | Trả lời mẫu |
|---|---|
| 4 tính chất OOP ở đâu? | **Đóng gói**: `valueArray` `private` trong `NumberArray`, chỉ ghi qua `setValue`/`setValueArray`; field `workingArray` và hàm `sortByMerge`/`merge` `private` trong `MergeSortStrategy`; `numberArray` `private` trong `NumberRepository`. **Kế thừa**: `MergeSortStrategy implements ISortStrategy`; `NumberArray` kế thừa `Object`. **Đa hình**: `SortService` gọi `sortStrategy.sort(numberArray)` — biến kiểu interface, chạy bản của `MergeSortStrategy`; `toString()` có `@Override`. **Trừu tượng**: interface `ISortStrategy` chỉ nói *"sắp được"*, giấu hẳn chuyện đệ quy và mảng phụ. |
| Sao `sort` không tự đệ quy mà cần `sortByMerge`? | Chữ ký `sort(NumberArray)` do interface quy định — không nhận `left`/`right`. Hàm phụ `private` mới cần đoạn. |
| `mergedArray` là biến cục bộ, `workingArray` là field — sao khác nhau? | `mergedArray` chỉ sống trong 1 lần trộn; `workingArray` phải dùng chung cho **mọi** lời gọi đệ quy. |
| Sao field tên `workingArray` mà không trùng tên tham số `numberArray`? | Tham số trùng tên field sẽ **che** field (phải viết `this.` mới tới field) — tờ checklist 3.2 *"Không khai báo biến local trùng tên với biến higher level"*. Đặt khác tên: `workingArray = numberArray;`. |
| Interface khác abstract class? | Interface chỉ là **hợp đồng** (ở đây 1 hàm); các thuật toán không chung dòng code nào → interface. |
| Không có ArrayList/List trong bài? | Đúng — dùng **mảng `int[]`** vì kích thước biết trước. (List vs ArrayList: `List` là interface, `ArrayList` là lớp cụ thể dùng mảng động; bộ lời giải khai kiểu cụ thể — QUY-TAC-THAY §9 V5.) |

### Access modifier và static — từng chỗ không `private` (thầy V2, V3)

| Thành phần | Vì sao như vậy |
|---|---|
| Mọi **field** (`valueArray`, `numberArray`, `size`, `unsortedArray`, `sortedArray`, `workingArray`, `sortStrategy`, `numberRepository`, `random`, `sortService`, `sortView`, `responseDTO`) | `private` — chỉ lớp đó được đụng (đóng gói) |
| `MergeSortStrategy.workingArray` — **không** `static` | mỗi đối tượng strategy giữ mảng **của nó**; `static` thì mọi đối tượng dùng chung một mảng — sai nghĩa và bị thầy bắt (V3) |
| `MergeSortStrategy.sortByMerge(int, int)`, `merge(int, int)` | **`private`** — hàm phụ, chỉ lớp này gọi |
| `NumberArray()`, get/set `valueArray` | `public` — chuẩn **JavaBean** (V10) |
| `NumberArray(int[])` | `public` — `NumberRepository` ở package **khác** (`repository`) gọi |
| `getSize`, `getValue`, `setValue`, `toString` | `public` — `SortService`/`MergeSortStrategy` ở package **khác** (`service`) gọi |
| `NumberRepository()`, `saveNumberArray`, `getNumberArray` | `public` — `SortService` gọi |
| `ISortStrategy.sort` | hàm interface **luôn public** |
| `MergeSortStrategy.sort` | `public` — ghi đè hàm public của interface (không được thu hẹp) |
| `SortService(...)`, `sortRandomArray` | `public` — controller gọi |
| `SortService.generateValueArray` | **`private`** — chỉ `sortRandomArray` dùng |
| `SortController()`, `sortArray` | `public` — `Main` gọi |
| `SortView.setResponseDTO`, `display` | `public` — controller gọi |
| get/set của 2 DTO, ctor rỗng | `public` — JavaBean; main/service/view gọi |
| `Validation.getSize` — `public static` | không dùng dữ liệu đối tượng nào; **bỏ `static`** thì phải bỏ constructor `private` và `new Validation()` trong `Main` |
| Constructor `private` của `Validation`, `Message`, `Constants`, `Main` | chặn `new` — lớp chỉ chứa hàm/hằng static (tờ checklist 3.4) |
| `Message.X`, `Constants.X` — `public static final` | hằng dùng chung, không đổi; gọi qua tên lớp |
| `Main.main` — `public static` | JVM gọi mà không tạo đối tượng `Main` |
| `Main.inputSize` — `private static` | Guide: *"cấm static với biến, có thể dùng với hàm"*; `main` static nên hàm nó gọi thẳng cũng phải static. Bỏ `static` → phải bỏ `private Main()` và gọi `new Main().inputSize(sc)` |

### Kiểu trả về

| Hàm | Trả | Vì sao |
|---|---|---|
| `sort`, `sortByMerge`, `merge` | `void` | kết quả được **chép về chính mảng** (tham chiếu) — không có gì mới để trả; mảng trong repository cũng đã sắp vì là cùng một đối tượng |
| `sortRandomArray` | `SortResponseDTO` | view cần **2 chuỗi** cùng lúc → gói vào 1 DTO |
| `generateValueArray` | `int[]` | các số vừa sinh, để repository gói thành `NumberArray` |
| `getNumberArray` | `NumberArray` | repository trả **model** cho service (service được phép làm việc với model, controller thì không) |
| `getSize`, `getValue` | `int` | số đếm / phần tử nguyên |
| `toString` | `String` | model **không được in** — trả chữ để view in |

---

## 8. Thầy đổi yêu cầu tại chỗ

| Thầy bảo | Sửa file | Không đụng |
|---|---|---|
| Đổi sang **thuật toán khác** | thêm `service/XSortStrategy.java` (`implements ISortStrategy`) + 1 dòng `new SortService(new XSortStrategy())` trong `SortController` | `SortService`, `Main`, `View`, DTO, repository |
| Sắp **giảm dần** | `MergeSortStrategy.merge`: `<=` → `>=` | mọi file khác |
| Chỉ cấp **1 mảng phụ** cho cả lần sắp | field `int[] bufferArray` trong `MergeSortStrategy`, tạo 1 lần trong `sort()`; `merge` ghi vào `bufferArray` thay vì `new int[...]` | mọi file khác |
| Số ngẫu nhiên trong `[-n, n]` | `SortService.generateValueArray`: `random.nextInt((2 * size) + 1) - size` | strategy, view, repository |
| In thêm **số lần trộn** | field đếm trong `MergeSortStrategy` + `sort` trả `int` (sửa interface) → field mới trong `SortResponseDTO` → `SortService` gán → `SortView.display()` in thêm dòng (+ câu trong `Message`) | `Main`, `Validation` |

---

## 9. Chỗ khác với đề / lời giải cũ trên web

| Chỗ | Bản cũ | Bài này | Lý do |
|---|---|---|---|
| Dòng tiêu đề | `===== Merge Sort Program =====` | không có | ảnh màn hình đề **không có** dòng này |
| Prompt | `Please input the number of array: ` | `Enter number of array:` (xuống dòng) | **đúng ảnh màn hình đề** — nên test đặt `REPLACE_REFERENCE = True` |
| Khoảng số ngẫu nhiên | `[0, 100)` | `[0, n)` | đề: *"random integer in number range input"*, ví dụ n=10 → 0..9 |
| Kiến trúc | `bo/ui/utils`, Scanner trong `Validator` | MVC theo Guide + Strategy + Repository, Scanner **chỉ ở `main`** | luật thầy |
| Hàm đệ quy / trộn | `mergeSort(array, buffer, low, high)` · `merge(array, buffer, low, mid, high)` — 4–5 tham số | `sortByMerge(left, right)` · `merge(left, right)` + field `workingArray` | thầy: *"không được truyền 3 tham số 1 hàm"* (V4) |
| Mảng phụ | 1 mảng `buffer` cấp một lần, dùng lại | mỗi lần trộn `new int[right - left + 1]` | dễ hiểu hơn cho người học; tổng cấp phát nhiều lần hơn nhưng lúc nào cũng chỉ **1** mảng phụ đang dùng (các lần trộn không lồng nhau). Bản 1-buffer ở mục 8 |
| Chặn mảng rỗng | `if (array.length < 2) return;` | không có | `Validation` bắt `size ≥ 1`; `sortByMerge(0, 0)` dừng ngay ở `left >= right` |
| Thông báo lỗi | `You must input a number.` · `Number must be between 1 and 1000.` | giữ nguyên | đề không ghi câu lỗi; giữ đúng bản cũ đã kiểm |

### 9.1 Đổi theo tờ checklist giấy (21/09/2026)

Màn hình chạy **không đổi một chữ** (`verify.py` so từng dòng, cả en_US và vi_VN).

| Chỗ | Bản trước | Bây giờ | Mục checklist |
|---|---|---|---|
| Tầng dữ liệu | không có `repository` | `repository/NumberRepository` giữ `NumberArray` | 1.1 |
| View | `setResponse(response)` | field `responseDTO` + `setResponseDTO(...)` + `display()` | 1.1 |
| Main | `public class Main` | `public final class Main` + `private Main()` | 3.4 |
| Interface | `SortStrategy` | `ISortStrategy` (đổi tên tệp, lớp, mọi chỗ dùng) | 1.3 |
| Tên hàm | `mergeSort`, `generateArray` | `sortByMerge`, `generateValueArray` | 1.4 |
| Field của strategy | `array` + `this.array = array` (tham số che field) | `workingArray = numberArray` | 3.2 |
| Tên mảng | `int[] values`, `getValues/setValues`; `int[] merged` | `int[] valueArray`, `getValueArray/setValueArray`; `int[] mergedArray` | 1.5 |
| Tên field DTO | `unsorted`, `sorted` | `unsortedArray`, `sortedArray` — cùng khuôn P0001–P0004 | thống nhất |
| Khai báo biến | `String line = sc.nextLine();` giữa vòng lặp; `int size;` chưa gán; `int mid` sau lệnh `if` | `String line = "";` đầu hàm; `int size = 0;`; `int mid = (left + right) / 2;` đầu `sortByMerge` | 2.6, 3.7 |
| Ngoặc | `i <= mid && j <= right`; `size < MIN_SIZE \|\| size > MAX_SIZE` | `(i <= mid) && (j <= right)`; `(size < …) \|\| (size > …)` | 3.3 |
| In nhãn | `Message.LABEL_SORTED + chuỗi` | `String.format(Message.SORTED_ARRAY, chuỗi)` | 3.8 |
| Comment | ba comment hàm bị cắt cụt (`// Merge sort of the range left ..`) | câu đủ nghĩa (`left .. right`, `mid + 1 .. right`) | 1.6 |
| Dòng trống | comment của các hằng/field dính nhau; khối sau `}` dính nhau | 1 dòng trống trước mỗi comment, sau vùng khai báo, sau mỗi `}` | 2.8 |

---

## 10. Tờ checklist 25 mục — bài này đạt thế nào

| Mục | Chỉ vào đâu |
|---|---|
| 1.1 | `repository/NumberRepository` (bắt buộc có); `SortController` chỉ import `dto`/`service`/`view`; `SortView` nhận `responseDTO` qua setter, `display()` gọi 1 lần; `Main` gọi `controller.sortArray` 1 lần |
| 1.3 / 1.4 | interface `ISortStrategy`; hàm mở đầu bằng động từ: `sortByMerge`, `merge`, `generateValueArray`, `saveNumberArray`, `sortRandomArray` |
| 1.5 | biến/field kiểu mảng đuôi `Array`: `int[] valueArray` (`NumberArray`, `SortService.generateValueArray`), `int[] mergedArray` (`MergeSortStrategy.merge`) |
| 2.6 + 3.7 | `String line = "";` (`Main.inputSize`), `int size = 0;` (`Validation.getSize`), `NumberArray numberArray = null;` (`SortService.sortRandomArray`), `mid` đầu `sortByMerge`, `mid`/`mergedArray`/`i`/`j`/`k` đầu `merge` |
| 2.8 | 1 dòng trống giữa các hằng/field có comment, sau vùng khai báo biến, sau mỗi `}` trước câu lệnh kế |
| 3.3 | `while ((i <= mid) && (j <= right))`; `if ((size < Constants.MIN_SIZE) \|\| (size > Constants.MAX_SIZE))` |
| 3.4 | `public final class Main` + `private Main()`; `Validation`, `Message`, `Constants` cũng `final` + private constructor |
| Soát máy | `checklist_audit.py` → **0 VI_PHAM**; còn RUI_RO: `String[] args` của `main` và tham số setter/constructor trùng tên field (`this.sortStrategy = sortStrategy` — IDE sinh) |
