# J1.S.P0004 — Quick Sort

> Bài anh em của **P0001 Bubble Sort**: cùng khung MVC, cùng màn hình, cùng Strategy. Chỉ khác
> **một lớp**: `QuickSortStrategy` — và đây là bài **đệ quy** (recursion). Thuật toán vẫn phải làm
> MVC — thầy: *"Các bài liên quan thuật toán như fibo, sắp xếp,… cũng phải làm MVC."* Bản 21/09/2026
> đã sửa theo **tờ checklist giấy 25 mục** của thầy — xem mục 10 cuối bài.

| | |
|---|---|
| Loại / LOC | Short Assignment · 70 LOC · 1 slot |
| Project | `HE176322_J1SP0004_QuickSort` |
| Chạy | NetBeans: **File ▸ Open Project** → **F6** |
| Lớp chạy | `main.Main` |
| Kiểm tự động | `python3 _tools/verify.py J1SP0004` → 7 kịch bản × 2 locale |

---

## 1. Đề bài nói gì

- Nhập **số phần tử** của mảng (số nguyên dương).
- Sinh mảng **số ngẫu nhiên "in number range input"** — nhập `10` → các số từ `0` đến `9`.
- In mảng **trước** và **sau** khi sắp xếp bằng **quick sort**.

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
| Pivot | *"the value of the **middle** element"* | `workingArray.getValue((low + high) / 2)` |
| Phân hoạch | 2 chỉ số `i` (đầu) và `j` (cuối); `i` tiến tới số **≥ pivot**, `j` lùi tới số **≤ pivot**; `i ≤ j` thì đổi, `i+1`, `j-1`; dừng khi `i > j` | `QuickSortStrategy.sortByQuick` |
| Đệ quy | sắp tiếp phần trái và phải | `sortByQuick(low, j)` · `sortByQuick(i, high)` |

Đề **không bắt** tên lớp hay tên hàm nào — mọi tên trong bài là của lời giải, đặt theo tờ checklist.

---

## 2. Kiến thức cần biết

### 2.1 Quick sort — ý tưởng (đúng lời đề: chia để trị)

> 1. **Chọn pivot** = giá trị phần tử **giữa**.
> 2. **Phân hoạch**: dồn số **nhỏ hơn pivot** sang trái, số **lớn hơn** sang phải (số bằng pivot ở
>    bên nào cũng được; hai phần có thể không bằng nhau).
> 3. **Sắp hai phần** bằng chính quick sort (đệ quy).

Vì sao đúng (đề, *"Why does it work?"*): sau phân hoạch, mọi số bên trái **≤ pivot ≤** mọi số bên
phải; sắp xong từng bên thì cả mảng đã sắp — **không cần trộn** gì thêm.

### 2.2 Chạy tay ví dụ của đề: `{1, 12, 5, 26, 7, 14, 3, 7, 2}` — lần phân hoạch đầu

`low = 0`, `high = 8`, pivot = giá trị ở ô `(0 + 8) / 2 = 4` → **7**.

| Bước | `i` tiến tới (số ≥ 7) | `j` lùi tới (số ≤ 7) | Làm gì | Mảng sau bước |
|---|---|---|---|---|
| đầu | `i = 0` | `j = 8` | | `1 12 5 26 7 14 3 7 2` |
| 1 | bỏ `1` → `i = 1` (**12**) | `j = 8` (**2**) | `12 ≥ 7 ≥ 2`, `i ≤ j` → đổi, `i = 2`, `j = 7` | `1 2 5 26 7 14 3 7 12` |
| 2 | bỏ `5` → `i = 3` (**26**) | `j = 7` (**7**) | `26 ≥ 7 ≥ 7` → đổi, `i = 4`, `j = 6` | `1 2 5 7 7 14 3 26 12` |
| 3 | `i = 4` (**7**) | `j = 6` (**3**) | `7 ≥ 7 ≥ 3` → đổi, `i = 5`, `j = 5` | `1 2 5 7 3 14 7 26 12` |
| 4 | `i = 5` (**14**) | bỏ `14` → `j = 4` (**3**) | `i = 5 > j = 4` → **dừng phân hoạch** | `1 2 5 7 3 \| 14 7 26 12` |

