# J1.L.P0021 — Student Management

> **Bài dài (Long Assignment)** — chấm theo **% hoàn thành**. Làm đủ 4 chức năng + đủ thông báo
> lỗi + giải thích được **Strategy** (Comparator) là lấy trọn LOC.

| | |
|---|---|
| Loại / LOC | Long Assignment · 350 LOC · 5 slot |
| Project | `HE176322_J1LP0021_StudentManagement` |
| Chạy | NetBeans: **File ▸ Open Project** → chọn thư mục → **F6** |
| Lớp chạy | `main.Main` |
| Kiểm tự động | `python3 _tools/verify.py --netbeans J1LP0021` → 4 kịch bản × 2 locale |

---

## 1. Đề bài nói gì

- Sinh viên có 4 thông tin: **ID, Student Name, Semester, Course Name** — course **chỉ có 3**: `Java`, `.Net`, `C/C++`.
- Menu: **1 Create · 2 Find and Sort · 3 Update/Delete · 4 Report · 5 Exit**.
- **Create**: phải tạo **ít nhất 10** sinh viên; từ đủ 10 trở đi hỏi `Do you want to continue (Y/N)?` — Y nhập tiếp, N về menu.
- **Find and Sort**: nhập tên **hoặc một phần tên** → liệt kê *Student name, semester, Course Name*, **sắp theo tên**.
- **Update/Delete**: tìm theo **ID** → hỏi `Do you want to update (U) or delete (D) student` → U sửa, D xoá.
- **Report**: mỗi dòng `Tên | Course | Tổng số lần` (ví dụ đề: `Nguyen Van A | Java | 2`).

**Đề bắt buộc** (mục Guidelines):

| Thứ | Đề viết | Bài này đặt ở |
|---|---|---|
| Lớp `Student` có `id, studentName, semester, courseName` | slot 1 | `model/Student.java` — đúng 4 tên field |
| `Collections.sort()` + ghi đè `compare()` của `Comparator` | slot 2 | `service/StudentNameComparator.compare` được `StudentService.findAndSort` truyền vào `Collections.sort` |
| Dùng `ArrayList` lưu danh sách | Background | `repository/StudentRepository`: `ArrayList<Student> students` |
| Câu hỏi `Do you want to continue (Y/N)?` | Create | `Message.ASK_CONTINUE` — chép đúng chữ |
| Dòng report `Nguyen Van A \| Java \| 2` | Report | `Constants.REPORT_FORMAT = "%s \| %s \| %d"` |

---

## 2. Kiến thức cần biết

### 2.1 Comparator + `Collections.sort` — đúng thứ đề bắt

```java
Collections.sort(found, sortStrategy);   // sortStrategy = new StudentNameComparator()
```

`Collections.sort` **biết cách sắp** (thuật toán merge sort của JDK), nhưng **không biết thứ tự nào là
đúng** — nó hỏi `compare(a, b)`: số âm → `a` đứng trước, `0` → bằng nhau, dương → `b` đứng trước.

| `compare(a, b)` trong bài | Vì sao |
|---|---|
| `a.getStudentName().compareToIgnoreCase(b.getStudentName())` | `compareTo` thường xếp **mọi chữ hoa trước mọi chữ thường** → `anh` đứng sau `Binh`. Bỏ qua hoa/thường mới "đúng mắt" |
| bằng tên → so tiếp `id` | đề có 2 người tên `Nguyen Van A`; thêm tiêu chí phụ để lần chạy nào cũng ra **một** thứ tự |

### 2.2 Tìm "một phần tên" không phân biệt hoa thường

```java
student.getStudentName().toLowerCase().contains(text)   // text đã toLowerCase
```
Gõ `van` → ra `Nguyen Van A`, `Le Van D`… (`Student.isNameContains`).

### 2.3 Report — gom nhóm bằng `LinkedHashMap`

Chạy tay dữ liệu ví dụ của đề:

