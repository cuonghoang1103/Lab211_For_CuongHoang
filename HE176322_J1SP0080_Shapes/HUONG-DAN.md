# J1.S.P0080 — Shapes (2D / 3D)

> Bài "cây kế thừa 3 tầng": `Shape` → `TwoDimensionalShape` / `ThreeDimensionalShape` → 6 hình.
> Thầy sẽ hỏi 3 thứ: **đa hình** (1 vòng lặp, 6 kiểu), **`instanceof` + ép kiểu**, và **vì sao
> `getVolume()` không nằm ở `Shape`**.

| | |
|---|---|
| Loại / LOC | Short Assignment · 90 LOC · 2 slot |
| Project | `HE176322_J1SP0080_Shapes` |
| Chạy | NetBeans: **File ▸ Open Project** → **F6** (không nhập gì) |
| Lớp chạy | `main.Main` |
| Kiểm tự động | `python3 _tools/verify.py J1SP0080` → 1 kịch bản × 2 locale |

---

## 1. Đề bài nói gì

- Dựng cây lớp như hình của đề: `Shape` (abstract `getArea()`, ghi đè `toString()`) → `TwoDimensionalShape`,
  `ThreeDimensionalShape` (thêm abstract `getVolume()`) → `Circle, Square, Triangle` / `Sphere, Cube, Tetrahedron`.
- Tạo **một mảng `Shape[]`** chứa 1 đối tượng của mỗi lớp cụ thể, **một vòng lặp**: in hình, dùng
  `instanceof` xét 2D hay 3D; 2D in diện tích, 3D in diện tích **và** thể tích; số thực **2 chữ số**.
- Không nhập gì.

Màn hình đề (chép đúng từng ký tự — dòng `BUILD SUCCESSFUL` là của NetBeans, không phải của chương trình):

```
=============================================================
No  Shape                          Area        Volume
=============================================================
1  Circle [r=2.00]                12.57          -
2  Square [side=3.00]              9.00          -
3  Triangle [base=4.00, h=5.00]   10.00          -
4  Sphere [r=2.00]                50.27        33.51
5  Cube [side=3.00]               54.00        27.00
6  Tetrahedron [side=4.00]        27.71         7.54
=============================================================
```

**Đề bắt buộc:**

| Thứ | Đề viết | Bài này đặt ở |
|---|---|---|
| `abstract Shape` + `abstract getArea()` + `toString()` | Function 1 | `model/Shape.java` (`toString()` khai lại `abstract` để **ép** mọi hình tự mô tả) |
| `TwoDimensionalShape`, `ThreeDimensionalShape` + `abstract getVolume()` | Function 1 | `model/` |
| 6 lớp cụ thể, mỗi lớp `toString()` riêng | *"Override toString() in each concrete class"* | `model/` |
| Một mảng `Shape[]`, một vòng lặp, `instanceof` | Function 3 | `service/ShapeService.getShapeReport()` |
| 2 chữ số thập phân | *"Format every real number to two decimal places"* | `%.2f` với `Locale.US` |

---

## 2. Kiến thức cần biết

### 2.1 Công thức — chạy tay đúng 6 dòng của đề

| Hình | Diện tích A | Thể tích V | Số | In |
|---|---|---|---|---|
| Circle r=2 | π·r² | – | 12.566… | `12.57` |
| Square s=3 | s² | – | 9 | `9.00` |
| Triangle b=4, h=5 | b·h/2 | – | 10 | `10.00` |
| Sphere r=2 | 4·π·r² | (4/3)·π·r³ | 50.265… · 33.510… | `50.27` · `33.51` |
| Cube s=3 | 6·s² | s³ | 54 · 27 | `54.00` · `27.00` |
| Tetrahedron s=4 | √3·s² | s³/(6·√2) | 27.712… · 7.542… | `27.71` · `7.54` |

⚠️ `4 / 3` trong Java là **chia nguyên = 1** → thể tích cầu thành 25.13 (sai). Phải `4.0 / 3.0`
(`Constants.SPHERE_VOLUME_FACTOR`).

