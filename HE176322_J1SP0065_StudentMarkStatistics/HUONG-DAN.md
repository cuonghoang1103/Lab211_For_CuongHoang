# J1.S.P0065 — Student Mark Statistics (Check data format)

> Nhập nhiều sinh viên, xếp loại A/B/C/D, thống kê %. Bài có **hai pattern hợp tự nhiên**:
> **Builder** (Student có 7 thuộc tính) và **Strategy** (luật xếp loại có thể đổi). Danh sách sinh viên
> nằm ở **repository** (tờ checklist 1.1: *"Bắt buộc phải có repository"*).

| | |
|---|---|
| Loại / LOC | Short Assignment · 70 LOC · 1 slot |
| Project | `HE176322_J1SP0065_StudentMarkStatistics` |
| Chạy | NetBeans: **File ▸ Open Project** → **F6** |
| Lớp chạy | `main.Main` |
| Kiểm tự động | `python3 _tools/verify.py J1SP0065` → 5 kịch bản × 2 locale |

---

## 1. Đề bài nói gì

- Nhập sinh viên: `Name`, `Classes`, điểm `Maths`, `Chemistry`, `Physics` (mỗi điểm **0–10**).
- Sau mỗi sinh viên hỏi `Do you want to enter more student information?(Y/N):`.
- Trả lời `N` → in từng sinh viên (Name, Classes, **AVG**, **Type**) rồi **thống kê % từng loại** và thoát.
- Loại: **A** > 7.5 · **B** 6–7.5 · **C** 4–<6 · **D** < 4.

Màn hình đề — dòng kết quả đầu tiên đứng **ngay sau** dòng trả lời `N`, **không có dòng trống**:

```
Do you want to enter more student information?(Y/N):N
------ Student1 Info ------
Name:Nghia
```

**Đề bắt buộc:**

| Thứ | Đề viết | Bài này đặt ở |
|---|---|---|
| `Student createStudent(name, classes, maths, chemistry, physics)` | 5 tham số | `StudentService.createStudent(StudentRequestDTO)` — **giữ tên, gói vào DTO**; comment `// brief:` chép nguyên chữ ký đề |
| `List<Student> averageStudent(List<Student> students)` | | `StudentService` — **giữ nguyên kiểu trả về + kiểu tham số** (comment `// brief:`); tên tham số `studentList` (tờ checklist 1.5) |
| `HashMap<String, Double> getPercentTypeStudent(List<Student> students)` | khoá A, B, C, D | `StudentService` — **giữ nguyên kiểu**; tham số `studentList` |
| 9 thông báo | `Maths is less than equal ten` · `Maths is greater than equal zero` · `Maths is digit` (và Chemistry/Physics) | `Message.MARK_TOO_BIG/SMALL/NOT_DIGIT` với `%s` = môn |
| Lớp `Student` 7 thuộc tính | Name, Class, Math, Physical, Chemistry, Average, Type | `model/Student` |

---

## 2. Kiến thức cần biết

### 2.1 Làm tròn **trước** khi xếp loại

`average = convertToOneDecimal((maths + chemistry + physics) / 3)` với `convertToOneDecimal(x) = Math.round(x * 10) / 10.0`.

| Điểm | Trung bình thật | AVG in ra | Loại | Vì sao |
|---|---|---|---|---|
| 10, 10, 10 | 10.0 | `10.0` | A | > 7.5 |
| 7.6, 7.6, 7.6 | 7.6 | `7.6` | A | > 7.5 |
| 7.5, 7.5, 7.5 | 7.5 | `7.5` | **B** | 7.5 **không** > 7.5 |
| 7.4, 7.6, 7.6 | 7.533… | `7.5` | **B** | xếp theo số **đã làm tròn** — khớp với số in ra |
| 3.96 ×3 | 3.96 | `4.0` | C | làm tròn lên 4.0 |
| 3.9 ×3 | 3.9 | `3.9` | D | < 4 |

### 2.2 Thống kê %

