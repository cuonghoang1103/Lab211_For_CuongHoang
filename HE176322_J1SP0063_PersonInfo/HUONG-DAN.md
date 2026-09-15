# J1.S.P0063 — Input and display Person Info

> Bài nhập 3 người rồi **sắp theo lương bằng bubble sort**. Có **3 hàm đề bắt**
> (`inputPersonInfo`, `displayPersonInfo`, `sortBySalary`) — mỗi hàm về **đúng tầng**, và phần sắp
> xếp bằng bubble sort ngay trong `PersonService.sortBySalary`.

| | |
|---|---|
| Loại / LOC | Short Assignment · 25 LOC · 1 slot |
| Project | `HE176322_J1SP0063_PersonInfo` |
| Chạy | NetBeans: **File ▸ Open Project** → **F6** |
| Lớp chạy | `main.Main` |
| Kiểm tự động | `python3 _tools/verify.py J1SP0063` → 4 kịch bản × 2 locale |

---

## 1. Đề bài nói gì

- Nhập **3 người**: tên, địa chỉ, lương. Lương sai thì **chỉ hỏi lại lương** (tên, địa chỉ giữ nguyên).
- In cả 3 người **theo lương tăng dần**, mỗi người một khối, cách nhau **1 dòng trống**.

Màn hình đề (lỗi `digidt` của màn hình đã sửa theo Guidelines — xem mục 9):

```
=====Management Person programer=====
Input Information of Person
Please input name:NghiaNV
Please input address:Ha Noi
Please input salary:abc
You must input digit.
Please input salary:-2000
Salary is greater than zero
Please input salary:2000
Input Information of Person
...                                  (LienVT 500, TuanNT 1000)
Information of Person you have entered:
Name:LienVT
Address:Ha Noi
Salary:500.0

Information of Person you have entered:
Name:TuanNT
...
```

**Đề bắt buộc** (mục Guidelines):

| Thứ | Đề viết | Bài này đặt ở |
|---|---|---|
| Lớp `Person` (`name`, `address`, `double salary`, constructors, get/set) | *"Create a class named Person"* | `model/Person` |
| `Person inputPersonInfo(String name, String address, String sSalary) throws Exception` | *"Create function"* | `service/PersonService.inputPersonInfo(PersonRequestDTO)` — **giữ tên**, 3 tham số gói vào DTO (luật thầy, mục 9) |
| `void displayPersonInfo(Person person)` | *"Create a function"* | `view/PersonView.displayPersonInfo(PersonResponseDTO)` — in là việc của **view** |
| `Person[] sortBySalary(Person[] person) throws Exception` — **BubbleSort** | *"Create function"* | `service/PersonService.sortBySalary` — **giữ nguyên chữ ký**; bubble sort viết ngay trong hàm |
| *"Create an array of 3 person"* | | `Constants.NUMBER_OF_PERSONS = 3`, mảng `Person[]` |
| Lỗi | `Salary is greater than zero` · `You must input Salary.` · `You must input digit.` · `Can't Sort Person` | `constants/Message` — **chép đúng từng chữ** |

---

## 2. Kiến thức cần biết

### 2.1 Kiểm lương — 3 lỗi, đúng thứ tự

| Thứ tự | Kiểm | Ví dụ | Thông báo |
|---|---|---|---|
| 1 | trống? | *(Enter)* | `You must input Salary.` |
| 2 | có đúng dạng số thập phân `-?\d+(\.\d+)?`? | `abc`, `1e3`, `NaN` | `You must input digit.` |
| 3 | `> 0`? | `-2000`, `0` | `Salary is greater than zero` |

> Vì sao dùng **regex** trước `Double.parseDouble`? Vì `parseDouble` nhận cả `"NaN"`, `"Infinity"`, `"1e3"`,
> `"10f"` — không cái nào là "digit". `NaN <= 0` còn ra `false` → lọt qua kiểm "lớn hơn 0"!

### 2.2 Bubble sort theo lương — chạy tay ví dụ của đề

Thứ tự nhập: `NghiaNV 2000`, `LienVT 500`, `TuanNT 1000`.

