# J1.S.P0074 — Matrix Calculator

| | |
|---|---|
| Loại / LOC | Short Assignment · 100 LOC · 2 slot |
| Project | `HE176322_J1SP0074_MatrixCalculator` |
| Chạy | NetBeans: **File ▸ Open Project** → **F6** |
| Lớp chạy | `main.Main` |
| Kiểm tự động | `python3 _tools/verify.py J1SP0074` → 4 kịch bản × 2 locale |

---

## 1. Đề bài nói gì

- Menu 4 mục: **cộng · trừ · nhân ma trận · Quit**.
- Nhập số dòng, số cột, rồi từng giá trị của **ma trận 1**, sau đó của **ma trận 2**.
- Giá trị không phải số → *"Values of matrix must be the number"* rồi hỏi lại đúng ô đó.
- In kết quả: ma trận 1, dấu phép tính, ma trận 2, `=`, kết quả — mỗi ô dạng `[3]`.

Màn hình đề (phép cộng):

```
-------- Addition --------
Enter Row Matrix 1:2
Enter Column Matrix 1:3
Enter Matrix1[1][1]:2
...
-------- Result --------
[2][3][2]
[1][2][2]
+
[1][32][3]
[2][21][23]
=
[3][35][5]
[3][23][25]
```

**Đề bắt buộc:**

| Thứ | Đề viết | Bài này đặt ở |
|---|---|---|
| `public int[][] additionMatrix(int[][] matrix1, int[][] matrix2)` | Function 1 | `service/MatrixCalculator.addMatrix(int[][] matrix1Array, int[][] matrix2Array)` — cùng `public`, cùng kiểu trả về, cùng kiểu và thứ tự tham số; dòng ngay trên khai báo có `// brief: public int[][] additionMatrix(int[][] matrix1, int[][] matrix2)` (đổi tên vì tờ checklist 1.4/1.5, xem §9) |
| `public int[][] subtractionMatrix(...)` | Function 2 | `MatrixCalculator.subtractMatrix(...)` + dòng `// brief:` |
| `public int[][] multiplicationMatrix(...)` | Function 3 | `MatrixCalculator.multiplyMatrix(...)` + dòng `// brief:` |
| `Values of matrix must be the number` | Function details | `constants/Message.INVALID_VALUE` |

---

## 2. Kiến thức cần biết

### 2.1 Điều kiện kích thước

| Phép | Điều kiện | Kết quả có kích thước |
|---|---|---|
| Cộng / trừ | `rows1 == rows2` **và** `cols1 == cols2` | `rows1 × cols1` |
| Nhân | `cols1 == rows2` | `rows1 × cols2` |

Sai điều kiện → bài báo `Two matrixes must have the same number of rows and columns.` hoặc
`Number of columns of matrix 1 must equal number of rows of matrix 2.` **ngay sau khi nhập kích
thước ma trận 2**, trước khi bắt gõ giá trị (không bắt người dùng gõ cả ma trận rồi mới báo). Việc
kiểm do `Main.checkMatrixSize` gọi `Validation.checkSameSize` / `Validation.checkMultiplySize`.

### 2.2 Nhân ma trận — chạy tay ví dụ của đề

`kq[i][j] = Σ m1[i][k] * m2[k][j]` (k chạy qua `cols1 = rows2`).

| Ô | Tính | = |
|---|---|---|
| `kq[1][2]` | `2·1000 + 3·100 + 4·10 + 5·10` | **2390** |
| `kq[2][2]` | `1·1000 + 1·100 + 1·10 + 5·10` | **1160** |
| `kq[1][1]` | cột 1 của m2 toàn 0 | **0** |

Ba vòng `for`: dòng của m1 → cột của m2 → k. **`sum = 0` phải khai báo trong vòng cột**, để mỗi
ô bắt đầu lại từ 0.

### 2.3 Độ phức tạp

| Phép | Số phép tính |
|---|---|
| Cộng / trừ | `m·n` → O(n²) |
| Nhân `(m×n)·(n×p)` | `m·n·p` → O(n³) với ma trận vuông |

### 2.4 Java dùng trong bài

