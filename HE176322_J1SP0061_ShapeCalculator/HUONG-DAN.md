# J1.S.P0061 — Calculate perimeter and area (Shape)

> Bài kế thừa đầu tiên: `abstract Shape` → `Rectangle`, `Circle`, `Triangle`. Chỗ **vênh** duy nhất
> với đề: đề bắt `printResult()` nằm trong `Shape` (model **in ra màn hình**) — luật thầy cấm, nên
> `printResult()` nằm ở **view**. Học thuộc câu trả lời ở §7.
>
> Theo **tờ checklist giấy** (21/09/2026): có `repository/ShapeRepository` giữ 3 hình (tờ giấy 1.1
> *"Bắt buộc phải có repository"*), luồng **Controller → Service → Repository → Model**, view nhận
> kết quả qua **thuộc tính** `responseDTO` rồi `printResult()` **1 lần**.

| | |
|---|---|
| Loại / LOC | Short Assignment · 42 LOC · 1 slot |
| Project | `HE176322_J1SP0061_ShapeCalculator` |
| Chạy | NetBeans: **File ▸ Open Project** → **F6** |
| Lớp chạy | `main.Main` |
| Kiểm tự động | `python3 _tools/verify.py J1SP0061` → 4 kịch bản × 2 locale |

---

## 1. Đề bài nói gì

- Nhập: chiều rộng + chiều dài hình chữ nhật, bán kính hình tròn, 3 cạnh tam giác.
- Tính **chu vi** và **diện tích** của cả 3 hình, in ra, **kết thúc** chương trình (không menu).

Màn hình đề (chép đúng chữ):

```
=====Calculator Shape Program=====
Please input side width of Rectangle:
11
Please input length of Rectangle:
32
Please input radius of Circle:
12
Please input side A of Triangle:
5
Please input side B of Triangle:
5
Please input side C of Triangle:
5
-----Rectangle-----
Width: 11.0
Length: 32.0
Area: 352.0
Perimeter: 86.0
-----Circle-----
Radius: 12.0
Area:452.3893421169302
Perimeter:75.39822368615503
-----Triangle-----
Side A: 5.0
Side B: 5.0
Side C: 5.0
Area:10.825317547305483
Perimeter:15.0
```

⚠️ Để ý: **Rectangle** in `Area: ` / `Perimeter: ` **có** dấu cách; **Circle** và **Triangle** in
`Area:` / `Perimeter:` **không** dấu cách. Bài giữ đúng từng ký tự.

**Đề bắt buộc:**

| Thứ | Đề viết | Bài này đặt ở |
|---|---|---|
| `abstract class Shape` + 3 lớp con | *"Create an abstract class Shape … Create classes Triangle, Rectangle, Circle that extend from class Shape"* | `model/` |
| `public double getPerimeter()` | Function 1 | `abstract` trong `Shape`, `@Override` ở 3 lớp con |
| `public double getArea()` | Function 2 | như trên; tam giác dùng **Heron** + `Math.sqrt`, tròn dùng `Math.PI` |
| `public void printResult()` | Function 3 | **`view/ShapeView.printResult()`** — cùng tên, cùng chữ ký, khác lớp; đây chính là hàm render (vai `display()` của các bài khác): không tham số, in theo thuộc tính `responseDTO` (xem §9) |
| getter/setter cho `radius`, `width`, `length`, `sideA/B/C` | *"generate their getter and setter"* | 3 lớp con |

---

## 2. Kiến thức cần biết

### 2.1 Công thức — chạy tay ví dụ của đề

| Hình | Công thức | Ví dụ đề | Kết quả |
|---|---|---|---|
| Chữ nhật | S = w·l · P = 2(w + l) | w=11, l=32 | 352.0 · 86.0 |
| Tròn | S = π·r² · P = 2·π·r | r=12 | 452.3893421169302 · 75.39822368615503 |
| Tam giác | P = a+b+c · p = P/2 · S = √(p(p−a)(p−b)(p−c)) | 5,5,5 | p=7.5 → √(7.5·2.5·2.5·2.5)=√117.1875 = 10.825317547305483 · 15.0 |

