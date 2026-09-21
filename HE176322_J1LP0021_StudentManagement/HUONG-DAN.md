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
| Dùng `ArrayList` lưu danh sách | Background | `repository/StudentRepository`: `ArrayList<Student> studentList` |
| Câu hỏi `Do you want to continue (Y/N)?` | Create | `Message.ASK_CONTINUE` — chép đúng chữ |
| Câu hỏi `Do you want to update (U) or delete (D) student` | Update/Delete | `Message.ASK_UPDATE_DELETE` — chép đúng chữ |
| Dòng report `Nguyen Van A \| Java \| 2` | Report | `Constants.REPORT_FORMAT = "%s \| %s \| %d"` (dùng trong `ReportItem.toString()`) |

---

## 2. Kiến thức cần biết

### 2.1 Comparator + `Collections.sort` — đúng thứ đề bắt

```java
Collections.sort(foundList, sortStrategy);   // sortStrategy = new StudentNameComparator()
```

`Collections.sort` **biết cách sắp** (thuật toán merge sort của JDK), nhưng **không biết thứ tự nào là
đúng** — nó hỏi `compare(a, b)`: số âm → `a` đứng trước, `0` → bằng nhau, dương → `b` đứng trước.

| `compare(a, b)` trong bài | Vì sao |
|---|---|
| `a.getStudentName().compareToIgnoreCase(b.getStudentName())` | `compareTo` thường xếp **mọi chữ hoa trước mọi chữ thường** → `anh` đứng sau `Binh`. Bỏ qua hoa/thường mới "đúng mắt" |
| bằng tên → so tiếp `id` | đề có 2 người tên `Nguyen Van A`; thêm tiêu chí phụ để lần chạy nào cũng ra **một** thứ tự |

### 2.2 Tìm "một phần tên" không phân biệt hoa thường

```java
student.getStudentName().toLowerCase().contains(searchText)   // searchText đã toLowerCase
```
Gõ `van` → ra `Nguyen Van A`, `Le Van D`… (`Student.isNameContains`).

### 2.3 Report — gom nhóm bằng `LinkedHashMap`

Chạy tay dữ liệu ví dụ của đề:

| Sinh viên đọc tới | Khoá `tên thường + ␀ + course` | `reportMap` sau bước |
|---|---|---|
| Nguyen Van A · Java | `nguyen van a␀JAVA` | mới → `(Nguyen Van A, Java, 1)` |
| Nguyen Van A · Java | trùng khoá | `increaseTotal()` → **2** |
| Nguyen Van B · .Net | `nguyen van b␀DOT_NET` | mới → 1 |
| Nguyen Van B · Java | `nguyen van b␀JAVA` | mới → 1 |

Rồi `Collections.sort(reportItemList, reportStrategy)` (tên, rồi course) → mỗi `ReportItem.toString()` là một
dòng. `LinkedHashMap` vì tra khoá **1 bước** (`get`) và giữ thứ tự gặp. Khoá dựng bằng
`String.format(Constants.REPORT_KEY_FORMAT, …)` với `REPORT_KEY_FORMAT = "%s\u0000%s"` — `␀` (ký tự NUL, trong code
viết bằng escape `\u0000` cho **nhìn thấy được**) là ký tự không gõ được nên 2 cặp khác nhau không thể ra cùng một
khoá; dùng `String.format` chứ không cộng chuỗi `+` (tờ checklist 3.8).

### 2.4 Java dùng trong bài

| API | Dùng làm gì |
|---|---|
| `ArrayList.add/get/set/remove` | lưu, sửa đúng vị trí, xoá sinh viên |
| `Collections.sort(list, comparator)` | sắp với Comparator (đề bắt) |
| `enum Course` + `values()` | tập **đóng** 3 course; `findByLabel("java")` → `JAVA` |
| `String.format("%-20s %-10d %s", ...)` | căn cột bảng, dòng report (`toString()` của model) |
| `Integer` (không phải `int`) trong `StudentRequestDTO` | `null` = "bỏ trống, giữ số cũ" khi update |

---

## 3. Thiết kế