| Lượt | So sánh | Kết quả | Mảng sau bước |
|---|---|---|---|
| 1 | 2000 > 500 | đổi | `Lien 500 · Nghia 2000 · Tuan 1000` |
| 1 | 2000 > 1000 | đổi | `Lien 500 · Tuan 1000 · Nghia 2000` ← **2000 đã đúng chỗ** |
| 2 | 500 < 1000 | giữ | **không đổi lần nào → dừng** (`swapped == false`) |

Kết quả in: `LienVT` → `TuanNT` → `NghiaNV` — đúng màn hình đề.

| Chi tiết | Code | Vì sao |
|---|---|---|
| đổi chỗ **cả đối tượng** | `Person temp = persons[j]; ...` | đổi riêng lương là **lẫn** lương người này sang tên người kia |
| so bằng `>` | `persons[j].getSalary() > persons[j + 1].getSalary()` | hai người **bằng lương** giữ thứ tự nhập (ổn định) — kịch bản test 3 |
| vòng trong `size - 1 - i` + cờ `swapped` | như P0001 | bỏ phần cuối đã đúng chỗ; lượt không đổi → dừng |

Độ phức tạp: xấu nhất `O(n²)`, tốt nhất `O(n)` (mảng đã sắp — 1 lượt rồi dừng).

### 2.3 In lương `500.0` mà không bao giờ ra `1.0E7`

`Double.toString(500)` = `"500.0"` ✓ nhưng `Double.toString(10000000)` = `"1.0E7"` ✗. Nên `PersonView` in bằng
`DecimalFormat("0.0##########")` + `Locale.US`:

| Lương | `Double.toString` | Bài này |
|---|---|---|
| `500` | `500.0` | `500.0` |
| `1500.75` | `1500.75` | `1500.75` |
| `10000000` | `1.0E7` | `10000000.0` |

`Locale.US` giữ dấu chấm: máy lab tiếng Việt không in `500,0`.

### 2.4 Java dùng trong bài

| API | Dùng làm gì |
|---|---|
| `String.matches(regex)` | kiểm **cả chuỗi** có đúng dạng không |
| `Double.parseDouble(s)` | đổi chuỗi sang `double` (sau khi regex đã chắc là số) |
| `DecimalFormat` + `DecimalFormatSymbols.getInstance(Locale.US)` | in số thực không ra dạng khoa học, luôn dấu chấm |
| `System.out.println()` không tham số | in **dòng trống** ngăn giữa các khối |

---

## 3. Thiết kế

```
HE176322_J1SP0063_PersonInfo/src/
├── model/      Person               name, address, salary (JavaBean) — lớp đề đặt tên
├── dto/        PersonRequestDTO     1 người vừa gõ        (main ──► controller)
│               PersonResponseDTO    1 người để in         (controller ──► view)
│               PersonService        inputPersonInfo + sortBySalary + sortPersons (Context)
├── controller/ PersonController     service ──► view
├── view/       PersonView           display + displayPersonInfo
├── constants/  Message.java         câu chữ, lỗi của đề
│               Constants.java       NUMBER_OF_PERSONS, MIN_SALARY, SALARY_PATTERN, SALARY_FORMAT
├── utils/      Validation           getNonBlank, checkSalary (3 lỗi của đề)
└── main/       Main                 Scanner, nhập 3 người, gọi controller 1 lần
```

| Lớp | Làm gì | Vì sao ở đây |
|---|---|---|
| `Person` | mô tả 1 người | đề đặt tên lớp; Guide: model chỉ thuộc tính + get/set |
| `PersonService` | DTO → `Person` (`inputPersonInfo`), sắp (`sortBySalary`), `Person` → DTO | Guide: service *"được phép import Model"*; controller thì **không** |
| `PersonView` | in từng khối (`displayPersonInfo`) | Guide: *"Không được gọi print ngoài view và main"* |
| `Validation` | kiểm chuỗi lương/tên/địa chỉ, ném lỗi | Guide: utils static; Main bắt lỗi và **hỏi lại ngay** |

| Câu hỏi thiết kế | Trả lời |
|---|---|
| Sao không có `repository`? | Mảng 3 người chỉ sống trong **một lần chạy**, không thêm/sửa/xoá — không có CRUD. |
| Lương bị kiểm **2 lần** (`Validation.checkSalary` ở Main và `inputPersonInfo` ở service)? | Main kiểm để **hỏi lại ngay** như màn hình đề. Service kiểm lại `salary > 0` vì đề nói `inputPersonInfo` ném `Salary is greater than zero` — service không tin người gọi. |