| API | Dùng làm gì |
|---|---|
| `int[][] a = new int[rows][cols]` | mảng 2 chiều; `a.length` = số dòng, `a[0].length` = số cột |
| `Integer.parseInt` | `"a"`, `"1.5"`, dòng trống → `NumberFormatException` |
| `String.format("[%d]", v)` | một ô đúng dạng màn hình đề |
| `StringBuilder` | ghép chuỗi nhiều dòng trong `Matrix.toString()` |

---

## 3. Thiết kế

```
src/
├── model/      Matrix                ma trận (JavaBean): int[][] valueArray + get/set + toString
├── dto/        MatrixDTO             MỘT ma trận người dùng gõ: số thứ tự (1/2) + valueArray
│               MatrixRequestDTO      phép tính + 2 MatrixDTO   (main ──► controller)
│               MatrixResponseDTO     4 chuỗi để in             (controller ──► view)
├── repository/ MatrixRepository      giữ 2 ma trận (model): saveMatrixes · getFirstMatrix · getSecondMatrix
├── service/    MatrixCalculator      3 hàm của đề: addMatrix · subtractMatrix · multiplyMatrix (dòng // brief: tên đề)
│               IMatrixOperation      «interface» Strategy: calculate · getSymbol
│               AdditionOperation · SubtractionOperation · MultiplicationOperation
│               OperationFactory      lựa chọn menu ──► đúng Operation
│               MatrixService         Context: lưu 2 ma trận vào repository, đọc lại, gọi strategy, đóng gói kết quả
├── controller/ MatrixController      calculateMatrix: service ──► view (setResponseDTO + display 1 lần)
├── view/       MatrixView            field responseDTO; display() không tham số in khối Result
├── constants/  Message, Constants
├── utils/      Validation            getChoice · getSize · getValue · checkSameSize · checkMultiplySize
└── main/       Main                  final + private Main(); menu + Scanner + kiểm kích thước; mỗi case gọi controller 1 lần
```

| Lớp | Vì sao ở đây |
|---|---|
| `MatrixCalculator` | tính toán = nghiệp vụ → `service` (Guide) |
| `MatrixRepository` | tờ checklist 1.1 *"Bắt buộc phải có repository"*: dữ liệu của bài là **2 ma trận người dùng gõ** → repository giữ chúng (dạng model `Matrix`) + CRUD đơn giản; service lấy ma trận **từ repository** rồi mới tính |
| `Validation.checkSameSize` / `checkMultiplySize` | kiểm kích thước là **validate dữ liệu nhập** → tờ checklist 1.1: *"Toàn bộ việc nhập dữ liệu/Validate … thực hiện ở Main"*; `Main.checkMatrixSize` gọi ngay sau kích thước ma trận 2 |
| `MatrixDTO` | để hàm nhập của `Main` chỉ có **2 tham số** `(sc, matrixDTO)` — thầy cấm 3 tham số |

**Luồng phép cộng:**

```
Main: createRequest(1) in tiêu đề ─► inputSize/inputValues ma trận 1 ─► inputSize ma trận 2
   ─► checkMatrixSize(requestDTO) → Validation.checkSameSize   (kiểm ngay — sai thì throw, Main in lỗi, về menu)
   ─► inputValues ma trận 2 ─► controller.calculateMatrix(requestDTO)   (case gọi controller đúng 1 lần)
        service: repository.saveMatrixes(requestDTO) → getFirstMatrix / getSecondMatrix
                 factory.createOperation(1) → AdditionOperation
                 calculate (= calculator.addMatrix) → MatrixResponseDTO
        controller: view.setResponseDTO(responseDTO) → view.display()   (1 lần)
```

### 3.1 Design Pattern

