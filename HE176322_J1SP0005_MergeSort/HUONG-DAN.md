# J1.S.P0005 — Merge Sort

> Bài anh em của **P0001 Bubble Sort**: cùng khung MVC, cùng màn hình, cùng Strategy. Chỉ khác
> **một lớp**: `MergeSortStrategy` — bài **đệ quy chia để trị**. Thuật toán vẫn phải làm MVC —
> thầy: *"Các bài liên quan thuật toán như fibo, sắp xếp,… cũng phải làm MVC."*

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
| Hai dòng kết quả | `Unsorted array: [...]` · `Sorted array: [...]` | `Message.LABEL_UNSORTED/LABEL_SORTED`, in ở `SortView` |
| Số ngẫu nhiên | *"random integer in number range input"* | `SortService.generateArray` → `[0, n)` |
| Bước 1 | chia mảng tới khi **mỗi phần 1 phần tử** (1 phần tử coi là đã sắp) | `MergeSortStrategy.mergeSort(left, right)` |
| Bước 2 | **trộn** lặp lại các phần đã sắp tới khi còn 1 danh sách | `MergeSortStrategy.merge(left, right)` |

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
| 1 | `mergeSort(0, 6)` | chia, `mid = 3` | `38 27 43 3` \| `9 82 10` |
| 2 | `mergeSort(0, 3)` | chia, `mid = 1` | `38 27` \| `43 3` |
| 3 | `mergeSort(0, 1)` | chia, `mid = 0` | `38` \| `27` (mỗi mảnh 1 số → dừng) |
| 4 | `merge(0, 1)` | trộn `38` + `27` | `27 38` |
| 5 | `mergeSort(2, 3)` → `merge(2, 3)` | chia `43` \| `3`, trộn | `3 43` |
| 6 | `merge(0, 3)` | trộn `27 38` + `3 43` | `3 27 38 43` |
| 7 | `mergeSort(4, 6)` | chia, `mid = 5` | `9 82` \| `10` |
| 8 | `mergeSort(4, 5)` → `merge(4, 5)` | chia `9` \| `82`, trộn | `9 82` |
| 9 | `merge(4, 6)` | trộn `9 82` + `10` | `9 10 82` |
| 10 | `merge(0, 6)` | trộn `3 27 38 43` + `9 10 82` | `3 9 10 27 38 43 82` |

Khớp từng mảnh với hình cây của đề.

**Trộn bước 10 chi tiết** (`i` chỉ nửa trái, `j` chỉ nửa phải, `merged` là mảng phụ):

| k | So `trái[i]` với `phải[j]` | Lấy | `merged` |
|---|---|---|---|
| 0 | `3 ≤ 9` | 3 (trái) | `3` |
| 1 | `27 > 9` | 9 (phải) | `3 9` |
| 2 | `27 > 10` | 10 (phải) | `3 9 10` |
| 3 | `27 ≤ 82` | 27 (trái) | `3 9 10 27` |
| 4 | `38 ≤ 82` | 38 (trái) | `3 9 10 27 38` |
| 5 | `43 ≤ 82` | 43 (trái) | `3 9 10 27 38 43` |
| — | nửa trái **hết** | vòng `while (j <= right)` chép nốt 82 | `3 9 10 27 38 43 82` |

Cuối cùng vòng `for` chép `merged` **đè lại** ô `0..6` của mảng.

### 2.3 Chi tiết thầy hay soi

