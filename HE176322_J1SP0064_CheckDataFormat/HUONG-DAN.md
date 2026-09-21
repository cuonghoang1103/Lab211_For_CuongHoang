# J1.S.P0064 — Check data format

> Nhập **số điện thoại, email, ngày** — mỗi ô hỏi lại tới khi đúng dạng. Có **3 hàm đề bắt**
> (`checkPhone`, `checkEmail`, `checkDate`) với hợp đồng lạ: **trả về câu lỗi**, đúng thì trả **chuỗi rỗng**.
>
> **Bản 21/09/2026 — sửa theo tờ checklist giấy 25 mục của thầy** (mục 10): thêm
> `repository/ContactRepository` giữ `Contact` (tờ giấy 1.1 *"Bắt buộc phải có repository"*); View nhận
> `responseDTO` qua thuộc tính (`setResponseDTO`), `display()` không tham số; `Main` thành `final` +
> constructor `private`; khai báo đầu block. **Đối chiếu lại đề từng ký tự:** trong .docx, chữ máy in màu
> **đen** còn chữ người gõ màu **xanh**, và 3 câu nhắc màu đen là `Phone number: `, `Email: `, `Date: ` —
> **có 1 dấu cách ở cuối** → chương trình in y hệt (mục 9). Màn hình chạy đổi đúng chỗ đó.

| | |
|---|---|
| Loại / LOC | Short Assignment · 30 LOC · 1 slot |
| Project | `HE176322_J1SP0064_CheckDataFormat` |
| Chạy | NetBeans: **File ▸ Open Project** → **F6** |
| Lớp chạy | `main.Main` |
| Kiểm tự động | `python3 _tools/verify.py J1SP0064` → 5 kịch bản × 2 locale |

---

## 1. Đề bài nói gì

- Điện thoại: **chỉ gồm số**, **đúng 10 chữ số**. Email: **đúng dạng email**. Ngày: **dd/MM/yyyy**.
- Sai ô nào thì in lỗi và **hỏi lại đúng ô đó**; đủ 3 ô thì in kết quả và thoát.

Màn hình (lỗi chính tả của màn hình đã sửa theo Guidelines — xem mục 9):

```
====== Validate Progaram ======
Phone number: 099999888
Phone number must be 10 digits
Phone number: abc
Phone number must be number
Phone number: 0999998888
Email: abc
Email must be correct format
Email: nghianv@ftico.com
Date: abc
Date to correct format(dd/MM/yyyy)
Date: 15/06/2015
----- Result -----
Phone number: 0999998888
Email: nghianv@ftico.com
Date: 15/06/2015
```

**Đề bắt buộc** (mục Guidelines):

| Hàm đề bắt | Hợp đồng | Bài này đặt ở |
|---|---|---|
| `public String checkPhone(String phone)` | trả câu lỗi, đúng → `""` | `utils/Validation` (thêm `static`) |
| `public String checkDate(String date)` | như trên | `utils/Validation` |
| `public String checkEmail(String email)` | như trên | `utils/Validation` |
| Gợi ý | *"Use regex … Use SimpleDateFormat"* | regex trong `Constants`, `SimpleDateFormat` trong `checkDate` |
| Câu lỗi | `Phone number must be number` · `Phone number must be 10 digits` · `Email must be correct format` · `Date to correct format(dd/MM/yyyy)` | `constants/Message` — **chép đúng Guidelines** |

---

## 2. Kiến thức cần biết

### 2.1 Ba phép kiểm, đúng thứ tự

| Hàm | Bước 1 | Bước 2 |
|---|---|---|
| `checkPhone` | `matches("\\d+")`? không → `must be number` | `length() == 10`? không → `must be 10 digits` |
| `checkEmail` | `matches("[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+")`? không → `must be correct format` | — |
| `checkDate` | `matches("\\d{2}/\\d{2}/\\d{4}")`? không → lỗi ngày | `SimpleDateFormat` **không lenient** `parse` được? không → lỗi ngày |

Chạy tay màn hình đề với `checkPhone`:

| Gõ | `\d+`? | độ dài | Trả về |
|---|---|---|---|
| `099999888` | ✓ | 9 | `Phone number must be 10 digits` |
| `abc` | ✗ | — | `Phone number must be number` |
| `0999998888` | ✓ | 10 | `""` → nhận |

### 2.2 Vì sao `checkDate` cần **cả regex lẫn** `SimpleDateFormat`

