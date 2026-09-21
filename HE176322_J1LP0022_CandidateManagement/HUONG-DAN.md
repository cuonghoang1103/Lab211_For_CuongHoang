# J1.L.P0022 — Candidate Management

> **Bài dài (Long Assignment)**, chấm theo **% hoàn thành**.
> ⚠️ Thầy dặn: bài này **bắt đủ SOLID**, nên nhiều bạn né. Đã chọn thì phải chỉ được **từng chữ S-O-L-I-D nằm ở file nào** (mục 3.2)
> và giải thích được **kế thừa + đa hình** (đề: *"Using Object-Oriented programming style: inheritance"*).

| | |
|---|---|
| Loại / LOC | Long Assignment · 350 LOC · 5 slot |
| Project | `HE176322_J1LP0022_CandidateManagement` |
| Chạy | NetBeans: **File ▸ Open Project** → chọn thư mục → **F6** |
| Lớp chạy | `main.Main` |
| Kiểm tự động | `python3 _tools/verify.py --netbeans J1LP0022` → 5 kịch bản × 2 locale (3 kịch bản của bản tham chiếu, màn hình chỉnh 3 chỗ cho khớp đề — mục 9 — + 2 bổ sung) · `python3 _tools/soat_checklist.py HE176322_J1LP0022_CandidateManagement` → **0 vi phạm** tờ checklist 25 mục |

---

## 1. Đề bài nói gì

| Phần | Đề yêu cầu |
|---|---|
| 3 loại ứng viên | **Experience**, **Fresher**, **Intern**, là 3 lớp con của `Candidate` |
| Thuộc tính chung | Candidate Id, First Name, Last Name, Birth Date, Address, Phone, Email, Candidate type (**0** Experience · **1** Fresher · **2** Intern) |
| Thuộc tính riêng | Experience: `ExpInYear`, `ProSkill` · Fresher: `Graduation_date`, `Graduation_rank`, `Education` · Intern: `Majors`, `Semester`, `University name` |
| Menu | 1 Experience · 2 Fresher · 3 Internship · 4 Searching · 5 Exit |
| Create (1, 2, 3) | Tạo xong mỗi người hỏi `Do you want to continue (Y/N)?` — N thì về menu và **in mọi ứng viên đã tạo** |
| Search (4) | In mọi ứng viên (**chỉ tên**, theo nhóm) → hỏi tên (First **hoặc** Last) và loại → in `Tên \| năm sinh \| địa chỉ \| phone \| email \| loại` |

**Luật kiểm dữ liệu đề bắt:**

| Ô | Luật của đề | Code |
|---|---|---|
| Birth Date | số **4 ký tự**, 1900..năm hiện tại | `Validation.checkBirthDate`: regex `\d{4}` rồi so với `Year.now()` |
| Phone | số, **tối thiểu 10** ký tự | `Validation.checkPhone`: regex `\d{10,}` |
| Email | `<account name>@<domain>` (vd `annguyen@fpt.edu.vn`) | `Validation.checkEmail`: regex `[^@\s]+@[^@\s.]+(\.[^@\s.]+)+` |
| Year of Experience | số từ **0 đến 100** | `Validation.checkExperience` |
| Rank of Graduation | 1 trong 4: Excellence, Good, Fair, Poor | `Validation.checkRank` → `enum GraduationRank` |

**Guidelines:**

| Slot | Đề viết | Bài này |
|---|---|---|
| 1 | *"Candidate as a SuperClass. Experience, Fresher and Internship … SubClasses that extend Candidate"* | `model/Candidate.java` (**abstract**) ← `Experience`, `Fresher`, `Intern` |
| 2–3 | *"Should use ArrayList to store … Candidate"* | `repository/CandidateRepository`: **một** `ArrayList<Candidate>` chứa cả 3 loại |
| 4 | Search candidate | `service/CandidateSearchService` + `NameSearchStrategy` |

---

## 2. Kiến thức cần biết

### 2.1 Kế thừa + lớp trừu tượng

```java
public abstract class Candidate { ... 7 field chung ... }   // không ai "là Candidate chung chung"
public class Experience extends Candidate { expInYear, proSkill }
public class Fresher    extends Candidate { graduationDate, graduationRank, education }
public class Intern     extends Candidate { majors, semester, universityName }
```

| Câu hỏi | Trả lời |
|---|---|
| Sao `Candidate` là `abstract`? | Mỗi ứng viên thật **luôn** thuộc 1 trong 3 loại. `new Candidate()` là vô nghĩa, và `abstract` khiến compiler chặn luôn. |
| Sao 7 field chung đặt ở lớp cha? | Viết **một lần**; lớp con tự có qua `extends`. Field riêng đặt ở lớp con, vì một field vô nghĩa với 2/3 lớp con thì đang nằm quá cao. |
| Constructor `protected Candidate()`? | Lớp abstract không `new` được; `protected` nghĩa là chỉ lớp con gọi (`super()` ngầm). |

### 2.2 Đa hình — một danh sách, ba loại, mỗi đối tượng tự trả lời

