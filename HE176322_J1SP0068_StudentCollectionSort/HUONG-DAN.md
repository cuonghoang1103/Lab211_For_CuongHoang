# J1.S.P0068 — Collection Sort (Student)

> Bài "dùng collection để sắp xếp" — điểm ăn tiền là **Comparator**: đó chính là pattern
> **Strategy** mà thư viện Java viết sẵn. Hiểu được câu này là trả lời được câu Design Pattern.
>
> **Bản 21/09/2026 — sửa theo tờ checklist giấy 25 mục của thầy** (mục 10): thêm
> `repository/StudentRepository` giữ danh sách `Student` (tờ giấy 1.1 *"Bắt buộc phải có repository"*);
> controller nhận **một** `StudentRequestDTO` (chứa `ArrayList<StudentDTO> studentList`) thay vì
> `ArrayList`; View nhận `responseDTO` qua thuộc tính, `display()` **không tham số**; `Main` thành `final`
> + constructor `private`. Đối chiếu lại đề từng ký tự: dòng `Please input student information ` của
> đề có **1 dấu cách ở cuối** → chương trình in y hệt (mục 9).

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

Màn hình đề (dòng 2 và dòng 7 có **1 dấu cách ở cuối**: `Please input student information␣` — file .docx
của đề giữ nguyên dấu cách đó, chương trình in y hệt):

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
| `List<Student> sortStudent(List<Student> students)` dùng `Collections.sort` | Function 1 | `service/StudentService.sortStudent(List<Student> studentList)` (**private**) — giữ tên, kiểu trả về, kiểu tham số; chỉ đổi **tên tham số** (tờ checklist 1.5) |
| `void display(List<Student> students)` | Function 2 | `view/StudentView.display()` — giữ tên + `void`; danh sách vào qua **thuộc tính** `responseDTO` (tờ checklist 1.1 cấm View nhận tham số) — comment `// brief:` ngay trên, **hỏi thầy** (mục 9) |

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
| `String.format("Mark: %s", 100f)` | ra `100.0` — `%s` gọi `Float.toString`, **không phụ thuộc locale** (máy lab tiếng Việt vẫn ra dấu chấm; `%f`/`%.1f` thì lại theo locale) |

---

## 3. Thiết kế

```
HE176322_J1SP0068_StudentCollectionSort/src/
├── model/      Student               name, mark, classes (JavaBean, đúng Hint)
├── repository/ StudentRepository     GIỮ ArrayList<Student> studentList: addStudent (Create) · getStudentList (Read)
├── dto/        StudentDTO            1 SV (name, classes, mark) — "một dòng" đi trong 2 DTO dưới
│               StudentRequestDTO     ArrayList<StudentDTO> studentList vừa gõ   (main ──► controller)
│               StudentResponseDTO    ArrayList<StudentDTO> studentList đã sắp   (controller ──► view)
├── service/    StudentComparator     «ConcreteStrategy» so theo tên A→Z
│               StudentService        «Context» request → repository → sortStudent → StudentDTO
├── controller/ StudentController     cắm StudentComparator vào service; service ──► view (1 lần)
├── view/       StudentView           field responseDTO; display() không tham số — in từng khối "Student i"
├── constants/  Message, Constants    câu chữ; MIN_MARK 0, MAX_MARK 100, Y, N, STUDENT_FORMAT
├── utils/      Validation            getNonBlank, getMark, getYesNo
└── main/       Main (final, ctor private)  Scanner, vòng nhập + validate, gọi controller 1 lần
```

| Câu hỏi | Trả lời |
|---|---|
| Sao bài có `repository`? | Tờ checklist 1.1: *"**Bắt buộc phải có repository**"*. Repository = **dữ liệu** + CRUD đơn giản: danh sách SV đã nhập (`ArrayList<Student> studentList`), với `addStudent` (thêm) và `getStudentList` (đọc). Sắp xếp là **nghiệp vụ** → nằm ở `StudentService`, làm trên danh sách lấy **từ repository** — đúng tầng *Controller ↔ Services ↔ Repository ↔ Model*. |
| Sao `Main` gom cả danh sách vào **một** `StudentRequestDTO` rồi mới gọi controller? | Guide: mỗi workflow **gọi controller 1 lần**; tờ checklist 1.1: *"Controller nhận input từ main qua DTO"*. Nhập xong cả danh sách → `requestDTO.setStudentList(studentList)` → `displaySortedStudents(requestDTO)` đúng 1 lần. |
| Sao có thêm `StudentDTO`? | Request và response đều là **danh sách SV**; `StudentDTO` là **một dòng** (name, classes, mark) nằm trong cả hai. View chỉ thấy `StudentDTO`, không bao giờ thấy model `Student`. |
| Sao controller không đụng `Student`? | Guide: controller *"chỉ import DTO, View, Service"*. `StudentRepository.addStudent` đổi `StudentDTO` → `Student`; service đổi `Student` đã sắp → `StudentDTO`. |

