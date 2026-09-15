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
| `public int[][] additionMatrix(int[][] matrix1, int[][] matrix2)` | Function 1 | `service/MatrixCalculator` — **đúng chữ ký** |
| `public int[][] subtractionMatrix(...)` | Function 2 | `service/MatrixCalculator` |
| `public int[][] multiplicationMatrix(...)` | Function 3 | `service/MatrixCalculator` |
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
thước ma trận 2**, trước khi bắt gõ giá trị (không bắt người dùng gõ cả ma trận rồi mới báo).

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
├── model/      Matrix                ma trận (JavaBean) + getRows/getColumns/hasSameSize/canMultiplyWith/toString
├── dto/        MatrixDTO             MỘT ma trận người dùng gõ: số thứ tự (1/2) + giá trị
│               MatrixRequestDTO      phép tính + 2 MatrixDTO   (main ──► controller)
│               MatrixResponseDTO     4 chuỗi để in             (controller ──► view)
├── service/    MatrixCalculator      3 hàm của đề (cộng/trừ/nhân)
│               MatrixOperation       «interface» Strategy: checkSize · calculate · getSymbol
│               AdditionOperation · SubtractionOperation · MultiplicationOperation
│               OperationFactory      lựa chọn menu ──► đúng Operation
│               MatrixService         Context: tạo Matrix, gọi strategy, đóng gói kết quả
├── controller/ MatrixController      checkMatrixSize (kiểm trước) · calculateMatrix
├── view/       MatrixView            in khối Result
├── constants/  Message, Constants
├── utils/      Validation            getChoice · getSize · getValue
└── main/       Main                  menu + Scanner
```

| Lớp | Vì sao ở đây |
|---|---|
| `MatrixCalculator` | tính toán = nghiệp vụ → `service` (Guide). Không có `repository` vì không lưu gì giữa hai lần tính |
| `Matrix.hasSameSize` | là **câu hỏi về hình dạng của chính ma trận** → hành vi của model |
| `MatrixDTO` | để hàm nhập của `Main` chỉ có **2 tham số** `(sc, matrix)` — thầy cấm 3 tham số |

**Luồng phép cộng:**

```
Main: createRequest(1) in tiêu đề ─► inputSize/inputValues ma trận 1 ─► inputSize ma trận 2
   ─► controller.checkMatrixSize(dto)   (kiểm trước — sai thì throw, về menu)
   ─► inputValues ma trận 2 ─► controller.calculateMatrix(dto)
        service: factory.createOperation(1) → AdditionOperation
                 checkSize → calculate (= calculator.additionMatrix) → MatrixResponseDTO
        view.setResponse → display
