# J1.S.P0003 — Insertion Sort

> Bài anh em của **P0001 Bubble Sort**: cùng khung MVC, cùng màn hình. Chỉ khác
> **một hàm**: `SortService.sortByInsertion`. Thuật toán vẫn phải làm MVC — thầy: *"Các bài liên quan
> thuật toán như fibo, sắp xếp,… cũng phải làm MVC, không OOP/MVC → không review."* Bản 21/09/2026 đã
> sửa theo **tờ checklist giấy 25 mục** của thầy — xem mục 10 cuối bài.

| | |
|---|---|
| Loại / LOC | Short Assignment · 40 LOC · 1 slot |
| Project | `HE176322_J1SP0003_InsertionSort` |
| Chạy | NetBeans: **File ▸ Open Project** → **F6** |
| Lớp chạy | `main.Main` |
| Kiểm tự động | `python3 _tools/verify.py J1SP0003` → 7 kịch bản × 2 locale |

---

## 1. Đề bài nói gì

- Nhập **số phần tử** của mảng (số nguyên dương).
- Sinh mảng **số ngẫu nhiên "in number range input"** — nhập `10` → các số từ `0` đến `9`.
- In mảng **trước** và **sau** khi sắp xếp bằng **insertion sort**.

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
| Thuật toán | lấy phần tử đầu phần chưa sắp, **chèn** vào đúng chỗ trong phần đã sắp; bản *"Shifting instead of swapping"* (đề: *"the most commonly used"*) | `SortService.sortByInsertion` |

Đề **không bắt** tên lớp hay tên hàm nào — mọi tên trong bài là của lời giải, đặt theo tờ checklist.

---

## 2. Kiến thức cần biết

### 2.1 Insertion sort — ý tưởng (đúng lời đề)

> Mảng chia làm 2 phần: **đã sắp** (lúc đầu chỉ có **phần tử đầu tiên**) và **chưa sắp**. Mỗi bước
> lấy **phần tử đầu phần chưa sắp** (gọi là `key`) và **chèn** nó vào đúng chỗ trong phần đã sắp.
> Phần chưa sắp rỗng → dừng. Giống cách xếp bài trên tay.

Hình của đề: trước `[≤ x | > x | x | …]` → sau `[≤ x | x | > x | …]` — mọi số **lớn hơn x** trong
phần đã sắp lùi sang phải 1 ô, `x` rơi vào khoảng trống.

### 2.2 Hai cách chèn đề nêu — bài này dùng cách 2

| Cách | Làm gì | Số lần ghi mỗi bước lùi |
|---|---|---|
| 1. "Sifting down" bằng **swap** | so `x` với số đứng trước, ngược thì **đổi chỗ**, lặp | 3 (swap) — `x` bị ghi đi ghi lại |
| 2. **Shifting** (bài này) | cất `x` vào `key`; số lớn hơn **dời** sang phải 1 ô; cuối cùng ghi `key` **1 lần** vào ô trống | 1 |

### 2.3 Chạy tay ví dụ của đề: `{7, -5, 2, 16, 4}`

`?` = ô trống đang chờ `key` (đúng ký hiệu trong hình của đề).

| i | key | So sánh | Làm gì | Mảng |
|---|---|---|---|---|
| — | | | chưa sắp | `7 -5 2 16 4` |
| 1 | `-5` | `7 > -5` | dời `7` sang phải | `? 7 2 16 4` |
| 1 | | `j = -1` | chạm biên trái → đặt `-5` vào ô 0 | `-5 7 2 16 4` |
| 2 | `2` | `7 > 2` | dời `7` | `-5 ? 7 16 4` |
| 2 | | `-5 < 2` | dừng → đặt `2` vào ô 1 | `-5 2 7 16 4` |
| 3 | `16` | `7 < 16` | dừng ngay → đặt `16` vào **chính ô 3** | `-5 2 7 16 4` |
| 4 | `4` | `16 > 4` | dời `16` | `-5 2 7 ? 16` |
| 4 | | `7 > 4` | dời `7` | `-5 2 ? 7 16` |
| 4 | | `2 < 4` | dừng → đặt `4` vào ô 2 | `-5 2 4 7 16` |