`percent = (số SV loại X × 100) / tổng SV`, rồi làm tròn 1 chữ số. 3 SV loại A, B, D → mỗi loại `33.3%`, C `0.0%`.
**Bốn khoá luôn có mặt** (kể cả 0), danh sách rỗng → bốn số 0, không chia cho 0.

### 2.3 Java dùng trong bài

| API | Dùng làm gì |
|---|---|
| `Double.parseDouble(s)` | chuỗi → điểm; chuỗi rỗng/chữ → `NumberFormatException` → `"Maths is digit"` |
| `String.format(Locale.US, "%.1f", x)` | in `10.0`, `33.3` — Locale.US để máy tiếng Việt không in `10,0` |
| `String.format("Name:%s", …)` | ghép nhãn + giá trị, không cộng chuỗi (tờ checklist 3.8) |
| `HashMap<String, Double>` | kiểu trả về đề bắt; **không có thứ tự** → view in theo mảng cố định `Constants.TYPE_ARRAY` |
| `do { … } while (…)` | nhập ít nhất 1 sinh viên rồi mới hỏi Y/N |

---

## 3. Thiết kế

```
HE176322_J1SP0065_StudentMarkStatistics/src/
├── model/      Student                        7 thuộc tính (JavaBean)
│               StudentBuilder                 Builder: setName().setMaths()...build()
├── repository/ StudentRepository              GIỮ ArrayList<Student> studentList: addStudent (Create) · getStudentList (Read)
├── dto/        StudentRequestDTO              5 ô 1 sinh viên đã gõ
│               ReportRequestDTO               ArrayList<StudentRequestDTO> studentList   (main ──► controller)
│               StudentResponseDTO             4 dòng "Student Info" của 1 sinh viên
│               ReportResponseDTO              studentList + HashMap percentMap           (controller ──► view)
├── service/    StudentService                 "Mark Calculation": createStudent, averageStudent, getPercentTypeStudent
│               IClassificationStrategy        «interface» classify(average)
│               StandardClassificationStrategy 4 mức A/B/C/D của đề
├── controller/ StudentController              cắm strategy vào service; setResponseDTO + display() 1 lần
├── view/       StudentView                    field responseDTO; display() in các khối + thống kê
├── constants/  Message, Constants             câu chữ; ngưỡng 7.5/6/4, TYPE_ARRAY
├── utils/      Validation                     getMark (3 lỗi), isYesOrNo, isYes — final + ctor private
└── main/       Main (final, ctor private)     vòng nhập + Scanner + validate; gọi controller 1 lần
```

| Câu hỏi thiết kế | Trả lời |
|---|---|
| Sao bài có `repository`? | Tờ checklist 1.1: *"**Bắt buộc phải có repository**"*. Repository = **dữ liệu** + CRUD đơn giản: danh sách sinh viên đã nhập (`ArrayList<Student> studentList`), `addStudent` / `getStudentList`. Xếp loại, thống kê là *"tính toán nghiệp vụ, report"* → `StudentService` — đúng tầng *Controller ↔ Services ↔ Repository ↔ Model*. |
| Main gọi controller mấy lần? | **Một lần**: `controller.classifyStudents(requestDTO)` sau khi nhập xong. Main gom các `StudentRequestDTO` vào `ReportRequestDTO` (Guide: *"Truyền data vào controller thông qua DTO param"*). |
| Controller có đụng model không? | Không. Controller chỉ import DTO, service, view. Service tạo `Student` (Builder), cất vào repository, xếp loại, rồi chép sang `StudentResponseDTO` cho view. |
| Sao không in dòng trống trước kết quả? | Prompt Y/N in bằng `print`; người dùng gõ `N` + **Enter** là đã xuống dòng trên console. In thêm `println()` sẽ ra **một dòng trống** mà màn hình đề không có. |

**Luồng:**

