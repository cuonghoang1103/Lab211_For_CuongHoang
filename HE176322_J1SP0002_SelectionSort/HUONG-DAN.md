# J1.S.P0002 — Selection Sort

> Bài anh em của **P0001 Bubble Sort**: cùng khung MVC, cùng màn hình. Chỉ khác
> **một hàm**: `SortService.selectionSort`. Thuật toán vẫn phải làm MVC — thầy: *"Các bài liên quan
> thuật toán như fibo, sắp xếp,… cũng phải làm MVC, không OOP/MVC → không review."*

| | |
|---|---|
| Loại / LOC | Short Assignment · 40 LOC · 1 slot |
| Project | `HE176322_J1SP0002_SelectionSort` |
| Chạy | NetBeans: **File ▸ Open Project** → **F6** |
| Lớp chạy | `main.Main` |
| Kiểm tự động | `python3 _tools/verify.py J1SP0002` → 7 kịch bản × 2 locale |

---

## 1. Đề bài nói gì

- Nhập **số phần tử** của mảng (số nguyên dương).
- Sinh mảng **số ngẫu nhiên "in number range input"** — nhập `10` → các số từ `0` đến `9`.
- In mảng **trước** và **sau** khi sắp xếp bằng **selection sort**.

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
| Thuật toán | tìm **min** của phần chưa sắp, **đổi** với phần tử đầu phần chưa sắp | `SortService.selectionSort` |

---

## 2. Kiến thức cần biết

### 2.1 Selection sort — ý tưởng (đúng lời đề)

> Mảng chia **tưởng tượng** làm 2 phần: **đã sắp** (bên trái, lúc đầu rỗng) và **chưa sắp** (bên
> phải). Mỗi bước: tìm **phần tử nhỏ nhất** của phần chưa sắp, **đổi chỗ** nó với **phần tử đầu**
> phần chưa sắp → phần đã sắp dài thêm 1. Phần chưa sắp rỗng → dừng.

Khác bubble sort: bubble **đổi mỗi khi gặp cặp ngược**; selection chỉ **nhớ vị trí** min trong vòng
trong, rồi **đổi đúng 1 lần** mỗi lượt.

### 2.2 Chạy tay ví dụ của đề: `{5, 1, 12, -5, 16, 2, 12, 14}`

`|` ngăn phần đã sắp (trái) và chưa sắp (phải).

| i | Phần chưa sắp | Min (vị trí) | Làm gì | Mảng sau bước |
|---|---|---|---|---|
| 0 | `5 1 12 -5 16 2 12 14` | `-5` (3) | đổi `5` ↔ `-5` | `-5 \| 1 12 5 16 2 12 14` |
| 1 | `1 12 5 16 2 12 14` | `1` (1) | min đã ở đầu → **không đổi** | `-5 1 \| 12 5 16 2 12 14` |
| 2 | `12 5 16 2 12 14` | `2` (5) | đổi `12` ↔ `2` | `-5 1 2 \| 5 16 12 12 14` |
| 3 | `5 16 12 12 14` | `5` (3) | không đổi | `-5 1 2 5 \| 16 12 12 14` |
| 4 | `16 12 12 14` | `12` (5) — số 12 **đầu tiên** | đổi `16` ↔ `12` | `-5 1 2 5 12 \| 16 12 14` |
| 5 | `16 12 14` | `12` (6) | đổi `16` ↔ `12` | `-5 1 2 5 12 12 \| 16 14` |
| 6 | `16 14` | `14` (7) | đổi `16` ↔ `14` | `-5 1 2 5 12 12 14 \| 16` |
| — | `16` | | còn 1 số → nó lớn nhất, **dừng** (`i < size - 1`) | `-5 1 2 5 12 12 14 16` |

Khớp từng hàng với hình của đề (hàng i = 4 hình tô đúng số `12` ở vị trí 5 — vì code so `<`, gặp
số bằng thì **giữ số tìm thấy trước**).

### 2.3 Chi tiết thầy hay soi

| Chi tiết | Code | Vì sao |
|---|---|---|
| Vòng ngoài dừng ở `size - 1` | `for (int i = 0; i < size - 1; i++)` | còn 1 số cuối thì nó chắc chắn lớn nhất |
| Vòng trong bắt đầu `i + 1` | `for (int j = i + 1; j < size; j++)` | `minIndex = i` đã là ứng viên đầu |
| Nhớ **vị trí**, không nhớ giá trị | `minIndex = j;` | cần vị trí để `swap(i, minIndex)` |
| `if (minIndex != i)` | chỉ đổi khi min ở chỗ khác | tránh đổi một số với chính nó |

