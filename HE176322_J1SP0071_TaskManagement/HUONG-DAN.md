# J1.S.P0071 — Task management program (CCRM)

> Bài CRUD có **ngày tháng** và **giờ theo nửa tiếng** — hai chỗ thầy hay bẫy. Khung giống hệt
> P0055 (có `repository`), thêm **enum** loại task và **Builder** cho Task 8 thuộc tính.
> Bản 21/09/2026 đã soát theo **tờ checklist giấy 25 mục** của thầy (mục 7 cuối có bảng đối chiếu)
> và đối chiếu lại **đề gốc** từng ký tự (mục 9).

| | |
|---|---|
| Loại / LOC | Short Assignment · 150 LOC · 2 slot |
| Project | `HE176322_J1SP0071_TaskManagement` |
| Chạy | NetBeans: **File ▸ Open Project** → **F6** |
| Lớp chạy | `main.Main` |
| Kiểm tự động | `python3 _tools/verify.py J1SP0071` → 4 kịch bản × 2 locale |

---

## 1. Đề bài nói gì

- Loại task **cố định**: `1 Code · 2 Test · 3 Design · 4 Review`.
- Task: `ID, TaskTypeID, Requirement Name, Date (dd-MM-yyyy), Plan From, Plan To, Assignee, Reviewer`; **ID = ID task cuối + 1**.
- Plan From / Plan To: từ **8.0 đến 17.5**, bước **0.5** (8.0, 8.5, 9.0 …), và **From < To**.
- Menu 4 mục: **Add Task · Delete task · Display Task · exit** (ảnh đề là danh sách Word đánh số tự động `1.`–`4.`).
  - Add: nhập 7 ô, kiểm TaskTypeID tồn tại (1–4), ngày hợp lệ, giờ hợp lệ → thêm.
  - Delete: nhập ID, ID phải có → xoá.
  - Display: in bảng task **tăng dần theo ID**; ảnh đề cho cột **Time = 1 số**: task mẫu From `9.5` To `17.5` hiện `8.0` = **số giờ** (To − From).

**Đề bắt buộc** (Guidelines):

| Thứ | Đề viết | Bài này đặt ở |
|---|---|---|
| `addTask(...)` trả `int` (id), `throws Exception` | *"Student must implement methods"* | `TaskRepository.addTask(TaskRequestDTO)` trả id (+ `TaskController.addTask` gọi nó); phần "Exception list" (kiểm 7 ô) ở `Main.inputTask` qua `Validation` — tờ checklist bắt validate ở Main (mục 9) |
| `deleteTask(String id) throws Exception` | như trên | `TaskRepository.deleteTask(TaskRequestDTO) throws Exception` — ném `Task [99] does not exist.` |
| `getDataTasks()` trả list task | như trên | `TaskRepository.getDataTasks()` → `ArrayList<TaskDTO>` (mỗi phần tử = 1 dòng bảng) |
| try-catch `NullPointerException`, `NumberFormatException` | Guidelines | `Validation.getPlanTime` (bắt cả hai), `getTaskType`, `getId`, `getChoice` |
| Dùng `SimpleDateFormat` | Guidelines | `Validation.getDate`, `FormatUtils.formatDate` |
| Dùng **wrapper class** để kiểm số | Guidelines | `Integer.parseInt`, `Double.parseDouble` trong `Validation` |

---

## 2. Kiến thức cần biết

### 2.1 `SimpleDateFormat` — hai cái bẫy ngày tháng

| Gõ | `parse` thường (lenient) | `setLenient(false)` | + so ngược (`format(parse(s)).equals(s)`) |
|---|---|---|---|
| `26-06-2015` | ✅ | ✅ | ✅ |
| `31-02-2003` | ✅ **thành 03-03-2003** (sai âm thầm!) | ❌ | ❌ |
| `1-2-2015` | ✅ | ✅ (vẫn nhận 1 chữ số) | ❌ `01-02-2015` ≠ `1-2-2015` |
| `26-06-2015rubbish` | ✅ (bỏ qua đuôi) | ✅ | ❌ |
| `29-02-2016` | ✅ | ✅ (năm nhuận) | ✅ |

→ `Validation.getDate` làm **cả hai**: `dateFormat.setLenient(false)` **và** so chuỗi format ngược với chuỗi đã gõ.

### 2.2 Giờ theo nửa tiếng — kiểm bằng "nhân 2 ra số nguyên"

