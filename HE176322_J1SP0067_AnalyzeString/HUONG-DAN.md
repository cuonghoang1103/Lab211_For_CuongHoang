# J1.S.P0067 — Analyze String

> Bài xử lý chuỗi. Hai chỗ thầy dễ bắt bẻ: **regex `\d+`** (vì sao ra `321` chứ không ra `3, 2, 1`)
> và **số chính phương** — màn hình đề in **sai** (`[321, 22]`), em phải giải thích được vì sao bài
> em in `[]`.
>
> **Bản 21/09/2026 — sửa theo tờ checklist giấy 25 mục của thầy** (mục 10): thêm
> `repository/TextRepository` giữ chuỗi (model `InputText`) — tờ giấy 1.1 *"Bắt buộc phải có
> repository"*; View nhận `responseDTO` qua thuộc tính (`setResponseDTO`), `display()` không tham số;
> tên collection đuôi `List`/`Map` (`allList`, `numberMap`, `resultMap`…); dòng kết quả thành
> `String.format(Message.RESULT_…)`; `Main` thành `final` + constructor `private`. Đối chiếu lại đề
> từng ký tự: màn hình **không đổi**.

| | |
|---|---|
| Loại / LOC | Short Assignment · 39 LOC · 1 slot |
| Project | `HE176322_J1SP0067_AnalyzeString` |
| Chạy | NetBeans: **File ▸ Open Project** → **F6** |
| Lớp chạy | `main.Main` |
| Kiểm tự động | `python3 _tools/verify.py J1SP0067` → 6 kịch bản × 2 locale |

---

## 1. Đề bài nói gì

Nhập một chuỗi, in: **số ký tự**; các **số** (tất cả / chẵn / lẻ / chính phương); ký tự **hoa**,
**thường**, **đặc biệt**, **tất cả ký tự** (không tính chữ số).

Màn hình của bài (ví dụ của đề):

```
===== Analysis String program ====
Input String: 321sdhkjDFGH!@#$%^22fdsf3
-----Result Analysis------
Number of characters: 25
Perfect Square Numbers: []
Odd Numbers: [321, 3]
Even Numbers: [22]
All Numbers: [321, 22, 3]
Uppercase Characters: DFGH
Lowercase Characters: sdhkjfdsf
Special Characters: !@#$%^
All Characters: sdhkjDFGH!@#$%^fdsf
```

**Đề bắt buộc:**

| Thứ | Đề viết | Bài này đặt ở |
|---|---|---|
| Lớp `AnalysisString` chứa 2 hàm | Hint | `service/AnalysisString` |
| `public HashMap<String, List<Integer>> getNumber(String input)` | Function 1 | `AnalysisString.getNumber` (**private**, giữ kiểu trả về) |
| `public HashMap<String, StringBuilder> getCharacter(String input)` | Function 2 | `AnalysisString.getCharacter` (**private**) |
| Số: regex · chẵn `% 2 == 0` · lẻ `% 2 != 0` · chính phương `Math.sqrt` | Hint | `NUMBER_REGEX`, `% 2` + `isPerfectSquare` trong `getNumber` |
| Hoa `Character.isUpperCase()` · thường "ngược lại" · đặc biệt bằng regex | Hint | `getCharacter` |

---

## 2. Kiến thức cần biết

### 2.1 Regex `\d+` — một **dãy** chữ số là một số

`Pattern.compile("\\d+").matcher(s)`; mỗi lần `find()` nhảy tới **dãy chữ số liền nhau** tiếp theo.

| Lần `find()` | `group()` | Số |
|---|---|---|
| 1 | `321` (đầu chuỗi) | 321 |
| 2 | `22` (sau `^`) | 22 |
| 3 | `3` (cuối chuỗi) | 3 |
| 4 | — | `false` → dừng |

`\d` (không có `+`) sẽ ra `3, 2, 1, 2, 2, 3` — **sai** với màn hình đề.

### 2.2 Số chính phương — và vì sao đề in sai

Số chính phương = bình phương của một số nguyên: 0, 1, 4, 9, 16, 25, …

| Số | `Math.sqrt` | Làm tròn `r` | `r * r` | Chính phương? |
|---|---|---|---|---|
| 321 | 17.916… | 18 | 324 | ❌ |
| 22 | 4.690… | 5 | 25 | ❌ |
| 3 | 1.732… | 2 | 4 | ❌ |
| 49 | 7.0 | 7 | 49 | ✅ |