Khớp từng hàng với hình của đề.

### 2.4 Chi tiết thầy hay soi

| Chi tiết | Code | Vì sao |
|---|---|---|
| Vòng ngoài bắt đầu từ `1` | `for (int i = 1; i < size; i++)` | phần tử 0 **một mình** đã là phần đã sắp |
| Cất `key` trước khi dời | `int key = numberArray.getValue(i);` | lần dời đầu tiên **ghi đè** ô `i` — không cất là mất số |
| Điều kiện `j >= 0` đứng **trước** | `while ((j >= 0) && (numberArray.getValue(j) > key))` | `&&` dừng sớm: `j = -1` thì không đọc `getValue(-1)` (tránh `ArrayIndexOutOfBoundsException`). Mỗi phép so sánh một cặp ngoặc riêng — tờ checklist 3.3 |
| Đặt ở `j + 1` | `numberArray.setValue(j + 1, key);` | vòng `while` dừng khi `j` trỏ vào số **≤ key** (hoặc -1) → ô trống ngay bên phải nó |
| So `>` chứ không `>=` | `numberArray.getValue(j) > key` | số **bằng** key không bị dời → giữ thứ tự cũ → **ổn định** |

### 2.5 Độ phức tạp

| Trường hợp | Số phép so sánh | Ví dụ |
|---|---|---|
| Tốt nhất (đã sắp) | `n - 1` → **O(n)** — mỗi `key` so 1 lần là dừng | `1 2 3 4` |
| Xấu nhất (ngược hẳn) | `n(n-1)/2` → **O(n²)** | `4 3 2 1` |

Đề: insertion sort **được dùng thật** cho mảng nhỏ (ví dụ bên trong quicksort) — vì mảng gần sắp
thì nó gần O(n), và mỗi bước dời chỉ 1 lần ghi.

### 2.6 Java dùng trong bài

| API | Dùng làm gì |
|---|---|
| `new Random().nextInt(n)` | số ngẫu nhiên trong `[0, n)` |
| `Arrays.toString(arr)` | ra chữ `[2, 6, 3]` đúng định dạng màn hình |
| `Integer.parseInt(s)` | chuỗi → số; sai → `NumberFormatException` |
| `String.format("Sorted array: %s", chuỗi)` | ghép nhãn với mảng **không** cộng chuỗi (tờ checklist 3.8) |

---

## 3. Thiết kế

```
HE176322_J1SP0003_InsertionSort/src/
├── model/       NumberArray        JavaBean: int[] valueArray + getSize/getValue/setValue/toString
├── repository/  NumberRepository   GIỮ mảng số: NumberArray numberArray + saveNumberArray/getNumberArray
├── dto/         SortRequestDTO     size                          (main ──► controller)
│                SortResponseDTO    unsortedArray, sortedArray    (controller ──► view)
├── service/     SortService        sinh số + cất vào repository + sortByInsertion ← thuật toán của đề ở ĐÂY
├── controller/  SortController     new SortService() → view, render 1 lần
├── view/        SortView           thuộc tính responseDTO + setResponseDTO + display()
├── constants/   Message, Constants câu chữ; MIN_SIZE = 1, MAX_SIZE = 1000
├── utils/       Validation         getSize(chuỗi) → int hoặc ném lỗi
└── main/        Main               final + private Main(); Scanner + gọi controller 1 lần
```

