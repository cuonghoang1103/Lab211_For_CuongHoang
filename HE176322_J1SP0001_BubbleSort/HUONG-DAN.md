# J1.S.P0001 — Bubble Sort

> Bài thuật toán **vẫn phải làm MVC** — thầy: *"Các bài liên quan thuật toán như fibo, sắp xếp,…
> cũng phải làm MVC, không OOP/MVC → không review."* Bài này là khuôn cho mọi bài sắp xếp/tìm
> kiếm (P0002–P0006, P0010). Bản 21/09/2026 đã sửa theo **tờ checklist giấy 25 mục** của thầy —
> xem mục 10 cuối bài.

| | |
|---|---|
| Loại / LOC | Short Assignment · 40 LOC · 1 slot |
| Project | `HE176322_J1SP0001_BubbleSort` |
| Chạy | NetBeans: **File ▸ Open Project** → **F6** |
| Lớp chạy | `main.Main` |
| Kiểm tự động | `python3 _tools/verify.py J1SP0001` → 5 kịch bản × 2 locale |

---

## 1. Đề bài nói gì

- Nhập **số phần tử** của mảng (số nguyên dương).
- Sinh mảng **số ngẫu nhiên "in number range input"** — ví dụ của đề: nhập `10` → các số từ `0` đến `9`.
- In mảng **trước** và **sau** khi sắp xếp bằng **bubble sort**.
- Đề **không bắt** tên lớp hay tên hàm nào — mọi tên trong bài là của lời giải, đặt theo tờ checklist.

Màn hình đề (chép đúng chữ):

```
Enter number of array:
10
Unsorted array: [2, 6, 3, 6, 8, 6, 1, 2, 9, 8]
Sorted array: [1, 2, 2, 3, 6, 6, 6, 8, 8, 9]
```

---

## 2. Kiến thức cần biết

### 2.1 Bubble sort — ý tưởng

> So sánh **từng cặp kề nhau** từ đầu mảng; cặp nào **ngược thứ tự thì đổi chỗ**. Hết một lượt, số
> **lớn nhất** đã "nổi" về cuối. Lặp lại cho phần còn lại. Lượt nào **không đổi chỗ lần nào** → mảng
> đã sắp xong, **dừng sớm**.

### 2.2 Chạy tay ví dụ của đề: `{5, 1, 12, -5, 16}`

| Lượt | So sánh | Kết quả | Mảng sau bước |
|---|---|---|---|
| 1 | 5 > 1 | đổi | `1 5 12 -5 16` |
| 1 | 5 < 12 | giữ | `1 5 12 -5 16` |
| 1 | 12 > -5 | đổi | `1 5 -5 12 16` |
| 1 | 12 < 16 | giữ | `1 5 -5 12 16` ← **16 đã đúng chỗ** |
| 2 | 1 < 5 | giữ | `1 5 -5 12 16` |
| 2 | 5 > -5 | đổi | `1 -5 5 12 16` |
| 2 | 5 < 12 | giữ | ← **12 đã đúng chỗ** |
| 3 | 1 > -5 | đổi | `-5 1 5 12 16` |
| 3 | 1 < 5 | giữ | ← **5 đã đúng chỗ** |
| 4 | -5 < 1 | giữ | **không đổi lần nào → dừng** |

### 2.3 Hai chi tiết thầy hay soi trong code

| Chi tiết | Code | Vì sao |
|---|---|---|
| Vòng trong dừng ở `size - 1 - i` | `for (int j = 0; j < (size - 1 - i); j++)` | sau `i` lượt, `i` số cuối đã đúng chỗ — so tiếp là thừa. Ngoặc `( )` là tờ checklist 3.3 |
| Cờ `swapped` | `if (!swapped) { return; }` | đúng câu của đề: *"If at least one swap has been done, repeat"* |

### 2.4 Độ phức tạp

| Trường hợp | Số phép so sánh | Ví dụ |
|---|---|---|
| Tốt nhất (đã sắp sẵn) | `n - 1` — 1 lượt rồi dừng nhờ `swapped` | `1 2 3 4` |
| Xấu nhất (ngược hẳn) | `n(n-1)/2` → **O(n²)** | `4 3 2 1` |