| Pattern | Name · Problem · Solution · Consequences |
|---|---|
| **Strategy** | **Problem**: 3 phép tính khác nhau ở 2 chỗ (cách tính, dấu in ra); viết `if (choice == 1) ... else if ...` rải khắp service. **Solution**: `IMatrixOperation` = Strategy; `AdditionOperation`/`SubtractionOperation`/`MultiplicationOperation` = ConcreteStrategy; `MatrixService` = Context (chỉ gọi `operation.calculate/getSymbol`). **Consequences**: ✅ thêm phép mới = thêm 1 lớp, `MatrixService`/controller/view không sửa (**O**CP); ❌ nhiều file hơn. |
| **Factory Method** | **Problem**: ai biết "lựa chọn 2 → `SubtractionOperation`"? **Solution**: `OperationFactory.createOperation(int)` là **nơi duy nhất** có `new XxxOperation`. **Consequences**: ✅ thêm phép chỉ thêm 1 `case`; ❌ factory phải sửa khi thêm loại. |
| **Facade** | `MatrixController`: `Main` chỉ thấy 1 hàm `calculateMatrix`, không biết service/repository/factory/strategy. |
| **MVC** (thầy: "MVC JSP") | Controller ~ Servlet, View ~ JSP, `Matrix` + DTO là JavaBean. |

**Thầy bảo "thêm phép nhân từng ô"**: tạo `ElementProductOperation implements IMatrixOperation`,
thêm 1 `case` trong `OperationFactory`, thêm dòng menu + hằng số menu; luật kích thước giống phép cộng
→ `Main.checkMatrixSize` dùng lại `Validation.checkSameSize`. `MatrixService`, `MatrixRepository`,
`MatrixController`, `MatrixView` **giữ nguyên**.

---

## 4. Code từng bước

> Thầy: *"code cái **model trước rồi đến data**"*.

| Bước | File | Việc |
|---|---|---|
| 1 | `model/Matrix.java` | field `int[][] valueArray`, 2 constructor, get/set, `toString` |
| 2 | `dto/MatrixDTO`, `MatrixRequestDTO`, `MatrixResponseDTO` | JavaBean: constructor rỗng + get/set |
| 3 | `repository/MatrixRepository.java` | field `firstMatrix`, `secondMatrix`; `saveMatrixes(requestDTO)` · `getFirstMatrix` · `getSecondMatrix` |
| 4 | `service/MatrixCalculator.java` | **3 hàm của đề**: `addMatrix`, `subtractMatrix`, `multiplyMatrix` — dòng `// brief:` (chữ ký của đề) ngay trên mỗi hàm |
| 5 | `service/IMatrixOperation.java` + 3 lớp Operation | Strategy |
| 6 | `service/OperationFactory.java`, `MatrixService.java` | Factory + Context |
| 7 | `view/MatrixView.java` | field `responseDTO`, `setResponseDTO`, `display` |
| 8 | `controller/MatrixController.java` | `calculateMatrix` |
| 9 | `constants/Message.java`, `Constants.java` | chữ trên màn hình / số menu, `MAX_SIZE = 20`, `CELL_FORMAT` |
| 10 | `utils/Validation.java` | `getChoice`, `getSize`, `getValue` (1 hàm `parseNumber` chung), `checkSameSize`, `checkMultiplySize` |
| 11 | `main/Main.java` | `public final class Main` + `private Main()`; menu, `createRequest`, `inputSize`, `checkMatrixSize`, `inputValues`, `inputValue` |

**Bẫy hay gặp:**

1. Kết quả phép nhân tạo bằng `new int[m1.length][m1[0].length]` → sai kích thước. Đúng: `[rows1][cols2]`.
2. `sum` khai báo ngoài vòng cột → từ ô thứ 2 trở đi cộng dồn sai.
3. Người dùng đếm từ **1** (`Matrix1[1][1]`) còn mảng đếm từ **0** → in `row + 1`, `column + 1`.

---

## 5. Test trước khi gọi thầy