| Chi tiết | Code | Vì sao |
|---|---|---|
| Điểm dừng đệ quy | `if (left >= right) { return; }` | đoạn 1 số (hoặc rỗng) đã sắp — đề: *"one element is considered as sorted"* |
| Cần **mảng phụ** | `int[] merged = new int[right - left + 1];` | ghi thẳng vào mảng sẽ **đè** số của nửa trái chưa đọc (đề: *"additional O(n) space"*) |
| So `<=` khi trộn | `if (array.getValue(i) <= array.getValue(j))` | số **bằng nhau** lấy bên **trái** trước → giữ thứ tự cũ → **ổn định** (đề: *"stable"*) |
| 2 vòng `while` chép nốt | `while (i <= mid)` · `while (j <= right)` | vòng chính dừng khi **một** nửa hết; nửa kia còn số phải chép — chỉ **một** trong hai vòng thực sự chạy |
| `merge` chỉ 2 tham số | `merge(int left, int right)` tính lại `mid = (left + right) / 2` | thầy: *"không được truyền 3 tham số 1 hàm"* (V4); cùng công thức nên cùng điểm cắt với `mergeSort` |
| Mảng ở field | `this.array = array;` trong `sort` | để hàm đệ quy chỉ cần `left`, `right` (V4) |

### 2.4 Độ phức tạp

| | Thời gian | Bộ nhớ thêm |
|---|---|---|
| **Mọi trường hợp** | **O(n log n)** — chia luôn ở giữa, bất kể dữ liệu → `log₂ n` tầng, mỗi tầng trộn tổng `n` số | **O(n)** mảng phụ + ngăn xếp đệ quy `log n` |

Merge sort **không có dữ liệu xấu** (khác quick sort có thể O(n²)), và **ổn định**. Đổi lại tốn
mảng phụ và nhiều lần chép.

### 2.5 Java dùng trong bài

| API / khái niệm | Dùng làm gì |
|---|---|
| **Đệ quy** | `mergeSort` tự gọi lại với nửa trái, nửa phải |
| `new int[k]` | mảng phụ `merged` cho mỗi lần trộn |
| `this.array = array` | gán tham số vào field cùng tên — hàm đệ quy dùng field |
| `new Random().nextInt(n)` | số ngẫu nhiên trong `[0, n)` |
| `Arrays.toString(arr)` | ra chữ `[2, 6, 3]` đúng định dạng màn hình |
| `Integer.parseInt(s)` | chuỗi → số; sai → `NumberFormatException` |

---

## 3. Thiết kế

```
HE176322_J1SP0005_MergeSort/src/
├── model/      NumberArray            JavaBean: int[] values + getSize/getValue/setValue/toString
├── dto/        SortRequestDTO         size          (main ──► controller)
│               SortResponseDTO        2 chuỗi mảng  (controller ──► view)
├── service/    SortStrategy           interface: void sort(NumberArray)       ← Strategy
│               MergeSortStrategy      sort() + mergeSort(l, r) + merge(l, r)  ← ConcreteStrategy
│               SortService            sinh mảng + gọi strategy + trả DTO      ← Context
├── controller/ SortController         new SortService(new MergeSortStrategy()) → view
├── view/       SortView               in 2 dòng kết quả
├── constants/  Message, Constants     câu chữ; MIN_SIZE = 1, MAX_SIZE = 1000
├── utils/      Validation             getSize(chuỗi) → int hoặc ném lỗi
└── main/       Main                   Scanner + gọi controller 1 lần
```

| Câu hỏi thiết kế | Trả lời |
|---|---|
| Sao có `service` mà không có `repository`? | Guide: repository = **giữ dữ liệu + CRUD**. Bài này không lưu gì. Thuật toán là **"tính toán nghiệp vụ"** → `service`. |
| Sao `MergeSortStrategy` có field `array`? | Đệ quy cần **mảng + 2 đầu đoạn** = 3 thứ, nhưng thầy cấm hàm 3 tham số (V4). `sort(array)` gán mảng vào field `private`, hàm đệ quy chỉ nhận `left`, `right`. |
| Sao `NumberArray` có `setValue` mà không có `swap`? | Merge sort **chép** số từ mảng phụ về, không đổi chỗ. Model chỉ giữ hàm bài thật sự dùng. |
| Sao controller không đụng `NumberArray`? | Guide: controller *"chỉ import DTO, View, Service"*. |

