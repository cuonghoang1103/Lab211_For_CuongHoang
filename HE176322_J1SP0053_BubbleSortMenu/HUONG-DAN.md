# J1.S.P0053 — Bubble Sort Menu (tăng dần / giảm dần)

> Bài sắp xếp **có menu** và **hai chiều sắp**: hai hàm `sortAscending` / `sortDescending` trong `SortService`.
> Mảng được **giữ lại** giữa các lựa chọn menu nên
> bài có thêm `repository`.

| | |
|---|---|
| Loại / LOC | Short Assignment · 42 LOC · 1 slot |
| Project | `HE176322_J1SP0053_BubbleSortMenu` |
| Chạy | NetBeans: **File ▸ Open Project** → **F6** |
| Lớp chạy | `main.Main` |
| Kiểm tự động | `python3 _tools/verify.py J1SP0053` → 3 kịch bản × 2 locale |

---

## 1. Đề bài nói gì

Menu 4 mục: **1** nhập mảng (độ dài > 0, phần tử là số nguyên) · **2** in tăng dần · **3** in giảm
dần · **4** thoát. Sau mỗi mục quay về menu.

Màn hình đề (chép đúng chữ, **kể cả lỗi chính tả** `numberand`, `Please choice`):

```
========= Bubble Sort program =========
1. Input Element
2. Sort Ascending
3. Sort Descending
4. Exit
Please choice one option:
1
----- Input Element -----
Input Length Of Array
Enter Number: a
Please input numberand number is greater than zero
Enter Number: -1
Please input numberand number is greater than zero
Enter Number: 3
Enter Number 1: 5
Enter Number 2: 1
Enter Number 3: 3
...
----- Ascending -----
[1]->[3]->[5]
...
----- Descending -----
[5]<-[3]<-[1]
```

**Đề bắt buộc** (mục Suggestion):

| Thứ | Đề viết | Bài này đặt ở |
|---|---|---|
| `public Integer checkIn(String inputVal)` — trả số hoặc `null` | Function 1 | `utils/Validation.checkIn` (static) — **mọi** số nhập đều đi qua nó |
| `int[] sortAscending(int[] arrayNeedSort)` | Function 2 | `service/SortService.sortAscending` (private) |
| `int[] sortDescending(int[] arrayNeedSort)` | Function 3 | `service/SortService.sortDescending` (private) |
| Bubble sort, Format 1 (đầu → cuối) và Format 2 (cuối → đầu) | Suggestion | `SortService.sortAscending` (Format 1), `SortService.sortDescending` (hướng Format 2) |

**Tên `checkIn`:** đề viết 3 kiểu — `checkln` (chữ **l** thường) trong danh sách, `chechIn` trong câu
văn, `checkIn` ở **dòng chữ ký**. Em chọn `checkIn` vì dòng chữ ký là dòng định nghĩa hàm, và
"check **In**put" mới có nghĩa; `checkln` là do font không chân: **I** hoa và **l** thường trông y hệt.

---

## 2. Kiến thức cần biết

### 2.1 Format 1 — tăng dần, đi từ đầu về cuối (`sortAscending`)

So từng cặp kề `j, j+1`; số **lớn** hơn đứng trước thì đổi → số lớn nhất "nổi" về **cuối**.
Chạy tay ví dụ đề `[5, 1, 3]`:

| Lượt `i` | Cặp `j` | So | Việc | Mảng |
|---|---|---|---|---|
| 0 | 0 | 5 > 1 | đổi | `1 5 3` |
| 0 | 1 | 5 > 3 | đổi | `1 3 5` ← **5 đúng chỗ** |
| 1 | 0 | 1 < 3 | giữ | **không đổi lần nào → dừng** |

Kết quả `[1]->[3]->[5]`.

### 2.2 Hướng Format 2 — giảm dần, đi từ cuối về đầu (`sortDescending`)

Đi `j` từ **cuối** về `i+1`; số bên phải **lớn hơn** số bên trái thì đổi → số lớn nhất "nổi" về
**đầu**. (Format 2 của đề đưa số **nhỏ** về đầu = tăng dần; em **đảo phép so** để ra giảm dần.)

| Lượt `i` | Cặp `(j-1, j)` | So | Việc | Mảng |
|---|---|---|---|---|
| 0 | (1, 2) | 3 > 1 | đổi | `5 3 1` |
| 0 | (0, 1) | 3 > 5? không | giữ | `5 3 1` ← **5 đúng chỗ** |
| 1 | (1, 2) | 1 > 3? không | giữ | **không đổi → dừng** |

Kết quả `[5]<-[3]<-[1]`.

### 2.3 Độ phức tạp (cả hai)

| Trường hợp | So sánh |
|---|---|
| Tốt nhất (đã đúng chiều) | `n - 1`, dừng sau 1 lượt nhờ `swapped` |
| Xấu nhất (ngược chiều) | `n(n-1)/2` → **O(n²)** |

