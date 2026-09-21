# J1.S.P0055 — Doctor Management

> **Bài đầu tiên thầy bắt làm** — buổi 1, *"theo mẫu, được mở file Guide, không tính LOC"*.
> Làm thật chắc bài này: **mọi bài sau đều chép đúng khung kiến trúc của nó.**

| | |
|---|---|
| Loại / LOC | Short Assignment · 73 LOC · 1 slot |
| Project | `HE176322_J1SP0055_DoctorManagement` |
| Chạy | NetBeans: **File ▸ Open Project** → chọn thư mục → **F6** |
| Lớp chạy | `main.Main` |
| Kiểm tự động | `python3 _tools/verify.py J1SP0055` → 4 kịch bản × 2 locale |

---

## 1. Đề bài nói gì

- Menu 5 mục: **Add · Update · Delete · Search · Exit**.
- Bác sĩ có 4 thông tin: `Code` (String), `Name` (String), `Specialization` (String), `Availability` (int).
- **Add**: code không được trống, không được trùng.
- **Update**: nhập code → không có thì báo lỗi ngay; có thì sửa các ô còn lại, **ô nào để trống thì giữ giá trị cũ**.
- **Delete**: nhập code → có thì xoá, không có thì báo lỗi.
- **Search**: nhập chuỗi → liệt kê bác sĩ có code/tên/chuyên khoa **chứa** chuỗi đó.

**Đề bắt buộc** (mục Guidelines):

| Thứ | Đề viết | Bài này đặt ở |
|---|---|---|
| Hàm `addDoctor` · `updateDoctor` · `deleteDoctor` · `searchDoctor` | *"in startup code"* | `DoctorController` (điều hướng) + `DoctorRepository` (CRUD) |
| Hàm `checkAvailability` (Availability ≥ 0) | *"rewrite checkAvailability"* | `utils/Validation` |
| Thông báo lỗi | `Doctor code [Code] is duplicate` · `Doctor code doesn’t exist` · `Database does not exist` · `Data does not exist` | `constants/Message` — **chép đúng từng chữ** |

---

## 2. Kiến thức cần biết

### 2.1 `HashMap` — vì sao bài này dùng Map

| Thao tác của đề | `HashMap<String, Doctor>` | `ArrayList<Doctor>` |
|---|---|---|
| Kiểm trùng code khi Add | `containsKey(code)` — 1 bước | duyệt cả danh sách |
| Tìm để Update / Delete | `get(code)` / `remove(code)` — 1 bước | duyệt cả danh sách |
| Chặn trùng khoá | **tự động** (khoá là duy nhất) | phải tự viết |
| Giữ thứ tự nhập | ❌ không | ✅ có |

→ 3/4 chức năng tra **theo code**, nên dùng Map. Bài dùng **`LinkedHashMap`** (là một `HashMap`, có
thêm thứ tự nhập) để kết quả tìm kiếm luôn in theo đúng thứ tự đã thêm — và **khai báo đúng kiểu cụ
thể** `LinkedHashMap<String, Doctor>` (thầy nói trên lớp: dùng `List`/`Map` là dấu hiệu AI, thầy sẽ
hỏi vặn — xem câu hỏi ở mục 7).

### 2.2 Tìm "chứa chuỗi" không phân biệt hoa thường

```java
field.toLowerCase().contains(text)   // "Orthopedics" và "orthodontic" đều khớp "ortho"
```

Dữ liệu mẫu của đề có cả `Orthopedics` lẫn `orthodontic` → phải hạ chữ thường **cả hai phía**.

### 2.3 `Integer` thay vì `int` cho ô "có thể để trống"

Khi Update, người dùng được **bỏ trống** Availability. `int` không có giá trị nào nghĩa là "không
nhập" (0 là số hợp lệ!), nên `DoctorRequestDTO` khai `Integer availability` và dùng `null` = bỏ trống.

---

## 3. Thiết kế — đúng khung trong `Guide.xlsx`

