# Quy tắc LAB211 của thầy — bản rút gọn CÓ NGUỒN

> Thầy: **Nguyễn Văn An** (FE FPTU HN, annv22@fe.edu.vn) — học kỳ Fall 2026.
> Quy tắc dưới đây có **hai nguồn**: (1) **trích nguyên văn** tài liệu thầy phát (thư mục
> `~/Documents/Lab211_Fall26`); (2) **lời thầy nói trên lớp** do anh ghi lại buổi 1 (mục 9, mã V1–V10).
> Chỗ nào là quyết định của bộ lời giải thì ghi rõ **[quyết định của bộ lời giải]** và lý do.
> Không có quy tắc nào đoán mò.

Nguồn viết tắt:

| Mã | Tệp gốc |
|---|---|
| **HD** | `01. Hướng dẫn chung/Huong-dan-hoc-LAB211.pptx` (slide Nội quy, Yêu cầu thực hành, Quy tắc đặt tên) |
| **GUIDE** | `02.Samples/Guide.xlsx` — 5 sheet: `MVC`, `SOLID`, `package diagram`, `sample`, `expert` |
| **SYL** | `LAB211- Course Evaluation - Reward.xlsx` (Syllabus + Reward) |
| **GP** | `Lab Grading policy.pdf` |
| **CC** | `Java_codeconventions-150003.pdf` (Sun, 1997) |
| **OOP** | `02. Base knowledges/OOP_Java_Guide.docx` |
| **SOLID** | `02. Base knowledges/SOLID-Principles.pptx` (kể cả ghi chú thuyết trình) |
| **DP** | `02. Base knowledges/Design Pattern.pptx` |
| **SM** | `02. Base knowledges/Chapter_7 - Static Modeling.pptx` |

---

## 1. Luật chơi của môn

| Quy tắc | Nguồn (nguyên văn) |
|---|---|
| Qua môn khi tích luỹ **≥ 750 LOC** | SYL: *"Students need to accumulate at least 750 LOC"*; HD: *"Tổng số LOC: >= 750"* |
| Học lại thì **giữ LOC cũ**, nhưng **không được làm lại bài đã pass** | SYL: *"the previously accumulated LOC is remained … students are not allowed to re-conduct previously completed assignments"* |
| Vi phạm nội quy phòng lab → **LOC về 0** | SYL: *"If students break the Lab room Regulartions, students' accumulated LOC will be reset to 0"* |
| Thầy có thể **từ chối kết quả** nếu em không hiểu bài mình làm | SYL: *"Mentor can re-check performence … and can reject the result if student do not understand what he/she have done"* |
| Tự làm **trong phòng lab** | SYL: *"Students are required to implement all assignments by him/her self in lab rooms"* |
| Công cụ: **NetBeans 8.0.2, JDK8, CheckStyle** | SYL mục Tools |
| Buổi đầu làm **S.P0055 theo mẫu Guide** (được mở Guide, **không tính LOC**) | HD Nội quy 4 |
| Chọn tối đa **5 bài**/lần, xong mới chọn tiếp | Hướng dẫn LAB cho Sinh Viên, mục III |
| Review tối đa **3 bài/SV/slot**; phải request **trước khi hết giờ 30 phút** | HD Nội quy 5 (bản pptx — bản PDF bị mất dòng này) |
| Chỉ **SUBMIT** khi thầy đã đánh giá đạt; submit rồi **không sửa được** | Hướng dẫn LAB cho Sinh Viên, mục IV.2 |
| Thưởng: 951–1151 Blue · 1152–1352 Yellow · ≥1353 Red YDeveloper | SYL sheet Reward |
| Bài dài (> 5 slot) tính LOC theo **% hoàn thành** (50%…100%) | SYL sheet Reward |