### 2.2 `instanceof` và ép kiểu (run-time type identification)

```java
Shape shape = shapes[i];                      // biến kiểu CHA, đối tượng thật là Sphere
shape.getArea();                              // được: getArea có ở Shape → chạy bản của Sphere
if (shape instanceof ThreeDimensionalShape) { // hỏi: đối tượng thật có phải hình 3D không?
    ThreeDimensionalShape solid = (ThreeDimensionalShape) shape;   // ép xuống
    solid.getVolume();                        // chỉ gọi được SAU khi ép — Shape không có getVolume
}
```

Hỏi `instanceof ThreeDimensionalShape` (tầng **trừu tượng**) chứ không hỏi
`instanceof Sphere || instanceof Cube || …` → thêm hình 3D thứ tư **không phải sửa `if`**.

### 2.3 Vì sao số in cột lệch "lạ"

Bảng của đề được **đo từng ký tự** và chép vào `Constants`:

| Format | Nghĩa |
|---|---|
| `HEADER_FORMAT = "%-4s%-29s%6s%14s"` | `No` + 2 cách, `Shape` rộng 29, `Area` canh phải 6, `Volume` canh phải 14 |
| `ROW_3D_FORMAT = "%-3d%-30s%6s%13s"` | số thứ tự rộng 3, mô tả rộng 30, diện tích canh phải 6, thể tích canh phải 13 |
| `ROW_2D_FORMAT = "%-3d%-30s%6s%11s"` | như trên, nhưng dấu `-` canh phải **11** → nằm **giữa** cột số thể tích, đúng như đề |

`%-30s` = chuỗi canh trái, đệm cách cho đủ 30; `%6s` = canh phải trong 6 ô.

---

## 3. Thiết kế

```
HE176322_J1SP0080_Shapes/src/
├── constants/  Message.java           thước kẻ, nhãn cột, mẫu mô tả "Circle [r=%.2f]"
│               Constants.java         kích thước mẫu, hệ số công thức, format cột
│               ShapeType.java         enum 6 loại hình (khoá của factory, thứ tự của mảng)
├── model/      Shape (abstract)              getArea · toString
│               TwoDimensionalShape (abstract)
│               ThreeDimensionalShape (abstract)  + getVolume
│               Circle, Square, Triangle, Sphere, Cube, Tetrahedron
├── dto/        ShapeResponseDTO       1 dòng: no, description, area, volume, threeDimensional
├── service/    ShapeFactory           ShapeType ──► new đúng lớp con
│               ShapeService           tạo Shape[], vòng lặp + instanceof
├── controller/ ShapeController        service ──► view
├── view/       ShapeView              in bảng
└── main/       Main                   gọi controller 1 lần
```

| Câu hỏi thiết kế | Trả lời |
|---|---|
| Sao không có `RequestDTO`, `utils/Validation`, Scanner? | Đề: *"requires no input"*. Không có gì người dùng gõ → không có gì để gói hay kiểm. Thêm lớp rỗng là trừu tượng thừa (ghi chú slide 26 SOLID). |
| Sao `service` mà không `repository`? | 6 hình là mẫu cố định: không thêm/sửa/xoá → không CRUD. Việc tính diện tích/thể tích + làm báo cáo là *"tính toán nghiệp vụ"*. |
| `instanceof` đặt ở đâu? | `ShapeService` — nó quyết định dữ liệu (có volume hay không) và ghi vào `ShapeResponseDTO.threeDimensional`. View chỉ đọc cờ đó để chọn format. |
| `TwoDimensionalShape` rỗng có thừa không? | Không: nó là **kiểu** để hỏi "hình phẳng?" và đúng hình cây của đề. |

**Luồng:**

```
Main ──► controller.displayShapes()
   controller ──► service.getShapeReport()
                    ├─ createShapes(): for ShapeType t : values() → factory.createShape(t)  → Shape[6]
                    └─ for i: row.no, row.description = shape.toString(), row.area = shape.getArea()
                              if (shape instanceof ThreeDimensionalShape) row.volume = ((3D) shape).getVolume()
   controller ──► view.setShapes(rows) ──► view.display()
```