| # | Gõ | Phải thấy |
|---|---|---|
| 1 | `1`, 2×3, gõ `a` ở ô `[2][1]` | `Values of matrix must be the number` rồi hỏi lại `Enter Matrix1[2][1]:` |
| 2 | tiếp ví dụ cộng của đề | `[3][35][5]` / `[3][23][25]` |
| 3 | `2`, 3×3: 1..9 trừ 10..18 | 9 ô `[-9]` |
| 4 | `3`, (3×4)·(4×2) của đề | `[0][2390]` / `[0][1160]` / `[0][1160]` |
| 5 | menu `x` | `Your choice must be a number.` |
| 6 | menu `9`, `0` | `Please input a number in [1, 4].` |
| 7 | số dòng `abc` | `Size must be a positive number.` |
| 8 | số dòng `0`, `21` | `Please input a number in [1, 20].` |
| 9 | cộng 1×2 với 2×1 | `Two matrixes must have the same number of rows and columns.` — **không** hỏi giá trị ma trận 2 |
| 10 | trừ 2×2 với 2×3 | như dòng 9 |
| 11 | nhân 2×3 với 2×3 | `Number of columns of matrix 1 must equal number of rows of matrix 2.` |
| 12 | giá trị `1.5`, dòng trống, số âm `-3` | 2 lần thông báo lỗi, `-3` được nhận; `[-3] * [-4] = [12]` |
| 13 | `4` | `Bye.` |

---

## 6. Debug

| Việc | Cách làm |
|---|---|
| Breakpoint | dòng `sum += (matrix1Array[row][k] * matrix2Array[k][column]);` trong `MatrixCalculator.multiplyMatrix` |
| Chạy | **Ctrl+F5**, chọn `3`, nhập (2×2)·(2×2) nhỏ |
| Quan sát | tab **Variables**: `row`, `column`, `k`, `sum`; mở `resultArray` |
| Bước | **F8** qua từng `k`, xem `sum` tăng; ở `MatrixService.calculateMatrix` bấm **F7** vào `operation.calculate(...)` — thấy nhảy vào `MultiplicationOperation` (đa hình qua interface) |
| Chứng minh kiểm trước | breakpoint ở `checkMatrixSize(requestDTO);` trong `Main`, nhập cộng 2×2 với 2×3 → F7 đến `Validation.checkSameSize` → `throw` → rơi vào `catch` của `Main` (controller chưa hề được gọi) |

---

## 7. Câu hỏi thầy hay hỏi

### OOP

| Câu hỏi | Trả lời mẫu |
|---|---|
| 4 tính chất OOP ở đâu? | **Đóng gói**: `valueArray` là `private` trong `Matrix`, chỉ đọc qua getter. **Kế thừa**: 3 lớp `implements IMatrixOperation`; mọi lớp `extends Object`, em ghi đè `toString()`. **Đa hình**: `MatrixService` gọi `operation.calculate(...)` — biến kiểu interface, chạy bản của lớp cụ thể. **Trừu tượng**: `IMatrixOperation` chỉ nói "tính được, có dấu phép tính", không nói cách làm. |
| Sao kiểm kích thước ở `Main` (qua `Validation`) mà không ở service? | Tờ checklist 1.1: *"Toàn bộ việc nhập dữ liệu/Validate … thực hiện ở Main"*. Kiểm **ngay** sau kích thước ma trận 2 để không bắt gõ cả ma trận rồi mới báo; và nhờ vậy mỗi case chỉ gọi controller **1 lần**. **Cách cộng/nhân** mới là nghiệp vụ → service. |

### Access modifier, static, kiểu trả về

