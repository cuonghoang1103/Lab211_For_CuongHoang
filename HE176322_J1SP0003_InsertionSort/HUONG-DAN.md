# J1.S.P0003 — Insertion Sort

> Bài anh em của **P0001 Bubble Sort**: cùng khung MVC, cùng màn hình. Chỉ khác
> **một hàm**: `SortService.insertionSort`. Thuật toán vẫn phải làm MVC — thầy: *"Các bài liên quan
> thuật toán như fibo, sắp xếp,… cũng phải làm MVC, không OOP/MVC → không review."*

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
| Hai dòng kết quả | `Unsorted array: [...]` · `Sorted array: [...]` | `Message.LABEL_UNSORTED/LABEL_SORTED`, in ở `SortView` |
| Số ngẫu nhiên | *"random integer in number range input"* | `SortService.generateArray` → `[0, n)` |
| Thuật toán | lấy phần tử đầu phần chưa sắp, **chèn** vào đúng chỗ trong phần đã sắp; bản *"Shifting instead of swapping"* (đề: *"the most commonly used"*) | `SortService.insertionSort` |

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
| Cất `key` trước khi dời | `int key = array.getValue(i);` | lần dời đầu tiên **ghi đè** ô `i` — không cất là mất số |
| Điều kiện `j >= 0` đứng **trước** | `while (j >= 0 && array.getValue(j) > key)` | `&&` dừng sớm: `j = -1` thì không đọc `getValue(-1)` (tránh `ArrayIndexOutOfBoundsException`) |
| Đặt ở `j + 1` | `array.setValue(j + 1, key);` | vòng `while` dừng khi `j` trỏ vào số **≤ key** (hoặc -1) → ô trống ngay bên phải nó |
| So `>` chứ không `>=` | `array.getValue(j) > key` | số **bằng** key không bị dời → giữ thứ tự cũ → **ổn định** |

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

---

## 3. Thiết kế

```
HE176322_J1SP0003_InsertionSort/src/
├── model/      NumberArray            JavaBean: int[] values + getSize/getValue/setValue/toString
├── dto/        SortRequestDTO         size          (main ──► controller)
│               SortResponseDTO        2 chuỗi mảng  (controller ──► view)
├── service/    SortService            sinh mảng + insertionSort + trả DTO  ← thuật toán của đề ở ĐÂY
├── controller/ SortController         new SortService() → view
├── view/       SortView               in 2 dòng kết quả
├── constants/  Message, Constants     câu chữ; MIN_SIZE = 1, MAX_SIZE = 1000
├── utils/      Validation             getSize(chuỗi) → int hoặc ném lỗi
└── main/       Main                   Scanner + gọi controller 1 lần
```

| Câu hỏi thiết kế | Trả lời |
|---|---|
| Sao có `service` mà không có `repository`? | Guide: repository = **giữ dữ liệu + CRUD**. Bài này không lưu gì. Thuật toán là **"tính toán nghiệp vụ"** → `service`. |
| Sao `NumberArray` có `setValue` mà không có `swap` như P0001? | Thuật toán **dời** (ghi 1 ô) chứ không **đổi chỗ**. Model chỉ giữ hàm mà bài thật sự dùng. |
| Sao controller không đụng `NumberArray`? | Guide: controller *"chỉ import DTO, View, Service"*. |

### 3.1 Design Pattern trong bài

| Pattern | Ở đâu |
|---|---|
| **MVC** — thầy gọi là "MVC JSP" | controller điều hướng (như Servlet) · view hiển thị (như trang JSP) · model là JavaBean |
| **Facade** | controller: `Main` chỉ gọi `controller.sortArray(dto)`, không biết service/model/view phía sau |

> Bài chỉ 40 LOC nên **không thêm lớp pattern GoF** — ghi chú slide SOLID của thầy cảnh báo *"trừu tượng hoá sớm … vi phạm YAGNI"*. Thuật toán nằm gọn trong **một hàm** `insertionSort` của `SortService`.

**Thầy hỏi "dùng pattern gì cho dễ mở rộng?"** → trả lời đủ 4 yếu tố GoF (slide *"Elements of a Design Pattern"*):