| Câu hỏi thiết kế | Trả lời |
|---|---|
| Sao bài thuật toán lại có `repository`? | Tờ checklist 1.1: *"**Bắt buộc phải có repository**"*. `NumberRepository` giữ **dữ liệu mà thuật toán làm việc** (mảng số) với CRUD đơn giản: `saveNumberArray` (Create), `getNumberArray` (Read). Không sắp, không in. |
| Vậy thuật toán nằm đâu? | Ở `service` (Guide: tính toán nghiệp vụ → Services, *"Services nằm giữa Controller và Repo"*). `SortService` **lấy mảng từ repository** rồi chạy `sortByInsertion`. |
| Sao `NumberArray` có `setValue` mà không có `swap` như P0001? | Thuật toán **dời** (ghi 1 ô) chứ không **đổi chỗ**. Model chỉ giữ hàm mà bài thật sự dùng. |
| Sao controller không đụng `NumberArray`? | Tờ checklist 1.1: controller *"không làm việc với Model"*; Guide: *"chỉ import DTO, View, Service"*. |

### 3.1 Design Pattern trong bài

| Pattern | Ở đâu |
|---|---|
| **MVC** — thầy gọi là "MVC JSP" | controller điều hướng (như Servlet) · view hiển thị (như trang JSP) · model là JavaBean |
| **Facade** | controller: `Main` chỉ gọi `controller.sortArray(requestDTO)`, không biết service/repository/model/view phía sau |
| **Repository** | `NumberRepository` là **chỗ duy nhất giữ dữ liệu**; service lấy mảng từ đây |

> Bài chỉ 40 LOC nên **không thêm lớp pattern GoF** — ghi chú slide SOLID của thầy cảnh báo *"trừu tượng hoá sớm … vi phạm YAGNI"*. Thuật toán nằm gọn trong **một hàm** `sortByInsertion` của `SortService`.

**Thầy hỏi "dùng pattern gì cho dễ mở rộng?"** → trả lời đủ 4 yếu tố GoF (slide *"Elements of a Design Pattern"*):

| Yếu tố | Trả lời |
|---|---|
| **Name** | Strategy (nhóm Behavioral) |
| **Problem** | nhiều thuật toán sắp xếp cùng làm một việc; viết thẳng vào service thì đổi thuật toán = sửa service |
| **Solution** | tách `interface ISortStrategy { void sort(NumberArray numberArray); }` (tờ checklist 1.3: tên interface bắt đầu bằng `I`); mỗi cách làm là 1 lớp `implements` nó (`BubbleSortStrategy`, `SelectionSortStrategy`…); `SortService` nhận strategy qua constructor, controller chọn `new SortService(new XxxSortStrategy())` |
| **Consequences** | ➕ thêm cách làm = thêm 1 lớp, service đứng yên (**O**) · ➖ thêm 2 file — chỉ đáng khi có từ 2 cách trở lên (bài P0004 Quick sort, P0005 Merge sort làm đúng như vậy) |

**Luồng chạy** (Main → RequestDTO → Controller → Service → Repository → Model; ResponseDTO → View 1 lần):

```
Main: đọc size (hỏi lại khi sai) ──► SortRequestDTO ──► controller.sortArray(requestDTO)   ← gọi 1 lần
   controller ──► sortService.sortRandomArray(requestDTO)
                     ├─ generateValueArray(size)                 → int[] ngẫu nhiên trong [0, size)
                     ├─ numberRepository.saveNumberArray(...)    → repository gói vào NumberArray (model)
                     ├─ numberRepository.getNumberArray()        ← service lấy dữ liệu TỪ repository
                     ├─ responseDTO.setUnsortedArray(...)        ← chụp TRƯỚC khi sắp
                     ├─ sortByInsertion(numberArray)             ← thuật toán của đề (sắp tại chỗ)
                     └─ responseDTO.setSortedArray(...)
   controller ──► sortView.setResponseDTO(responseDTO) ──► sortView.display()   ← render 1 lần
```

### 3.2 SOLID trong bài