| Câu hỏi | Trả lời mẫu |
|---|---|
| 3 hàm của đề sao `public`? | Đề ghi `public`, và **lớp khác gọi** (3 lớp Operation). |
| Sao `addMatrix` mà đề là `additionMatrix`? | Tờ checklist 1.4: tên method **mở đầu bằng động từ** — `addition` là danh từ. Em giữ đúng chữ ký của đề (`public int[][] …(int[][], int[][])`), chỉ đổi tên, và để dòng `// brief: public int[][] additionMatrix(int[][] matrix1, int[][] matrix2)` ngay trên khai báo. Tham số `matrix1Array`/`matrix2Array` theo 1.5 (mảng kết thúc bằng `Array`). Thầy muốn giữ tên đề thì **Refactor ▸ Rename** là xong. |
| Hàm nhập/kiểm trong `Main` sao `private static`? | Chỉ `main` gọi; Guide cho `static` với hàm ở Main. |
| `calculateMatrix` sao `public`? | `Main` gọi controller; controller gọi service — gọi từ lớp khác. |
| Field `calculator` trong mỗi Operation sao `private`? | Chỉ lớp đó dùng; nhận qua constructor. |
| `static` ở đâu? | Chỉ `Validation` (Guide: *"phải dùng static method"*), hằng trong `constants`, hàm trong `Main` (Guide: *"cấm static với biến, có thể dùng với hàm"*). |
| **Bỏ `static` ở `Validation.getValue`?** | Lỗi biên dịch ở `Validation.getValue(...)`. Muốn chạy: bỏ constructor `private`, tạo `Validation v = new Validation();` trong `Main`, gọi `v.getValue(...)`. |
| `addMatrix` trả `int[][]` sao không `void`? | Đề ghi thế, và trả **mảng mới** giữ nguyên 2 ma trận vào — màn hình còn phải in lại chúng. |
| `Validation.checkSameSize` trả `void`? | Kết quả đúng thì không có gì để trả; sai thì `throw` kèm thông báo. |
| `saveMatrixes` trả `void`? | Chỉ cất 2 ma trận; service đọc lại bằng `getFirstMatrix`/`getSecondMatrix`. |
| Sao không có `List`/`ArrayList`? | Ma trận có kích thước cố định sau khi nhập → `int[][]` là đúng kiểu; đề cũng dùng `int[][]`. (`List` là interface, `ArrayList` là lớp cài đặt bằng mảng động — dùng khi số phần tử thay đổi.) |

### SOLID & thiết kế

| Câu hỏi | Trả lời mẫu |
|---|---|
| SOLID ở đâu? | **S**: `Matrix` giữ số, `MatrixRepository` cất 2 ma trận, `MatrixCalculator` tính, `OperationFactory` tạo, `MatrixView` in, `Validation` kiểm. **O**: thêm phép = thêm lớp (3.1). **L**: 3 Operation thay nhau được ở chỗ `IMatrixOperation`. **I**: interface chỉ 2 hàm, lớp nào cũng dùng đủ. **D**: `MatrixService` phụ thuộc `IMatrixOperation` (trừu tượng). |
| `Main` gọi controller mấy lần mỗi phép? | **1 lần** (`calculateMatrix`). Bản trước gọi thêm `controller.checkMatrixSize` để kiểm trước — thành 2 lần/case, trái Guide *"Mỗi workflow chính chỉ gọi vào controller 1 lần duy nhất"* và đề không bắt kiểm kiểu đó. Nay `Main` tự kiểm qua `Validation` (vẫn **ngay** sau kích thước ma trận 2, màn hình không đổi). |
| Sao có `MatrixDTO`? | Thầy cấm hàm 3 tham số: `inputValues(sc, số thứ tự, mảng)` → gói "số thứ tự + mảng" vào `MatrixDTO`. |
| Tràn số `int`? | Giá trị lớn nhân nhau có thể vượt `int` — đề dùng `int[][]` nên giữ; muốn an toàn đổi sang `long[][]`. |

### Kiến trúc theo tờ checklist

| Câu hỏi | Trả lời mẫu |
|---|---|
| Sao bài có repository? | Tờ checklist 1.1: *"Bắt buộc phải có repository"*. Dữ liệu của bài là 2 ma trận người dùng gõ → `MatrixRepository` giữ `firstMatrix`, `secondMatrix` (model `Matrix`) + `saveMatrixes`/`getFirstMatrix`/`getSecondMatrix`; không tính, không in. `MatrixService` lấy 2 ma trận **từ repository** rồi mới gọi strategy. Luồng: **Controller → Service → Repository → Model**. |
| View nhận dữ liệu thế nào? | Qua **thuộc tính**: `MatrixView` có `private MatrixResponseDTO responseDTO` + `setResponseDTO(...)`; `display()` **không tham số**. Controller gọi `setResponseDTO` rồi `display()` **đúng 1 lần**. |
| Validate ở đâu? | Toàn bộ ở `Main` qua `utils/Validation`: số menu, kích thước (1..20), từng giá trị, và **kích thước có hợp phép tính không** (`checkSameSize`/`checkMultiplySize`). Controller chỉ nhận `MatrixRequestDTO` đã sạch. |
| Controller có đụng model không? | Không: `MatrixController` chỉ import `MatrixRequestDTO`, `MatrixResponseDTO`, `MatrixService`, `MatrixView`. Model `Matrix` do repository tạo, service dùng. |