Khớp từng hàng với hình của đề. Phần trái `0..j = 0..4` là `{1, 2, 5, 7, 3}`, phần phải
`i..high = 5..8` là `{14, 7, 26, 12}` — đúng hai mảnh đề ghi *"are sorted then recursively"*.

### 2.3 Các lời gọi đệ quy tiếp theo (đề bỏ qua, đây là đầy đủ)

| Lời gọi | Đoạn | Pivot | Sau phân hoạch | `i`, `j` | Gọi tiếp |
|---|---|---|---|---|---|
| `sortByQuick(0, 8)` | `1 12 5 26 7 14 3 7 2` | 7 | `1 2 5 7 3 \| 14 7 26 12` | 5, 4 | `(0, 4)` và `(5, 8)` |
| `sortByQuick(0, 4)` | `1 2 5 7 3` | 5 | `1 2 3 \| 7 5` | 3, 2 | `(0, 2)` và `(3, 4)` |
| `sortByQuick(0, 2)` | `1 2 3` | 2 | `1 \| 2 \| 3` (đổi 2 với chính nó) | 2, 0 | không (`0 < 0` sai, `2 < 2` sai) |
| `sortByQuick(3, 4)` | `7 5` | 7 | `5 \| 7` | 4, 3 | không |
| `sortByQuick(5, 8)` | `14 7 26 12` | 7 | `7 \| 14 26 12` | 6, 5 | chỉ `(6, 8)` |
| `sortByQuick(6, 8)` | `14 26 12` | 26 | `14 12 \| 26` | 8, 7 | chỉ `(6, 7)` |
| `sortByQuick(6, 7)` | `14 12` | 14 | `12 \| 14` | 7, 6 | không |

Kết quả: `1 2 3 5 7 7 12 14 26` — đúng dòng *"sorted"* của hình đề.

### 2.4 Chi tiết thầy hay soi

| Chi tiết | Code | Vì sao |
|---|---|---|
| Pivot giữ **giá trị**, không giữ **chỉ số** | `int pivot = workingArray.getValue((low + high) / 2);` | lệnh `swap` có thể dời chính ô giữa đi chỗ khác (bước 2 ở trên: số 7 ở ô 4 bị đổi) |
| `i` dừng ở số **≥** pivot (vòng `< pivot`) | `while (workingArray.getValue(i) < pivot)` | pivot nằm trong đoạn nên `i` **chắc chắn dừng** trước khi chạy ra ngoài; viết `<=` thì mảng toàn số ≤ pivot sẽ chạy quá `high` |
| Đổi cả khi `i == j` | `if (i <= j)` | để `i`, `j` vẫn bước qua nhau → vòng `while (i <= j)` kết thúc (ví dụ `sortByQuick(0, 2)`) |
| Chỉ gọi đệ quy khi còn ≥ 2 số | `if (low < j)` · `if (i < high)` | đây là **điểm dừng** của đệ quy |
| Hàm đệ quy chỉ 2 tham số | `private void sortByQuick(int low, int high)` | thầy: *"không được truyền 3 tham số 1 hàm"* (V4) → mảng giữ ở field `workingArray`, gán trong `sort()` |

### 2.5 Độ phức tạp

| Trường hợp | Thời gian | Khi nào |
|---|---|---|
| Trung bình | **O(n log n)** | pivot chia đoạn gần đôi → khoảng `log₂ n` tầng, mỗi tầng quét `n` số |
| Tốt nhất | O(n log n) | pivot luôn là trung vị — mảng **đã sắp** hoặc **ngược hẳn** rơi vào đây nhờ lấy pivot ở **giữa** |
| Xấu nhất | **O(n²)** | pivot lần nào cũng là số nhỏ nhất/lớn nhất của đoạn → mỗi lần chỉ tách ra 1 số |

Bộ nhớ thêm: chỉ ngăn xếp đệ quy (trung bình `log n` tầng). **Không ổn định**: `[2a, 2b, 1]` →
pivot `2b`, đổi `2a` ↔ `1` → `[1, 2b, 2a]`.

### 2.6 Java dùng trong bài

