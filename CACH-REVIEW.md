# Cách qua review LAB211 — cẩm nang dùng chung cho mọi bài

> Đọc cùng `QUY-TAC-THAY.md` (luật có nguồn). File này là **cách làm**: trước, trong và sau khi
> giơ tay xin review. Mỗi bài còn có `HUONG-DAN.md` riêng với câu hỏi sát bài đó.

---

## 1. Năm cửa — trượt cửa nào là dừng ở cửa đó

Thầy review **theo thứ tự**. Sai cửa 1 thì thầy không xem tới cửa 2.

| Cửa | Thầy kiểm gì | Tự kiểm trước thế nào |
|---|---|---|
| **1. Cấu trúc MVC** | có đủ package theo Guide không | mở cây project: `constants · controller · dto · main · model · repository · (service) · utils · view` — **repository bắt buộc** (tờ checklist 1.1) |
| **2. Convention** | tên class/hàm/biến, thụt lề, ngoặc | **Alt+Shift+F** từng file; tên class là danh từ PascalCase, hàm là động từ camelCase |
| **3. Comment** | mỗi hàm + mỗi `if`/`for`/`switch`/`case`/`try` có comment | lướt từng file: khối nào không có dòng `//` ở trên là thiếu |
| **4. Debug** | đặt breakpoint, đi từng bước, đọc biến | tập trước theo mục 6 của `HUONG-DAN.md` bài đó |
| **5. Test** | đủ happy case + **đủ mọi thông báo validation** | đi hết bảng mục 5 của `HUONG-DAN.md` bài đó |

Sau 5 cửa mới đến **vấn đáp** (mục 3 dưới đây).

---

## 2. Quy trình một buổi lab (1 slot ≈ 90 phút)

| Phút | Việc |
|---|---|
| 0–5 | Đọc **lại** đề trên PTS. Gạch chân: tên hàm bắt buộc, thông báo lỗi, định dạng màn hình |
| 5–15 | Tạo project `HE176322_<Mã>_<Tên>` · tạo đủ package · gõ **model trước** (thầy dặn) |
| 15–50 | Gõ theo bảng "Code từng bước" của bài · **F6 chạy sau mỗi 2–3 file** — đừng để lỗi dồn |
| 50–65 | **Alt+Shift+F** toàn bộ · thêm comment còn thiếu · đi bảng test |
| 65–75 | **Save draft** trên PTS (file zip, < 10MB, xoá `build/` `dist/` trước khi nén) |
| 75–90 | Giơ tay xin review — **trước khi hết giờ 30 phút** (thầy chỉ nhận trước mốc đó) |

> Thầy chỉ review **tối đa 3 bài / sinh viên / slot**. Chọn **tối đa 5 bài** một lúc trên PTS.
> Chỉ bấm **SUBMIT** khi thầy đã nói đạt — submit rồi là không sửa được nữa.

---

## 3. Ngân hàng câu hỏi vấn đáp — trả lời được hết là qua

### 3.1 Bốn tính chất OOP — thầy bắt **chỉ vào code**

| Tính chất | Định nghĩa 1 câu | Chỉ vào đâu (mọi bài đều có) |
|---|---|---|
| **Encapsulation** — đóng gói | gói dữ liệu + hành vi vào class, **che** dữ liệu, chỉ mở qua hàm | field `private` trong `model/` + getter/setter |
| **Inheritance** — kế thừa | lớp con dùng lại thuộc tính/hàm của lớp cha (`extends`) | lớp con `extends` lớp cha; mọi lớp ngầm `extends Object` |
| **Polymorphism** — đa hình | cùng một lời gọi, chạy bản khác nhau tuỳ đối tượng thật | `@Override toString()`; biến kiểu cha/interface gọi hàm của lớp con |
| **Abstraction** — trừu tượng | chỉ để lộ "làm gì", giấu "làm thế nào" | `interface`/`abstract class`; `Main` gọi `controller.xxx(dto)` mà không biết dữ liệu nằm đâu |

**Overloading vs Overriding** (hay bị hỏi kèm):

| | Overloading | Overriding |
|---|---|---|
| Ở đâu | **cùng** một class | lớp **con** so với lớp cha |
| Chữ ký | cùng tên, **khác** tham số | **giống hệt** tên + tham số |
| Quyết định lúc | biên dịch | chạy |
| Ví dụ | 2 constructor của `Doctor` | `toString()` có `@Override` |