| Gõ | `time * 2` | Nguyên? | Kết quả |
|---|---|---|---|
| `9.5` | 19.0 | ✅ | nhận |
| `9.7` | 19.4 | ❌ | `... must be a whole or half hour ...` |
| `7.5` | — | — | ngoài `[8.0, 17.5]` → `... must be between 8.0 and 17.5.` |
| `NaN` | — | — | `!((t >= 8.0) && (t <= 17.5))` đúng với NaN → bị chặn |

Không dùng `time % 0.5 == 0` vì so sánh `double` bằng `==` dễ sai; dùng `Math.abs(x - Math.rint(x)) > EPSILON`.
Viết điều kiện khoảng dạng **`!(trong khoảng)`** vì mọi phép so sánh với `NaN` đều `false` — viết
`(t < 8.0) || (t > 17.5)` thì `NaN` lọt qua.

### 2.3 Wrapper class và 2 exception đề bắt

| Lệnh | Gõ sai | Ném ra |
|---|---|---|
| `Integer.parseInt("abc")` | chữ | `NumberFormatException` |
| `Integer.parseInt(null)` | null | `NumberFormatException` |
| `Double.parseDouble(null)` | null | **`NullPointerException`** ← vì vậy `getPlanTime` bắt `NumberFormatException \| NullPointerException` |

### 2.4 Java dùng trong bài

| API | Dùng làm gì |
|---|---|
| `enum TaskType` + `values()` | bảng loại cố định; `findById(int)` tra loại theo số gõ |
| `SimpleDateFormat("dd-MM-yyyy")` | đổi chuỗi ↔ `Date` |
| `String.format(Locale.US, "%.1f", ...)` | cột Time `8.0` (= `task.calculateTime()` = To − From) — `Locale.US` để máy tiếng Việt không in `8,0` |
| `Collections.sort(list, Comparator)` | sắp bản sao theo ID trước khi hiển thị |

---

## 3. Thiết kế

```
HE176322_J1SP0071_TaskManagement/src/
├── constants/  TaskType (enum 1..4), Message, Constants
├── model/      Task (JavaBean 8 thuộc tính + calculateTime), TaskBuilder (Builder: setId … build)
├── dto/        TaskRequestDTO  (7 giá trị ĐÃ KIỂM + id xoá)          main ──► controller
│               TaskDTO         (1 dòng bảng, đã là chữ)               repository ──► view
│               TaskResponseDTO (message + taskList)                   controller ──► view
├── repository/ TaskRepository  ArrayList<Task> taskList + nextId; addTask/deleteTask/getDataTasks
├── controller/ TaskController  điều hướng repository ↔ view; mỗi luồng render view 1 lần
├── view/       TaskView        field responseDTO + setResponseDTO() + display() KHÔNG tham số
├── utils/      Validation (kiểm chuỗi), FormatUtils (ngày, số giờ ra chữ)
└── main/       Main            final + private Main(); menu + Scanner + MỌI validate
```

| Lớp | Làm gì | Vì sao ở đây |
|---|---|---|
| `TaskType` | 4 loại cố định, `findById`, `getFirstId`, `getLastId` | Guide: Constants chứa *"hằng số, enum"* |
| `Task` | 1 task; `calculateTime()` = To − From | model = mô tả đối tượng + hàm của chính nó, không in, không Scanner |
| `TaskBuilder` | lắp `Task` từng bước | chỉ biết `Task` → cùng package model |
| `TaskRepository` | giữ `taskList`, 3 hàm đề bắt (CRUD) | Guide: *"Chứa data … CRUD"*; tờ checklist: *"Bắt buộc phải có repository"* |
| `TaskDTO` | 1 dòng bảng (7 cột, đã là chữ) + `toString()` căn cột | view không được thấy model |
| `TaskResponseDTO` | câu kết quả (`message`) **hoặc** các dòng bảng (`taskList`) | Guide: *"những gì sẽ hiển thị qua view khai báo ở đây"* |
| `TaskView` | in câu kết quả / bảng — nhận qua **thuộc tính** | tờ checklist 1.1 |
| `Validation` | chuỗi → giá trị sạch hoặc ném lỗi; **Main gọi** | Guide: utils *"validate"*, static |
| `FormatUtils` | `Date` → `dd-MM-yyyy`, số giờ → `8.0` | hàm dùng chung, static |