### 3.1 Design Pattern — **Factory** (Simple Factory)

| Yếu tố | Trong bài này |
|---|---|
| **Name** | Factory — dạng **Simple Factory** (một lớp có hàm tạo theo loại); GoF gọi biến thể dùng lớp con ghi đè hàm tạo là *Factory Method* |
| **Problem** | Cần tạo **đúng lớp con** cho từng loại hình. Nếu `new Circle(...)`, `new Cube(...)` rải trong service thì service phụ thuộc cả 6 lớp cụ thể, và thêm hình phải sửa service. |
| **Solution** | `ShapeType` (enum) = **khoá loại**. `ShapeFactory.createShape(ShapeType)` = **Creator**: `switch` → `new` đúng lớp, trả về kiểu trừu tượng `Shape` = **Product**; 6 lớp hình = **ConcreteProduct**. `ShapeService` = **Client**: chỉ lặp `ShapeType.values()` và gọi factory. |
| **Consequences** | ✅ Nơi **duy nhất** biết "loại X → lớp X"; service chỉ biết `Shape`. ✅ Thêm `Cylinder` = 1 hằng enum + 1 lớp + 1 `case`; service/controller/view **không đổi**. ❌ Thêm 2 file (enum + factory) so với khởi tạo mảng thẳng. |

Controller còn là **Facade**: `Main` chỉ biết `displayShapes()`.

### 3.2 SOLID trong bài

| Nguyên lý | Ở đâu |
|---|---|
| **S** | mỗi hình chỉ biết công thức của nó; factory chỉ tạo; service chỉ làm báo cáo; view chỉ in |
| **O** | thêm hình = thêm lớp (+1 case factory); vòng lặp trong service **không sửa** vì hỏi theo tầng trừu tượng |
| **L** | mọi hình đứng được ở chỗ `Shape` trong vòng lặp; không hình phẳng nào bị ép viết `getVolume` giả |
| **I** | `getVolume()` chỉ ở `ThreeDimensionalShape` — lớp không cần thì không bị ép (tinh thần Interface Segregation) |
| **D** | `ShapeService` làm việc với `Shape` (trừu tượng), không với `Circle`/`Cube` |

---

## 4. Code từng bước

> Thầy: *"code cái **model trước rồi đến data**"*.

| Bước | File | Việc |
|---|---|---|
| 1 | `model/Shape.java` | `abstract double getArea()` + `@Override abstract String toString()` |
| 2 | `model/TwoDimensionalShape.java`, `ThreeDimensionalShape.java` | `abstract class … extends Shape`; lớp 3D thêm `abstract double getVolume()` |
| 3 | `model/` 6 lớp cụ thể | field `private` + constructor rỗng + constructor đủ + get/set (**Alt+Insert**) + công thức + `toString` |
| 4 | `dto/ShapeResponseDTO.java` | JavaBean 5 field (`isThreeDimensional()` cho boolean) |
| 5 | `constants/ShapeType.java` | enum 6 hằng, **đúng thứ tự bảng** |
| 6 | `service/ShapeFactory.java` | `createShape(type)` — `switch` + `default` |
| 7 | `service/ShapeService.java` | `createShapes()` (private) + `getShapeReport()` |
| 8 | `view/ShapeView.java` | `setShapes` + `display` |
| 9 | `controller/ShapeController.java` | `displayShapes()` |
| 10 | `constants/Message.java`, `Constants.java` | chữ + số + format (gõ dần khi bước trên cần) |
| 11 | `main/Main.java` | `new ShapeController().displayShapes()` |

**Bẫy hay gặp:**

1. `4 / 3 * Math.PI * r³` → chia nguyên ra 1 → thể tích cầu sai.
2. `String.format("%.2f", x)` không có `Locale.US` → máy tiếng Việt in `12,57`.
3. Gọi `shape.getVolume()` khi `shape` khai kiểu `Shape` → lỗi biên dịch; phải `instanceof` rồi ép.
4. Đưa `getVolume()` lên `Shape` cho "tiện" → hình tròn phải trả 0 giả — sai thiết kế (L/I).