### 3.1 Design Pattern — Strategy

Thầy đánh giá cao nhất **Design Pattern** (QUY-TAC-THAY §9, V7). Trình bày đủ 4 yếu tố (slide Design
Pattern, *"Elements of a Design Pattern"*):

| Yếu tố | Trong bài này |
|---|---|
| **Name** | Strategy (nhóm Behavioral) |
| **Problem** | Có **nhiều thuật toán sắp xếp** (bubble, selection, insertion, quick, merge…) cùng làm một việc. Viết thẳng một thuật toán vào service thì đổi thuật toán = sửa service. |
| **Solution** | `SortStrategy` (**Strategy**, interface 1 hàm `sort`) · `MergeSortStrategy` (**ConcreteStrategy**) · `SortService` (**Context**: giữ field `SortStrategy`, nhận qua constructor, gọi `sortStrategy.sort(array)`) · `SortController` là nơi **chọn** strategy: `new SortService(new MergeSortStrategy())`. |
| **Consequences** | ➕ Thêm thuật toán = **thêm 1 lớp** + sửa **1 dòng** ở controller; `SortService`, `Main`, `View` không đụng (**O** của SOLID). `SortService` phụ thuộc interface, không phụ thuộc lớp cụ thể (**D**). Mảng phụ, đệ quy, field `array` **giấu kín** trong strategy. ➖ Thêm 2 file so với viết thẳng. Riêng bài này: field `array` làm một đối tượng `MergeSortStrategy` **không dùng được cho 2 mảng cùng lúc** (2 luồng) — chương trình console một luồng nên không sao. |

Ngoài ra: **MVC** (kiến trúc của thầy) và `SortController` đóng vai **Facade** — `Main` chỉ gọi
`controller.sortArray(dto)`, không biết có service/strategy/view phía sau.

**Thầy bảo "đổi sang quick sort" — làm trong 2 phút:**
1. Tạo `service/QuickSortStrategy.java` `implements SortStrategy`, viết `sort()` (cần thêm `swap`
   vào `NumberArray`).
2. Sửa **đúng 1 dòng** trong `SortController`: `new SortService(new QuickSortStrategy())`.

**Luồng chạy:**

```
Main: đọc size (hỏi lại khi sai) ──► SortRequestDTO ──► controller.sortArray(dto)
   controller ──► sortService.sortRandomArray(dto)
                     ├─ generateArray(size)          → NumberArray ngẫu nhiên
                     ├─ response.setUnsorted(array.toString())   ← chụp TRƯỚC khi sắp
                     ├─ sortStrategy.sort(array)     → MergeSortStrategy.sort
                     │                                   ├─ this.array = array
                     │                                   └─ mergeSort(0, size - 1)
                     │                                        ├─ mergeSort(left, mid)
                     │                                        ├─ mergeSort(mid + 1, right)
                     │                                        └─ merge(left, right)
                     └─ response.setSorted(array.toString())
   controller ──► view.setResponse(response) ──► view.display()
```

### 3.2 SOLID trong bài

| Nguyên lý | Ở đâu |
|---|---|
| **S** | mỗi lớp 1 việc: `NumberArray` giữ mảng, `MergeSortStrategy` sắp, `SortService` điều phối, `SortView` in, `Validation` kiểm; trong strategy: `mergeSort` chỉ **chia**, `merge` chỉ **trộn** |
| **O** | thêm thuật toán = thêm lớp `implements SortStrategy`, không sửa `SortService` |
| **L** | mọi `SortStrategy` thay được cho nhau: đều sắp tăng dần, kết quả nằm trong chính mảng truyền vào |
| **I** | `SortStrategy` chỉ có **1 hàm** |
| **D** | `SortService` giữ field kiểu **interface** `SortStrategy`, nhận qua constructor |

---

## 4. Code từng bước

> Thầy (QUY-TAC-THAY §9, V8): *"code cái model trước rồi đến data"*.