| API / khái niệm | Dùng làm gì |
|---|---|
| **Đệ quy** | hàm `sortByQuick` tự gọi lại chính nó với đoạn nhỏ hơn |
| `workingArray = numberArray` | gán tham số vào field **khác tên** — hàm đệ quy dùng field; khác tên để tham số không "che" field (tờ checklist 3.2) |
| `new Random().nextInt(n)` | số ngẫu nhiên trong `[0, n)` |
| `Arrays.toString(arr)` | ra chữ `[2, 6, 3]` đúng định dạng màn hình |
| `Integer.parseInt(s)` | chuỗi → số; sai → `NumberFormatException` |
| `String.format("Sorted array: %s", chuỗi)` | ghép nhãn với mảng **không** cộng chuỗi (tờ checklist 3.8) |

---

## 3. Thiết kế

```
HE176322_J1SP0004_QuickSort/src/
├── model/       NumberArray        JavaBean: int[] valueArray + getSize/getValue/swap/toString
├── repository/  NumberRepository   GIỮ mảng số: NumberArray numberArray + saveNumberArray/getNumberArray
├── dto/         SortRequestDTO     size                          (main ──► controller)
│                SortResponseDTO    unsortedArray, sortedArray    (controller ──► view)
├── service/     ISortStrategy      interface: void sort(NumberArray)            ← Strategy
│                QuickSortStrategy  sort() + sortByQuick(low, high) đệ quy       ← ConcreteStrategy
│                SortService        sinh số + cất vào repository + gọi strategy  ← Context
├── controller/  SortController     new SortService(new QuickSortStrategy()) → view, render 1 lần
├── view/        SortView           thuộc tính responseDTO + setResponseDTO + display()
├── constants/   Message, Constants câu chữ; MIN_SIZE = 1, MAX_SIZE = 1000
├── utils/       Validation         getSize(chuỗi) → int hoặc ném lỗi
└── main/        Main               final + private Main(); Scanner + gọi controller 1 lần
```

| Câu hỏi thiết kế | Trả lời |
|---|---|
| Sao bài thuật toán lại có `repository`? | Tờ checklist 1.1: *"**Bắt buộc phải có repository**"*. `NumberRepository` giữ **dữ liệu mà thuật toán làm việc** (mảng số) với CRUD đơn giản: `saveNumberArray` (Create), `getNumberArray` (Read). Không sắp, không in. |
| Vậy thuật toán nằm đâu? | Ở `service` (Guide: tính toán nghiệp vụ → Services, *"Services nằm giữa Controller và Repo"*). `SortService` **lấy mảng từ repository** rồi giao cho strategy sắp. |
| Sao `QuickSortStrategy` có field `workingArray`? | Đệ quy cần biết **mảng + 2 đầu đoạn** = 3 thứ, nhưng thầy cấm hàm 3 tham số (V4). `sort(numberArray)` gán mảng vào field `private`, hàm đệ quy chỉ nhận `low`, `high`. |
| Sao `sortByQuick(int, int)` là `private`? | chỉ `sort()` và chính nó gọi; bên ngoài chỉ cần hợp đồng `sort(NumberArray)` |
| Sao controller không đụng `NumberArray`? | Tờ checklist 1.1: controller *"không làm việc với Model"*; Guide: *"chỉ import DTO, View, Service"*. |

### 3.1 Design Pattern — Strategy

Thầy đánh giá cao nhất **Design Pattern** (QUY-TAC-THAY §9, V7). Trình bày đủ 4 yếu tố (slide Design
Pattern, *"Elements of a Design Pattern"*):

