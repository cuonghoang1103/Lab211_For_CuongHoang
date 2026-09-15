# J1.S.P0071 — Task management program (CCRM)

> Bài CRUD có **ngày tháng** và **giờ theo nửa tiếng** — hai chỗ thầy hay bẫy. Khung giống hệt
> P0055 (có `repository`), thêm **enum** loại task và **Builder** cho Task 8 thuộc tính.

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
- Menu 4 mục: **Add Task · Delete task · Display Task · exit**.
  - Add: nhập 7 ô, kiểm TaskTypeID tồn tại (1–4), ngày hợp lệ, giờ hợp lệ → thêm.
  - Delete: nhập ID, ID phải có → xoá.
  - Display: in bảng task **tăng dần theo ID**.

**Đề bắt buộc** (Guidelines):

| Thứ | Đề viết | Bài này đặt ở |
|---|---|---|
| `addTask(...)` trả `int` (id), `throws Exception` | *"Student must implement methods"* | `TaskRepository.addTask(TaskRequestDTO)` (+ `TaskController.addTask` gọi nó) |
| `deleteTask(String id) throws Exception` | như trên | `TaskRepository.deleteTask(TaskRequestDTO)` |
| `getDataTasks()` trả list task | như trên | `TaskRepository.getDataTasks()` → `ArrayList<TaskResponseDTO>` |
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

→ `Validation.getDate` làm **cả hai**: `setLenient(false)` **và** so chuỗi format ngược với chuỗi đã gõ.

### 2.2 Giờ theo nửa tiếng — kiểm bằng "nhân 2 ra số nguyên"

| Gõ | `time * 2` | Nguyên? | Kết quả |
|---|---|---|---|
| `9.5` | 19.0 | ✅ | nhận |
| `9.7` | 19.4 | ❌ | `... must be a whole or half hour ...` |
| `7.5` | — | — | ngoài `[8.0, 17.5]` → `... must be between 8.0 and 17.5.` |
| `NaN` | — | — | `!(t >= 8.0 && t <= 17.5)` đúng với NaN → bị chặn |

Không dùng `time % 0.5 == 0` vì so sánh `double` bằng `==` dễ sai; dùng `Math.abs(x - Math.rint(x)) > EPSILON`.
Viết điều kiện khoảng dạng **`!(trong khoảng)`** vì mọi phép so sánh với `NaN` đều `false` — viết
`t < 8.0 || t > 17.5` thì `NaN` lọt qua.

### 2.3 Wrapper class và 2 exception đề bắt

| Lệnh | Gõ sai | Ném ra |
|---|---|---|
| `Integer.parseInt("abc")` | chữ | `NumberFormatException` |
| `Integer.parseInt(null)` | null | `NumberFormatException` |
| `Double.parseDouble(null)` | null | **`NullPointerException`** ← vì vậy `getPlanTime` bắt `NumberFormatException \| NullPointerException` |

### 2.4 Java dùng trong bài

| API | Dùng làm gì |
|---|---|
| `enum TaskType` + `values()` | bảng loại cố định; `fromId(int)` tra loại theo số gõ |
| `SimpleDateFormat("dd-MM-yyyy")` | đổi chuỗi ↔ `Date` |
| `String.format(Locale.US, "%.1f-%.1f", ...)` | cột Time `9.5-17.5` — `Locale.US` để máy tiếng Việt không in `9,5` |
| `Collections.sort(list, Comparator)` | sắp bản sao theo ID trước khi hiển thị |

---

## 3. Thiết kế

```
HE176322_J1SP0071_TaskManagement/src/
├── constants/  TaskType (enum 1..4), Message, Constants
├── model/      Task (JavaBean 8 thuộc tính), TaskBuilder (Builder)
├── dto/        TaskRequestDTO  (7 chuỗi gõ + id xoá)   main ──► controller
│               TaskResponseDTO (1 dòng bảng, đã là chữ) controller ──► view
├── repository/ TaskRepository  ArrayList<Task> + nextId; addTask/deleteTask/getDataTasks
├── controller/ TaskController  điều hướng repository ↔ view
├── view/       TaskView        in bảng + câu thông báo
├── utils/      Validation (kiểm chuỗi), FormatUtils (ngày, giờ ra chữ)
└── main/       Main            menu + Scanner
```

| Lớp | Làm gì | Vì sao ở đây |
|---|---|---|
| `TaskType` | 4 loại cố định, `fromId`, `firstId`, `lastId` | Guide: Constants chứa *"hằng số, enum"* |
| `Task` | 1 task | model = mô tả đối tượng, không in, không Scanner |
| `TaskBuilder` | lắp `Task` từng bước | chỉ biết `Task` → cùng package model |
| `TaskRepository` | giữ danh sách, 3 hàm đề bắt | Guide: *"Chứa data … CRUD"* |
| `Validation` | chuỗi → giá trị sạch hoặc ném lỗi | Guide: utils *"validate"*, static |
| `FormatUtils` | `Date` → `dd-MM-yyyy`, giờ → `9.5-17.5` | hàm dùng chung, static |