```
Main: do { inputStudent(sc) → studentList.add } while (inputMore(sc))
      requestDTO.setStudentList(studentList); controller.classifyStudents(requestDTO)       (gọi controller 1 lần)
         service.makeReport(requestDTO)
            ├─ repository.addStudent(createStudent(studentDTO))   (Builder) × n
            ├─ averageStudent(repository.getStudentList())         AVG + Type qua strategy.classify
            ├─ convertToResponse(student)                          × n
            └─ getPercentTypeStudent(...)                          HashMap A/B/C/D
         view.setResponseDTO(responseDTO); view.display()                                   (render 1 lần)
```

Tầng: **Main → RequestDTO → Controller → Service → Repository → Model**; kết quả **ResponseDTO → View**, render 1 lần.

### 3.1 Design Pattern

**Builder** (Creational)

| Yếu tố | Trong bài này |
|---|---|
| **Problem** | `Student` có **7 thuộc tính**; constructor 5–7 tham số dễ truyền nhầm (ba `double` liền nhau: maths hay physics?) và trái tinh thần luật "không truyền nhiều tham số". |
| **Solution** | `StudentBuilder` = **Builder** (các hàm `setName`, `setClasses`, `setMaths`, `setChemistry`, `setPhysics` trả lại chính builder, `build()` trả `Student`). `Student` = **Product**. `StudentService.createStudent` = **Director** (gọi các bước theo thứ tự). |
| **Consequences** | ✅ Mỗi giá trị **có tên** ở chỗ gán, thêm thuộc tính không phải sửa chữ ký hàm nào. ❌ Thêm 1 lớp. Builder không `static` (model cấm static) nên là lớp riêng, tạo bằng `new StudentBuilder()`. |

**Strategy** (Behavioral)

| Yếu tố | Trong bài này |
|---|---|
| **Problem** | Thang xếp loại là thứ thầy dễ đổi tại chỗ ("A từ 8.0", "thêm loại E"). Viết cứng trong `averageStudent` thì mỗi lần đổi phải sửa service. |
| **Solution** | `IClassificationStrategy` = **Strategy** (`String classify(double average)`); `StandardClassificationStrategy` = **ConcreteStrategy** (4 mức của đề); `StudentService` = **Context** nhận strategy qua **constructor**; `StudentController` là nơi chọn: `new StudentService(new StandardClassificationStrategy())`. |
| **Consequences** | ✅ Thang mới = **1 class mới + 1 dòng** trong controller (**O**CP, **D**IP). ❌ Thêm 2 file cho một luật đơn giản. |

**Facade**: `StudentController` là một cửa gọn cho `Main`. **MVC (JSP)**: `Student`, DTO là JavaBean.

### 3.2 SOLID

| | Ở đâu |
|---|---|
| **S** | `Student` giữ dữ liệu · `StudentBuilder` tạo · `StudentRepository` cất danh sách · `StudentService` tính · `StudentView` in · `Validation` kiểm |
| **O** | thang xếp loại mới không sửa `StudentService` |
| **L** | mọi `IClassificationStrategy` thay nhau được |
| **I** | interface 1 hàm |
| **D** | `StudentService` phụ thuộc `IClassificationStrategy`, nhận qua constructor |

---

## 4. Code từng bước

> Thầy: *"code cái **model trước rồi đến data**"*.

