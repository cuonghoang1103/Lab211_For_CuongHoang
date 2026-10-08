# P0071 Task Management — HỌC THUỘC (bài bạn đã PASS 150 LOC + Message rõ + 6 chỗ sửa nhỏ)

## (a) Đề tóm
Menu 4 mục: 1 Add Task (Requirement Name, Task Type 1–4 = Code/Test/Design/Review, Date dd-MM-yyyy, From, To trong
8.0–17.5 bước 0.5, From < To, Assignee, Reviewer; ID tự tăng) · 2 Delete (theo ID) · 3 Display (bảng; Time = To − From) · 4 Exit.

## (b) Cây file
| File | Làm gì |
|---|---|
| constants/Message | mọi câu chữ; câu lỗi = sai gì + phải nhập thế nào |
| constants/TaskType | enum CODE/TEST/DESIGN/REVIEW (id, name) + fromId |
| model/Task | 1 task 8 thuộc tính |
| dto/TaskRequestDTO | phiếu Main → Controller (đã kiểm) |
| dto/TaskResponseDTO | 1 dòng bảng + toString căn cột |
| repository/TaskRepository | `taskList` + `lastId`: addTask (ID = ++lastId), deleteTask (không có thì throw), getTasks (sắp theo ID) |
| service/TaskService | đổi Task → TaskResponseDTO (tên loại, tính Time) |
| controller/TaskController | addTask, deleteTask, displayTask: gọi service → view |
| view/TaskView | in tiêu đề cột + từng dòng |
| utils/Validation | getString, getPositiveInt/Double, validateDate, validatePlantime, validateTaskType, getChoice |
| main/Main | menu, Scanner, hỏi từng ô tới khi đúng |

## (c) Luồng Add
Main hỏi từng ô (sai ô nào hỏi lại ô đó) → gói `TaskRequestDTO` → `taskController.addTask` → `taskService.addTask` →
`taskRepository.addTask` tạo `Task` với ID = ++lastId, thêm vào `taskList`. Display: controller → `taskService.getTaskList()`
(đổi từng Task sang ResponseDTO, Time = To − From) → `taskView.setData` → `taskView.display()`.

## (d) Bảng import
| Lớp | Import | Câu nói |
|---|---|---|
| Main | Message, TaskController, TaskRequestDTO, Date, Scanner, Validation | Main nhập + kiểm |
| TaskController | TaskRequestDTO, TaskService, TaskView | chỉ điều hướng, không đụng model |
| TaskService | TaskType, 2 DTO, Task, TaskRepository, List | đổi model → DTO hiển thị |
| TaskRepository | Message, TaskRequestDTO, Task, ArrayList, Comparator | giữ danh sách + CRUD |
| TaskView | TaskResponseDTO, List | chỉ in |

## (e) Phím test
`3` (bảng rỗng) → `1` → Name: Enter trống (báo rỗng) rồi `Dev Program` → Type: `5` (báo 1 to 4), `abc` (báo phải là số), `1` →
Date: `31-02-2015` (ngày ảo), `26-06-2015` → From `9.7` To `17.5` (báo nửa giờ, hỏi lại cả From/To) → From `17.5` To `9.5`
(From phải nhỏ hơn To) → From `9.5` To `17.5` → Assignee `Dev` → Reviewer `Lead` → `3` (Time 8.0) → `2` `99` (không có) →
`2` `abc` (phải là số) → `2` `1` → `3` (bảng rỗng) → menu `0`, `x` (báo 1 to 4) → `4`.

## (f) Thầy hay hỏi
| Hỏi | Trả lời |
|---|---|
| Sao enum TaskType? | 4 loại cố định, không đổi lúc chạy |
| Ngày 31-02? | `setLenient(false)` không nhận ngày ảo |
| Giờ 9.7? | `(time * 2) % 1 != 0` → không phải nửa giờ → báo lỗi |
| From ≥ To? | validatePlantime báo "From must be less than To" |
| Xoá ID không có? | Repository findTaskById không thấy → throw TASK_NOT_EXIST, Main in ra |
| Cột Time? | To − From, ví dụ 17.5 − 9.5 = 8.0 |
| Sao `taskController`? | Tên biến rõ của lớp nào (thầy bắt ở bài Worker) |