---

## 5. Test trước khi gọi thầy

Chương trình không nhập gì — chỉ có 1 kịch bản, chạy dưới 2 locale.

| # | Làm | Phải thấy |
|---|---|---|
| 1 | **F6** | đúng 10 dòng của đề (§1), từng dấu cách |
| 2 | máy để locale tiếng Việt (`verify.py` chạy `vi_VN`) | vẫn `12.57`, không `12,57` |
| 3 | soát 3 dòng 2D | cột Volume là `-` |
| 4 | soát 3 dòng 3D | có số thể tích `33.51`, `27.00`, `7.54` |

---

## 6. Debug

| Việc | Cách làm |
|---|---|
| Breakpoint | dòng `row.setArea(shape.getArea());` trong `ShapeService.getShapeReport` |
| Chạy | **Ctrl+F5** |
| Bước | **F7** vào `shape.getArea()`: vòng 1 nhảy vào `Circle.getArea`, vòng 4 vào `Sphere.getArea` — **cùng 1 dòng code, 6 hàm khác nhau** = đa hình |
| Quan sát | tab **Variables**: `shape` khai `Shape` nhưng hiện kiểu thật `Sphere`; **F8** qua `if (shape instanceof …)` thấy vòng 1–3 rẽ `else`, vòng 4–6 rẽ vào `if` |
| Factory | breakpoint ở `switch (type)` trong `ShapeFactory`, F8 xem `type` đi CIRCLE → TETRAHEDRON |

---

## 7. Câu hỏi thầy hay hỏi

### OOP