```
HE176322_J1LP0021_StudentManagement/src/
├── constants/  Message.java            mọi câu chữ màn hình
│               Constants.java          số menu, MIN_STUDENTS=10, chữ Y/N/U/D, định dạng cột
│               Course.java             «enum» Java / .Net / C/C++ (findByLabel, joinLabels)
├── model/      Student.java            4 field của đề (JavaBean) + isNameContains + toString = 1 dòng bảng
│               ReportItem.java         1 dòng report: tên, course, total + increaseTotal + toString
├── dto/        StudentRequestDTO       main ──► controller: 4 ô, searchText, option U/D, studentList (Create)
│               StudentResponseDTO      controller ──► view: message, messageList, searchRowList, reportRowList
├── repository/ IStudentRepository      «interface» hợp đồng CRUD (DIP)
│               StudentRepository       ArrayList<Student> studentList + CRUD
├── service/    StudentService          luật nghiệp vụ, tìm + sắp, report (Context)
│               StudentNameComparator   ConcreteStrategy: theo tên (đề bắt)
│               ReportComparator        ConcreteStrategy: tên rồi course
├── controller/ StudentController       Facade: cắm repository + strategy; service ──► view (1 lần/luồng)
├── view/       StudentView             responseDTO · setResponseDTO · display()
├── utils/      Validation              kiểm DẠNG dữ liệu gõ (static)
└── main/       Main                    final + ctor private; menu + Scanner + nhập/validate
```

| Lớp | Làm gì | Không được làm |
|---|---|---|
| `Main` | menu, **đọc bàn phím**, kiểm dạng (số? Y/N? U/D?) qua `Validation`, gói DTO, gom sinh viên của Create, bắt lỗi in `e.getMessage()`; **mỗi `case` gọi controller 1 lần** | gọi model/view/service/repository |
| `StudentController` | nhận DTO → service → gói `StudentResponseDTO` → `view.setResponseDTO` + `view.display()` **1 lần** | Scanner, `System.out`, static, import model |
| `StudentService` | kiểm luật (ID trống/trùng/không có, tên trống, semester > 0, 3 course), tìm + sắp, report; trả **chữ** (dòng bảng, câu kết quả) | in ra, đọc phím, trả model cho controller |
| `StudentRepository` | giữ `ArrayList<Student> studentList`, thêm/tìm/sửa/xoá | kiểm luật, in ra |
| `Student`, `ReportItem` | mô tả đối tượng; `toString()` trả chữ của 1 dòng | Scanner, printf, static |
| `StudentView` | in những gì controller đã đặt vào `responseDTO` | tính toán, nhận tham số |
| `Validation` | "có phải số không? Y hay N?" | luật về sinh viên (đó là việc service) |

**Luồng Create** (Controller ↔ Service ↔ Repository ↔ Model):

```
Main.inputStudentList(sc, controller)                         ← case 1
   storedCount = controller.countStudents()   ← CHỈ ĐỂ ĐỌC, 1 lần đầu luồng: số sinh viên đã lưu
                                                 (không in, không ghi) — đề đếm CẢ danh sách
   lặp: đọc 4 ô vào requestDTO (semester phải là số, hỏi lại tại chỗ)
        keepStudent: controller.checkStudent(requestDTO)   ← CHỈ ĐỂ KIỂM: service ném
                     "ID [..] already exists.", "Semester must be greater than 0."…; không in, không lưu
                     hợp lệ → requestDTO.getStudentList().add(bản sao 4 ô)
        count = storedCount + requestDTO.getStudentList().size()
        count < 10: Main in "At least 10 students are required - N so far."
        count ≥ 10: hỏi "Do you want to continue (Y/N)? " — Y lặp tiếp, N thoát vòng
controller.createStudents(requestDTO)                        ← 1 lần duy nhất của case 1
   └─► service.createStudents: mỗi sinh viên → checkStudent lần nữa → new Student(...) → repository.addStudent
   └─► responseDTO.setMessageList(["Student [S001] has been added.", …]) → view.setResponseDTO → view.display()
Main: catch (Exception e) → in e.getMessage()
```