Nói với thầy: *"n gấp đôi thì thời gian gấp khoảng bốn — nên bubble sort chỉ hợp mảng nhỏ."*

### 2.5 Java dùng trong bài

| API | Dùng làm gì |
|---|---|
| `new Random().nextInt(n)` | số ngẫu nhiên trong `[0, n)` — đúng "in number range input" |
| `Arrays.toString(arr)` | ra chữ `[2, 6, 3]` đúng định dạng màn hình đề |
| `Integer.parseInt(s)` | đổi chuỗi sang số; chuỗi sai → `NumberFormatException` |
| `String.format("Sorted array: %s", chuỗi)` | ghép nhãn với mảng **không** cộng chuỗi (tờ checklist 3.8) |

---

## 3. Thiết kế

```
HE176322_J1SP0001_BubbleSort/src/
├── model/       NumberArray        mảng số (JavaBean): int[] valueArray + getSize/getValue/swap/toString
├── repository/  NumberRepository   GIỮ mảng số: NumberArray numberArray + saveNumberArray/getNumberArray
├── dto/         SortRequestDTO     size                          (main ──► controller)
│                SortResponseDTO    unsortedArray, sortedArray    (controller ──► view)
├── service/     SortService        sinh số + cất vào repository + sortByBubble ← thuật toán của đề ở ĐÂY
├── controller/  SortController     service ──► view, render 1 lần
├── view/        SortView           thuộc tính responseDTO + setResponseDTO + display()
├── constants/   Message.java       câu chữ màn hình ("Unsorted array: %s"…)
│                Constants.java     MIN_SIZE = 1, MAX_SIZE = 1000
├── utils/       Validation         getSize(chuỗi) → int hoặc ném lỗi
└── main/        Main               final + private Main(); Scanner + gọi controller 1 lần
```

| Câu hỏi thiết kế | Trả lời |
|---|---|
| Sao bài thuật toán lại có `repository`? | Tờ checklist 1.1: *"**Bắt buộc phải có repository**"*. Repository giữ **dữ liệu mà thuật toán làm việc** — mảng số (`NumberArray`) — với CRUD đơn giản: `saveNumberArray` (Create) và `getNumberArray` (Read). Nó không sắp, không in, không đọc bàn phím. |
| Vậy thuật toán nằm đâu? | Vẫn ở `service`: Guide *"Nếu có các tính toán nghiệp vụ ngoài CRUD thì cần thêm class …Services … Services nằm giữa Controller và Repo"*. `SortService` **lấy mảng từ repository** rồi mới chạy `sortByBubble`. |
| Sinh số ngẫu nhiên sao không để repository làm? | Repository chỉ *"chứa data và CRUD methods đơn giản"*. Sinh số theo luật *"in number range input"* là nghiệp vụ → `SortService.generateValueArray`. |
| Sao `NumberArray` không tự sắp xếp? | Model chỉ mô tả **đối tượng mảng** (lấy phần tử, đổi chỗ). Cách sắp xếp là **nghiệp vụ** — tách ra để đổi thuật toán không phải sửa model. |
| Sao controller không đụng `NumberArray`? | Tờ checklist 1.1: controller *"không làm việc với Model"*; Guide: *"chỉ import DTO, View, Service"* → service nhận `RequestDTO`, trả `ResponseDTO`. |

**Luồng chạy** (Main → RequestDTO → Controller → Service → Repository → Model; ResponseDTO → View 1 lần):

```
Main: đọc size (hỏi lại khi sai) ──► SortRequestDTO ──► controller.sortArray(requestDTO)   ← gọi 1 lần
   controller ──► sortService.sortRandomArray(requestDTO)
                     ├─ generateValueArray(size)                 → int[] ngẫu nhiên trong [0, size)
                     ├─ numberRepository.saveNumberArray(...)    → repository gói vào NumberArray (model)
                     ├─ numberRepository.getNumberArray()        ← service lấy dữ liệu TỪ repository
                     ├─ responseDTO.setUnsortedArray(...)        ← chụp TRƯỚC khi sắp
                     ├─ sortByBubble(numberArray)                ← thuật toán của đề chạy ở đây
                     └─ responseDTO.setSortedArray(...)
   controller ──► sortView.setResponseDTO(responseDTO) ──► sortView.display()   ← render 1 lần
```