| Yếu tố | Trong bài này |
|---|---|
| **Name** | Strategy (nhóm Behavioral) |
| **Problem** | Có **nhiều thuật toán sắp xếp** (bubble, selection, insertion, quick, merge…) cùng làm một việc. Viết thẳng một thuật toán vào service thì đổi thuật toán = sửa service. |
| **Solution** | `ISortStrategy` (**Strategy**, interface 1 hàm `sort`; tên bắt đầu bằng `I` — tờ checklist 1.3) · `QuickSortStrategy` (**ConcreteStrategy**) · `SortService` (**Context**: giữ field `ISortStrategy`, nhận qua constructor, gọi `sortStrategy.sort(numberArray)`) · `SortController` là nơi **chọn** strategy: `new SortService(new QuickSortStrategy())`. |
| **Consequences** | ➕ Thêm thuật toán = **thêm 1 lớp** + sửa **1 dòng** ở controller; `SortService`, `Main`, `View` không đụng (**O** của SOLID). `SortService` phụ thuộc interface, không phụ thuộc lớp cụ thể (**D**). Chi tiết đệ quy (`low`, `high`, field `workingArray`) **giấu kín** trong strategy. ➖ Thêm 2 file so với viết thẳng. Riêng bài này: field `workingArray` làm một đối tượng `QuickSortStrategy` **không dùng được cho 2 mảng cùng lúc** (2 luồng) — chương trình console một luồng nên không sao. |

Ngoài ra: **MVC** (kiến trúc của thầy), `SortController` đóng vai **Facade** — `Main` chỉ gọi
`controller.sortArray(requestDTO)`, không biết có service/strategy/repository/view phía sau — và
**Repository**: `NumberRepository` là chỗ duy nhất giữ dữ liệu.

**Thầy bảo "đổi sang merge sort" — làm trong 2 phút:**
1. Tạo `service/MergeSortStrategy.java` `implements ISortStrategy`, viết `sort()` (cần thêm
   `setValue` vào `NumberArray`).
2. Sửa **đúng 1 dòng** trong `SortController`: `new SortService(new MergeSortStrategy())`.

**Luồng chạy** (Main → RequestDTO → Controller → Service → Repository → Model; ResponseDTO → View 1 lần):

```
Main: đọc size (hỏi lại khi sai) ──► SortRequestDTO ──► controller.sortArray(requestDTO)   ← gọi 1 lần
   controller ──► sortService.sortRandomArray(requestDTO)
                     ├─ generateValueArray(size)                 → int[] ngẫu nhiên trong [0, size)
                     ├─ numberRepository.saveNumberArray(...)    → repository gói vào NumberArray (model)
                     ├─ numberRepository.getNumberArray()        ← service lấy dữ liệu TỪ repository
                     ├─ responseDTO.setUnsortedArray(...)        ← chụp TRƯỚC khi sắp
                     ├─ sortStrategy.sort(numberArray)           → QuickSortStrategy.sort
                     │                                              ├─ workingArray = numberArray
                     │                                              └─ sortByQuick(0, size - 1) ─► đệ quy
                     └─ responseDTO.setSortedArray(...)
   controller ──► sortView.setResponseDTO(responseDTO) ──► sortView.display()   ← render 1 lần
```

### 3.2 SOLID trong bài

| Nguyên lý | Ở đâu |
|---|---|
| **S** | mỗi lớp 1 việc: `NumberArray` mô tả mảng, `NumberRepository` giữ mảng, `QuickSortStrategy` sắp, `SortService` điều phối, `SortView` in, `Validation` kiểm |
| **O** | thêm thuật toán = thêm lớp `implements ISortStrategy`, không sửa `SortService` |
| **L** | mọi `ISortStrategy` thay được cho nhau: đều sắp tăng dần, tại chỗ |
| **I** | `ISortStrategy` chỉ có **1 hàm** |
| **D** | `SortService` giữ field kiểu **interface** `ISortStrategy`, nhận qua constructor |

---

## 4. Code từng bước

> Thầy (QUY-TAC-THAY §9, V8): *"code cái model trước rồi đến data"*.

