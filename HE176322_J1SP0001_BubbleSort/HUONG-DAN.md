# J1.S.P0001 — Bubble Sort

> Bài thuật toán **vẫn phải làm MVC** — thầy: *"Các bài liên quan thuật toán như fibo, sắp xếp,…
> cũng phải làm MVC, không OOP/MVC → không review."* Bài này là khuôn cho mọi bài sắp xếp/tìm
> kiếm (P0002–P0006, P0010).

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
| Vòng trong dừng ở `size - 1 - i` | `for (int j = 0; j < size - 1 - i; j++)` | sau `i` lượt, `i` số cuối đã đúng chỗ — so tiếp là thừa |
| Cờ `swapped` | `if (!swapped) return;` | đúng câu của đề: *"If at least one swap has been done, repeat"* |

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

---

## 3. Thiết kế

```
HE176322_J1SP0001_BubbleSort/src/
├── model/      NumberArray           mảng số (JavaBean) + getSize/getValue/swap/toString
├── dto/        SortRequestDTO        size          (main ──► controller)
│               SortResponseDTO       2 chuỗi mảng  (controller ──► view)
├── service/    SortService           sinh mảng + bubbleSort ← thuật toán của đề ở ĐÂY
├── controller/ SortController        service ──► view
├── view/       SortView              in 2 dòng kết quả
├── constants/  Message.java          câu chữ màn hình
│               Constants.java        MIN_SIZE = 1, MAX_SIZE = 1000
├── utils/      Validation            getSize(chuỗi) → int hoặc ném lỗi
└── main/       Main                  Scanner + gọi controller 1 lần
```

| Câu hỏi thiết kế | Trả lời |
|---|---|
| Sao có `service` mà không có `repository`? | Guide: repository = **giữ dữ liệu + CRUD**. Bài này không lưu gì, không thêm/sửa/xoá. Thuật toán là **"tính toán nghiệp vụ"** → `service`. |
| Sao `NumberArray` không tự sắp xếp? | Model chỉ mô tả **đối tượng mảng** (lấy phần tử, đổi chỗ). Cách sắp xếp là **nghiệp vụ** — tách ra để đổi thuật toán không phải sửa model. |
| Sao controller không đụng `NumberArray`? | Guide: controller *"chỉ import DTO, View, Service"* → service nhận `RequestDTO`, trả `ResponseDTO`. |

**Luồng chạy:**

```
Main: đọc size (hỏi lại khi sai) ──► SortRequestDTO ──► controller.sortArray(dto)
   controller ──► service.sortRandomArray(dto)
                     ├─ generateArray(size)       → NumberArray ngẫu nhiên
                     ├─ response.setUnsorted(...) ← chụp TRƯỚC khi sắp
                     ├─ bubbleSort(array)          ← thuật toán của đề chạy ở đây
                     └─ response.setSorted(...)
   controller ──► view.setResponse(response) ──► view.display()
```

### 3.1 Design Pattern trong bài

| Pattern | Ở đâu |
|---|---|
| **MVC** — thầy gọi là "MVC JSP" | controller điều hướng (như Servlet) · view hiển thị (như trang JSP) · model là JavaBean |
| **Facade** | controller: `Main` chỉ gọi `controller.sortArray(dto)`, không biết service/model/view phía sau |

> Bài chỉ 40 LOC nên **không thêm lớp pattern GoF** — ghi chú slide SOLID của thầy cảnh báo *"trừu tượng hoá sớm … vi phạm YAGNI"*. Thuật toán nằm gọn trong **một hàm** `bubbleSort` của `SortService`.

**Thầy hỏi "dùng pattern gì cho dễ mở rộng?"** → trả lời đủ 4 yếu tố GoF (slide *"Elements of a Design Pattern"*):

| Yếu tố | Trả lời |
|---|---|
| **Name** | Strategy (nhóm Behavioral) |
| **Problem** | nhiều thuật toán sắp xếp cùng làm một việc; viết thẳng vào service thì đổi thuật toán = sửa service |
| **Solution** | tách `interface SortStrategy { void sort(NumberArray array); }`; mỗi cách làm là 1 lớp `implements` nó (`BubbleSortStrategy`, `SelectionSortStrategy`…); `SortService` nhận strategy qua constructor, controller chọn `new SortService(new XxxSortStrategy())` |
| **Consequences** | ➕ thêm cách làm = thêm 1 lớp, service đứng yên (**O**) · ➖ thêm 2 file — chỉ đáng khi có từ 2 cách trở lên (bài P0004 Quick sort, P0005 Merge sort làm đúng như vậy) |

### 3.2 SOLID trong bài

| Nguyên lý | Ở đâu |
|---|---|
| **S** | mỗi lớp 1 việc: `NumberArray` giữ số · `SortService` sinh + sắp · `SortView` in · `Validation` kiểm · `Message`/`Constants` giữ chữ/số |
| **O** | đổi câu chữ chỉ sửa `Message`; đổi thuật toán chỉ sửa **một hàm** `bubbleSort` |
| **L** | bài chưa có lớp con riêng — chỉ `extends Object` và ghi đè `toString()` đúng nghĩa |
| **I** | không có interface — bài chưa cần (xem 3.1) |
| **D** | `Main` chỉ phụ thuộc controller + DTO; không biết service hay model |

---

## 4. Code từng bước

> Thầy: *"code cái **model trước rồi đến data**"*.

