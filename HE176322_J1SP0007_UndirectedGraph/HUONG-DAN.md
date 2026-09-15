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
| Lớp `Graph` dùng **ma trận kề** | *"Construct a class Graph using adjacency matrix"* | `model/Graph.java` — field `int[][] matrix` |
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

Hỏi `(2, 5)` → đọc `matrix[1][4]` = 1 → *"This is  an edge"*. Hỏi `(1, 3)` → `matrix[0][2]` = 0 → *"This is not an edge"*.

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
| `for (int[] edge : Constants.EDGES)` | duyệt từng cạnh `{a, b}` của hình |
| `Integer.parseInt(s)` | đổi chuỗi → số; sai → `NumberFormatException` |
| `StringBuilder` | ghép ma trận thành chữ trong `toString()` |

---

## 3. Thiết kế

```
HE176322_J1SP0007_UndirectedGraph/src/
├── model/      GraphRepresentation   «interface» addEdge / isEdge  (Strategy)
│               Graph                 ma trận kề int[][] (lớp đề bắt) — JavaBean
├── dto/        GraphRequestDTO       start, end       (main ──► controller)
│               GraphResponseDTO      start, end, edge (controller ──► view)
├── service/    GraphService          dựng đồ thị của đề + checkEdge (Context)
├── controller/ GraphController       service ──► view (Facade)
├── view/       GraphView             in "This is  an edge" / "This is not an edge"
├── constants/  Message.java          câu chữ
│               Constants.java        VERTICES = 5, EDGES của hình, EDGE = 1
├── utils/      Validation            getInt(chuỗi, min, max)
└── main/       Main                  Scanner, nhập 2 điểm, gọi controller 1 lần
```

| Câu hỏi thiết kế | Trả lời |
|---|---|
| Sao có `service` mà không `repository`? | Đồ thị dựng **1 lần từ hằng số**, người dùng không thêm/sửa/xoá → không có CRUD. "Có cạnh không" là **nghiệp vụ** → `service`. |
| Sao danh sách cạnh ở `Constants`? | Hình của đề **cố định** — đó là hằng số. Đổi đồ thị = sửa 1 mảng. |
| Sao controller không đụng `Graph`? | Guide: controller *"chỉ import DTO, View, Service"*. |

**Luồng chạy:**

```
Main: inputPoint(start) ─┐ (hỏi lại khi sai)
      inputPoint(end)   ─┴► GraphRequestDTO ──► controller.checkEdge(dto)
   controller ──► service.checkEdge(dto) ──► graph.isEdge(start, end)   ← 1 lần đọc mảng
   controller ──► view.setResponse(response) ──► view.display()
```

### 3.1 Design Pattern — **Strategy** (cách lưu đồ thị)

| Yếu tố | Trong bài này |
|---|---|
| **Name** | Strategy (nhóm Behavioral) |
| **Problem** | Đề tự nêu *"ưu nhược điểm của cách biểu diễn này"* — cách kia là **danh sách kề**. Nếu service dính chặt vào `int[][]`, thầy bảo *"đổi sang danh sách kề"* là phải sửa cả service. |
| **Solution** | `GraphRepresentation` = **Strategy** (`addEdge`, `isEdge`). `Graph` = **ConcreteStrategy** (ma trận kề). `GraphService` = **Context**: field kiểu `GraphRepresentation`, chỉ **một dòng** `new Graph(Constants.VERTICES)` biết lớp cụ thể. |
| **Consequences** | ✅ Đổi cách lưu = **thêm 1 class** `AdjacencyListGraph implements GraphRepresentation` + sửa 1 dòng trong `GraphService` (**O**). ❌ Thêm 1 file interface. Interface nằm ở `model` vì `Graph` (model) implement nó, mà model không được import service. |

Controller còn là **Facade**: `Main` chỉ biết `checkEdge(dto)`.

### 3.2 SOLID trong bài