| Gõ | Chỉ `SimpleDateFormat` (không lenient) | Có regex trước | Đúng? |
|---|---|---|---|
| `5/6/2015` | nhận | **từ chối** | đề là `dd` 2 chữ số |
| `15/06/2015abc` | **nhận** (đọc xong năm là dừng) | từ chối | chuỗi thừa chữ |
| `31/02/2015` | từ chối | từ chối | tháng 2 không có 31 |
| `29/02/2016` | nhận | nhận | năm nhuận |

> **`setLenient(false)` là bắt buộc**: để mặc định (lenient), `31/02/2015` bị âm thầm "cuộn" thành
> `03/03/2015` thay vì báo sai.

### 2.3 Phân tích regex email

| Phần | Nghĩa |
|---|---|
| `[\w.+-]+` | tên: chữ, số, `_`, `.`, `+`, `-` — ít nhất 1 ký tự |
| `@` | đúng một `@` |
| `[\w-]+` | tên miền |
| `(\.[\w-]+)+` | ít nhất một `.phần` → `a@b` bị từ chối, `a@fpt.edu.vn` được |

### 2.4 Java dùng trong bài

| API | Dùng làm gì |
|---|---|
| `String.matches(regex)` | kiểm **cả chuỗi** khớp mẫu |
| `SimpleDateFormat("dd/MM/yyyy")` + `setLenient(false)` | kiểm ngày **có thật** trên lịch; `MM` là tháng (`mm` là **phút**!) |
| `formatter.parse(s)` / `format(date)` | chữ → `Date` / `Date` → chữ; sai → `ParseException` |
| `java.util.Date` | kiểu ngày trong `model/Contact` |

---

## 3. Thiết kế

```
HE176322_J1SP0064_CheckDataFormat/src/
├── model/      Contact              phone, email, Date date (JavaBean)
├── dto/        ContactRequestDTO    3 chuỗi đã qua kiểm   (main ──► controller)
│               ContactResponseDTO   3 chuỗi để in         (controller ──► view)
├── repository/ ContactRepository    giữ Contact: saveContact / getContact
├── service/    ContactService       chuỗi ngày → Date → Contact → repository → DTO
├── controller/ ContactController    service ──► view (setResponseDTO + display 1 lần)
├── view/       ContactView          field responseDTO; display() in khối "----- Result -----"
├── constants/  Message.java         câu chữ, 4 câu lỗi của đề, NO_ERROR = "", RESULT_PHONE = "Phone number: %s"…
│               Constants.java       PHONE_PATTERN, PHONE_LENGTH, EMAIL_PATTERN, DATE_PATTERN, DATE_FORMAT, CONTACT_FORMAT
├── utils/      Validation           getText, checkPhone, checkEmail, checkDate  ← 3 hàm đề bắt
└── main/       Main                 Scanner, 3 vòng hỏi lại, gọi controller 1 lần
```

| Câu hỏi thiết kế | Trả lời |
|---|---|
| Sao 3 hàm đề bắt nằm ở `utils/Validation`? | Chúng **kiểm dữ liệu nhập** — Guide: utils *"chứa các functions dùng chung như validate"*. Nếu đặt ở service thì `Main` phải gọi controller **mỗi lần gõ sai** — trái luật *"mỗi workflow chỉ gọi controller 1 lần"*. |
| Sao chúng trả `String` mà không `throw` như các bài khác? | **Đề bắt** hợp đồng *"returns the message if wrong, String empty if correct"* — giữ nguyên kiểu trả về. `Main` xem `error.isEmpty()` để biết nhận hay hỏi lại. |
| Sao có `service` + `model` khi kiểm xong là xong? | Guide: controller không được thấy model. Service biến chuỗi ngày thành `Date` thật và tạo `Contact` — nơi chương trình **hiểu** dữ liệu chứ không chỉ kiểm hình dạng. Ngày in ra là ngày lịch đã đọc (text → `Date` → text). |
| Sao bài có `repository`? | Tờ checklist 1.1: *"**Bắt buộc phải có repository**"*. Repository = **dữ liệu** + CRUD đơn giản: ở đây là liên hệ người dùng đã nhập (model `Contact`), với `saveContact` / `getContact`. Đổi chữ ngày thành `Date` là nghiệp vụ nên nằm ở `ContactService` — đúng tầng *Controller ↔ Services ↔ Repository ↔ Model*. |

**Luồng chạy:**