| Bước | File | Việc |
|---|---|---|
| 1 | `model/Student.java` | 7 field `private` + ctor rỗng + get/set + `toString` (`String.format`) |
| 2 | `model/StudentBuilder.java` | field `student`; 5 hàm `setX` `return this;`; `build()` |
| 3 | `repository/StudentRepository.java` | `ArrayList<Student> studentList`; `addStudent`, `getStudentList` |
| 4 | `dto/` 4 lớp | `StudentRequestDTO` (5 ô), `ReportRequestDTO` (danh sách), `StudentResponseDTO` (4 dòng), `ReportResponseDTO` (`ArrayList` + `HashMap`) |
| 5 | `service/IClassificationStrategy` + `StandardClassificationStrategy` | bậc thang `if` từ trên xuống |
| 6 | `service/StudentService.java` | `makeReport`, `createStudent`, `averageStudent`, `getPercentTypeStudent`, `convertToResponse`, `convertToOneDecimal` |
| 7 | `constants/` | 9 thông báo **chép đúng** (dùng `%s`), nhãn `Name:%s`…, ngưỡng, `TYPE_ARRAY` |
| 8 | `view/StudentView.java`, `controller/StudentController.java` | field `responseDTO` + `display()`; cắm strategy; `setResponseDTO` rồi `display()` |
| 9 | `utils/Validation.java` | `getMark(input, subject)` — **kiểm "là số" trước, rồi > 10, rồi < 0** |
| 10 | `main/Main.java` | `do/while`, `inputMark`, `inputMore`; gói `ReportRequestDTO`, gọi controller 1 lần |

**Bẫy hay gặp:**

1. Kiểm khoảng **trước** khi parse → dòng trống không ra `"Maths is digit"`.
2. `/ 10` (int) thay vì `/ 10.0` → mất phần thập phân. Bài dùng `Constants.ROUND_FACTOR = 10.0`.
3. Duyệt `HashMap` để in → thứ tự A/B/C/D **không được đảm bảo**. In theo `Constants.TYPE_ARRAY`.
4. Prompt Y/N dùng `print` → **đừng** `println()` thêm trước kết quả: `N` + Enter người dùng gõ đã xuống dòng; in thêm là ra dòng trống mà màn hình đề không có.

---

## 5. Test trước khi gọi thầy

| # | Gõ | Phải thấy |
|---|---|---|
| 1 | Maths `11` | `Maths is less than equal ten` rồi hỏi lại |
| 2 | Maths `-1` | `Maths is greater than equal zero` |
| 3 | Maths để trống / `abc` | `Maths is digit` |
| 4 | như 1–3 cho Chemistry, Physics | 6 thông báo còn lại, đúng tên môn |
| 5 | Y/N gõ `maybe` | hỏi lại, **không** in lỗi (đề không có thông báo) |
| 6 | `y`, `n` chữ thường | được nhận |
| 7 | 10/10/10 | `AVG:10.0` `Type:A` |
| 8 | 7.5/7.5/7.5 | `AVG:7.5` `Type:B` |
| 9 | 7.4/7.6/7.6 | `AVG:7.5` `Type:B` |
| 10 | 6/6/6 · 4/4/4 · 0/0/0 | B · C · D (biên) |
| 11 | 4 SV đủ 4 loại | `A: 25.0%` … `D: 25.0%` |
| 12 | 6 SV (1A, 2B, 2C, 1D) | `A: 16.7%` `B: 33.3%` `C: 33.3%` `D: 16.7%` |
| 13 | trả lời `N` | `------ Student1 Info ------` hiện **ngay dòng dưới**, không có dòng trống |

---

## 6. Debug

| Việc | Cách làm |
|---|---|
| Breakpoint | dòng `student.setType(classificationStrategy.classify(average));` trong `averageStudent` |
| Chạy | **Ctrl+F5**, nhập 7.4/7.6/7.6 |
| Quan sát | `sum` = 22.6, `average` = **7.5** (đã làm tròn); `studentList` chính là danh sách repository đang giữ |
| Bước | **F7** vào `classify` → nhảy vào `StandardClassificationStrategy`; **F8** thấy nhánh `>= TYPE_B_FROM` trả `"B"` |
| Thống kê | breakpoint vòng `for (String type : Constants.TYPE_ARRAY)` cuối `getPercentTypeStudent`, xem `percentMap` đổi từ số đếm sang % |

---

## 7. Câu hỏi thầy hay hỏi

### OOP