```
HE176322_J1SP0055_DoctorManagement/
└── src/
    ├── constants/   Message.java         mọi câu chữ in ra màn hình
    │                Constants.java       số menu, định dạng cột
    ├── dto/         DoctorRequestDTO     code, name, specialization, Integer availability, searchText (main ──► controller)
    │                DoctorResponseDTO    message ("Add doctor successfully."…) + doctorMap: code → dòng bảng (controller ──► view)
    ├── model/       Doctor.java          1 bác sĩ: 4 thuộc tính + get/set + toString() = 1 dòng bảng
    ├── repository/  DoctorRepository     LinkedHashMap<String, Doctor> doctorMap + isExistDoctor/add/update/delete/search
    ├── controller/  DoctorController     điều hướng repository ↔ view (Facade); mỗi luồng render view 1 lần
    ├── view/        DoctorView           field responseDTO + setResponseDTO() + display() KHÔNG tham số
    ├── utils/       Validation.java      kiểm dữ liệu nhập (final + private ctor + static)
    └── main/        Main.java            final + private Main(); menu + Scanner + mọi validate
```

| Lớp | Làm gì | Không được làm (luật Guide) |
|---|---|---|
| `Main` | vòng menu, **đọc bàn phím + validate**, gói dữ liệu vào `DoctorRequestDTO`, in tiêu đề/câu nhắc/lỗi | gọi model/view, biến static |
| `DoctorController` | nhận DTO → gọi repository → gói `DoctorResponseDTO` → view (1 lần) | Scanner, `System.out`, static, import `model` |
| `DoctorRepository` | giữ `LinkedHashMap<String, Doctor>`, thêm/sửa/xoá/tìm | in ra màn hình, đọc bàn phím |
| `Doctor` | mô tả 1 bác sĩ; `toString()` = 1 dòng bảng | Scanner, printf, static |
| `DoctorView` | in bảng kết quả + câu thông báo — nhận qua **thuộc tính** `responseDTO` | tính toán, đọc bàn phím, nhận dữ liệu qua tham số |
| `Validation` | nhận **chuỗi** → trả giá trị sạch hoặc ném lỗi | đọc bàn phím (chỉ nhận chuỗi Main đưa) |

| Câu hỏi thiết kế | Trả lời |
|---|---|
| Sao bài **có repository**? | Tờ checklist 1.1: *"Bắt buộc phải có repository"* — và Guide đặt đúng lớp này: `DoctorRepository` giữ `LinkedHashMap<String, Doctor> doctorMap` + CRUD đơn giản (có không, thêm, sửa, xoá, tìm); **không** in, **không** đọc bàn phím. Bài không có tính toán ngoài CRUD nên không cần `DoctorServices` (Guide: *"Nếu có các tính toán nghiệp vụ ngoài CRUD thì cần thêm class DoctorServices.java"*) → Controller → Repository → Model. |
| View nhận dữ liệu thế nào? | Qua **thuộc tính**, không qua tham số (tờ checklist 1.1): `DoctorView` có field `responseDTO`; controller gọi `setResponseDTO(responseDTO)` rồi `display()` — **1 lần cho 1 luồng**. Add/Update/Delete set `message`; Search set `doctorMap` (rỗng thì view in `No doctor found.`). |
| Validate ở đâu? | Ở **Main** qua `utils/Validation` (tờ checklist 1.1: *"Toàn bộ việc nhập dữ liệu/Validate … thực hiện ở Main"*): `getChoice`, `getNonBlank` (code), `checkAvailability`, `checkOptionalAvailability`. Controller chỉ còn luật **nghiệp vụ** của đề: code trùng, code không tồn tại (`throw new Exception(Message.X)`, `Main` in `e.getMessage()`). |
| Update gọi controller 2 lần? | Đề bắt báo `Doctor code doesn’t exist` **ngay sau khi nhập code**, trước khi hỏi Name. Nên `Main.inputUpdate` gọi `controller.checkExistDoctor(requestDTO)` — lần gọi **chỉ để kiểm** (ném lỗi, **không** render); case 2 vẫn gọi `updateDoctor` **đúng 1 lần**. Các case khác gọi controller đúng 1 lần. |

### 3.1 Design Pattern trong bài

