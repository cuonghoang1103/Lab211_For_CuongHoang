# J1.S.P0068 — Collection Sort (Student)

> Bài "dùng collection để sắp xếp" — điểm ăn tiền là **Comparator**: đó chính là pattern
> **Strategy** mà thư viện Java viết sẵn. Hiểu được câu này là trả lời được câu Design Pattern.

| | |
|---|---|
| Loại / LOC | Short Assignment · 37 LOC · 1 slot |
| Project | `HE176322_J1SP0068_StudentCollectionSort` |
| Chạy | NetBeans: **File ▸ Open Project** → **F6** |
| Lớp chạy | `main.Main` |
| Kiểm tự động | `python3 _tools/verify.py J1SP0068` → 4 kịch bản × 2 locale |

---

## 1. Đề bài nói gì

- Nhập sinh viên (Name, Classes, Mark) — hỏi *nhập tiếp không (Y/N)* sau mỗi người.
- Mark **không phải số hợp lệ → nhập lại**.
- Dùng **collection** sắp theo **tên A → Z**, in từng sinh viên.

Màn hình đề:

```
====== Collection Sort Program ======
Please input student information
Name: Nghia
Classes: FU1
Mark: 100
Do you want to enter more student information?(Y/N):Y
Please input student information
Name: Lien
Classes: FU1
Mark: 100
Do you want to enter more student information?(Y/N):N
-------------Student 1-------------
Name: Lien
Classes: FU1
Mark: 100.0
-------------Student 2-------------
Name: Nghia
Classes: FU1
Mark: 100.0
```

**Đề bắt buộc:**

| Thứ | Đề viết | Bài này đặt ở |
|---|---|---|
| Lớp `Student`: `private String name; private float mark; private String classes;` + constructor rỗng + constructor đủ + get/set | Hint | `model/Student` |
| Lớp **Student Comparator** `implements Comparator`, ghi đè `compare` | Hint | `service/StudentComparator` |
| `List<Student> sortStudent(List<Student> students)` dùng `Collections.sort` | Function 1 | `service/StudentService.sortStudent` (**private**) |
| `void display(List<Student> students)` | Function 2 | `view/StudentView.display(List<StudentResponseDTO>)` |

---

## 2. Kiến thức cần biết

### 2.1 `Comparator` — ai đứng trước ai

```java
public int compare(Student first, Student second) {
    return first.getName().compareToIgnoreCase(second.getName());
}
```

| Kết quả `compare` | Nghĩa |
|---|---|
| số **âm** | `first` đứng **trước** |
| `0` | ngang nhau (giữ thứ tự cũ) |
| số **dương** | `first` đứng **sau** |

`Collections.sort(list, comparator)` **tự lo thuật toán** (merge sort, ổn định), chỉ hỏi comparator
"hai người này ai trước".

### 2.2 Chạy tay ví dụ của đề: `[Nghia, Lien]`

| Bước | So sánh | `compareToIgnoreCase` | Kết quả |
|---|---|---|---|
| 1 | `Nghia` vs `Lien` | `'n' - 'l'` = **2** (dương) | Nghia đứng **sau** Lien |
| — | danh sách sau sort | | `[Lien, Nghia]` → Student 1 = Lien |

### 2.3 Vì sao `compareToIgnoreCase`?

`compareTo` so mã Unicode: mọi chữ **HOA** (65–90) đứng trước mọi chữ thường (97–122) → `Binh`,
`Nghia`, rồi mới `an`. Người dùng hiểu "A → Z" là `an, Binh, Nghia` → bỏ qua hoa thường.

### 2.4 Java dùng trong bài

| API | Dùng làm gì |
|---|---|
| `Collections.sort(list, comparator)` | sắp **tại chỗ**, ổn định (2 người trùng tên giữ thứ tự nhập) |
| `Float.parseFloat(s)` | chuỗi → `float`; sai → `NumberFormatException` |
| `Float.isNaN(x)` | `"NaN"` parse **được** nhưng không phải số — phải chặn riêng |
| `"Mark: " + 100f` | ra `100.0` — `Float.toString`, **không phụ thuộc locale** (máy lab tiếng Việt vẫn ra dấu chấm) |

---

## 3. Thiết kế