| Câu hỏi | Trả lời mẫu |
|---|---|
| 4 tính chất OOP? | **Đóng gói**: 7 field `private` của `Student`; `studentList` `private` trong `StudentRepository`. **Kế thừa**: `StandardClassificationStrategy implements IClassificationStrategy`; ghi đè `toString()` của `Object`. **Đa hình**: `classificationStrategy.classify(average)` chạy bản của lớp thật. **Trừu tượng**: interface chỉ nói "xếp loại được"; `Main` không biết có service. |
| Builder khác constructor? | Constructor nhận mọi thứ **một lần, theo vị trí**; builder nhận **từng giá trị có tên**, gọi nối chuỗi. |

### Access modifier / static / kiểu

| Chỗ | Vì sao |
|---|---|
| field | `private` hết |
| get/set, `setX` của builder, `build` | `public` — service (package khác) gọi |
| `StudentRepository.addStudent/getStudentList` | `public` — `StudentService` (package `service`) gọi |
| `StudentService.makeReport` | `public` — controller gọi |
| `createStudent`, `averageStudent`, `getPercentTypeStudent` | **`private`** — chỉ `makeReport` của chính service gọi; **tên + kiểu giữ đúng đề** (comment `// brief:`). `convertToResponse`, `convertToOneDecimal` cũng `private` |
| `classify` | `public` — hàm interface |
| `StudentController.classifyStudents`, `StudentView.setResponseDTO/display` | `public` — `Main`/controller gọi; `formatOneDecimal` **private** |
| `Validation.*` | `public static` — utils *"phải dùng static method"* |
| hàm trong `Main` | `private static` — chỉ `main` gọi, `main` static |
| `Constants.TYPE_ARRAY` | `public static final` mảng — một bản dùng chung cho service và view |

| Câu hỏi | Trả lời mẫu |
|---|---|
| Sao bài có repository? | Tờ checklist 1.1 *"Bắt buộc phải có repository"*. `StudentRepository` giữ **dữ liệu** — danh sách sinh viên đã nhập — chỉ `addStudent`/`getStudentList`, không tính, không in. `makeReport` cất từng `Student` vào đó rồi `averageStudent` làm việc trên **chính danh sách repository giữ**. |
| View nhận dữ liệu thế nào? | Qua **thuộc tính**: controller gọi `studentView.setResponseDTO(responseDTO)` rồi `studentView.display()` — `display()` **không tham số**, gọi **1 lần** cho cả luồng. |
| Validate ở đâu? | Ở `Main` qua `utils/Validation` (`getMark`, `isYesOrNo`): điểm sai thì `Validation` ném `Exception(String.format(Message.MARK_…, môn))`, `Main` bắt, in `e.getMessage()` rồi hỏi lại ô đó. Controller/service chỉ nhận điểm đã hợp lệ trong `ReportRequestDTO`. |
| Sao interface tên `IClassificationStrategy`? | Tờ checklist 1.3: *"Tên của interface bắt đầu bằng I"*. |
| Sao builder là `setName` chứ không `withName`? | Tờ checklist 1.4: *"Tên method bắt đầu bằng động từ"* — `with` không phải động từ. Vẫn là builder: mỗi `setX` trả `this` để gọi nối. |
| Sao tham số là `studentList`, đề viết `students`? | Tờ checklist 1.5: *"tên biến kiểu collection kết thúc bằng List"*. Chữ ký Java chỉ gồm **tên hàm + kiểu tham số** → `averageStudent(List<Student>)` vẫn y đề; comment `// brief:` chép nguyên chữ ký đề. |
| **Bỏ `static` ở `Validation.getMark`?** | Lỗi biên dịch ở `Validation.getMark(...)`. Phải bỏ `private` constructor, `new Validation()` trong `Main`, gọi qua đối tượng. |
| `averageStudent` trả `List<Student>` dù sửa ngay trên list? | **Chữ ký đề bắt** (comment `// brief:`). Nó trả lại chính list đã xếp loại. |
| **Sao chỗ khác dùng `ArrayList` mà đây `List`?** | Thầy dặn khai báo kiểu cụ thể. `List` là interface, `ArrayList` là lớp cài bằng mảng động. Chỉ 2 hàm đề **bắt** chữ ký `List` (và biến `classifiedList` nhận kết quả của nó) nên giữ, còn lại (`ArrayList<Student> studentList` trong repository) là kiểu cụ thể. |
| `getPercentTypeStudent` trả `HashMap<String, Double>`? | Đề bắt; `Double` (lớp bọc) vì Map chỉ chứa đối tượng, không chứa `double`. |
| `getMark` trả `double`? | Điểm có thể 7.5. |
| `isYes` trả `boolean`? | Chỉ cần có/không. |
| Sao `Main` là `final` và có `private Main() { }`? | Tờ checklist 3.4: *"Class chỉ có static method thì phải có private constructor, và khai báo class là final"*. |