### 2.4 Độ phức tạp

| | Số phép so sánh | Số lần đổi |
|---|---|---|
| **Mọi trường hợp** (kể cả mảng đã sắp) | `n(n-1)/2` → **O(n²)** | tối đa `n - 1` |

Selection **không dừng sớm được** (khác bubble có `swapped`): mảng đã sắp vẫn phải quét hết để
biết min. Đổi lại: số lần **đổi chỗ ít nhất** trong các thuật toán O(n²).

**Không ổn định** (đề nói rõ): `[2a, 2b, 1]` → i=0 đổi `2a` ↔ `1` → `[1, 2b, 2a]` — hai số 2 đảo thứ tự.

### 2.5 Java dùng trong bài

| API | Dùng làm gì |
|---|---|
| `new Random().nextInt(n)` | số ngẫu nhiên trong `[0, n)` |
| `Arrays.toString(arr)` | ra chữ `[2, 6, 3]` đúng định dạng màn hình |
| `Integer.parseInt(s)` | chuỗi → số; sai → `NumberFormatException` |

---

## 3. Thiết kế

```
HE176322_J1SP0002_SelectionSort/src/
├── model/      NumberArray            JavaBean: int[] values + getSize/getValue/swap/toString
├── dto/        SortRequestDTO         size          (main ──► controller)
│               SortResponseDTO        2 chuỗi mảng  (controller ──► view)
├── service/    SortService            sinh mảng + selectionSort + trả DTO  ← thuật toán của đề ở ĐÂY
├── controller/ SortController         new SortService() → view
├── view/       SortView               in 2 dòng kết quả
├── constants/  Message, Constants     câu chữ; MIN_SIZE = 1, MAX_SIZE = 1000
├── utils/      Validation             getSize(chuỗi) → int hoặc ném lỗi
└── main/       Main                   Scanner + gọi controller 1 lần
```

| Câu hỏi thiết kế | Trả lời |
|---|---|
| Sao có `service` mà không có `repository`? | Guide: repository = **giữ dữ liệu + CRUD**. Bài này không lưu gì. Thuật toán là **"tính toán nghiệp vụ"** → `service`. |
| Sao `NumberArray` không tự sắp xếp? | Model chỉ mô tả **mảng** (lấy phần tử, đổi chỗ). Cách sắp là **nghiệp vụ** → nằm ở `SortService` (Guide: service giữ *"các tính toán nghiệp vụ"*). |
| Sao controller không đụng `NumberArray`? | Guide: controller *"chỉ import DTO, View, Service"*. |

### 3.1 Design Pattern trong bài

| Pattern | Ở đâu |
|---|---|
| **MVC** — thầy gọi là "MVC JSP" | controller điều hướng (như Servlet) · view hiển thị (như trang JSP) · model là JavaBean |
| **Facade** | controller: `Main` chỉ gọi `controller.sortArray(dto)`, không biết service/model/view phía sau |

> Bài chỉ 40 LOC nên **không thêm lớp pattern GoF** — ghi chú slide SOLID của thầy cảnh báo *"trừu tượng hoá sớm … vi phạm YAGNI"*. Thuật toán nằm gọn trong **một hàm** `selectionSort` của `SortService`.

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
                     ├─ selectionSort(array)     ← thuật toán của đề (sắp tại chỗ)
                     └─ response.setSorted(array.toString())
   controller ──► view.setResponse(response) ──► view.display()