**Luồng Update/Delete**:

```
Main.inputUpdateDelete(sc, controller)                        ← case 3
   đọc id → controller.checkExistStudent(requestDTO)   ← CHỈ ĐỂ KIỂM: "The student list is empty.",
                                                          "ID cannot be empty.", "ID [..] does not exist."
   hỏi "Do you want to update (U) or delete (D) student? " → requestDTO.setOption("U"/"D")
   U: đọc tên, semester (trống = giữ), course mới
controller.updateOrDeleteStudent(requestDTO)                 ← 1 lần duy nhất của case 3
   D → service.deleteStudent;  U → service.updateStudent (kiểm hết rồi mới ghi)
   └─► responseDTO.setMessage("Student [S03] has been updated.") → view.setResponseDTO → view.display()
```

### 3.1 Design Pattern trong bài

**Strategy** (Behavioral) — pattern chính:

| Yếu tố | Trong bài |
|---|---|
| **Name** | Strategy |
| **Problem** | Đề bắt "sort by name", nhưng thầy rất hay bảo *"sắp theo semester xem"*. Viết cứng `if` so tên trong service thì mỗi lần đổi phải mở service ra sửa. |
| **Solution** | `java.util.Comparator<T>` = **Strategy** (interface `compare`). `StudentNameComparator`, `ReportComparator` = **ConcreteStrategy**. `StudentService` = **Context**: giữ `Comparator<Student> sortStrategy` nhận qua **constructor**, gọi `Collections.sort(foundList, sortStrategy)` mà không biết là sắp theo gì. `StudentController` là nơi **chọn**: `new StudentService(new StudentRepository(), new StudentNameComparator())`. |
| **Consequences** | ✅ Đổi thứ tự = **thêm 1 class** + sửa 1 dòng controller; service/view/main đứng yên (**O**CP, **D**IP). ❌ Thêm file; người đọc phải nhảy sang lớp khác mới thấy tiêu chí so sánh. |

**Thầy bảo "sắp theo semester"**: tạo `service/StudentSemesterComparator implements Comparator<Student>`
(`return Integer.compare(first.getSemester(), second.getSemester());`) → sửa đúng 1 dòng trong constructor `StudentController`.

**Các pattern khác**

| Pattern | Ở đâu | Name · Problem · Solution · Consequences |
|---|---|---|
| **MVC** ("MVC JSP" của thầy) | controller ~ Servlet, view ~ JSP, model ~ JavaBean | tách nhập / xử lý / in; đổi cách in chỉ sửa `StudentView` · thêm nhiều lớp |
| **Facade** | `StudentController` | Main chỉ thấy **một cửa** `createStudents(requestDTO)`, không biết có service, repository, view · controller phải giữ vai điều hướng, không ôm luật |
| **Repository** (mẫu kiến trúc, không thuộc 23 GoF) | `IStudentRepository` + `StudentRepository` | một nơi duy nhất đụng vào `ArrayList` · đổi sang lưu file = thêm 1 lớp implement interface |

### 3.2 SOLID trong bài

| Nguyên lý | Ở đâu |
|---|---|
| **S** | `Student` chỉ mô tả · `StudentRepository` chỉ lưu · `StudentService` chỉ luật · `StudentView` chỉ in · `Validation` chỉ kiểm dạng |
| **O** | thêm thứ tự sắp = thêm Comparator; thêm course = thêm 1 hằng trong `Course` (thông báo lỗi tự cập nhật nhờ `Course.joinLabels()`) |
| **L** | mọi `Comparator<Student>` thay cho nhau được; `StudentRepository` thay được chỗ `IStudentRepository` |
| **I** | `IStudentRepository` chỉ 6 hàm CRUD mà service thật sự dùng |
| **D** | `StudentService` phụ thuộc `IStudentRepository` và `Comparator` (trừu tượng), nhận qua constructor |

---

## 4. Code từng bước

> Thầy: *"code cái **model trước rồi đến data**"*.