---

## 8. Thầy đổi yêu cầu tại chỗ

| Thầy bảo | Sửa | Không đụng |
|---|---|---|
| A khi > 8.0 | `Constants.TYPE_A_ABOVE` | mọi file khác |
| Thang khác hẳn (5 loại) | lớp mới `implements IClassificationStrategy` + 1 dòng trong `StudentController`; thêm khoá vào `Constants.TYPE_ARRAY` | `StudentService.averageStudent`, repository |
| Thêm môn Biology | `Student`, `StudentBuilder.setBiology`, `StudentRequestDTO`, `Constants.NUMBER_OF_SUBJECTS`, `Message.BIOLOGY`, `Main.inputStudent`, dòng cộng `sum` | controller, view, strategy, repository |
| Không cho tên trống | `Validation.getNonBlank` + `Message` + vòng hỏi lại trong `Main` | service, view, repository |

---

## 9. Chỗ khác với đề / bản cũ

| Chỗ | Đề / bản cũ | Bài này | Lý do |
|---|---|---|---|
| `createStudent(5 tham số)` | 5 tham số | `createStudent(StudentRequestDTO)` | luật V4, giữ tên |
| Khoảng điểm | Đặc tả: *"from 1 to 10"* | **0–10** | thông báo của đề: *"greater than equal zero"* — màn hình/Guidelines thắng câu mô tả |
| Chữ in `AVG` / `%` | bản cũ in `Double.toString` | `String.format(Locale.US, "%.1f")` | cùng kết quả sau khi làm tròn 1 chữ số; không phụ thuộc locale |
| Lớp `Mark Calculation` | tên gợi ý | `StudentService` | vai service theo Guide |
| Y/N sai | đề không nói | hỏi lại im lặng | không bịa thông báo không có trong đề |
| Kiến trúc | `entity/bo/ui`, Scanner trong `Validator` | MVC Guide + **Builder** + **Strategy** | luật thầy (V7) |
| Dòng trống trước kết quả | bản trước: `System.out.println()` sau câu Y/N | **bỏ** — kết quả in ngay | màn hình đề không có dòng trống; `N` + Enter đã xuống dòng. `_tools/tests/J1SP0065.py` đặt `REPLACE_REFERENCE = True` (bản tham chiếu có `\n` thừa) |
| Repository | Bản trước: *"không lưu giữ lâu, không CRUD → không repository"* | có `StudentRepository` giữ `ArrayList<Student> studentList` | tờ checklist 1.1: *"Bắt buộc phải có repository"* |
| Controller nhận | `classifyStudents(ArrayList<StudentRequestDTO> requestList)` | `classifyStudents(ReportRequestDTO requestDTO)` | tờ checklist 1.1: *"Controller nhận input từ main qua DTO"* |
| View | `setReport(report)` | field `responseDTO` + `setResponseDTO` + `display()` | tờ checklist 1.1 (nhận qua thuộc tính, render 1 lần) |
| Tên | `ClassificationStrategy`, `withName…`, `students`, `TYPES`, `round`, `toResponse`, `oneDecimal`, `class Main` | `IClassificationStrategy`, `setName…`, `studentList`, `TYPE_ARRAY`, `convertToOneDecimal`, `convertToResponse`, `formatOneDecimal`, `final class Main` + `private Main()` | tờ checklist 1.3 (interface `I`), 1.4 (động từ), 1.5 (`List`/`Array`), 3.4 |
| Access 3 hàm đề | `public` | `private` | chỉ `makeReport` gọi (luật V2: `public` chỉ khi lớp khác gọi) |
| Nối chuỗi | `Message.LABEL_NAME + student.getName()`, `name + " (" + …` | `String.format(Message.LABEL_NAME, …)` với `"Name:%s"`; `String.format(Constants.STUDENT_FORMAT, …)` | tờ checklist 3.8; chữ in ra y hệt |
| Khai báo | `String line = sc.nextLine();` trong vòng lặp, `double sum = …` trong `for`, `double mark;` chưa khởi tạo | khai báo ở đầu block + khởi tạo, trong vòng lặp chỉ gán | tờ checklist 2.6, 3.7 |
| Ngắt dòng `\|\|` | xuống dòng **trước** `\|\|` | viết trên một dòng (95 ký tự) | tờ checklist 2.3: ngắt **sau** toán tử logic |