| Câu hỏi | Trả lời mẫu |
|---|---|
| 4 tính chất OOP ở đâu? | **Đóng gói**: `radius`, `side`, `base`… `private` + get/set. **Kế thừa**: 3 tầng `Circle → TwoDimensionalShape → Shape`. **Đa hình**: `shape.getArea()` / `shape.toString()` trong vòng lặp `ShapeService` chạy bản của lớp thật. **Trừu tượng**: 3 lớp `abstract`, `abstract getArea()`, `abstract getVolume()`. |
| Sao `getVolume()` không ở `Shape`? | Hình tròn **không có** thể tích. Đặt ở `Shape` thì 3 hình phẳng phải viết `getVolume()` trả số giả → vi phạm **L**/**I**. |
| Sao `toString()` khai lại `abstract` ở `Shape`? | `Object` đã có `toString()`; khai lại `abstract` **bắt** lớp con cụ thể nào quên viết thì lỗi biên dịch. |
| `instanceof` có trái OOP không? | Đề bắt dùng (*"run-time type identification"*). Em hỏi theo **tầng trừu tượng** `ThreeDimensionalShape`, không hỏi từng lớp — thêm hình không phải sửa `if`. Ngoài chỗ đó mọi thứ đều đi bằng đa hình. |
| Upcasting / downcasting? | `Shape s = new Sphere(2)` — **upcast**, tự động, luôn an toàn. `(ThreeDimensionalShape) s` — **downcast**, phải ép, sai kiểu thì `ClassCastException` → vì vậy phải `instanceof` trước. |
| Abstract class khác interface? | Abstract class là "loại" (is-a) có thể mang code chung; interface là "khả năng". Ở đây `TwoDimensionalShape` **là một loại** hình → abstract class. |

### Access modifier, static, kiểu trả về

| Thành phần | Vì sao |
|---|---|
| mọi field `private` | đóng gói |
| `getArea/getVolume/toString` `public` | service (lớp khác) gọi |
| get/set + constructor rỗng `public` ở 6 hình và DTO | JavaBean (MVC JSP) |
| `ShapeFactory.createShape` `public` | service gọi |
| `ShapeService.createShapes` `private` | chỉ `getShapeReport` dùng |
| **không có `static` nào** ngoài `Constants/Message` và `Main.main` | model/service/controller cấm static (Guide); không có utils vì không có nhập liệu |
| `ShapeType.values()` gọi qua tên kiểu | hàm static sẵn của mọi enum — gọi qua tên lớp đúng Code Conventions §10.2 |
| `getArea` trả `double` | có π và căn |
| `isThreeDimensional` trả `boolean` | câu hỏi có/không; JavaBean đặt tên `is…` cho boolean |
| `getShapeReport` trả `ArrayList<ShapeResponseDTO>` | view duyệt theo thứ tự 1..6 |

### ArrayList hay List? Mảng hay ArrayList?

| Câu hỏi | Trả lời mẫu |
|---|---|
| Sao khai `ArrayList` mà không `List`? | `List` là interface, `ArrayList` là lớp cài đặt. Em chỉ thêm cuối và duyệt theo thứ tự — đúng thứ `ArrayList` làm nhanh — nên khai thẳng kiểu em dùng. Khai `List` chỉ khác ở chỗ đổi cài đặt dễ hơn; chạy như nhau. |
| Sao mảng `Shape[]` cho hình, `ArrayList` cho dòng báo cáo? | Đề bắt *"a single Shape[] array"*; số hình biết trước (= số hằng enum). Danh sách dòng thì dựng dần. |

---

## 8. Thầy đổi yêu cầu tại chỗ

| Thầy bảo | Sửa file | Không đụng |
|---|---|---|
| Thêm **Cylinder** (3D) | `model/Cylinder` (mới), `ShapeType` (+1 hằng), `ShapeFactory` (+1 case), `Constants` (kích thước), `Message` (mẫu mô tả) | `ShapeService`, controller, view, main |
| Thêm **Rectangle** (2D) | như trên, lớp mới `extends TwoDimensionalShape` | như trên |
| In **3 chữ số** | `Constants.NUMBER_FORMAT` + các mẫu `%.2f` trong `Message` (cột có thể phải nới) | model, service |
| Cho người dùng **nhập** bán kính | thêm `ShapeRequestDTO`, `utils/Validation`, Scanner trong `Main`; `ShapeFactory.createShape` nhận thêm DTO | view |
| In thêm **chu vi** cho hình 2D | `TwoDimensionalShape` + `abstract getPerimeter()`, 3 lớp 2D, DTO + 1 field, view + format | lớp 3D |

---

## 9. Chỗ khác với đề / bản cũ

| Chỗ | Đề / bản cũ | Bài này | Lý do |
|---|---|---|---|
| Độ rộng cột | bản cũ `%-3s %-30s %10s %10s` (Area kết thúc cột 45) | đo từ mẫu của đề (Area kết thúc cột 39) | **màn hình đề** thắng → test `REPLACE_REFERENCE = True` |
| Header lệch 1 ô so với dòng số (`No  Shape` vs `1  Circle`), dấu `-` nằm giữa cột thể tích | có trong mẫu đề | giữ **y hệt** bằng 3 format riêng | đề là nguồn sự thật từng ký tự |
| `BUILD SUCCESSFUL (total time: 1 second)` | có trong mẫu đề | không in | đó là dòng NetBeans in sau khi chạy, không phải chương trình |
| Tạo mảng | đề: `Shape[] shapes = { new Circle(2), … }` | vẫn là `Shape[]`, nhưng từng phần tử tạo qua `ShapeFactory` theo `ShapeType` | Design Pattern (QUY-TAC-THAY §9 V7) — thêm hình không sửa service |
| In trong vòng lặp | đề: `System.out.println(s)` ngay trong vòng | service làm dữ liệu, **view** in | Guide: chỉ view/main được in |
| Không có `RequestDTO`, `utils/Validation` | khung chuẩn có | bỏ | đề không có nhập liệu (AGENT-BRIEF: bài không nhập được bỏ RequestDTO; Validation không có gì để kiểm) |
| Field | bản cũ `private final`, không setter | có setter + constructor rỗng | JavaBean — MVC JSP (V10) |
| Kiến trúc | `entity/ui`, `printf` trong `Main` | MVC theo Guide | luật thầy |
