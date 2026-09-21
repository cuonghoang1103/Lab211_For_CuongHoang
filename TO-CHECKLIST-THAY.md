# Tờ checklist GIẤY của thầy LAB211 (chép nguyên văn từ 2 tờ ảnh, 21/09/2026)

Tiêu đề: "Coding check sheet" | Mã - Tên SV | Confirm item | 5 cột "Date:" (mỗi cột Date chia 3 ô con)

| STT | Category | Confirm item (nguyên văn) |
|---|---|---|
| 1.1 | Common | Đã đúng MVC chưa? |
| | | + Main chỉ làm việc với Controller, DTO, Utils. Toàn bộ việc nhập dữ liệu/Validate/đọc từ file/mã hóa thực hiện ở Main. |
| | | + Controller nhận input từ main qua DTO, gửi/nhận data qua Services (Repository), không làm việc với Model, chỉ gửi kết quả cần hiển thị sang View. Không thực hiện print thông tin gì ở controller. |
| | | + Repository chỉ chứa data và CRUD methods đơn giản. Nếu có nghiệp vụ tính toán thì cần thêm Services và đảm bảo layer: Controller <-> Services <-> Repository <-> Model. **Bắt buộc phải có repository** |
| | | Cần hiển thị thông tin gì thì gọi qua View. **Việc rendering khi gọi view chỉ được gọi 1 lần cho 1 luồng xử lý (Mỗi luồng tính là 1 switch - case ở Main).** |
| | | + Services/Repository nhận data từ controller (Có thể thông qua param nếu số param < 3) -> xử lý nghiệp vụ và trả kết quả về controller. Được giao tiếp với Model. Không thực hiện print thông tin gì ở đây. |
| | | + Model chỉ làm nhiệm vụ miêu tả thực thể, không làm việc với View. Không thực hiện print thông tin gì ở đây. |
| | | + View: Chỉ nhận thông tin từ Controller. **Không nên truyền qua param mà phải nhận qua thuộc tính (Nên để ResponseDTO giống ví dụ).** |
| 1.2 | | + Package là chữ thường, thể hiện được ý nghĩa chung của package |
| 1.3 | | + Class bắt đầu bằng chữ hoa + Tên class bắt đầu bằng danh từ mô tả ý nghĩa của class. + Phải thể hiện được ý nghĩa mục đích của method + Đảm bảo S trong SOLID + **Tên của class exception kết thúc bằng "Exception"** + **Tên của interface bắt đầu bằng "I"** |
| 1.4 | | + Method bắt đầu bằng chữ thường + Tên method bắt đầu bằng động từ mô tả chức năng của method. + Phải thể hiện được ý nghĩa mục đích của method + Đảm bảo SRP trong SOLID |
| 1.5 | | + Tên biến bắt đầu bằng chữ thường và có ý nghĩa + **tên biến kiểu collection (list, colection) kết thúc bằng "List"** + **tên biến kiểu set (Set, HashSet,...) kết thúc bằng "Set"** + **tên biến kiểu Map (Map, HashMap, TreeMap,...) kết thúc bằng "Map"** + **tên biến kiểu Array kết thúc bằng Array** + **khi refer đến ID thì thống nhất viết là "Id" không viết là "ID"** |
| 1.6 | | Comment ngắn gọn, rõ ràng. Dùng Javadoc cho class/method nếu cần. Ví dụ: /** This method gets user by ID */. Đã có comment ở các vị trí sau hay chưa? - Mỗi method đều phải có comment miêu tả ý nghĩa của method - Mỗi block source đều phải có comment giải thích block đó làm gì |
| 2.1 | Coding Convention (Việc format code nên Dùng Ctrl+Shift+F (Eclipse) hoặc Ctrl+Alt+L (IntelliJ) hoặc Alt+Shift+F (Netbean)) | "{" nằm ở kết thúc của line. "}" nằm ở bắt đầu của line |
| 2.2 | | Dù block có 1 dòng code cũng đặt trong {} |
| 2.3 | | 1 line (không tính comment) không dài quá 100 kí tự. Khi line dài hơn 100 ký tự thì break ở các vị trí sau: + sau toán tử logic (and, or,...) + hạn chế break giữa biểu thức trong () + trước toán hạng (+, -, *,...) |
| 2.4 | | Mỗi khai báo biến để trên 1 dòng. |
| 2.5 | | Khai báo array thống nhất theo 1 kiểu Type [] anArray; |
| 2.6 | | **Biến được khai báo tập trung ở đầu mỗi block code.** |
| 2.7 | | Mỗi statement nằm trên 1 line. |
| 2.8 | | **Có 1 blank line giữa các method, giữa vùng khai báo biến và vùng còn lại, trước block comment, trước line comment, giữa các block code sử lý logic** |
| 2.9 | | Có 1 space ở các vị trí: + trước ( + sau "," + trước và sau các phép tính (=, +, - *,; (trong for)...) |
| 2.10 | | Tất cả hằng số cần để vào một class riêng đặt (Constants.java) + constant viết chữ hoa, phân cách bằng "_" + constant khai báo static final |
| 2.11 | | Tất cả message cần để vào một constant class riêng đặt (Message.java) + constant viết chữ hoa, phân cách bằng "_" + constant khai báo static final |
| 3.1 | Performance | Sử dụng class để truy cập vào biến, method static |
| 3.2 | | Không khai báo biến local trùng tên với biến high level (tờ 2: "higher level") |
| 3.3 | | **Sử dụng () để làm tường minh thứ tự các phép tính** (Sun CC §10.5.1: `if ((a == b) && (c == d))`) |
| 3.4 | | Class chỉ có static method thì phải có private contructor, Và khai báo class là final. |
| 3.5 | | + Khi so sánh giá trị của object chẳng hạn như là String thì phải dùng phương thức equals chứ không được dùng toán tử 「==」 + Khi so sánh text thì đã chú ý đến case sensitive chưa? |
| 3.6 | | Không được có biến khai báo mà không dùng ở đâu cả. |
| 3.7 | | **Biến có declare khi bắt đầu xử lý và thực hiện khởi tạo** |
| 3.8 | | Khi thực hiện cộng string thì sẽ dùng StringBuilder. không dùng String += String |

Chân trang tờ 1: "Mã - Tên SV: SV điền thông tin mã nhân viên và tên của mình. Điền ngày bắt đầu làm, bên dưới ghi mã bài toán (VD: P0061). Mỗi bài sẽ có 3 cột để thực hiện tự check/yêu cầu review 3 lần. SV sau khi code xong thì tự review source của mình theo checklist trên, item nào đã OK thì fill "O", khi nào tất cả các item trên 1 cột đã là "0" thì yêu cầu thầy review."

Tờ 2 có thêm hình MVC (Controller ở trên; View trái; Model phải; User dưới — Controller→View "Send Data", Controller↔Model "Request Data / Response Data", View→User "Response", User→Controller "Request").

## Guide.xlsx của thầy (tài liệu mẫu thầy phát, sheet `package diagram` + `sample`) — nguyên văn các câu then chốt
- Message.java: "Tất cả message/label cần khai báo chung ở đây, không hardcode trong các class khác"
- Controller: "Nhiệm vụ chỉ là nhận input từ main, điều hướng hoạt động của Services/Repository và View, chỉ import DTO, View, Service" · "Không static" · "Không được nhập trực tiếp từ bàn phím (nhận input qua DTO) hoặc print trực tiếp ra màn hình (Print qua View)."
- RequestDTO: "Đối tượng chứa data giao tiếp giữa main và controler, có thể truyền nguyên vẹn DTO này vào services và repository"
- ResponseDTO: "Đối tượng chứa data giao tiếp giữa controller và view, những gì sẽ hiển thị qua view khai báo ở đây"
- Main: "Chứa work flow chính, chỉ làm việc với validation, controller. Scanner cũng chỉ được sử dụng ở đây" · "**Mỗi workflow chính (Create student, report,…) chỉ gọi vào controller 1 lần duy nhất**" · "Cấm dùng static với biến, có thể dùng với hàm" · "Không gọi đến model và view, chỉ làm việc với DTO, validator, controller, FileUtils, CapchaUtils" · "Truyền data vào controller thông qua DTO param"
- Model: "Chỉ chứa thuộc tính và function của đối tượng đang mô tả, không được input từ scanner hoặc output (printf) ở đây" · "Không được dùng static" · "Cần output gì thì thêm hàm toString() để trả lại repository -> controller sẽ nhận kết quả và truyền vào view"
- Repository: "Chứa data, ví dụ danh sách sinh viên, danh sách bác sỹ sẽ nằm ở đây. Các method CRUD đơn giản đối với data chính cũng nằm ở đây. Nếu có các tính toán nghiệp vụ ngoài CRUD thì cần thêm class DoctorServices.java để thực hiện các nghiệp vụ này. Services nằm giữa Controller và Repo"
- Services: "Chứa các tính toán nghiệp vụ nếu có (Tính tổng, chu vi, diện tích, report,…). Services chỉ được gọi từ Controller và được phép import Model. Services không làm việc với input, output, View"
- utils/Validation: "Chứa các functions dùng chung như validate, đọc/ghi file, mã hóa dữ liệu. Chỗ này phải dùng static method" · "Chú ý final" · "Chú ý cần có private constructor"
- View: "Chứa ResponseDTO, việc hiển thị kết quả xử lý trên console sẽ thực hiện ở đây. Không được gọi print ngoài view và main."
- Code mẫu View của thầy: có field `private Map<String, DoctorResponseDTO> doctorMap;` + setter `setDoctorMap(...)` + `display()` KHÔNG tham số. Controller: `doctorView.setDoctorMap(result); doctorView.display();`
- Code mẫu Main của thầy: in `Message.MENU`, in câu nhắc nhập (`System.out.print(Message.INPUT_CODE)`), `catch (Exception e) { System.out.println(e.getMessage()); }` — tức Main được in menu/câu nhắc/lỗi; KẾT QUẢ thì qua View.
- Code mẫu Validation của thầy: `if((choice < min) || (choice > max))` — có ngoặc riêng cho từng phép so sánh.