```
HE176322_J1SP0068_StudentCollectionSort/src/
├── model/      Student               name, mark, classes (JavaBean, đúng Hint)
├── dto/        StudentRequestDTO     1 SV vừa gõ     (main ──► controller)
│               StudentResponseDTO    1 SV đã sắp     (controller ──► view)
├── service/    StudentComparator     «ConcreteStrategy» so theo tên A→Z
│               StudentService        «Context» DTO → Student → sortStudent → DTO
├── controller/ StudentController     cắm StudentComparator vào service; service ──► view
├── view/       StudentView           display(List) — in từng khối "Student i"
├── constants/  Message, Constants    câu chữ; MIN_MARK 0, MAX_MARK 100, Y, N
├── utils/      Validation            getNonBlank, getMark, getYesNo
└── main/       Main                  Scanner, vòng nhập, gọi controller 1 lần
```

| Câu hỏi | Trả lời |
|---|---|
| Sao không có `repository`? | Guide: repository = **giữ dữ liệu + CRUD**. Danh sách chỉ sống một lượt chạy, không thêm/sửa/xoá. Sắp xếp là **nghiệp vụ** → `service`. (Giống khung P0063 PersonInfo.) |
| Sao `Main` gom `ArrayList<StudentRequestDTO>` rồi mới gọi controller? | Guide: mỗi workflow **gọi controller 1 lần**. Nhập xong cả danh sách → gọi `displaySortedStudents(requests)` đúng 1 lần. |
| Sao controller không đụng `Student`? | Guide: controller *"chỉ import DTO, View, Service"*. Service đổi DTO ↔ model. |

**Luồng:**

```
Main: while(more) { inputStudent → requests.add; more = inputYesNo }
      controller.displaySortedStudents(requests)
         └─ service.getSortedStudents(requests)
               ├─ mỗi request → new Student(...)
               ├─ sortStudent(students)  → Collections.sort(students, studentComparator)
               └─ mỗi Student → StudentResponseDTO
         └─ view.display(sorted)
```

### 3.1 Design Pattern — **Strategy** (qua `Comparator`)

| Yếu tố | Trong bài |
|---|---|
| **Name** | Strategy (Behavioral) |
| **Problem** | Đề bắt sắp theo tên, nhưng thầy rất hay bảo *"sắp theo điểm xem"*. Viết cứng phép so tên vào vòng sắp thì mỗi lần đổi phải mở service sửa. |
| **Solution** | `java.util.Comparator<Student>` = **Strategy** · `StudentComparator` = **ConcreteStrategy** · `Collections.sort` (được `StudentService.sortStudent` gọi) = **Context** — nó biết *cách sắp*, chỉ hỏi strategy *ai trước*. `StudentService` nhận `Comparator<Student>` qua **constructor**; `StudentController` là nơi **chọn**: `new StudentService(new StudentComparator())`. |
| **Consequences** | ✅ Sắp theo tiêu chí khác = **thêm 1 class** `MarkComparator`, sửa **1 dòng** ở controller (**O**pen/Closed); service phụ thuộc interface (**D**IP). ❌ Thêm một lớp so với viết lambda/so thẳng. |

Hai pattern đi kèm: **MVC** (thầy bắt — Controller ~ Servlet, View ~ JSP, `Student` ~ JavaBean) và
**Facade** (`StudentController.displaySortedStudents` là một cửa cho `Main`).

**Thầy bảo "sắp theo điểm giảm dần":**
1. Tạo `service/MarkComparator implements Comparator<Student>`: `return Float.compare(second.getMark(), first.getMark());`
2. Controller: `new StudentService(new MarkComparator())`. Xong.

### 3.2 SOLID

| Nguyên lý | Ở đâu |
|---|---|
| **S** | `Student` giữ dữ liệu · `StudentComparator` chỉ so · `StudentService` điều phối · `StudentView` in · `Validation` kiểm |
| **O** | tiêu chí sắp mới không sửa `StudentService` |
| **L** | mọi `Comparator<Student>` thay được cho nhau trong `StudentService` |
| **I** | `Comparator` chỉ bắt viết **1** hàm `compare` |
| **D** | `StudentService` phụ thuộc `Comparator<Student>` (trừu tượng), nhận qua constructor |

---

## 4. Code từng bước

> Thầy: *"code cái **model trước rồi đến data**"*.