### 2.4 Java dùng trong bài

| API | Dùng làm gì |
|---|---|
| `Integer.valueOf(s)` | chuỗi → `Integer`; sai → `NumberFormatException` → `checkIn` trả `null` |
| `Arrays.copyOf(a, n)` | bản sao mảng — sắp để **in** không làm hỏng mảng đã nhập |
| `StringBuilder` | ghép `[1]->[3]->[5]` |

---

## 3. Thiết kế

```
HE176322_J1SP0053_BubbleSortMenu/src/
├── model/       NumberArray            mảng int (JavaBean) + getSize/getValue/swap
├── dto/         ArrayRequestDTO        phần tử vừa nhập  (main ──► controller)
│                SortResponseDTO        mảng đã sắp       (controller ──► view)
├── repository/  ArrayRepository        giữ mảng giữa các lần chọn menu; trả BẢN SAO
├── service/     SortService            saveArray, getAscending/getDescending,
│                                        sortAscending/sortDescending (đề bắt)
├── controller/  ArrayController        1 hàm / mục menu; service ──► view
├── view/        ArrayView              displayAscending ("->"), displayDescending ("<-")
├── constants/   Message, Constants     câu chữ đề; số menu; MIN/MAX_LENGTH; "[%d]", mũi tên
├── utils/       Validation             checkIn (đề bắt), getChoice, getLength, getElement
└── main/        Main                   menu while + switch, Scanner
```

| Câu hỏi | Trả lời |
|---|---|
| Sao có `repository`? | Đề: *"Save element(s) of array"* — mảng phải **được giữ** từ mục 1 tới mục 2, 3. Guide: repository = nơi chứa data. |
| Sao controller chỉ gọi service? | Guide: *"Controller <-> Services <-> Repository <-> Model"* — service giữ repository. |
| Sao repository trả **bản sao**? | Sắp tăng rồi sắp giảm phải xuất phát từ **đúng mảng đã nhập**; hàm sắp đổi mảng tại chỗ, nên đưa bản sao. |

**Luồng mục 2:**

```
Main: case 2 ──► controller.sortAscending()
   controller ──► service.getAscending()
                    ├─ repository.getValues()   → bản sao (chưa nhập → throw "Please input the array first")
                    ├─ sortAscending(values)    → new NumberArray → bubble sort Format 1
                    └─ new SortResponseDTO(sorted)
   controller ──► view.setResponse(r) ──► view.displayAscending()  → "[1]->[3]->[5]"
```

### 3.1 Design Pattern trong bài

| Pattern | Ở đâu |
|---|---|
| **MVC** — thầy gọi là "MVC JSP" | controller điều hướng (như Servlet) · view hiển thị (như trang JSP) · model là JavaBean |
| **Facade** | controller: `Main` chỉ gọi `controller.sortAscending()`, không biết service/model/view phía sau |

> Bài chỉ 42 LOC nên **không thêm lớp pattern GoF** — ghi chú slide SOLID của thầy cảnh báo *"trừu tượng hoá sớm … vi phạm YAGNI"*. Hai chiều sắp là **hai hàm** `sortAscending` / `sortDescending` trong `SortService`, đúng hai Format đề mô tả.

**Thầy hỏi "dùng pattern gì cho dễ mở rộng?"** → trả lời đủ 4 yếu tố GoF (slide *"Elements of a Design Pattern"*):

| Yếu tố | Trả lời |
|---|---|
| **Name** | Strategy (nhóm Behavioral) |
| **Problem** | hai (hoặc nhiều) cách sắp cùng một mảng |
| **Solution** | tách `interface SortStrategy { void sort(NumberArray array); }`; mỗi cách làm là 1 lớp `implements` nó (`AscendingSortStrategy`, `DescendingSortStrategy`); `SortService` nhận 2 strategy qua constructor |
| **Consequences** | ➕ thêm cách làm = thêm 1 lớp, service đứng yên (**O**) · ➖ thêm 2 file — chỉ đáng khi có từ 2 cách trở lên |

### 3.2 SOLID trong bài

| Nguyên lý | Ở đâu |
|---|---|
| **S** | `NumberArray` giữ số · `ArrayRepository` giữ mảng đã nhập · `SortService` sắp · `ArrayView` in · `Validation` kiểm |
| **O** | đổi thuật toán chỉ sửa thân 2 hàm sắp; thêm mục menu không đụng repository |
| **L** | bài chưa có lớp con riêng — chỉ `extends Object` |
| **I** | không có interface — bài chưa cần |
| **D** | `Main` chỉ phụ thuộc controller + DTO; không biết repository |

---

## 4. Code từng bước

> Thầy: *"code cái **model trước rồi đến data**"*.