| Pattern | Trong bài | 4 yếu tố GoF, nói gọn |
|---|---|---|
| **MVC** (kiến trúc — thầy gọi là "MVC JSP") | `DoctorController` ~ Servlet · `DoctorView` ~ trang JSP · `Doctor` ~ JavaBean | **Problem**: code nhập/xử lý/in trộn một chỗ, sửa giao diện là vỡ logic. **Solution**: tách 3 vai, dữ liệu đi qua DTO. **Consequences**: đổi cách in chỉ sửa View; nhiều lớp hơn. |
| **Facade** | `DoctorController` | **Problem**: `Main` sẽ phải biết repository, view, thứ tự gọi. **Solution**: controller là **một cửa**: `addDoctor(requestDTO)` tự gọi repository rồi view. **Consequences**: `Main` gọn, không phụ thuộc repository; controller phải giữ đúng vai điều hướng, không ôm nghiệp vụ. |
| **Repository** (mẫu kiến trúc dữ liệu, không thuộc 23 GoF) | `DoctorRepository` | **Problem**: nhiều nơi cùng động vào `Map`. **Solution**: một lớp duy nhất giữ dữ liệu + CRUD. **Consequences**: đổi `HashMap` → `ArrayList` chỉ sửa 1 file (mục 8). |

> Bài đầu (P0055) thầy cho làm **theo mẫu Guide**, nên giữ đúng khung mẫu. Pattern GoF "nặng" (Strategy,
> Factory…) có ở các bài sau — xem P0001 (Strategy).

**Luồng chức năng Add** (đi đúng một chiều, không tắt ngang):

```
Main: inputAdd đọc + validate 4 ô ──► DoctorRequestDTO ──► controller.addDoctor(requestDTO)   (1 lần gọi)
   controller ──► repository.isExistDoctor(code)  → trùng? throw new Exception(DUPLICATE_CODE)
   controller ──► repository.addDoctor(requestDTO) → new Doctor, put vào Map        (Repository → Model)
   controller ──► responseDTO.setMessage(ADD_SUCCESS) ──► view.setResponseDTO + view.display()  (1 lần)
Main: catch (Exception e) → in e.getMessage()   (khi trùng code)
```

**Luồng Search:** `Main.inputSearch` ──► `controller.searchDoctor(requestDTO)` ──► `repository.searchDoctor`
duyệt `doctorMap.values()` + `contains`, mỗi bác sĩ khớp thành **`doctor.toString()`** (1 dòng bảng) ──►
`responseDTO.setDoctorMap(...)` ──► view in tiêu đề, header, từng dòng — **1 lần**. Đúng câu Guide:
*"Cần output gì thì thêm hàm toString() để trả lại repository -> controller sẽ nhận kết quả và truyền vào view"*.

---

## 4. Code từng bước — thứ tự nên gõ khi vào phòng lab

> Thầy: *"code cái **model trước rồi đến data**"* — gõ **model → dto → repository**, rồi mới đến các lớp dùng chúng.

| Bước | File | Việc | Lưu ý |
|---|---|---|---|
| 1 | `model/Doctor.java` | 4 field `private` + constructor rỗng + constructor đủ + get/set + `toString()` = `String.format(Constants.ROW_FORMAT, …)` | **JavaBean** (MVC JSP) · **Alt+Insert** sinh get/set |
| 2 | `dto/DoctorRequestDTO.java` | code, name, specialization, `Integer` availability, searchText | constructor rỗng + get/set |
| 3 | `dto/DoctorResponseDTO.java` | `String message` + `LinkedHashMap<String, String> doctorMap` + constructor rỗng + get/set | chở **cả câu trả lời** của 1 luồng |
| 4 | `repository/DoctorRepository.java` | `LinkedHashMap<String, Doctor> doctorMap` + `isExistDoctor/add/update/delete/search` | **không** `System.out` |
| 5 | `constants/Message.java`, `Constants.java` | menu, prompt, **thông báo lỗi chép từ đề**; số menu, `MIN_AVAILABILITY`, định dạng cột | `final class` + `private` constructor · không viết số thẳng trong code |
| 6 | `view/DoctorView.java` | field `responseDTO` + `setResponseDTO` + `display()` không tham số | nơi duy nhất in kết quả |
| 7 | `controller/DoctorController.java` | 5 hàm, mỗi hàm: kiểm → gọi repository → `setResponseDTO` + `display()` **1 lần** | **không** Scanner, không import `model` |
| 8 | `utils/Validation.java` | `getText`, `getNonBlank`, `getChoice`, `checkAvailability`, `checkOptionalAvailability` | `final` + `private` ctor + static |
| 9 | `main/Main.java` | `final` + `private Main()`; menu `while` + `switch`; các hàm `inputX` hỏi lại tới khi hợp lệ và **trả `DoctorRequestDTO`**; mỗi case gọi controller 1 lần | Scanner **chỉ ở đây**; biến khai đầu hàm + khởi tạo |
| 10 | — | **Alt+Shift+F** từng file, **F6** chạy, đi hết bảng test mục 5 | |