| Bước | File | Việc |
|---|---|---|
| 1 | `constants/Course.java` | enum 3 course + `label` + `findByLabel` + `joinLabels` (model cần nó nên gõ trước) |
| 2 | `model/Student.java` | 4 field `private` + ctor rỗng + ctor đủ + get/set + `isNameContains` + `toString` (1 dòng bảng) |
| 3 | `model/ReportItem.java` | tên, course, total + `increaseTotal()` + `toString` (1 dòng report) |
| 4 | `dto/StudentRequestDTO`, `StudentResponseDTO` | JavaBean; `Integer semester`, `option`, `studentList` ở Request; `message` + 3 danh sách dòng ở Response |
| 5 | `repository/IStudentRepository` → `StudentRepository` | `countStudents/findById/addStudent/updateStudent/deleteStudent/findAll` |
| 6 | `service/StudentNameComparator`, `ReportComparator` | `compare` (**đề bắt**) |
| 7 | `service/StudentService` | `countStudents`, `checkStudent`, `createStudents`, `findAndSort`, `checkExistStudent`, `updateStudent`, `deleteStudent`, `report` + hàm `private` kiểm luật |
| 8 | `view/StudentView` | field `responseDTO` + `setResponseDTO` + `display()` |
| 9 | `controller/StudentController` | constructor cắm strategy; mỗi hàm: service → `responseDTO` → view 1 lần |
| 10 | `constants/Message`, `Constants` | gõ dần khi các bước trên cần |
| 11 | `utils/Validation` | `getText/getChoice/getInt/getOptionalInt/getOption` |
| 12 | `main/Main` | `final` + `private Main()`; menu `while`+`switch`, `inputStudentList` + `keepStudent`, `inputSearch`, `inputUpdateDelete` |

**Bẫy hay gặp**

1. **Sắp thẳng danh sách gốc** khi tìm → mất luôn thứ tự nhập. Service sắp **bản sao** (`findAll()` trả `new ArrayList<>(studentList)`).
2. **Update ghi dở dang**: set tên rồi mới phát hiện course sai → sinh viên sửa được một nửa. Bài này kiểm **hết** trước rồi mới `studentRepository.updateStudent(newStudent)`.
3. Hỏi Y/N **trước** khi đủ 10 → cho thoát sớm, trái đề. Chỉ hỏi khi `storedCount + requestDTO.getStudentList().size()` đã ≥ 10. Ngược lại, **đếm lại từ 0** mỗi lượt Create cũng lệch đề: *"number of students"* là cả danh sách, nên lượt Create sau (đã có ≥ 10) hỏi Y/N ngay sau mỗi sinh viên mới.
4. Gom sinh viên rồi mới lưu mà **quên kiểm trùng ID trong chính lượt Create** → hai `S001` cùng lọt. `StudentService.isTypedBefore` so với `studentList` của request.
5. `sc.nextInt()` rồi `nextLine()` → nuốt dòng. Luôn `nextLine()` rồi parse.

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
| 8 | 1 | ID `s001` khi đã gõ `S001` trong lượt này (hoặc đã lưu từ trước) | `ID [s001] already exists.` |
| 9 | 1 | đủ 10 người | `Do you want to continue (Y/N)? `; gõ `x` → `Please enter Y or N.`; `Y` nhập tiếp; `N` → in **một lượt** `Student [..] has been added.` cho từng người rồi về menu |
| 10 | 1 lần nữa (đã lưu 9 người) | ID đã lưu, rồi 1 người mới | `ID [S02] already exists.` · `At least 10 students are required - 9 so far.` · người mới là thứ 10 → hỏi ngay `Do you want to continue (Y/N)? ` |
| 11 | 1 lần nữa (đã có ≥ 10) | 1 người (kể cả bị từ chối) | hỏi `Do you want to continue (Y/N)? ` ngay sau **mỗi** người — đề đếm **cả danh sách** |
| 12 | 2 | `nguyen van` | bảng *Student name / Semester / Course* **sắp theo tên** |
| 13 | 2 | `Zorro` / trống | `No student found.` / `Search keyword cannot be empty.` |
| 14 | 3 | `S999` / trống | `ID [S999] does not exist.` / `ID cannot be empty.` |
| 15 | 3 | `s03` → `x` | hỏi ngay câu U/D (ID gõ thường vẫn tìm ra) · `Please enter U or D.` |
| 16 | 3 | `U` → trống, `abc`, `0`, trống | `You must input a number.` → `Semester must be greater than 0.` (không đổi gì) |
| 17 | 3 | `s03` → `U` → tên mới, `5`, `.net` | `Student [S03] has been updated.` (in ID **đã lưu**) |
| 18 | 3 | `U` → trống, trống, `Wrong` | `Course must be one of: ...` và tên **không** đổi |
| 19 | 3 | `D` | `Student [S002] has been deleted.` |
| 20 | 4 | — | `Nguyen Van A \| Java \| 2` … sắp theo tên rồi course |