---

## 10. Tờ checklist 25 mục — bài này đạt thế nào

| Mục | Chỗ trong code |
|---|---|
| 1.1 MVC + repository | `repository/StudentRepository` giữ `ArrayList<Student> studentList`; `StudentService.makeReport` cất (`addStudent`) rồi xếp loại trên `getStudentList()`; controller nhận **một** `ReportRequestDTO`, chỉ import DTO/service/view; `StudentView` nhận `responseDTO` qua `setResponseDTO`, `display()` gọi **1 lần**; mọi nhập + validate ở `Main` |
| 1.3 | interface `IClassificationStrategy`; lớp là danh từ (`StudentService`, `StudentRepository`, `StudentBuilder`) |
| 1.4 method = động từ | `makeReport`, `createStudent`, `averageStudent`, `convertToResponse`, `convertToOneDecimal`, `formatOneDecimal`, `setName` (builder), `addStudent` |
| 1.5 tên biến | `studentList` (mọi danh sách), `classifiedList`, `percentMap`, `TYPE_ARRAY`; không có `ID` |
| 2.6 + 3.7 khai báo đầu block, có khởi tạo | `Main.inputMark`: `String line = "";`, `inputMore`: `String answer = "";` ở đầu, trong `while` chỉ gán; `averageStudent`: `double sum = 0; double average = 0;`; `StudentView.display`: `studentDTO = null` ở đầu; `Validation.getMark`: `double mark = 0;` |
| 2.8 dòng trống | trước mọi comment (kể cả comment field trong `Constants`, `Message`, DTO, model), sau vùng khai báo, sau `}` của `if`/`for` trước câu lệnh tiếp |
| 3.3 ngoặc | `if ((studentList == null) \|\| studentList.isEmpty())`; `percent = (percentMap.get(type) * Constants.PERCENT) / studentList.size();` |
| 3.4 | `public final class Main` + `private Main() { }`; `Validation`, `Constants`, `Message` cũng `final` + ctor private |
| 3.8 | không cộng chuỗi: `String.format(Message.LABEL_…, …)` |

Kiểm lại: `python3 _tools/verify.py J1SP0065` · `python3 _tools/lint.py HE176322_J1SP0065_*` · `python3 _tools/soat_checklist.py HE176322_J1SP0065_*` → 0 `VI_PHAM`.
`RUI_RO` còn lại chỉ là `String[] args` và tham số setter/constructor trùng tên field (`this.name = name`, `this.classificationStrategy = classificationStrategy`) — kiểu IDE sinh, được chấp nhận.
`man-hinh-chay.txt` đã chạy lại bằng đầu ra thật: trong tệp chụp stdout, `(Y/N):` dính liền `------ Student1 Info ------` vì chữ `N` người dùng gõ không nằm trong stdout (giống P0068); trên console NetBeans thì `N` + Enter hiện ra và kết quả nằm ngay dòng dưới, đúng màn hình đề.