| Câu hỏi thiết kế | Trả lời |
|---|---|
| Sao bài **có repository**? | Tờ checklist 1.1: *"Bắt buộc phải có repository"*. `TaskRepository` giữ `ArrayList<Task> taskList` + `nextId` và 3 hàm CRUD đơn giản của đề (`addTask` thêm, `deleteTask` xoá, `getDataTasks` đọc); **không** kiểm chuỗi gõ, **không** in, **không** đọc bàn phím. Không có tính toán nghiệp vụ ngoài CRUD nên không cần `service` (Controller → Repository → Model). |
| View nhận dữ liệu thế nào? | Qua **thuộc tính**, không qua tham số (tờ checklist 1.1): `TaskView` có field `responseDTO`; controller gọi `setResponseDTO(responseDTO)` rồi `display()` — **1 lần cho 1 luồng**. Add/Delete set `message` (`Task [1] has been added.`); Display set `taskList` (rỗng → view in `There is no task yet.`). |
| Validate ở đâu? | Ở **Main** qua `utils/Validation` (tờ checklist 1.1: *"Toàn bộ việc nhập dữ liệu/Validate … thực hiện ở Main"*): `getChoice`, `getRequired`, `getTaskType`, `getDate`, `getPlanTime`, `checkPlanOrder`, `getId`. Repository chỉ còn luật **nghiệp vụ**: ID phải tồn tại (`Task [99] does not exist.`). |

**Luồng chung:** `Main` (nhập + validate) ──TaskRequestDTO──► `TaskController` ──► `TaskRepository` ──►
`Task`; kết quả thành **một** `TaskResponseDTO` ──► `TaskView` (`setResponseDTO` + `display()` **1 lần**).
Mỗi case của `Main.main` gọi controller **đúng 1 lần**; lỗi nào cũng `throw new Exception(Message.X)` và
`Main` in `e.getMessage()`.

**Luồng Add** (đề: `addTask` nhận 7 chuỗi, **kiểm xong mới thêm**, lỗi thì ném):

```
Main.inputTask: in tiêu đề, hỏi đủ 7 ô (inputLine) ── chưa kiểm gì, màn hình y như đề
   ├─ Validation.getRequired(name) → getRequired(assignee) → getRequired(reviewer)
   ├─ Validation.getTaskType → getDate → getPlanTime(from) → getPlanTime(to) → checkPlanOrder
   │        (lỗi đầu tiên → throw → rơi về catch trong Main.main → in e.getMessage())
   └─ TaskRequestDTO (TaskType, Date, double … đã sạch)
Main.main: controller.addTask(requestDTO)                            ← gọi controller 1 lần
   controller ──► repository.addTask(requestDTO)
        ├─ new TaskBuilder().setId(nextId)...build()   ← Builder
        └─ taskList.add(task); nextId++; return id
   controller ──► responseDTO.setMessage("Task [1] has been added.")
              ──► view.setResponseDTO(responseDTO) + view.display()   ← render 1 lần
```

### 3.1 Design Pattern

**Builder** (Creational)

| Yếu tố | Trong bài này |
|---|---|
| **Name** | Builder |
| **Problem** | `Task` có **8 thuộc tính**, trong đó 2 `double` liền nhau (from/to) và nhiều `String` (name, assignee, reviewer). Constructor 8 tham số gọi đảo 2 giá trị cùng kiểu thì **compiler không phát hiện**. |
| **Solution** | `TaskBuilder` = **Builder** (`setId`, `setTaskType`, `setRequirementName`, `setDate`, `setPlanFrom`, `setPlanTo`, `setAssignee`, `setReviewer`, `build`). `Task` = **Product**. `TaskRepository.addTask` = **Director** (gọi các bước). |
| **Consequences** | ✅ Mỗi giá trị **có tên** ở chỗ gán; thêm thuộc tính = thêm 1 hàm `setX`, không đổi chữ ký nào. ❌ Thêm 1 lớp. Builder không `static` (model cấm static) nên là lớp riêng, `new TaskBuilder()` cho mỗi task. |

**Facade**: `TaskController` là một cửa gọn cho `Main` (Main không biết có repository).
**Repository**: `TaskRepository` là nơi duy nhất chạm vào `ArrayList<Task>`.
**MVC (JSP)**: `Task`, 3 DTO là JavaBean (field private, constructor rỗng public, get/set).