| Sinh viên đọc tới | Khoá `tên thường + course` | Map sau bước |
|---|---|---|
| Nguyen Van A · Java | `nguyen van a␀JAVA` | mới → `(Nguyen Van A, Java, 1)` |
| Nguyen Van A · Java | trùng khoá | `increaseTotal()` → **2** |
| Nguyen Van B · .Net | `nguyen van b␀DOT_NET` | mới → 1 |
| Nguyen Van B · Java | `nguyen van b␀JAVA` | mới → 1 |

Rồi `Collections.sort(items, reportStrategy)` (tên, rồi course) → in. `LinkedHashMap` vì tra khoá **1 bước**
(`get`) và giữ thứ tự gặp; `␀` (`Constants.KEY_SEPARATOR`) là ký tự không gõ được nên 2 cặp khác nhau không
thể ra cùng một khoá.

### 2.4 Java dùng trong bài

| API | Dùng làm gì |
|---|---|
| `ArrayList.add/get/set/remove` | lưu, sửa đúng vị trí, xoá sinh viên |
| `Collections.sort(list, comparator)` | sắp với Comparator (đề bắt) |
| `enum Course` + `values()` | tập **đóng** 3 course; `fromLabel("java")` → `JAVA` |
| `String.format("%-20s %-10d %s", ...)` | căn cột bảng |
| `Integer` (không phải `int`) trong `StudentRequestDTO` | `null` = "bỏ trống, giữ số cũ" khi update |

---

## 3. Thiết kế

```
HE176322_J1LP0021_StudentManagement/src/
├── constants/  Message.java            mọi câu chữ màn hình
│               Constants.java          số menu, MIN_STUDENTS=10, chữ Y/N/U/D, định dạng cột
│               Course.java             «enum» Java / .Net / C/C++
├── model/      Student.java            4 field của đề (JavaBean) + isNameContains
│               ReportItem.java         1 dòng report: tên, course, total + increaseTotal
├── dto/        StudentRequestDTO       main ──► controller
│               StudentResponseDTO      1 sinh viên ──► view
│               ReportResponseDTO       1 dòng report ──► view
├── repository/ IStudentRepository      «interface» hợp đồng CRUD (DIP)
│               StudentRepository       ArrayList<Student> + CRUD
├── service/    StudentService          luật nghiệp vụ, tìm + sắp, report (Context)
│               StudentNameComparator   ConcreteStrategy: theo tên (đề bắt)
│               ReportComparator        ConcreteStrategy: tên rồi course
├── controller/ StudentController       Facade: cắm repository + strategy; service ──► view
├── view/       StudentView             in bảng, report, câu thông báo
├── utils/      Validation              kiểm DẠNG dữ liệu gõ (static)
└── main/       Main                    menu + Scanner
```

| Lớp | Làm gì | Không được làm |
|---|---|---|
| `Main` | menu, **đọc bàn phím**, gói DTO, bắt lỗi in `e.getMessage()` | gọi model/view/service/repository |
| `StudentController` | nhận DTO → service → view | Scanner, `System.out`, static, import model |
| `StudentService` | kiểm luật (trùng ID, tên trống, semester > 0, 3 course), tìm + sắp, report | in ra, đọc phím |
| `StudentRepository` | giữ `ArrayList`, thêm/tìm/sửa/xoá | kiểm luật, in ra |
| `Student`, `ReportItem` | mô tả đối tượng | Scanner, printf, static |
| `StudentView` | in | tính toán |
| `Validation` | "có phải số không? Y hay N?" | luật về sinh viên (đó là việc service) |

**Luồng Create** (Controller ↔ Service ↔ Repository ↔ Model):

```
Main: đọc 4 ô (semester phải là số, hỏi lại tại chỗ) ──► StudentRequestDTO
   └─► controller.createStudent(dto)
          └─► service.createStudent(dto): ID trống? trùng? tên trống? semester<=0? course lạ? → throw
                 └─► repository.addStudent(new Student(...))
          └─► view.showMessage("Student [S001] has been added.")
Main: controller.checkEnoughStudents() → < 10: in "At least 10 students are required - N so far."
                                        ≥ 10: hỏi "Do you want to continue (Y/N)? "
```