| Bước | File | Việc |
|---|---|---|
| 1 | `model/NumberArray.java` | field `int[] valueArray` · ctor rỗng + ctor đủ · get/set `valueArray` · `getSize` · `getValue` · `swap` · `toString` |
| 2 | `repository/NumberRepository.java` | field `NumberArray numberArray` · ctor (mảng rỗng) · `saveNumberArray(int[] valueArray)` · `getNumberArray()` |
| 3 | `dto/SortRequestDTO.java`, `SortResponseDTO.java` | ctor rỗng + field + get/set (JavaBean) |
| 4 | `service/ISortStrategy.java` | interface: `void sort(NumberArray numberArray);` |
| 5 | `service/QuickSortStrategy.java` | field `workingArray` · `sort()` gán field rồi `sortByQuick(0, size - 1)` · **`sortByQuick(low, high)`**: pivot → `while (i <= j)` với 2 vòng con + `if` đổi → 2 lời gọi đệ quy |
| 6 | `service/SortService.java` | field `sortStrategy`, `numberRepository`, `random` + ctor nhận strategy · `generateValueArray` · `sortRandomArray` |
| 7 | `controller/SortController.java` | `new SortService(new QuickSortStrategy())` · `sortArray(requestDTO)`: `setResponseDTO` rồi `display()` **1 lần** |
| 8 | `view/SortView.java` | field `responseDTO` · `setResponseDTO` · `display()` **không tham số** |
| 9 | `constants/Message.java`, `Constants.java` | prompt, 2 lỗi, 2 dòng kết quả `"…: %s"` · `MIN_SIZE`, `MAX_SIZE` |
| 10 | `utils/Validation.java` | `getSize(String)` — **tách 2 lỗi** |
| 11 | `main/Main.java` | `public final class` + `private Main()`; `inputSize` (vòng hỏi lại) + gọi `controller.sortArray` **1 lần** |

**Bẫy hay gặp:**

1. Giữ **chỉ số** pivot (`int mid = ...` rồi so `workingArray.getValue(mid)` trong vòng) → sau một lần
   đổi, ô `mid` không còn chứa pivot → phân hoạch sai. Phải lấy **giá trị** một lần trước vòng.
2. Viết `while (workingArray.getValue(i) <= pivot)` → `i` chạy quá `high` → `ArrayIndexOutOfBoundsException`.
3. Quên `i++; j--;` sau khi đổi → vòng lặp vô tận khi hai số bằng pivot.
4. Gọi đệ quy `sortByQuick(low, i)` / `sortByQuick(j, high)` (đảo `i`, `j`) → sai hoặc đệ quy vô tận
   (`StackOverflowError`). Trái là `low..j`, phải là `i..high`.
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
| 8 | `1` | mảng 1 phần tử, hai dòng giống nhau (`sortByQuick(0, 0)`: đổi số với chính nó, không gọi đệ quy) |
| 9 | `2` | 2 số, dòng Sorted tăng dần |
| 10 | `1000` | dòng Sorted tăng dần, **nhiều số trùng** (1000 số lấy từ 0..999) — chứng minh `i`/`j` dừng ở số bằng pivot vẫn đúng |

---

## 6. Debug

| Việc | Cách làm |
|---|---|
| Breakpoint | dòng `int pivot = workingArray.getValue((low + high) / 2);` trong `QuickSortStrategy.sortByQuick` |
| Chạy | **Ctrl+F5**, nhập `8` |
| Vào thuật toán | breakpoint ở `sortStrategy.sort(numberArray);` trong `SortService`, **F7** → nhảy vào `QuickSortStrategy.sort` (đa hình qua interface) |
| Quan sát | tab **Variables**: `low`, `high`, `pivot`, `i`, `j`; mở `this` → `workingArray` → `valueArray` |
| Đệ quy | cửa sổ **Call Stack** (Window ▸ Debugging ▸ Call Stack): mỗi lần tới breakpoint thấy thêm/bớt một dòng `sortByQuick` — chỉ cho thầy **đệ quy sâu bao nhiêu tầng** |
| Bước | **F8** trong vòng `while (i <= j)`; **F7** tại `sortByQuick(low, j)` để vào lời gọi con; **Ctrl+F7** (Step Out) để quay lên tầng trên |
| Thấy repository | **F7** vào `numberRepository.saveNumberArray(...)` — mảng vừa sinh được gói thành `NumberArray` và cất lại |

---

## 7. Câu hỏi thầy hay hỏi

### Thuật toán

