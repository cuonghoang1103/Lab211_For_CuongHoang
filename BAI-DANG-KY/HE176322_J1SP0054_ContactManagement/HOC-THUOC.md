# J1SP0054 — Contact Management (bản học thuộc)

## (a) Đề tóm 5 dòng
1. Menu 4 mục: Add a Contact · Display all Contact · Delete a Contact · Exit.
2. Contact: ID, Name, First Name, Last Name, Group, Address, Phone.
3. ID tự tăng: contact đầu = 1, contact mới = **ID contact cuối + 1**.
4. First/Last name tách ở **dấu cách đầu tiên** của Name ("Ronaldo de Assis" → "Ronaldo" + "de Assis").
5. Phone chỉ nhận 7 dạng của đề; Delete nhập ID phải là số (`ID is digit`), không có thì `No found contact`, xong thì `Successful`.

## (b) Cây file
```
src/
├── constants/Message.java        mọi chữ hiện ra màn hình (menu, câu nhắc, lỗi, 7 dạng phone)
├── constants/Constants.java      số cố định: số menu 1-4, FIRST_ID = 1, regex 7 dạng phone
├── controller/ContactController  nhận DTO từ Main → gọi Repository → đưa dữ liệu cho View
├── dto/ContactRequestDTO         dữ liệu người dùng nhập (name, group, address, phone)
├── dto/ContactResponseDTO        1 dòng của bảng Display (toString in theo cột)
├── main/Main.java                Scanner, menu, nhập + validate, hỏi lại đúng ô sai
├── model/Contact.java            1 contact; constructor tự tách first/last name
├── repository/ContactRepository  giữ ArrayList contactList: thêm, xoá theo ID, cấp ID mới, đổi sang ResponseDTO
├── utils/Validation.java         hàm static kiểm chuỗi rỗng, phone, ID, lựa chọn menu
└── view/ContactView.java         in tiêu đề cột + từng dòng contact
```
Không có `service`: bài chỉ CRUD (thêm/hiện/xoá), không có tính toán nghiệp vụ.

## (c) Luồng chính (Add)
1. `Main` in menu, `promptChoice` → `Validation.getChoice` (sai → in lỗi, hỏi lại).
2. Chọn 1: `Main` hỏi Name/Group/Address bằng `promptString` (rỗng → `Name must not be blank.`, hỏi lại đúng ô đó), Phone bằng `promptPhone` → `Validation.getPhone`.
3. `Main` gói 4 giá trị vào `ContactRequestDTO` → `contactController.addContact(addDto)`.
4. `ContactController` → `contactRepository.addContact(dto)`: `generateNextId()` (ID cuối + 1) rồi `new Contact(...)` (tự tách tên), thêm vào `contactList`.
5. `Main` in `Successful`.
6. Display: Controller kiểm rỗng (→ ném `No found contact`), lấy `getContactList()` (đã đổi sang `ContactResponseDTO`) → `contactView.setContactList(...)` → `display()`.
7. Delete: `promptId` → `Validation.getId` (chữ/0/âm → `ID is digit`) → Controller → Repository `deleteContact(contactId)` trả `false` thì Controller ném `No found contact`.

## (d) Bảng import
| Lớp | Import | Nói với thầy |
|---|---|---|
| `Main` | Constants, Message, ContactController, ContactRequestDTO, Scanner, Validation | Main nhập + validate, gói DTO, chỉ gọi controller — không đụng model. |
| `ContactController` | Message, ContactRequestDTO, ContactRepository, ContactView | Controller không import model, chỉ nối Repository với View. |
| `ContactRepository` | Constants, ContactRequestDTO, ContactResponseDTO, ArrayList, Contact | Chỉ repository được đụng model: tạo Contact từ DTO và đổi Contact sang ResponseDTO. |
| `ContactView` | ContactResponseDTO, ArrayList | View chỉ biết ResponseDTO, nhận dữ liệu qua setter rồi in. |
| `Validation` | Constants, Message | Lấy regex/FIRST_ID từ Constants, câu lỗi từ Message. |
| `Contact` | (không) | Model thuần, tự tách tên trong constructor. |
| `ContactRequestDTO`, `ContactResponseDTO` | (không) | DTO chỉ chở dữ liệu. |
| `Message`, `Constants` | (không) | Chỉ chứa hằng, constructor private. |

## (e) Phím test (gõ theo thứ tự, 1 lần chạy)
| # | Gõ | Phải thấy |
|---|---|---|
| 1 | `2` | `No found contact` (chưa có ai) |
| 2 | `1` → `Raul Gonzalez` → `Star` → `Spain` → `09` → `1234567890` | 7 dòng `Please input Phone flow…`, hỏi lại phone, rồi `Successful` |
| 3 | `1` → (Enter trống) → `Ronaldo de Assis` → (trống) → `Star` → (trống) → `Brazil` → `123-456.7890` → `123-456-7890 ext1234` | `Name must not be blank.`, `Group…`, `Address…`, 7 dạng phone, rồi `Successful` |
| 4 | `1` → `Cher` → `Star` → `France` → `(123)-456-7890` | `Successful` |
| 5 | `2` | bảng 3 dòng: ID 1,2,3; Ronaldo / de Assis; Cher / (trống) |
| 6 | `3` → `a` → `0` → `-3` → `2` | 3 lần `ID is digit`, rồi `Successful` |
| 7 | `3` → `2` | `No found contact` |
| 8 | `1` → `Xavi Hernandez` → `Star` → `Spain` → `123.456.7890` rồi `2` | Xavi có ID **4** (ID cuối 3 + 1) |
| 9 | menu: `x`, trống, `0`, `5` | `Please choice one option from 1 to 4.` mỗi lần |
| 10 | `4` | thoát |

Thử thêm đủ 7 dạng phone: `1234567890`, `123-456-7890`, `123-456-7890 x1234`, `123-456-7890 ext1234`, `(123)-456-7890`, `123.456.7890`, `123 456 7890` — đều `Successful`.

## (f) Câu thầy hay hỏi
1. **Sao không có service?** — Bài chỉ thêm/hiện/xoá (CRUD), không có tính toán nghiệp vụ, nên Controller gọi thẳng Repository.
2. **ID cấp thế nào? Sao không dùng `size() + 1`?** — `generateNextId()` lấy ID contact cuối + 1, rỗng thì 1. `size() + 1` sai sau khi xoá (có thể trùng ID).
3. **Tách tên ở đâu?** — Trong constructor `Contact`: `indexOf(' ')`; không có dấu cách thì firstName = cả tên, lastName rỗng.
4. **Regex phone đọc thế nào?** — 5 nhánh nối bằng `|`; `\d{3}` = 3 chữ số, `\(` `\.` là ký tự thật, `( (x|ext)\d{4})?` là đuôi có hoặc không. `matches()` phải khớp cả chuỗi.
5. **Sao Controller không import model?** — Controller chỉ làm việc với DTO; Repository đổi `Contact` sang `ContactResponseDTO` rồi mới trả lên.
6. **Nhập sai thì sao chỉ hỏi lại ô đó?** — Mỗi ô có hàm `promptX` riêng trong Main, vòng `while(true)` + `try/catch`, sai thì in `e.getMessage()` và hỏi lại.
7. **Sao Validation static + constructor private?** — Không giữ dữ liệu, chỉ kiểm chuỗi; gọi thẳng `Validation.getPhone(...)`, không cho `new`.
8. **Xoá theo vị trí hay giá trị?** — Duyệt tìm ID rồi `remove(i)` theo vị trí `int`.