---

## 6. Debug

| Việc | Cách làm |
|---|---|
| Breakpoint | dòng `int byName = ...` trong `StudentNameComparator.compare` |
| Chạy | **Ctrl+F5**, tạo 10 người, chọn 2, gõ `van` |
| Quan sát | tab **Variables**: `first`, `second`, `byName` — mỗi lần dừng là một lần `Collections.sort` hỏi "ai trước?" |
| Bước | Ở `StudentService.findAndSort` đặt breakpoint `Collections.sort(foundList, sortStrategy);` rồi **F7** → nhảy vào `compare` của `StudentNameComparator` (đa hình qua interface) |
| Report | breakpoint `reportItem.increaseTotal();` — thấy total của `Nguyen Van A · Java` lên 2 |
| Create | breakpoint `controller.createStudents(requestDTO);` trong `Main.main` — xem `requestDTO.studentList` đã đủ 10 người trước khi gọi |

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
| Hàm nào `public`, vì sao? | Chỉ hàm lớp khác gọi: hàm của controller (Main gọi), service (controller gọi), repository (service gọi), `setResponseDTO`/`display` của view (controller gọi), getter/setter. `checkNotEmpty`, `requireStudent`, `isTypedBefore`, `checkSemester`, `parseCourse` là `private` — chỉ `StudentService` dùng. Hàm nhập trong `Main` đều `private static`. |
| Mọi field `private`? | Có — kể cả `sortStrategy`, `studentRepository`, `responseDTO`; không lớp nào đổi được chúng từ ngoài. |
| `static` ở đâu? | Chỉ 3 chỗ: `Validation` (Guide *"phải dùng static method"* — không dùng dữ liệu đối tượng), hằng trong `Message/Constants` + `Course.findByLabel/joinLabels` (làm việc trên cả enum, không trên một hằng), và **hàm** trong `Main`. |
| **Bỏ `static` ở `Validation` thì sao?** | `Validation.getInt(...)` báo lỗi biên dịch. Phải bỏ `private` constructor, tạo `Validation v = new Validation();` trong `main()` rồi truyền `v` vào các hàm nhập. |
| **Bỏ `static` ở hàm của `Main`** | `main` là static nên không gọi được hàm thường; phải `new Main()` — mà constructor đang `private` (lớp chỉ có hàm static, tờ checklist 3.4). |
| **Sao `ArrayList` mà không `List`?** | `List` là **interface** (hợp đồng: `add/get/remove`); `ArrayList` là **lớp cài đặt** bằng mảng động — lấy theo chỉ số O(1), chèn/xoá giữa phải dời phần tử. `LinkedList` cài cùng hợp đồng bằng nút liên kết. Đề bắt `ArrayList`, và `updateStudent` dùng `set(i, ...)` theo chỉ số — nên em khai báo đúng kiểu cụ thể. |
| Sao `Comparator<Student>` lại khai báo kiểu interface? | Đó **chính là** Strategy: service phải nhận **bất kỳ** thứ tự nào. Luật "kiểu cụ thể" của thầy nói về collection (List/Map), không phải interface pattern. |
| `findById` trả `null`? | "Không thấy" là câu trả lời bình thường của kho; service biến nó thành `ID [..] does not exist.` |
| Service trả gì cho controller? | **Chữ**, không trả model: `ArrayList<String>` (dòng bảng / dòng report / câu "added"), `String` (câu updated/deleted). Chữ của một dòng do `toString()` của model dựng (Guide: *"cần output gì thì thêm hàm toString()"*). |
| Hàm controller trả `void`? | Kết quả đã sang view; lỗi đi bằng `throw`. Riêng `countStudents()` trả `int`: nó **chỉ đọc** số sinh viên đã lưu cho Main đếm, không phải kết quả để hiển thị. |