| Nguyên lý | Ở đâu |
|---|---|
| **S** | mỗi lớp 1 việc: `NumberArray` mô tả mảng · `NumberRepository` giữ mảng · `SortService` sinh + sắp · `SortView` in · `Validation` kiểm · `Message`/`Constants` giữ chữ/số |
| **O** | đổi câu chữ chỉ sửa `Message`; đổi thuật toán chỉ sửa **một hàm** `sortByInsertion` |
| **L** | bài chưa có lớp con riêng — chỉ `extends Object` và ghi đè `toString()` đúng nghĩa |
| **I** | không có interface — bài chưa cần (xem 3.1) |
| **D** | `Main` chỉ phụ thuộc controller + DTO; không biết service, repository hay model |

---

## 4. Code từng bước

> Thầy (QUY-TAC-THAY §9, V8): *"code cái model trước rồi đến data"*.

| Bước | File | Việc |
|---|---|---|
| 1 | `model/NumberArray.java` | field `int[] valueArray` · ctor rỗng + ctor đủ · get/set `valueArray` · `getSize` · `getValue` · `setValue` · `toString` |
| 2 | `repository/NumberRepository.java` | field `NumberArray numberArray` · ctor (mảng rỗng) · `saveNumberArray(int[] valueArray)` · `getNumberArray()` |
| 3 | `dto/SortRequestDTO.java`, `SortResponseDTO.java` | ctor rỗng + field + get/set (JavaBean) |
| 4 | `service/SortService.java` | field `numberRepository`, `random` + ctor · **`sortByInsertion`** (`private`): for từ 1 · cất `key` · while dời · đặt `key` ở `j + 1` · `generateValueArray` · `sortRandomArray` |
| 5 | `controller/SortController.java` | `new SortService()` · `sortArray(requestDTO)`: `setResponseDTO` rồi `display()` **1 lần** |
| 6 | `view/SortView.java` | field `responseDTO` · `setResponseDTO` · `display()` **không tham số** |
| 7 | `constants/Message.java`, `Constants.java` | prompt, 2 lỗi, 2 dòng kết quả `"…: %s"` · `MIN_SIZE`, `MAX_SIZE` |
| 8 | `utils/Validation.java` | `getSize(String)` — **tách 2 lỗi** |
| 9 | `main/Main.java` | `public final class` + `private Main()`; `inputSize` (vòng hỏi lại) + gọi `controller.sortArray` **1 lần** |

**Bẫy hay gặp:**

1. Viết `while ((numberArray.getValue(j) > key) && (j >= 0))` — đảo thứ tự điều kiện → khi `j = -1` sẽ
   đọc `getValue(-1)` → **văng `ArrayIndexOutOfBoundsException`**. `j >= 0` phải đứng **trước**.
2. Đặt `key` vào `j` thay vì `j + 1` → mất một số, mảng sai.
3. Không cất `key` mà dùng thẳng `numberArray.getValue(i)` trong `while` → sau lần dời đầu, ô `i` đã
   bị ghi đè, so với số sai.
4. In mảng "trước" **sau khi** đã sắp → hai dòng giống nhau: phải `setUnsortedArray` trước `sortByInsertion`.
5. `sc.nextInt()` + gõ chữ → văng `InputMismatchException`. Luôn `nextLine()` rồi `parseInt`.
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
| 8 | `1` | mảng 1 phần tử, hai dòng giống nhau (vòng for không chạy lần nào) |
| 9 | `2` | 2 số, dòng Sorted tăng dần |
| 10 | `1000` | dòng Sorted tăng dần, nhiều số trùng |

---

## 6. Debug

| Việc | Cách làm |
|---|---|
| Breakpoint | dòng `numberArray.setValue(j + 1, key);` (cuối vòng for) trong `SortService.sortByInsertion` |
| Chạy | **Ctrl+F5**, nhập `5` |
| Vào thuật toán | breakpoint ở dòng `sortByInsertion(numberArray);` trong `sortRandomArray`, **F7** (Step Into) → vào thuật toán |
| Quan sát | tab **Variables**: `i`, `key`, `j`; mở `numberArray` → `valueArray` — lúc dời sẽ thấy **hai ô liền nhau cùng số** (đó là ô "?" của hình đề) |
| Bước | **F5** (Continue) mỗi lần tới breakpoint = xong 1 lượt chèn; **F8** trong `while` để thấy từng lần dời |
| Thấy repository | **F7** vào `numberRepository.saveNumberArray(...)` — mảng vừa sinh được gói thành `NumberArray` và cất lại |