→ ví dụ của đề **không có** số chính phương nào; màn hình đề ghi `[321, 22]` là lỗi của đề.
Làm **tròn** rồi bình phương lại (không lấy phần nguyên) vì `sqrt` của số lớn có thể ra `4.9999999`.

### 2.3 Phân loại ký tự (bỏ qua chữ số)

| Ký tự | Nhánh | Vào |
|---|---|---|
| `s` | `isLowerCase` | thường + tất cả |
| `D` | `isUpperCase` | hoa + tất cả |
| `!` | khớp `[^a-zA-Z0-9]` | đặc biệt + tất cả |
| `3` | khớp `\d` → `continue` | không vào đâu (đã là **số**) |

| API | Dùng làm gì |
|---|---|
| `Pattern` / `Matcher.find()` / `group()` | tìm từng dãy chữ số |
| `String.matches(regex)` | cả chuỗi (1 ký tự) có khớp regex không |
| `StringBuilder.append` | nối ký tự không tạo chuỗi mới mỗi lần |
| `ArrayList.toString()` | ra đúng `[321, 22, 3]` |

---

## 3. Thiết kế

```
HE176322_J1SP0067_AnalyzeString/src/
├── model/      InputText              chuỗi người dùng gõ + getLength()
├── dto/        AnalysisRequestDTO     input          (main ──► controller)
│               AnalysisResponseDTO    9 câu trả lời dạng chữ (controller ──► view)
├── repository/ TextRepository         giữ InputText: saveInputText / getInputText
├── service/    AnalysisString         analyze · getNumber · getCharacter · isPerfectSquare
├── controller/ AnalysisController     service ──► view (setResponseDTO + display 1 lần)
├── view/       AnalysisView           field responseDTO; display() in khối kết quả
├── constants/  Message, Constants     câu chữ (RESULT_… = "…: %s"); 3 regex; 8 khoá của HashMap; số 2
├── utils/      Validation             getInput: không trống, số không vượt int
└── main/       Main                   Scanner, gọi controller 1 lần
```

| Câu hỏi | Trả lời |
|---|---|
| Sao `AnalysisString` ở `service`? | Đề bắt tên lớp; việc của nó là **tính toán nghiệp vụ** (phân tích) → Guide đặt ở service. |
| Sao bài có `repository`? | Tờ checklist 1.1: *"**Bắt buộc phải có repository**"*. Repository = **dữ liệu** + CRUD đơn giản: ở đây là chuỗi người dùng gõ (model `InputText`), với `saveInputText` / `getInputText`. Việc **phân tích** là nghiệp vụ nên nằm ở `AnalysisString` — đúng tầng *Controller ↔ Services ↔ Repository ↔ Model*. |
| Model là gì? | `InputText` — đối tượng bài mô tả (chuỗi được phân tích), tự trả lời "dài bao nhiêu". |
| Sao ResponseDTO chứa **chữ**, không chứa HashMap? | View chỉ in; không cần biết khoá của map. |

**Luồng chạy:**

```
Main: đọc + validate chuỗi (hỏi lại khi sai) ──► AnalysisRequestDTO ──► controller.analyzeString(requestDTO)   (gọi 1 lần)
   controller ──► analysisString.analyze(requestDTO)
                     ├─ repository.saveInputText(input)        → new InputText(input)
                     ├─ inputText = repository.getInputText()  (model)
                     ├─ numberMap = getNumber(text)  ·  characterMap = getCharacter(text)
                     └─ responseDTO: 9 câu trả lời dạng chữ
   controller ──► view.setResponseDTO(responseDTO) ──► view.display()                                       (render 1 lần)
```

### 3.1 Design Pattern trong bài

| Pattern | Ở đâu |
|---|---|
| **MVC** — thầy gọi là "MVC JSP" | controller điều hướng (như Servlet) · view hiển thị (như trang JSP) · model là JavaBean |
| **Facade** | controller: `Main` chỉ gọi `controller.analyzeString(requestDTO)`, không biết service/model/view phía sau |

> Bài chỉ 39 LOC nên **không thêm lớp pattern GoF** — ghi chú slide SOLID của thầy cảnh báo *"trừu tượng hoá sớm … vi phạm YAGNI"*. Bốn danh sách số được lọc bằng `if` ngay trong `getNumber`, luật chính phương tách thành hàm `isPerfectSquare`.

**Thầy hỏi "dùng pattern gì cho dễ mở rộng?"** → trả lời đủ 4 yếu tố GoF (slide *"Elements of a Design Pattern"*):