### Kiến trúc & thuật toán

| Câu hỏi | Trả lời mẫu |
|---|---|
| **Sao bài có repository?** | Tờ checklist 1.1: *"Bắt buộc phải có repository"*. `StudentRepository` giữ **dữ liệu** (`ArrayList<Student> studentList`) và CRUD đơn giản; luật + tìm + sắp + report nằm ở `StudentService` (Guide: *"tính toán nghiệp vụ ngoài CRUD thì cần thêm Services"*). |
| **View nhận dữ liệu thế nào?** | Qua **thuộc tính**: `StudentView` có `private StudentResponseDTO responseDTO` + `setResponseDTO(...)`; `display()` **không tham số**, in `message` nếu có, rồi `messageList`, bảng `searchRowList` (có dòng tiêu đề), `reportRowList`. Mỗi hàm luồng của controller gọi `setResponseDTO` rồi `display()` **đúng 1 lần**. Bản trước có `showMessage(String)` và 3 hàm `display…` — đã bỏ. |
| **Validate ở đâu?** | **Dạng** dữ liệu (có phải số không, menu 1–5, Y/N, U/D) ở `Main` qua `utils/Validation` — sai thì hỏi lại ngay ô đó. **Luật của đề** (ID trống/trùng/không có, tên trống, semester > 0, chỉ 3 course) ở `StudentService` — cần dữ liệu trong repository, nên hiện **sau khi nhập đủ 4 ô**. |
| **Mỗi case gọi controller mấy lần?** | **1 lần** (Guide: *"Mỗi workflow chính (Create student, …) chỉ gọi vào controller 1 lần duy nhất"*): case 1 `createStudents`, case 2 `findAndSort`, case 3 `updateOrDeleteStudent`, case 4 `report`. Thêm các lần gọi **không render, không ghi** ở chỗ đề bắt: `countStudents` (**chỉ đọc**, 1 lần ở đầu luồng Create: đề *"if number of students greater than 10"* đếm **cả danh sách** = đã lưu + đang nhập), `checkStudent` (**chỉ kiểm**, mỗi sinh viên: phải biết ngay sinh viên vừa gõ có hợp lệ không mới đếm đúng) và `checkExistStudent` (**chỉ kiểm**: đề *"find a student by ID. **After** finding … a question is displayed"* — tìm thấy rồi mới hỏi U/D). Các lần kiểm chỉ **ném lỗi**; không lần nào in, lưu hay sửa gì. |
| Sao câu `Student [..] has been added.` hiện **sau** câu N? | Tờ checklist 1.1: *"rendering … chỉ được gọi 1 lần cho 1 luồng xử lý (mỗi luồng = 1 switch-case)"*. Create là **một** luồng, nên sinh viên được lưu và báo **một lần** ở cuối. Câu *"At least 10 … so far."* là câu **nhắc** của vòng nhập nên `Main` in (như `Please enter Y or N.`). |
| Sao Update/Delete không in lại dòng của sinh viên tìm thấy? | In dòng đó là một lần render **trước** câu hỏi U/D — lần thứ hai trong cùng luồng. Lần gọi kiểm ID không render; câu nhắc ghi `(blank to keep the old one)`. |
| Sao lỗi Create hiện **sau** khi gõ xong 4 ô? | Luật sinh viên (trùng ID, tên trống, semester > 0, course) nằm ở **service**, kiểm cùng lúc; chỉ "semester phải là số" là lỗi **dạng** nên `Validation` bắt ngay. |
| Độ phức tạp? | Tìm theo ID O(n) (duyệt ArrayList); `Collections.sort` O(n log n); report O(n) gom + O(k log k) sắp. |
| Sao không có hàm nào 3 tham số? | Thầy: *"không truyền 3 tham số 1 hàm"* → dữ liệu gói vào `StudentRequestDTO`. Chỉ `Validation.getChoice/getOption` 3 tham số (đúng mẫu Guide `getChoice(input, min, max)`) và constructor. |