### 2.2 Bất đẳng thức tam giác — vì sao phải kiểm

Heron chỉ đúng khi 3 cạnh tạo được tam giác: **mỗi tổng 2 cạnh > cạnh còn lại**.

| Cạnh | p | p(p−a)(p−b)(p−c) | Kết quả nếu không kiểm |
|---|---|---|---|
| 3, 4, 5 | 6 | 6·3·2·1 = 36 | 6.0 ✅ |
| 1, 2, 3 (1+2 = 3, "dẹt") | 3 | 3·2·1·0 = 0 | 0.0 — không phải tam giác |
| 1, 2, 10 | 6.5 | 6.5·5.5·4.5·(−3.5) < 0 | `NaN` ❌ |

→ `Validation.checkTriangle` từ chối cả hai trường hợp dưới, `Main` hỏi lại **cả 3 cạnh**.

### 2.3 Java dùng trong bài

| API | Dùng làm gì |
|---|---|
| `Math.PI`, `Math.sqrt` | đề bắt dùng |
| `"Area: " + 352.0` | nối chuỗi với `double` → Java in dạng đầy đủ `452.3893421169302` (đúng màn hình đề; `%.2f` sẽ làm tròn → **sai**) |
| `Double.parseDouble` | đổi chuỗi → số; nhận cả `"NaN"`, `"Infinity"` → phải chặn thêm bằng `Double.isNaN / isInfinite` |

---

## 3. Thiết kế

```
HE176322_J1SP0061_ShapeCalculator/src/
├── constants/  Message.java           prompt, lỗi, nhãn (có/không dấu cách đúng đề)
│               Constants.java         MIN_LENGTH = 0 · LINE/TWO_LINES/THREE_LINES/RESULT_FORMAT
├── model/      Shape (abstract)       getPerimeter · getArea · toString (template, final)
│               Rectangle, Circle, Triangle    công thức + các bước của template
├── dto/        ShapeRequestDTO        6 số                          (main ──► controller)
│               ShapeResponseDTO       resultList: 3 khối chữ        (controller ──► view)
├── repository/ ShapeRepository        ArrayList<Shape> shapeList · saveShapes · getShapeList
├── service/    ShapeService           lấy 3 hình từ repository, lặp tính kết quả
├── controller/ ShapeController        service ──► view (1 lần)
├── view/       ShapeView              responseDTO · setResponseDTO · printResult()   ← hàm đề bắt
├── utils/      Validation             getPositiveDouble · checkTriangle
└── main/       Main                   final + ctor private; Scanner, nhập + validate 6 số, gọi controller 1 lần
```

| Câu hỏi thiết kế | Trả lời |
|---|---|
| **Sao bài có `repository`?** | Tờ checklist 1.1: *"Bắt buộc phải có repository"* — kể cả bài tính toán. Repository giữ **dữ liệu** của chương trình: 3 hình dựng từ 6 số đã nhập (`ArrayList<Shape> shapeList`) + CRUD đơn giản `saveShapes` / `getShapeList`, **không** tính, **không** in. |
| Vì sao vẫn có `service`? | Guide: service = *"tính tổng, chu vi, diện tích"*. Tính là việc của service: lấy hình **từ repository**, gọi `toString()` (trong đó có `getArea/getPerimeter`). Tầng: Controller ↔ Service ↔ Repository ↔ Model. |
| View in được mà không biết `Shape`? | Model trả chữ qua `toString()` (Guide: *"Cần output gì thì thêm hàm toString()"*) → service gói vào `ShapeResponseDTO.resultList` → controller `setResponseDTO` → view `printResult()` in. |
| Sao `Shape` không có field? | Mỗi hình có số đo khác nhau; cái chung là **hành vi** (tính, mô tả) chứ không phải dữ liệu. |

