# J1.S.P0054 — Contact Management

> Bài CRUD **anh em ruột của P0055** (thêm · hiện · xoá) — chép đúng khung P0055. Ba chỗ thầy hay
> bắt: **ID tự tăng = ID của contact CUỐI + 1**, **tách tên ở dấu cách ĐẦU TIÊN**, và **regex số điện
> thoại** đúng 7 dạng của đề — không hơn, không kém.

| | |
|---|---|
| Loại / LOC | Short Assignment · 64 LOC · 1 slot |
| Project | `HE176322_J1SP0054_ContactManagement` |
| Chạy | NetBeans: **File ▸ Open Project** → **F6** |
| Lớp chạy | `main.Main` |
| Kiểm tự động | `python3 _tools/verify.py J1SP0054` → 6 kịch bản × 2 locale |

---

## 1. Đề bài nói gì

- Menu 4 mục: **Add · Display all · Delete · Exit**.
- Contact có: `ID (int)`, `fullName`, `group`, `address`, `phone`, `firstName`, `lastName`.
- **ID tự tăng**: contact đầu có ID **1**, contact mới = **ID contact cuối + 1**.
- `firstName`, `lastName` **lấy từ tên**, tách ở **dấu cách đầu tiên**.
- Phone chỉ nhận **7 dạng**: `1234567890` · `123-456-7890` · `123-456-7890 x1234` ·
  `123-456-7890 ext1234` · `(123)-456-7890` · `123.456.7890` · `123 456 7890`.
- Delete: nhập ID — **phải là số**, **phải tồn tại**, không có thì `No found contact`.

**Đề bắt buộc:**

| Thứ | Đề viết | Bài này đặt ở |
|---|---|---|
| `addContact` | `public boolean addContact(List<Contact> list, Contact contact)` | `ContactController.addContact(ContactRequestDTO)` → `ContactRepository.addContact(ContactRequestDTO)` trả **`boolean`** |
| `displayAll` | `public void displayAll(List<Contact> list)` | `ContactController.displayAll()` → `ContactRepository.displayAll()` (lấy dữ liệu) + `ContactView.displayAll()` (in) |
| `deleteContact` | `public boolean deleteContact(List<Contact> list, Contact contact)` | `ContactController.deleteContact(dto)` → `ContactRepository.deleteContact(dto)` trả **`boolean`** |
| Thông báo | `Please input Phone flow` + 7 dòng · `ID is digit` · `No found contact` · `Successful` | `constants/Message` — **chép đúng từng chữ** |

---

## 2. Kiến thức cần biết

### 2.1 ID tự tăng — chạy tay

| Việc | Danh sách sau đó | ID vừa cấp | Vì sao |
|---|---|---|---|
| Add Iker | [1] | 1 | danh sách rỗng → `FIRST_ID = 1` |
| Add John | [1, 2] | 2 | cuối là 1 → 1 + 1 |
| Add Raul | [1, 2, 3] | 3 | cuối là 2 |
| Delete 2 | [1, 3] | — | |
| Add Xavi | [1, 3, 4] | **4** | cuối là **3** → 4 |
| Delete 4 rồi Add | [1, 3, 4] | **4** (cấp lại) | cuối là 3 — đúng chữ đề *"last ID contact + 1"* |

### 2.2 Tách tên ở dấu cách đầu tiên

| Tên nhập | `indexOf(' ')` | First | Last |
|---|---|---|---|
| `Raul Gonzalez` | 4 | `Raul` | `Gonzalez` |
| `Ronaldo de Assis` | 7 | `Ronaldo` | `de Assis` |
| `Cher` | -1 | `Cher` | *(trống)* |

### 2.3 Regex số điện thoại — mỗi dạng một nhánh

```
\d{10}                                   1234567890
|\d{3}-\d{3}-\d{4}( (x|ext)\d{4})?       123-456-7890 [ x1234 | ext1234]
|\(\d{3}\)-\d{3}-\d{4}                   (123)-456-7890
|\d{3}\.\d{3}\.\d{4}                     123.456.7890
|\d{3} \d{3} \d{4}                       123 456 7890
```