### 3.2 Access modifier — thầy **reject nhiều nhất** ở đây

| Modifier | Ai thấy được | Trong bộ lời giải dùng khi |
|---|---|---|
| `private` | chỉ chính class đó | **mọi field**; hàm phụ chỉ class đó dùng; constructor của `Message`/`Constants`/`Validation` |
| *(default)* | các class **cùng package** | không dùng — không có lý do trong bài console |
| `protected` | cùng package **+ lớp con** | chỉ khi lớp con thật sự cần, hoặc **đề bắt** (P0052 `Country`) |
| `public` | mọi nơi | hàm là "hợp đồng" để lớp khác gọi (controller gọi repository, main gọi controller) |

Câu trả lời mẫu cho *"sao field này private?"*: **"Để không lớp nào sửa thẳng được — muốn đổi phải đi
qua hàm có kiểm tra. Nếu để public thì ai cũng gán bậy được, ví dụ availability = -5."**

### 3.3 `static` — thầy reject khi dùng linh tinh

| Câu hỏi | Trả lời mẫu |
|---|---|
| Static là gì? | Thuộc về **lớp**, không thuộc về đối tượng: một bản duy nhất, gọi qua tên lớp `Validation.getChoice(...)`. |
| Sao `Validation` dùng static? | Hàm **không dùng dữ liệu riêng** của đối tượng nào: cùng đầu vào → cùng kết quả. Guide của thầy ghi *"phải dùng static method"*. |
| **Bỏ static đi thì sao?** | Lời gọi `Validation.getChoice(...)` báo lỗi biên dịch *"non-static method cannot be referenced from a static context"*. |
| **Không dùng static thì sửa thế nào cho chạy?** | Bỏ `private` ở constructor của `Validation`, trong `main()` tạo `Validation validation = new Validation();`, rồi gọi `validation.getChoice(...)`. |
| Sao `main()` phải static? | JVM gọi `main` **trước khi** có đối tượng nào — nên nó phải thuộc về lớp. |
| Sao hàm phụ trong `Main` static? | Được gọi từ `main()` (static) mà không tạo đối tượng `Main`. Thầy cho phép: *"cấm static với biến, có thể dùng với hàm"*. |
| Sao `Main` không có biến static? | Luật Guide. Biến static là **trạng thái dùng chung toàn chương trình**, ai cũng sửa được — khó kiểm soát. Scanner để là biến **cục bộ** và truyền vào hàm. |
| Sao model/controller không static? | Guide cấm. Mỗi đối tượng (mỗi bác sĩ, mỗi controller) có **dữ liệu riêng**; static sẽ làm mọi đối tượng dùng chung một bản. |

### 3.4 Kiểu trả về — "tại sao trả về void / String / boolean?"

| Kiểu | Khi nào | Ví dụ trả lời |
|---|---|---|
| `void` | hàm **làm việc**, không cần báo kết quả (kết quả đã in ra qua view, lỗi đi bằng `throw`) | `controller.addDoctor(dto)` |
| `boolean` | nơi gọi chỉ cần **có/không** | `isExistDoctor(code)` |
| `int` / `double` | kết quả là **một con số** tính được | `calcTotal(bills)` |
| `String` | kết quả là **chữ** để hiển thị / kiểm tra | `toString()`, hàm kiểm tra trả thông báo lỗi |
| Đối tượng / DTO | nhiều giá trị đi cùng nhau | `SortResponseDTO` chứa mảng trước + sau |
| `Integer` (không phải `int`) | cần giá trị **"không có"** (`null`) | availability để trống khi update |

### 3.5 Collection — "sao dùng ArrayList mà không phải List?"

| | `List` / `Map` | `ArrayList` / `HashMap` / `LinkedHashMap` |
|---|---|---|
| Là gì | **interface** — bản hợp đồng (có `add`, `get`...) | **lớp cài đặt** hợp đồng đó |
| Tạo đối tượng được không | ❌ `new List()` lỗi | ✅ |
| Đặc điểm | — | `ArrayList`: mảng động, lấy theo chỉ số nhanh · `HashMap`: tra theo khoá nhanh, **không giữ thứ tự** · `LinkedHashMap`: như HashMap **+ giữ thứ tự nhập** |