**Luồng:**

```
Main: 6 lần nhập + validate (hỏi lại khi sai, tam giác sai hỏi lại cả 3) ──► ShapeRequestDTO ──► controller.calculate(requestDTO)
   controller ──► service.calculateShapes(requestDTO)
                    ├─ repository.saveShapes(requestDTO): new Rectangle, new Circle, new Triangle → shapeList
                    ├─ for (Shape shape : repository.getShapeList()) → resultList.add(shape.toString())   ← đa hình
                    └─ ShapeResponseDTO(resultList)
   controller ──► view.setResponseDTO(responseDTO) ──► view.printResult()   ← render 1 lần
```

### 3.1 Design Pattern — **Template Method**

| Yếu tố | Trong bài này |
|---|---|
| **Name** | Template Method (nhóm Behavioral) |
| **Problem** | 3 hình in cùng một **khung**: tiêu đề → các thuộc tính → Area → Perimeter. Viết lại khung ở 3 lớp con = lặp code 3 lần, và dễ có lớp in sai thứ tự. Riêng Rectangle lại có nhãn `Area: ` khác 2 hình kia. |
| **Solution** | `Shape` = **AbstractClass**: `toString()` là **template method** (`final` — lớp con không được đổi khung) gọi các bước: `getTitle()`, `getProperties()` (**abstract**, bắt buộc), `getAreaLabel()`, `getPerimeterLabel()` (**hook** có sẵn mặc định `Area:`), và `getArea()`, `getPerimeter()`. `Rectangle`, `Circle`, `Triangle` = **ConcreteClass** điền các bước; chỉ `Rectangle` ghi đè 2 hook. |
| **Consequences** | ✅ Khung viết **1 lần**; thêm hình `Square` = 1 lớp điền 4 hàm (**O**pen/Closed). ✅ Chỗ vênh `Area: ` gói gọn trong 1 hook. ❌ Người đọc phải nhìn cả lớp cha lẫn lớp con mới thấy đủ 1 khối chữ. |

**Thầy bảo "thêm hình vuông":** tạo `model/Square extends Shape`, viết `getArea`, `getPerimeter`,
`getTitle`, `getProperties`; thêm field `side` vào `ShapeRequestDTO`, 1 prompt ở `Main`, 1 dòng
`new Square(...)` + 1 dòng `shapeList.add(...)` trong `ShapeRepository.saveShapes`. `Shape`, service,
view, controller **không đổi**.

Controller còn đóng vai **Facade**: `Main` chỉ thấy `calculate(requestDTO)`.

### 3.2 SOLID trong bài

| Nguyên lý | Ở đâu |
|---|---|
| **S** | mỗi hình tự lo công thức của mình; `ShapeRepository` chỉ giữ dữ liệu; `ShapeService` chỉ tính; `ShapeView` chỉ in; `Validation` chỉ kiểm |
| **O** | thêm hình = thêm lớp con, không sửa `Shape` |
| **L** | `Rectangle/Circle/Triangle` thay được cho `Shape` trong vòng `for` — không lớp nào ném lỗi hay đổi nghĩa `getArea` |
| **I/D** | không có interface — không áp dụng |

---

## 4. Code từng bước

> Thầy: *"code cái **model trước rồi đến data**"*.