```
Main: inputPhone ─┐  mỗi vòng: print prompt → đọc dòng → error = Validation.checkXxx(dòng)
      inputEmail ─┤            error rỗng → nhận; không → in error, hỏi lại
      inputDate  ─┘
      ──► ContactRequestDTO ──► controller.saveContact(requestDTO)     ← gọi controller ĐÚNG 1 lần
   controller ──► service.createContact(requestDTO)
                     ├─ date = formatter.parse(requestDTO.getDate())   (không lenient)
                     ├─ repository.saveContact(new Contact(phone, email, date))
                     ├─ contact = repository.getContact()               (model)
                     └─ responseDTO (ngày format lại dd/MM/yyyy)
   controller ──► view.setResponseDTO(responseDTO) ──► view.display()  (render 1 lần)
```

### 3.1 Design Pattern trong bài

Ba phép kiểm do đề **đặt tên cố định** và nằm ở `utils` (bắt buộc `static`) — không có đối tượng nào để
"cắm" thay nhau, nên bài **không** nhét Strategy cho có. Pattern có thật:

| Yếu tố | **MVC** (thầy gọi "MVC JSP") | **Facade** |
|---|---|---|
| **Name** | Model–View–Controller | Facade (Structural) |
| **Problem** | nhập, kiểm, in trộn một chỗ → đổi câu in là đụng phép kiểm | `Main` phải biết `ContactService`, `ContactView` và thứ tự gọi |
| **Solution** | `Contact` ~ JavaBean (Model) · `ContactView` ~ trang JSP · `ContactController` ~ Servlet; dữ liệu qua DTO | `ContactController.saveContact(requestDTO)` là **một cửa**: service rồi view |
| **Consequences** | ✅ đổi dạng in chỉ sửa `ContactView`; ❌ nhiều file | ✅ `Main` chỉ biết 1 lớp; ❌ controller chỉ được điều hướng, không kiểm |

> Nếu thầy hỏi *"3 vòng nhập trong Main giống nhau, gộp được không?"* — gộp được bằng **Strategy**: một
> interface `FormatChecker { String check(String value); }` + 3 lớp `PhoneChecker`, `EmailChecker`,
> `DateChecker`, và một hàm `input(sc, checker)`. Em không làm vì 3 hàm đề bắt phải là hàm `static` trong
> `Validation`, và prompt mỗi ô khác nhau — đổi sang Strategy là thêm 4 file cho 3 vòng lặp 8 dòng.

### 3.2 SOLID trong bài

| Nguyên lý | Ở đâu |
|---|---|
| **S** | `Validation` chỉ kiểm · `ContactService` chỉ dựng `Contact` · `ContactRepository` chỉ giữ `Contact` · `ContactView` chỉ in · `Main` chỉ hỏi |
| **O** | thêm ô thứ 4 (vd. mã số thuế) = thêm 1 hàm `checkXxx` + 1 hàm nhập + 1 field DTO; 3 hàm kiểm cũ đứng yên |
| **L / I / D** | bài không có kế thừa/interface — **không cố gượng** |

---

## 4. Code từng bước

> Thầy: *"code cái **model trước rồi đến data**"*.

| Bước | File | Việc |
|---|---|---|
| 1 | `model/Contact.java` | `private String phone, email; private Date date` + constructor rỗng + constructor đủ + get/set + `toString` |
| 2 | `dto/ContactRequestDTO.java`, `ContactResponseDTO.java` | JavaBean: constructor rỗng + get/set (3 chuỗi) |
| 3 | `repository/ContactRepository.java` | field `contact` · `saveContact` · `getContact` |
| 4 | `service/ContactService.java` | `createContact(requestDTO)`: parse ngày, tạo `Contact`, cất vào repository, trả DTO |
| 5 | `view/ContactView.java` | field `responseDTO` · `setResponseDTO` · `display` |
| 6 | `controller/ContactController.java` | `saveContact(requestDTO)` |
| 7 | `constants/Message.java`, `Constants.java` | 3 câu nhắc (**có dấu cách cuối**) + 4 câu lỗi + 3 regex + định dạng ngày |
| 8 | `utils/Validation.java` | `getText` · **`checkPhone`** · **`checkEmail`** · **`checkDate`** |
| 9 | `main/Main.java` | `final` + `private Main()`; `inputPhone` · `inputEmail` · `inputDate` + gọi controller **1 lần** |

**Bẫy hay gặp:**

