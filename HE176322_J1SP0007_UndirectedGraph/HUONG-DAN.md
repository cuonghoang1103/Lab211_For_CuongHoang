# J1.S.P0007 — Undirected Graph (ma trận kề)

> Bài thuật toán nhỏ nhưng **vẫn phải MVC** (thầy: *"các bài liên quan thuật toán … cũng phải làm
> MVC"*). Hai chỗ thầy hay bắt: **ghi CẢ HAI ô** `(i, j)` và `(j, i)` (đồ thị vô hướng), và **nhãn
> đỉnh từ 1 nhưng chỉ số mảng từ 0**.

| | |
|---|---|
| Loại / LOC | Short Assignment · 70 LOC · 1 slot |
| Project | `HE176322_J1SP0007_UndirectedGraph` |
| Chạy | NetBeans: **File ▸ Open Project** → **F6** |
| Lớp chạy | `main.Main` |
| Kiểm tự động | `python3 _tools/verify.py J1SP0007` → 27 kịch bản × 2 locale (cả 25 cặp đỉnh) |

---

## 1. Đề bài nói gì

- Dựng lớp **`Graph`** biểu diễn đồ thị bằng **ma trận kề** — đúng đồ thị trong hình của đề.
- Nhập **2 điểm**, trả lời hai điểm đó **có phải một cạnh** không.

Đồ thị trong hình của đề (5 đỉnh, 5 cạnh): **1-4 · 2-4 · 2-5 · 3-5 · 4-5**.

Màn hình đề (hình 1, chép đúng chữ — có **2 dấu cách** giữa `is` và `an`):

```
Enter the start point:
2
Enter the end point:
5
This is  an edge
```

**Đề bắt buộc:**

| Thứ | Đề viết | Bài này đặt ở |
|---|---|---|
| Lớp `Graph` dùng **ma trận kề** | *"Construct a class Graph using adjacency matrix"* | `model/Graph.java` — field `int[][] matrixArray` |
| Đồ thị vô hướng → ma trận **đối xứng** | *"if there is an edge (2, 5) then there is also an edge (5, 2)"* | `Graph.addEdge` ghi **2 ô** |
| Câu trả lời | `This is  an edge` | `constants/Message.IS_EDGE` |

---

## 2. Kiến thức cần biết

### 2.1 Ma trận kề — chạy tay đồ thị của đề

Ô `a[i][j] = 1` nếu có cạnh giữa đỉnh `i` và `j`, ngược lại `0`:

| | 1 | 2 | 3 | 4 | 5 |
|---|---|---|---|---|---|
| **1** | 0 | 0 | 0 | **1** | 0 |
| **2** | 0 | 0 | 0 | **1** | **1** |
| **3** | 0 | 0 | 0 | 0 | **1** |
| **4** | **1** | **1** | 0 | 0 | **1** |
| **5** | 0 | **1** | **1** | **1** | 0 |

| Thêm cạnh | Ô được ghi (nhãn) | Ô trong mảng Java (chỉ số) |
|---|---|---|
| 1-4 | (1,4) và (4,1) | `[0][3]` và `[3][0]` |
| 2-5 | (2,5) và (5,2) | `[1][4]` và `[4][1]` |
| … | … | … |

Hỏi `(2, 5)` → đọc `matrixArray[1][4]` = 1 → *"This is  an edge"*. Hỏi `(1, 3)` → `matrixArray[0][2]` = 0 → *"This is not an edge"*.

### 2.2 Ma trận kề hay danh sách kề? (đề nhắc *"advantages and disadvantages"*)

| | Ma trận kề (bài này) | Danh sách kề |
|---|---|---|
| Bộ nhớ | `V × V` ô (25 ô ở đây) | `V + 2E` |
| Hỏi "(u, v) có cạnh?" | **1 lần đọc mảng — O(1)** | duyệt danh sách của `u` — O(bậc u) |
| Liệt kê hàng xóm của `u` | duyệt cả hàng — O(V) | O(bậc u) |
| Hợp với | đồ thị **dày**, hỏi cạnh nhiều | đồ thị **thưa** (bản đồ đường) |

Bài này chỉ hỏi đúng câu "có cạnh không" → ma trận là lựa chọn đúng.

### 2.3 Java dùng trong bài

| API | Dùng làm gì |
|---|---|
| `new int[n][n]` | mảng 2 chiều, Java **tự điền 0** → đồ thị rỗng không cần vòng khởi tạo |
| `for (int[] edgeArray : Constants.EDGES)` | duyệt từng cạnh `{a, b}` của hình (biến mảng → đuôi `Array`, tờ checklist 1.5) |
| `Integer.parseInt(s)` | đổi chuỗi → số; sai → `NumberFormatException` |
| `StringBuilder` | ghép ma trận thành chữ trong `toString()` |

---

## 3. Thiết kế

```
HE176322_J1SP0007_UndirectedGraph/src/
├── model/      IGraphRepresentation  «interface» addEdge / isEdge  (Strategy)
│               Graph                 ma trận kề int[][] matrixArray (lớp đề bắt) — JavaBean
├── repository/ GraphRepository       GIỮ đồ thị: addEdge (thêm) / isEdge (đọc) — Context
├── dto/        GraphRequestDTO       start, end  (main ──► controller)
│               GraphResponseDTO      message     (controller ──► view)
├── service/    GraphService          dựng đồ thị của đề vào repository + checkEdge
├── controller/ GraphController       service ──► view (Facade)
├── view/       GraphView             field responseDTO + setResponseDTO + display()
├── constants/  Message.java          câu chữ
│               Constants.java        VERTICES = 5, EDGES của hình, EDGE = 1
├── utils/      Validation            getInt(chuỗi, min, max)
└── main/       Main                  final + private Main(); Scanner, nhập 2 điểm, gọi controller 1 lần
```

| Câu hỏi thiết kế | Trả lời |
|---|---|
| Sao bài có `repository`? | Tờ checklist 1.1: *"**Bắt buộc phải có repository**"* — kể cả bài thuật toán. Dữ liệu của bài này là **đồ thị** (ma trận kề) → `GraphRepository` giữ nó, chỉ có 2 hàm CRUD đơn giản: `addEdge` (thêm cạnh) và `isEdge` (đọc một ô). Không tính toán, không in. |
| Repository và service chia việc thế nào? | `GraphRepository` = **giữ** dữ liệu. `GraphService` = **nghiệp vụ**: dựng đúng đồ thị của hình (vòng `for` qua `Constants.EDGES`) và biến câu trả lời có/không thành câu chữ của đề. |
| Sao danh sách cạnh ở `Constants`? | Hình của đề **cố định** — đó là hằng số. Đổi đồ thị = sửa 1 mảng. |
| Sao controller không đụng `Graph`? | Tờ checklist: controller *"không làm việc với Model"*; Guide: controller *"chỉ import DTO, View, Service"*. |
| View nhận dữ liệu thế nào? | Qua **thuộc tính**: `private GraphResponseDTO responseDTO` + `setResponseDTO(...)`, rồi `display()` **không tham số** in `responseDTO.getMessage()`. Controller gọi `display()` đúng **1 lần** cho luồng duy nhất. |

**Luồng chạy:**

```
Main: inputPoint(start) ─┐ (hỏi lại khi sai — nhập + validate đều ở Main)
      inputPoint(end)   ─┴► GraphRequestDTO ──► controller.checkEdge(requestDTO)   ← gọi 1 lần
   controller ──► service.checkEdge(requestDTO) ──► graphRepository.isEdge(start, end)
                                                   ──► graph.isEdge(...)   ← 1 lần đọc mảng
   service điền GraphResponseDTO.message ("This is  an edge" / "This is not an edge")
   controller ──► view.setResponseDTO(responseDTO) ──► view.display()      ← render 1 lần
```

### 3.1 Design Pattern — **Strategy** (cách lưu đồ thị)

| Yếu tố | Trong bài này |
|---|---|
| **Name** | Strategy (nhóm Behavioral) |
| **Problem** | Đề tự nêu *"ưu nhược điểm của cách biểu diễn này"* — cách kia là **danh sách kề**. Nếu kho dữ liệu dính chặt vào `int[][]`, thầy bảo *"đổi sang danh sách kề"* là phải sửa cả repository lẫn service. |
| **Solution** | `IGraphRepresentation` = **Strategy** (`addEdge`, `isEdge`). `Graph` = **ConcreteStrategy** (ma trận kề). `GraphRepository` = **Context**: field kiểu `IGraphRepresentation`, chỉ **một dòng** `new Graph(Constants.VERTICES)` biết lớp cụ thể. |
| **Consequences** | ✅ Đổi cách lưu = **thêm 1 class** `AdjacencyListGraph implements IGraphRepresentation` + sửa 1 dòng `new` trong `GraphRepository` (**O**); service, controller, view không đổi. ❌ Thêm 1 file interface. Interface nằm ở `model` vì `Graph` (model) implement nó, mà model không được import tầng khác. Tên bắt đầu bằng **`I`** vì tờ checklist 1.3: *"Tên của interface bắt đầu bằng I"*. |

Controller còn là **Facade**: `Main` chỉ biết `checkEdge(requestDTO)`.

### 3.2 SOLID trong bài

| Nguyên lý | Ở đâu |
|---|---|
| **S** | `Graph` giữ ma trận · `GraphRepository` giữ đồ thị · `GraphService` dựng + trả lời · `GraphView` in · `Validation` kiểm · `Main` nhập |
| **O** | thêm cách lưu mới không sửa `isEdge`/`checkEdge` (3.1) |
| **L** | mọi lớp implement `IGraphRepresentation` thay được `Graph` mà `GraphRepository` vẫn đúng |
| **I** | interface chỉ 2 hàm chương trình thật sự dùng |
| **D** | `GraphRepository` giữ field kiểu **interface** `IGraphRepresentation` |

---

## 4. Code từng bước

> Thầy: *"code cái **model trước rồi đến data**"*.

| Bước | File | Việc |
|---|---|---|
| 1 | `model/IGraphRepresentation.java` | interface 2 hàm `addEdge`, `isEdge` |
| 2 | `model/Graph.java` | `private int[][] matrixArray` + constructor rỗng + `Graph(int)` + get/set + `getVertices` + `addEdge` (2 ô, qua `row`/`column`) + `isEdge` + `toString` |
| 3 | `dto/GraphRequestDTO`, `GraphResponseDTO` | JavaBean: constructor rỗng + get/set (`start`, `end` / `message`) |
| 4 | `constants/Constants.java` | `VERTICES`, `FIRST_VERTEX`, `EDGE`, `NO_EDGE`, `EDGES` |
| 5 | `repository/GraphRepository.java` | field `IGraphRepresentation graph` + constructor `new Graph(VERTICES)` + `addEdge` + `isEdge` |
| 6 | `service/GraphService.java` | constructor dựng đồ thị vào repository (vòng `for` qua `EDGES`) + `checkEdge` (if/else điền `message`) |
| 7 | `view/GraphView.java` | field `responseDTO` + `setResponseDTO` + `display()` (in `getMessage()`) |
| 8 | `controller/GraphController.java` | `checkEdge(requestDTO)`: service → `setResponseDTO` → `display()` **1 lần** |
| 9 | `constants/Message.java` | 2 prompt, 2 câu kết quả, 2 lỗi |
| 10 | `utils/Validation.java` | `getInt(input, min, max)` — **tách 2 lỗi**, `int value = 0;` đầu hàm |
| 11 | `main/Main.java` | `public final class` + `private Main()` + `inputPoint(sc, prompt)` + gọi controller **1 lần** |

**Bẫy hay gặp:**

1. Chỉ ghi ô `[row][column]` → đồ thị thành **có hướng**: hỏi (2,5) ra có, hỏi (5,2) ra không.
2. Quên `- 1` (trong `row`/`column`) → `ArrayIndexOutOfBoundsException` khi nhập `5` (mảng 5 phần tử chỉ số 0..4).
3. In prompt bằng `print` → sai màn hình; đề cho số nhập ở **dòng dưới** → `println`.
4. Câu kết quả có **2 dấu cách** `This is  an edge` — gõ 1 dấu cách là lệch màn hình đề.

---

## 5. Test trước khi gọi thầy

| # | Gõ | Phải thấy |
|---|---|---|
| 1 | `2` / `5` | `This is  an edge` (ví dụ của đề) |
| 2 | `5` / `2` | `This is  an edge` — **đối xứng** |
| 3 | `1` / `3` | `This is not an edge` (cặp đề tô trong hình) |
| 4 | `4` / `4` | `This is not an edge` — đường chéo = 0, không có khuyên |
| 5 | `1`/`4`, `2`/`4`, `3`/`5`, `4`/`5` | `This is  an edge` |
| 6 | start `two` | `You must input a number.` rồi hỏi lại |
| 7 | start *(Enter trống)* / `2.5` | `You must input a number.` |
| 8 | start `0` / `6` / `-1` | `Value must be between 1 and 5.` |
| 9 | end `abc` rồi `9` | 2 thông báo lỗi trên, rồi hỏi lại end |

(Kiểm tự động chạy **cả 25 cặp** và so với hình của đề.)

---

## 6. Debug

| Việc | Cách làm |
|---|---|
| Breakpoint 1 | dòng `graphRepository.addEdge(edgeArray[0], edgeArray[1]);` trong `GraphService()` |
| Chạy | **Ctrl+F5**; ở mỗi lần dừng mở **Variables** → `graphRepository` → `graph` → `matrixArray`, bấm **F7** hai lần (vào `GraphRepository.addEdge` rồi `Graph.addEdge`) xem 2 ô được ghi |
| Breakpoint 2 | dòng `return matrixArray[row][column] == Constants.EDGE;` trong `Graph.isEdge` |
| Quan sát | nhập `2`, `5`: xem `row = 1`, `column = 4`, ô `matrixArray[1][4]` = 1 |
| Đa hình | ở `GraphRepository.isEdge` bấm **F7** vào `graph.isEdge(...)`: biến kiểu interface nhảy vào `Graph.isEdge` |

---

## 7. Câu hỏi thầy hay hỏi

### Thuật toán

| Câu hỏi | Trả lời mẫu |
|---|---|
| Vì sao ma trận đối xứng? | Đồ thị **vô hướng**: cạnh (2,5) cũng là (5,2) → `addEdge` ghi cả 2 ô. |
| Đường chéo nghĩa là gì? | Ô `(i, i)` = **khuyên** (cạnh nối đỉnh với chính nó). Hình không có → toàn 0. |
| Độ phức tạp hỏi cạnh? | **O(1)** — một lần đọc mảng. Bộ nhớ **O(V²)**. |
| Đề nói *"0 nếu có cạnh, 1 nếu không"* — sao em làm ngược? | Câu đó **gõ nhầm**: mọi hình của đề đều có **1** ở ô có cạnh và tô **0** ở cặp (1,3) không có cạnh. Em theo hình (xem mục 9). |
| Sao `-1` khi truy cập mảng? | Đề đánh nhãn đỉnh **từ 1**, mảng Java **từ 0**. Em dùng `Constants.FIRST_VERTEX` để không có số thẳng. |
| Đổi sang đồ thị có hướng? | Bỏ dòng ghi ô thứ hai trong `addEdge`. |

### OOP / Java

| Câu hỏi | Trả lời mẫu |
|---|---|
| 4 tính chất OOP ở đâu? | **Đóng gói**: `matrixArray` là `private` trong `Graph`, chỉ đổi qua `addEdge`/`setMatrixArray`. **Kế thừa**: `Graph implements IGraphRepresentation`; mọi lớp `extends Object`, em ghi đè `toString()`. **Đa hình**: `GraphRepository` gọi `graph.isEdge(...)` trên biến kiểu interface → chạy bản của `Graph`. **Trừu tượng**: interface `IGraphRepresentation` chỉ nói *"hỏi được cạnh"*, không nói lưu thế nào. |
| `isEdge` trả `boolean` vì sao? | Câu hỏi có/không. |
| `addEdge` trả `void`? | Nó **đổi chính ma trận** của đối tượng, không có gì mới để trả. |
| `inputPoint` sao `private static`? | `private`: chỉ `Main` dùng. `static`: `main()` là static, gọi thẳng được hàm static mà không cần `new Main()`; Guide cho phép *"static với hàm"* trong main. |
| Sao `Main` là `final` và có `private Main() { }`? | Tờ checklist 3.4: *"Class chỉ có static method thì phải có private constructor, và khai báo class là final"*. `Main` chỉ có hàm static → không ai cần `new Main()` hay kế thừa nó. |
| Validate ở đâu? | **Chỉ ở `Main`** (tờ checklist 1.1: *"Toàn bộ việc nhập dữ liệu/Validate … thực hiện ở Main"*): `Main.inputPoint` đọc dòng rồi gọi `Validation.getInt`, sai thì in `e.getMessage()` và hỏi lại. Controller nhận `GraphRequestDTO` đã sạch. |
| Sao `Validation.getInt` static? Bỏ đi thì sao? | Không dùng dữ liệu đối tượng nào. Bỏ `static` → `Validation.getInt(...)` lỗi biên dịch; phải bỏ `private` constructor, `new Validation()` trong `Main` rồi gọi qua đối tượng. |
| Hằng trong `Constants` sao `public static final`? | `public`: nhiều lớp dùng; `static`: của lớp, không cần đối tượng; `final`: không ai sửa được. |
| Field `graphService`, `graphView`, `graphRepository` sao `private`? | Chỉ lớp chứa nó dùng; Guide + thầy: *field luôn private*. |
| Sao `Graph` có constructor rỗng dù không dùng? | Thầy dạy **MVC JSP**: model là **JavaBean** — constructor rỗng `public` + get/set. |
| Sao field trong `GraphRepository` là `IGraphRepresentation` chứ không `Graph`? | Phụ thuộc vào **trừu tượng** (chữ D), để đổi cách lưu chỉ sửa 1 dòng `new`. |
| Sao tên `matrixArray`, `edgeArray`? | Tờ checklist 1.5: *"tên biến kiểu Array kết thúc bằng Array"*. |
| Interface khác abstract class? | Interface chỉ là hợp đồng, không giữ trạng thái; ở đây ma trận kề và danh sách kề **không chung dòng code nào** → interface. |
| Bài có collection không — sao không `List`? | Bài dùng **mảng** `int[][]` vì ma trận có kích thước cố định `V × V`. Nếu làm danh sách kề em khai `ArrayList<ArrayList<Integer>>` — `List` là interface, `ArrayList` là lớp cài đặt bằng mảng động; thầy muốn thấy kiểu cụ thể em thật sự dùng. |

---

## 8. Thầy đổi yêu cầu tại chỗ

| Thầy bảo | Sửa | Không phải đụng |
|---|---|---|
| **Đổi sang danh sách kề** | thêm `model/AdjacencyListGraph implements IGraphRepresentation` (field `ArrayList<ArrayList<Integer>> adjacencyList`) + sửa 1 dòng `new` trong `GraphRepository` | service, controller, view, main, DTO |
| In ma trận ra màn hình | thêm hàm đọc `getMatrixText()` vào `GraphRepository` (trả `graph.toString()`), field `String matrixText` vào `GraphResponseDTO` (service điền), `GraphView.display()` in thêm | `Graph` (đã có `toString`) |
| Đồ thị khác / thêm cạnh | chỉ `Constants.EDGES` (và `VERTICES`) | mọi file khác |
| Đồ thị có hướng | bỏ dòng ghi ô `[column][row]` trong `Graph.addEdge` | mọi file khác |
| Hỏi nhiều lần (menu lặp) | `Main`: vòng `while` + hỏi `Y/N` (thêm Message + Validation) — mỗi vòng vẫn gọi controller **1 lần** | service, repository, model, view |

---

## 9. Chỗ khác với đề / bản cũ

| Chỗ | Đề / bản cũ | Bài này | Lý do |
|---|---|---|---|
| Ý nghĩa ô | Câu chữ Guidelines: *"0 if there is an edge, 1 otherwise"* | **1 = có cạnh**, 0 = không | mọi hình của đề ghi 1 ở ô có cạnh và tô 0 ở cặp (1,3) — câu chữ gõ nhầm |
| Màn hình | Bản cũ in cả ma trận + hàng xóm của 2 đỉnh | chỉ 2 prompt + 1 câu kết quả | **đúng màn hình đề** (hình 1) |
| Câu "không phải cạnh" | Đề không có; bản cũ `This is  not an edge` | `This is not an edge` | câu đề `This is  an edge` là mẫu `"This is " + từ + " an edge"` với từ rỗng; điền `not` ra đúng 1 dấu cách mỗi bên |
| Lỗi nhập | Đề không có | `You must input a number.` · `Value must be between 1 and 5.` | giữ đúng chữ bản cũ |
| Kiến trúc | `entity/ui/utils`, Scanner trong `Validator` | MVC theo Guide, Scanner **chỉ ở `main`** | luật thầy |
| Repository (sửa 21/09/2026) | bản trước **không có** `repository` (đồ thị nằm trong `GraphService`) | `repository/GraphRepository` giữ đồ thị; service dựng + trả lời | tờ checklist 1.1: *"Bắt buộc phải có repository"* |
| View (sửa 21/09/2026) | `setResponse(...)`, DTO trả `start, end, edge`, view tự chọn câu bằng if/else | field `responseDTO` + `setResponseDTO(...)` + `display()`; DTO chỉ còn `message` do service điền | tờ checklist 1.1: view *"nhận qua thuộc tính (ResponseDTO)"*, render 1 lần/luồng |
| Tên (sửa 21/09/2026) | `GraphRepresentation`, `matrix`, `edge` (mảng), `dto` | `IGraphRepresentation`, `matrixArray`, `edgeArray`, `requestDTO` | tờ checklist 1.3 (interface bắt đầu bằng `I`) và 1.5 (biến mảng đuôi `Array`) |
| Định dạng (sửa 21/09/2026) | `Main` không `final`; `String line` khai báo trong vòng lặp; `int value;` chưa khởi tạo; `value < min \|\| value > max`; comment dính dòng code | `public final class Main` + `private Main()`; khai báo **đầu block** + khởi tạo; `(value < min) \|\| (value > max)`; dòng trống trước mọi comment | tờ checklist 3.4, 2.6 + 3.7, 3.3, 2.8 |

---

## 10. Tờ checklist 25 mục — bài này đạt thế nào

| Mục | Chỉ vào đâu |
|---|---|
| 1.1 MVC + repository | `repository/GraphRepository` giữ đồ thị; luồng `Main → GraphController.checkEdge → GraphService → GraphRepository → Graph`; `GraphView` nhận `GraphResponseDTO` qua `setResponseDTO`, `display()` gọi **1 lần**; lỗi nhập do `Main` in `e.getMessage()` |
| 1.3 interface bắt đầu bằng `I` | `model/IGraphRepresentation` |
| 1.5 tên biến | `int[][] matrixArray` (`Graph`), `int[] edgeArray` (vòng `for` trong `GraphService`) |
| 2.6 + 3.7 khai báo đầu block + khởi tạo | `Main.inputPoint`: `String line = "";` đầu hàm, trong vòng chỉ gán `line = sc.nextLine();` · `Validation.getInt`: `int value = 0;` |
| 2.8 dòng trống | giữa các field (`GraphRequestDTO`, `Constants`, `Message`), sau vùng khai báo biến, trước mọi comment, sau `}` của một khối |
| 3.3 ngoặc | `Validation.getInt`: `if ((value < min) \|\| (value > max))` |
| 3.4 lớp chỉ có static | `Main`, `Validation`, `Constants`, `Message`: `final` + constructor `private` |