| Bước | File | Việc |
|---|---|---|
| 1 | `model/NumberArray.java` | field `int[] values` · ctor rỗng + ctor đủ · get/set `values` · `getSize` · `getValue` · `setValue` · `toString` |
| 2 | `dto/SortRequestDTO.java`, `SortResponseDTO.java` | ctor rỗng + field + get/set (JavaBean) |
| 3 | `service/SortStrategy.java` | interface: `void sort(NumberArray array);` |
| 4 | `service/MergeSortStrategy.java` | field `array` · `sort()` gán field rồi `mergeSort(0, size - 1)` · **`mergeSort(left, right)`**: dừng / chia / 2 lời gọi / `merge` · **`merge(left, right)`**: mảng phụ + 3 vòng `while` + `for` chép lại |
| 5 | `service/SortService.java` | field strategy + ctor nhận strategy · `generateArray` · `sortRandomArray` |
| 6 | `controller/SortController.java` | `new SortService(new MergeSortStrategy())` · `sortArray(dto)` |
| 7 | `view/SortView.java` | `setResponse` · `display` |
| 8 | `constants/Message.java`, `Constants.java` | prompt, 2 lỗi, 2 nhãn · `MIN_SIZE`, `MAX_SIZE` |
| 9 | `utils/Validation.java` | `getSize(String)` — **tách 2 lỗi** |
| 10 | `main/Main.java` | `inputSize` (vòng hỏi lại) + gọi `controller.sortArray` **1 lần** |

**Bẫy hay gặp:**

1. Quên điểm dừng `if (left >= right) return;` → đệ quy vô tận → `StackOverflowError`.
2. Chia sai: `mergeSort(left, mid - 1)` + `mergeSort(mid, right)` → với 2 số (`mid = left`) gọi lại
   đúng đoạn cũ → vô tận. Đúng là `left..mid` và `mid + 1..right`.
3. Quên 2 vòng `while` chép nốt → mất số, mảng sau ngắn hơn/sai.
4. Chép `merged` về sai chỗ: phải `setValue(left + k, ...)`, **không** phải `setValue(k, ...)`.
5. In mảng "trước" **sau khi** đã sắp → hai dòng giống nhau: phải `setUnsorted` trước `sort`.

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
| 8 | `1` | mảng 1 phần tử, hai dòng giống nhau (`mergeSort(0, 0)` dừng ngay) |
| 9 | `2` | 2 số, dòng Sorted tăng dần |
| 10 | `1000` | dòng Sorted tăng dần, nhiều số trùng |

---

## 6. Debug

| Việc | Cách làm |
|---|---|
| Breakpoint | dòng đầu của `merge` (`int mid = (left + right) / 2;`) trong `MergeSortStrategy` |
| Chạy | **Ctrl+F5**, nhập `7` |
| Vào thuật toán | breakpoint ở `sortStrategy.sort(array);` trong `SortService`, **F7** → nhảy vào `MergeSortStrategy.sort` (đa hình qua interface) |
| Quan sát | tab **Variables**: `left`, `right`, `mid`, `i`, `j`, `k`, mở `merged`; mở `this` → `array` → `values` để thấy đoạn `left..right` **đổi** sau vòng `for` cuối |
| Đệ quy | cửa sổ **Call Stack** (Window ▸ Debugging ▸ Call Stack): thấy chồng `mergeSort` → `mergeSort` → `merge` — chỉ cho thầy **thứ tự** chia xong mới trộn |
| Bước | **F5** (Continue) mỗi lần tới breakpoint = 1 lần trộn (đúng các dòng 4, 5, 6, 8, 9, 10 của bảng 2.2); **F8** trong `merge` để thấy từng số được lấy |

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
| Sao `merge` tính lại `mid`? | Để hàm chỉ 2 tham số (V4). Cùng công thức `(left + right) / 2` nên ra **đúng** điểm cắt `mergeSort` đã dùng. |
| Sắp giảm dần sửa gì? | Trong `merge`: `<=` → `>=`. |