**Luồng:**

```
Main: while (more) { studentList.add(inputStudent(sc)); more = inputYesNo(sc); }   (nhập + validate ở Main)
      requestDTO.setStudentList(studentList)
      controller.displaySortedStudents(requestDTO)                                  (gọi controller 1 lần)
         └─ service.getSortedStudents(requestDTO)
               ├─ mỗi StudentDTO → repository.addStudent(...)  → new Student(...) cất vào studentList
               ├─ sortedList = sortStudent(repository.getStudentList())
               │                  └─ Collections.sort(studentList, studentComparator)
               └─ mỗi Student → new StudentDTO(...) → responseDTO.setStudentList(rowList)
         └─ view.setResponseDTO(responseDTO) ──► view.display()                      (render 1 lần)
```

Tầng: **Main → RequestDTO → Controller → Service → Repository → Model**; kết quả **ResponseDTO → View**, render 1 lần.

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
| **S** | `Student` mô tả SV · `StudentRepository` giữ danh sách · `StudentComparator` chỉ so · `StudentService` điều phối + sắp · `StudentView` in · `Validation` kiểm |
| **O** | tiêu chí sắp mới không sửa `StudentService` |
| **L** | mọi `Comparator<Student>` thay được cho nhau trong `StudentService` |
| **I** | `Comparator` chỉ bắt viết **1** hàm `compare` |
| **D** | `StudentService` phụ thuộc `Comparator<Student>` (trừu tượng), nhận qua constructor |

---

## 4. Code từng bước

> Thầy: *"code cái **model trước rồi đến data**"*.

| Bước | File | Việc |
|---|---|---|
| 1 | `model/Student.java` | 3 field `private` (đúng Hint) + ctor rỗng + ctor đủ + get/set + `toString` (`String.format`) |
| 2 | `dto/StudentDTO.java`, `StudentRequestDTO.java`, `StudentResponseDTO.java` | JavaBean: 1 dòng SV; 2 DTO mỗi cái một `ArrayList<StudentDTO> studentList` |
| 3 | `repository/StudentRepository.java` | field `ArrayList<Student> studentList`; `addStudent(studentDTO)` · `getStudentList()` — không sắp, không in |
| 4 | `service/StudentComparator.java` | `@Override compare` → `compareToIgnoreCase` |
| 5 | `service/StudentService.java` | field `studentRepository`, `Comparator<Student>`; `getSortedStudents(requestDTO)`; `sortStudent` (private) |
| 6 | `view/StudentView.java` | field `responseDTO`; `setResponseDTO` · `display()` không tham số |
| 7 | `controller/StudentController.java` | ctor cắm comparator; `displaySortedStudents(requestDTO)` |
| 8 | `constants/Message.java`, `Constants.java` | câu chữ (giữ dấu cách cuối của `INPUT_INFO`), giới hạn điểm |
| 9 | `utils/Validation.java` | `getNonBlank`, `getMark` (tách 3 lỗi), `getYesNo` |
| 10 | `main/Main.java` | `public final class Main` + `private Main() { }`; `inputStudent`, `inputName`, `inputClasses`, `inputMark`, `inputYesNo` |

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
| Bước | ở `StudentService.sortStudent` bấm **F7** vào `Collections.sort` (hoặc **F8** qua); sau đó mở `studentList` thấy thứ tự đã đổi |
| Xem repository | breakpoint ở `sortedList = sortStudent(studentRepository.getStudentList());` → mở `studentRepository` ▸ `studentList`: các `Student` theo **thứ tự nhập**; F8 một bước → cùng danh sách đó đã sắp A→Z |

---

## 7. Câu hỏi thầy hay hỏi

### OOP / access modifier / static / kiểu trả về