### 3.1 Design Pattern trong bài

**Strategy** (Behavioral) — pattern chính:

| Yếu tố | Trong bài |
|---|---|
| **Name** | Strategy |
| **Problem** | Đề bắt "sort by name", nhưng thầy rất hay bảo *"sắp theo semester xem"*. Viết cứng `if` so tên trong service thì mỗi lần đổi phải mở service ra sửa. |
| **Solution** | `java.util.Comparator<T>` = **Strategy** (interface `compare`). `StudentNameComparator`, `ReportComparator` = **ConcreteStrategy**. `StudentService` = **Context**: giữ `Comparator<Student> sortStrategy` nhận qua **constructor**, gọi `Collections.sort(found, sortStrategy)` mà không biết là sắp theo gì. `StudentController` là nơi **chọn**: `new StudentService(new StudentRepository(), new StudentNameComparator())`. |
| **Consequences** | ✅ Đổi thứ tự = **thêm 1 class** + sửa 1 dòng controller; service/view/main đứng yên (**O**CP, **D**IP). ❌ Thêm file; người đọc phải nhảy sang lớp khác mới thấy tiêu chí so sánh. |

**Thầy bảo "sắp theo semester"**: tạo `service/StudentSemesterComparator implements Comparator<Student>`
(`return Integer.compare(a.getSemester(), b.getSemester());`) → sửa đúng 1 dòng trong constructor `StudentController`.

**Các pattern khác**

| Pattern | Ở đâu | Name · Problem · Solution · Consequences |
|---|---|---|
| **MVC** ("MVC JSP" của thầy) | controller ~ Servlet, view ~ JSP, model ~ JavaBean | tách nhập / xử lý / in; đổi cách in chỉ sửa `StudentView` · thêm nhiều lớp |
| **Facade** | `StudentController` | Main chỉ thấy **một cửa** `createStudent(dto)`, không biết có service, repository, view · controller phải giữ vai điều hướng, không ôm luật |
| **Repository** (mẫu kiến trúc, không thuộc 23 GoF) | `IStudentRepository` + `StudentRepository` | một nơi duy nhất đụng vào `ArrayList` · đổi sang lưu file = thêm 1 lớp implement interface |

### 3.2 SOLID trong bài

| Nguyên lý | Ở đâu |
|---|---|
| **S** | `Student` chỉ mô tả · `StudentRepository` chỉ lưu · `StudentService` chỉ luật · `StudentView` chỉ in · `Validation` chỉ kiểm dạng |
| **O** | thêm thứ tự sắp = thêm Comparator; thêm course = thêm 1 hằng trong `Course` (thông báo lỗi tự cập nhật nhờ `Course.labels()`) |
| **L** | mọi `Comparator<Student>` thay cho nhau được; `StudentRepository` thay được chỗ `IStudentRepository` |
| **I** | `IStudentRepository` chỉ 6 hàm CRUD mà service thật sự dùng |
| **D** | `StudentService` phụ thuộc `IStudentRepository` và `Comparator` (trừu tượng), nhận qua constructor |

---

## 4. Code từng bước

> Thầy: *"code cái **model trước rồi đến data**"*.

| Bước | File | Việc |
|---|---|---|
| 1 | `constants/Course.java` | enum 3 course + `label` + `fromLabel` + `labels` (model cần nó nên gõ trước) |
| 2 | `model/Student.java` | 4 field `private` + ctor rỗng + ctor đủ + get/set + `isNameContains` + `toString` |
| 3 | `model/ReportItem.java` | tên, course, total + `increaseTotal()` |
| 4 | `dto/StudentRequestDTO`, `StudentResponseDTO`, `ReportResponseDTO` | JavaBean; `Integer semester` ở Request |
| 5 | `repository/IStudentRepository` → `StudentRepository` | `countStudents/findById/addStudent/updateStudent/deleteStudent/findAll` |
| 6 | `service/StudentNameComparator`, `ReportComparator` | `compare` (**đề bắt**) |
| 7 | `service/StudentService` | `createStudent`, `findAndSort`, `findStudent`, `updateStudent`, `deleteStudent`, `report` + hàm `private` kiểm luật |
| 8 | `view/StudentView` | 3 setter + `displaySearchResult/displayStudent/displayReport/showMessage` |
| 9 | `controller/StudentController` | constructor cắm strategy; mỗi hàm: service → view |
| 10 | `constants/Message`, `Constants` | gõ dần khi các bước trên cần |
| 11 | `utils/Validation` | `getText/getChoice/getInt/getOptionalInt/getOption` |
| 12 | `main/Main` | menu `while`+`switch`, vòng Create, Update/Delete |