### Tờ checklist 25 mục — bài này đạt thế nào

| Mục | Chỉ vào đâu |
|---|---|
| **1.1** MVC + repository | `repository/StudentRepository`; `StudentController` không import `model`; `StudentView` nhận `responseDTO` qua setter, `display()` không tham số, gọi **1 lần/luồng**; mỗi `case` trong `Main.main` gọi controller **1 lần** (+ `countStudents` chỉ để đọc, `checkStudent`, `checkExistStudent` chỉ để kiểm — xem câu "Mỗi case gọi controller mấy lần?") |
| **1.3 / 1.4** tên lớp, tên hàm | `IStudentRepository` bắt đầu bằng `I`; hàm mở đầu bằng động từ: `findByLabel`, `joinLabels` (bản trước `fromLabel`, `labels`), `checkStudent`, `createStudents`, `updateOrDeleteStudent` |
| **1.5** tên collection / map / Id | `studentList` (bản trước `students`), `foundList`, `rowList`, `messageList`, `searchRowList`, `reportRowList`, `reportItemList`, `reportMap` (bản trước `groups`); `getId/setId` |
| **2.6 / 3.7** khai báo đầu block + khởi tạo | `Main.main`: `requestDTO = null`, `running = true`, `choice = 0`; `Main.inputX`: `String line = ""`, trong vòng lặp chỉ gán; `Main.inputStudentList`: `storedCount = 0`, `count = 0` rồi mới gán; `StudentService.report`: 3 danh sách ở đầu hàm, `key`/`reportItem` ở đầu khối `for`; `requireStudent`: `Student student = null` |
| **2.8** dòng trống | giữa các field/hằng enum, sau vùng khai báo biến, trước mọi comment đứng sau dòng code, giữa các `case`, sau `}` trước câu lệnh tiếp (kể cả `return`) |
| **3.3** ngoặc | `Validation.getChoice`: `if ((choice < min) \|\| (choice > max))`; `StudentService.checkStudent`: `if ((studentRepository.findById(id) != null) \|\| isTypedBefore(requestDTO))` |
| **3.4** lớp chỉ có static | `Main` (`final` + `private Main()`), `Validation`, `Constants`, `Message` |
| **3.8** cộng chuỗi | `Student.toString()`, `ReportItem.toString()`, khoá report dùng `String.format(Constants.…_FORMAT, …)`; `Course.joinLabels` dùng `StringBuilder` |

---

## 8. Thầy đổi yêu cầu tại chỗ

| Thầy bảo | Sửa file | Không phải đụng |
|---|---|---|
| Sắp theo semester / giảm dần | thêm 1 Comparator trong `service/` + 1 dòng constructor `StudentController` | `StudentService`, `Main`, `View` |
| Thêm course `Python` | 1 hằng `PYTHON("Python")` trong `Course` + `Message.INPUT_COURSE` | service, repository |
| Đổi 10 thành 5 | `Constants.MIN_STUDENTS` | mọi file khác |
| Lưu vào file | lớp mới `FileStudentRepository implements IStudentRepository` + 1 dòng controller | `StudentService` |
| Thêm cột Semester vào report | `ReportItem` (field + `toString`) + khoá gom trong `StudentService.report` + `REPORT_FORMAT` | `Main`, `Controller`, `View` |
| Không cho sửa trùng tên+course | thêm kiểm trong `StudentService.updateStudent` + 1 `Message` | view, main |

---

## 9. Chỗ khác với đề / bản cũ