### 3.1 Design Pattern trong bài

| Pattern | Ở đâu |
|---|---|
| **MVC** — thầy gọi là "MVC JSP" | controller điều hướng (như Servlet) · view hiển thị (như trang JSP) · model là JavaBean |
| **Facade** | controller: `Main` chỉ gọi `controller.sortArray(requestDTO)`, không biết service/repository/model/view phía sau |
| **Repository** | `NumberRepository` là **chỗ duy nhất giữ dữ liệu**; service không tự giữ mảng mà lấy từ đây |

> Bài chỉ 40 LOC nên **không thêm lớp pattern GoF** — ghi chú slide SOLID của thầy cảnh báo *"trừu tượng hoá sớm … vi phạm YAGNI"*. Thuật toán nằm gọn trong **một hàm** `sortByBubble` của `SortService`.

**Thầy hỏi "dùng pattern gì cho dễ mở rộng?"** → trả lời đủ 4 yếu tố GoF (slide *"Elements of a Design Pattern"*):

| Yếu tố | Trả lời |
|---|---|
| **Name** | Strategy (nhóm Behavioral) |
| **Problem** | nhiều thuật toán sắp xếp cùng làm một việc; viết thẳng vào service thì đổi thuật toán = sửa service |
| **Solution** | tách `interface ISortStrategy { void sort(NumberArray numberArray); }` (tờ checklist 1.3: tên interface bắt đầu bằng `I`); mỗi cách làm là 1 lớp `implements` nó (`BubbleSortStrategy`, `SelectionSortStrategy`…); `SortService` nhận strategy qua constructor, controller chọn `new SortService(new XxxSortStrategy())` |
| **Consequences** | ➕ thêm cách làm = thêm 1 lớp, service đứng yên (**O**) · ➖ thêm 2 file — chỉ đáng khi có từ 2 cách trở lên (bài P0004 Quick sort, P0005 Merge sort làm đúng như vậy) |

### 3.2 SOLID trong bài

| Nguyên lý | Ở đâu |
|---|---|
| **S** | mỗi lớp 1 việc: `NumberArray` mô tả mảng · `NumberRepository` giữ mảng · `SortService` sinh + sắp · `SortView` in · `Validation` kiểm · `Message`/`Constants` giữ chữ/số |
| **O** | đổi câu chữ chỉ sửa `Message`; đổi thuật toán chỉ sửa **một hàm** `sortByBubble` |
| **L** | bài chưa có lớp con riêng — chỉ `extends Object` và ghi đè `toString()` đúng nghĩa |
| **I** | không có interface — bài chưa cần (xem 3.1) |
| **D** | `Main` chỉ phụ thuộc controller + DTO; không biết service, repository hay model |

---

## 4. Code từng bước

> Thầy: *"code cái **model trước rồi đến data**"*.

| Bước | File | Việc |
|---|---|---|
| 1 | `model/NumberArray.java` | field `private int[] valueArray` + constructor rỗng + constructor đủ + `getValueArray`/`setValueArray` + `getSize` · `getValue` · `swap` · `toString` |
| 2 | `repository/NumberRepository.java` | field `private NumberArray numberArray` + constructor (mảng rỗng) + `saveNumberArray(int[] valueArray)` + `getNumberArray()` |
| 3 | `dto/SortRequestDTO.java`, `SortResponseDTO.java` | JavaBean: constructor rỗng + get/set (`size`; `unsortedArray`, `sortedArray`) |
| 4 | `service/SortService.java` | field `numberRepository`, `random` + constructor; `sortRandomArray`; **`sortByBubble`** (thuật toán của đề, `private`); `generateValueArray` (`private`) |
| 5 | `view/SortView.java` | field `responseDTO` · `setResponseDTO` · `display()` **không tham số** |
| 6 | `controller/SortController.java` | constructor tạo service + view; `sortArray(requestDTO)`: `setResponseDTO` rồi `display()` **1 lần** |
| 7 | `constants/Message.java`, `Constants.java` | câu chữ + giới hạn (gõ dần khi bước trên cần) |
| 8 | `utils/Validation.java` | `getSize(String)` — **tách 2 lỗi** |
| 9 | `main/Main.java` | `public final class` + `private Main()`; `inputSize` (vòng hỏi lại) + gọi `controller.sortArray` **1 lần** |

