# P0056 Worker Management — HỌC THUỘC (bài bạn đã PASS 120 LOC, đã đổi tên biến theo thầy)

## (a) Đề tóm
Menu 5 mục: 1 Add Worker (Code, Name, Age 18–50, Salary > 0, Location; Code không trùng) · 2 Up salary · 3 Down salary
(nhập Code + số tiền; Code phải có; giảm không quá lương hiện tại) · 4 Display Information salary (bảng lịch sử:
ID, Name, Age, Salary, Status UP/DOWN, Date) · 5 Exit.

## (b) Cây file
| File | Làm gì |
|---|---|
| constants/Message | mọi câu chữ: menu, câu nhắc, tiêu đề, lỗi |
| constants/SalaryStatus | enum UP / DOWN |
| model/Worker | 1 công nhân + danh sách lịch sử lương; tạo mới thì ghi luôn 1 dòng UP lương khởi điểm |
| model/SalaryHistory | 1 lần đổi lương: lương sau đổi, trạng thái, ngày |
| dto/WorkerRequestDTO | phiếu Main → Controller (5 ô nhập) |
| dto/SalaryHistoryResponseDTO | 1 dòng bảng hiển thị + toString căn cột |
| repository/WorkerRepository | kho `workerMap` (HashMap): save, findById, exists, isEmpty, findAll |
| service/WorkerService | NGHIỆP VỤ: tăng/giảm lương + ghi lịch sử; gom bảng lịch sử |
| controller/WorkerController | điều hướng: kiểm trùng/rỗng → gọi service → đưa view |
| view/WorkerView | in tiêu đề cột + từng dòng |
| utils/Validation | getString, getAge, getPositiveDouble, getChoice — sai thì throw |
| main/Main | menu, Scanner, prompt… lặp lại tới khi nhập đúng |

## (c) Luồng (nói với thầy)
- **Add**: Main hỏi 5 ô (mỗi ô sai thì hỏi lại ô đó) → gói `WorkerRequestDTO` → `workerController.addWorker` → kiểm Code trùng
  (trùng thì throw) → `workerService.addWorker` → `workerRepository.save` tạo `Worker` cất vào `workerMap`.
- **Up/Down**: Main hỏi Code + tiền → controller kiểm kho rỗng → service tìm Worker (không có thì throw) → (Down: tiền > lương
  thì throw) → đổi lương + thêm 1 `SalaryHistory`.
- **Display**: controller lấy danh sách từ service (service duyệt từng worker, từng lịch sử → `SalaryHistoryResponseDTO`)
  → `workerView.setData` → `workerView.display()`.
- Lỗi nào cũng `throw new Exception(Message.X)`, Main `catch` in `e.getMessage()` rồi quay lại menu.

## (d) Bảng import — thầy nhìn chỗ này
| Lớp | Import | Câu nói |
|---|---|---|
| Main | Message, WorkerController, WorkerRequestDTO, Scanner, Validation | Main nhập + kiểm, gói DTO đưa controller |
| WorkerController | Message, 2 DTO, WorkerService, WorkerView, List | chỉ điều hướng, KHÔNG import model |
| WorkerService | Message, SalaryStatus, DTO, Worker, SalaryHistory, WorkerRepository… | nghiệp vụ lương, được đụng model |
| WorkerRepository | WorkerRequestDTO, Worker, HashMap/Map/List/ArrayList | giữ dữ liệu + CRUD |
| WorkerView | SalaryHistoryResponseDTO, List | chỉ in, không biết model |
| Validation | Message | kiểm chuỗi, static |

## (e) Phím test cho thầy xem (theo thứ tự)
`4` (bảng rỗng) → `2` W1 100 (Database is empty) → `1` W1 · An · `17` (tuổi sai) · 25 · `-5` (lương sai) · 1000 · Hanoi →
`1` W1 … (Code is duplicated!) → `2` W9 100 (Worker does not exist!) → `2` W1 500 → `3` W1 5000 (giảm quá lương) →
`3` W1 300 → `4` (bảng: UP 1000, UP 1500, DOWN 1200) → `6` (ngoài 1–5) → `x` (Invalid number!) → `5`.

## (f) Thầy hay hỏi
| Hỏi | Trả lời |
|---|---|
| Sao có service? | Bài có tính toán (tăng/giảm lương, ghi lịch sử) — ngoài CRUD thì phải có service (Controller ↔ Service ↔ Repository ↔ Model) |
| Controller có đụng model không? | Không — nhìn import: chỉ DTO, Service, View, Message |
| Sao `workerService` không phải `service`? | Tên biến phải rõ của lớp nào (thầy đã bắt, sửa bằng Refactor → Rename, Ctrl+R) |
| Lưu worker bằng gì? | `HashMap<String, Worker> workerMap`, khoá là Code → tìm/kiểm trùng nhanh |
| Giảm quá lương? | Service so `amount > currentSalary` → throw INSUFFICIENT_SALARY |
| Tuổi `abc`? | `Integer.parseInt` ném NumberFormatException → catch → INVALID_NUMBER |
| Lịch sử ở đâu? | Mỗi Worker giữ `List<SalaryHistory> history`; mỗi lần đổi lương thêm 1 dòng |
| UP/DOWN là gì? | enum `SalaryStatus` trong constants — giá trị cố định |
