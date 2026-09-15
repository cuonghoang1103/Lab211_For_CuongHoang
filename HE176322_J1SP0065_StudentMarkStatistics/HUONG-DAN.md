# J1.S.P0065 — Student Mark Statistics (Check data format)

> Nhập nhiều sinh viên, xếp loại A/B/C/D, thống kê %. Bài có **hai pattern hợp tự nhiên**:
> **Builder** (Student có 7 thuộc tính) và **Strategy** (luật xếp loại có thể đổi).

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

**Đề bắt buộc:**

| Thứ | Đề viết | Bài này đặt ở |
|---|---|---|
| `Student createStudent(name, classes, maths, chemistry, physics)` | 5 tham số | `StudentService.createStudent(StudentRequestDTO)` — **giữ tên, gói vào DTO** |
| `List<Student> averageStudent(List<Student> students)` | | `StudentService` — **giữ nguyên chữ ký** (comment `// brief:`) |
| `HashMap<String, Double> getPercentTypeStudent(List<Student> students)` | khoá A, B, C, D | `StudentService` — **giữ nguyên chữ ký** |
| 9 thông báo | `Maths is less than equal ten` · `Maths is greater than equal zero` · `Maths is digit` (và Chemistry/Physics) | `Message.MARK_TOO_BIG/SMALL/NOT_DIGIT` với `%s` = môn |
| Lớp `Student` 7 thuộc tính | Name, Class, Math, Physical, Chemistry, Average, Type | `model/Student` |

---

## 2. Kiến thức cần biết

### 2.1 Làm tròn **trước** khi xếp loại

`average = round((maths + chemistry + physics) / 3)` với `round(x) = Math.round(x * 10) / 10.0`.

| Điểm | Trung bình thật | AVG in ra | Loại | Vì sao |
|---|---|---|---|---|
| 10, 10, 10 | 10.0 | `10.0` | A | > 7.5 |
| 7.6, 7.6, 7.6 | 7.6 | `7.6` | A | > 7.5 |
| 7.5, 7.5, 7.5 | 7.5 | `7.5` | **B** | 7.5 **không** > 7.5 |
| 7.4, 7.6, 7.6 | 7.533… | `7.5` | **B** | xếp theo số **đã làm tròn** — khớp với số in ra |
| 3.96 ×3 | 3.96 | `4.0` | C | làm tròn lên 4.0 |
| 3.9 ×3 | 3.9 | `3.9` | D | < 4 |

### 2.2 Thống kê %

`percent = round(số SV loại X × 100 / tổng SV)`. 3 SV loại A, B, D → mỗi loại `33.3%`, C `0.0%`.
**Bốn khoá luôn có mặt** (kể cả 0), danh sách rỗng → bốn số 0, không chia cho 0.

### 2.3 Java dùng trong bài

| API | Dùng làm gì |
|---|---|
| `Double.parseDouble(s)` | chuỗi → điểm; chuỗi rỗng/chữ → `NumberFormatException` → `"Maths is digit"` |
| `String.format(Locale.US, "%.1f", x)` | in `10.0`, `33.3` — Locale.US để máy tiếng Việt không in `10,0` |
| `HashMap<String, Double>` | kiểu trả về đề bắt; **không có thứ tự** → view in theo mảng cố định `Constants.TYPES` |
| `do { … } while (…)` | nhập ít nhất 1 sinh viên rồi mới hỏi Y/N |

---

## 3. Thiết kế

```
HE176322_J1SP0065_StudentMarkStatistics/src/
├── model/      Student                        7 thuộc tính (JavaBean)
│               StudentBuilder                 Builder: withName().withMaths()...build()
├── dto/        StudentRequestDTO              5 ô 1 sinh viên        (main ──► controller)
│               StudentResponseDTO             4 dòng "Student Info"  (controller ──► view)
│               ReportResponseDTO              danh sách SV + HashMap %
├── service/    StudentService                 "Mark Calculation": createStudent, averageStudent, getPercentTypeStudent
│               ClassificationStrategy         «interface» classify(average)
│               StandardClassificationStrategy 4 mức A/B/C/D của đề
├── controller/ StudentController              cắm strategy vào service; service ──► view
├── view/       StudentView                    in các khối + thống kê
├── constants/  Message, Constants             câu chữ; ngưỡng 7.5/6/4, TYPES
├── utils/      Validation                     getMark (3 lỗi), isYesOrNo, isYes
└── main/       Main                           vòng nhập + Scanner
```