| Câu hỏi | Trả lời mẫu |
|---|---|
| Sau một lần phân hoạch, điều gì chắc chắn? | Mọi số ở `low..j` **≤ pivot ≤** mọi số ở `i..high` (đề: *"all values before i-th element are less or equal than the pivot"*). |
| Sao lấy pivot ở **giữa**, không lấy phần tử đầu? | Đề bảo vậy. Và nếu lấy phần tử đầu, mảng **đã sắp** sẽ luôn tách ra 1 số → **O(n²)**; lấy giữa thì mảng đã sắp lại là trường hợp **tốt nhất**. |
| Đệ quy dừng khi nào? | Khi đoạn còn **0 hoặc 1** số: `if (low < j)` và `if (i < high)` sai thì không gọi nữa. |
| Độ phức tạp? | Trung bình **O(n log n)**, xấu nhất **O(n²)**; bộ nhớ thêm là ngăn xếp đệ quy. |
| Khác merge sort? | Quick **làm việc trước** khi đệ quy (phân hoạch) rồi không phải trộn; merge chia **mù** ở giữa rồi **làm việc sau** (trộn). Quick không cần mảng phụ; merge luôn O(n log n) và ổn định. |
| Có ổn định không? | **Không** — `[2a, 2b, 1]` → `[1, 2b, 2a]`. |
| Sắp giảm dần sửa gì? | Đảo 2 vòng con: `while (workingArray.getValue(i) > pivot)` và `while (workingArray.getValue(j) < pivot)`. |
| `(low + high) / 2` có tràn số không? | Chỉ khi `low + high > 2³¹ - 1` (~2 tỉ); ở đây `MAX_SIZE = 1000` nên không thể. Viết an toàn tuyệt đối: `low + ((high - low) / 2)`. |

### Kiến trúc theo tờ checklist

| Câu hỏi | Trả lời mẫu |
|---|---|
| Sao bài có repository? | Tờ checklist 1.1 *"Bắt buộc phải có repository"*. `NumberRepository` giữ **dữ liệu** (mảng số) và chỉ có CRUD đơn giản: `saveNumberArray` tạo `NumberArray` từ các số vừa sinh, `getNumberArray` trả nó ra. Tính toán ở `SortService` + strategy — luồng **Controller → Service → Repository → Model**. |
| View nhận dữ liệu thế nào? | **Qua thuộc tính**: `SortView` có field `private SortResponseDTO responseDTO` + setter `setResponseDTO(...)`; `display()` không tham số. Controller gọi `setResponseDTO(responseDTO)` rồi `display()` **đúng 1 lần**. |
| Validate ở đâu? | Ở **Main**: `Main.inputSize` đọc `sc.nextLine()`, đưa cho `Validation.getSize` — sai thì ném `Exception(Message.X)`, Main bắt, in `e.getMessage()` rồi hỏi lại. Controller/service chỉ nhận `SortRequestDTO` đã sạch. |
| Sao `Main` là `final` và có `private Main()`? | Tờ checklist 3.4: lớp chỉ có hàm `static` phải có private constructor và khai báo `final`. |
| Sao interface tên `ISortStrategy`? | Tờ checklist 1.3: *"Tên của interface bắt đầu bằng "I""*. |

### OOP / Java

| Câu hỏi | Trả lời mẫu |
|---|---|
| 4 tính chất OOP ở đâu? | **Đóng gói**: `valueArray` `private` trong `NumberArray`, chỉ đổi qua `swap`/`setValueArray`; field `workingArray` và hàm `sortByQuick` `private` trong `QuickSortStrategy`; `numberArray` `private` trong `NumberRepository`. **Kế thừa**: `QuickSortStrategy implements ISortStrategy`; `NumberArray` kế thừa `Object`. **Đa hình**: `SortService` gọi `sortStrategy.sort(numberArray)` — biến kiểu interface, chạy bản của `QuickSortStrategy`; `toString()` có `@Override`. **Trừu tượng**: interface `ISortStrategy` chỉ nói *"sắp được"*, giấu hẳn chuyện đệ quy. |
| Sao `sort` không tự đệ quy mà cần `sortByQuick`? | Chữ ký `sort(NumberArray)` do interface quy định — không nhận `low`/`high`. Hàm phụ `private` mới cần đoạn. |
| Sao field tên `workingArray` mà không trùng tên tham số `numberArray`? | Tham số trùng tên field sẽ **che** field (phải viết `this.` mới tới field) — tờ checklist 3.2 *"Không khai báo biến local trùng tên với biến higher level"*. Đặt khác tên: `workingArray = numberArray;`. |
| Interface khác abstract class? | Interface chỉ là **hợp đồng** (ở đây 1 hàm); các thuật toán không chung dòng code nào → interface. |
| Không có ArrayList/List trong bài? | Đúng — dùng **mảng `int[]`** vì kích thước biết trước. (List vs ArrayList: `List` là interface, `ArrayList` là lớp cụ thể dùng mảng động; bộ lời giải khai kiểu cụ thể — QUY-TAC-THAY §9 V5.) |