| Nguyên lý | Ở đâu |
|---|---|
| **S** | `Graph` giữ ma trận · `GraphService` dựng + hỏi · `GraphView` in · `Validation` kiểm · `Main` nhập |
| **O** | thêm cách lưu mới không sửa `isEdge`/`checkEdge` (3.1) |
| **L** | mọi lớp implement `GraphRepresentation` thay được `Graph` mà `GraphService` vẫn đúng |
| **I** | interface chỉ 2 hàm chương trình thật sự dùng |
| **D** | `GraphService` giữ field kiểu **interface** `GraphRepresentation` |

---

## 4. Code từng bước

> Thầy: *"code cái **model trước rồi đến data**"*.

| Bước | File | Việc |
|---|---|---|
| 1 | `model/GraphRepresentation.java` | interface 2 hàm `addEdge`, `isEdge` |
| 2 | `model/Graph.java` | `private int[][] matrix` + constructor rỗng + `Graph(int)` + get/set + `getVertices` + `addEdge` (2 ô) + `isEdge` + `toString` |
| 3 | `dto/GraphRequestDTO`, `GraphResponseDTO` | JavaBean: constructor rỗng + get/set |
| 4 | `constants/Constants.java` | `VERTICES`, `FIRST_VERTEX`, `EDGE`, `NO_EDGE`, `EDGES` |
| 5 | `service/GraphService.java` | constructor dựng đồ thị (vòng `for` qua `EDGES`) + `checkEdge` |
| 6 | `view/GraphView.java` | `setResponse` + `display` (if/else 2 câu) |
| 7 | `controller/GraphController.java` | `checkEdge(dto)` |
| 8 | `constants/Message.java` | 2 prompt, 2 câu kết quả, 2 lỗi |
| 9 | `utils/Validation.java` | `getInt(input, min, max)` — **tách 2 lỗi** |
| 10 | `main/Main.java` | `inputPoint(sc, prompt)` + gọi controller **1 lần** |

**Bẫy hay gặp:**

1. Chỉ ghi `matrix[start-1][end-1]` → đồ thị thành **có hướng**: hỏi (2,5) ra có, hỏi (5,2) ra không.
2. Quên `- 1` → `ArrayIndexOutOfBoundsException` khi nhập `5` (mảng 5 phần tử chỉ số 0..4).
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
| Breakpoint 1 | dòng `graph.addEdge(edge[0], edge[1]);` trong `GraphService()` |
| Chạy | **Ctrl+F5**; ở mỗi lần dừng mở **Variables** → `graph` → `matrix`, bấm **F7** vào `addEdge` xem 2 ô được ghi |
| Breakpoint 2 | dòng `return matrix[...] == Constants.EDGE;` trong `Graph.isEdge` |
| Quan sát | nhập `2`, `5`: xem `start - 1 = 1`, `end - 1 = 4`, ô `matrix[1][4]` = 1 |
| Đa hình | ở `GraphService.checkEdge` bấm **F7** vào `graph.isEdge(...)`: biến kiểu interface nhảy vào `Graph.isEdge` |

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
| 4 tính chất OOP ở đâu? | **Đóng gói**: `matrix` là `private` trong `Graph`, chỉ đổi qua `addEdge`/`setMatrix`. **Kế thừa**: `Graph implements GraphRepresentation`; mọi lớp `extends Object`, em ghi đè `toString()`. **Đa hình**: `GraphService` gọi `graph.isEdge(...)` trên biến kiểu interface → chạy bản của `Graph`. **Trừu tượng**: interface `GraphRepresentation` chỉ nói *"hỏi được cạnh"*, không nói lưu thế nào. |
| `isEdge` trả `boolean` vì sao? | Câu hỏi có/không. |
| `addEdge` trả `void`? | Nó **đổi chính ma trận** của đối tượng, không có gì mới để trả. |
| `inputPoint` sao `private static`? | `private`: chỉ `Main` dùng. `static`: `main()` là static, gọi thẳng được hàm static mà không cần `new Main()`; Guide cho phép *"static với hàm"* trong main. |
| Sao `Validation.getInt` static? Bỏ đi thì sao? | Không dùng dữ liệu đối tượng nào. Bỏ `static` → `Validation.getInt(...)` lỗi biên dịch; phải bỏ `private` constructor, `new Validation()` trong `Main` rồi gọi qua đối tượng. |
| Hằng trong `Constants` sao `public static final`? | `public`: nhiều lớp dùng; `static`: của lớp, không cần đối tượng; `final`: không ai sửa được. |
| Field `graphService`, `graphView` sao `private`? | Chỉ controller dùng; Guide + thầy: *field luôn private*. |
| Sao `Graph` có constructor rỗng dù không dùng? | Thầy dạy **MVC JSP**: model là **JavaBean** — constructor rỗng `public` + get/set. |
| Sao field trong `GraphService` là `GraphRepresentation` chứ không `Graph`? | Phụ thuộc vào **trừu tượng** (chữ D), để đổi cách lưu chỉ sửa 1 dòng `new`. |
| Interface khác abstract class? | Interface chỉ là hợp đồng, không giữ trạng thái; ở đây ma trận kề và danh sách kề **không chung dòng code nào** → interface. |
| Bài có collection không — sao không `List`? | Bài dùng **mảng** `int[][]` vì ma trận có kích thước cố định `V × V`. Nếu làm danh sách kề em khai `ArrayList<ArrayList<Integer>>` — `List` là interface, `ArrayList` là lớp cài đặt bằng mảng động; thầy muốn thấy kiểu cụ thể em thật sự dùng. |