| Bước | File | Việc |
|---|---|---|
| 1 | `model/NumberArray.java` | field `private int[] values` + constructor rỗng + constructor đủ + get/set + `getSize` · `getValue` · `swap` · `toString` |
| 2 | `dto/SortRequestDTO.java`, `SortResponseDTO.java` | JavaBean: constructor rỗng + get/set |
| 3 | `service/SortService.java` | field `random`; `sortRandomArray`; **`bubbleSort`** (thuật toán của đề, `private`); `generateArray` (`private`) |
| 4 | `view/SortView.java` | `setResponse` · `display` |
| 5 | `controller/SortController.java` | constructor tạo service + view; `sortArray(dto)` |
| 6 | `constants/Message.java`, `Constants.java` | câu chữ + giới hạn (gõ dần khi bước trên cần) |
| 7 | `utils/Validation.java` | `getSize(String)` — **tách 2 lỗi** |
| 8 | `main/Main.java` | `inputSize` (vòng hỏi lại) + gọi `controller.sortArray` **1 lần** |

**Bẫy hay gặp:**

1. In mảng "trước" **sau khi** đã sắp → hai dòng giống nhau. Phải **chụp chuỗi trước** rồi mới gọi `bubbleSort`.
2. Vòng trong `j < size - 1` (thiếu `- i`) vẫn **ra đúng** nhưng thừa phép so sánh — thầy hỏi là lộ.
3. `sc.nextInt()` + gõ chữ → văng `InputMismatchException`. Luôn `nextLine()` rồi `parseInt`.

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
| Breakpoint | dòng `if (array.getValue(j) > array.getValue(j + 1))` trong `SortService.bubbleSort` |
| Chạy | **Ctrl+F5**, nhập `5` |
| Quan sát | tab **Variables**: `i`, `j`, `swapped`; mở `array` → `values` |
| Bước | **F8** từng dòng — chỉ cho thầy lúc `swap` được gọi và `swapped` thành `true`; ở `sortRandomArray` bấm **F7** vào `bubbleSort(array)` để vào thuật toán |
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

### OOP / Java

| Câu hỏi | Trả lời mẫu |
|---|---|
| 4 tính chất OOP ở đâu? | **Đóng gói**: `values` là `private` trong `NumberArray`, chỉ đổi qua `swap`/`setValues`. **Kế thừa**: mọi lớp ngầm `extends Object`; `NumberArray` kế thừa và ghi đè `toString()`. **Đa hình**: `toString()` có `@Override` — `array.toString()` chạy bản của `NumberArray`, không phải của `Object`. **Trừu tượng**: `Main` chỉ gọi `controller.sortArray(dto)` — không biết bên trong có service, model hay thuật toán nào. |
| Sao `random` là field, không tạo trong vòng lặp? | Một đối tượng `Random` dùng lại cho mọi phần tử; `new Random()` liên tục là lãng phí. |
| `bubbleSort` trả `void` vì sao? | Nó **đổi chính mảng** được truyền vào (truyền tham chiếu), không tạo mảng mới — không có gì để trả. |
| `generateArray` sao `private`? | Chỉ `SortService` dùng; không phải "hợp đồng" của lớp. |
| `getSize` trả `int` vì sao? | Kích thước là **số đếm** nguyên. |
| Sao `Validation.getSize` static? Bỏ đi thì sao? | Không dùng dữ liệu đối tượng nào. Bỏ `static` thì `Validation.getSize(...)` báo lỗi biên dịch; phải bỏ `private` constructor, tạo `Validation v = new Validation();` trong `Main` rồi gọi `v.getSize(...)`. |
| Sao `NumberArray` có constructor rỗng dù không dùng? | Thầy dạy **MVC kiểu JSP**: model là **JavaBean** — field `private`, **constructor rỗng `public`**, getter/setter. |
| Sao `MAX_SIZE = 1000`? | Đề không cho trần, nhưng gõ nhầm `99999999` sẽ tốn bộ nhớ và in cả trang — trần giữ chương trình an toàn. |
| Interface khác abstract class? | Interface chỉ là **hợp đồng** (không field trạng thái, một lớp implements được nhiều interface); abstract class chứa được code chung + field. Bài này chưa có cả hai — nếu tách Strategy (mục 3.1) thì chọn **interface** vì các thuật toán không chung dòng code nào. |

---

## 8. Thầy đổi yêu cầu tại chỗ

| Thầy bảo | Sửa |
|---|---|
| Đổi sang **selection sort** | chỉ thân hàm sắp trong `SortService` (đổi tên thành `selectionSort`) — `Main`, `Controller`, `View`, model giữ nguyên |
| Sắp **giảm dần** | `>` → `<` trong `SortService.bubbleSort` |
| Số ngẫu nhiên trong `[-n, n]` | `generateArray`: `random.nextInt(2 * size + 1) - size` |
| In thêm **số lượt** đã chạy | `bubbleSort` trả `int` số lượt → thêm field vào `SortResponseDTO` → `SortView` in thêm dòng |

---

## 9. Chỗ khác với lời giải cũ trên web

| Chỗ | Bản cũ | Bài này | Lý do |
|---|---|---|---|
| Prompt | `Please input the number of array: ` | `Enter number of array:` (xuống dòng) | **đúng màn hình đề** |
| Khoảng số ngẫu nhiên | `[0, 100)` | `[0, n)` | đề: *"random integer in number range input"*, ví dụ n=10 → 0..9 |
| Kiến trúc | `bo/ui/utils`, Scanner trong `Validator` | MVC theo Guide, Scanner **chỉ ở `main`** | luật thầy |
| Thuật toán | hàm `bubbleSort` trong `ArraySorter` (tầng `bo`) | hàm `bubbleSort` (`private`) trong `SortService` | thuật toán là **nghiệp vụ** → tầng service (Guide) |