| Chỗ | Đề / bản cũ | Bài này | Lý do |
|---|---|---|---|
| Hỏi Y/N khi nào | đề: *"greater than 10"* | hỏi **từ người thứ 10** | đề cũng nói *"at least 10"*; giữ đúng bản tham chiếu đã kiểm |
| Menu | đề in `- Create`… (mất số) | `1. Create` … + câu `(Please choose 1 to Create, …)` | số là thứ người dùng gõ; câu cuối chép đúng đề |
| Prompt, thông báo lỗi | đề không ghi | giữ **y nguyên** chữ của bản tham chiếu | quy tắc bộ lời giải |
| Kiến trúc | bản cũ: `entity/bo/ui`, Scanner static trong `Validator`, controller in ra | MVC Guide: Scanner chỉ ở `main`, in ở `view`, luật ở `service`, CRUD ở `repository` | luật thầy |
| `Course` | bản cũ: `entity.Course` | `constants.Course` | Guide: enum ở Constants |
| Report | bản cũ: lớp `CourseCount` + comparator ẩn danh | `ReportItem` (model) + `ReportComparator` (lớp có tên) | pattern Strategy rõ ràng, lint bắt comment từng hàm |
| Kho | bản cũ: `List<Student>` | `ArrayList<Student> studentList` sau interface `IStudentRepository` | thầy: kiểu cụ thể; bài Long → DIP |
| Create: câu *"added"* | bản 14/09: in ngay sau từng sinh viên (render mỗi người 1 lần) | Main gom sinh viên đã qua `checkStudent`; **1 lần** `createStudents` sau câu N → in các câu *"added"* cùng lúc | tờ giấy 1.1 (render 1 lần/luồng) + Guide (*"Create student … gọi controller 1 lần"*); test đặt `REPLACE_REFERENCE = True`, chép lại 3 kịch bản tham chiếu với cùng phím gõ |
| Create: đếm 10 | bản 14/09: controller đếm và in (`checkEnoughStudents`), **cả danh sách** | vẫn đếm **cả danh sách** (đã lưu + đang nhập): Main lấy số đã lưu bằng **1 lần gọi chỉ đọc** `countStudents()` ở đầu luồng, cộng `studentList.size()`; lượt Create sau (đã có ≥ 10) hỏi Y/N ngay sau mỗi sinh viên mới | đề: *"if number of students greater than 10"* — số sinh viên là của danh sách; không render thêm lần nào (tờ giấy 1.1) |
| Update/Delete | bản 14/09: in bảng 1 dòng của sinh viên tìm thấy rồi mới hỏi U/D; câu nhắc `(blank to keep Tran Van An)` | kiểm ID bằng lần gọi **chỉ để kiểm** (không in); câu nhắc `(blank to keep the old one)` | bảng đó là lần render thứ hai trong cùng luồng (tờ giấy 1.1); Main không nhận model/dữ liệu cũ |
| View | bản 14/09: `setStudentList/setStudent/setReportList` + `displaySearchResult/displayStudent/displayReport` + `showMessage(String)` | `setResponseDTO(StudentResponseDTO)` + `display()` duy nhất | tờ giấy 1.1: view nhận qua **thuộc tính (ResponseDTO)** |
| DTO | bản 14/09: `StudentResponseDTO` (1 sinh viên) + `ReportResponseDTO` (1 dòng report) | **một** `StudentResponseDTO` (`message`, `messageList`, `searchRowList`, `reportRowList`); chữ của dòng nằm ở `toString()` của model | khuôn chung của kho (P0056) |
| Service | bản 14/09: trả `StudentResponseDTO`/`ReportResponseDTO`, có `countStudents`, `findStudent` | trả **chữ**; `checkStudent`, `createStudents`, `checkExistStudent`, `updateStudent`/`deleteStudent` trả câu kết quả | controller chỉ gói vào ResponseDTO |
| Tên | `fromLabel`, `labels`, `students`, `groups`, `found`, `result`, `KEY_SEPARATOR` | `findByLabel`, `joinLabels`, `studentList`, `reportMap`, `foundList`, `rowList`, `REPORT_KEY_FORMAT` | tờ giấy 1.4 (method mở đầu bằng động từ), 1.5 (đuôi List/Map), 3.8 (không cộng chuỗi) |
| `Main` | `public class Main` | `public final class Main` + `private Main()` | tờ giấy 3.4 |