**Thầy khuyên tránh** (HD Nội quy 4):
- *"Bài candidate cần implement đầy đủ SOLID trong solid → rất khó, không nên liều"* → **J1.L.P0022**
- *"Bài mua bán hoa quả cần thiết kế được ERD cho phép mỗi lần mua nhiều loại quả, có thể kiểm tra số hàng tồn … → không nên chọn"* → **J1.L.P0023**

## 2. Năm cửa "không review" — trượt một cửa là dừng

Nguyên văn HD Nội quy 6–7:

1. *"Source code bắt buộc theo cấu trúc (MVC), không có cấu trúc → không review"*
2. *"Các bài liên quan thuật toán như fibo, sắp xếp,… **cũng phải làm MVC**, không OOP/MVC → không review"*
3. *"Đúng coding convention, không đảm bảo convention sẽ không review tiếp. (Ưu tiên **Alt + Shift + F**)"*
4. *"Có đủ **comment** source **ít nhất cho function và block/rẽ nhánh**. Không có comment → không review"*
5. *"Phải **debug** được khi thày yêu cầu, không debug được sẽ không review tiếp"*
6. *"Phải thực hiện **test tất cả các happy case** cũng như **hiển thị đủ các message validation**, test thiếu không review."*

Khi review, phải **trả lời được** (HD Nội quy 7):
- *"Làm đúng OOP: Tạo được class cho từng đối tượng độc lập, đủ attribute và methods, đặc biệt phải đúng **access modifier**, dùng được các tính chất như **kế thừa, đa hình sẽ được cộng LOC**. **Implement và hiểu SOLID được cộng LOC**"*
- *"Các access modifier như: public, private, protected, default – khái niệm, phạm vi, và **chỉ rõ tại sao trong source chúng nó cần dùng**."*
- *"Các kiểu dữ liệu trả về của method: int, float, string, void… **tại sao trả về void, tại sao cần trả về string**,…"*
- *"Từ khóa static: **tại sao dùng static? Bỏ đi thì sao? Nếu ko dùng thì sửa source thế nào cho chạy**…"*
- *"4 concepts của oop: liệt kê 4 tính chất ra, **phải chỉ được src của nó có tính chất nào của OOP**"*

## 3. Kiến trúc — đúng theo `Guide.xlsx`

### 3.1 Biến thể MVC thầy dùng

GUIDE sheet `MVC`, dòng chú ý: *"MVC có nhiều biến thể, ở đây chỉ implement theo biến thể trong đó
**Model và View không giao tiếp với nhau**, View cũng **không đóng vai trò communicate giữa user và
controller**"*.

GUIDE sheet `MVC`: *"Nếu giữa Controller và Model có Services và Repository thì cần đảm bảo data
flow theo đúng layer: **Controller <-> Services <-> Repository <-> Model**"*.

```
User ──(gõ phím)──► main.Main ──RequestDTO──► controller ──► service ──► repository ──► model
                                                  │                                     │
User ◄──(màn hình)── view ◄──ResponseDTO──────────┘◄────────────────────────────────────┘
```

HD slide "Yêu cầu thực hành": *"OOP — Tất cả triển khai theo OOP"* · *"Cấu trúc project — Phân chia
package, class, method theo MVC"* · *"SOLID — Model đáp ứng nguyên tắc Single Responsibility"* ·
*"Đóng gói — Không truyền dữ liệu qua lại"*.

### 3.2 Từng package — luật nguyên văn (GUIDE sheet `package diagram` + `sample`)

