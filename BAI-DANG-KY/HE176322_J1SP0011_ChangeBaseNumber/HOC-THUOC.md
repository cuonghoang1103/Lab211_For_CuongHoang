# J1.S.P0011 — Change Base Number System (bản học thuộc)

## (a) Đề tóm 5 dòng
1. Chọn hệ VÀO: 1 = nhị phân (BIN), 2 = thập phân (DEC), 3 = thập lục phân (HEX); 0 = thoát.
2. Chọn hệ RA (1–3), rồi nhập giá trị.
3. In kết quả dạng `535 (DEC) = 217 (HEX)`.
4. Đổi bằng tay: hệ vào → thập phân (tổng chữ số × cơ số^vị trí) → hệ ra (chia lấy dư).
5. Lặp lại tới khi người dùng chọn 0 (`Goodbye.`).

## (b) Cây file
```
src/
├── constants/Message.java        mọi chữ hiện ra (menu, câu nhắc, lỗi, định dạng kết quả)
├── constants/Constants.java      số 0/1/3 của menu, bảng chữ số "0123456789ABCDEF", dấu - +
├── constants/Base.java           enum 3 hệ: số chọn, cơ số (2/10/16), nhãn (BIN/DEC/HEX)
├── model/BaseNumber.java         1 số: giá trị (chuỗi) + hệ
├── dto/ConvertRequestDTO.java    Main gửi xuống: hệ vào, hệ ra, giá trị
├── dto/ConvertResponseDTO.java   service gửi lên view: 2 giá trị + 2 nhãn, toString in 1 dòng
├── repository/BaseNumberRepository.java  lưu lịch sử các số đã nhập
├── service/ConvertService.java   tính toán: convert + 2 hàm private convertToDecimal / convertFromDecimal
├── controller/ConvertController.java  nhận DTO, gọi service, đưa kết quả cho view
├── view/ConvertView.java         in 1 dòng kết quả
├── utils/Validation.java         kiểm số menu, giá trị rỗng, chữ số đúng hệ
└── main/Main.java                Scanner, menu, hỏi từng ô
```

## (c) Luồng chức năng chính
1. `Main` in menu, `promptInputBase` → `Validation.getChoice(0..3)`; sai thì in lỗi, hỏi lại đúng ô đó; chọn 0 → `Goodbye.`.
2. `promptOutputBase` → `getChoice(1..3)`; `promptValue` → `Validation.getValue` (không rỗng).
3. `Main` gói vào `ConvertRequestDTO` (đổi số chọn → `Base.fromChoice`), gọi `Validation.checkValue` (chữ số đúng hệ; sai → in lỗi, về menu).
4. `convertController.convert(dto)` → `convertService.convert(dto)`: lưu `BaseNumber` vào repository.
5. Service: `convertToDecimal` (nhân kết quả với cơ số rồi cộng chữ số; quá `long` → `TOO_BIG`) rồi `convertFromDecimal` (chia lấy dư, ghép số dư từ phải sang trái).
6. Service trả `ConvertResponseDTO` → controller `setResponseDTO` → `ConvertView.display()` in `535 (DEC) = 217 (HEX)`.

## (d) Bảng import
| Lớp | Import | Nói với thầy |
|---|---|---|
| Main | Base, Constants, Message, ConvertController, ConvertRequestDTO, Scanner, Validation | Main nhập + validate, chỉ biết controller và RequestDTO. |
| ConvertController | ConvertRequestDTO, ConvertResponseDTO, ConvertService, ConvertView | Controller không import model, chỉ chuyển DTO. |
| ConvertService | Constants, Message, 2 DTO, BaseNumber, BaseNumberRepository | Đổi hệ là tính toán nên có service; thuật toán là 2 hàm private, không cần interface. |
| BaseNumberRepository | ArrayList, List, BaseNumber | Giữ danh sách số đã nhập. |
| ConvertView | ConvertResponseDTO | View chỉ biết ResponseDTO, chỉ in. |
| ConvertResponseDTO | Message | Lấy định dạng `%s (%s) = %s (%s)` từ Message để in 1 dòng. |
| ConvertRequestDTO / BaseNumber | Base | Giữ hệ cơ số dạng enum. |
| Validation | Base, Constants, Message | Kiểm dữ liệu, sai thì `throw new Exception(Message.X)`. |
| Message / Constants / Base | (không import) | Chỉ chứa hằng số / enum. |

## (e) Phím test
| # | Vào / Ra / Giá trị | Phải thấy |
|---|---|---|
| 1 | `x`, `5` ở ô INPUT | `You must input a number.` · `Please choose from 0 to 3.` |
| 2 | 2 / 3 / `535` | `535 (DEC) = 217 (HEX)` |
| 3 | 3 / 2 / `217` | `217 (HEX) = 535 (DEC)` |
| 4 | 2 / 1 / `27` | `27 (DEC) = 11011 (BIN)` |
| 5 | 1 / 2 / `11011` | `11011 (BIN) = 27 (DEC)` |
| 6 | 3 / 1 / `ff` · 2 / 1 / `-27` · 2 / 2 / `007` | `= 11111111 (BIN)` · `= -11011 (BIN)` · `007 (DEC) = 7 (DEC)` |
| 7 | 2 rồi ô OUTPUT `x`, `5`, `0`, `3`, giá trị trống, `255` | 2 câu lỗi menu, `You must input something.`, `255 (DEC) = FF (HEX)` |
| 8 | 3 / 2 / `1G` · 1 / 2 / `2` · 2 / 1 / `-` | `1G is not a valid HEX number.` · `2 is not a valid BIN number.` · `- is not a valid DEC number.` |
| 9 | 2 / 3 / `9223372036854775807` · `9223372036854775808` | `= 7FFFFFFFFFFFFFFF (HEX)` · `The value is too big for this program.` |
| 10 | `0` | `Goodbye.` |

## (f) Câu thầy hay hỏi
1. **Sao có service?** Đổi hệ cơ số là tính toán nghiệp vụ, không phải CRUD → để ở `ConvertService`.
2. **Sao không có interface/Strategy?** Chỉ có 1 cách đổi, 2 hàm private trong service là đủ; thêm interface là thừa.
3. **Hệ bất kỳ → thập phân làm sao?** Duyệt chữ số từ trái: `kết quả = kết quả × cơ số + chữ số`. Ví dụ `11011`: 1 → 3 → 6 → 13 → 27, bằng 1·2⁴ + 1·2³ + 0·2² + 1·2¹ + 1·2⁰.
4. **Thập phân → hệ khác làm sao?** Chia liên tiếp cho cơ số, lấy số dư, ghép số dư từ dưới lên: 535 / 16 = 33 dư 7, 33 / 16 = 2 dư 1, 2 / 16 = 0 dư 2 → `217`.
5. **Chữ số A–F lấy ở đâu?** `Constants.DIGITS = "0123456789ABCDEF"`: vị trí trong chuỗi chính là giá trị (`indexOf`), và ngược lại `charAt(số dư)`.
6. **Số quá lớn thì sao?** Trước mỗi lần nhân kiểm `result > (Long.MAX_VALUE - digit) / radix` → ném `TOO_BIG`, không bị tràn ra số âm.
7. **Sao nhập sai chữ số thì về menu chứ không hỏi lại?** Giữ đúng màn hình bài đã kiểm: sai hệ của giá trị thì in lỗi rồi chọn lại từ đầu (vì có thể người dùng chọn nhầm hệ). Sai ô menu hoặc để trống thì hỏi lại đúng ô đó.
8. **Controller có import model không?** Không — chỉ DTO, Service, View; model `BaseNumber` chỉ service và repository dùng.