| Ký hiệu | Nghĩa |
|---|---|
| `\d{3}` | đúng 3 chữ số |
| `\(` `\.` | dấu `(` và `.` thật (không có `\` thì `.` = "ký tự bất kỳ") |
| `( … )?` | phần đuôi có hoặc không |
| `a|b` | dạng a **hoặc** dạng b |
| `matches()` | phải khớp **cả chuỗi** — `call 1234567890` bị từ chối |

Bị từ chối đúng: `123.456.7890 x1234` (đuôi chỉ đi với dấu `-`), `123-456.7890` (trộn dấu),
`123-456-7890ext1234` (thiếu dấu cách), `123456789` (9 số).

### 2.4 Java dùng trong bài

| API | Dùng làm gì |
|---|---|
| `ArrayList.add / get / remove(int)` | lưu, lấy contact cuối, xoá theo vị trí |
| `String.indexOf / substring` | tách tên |
| `String.matches(regex)` | kiểm phone |
| `String.format("%-18s", …)` | căn cột bảng |

---

## 3. Thiết kế

```
HE176322_J1SP0054_ContactManagement/src/
├── model/      Contact               7 thuộc tính, tự tách first/last (JavaBean)
│               ContactBuilder        Builder: tạo Contact từng bước
├── dto/        ContactRequestDTO     name, group, address, phone, id  (main ──► controller)
│               ContactResponseDTO    7 cột + toString() định dạng       (controller ──► view)
├── repository/ ContactRepository     ArrayList<Contact> + addContact / displayAll / deleteContact / nextId
├── controller/ ContactController     điều hướng repository ↔ view (Facade)
├── view/       ContactView           displayAll (in bảng) + showMessage
├── constants/  Message.java          câu chữ (cả 7 dòng phone)
│               Constants.java        số menu, FIRST_ID, PHONE_PATTERN, ROW_FORMAT
├── utils/      Validation            getChoice, getNonBlank, checkPhone, checkId
└── main/       Main                  menu + Scanner
```

| Lớp | Làm gì | Không được làm (luật Guide) |
|---|---|---|
| `Main` | vòng menu, **đọc bàn phím**, gói dữ liệu vào DTO, in tiêu đề | gọi model/view, biến static |
| `ContactController` | nhận DTO → gọi repository → đưa kết quả cho view | Scanner, `System.out`, static |
| `ContactRepository` | giữ `ArrayList<Contact>`, thêm/lấy/xoá, cấp ID | in, đọc bàn phím |
| `Contact`, `ContactBuilder` | mô tả 1 contact / dựng nó | Scanner, printf, static |
| `ContactView` | in bảng + câu thông báo | tính toán |
| `Validation` | nhận **chuỗi** → trả giá trị sạch hoặc ném lỗi | đọc bàn phím |

**Luồng Delete:**

```
Main: in tiêu đề → inputId (hỏi lại khi "ID is digit") → ContactRequestDTO → controller.deleteContact(dto)
   controller ──► repository.deleteContact(dto) → false? throw "No found contact"
   controller ──► view.showMessage("Successful")
Main: catch → in e.getMessage()
```

### 3.1 Design Pattern — **Builder**

| Yếu tố | Trong bài này |
|---|---|
| **Name** | Builder (nhóm Creational) |
| **Problem** | `Contact` có **7 thuộc tính**; constructor đủ nhận 5 giá trị, **4 cái là `String`** liền nhau → đảo `group` với `address` vẫn biên dịch được, sai âm thầm. Và thầy cấm hàm nhiều tham số. |
| **Solution** | `ContactBuilder` = **Builder**: mỗi bước **1 tham số có tên** `withId(...)`, `withFullName(...)`, … ; `build()` tạo **Product** `Contact`. `ContactRepository.addContact` = **Director** (gọi các bước theo thứ tự). |
| **Consequences** | ✅ Đọc là hiểu giá trị nào vào ô nào; thêm trường `email` = thêm 1 bước `withEmail`, chỗ gọi cũ vẫn chạy. ❌ Thêm 1 lớp. (Builder để lớp **riêng**, không lồng `static class` trong model, vì model **không được static**.) |

Còn có: **Facade** (`ContactController` là một cửa cho `Main`) và **Repository**
(`ContactRepository` là nơi duy nhất đụng `ArrayList`).

### 3.2 SOLID trong bài

| Nguyên lý | Ở đâu |
|---|---|
| **S** | `Contact` giữ dữ liệu + luật tách tên · `ContactRepository` lưu + cấp ID · `ContactView` in · `Validation` kiểm |
| **O** | thêm trường mới: thêm 1 bước Builder, `ContactController` không đổi |
| **L** | — (không có lớp con) |
| **I** | — (không có interface) |
| **D** | `Main` chỉ biết controller + DTO, không biết repository |

---

## 4. Code từng bước

> Thầy: *"code cái **model trước rồi đến data**"*.

| Bước | File | Việc |
|---|---|---|
| 1 | `model/Contact.java` | 7 field `private` + constructor rỗng + constructor đủ + `applyFullName` (private) + get/set + `toString` |
| 2 | `model/ContactBuilder.java` | 5 field + 5 hàm `withX` trả `this` + `build()` |
| 3 | `dto/ContactRequestDTO`, `ContactResponseDTO` | JavaBean; Response có `toString()` dùng `ROW_FORMAT` |
| 4 | `repository/ContactRepository.java` | `ArrayList<Contact>` + `addContact` · `displayAll` · `deleteContact` · `nextId` · `toResponse` |
| 5 | `constants/Message`, `Constants` | menu, tiêu đề, 7 dòng phone; `FIRST_ID`, `PHONE_PATTERN`, `ROW_FORMAT` |
| 6 | `view/ContactView.java` | `setContactList`, `displayAll`, `showMessage` |
| 7 | `controller/ContactController.java` | 3 hàm |
| 8 | `utils/Validation.java` | `getChoice`, `getNonBlank`, `checkPhone`, `checkId` |
| 9 | `main/Main.java` | menu + `inputChoice/inputText/inputPhone/inputId` + `addContact/deleteContact` |

**Bẫy hay gặp:**

1. Regex `\d{3}[-. ]\d{3}[-. ]\d{4}` gọn hơn nhưng nhận cả `123-456.7890` — **không** nằm trong 7 dạng.
2. `list.remove(contact)` dùng `equals()`; `Contact` không ghi đè `equals` nên chỉ xoá được **đúng đối tượng đó**. Em xoá theo **ID**.
3. `list.remove(i)` với `i` là `int` = xoá **theo vị trí**; `remove(Integer)` = xoá **theo giá trị** — dễ nhầm.
4. ID lấy `size() + 1` là **sai** sau khi xoá (trùng ID). Đề nói **ID cuối + 1**.

---

## 5. Test trước khi gọi thầy

| # | Chọn | Gõ | Phải thấy |
|---|---|---|---|
| 1 | 1 | `Raul Gonzalez` / `Star` / `Spain` / `09` rồi `1234567890` | 7 dòng `Please input Phone flow…` rồi `Successful` |
| 2 | 1 | 7 dạng phone, mỗi dạng 1 contact | cả 7 `Successful` |
| 3 | 1 | phone `123.456.7890 x1234`, `123-456.7890`, `123-456-7890ext1234` | 7 dòng hướng dẫn, hỏi lại |
| 4 | 1 | Name/Group/Address để trống | `Name must not be blank.` (…Group…, …Address…) rồi hỏi lại |
| 5 | 1 | `Ronaldo de Assis` | First `Ronaldo`, Last `de Assis` |
| 6 | 1 | `Cher` | First `Cher`, Last trống |
| 7 | 2 | (chưa có ai) | `No found contact` |
| 8 | 2 | (có dữ liệu) | bảng đủ 7 cột |
| 9 | 3 | `a`, `0`, `-3` | `ID is digit` rồi hỏi lại |
| 10 | 3 | ID không có | `No found contact` |
| 11 | 3 | ID có | `Successful` |
| 12 | 1 | sau khi xoá contact giữa | ID mới = ID cuối + 1 |
| 13 | menu | `x`, trống, `0`, `5` | `Please choice one option from 1 to 4.` |

---

## 6. Debug

| Việc | Cách làm |
|---|---|
| Breakpoint | dòng `return contacts.get(contacts.size() - 1).getId() + Constants.ID_STEP;` trong `ContactRepository.nextId` |
| Chạy | **Ctrl+F5**, thêm 2 contact |
| Quan sát | **Variables** → `contacts` (mở ra thấy `elementData`, `size`), xem ID contact cuối |
| Bước | ở `addContact` bấm **F7** vào `.build()` → vào `Contact(...)` → **F7** vào `applyFullName` xem `space`, `firstName`, `lastName` |
| Xoá | breakpoint trong vòng `for` của `deleteContact`, xem `i` và `contacts.get(i).getId()` |

---

## 7. Câu hỏi thầy hay hỏi

### OOP

| Câu hỏi | Trả lời mẫu |
|---|---|
| 4 tính chất OOP ở đâu? | **Đóng gói**: 7 field `private` trong `Contact`; `firstName/lastName` **không có setter** — chỉ đổi được qua `setFullName` nên không bao giờ lệch với tên đầy đủ. **Kế thừa**: mọi lớp `extends Object`, em ghi đè `toString()`. **Đa hình**: `println(contact)` trong view gọi `toString()` của `ContactResponseDTO`. **Trừu tượng**: `Main` gọi `controller.addContact(dto)` mà không biết có `ArrayList` và Builder. |
| Sao `firstName` không có setter — thế có còn JavaBean? | JavaBean cho phép thuộc tính **chỉ đọc** (chỉ getter). Hai thuộc tính này **tính ra** từ tên đầy đủ. |
| Sao constructor gọi `applyFullName` (private) mà không gọi `setFullName`? | Gọi hàm **có thể bị ghi đè** trong constructor là bẫy: lớp con ghi đè sẽ chạy trước khi nó khởi tạo xong. Hàm `private` không ghi đè được. |

### Access modifier, static, kiểu trả về

| Câu hỏi | Trả lời mẫu |
|---|---|
| `nextId`, `toResponse` sao `private`? | Chỉ `ContactRepository` dùng; `public` thì lớp khác tự cấp ID được, phá luật "ID cuối + 1". |
| `withX` của Builder sao `public` và trả `ContactBuilder`? | Repository gọi; trả **chính nó** (`this`) để gọi nối `.withId(..).withGroup(..)`. |
| `addContact`/`deleteContact` trả `boolean`? | **Đề bắt**: *"to be more one contact status"*, *"Delete contact status"*. Controller dùng `false` để ném `No found contact`. |
| `displayAll` của repository trả `ArrayList<ContactResponseDTO>`? | Repository **không được in**; nó trả dữ liệu, view in. |
| Hàm controller trả `void`? | Kết quả đã đưa cho view; lỗi đi bằng `throw`. |
| Sao `Validation` static? Bỏ đi thì sao? | Không dùng dữ liệu đối tượng. Bỏ `static` → `Validation.checkPhone(...)` lỗi biên dịch; phải bỏ `private` constructor và `new Validation()` trong `Main`. |
| Hàm `inputX` trong `Main` sao `private static`? | `private`: chỉ `Main` dùng. `static`: `main()` là static nên gọi thẳng được; Guide cho *static với hàm* trong main, **cấm** static với biến → Scanner là biến cục bộ truyền vào. |
| `inputText(sc, field)` — sao không truyền prompt và error riêng? | Sẽ thành **3 tham số** — thầy cấm. Nhãn `Name/Group/Address` dựng ra cả prompt `Enter %s: ` lẫn lỗi `%s must not be blank.` |
| **Sao `ArrayList<Contact>` mà không `List<Contact>` như đề?** | `List` là **interface** (hợp đồng `add/get/remove`), `ArrayList` là **lớp cài đặt** bằng mảng động — lấy theo vị trí O(1), đúng việc em làm (lấy contact **cuối**, xoá theo vị trí). Em khai kiểu em thật sự dùng. Đề đưa `List` vào **tham số**, nhưng danh sách là **dữ liệu của repository** (field) nên không cần truyền qua tham số nữa. |
| `ArrayList` khác `LinkedList`? | Cùng hợp đồng `List`. `ArrayList` = mảng động (get nhanh, xoá giữa phải dời phần tử); `LinkedList` = nút liên kết (chèn/xoá đầu nhanh, get chậm). |

### Kiến trúc

| Câu hỏi | Trả lời mẫu |
|---|---|
| Sao `displayAll` tách 2 nửa? | Đề cho nó trả `void` = **in ra**. Guide cấm in ngoài view → nửa **lấy dữ liệu** ở repository, nửa **in** ở view — cùng tên `displayAll`. |
| Sao `deleteContact` nhận DTO chứ không nhận `Contact`? | `Main` và controller **không được** đụng model (Guide). Người dùng gõ **ID**, nên DTO mang ID. |
| Sao cả "không có ai" lẫn "xoá ID không có" đều `No found contact`? | Câu của đề cho trường hợp xoá; bảng rỗng dùng lại cùng câu (bản cũ cũng vậy). |

---

## 8. Thầy đổi yêu cầu tại chỗ

| Thầy bảo | Sửa file | Không phải đụng |
|---|---|---|
| Thêm trường `email` | `Contact`, `ContactBuilder` (`withEmail`), 2 DTO, `Message`, `Constants.ROW_FORMAT`, `ContactRepository` (add + `toResponse`), `ContactView` (header), `Main` (nhập) | **`ContactController`** |
| Thêm menu "Search by name" | `Message.MENU`, `Constants`, `ContactController.searchContact`, `ContactRepository.searchContact`, `case` mới ở `Main` | `Contact`, Builder |
| ID không cấp lại sau khi xoá | `ContactRepository`: thêm field `private int lastId` tăng dần | mọi file khác |
| Thêm 1 dạng phone | chỉ `Constants.PHONE_PATTERN` + `Message.INVALID_PHONE` | mọi file khác |
| Tách tên ở dấu cách **cuối** | `Contact.applyFullName`: `indexOf` → `lastIndexOf` | mọi file khác |

---

## 9. Chỗ khác với đề / bản cũ

| Chỗ | Đề / bản cũ | Bài này | Lý do |
|---|---|---|---|
| `addContact(List<Contact>, Contact)` | nhận list + model | `addContact(ContactRequestDTO)` (controller + repository), trả `boolean` | list là field của repository; Guide: truyền DTO, controller không đụng model |
| `displayAll(List<Contact>)` | 1 hàm in | repository `displayAll()` trả dữ liệu + view `displayAll()` in | Guide cấm in ngoài view |
| `deleteContact(List<Contact>, Contact)` | nhận model | `deleteContact(ContactRequestDTO)` mang ID, trả `boolean` | người dùng gõ ID; controller không đụng model |
| Kiểu danh sách | `List<Contact>` | `ArrayList<Contact>` | thầy dặn khai kiểu cụ thể (V5) |
| Bảng Display | đề dùng tab (lệch cột ở dòng tên dài) | cột rộng cố định `%-4s%-18s…` | giữ đúng bản cũ; luôn thẳng cột |
| `Please choice one option from 1 to 4.` · `… must not be blank.` | đề không có | có | câu của bản cũ (giữ nguyên chữ) |
| Bảng rỗng | đề không nói | `No found contact` | bản cũ |
| Builder | bản cũ `new Contact(...)` | `ContactBuilder` | thầy đánh giá cao Design Pattern; model ≥ 6 trường |
| Kiến trúc | 3 hàm static trong `Main`, Scanner trong `Validator` | MVC theo Guide, Scanner **chỉ ở `main`** | luật thầy |