| Bước | File | Việc |
|---|---|---|
| 1 | `model/Shape.java` | 2 hàm `abstract` của đề + 2 bước `protected abstract` + 2 hook + `toString()` **final** (`String.format(Constants.RESULT_FORMAT, …)`) |
| 2 | `model/Rectangle.java`, `Circle.java`, `Triangle.java` | field `private` + constructor rỗng + constructor đủ + get/set (**Alt+Insert**) + công thức + các bước |
| 3 | `dto/ShapeRequestDTO.java`, `ShapeResponseDTO.java` | JavaBean; Response có `ArrayList<String> resultList` |
| 4 | `repository/ShapeRepository.java` | `ArrayList<Shape> shapeList` · `saveShapes(requestDTO)` · `getShapeList()` |
| 5 | `service/ShapeService.java` | field `shapeRepository` · `calculateShapes(requestDTO)` trả `ShapeResponseDTO` |
| 6 | `view/ShapeView.java` | field `responseDTO` · `setResponseDTO` · **`printResult()`** không tham số |
| 7 | `controller/ShapeController.java` | `calculate(requestDTO)`: `setResponseDTO` rồi `printResult()` **1 lần** |
| 8 | `constants/Message.java`, `Constants.java` | chép chữ từ đề, **để ý dấu cách sau dấu hai chấm**; 4 khuôn `%s` |
| 9 | `utils/Validation.java` | `getPositiveDouble` (tách lỗi "không phải số" / "≤ 0") · `checkTriangle` |
| 10 | `main/Main.java` | `final` + ctor `private` · `inputLength(sc, prompt)` · `inputTriangle(sc, requestDTO)` · gọi controller **1 lần** |

**Bẫy hay gặp:**

1. In số bằng `String.format("%.2f")` → `452.39` — **sai** màn hình đề. Dùng `%s` (`String.format("%s%s", "Area:", getArea())`): `%s` in `double` y như Java tự in (`452.3893421169302`), không làm tròn, và không phụ thuộc máy tiếng Việt.
2. Heron dùng `p = a + b + c` (quên chia 2) → diện tích sai hẳn.
3. Không kiểm bất đẳng thức → nhập `1 2 10` in ra `NaN`.
4. Viết `printResult()` có `System.out` trong `Shape` như đề → **vi phạm Guide**, thầy không review.

---

## 5. Test trước khi gọi thầy

| # | Gõ | Phải thấy |
|---|---|---|
| 1 | `11` `32` `12` `5` `5` `5` | **đúng màn hình đề** (§1) |
| 2 | width `abc`, *(trống)* | `You must input a number.` rồi hỏi lại |
| 3 | width `0`, `-3` | `Value must be greater than zero.` |
| 4 | width `NaN`, radius `Infinity` | `You must input a number.` |
| 5 | width `2.5`, length `4` | `Width: 2.5` · `Area: 10.0` · `Perimeter: 13.0` |
| 6 | radius `1` | `Area:3.141592653589793` · `Perimeter:6.283185307179586` |
| 7 | cạnh `1 2 3` (dẹt) | `These three sides cannot form a triangle. Please input again.` rồi hỏi lại **cả 3** |
| 8 | cạnh `1 2 10` | như #7, **không** có `NaN` |
| 9 | cạnh `3 4 5` | `Area:6.0` · `Perimeter:12.0` |
| 10 | cạnh B `x`, cạnh C `-5` | lỗi hiện **ngay ở cạnh đó**, không hỏi lại cạnh A |

---

## 6. Debug

| Việc | Cách làm |
|---|---|
| Breakpoint | dòng `resultList.add(shape.toString());` trong `ShapeService.calculateShapes` |
| Chạy | **Ctrl+F5**, nhập ví dụ của đề |
| Bước | **F7** vào `shape.toString()` — lần 1 nhảy vào `Shape.toString()`, rồi F7 vào `getTitle()` thấy nó nhảy sang **`Rectangle.getTitle()`**; vòng 2 cùng dòng đó nhảy sang `Circle` → **chỉ cho thầy thấy đa hình** |
| Quan sát | tab **Variables**: `shape` hiện kiểu thật (`Rectangle`, `Circle`…) dù khai là `Shape`; trong `Triangle.getArea` xem `halfPerimeter` = 7.5, `heronProduct` = 117.1875 |

---

## 7. Câu hỏi thầy hay hỏi

### Chỗ vênh với đề — học thuộc