**Luồng chạy:**

```
Main: 3 lần { in "Input Information of Person"; đọc tên, địa chỉ, lương (hỏi lại khi sai) → PersonRequestDTO }
      ──► controller.displaySortedPersons(requests)                  ← gọi controller ĐÚNG 1 lần
   controller ──► service.sortPersons(requests)
                     ├─ persons[i] = inputPersonInfo(requests[i])   (DTO → Person)
                     ├─ sortBySalary(persons)  ← bubble sort chạy ở đây
                     └─ persons → PersonResponseDTO[] theo thứ tự đã sắp
   controller ──► view.setPersons(sorted) ──► view.display() ──► displayPersonInfo × 3
Main: catch (Exception e) → in e.getMessage()
```

### 3.1 Design Pattern trong bài

| Pattern | Ở đâu |
|---|---|
| **MVC** — thầy gọi là "MVC JSP" | controller điều hướng (như Servlet) · view hiển thị (như trang JSP) · model là JavaBean |
| **Facade** | controller: `Main` chỉ gọi `controller.displaySortedPersons(requests)`, không biết service/model/view phía sau |

> Bài chỉ 25 LOC nên **không thêm lớp pattern GoF** — ghi chú slide SOLID của thầy cảnh báo *"trừu tượng hoá sớm … vi phạm YAGNI"*. Bubble sort theo lương nằm ngay trong hàm đề bắt `sortBySalary` của `PersonService`.

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
| **S** | `Person` giữ dữ liệu · `PersonService` kiểm lương + sắp · `PersonView` in · `Validation` kiểm |
| **O** | đổi thuật toán chỉ sửa thân `sortBySalary`; đổi câu chữ chỉ sửa `Message` |
| **L** | bài chưa có lớp con riêng — chỉ `extends Object` |
| **I** | không có interface — bài chưa cần |
| **D** | `Main` chỉ phụ thuộc controller + DTO |

---

## 4. Code từng bước

> Thầy: *"code cái **model trước rồi đến data**"*.

| Bước | File | Việc |
|---|---|---|
| 1 | `model/Person.java` | 3 field `private` (đúng tên đề) + constructor rỗng + constructor `(name, address, salary)` + get/set + `toString` |
| 2 | `dto/PersonRequestDTO.java`, `PersonResponseDTO.java` | JavaBean: constructor rỗng + get/set |
| 3 | `service/PersonService.java` | `inputPersonInfo` · `sortBySalary` · `sortPersons` |
| 4 | `view/PersonView.java` | `setPersons` · `display` · `displayPersonInfo` |
| 5 | `controller/PersonController.java` | `new PersonService()`; `displaySortedPersons(requests)` |
| 6 | `constants/Message.java`, `Constants.java` | câu chữ, lỗi của đề, regex, định dạng |
| 7 | `utils/Validation.java` | `getNonBlank` · `checkSalary` (**3 lỗi đúng thứ tự**) |
| 8 | `main/Main.java` | vòng 3 người · `inputPerson` · `inputName` · `inputAddress` · `inputSalary` · gọi controller **1 lần** |

**Bẫy hay gặp:**

1. Lương sai mà bắt nhập lại **cả tên, địa chỉ** — sai màn hình đề. Chỉ `inputSalary` có vòng hỏi lại.
2. Đổi chỗ **lương** thay vì đổi chỗ **cả người** → tên và lương bị trộn.
3. Quên dòng trống giữa các khối (`System.out.println()` cuối `displayPersonInfo`).
4. Gõ `You must input digidt.` theo màn hình — **Guidelines** ghi `digit.`

---

## 5. Test trước khi gọi thầy