| Yếu tố | Trả lời |
|---|---|
| **Name** | Strategy (nhóm Behavioral) |
| **Problem** | nhiều thuật toán sắp xếp cùng làm một việc; viết thẳng vào service thì đổi thuật toán = sửa service |
| **Solution** | tách `interface SortStrategy { void sort(NumberArray array); }`; mỗi cách làm là 1 lớp `implements` nó (`BubbleSortStrategy`, `SelectionSortStrategy`…); `SortService` nhận strategy qua constructor, controller chọn `new SortService(new XxxSortStrategy())` |
| **Consequences** | ➕ thêm cách làm = thêm 1 lớp, service đứng yên (**O**) · ➖ thêm 2 file — chỉ đáng khi có từ 2 cách trở lên (bài P0004 Quick sort, P0005 Merge sort làm đúng như vậy) |

**Luồng chạy:**

```
Main: đọc size (hỏi lại khi sai) ──► SortRequestDTO ──► controller.sortArray(dto)
   controller ──► sortService.sortRandomArray(dto)
                     ├─ generateArray(size)          → NumberArray ngẫu nhiên
                     ├─ response.setUnsorted(array.toString())   ← chụp TRƯỚC khi sắp
                     ├─ insertionSort(array)     ← thuật toán của đề (sắp tại chỗ)
                     └─ response.setSorted(array.toString())
   controller ──► view.setResponse(response) ──► view.display()
```

### 3.2 SOLID trong bài

| Nguyên lý | Ở đâu |
|---|---|
| **S** | mỗi lớp 1 việc: `NumberArray` giữ số · `SortService` sinh + sắp · `SortView` in · `Validation` kiểm · `Message`/`Constants` giữ chữ/số |
| **O** | đổi câu chữ chỉ sửa `Message`; đổi thuật toán chỉ sửa **một hàm** `insertionSort` |
| **L** | bài chưa có lớp con riêng — chỉ `extends Object` và ghi đè `toString()` đúng nghĩa |
| **I** | không có interface — bài chưa cần (xem 3.1) |
| **D** | `Main` chỉ phụ thuộc controller + DTO; không biết service hay model |

---

## 4. Code từng bước

> Thầy (QUY-TAC-THAY §9, V8): *"code cái model trước rồi đến data"*.

| Bước | File | Việc |
|---|---|---|
| 1 | `model/NumberArray.java` | field `int[] values` · ctor rỗng + ctor đủ · get/set `values` · `getSize` · `getValue` · `setValue` · `toString` |
| 2 | `dto/SortRequestDTO.java`, `SortResponseDTO.java` | ctor rỗng + field + get/set (JavaBean) |
| 3 | `service/SortService.java` | field `random` · **`insertionSort`** (`private`): for từ 1 · cất `key` · while dời · đặt `key` ở `j + 1` · `generateArray` · `sortRandomArray` |
| 4 | `controller/SortController.java` | `new SortService()` · `sortArray(dto)` |
| 5 | `view/SortView.java` | `setResponse` · `display` |
| 6 | `constants/Message.java`, `Constants.java` | prompt, 2 lỗi, 2 nhãn · `MIN_SIZE`, `MAX_SIZE` |
| 7 | `utils/Validation.java` | `getSize(String)` — **tách 2 lỗi** |
| 8 | `main/Main.java` | `inputSize` (vòng hỏi lại) + gọi `controller.sortArray` **1 lần** |

**Bẫy hay gặp:**

1. Viết `while (array.getValue(j) > key && j >= 0)` — đảo thứ tự điều kiện → khi `j = -1` sẽ đọc
   `getValue(-1)` → **văng `ArrayIndexOutOfBoundsException`**. `j >= 0` phải đứng **trước**.
2. Đặt `key` vào `j` thay vì `j + 1` → mất một số, mảng sai.
3. Không cất `key` mà dùng thẳng `array.getValue(i)` trong `while` → sau lần dời đầu, ô `i` đã bị
   ghi đè, so với số sai.