---

## 7. Câu hỏi thầy hay hỏi

### Thuật toán

| Câu hỏi | Trả lời mẫu |
|---|---|
| Sau lượt `i`, phần nào đã sắp? | `i + 1` số đầu **đã sắp với nhau** — nhưng **chưa chắc ở vị trí cuối cùng** (khác selection): số nhỏ đến sau vẫn chen vào được. |
| Khác selection sort chỗ nào? | Selection **tìm min trong phần chưa sắp**; insertion **lấy số kế tiếp**, tìm chỗ cho nó trong phần đã sắp. |
| Vì sao dời (shift) thay vì đổi chỗ (swap)? | Đề: *"Shifting instead of swapping"* — swap ghi 3 lần/bước, shift ghi 1 lần; `key` chỉ được ghi **1 lần** vào chỗ cuối. |
| Độ phức tạp? | Xấu nhất **O(n²)**, tốt nhất **O(n)** (mảng đã sắp). |
| Có ổn định không? | **Có** — chỉ dời khi `> key`; số bằng `key` đứng yên bên trái nó. |
| Sắp giảm dần sửa gì? | `numberArray.getValue(j) > key` → `<`. |

### Kiến trúc theo tờ checklist

| Câu hỏi | Trả lời mẫu |
|---|---|
| Sao bài có repository? | Tờ checklist 1.1 *"Bắt buộc phải có repository"*. `NumberRepository` giữ **dữ liệu** (mảng số) và chỉ có CRUD đơn giản: `saveNumberArray` tạo `NumberArray` từ các số vừa sinh, `getNumberArray` trả nó ra. Tính toán ở `SortService` — luồng **Controller → Service → Repository → Model**. |
| View nhận dữ liệu thế nào? | **Qua thuộc tính**: `SortView` có field `private SortResponseDTO responseDTO` + setter `setResponseDTO(...)`; `display()` không tham số. Controller gọi `setResponseDTO(responseDTO)` rồi `display()` **đúng 1 lần**. |
| Validate ở đâu? | Ở **Main**: `Main.inputSize` đọc `sc.nextLine()`, đưa cho `Validation.getSize` — sai thì ném `Exception(Message.X)`, Main bắt, in `e.getMessage()` rồi hỏi lại. Controller/service chỉ nhận `SortRequestDTO` đã sạch. |
| Sao `Main` là `final` và có `private Main()`? | Tờ checklist 3.4: lớp chỉ có hàm `static` phải có private constructor và khai báo `final`. |
| Sao `key` và `j` khai báo ở đầu thân `for`? | Tờ checklist 2.6 + 3.7: biến khai báo **đầu block** nơi dùng nó và **khởi tạo luôn** (`int key = numberArray.getValue(i);`, `int j = i - 1;`). Mỗi lượt cần `key`, `j` mới nên block của chúng là thân `for`. |

### OOP / Java

| Câu hỏi | Trả lời mẫu |
|---|---|
| 4 tính chất OOP ở đâu? | **Đóng gói**: `valueArray` là `private` trong `NumberArray`, chỉ đổi qua `setValue`/`setValueArray`; `numberArray` là `private` trong `NumberRepository`. **Kế thừa**: mọi lớp ngầm `extends Object`; `NumberArray` kế thừa và ghi đè `toString()`. **Đa hình**: `toString()` có `@Override` — `numberArray.toString()` chạy bản của `NumberArray`, không phải của `Object`. **Trừu tượng**: `Main` chỉ gọi `controller.sortArray(requestDTO)` — không biết bên trong có service, repository, model hay thuật toán nào. |
| Không có ArrayList/List trong bài? | Đúng — bài dùng **mảng `int[]`** vì kích thước biết trước. (Thầy hỏi List vs ArrayList: `List` là interface, `ArrayList` là lớp cụ thể dùng mảng động bên dưới; bộ lời giải khai kiểu cụ thể — QUY-TAC-THAY §9 V5.) |