| Yếu tố | Trả lời |
|---|---|
| **Name** | Strategy (nhóm Behavioral) |
| **Problem** | nhiều luật lọc số (chẵn, lẻ, chính phương, nguyên tố…) |
| **Solution** | tách `interface NumberFilter { boolean accept(int number); }`; mỗi cách làm là 1 lớp `implements` nó (`EvenNumberFilter`, `PerfectSquareFilter`…); `AnalysisString` giữ danh sách filter và hỏi từng cái |
| **Consequences** | ➕ thêm cách làm = thêm 1 lớp, service đứng yên (**O**) · ➖ thêm 2 file — chỉ đáng khi có từ 2 cách trở lên |

### 3.2 SOLID trong bài

| Nguyên lý | Ở đâu |
|---|---|
| **S** | `InputText` mô tả chuỗi · `TextRepository` giữ chuỗi · `AnalysisString` phân tích · `AnalysisView` in · `Validation` kiểm |
| **O** | sửa luật chính phương chỉ sửa `isPerfectSquare`; đổi câu chữ chỉ sửa `Message` |
| **L** | bài chưa có lớp con riêng — chỉ `extends Object` |
| **I** | không có interface — bài chưa cần |
| **D** | `Main` chỉ phụ thuộc controller + DTO |

---

## 4. Code từng bước

> Thầy: *"code cái **model trước rồi đến data**"*.

| Bước | File | Việc |
|---|---|---|
| 1 | `model/InputText.java` | field `text` + ctor rỗng + ctor đủ + get/set + `getLength` + `toString` |
| 2 | `dto/AnalysisRequestDTO.java`, `AnalysisResponseDTO.java` | JavaBean (9 field chữ ở Response) |
| 3 | `repository/TextRepository.java` | field `inputText` · `saveInputText` · `getInputText` |
| 4 | `service/AnalysisString.java` | constructor; `analyze`; `getNumber` (regex + chẵn/lẻ + `isPerfectSquare`); `getCharacter` |
| 5 | `view/AnalysisView.java` | field `responseDTO` · `setResponseDTO` · `display` |
| 6 | `controller/AnalysisController.java` | `analyzeString` |
| 7 | `constants/Message.java`, `Constants.java` | câu chữ (`RESULT_…`); regex; khoá map |
| 8 | `utils/Validation.java` | `getInput` |
| 9 | `main/Main.java` | `final` + `private Main()`; `inputString` + gọi controller 1 lần |

**Bẫy hay gặp:**

1. Regex `\d` thay vì `\d+` → tách từng chữ số.
2. `(int) Math.sqrt(n)` rồi so → lỗi làm tròn với số lớn; phải `Math.round`.
3. Chữ số lọt vào "All Characters" — màn hình đề **không** có chữ số ở dòng đó → `continue`.
4. Dãy số quá dài (`99999999999`) làm `Integer.parseInt` văng lỗi → chặn trước ở `Validation`.

---

## 5. Test trước khi gọi thầy

| # | Gõ | Phải thấy |
|---|---|---|
| 1 | `321sdhkjDFGH!@#$%^22fdsf3` | đúng khối kết quả ở mục 1 (square `[]`) |
| 2 | *(Enter trống / toàn dấu cách)* | `Input must not be empty.` rồi hỏi lại |
| 3 | `12345678901x` | `Each number in the string must be at most 2147483647.` |
| 4 | `  Ab 0,1_49Z  ` | 10 ký tự; square `[0, 1, 49]`; even `[0]`; special ` ,_` |
| 5 | `16abc25XY!9` | square `[16, 25, 9]`; odd `[25, 9]`; even `[16]` |
| 6 | `hello` | mọi danh sách số `[]`; hoa trống; thường `hello` |
| 7 | `x2147395600y2147483647` | `2147395600` = 46340² là chính phương; số lớn nhất của int vẫn chạy |
| 8 | `007a` | số là `7` |

---

## 6. Debug

| Việc | Cách làm |
|---|---|
| Breakpoint | dòng `number = Integer.parseInt(matcher.group());` trong `AnalysisString.getNumber` |
| Chạy | **Ctrl+F5**, nhập `16abc9` |
| Quan sát | **Variables**: `number`; mở `allList`, `squareList`… thấy các list lớn dần |
| Bước | **F8** trong vòng `while (matcher.find())`; ở `if (isPerfectSquare(number))` bấm **F7** — xem `root` = 4 với 16 |

---

## 7. Câu hỏi thầy hay hỏi