| Bước | File | Việc |
|---|---|---|
| 1 | `model/Student.java` | 3 field `private` (đúng Hint) + ctor rỗng + ctor đủ + get/set + `toString` |
| 2 | `dto/StudentRequestDTO.java`, `StudentResponseDTO.java` | JavaBean |
| 3 | `service/StudentComparator.java` | `@Override compare` → `compareToIgnoreCase` |
| 4 | `service/StudentService.java` | field `Comparator<Student>`; `getSortedStudents`; `sortStudent` (private) |
| 5 | `view/StudentView.java` | `display(List<StudentResponseDTO>)` |
| 6 | `controller/StudentController.java` | ctor cắm comparator; `displaySortedStudents` |
| 7 | `constants/Message.java`, `Constants.java` | câu chữ, giới hạn điểm |
| 8 | `utils/Validation.java` | `getNonBlank`, `getMark` (tách 3 lỗi), `getYesNo` |
| 9 | `main/Main.java` | `inputStudent`, `inputName`, `inputClasses`, `inputMark`, `inputYesNo` |

**Bẫy hay gặp:**

1. Quên `@Override` trên `compare` → gõ sai chữ ký là thành hàm mới, `Collections.sort` báo lỗi khó hiểu.
2. `compareTo` thay vì `compareToIgnoreCase` → `an` bị xếp **cuối**.
3. `"NaN"` lọt qua kiểm khoảng (mọi phép so với NaN đều `false`) → phải `Float.isNaN`.
4. In `Mark: 100` thay vì `100.0` — đề in `float` nên phải giữ kiểu `float`.

---

## 5. Test trước khi gọi thầy

| # | Gõ | Phải thấy |
|---|---|---|
| 1 | ví dụ đề: `Nghia/FU1/100/Y/Lien/FU1/100/N` | Student 1 = **Lien**, Student 2 = Nghia, `Mark: 100.0` |
| 2 | Name để trống | `Name must not be empty.` rồi hỏi lại |
| 3 | Classes để trống | `Class must not be empty.` |
| 4 | Mark `abc` / `NaN` | `You must input a number.` |
| 5 | Mark `-5` / `101` / `200` | `Mark must be between 0.0 and 100.0.` |
| 6 | Y/N gõ `maybe` | `Please answer Y or N.` |
| 7 | `y` và `n` thường | được chấp nhận |
| 8 | tên `Nghia`, `an`, `Binh` | thứ tự `an`, `Binh`, `Nghia` |
| 9 | 2 SV trùng tên `Minh` (FU2 rồi FU1) | giữ **thứ tự nhập** (sort ổn định) |
| 10 | Mark `7.5`, `0` | `Mark: 7.5`, `Mark: 0.0` |

---

## 6. Debug

| Việc | Cách làm |
|---|---|
| Breakpoint | dòng `return first.getName().compareToIgnoreCase(...)` trong `StudentComparator.compare` |
| Chạy | **Ctrl+F5**, nhập `Nghia`, `Lien` |
| Quan sát | tab **Variables**: `first`, `second` (mở ra xem `name`); **Call Stack** thấy `Collections.sort` gọi vào `compare` — đa hình qua interface |
| Bước | ở `StudentService.sortStudent` bấm **F7** vào `Collections.sort` (hoặc **F8** qua); sau đó mở `students` thấy thứ tự đã đổi |

---

## 7. Câu hỏi thầy hay hỏi

### OOP / access modifier / static / kiểu trả về