### Access modifier và static — từng chỗ không `private` (thầy V2, V3)

| Thành phần | Vì sao như vậy |
|---|---|
| Mọi **field** (`valueArray`, `numberArray`, `size`, `unsortedArray`, `sortedArray`, `workingArray`, `sortStrategy`, `numberRepository`, `random`, `sortService`, `sortView`, `responseDTO`) | `private` — chỉ lớp đó được đụng (đóng gói) |
| `QuickSortStrategy.workingArray` — **không** `static` | mỗi đối tượng strategy giữ mảng **của nó**; `static` thì mọi đối tượng dùng chung một mảng — sai nghĩa và bị thầy bắt (V3) |
| `QuickSortStrategy.sortByQuick(int, int)` | **`private`** — hàm phụ, chỉ lớp này gọi |
| `NumberArray()`, get/set `valueArray` | `public` — chuẩn **JavaBean** (V10) |
| `NumberArray(int[])` | `public` — `NumberRepository` ở package **khác** (`repository`) gọi |
| `getSize`, `getValue`, `swap`, `toString` | `public` — `SortService`/`QuickSortStrategy` ở package **khác** (`service`) gọi |
| `NumberRepository()`, `saveNumberArray`, `getNumberArray` | `public` — `SortService` gọi |
| `ISortStrategy.sort` | hàm interface **luôn public** |
| `QuickSortStrategy.sort` | `public` — ghi đè hàm public của interface (không được thu hẹp) |
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
| `sort(NumberArray)`, `sortByQuick(int, int)` | `void` | đổi **chính mảng** (tham chiếu) tại chỗ — không có gì mới để trả; mảng trong repository cũng đã sắp vì là cùng một đối tượng |
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
| Pivot = phần tử **đầu** / **cuối** | `QuickSortStrategy.sortByQuick`: `workingArray.getValue(low)` / `workingArray.getValue(high)` | mọi file khác |
| Sắp **giảm dần** | 2 vòng con trong `sortByQuick`: `<` ↔ `>` | mọi file khác |
| Số ngẫu nhiên trong `[-n, n]` | `SortService.generateValueArray`: `random.nextInt((2 * size) + 1) - size` | strategy, view, repository |
| In thêm **số lần đổi chỗ** | field đếm trong `QuickSortStrategy` + `sort` trả `int` (sửa interface) → field mới trong `SortResponseDTO` → `SortService` gán → `SortView.display()` in thêm dòng (+ câu trong `Message`) | `Main`, `Validation` |

---

## 9. Chỗ khác với đề / lời giải cũ trên web