| Câu hỏi | Trả lời mẫu |
|---|---|
| 4 tính chất OOP ở đâu? | **Đóng gói**: 3 field `private` trong `Student` + get/set. **Kế thừa**: `StudentComparator implements Comparator<Student>`; mọi lớp `extends Object`. **Đa hình**: `Collections.sort` gọi `comparator.compare(...)` qua biến kiểu interface — chạy bản của `StudentComparator`; `toString()` có `@Override`. **Trừu tượng**: `StudentService` chỉ biết "một `Comparator`", không biết so theo gì. |
| Sao `sortStudent` `private`? | Chỉ `StudentService` gọi. Đề không ghi access modifier; thầy dặn `public` chỉ khi lớp khác gọi. |
| `sortStudent` sắp tại chỗ rồi còn `return` làm gì? | **Đúng chữ ký đề** (*"Return: list of students that was sorted"*); `sortedList = sortStudent(studentRepository.getStudentList())` đọc rõ ý. |
| Sao `display` trả `void`? | Nó chỉ **in**, không tạo ra giá trị nào — đề cũng ghi `void`. |
| `compare` sao trả `int`? | Hợp đồng của `Comparator`: âm/0/dương. |
| Sao `mark` là `float`? | Đề khai `private float mark`; in ra `100.0` đúng màn hình. |
| Static ở đâu, bỏ thì sao? | Chỉ `Validation` (utils), hằng `constants`, hàm trong `Main`. Bỏ `static` ở `Validation.getMark` → `Validation.getMark(...)` lỗi biên dịch; phải bỏ `private` constructor, `new Validation()` trong `Main` rồi gọi qua đối tượng. |
| Constructor `StudentService` nhận `Comparator` — sao không `new StudentComparator()` bên trong? | Để **tiêm** strategy từ ngoài (DIP): đổi tiêu chí không phải mở service. |
| **Sao `ArrayList` mà không `List`? Khác nhau thế nào?** | `List` là **interface** (hợp đồng `add/get/size`); `ArrayList` là **lớp cài đặt** bằng **mảng động** — `get(i)` nhanh, chèn giữa chậm; `LinkedList` cài cùng hợp đồng bằng danh sách liên kết. Em khai báo **kiểu cụ thể** `ArrayList<...>` ở mọi chỗ (Main, DTO, repository, service, view). Chỉ **chữ ký đề bắt** giữ `List`: `sortStudent(List<Student>)` và biến `sortedList` nhận kết quả của nó — có comment `// brief:` ngay trên. Truyền `ArrayList` vào tham số `List` được vì `ArrayList` **implements** `List` (đa hình). |
| Sao tham số là `studentList` chứ không `students` như đề? | Tờ checklist 1.5: *"tên biến kiểu collection kết thúc bằng List"*. Tên tham số **không thuộc chữ ký** hàm trong Java (chữ ký = tên hàm + kiểu tham số), nên `sortStudent(List<Student> studentList)` vẫn đúng chữ ký đề. |
| Sao bài có repository? | Tờ checklist 1.1 *"Bắt buộc phải có repository"*: `StudentRepository` giữ danh sách SV + `addStudent`/`getStudentList`. Không sắp, không in — sắp là việc của service. |
| View nhận dữ liệu thế nào? Sao `display()` không có tham số như đề? | Tờ checklist 1.1: View *"không nên truyền qua param mà phải nhận qua thuộc tính (ResponseDTO)"* và chỉ render **1 lần/luồng**. Controller gọi `studentView.setResponseDTO(responseDTO)` rồi `studentView.display()`. Đề ghi `void display(List<Student> students)` → em giữ **tên `display` + `void`**, ghi `// brief:` trên hàm; thầy muốn đúng chữ ký đề thì hỏi lại thầy (mục 9). |
| Validate ở đâu? | Ở `Main` qua `utils/Validation` (`getNonBlank`, `getMark`, `getYesNo`): sai thì `Validation` ném `Exception(Message…)`, `Main` bắt, in `e.getMessage()` rồi hỏi lại **đúng ô đó**. Controller/service chỉ nhận dữ liệu đã hợp lệ. |
| Sao `Main` là `final` và có `private Main() { }`? | Tờ checklist 3.4: *"Class chỉ có static method thì phải có private constructor, và khai báo class là final"*. |
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
| Thêm trường `address` | `Student`, `StudentDTO`, `Message`, `Main` (nhập), `StudentRepository` (tạo `Student`), `StudentService` (copy), `StudentView` (in) | `StudentController`, `StudentComparator`, `StudentRequestDTO`, `StudentResponseDTO` |
| Điểm thang 10 | chỉ `Constants.MAX_MARK` (câu lỗi tự đổi theo) | mọi file khác |

---

## 9. Chỗ khác với đề / bản cũ