**Luồng Add** (đề: `addTask` nhận chuỗi và **tự kiểm, tự ném lỗi**):

```
Main: in tiêu đề, hỏi đủ 7 ô ──► TaskRequestDTO (chuỗi) ──► controller.addTask(dto)   ← gọi 1 lần
   controller ──► repository.addTask(dto)
        ├─ Validation.getRequired(name) → getRequired(assignee) → getRequired(reviewer)
        ├─ Validation.getTaskType → getDate → getPlanTime(from) → getPlanTime(to) → checkPlanOrder
        │        (lỗi đầu tiên → throw → rơi về catch trong Main → in e.getMessage())
        ├─ new TaskBuilder().withId(nextId)...build()   ← Builder
        └─ tasks.add(task); nextId++; return id
   controller ──► view.showMessage("Task [1] has been added.")
```

### 3.1 Design Pattern

**Builder** (Creational)

| Yếu tố | Trong bài này |
|---|---|
| **Name** | Builder |
| **Problem** | `Task` có **8 thuộc tính**, trong đó 2 `double` liền nhau (from/to) và nhiều `String` (name, assignee, reviewer). Constructor 8 tham số gọi đảo 2 giá trị cùng kiểu thì **compiler không phát hiện**. |
| **Solution** | `TaskBuilder` = **Builder** (`withId`, `withTaskType`, `withRequirementName`, `withDate`, `withPlanFrom`, `withPlanTo`, `withAssignee`, `withReviewer`, `build`). `Task` = **Product**. `TaskRepository.addTask` = **Director** (gọi các bước). |
| **Consequences** | ✅ Mỗi giá trị **có tên** ở chỗ gán; thêm thuộc tính = thêm 1 hàm `withX`, không đổi chữ ký nào. ❌ Thêm 1 lớp. Builder không `static` (model cấm static) nên là lớp riêng, `new TaskBuilder()` cho mỗi task. |

**Facade**: `TaskController` là một cửa gọn cho `Main` (Main không biết có repository).
**Repository**: `TaskRepository` là nơi duy nhất chạm vào `ArrayList<Task>`.
**MVC (JSP)**: `Task`, 2 DTO là JavaBean (field private, constructor rỗng public, get/set).

### 3.2 SOLID

| | Ở đâu |
|---|---|
| **S** | `Task` giữ dữ liệu · `TaskBuilder` tạo · `TaskRepository` lưu · `Validation` kiểm · `FormatUtils` định dạng · `TaskView` in |
| **O** | thêm loại "5 Deploy" = **thêm 1 dòng** trong enum `TaskType`; `Validation`, thông báo `It must be 1 to 5.` tự đúng nhờ `firstId()/lastId()` |
| **L** | (không có họ lớp con — không ép) |
| **I** | (không có interface — không nhét cho có) |
| **D** | `Main` chỉ phụ thuộc controller + DTO, không biết repository/model |

---

## 4. Code từng bước

> Thầy: *"code cái **model trước rồi đến data**"*.

| Bước | File | Việc |
|---|---|---|
| 1 | `constants/TaskType.java` | enum 4 giá trị (id, name) + `fromId` · `firstId` · `lastId` |
| 2 | `model/Task.java` | 8 field private + constructor rỗng + get/set + `toString` (Alt+Insert) |
| 3 | `model/TaskBuilder.java` | field `task`; 8 hàm `withX` trả `this`; `build()` |
| 4 | `dto/TaskRequestDTO.java` | 8 field **String** + get/set |
| 5 | `dto/TaskResponseDTO.java` | 7 cột (đã là chữ) + get/set + `toString` theo `ROW_FORMAT` |
| 6 | `constants/Message.java`, `Constants.java` | menu, prompt, lỗi; số menu, 8.0/17.5, định dạng |
| 7 | `utils/Validation.java` | `getText` · `getChoice` · `getRequired` · `getTaskType` · `getDate` · `getPlanTime` · `checkPlanOrder` · `getId` |
| 8 | `utils/FormatUtils.java` | `formatDate`, `formatTime` |
| 9 | `repository/TaskRepository.java` | `addTask` · `deleteTask` · `getDataTasks` · `toResponse` (private) |
| 10 | `view/TaskView.java` | `setTasks` · `display` · `showMessage` |
| 11 | `controller/TaskController.java` | `addTask` · `deleteTask` · `displayTasks` |
| 12 | `main/Main.java` | menu, `inputChoice`, `addTask`, `deleteTask` |