| Câu hỏi | Trả lời mẫu |
|---|---|
| 4 tính chất OOP ở đâu? | **Đóng gói**: `text` `private` trong `InputText`; 9 field `private` trong DTO. **Kế thừa**: mọi lớp ngầm `extends Object`; ghi đè `toString()`. **Đa hình**: `@Override toString`; `ArrayList` cất vào chỗ khai báo `List` (đề bắt) — biến kiểu cha giữ đối tượng lớp con. **Trừu tượng**: `Main` chỉ gọi `controller.analyzeString(requestDTO)`. |
| Đề ghi `public getNumber`, sao em để `private`? | Chỉ `AnalysisString.analyze` gọi; thầy dặn `public` chỉ khi **lớp khác** gọi. Tên + kiểu trả về giữ đúng đề. |
| Sao `analyze` `public`? | `AnalysisController` gọi nó. |
| `getNumber` sao trả `HashMap`? | Đề bắt; một lần quét cho **nhiều** kết quả, mỗi kết quả một khoá. |
| `isPerfectSquare` sao trả `boolean`? | Chỉ cần có/không. |
| Static ở đâu? Bỏ thì sao? | Chỉ `Validation.getInput`, hằng trong `constants`, hàm `inputString` ở `Main`. Bỏ `static` ở `getInput` → `Validation.getInput(...)` lỗi biên dịch; phải bỏ `private` ctor và `new Validation()` trong `Main`. `service` không có static (Guide). |
| `numberPattern` sao là field? | Biên dịch regex một lần (trong constructor), dùng lại cho mọi lần gọi. |
| **Sao `ArrayList` mà không `List`? Khác nhau thế nào?** | `List` là **interface**, `ArrayList` là **lớp cài đặt** bằng mảng động (thêm cuối nhanh, lấy theo chỉ số nhanh). Em khai báo đúng kiểu cụ thể: `ArrayList<Integer> allList = new ArrayList<>()`. Chỉ chữ ký **đề bắt** `HashMap<String, List<Integer>>` giữ `List` — có comment `// brief:`; bỏ `ArrayList` vào đó được vì `ArrayList` **implements** `List`. |
| Độ phức tạp? | Quét chuỗi 1 lần: O(n) ký tự; mỗi số kiểm chẵn/lẻ/chính phương O(1) → tổng O(n). |
| Ký tự tiếng Việt `Đ`? | `Character.isUpperCase('Đ')` = true → vào **hoa** (xét hoa/thường **trước** regex đặc biệt). |
| View nhận dữ liệu thế nào? | Qua **thuộc tính**: controller gọi `analysisView.setResponseDTO(responseDTO)` rồi `analysisView.display()` — `display()` **không tham số**, gọi **1 lần** cho cả luồng (tờ checklist 1.1). |
| Validate ở đâu? | Ở `Main` qua `utils/Validation.getInput`: trống hoặc có dãy số quá `int` thì ném `Exception(Message…)`, `Main` bắt, in `e.getMessage()` rồi hỏi lại. Service chỉ nhận chuỗi đã hợp lệ. |
| Sao tên biến `numberMap`, `allList`, `resultMap`? | Tờ checklist 1.5: *"tên biến kiểu collection kết thúc bằng List"*, *"kiểu Map kết thúc bằng Map"*. `StringBuilder` không phải collection nên giữ `uppercase`, `special`… |
| Sao `Main` là `final` và có `private Main() { }`? | Tờ checklist 3.4: *"Class chỉ có static method thì phải có private contructor, và khai báo class là final"*. |

---

## 8. Thầy đổi yêu cầu tại chỗ

| Thầy bảo | Sửa file | Không đụng |
|---|---|---|
| Thêm **số nguyên tố** | hàm `isPrime` + 1 danh sách `primeList` trong `getNumber` + `Constants.KEY_PRIME` + câu `Message` + field DTO + dòng `analyze` + dòng `View` | `Main`, controller, repository |
| Tách từng chữ số | `Constants.NUMBER_REGEX = "\\d"` | mọi file khác |
| Đặc biệt không tính dấu cách | `Constants.SPECIAL_REGEX = "[^a-zA-Z0-9 ]"` | mọi file khác |
| In thêm tổng các số | field DTO + tính trong `analyze` + dòng `View` + nhãn | `Main` |

---

## 9. Chỗ khác với đề / bản cũ