1. `"dd/mm/yyyy"` (chữ thường) → `mm` là **phút**, ngày nào tháng cũng thành tháng 1. Phải `MM`.
2. Quên `setLenient(false)` → `31/02/2015` được nhận.
3. Kiểm độ dài **trước** kiểm số → `abc` bị báo `must be 10 digits`. Đề: không phải số → `must be number`.
4. Lưu số điện thoại bằng `int`/`long` → mất số `0` đầu. Dùng `String`.

---

## 5. Test trước khi gọi thầy

| # | Ô | Gõ | Phải thấy |
|---|---|---|---|
| 1 | Phone | `099999888` (9 số) | `Phone number must be 10 digits` |
| 2 | Phone | `abc` | `Phone number must be number` |
| 3 | Phone | *(trống)* · `09a9998888` · `+84999888` · `0999 998888` | `Phone number must be number` |
| 4 | Phone | `09999988881` (11 số) | `Phone number must be 10 digits` |
| 5 | Phone | `  0987654321  ` | nhận, kết quả in không khoảng trắng |
| 6 | Email | `abc` · *(trống)* · `a@b` · `@b.com` · `a b@c.com` · `a@@b.com` | `Email must be correct format` |
| 7 | Email | `he.176322+lab@fpt.edu.vn` | nhận |
| 8 | Date | `abc` · *(trống)* · `15-06-2015` · `5/6/2015` · `15/06/2015abc` | `Date to correct format(dd/MM/yyyy)` |
| 9 | Date | `29/02/2015` · `31/04/2015` · `01/13/2015` · `00/01/2015` | `Date to correct format(dd/MM/yyyy)` (ngày không có thật) |
| 10 | Date | `29/02/2016` | nhận (năm nhuận) |
| 11 | cả 3 đúng | `0999998888` · `nghianv@ftico.com` · `15/06/2015` | khối `----- Result -----` 3 dòng |

---

## 6. Debug

| Việc | Cách làm |
|---|---|
| Breakpoint | dòng `formatter.parse(value);` trong `Validation.checkDate` |
| Chạy | **Ctrl+F5**, qua phone/email, ngày gõ `31/02/2015` |
| Quan sát | tab **Variables**: `value`; F8 → nhảy vào `catch (ParseException e)` → trả `DATE_INVALID` |
| So sánh (chỉ để thầy xem, rồi trả lại) | comment tạm dòng `formatter.setLenient(false);`, chạy lại `31/02/2015` → được nhận — thấy ngay vì sao cần dòng đó |
| Bước | ở `Main.inputDate` bấm **F7** vào `Validation.checkDate(date)`, **Ctrl+F7** ra, xem biến `error` |

---

## 7. Câu hỏi thầy hay hỏi

### OOP

| Câu hỏi | Trả lời mẫu |
|---|---|
| 4 tính chất OOP ở đâu? | **Đóng gói**: 3 field `private` của `Contact` + get/set. **Kế thừa**: `Contact` `extends Object`, ghi đè `toString()`. **Đa hình**: `@Override toString()`; `String.format(Constants.CONTACT_FORMAT, phone, email, date)` — `%s` tự gọi `Date.toString()`. **Trừu tượng**: `Main` gọi `controller.saveContact(requestDTO)` mà không biết có `Contact` hay `Date`. |
| Sao model/DTO có constructor rỗng? | **MVC JSP**: JavaBean — field `private`, constructor rỗng `public`, get/set. |

### Access modifier, static, kiểu trả về