| Câu hỏi | Trả lời mẫu |
|---|---|
| **Đề bắt `printResult()` trong `Shape`, sao em để ở view?** | Guide của thầy: model *"không được input từ scanner hoặc output (printf) ở đây"*, view: *"Không được gọi print ngoài view và main"*. Nên em giữ **đúng tên và chữ ký** `public void printResult()` nhưng đặt ở `ShapeView`. Phần của model là `toString()` trả **chữ** — Guide: *"Cần output gì thì thêm hàm toString()"*. |
| Vậy đa hình của `printResult` mất à? | Không: đa hình chuyển sang `toString()` và `getArea()/getPerimeter()` — `ShapeService` lặp `ArrayList<Shape>` lấy từ repository, mỗi phần tử chạy bản của lớp thật. |

### OOP

| Câu hỏi | Trả lời mẫu |
|---|---|
| 4 tính chất OOP ở đâu? | **Đóng gói**: `width`, `radius`, `sideA`… `private` + get/set. **Kế thừa**: `Rectangle/Circle/Triangle extends Shape`. **Đa hình**: vòng `for (Shape shape : shapeRepository.getShapeList())` trong `ShapeService` gọi `shape.toString()` → bản của lớp thật; cả `@Override getArea()`. **Trừu tượng**: `abstract class Shape` với `abstract getArea()` — nói *"hình nào cũng tính được diện tích"* mà không nói cách. |
| Sao `Shape` là `abstract`? | "Một hình" chung chung không có công thức; `abstract` cấm `new Shape()` và **ép** lớp con phải viết `getArea/getPerimeter` (quên là lỗi biên dịch). |
| Abstract class khác interface? | Abstract class chứa được **code chung** (template `toString()`, hook mặc định) — interface thì chỉ là hợp đồng. Ở đây có code chung → abstract class. |
| `toString()` sao `final`? | Nó là **khung** của Template Method; `final` để lớp con không phá thứ tự tiêu đề → thuộc tính → Area → Perimeter. |
| Override khác overload? | Override: lớp con viết lại hàm **cùng chữ ký** (`getArea`). Overload: **cùng tên khác tham số** trong 1 lớp — ví dụ 2 constructor `Circle()` và `Circle(double)`. |

### Access modifier, static, kiểu trả về

| Thành phần | Vì sao |
|---|---|
| mọi field `private` | đóng gói |
| `getArea/getPerimeter` `public` | `toString()` gọi, service chạy qua `toString()` (và đề ghi `public`) |
| `getTitle/getProperties/getAreaLabel/getPerimeterLabel` **`protected`** | chỉ template trong `Shape` và lớp con cần; lớp ngoài không có lý do gọi — đây là chỗ **đúng** để dùng `protected` |
| get/set của hình, constructor rỗng `public` | JavaBean (MVC JSP) — đề cũng bắt *"generate getter and setter"* |
| `Main.inputLength/inputTriangle` `private static` | chỉ `main` gọi; static vì `main` static và Guide cho phép static **hàm** ở main |
| `Validation.*` `public static` | Guide: utils *"phải dùng static method"*. **Bỏ static** → phải bỏ constructor `private` và `new Validation()` trong `Main` |
| `checkTriangle` 3 tham số | thầy cấm hàm 3 tham số, trừ `utils` kiểu `getChoice(input, min, max)` của Guide — đây là hàm utils |
| `getArea` trả `double` | có `π` và căn → số thực |
| `printResult` / `checkTriangle` trả `void` | in xong / kiểm xong là hết việc; lỗi đi bằng `throw` |
| `calculateShapes` trả `ShapeResponseDTO` | **một** gói cho view; bên trong `resultList` giữ đúng thứ tự Rectangle → Circle → Triangle |
| `saveShapes` trả `void`, `getShapeList` trả `ArrayList<Shape>` | CRUD đơn giản: lưu / đọc; chỉ service (được phép làm với model) gọi |

### ArrayList hay List?