### 3.2 SOLID

| | Ở đâu |
|---|---|
| **S** | `Task` giữ dữ liệu · `TaskBuilder` tạo · `TaskRepository` lưu · `Validation` kiểm · `FormatUtils` định dạng · `TaskView` in |
| **O** | thêm loại "5 Deploy" = **thêm 1 dòng** trong enum `TaskType`; `Validation`, thông báo `It must be 1 to 5.` tự đúng nhờ `getFirstId()/getLastId()` |
| **L** | (không có họ lớp con — không ép) |
| **I** | (không có interface — không nhét cho có) |
| **D** | `Main` chỉ phụ thuộc controller + DTO + utils, không biết repository/model/view |

---

## 4. Code từng bước

> Thầy: *"code cái **model trước rồi đến data**"*.

| Bước | File | Việc |
|---|---|---|
| 1 | `constants/TaskType.java` | enum 4 giá trị (id, name) + `findById` · `getFirstId` · `getLastId` |
| 2 | `model/Task.java` | 8 field private + constructor rỗng + get/set + `calculateTime` + `toString` (Alt+Insert) |
| 3 | `model/TaskBuilder.java` | field `task`; 8 hàm `setX` trả `this`; `build()` |
| 4 | `dto/TaskRequestDTO.java` | `requirementName`, `taskType` (TaskType), `date` (Date), `planFrom`/`planTo` (double), `assignee`, `reviewer`, `id` (int) + get/set |
| 5 | `dto/TaskDTO.java` | 7 cột (đã là chữ) + get/set + `toString` theo `ROW_FORMAT` |
| 6 | `dto/TaskResponseDTO.java` | `message` + `taskList` (`ArrayList<TaskDTO>`) + get/set |
| 7 | `constants/Message.java`, `Constants.java` | menu, prompt, lỗi; số menu, 8.0/17.5, định dạng |
| 8 | `utils/Validation.java` | `getText` · `getChoice` · `getRequired` · `getTaskType` · `getDate` · `getPlanTime` · `checkPlanOrder` · `getId` |
| 9 | `utils/FormatUtils.java` | `formatDate`, `formatTime` |
| 10 | `repository/TaskRepository.java` | `addTask` · `deleteTask` · `getDataTasks` · `convertToTaskDTO` (private) |
| 11 | `view/TaskView.java` | field `responseDTO` + `setResponseDTO` + `display()` |
| 12 | `controller/TaskController.java` | `addTask` · `deleteTask` · `displayTasks` — mỗi hàm `setResponseDTO` rồi `display()` **1 lần** |
| 13 | `main/Main.java` | `final` + `private Main()`; `main`, `inputChoice`, `inputLine`, `inputTask`, `inputDelete` |

**Bẫy hay gặp:**

1. **Thứ tự hỏi ≠ thứ tự kiểm.** Màn hình hỏi *Name, Type, Date, From, To, Assignee, Reviewer*;
   `addTask` của đề nhận (và kiểm) *requirementName, assignee, reviewer, taskTypeID, date, planFrom, planTo*.
   `Main.inputTask` hỏi theo màn hình, **kiểm theo đề** — nên Name và Assignee cùng trống thì báo Name,
   Assignee trống và Type sai thì báo **Assignee**. Setter của DTO theo **tên** nên không gán nhầm được.
2. Quên `setLenient(false)` → `31-02-2003` được nhận thành 03-03-2003.
3. `String.format("%.1f")` không có `Locale.US` → máy lab tiếng Việt in `8,0`.
4. Kiểm xong **mới** gọi controller: Main kiểm đủ 7 ô rồi mới `controller.addTask`, nên lỗi giờ không để lại task dở.

---

## 5. Test trước khi gọi thầy