### Tờ checklist 25 mục — bài này đạt thế nào

| Mục | Chỉ vào đâu |
|---|---|
| **1.1** MVC + repository | `repository/MatrixRepository` (giữ 2 ma trận); `MatrixController` không import `model`; `MatrixView` nhận `responseDTO` qua setter, `display()` không tham số, gọi **1 lần**; mỗi case của `Main` gọi controller **1 lần** (kiểm kích thước ở `Main.checkMatrixSize` → `Validation`) |
| **1.3 / 1.4** tên | interface `IMatrixOperation` (bản trước `MatrixOperation`); `addMatrix`/`subtractMatrix`/`multiplyMatrix` (đề: `additionMatrix`…, có dòng `// brief:`) |
| **1.5** tên mảng | `valueArray` (`Matrix`, `MatrixDTO`, `Main.inputValues`), `matrix1Array`/`matrix2Array`/`resultArray` (`MatrixCalculator`), `firstArray`/`secondArray` (`Validation`, `Main.checkMatrixSize`) — bản trước `values`, `matrix1`, `matrix2`, `result` |
| **2.6 / 3.7** khai báo đầu block + khởi tạo | `Main.main`: `requestDTO = null`, `running`, `choice = 0` ở đầu; mọi hàm hỏi lại: `String line = ""` đầu hàm, trong vòng chỉ gán; `Main.createRequest`: `requestDTO` đầu hàm; `MatrixService.calculateMatrix`: `responseDTO`, `operation = null`, 3 `Matrix … = null` đầu hàm; `MatrixCalculator.multiplyMatrix`: `sum = 0` đầu thân vòng cột |
| **2.8** dòng trống | giữa các field (mọi lớp, cả `Constants`/`Message`), trước mọi comment đứng sau dòng code (cả comment `case`), sau vùng khai báo biến, sau `}` trước câu lệnh tiếp |
| **3.3** ngoặc | `Validation.getChoice/getSize`: `((choice < min) \|\| (choice > max))`; `Validation.checkSameSize`: `if ((firstArray.length != secondArray.length) \|\|` xuống dòng **sau** `\|\|`; `MatrixCalculator.multiplyMatrix`: `sum += (matrix1Array[row][k] * matrix2Array[k][column])` |
| **3.4** lớp chỉ có static | `Main` (`final` + `private Main()`), `Validation`, `Constants`, `Message` |
| **3.8** cộng chuỗi | `Matrix.toString()` dùng `StringBuilder` + `String.format(Constants.CELL_FORMAT, …)`; không `+=` trên chuỗi |

---

## 8. Thầy đổi yêu cầu tại chỗ

| Thầy bảo | Sửa | Không đụng |
|---|---|---|
| Thêm phép mới (nhân từng ô, chia…) | lớp `XxxOperation`, 1 `case` ở `OperationFactory`, `Message.MENU` + tiêu đề, `Constants` số menu, `case`/tiêu đề trong `Main.createRequest`, luật kích thước trong `Main.checkMatrixSize` | `MatrixService`, `MatrixRepository`, `MatrixController`, `MatrixView` |
| Cho nhập số thực | `int[][]` → `double[][]` ở model/DTO/calculator/`Validation.checkSameSize…`, `Validation.getValue` parse `double`, `CELL_FORMAT` + `Locale.US` | luồng controller |
| Kích thước tối đa 10 | `Constants.MAX_SIZE` | mọi file khác |
| In thêm "Result is square matrix" | `MatrixResponseDTO` thêm field, `MatrixService` điền, `MatrixView` in | `Main` |

---

## 9. Chỗ khác với đề / bản cũ