| Chỗ | Đề / bản cũ | Bài này | Lý do |
|---|---|---|---|
| **Perfect Square** của ví dụ đề | màn hình đề: `[321, 22]` | `[]` | sai toán học (mục 2.2); Guideline bảo dùng `Math.sqrt` → theo **định nghĩa** |
| Dòng `Number of characters: 25` | màn hình đề **không** có | **có** (như bản cũ) | Program Specifications + Function 2 đều bắt *"Display the number of characters"* |
| `getNumber`/`getCharacter` | đề: `public` | `private` trong `AnalysisString` | chỉ lớp này gọi (luật access modifier của thầy) |
| Kiểu `List<Interger>` | đề gõ sai chính tả | `List<Integer>` | lỗi chính tả của đề |
| Số quá lớn | bản cũ văng `NumberFormatException` | báo `Each number in the string must be at most 2147483647.` | đề bắt `Integer` → chặn trước |
| Kiến trúc | bản cũ: `bo/ui`, Scanner trong `Validator`, 4 danh sách bằng `if` | MVC theo Guide; 4 danh sách bằng `if` trong `getNumber` + hàm `isPerfectSquare` | luật thầy; bài 39 LOC không cần lớp pattern |
| `Input must not be empty.` | đề không ghi | giữ như bản cũ | đề im lặng |
| Repository | bản trước 21/09: không có (*"không có dữ liệu lưu giữ"*) | `repository/TextRepository` giữ `InputText` | tờ checklist 1.1 *"Bắt buộc phải có repository"* |
| View | bản trước 21/09: `setResponse(response)` | `setResponseDTO(responseDTO)` + `display()` không tham số | tờ checklist 1.1 |
| Tên collection | `numbers`, `characters`, `all`, `square`, `odd`, `even`, `result`, biến `c` | `numberMap`, `characterMap`, `allList`, `squareList`, `oddList`, `evenList`, `resultMap`, `currentChar` | tờ checklist 1.5 |
| Dòng kết quả | `Message.LABEL_ODD + response.getOddNumbers()` | `String.format(Message.RESULT_ODD, …)` | tờ checklist 3.8 |

---

## 10. Tờ checklist 25 mục — bài này đạt thế nào

| Mục | Chỗ trong code |
|---|---|
| 1.1 MVC + repository | `repository/TextRepository` giữ model `InputText`; `AnalysisString` cất/đọc chuỗi qua repository rồi mới phân tích; controller chỉ import DTO/service/view; `AnalysisView` nhận `responseDTO` qua `setResponseDTO`, `display()` gọi **1 lần**; nhập + validate ở `Main` |
| 1.3 tên lớp | `AnalysisString` là **tên đề đặt** (danh từ "Analysis" đứng đầu) — giữ nguyên |
| 1.4 method = động từ | `analyze`, `analyzeString`, `getNumber`, `getCharacter`, `isPerfectSquare`, `saveInputText`, `inputString` |
| 1.5 tên biến | `numberMap`, `characterMap`, `resultMap` (Map); `allList`, `squareList`, `oddList`, `evenList` (List); `currentChar` thay `c`; không có `ID` |
| 2.6 + 3.7 khai báo đầu block, có khởi tạo | `getNumber`: `resultMap`, 4 list, `matcher`, `int number = 0;` ở đầu, trong `while` chỉ gán; `getCharacter`: `String character = "";`; `analyze`: `numberMap = null`, `characterMap = null`, `inputText = null`; `Main.inputString`: `String line = "";` |
| 2.8 dòng trống | trước mọi comment (kể cả comment field trong `Constants`, `Message`, DTO), sau vùng khai báo, sau `}` trước câu lệnh kế |
| 3.3 ngoặc | `if ((number % Constants.EVEN_DIVISOR) != 0)`; `return (root * root) == number;`; `Validation`: `(input == null) ? "" : input.trim()` |
| 3.4 | `public final class Main` + `private Main() { }`; `Validation`, `Constants`, `Message` cũng `final` + ctor private |
| 3.8 | không cộng chuỗi: `AnalysisView` in bằng `String.format(Message.RESULT_…)`; ký tự gom bằng `StringBuilder` |

**Chỗ cần hỏi thầy (đề đặt, tờ checklist bắt khác):** không có — `getNumber`/`getCharacter` giữ đúng tên
và kiểu trả về đề bắt (`HashMap<String, List<Integer>>` có comment `// brief:`), chỉ để `private` vì
chỉ `AnalysisString` gọi (mục 9).

Kiểm lại: `python3 _tools/verify.py J1SP0067` · `python3 _tools/lint.py HE176322_J1SP0067_*` · `python3 _tools/soat_checklist.py HE176322_J1SP0067_*` → 0 `VI_PHAM`.
`RUI_RO` còn lại chỉ là `String[] args` và tham số setter/constructor trùng tên field (`this.text = text`) — kiểu IDE sinh, được chấp nhận.