### Mẹo gõ nhanh trong NetBeans

| Phím | Làm gì |
|---|---|
| `Alt+Insert` | sinh constructor / getter-setter / toString |
| `Alt+Shift+F` | định dạng code (thầy **ưu tiên** phím này) |
| `sout` + `Tab` | `System.out.println("")` |
| `Ctrl+Shift+I` | tự thêm import |

### Ba chỗ hay sai

1. **`getChoice` phải tách 2 lỗi.** Mẫu Guide bọc cả hai trong một `catch (Exception)` → nhập `9`
   bị báo *"không phải số"*. Bắt `NumberFormatException` riêng, kiểm khoảng **sau**.
2. **Update phải báo lỗi code NGAY**, trước khi hỏi Name. Vì vậy `Main.inputUpdate` gọi
   `controller.checkExistDoctor(requestDTO)` ngay sau khi nhập code — lần gọi **chỉ để kiểm** (ném lỗi,
   không render); cập nhật thật vẫn là **một** lần `controller.updateDoctor(requestDTO)` ở case 2.
3. **Mẫu Guide quên gọi controller ở `case 3`** (tạo DTO rồi thôi) → chức năng xoá không làm gì.

---

## 5. Test trước khi gọi thầy review

> Thầy: *"Phải test tất cả các happy case cũng như hiển thị đủ các message validation, test thiếu
> không review."* Đi **đủ 14 dòng** dưới đây.

| # | Chọn | Gõ | Phải thấy |
|---|---|---|---|
| 1 | 1 | `DOC 1` / `Nghia` / `Orthopedics` / `3` | `Add doctor successfully.` |
| 2 | 1 | `DOC 2` / `Phuong` / `Obstetrics` / `2` | `Add doctor successfully.` |
| 3 | 1 | `DOC 1` / … | `Doctor code [DOC 1] is duplicate` |
| 4 | 1 | Code để trống | `Code cannot be blank.` rồi hỏi lại |
| 5 | 1 | Availability `many` | `Please input number` rồi hỏi lại |
| 6 | 1 | Availability `-2` | `Availability must be greater than or equal to 0` |
| 7 | 2 | `DOC 9` | `Doctor code doesn’t exist` (không hỏi Name) |
| 8 | 2 | `DOC 2` / để trống / để trống / `7` | `Update doctor successfully.` — Name giữ `Phuong` |
| 9 | 2 | Availability `abc` rồi `-1` rồi để trống | 2 thông báo lỗi, rồi giữ số cũ |
| 10 | 3 | `DOC 9` | `Doctor code doesn’t exist` |
| 11 | 3 | `DOC 1` | `Delete doctor successfully.` |
| 12 | 4 | `ortho` | ra bác sĩ có `orthodontic` (không phân biệt hoa thường) |
| 13 | 4 | `zzz` | `No doctor found.` |
| 14 | menu | `9` rồi `x` | `Please choose from 1 to 5.` rồi `Please input number` |

---

## 6. Debug — khi thầy bảo "debug cho thầy xem"

| Việc | Cách làm |
|---|---|
| Đặt breakpoint | click lề trái số dòng tại `doctorRepository.addDoctor(requestDTO);` trong `DoctorController.addDoctor` |
| Chạy debug | **Ctrl+F5** (Debug Project) |
| Đi từng dòng | **F8** Step Over · **F7** Step Into (vào trong `addDoctor` của repository) · **Ctrl+F7** Step Out |
| Xem biến | tab **Variables**: mở `requestDTO` xem 4 giá trị vừa nhập, mở `doctorMap` xem số phần tử |
| Chạy tiếp tới breakpoint sau | **F5** Continue |

**Kịch bản debug nên tập:** thêm `DOC 1` hai lần, breakpoint ở dòng `if (doctorRepository.isExistDoctor(...))`.
Lần 2, F8 sẽ nhảy vào `throw` — chỉ cho thầy thấy **lỗi trùng được phát hiện ở controller**, rồi
F8 tiếp thấy nó rơi vào `catch` trong `Main`.