| Câu hỏi | Trả lời mẫu |
|---|---|
| Sao `checkPhone`… là `static`, đề ghi `public String checkPhone`? | Guide bắt hàm utils *"phải dùng static method"*; hàm không dùng dữ liệu đối tượng nào. Tên, tham số, kiểu trả về **giữ đúng đề**, chỉ thêm `static`. |
| **Bỏ `static` thì sao?** | `Validation.checkPhone(...)` lỗi biên dịch; phải bỏ `private` constructor, `Validation v = new Validation();` trong `Main`, gọi `v.checkPhone(...)`. |
| 3 hàm kiểm và `getText` sao `public`? | `Main` (package khác) gọi. |
| Hàm nhập trong `Main` sao `private static`? | `private`: chỉ `main()` gọi; `static`: `main()` static; thầy cho static **hàm** ở main, cấm static **biến**. |
| `Message.NO_ERROR = ""` sao là hằng? | "Không lỗi" là **một giá trị có nghĩa** trong hợp đồng của đề — đặt tên để 3 hàm trả cùng một thứ, không rải `""` khắp nơi. |
| Sao trả `String` chứ không `boolean`? | Đề bắt; một giá trị mang **cả** "đúng/sai" (rỗng hay không) **lẫn** câu cần in. `boolean` thì còn phải hỏi thêm "sai vì sao". |
| `createContact` sao `throws Exception` dù ngày đã kiểm? | `parse` bắt buộc xử lý `ParseException`; service không tin người gọi — ai bỏ qua `checkDate` vẫn nhận đúng câu lỗi của đề. |
| `saveContact` trả `void`? | Kết quả đã sang view in ra. |
| Có dùng `List`/`Map` không? | Không — 3 ô cố định, 3 field. `List` là **interface**, `ArrayList` là **lớp** cài bằng mảng co giãn; khi cần danh sách em khai kiểu cụ thể `ArrayList<...>`. |
| Hàm nào quá 2 tham số? | Không — tất cả 0–1 tham số (luật thầy V4). |
| View nhận dữ liệu thế nào? | Qua **thuộc tính**: controller gọi `contactView.setResponseDTO(responseDTO)` rồi `contactView.display()` — `display()` **không tham số**, gọi **1 lần** cho cả luồng (tờ checklist 1.1). |
| Validate ở đâu? | Ở `Main` qua 3 hàm đề bắt trong `utils/Validation`: sai thì hàm **trả câu lỗi** (hợp đồng của đề), `Main` in câu đó rồi hỏi lại ô đó. Controller/service chỉ nhận 3 chuỗi đã hợp lệ trong `ContactRequestDTO`; service vẫn bắt `ParseException` rồi `throw new Exception(Message.DATE_INVALID)`, `Main` in `e.getMessage()`. |
| Sao `Main` là `final` và có `private Main() { }`? | Tờ checklist 3.4: *"Class chỉ có static method thì phải có private contructor, và khai báo class là final"*. |
| Sao câu nhắc có dấu cách cuối (`"Phone number: "`)? | Đối chiếu .docx: chữ máy in màu đen là `Phone number: ` (có dấu cách), chữ người gõ màu xanh là `099999888`. Em chép đúng từng ký tự của đề. |

### Ca biên

| Câu hỏi | Trả lời mẫu |
|---|---|
| `lenient` là gì? | Chế độ "dễ dãi" của `SimpleDateFormat`: ngày tràn thì cuộn sang tháng sau. Tắt đi để bắt ngày không có thật. |
| `29/02/2016` sao được mà `29/02/2015` không? | 2016 là năm nhuận; lịch Gregorian của Java biết điều đó. |
| Số điện thoại `+84…`? | Đề: *"must be numbers"* → `+` không phải số → `must be number`. |
| Nhập `5/6/2015`? | Từ chối — đề ghi `dd/MM/yyyy`: ngày, tháng **2 chữ số**. |

---

## 8. Thầy đổi yêu cầu tại chỗ

| Thầy bảo | Sửa file | Không phải đụng |
|---|---|---|
| Số điện thoại **10 hoặc 11** số | `Constants` (min/max độ dài) + điều kiện trong `checkPhone` | mọi file khác |
| Phải bắt đầu bằng `0` | `Constants.PHONE_PATTERN` → `"0\\d+"` (thứ tự kiểm giữ nguyên) | mọi file khác |
| Ngày không được ở **tương lai** | `checkDate`: sau `parse`, so với `new Date()` + 1 câu `Message` | `Main`, service, view |
| Chỉ nhận email **@fpt.edu.vn** | `Constants.EMAIL_PATTERN` | mọi file khác |
| Gộp 3 vòng nhập thành 1 | Strategy như mục 3.1 | `Validation` (hàm đề bắt giữ nguyên) |

---

## 9. Chỗ khác với đề / bản cũ