| Package | Lớp mẫu | Luật của thầy (nguyên văn) |
|---|---|---|
| `constants` | `Message.java` | *"Chứa nội dung các message, label sẽ hiển thị trong chương trình. **Tất cả message/label cần khai báo chung ở đây, không hardcode trong các class khác**"* |
| `constants` | `Constants.java` | *"Chứa hằng số, enum,…"* |
| `controller` | `DoctorController.java` | *"Nhiệm vụ chỉ là nhận input từ main, điều hướng hoạt động của Services/Repository và View, chỉ import DTO, View, Service"* · *"Chỉ làm việc điều hướng với view và services/repo (Nhận kết quả xử lý từ services/repo → truyền qua view để hiển thị)"* · *"**Không static**"* · *"**Không được nhập trực tiếp từ bàn phím** (nhận input qua DTO) **hoặc print trực tiếp ra màn hình** (Print qua View)."* |
| `dto` | `DoctorRequestDTO.java` | *"Đối tượng chứa data giao tiếp giữa main và controler, có thể truyền nguyên vẹn DTO này vào services và repository"* · *"Những gì cần truyền từ main vào controller khai báo ở đây"* |
| `dto` | `DoctorResponseDTO.java` | *"Đối tượng chứa data giao tiếp giữa controller và view, những gì sẽ hiển thị qua view khai báo ở đây"* |
| `main` | `Main.java` | *"Chứa work flow chính, chỉ làm việc với validation, controller. **Scanner cũng chỉ được sử dụng ở đây**"* · *"Mỗi workflow chính (Create student, report,…) **chỉ gọi vào controller 1 lần duy nhất**"* · *"**Cấm dùng static với biến, có thể dùng với hàm**"* · *"Scanner chỉ được sử dụng ở main, không được gọi ở bất cứ đâu khác"* · *"**Không gọi đến model và view**, chỉ làm việc với DTO, validator, controller, FileUtils, CapchaUtils"* · *"Truyền data vào controller thông qua DTO param"* |
| `model` | `Doctor.java` | *"Chỉ chứa thuộc tính và function của đối tượng đang mô tả, **không được input từ scanner hoặc output (printf)** ở đây"* · *"**Không được dùng static**"* · *"Cần output gì thì thêm hàm **toString()** để trả lại repository → controller sẽ nhận kết quả và truyền vào view"* |
| `repository` | `DoctorRepository.java` | *"Chứa data, ví dụ danh sách sinh viên, danh sách bác sỹ sẽ nằm ở đây. Các method CRUD đơn giản đối với data chính cũng nằm ở đây. Nếu có các tính toán nghiệp vụ ngoài CRUD thì cần thêm class DoctorServices.java để thực hiện các nghiệp vụ này. Services nằm giữa Controller và Repo"* |
| `service` | `DoctorServices.java` | *"Chứa các tính toán nghiệp vụ nếu có (Tính tổng, chu vi, diện tích, report,…). **Services chỉ được gọi từ Controller** và được phép import Model. **Services không làm việc với input, output, View**"* |
| `utils` | `Validation.java` | *"Chứa các functions dùng chung như **validate, đọc/ghi file, mã hóa dữ liệu**. Chỗ này **phải dùng static method**"* · *"Chú ý **final**"* · *"Chú ý cần có **private constructor**"* |
| `view` | `DoctorView.java` | *"Chứa ResponseDTO, việc hiển thị kết quả xử lý trên console sẽ thực hiện ở đây. **Không được gọi print ngoài view và main**."* |
| `exceptions` | (tuỳ bài) | HD slide "Quy tắc đặt tên → Package": *"main, controllers/services, **exceptions**, utils"* |

### 3.3 Quy tắc thêm tầng — **[quyết định của bộ lời giải]**, bám sát chữ của thầy

Thầy **không** nói "bài nhỏ thì bỏ tầng". Ngược lại: thuật toán cũng phải MVC (HD Nội quy 4).
Nên mọi bài trong bộ này đều có đủ **khung cố định**:

```
constants/  Message.java, Constants.java
controller/ <Ten>Controller.java
dto/        <Ten>RequestDTO.java, <Ten>ResponseDTO.java
main/       Main.java
model/      (ít nhất 1 lớp mô tả đối tượng của bài)
utils/      Validation.java  khi bài CÓ nhập bàn phím (P0009, P0080 không nhập gì → không có)
            (+ FileUtils.java khi bài có đọc/ghi tệp)
view/       <Ten>View.java
```