### Access modifier và static — từng chỗ không `private` (thầy V2, V3)

| Thành phần | Vì sao như vậy |
|---|---|
| Mọi **field** (`valueArray`, `numberArray`, `size`, `unsortedArray`, `sortedArray`, `numberRepository`, `random`, `sortService`, `sortView`, `responseDTO`) | `private` — chỉ lớp đó được đụng (đóng gói) |
| `NumberArray()`, get/set `valueArray` | `public` — chuẩn **JavaBean** (V10) |
| `NumberArray(int[])` | `public` — `NumberRepository` ở package **khác** (`repository`) gọi |
| `getSize`, `getValue`, `setValue`, `toString` | `public` — `SortService` ở package **khác** (`service`) gọi |
| `NumberRepository()`, `saveNumberArray`, `getNumberArray` | `public` — `SortService` gọi |
| `sortRandomArray` | `public` — controller gọi |
| `generateValueArray`, `sortByInsertion` | **`private`** — chỉ `sortRandomArray` trong cùng lớp gọi (thầy V2: hàm chỉ `public` khi lớp khác gọi) |
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
| `sortByInsertion(NumberArray)` | `void` | đổi **chính mảng** được truyền vào (tham chiếu) — không có gì mới để trả; mảng trong repository cũng đã sắp vì là cùng một đối tượng |
| `setValue(int, int)` | `void` | chỉ ghi, không có kết quả |
| `sortRandomArray` | `SortResponseDTO` | view cần **2 chuỗi** cùng lúc → gói vào 1 DTO |
| `generateValueArray` | `int[]` | các số vừa sinh, để repository gói thành `NumberArray` |
| `getNumberArray` | `NumberArray` | repository trả **model** cho service (service được phép làm việc với model, controller thì không) |
| `getSize`, `getValue` | `int` | số đếm / phần tử nguyên |
| `toString` | `String` | model **không được in** — trả chữ để view in |

---

## 8. Thầy đổi yêu cầu tại chỗ

| Thầy bảo | Sửa file | Không đụng |
|---|---|---|
| Đổi sang **bubble/selection sort** | thân hàm sắp trong `SortService` (+ đổi tên hàm: `sortByBubble`/`sortBySelection`) | `Main`, `Controller`, `View`, repository, model, DTO |
| Dùng bản **"sifting down" bằng swap** của đề | thêm `swap` vào `NumberArray`; trong `sortByInsertion` bỏ `key`, giữ `int j = i - 1;` rồi `while ((j >= 0) && (numberArray.getValue(j) > numberArray.getValue(j + 1))) { numberArray.swap(j, j + 1); j--; }` | mọi file khác |
| Sắp **giảm dần** | `SortService.sortByInsertion`: `>` → `<` | mọi file khác |
| Số ngẫu nhiên trong `[-n, n]` | `SortService.generateValueArray`: `random.nextInt((2 * size) + 1) - size` | view, controller, repository |
| In thêm **số lần dời** | `sortByInsertion` trả `int` → field mới trong `SortResponseDTO` → `sortRandomArray` gán → `SortView.display()` in thêm dòng (+ câu trong `Message`) | `Main`, `Validation` |

---

## 9. Chỗ khác với đề / lời giải cũ trên web