| # | Gõ | Phải thấy |
|---|---|---|
| 1 | đúng màn hình đề (`abc`, `-2000`, `2000`, rồi 500, 1000) | `You must input digit.` · `Salary is greater than zero` · kết quả Lien 500.0 → Tuan 1000.0 → Nghia 2000.0, cách dòng trống |
| 2 | tên *(trống)* | `You must input name.` rồi hỏi lại tên |
| 3 | địa chỉ *(trống)* | `You must input address.` rồi hỏi lại địa chỉ |
| 4 | lương *(trống)* | `You must input Salary.` |
| 5 | lương `abc` · `1e3` · `NaN` | `You must input digit.` (mỗi lần) |
| 6 | lương `0` · `-5` | `Salary is greater than zero` |
| 7 | lương `1500.75` · `10000000` · `12.5` | in `Salary:1500.75` · `Salary:10000000.0` · `Salary:12.5` |
| 8 | 3 người lương `100`, `100`, `200` | 2 người bằng lương giữ **thứ tự nhập** (A trước B) |
| 9 | lương `3`, `2`, `1` (ngược hẳn) | in `1.0` → `2.0` → `3.0` |
| — | `Can't Sort Person` | **không gõ ra được** từ bàn phím — xem mục 7 |

---

## 6. Debug

| Việc | Cách làm |
|---|---|
| Breakpoint | dòng `if (person[j].getSalary() > person[j + 1].getSalary())` trong `PersonService.sortBySalary` |
| Chạy | **Ctrl+F5**, nhập đúng ví dụ của đề |
| Quan sát | tab **Variables**: `i`, `j`, `swapped`; mở `persons` → `[0]`, `[1]`, `[2]` → xem `name`, `salary` |
| Bước | **F8** qua các lần đổi chỗ trong `sortBySalary`; mở mảng `person` xem thứ tự đổi |
| Kiểm lương | breakpoint trong `Validation.checkSalary`, gõ `abc` → F8 thấy nhảy vào `throw ... SALARY_NOT_DIGIT`, rồi rơi vào `catch` của `Main.inputSalary` |

---

## 7. Câu hỏi thầy hay hỏi

### OOP

| Câu hỏi | Trả lời mẫu |
|---|---|
| 4 tính chất OOP ở đâu? | **Đóng gói**: 3 field `private` của `Person` + get/set. **Kế thừa**: mọi lớp ngầm `extends Object`; `Person` ghi đè `toString()`. **Đa hình**: `toString()` có `@Override`. **Trừu tượng**: `Main` gọi `controller.displaySortedPersons(...)` mà không biết có `Person` hay thuật toán nào. |
| Interface khác abstract class? | Interface chỉ là **hợp đồng** (không trạng thái, một lớp implement được nhiều interface); abstract class chứa được code chung + field. Các thuật toán sắp **không chung dòng code nào** → interface. |
| Sao `Person` có constructor rỗng? | **MVC JSP** của thầy: model là **JavaBean** — field `private`, constructor rỗng `public`, get/set. Constructor `(name, address, salary)` là do **đề** bắt. |

### Access modifier, static, kiểu trả về, tham số

| Câu hỏi | Trả lời mẫu |
|---|---|
| `sortPersons` sao `public`, còn `inputPersonInfo`/`sortBySalary` sao `private`? | `sortPersons` do `PersonController` (lớp khác, package khác) gọi → `public`. Hai hàm đề bắt chỉ được gọi **bên trong** `PersonService` → `private` (thầy V2: chỉ `public` khi lớp khác gọi). Đề chỉ bắt **tên** hàm, không bắt `public`. |
| `displayPersonInfo` sao `public`? | Hàm đề bắt; là "hợp đồng" của view (in 1 người). |
| Field `salaryFormat` sao `private`? | Chỉ lớp đó dùng; không ai bên ngoài được đổi thuật toán/định dạng giữa chừng. |
| Hàm nhập trong `Main` sao `private static`? | `private`: chỉ `main()` gọi. `static`: `main()` static nên chỉ gọi thẳng được hàm static; thầy cho static **hàm** ở main, cấm static **biến**. |
| `Validation.checkSalary` sao static? Bỏ thì sao? | Không dùng dữ liệu đối tượng nào; Guide bắt utils static. Bỏ `static` → phải bỏ `private` constructor, `new Validation()` trong `Main`, gọi qua đối tượng. |
| `checkSalary` trả `double` vì sao? | `Person.salary` là `double` (đề khai báo). |
| `sortBySalary` trả `Person[]` dù sắp tại chỗ? | Đề bắt chữ ký `Person[] sortBySalary(Person[] person)`; trả lại chính mảng đó cho tiện gọi nối. |
| `inputPersonInfo` trả `Person` vì sao? | Đề: *"Return value: Person object"* — nó **tạo** người từ dữ liệu nhập. |
| Sao `inputPersonInfo` nhận DTO, không nhận `(name, address, sSalary)` như đề? | Thầy: *"không được truyền 3 tham số 1 hàm"* → 3 giá trị gói vào `PersonRequestDTO`; **tên hàm giữ nguyên**. |
| Sao `displayPersonInfo` nhận `PersonResponseDTO`, không nhận `Person`? | Guide: **Model và View không giao tiếp** — view chỉ thấy DTO. |
| Sao dùng mảng mà không `ArrayList`? | Đề bắt *"array of 3 person"* và chữ ký `Person[]`; số người **cố định 3**. `ArrayList` là **lớp** cài đặt bằng mảng **co giãn**; `List` là **interface** (hợp đồng). Nếu thầy bắt nhập số người tuỳ ý thì em khai `ArrayList<Person> persons = new ArrayList<>()` — kiểu cụ thể. |