```java
ArrayList<Candidate> candidateList;               // chứa lẫn Experience, Fresher, Intern
candidate.getCandidateType()                      // Experience trả EXPERIENCE, Fresher trả FRESHER…
candidate.toString()                              // mỗi loại in thêm cột RIÊNG của nó
```

`CandidateRepository.findByType` **không** dùng `instanceof`. Nó hỏi `candidate.getCandidateType()` và **đối tượng tự trả lời**
theo lớp thật của nó. Đó là đa hình lúc chạy (dynamic binding).

### 2.3 Template Method — `toString()` của `Candidate`

```java
public final String toString() {                   // khung CỐ ĐỊNH, lớp con không được sửa
    return String.format(Constants.DETAIL_FORMAT,  // "%s | %s" — không cộng chuỗi (3.8)
            getSummary(), getExtraInfo());         // getExtraInfo() = bước lớp con tự điền
}
protected abstract String getExtraInfo();          // Experience: "0 | Java"
                                                   // Fresher:    "06/2022 | Good | FPT University"
                                                   // Intern:     "SE | 5 | FPT University"
```

| Đoạn | Ai viết |
|---|---|
| `getSummary()` = 6 cột chung: tên, năm sinh, địa chỉ, phone, email, **mã loại** | lớp cha (cũng là dòng kết quả Search) |
| `getExtraInfo()` = cột riêng | từng lớp con |
| `final` trên `toString` | chặn lớp con đổi thứ tự cột chung |

### 2.4 Tìm kiếm — "tên chứa chuỗi", không phân biệt hoa thường

`NameSearchStrategy.isMatch`: `firstName.toLowerCase().contains(kw) ||` (xuống dòng **sau** `||`) `lastName.toLowerCase().contains(kw)`.
Gõ `eva` ra `Aguirre Eva` (khớp Last name) và `Antošová Adeleva` (chữ `eva` nằm **trong** `Adeleva`), đúng ví dụ của đề.
Chỉ xét ứng viên **đúng loại** đã gõ (`findByType`).

### 2.5 Java dùng trong bài

| API | Dùng làm gì |
|---|---|
| `abstract class`, `extends`, `@Override` | cây kế thừa `Candidate` |
| `enum CandidateType(code, menuChoice, label, banner)` | 3 loại, mã 0/1/2, số menu 1/2/3, dòng `=====EXPERIENCE CANDIDATE=====` |
| `enum GraduationRank` + `findByLabel` | 4 hạng, gõ `good` vẫn nhận và in `Good` |
| `String.format(Constants.SUMMARY_FORMAT, …)` | dựng dòng `Tên \| năm \| … \| loại` — không cộng chuỗi bằng `+` (tờ checklist 3.8) |
| `String.matches(regex)` | năm sinh, phone, email |
| `java.time.Year.now().getValue()` | "năm hiện tại" (đề: *Date handling*) — không viết cứng `2026` |
| `HashMap<CandidateType, CandidateCreator>` | Factory tra ra "thợ" tạo đúng loại |
| `LinkedHashMap<CandidateType, ArrayList<String>> candidateMap` | gom theo nhóm, **giữ thứ tự** Experience → Fresher → Intern (tên biến Map kết thúc bằng `Map` — 1.5) |

---

## 3. Thiết kế

```
HE176322_J1LP0022_CandidateManagement/src/
├── constants/  Message.java             mọi câu chữ màn hình
│               Constants.java           số menu, giới hạn (1900, 0..100), regex, các *_FORMAT của dòng in
│               CandidateType.java       «enum» EXPERIENCE(0) FRESHER(1) INTERN(2) + findByCode / findByMenuChoice
│               GraduationRank.java      «enum» Excellence / Good / Fair / Poor + findByLabel
├── model/      Candidate.java           «abstract» 7 field chung + Template Method toString
│               Experience / Fresher / Intern   lớp con + getExtraInfo
├── dto/        CandidateRequestDTO      mọi ô Main đọc được (chung + riêng + keyword)      main ──► controller
│               CandidateResponseDTO     message · candidateMap · foundList                 controller ──► view
├── repository/ ICandidateSaver          «interface» isExistId, addCandidate      (ISP)
│               ICandidateFinder         «interface» isEmpty, findByType          (ISP)
│               ICandidateRepository     «interface» extends CẢ HAI               (DIP: controller giữ kho qua interface)
│               CandidateRepository      ArrayList<Candidate> candidateList, implements ICandidateRepository
├── service/    CandidateCreator         «abstract» Factory Method buildCandidate + Template createCandidate
│               ExperienceCreator / FresherCreator / InternCreator
│               CandidateFactory         HashMap<CandidateType, CandidateCreator> creatorMap
│               CandidateService         luật tạo: id trống / trùng, tên trống → trả ResponseDTO
│               ISearchStrategy          «interface» isMatch(candidate, keyword)
│               NameSearchStrategy       First hoặc Last name chứa keyword
│               CandidateSearchService   liệt kê theo nhóm (đủ cột / chỉ tên) + tìm → trả ResponseDTO
├── controller/ CandidateController      Facade + nơi LẮP RÁP (một kho cho hai service); mỗi hàm render 1 lần
├── view/       CandidateView            thuộc tính responseDTO + setResponseDTO + display() không tham số
├── utils/      Validation               kiểm DẠNG: năm sinh, phone, email, 0..100, hạng, Y/N (final + private ctor)
└── main/       Main                     final + private ctor; menu + Scanner + validate; mỗi luồng gọi controller 1 lần
```