**Bẫy hay gặp:**

1. **Thứ tự hỏi ≠ thứ tự tham số của đề.** Màn hình hỏi *Name, Type, Date, From, To, Assignee, Reviewer*;
   `addTask` của đề nhận *requirementName, assignee, reviewer, taskTypeID, date, planFrom, planTo*. Tất cả
   là `String` — gán nhầm thì compiler **không báo**. DTO có setter theo **tên** nên hết nhầm.
2. Quên `setLenient(false)` → `31-02-2003` được nhận thành 03-03-2003.
3. `String.format("%.1f")` không có `Locale.US` → máy lab tiếng Việt in `9,5`.
4. Kiểm xong **mới** thêm: nếu thêm trước rồi kiểm giờ, lỗi giờ sẽ để lại một task dở.

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
| 15 | 3 | | bảng tăng dần theo ID, cột Time `9.5-17.5` |
| 16 | menu | `x` / `5` | `You must input a number.` / `Please choose from 1 to 4.` |

---

## 6. Debug

| Việc | Cách làm |
|---|---|
| Breakpoint | dòng `Date date = Validation.getDate(requestDTO.getDate());` trong `TaskRepository.addTask` |
| Chạy | **Ctrl+F5**, chọn 1, gõ ngày `31-02-2003` |
| Bước | **F7** vào `getDate` → **F8** qua `format.parse(text)` → xem biến `date` trong **Variables** (ném lỗi nhờ `setLenient(false)`); thử lại với `1-2-2015`: `parse` qua được, đến `if (!format.format(date).equals(text))` thấy `"01-02-2015"` ≠ `"1-2-2015"` |
| Xem Builder | breakpoint ở `.build()` → mở `task` trong Variables thấy đủ 8 thuộc tính |
| Xem ID | watch `nextId` trước/sau `tasks.add(task)` |

---

## 7. Câu hỏi thầy hay hỏi

### OOP

| Câu hỏi | Trả lời mẫu |
|---|---|
| 4 tính chất OOP ở đâu? | **Đóng gói**: 8 field `private` trong `Task` + get/set. **Kế thừa**: mọi lớp `extends Object`, enum `TaskType` ngầm `extends Enum`; anonymous class trong `getDataTasks` `implements Comparator<Task>`. **Đa hình**: `toString()` `@Override` trong `Task`, `TaskResponseDTO`; `Collections.sort` gọi `compare` của lớp em viết qua biến kiểu `Comparator`. **Trừu tượng**: `Main` gọi `controller.addTask(dto)` không biết dữ liệu nằm trong `ArrayList`. |
| Sao `TaskType` là enum mà không phải class? | Bảng loại **cố định**, không thêm/xoá lúc chạy → enum chặn được mọi loại "lạ", so sánh bằng `==`, và Guide xếp enum vào `constants`. |

### Access modifier / static / kiểu trả về

| Câu hỏi | Trả lời mẫu |
|---|---|
| Field sao `private`? | Chỉ đổi qua setter/builder — không lớp nào sửa thẳng. |
| `toResponse` sao `private`? | Chỉ `TaskRepository` dùng. Hàm `public` là "hợp đồng" cho lớp khác gọi: `addTask/deleteTask/getDataTasks` (controller gọi), mọi `withX` (repository gọi), get/set (builder, repository, view gọi). |
| Constructor `TaskType` sao `private`? | Chỉ 4 giá trị trong enum được tồn tại; không ai `new` thêm được. |
| `static` ở đâu, vì sao? | (1) `Validation`, `FormatUtils`: không giữ dữ liệu, cùng chuỗi vào → cùng kết quả; Guide bắt *"phải dùng static method"*. (2) Hằng `Message`/`Constants`: một bản dùng chung. (3) `TaskType.fromId/firstId/lastId`: thuộc **bảng loại**, không thuộc một loại nào. (4) Hàm phụ trong `Main`: `main` là static nên chỉ gọi thẳng được hàm static; biến thì không static (Guide cấm). |
| **Bỏ `static` ở `Validation.getDate`?** | `Validation.getDate(...)` báo lỗi biên dịch. Muốn chạy: bỏ `private` constructor, `new Validation()` trong repository rồi gọi `v.getDate(...)`. |
| `addTask` trả `int` vì sao? | Đề bắt trả **id task** — controller dùng nó in `Task [1] has been added.` |
| `deleteTask` trả `void`? | Không có gì để trả; lỗi đi bằng `throw` (đề: *"Return value: Exception list"*). |
| `getTaskType` trả `TaskType` chứ không `int`? | Task giữ luôn **loại**, lấy được cả tên để in — không phải tra lại. |
| `getPlanTime` trả `double`? | Giờ có nửa: `9.5` = 9h30. |
| **Sao `ArrayList` mà không `List`?** | `List` là **interface** (hợp đồng: `add/get/remove`), `ArrayList` là **lớp cài đặt** bằng mảng động: lấy theo chỉ số nhanh, chèn/xoá giữa phải dời phần tử. Em khai báo đúng lớp em dùng; `LinkedList` cài cùng hợp đồng bằng danh sách liên kết. |
| Sao DTO toàn `String`? | Đề cho `addTask(String ..., String ...)` và bắt `addTask` tự kiểm + ném lỗi → Main chuyển nguyên chữ, việc đổi kiểu nằm trong `addTask`. |