### OOP / Java

| Câu hỏi | Trả lời mẫu |
|---|---|
| 4 tính chất OOP ở đâu? | **Đóng gói**: `values` `private` trong `NumberArray`, chỉ ghi qua `setValue`/`setValues`; field `array` và hàm `mergeSort`/`merge` `private` trong `MergeSortStrategy`. **Kế thừa**: `MergeSortStrategy implements SortStrategy`; `NumberArray` kế thừa `Object`. **Đa hình**: `SortService` gọi `sortStrategy.sort(array)` — biến kiểu interface, chạy bản của `MergeSortStrategy`; `toString()` có `@Override`. **Trừu tượng**: interface `SortStrategy` chỉ nói *"sắp được"*, giấu hẳn chuyện đệ quy và mảng phụ. |
| Sao `sort` không tự đệ quy mà cần `mergeSort`? | Chữ ký `sort(NumberArray)` do interface quy định — không nhận `left`/`right`. Hàm phụ `private` mới cần đoạn. |
| `merged` là biến cục bộ, `array` là field — sao khác nhau? | `merged` chỉ sống trong 1 lần trộn; `array` phải dùng chung cho **mọi** lời gọi đệ quy. |
| Interface khác abstract class? | Interface chỉ là **hợp đồng** (ở đây 1 hàm); các thuật toán không chung dòng code nào → interface. |
| Không có ArrayList/List trong bài? | Đúng — dùng **mảng `int[]`** vì kích thước biết trước. (List vs ArrayList: `List` là interface, `ArrayList` là lớp cụ thể dùng mảng động; bộ lời giải khai kiểu cụ thể — QUY-TAC-THAY §9 V5.) |

### Access modifier và static — từng chỗ không `private` (thầy V2, V3)

| Thành phần | Vì sao như vậy |
|---|---|
| Mọi **field** (`values`, `size`, `unsorted`, `sorted`, `array`, `sortStrategy`, `random`, `sortService`, `sortView`, `response`) | `private` — chỉ lớp đó được đụng (đóng gói) |
| `MergeSortStrategy.array` — **không** `static` | mỗi đối tượng strategy giữ mảng **của nó**; `static` thì mọi đối tượng dùng chung một mảng — sai nghĩa và bị thầy bắt (V3) |
| `MergeSortStrategy.mergeSort(int, int)`, `merge(int, int)` | **`private`** — hàm phụ, chỉ lớp này gọi |
| `NumberArray()`, get/set `values` | `public` — chuẩn **JavaBean** (V10) |
| `NumberArray(int[])`, `getSize`, `getValue`, `setValue`, `toString` | `public` — `SortService`/`MergeSortStrategy` ở package **khác** (`service`) gọi |
| `SortStrategy.sort` | hàm interface **luôn public** |
| `MergeSortStrategy.sort` | `public` — ghi đè hàm public của interface (không được thu hẹp) |
| `SortService(...)`, `sortRandomArray` | `public` — controller gọi |
| `SortService.generateArray` | **`private`** — chỉ `sortRandomArray` dùng |
| `SortController()`, `sortArray` | `public` — `Main` gọi |
| `SortView.setResponse`, `display` | `public` — controller gọi |
| get/set của 2 DTO, ctor rỗng | `public` — JavaBean; main/service/view gọi |
| `Validation.getSize` — `public static` | không dùng dữ liệu đối tượng nào; **bỏ `static`** thì phải bỏ constructor `private` và `new Validation()` trong `Main` |
| Constructor `private` của `Validation`, `Message`, `Constants` | chặn `new` — lớp chỉ chứa hàm/hằng static |
| `Message.X`, `Constants.X` — `public static final` | hằng dùng chung, không đổi; gọi qua tên lớp |
| `Main.main` — `public static` | JVM gọi mà không tạo đối tượng `Main` |
| `Main.inputSize` — `private static` | Guide: *"cấm static với biến, có thể dùng với hàm"*; `main` static nên hàm nó gọi thẳng cũng phải static. Bỏ `static` → phải `new Main().inputSize(sc)` |

