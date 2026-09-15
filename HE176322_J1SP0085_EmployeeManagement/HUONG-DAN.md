# J1.S.P0085 — Employee management system

> Bài CRUD đầy đủ (thêm/sửa/xoá/tìm) + **sắp xếp theo lương**. Hai pattern thầy thích nằm gọn trong bài:
> **Builder** (Employee có **10 thuộc tính**) và **Strategy** (sắp xếp bằng `Comparator`).

| | |
|---|---|
| Loại / LOC | Practical Assignment · 150 LOC · 3 slot |
| Project | `HE176322_J1SP0085_EmployeeManagement` |
| Chạy | NetBeans: **File ▸ Open Project** → **F6** |
| Lớp chạy | `main.Main` |
| Kiểm tự động | `python3 _tools/verify.py J1SP0085` → 4 kịch bản × 2 locale |

---

## 1. Đề bài nói gì

- Employee: `Id, First Name, Last Name, Phone, Email, Address, DOB, Sex, Salary, Agency`.
- Menu 6 mục: **Add · Update · Remove · Search · Sort by salary · Exit**.
- **Add**: nhập đủ 10 ô, **mọi ô bắt buộc**, **Id không trùng**; kiểm **ngay khi nhập** từng ô.
- **Update / Remove**: tìm theo Id; không có thì báo.
- **Search**: gõ một phần tên → khớp **first name hoặc last name**, **không phân biệt hoa thường** (`sm` → Smith).
- **Sort**: sắp theo lương tăng dần bằng **`Comparator`** rồi in.

**Đề bắt buộc** (bảng Guidelines + sơ đồ lớp):

| Thứ | Đề viết | Bài này đặt ở |
|---|---|---|
| Lớp `Employee` 10 thuộc tính + get/set + `toString()` | Design hints | `model/Employee.java` (+ `EmployeeBuilder`) |
| Phone chỉ chữ số · Email có `@` và tên miền · DOB ngày hợp lệ `yyyy-MM-dd` · Sex `Male`/`Female` · Salary > 0 | bảng field | `utils/Validation` (`getPhone`, `getEmail`, `getDob`, `getSex`, `getSalary`) |
| `addEmployee`, `updateEmployee(id)`, `removeEmployee(id)`, `searchByName(name)`, `sortBySalary()`, `display()` | sơ đồ `EmployeeManager` | `EmployeeController` (cùng tên) → `EmployeeRepository` / `EmployeeService`; `display()` ở `EmployeeView` |
| `List<Employee>` (một `ArrayList`) | Design hints | `ArrayList<Employee>` trong `EmployeeRepository` |
| Sort bằng `Comparator` | Design hints | `SalaryComparator implements Comparator<Employee>` |

---

## 2. Kiến thức cần biết

### 2.1 `Comparator` — so sánh 2 nhân viên

```java
public int compare(Employee first, Employee second) {
    return Double.compare(first.getSalary(), second.getSalary());   // âm / 0 / dương
}
Collections.sort(sorted, comparator);   // Collections.sort biết CÁCH sắp, comparator quyết định AI ĐỨNG TRƯỚC
```

Chạy tay ví dụ của đề (John 1500, Bob 1800, Anna 1200):

| Bước | So | `compare` | Kết quả |
|---|---|---|---|
| 1 | John(1500) vs Bob(1800) | âm | John trước Bob |
| 2 | Bob(1800) vs Anna(1200) | dương | Anna trước Bob |
| 3 | John(1500) vs Anna(1200) | dương | Anna trước John |
| → | | | **Anna 1200 · John 1500 · Bob 1800** |