Thầy nói trên lớp: dùng `List`/`Map` là dấu hiệu **AI viết** → bộ lời giải khai báo **kiểu cụ thể**.
Chỗ nào **đề bắt** `List` trong chữ ký thì giữ và có comment `// brief:` — nói được: *"đề bắt kiểu
trả về List, bên trong em dùng ArrayList"*.

### 3.6 Không truyền 3 tham số vào 1 hàm

Thầy nói trên lớp. Trả lời: **"Nhiều dữ liệu thì em gói vào DTO. Thêm một trường chỉ phải sửa DTO,
chữ ký hàm không đổi, nên controller không phải sửa."** (constructor của model được phép nhiều tham
số — đúng như mẫu `Doctor(code, name, specialization, availability)` trong Guide của thầy).

### 3.7 SOLID — nhớ bằng một câu mỗi chữ

| | Nguyên lý | Một câu | Chỉ vào bài |
|---|---|---|---|
| **S** | Single Responsibility | mỗi lớp **một lý do để sửa** | đổi câu chữ chỉ sửa `Message`; đổi cách lưu chỉ sửa `Repository` |
| **O** | Open/Closed | **thêm** code mới, không **sửa** code cũ | thêm thuật toán = thêm 1 class Strategy |
| **L** | Liskov Substitution | lớp con **thay được** lớp cha mà không làm sai chương trình | mọi `Shape` con đều tính được `getArea()` |
| **I** | Interface Segregation | interface **nhỏ, đúng việc** | `SortStrategy` chỉ 1 hàm `sort` |
| **D** | Dependency Inversion | phụ thuộc **trừu tượng**, không phụ thuộc lớp cụ thể | `SortService` nhận `SortStrategy` qua constructor |

Ví dụ kinh điển trong slide của thầy: **Square kế thừa Rectangle vi phạm L** (đặt width=5, height=10
mong diện tích 50, Square ra 100). Cách sửa: cả hai cùng implement một interface `Shape`.

### 3.8 Design Pattern — **thứ thầy đánh giá cao nhất**

Trình bày một pattern bằng **4 yếu tố** (slide thầy: *"Elements of a Design Pattern"*):

| Yếu tố | Trả lời câu hỏi |
|---|---|
| **Name** | tên pattern, thuộc nhóm nào (Creational / Structural / Behavioral) |
| **Problem** | bài của em gặp vấn đề gì nếu KHÔNG dùng nó |
| **Solution** | lớp nào đóng vai gì (Context, Strategy, ConcreteStrategy…) |
| **Consequences** | được gì (thêm tính năng không sửa code cũ) và mất gì (nhiều lớp hơn) |

Ba nhóm (slide thầy):

| Nhóm | Lo việc | Pattern có trong bộ lời giải |
|---|---|---|
| **Creational** | tạo đối tượng | Factory Method · Builder · Singleton |
| **Structural** | ghép lớp/đối tượng | Facade (controller) · Adapter |
| **Behavioral** | chia trách nhiệm/hành vi | Strategy · Template Method · Chain of Responsibility |

> **Đừng nhét pattern cho có.** Ghi chú slide SOLID của thầy cảnh báo *"trừu tượng hoá sớm … vi phạm
> YAGNI"*. Mỗi pattern trong bộ lời giải đều trả lời được câu "nếu không có nó thì bài gặp vấn đề gì".

### 3.9 Câu hỏi "bẫy"

| Thầy làm | Phải xảy ra |
|---|---|
| gõ `abc` vào chỗ nhập số | báo lỗi lịch sự + **hỏi lại**, không văng stack trace đỏ |
| Enter trống | báo lỗi, hỏi lại |
| nhập số âm / số 0 / số quá lớn | báo đúng thông báo của đề |
| thêm trùng mã | báo trùng, không thêm |
| xoá file dữ liệu rồi chạy lại | chương trình vẫn chạy (tạo file mới / báo rỗng) |
| *"dòng này chép ở đâu?"* | giải thích được từng dòng — **không biết mới là lỗi**, chép thì không |
| *"đổi yêu cầu X, sửa mất bao lâu?"* | trả lời bằng **tên file cần sửa** (mục 8 của mỗi `HUONG-DAN.md`) |

---

## 4. Debug trong NetBeans — cửa số 4