| Lớp | Làm gì | Không được làm |
|---|---|---|
| `Main` | đọc bàn phím, **validate** (hỏi lại **tại chỗ** khi sai dạng), gói `CandidateRequestDTO`, mỗi luồng gọi controller **1 lần**, in `e.getMessage()` | import model/view/service/repository |
| `CandidateController` | lắp ráp; nhận DTO → service → `view.setResponseDTO(r)` + `view.display()` **1 lần** | Scanner, `System.out`, static, import model |
| `CandidateService`, `CandidateSearchService` | luật nghiệp vụ, `throw new Exception(Message.X)`, đổi model → `CandidateResponseDTO` | in, đọc phím |
| `CandidateRepository` | giữ `ArrayList<Candidate> candidateList`, thêm/tìm (CRUD đơn giản) | kiểm luật, in |
| `Candidate` + 3 lớp con | mô tả đối tượng, tự dựng dòng cột của mình (`toString`, `getSummary`) | Scanner, `System.out`, static |
| `Validation` | "đúng dạng không?" | luật "id trùng" (việc của service) |

**Luồng chung (mọi chức năng):**

```
Main (nhập + validate) ──CandidateRequestDTO──► CandidateController ──► Service ──► ICandidateSaver/ICandidateFinder
                                                        │                                (CandidateRepository) ──► Candidate
                                                        └──CandidateResponseDTO──► CandidateView.setResponseDTO(…) → display()   (1 lần / luồng)
lỗi nghiệp vụ: service throw new Exception(Message.X) ──► Main catch → System.out.println(e.getMessage())
```

Mỗi `case` của menu và mỗi **vòng** của màn tạo là **một luồng**: gọi controller đúng một lần, view render đúng một lần.

**Luồng Create** (case 1/2/3 dùng chung):

```
Main: CandidateType.findByMenuChoice(choice) → in "---------- Create Experience Candidate ----------"
  ┌─► do: đọc 7 ô chung (năm sinh / phone / email sai dạng → hỏi lại NGAY ô đó)
  │       đọc ô riêng theo loại (inputExtraInfo: switch 1 chỗ duy nhất)
  │       createCandidate(controller, dto)  → controller.createCandidate(dto)      ← 1 luồng / vòng
  │           └─► CandidateService: id trống? trùng? tên trống? → throw (Main in, vòng vẫn tiếp)
  │                  └─► CandidateFactory → ExperienceCreator.buildCandidate → new Experience()
  │                  └─► ICandidateSaver.addCandidate
  │           └─► responseDTO.message = "Experience candidate [E01] has been created." ──► view (1 lần)
  └── while "Do you want to continue (Y/N)?"  Y ─┘
      N → controller.displayAllCandidates()   ← luồng "N": "List of candidate:" + 3 banner + dòng đủ cột (1 lần)
```

**Luồng Search** (case 4) — **hai luồng liền nhau**, vì đề in danh sách **trước** khi hỏi:

```
Main: controller.displayCandidateNames()      ← luồng 1: chưa có ai → throw "The candidate list is empty." (về menu)
          └─► responseDTO.candidateMap = tên theo nhóm ──► view: "List of candidate:" + banner + tên (1 lần)
      searchCandidate(sc, controller):  hỏi tên (sau 1 dòng trống, như đề) + loại 0..2 (sai → hỏi lại)
          controller.searchCandidate(dto)       ← luồng 2: tên trống / không ai khớp → throw
          └─► responseDTO.foundList ──► view: dòng trống + "The candidates found:" + các dòng 6 cột (1 lần)
```

### 3.1 Design Pattern trong bài

**1. Factory Method** (Creational) — tạo đúng lớp con

| Yếu tố | Trong bài |
|---|---|
| **Problem** | Có 3 loại ứng viên và thầy hay bảo *"thêm loại Senior"*. Viết `if (type == 0) new Experience() else if…` trong service thì mỗi loại mới lại phải mở service ra sửa. |
| **Solution** | `CandidateCreator` (abstract) có hàm `buildCandidate(dto)` **abstract** (= Factory Method) và hàm `createCandidate(dto)` **final**: gọi `buildCandidate` rồi điền 7 field chung. `ExperienceCreator`/`FresherCreator`/`InternCreator` chỉ viết `buildCandidate`. `CandidateFactory` giữ `HashMap<loại, creator> creatorMap` và tra 1 bước. |
| **Consequences** | ✅ Thêm loại = thêm class, service không đổi (**O**). ❌ Nhiều file hơn `switch`. |