| Câu hỏi thiết kế | Trả lời |
|---|---|
| Sao không có `repository`? | Không lưu giữ lâu, không thêm/sửa/xoá — danh sách chỉ sống cho **một báo cáo**. Xếp loại, thống kê = *"tính toán nghiệp vụ, report"* → `service`. |
| Main gọi controller mấy lần? | **Một lần**: `controller.classifyStudents(requestList)` sau khi nhập xong. Main gom các `StudentRequestDTO` vào `ArrayList`. |

**Luồng:**

```
Main: do { inputStudent(sc) → requestList.add } while (inputMore(sc))
      controller.classifyStudents(requestList)
         service.makeReport(requestList)
            ├─ createStudent(dto)          (Builder) × n
            ├─ averageStudent(students)    AVG + Type qua strategy.classify
            ├─ toResponse(...)             × n
            └─ getPercentTypeStudent(...)  HashMap A/B/C/D
         view.setReport(report); view.display()
```

### 3.1 Design Pattern

**Builder** (Creational)

| Yếu tố | Trong bài này |
|---|---|
| **Problem** | `Student` có **7 thuộc tính**; constructor 5–7 tham số dễ truyền nhầm (ba `double` liền nhau: maths hay physics?) và trái tinh thần luật "không truyền nhiều tham số". |
| **Solution** | `StudentBuilder` = **Builder** (các hàm `withName`, `withClasses`, `withMaths`, `withChemistry`, `withPhysics` trả lại chính builder, `build()` trả `Student`). `Student` = **Product**. `StudentService.createStudent` = **Director** (gọi các bước theo thứ tự). |
| **Consequences** | ✅ Mỗi giá trị **có tên** ở chỗ gán, thêm thuộc tính không phải sửa chữ ký hàm nào. ❌ Thêm 1 lớp. Builder không `static` (model cấm static) nên là lớp riêng, tạo bằng `new StudentBuilder()`. |

**Strategy** (Behavioral)

| Yếu tố | Trong bài này |
|---|---|
| **Problem** | Thang xếp loại là thứ thầy dễ đổi tại chỗ ("A từ 8.0", "thêm loại E"). Viết cứng trong `averageStudent` thì mỗi lần đổi phải sửa service. |
| **Solution** | `ClassificationStrategy` = **Strategy** (`String classify(double average)`); `StandardClassificationStrategy` = **ConcreteStrategy** (4 mức của đề); `StudentService` = **Context** nhận strategy qua **constructor**; `StudentController` là nơi chọn: `new StudentService(new StandardClassificationStrategy())`. |
| **Consequences** | ✅ Thang mới = **1 class mới + 1 dòng** trong controller (**O**CP, **D**IP). ❌ Thêm 2 file cho một luật đơn giản. |

**Facade**: `StudentController` là một cửa gọn cho `Main`. **MVC (JSP)**: `Student`, DTO là JavaBean.

### 3.2 SOLID

| | Ở đâu |
|---|---|
| **S** | `Student` giữ dữ liệu · `StudentBuilder` tạo · `StudentService` tính · `StudentView` in · `Validation` kiểm |
| **O** | thang xếp loại mới không sửa `StudentService` |
| **L** | mọi `ClassificationStrategy` thay nhau được |
| **I** | interface 1 hàm |
| **D** | `StudentService` phụ thuộc `ClassificationStrategy`, nhận qua constructor |

---

## 4. Code từng bước

> Thầy: *"code cái **model trước rồi đến data**"*.

| Bước | File | Việc |
|---|---|---|
| 1 | `model/Student.java` | 7 field `private` + ctor rỗng + get/set + `toString` |
| 2 | `model/StudentBuilder.java` | field `student`; 5 hàm `withX` `return this;`; `build()` |
| 3 | `dto/` 3 lớp | Request (5 ô), Response (4 dòng), Report (`ArrayList` + `HashMap`) |
| 4 | `service/ClassificationStrategy` + `StandardClassificationStrategy` | bậc thang `if` từ trên xuống |
| 5 | `service/StudentService.java` | `makeReport`, `createStudent`, `averageStudent`, `getPercentTypeStudent`, `round` |
| 6 | `constants/` | 9 thông báo **chép đúng** (dùng `%s`), ngưỡng, `TYPES` |
| 7 | `view/StudentView.java`, `controller/StudentController.java` | in khối + `%`; cắm strategy |
| 8 | `utils/Validation.java` | `getMark(input, subject)` — **kiểm "là số" trước, rồi > 10, rồi < 0** |
| 9 | `main/Main.java` | `do/while`, `inputMark`, `inputMore`, `System.out.println()` trước kết quả |

**Bẫy hay gặp:**