```

### 3.2 SOLID trong bài

| Nguyên lý | Ở đâu |
|---|---|
| **S** | mỗi lớp 1 việc: `NumberArray` giữ số · `SortService` sinh + sắp · `SortView` in · `Validation` kiểm · `Message`/`Constants` giữ chữ/số |
| **O** | đổi câu chữ chỉ sửa `Message`; đổi thuật toán chỉ sửa **một hàm** `selectionSort` |
| **L** | bài chưa có lớp con riêng — chỉ `extends Object` và ghi đè `toString()` đúng nghĩa |
| **I** | không có interface — bài chưa cần (xem 3.1) |
| **D** | `Main` chỉ phụ thuộc controller + DTO; không biết service hay model |

---

## 4. Code từng bước

> Thầy (QUY-TAC-THAY §9, V8): *"code cái model trước rồi đến data"*.

| Bước | File | Việc |
|---|---|---|
| 1 | `model/NumberArray.java` | field `int[] values` · ctor rỗng + ctor đủ · get/set `values` · `getSize` · `getValue` · `swap` · `toString` |
| 2 | `dto/SortRequestDTO.java`, `SortResponseDTO.java` | ctor rỗng + field + get/set (JavaBean) |
| 3 | `service/SortService.java` | field `random` · **`selectionSort`** (`private`): 2 vòng for + `minIndex` + `swap` · `generateArray` · `sortRandomArray` |
| 4 | `controller/SortController.java` | `new SortService()` · `sortArray(dto)` |
| 5 | `view/SortView.java` | `setResponse` · `display` |
| 6 | `constants/Message.java`, `Constants.java` | prompt, 2 lỗi, 2 nhãn · `MIN_SIZE`, `MAX_SIZE` |
| 7 | `utils/Validation.java` | `getSize(String)` — **tách 2 lỗi** |
| 8 | `main/Main.java` | `inputSize` (vòng hỏi lại) + gọi `controller.sortArray` **1 lần** |

**Bẫy hay gặp:**

1. **Đổi chỗ ngay trong vòng trong** mỗi khi gặp số nhỏ hơn → vẫn ra đúng nhưng đó là "bubble lai",
   không phải selection. Vòng trong **chỉ gán `minIndex = j`**; `swap` nằm **sau** vòng trong.
2. Quên `minIndex = i;` ở **đầu mỗi lượt** (khai ngoài vòng) → lượt sau so với min cũ, sai kết quả.
3. In mảng "trước" **sau khi** đã sắp → hai dòng giống nhau: phải `setUnsorted` trước `selectionSort`.
4. `sc.nextInt()` + gõ chữ → văng `InputMismatchException`. Luôn `nextLine()` rồi `parseInt`.

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
| 8 | `1` | mảng 1 phần tử, hai dòng giống nhau |
| 9 | `2` | 2 số, dòng Sorted tăng dần |
| 10 | `1000` | chạy ngay (≈ 500 nghìn phép so sánh), dòng Sorted tăng dần, nhiều số trùng |

---

## 6. Debug

| Việc | Cách làm |
|---|---|
| Breakpoint | dòng `if (array.getValue(j) < array.getValue(minIndex))` trong `SortService.selectionSort` |
| Chạy | **Ctrl+F5**, nhập `5` |
| Vào thuật toán | breakpoint ở dòng `selectionSort(array);` trong `sortRandomArray`, **F7** (Step Into) → vào thuật toán |
| Quan sát | tab **Variables**: `i`, `j`, `minIndex`; mở `array` → `values` |
| Bước | **F8** — chỉ cho thầy: vòng trong chỉ đổi `minIndex`, còn `swap(i, minIndex)` chạy **1 lần** sau vòng trong |

---

## 7. Câu hỏi thầy hay hỏi

### Thuật toán

| Câu hỏi | Trả lời mẫu |
|---|---|
| Sau lượt `i`, phần nào chắc chắn đúng? | `i + 1` số đầu — là `i + 1` số **nhỏ nhất**, đúng thứ tự. |
| Khác bubble sort chỗ nào? | Bubble đổi chỗ **nhiều lần** mỗi lượt, số lớn "nổi" về cuối; selection **tìm min rồi đổi 1 lần**, phần sắp xong lớn dần từ **đầu**. |
| Độ phức tạp? | Luôn `n(n-1)/2` phép so sánh → **O(n²)**, kể cả mảng đã sắp. Tối đa `n-1` lần đổi. |
| Có ổn định không? | **Không** (đề cũng nói): `[2a, 2b, 1]` → `[1, 2b, 2a]`. |
| Sắp giảm dần sửa gì? | Tìm **max** thay vì min: đổi `<` thành `>` trong `if` (nên đổi tên `minIndex` → `maxIndex`). |
| Sao `if (minIndex != i)`? | Min đã ở đầu thì không cần đổi; bỏ `if` vẫn đúng, chỉ thừa một lần đổi số với chính nó. |

### OOP / Java

| Câu hỏi | Trả lời mẫu |
|---|---|
| 4 tính chất OOP ở đâu? | **Đóng gói**: `values` là `private` trong `NumberArray`, chỉ đổi qua `swap`/`setValues`. **Kế thừa**: mọi lớp ngầm `extends Object`; `NumberArray` kế thừa và ghi đè `toString()`. **Đa hình**: `toString()` có `@Override` — `array.toString()` chạy bản của `NumberArray`, không phải của `Object`. **Trừu tượng**: `Main` chỉ gọi `controller.sortArray(dto)` — không biết bên trong có service, model hay thuật toán nào. |
| Không có ArrayList/List trong bài? | Đúng — bài dùng **mảng `int[]`** vì kích thước biết trước và chỉ đổi chỗ. (Thầy hỏi List vs ArrayList: `List` là interface, `ArrayList` là lớp cụ thể dùng mảng động bên dưới; bộ lời giải khai kiểu cụ thể — QUY-TAC-THAY §9 V5.) |

### Access modifier và static — từng chỗ không `private` (thầy V2, V3)

| Thành phần | Vì sao như vậy |
|---|---|
| Mọi **field** (`values`, `size`, `unsorted`, `sorted`, `random`, `sortService`, `sortView`, `response`) | `private` — chỉ lớp đó được đụng (đóng gói) |
| `NumberArray()`, get/set `values` | `public` — chuẩn **JavaBean** (V10) |
| `NumberArray(int[])`, `getSize`, `getValue`, `swap`, `toString` | `public` — `SortService` ở package **khác** (`service`) gọi |
| `sortRandomArray` | `public` — controller gọi |
| `generateArray`, `selectionSort` | **`private`** — chỉ `sortRandomArray` trong cùng lớp gọi (thầy V2: hàm chỉ `public` khi lớp khác gọi) |
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
| `selectionSort(NumberArray)` | `void` | đổi **chính mảng** được truyền vào (tham chiếu) — không có gì mới để trả |
| `sortRandomArray` | `SortResponseDTO` | view cần **2 chuỗi** cùng lúc → gói vào 1 DTO |
| `getSize`, `getValue` | `int` | số đếm / phần tử nguyên |
| `toString` | `String` | model **không được in** — trả chữ để view in |

---

## 8. Thầy đổi yêu cầu tại chỗ

| Thầy bảo | Sửa file | Không đụng |
|---|---|---|
| Đổi sang **bubble/insertion sort** | thân hàm sắp trong `SortService` (+ đổi tên hàm) | `Main`, `Controller`, `View`, model, DTO |
| Sắp **giảm dần** | `SortService.selectionSort`: `<` → `>` | mọi file khác |
| Số ngẫu nhiên trong `[-n, n]` | `SortService.generateArray`: `random.nextInt(2 * size + 1) - size` | view, controller |
| In thêm **số lần đổi chỗ** | `selectionSort` trả `int` → field mới trong `SortResponseDTO` → `sortRandomArray` gán → `SortView` in thêm dòng (+ nhãn trong `Message`) | `Main`, `Validation` |
| Cho người dùng **chọn thuật toán** | lúc này mới đáng tách **Strategy** (mục 3.1): interface + 1 lớp/thuật toán; `Main` hỏi lựa chọn, `SortController` chọn lớp | `Validation`, `View`, model |

---

## 9. Chỗ khác với đề / lời giải cũ trên web

| Chỗ | Bản cũ | Bài này | Lý do |
|---|---|---|---|
| Dòng tiêu đề | `===== Selection Sort Program =====` | không có | ảnh màn hình đề **không có** dòng này |
| Prompt | `Please input the number of array: ` | `Enter number of array:` (xuống dòng) | **đúng ảnh màn hình đề** — nên test đặt `REPLACE_REFERENCE = True` |
| Khoảng số ngẫu nhiên | `[0, 100)` | `[0, n)` | đề: *"random integer in number range input"*, ví dụ n=10 → 0..9 |
| Kiến trúc | `bo/ui/utils`, Scanner trong `Validator` | MVC theo Guide, Scanner **chỉ ở `main`** | luật thầy |
| Thông báo lỗi | `You must input a number.` · `Number must be between 1 and 1000.` | giữ nguyên | đề không ghi câu lỗi; giữ đúng bản cũ đã kiểm |