**2. Template Method** (Behavioral) — `Candidate.toString()` (mục 2.3) và `CandidateCreator.createCandidate()`: khung cố định, lớp con điền 1 bước.

**3. Strategy** (Behavioral) — cách "khớp" khi tìm

| Yếu tố | Trong bài |
|---|---|
| **Problem** | Đề: tìm theo First **hoặc** Last name. Thầy có thể đổi thành *"tìm theo phone"*. |
| **Solution** | `ISearchStrategy` (interface `isMatch`) ← `NameSearchStrategy`. `CandidateSearchService` giữ biến kiểu interface, nhận qua constructor; controller chọn `new NameSearchStrategy()`. |
| **Consequences** | ✅ Đổi cách tìm = thêm 1 class + sửa 1 dòng controller. ❌ Thêm một interface. |

**Các pattern khác:** **Facade** (`CandidateController`: Main chỉ thấy 4 hàm) · **Repository** (`CandidateRepository`) · **DTO** · **MVC**.

### 3.2 SOLID trong bài — thầy bắt đủ 5 chữ

| Chữ | Nguyên lý | Chỉ vào file | Nói với thầy |
|---|---|---|---|
| **S** | Single Responsibility | `CandidateService` chỉ **tạo** · `CandidateSearchService` chỉ **liệt kê/tìm** · `CandidateRepository` chỉ **lưu** · `CandidateView` chỉ **in** · `Validation` chỉ **kiểm dạng** | "Mỗi lớp có đúng một lý do để sửa: đổi luật tạo thì sửa `CandidateService`, đổi cách in thì sửa `CandidateView`." |
| **O** | Open/Closed | `CandidateFactory` + các `*Creator`; `ISearchStrategy` | "Thêm loại Senior hay cách tìm mới là **thêm** class, không sửa `CandidateService`/`CandidateSearchService`." |
| **L** | Liskov Substitution | mọi chỗ dùng kiểu `Candidate` (repository, `toResponseList`) nhận được `Experience`/`Fresher`/`Intern` mà chạy đúng | "Lớp con thay lớp cha mà không hỏng gì; `toString` `final` giữ khung chung nên lớp con không làm sai được thứ tự cột." |
| **I** | Interface Segregation | `ICandidateSaver` (2 hàm ghi) và `ICandidateFinder` (2 hàm đọc), thay vì 1 interface 4 hàm; `ICandidateRepository` chỉ gộp hai cái cho nơi lắp ráp | "`CandidateService` chỉ thấy hàm ghi, `CandidateSearchService` chỉ thấy hàm đọc, không lớp nào bị ép phụ thuộc hàm nó không dùng." |
| **D** | Dependency Inversion | 2 service nhận **interface** (`ICandidateSaver`, `ICandidateFinder`, `ISearchStrategy`) qua constructor; controller giữ kho bằng biến kiểu `ICandidateRepository`; `CandidateController()` là nơi duy nhất `new` lớp cụ thể | "Controller và service phụ thuộc interface của repository; muốn lưu file thì viết `FileCandidateRepository implements ICandidateRepository` và sửa 1 dòng controller." |

---

## 4. Code từng bước

> Thầy: *"code cái **model trước rồi đến data**"*.

| Bước | File | Việc |
|---|---|---|
| 1 | `constants/CandidateType.java`, `GraduationRank.java` | 2 enum — model cần nên gõ trước; `findByCode`, `findByMenuChoice`, `findByLabel` |
| 2 | `model/Candidate.java` | `abstract`, 7 field `private`, get/set, `getFullName`, `getSummary` (`String.format`), `abstract getCandidateType/getExtraInfo`, `final toString` |
| 3 | `model/Experience.java`, `Fresher.java`, `Intern.java` | `extends Candidate`, field riêng + get/set, `@Override` 2 hàm abstract |
| 4 | `dto/CandidateRequestDTO`, `CandidateResponseDTO` | JavaBean; ResponseDTO có `message`, `candidateMap`, `foundList` |
| 5 | `repository/ICandidateSaver`, `ICandidateFinder`, `ICandidateRepository` → `CandidateRepository` | 3 interface nhỏ (tên bắt đầu bằng `I`), 1 lớp implement |
| 6 | `service/CandidateCreator` → 3 Creator → `CandidateFactory` | Factory Method `buildCandidate` |
| 7 | `service/CandidateService` | kiểm id trống / trùng, tên trống → factory → saver → trả ResponseDTO có `message` |
| 8 | `service/ISearchStrategy` → `NameSearchStrategy` → `CandidateSearchService` | `getAllCandidates`, `getCandidateNames`, `searchCandidate` |
| 9 | `view/CandidateView` | field `responseDTO` + `setResponseDTO` + `display()` **không tham số** (in phần nào khác `null`) |
| 10 | `controller/CandidateController` | constructor lắp ráp **một** kho (`ICandidateRepository`) cho **hai** service; mỗi hàm: service → `setResponseDTO` → `display()` **1 lần** |
| 11 | `constants/Message`, `Constants` | gõ dần khi cần; mỗi hằng một dòng comment, cách nhau 1 dòng trống |
| 12 | `utils/Validation` | các hàm `check*` / `get*` ném `Exception(Message…)`; `final` + private constructor |
| 13 | `main/Main` | `final` + private constructor; menu; case 1-2-3 **dùng chung một luồng** (`do … while`); case 4 = 2 luồng; `inputExtraInfo` switch theo loại |