### Kiến trúc / nghiệp vụ

| Câu hỏi | Trả lời mẫu |
|---|---|
| Sao lỗi hiện **sau** khi hỏi đủ 7 ô? | Đề: `addTask(7 chuỗi) throws Exception` — kiểm nằm **trong** `addTask`, nên hỏi đủ rồi mới kiểm; lỗi đầu tiên dừng lại, về menu. |
| Sao repository lại gọi `Validation`? | `Validation` là **hàm dùng chung** (Guide: utils chứa *"validate"*); `addTask` của đề vừa kiểm vừa thêm, và thêm là CRUD → repository. |
| ID sau khi xoá task cuối? | `nextId` chỉ tăng: thêm 1, 2, xoá 2, thêm tiếp → **3** (ID không bao giờ bị dùng lại cho task khác). |
| Pattern gì? | **Builder** (`TaskBuilder`), **Facade** (controller), **Repository**, **MVC** — mục 3.1. |

---

## 8. Thầy đổi yêu cầu tại chỗ

| Thầy bảo | Sửa | Không đụng |
|---|---|---|
| Thêm loại `5 Deploy` | 1 dòng trong `TaskType` | mọi file khác (thông báo tự thành `1 to 5`) |
| Giờ làm 7.0–18.0 | `Constants.PLAN_MIN/PLAN_MAX` + chữ trong `Message.PLAN_OUT_OF_RANGE` | `Validation` |
| Bước 15 phút | `Constants.STEPS_PER_HOUR = 4` + chữ `PLAN_NOT_HALF` | `Validation` |
| Định dạng ngày `dd/MM/yyyy` | `Constants.DATE_FORMAT` + chữ `DATE_INVALID` | mọi file khác |
| Thêm "Update task" | `Message.MENU`, `Constants` (số menu), `TaskController.updateTask`, `TaskRepository.updateTask`, `case` trong `Main` | `Task`, `TaskBuilder`, `Validation` (dùng lại) |
| ID = "ID task cuối + 1" theo **task còn lại** | `TaskRepository.addTask`: `tasks.isEmpty() ? FIRST_ID : tasks.get(tasks.size()-1).getId() + 1` | mọi file khác |

---

## 9. Chỗ khác với đề / bản cũ

| Chỗ | Đề / bản cũ | Bài này | Lý do |
|---|---|---|---|
| Chữ ký `addTask` | 7 tham số `String` | `addTask(TaskRequestDTO)` — DTO giữ 7 chuỗi | thầy: **không truyền 3 tham số 1 hàm** (V4); Guide: truyền qua DTO |
| `deleteTask(String id)` | nhận `String` | `deleteTask(TaskRequestDTO)` (dto.id là chuỗi gõ) | Guide: dữ liệu vào controller qua DTO |
| `getDataTasks()` | "list of task" | `ArrayList<TaskResponseDTO>` | view không được thấy model; khai báo kiểu cụ thể (V5) |
| Menu | ảnh đề in 4 dòng **không số** | `1. Add Task` … `4. exit` + `Please choose one option: ` | đề bảo *"select an option"*; giữ đúng bản tham chiếu đã chấm |
| Bảng | đề dùng tab, cột Time `8.0` | cột căn bằng `printf`, Time = `from-to` | tab lệch khi tên dài; giữ đúng bản tham chiếu |
| ID | "ID task cuối + 1" | bộ đếm chỉ tăng (`nextId`) | đọc "task cuối" = task được thêm gần nhất; xoá task cuối không làm ID bị dùng lại (bản tham chiếu cũng vậy). Muốn theo task còn lại: mục 8 |
| `NaN` ở From/To | bản cũ **nhận** `NaN` | từ chối (`must be between 8.0 and 17.5.`) | viết điều kiện `!(trong khoảng)` |
| Kiến trúc | `bo/ui`, Scanner trong `Validator` | MVC theo Guide, Scanner **chỉ ở main** | luật thầy |