| # | Chọn | Gõ (Name/Type/Date/From/To/Assignee/Reviewer) | Phải thấy |
|---|---|---|---|
| 1 | 3 | (danh sách rỗng) | tiêu đề bảng + `There is no task yet.` |
| 2 | 1 | `Dev Program/1/26-06-2015/9.5/17.5/Dev/Lead` | `Task [1] has been added.` |
| 3 | 1 | Name trống | `Requirement Name cannot be empty.` |
| 4 | 1 | Assignee trống / Reviewer trống | `Assignee cannot be empty.` / `Reviewer cannot be empty.` |
| 5 | 1 | Type trống / `Code` / `9` | `Task Type cannot be empty.` / `Task Type must be a number.` / `Task Type [9] does not exist. It must be 1 to 4.` |
| 6 | 1 | Date trống | `Date cannot be empty.` |
| 7 | 1 | Date `31-02-2003`, `1-2-2015`, `26-06-2015rubbish`, `29-02-2015` | `Date must be a real date in the format dd-MM-yyyy.` |
| 8 | 1 | From trống / `abc` | `Plan From cannot be empty.` / `Plan From must be a number.` |
| 9 | 1 | From `7.5` / `NaN`; To `18.0` | `Plan From must be between 8.0 and 17.5.` / `Plan To must be between 8.0 and 17.5.` |
| 10 | 1 | From `9.7` | `Plan From must be a whole or half hour: 8.0, 8.5, 9.0 ... 17.5.` |
| 11 | 1 | To trống | `Plan To cannot be empty.` |
| 12 | 1 | From `17.5` To `9.5`; From `9.0` To `9.0` | `Plan From must be less than Plan To.` |
| 13 | 2 | `99` / `abc` / trống | `Task [99] does not exist.` / `ID must be a number.` / `ID cannot be empty.` |
| 14 | 2 | `2` | `Task [2] has been deleted.` |
| 15 | 3 | | bảng tăng dần theo ID, cột Time `8.0` cho task 9.5 → 17.5 (như ảnh đề) |
| 16 | menu | `x` / `5` | `You must input a number.` / `Please choose from 1 to 4.` |

---

## 6. Debug

| Việc | Cách làm |
|---|---|
| Breakpoint | dòng `requestDTO.setDate(Validation.getDate(date));` trong `Main.inputTask` |
| Chạy | **Ctrl+F5**, chọn 1, gõ ngày `31-02-2003` (các ô khác đúng) |
| Bước | **F7** vào `getDate` → **F8** qua `dateFormat.parse(text)` → ném `ParseException` nhờ `setLenient(false)`; thử lại với `1-2-2015`: `parse` qua được, đến `if (!dateFormat.format(date).equals(text))` thấy `"01-02-2015"` ≠ `"1-2-2015"` |
| Xem Builder | breakpoint ở `.build()` trong `TaskRepository.addTask` → mở `task` trong Variables thấy đủ 8 thuộc tính |
| Xem ID | watch `nextId` trước/sau `taskList.add(task)` |
| Xem 1 luồng render 1 lần | breakpoint ở `taskView.display()` trong controller — mỗi lần chọn menu chỉ dừng **1 lần** |

---

## 7. Câu hỏi thầy hay hỏi

### OOP

| Câu hỏi | Trả lời mẫu |
|---|---|
| 4 tính chất OOP ở đâu? | **Đóng gói**: 8 field `private` trong `Task` + get/set. **Kế thừa**: mọi lớp `extends Object`, enum `TaskType` ngầm `extends Enum`; anonymous class trong `getDataTasks` `implements Comparator<Task>`. **Đa hình**: `toString()` `@Override` trong `Task`, `TaskDTO`; `Collections.sort` gọi `compare` của lớp em viết qua biến kiểu `Comparator`. **Trừu tượng**: `Main` gọi `controller.addTask(requestDTO)` không biết dữ liệu nằm trong `ArrayList`. |
| Sao `TaskType` là enum mà không phải class? | Bảng loại **cố định**, không thêm/xoá lúc chạy → enum chặn được mọi loại "lạ", so sánh bằng `==`, và Guide xếp enum vào `constants`. |

### Access modifier / static / kiểu trả về