**Bẫy hay gặp**

1. Phone kiểu `int`: mất số **0** đầu, và 10 chữ số tràn `int`. Phải để `String`.
2. Viết cứng năm `2026` trong luật năm sinh, sang năm là sai. Dùng `Year.now()`.
3. 3 `ArrayList` riêng cho 3 loại thì kiểm "id trùng" phải quét 3 list. Bài này dùng một list + `findByType`.
4. `instanceof` rải khắp nơi thay vì hỏi `getCandidateType()`: thêm loại mới là quên sửa chỗ nào đó.
5. Search với tên trống: `contains("")` luôn đúng, nên in ra **tất cả**. Service chặn bằng `Candidate name cannot be empty.`

---

## 5. Test trước khi gọi thầy

> Thầy: *"test tất cả các happy case cũng như hiển thị đủ các message validation"*.

| # | Chọn | Gõ | Phải thấy |
|---|---|---|---|
| 1 | menu | `abc`, `9` | `You must input a number.` · `Please choose from 1 to 5.` |
| 2 | 4 khi chưa có ai | — | `The candidate list is empty.` |
| 3 | 1 | năm sinh `1899`, năm sau, `19` | `Birth date must be a number of 4 digits from 1900 to the current year.` và hỏi lại; **năm nay** thì nhận |
| 4 | 1 | phone `abcdefghij`, `091234567` (9 số) | `Phone must be a number with at least 10 digits.` |
| 5 | 1 | email `a.fpt.edu.vn`, `a@fpt..vn` | `Email must have the format <account name>@<domain> (eg: annguyen@fpt.edu.vn).` |
| 6 | 1 | kinh nghiệm `101`, `abc` / `0`, `100` | `Year of experience must be a number from 0 to 100.` / nhận |
| 7 | 2 | hạng `Average` / `good` | `Rank of graduation must be one of: …` / nhận, in `Good` |
| 8 | 3 | semester `abc` | `You must input a number.` |
| 9 | 1 | ID trống · ID `e01` khi đã có `E01` · First/Last name trống | `Candidate id cannot be empty.` · `Candidate id [e01] already exists.` · `First name cannot be empty.` / `Last name cannot be empty.` |
| 10 | tạo xong | `x`, rồi `y` / `n` thường | `Please enter Y or N.`; chữ thường vẫn nhận |
| 11 | N | — | `List of candidate:` + 3 dòng `=====…=====` + dòng chi tiết đủ cột |
| 12 | 4 | — | danh sách **chỉ tên** theo nhóm, **một dòng trống** (như đề), rồi `Input Candidate name (First name or Last name):` |
| 13 | 4 | tên trống | `Candidate name cannot be empty.` |
| 14 | 4 | loại `x` / `5` | `You must input a number.` / `Please choose from 0 to 2.` |
| 15 | 4 | `NGUY`, loại `2` | **một dòng trống**, `The candidates found:` + dòng 6 cột (khớp **Last name**, không phân biệt hoa thường) |
| 16 | 4 | `Zorro` | `No candidate found.` |

---

## 6. Debug

| Việc | Cách làm |
|---|---|
| Thấy đa hình | breakpoint trong `CandidateRepository.findByType` ở `if (candidate.getCandidateType() == type)` → **F7** → nhảy vào `Experience.getCandidateType()` **hoặc** `Intern.getCandidateType()` tuỳ đối tượng thật |
| Thấy Factory Method | breakpoint `return creator.createCandidate(requestDTO);` trong `CandidateFactory` → Variables: `creator` là `FresherCreator` khi chọn 2 → **F7** vào `buildCandidate` của đúng lớp đó |
| Thấy Template Method | breakpoint trong `Candidate.toString()` → **F7** vào `getExtraInfo()` → vào bản của lớp con |
| Regex | breakpoint trong `Validation.checkEmail`, gõ `a@fpt..vn`, xem `text.matches(...)` = `false` |

---

## 7. Câu hỏi thầy hay hỏi

### OOP