| Câu hỏi | Trả lời mẫu |
|---|---|
| 4 tính chất OOP ở đâu? | **Đóng gói**: 3 field `private` trong `Student` + get/set. **Kế thừa**: `StudentComparator implements Comparator<Student>`; mọi lớp `extends Object`. **Đa hình**: `Collections.sort` gọi `comparator.compare(...)` qua biến kiểu interface — chạy bản của `StudentComparator`; `toString()` có `@Override`. **Trừu tượng**: `StudentService` chỉ biết "một `Comparator`", không biết so theo gì. |
| Sao `sortStudent` `private`? | Chỉ `StudentService` gọi. Đề không ghi access modifier; thầy dặn `public` chỉ khi lớp khác gọi. |
| `sortStudent` sắp tại chỗ rồi còn `return` làm gì? | **Đúng chữ ký đề** (*"Return: list of students that was sorted"*); gọi `List<Student> sorted = sortStudent(students)` đọc rõ ý. |
| Sao `display` trả `void`? | Nó chỉ **in**, không tạo ra giá trị nào — đề cũng ghi `void`. |
| `compare` sao trả `int`? | Hợp đồng của `Comparator`: âm/0/dương. |
| Sao `mark` là `float`? | Đề khai `private float mark`; in ra `100.0` đúng màn hình. |
| Static ở đâu, bỏ thì sao? | Chỉ `Validation` (utils), hằng `constants`, hàm trong `Main`. Bỏ `static` ở `Validation.getMark` → `Validation.getMark(...)` lỗi biên dịch; phải bỏ `private` constructor, `new Validation()` trong `Main` rồi gọi qua đối tượng. |
| Constructor `StudentService` nhận `Comparator` — sao không `new StudentComparator()` bên trong? | Để **tiêm** strategy từ ngoài (DIP): đổi tiêu chí không phải mở service. |
| **Sao `ArrayList` mà không `List`? Khác nhau thế nào?** | `List` là **interface** (hợp đồng `add/get/size`); `ArrayList` là **lớp cài đặt** bằng **mảng động** — `get(i)` nhanh, chèn giữa chậm; `LinkedList` cài cùng hợp đồng bằng danh sách liên kết. Em khai báo **kiểu cụ thể** `ArrayList<...>` ở mọi chỗ (Main, service, controller). Chỉ **2 chỗ đề bắt chữ ký** giữ `List`: `sortStudent(List<Student>)` và `display(List<...>)` — có comment `// brief:` ngay trên. Truyền `ArrayList` vào tham số `List` được vì `ArrayList` **implements** `List` (đa hình). |
| `Comparable` khác `Comparator`? | `Comparable` nằm **trong** `Student` (`compareTo`) — một thứ tự tự nhiên duy nhất, đổi phải sửa `Student`. `Comparator` là lớp **riêng** — nhiều thứ tự, thay nhau được. Đề bắt `Comparator`. |

### Thuật toán

| Câu hỏi | Trả lời mẫu |
|---|---|
| `Collections.sort` dùng thuật toán gì? | Merge sort cải tiến (TimSort), **O(n log n)**, **ổn định**. |
| Ổn định nghĩa là gì? | Hai phần tử "bằng nhau" giữ nguyên thứ tự trước khi sắp (test #9). |

---

## 8. Thầy đổi yêu cầu tại chỗ

| Thầy bảo | Sửa file | Không đụng |
|---|---|---|
| Sắp theo **điểm** | thêm `service/MarkComparator` + 1 dòng ở `StudentController` | `StudentService`, `Main`, `View`, `Student` |
| Sắp **Z → A** | `StudentComparator`: đảo `second...compareToIgnoreCase(first...)` (hoặc thêm lớp mới) | mọi file khác |
| Thêm trường `address` | `Student`, 2 DTO, `Message`, `Main` (nhập), `StudentService` (copy), `StudentView` (in) | `StudentController`, `StudentComparator` |
| Điểm thang 10 | chỉ `Constants.MAX_MARK` (câu lỗi tự đổi theo) | mọi file khác |

---

## 9. Chỗ khác với đề / bản cũ

| Chỗ | Đề / bản cũ | Bài này | Lý do |
|---|---|---|---|
| `display(List<Student>)` | đề: tham số `List<Student>` | `StudentView.display(List<StudentResponseDTO>)` | Guide: view **không được** thấy model; giữ tên + `List` + `void` |
| `sortStudent` | đề không ghi lớp/modifier; bản cũ: `public static` trong `Main` | `private` trong `StudentService` | sắp xếp là nghiệp vụ → service; chỉ lớp này gọi → `private` |
| Tên lớp comparator | đề: "Student Comparator"; bản cũ: `StudentNameComparator` | `StudentComparator` | đúng chữ đề (bỏ dấu cách) |
| `List` vs `ArrayList` | bản cũ khai `List` khắp nơi | `ArrayList` trừ 2 chữ ký đề bắt (`// brief:`) | thầy dặn trên lớp (V5) |
| Kiến trúc | bản cũ: `entity/utils/ui`, Scanner trong `Validator` | MVC theo Guide | luật thầy |
| `NaN` | bản cũ nhận `NaN` làm điểm | báo `You must input a number.` | `NaN` lọt qua kiểm khoảng |
| Câu lỗi Name/Class/Mark/Y-N | đề không ghi | giữ đúng như bản cũ | đề im lặng → giữ màn hình đã kiểm |
| "enter student name, address, and mark" | đề (Function 1) nói **address** | nhập **Classes** | màn hình + Hint của đề đều là `classes`; "address" là lỗi chép đề |