**Bẫy hay gặp**

1. **Sắp thẳng danh sách gốc** khi tìm → mất luôn thứ tự nhập. Service sắp **bản sao** (`findAll()` trả `new ArrayList<>(students)`).
2. **Update ghi dở dang**: set tên rồi mới phát hiện course sai → sinh viên sửa được một nửa. Bài này kiểm **hết** trước rồi mới `repository.updateStudent(updated)`.
3. Hỏi Y/N **trước** khi đủ 10 → cho thoát sớm, trái đề. Chỉ hỏi khi `checkEnoughStudents()` trả `true`.
4. `sc.nextInt()` rồi `nextLine()` → nuốt dòng. Luôn `nextLine()` rồi parse.

---

## 5. Test trước khi gọi thầy

> Thầy: *"test tất cả các happy case cũng như hiển thị đủ các message validation"*.

| # | Chọn | Gõ | Phải thấy |
|---|---|---|---|
| 1 | menu | `abc`, rồi `9` | `You must input a number.` · `Please choose from 1 to 5.` |
| 2 | 2 / 3 / 4 khi chưa có ai | bất kỳ | `The student list is empty.` |
| 3 | 1 | ID trống | `ID cannot be empty.` rồi `At least 10 students are required - 0 so far.` |
| 4 | 1 | tên trống | `Student name cannot be empty.` |
| 5 | 1 | semester `abc` | `You must input a number.` rồi hỏi lại **ngay** semester |
| 6 | 1 | semester `0` | `Semester must be greater than 0.` (sau khi gõ xong course) |
| 7 | 1 | course `Pascal` | `Course must be one of: Java, .Net, C/C++.` |
| 8 | 1 | ID `s001` khi đã có `S001` | `ID [s001] already exists.` |
| 9 | 1 | đủ 10 người | `Do you want to continue (Y/N)? `; gõ `x` → `Please enter Y or N.`; `Y` nhập tiếp; `N` về menu |
| 10 | 2 | `nguyen van` | bảng *Student name / Semester / Course* **sắp theo tên** |
| 11 | 2 | `Zorro` / trống | `No student found.` / `Search keyword cannot be empty.` |
| 12 | 3 | `S999` / trống | `ID [S999] does not exist.` / `ID cannot be empty.` |
| 13 | 3 | `s03` → `x` | hiện dòng `S03` (ID gõ thường vẫn tìm ra) · `Please enter U or D.` |
| 14 | 3 | `U` → trống, `abc`, `0`, trống | `You must input a number.` → `Semester must be greater than 0.` (không đổi gì) |
| 15 | 3 | `U` → tên mới, `5`, `.net` | `Student [S03] has been updated.` |
| 16 | 3 | `U` → trống, trống, `Wrong` | `Course must be one of: ...` và tên **không** đổi |
| 17 | 3 | `D` | `Student [S002] has been deleted.` |
| 18 | 4 | — | `Nguyen Van A \| Java \| 2` … sắp theo tên rồi course |

---

## 6. Debug