| Câu hỏi | Trả lời mẫu |
|---|---|
| 4 tính chất OOP ở đâu? | **Đóng gói**: field `private` + get/set, phone là `String`. **Kế thừa**: `Experience/Fresher/Intern extends Candidate`; 3 Creator `extends CandidateCreator`. **Đa hình**: `ArrayList<Candidate>` chứa 3 loại, `getCandidateType()`/`getExtraInfo()` chạy bản của lớp thật. **Trừu tượng**: `Candidate`, `CandidateCreator` abstract; `ICandidateSaver`, `ICandidateFinder`, `ICandidateRepository`, `ISearchStrategy` là interface. |
| Abstract class khác interface? | Abstract class **có** field và code chung (7 field, `getSummary`, `toString`), chỉ được `extends` **một**. Interface chỉ là hợp đồng, một interface `extends` **nhiều** được (`ICandidateRepository extends ICandidateSaver, ICandidateFinder`), và `CandidateRepository implements ICandidateRepository`. |
| Overload khác override? | Override: lớp con viết lại hàm cha **cùng chữ ký** (`getExtraInfo`, có `@Override`), chọn lúc **chạy**. Overload: cùng tên, **khác tham số**, chọn lúc **biên dịch**. |
| Sao `toString` `final` mà `getExtraInfo` `protected abstract`? | `final`: khung chung không ai sửa được. `protected`: chỉ lớp con viết/gọi; `abstract`: bắt buộc lớp con phải viết. |
| Sao `CandidateType` là enum mà không `int`? | `int` thì gõ `7` vẫn lọt; enum là tập **đóng** 3 giá trị, mỗi hằng mang luôn mã, số menu, nhãn, banner. |

### Access modifier, static, tham số

| Câu hỏi | Trả lời mẫu |
|---|---|
| `static` ở đâu? | `Validation` (Guide bắt), hằng trong `Message`/`Constants`, `CandidateType.findByCode/findByMenuChoice` và `GraduationRank.findByLabel` (làm việc trên **cả** enum, không trên một hằng), và **hàm** trong `Main`. Không có biến static. |
| Constructor của enum? | Luôn `private` (ngầm định); không ai `new CandidateType(...)` được. |
| Hàm nào quá 2 tham số? | Chỉ `Validation.getChoice(input, min, max)` (utils được 3) và constructor enum `CandidateType(code, menuChoice, label, banner)` (constructor được miễn). Còn lại gói vào `CandidateRequestDTO`. Vì thế vòng tạo ứng viên (cần `sc`, `controller` **và** loại) viết thẳng trong `case` của `main` bằng `do … while`, không tách hàm 3 tham số. |
| `protected` dùng ở đâu, vì sao? | `Candidate()` và `CandidateCreator()` (chỉ lớp con gọi), `getExtraInfo()` và `buildCandidate()` (bước lớp con điền). |

### Kiến trúc & thuật toán

| Câu hỏi | Trả lời mẫu |
|---|---|
| Sao lại có `switch` theo loại trong `Main.inputExtraInfo` — trái OCP? | Mỗi loại hỏi **câu khác nhau**, mà Scanner chỉ được ở Main, nên đây là chỗ **duy nhất** phải biết loại. Phần tạo đối tượng và phần in thì không có `switch` nào. |
| Năm sinh kiểm thế nào? | `matches("\\d{4}")` (đúng 4 chữ số) rồi `1900 ≤ năm ≤ Year.now()`. |
| Email regex đọc sao? | `[^@\s]+` (tên tài khoản: không `@`, không cách) `@` `[^@\s.]+` (tên miền) `(\.[^@\s.]+)+` (ít nhất một đuôi `.edu`, `.vn`…). Nên `a@fpt..vn` sai vì có đoạn rỗng giữa 2 dấu chấm. |
| Độ phức tạp tìm kiếm? | O(n): duyệt một lượt danh sách, mỗi người `contains` 2 lần. |
| Sao hai service mà không một? | **S**: tạo và tìm là hai lý do đổi khác nhau; và **I**: mỗi service chỉ cần một nửa kho. |
| Sao bài có repository? | Tờ checklist 1.1: *"Bắt buộc phải có repository"*. `CandidateRepository` chỉ giữ `ArrayList<Candidate> candidateList` và CRUD đơn giản (`isExistId`, `addCandidate`, `isEmpty`, `findByType`); luật nằm ở service. |
| View nhận dữ liệu thế nào? | Qua **thuộc tính**: `private CandidateResponseDTO responseDTO` + `setResponseDTO(...)`, rồi `display()` **không tham số** — đúng code mẫu của thầy. `display()` in `message`, rồi `candidateMap` (tiêu đề + banner + dòng), rồi `foundList`; phần nào `null` thì bỏ. Hết `showMessage(String)`, `displayDetails/displayNames/displayFound`. |
| Validate ở đâu? | **Dạng** dữ liệu (năm sinh, phone, email, 0..100, hạng, số, Y/N) ở `Main` qua `utils/Validation`, hỏi lại ngay ô sai. Luật cần **cả danh sách** (id trống/trùng, tên trống, tên tìm trống, không ai khớp) ở service, `throw new Exception(Message.X)`, `Main` in `e.getMessage()`. |
| Case 4 gọi controller 2 lần — trái luật "1 lần/luồng"? | Đề bắt in **danh sách trước**, rồi mới hỏi tên + loại, rồi in kết quả: có 2 lần hiện kết quả xen giữa lần nhập, nên là **2 luồng liền nhau**, mỗi luồng 1 lần gọi, 1 lần render (`displayCandidateNames`, `searchCandidate`). Muốn đúng 1 lần gọi thì phải in danh sách **sau** khi hỏi — lệch màn hình đề (xem mục "Hỏi thầy"). |
| Case 1-3 gọi controller mấy lần? | Mỗi **vòng** tạo (một ứng viên) là 1 luồng: `createCandidate` 1 lần. Trả lời `N` là luồng hiển thị: `displayAllCandidates` 1 lần. Không hàm controller nào gọi view 2 lần. |
| Sao controller import `repository`? | Controller là **nơi lắp ráp** duy nhất: `ICandidateRepository candidateRepository = new CandidateRepository();` rồi đưa cho 2 service. Nó không gọi hàm nào của kho; mọi việc đi qua service. |