### Ca biên

| Câu hỏi | Trả lời mẫu |
|---|---|
| Khi nào ra `Can't Sort Person`? | Khi mảng `null` hoặc có phần tử `null` (`sortBySalary` kiểm trước khi sắp). Từ bàn phím **không gõ ra được**, vì `Main` luôn đưa đủ 3 người hợp lệ — đây là lưới an toàn cho người gọi khác, đúng lời đề *"If there a errors"*. |
| Hai người bằng lương thì ai trước? | Người nhập trước — bubble sort chỉ đổi khi `>`, nên **ổn định**. |
| Lương `0`? | `Salary is greater than zero` — đề nói *"greater than zero"*, 0 không lớn hơn 0. |
| Lương `1e3`? | `You must input digit.` — đề đòi "digit", regex không nhận chữ `e`. |

---

## 8. Thầy đổi yêu cầu tại chỗ

| Thầy bảo | Sửa file | Không phải đụng |
|---|---|---|
| Đổi sang **selection sort** | thân `sortBySalary` trong `PersonService` | `Main`, `PersonView`, `Person` |
| Sắp **giảm dần** | `>` → `<` trong `PersonService.sortBySalary` | mọi file khác |
| Nhập **n người** thay vì 3 | `Main` hỏi thêm số người (thêm `Validation.getInt`), tạo mảng `n` | `PersonService`, view — đều chạy theo `length` |
| Sắp theo **tên** | `sortBySalary` so `getName().compareTo(...) > 0` (nên đổi tên `sortByName`) | `Main`, view |
| Thêm trường **tuổi** | `Person`, 2 DTO, `Validation` (kiểm số), `Main` (hỏi), `PersonService` (chép), `PersonView` (in), `Message` | `PersonController` |

---

## 9. Chỗ khác với đề / bản cũ

| Chỗ | Đề / bản cũ | Bài này | Lý do |
|---|---|---|---|
| Thông báo lương sai | màn hình đề: `You must input digidt.` | `You must input digit.` | Guidelines ghi `Exception("You must input digit.")` — **Guidelines thắng** màn hình |
| Dòng trống giữa các khối | bản cũ: **không** có | **có** (như màn hình đề) | màn hình đề thắng → file test `REPLACE_REFERENCE = True`, viết lại kịch bản của bản cũ có dòng trống |
| `inputPersonInfo(name, address, sSalary)` | đề: 3 tham số | `inputPersonInfo(PersonRequestDTO)` | luật thầy V4: không truyền 3 tham số 1 hàm |
| `displayPersonInfo(Person)` | đề: nhận `Person` | nhận `PersonResponseDTO`, nằm ở `view` | Guide: view không thấy model |
| Kiểm "là số" | bản cũ: `Double.parseDouble` (nhận `NaN`, `1e3`) | regex `-?\d+(\.\d+)?` rồi mới parse | `NaN` lọt qua kiểm `> 0` |
| In lương | bản cũ: `Double.toString` (`1.0E7`) | `DecimalFormat` `0.0##########`, `Locale.US` | giữ dạng `500.0` của đề mà không ra dạng khoa học |
| Tên/địa chỉ trống | đề **không** nói | `You must input name.` · `You must input address.` | lấy đúng chữ bản cũ |
| Kiến trúc | cả 3 hàm static trong `ui.Main` | MVC theo Guide | luật thầy |