| Việc | Cách làm |
|---|---|
| Breakpoint | dòng `int byName = ...` trong `StudentNameComparator.compare` |
| Chạy | **Ctrl+F5**, tạo 10 người, chọn 2, gõ `van` |
| Quan sát | tab **Variables**: `first`, `second`, `byName` — mỗi lần dừng là một lần `Collections.sort` hỏi "ai trước?" |
| Bước | Ở `StudentService.findAndSort` đặt breakpoint `Collections.sort(found, sortStrategy);` rồi **F7** → nhảy vào `compare` của `StudentNameComparator` (đa hình qua interface) |
| Report | breakpoint `item.increaseTotal();` — thấy total của `Nguyen Van A · Java` lên 2 |

---

## 7. Câu hỏi thầy hay hỏi

### OOP

| Câu hỏi | Trả lời mẫu |
|---|---|
| 4 tính chất OOP ở đâu? | **Đóng gói**: field `private` trong `Student` + get/set. **Kế thừa**: `StudentRepository implements IStudentRepository`, 2 Comparator `implements Comparator`; ghi đè `toString()` của `Object`. **Đa hình**: `Collections.sort` gọi `compare` qua biến kiểu `Comparator<Student>` → chạy bản của `StudentNameComparator`; service gọi `studentRepository.findById` qua kiểu interface. **Trừu tượng**: `IStudentRepository` chỉ nói "lưu được", không nói bằng gì. |
| Sao course là `enum` mà không phải `String`? | Đề: *"only three courses"* — tập **đóng**. Với `String`, `java`/`Java`/`JAVA` là 3 course khác nhau và report gom sai. Enum làm compiler chặn luôn course thứ tư. |
| Sao `Course` nằm ở `constants`? | Guide: *Constants "chứa hằng số, enum"*. |

### Access modifier, static, kiểu trả về

| Câu hỏi | Trả lời mẫu |
|---|---|
| Hàm nào `public`, vì sao? | Chỉ hàm lớp khác gọi: hàm của controller (Main gọi), service (controller gọi), repository (service gọi), view (controller gọi), getter/setter. `checkNotEmpty`, `requireStudent`, `checkSemester`, `parseCourse`, `toResponse` là `private` — chỉ `StudentService` dùng. Hàm nhập trong `Main` đều `private static`. |
| Mọi field `private`? | Có — kể cả `sortStrategy`, `studentRepository`; không lớp nào đổi được chúng từ ngoài. |
| `static` ở đâu? | Chỉ 3 chỗ: `Validation` (Guide *"phải dùng static method"* — không dùng dữ liệu đối tượng), hằng trong `Message/Constants` + `Course.fromLabel/labels` (làm việc trên cả enum, không trên một hằng), và **hàm** trong `Main`. |
| **Bỏ `static` ở `Validation` thì sao?** | `Validation.getInt(...)` báo lỗi biên dịch. Phải bỏ `private` constructor, tạo `Validation v = new Validation();` trong `main()` rồi truyền `v` vào các hàm nhập. |
| **Bỏ `static` ở hàm của `Main`** | `main` là static nên không gọi được hàm thường; phải `new Main()` rồi gọi qua đối tượng. |
| **Sao `ArrayList` mà không `List`?** | `List` là **interface** (hợp đồng: `add/get/remove`); `ArrayList` là **lớp cài đặt** bằng mảng động — lấy theo chỉ số O(1), chèn/xoá giữa phải dời phần tử. `LinkedList` cài cùng hợp đồng bằng nút liên kết. Đề bắt `ArrayList`, và `updateStudent` dùng `set(i, ...)` theo chỉ số — nên em khai báo đúng kiểu cụ thể. |
| Sao `Comparator<Student>` lại khai báo kiểu interface? | Đó **chính là** Strategy: service phải nhận **bất kỳ** thứ tự nào. Luật "kiểu cụ thể" của thầy nói về collection (List/Map), không phải interface pattern. |
| `findById` trả `null`? | "Không thấy" là câu trả lời bình thường của kho; service biến nó thành `ID [..] does not exist.` |
| `checkEnoughStudents` trả `boolean`? | Vòng Create chỉ cần có/không để quyết định hỏi Y/N. |
| `findStudent` trả DTO cho Main — trái luật? | Main được làm việc với **DTO** (Guide). Nó cần tên/semester/course hiện tại để in *"blank to keep ..."*; nó không đụng model. |
| Hàm controller trả `void`? | Kết quả đã sang view; lỗi đi bằng `throw`. |