---

## 8. Thầy đổi yêu cầu tại chỗ

| Thầy bảo | Sửa file | Không phải đụng |
|---|---|---|
| Thêm loại **Senior** (type 3) | `model/Senior extends Candidate` + `service/SeniorCreator` + 1 dòng `register` trong `CandidateFactory` + 1 hằng `SENIOR(3, 4, …)` trong `CandidateType` + 1 case trong `Main.inputExtraInfo` + menu | `CandidateService`, `CandidateSearchService`, `CandidateView`, repository |
| Tìm theo phone / email | `service/PhoneSearchStrategy implements ISearchStrategy` + 1 dòng controller | service, view |
| Sắp kết quả theo năm sinh | `CandidateSearchService.searchCandidate`: `Collections.sort(found, comparator)` | view |
| Update / Delete theo id | `ICandidateSaver` thêm `updateCandidate/deleteCandidate` + repository + `CandidateService` + controller (1 lần render) + case menu | search, factory |
| Graduation date phải dạng `dd/MM/yyyy` | `Validation.checkGraduationDate` với `SimpleDateFormat` + `setLenient(false)` + `Message` | model, service |
| Semester phải > 0 | `Validation` thêm `checkSemester` + `Message` | service |
| Lưu vào file | `Main` đọc tệp qua `utils/FileUtils.readLines` → `CandidateRequestDTO.lineList` → `controller.loadData` → repository parse + lưu (tờ checklist: đọc file ở **Main**); ghi tệp: `FileUtils.writeLines` do repository gọi; hoặc lớp mới `FileCandidateRepository implements ICandidateRepository` + 1 dòng controller | cả hai service |

---

## 9. Chỗ khác với đề / bản cũ

| Chỗ | Đề / bản cũ | Bài này | Lý do |
|---|---|---|---|
| Menu | đề in `- Experience`… (mất số) | `1. Experience`… + câu ` (Please choose 1 …)` chép đúng — **kể cả dấu cách đầu dòng** như đề | số là thứ người dùng gõ |
| Birth Date | đề gọi là "date" | lưu **năm** (`int`) | đề định nghĩa: *"number with length is 4 character (1900..Current Year)"* |
| Kho | Guidelines: *"ArrayList to store Fresher Candidate"* | **một** `ArrayList<Candidate>` cho cả 3 loại | đề chung: *"Create Candidate and store in ArrayList"*; một list thì kiểm id trùng một lần |
| Ví dụ kết quả tìm | phone mẫu `940394` (6 số) | phone phải ≥ 10 số | luật của đề mạnh hơn dữ liệu minh hoạ |
| Tiêu đề `---------- Create … ----------`, câu `… has been created.` | đề không ghi | có | người dùng biết mình đang ở đâu và đã lưu chưa; giữ đúng bản tham chiếu |
| Graduation date, semester | đề không nêu luật | graduation date là chữ tự do; semester chỉ bắt là số | không bịa luật đề không có (mục 8 nếu thầy muốn) |
| Kiến trúc | bản cũ: `entity/bo/ui` | MVC Guide + Factory Method + Template Method + Strategy + 2 interface ISP | thầy bắt đủ SOLID |
| Màn hình Search | bản tham chiếu: `Input type of candidate: The candidates found:` và câu hỏi tên dính ngay dưới danh sách | **một dòng trống** sau danh sách tên, **một dòng trống** trước `The candidates found:` | màn hình đề có đúng hai dòng trống đó (đối chiếu `.docx` ngày 22/09) |
| Câu dưới menu | bản tham chiếu: `(Please choose …` | ` (Please choose …` (dấu cách đầu dòng) | đề viết như vậy, từng ký tự |

### Chỗ khác với bản trước (21/09 → 22/09, theo tờ checklist giấy)