**Bẫy hay gặp:**

1. In mảng "trước" **sau khi** đã sắp → hai dòng giống nhau. Phải **chụp chuỗi trước** rồi mới gọi `sortByBubble`.
2. Vòng trong `j < (size - 1)` (thiếu `- i`) vẫn **ra đúng** nhưng thừa phép so sánh — thầy hỏi là lộ.
3. `sc.nextInt()` + gõ chữ → văng `InputMismatchException`. Luôn `nextLine()` rồi `parseInt`.
4. Khai báo biến giữa khối (`String line = sc.nextLine();` trong vòng lặp) → tờ checklist 2.6/3.7 đánh trượt. Khai báo **đầu hàm** và **khởi tạo luôn** (`String line = "";`), trong vòng chỉ **gán**.
5. View có hàm nhận tham số (`display(dto)`, `showMessage(String)`) → tờ checklist 1.1 đánh trượt: View nhận dữ liệu **qua thuộc tính**.

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

---

## 6. Debug

| Việc | Cách làm |
|---|---|
| Breakpoint | dòng `if (numberArray.getValue(j) > numberArray.getValue(j + 1))` trong `SortService.sortByBubble` |
| Chạy | **Ctrl+F5**, nhập `5` |
| Quan sát | tab **Variables**: `i`, `j`, `swapped`; mở `numberArray` → `valueArray` |
| Bước | **F8** từng dòng — chỉ cho thầy lúc `swap` được gọi và `swapped` thành `true`; ở `sortRandomArray` bấm **F7** vào `saveNumberArray(...)` để thấy dữ liệu vào repository, rồi **F7** vào `sortByBubble(numberArray)` để vào thuật toán |
| Chứng minh dừng sớm | nhập mảng nhỏ, đến lượt không đổi chỗ thấy `if (!swapped)` → `return` |

---

## 7. Câu hỏi thầy hay hỏi

### Thuật toán

| Câu hỏi | Trả lời mẫu |
|---|---|
| Sau lượt đầu, phần tử nào chắc chắn đúng chỗ? | Phần tử **lớn nhất** — nó bị đẩy dần về cuối. |
| Vì sao vòng trong là `size - 1 - i`? | Sau `i` lượt, `i` số cuối đã đúng chỗ; và `- 1` vì so `j` với `j + 1`, không được vượt mảng. |
| Bỏ biến `swapped` thì sao? | Vẫn đúng, nhưng mảng đã sắp sẵn cũng phải chạy hết `n-1` lượt thay vì 1. |
| Độ phức tạp? | Xấu nhất `O(n²)`, tốt nhất `O(n)` nhờ `swapped`. |
| Bubble sort có ổn định (stable) không? | Có — chỉ đổi khi `>`, hai số **bằng nhau** không bao giờ đổi chỗ cho nhau. |
| Sắp giảm dần sửa gì? | Đổi `>` thành `<` trong `if`. |

### Kiến trúc theo tờ checklist

| Câu hỏi | Trả lời mẫu |
|---|---|
| Sao bài có repository? | Tờ checklist 1.1 *"Bắt buộc phải có repository"*. `NumberRepository` giữ **dữ liệu** của chương trình (mảng số) và chỉ có CRUD đơn giản: `saveNumberArray` tạo `NumberArray` từ các số vừa sinh, `getNumberArray` trả nó ra. Tính toán (sinh số, sắp xếp) ở `SortService` — luồng **Controller → Service → Repository → Model**. |
| View nhận dữ liệu thế nào? | **Qua thuộc tính**, không qua tham số: `SortView` có field `private SortResponseDTO responseDTO` + setter `setResponseDTO(...)`; `display()` không tham số. Controller gọi `setResponseDTO(responseDTO)` rồi `display()` **đúng 1 lần** cho luồng. |
| Validate ở đâu? | Ở **Main**: `Main.inputSize` đọc dòng bằng `sc.nextLine()`, đưa cho `Validation.getSize` — sai thì `Validation` ném `Exception(Message.X)`, Main bắt và in `e.getMessage()` rồi hỏi lại. Controller/service chỉ nhận `SortRequestDTO` đã sạch. |
| Sao `Main` là `final` và có `private Main()`? | Tờ checklist 3.4: *"Class chỉ có static method thì phải có private contructor, Và khai báo class là final"*. `Main` chỉ có `main` và `inputSize`, đều `static`. |
| Sao `NumberArray numberArray = null;` ở đầu `sortRandomArray`? | Tờ checklist 2.6 + 3.7: biến khai báo **tập trung đầu khối** và **khởi tạo luôn**. Giá trị thật chỉ có sau khi repository đã lưu, nên đầu hàm gán `null`, sau đó `numberArray = numberRepository.getNumberArray();`. |