và **thêm** hai tầng theo đúng câu chữ của thầy:

| Thêm | Khi nào (theo GUIDE) |
|---|---|
| `repository/` | bài có **một tập dữ liệu được lưu giữ** (danh sách bác sĩ, sinh viên, từ điển…) và làm **CRUD** trên nó |
| `service/` | bài có **tính toán nghiệp vụ ngoài CRUD**: tổng, chu vi, diện tích, report, **thuật toán** (sắp xếp, tìm kiếm, đổi hệ cơ số, nhân số lớn…) |
| `exceptions/` | đề **bắt** tạo lớp ngoại lệ riêng (ExceptionCar, ExceptionHandle…) |

Bài thuật toán (fibo, sort…): **không** có `repository` (không có tập dữ liệu được lưu giữ và
CRUD), **có** `service` (thuật toán chính là "tính toán nghiệp vụ").

### 3.4 Luồng của một chức năng — chép theo mẫu P0055 của thầy

```java
// main.Main  — Scanner ở ĐÂY, và chỉ ở đây
DoctorRequestDTO dto = new DoctorRequestDTO();
System.out.print(Message.INPUT_CODE);
dto.setCode(Validation.getString(sc.nextLine()));
...
controller.addDoctor(dto);          // gọi controller ĐÚNG 1 lần cho workflow này

// controller — không Scanner, không print
public void addDoctor(DoctorRequestDTO requestDTO) throws Exception {
    if (doctorRepository.isDuplicate(requestDTO.getCode())) {
        throw new Exception(Message.DUPLICATE);
    }
    doctorRepository.addDoctor(requestDTO);
}

// view — nơi in kết quả
doctorView.setDoctorMap(result);
doctorView.display();
```

Lỗi nghiệp vụ đi bằng `throw new Exception(Message.X)`; **main** bắt và in `e.getMessage()`.

## 4. Coding convention — thứ chấm ngay ở cửa đầu