---

## 7. Câu hỏi thầy hay hỏi — và câu trả lời mẫu

### OOP — "chỉ vào code"

| Câu hỏi | Trả lời mẫu |
|---|---|
| 4 tính chất OOP ở đâu? | **Đóng gói**: 4 field `private` trong `Doctor` + getter/setter. **Kế thừa**: mọi lớp `extends Object` ngầm, và em ghi đè `toString()` của nó. **Đa hình**: `toString()` có `@Override` trong `Doctor` — `doctor.toString()` trong `DoctorRepository.searchDoctor` gọi đúng bản của `Doctor` (1 dòng bảng), không phải bản của `Object`. **Trừu tượng**: `Main` gọi `controller.addDoctor(requestDTO)` mà không biết dữ liệu nằm trong `LinkedHashMap`. |
| Vì sao field `private`? | Để không lớp nào sửa thẳng được, phải đi qua setter hoặc qua repository — nơi có kiểm tra. |
| `@Override` để làm gì? | Bắt compiler kiểm chữ ký: gõ sai `tostring()` thì compiler báo lỗi thay vì âm thầm tạo hàm mới. |

### Access modifier, static, kiểu trả về

| Câu hỏi | Trả lời mẫu |
|---|---|
| `contains()` / `checkDatabase()` / `displayResult()` sao `private`? | `contains`, `checkDatabase` chỉ `DoctorRepository` dùng; `displayResult` chỉ `display()` của `DoctorView` dùng. Để `public` thì lớp khác gọi được thứ không phải "hợp đồng" của nó. |
| Constructor của `Message`, `Validation` sao `private`? | Lớp chỉ chứa hằng/hàm static — không có lý do tạo đối tượng, `private` chặn luôn `new`. |
| Sao `Validation` là `static`? | Hàm không dùng dữ liệu riêng của đối tượng nào: cùng chuỗi vào luôn ra cùng kết quả. Thầy Guide ghi *"phải dùng static method"*. |
| **Bỏ `static` thì sao?** | `Validation.getChoice(...)` sẽ báo lỗi biên dịch. Muốn chạy phải bỏ `private` constructor, tạo `Validation v = new Validation();` trong `Main` rồi gọi `v.getChoice(...)`. |
| Sao `Main` không có biến static? | Luật Guide: *"Cấm dùng static với biến, có thể dùng với hàm"* → Scanner là biến **cục bộ** trong `main()`, truyền vào các hàm nhập. |
| `isExistDoctor` trả `boolean` vì sao? | Nơi gọi chỉ cần có/không. |
| `checkOptionalAvailability` trả `Integer`, không `int`? | Cần giá trị `null` = "bỏ trống"; `int` không có. |
| Hàm controller trả `void`? | Kết quả đã được đưa sang view in ra; lỗi đi bằng `throw` — không còn gì để trả về. |
| **Sao `LinkedHashMap` mà không `Map`/`HashMap`?** | `Map` là **interface** (hợp đồng: `put/get/remove`), `HashMap` là **lớp cài đặt** bằng bảng băm — tra theo khoá rất nhanh nhưng **không giữ thứ tự**. `LinkedHashMap` **kế thừa** `HashMap` và nhớ thêm thứ tự nhập. Em cần kết quả tìm kiếm in theo thứ tự đã thêm, nên em khai báo đúng kiểu `LinkedHashMap` — code phụ thuộc thật vào tính chất đó. |
| `List` khác `ArrayList`? | `List` là interface, `ArrayList` là lớp cài đặt bằng **mảng động** (lấy theo chỉ số nhanh, chèn/xoá giữa chậm vì dời phần tử); `LinkedList` cài cùng hợp đồng bằng danh sách liên kết. |
| Sao `Doctor` có constructor rỗng? | Thầy dạy **MVC JSP**: model là **JavaBean** — field `private`, constructor rỗng `public`, get/set. |

### Kiến trúc & SOLID