### OOP / Java

| Câu hỏi | Trả lời mẫu |
|---|---|
| 4 tính chất OOP ở đâu? | **Đóng gói**: `valueArray` là `private` trong `NumberArray`, chỉ đổi qua `swap`/`setValueArray`; `numberArray` là `private` trong `NumberRepository`. **Kế thừa**: mọi lớp ngầm `extends Object`; `NumberArray` kế thừa và ghi đè `toString()`. **Đa hình**: `toString()` có `@Override` — `numberArray.toString()` chạy bản của `NumberArray`, không phải của `Object`. **Trừu tượng**: `Main` chỉ gọi `controller.sortArray(requestDTO)` — không biết bên trong có service, repository, model hay thuật toán nào. |
| Sao `random` là field, không tạo trong vòng lặp? | Một đối tượng `Random` dùng lại cho mọi phần tử; `new Random()` liên tục là lãng phí. |
| `sortByBubble` trả `void` vì sao? | Nó **đổi chính mảng** được truyền vào (truyền tham chiếu), không tạo mảng mới — không có gì để trả. Vì cùng một đối tượng, mảng trong repository cũng đã được sắp. |
| `generateValueArray` sao `private`? | Chỉ `SortService` dùng; không phải "hợp đồng" của lớp. |
| `getSize` trả `int` vì sao? | Kích thước là **số đếm** nguyên. |
| Sao `Validation.getSize` static? Bỏ đi thì sao? | Không dùng dữ liệu đối tượng nào. Bỏ `static` thì `Validation.getSize(...)` báo lỗi biên dịch; phải bỏ `private` constructor, tạo `Validation v = new Validation();` trong `Main` rồi gọi `v.getSize(...)`. |
| Sao `NumberArray` có constructor rỗng? | Thầy dạy **MVC kiểu JSP**: model là **JavaBean** — field `private`, **constructor rỗng `public`**, getter/setter. `NumberRepository` dùng nó để có mảng rỗng lúc khởi tạo. |
| Sao `MAX_SIZE = 1000`? | Đề không cho trần, nhưng gõ nhầm `99999999` sẽ tốn bộ nhớ và in cả trang — trần giữ chương trình an toàn. |
| Interface khác abstract class? | Interface chỉ là **hợp đồng** (không field trạng thái, một lớp implements được nhiều interface); abstract class chứa được code chung + field. Bài này chưa có cả hai — nếu tách Strategy (mục 3.1) thì chọn **interface** `ISortStrategy` vì các thuật toán không chung dòng code nào. |

---

## 8. Thầy đổi yêu cầu tại chỗ

| Thầy bảo | Sửa |
|---|---|
| Đổi sang **selection sort** | chỉ thân hàm sắp trong `SortService` (đổi tên thành `sortBySelection`) — `Main`, `Controller`, `View`, repository, model giữ nguyên |
| Sắp **giảm dần** | `>` → `<` trong `SortService.sortByBubble` |
| Số ngẫu nhiên trong `[-n, n]` | `generateValueArray`: `valueArray[i] = random.nextInt((2 * size) + 1) - size;` |
| In thêm **số lượt** đã chạy | `sortByBubble` trả `int` số lượt → thêm field vào `SortResponseDTO` → `SortView.display()` in thêm dòng |

---