| Câu hỏi | Trả lời mẫu |
|---|---|
| Field sao `private`? | Chỉ đổi qua setter/builder — không lớp nào sửa thẳng. |
| `convertToTaskDTO` sao `private`? | Chỉ `TaskRepository` dùng. Hàm `public` là "hợp đồng" cho lớp khác gọi: `addTask/deleteTask/getDataTasks` (controller gọi), mọi `setX` của Builder (repository gọi), get/set (builder, repository, view gọi). |
| Constructor `TaskType` sao `private`? | Chỉ 4 giá trị trong enum được tồn tại; không ai `new` thêm được. |
| `static` ở đâu, vì sao? | (1) `Validation`, `FormatUtils`: không giữ dữ liệu, cùng chuỗi vào → cùng kết quả; Guide bắt *"phải dùng static method"*. (2) Hằng `Message`/`Constants`: một bản dùng chung. (3) `TaskType.findById/getFirstId/getLastId`: thuộc **bảng loại**, không thuộc một loại nào. (4) Hàm phụ trong `Main`: `main` là static nên chỉ gọi thẳng được hàm static; biến thì không static (Guide cấm). |
| **Bỏ `static` ở `Validation.getDate`?** | `Validation.getDate(...)` báo lỗi biên dịch. Muốn chạy: bỏ `private` constructor, `new Validation()` trong `Main` rồi gọi `validation.getDate(...)`. |
| `Main` sao `final` + `private Main()`? | Tờ checklist 3.4: lớp chỉ có hàm static phải có private constructor và khai báo `final` (không ai `new Main()`, không ai kế thừa). |
| `addTask` trả `int` vì sao? | Đề bắt trả **id task** — controller dùng nó in `Task [1] has been added.` |
| `deleteTask` trả `void`? | Không có gì để trả; lỗi đi bằng `throw` (đề: *"Return value: Exception list"*). |
| `getTaskType` trả `TaskType` chứ không `int`? | Task giữ luôn **loại**, lấy được cả tên để in — không phải tra lại. |
| `getPlanTime` trả `double`? | Giờ có nửa: `9.5` = 9h30. |
| **Sao `ArrayList` mà không `List`?** | `List` là **interface** (hợp đồng: `add/get/remove`), `ArrayList` là **lớp cài đặt** bằng mảng động: lấy theo chỉ số nhanh, chèn/xoá giữa phải dời phần tử. Em khai báo đúng lớp em dùng; `LinkedList` cài cùng hợp đồng bằng danh sách liên kết. |
| Sao `TaskRequestDTO` giữ `TaskType`, `Date`, `double` chứ không phải chuỗi như đề? | Tờ checklist 1.1: validate ở **Main**. Main kiểm xong thì đã có giá trị đúng kiểu → đặt luôn vào DTO; controller/repository nhận giá trị **sạch**, không phải đổi kiểu lại. |
| Sao trong `main()` có `requestDTO = null`, `choice = 0`? | Tờ checklist 2.6 + 3.7: biến **khai ở đầu block và khởi tạo luôn**; trong vòng lặp chỉ **gán** (`choice = inputChoice(sc);`). Mỗi option tạo DTO mới trong `inputTask`/`inputDelete`. |

### Kiến trúc / nghiệp vụ

| Câu hỏi | Trả lời mẫu |
|---|---|
| Sao lỗi hiện **sau** khi hỏi đủ 7 ô? | Đề: `addTask(7 chuỗi) throws Exception` — kiểm khi đã có đủ 7 chuỗi. Tờ checklist bắt validate ở Main, nên `Main.inputTask` hỏi đủ 7 ô **rồi** kiểm theo đúng thứ tự tham số của `addTask`; lỗi đầu tiên → `throw` → `catch` trong `main()` in `e.getMessage()`, về menu. |
| Sao bài có repository? · View nhận dữ liệu thế nào? · Validate ở đâu? | Xem bảng "Câu hỏi thiết kế" ở mục 3. |
| Controller có đụng model không? | Không: `TaskController` chỉ import `Message`, 2 DTO, `TaskRepository`, `TaskView`. `Task` chỉ nằm trong repository; ra khỏi repository là `TaskDTO`. |
| Mỗi luồng gọi view mấy lần? | **1 lần** `display()`: controller gom câu kết quả hoặc cả bảng vào một `TaskResponseDTO` rồi mới render. |
| Cột Time sao là `8.0`? | Ảnh đề: task Add mẫu From `9.5` To `17.5` hiện ở bảng Time `8.0` = **số giờ** làm → `Task.calculateTime()` = `planTo - planFrom`, in 1 chữ số thập phân. |
| ID sau khi xoá task cuối? | `nextId` chỉ tăng: thêm 1, 2, xoá 2, thêm tiếp → **3** (ID không bao giờ bị dùng lại cho task khác). |
| Pattern gì? | **Builder** (`TaskBuilder`), **Facade** (controller), **Repository**, **MVC** — mục 3.1. |

### Tờ checklist 25 mục — bài này đạt thế nào