| Chỗ | Đề / bản cũ | Bài này | Lý do |
|---|---|---|---|
| Thông báo giá trị sai | ảnh đề: `Value of matrix is digit` | `Values of matrix must be the number` | chữ trong **Function details** của đề (và thầy dặn giữ đúng câu này); ảnh mâu thuẫn với chữ |
| Tiêu đề Subtraction/Multiplication | ảnh: số dấu `-` hai bên lệch nhau | `-------- Subtraction --------` (8 dấu mỗi bên, như Addition/Result) | ảnh bị cắt/lệch, thống nhất theo Addition |
| Kiểm kích thước | đề không nói | báo lỗi ngay sau kích thước ma trận 2, về menu | nhân/cộng sai kích thước là vô nghĩa; không bắt gõ thừa |
| Giới hạn kích thước | đề không nói | 1..20 | gõ nhầm `9999` sẽ bị hỏi hàng triệu ô |
| Thông báo menu/kích thước, `Bye.` | đề không có | chữ của bản cũ | đề im lặng → giữ như bản cũ |
| Bản cũ: menu, prompt, cách in | `Matrix Calculator`, `Element [1][1]:`, in cột căn lề | theo **ảnh đề** | ảnh đề là chuẩn |
| Kiến trúc | `entity/bo/ui`, Scanner trong `Validator` | MVC Guide + Strategy + Factory | luật thầy |
| **Tên 3 hàm của đề** | đề: `additionMatrix`, `subtractionMatrix`, `multiplicationMatrix` (tham số `matrix1`, `matrix2`) | `addMatrix`, `subtractMatrix`, `multiplyMatrix` (tham số `matrix1Array`, `matrix2Array`); cùng `public`, cùng kiểu trả về/tham số; dòng ngay trên mỗi hàm có `// brief: public int[][] additionMatrix(int[][] matrix1, int[][] matrix2)`… | **đề đặt `additionMatrix`…, tờ checklist 1.4 bắt tên method mở đầu bằng động từ (và 1.5 bắt tên mảng kết thúc bằng `Array`) → hỏi thầy nếu thầy muốn giữ tên đề** (Refactor ▸ Rename, không đổi gì khác) |
| Repository | bản 14/09: **không có** (*"không lưu gì giữa hai lần tính"*) | `repository/MatrixRepository` giữ `firstMatrix`, `secondMatrix`; service lấy ma trận từ đây (bỏ `MatrixService.toMatrix`) | tờ checklist 1.1: *"Bắt buộc phải có repository"* |
| Kiểm kích thước | bản 14/09: `controller.checkMatrixSize(dto)` gọi trước (1 case gọi controller **2 lần**), luật trong `Matrix.hasSameSize/canMultiplyWith` + `checkSize` của 3 Operation | `Main.checkMatrixSize` → `Validation.checkSameSize` / `checkMultiplySize`; bỏ `checkSize` khỏi strategy, bỏ `getRows/getColumns/hasSameSize/canMultiplyWith` (không còn ai dùng) | tờ giấy 1.1 (validate ở Main) + Guide (*"mỗi workflow chỉ gọi vào controller 1 lần"*); đề không bắt kiểm qua controller. Màn hình **không đổi**: lỗi vẫn hiện ngay sau kích thước ma trận 2 rồi về menu |
| Interface | `MatrixOperation` | `IMatrixOperation` | tờ giấy 1.3 |
| View | `setResponse(...)`, field `response` | `setResponseDTO(...)`, field `responseDTO`, `display()` 1 lần | tờ giấy 1.1 |
| Tên mảng | `values`, `result`, `matrix` (tham số `MatrixDTO`) | `valueArray`, `resultArray`, `matrixDTO` | tờ giấy 1.5 |
| `Main` | `public class Main`; `choice`, `line`, `dto` khai giữa block | `public final class Main` + `private Main()`; biến khai đầu block, khởi tạo luôn | tờ giấy 3.4, 2.6, 3.7 |
| Dòng trống, ngoặc | field/hằng liền nhau; `choice < min \|\| choice > max` | 1 dòng trống trước mọi comment; ngoặc từng phép so sánh | tờ giấy 2.8, 3.3 |