`Collections.sort` là **stable**: hai người **cùng lương** giữ nguyên thứ tự nhập (test #13).
Không dùng `a - b` với `double` (ép về `int` mất phần lẻ: 1500.4 vs 1500.1 thành 0) → dùng `Double.compare`.

### 2.2 Tìm "chứa chuỗi" không phân biệt hoa thường

```java
employee.getFirstName().toLowerCase().contains(keyword.toLowerCase())
    || employee.getLastName().toLowerCase().contains(keyword.toLowerCase())
```

`jo` khớp **Jo**hn (first name) và **Jo**hnson (last name).

### 2.3 Ngày `yyyy-MM-dd` — `setLenient(false)` + so ngược

| Gõ | Kết quả | Vì sao |
|---|---|---|
| `1994-05-20` | ✅ | |
| `1994-13-40`, `1994-02-30` | ❌ | `setLenient(false)` — mặc định `1994-02-30` âm thầm thành `1994-03-02` |
| `1994-5-20`, `1994-05-20xyz` | ❌ | `format(parse(s))` ≠ `s` |

### 2.4 Ô "giữ giá trị cũ" khi Update

Mọi hàm kiểm ô nhận thêm tham số **`current`**: `null` khi **Add** (ô trống → `This field is required.`),
**giá trị cũ** khi **Update** (ô trống → giữ cũ). Một hàm dùng cho **cả hai** màn hình.

### 2.5 Java dùng trong bài

| API | Dùng làm gì |
|---|---|
| `Double.parseDouble` | lương; chú ý nó **nhận** `"NaN"`, `"Infinity"` → bài chặn thêm bằng `Double.isNaN/isInfinite` |
| `String.format(Locale.US, "%.2f", x)` | lương `1500.00` đúng màn hình đề; máy tiếng Việt không in `1500,00` (`FormatUtils.formatSalary`) |
| `String.matches(regex)` | phone `\d+`, email `^...@...\.[A-Za-z]{2,}$` |
| `equalsIgnoreCase` | Id (`e001` = `E001`), Sex (`female` → `Female`) |

---

## 3. Thiết kế

```
HE176322_J1SP0085_EmployeeManagement/src/
├── model/      Employee (JavaBean 10 thuộc tính), EmployeeBuilder (Builder)
├── dto/        EmployeeRequestDTO  (giá trị đã kiểm + keyword)   main ──► controller
│               EmployeeResponseDTO (1 nhân viên, ngày đã là chữ) controller ──► view / main
├── repository/ EmployeeRepository  ArrayList<Employee>: add/find/update/remove/searchByName
├── service/    EmployeeService     sortBySalary (Context của Strategy)
│               SalaryComparator    implements Comparator<Employee> (ConcreteStrategy)
├── controller/ EmployeeController  điều hướng repository / service ↔ view; cắm strategy
├── view/       EmployeeView        display() (bảng sort), displaySearch(), showMessage
├── utils/      Validation (kiểm), FormatUtils (lương, ngày ra chữ)
├── constants/  Message, Constants
└── main/       Main                menu + Scanner + mỗi ô một vòng hỏi lại
```

| Lớp | Làm gì | Vì sao ở đây |
|---|---|---|
| `EmployeeRepository` | giữ danh sách + CRUD + tìm | Guide: *"Chứa data … CRUD"* |
| `EmployeeService` | sắp xếp | Guide: service = *"tính toán nghiệp vụ"* ngoài CRUD; luồng **Controller ↔ Service ↔ Repository** (service lấy bản sao list từ repository) |
| `SalaryComparator` | luật "ai lương thấp đứng trước" | tách thuật toán khỏi service (Strategy) |
| `EmployeeBuilder` | lắp `Employee` 10 thuộc tính | chỉ biết `Employee` → package model |

**Luồng Update** (khó nhất):

```
Main: in tiêu đề ─► controller.checkNotEmpty()        (kiểm trước: rỗng thì báo, không hỏi Id)
      đọc Id ─► controller.findEmployee(dto)          (kiểm trước: trả giá trị cũ để in trong [ ])
      in "Press Enter to keep the value in brackets."
      9 ô, mỗi ô: in "Nhãn : [cũ] " ─► Validation.getX(dòng gõ, cũ)  (trống → giữ cũ; sai → báo, hỏi lại)
      ─► controller.updateEmployee(dto)                ← lời gọi chính, 1 lần
            repository.updateEmployee(dto) → set 9 field → toResponse
            view: "=> Employee E002 updated successfully." + dòng Employee{...}
```

### 3.1 Design Pattern

**Builder** (Creational)

| Yếu tố | Trong bài này |
|---|---|
| **Name** | Builder |
| **Problem** | `Employee` có **10 thuộc tính**, 7 cái là `String` liền nhau. Constructor 10 tham số gọi đảo firstName ↔ lastName hay phone ↔ email vẫn biên dịch — sai âm thầm. |
| **Solution** | `EmployeeBuilder` = **Builder** (`withId` … `withAgency`, `build`). `Employee` = **Product**. `EmployeeRepository.addEmployee` = **Director**. |
| **Consequences** | ✅ Mỗi giá trị **có tên**; thêm thuộc tính = thêm 1 hàm `withX`. ❌ Thêm 1 lớp; không `static` (model cấm) nên là lớp riêng. |

**Strategy** (Behavioral)

| Yếu tố | Trong bài này |
|---|---|
| **Name** | Strategy |
| **Problem** | Thầy rất dễ bảo *"sắp theo tên / theo tuổi / giảm dần"*. Viết cứng luật so sánh trong service thì lần nào cũng mở service ra sửa. |
| **Solution** | `Comparator<Employee>` (interface có sẵn của Java) = **Strategy**. `SalaryComparator` = **ConcreteStrategy**. `EmployeeService` = **Context**, nhận comparator qua **constructor**, gọi `Collections.sort(sorted, comparator)` mà không biết luật gì. `EmployeeController` là nơi **chọn**: `new EmployeeService(employeeRepository, new SalaryComparator())`. |
| **Consequences** | ✅ Luật mới = **1 class mới + 1 dòng** trong controller (**O**CP, **D**IP). ❌ Thêm 1 file cho một phép so sánh. |

**Facade**: `EmployeeController` — `Main` chỉ thấy controller. **Repository**: `EmployeeRepository`.
**MVC (JSP)**: `Employee`, 2 DTO là JavaBean.

### 3.2 SOLID

| | Ở đâu |
|---|---|
| **S** | `Employee` giữ dữ liệu · `EmployeeBuilder` tạo · `EmployeeRepository` lưu/tìm · `EmployeeService` sắp · `SalaryComparator` so · `Validation` kiểm · `FormatUtils` định dạng · `EmployeeView` in |
| **O** | sắp theo tên: thêm `NameComparator`, không sửa `EmployeeService` |
| **L** | mọi `Comparator<Employee>` thay nhau được trong `EmployeeService` |
| **I** | `Comparator` chỉ bắt viết đúng 1 hàm `compare` |
| **D** | `EmployeeService` phụ thuộc `Comparator<Employee>` (trừu tượng), nhận qua constructor |

---

## 4. Code từng bước

> Thầy: *"code cái **model trước rồi đến data**"*.

| Bước | File | Việc |
|---|---|---|
| 1 | `model/Employee.java` | 10 field private + constructor rỗng + get/set (**Alt+Insert**) + `toString` |
| 2 | `model/EmployeeBuilder.java` | field `employee`; 10 hàm `withX`; `build()` |
| 3 | `dto/EmployeeRequestDTO.java` | 10 field + `keyword` + get/set |
| 4 | `dto/EmployeeResponseDTO.java` | 10 field (dob là `String`) + get/set + `getFullName` · `getSalaryText` · `toString` |
| 5 | `repository/EmployeeRepository.java` | `isEmpty` · `isExistEmployee` · `addEmployee` · `findEmployee` · `updateEmployee` · `removeEmployee` · `searchByName` · `getEmployees` · `toResponse` · `findById` (private) |
| 6 | `service/SalaryComparator.java`, `EmployeeService.java` | `compare`; `sortBySalary` |
| 7 | `constants/Message.java`, `Constants.java` | menu, nhãn đệm 11 ký tự, lỗi; regex, định dạng |
| 8 | `utils/Validation.java`, `FormatUtils.java` | các hàm kiểm (input, current); `formatSalary`, `formatDate` |
| 9 | `view/EmployeeView.java` | `setEmployees` · `display` · `displaySearch` · `showMessage` |
| 10 | `controller/EmployeeController.java` | `checkNotEmpty` · `checkNewId` · `addEmployee` · `findEmployee` · `updateEmployee` · `removeEmployee` · `searchByName` · `sortBySalary` |
| 11 | `main/Main.java` | menu, 5 workflow, `inputEmployee`, `prompt`, `inputId`, `inputNewId`, `inputKeyword`, 9 hàm `inputX` |

**Bẫy hay gặp:**

1. Sort **thẳng danh sách gốc** → mất thứ tự nhập. Bài sort **bản sao** (`getEmployees()` trả `new ArrayList<>(employees)`).
2. Kiểm trùng Id **sau** khi hỏi đủ 10 ô → người dùng gõ phí 9 ô. Bài kiểm **ngay** sau ô Id (`checkNewId`).
3. `%.2f` không `Locale.US` → `1500,00` trên máy lab.
4. `a.getSalary() - b.getSalary()` ép `int` → so sai khi chênh dưới 1.

---

## 5. Test trước khi gọi thầy

| # | Chọn | Gõ | Phải thấy |
|---|---|---|---|
| 1 | 2 / 3 / 4 / 5 | (danh sách rỗng) | `=> The employee list is empty.` |
| 2 | 1 | `E001/John/Smith/0901234567/john.smith@example.com/12 Le Loi, Da Nang/1994-05-20/Male/1500/Sales` | `=> Employee E001 added successfully.` |
| 3 | 1 | Id trống / First name trống / Agency trống | `This field is required.` rồi hỏi lại ô đó |
| 4 | 1 | Id `E001` (trùng) | `=> Employee id E001 already exists.` rồi hỏi lại Id |
| 5 | 1 | Phone `09xx` | `Phone must contain digits only.` |
| 6 | 1 | Email `bob` | `Email must look like name@domain.com.` |
| 7 | 1 | DOB `1994-13-40`, `1994-02-30`, `1994-05-20xyz` | `DOB must be a real date in yyyy-MM-dd format.` |
| 8 | 1 | Sex `boy` / `female` | `Sex must be Male or Female.` / lưu `Female` |
| 9 | 1 | Salary `abc`, `Infinity` / `-5`, `0` | `Salary must be a number.` / `Salary must be greater than 0.` |
| 10 | 4 | trống / `sm` / `JO` / `zz` | `Please type something to search for.` / bảng có Smith / Johnson+John / `=> No employee matches "zz".` |
| 11 | 2 | `E009` | `=> No employee found with id E009.` |
| 12 | 2 | `e002`, đổi 1 ô, Enter các ô khác; Phone `09ab` | báo lỗi phone rồi hỏi lại; Enter giữ `[cũ]`; `=> Employee E002 updated successfully.` + dòng `Employee{...}` |
| 13 | 5 | | bảng tăng dần theo lương, `1500.00`; cùng lương giữ thứ tự nhập |
| 14 | 3 | `e001` / Id không có | `=> Employee e001 removed successfully.` / `=> No employee found with id E003.` |
| 15 | menu | `abc` / `9` | `You must input a number.` / `Please choose from 1 to 6.` |

---

## 6. Debug

| Việc | Cách làm |
|---|---|
| Breakpoint | dòng `return Double.compare(...)` trong `SalaryComparator.compare` |
| Chạy | **Ctrl+F5**, thêm 3 nhân viên, chọn 5 |
| Bước | mỗi lần dừng, **Variables** hiện `first`, `second` → thấy `Collections.sort` gọi comparator nhiều lần; **Ctrl+F7** ra ngoài `EmployeeService.sortBySalary`, mở `sorted` xem thứ tự mới, mở `employeeRepository.employees` thấy **thứ tự gốc không đổi** |
| Xem Builder | breakpoint ở `.build()` trong `EmployeeRepository.addEmployee`, mở `employee` thấy đủ 10 thuộc tính |
| Xem Update | breakpoint trong `Validation.getField`: gõ Enter → nhánh `current != null` trả giá trị cũ |

---

## 7. Câu hỏi thầy hay hỏi

### OOP

| Câu hỏi | Trả lời mẫu |
|---|---|
| 4 tính chất OOP ở đâu? | **Đóng gói**: 10 field `private` trong `Employee`; Id không đổi khi Update (repository chỉ set 9 field còn lại). **Kế thừa**: `SalaryComparator implements Comparator<Employee>`; mọi lớp `extends Object`. **Đa hình**: `Collections.sort` gọi `compare` qua biến kiểu `Comparator` — chạy bản của `SalaryComparator`; `toString()` `@Override` trong `Employee`, `EmployeeResponseDTO`. **Trừu tượng**: `EmployeeService` chỉ biết `Comparator`, không biết luật so sánh. |

### Access modifier / static / kiểu trả về

| Câu hỏi | Trả lời mẫu |
|---|---|
| `findById` sao `private`? | Chỉ repository dùng. |
| `toResponse` sao `public`? | `EmployeeService` cần đổi model → DTO sau khi sắp; viết **một chỗ**, hai nơi dùng. |
| `prompt`, `inputX` trong Main sao `private static`? | `private`: chỉ `Main` dùng. `static`: `main` là static nên chỉ gọi thẳng được hàm static; **biến** thì không static (Guide cấm) — `Scanner` là biến cục bộ truyền vào. |
| **Bỏ `static` ở `Validation.getSalary`?** | `Validation.getSalary(...)` lỗi biên dịch; phải bỏ `private` constructor, `new Validation()` trong `Main` rồi gọi qua đối tượng. |
| `removeEmployee` (repository) trả `boolean`? | Có xoá được hay không → controller quyết định báo lỗi. |
| `findEmployee` (controller) trả `EmployeeResponseDTO`? | Main cần **giá trị cũ** để in trong `[ ]`; DTO chứ không phải `Employee` vì main không được chạm model. |
| `getDob` trả `Date`, DTO phản hồi lại giữ `String`? | Model giữ ngày **thật** (so/sắp được); view chỉ cần **chữ** để in. |
| `getSalary` trả `double`? | Lương có phần lẻ (`2500.5`). |
| **Sao `ArrayList` mà không `List`?** | Đề gợi ý *"List<Employee> (an ArrayList)"*. `List` là **interface** (hợp đồng), `ArrayList` là **lớp cài đặt** bằng mảng động (lấy theo chỉ số nhanh; xoá giữa phải dời phần tử). Em khai báo đúng lớp em dùng. |
| Sao `Comparator<Employee>` (interface) mà không `SalaryComparator`? | Đây là **chỗ cố ý** phụ thuộc trừu tượng: service nhận **mọi** comparator (DIP). Khác `ArrayList`: ở đó em không cần thay lớp cài đặt. |

### Nghiệp vụ / kiến trúc

| Câu hỏi | Trả lời mẫu |
|---|---|
| Update gọi controller 3 lần? | `checkNotEmpty` và `findEmployee` là **kiểm trước** (đề: rỗng/không thấy Id thì báo ngay; cần giá trị cũ cho `[ ]`). Việc cập nhật chỉ gọi `updateEmployee` **1 lần**. Add tương tự: `checkNewId` kiểm trước, `addEmployee` 1 lần. |
| Sao sort ở service mà search ở repository? | Search là **tìm dữ liệu đã lưu** (như P0055). Sort là **thuật toán** có thể thay → service + Strategy. |
| Độ phức tạp? | Tìm theo Id `O(n)`; search `O(n)`; sort `O(n log n)` (TimSort, stable). |
| Pattern gì? | **Builder**, **Strategy**, **Facade**, **Repository**, **MVC** — mục 3.1. |

---

## 8. Thầy đổi yêu cầu tại chỗ

| Thầy bảo | Sửa | Không đụng |
|---|---|---|
| Sắp **giảm dần** | thêm `SalaryDescComparator` (`Double.compare(second..., first...)`) + 1 dòng trong `EmployeeController` | `EmployeeService`, `Main`, view |
| Sắp theo **tên** | thêm `NameComparator` + 1 dòng controller (+ chữ `TITLE_SORT`) | `EmployeeService` |
| Phone 10 số | `Constants.PHONE_REGEX = "\\d{10}"` + chữ `PHONE_INVALID` | `Validation` |
| DOB `dd/MM/yyyy` | `Constants.DOB_FORMAT` + chữ `DOB_INVALID` | mọi file khác |
| Thêm field `position` | `Employee`, `EmployeeBuilder.withPosition`, 2 DTO, `Message` (nhãn), `Validation` nếu có luật, `EmployeeRepository` (add/update/toResponse), `Main.inputEmployee` + `inputPosition` | `EmployeeService`, `SalaryComparator` |
| Menu "Hiện tất cả" | `Message.MENU`, `Constants`, `EmployeeController.displayAll` (dùng `repository.getEmployees` + `toResponse`), `case` trong Main | model |

---

## 9. Chỗ khác với đề / bản cũ

| Chỗ | Đề / bản cũ | Bài này | Lý do |
|---|---|---|---|
| `EmployeeManager` | 1 lớp giữ list + mọi hàm | `EmployeeController` (tên hàm giữ nguyên) + `EmployeeRepository` (CRUD, search) + `EmployeeService` (sort) | Guide: Controller ↔ Service ↔ Repository |
| `List<Employee>` | kiểu interface | `ArrayList<Employee>` | thầy: khai báo kiểu cụ thể (V5) |
| Sort | `list.sort(Comparator.comparingDouble(...))` | `Collections.sort(bản sao, new SalaryComparator())` | Strategy thấy rõ từng vai; không đổi thứ tự gốc |
| DOB | bản cũ `LocalDate` | `java.util.Date` + `SimpleDateFormat` strict | sơ đồ đề ghi `dob : Date` |
| Salary | bản cũ nhận `Infinity` | từ chối (`Salary must be a number.`) | không phải số lương |
| Màn Update / Remove / thông báo lỗi | đề không vẽ | theo bản tham chiếu: `[giá trị cũ]`, `=> ...` | đề im lặng → giữ đúng bản đã chấm |
| Kiến trúc | `bo/ui`, Scanner trong `Validator` | MVC theo Guide, Scanner **chỉ ở main** | luật thầy |