| Câu hỏi | Trả lời mẫu |
|---|---|
| Sao controller không in ra? | Guide: *"Print qua View"*. Nếu in trong controller thì đổi giao diện (console → cửa sổ) phải sửa cả logic. |
| Sao có 2 DTO? | Request: main → controller (thứ **người dùng gõ**: code, name, specialization, availability, searchText). Response: controller → view (thứ **được hiển thị**: `message` hoặc `doctorMap` các dòng bảng). Guide: *"những gì sẽ hiển thị qua view khai báo ở đây"*. |
| SOLID ở đâu? | **S**: mỗi lớp một lý do để sửa — đổi cách lưu chỉ sửa `DoctorRepository`, đổi câu chữ chỉ sửa `Message`, đổi cách in chỉ sửa `DoctorView`. **O**: thêm trường mới không phải sửa `DoctorController` (mục 8). **D** (một phần): `Main` chỉ biết controller + DTO, không biết repository. |
| Pattern gì? | **MVC** (thầy bắt), controller đóng vai **Facade**, `DoctorRepository` theo mẫu **Repository** — xem mục 3.1. |
| Sao hàm nào cũng chỉ 1 tham số DTO? | Thầy: *"không được truyền 3 tham số 1 hàm"*. Gói dữ liệu vào `DoctorRequestDTO` — thêm trường chỉ sửa DTO, chữ ký hàm đứng yên. |
| Update gọi controller 2 lần — trái luật "1 lần"? | `checkExistDoctor` là lần gọi **chỉ để kiểm** (ném lỗi, **không** render) nằm trong `Main.inputUpdate`, vì đề bắt báo ngay ("không có code thì báo, không hỏi tiếp"). Việc cập nhật vẫn chỉ gọi `updateDoctor` **đúng 1 lần**, và view chỉ render **1 lần**. |
| Đề gợi ý lớp `DoctorHash`, sao em đặt `DoctorRepository`? | Thầy bắt làm P0055 **theo mẫu Guide**, và Guide đặt tên lớp này là `DoctorRepository`. Muốn đúng tên đề thì đổi tên lớp là xong — chức năng giữ nguyên. |
| Guide để view `setDoctorMap(Map<String, DoctorResponseDTO>)`, sao em `setResponseDTO`? | Cùng một ý của tờ giấy: view nhận qua **thuộc tính** + `display()` không tham số. Nhưng mẫu Guide chỉ in bảng Search; bài này còn phải in `Add/Update/Delete doctor successfully.` **qua view** (controller không được in) → một `DoctorResponseDTO` chở cả `message` lẫn `doctorMap`, view render **1 lần/luồng**. Tên `doctorMap` của Guide vẫn giữ (field trong DTO). |

### Tờ checklist 25 mục — bài này đạt thế nào

| Mục | Chỉ vào đâu |
|---|---|
| **1.1** MVC + repository | `repository/DoctorRepository` (bắt buộc có repository); `DoctorController` không import `model`; `DoctorView` nhận `responseDTO` qua setter, `display()` không tham số, gọi **1 lần/luồng**; mỗi case trong `Main.main` gọi controller 1 lần (`inputUpdate` thêm `checkExistDoctor` **chỉ để kiểm**, lý do ở mục 3) |
| **1.5** tên collection / Id | `doctorMap` (repository + `DoctorResponseDTO`), `foundMap` (`DoctorRepository.searchDoctor`); bài không có tên nào chứa `ID` |
| **2.6 / 3.7** khai báo đầu block + khởi tạo | `Main.main`: `requestDTO = null`, `running = true`, `choice = 0`; `Main.inputX`: `String line = ""`, trong vòng lặp chỉ gán; `Validation`: `int choice = 0`, `int availability = 0` |
| **2.8** dòng trống | giữa các field (cả `Constants`, `Message`, DTO), sau vùng khai báo biến, trước mọi comment đứng sau dòng code, giữa các `case`, sau `}` trước câu lệnh tiếp |
| **3.3** ngoặc | `Validation.getChoice`: `if ((choice < min) \|\| (choice > max))`; `DoctorRepository.contains`: `(field != null) && …` |
| **3.4** lớp chỉ có static | `Main`, `Validation`, `Constants`, `Message`: `final` + `private` constructor |
| **3.8** cộng chuỗi | `Doctor.toString()` dùng `String.format(Constants.ROW_FORMAT, …)` (bản trước `code + " " + name …`) |

---

## 8. Thầy đổi yêu cầu tại chỗ — sửa ở đâu