```

### 3.1 Design Pattern

| Pattern | Name · Problem · Solution · Consequences |
|---|---|
| **Strategy** | **Problem**: 3 phép tính khác nhau ở 3 chỗ (luật kích thước, cách tính, dấu in ra); viết `if (choice == 1) ... else if ...` rải khắp service. **Solution**: `MatrixOperation` = Strategy; `AdditionOperation`/`SubtractionOperation`/`MultiplicationOperation` = ConcreteStrategy; `MatrixService` = Context (chỉ gọi `operation.checkSize/calculate/getSymbol`). **Consequences**: ✅ thêm phép mới = thêm 1 lớp, `MatrixService`/controller/view không sửa (**O**CP); ❌ nhiều file hơn. |
| **Factory Method** | **Problem**: ai biết "lựa chọn 2 → `SubtractionOperation`"? **Solution**: `OperationFactory.createOperation(int)` là **nơi duy nhất** có `new XxxOperation`. **Consequences**: ✅ thêm phép chỉ thêm 1 `case`; ❌ factory phải sửa khi thêm loại. |
| **Facade** | `MatrixController`: `Main` chỉ thấy 2 hàm, không biết service/factory/strategy. |
| **MVC** (thầy: "MVC JSP") | Controller ~ Servlet, View ~ JSP, `Matrix` + DTO là JavaBean. |

**Thầy bảo "thêm phép chuyển vị / nhân từng ô"**: tạo `ElementProductOperation implements MatrixOperation`
(checkSize giống phép cộng), thêm 1 `case` trong `OperationFactory`, thêm dòng menu + hằng số menu.
`MatrixService`, `MatrixController`, `MatrixView` **giữ nguyên**.

---

## 4. Code từng bước

> Thầy: *"code cái **model trước rồi đến data**"*.

| Bước | File | Việc |
|---|---|---|
| 1 | `model/Matrix.java` | field `int[][] values`, 2 constructor, get/set, `getRows/getColumns`, `hasSameSize`, `canMultiplyWith`, `toString` |
| 2 | `dto/MatrixDTO`, `MatrixRequestDTO`, `MatrixResponseDTO` | JavaBean: constructor rỗng + get/set |
| 3 | `service/MatrixCalculator.java` | **3 hàm của đề** |
| 4 | `service/MatrixOperation.java` + 3 lớp Operation | Strategy |
| 5 | `service/OperationFactory.java`, `MatrixService.java` | Factory + Context |
| 6 | `view/MatrixView.java` | `setResponse`, `display` |
| 7 | `controller/MatrixController.java` | `checkMatrixSize`, `calculateMatrix` |
| 8 | `constants/Message.java`, `Constants.java` | chữ trên màn hình / số menu, `MAX_SIZE = 20`, `CELL_FORMAT` |
| 9 | `utils/Validation.java` | `getChoice`, `getSize`, `getValue` (1 hàm `parseNumber` chung) |
| 10 | `main/Main.java` | menu, `createRequest`, `inputSize`, `inputValues`, `inputValue` |

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
| Breakpoint | dòng `sum += matrix1[row][k] * matrix2[k][column];` trong `MatrixCalculator.multiplicationMatrix` |
| Chạy | **Ctrl+F5**, chọn `3`, nhập (2×2)·(2×2) nhỏ |
| Quan sát | tab **Variables**: `row`, `column`, `k`, `sum`; mở `result` |
| Bước | **F8** qua từng `k`, xem `sum` tăng; ở `MatrixService.calculateMatrix` bấm **F7** vào `operation.calculate(...)` — thấy nhảy vào `MultiplicationOperation` (đa hình qua interface) |
| Chứng minh kiểm trước | breakpoint ở `controller.checkMatrixSize(dto);` trong `Main`, nhập cộng 2×2 với 2×3 → F7 đến `AdditionOperation.checkSize` → `throw` → rơi vào `catch` của `Main` |

---

## 7. Câu hỏi thầy hay hỏi

### OOP

| Câu hỏi | Trả lời mẫu |
|---|---|
| 4 tính chất OOP ở đâu? | **Đóng gói**: `values` là `private` trong `Matrix`, chỉ đọc qua getter. **Kế thừa**: 3 lớp `implements MatrixOperation`; mọi lớp `extends Object`, em ghi đè `toString()`. **Đa hình**: `MatrixService` gọi `operation.calculate(...)` — biến kiểu interface, chạy bản của lớp cụ thể. **Trừu tượng**: `MatrixOperation` chỉ nói "kiểm được kích thước, tính được", không nói cách làm. |
| Sao `hasSameSize` nằm trong model? | Nó hỏi về **hình dạng của chính ma trận** — đúng là hành vi của đối tượng. Còn **cách cộng/nhân** là nghiệp vụ → service. |

### Access modifier, static, kiểu trả về

| Câu hỏi | Trả lời mẫu |
|---|---|
| 3 hàm của đề sao `public`? | Đề ghi `public`, và **lớp khác gọi** (3 lớp Operation). |
| `toMatrix` sao `private`? | Chỉ `MatrixService` dùng. |
| `checkMatrixSize`, `calculateMatrix` sao `public`? | `Main` gọi controller; controller gọi service — gọi từ lớp khác. |
| Field `calculator` trong mỗi Operation sao `private`? | Chỉ lớp đó dùng; nhận qua constructor. |
| `static` ở đâu? | Chỉ `Validation` (Guide: *"phải dùng static method"*), hằng trong `constants`, hàm trong `Main` (Guide: *"cấm static với biến, có thể dùng với hàm"*). |
| **Bỏ `static` ở `Validation.getValue`?** | Lỗi biên dịch ở `Validation.getValue(...)`. Muốn chạy: bỏ constructor `private`, tạo `Validation v = new Validation();` trong `Main`, gọi `v.getValue(...)`. |
| `additionMatrix` trả `int[][]` sao không `void`? | Đề ghi thế, và trả **mảng mới** giữ nguyên 2 ma trận vào — màn hình còn phải in lại chúng. |
| `checkSize` trả `void`? | Kết quả đúng thì không có gì để trả; sai thì `throw` kèm thông báo. |
| `hasSameSize` trả `boolean`? | Nơi gọi chỉ cần có/không. |
| Sao không có `List`/`ArrayList`? | Ma trận có kích thước cố định sau khi nhập → `int[][]` là đúng kiểu; đề cũng dùng `int[][]`. (`List` là interface, `ArrayList` là lớp cài đặt bằng mảng động — dùng khi số phần tử thay đổi.) |

### SOLID & thiết kế

| Câu hỏi | Trả lời mẫu |
|---|---|
| SOLID ở đâu? | **S**: `Matrix` giữ số, `MatrixCalculator` tính, `OperationFactory` tạo, `MatrixView` in. **O**: thêm phép = thêm lớp (3.1). **L**: 3 Operation thay nhau được ở chỗ `MatrixOperation`. **I**: interface chỉ 3 hàm, lớp nào cũng dùng đủ. **D**: `MatrixService` phụ thuộc `MatrixOperation` (trừu tượng). |
| Sao `Main` gọi controller 2 lần mỗi phép? | `checkMatrixSize` là **bước kiểm tra trước** (giống `checkExistDoctor` mẫu Guide) để không bắt gõ cả ma trận 2 rồi mới báo sai. Việc tính chỉ gọi `calculateMatrix` **1 lần**; service **kiểm lại** trong `calculateMatrix`. |
| Sao có `MatrixDTO`? | Thầy cấm hàm 3 tham số: `inputValues(sc, số thứ tự, mảng)` → gói "số thứ tự + mảng" vào `MatrixDTO`. |
| Tràn số `int`? | Giá trị lớn nhân nhau có thể vượt `int` — đề dùng `int[][]` nên giữ; muốn an toàn đổi sang `long[][]`. |

---

## 8. Thầy đổi yêu cầu tại chỗ

| Thầy bảo | Sửa | Không đụng |
|---|---|---|
| Thêm phép mới (nhân từng ô, chia…) | lớp `XxxOperation`, 1 `case` ở `OperationFactory`, `Message.MENU` + tiêu đề, `Constants` số menu, `case`/tiêu đề trong `Main.createRequest` | `MatrixService`, `MatrixController`, `MatrixView` |
| Cho nhập số thực | `int[][]` → `double[][]` ở model/DTO/calculator, `Validation.getValue` parse `double`, `CELL_FORMAT` + `Locale.US` | luồng controller |
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