4. In mảng "trước" **sau khi** đã sắp → hai dòng giống nhau: phải `setUnsorted` trước `insertionSort`.
5. `sc.nextInt()` + gõ chữ → văng `InputMismatchException`. Luôn `nextLine()` rồi `parseInt`.

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
| Breakpoint | dòng `array.setValue(j + 1, key);` (cuối vòng for) trong `SortService.insertionSort` |
| Chạy | **Ctrl+F5**, nhập `5` |
| Vào thuật toán | breakpoint ở dòng `insertionSort(array);` trong `sortRandomArray`, **F7** (Step Into) → vào thuật toán |
| Quan sát | tab **Variables**: `i`, `key`, `j`; mở `array` → `values` — lúc dời sẽ thấy **hai ô liền nhau cùng số** (đó là ô "?" của hình đề) |
| Bước | **F5** (Continue) mỗi lần tới breakpoint = xong 1 lượt chèn; **F8** trong `while` để thấy từng lần dời |

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
| Sắp giảm dần sửa gì? | `array.getValue(j) > key` → `<`. |

### OOP / Java

| Câu hỏi | Trả lời mẫu |
|---|---|
| 4 tính chất OOP ở đâu? | **Đóng gói**: `values` là `private` trong `NumberArray`, chỉ đổi qua `setValue`/`setValues`. **Kế thừa**: mọi lớp ngầm `extends Object`; `NumberArray` kế thừa và ghi đè `toString()`. **Đa hình**: `toString()` có `@Override` — `array.toString()` chạy bản của `NumberArray`, không phải của `Object`. **Trừu tượng**: `Main` chỉ gọi `controller.sortArray(dto)` — không biết bên trong có service, model hay thuật toán nào. |
| Không có ArrayList/List trong bài? | Đúng — bài dùng **mảng `int[]`** vì kích thước biết trước. (Thầy hỏi List vs ArrayList: `List` là interface, `ArrayList` là lớp cụ thể dùng mảng động bên dưới; bộ lời giải khai kiểu cụ thể — QUY-TAC-THAY §9 V5.) |

### Access modifier và static — từng chỗ không `private` (thầy V2, V3)

| Thành phần | Vì sao như vậy |
|---|---|
| Mọi **field** (`values`, `size`, `unsorted`, `sorted`, `random`, `sortService`, `sortView`, `response`) | `private` — chỉ lớp đó được đụng (đóng gói) |
| `NumberArray()`, get/set `values` | `public` — chuẩn **JavaBean** (V10) |
| `NumberArray(int[])`, `getSize`, `getValue`, `setValue`, `toString` | `public` — `SortService` ở package **khác** (`service`) gọi |
| `sortRandomArray` | `public` — controller gọi |
| `generateArray`, `insertionSort` | **`private`** — chỉ `sortRandomArray` trong cùng lớp gọi (thầy V2: hàm chỉ `public` khi lớp khác gọi) |
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
| `insertionSort(NumberArray)` | `void` | đổi **chính mảng** được truyền vào (tham chiếu) — không có gì mới để trả |
| `setValue(int, int)` | `void` | chỉ ghi, không có kết quả |
| `sortRandomArray` | `SortResponseDTO` | view cần **2 chuỗi** cùng lúc → gói vào 1 DTO |
| `getSize`, `getValue` | `int` | số đếm / phần tử nguyên |
| `toString` | `String` | model **không được in** — trả chữ để view in |

---

## 8. Thầy đổi yêu cầu tại chỗ

| Thầy bảo | Sửa file | Không đụng |
|---|---|---|
| Đổi sang **bubble/selection sort** | thân hàm sắp trong `SortService` (+ đổi tên hàm) | `Main`, `Controller`, `View`, model, DTO |
| Dùng bản **"sifting down" bằng swap** của đề | thêm `swap` vào `NumberArray`; trong `insertionSort` bỏ `key`, `j = i - 1` rồi `while (j >= 0 && array.getValue(j) > array.getValue(j + 1)) { array.swap(j, j + 1); j--; }` | mọi file khác |
| Sắp **giảm dần** | `SortService.insertionSort`: `>` → `<` | mọi file khác |
| Số ngẫu nhiên trong `[-n, n]` | `SortService.generateArray`: `random.nextInt(2 * size + 1) - size` | view, controller |
| In thêm **số lần dời** | `insertionSort` trả `int` → field mới trong `SortResponseDTO` → `sortRandomArray` gán → `SortView` in thêm dòng (+ nhãn trong `Message`) | `Main`, `Validation` |

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