## 9. Chỗ khác với lời giải cũ trên web

| Chỗ | Bản cũ | Bài này | Lý do |
|---|---|---|---|
| Prompt | `Please input the number of array: ` | `Enter number of array:` (xuống dòng) | **đúng màn hình đề** |
| Khoảng số ngẫu nhiên | `[0, 100)` | `[0, n)` | đề: *"random integer in number range input"*, ví dụ n=10 → 0..9 |
| Kiến trúc | `bo/ui/utils`, Scanner trong `Validator` | MVC theo Guide, Scanner **chỉ ở `main`** | luật thầy |
| Thuật toán | hàm `bubbleSort` trong `ArraySorter` (tầng `bo`) | hàm `sortByBubble` (`private`) trong `SortService` | thuật toán là **nghiệp vụ** → tầng service (Guide) |

### 9.1 Đổi theo tờ checklist giấy (21/09/2026)

Màn hình chạy **không đổi một chữ** (`verify.py` so từng dòng, cả en_US và vi_VN).

| Chỗ | Bản trước | Bây giờ | Mục checklist |
|---|---|---|---|
| Tầng dữ liệu | không có `repository`, mảng chỉ sống trong service | `repository/NumberRepository` giữ `NumberArray` | 1.1 |
| View | `setResponse(response)` | field `responseDTO` + `setResponseDTO(...)` + `display()` | 1.1 |
| Main | `public class Main` | `public final class Main` + `private Main()` | 3.4 |
| Tên hàm | `bubbleSort`, `generateArray` | `sortByBubble`, `generateValueArray` | 1.4 |
| Tên mảng | `int[] values`, `getValues/setValues` | `int[] valueArray`, `getValueArray/setValueArray` | 1.5 |
| Khai báo biến | `String line = sc.nextLine();` giữa vòng lặp; `int size;` chưa gán | `String line = "";` đầu hàm, trong vòng chỉ gán; `int size = 0;` | 2.6, 3.7 |
| Ngoặc | `size < MIN_SIZE \|\| size > MAX_SIZE`, `i < size - 1` | `(size < …) \|\| (size > …)`, `i < (size - 1)` | 3.3 |
| In nhãn | `Message.LABEL_SORTED + chuỗi` | `String.format(Message.SORTED_ARRAY, chuỗi)` | 3.8 |
| Dòng trống | comment của các hằng/field dính nhau | 1 dòng trống trước mỗi comment, sau vùng khai báo, sau mỗi `}` | 2.8 |

---

## 10. Tờ checklist 25 mục — bài này đạt thế nào

| Mục | Chỉ vào đâu |
|---|---|
| 1.1 | `repository/NumberRepository` (bắt buộc có); `SortController` chỉ import `dto`/`service`/`view`; `SortView` nhận `responseDTO` qua setter, `display()` gọi 1 lần; `Main` gọi `controller.sortArray` 1 lần |
| 1.3 / 1.4 | lớp là danh từ (`NumberRepository`, `SortService`); hàm mở đầu bằng động từ: `sortByBubble`, `generateValueArray`, `saveNumberArray`, `sortRandomArray` |
| 1.5 | biến/field kiểu mảng đuôi `Array`: `int[] valueArray` (`NumberArray`, `SortService.generateValueArray`) |
| 2.6 + 3.7 | `String line = "";` (`Main.inputSize`), `int size = 0;` (`Validation.getSize`), `NumberArray numberArray = null;` (`SortService.sortRandomArray`), `boolean swapped = false;` đầu thân `for` |
| 2.8 | 1 dòng trống giữa các hằng/field có comment, sau vùng khai báo biến, sau mỗi `}` trước câu lệnh kế |
| 3.3 | `if ((size < Constants.MIN_SIZE) \|\| (size > Constants.MAX_SIZE))`; `i < (size - 1)`; `j < (size - 1 - i)` |
| 3.4 | `public final class Main` + `private Main()`; `Validation`, `Message`, `Constants` cũng `final` + private constructor |
| Soát máy | `checklist_audit.py` → **0 VI_PHAM**; còn RUI_RO: `String[] args` của `main` và tham số setter/constructor trùng tên field (`this.size = size` — IDE sinh) |