| Chỗ | Đề / bản cũ | Bài này | Lý do |
|---|---|---|---|
| Câu lỗi | màn hình: `must is number`, `must is correct format`, `(dd/mm/yyyy)` | `must be number`, `must be correct format`, `(dd/MM/yyyy)` | **Guidelines thắng** màn hình (bản cũ cũng theo Guidelines) |
| Chỗ xuống dòng | màn hình: `Date to correct format(dd/mm/yyyy) Date: 15/06/2015` cùng dòng | câu lỗi rồi **xuống dòng** hỏi lại | nhất quán với phone/email của chính màn hình đề |
| Khối `----- Result -----` | đề **không** nói in gì sau ô cuối | giữ đúng khối của bản cũ | đề im lặng → giữ y bản cũ (kịch bản bản cũ vẫn chạy) |
| `checkXxx` | đề: hàm thường | `public static` trong `Validation` | Guide: utils phải static |
| `checkDate` | bản cũ: chỉ `SimpleDateFormat` (nhận `5/6/2015`, `15/06/2015abc`) | regex `\d{2}/\d{2}/\d{4}` trước | đúng `dd/MM/yyyy`; chặn chuỗi thừa |
| Kiến trúc | `FormatValidator` + `InputHelper` (Scanner) + `Function<>` trong `ui.Main` | MVC theo Guide, Scanner **chỉ ở `main`** | luật thầy |
| Câu nhắc | bản cũ: `Phone number:` · `Email:` · `Date:` (không dấu cách) | `Phone number: ` · `Email: ` · `Date: ` | .docx: câu nhắc màu đen có dấu cách cuối ở 6/7 chỗ (chỉ `Phone number:abc` thiếu — coi là lỗi gõ của đề); file test `REPLACE_REFERENCE = True`, chép lại 2 kịch bản bản cũ có dấu cách |
| Repository | bản trước 21/09: không có (*"không lưu, không CRUD"*) | `repository/ContactRepository` giữ `Contact` | tờ checklist 1.1 *"Bắt buộc phải có repository"* |
| View | bản trước 21/09: `setResponse(response)` | `setResponseDTO(responseDTO)` + `display()` không tham số | tờ checklist 1.1 |
| Nối chuỗi | `Message.LABEL_PHONE + …`, `phone + " " + …` trong `toString` | `String.format(Message.RESULT_…)`, `String.format(Constants.CONTACT_FORMAT, …)` | tờ checklist 3.8 |

---

## 10. Tờ checklist 25 mục — bài này đạt thế nào

| Mục | Chỗ trong code |
|---|---|
| 1.1 MVC + repository | `repository/ContactRepository` giữ model `Contact`; `ContactService` cất/đọc `Contact` qua repository; controller chỉ import DTO/service/view; `ContactView` nhận `responseDTO` qua `setResponseDTO`, `display()` gọi **1 lần**; mọi nhập + validate (3 hàm đề bắt) ở `Main` qua `utils/Validation` |
| 1.4 method = động từ | `checkPhone`, `checkEmail`, `checkDate`, `getText`, `createContact`, `saveContact`, `inputPhone`… |
| 1.5 tên biến | `requestDTO`/`responseDTO`, `contact`, `typedContact`; không có mảng/collection; không có `ID` |
| 2.6 + 3.7 khai báo đầu block, có khởi tạo | mỗi hàm `input…` của `Main`: `String line = "";` và `String error = "";` ở đầu, trong `while` chỉ gán; `ContactService.createContact`: `formatter`, `date = null`, `contact = null`, `responseDTO` ở đầu; `Validation.checkDate`: `value`, `formatter` ở đầu |
| 2.8 dòng trống | trước mọi comment (kể cả comment field trong `Constants`, `Message`, DTO, `Contact`), sau vùng khai báo, sau `}` trước câu lệnh kế |
| 3.3 ngoặc | không có biểu thức trộn `&&`/`\|\|` hay ba ngôi; mỗi `if` một phép kiểm |
| 3.4 | `public final class Main` + `private Main() { }`; `Validation`, `Constants`, `Message` cũng `final` + ctor private |
| 3.8 | không cộng chuỗi: `ContactView` in bằng `String.format(Message.RESULT_…)`, `Contact.toString` bằng `String.format(Constants.CONTACT_FORMAT, …)` |

**Chỗ cần hỏi thầy (đề đặt, tờ checklist bắt khác):** không có — 3 hàm đề bắt giữ đúng tên, tham số, kiểu
trả về (chỉ thêm `static` vì nằm ở utils, như Guide bắt). Nên hỏi thầy một câu về **màn hình**: khối
`----- Result -----` là của bản cũ (đề không nói in gì sau ô cuối) — nếu thầy muốn đúng y màn hình đề thì bỏ
khối đó ở `ContactView.display()`.

Kiểm lại: `python3 _tools/verify.py J1SP0064` · `python3 _tools/lint.py HE176322_J1SP0064_*` · `python3 _tools/soat_checklist.py HE176322_J1SP0064_*` → 0 `VI_PHAM`.
`RUI_RO` còn lại chỉ là `String[] args` và tham số setter/constructor trùng tên field (`this.phone = phone`) — kiểu IDE sinh, được chấp nhận.