| Chỗ | Bản trước | Bản này | Lý do (tờ checklist) |
|---|---|---|---|
| View | `setCandidateMap`/`setFoundList` + `displayDetails()`, `displayNames()`, `displayFound()`, `showMessage(String)` | **một** thuộc tính `responseDTO` + `setResponseDTO` + `display()` không tham số | 1.1: *View nhận qua thuộc tính*, *render 1 lần/luồng* |
| ResponseDTO | 1 dòng ứng viên (`fullName`, `summary`, `detail`) | câu trả lời của cả luồng: `message`, `candidateMap`, `foundList` | 1.1 *"Nên để ResponseDTO giống ví dụ"*; service dựng sẵn chuỗi, view chỉ in |
| Controller | `createCandidate` gọi `showMessage`; tự dựng câu thành công | service trả `CandidateResponseDTO`, controller chỉ `setResponseDTO` + `display()` 1 lần | 1.1 |
| Interface | `CandidateSaver`, `CandidateFinder`, `SearchStrategy` | `ICandidateSaver`, `ICandidateFinder`, `ISearchStrategy` + mới `ICandidateRepository` (controller giữ kho qua interface) | 1.3 *"Tên của interface bắt đầu bằng I"*; DIP |
| Tên method | `fromCode`, `fromMenuChoice`, `fromText`, `newCandidate`, `matches`, `Main.submitCandidate` | `findByCode`, `findByMenuChoice`, `findByLabel`, `buildCandidate`, `isMatch`, `Main.createCandidate` | 1.4 method bắt đầu bằng động từ |
| Tên biến | `result`, `found`, `groups`, `rows`, `candidates`, `dto` | `typeList`, `foundList`, `candidateMap`, `lineList`, `candidateList`, `requestDTO` | 1.5 List/Map |
| `Main` | `public class Main`, `int choice = …` / `String line = …` khai báo giữa vòng lặp | `public final class Main` + `private Main()`; mọi biến khai báo đầu block và khởi tạo (`String line = ""`, `int choice = 0`) | 3.4, 2.6, 3.7 |
| Nối chuỗi | `getSummary()`, `toString()`, `getExtraInfo()` cộng bằng `+` | `String.format(Constants.*_FORMAT, …)` | 3.8 |
| Định dạng | field/hằng dính nhau, `if (a < b \|\| c > d)`, `\|\|` đầu dòng | 1 dòng trống trước mọi comment/giữa field; `if ((a < b) \|\| (c > d))`; xuống dòng **sau** `\|\|` | 2.8, 3.3, 2.3 |

---

## 10. Tờ checklist 25 mục — bài này đạt thế nào

`python3 _tools/soat_checklist.py HE176322_J1LP0022_CandidateManagement` → **0 VI PHẠM**; còn lại chỉ "rủi ro" chấp nhận được: `String[] args` của `main` và tham số setter/constructor trùng tên field (`this.x = x`).

| Mục | Chỉ vào đâu |
|---|---|
| **1.1** MVC + repository | `repository/CandidateRepository` (bắt buộc có) sau 3 interface `ICandidate…`; `CandidateController` không import `model`, mỗi hàm `setResponseDTO` + `display()` **1 lần**; `CandidateView` nhận `responseDTO` qua setter; `Main` nhập + validate, mỗi luồng gọi controller 1 lần (case 4 = 2 luồng, lý do ở mục 3) |
| **1.3 / 1.4** tên | interface `ICandidateSaver`, `ICandidateFinder`, `ICandidateRepository`, `ISearchStrategy`; method đều mở đầu bằng động từ (`findByCode`, `buildCandidate`, `isMatch`…) |
| **1.5** tên collection / Id | `candidateList`, `typeList`, `foundList`, `lineList`, `candidateMap`, `creatorMap`; không tên nào chứa `ID` (chỉ hằng `ID_EMPTY`, `ID_EXISTS`) |
| **2.6 / 3.7** khai báo đầu block + khởi tạo | `Main.main`: `requestDTO = null`, `candidateType = null`, `running = true`, `choice = 0`; mọi `Main.inputX`: `String line = ""` rồi trong vòng chỉ gán; `Validation.checkBirthDate`: `int year = 0`; `checkExperience`: `int years = 0` |
| **2.8** dòng trống | giữa các hằng/field (cả enum, `Message`, DTO), sau vùng khai báo biến, trước mọi comment đứng sau dòng code, giữa các `case`, sau `}` trước câu lệnh tiếp |
| **3.3** ngoặc | `Validation.getChoice`: `if ((choice < min) \|\| (choice > max))`; `checkBirthDate`, `checkExperience` tương tự |
| **3.4** lớp chỉ có static | `Main`, `Validation`, `Constants`, `Message`: `final` + `private` constructor |
| **3.8** cộng chuỗi | mọi dòng in dựng bằng `String.format(Constants.SUMMARY_FORMAT / DETAIL_FORMAT / …)` |

## 11. Hỏi thầy

- **Searching (case 4) = 2 luồng.** Đề in danh sách tên **trước** khi hỏi tên + loại, rồi mới in kết quả, nên case 4 gọi controller 2 lần (`displayCandidateNames`, `searchCandidate`), mỗi lần render 1 lần. Nếu thầy bắt đúng 1 lần gọi cho case 4 thì chỉ còn cách in danh sách **sau** khi hỏi — lệch màn hình đề. Nên hỏi thầy muốn theo cách nào.
- Dòng trống và dấu cách đầu câu `(Please choose…` là chép **đúng từng ký tự** của đề (`.docx`); nếu thầy thấy thừa thì xoá dấu cách đầu chuỗi trong `Message.MENU` và `\n` đầu `INPUT_SEARCH_NAME`/`FOUND_TITLE`.