| Chỗ | Đề / bản cũ | Bài này | Lý do |
|---|---|---|---|
| `display(List<Student>)` | đề: tham số `List<Student>`; bản cũ: `display(List<StudentResponseDTO>)` | `StudentView.display()` **không tham số**, danh sách vào qua `setResponseDTO` (thuộc tính); giữ tên + `void`; `// brief:` ngay trên | Guide: view **không được** thấy model; tờ checklist 1.1: View nhận qua **thuộc tính**, không qua param. **Hỏi thầy:** *"Đề ghi `void display(List<Student> students)`, còn tờ checklist bắt View nhận qua thuộc tính — em để `display()` không tham số, nhận danh sách qua `setResponseDTO`, vậy được không ạ?"* |
| Tên tham số `sortStudent` | đề: `students` | `studentList` | tờ checklist 1.5 (đuôi `List`); tên tham số không thuộc chữ ký nên chữ ký vẫn đúng đề |
| Dấu cách cuối `Please input student information ` | đề: **có** 1 dấu cách ở cuối dòng; bản cũ in không có | in **có** dấu cách (`Message.INPUT_INFO`) | đúng đề từng ký tự; `_tools/tests/J1SP0068.py` đổi sang `REPLACE_REFERENCE = True` vì bản chạy tham chiếu cũ thiếu dấu cách này |
| Repository | bản trước: *"không thêm/sửa/xoá → không repository"* | có `StudentRepository` giữ `ArrayList<Student>` | tờ checklist 1.1: *"Bắt buộc phải có repository"* |
| Controller nhận gì | bản trước: `displaySortedStudents(ArrayList<StudentRequestDTO> requests)` | `displaySortedStudents(StudentRequestDTO requestDTO)` — DTO chứa `ArrayList<StudentDTO> studentList` | tờ checklist 1.1: *"Controller nhận input từ main qua DTO"*; 1.5: đuôi `List` |
| `sortStudent` | đề không ghi lớp/modifier; bản cũ: `public static` trong `Main` | `private` trong `StudentService` | sắp xếp là nghiệp vụ → service; chỉ lớp này gọi → `private` |
| Tên lớp comparator | đề: "Student Comparator"; bản cũ: `StudentNameComparator` | `StudentComparator` | đúng chữ đề (bỏ dấu cách) |
| `List` vs `ArrayList` | bản cũ khai `List` khắp nơi | `ArrayList` trừ 2 chữ ký đề bắt (`// brief:`) | thầy dặn trên lớp (V5) |
| Kiến trúc | bản cũ: `entity/utils/ui`, Scanner trong `Validator` | MVC theo Guide | luật thầy |
| `NaN` | bản cũ nhận `NaN` làm điểm | báo `You must input a number.` | `NaN` lọt qua kiểm khoảng |
| Câu lỗi Name/Class/Mark/Y-N | đề không ghi | giữ đúng như bản cũ | đề im lặng → giữ màn hình đã kiểm |
| "enter student name, address, and mark" | đề (Function 1) nói **address** | nhập **Classes** | màn hình + Hint của đề đều là `classes`; "address" là lỗi chép đề |

---

## 10. Tờ checklist 25 mục — bài này đạt thế nào

| Mục | Chỗ trong code |
|---|---|
| 1.1 MVC + repository | `repository/StudentRepository` giữ `ArrayList<Student> studentList`; `StudentService` cất/đọc qua repository rồi mới sắp; controller nhận **một** `StudentRequestDTO`, chỉ import DTO/service/view; `StudentView` nhận `responseDTO` qua `setResponseDTO`, `display()` gọi **1 lần**; mọi nhập + validate ở `Main` |
| 1.4 method = động từ | `sortStudent`, `getSortedStudents`, `displaySortedStudents`, `addStudent`, `inputMark`… (`display`, `compare` là tên đề / Java đặt) |
| 1.5 tên biến | `studentList`, `rowList`, `sortedList` (collection → `List`); `requestDTO`/`responseDTO`; không có `ID` |
| 2.6 + 3.7 khai báo đầu block, có khởi tạo | `Main.main`: `more = true` ở đầu; mỗi hàm `input…`: `String line = "";` ở đầu, trong `while` chỉ `line = sc.nextLine();`; `Validation.getMark`: `float mark = 0f;`; `StudentView.display`: `studentDTO = null` rồi gán trong `for` |
| 2.8 dòng trống | trước mọi comment (kể cả comment field trong `Message`, `Constants`, DTO, `Student`), sau vùng khai báo, sau `}` trước câu lệnh kế |
| 3.3 ngoặc | `Validation`: `if ((input == null) \|\| …)`, `if ((mark < Constants.MIN_MARK) \|\| (mark > Constants.MAX_MARK))`, `(input == null) ? "" : input.trim()` |
| 3.4 | `public final class Main` + `private Main() { }`; `Validation`, `Constants`, `Message` cũng `final` + ctor private |
| 3.8 | không cộng chuỗi: `Student.toString` và `StudentView` đều dùng `String.format` |

**Chỗ cần hỏi thầy (đề đặt, tờ checklist bắt khác):** `void display(List<Student> students)` → em để
`display()` không tham số, nhận danh sách qua thuộc tính `responseDTO` (mục 9). Tham số `students` →
`studentList` không đổi chữ ký nên không cần hỏi.

Kiểm lại: `python3 _tools/verify.py J1SP0068` · `python3 _tools/lint.py HE176322_J1SP0068_*` · `checklist_audit.py` → 0 `VI_PHAM`.
`RUI_RO` còn lại chỉ là `String[] args` và tham số setter/constructor trùng tên field (`this.name = name`) — kiểu IDE sinh, được chấp nhận.