| Câu hỏi | Trả lời mẫu |
|---|---|
| Sao `ArrayList<Shape>` / `ArrayList<String>` mà không `List`? | `List` là interface, `ArrayList` là lớp cài đặt bằng mảng động — em chọn nó vì chỉ cần thêm cuối + duyệt theo thứ tự. Khai `List` thì sau đổi cài đặt dễ hơn; khai `ArrayList` thì nói rõ em dùng cái gì. Chạy như nhau. Tên biến kết thúc bằng `List` (tờ checklist 1.5): `shapeList`, `resultList`. |
| Sao `shapeList` khai kiểu `Shape` mà chứa `Rectangle`? | **Biến kiểu cha chứa đối tượng lớp con** — cả 3 hình vào chung một danh sách, và một vòng `for` gọi đúng bản `toString()` của từng hình. |

### Kiến trúc theo tờ checklist

| Câu hỏi | Trả lời mẫu |
|---|---|
| **View nhận dữ liệu thế nào?** | Qua **thuộc tính**: `ShapeView` có field `private ShapeResponseDTO responseDTO` + `setResponseDTO(...)`; `printResult()` **không tham số**, in theo cái controller đã set. Controller gọi `setResponseDTO` rồi `printResult()` **đúng 1 lần** cho cả luồng. |
| Sao view không tên `display()` như các bài khác? | Đề **bắt** tên `public void printResult()` (Function 3). Nó giữ đúng vai `display()`: không tham số, render 1 lần. |
| **Validate ở đâu?** | Ở `Main` qua `utils/Validation`: `getPositiveDouble` (số thật, > 0) cho từng ô, `checkTriangle` cho 3 cạnh — sai thì hỏi lại **ngay tại ô đó**. Controller nhận `ShapeRequestDTO` đã sạch. |
| Controller có đụng `Shape` không? | Không — controller chỉ import DTO, service, view. `Rectangle/Circle/Triangle` chỉ được tạo trong `ShapeRepository` và đọc trong `ShapeService`. |
| Main gọi controller mấy lần? | **1 lần** (`controller.calculate(requestDTO)`) — bài không có menu, cả chương trình là 1 luồng. |

### Tờ checklist 25 mục — bài này đạt thế nào

| Mục | Chỉ vào đâu |
|---|---|
| **1.1** MVC + repository | `repository/ShapeRepository` (bắt buộc có); `ShapeController` không import `model`; `ShapeView` nhận `responseDTO` qua setter, `printResult()` không tham số, gọi **1 lần**; `Main` gọi controller **1 lần** |
| **1.5** tên collection / mảng | `shapeList` (repository), `resultList` (`ShapeResponseDTO`, `ShapeService`) — bản trước là `shapes`, `results`, `Shape[] shapes` |
| **2.6 / 3.7** khai báo đầu block + khởi tạo | `Main.main`: `sc`, `controller`, `requestDTO` ở đầu; `Main.inputLength`: `String line = ""`, trong vòng lặp chỉ gán; `Main.inputTriangle`: `sideA/B/C = 0`; `Validation.getPositiveDouble`: `double value = 0`; `Triangle.getArea`: `halfPerimeter`, `heronProduct` |
| **2.8** dòng trống | giữa các field (mọi lớp), sau vùng khai báo biến, trước mọi comment đứng sau dòng code, sau `}` trước câu lệnh tiếp |
| **3.3** ngoặc | `Validation.checkTriangle`: `if (((sideA + sideB) <= sideC) \|\| … \|\|` xuống dòng **sau** `\|\|` |
| **3.4** lớp chỉ có static | `Main` (`final` + `private Main()`), `Validation`, `Constants`, `Message` |
| **3.8** cộng chuỗi | `Shape.toString()`, `getProperties()` dùng `String.format(Constants.RESULT_FORMAT / LINE_FORMAT / …)` — bản trước nối bằng `+` |

---

## 8. Thầy đổi yêu cầu tại chỗ