| Bước | File | Việc |
|---|---|---|
| 1 | `model/NumberArray.java` | `private int[] values` + ctor rỗng + ctor đủ + get/set + `getSize`/`getValue`/`swap`/`toString` |
| 2 | `dto/ArrayRequestDTO.java`, `SortResponseDTO.java` | JavaBean |
| 3 | `repository/ArrayRepository.java` | `saveArray`, `getValues` (bản sao; chưa có → throw) |
| 4 | `service/SortService.java` | ctor tạo repository; `saveArray`; `getAscending/getDescending`; `sortAscending/sortDescending` (`private`, bubble sort mục 2.1, 2.2) |
| 5 | `view/ArrayView.java` | `setResponse`, `displayAscending`, `displayDescending`, `join` (private) |
| 6 | `controller/ArrayController.java` | `inputArray`, `sortAscending`, `sortDescending` |
| 7 | `constants/Message.java`, `Constants.java` | câu chữ **chép đúng đề**; số menu; format |
| 8 | `utils/Validation.java` | `checkIn` → `getChoice`, `getLength`, `getElement` đều gọi nó |
| 9 | `main/Main.java` | menu `while` + `switch`, `inputChoice/inputLength/inputElement/inputArray` |

**Bẫy hay gặp:**

1. Sắp thẳng mảng đã lưu → lần sau mảng "đã nhập" bị đổi thứ tự. Repository trả **bản sao**.
2. Vòng ngược `for (int j = size - 1; j > i; j--)` — viết `j >= i` là tràn `j - 1 = -1`.
3. Viết `checkIn` rồi không dùng — thầy hỏi *"hàm này gọi ở đâu?"*. Ở bài này **mọi** số đi qua nó.
4. Sửa "numberand" thành "number and" → **sai màn hình đề**. Giữ nguyên.

---

## 5. Test trước khi gọi thầy

| # | Gõ | Phải thấy |
|---|---|---|
| 1 | `1`, `a`, `-1`, `3`, `5 1 3` | 2 lần `Please input numberand number is greater than zero`, rồi hỏi `Enter Number 1..3` |
| 2 | `2` | `----- Ascending -----` / `[1]->[3]->[5]` |
| 3 | `3` | `----- Descending -----` / `[5]<-[3]<-[1]` |
| 4 | menu `x` | `Please input number` |
| 5 | menu `9`, `0` | `Please choose from 1 to 4.` |
| 6 | `2` hoặc `3` **trước** khi nhập | `Please input the array first (option 1).` |
| 7 | độ dài `0` | câu lỗi của đề |
| 8 | độ dài `1001` | `Length must not be greater than 1000.` |
| 9 | phần tử `abc`, `2.5`, trống | `Please input number` rồi hỏi lại **đúng số thứ tự đó** |
| 10 | phần tử âm, 0, trùng: `-7 0 12 -7` | `[-7]->[-7]->[0]->[12]` và `[12]<-[0]<-[-7]<-[-7]` |
| 11 | chọn `1` lần hai, nhập `42` | mảng mới thay mảng cũ: `[42]` |
| 12 | `5 4 3 2 1`, rồi `2`, `3`, `2` | tăng/giảm đúng; lần `2` thứ hai vẫn giống lần đầu (mảng gốc không đổi) |
| 13 | `4` | thoát |

---

## 6. Debug

| Việc | Cách làm |
|---|---|
| Breakpoint | dòng `if (array.getValue(j) > array.getValue(j - 1))` trong `SortService.sortDescending` |
| Chạy | **Ctrl+F5**: `1`, `3`, `5 1 3`, rồi `3` |
| Quan sát | **Variables**: `i`, `j` (thấy `j` **giảm**), `swapped`, mở `array` → `values` |
| Bước | ở `getDescending` bấm **F7** vào `sortDescending(...)` — vào đúng hàm giảm dần |
| Mảng gốc không đổi | breakpoint cuối `ArrayRepository.getValues`, so `numberArray.values` trước/sau khi sắp |

---

## 7. Câu hỏi thầy hay hỏi