### Kiểu trả về

| Hàm | Trả | Vì sao |
|---|---|---|
| `sort`, `mergeSort`, `merge` | `void` | kết quả được **chép về chính mảng** (tham chiếu) — không có gì mới để trả |
| `sortRandomArray` | `SortResponseDTO` | view cần **2 chuỗi** cùng lúc → gói vào 1 DTO |
| `getSize`, `getValue` | `int` | số đếm / phần tử nguyên |
| `toString` | `String` | model **không được in** — trả chữ để view in |

---

## 8. Thầy đổi yêu cầu tại chỗ

| Thầy bảo | Sửa file | Không đụng |
|---|---|---|
| Đổi sang **thuật toán khác** | thêm `service/XSortStrategy.java` + 1 dòng `new SortService(new XSortStrategy())` trong `SortController` | `SortService`, `Main`, `View`, DTO |
| Sắp **giảm dần** | `MergeSortStrategy.merge`: `<=` → `>=` | mọi file khác |
| Chỉ cấp **1 mảng phụ** cho cả lần sắp | field `int[] buffer` trong `MergeSortStrategy`, tạo 1 lần trong `sort()`; `merge` ghi vào `buffer` thay vì `new int[...]` | mọi file khác |
| Số ngẫu nhiên trong `[-n, n]` | `SortService.generateArray`: `random.nextInt(2 * size + 1) - size` | strategy, view |
| In thêm **số lần trộn** | field đếm trong `MergeSortStrategy` + `sort` trả `int` (sửa interface) → field mới trong `SortResponseDTO` → `SortService` gán → `SortView` in thêm dòng (+ nhãn trong `Message`) | `Main`, `Validation` |

---

## 9. Chỗ khác với đề / lời giải cũ trên web

| Chỗ | Bản cũ | Bài này | Lý do |
|---|---|---|---|
| Dòng tiêu đề | `===== Merge Sort Program =====` | không có | ảnh màn hình đề **không có** dòng này |
| Prompt | `Please input the number of array: ` | `Enter number of array:` (xuống dòng) | **đúng ảnh màn hình đề** — nên test đặt `REPLACE_REFERENCE = True` |
| Khoảng số ngẫu nhiên | `[0, 100)` | `[0, n)` | đề: *"random integer in number range input"*, ví dụ n=10 → 0..9 |
| Kiến trúc | `bo/ui/utils`, Scanner trong `Validator` | MVC theo Guide + Strategy, Scanner **chỉ ở `main`** | luật thầy |
| Hàm đệ quy / trộn | `mergeSort(array, buffer, low, high)` · `merge(array, buffer, low, mid, high)` — 4–5 tham số | `mergeSort(left, right)` · `merge(left, right)` + field `array` | thầy: *"không được truyền 3 tham số 1 hàm"* (V4) |
| Mảng phụ | 1 mảng `buffer` cấp một lần, dùng lại | mỗi lần trộn `new int[right - left + 1]` | dễ hiểu hơn cho người học; tổng cấp phát nhiều lần hơn nhưng lúc nào cũng chỉ **1** mảng phụ đang dùng (các lần trộn không lồng nhau). Bản 1-buffer ở mục 8 |
| Chặn mảng rỗng | `if (array.length < 2) return;` | không có | `Validation` bắt `size ≥ 1`; `mergeSort(0, 0)` dừng ngay ở `left >= right` |
| Thông báo lỗi | `You must input a number.` · `Number must be between 1 and 1000.` | giữ nguyên | đề không ghi câu lỗi; giữ đúng bản cũ đã kiểm |