| Chỗ | Bản cũ | Bài này | Lý do |
|---|---|---|---|
| Dòng tiêu đề | `===== Insertion Sort Program =====` | không có | ảnh màn hình đề **không có** dòng này |
| Prompt | `Please input the number of array: ` | `Enter number of array:` (xuống dòng) | **đúng ảnh màn hình đề** — nên test đặt `REPLACE_REFERENCE = True` |
| Khoảng số ngẫu nhiên | `[0, 100)` | `[0, n)` | đề: *"random integer in number range input"*, ví dụ n=10 → 0..9 |
| Kiến trúc | `bo/ui/utils`, Scanner trong `Validator` | MVC theo Guide, Scanner **chỉ ở `main`** | luật thầy |
| Model | `int[]` trần | `NumberArray` (JavaBean) + `setValue` | luật thầy: model là JavaBean (V10) |
| Thông báo lỗi | `You must input a number.` · `Number must be between 1 and 1000.` | giữ nguyên | đề không ghi câu lỗi; giữ đúng bản cũ đã kiểm |

### 9.1 Đổi theo tờ checklist giấy (21/09/2026)

Màn hình chạy **không đổi một chữ** (`verify.py` so từng dòng, cả en_US và vi_VN).

| Chỗ | Bản trước | Bây giờ | Mục checklist |
|---|---|---|---|
| Tầng dữ liệu | không có `repository` | `repository/NumberRepository` giữ `NumberArray` | 1.1 |
| View | `setResponse(response)` | field `responseDTO` + `setResponseDTO(...)` + `display()` | 1.1 |
| Main | `public class Main` | `public final class Main` + `private Main()` | 3.4 |
| Tên hàm | `insertionSort`, `generateArray` | `sortByInsertion`, `generateValueArray` | 1.4 |
| Tên mảng | `int[] values`, `getValues/setValues` | `int[] valueArray`, `getValueArray/setValueArray` | 1.5 |
| Khai báo biến | `String line = sc.nextLine();` giữa vòng lặp; `int size;` chưa gán | `String line = "";` đầu hàm; `int size = 0;` | 2.6, 3.7 |
| Ngoặc | `j >= 0 && array.getValue(j) > key`; `size < MIN_SIZE \|\| size > MAX_SIZE` | `(j >= 0) && (numberArray.getValue(j) > key)`; `(size < …) \|\| (size > …)` | 3.3 |
| In nhãn | `Message.LABEL_SORTED + chuỗi` | `String.format(Message.SORTED_ARRAY, chuỗi)` | 3.8 |
| Dòng trống | comment của các hằng/field dính nhau; `setValue(j + 1, key)` dính ngay sau `}` | 1 dòng trống trước mỗi comment, sau vùng khai báo, sau mỗi `}` | 2.8 |

---

## 10. Tờ checklist 25 mục — bài này đạt thế nào

| Mục | Chỉ vào đâu |
|---|---|
| 1.1 | `repository/NumberRepository` (bắt buộc có); `SortController` chỉ import `dto`/`service`/`view`; `SortView` nhận `responseDTO` qua setter, `display()` gọi 1 lần; `Main` gọi `controller.sortArray` 1 lần |
| 1.3 / 1.4 | lớp là danh từ (`NumberRepository`, `SortService`); hàm mở đầu bằng động từ: `sortByInsertion`, `generateValueArray`, `saveNumberArray`, `sortRandomArray` |
| 1.5 | biến/field kiểu mảng đuôi `Array`: `int[] valueArray` (`NumberArray`, `SortService.generateValueArray`) |
| 2.6 + 3.7 | `String line = "";` (`Main.inputSize`), `int size = 0;` (`Validation.getSize`), `NumberArray numberArray = null;` (`SortService.sortRandomArray`), `int key`/`int j` đầu thân `for` |
| 2.8 | 1 dòng trống giữa các hằng/field có comment, sau vùng khai báo biến, sau mỗi `}` trước câu lệnh kế |
| 3.3 | `while ((j >= 0) && (numberArray.getValue(j) > key))`; `if ((size < Constants.MIN_SIZE) \|\| (size > Constants.MAX_SIZE))` |
| 3.4 | `public final class Main` + `private Main()`; `Validation`, `Message`, `Constants` cũng `final` + private constructor |
| Soát máy | `checklist_audit.py` → **0 VI_PHAM**; còn RUI_RO: `String[] args` của `main` và tham số setter/constructor trùng tên field (`this.size = size` — IDE sinh) |