---

## 8. Thầy đổi yêu cầu tại chỗ

| Thầy bảo | Sửa | Không phải đụng |
|---|---|---|
| **Đổi sang danh sách kề** | thêm `model/AdjacencyListGraph implements GraphRepresentation` (field `ArrayList<ArrayList<Integer>>`) + sửa 1 dòng `new` trong `GraphService` | controller, view, main, DTO |
| In ma trận ra màn hình | thêm field `String matrixText` vào `GraphResponseDTO` (service điền `graph.toString()`), `GraphView` in thêm | `Graph` (đã có `toString`) |
| Đồ thị khác / thêm cạnh | chỉ `Constants.EDGES` (và `VERTICES`) | mọi file khác |
| Đồ thị có hướng | bỏ dòng ghi ô `[end-1][start-1]` trong `Graph.addEdge` | mọi file khác |
| Hỏi nhiều lần (menu lặp) | `Main`: vòng `while` + hỏi `Y/N` (thêm Message + Validation) | service, model, view |

---

## 9. Chỗ khác với đề / bản cũ

| Chỗ | Đề / bản cũ | Bài này | Lý do |
|---|---|---|---|
| Ý nghĩa ô | Câu chữ Guidelines: *"0 if there is an edge, 1 otherwise"* | **1 = có cạnh**, 0 = không | mọi hình của đề ghi 1 ở ô có cạnh và tô 0 ở cặp (1,3) — câu chữ gõ nhầm |
| Màn hình | Bản cũ in cả ma trận + hàng xóm của 2 đỉnh | chỉ 2 prompt + 1 câu kết quả | **đúng màn hình đề** (hình 1) |
| Câu "không phải cạnh" | Đề không có; bản cũ `This is  not an edge` | `This is not an edge` | câu đề `This is  an edge` là mẫu `"This is " + từ + " an edge"` với từ rỗng; điền `not` ra đúng 1 dấu cách mỗi bên |
| Lỗi nhập | Đề không có | `You must input a number.` · `Value must be between 1 and 5.` | giữ đúng chữ bản cũ |
| Kiến trúc | `entity/ui/utils`, Scanner trong `Validator` | MVC theo Guide, Scanner **chỉ ở `main`** | luật thầy |