| Câu hỏi | Trả lời mẫu |
|---|---|
| 4 tính chất OOP ở đâu? | **Đóng gói**: `values` `private` trong `NumberArray`, đổi chỗ qua `swap`; repository trả **bản sao**. **Kế thừa**: mọi lớp ngầm `extends Object`; ghi đè `toString()`. **Đa hình**: `@Override toString`. **Trừu tượng**: `Main` chỉ gọi `controller.sortAscending()`, không biết mảng nằm ở repository. |
| `checkIn` sao trả `Integer`, không `int`? | Đề: *"the number **or null**"* — `int` không có `null`. |
| Sao `checkIn` static? Bỏ thì sao? | Không dùng dữ liệu đối tượng. Bỏ `static` → `Validation.checkIn(...)` lỗi biên dịch; phải bỏ `private` ctor, `new Validation()` trong `Main` rồi gọi qua đối tượng (và `getChoice`… cũng phải bỏ static theo). |
| `sortAscending` sao `private`? Đề đâu ghi? | Đề không ghi access modifier; chỉ `SortService` gọi → `private` (thầy: `public` chỉ khi lớp khác gọi). |
| `sortAscending` sao trả `int[]`? | **Đúng chữ ký đề**: *"Return value: the array containing the elements sorted"*. |
| `sortAscending` sao trả `int[]`? | Đề bắt chữ ký `int[] sortAscending(int[] arrayNeedSort)` — trả mảng đã sắp để view in. |
| `getValues` của repository sao `throws Exception`? | Chưa nhập mà chọn 2/3 → báo lỗi bằng Exception mang câu `Message`, `Main` bắt và in. |
| Hàm nào `private`? | `join` (view), `sortAscending/sortDescending` (service), mọi hàm nhập trong `Main`. Mọi field `private`. |
| Static trong `Main`? | Chỉ **hàm** (`inputChoice`…) vì `main` static; Scanner là **biến cục bộ**. |
| **Sao `ArrayList` mà không `List`? Khác nhau thế nào?** | Bài này dùng **mảng `int[]`** vì đề bắt chữ ký `int[]` và độ dài biết trước. Nguyên tắc chung: `List` là **interface**, `ArrayList` là **lớp cài đặt** (mảng động, tự nới) — em khai báo đúng kiểu cụ thể `ArrayList<X>`, chỉ để `List` khi đề bắt. |
| Mảng `int[]` khác `ArrayList<Integer>`? | `int[]` cố định độ dài, chứa số nguyên thuỷ; `ArrayList` tự nới, chỉ chứa đối tượng (`Integer`, tốn bộ nhớ hơn). |
| Bubble sort ổn định không? | Có — chỉ đổi khi **lớn hơn hẳn**, hai số bằng nhau không đổi chỗ. |
| Sao `MAX_LENGTH = 1000`? | Đề chỉ cấm ≤ 0. Gõ nhầm `999999999` sẽ cấp 4 GB → chương trình chết; trần giữ an toàn. |

---

## 8. Thầy đổi yêu cầu tại chỗ

| Thầy bảo | Sửa file | Không đụng |
|---|---|---|
| Tăng dần bằng **Format 2** | `SortService.sortAscending` (vòng ngược, so `<`) | `Main`, `View` |
| Đổi sang **selection sort** | thân `sortAscending`/`sortDescending` trong `SortService` | mọi file khác |
| In thêm **mảng gốc** (mục mới) | `Message.MENU`, `Constants` (số menu), `SortService.getOriginal`, `ArrayController`, `ArrayView`, `case` ở `Main` | model |
| Phần tử phải dương | `Validation.getElement` + 1 câu `Message` | mọi file khác |
| In `[1, 3, 5]` thay mũi tên | chỉ `ArrayView.join` / `Constants` | service, controller |

---

## 9. Chỗ khác với đề / bản cũ

| Chỗ | Đề / bản cũ | Bài này | Lý do |
|---|---|---|---|
| Tên hàm kiểm | đề: `checkln` / `chechIn` / `checkIn` | `checkIn` | dòng chữ ký của đề (mục 1) |
| `checkIn` | đề: không nói lớp; bản cũ: `public static` trong `Main` | `public static` trong `utils/Validation` | Guide: kiểm dữ liệu ở utils, static |
| `sortAscending/Descending` | đề không ghi lớp; bản cũ: `void`, trong `Main` | `private int[]` trong `SortService` | đúng kiểu trả về đề; sắp xếp là nghiệp vụ → service |
| Màn hình | bản cũ: `===== Array Sort Program =====`, `Length of array: `, in `[1, 3, 5]` | **đúng màn hình đề** | đề thắng → test đặt `REPLACE_REFERENCE = True` |
| Menu prompt | bản cũ `Please choose one option: ` | `Please choice one option:` (xuống dòng) | chữ của đề |
| Câu lỗi độ dài | bản cũ `You must input a number.` | `Please input numberand number is greater than zero` | chữ của đề (giữ cả lỗi chính tả) |
| Câu thêm (đề không có) | — | `Please input number`, `Please choose from 1 to 4.`, `Length must not be greater than 1000.`, `Please input the array first (option 1).` | đề im lặng; cần để không văng lỗi |
| Thoát | bản cũ in `Bye` | không in gì | màn hình đề chỉ có `4` |
| Thuật toán | bản cũ: 1 hàm + cờ `boolean ascending` | 2 hàm `sortAscending` / `sortDescending` — đúng Format 1 và hướng Format 2 của đề | đề mô tả 2 Format; mỗi hàm một việc (S) |