1. Kiểm khoảng **trước** khi parse → dòng trống không ra `"Maths is digit"`.
2. `/ 10` (int) thay vì `/ 10.0` → mất phần thập phân. Bài dùng `Constants.ROUND_FACTOR = 10.0`.
3. Duyệt `HashMap` để in → thứ tự A/B/C/D **không được đảm bảo**. In theo `Constants.TYPES`.
4. Prompt Y/N dùng `print` → phải `println()` một dòng trống trước khi in kết quả (màn hình đề).

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

---

## 6. Debug

| Việc | Cách làm |
|---|---|
| Breakpoint | dòng `student.setType(classificationStrategy.classify(average));` trong `averageStudent` |
| Chạy | **Ctrl+F5**, nhập 7.4/7.6/7.6 |
| Quan sát | `sum` = 22.6, `average` = **7.5** (đã làm tròn) |
| Bước | **F7** vào `classify` → nhảy vào `StandardClassificationStrategy`; **F8** thấy nhánh `>= TYPE_B_FROM` trả `"B"` |
| Thống kê | breakpoint vòng `for (String type : Constants.TYPES)` cuối `getPercentTypeStudent`, xem `percentMap` đổi từ số đếm sang % |

---

## 7. Câu hỏi thầy hay hỏi

### OOP

| Câu hỏi | Trả lời mẫu |
|---|---|
| 4 tính chất OOP? | **Đóng gói**: 7 field `private` của `Student`. **Kế thừa**: `StandardClassificationStrategy implements ClassificationStrategy`; ghi đè `toString()` của `Object`. **Đa hình**: `classificationStrategy.classify(average)` chạy bản của lớp thật. **Trừu tượng**: interface chỉ nói "xếp loại được"; `Main` không biết có service. |
| Builder khác constructor? | Constructor nhận mọi thứ **một lần, theo vị trí**; builder nhận **từng giá trị có tên**, gọi nối chuỗi. |

### Access modifier / static / kiểu

| Chỗ | Vì sao |
|---|---|
| field | `private` hết |
| get/set, `withX`, `build` | `public` — service (package khác) gọi |
| `StudentService.makeReport` | `public` — controller gọi |
| `createStudent`, `averageStudent`, `getPercentTypeStudent` | `public` — **tên và chữ ký đề bắt** ("in startup code"); `toResponse`, `round` **private** vì chỉ service dùng |
| `classify` | `public` — hàm interface |
| `StudentController.classifyStudents`, `StudentView.setReport/display` | `public` — `Main`/controller gọi; `oneDecimal` **private** |
| `Validation.*` | `public static` — utils *"phải dùng static method"* |
| hàm trong `Main` | `private static` — chỉ `main` gọi, `main` static |
| `Constants.TYPES` | `public static final` mảng — một bản dùng chung cho service và view |

| Câu hỏi | Trả lời mẫu |
|---|---|
| **Bỏ `static` ở `Validation.getMark`?** | Lỗi biên dịch ở `Validation.getMark(...)`. Phải bỏ `private` constructor, `new Validation()` trong `Main`, gọi qua đối tượng. |
| `averageStudent` trả `List<Student>` dù sửa ngay trên list? | **Chữ ký đề bắt** (comment `// brief:`). Nó trả lại chính list đã xếp loại. |
| **Sao chỗ khác dùng `ArrayList` mà đây `List`?** | Thầy dặn khai báo kiểu cụ thể. `List` là interface, `ArrayList` là lớp cài bằng mảng động. Chỉ 2 hàm đề **bắt** chữ ký `List` nên giữ, còn lại (`ArrayList<Student> students = new ArrayList<>()`) là kiểu cụ thể. |
| `getPercentTypeStudent` trả `HashMap<String, Double>`? | Đề bắt; `Double` (lớp bọc) vì Map chỉ chứa đối tượng, không chứa `double`. |
| `getMark` trả `double`? | Điểm có thể 7.5. |
| `isYes` trả `boolean`? | Chỉ cần có/không. |

---

## 8. Thầy đổi yêu cầu tại chỗ

| Thầy bảo | Sửa | Không đụng |
|---|---|---|
| A khi > 8.0 | `Constants.TYPE_A_ABOVE` | mọi file khác |
| Thang khác hẳn (5 loại) | lớp mới `implements ClassificationStrategy` + 1 dòng trong `StudentController`; thêm khoá vào `Constants.TYPES` | `StudentService.averageStudent` |
| Thêm môn Biology | `Student`, `StudentBuilder.withBiology`, `StudentRequestDTO`, `Constants.NUMBER_OF_SUBJECTS`, `Message.BIOLOGY`, `Main.inputStudent`, dòng cộng `sum` | controller, view, strategy |
| Không cho tên trống | `Validation.getNonBlank` + `Message` + vòng hỏi lại trong `Main` | service, view |

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