| Phím | Tác dụng |
|---|---|
| click lề trái số dòng | đặt / bỏ **breakpoint** (chấm đỏ) |
| **Ctrl+F5** | chạy ở chế độ debug |
| **F8** | Step Over — chạy dòng hiện tại, **không** chui vào hàm |
| **F7** | Step Into — **chui vào** hàm đang gọi |
| **Ctrl+F7** | Step Out — chạy hết hàm hiện tại, quay ra chỗ gọi |
| **F5** | Continue — chạy tới breakpoint kế |
| **Shift+F5** | dừng debug |
| tab **Variables** | xem giá trị biến; bấm ▸ để mở đối tượng / mảng / map |

**Kịch bản debug mẫu (áp dụng cho mọi bài có validation):**
1. Breakpoint ở dòng gọi `Validation.xxx(sc.nextLine())` trong `Main`.
2. **Ctrl+F5**, gõ dữ liệu **sai**.
3. **F7** vào `Validation` → **F8** tới dòng `throw` → chỉ cho thầy biến đang giữ giá trị sai.
4. **F8** tiếp → nhảy vào `catch` ở `Main` → in thông báo → vòng lặp hỏi lại.

---

## 5. Trước khi giơ tay — tờ checklist 25 mục

Thầy review bằng tờ **"Coding check sheet"** giấy: tự soát, điền **"O"** từng mục, **cả cột "O" mới xin
review**. 25 mục nguyên văn: [`TO-CHECKLIST-THAY.md`](TO-CHECKLIST-THAY.md). Những dòng hay trượt nhất:

- [ ] Tên project `HE176322_<Mã>_<Tên>`; có đủ package, **có `repository/`** (1.1, 1.2)
- [ ] `Scanner` chỉ ở `main`; **Main nhập + validate** qua `utils`; mỗi `case` gọi controller **1 lần** (1.1)
- [ ] Controller không import `model`, không `System.out`; View có field `ResponseDTO` + setter +
      `display()` **không tham số**, gọi **1 lần/luồng** (1.1)
- [ ] Mọi câu chữ ở `Message`, mọi số/regex ở `Constants` (2.10, 2.11)
- [ ] Mọi field `private`; `static` chỉ ở `utils`, `constants`, hàm trong `main`; class chỉ có hàm
      static (kể cả `Main`) là `final` + constructor `private` (3.4)
- [ ] Tên: `…List` / `…Set` / `…Map` / `…Array`, `Id` không `ID`, interface `I…`, exception `…Exception` (1.3, 1.5)
- [ ] Khai báo **đầu block** và **khởi tạo luôn** (`String line = "";`) (2.6, 3.7)
- [ ] **Dòng trống** trước mọi comment, sau vùng khai báo, giữa các khối — Alt+Shift+F không làm hộ (2.8)
- [ ] Ngoặc quanh từng phép so sánh: `if ((a < min) || (a > max))` (3.3); không `String +=` (3.8)
- [ ] Mỗi hàm (cả getter/setter) + mỗi if/else/switch/case/for/while/try/catch có comment (1.6)
- [ ] **Alt+Shift+F** tất cả file · dòng ≤ 100 ký tự (2.1, 2.3)
- [ ] Đi hết bảng test: mọi happy case + mọi thông báo lỗi của đề
- [ ] Tập 1 lần debug theo mục 4, và trả lời thành tiếng 5 câu ở mục 3

---

## 6. Dùng bộ kiểm tự động ở nhà

```bash
cd ~/Documents/Source_Lab211_ForCuongThai
python3 _tools/verify.py J1SP0055              # kiểm 1 bài
python3 _tools/verify.py --netbeans            # kiểm cả 54 bài + build NetBeans
python3 _tools/lint.py HE176322_J1SP0055_DoctorManagement   # chỉ kiểm luật thầy
python3 _tools/soat_checklist.py HE176322_J1SP0070_EbankLogin   # soát 25 mục tờ giấy
```

Tự gõ lại một bài rồi chạy `lint.py` lên project **của em** — nó chỉ ra đúng chỗ thiếu comment, sai
tầng, chuỗi hardcode… trước khi thầy thấy. Rồi chạy `soat_checklist.py`: **0 VI PHAM** là đủ điều kiện
điền "O" cả 25 mục (dòng "rui ro" thì đọc lại và tự quyết).