Nguồn: GP (*"Coding convention: Source File name, Function name, Variable naming convention, Code
comment, Statement format"*), CC, OOP mục 8.1, HD slide "Quy tắc đặt tên".

| Mục | Quy tắc | Nguồn |
|---|---|---|
| Tên project | `RollNo_ExcerciseNo_ExcerciseDescription` → `HE176322_J1SP0055_DoctorManagement` | HD |
| Class | PascalCase, **danh từ**: `Student, Worker, Wallet, Person` | HD, CC §9 |
| Method | camelCase, **động từ**: `calcSummaryFee(), checkValidAge()` | HD, CC §9 |
| Biến | camelCase, có nghĩa; tên 1 chữ chỉ cho biến tạm `i, j, k` | CC §9 |
| Hằng | `UPPER_SNAKE_CASE`; **không viết số trực tiếp** trừ -1, 0, 1 | CC §9, §10.3 |
| Package | chữ thường | OOP 8.1 |
| Thụt lề | **4 dấu cách**; `{` cuối dòng khai báo; `}` đứng riêng một dòng | CC §4, §6.4 |
| Ngoặc | **luôn có `{}`** cho if/for/while, kể cả 1 câu lệnh | CC §7.2, §7.4 |
| Khoảng trắng | sau từ khoá (`if (`), sau dấu phẩy, quanh toán tử 2 ngôi; **không** giữa tên hàm và `(` | CC §8.2 |
| Mỗi dòng | 1 câu lệnh; 1 khai báo; tránh dòng > 80 ký tự | CC §4.1, §6.1, §7.1 |
| switch | mọi `switch` có `default` | CC §7.8 |
| Thứ tự trong class | hằng static → biến instance (public→protected→private) → constructor → method | CC §3.1.3 |
| Trường | **không public** nếu không có lý do | CC §10.1 |
| Static | gọi qua **tên lớp**, không qua đối tượng | CC §10.2 |
| Định dạng | NetBeans **Alt + Shift + F** | HD Nội quy 7 |

## 5. Comment — cửa "không có comment → không review"

HD Nội quy 7: *"Có đủ comment source **ít nhất cho function và block/rẽ nhánh**"*.
CC §5: comment để **giải thích điều không tự hiện ra trong code**, doc comment `/** */` đặt ngay
trước khai báo class/method.

Bộ lời giải này tuân theo mức **tối thiểu bắt buộc** sau, và **bộ kiểm tự động chặn** nếu thiếu:

| Vị trí | Bắt buộc |
|---|---|
| Mỗi class / interface / enum | Javadoc **ngắn** 1–2 câu + `@author HE176322` |
| Mỗi method + constructor | **một dòng `//`** ngay trên khai báo: hàm này làm gì |
| Mỗi `if` / `else if` / `else` | 1 dòng: nhánh này bắt trường hợp nào |
| Mỗi `switch`, mỗi `case`, `default` | 1 dòng: lựa chọn nào |
| Mỗi `for` / `while` / `do` | 1 dòng: lặp để làm gì, dừng khi nào |
| Mỗi `try` / `catch` | 1 dòng: bảo vệ đoạn nào / bắt lỗi gì |
| Mỗi trường (field) | 1 dòng: giữ gì |

**Vì sao `//` một dòng chứ không Javadoc đầy đủ cho từng hàm** — lời thầy V8 (mục 9): thầy gõ mẫu
comment `//` ngắn cho từng model / method / block, và `// brief:` khi phải giữ kiểu List/Map do đề
bắt. Javadoc `@param`/`@return` cho **mọi** hàm làm file dài gấp đôi mà không thêm thông tin nào,
trong khi thầy review bằng mắt ngay trên máy lab. Javadoc chỉ giữ ở **đầu class** (chỗ NetBeans hiện
tooltip), luôn kèm `@author HE176322`.

## 6. OOP, access modifier, static — câu hỏi chắc chắn bị hỏi

OOP mục 4 (4 tính chất), mục 5 (6 quan hệ); SM (6 quan hệ + bội số).

| Tính chất | Nhìn vào đâu trong bài của em |
|---|---|
| **Encapsulation** | mọi field `private` + getter/setter trong `model`, `dto` |
| **Inheritance** | `extends` (Shape → Circle…), hoặc lớp ngoại lệ `extends Exception` |
| **Polymorphism** | `@Override toString()`; biến kiểu cha gọi hàm bị ghi đè trong vòng lặp; overloading |
| **Abstraction** | `abstract class`/`interface`; Main gọi `controller.addX(dto)` mà **không biết** dữ liệu nằm trong gì |

| Access | Phạm vi (OOP 4.1) | Dùng ở đâu trong bộ lời giải |
|---|---|---|
| `private` | cùng class | **mọi field**; constructor của `Message`, `Constants`, `Validation`; hàm phụ trong 1 class |
| (default) | cùng package | (tránh dùng — không có lý do trong bài console) |
| `protected` | cùng package + lớp con | chỉ khi **lớp con cần** (hoặc đề bắt, như P0052 `Country`) |
| `public` | mọi nơi | hàm là "hợp đồng" của class được lớp khác gọi |

`static` (HD Nội quy 7 + GUIDE): **chỉ** ở `utils` (bắt buộc — *"phải dùng static method"*), hằng
trong `constants`, và **hàm** trong `main` (*"cấm static với biến, có thể dùng với hàm"*). `model` và
`controller` **không static**. Câu trả lời mẫu: *"Validation.getInt() static vì nó không dùng dữ
liệu riêng của đối tượng nào — cho cùng chuỗi vào luôn ra cùng kết quả. Bỏ static thì phải
`new Validation()` mỗi lần gọi, nhưng constructor đang `private` nên không new được — em sẽ phải
bỏ `private` constructor và tạo một đối tượng Validation trong Main để gọi."*

## 7. SOLID và Design Pattern — **có** trong môn này

HD slide "Kiến thức cần nắm" mục 7: *"SOLID, Design Pattern"*. HD Nội quy 7: *"Implement và hiểu
SOLID được cộng LOC"*. HD slide "Yêu cầu thực hành": *"Model đáp ứng nguyên tắc Single
Responsibility"*.

Nhưng ghi chú slide 26 của SOLID-Principles.pptx cảnh báo: *"Thiết Kế Quá Mức/Trừu Tượng Hóa Sớm …
SOLID không phải lúc nào cũng phù hợp với các ứng dụng nhỏ"*, *"Nếu một abstraction được giới thiệu
'chỉ vì SOLID nói vậy' nhưng không giải quyết được vấn đề thực sự … vi phạm nguyên tắc YAGNI"*.

**[quyết định của bộ lời giải]** — cách áp dụng:

| Nguyên lý | Áp dụng |
|---|---|
| **S** | **Mọi bài**: mỗi lớp một lý do để thay đổi — đó chính là việc chia package ở mục 3 |
| **O** | Khi có **họ đối tượng** (Shape, Bee, Candidate, Vehicle…): thêm loại mới = thêm lớp con, không sửa code cũ |
| **L** | Lớp con thay được lớp cha, không ném `UnsupportedOperationException`, không đổi nghĩa hàm cha |
| **I** | Interface nhỏ, chỉ khi thật sự có 2+ lớp hiện thực |
| **D** | Bài lớn (Long assignment) và bài **Candidate** (thầy bắt đủ SOLID): controller/service phụ thuộc **interface** repository |

Design pattern (DP) — **thầy đánh giá cao nhất** (lời thầy V7, mục 9). Mỗi bài áp dụng pattern
**hợp với bài** (không nhét cho có — ghi chú slide 26 SOLID), và HUONG-DAN trình bày đủ 4 yếu tố GoF:

| Pattern | Dùng ở loại bài | Vì sao hợp |
|---|---|---|
| **MVC** (architectural) | mọi bài | luật bắt buộc của thầy |
| **Strategy** | sắp xếp / tìm kiếm / nhiều cách tính | đổi thuật toán = thêm 1 class, không sửa Service (OCP) |
| **Factory Method** | tạo đúng lớp con theo loại (Candidate, Vehicle, Bee, Shape…) | nơi duy nhất biết "loại X → new LớpX" |
| **Template Method** | lớp cha abstract giữ khung, lớp con điền bước (Bees: `isDead` dùng `getThreshold()`) | không lặp code ở lớp con |
| **Builder** | đối tượng rất nhiều thuộc tính (Employee 10 trường, Account 7 trường…) | tránh constructor dài — khớp luật V4 |
| **Facade** | controller: một cửa gọn cho main, che repository + service + view | chính là vai của controller |
| **Singleton** | chỉ khi NHIỀU controller phải dùng chung MỘT kho (bài Long) | có dùng `static` — phải nói được lý do (V3) |

**Chia package thì luôn giữ đủ, lớp pattern thì tuỳ bài.** Hai chuyện khác nhau, đừng lẫn:

| | Quyết định | Vì sao |
|---|---|---|
| **Khung cố định**: `constants` · `model` · `dto` · `controller` · `view` · `main` (+ `utils` khi có nhập liệu / đọc ghi file / mã hoá) | **mọi bài, kể cả bài 21 dòng** | Chính project mẫu thầy phát — `HE176322_J1S0055_DoctorManagement` — có đúng bộ này (8 package, kèm `repository`). Bài luyện 01 của thầy cũng vậy. Anh hỏi lại thầy **15/09/2026**: chia package như thế mới đúng Design Pattern và SOLID |
| **Thêm `repository`** | khi chương trình **giữ một collection** (danh sách bác sỹ, sinh viên…) | GUIDE: *"Chứa data, ví dụ danh sách sinh viên… Các method CRUD đơn giản đối với data chính cũng nằm ở đây"*. Bộ này: **23/54 bài** |
| **Thêm `service`** | khi có **tính toán nghiệp vụ hoặc thuật toán** ngoài CRUD | GUIDE, ngay trong ô `repository`: *"Nếu có các tính toán nghiệp vụ ngoài CRUD thì **cần thêm** class DoctorServices.java… Services nằm giữa Controller và Repo"*. Bài luyện 02 của thầy có `service/` (và cả `service/sort/`). Bộ này: **46/54 bài** |
| **Thêm `exceptions`** | khi **đề bắt** có exception riêng | HD slide "Quy tắc đặt tên → Package". Bộ này: **2/54 bài** |
| **Lớp pattern GoF** (interface `*Strategy` + lớp cài đặt, `*Factory`, `*Builder`…) | bài **≤ 60 LOC không thêm**; bài có họ đối tượng / nhiều cách tính / bài Long thì thêm | Với 40 dòng thuật toán, một interface + một lớp cài đặt là abstraction "chỉ vì SOLID nói vậy" — đúng thứ **ghi chú slide 26** của thầy cảnh báo (YAGNI). Thuật toán vẫn là hàm `private` trong service |

Bài nhỏ vẫn có **MVC** (thầy bắt) và **Facade** (vai của controller). `HUONG-DAN.md` của những bài
đó, mục 3.1, có sẵn câu trả lời khi thầy hỏi *"Strategy đâu?"*: nói được **chỗ sẽ cắm** Strategy và
**giá phải trả** nếu cắm sớm.

## 8. Đề bài vs. quy tắc thầy — khi hai bên vênh nhau

**[quyết định của bộ lời giải]**:

1. **Chức năng + màn hình + thông báo lỗi trong ĐỀ là bắt buộc** — giữ nguyên từng chữ.
2. **Tên hàm đề bắt** (*"Student must implement methods … in startup code"*) — **giữ đúng tên và chữ
   ký**, đặt vào **đúng tầng theo quy tắc thầy** (tính toán → `service`, CRUD → `repository`,
   in ra → `view`).
3. **Cấu trúc lớp mà đề gợi ý ngược với luật thầy** (ví dụ đề P0061 bắt `Shape.printResult()` in ra
   màn hình ngay trong model) → **luật thầy thắng** vì thầy *"không có cấu trúc → không review"*.
   Hàm `printResult` được đặt ở `view`, và README bài đó ghi sẵn câu trả lời khi thầy hỏi.
4. Mọi chỗ vênh đều được **liệt kê** trong README của bài.

## 9. LỜI THẦY NÓI TRÊN LỚP — anh ghi lại buổi 1 (07/09/2026)

Không có trong file giấy, nhưng là **lời thầy nói trực tiếp** (ghi chép của anh gửi ngày 07/09) — nên
đây là **luật**, ngang hàng với tài liệu. Nguyên văn ghi chép:

| # | Ghi chép của anh (nguyên văn) | Bộ lời giải làm gì |
|---|---|---|
| V1 | *"hiểu 4 tính chất oop và 5 nguyên lí Solid"* | mỗi HUONG-DAN chỉ rõ 4 tính chất + SOLID nằm ở file/hàm nào |
| V2 | *"Thầy quan tâm nhất là **Access modifer** dùng sai linh tinh là thầy cho reject"* | field luôn `private`; hàm `public` chỉ khi lớp khác gọi; hàm phụ `private`; không để default vô cớ |
| V3 | *"**Static** cũng thế dùng được thì tốt nhưng dùng linh tinh thầy cũng cho reject"* | static CHỈ ở `utils`, `constants`, hàm trong `main` (và mỗi chỗ có lý do) |
| V4 | *"Truyền tham số qua hàm thì **không được truyền 3 tham số 1 hàm**"* | hàm nghiệp vụ (controller/service/repository/view/model/main) **tối đa 2 tham số** — nhiều dữ liệu hơn thì gói vào **DTO/model**. Ngoại lệ có căn cứ: hàm `utils` kiểu `getChoice(input, min, max)` (đúng **mẫu Guide của thầy**) và **constructor** của model/DTO (mẫu Guide: `Doctor(code, name, specialization, availability)`) |
| V5 | *"AI thường dùng **List, Map thay vì ArrayList và HashMap** … dùng List là thầy biết AI làm liền học vẹt và sẽ hỏi phân biệt tại sao dùng List mà không phải ArrayList"* | khai báo **kiểu cụ thể**: `ArrayList<X> list = new ArrayList<>()`, `HashMap`/`LinkedHashMap`. Chỉ giữ `List`/`Map` khi **chữ ký do đề bắt** — và dòng đó có comment `// brief:` để em trả lời được |
| V6 | *"Nhập vào main view là in ra gì đấy"* (anh ghi: có thể ghi nhầm) | khớp Guide: **nhập ở main, in ở view** |
| V7 | *"Cái quan trọng nhất thầy đánh giá cao đó là **Design Patten** … ai mà hiểu và áp dụng và thành thạo phần này thầy cho pass trong 5 tuần đầu"* | mỗi bài áp dụng pattern **hợp với bài** và HUONG-DAN giải thích theo 4 yếu tố GoF (Name · Problem · Solution · Consequences — slide DP "Elements of a Design Pattern") |
| V8 | *"khi vào code đầu tiên phải **code cái model trước rồi đến data**"* | bảng "Code từng bước" của mọi bài bắt đầu bằng **model**, rồi đến dữ liệu (dto, repository) |
| V9 | *"mỗi model, method,... phải **comment kiểu //** giải thích nó làm gì cho thầy hiểu ngắn gọn"* · *"block code ví dụ vòng for ấy làm gì"* | mọi class/method/field có comment; **mọi** if/else/switch/case/for/while/try/catch có comment `//` 1 dòng |
| V10 | *"**MVC JSP** — thầy bảo phải làm theo mô hình này. Nhắc đi nhắc lại là theo MVC của 'JSP'"* | MVC kiểu JSP Model 2: **Controller** (như Servlet) điều hướng, **View** (như trang JSP) chỉ hiển thị, **Model** là **JavaBean** — field `private`, **constructor rỗng `public`**, getter/setter. Mọi model & DTO đều theo chuẩn JavaBean |

> Những câu này từng bị một bản ghi trước đánh dấu là "không có trong tài liệu". Đó là nhầm: chúng
> không nằm trong file, nhưng **thầy nói trên lớp** — và thầy là người review.

Còn một câu vẫn **không có căn cứ** ở cả tài liệu lẫn ghi chép: ~~"Strategy là pattern thầy đánh giá
cao nhất"~~ — thầy đánh giá cao **Design Pattern nói chung**, không nêu riêng pattern nào.

## 10. Ba lỗi trong code mẫu Guide — đừng chép

| Chỗ | Mẫu viết | Vì sao sai | Bộ lời giải viết |
|---|---|---|---|
| `Validation.getString` | `input.equals(null)` | không bao giờ đúng; `input` null thì NPE | `input == null` |
| `Validation.getChoice` | 1 `catch (Exception e)` bọc cả `throw` "ngoài khoảng" | nhập `9` bị báo nhầm "không phải số" | tách `catch (NumberFormatException)` |
| `getPosititveInteger` | sai chính tả, trả `float` mà parse `Integer` | convention bị chấm | `getPositiveInt` trả `int` |
| `Main case 3` | tạo DTO xoá nhưng **không gọi** controller | chức năng xoá không chạy | gọi `controller.deleteDoctor(dto)` |