| Thầy bảo | Sửa file | Không đụng |
|---|---|---|
| Thêm **hình vuông** | `model/Square` (mới), `ShapeRequestDTO`, `Message` (prompt/nhãn), `Main` (1 lần nhập), `ShapeRepository.saveShapes` (`new Square(...)` + `add`) | `Shape`, service, view, controller |
| In **2 chữ số thập phân** | `Constants.RESULT_FORMAT`: 2 chỗ `%s` của số → `%.2f`; `Shape.toString()`: `String.format(Locale.US, Constants.RESULT_FORMAT, …)` (`Locale.US` để máy tiếng Việt không in `3,14`) | lớp con, view |
| Rectangle in `Area:` không dấu cách | xoá 2 hook override trong `Rectangle` (hoặc sửa `Message`) | mọi file khác |
| Tam giác cho phép cạnh "dẹt" 1-2-3 | `Validation.checkTriangle`: `<=` → `<` | mọi file khác |
| Chạy lặp nhiều lần (menu) | chỉ `Main` (vòng `while` + hỏi tiếp) | model, service, view |

---

## 9. Chỗ khác với đề / bản cũ

| Chỗ | Đề / bản cũ | Bài này | Lý do |
|---|---|---|---|
| `printResult()` | Đề: trong `abstract Shape`, lớp con in ra | `view/ShapeView.printResult()` — **cùng tên, cùng `public void`, không tham số** | Guide cấm in trong model (QUY-TAC-THAY §8.3) |
| Nội dung in | bản cũ: `System.out` trong 3 lớp con | lớp con trả chữ qua `toString()` (Template Method) | Guide: *"Cần output gì thì thêm hàm toString()"* |
| `Triangle.isValid` | bản cũ: `static` trong model | `Validation.checkTriangle` | model **không được static**; kiểm dữ liệu nhập là việc của utils |
| `NaN`, `Infinity` | bản cũ nhận là số hợp lệ | `You must input a number.` | `parseDouble` nhận chúng, nhưng chúng không phải độ dài |
| Câu lỗi | đề không cho chữ | giữ chữ bản cũ: `You must input a number.` · `Value must be greater than zero.` · `These three sides cannot form a triangle. Please input again.` | bản cũ đã được kiểm với đề; đề im lặng |
| Màn hình | — | **giống hệt** đề và bản cũ | test giữ nguyên kịch bản bản cũ + thêm 2 kịch bản |
| Kiến trúc | `entity/ui/utils`, Scanner trong `Validator` | MVC theo Guide | luật thầy |
| Repository | bản 14/09: **không có** (*"không lưu gì, không CRUD"*) | `repository/ShapeRepository` giữ `ArrayList<Shape> shapeList` (`saveShapes`, `getShapeList`); service lấy hình từ đây | tờ checklist 1.1: *"Bắt buộc phải có repository"* |
| Dựng 3 hình | bản 14/09: trong `ShapeService` (`Shape[] shapes`) | trong `ShapeRepository.saveShapes` | repository = dữ liệu + CRUD; service chỉ tính |
| View | bản 14/09: `setShapes(ArrayList<ShapeResponseDTO>)`, mỗi DTO 1 khối | `setResponseDTO(ShapeResponseDTO)`, 1 DTO có `resultList` | tờ giấy 1.1: view nhận qua **thuộc tính (ResponseDTO)** |
| Nối chuỗi trong model | bản 14/09: `getTitle() + NEW_LINE + …` | `String.format(Constants.RESULT_FORMAT, …)`; màn hình **không đổi** (`%s` in `double` như `+`) | tờ giấy 3.8 |
| Tên | `shapes`, `results`, `p`, `dto` | `shapeList`, `resultList`, `halfPerimeter`, `requestDTO` | tờ giấy 1.5 (collection → `List`, tên có nghĩa) |
| `Main` | `public class Main` | `public final class Main` + `private Main()` | tờ giấy 3.4 |