| Thầy bảo | Sửa file | Không phải đụng |
|---|---|---|
| Thêm trường `phone` | `Doctor` (+ `toString`), `DoctorRequestDTO`, `Message`, `Constants.ROW_FORMAT`/`HEADER_FORMAT`, `Validation` (kiểm số), `DoctorRepository` (add/update/search), `DoctorView` (header), `Main` (nhập ở add **và** update) | **`DoctorController`**, `DoctorResponseDTO` — vì chúng chỉ chuyển dữ liệu |
| Thêm menu "Hiện tất cả" | `Message.MENU`, `Constants` (số menu), `DoctorController.displayAll()`, `DoctorRepository.findAll()`, thêm `case` ở `Main` | `Doctor`, DTO |
| Đổi thông báo lỗi trùng | chỉ `Message.DUPLICATE_CODE` | mọi file khác |
| Đổi `HashMap` → `ArrayList` | chỉ `DoctorRepository` (tự viết vòng lặp tìm theo code) | `Main`, `Controller`, `View` |

---

## 9. Chỗ khác với đề / mẫu Guide

| Chỗ | Đề / Guide | Bài này | Lý do |
|---|---|---|---|
| Tên lớp CRUD | Đề: `DoctorHash` | `DoctorRepository` | theo mẫu Guide thầy bắt làm |
| Kiểu kho | Guide: `Map<String, Doctor> ... = new HashMap<>()` | `LinkedHashMap<String, Doctor>` | giữ thứ tự nhập + thầy dặn khai báo kiểu cụ thể |
| Chữ ký `addDoctor(Doctor)` | Đề nhận `Doctor` | `addDoctor(DoctorRequestDTO)` | Guide: *"Truyền data vào controller thông qua DTO param"* |
| `getChoice` | Guide: 1 `catch` | tách 2 `catch` | Guide báo sai lỗi khi nhập `9` |
| `getString` | Guide: `input.equals(null)` | `input == null` | `equals(null)` không bao giờ đúng |
| Xoá | Guide: không gọi controller | có gọi | mẫu bị thiếu |
| Menu | đề (phần chữ) ghi lẫn có/không dấu chấm: `2. Update Doctor.`, `5. Exit.` | tiêu đề `========= Doctor Management ==========` + 5 mục không dấu chấm | màn hình giữ đúng bản đã kiểm (`verify.py`); mục *Expectation of User interface* trong file `.docx` của đề để trống — hỏi thầy nếu thầy muốn đúng từng dấu chấm |
| Thông báo code không có | đề ghi 3 kiểu: `Doctor code does not exist` (menu 2), `code does not exist Doctor` (menu 3), `Doctor code doesn’t exist` (Function 2-3) | `Doctor code doesn’t exist` cho cả Update lẫn Delete | chép đúng câu Exception của Function details |
| View (21/09) | bản trước: `setDoctorMap(LinkedHashMap<String, DoctorResponseDTO>)` + `display()` + `showMessage(String)` | field `responseDTO` + `setResponseDTO` + `display()`; câu thành công đi trong `DoctorResponseDTO.message` | tờ checklist 1.1: view nhận qua **thuộc tính**, render **1 lần/luồng**; `showMessage(String)` là nhận qua tham số |
| `DoctorResponseDTO` (21/09) | bản trước: 4 cột của 1 bác sĩ + `toString()`; repository có `toResponse()` | `message` + `doctorMap` (code → dòng bảng); dòng bảng do `Doctor.toString()` dựng | một DTO chở **cả câu trả lời** của 1 luồng; Guide: *"Cần output gì thì thêm hàm toString()"* ở model |
| `Main` (21/09) | bản trước: `addDoctor/updateDoctor/…` trong `Main` vừa đọc vừa gọi controller (update gọi 2 lần trong 1 hàm); biến khai giữa block; không `final` | `inputAdd/inputUpdate/inputDelete/inputSearch` chỉ đọc + validate và trả `DoctorRequestDTO`; case gọi controller 1 lần; biến khai đầu hàm + khởi tạo; `final` + `private Main()` | tờ checklist 1.1, 2.6, 3.4, 3.7 |
| `Validation` (21/09) | bản trước: `int choice;` `if (choice < min \|\| choice > max)` | `int choice = 0;` `if ((choice < min) \|\| (choice > max))` | tờ checklist 3.7, 3.3 (đúng mẫu `getChoice` của thầy) |