### Kiến trúc & thuật toán

| Câu hỏi | Trả lời mẫu |
|---|---|
| Create gọi controller 2 lần/lượt — trái luật "1 lần"? | `createStudent` là việc thêm (1 lần/sinh viên); `checkEnoughStudents` là **bước kiểm** để biết có được hỏi Y/N — giống `checkExistDoctor` của P0055. Update/Delete: `findStudent` là bước kiểm, rồi `updateStudent` **hoặc** `deleteStudent` đúng 1 lần. |
| Sao lỗi Create hiện **sau** khi gõ xong 4 ô? | Luật sinh viên (trùng ID, tên trống, semester > 0, course) nằm ở **service**, kiểm cùng lúc; chỉ "semester phải là số" là lỗi **dạng** nên `Validation` bắt ngay. |
| Độ phức tạp? | Tìm theo ID O(n) (duyệt ArrayList); `Collections.sort` O(n log n); report O(n) gom + O(k log k) sắp. |
| Sao không có hàm nào 3 tham số? | Thầy: *"không truyền 3 tham số 1 hàm"* → dữ liệu gói vào `StudentRequestDTO`. Chỉ `Validation.getChoice/getOption` 3 tham số (đúng mẫu Guide `getChoice(input, min, max)`) và constructor. |

---

## 8. Thầy đổi yêu cầu tại chỗ

| Thầy bảo | Sửa file | Không phải đụng |
|---|---|---|
| Sắp theo semester / giảm dần | thêm 1 Comparator trong `service/` + 1 dòng constructor `StudentController` | `StudentService`, `Main`, `View` |
| Thêm course `Python` | 1 hằng `PYTHON("Python")` trong `Course` + `Message.INPUT_COURSE` | service, repository |
| Đổi 10 thành 5 | `Constants.MIN_STUDENTS` | mọi file khác |
| Lưu vào file | lớp mới `FileStudentRepository implements IStudentRepository` + 1 dòng controller | `StudentService` |
| Thêm cột Semester vào report | `ReportItem`/`ReportResponseDTO` + khoá gom trong `StudentService.report` + `REPORT_FORMAT` | `Main`, `Controller` |
| Không cho sửa trùng tên+course | thêm kiểm trong `StudentService.updateStudent` + 1 `Message` | view, main |

---

## 9. Chỗ khác với đề / bản cũ

| Chỗ | Đề / bản cũ | Bài này | Lý do |
|---|---|---|---|
| Hỏi Y/N khi nào | đề: *"greater than 10"* | hỏi **từ người thứ 10** | đề cũng nói *"at least 10"*; giữ đúng bản tham chiếu đã kiểm |
| Menu | đề in `- Create`… (mất số) | `1. Create` … + câu `(Please choose 1 to Create, …)` | số là thứ người dùng gõ; câu cuối chép đúng đề |
| Prompt, thông báo lỗi | đề không ghi | giữ **y nguyên** bản tham chiếu (3 kịch bản của nó chạy qua `verify.py`) | quy tắc bộ lời giải |
| Kiến trúc | bản cũ: `entity/bo/ui`, Scanner static trong `Validator`, controller in ra | MVC Guide: Scanner chỉ ở `main`, in ở `view`, luật ở `service`, CRUD ở `repository` | luật thầy |
| `Course` | bản cũ: `entity.Course` | `constants.Course` | Guide: enum ở Constants |
| Report | bản cũ: lớp `CourseCount` + comparator ẩn danh | `ReportItem` (model) + `ReportComparator` (lớp có tên) | pattern Strategy rõ ràng, lint bắt comment từng hàm |
| Kho | bản cũ: `List<Student>` | `ArrayList<Student>` sau interface `IStudentRepository` | thầy: kiểu cụ thể; bài Long → DIP |