| Mục | Chỉ vào đâu |
|---|---|
| **1.1** MVC + repository | `repository/TaskRepository` (bắt buộc có repository); `TaskController` không import `model`; `TaskView` nhận `responseDTO` qua setter, `display()` không tham số, gọi **1 lần/luồng**; mỗi case trong `Main.main` gọi controller **1 lần**; mọi nhập + validate ở `Main` |
| **1.4** method mở đầu bằng động từ | `TaskBuilder.setId/setTaskType/…` (bản trước `withX`), `TaskType.findById/getFirstId/getLastId` (bản trước `fromId/firstId/lastId`), `Task.calculateTime`, `TaskRepository.convertToTaskDTO` |
| **1.5** tên collection + `Id` | `taskList` (`TaskRepository`, `TaskResponseDTO`), `sortedList`, `rowList`; `nextId`, `typeId`, `taskId`, `taskTypeId` (đề viết `taskTypeID`) |
| **2.6 / 3.7** khai báo đầu block + khởi tạo | `Main.main`: `requestDTO = null`, `running = true`, `choice = 0`; `Main.inputTask`: 7 chuỗi `= ""` ở đầu hàm; `Validation`: `int choice = 0`, `int typeId = 0`, `Date date = null`, `double time = 0`, `double steps = 0` |
| **2.8** dòng trống | giữa các field (mọi lớp, cả hằng trong `Message`/`Constants` và giá trị enum), sau vùng khai báo biến, trước mọi comment đứng sau dòng code, giữa các `case`, sau `}` trước câu lệnh tiếp |
| **3.3** ngoặc | `Validation.getChoice`: `if ((choice < min) \|\| (choice > max))`; `getPlanTime`: `if (!((time >= Constants.PLAN_MIN) && (time <= Constants.PLAN_MAX)))` |
| **3.4** lớp chỉ có static | `Main`, `Validation`, `FormatUtils`, `Constants`, `Message`: `final` + `private` constructor |
| **3.8** cộng chuỗi | `Task.toString()` dùng `String.format(Constants.TASK_TEXT_FORMAT, …)` (bản trước `id + " " + …`) |

---

## 8. Thầy đổi yêu cầu tại chỗ

| Thầy bảo | Sửa | Không đụng |
|---|---|---|
| Thêm loại `5 Deploy` | 1 dòng trong `TaskType` | mọi file khác (thông báo tự thành `1 to 5`) |
| Giờ làm 7.0–18.0 | `Constants.PLAN_MIN/PLAN_MAX` + chữ trong `Message.PLAN_OUT_OF_RANGE` | `Validation` |
| Bước 15 phút | `Constants.STEPS_PER_HOUR = 4` + chữ `PLAN_NOT_HALF` | `Validation` |
| Định dạng ngày `dd/MM/yyyy` | `Constants.DATE_FORMAT` + chữ `DATE_INVALID` | mọi file khác |
| Cột Time in `9.5-17.5` (from-to) | `Constants.TIME_FORMAT = "%.1f-%.1f"`, `FormatUtils.formatTime(planFrom, planTo)`, `TaskRepository.convertToTaskDTO` truyền 2 giờ | `Task`, `TaskView`, `Main` |
| Thêm "Update task" | `Message.MENU`, `Constants` (số menu), `Main.inputUpdate` + `case` trong `Main`, `TaskController.updateTask`, `TaskRepository.updateTask` | `Task`, `TaskBuilder`, `Validation` (dùng lại) |
| ID = "ID task cuối + 1" theo **task còn lại** | `TaskRepository.addTask`: `taskList.isEmpty() ? FIRST_ID : taskList.get(taskList.size() - 1).getId() + 1` | mọi file khác |

---

## 9. Chỗ khác với đề / bản cũ

Đối chiếu đề gốc 21/09/2026 (`.docx`, cả chữ trong khung ảnh): menu, `------------Add Task---------------`,
7 câu nhắc (`Requirement Name: ` … `Reviewer: `), `---------Del Task------`, `ID:`, tiêu đề bảng và 7 nhãn cột
**khớp từng ký tự**. Còn lại:

| Chỗ | Đề / bản cũ | Bài này | Lý do |
|---|---|---|---|
| Chữ ký `addTask` | 7 tham số `String`, tự kiểm, `throws Exception` | `addTask(TaskRequestDTO)` — DTO giữ 7 giá trị **Main đã kiểm**; `addTask` không còn gì để ném | thầy: **không truyền 3 tham số 1 hàm** (V4); tờ checklist 1.1: validate ở Main, *"Có thể thông qua param nếu số param < 3"*. **Hỏi thầy** nếu thầy muốn `addTask` tự kiểm như đề |
| `deleteTask(String id)` | nhận `String` | `deleteTask(TaskRequestDTO)` (dto.id là `int` Main đã đổi) | Guide: dữ liệu vào controller qua DTO; chuỗi → số là validate (ở Main) |
| `taskTypeID` | đề viết `ID` | `taskTypeId` (biến chuỗi gõ trong `Main.inputTask`) | tờ checklist 1.5: *"thống nhất viết là Id"* |
| `getDataTasks()` | "list of task" | `ArrayList<TaskDTO>` | view không được thấy model; khai báo kiểu cụ thể (V5) |
| Menu | ảnh đề là danh sách Word **đánh số tự động** `1.`–`4.` (bản `.txt` rút ra làm rơi số) | `1. Add Task` … `4. exit` + `Please choose one option: ` | khớp đề; câu nhắc vì đề bảo *"prompts users to select an option"* |
| Bảng | đề gõ bằng **tab** | cột căn bằng `printf` (`Constants.ROW_FORMAT`), cùng nhãn, cùng thứ tự cột | tab chỉ thẳng hàng khi tên dài 8–15 ký tự (thử `Test` là lệch); giữ đúng bản tham chiếu |
| Cột Time (21/09) | bản cũ + bản tham chiếu: `9.5-17.5` (from-to) | `8.0` = To − From (`Task.calculateTime`) | ảnh đề: task Add mẫu 9.5 → 17.5 hiện Time `8.0`. `_tools/tests/J1SP0071.py` đặt `REPLACE_REFERENCE = True` (chép lại 2 kịch bản tham chiếu, chỉ đổi cột Time). Thầy muốn from-to: mục 8 |
| ID | "ID task cuối + 1" | bộ đếm chỉ tăng (`nextId`) | đọc "task cuối" = task được thêm gần nhất; xoá task cuối không làm ID bị dùng lại (bản tham chiếu cũng vậy). Muốn theo task còn lại: mục 8 |
| `NaN` ở From/To | bản cũ **nhận** `NaN` | từ chối (`must be between 8.0 and 17.5.`) | viết điều kiện `!(trong khoảng)` |
| Kiến trúc | `bo/ui`, Scanner trong `Validator` | MVC theo Guide, Scanner **chỉ ở main** | luật thầy |
| Validate (21/09) | bản trước: `TaskRepository.addTask/deleteTask` gọi `Validation`; DTO toàn `String` | `Main.inputTask/inputDelete` gọi `Validation` (cùng thứ tự, cùng câu báo lỗi → màn hình không đổi); DTO giữ giá trị đúng kiểu; repository chỉ CRUD | tờ checklist 1.1: *"Toàn bộ việc nhập dữ liệu/Validate … thực hiện ở Main"*; *"Repository chỉ chứa data và CRUD methods đơn giản"* |
| View (21/09) | bản trước: `setTasks(ArrayList)` + `display()` + `showMessage(String)` | field `responseDTO` + `setResponseDTO` + `display()`; câu kết quả đi trong `TaskResponseDTO.message` | tờ checklist 1.1: view nhận qua **thuộc tính**, render **1 lần/luồng** |
| DTO (21/09) | bản trước: `TaskResponseDTO` = 1 dòng bảng | `TaskDTO` = 1 dòng bảng; `TaskResponseDTO` = `message` + `taskList` | một DTO chở **cả câu trả lời** của 1 luồng |
| Tên (21/09) | bản trước: `tasks`, `sorted`, `rows`, `withX`, `fromId/firstId/lastId`, `toResponse` | `taskList`, `sortedList`, `rowList`, `setX`, `findById/getFirstId/getLastId`, `convertToTaskDTO` | tờ checklist 1.4 (động từ), 1.5 (đuôi `List`) |
| `Main` (21/09) | bản trước: không `final`; `addTask/deleteTask` vừa đọc vừa gọi controller; biến khai giữa block | `final` + `private Main()`; `inputTask/inputDelete` chỉ đọc + kiểm, trả `TaskRequestDTO`; mỗi case gọi controller 1 lần; biến khai đầu hàm + khởi tạo | tờ checklist 1.1, 2.6, 3.4, 3.7 |