| Chỗ | Bản cũ | Bài này | Lý do |
|---|---|---|---|
| Dòng tiêu đề | `===== Quick Sort Program =====` | không có | ảnh màn hình đề **không có** dòng này |
| Prompt | `Please input the number of array: ` | `Enter number of array:` (xuống dòng) | **đúng ảnh màn hình đề** — nên test đặt `REPLACE_REFERENCE = True` |
| Khoảng số ngẫu nhiên | `[0, 100)` | `[0, n)` | đề: *"random integer in number range input"*, ví dụ n=10 → 0..9 |
| Kiến trúc | `bo/ui/utils`, Scanner trong `Validator` | MVC theo Guide + Strategy, Scanner **chỉ ở `main`** | luật thầy |
| Hàm đệ quy | `quickSort(int[] array, int low, int high)` — 3 tham số | `sortByQuick(int low, int high)` + field `workingArray` | thầy: *"không được truyền 3 tham số 1 hàm"* (V4) |
| Chặn mảng rỗng | `if (array.length < 2) return;` | không có | `Validation` bắt `size ≥ 1`; với 1 phần tử `sortByQuick(0, 0)` chạy đúng (bảng test #8) |
| Công thức giữa | `low + (high - low) / 2` | `(low + high) / 2` | dễ đọc; không tràn vì `MAX_SIZE = 1000` (mục 7) |
| Thông báo lỗi | `You must input a number.` · `Number must be between 1 and 1000.` | giữ nguyên | đề không ghi câu lỗi; giữ đúng bản cũ đã kiểm |

### 9.1 Đổi theo tờ checklist giấy (21/09/2026)

Màn hình chạy **không đổi một chữ** (`verify.py` so từng dòng, cả en_US và vi_VN).

| Chỗ | Bản trước | Bây giờ | Mục checklist |
|---|---|---|---|
| Tầng dữ liệu | không có `repository` | `repository/NumberRepository` giữ `NumberArray` | 1.1 |
| View | `setResponse(response)` | field `responseDTO` + `setResponseDTO(...)` + `display()` | 1.1 |
| Main | `public class Main` | `public final class Main` + `private Main()` | 3.4 |
| Interface | `SortStrategy` | `ISortStrategy` (đổi tên tệp, lớp, mọi chỗ dùng) | 1.3 |
| Tên hàm | `quickSort`, `generateArray` | `sortByQuick`, `generateValueArray` | 1.4 |
| Field của strategy | `array` + `this.array = array` (tham số che field) | `workingArray = numberArray` | 3.2 |
| Tên mảng | `int[] values`, `getValues/setValues` | `int[] valueArray`, `getValueArray/setValueArray` | 1.5 |
| Khai báo biến | `String line = sc.nextLine();` giữa vòng lặp; `int size;` chưa gán | `String line = "";` đầu hàm; `int size = 0;` | 2.6, 3.7 |
| Ngoặc | `size < MIN_SIZE \|\| size > MAX_SIZE` | `(size < …) \|\| (size > …)` | 3.3 |
| In nhãn | `Message.LABEL_SORTED + chuỗi` | `String.format(Message.SORTED_ARRAY, chuỗi)` | 3.8 |
| Dòng trống | comment của các hằng/field dính nhau; khối sau `}` dính nhau | 1 dòng trống trước mỗi comment, sau vùng khai báo, sau mỗi `}` | 2.8 |

---

## 10. Tờ checklist 25 mục — bài này đạt thế nào

| Mục | Chỉ vào đâu |
|---|---|
| 1.1 | `repository/NumberRepository` (bắt buộc có); `SortController` chỉ import `dto`/`service`/`view`; `SortView` nhận `responseDTO` qua setter, `display()` gọi 1 lần; `Main` gọi `controller.sortArray` 1 lần |
| 1.3 / 1.4 | interface `ISortStrategy`; hàm mở đầu bằng động từ: `sortByQuick`, `generateValueArray`, `saveNumberArray`, `sortRandomArray` |
| 1.5 | biến/field kiểu mảng đuôi `Array`: `int[] valueArray` (`NumberArray`, `SortService.generateValueArray`) |
| 2.6 + 3.7 | `String line = "";` (`Main.inputSize`), `int size = 0;` (`Validation.getSize`), `NumberArray numberArray = null;` (`SortService.sortRandomArray`), `i`/`j`/`pivot` đầu `sortByQuick` |
| 2.8 | 1 dòng trống giữa các hằng/field có comment, sau vùng khai báo biến, sau mỗi `}` trước câu lệnh kế |
| 3.3 | `if ((size < Constants.MIN_SIZE) \|\| (size > Constants.MAX_SIZE))` |
| 3.4 | `public final class Main` + `private Main()`; `Validation`, `Message`, `Constants` cũng `final` + private constructor |
| Soát máy | `checklist_audit.py` → **0 VI_PHAM**; còn RUI_RO: `String[] args` của `main` và tham số setter/constructor trùng tên field (`this.sortStrategy = sortStrategy` — IDE sinh) |
