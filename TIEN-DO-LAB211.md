# Tiến độ LAB211 — HE176322 (Fall 2026)

> AI đọc file này ĐẦU MỖI BUỔI, cập nhật CUỐI MỖI BUỔI (skill `lab211-kem-hoc`).

## Trạng thái hiện tại (cập nhật 01/10/2026)

- **LOC đã pass:** 0 / 750 · đang ở tuần 4/10 (2 slot/tuần)
- **Bài đang làm:** J1.S.P0071 Task Management (150 LOC) — project `BAI-LAM/HE176322_J1SP0071_TaskManagement`
- **Đã dạy (người học CHƯA báo xong):**
  - Bước 1: tạo project + 8 package (constants, model, dto, repository, controller, view, utils, main — KHÔNG service)
  - Bước 2: `constants/TaskType.java` (enum 4 loại + findById, getFirstId, getLastId)
  - KHUNG khúc 1/3: `Constants` (MENU_MIN, MENU_ADD/DELETE/DISPLAY/EXIT), `Message` (MENU, INPUT_CHOICE,
    INVALID_NUMBER, INVALID_RANGE, GOODBYE), `Validation` (getText, getChoice)
  - Đính chính khúc 1: thêm dòng trống trước `return input.trim();` và `return choice;` (mục 2.8)
  - Đã giảng: package repository làm gì (kho: giữ ArrayList + CRUD + báo "không tồn tại"; kiểm định dạng ở Main,
    kiểm tồn tại ở Repository)
- **01/10 (lượt 2) — ĐỔI HƯỚNG theo ý người học: học KHUNG CHUNG trước, rồi mới phần riêng từng bài.** Mẫu: `KHUNG-CHUNG/HE176322_KHUNG_NoteManagement` (11 file, 0 vi phạm). Người học gõ vào `BAI-LAM/HE176322_KHUNG_NoteManagement`. Lộ trình K1a→K1b→K2→K3→K4→K5 (xem `KHUNG-CHUNG/README.md`) → thi thử khung 40 phút → P0071. Đang ở **K1a**, chờ "xong K1a" + 3 câu.
- (thay bởi dòng trên) **01/10 làm lại từ đầu, khúc 1 tách đôi:** 1a = Constants + Message (khung) · 1b = Validation (getText, getChoice). Đang chờ "xong khúc 1a" + 3 câu (final/private ctor/static là gì; vì sao câu chữ để ở Message; `%d` trong INVALID_RANGE để làm gì).
- **(Cũ, chưa dùng) Đang chờ người học:** "xong khúc 1" + trả lời 5 câu:
  1. Công thức 3 chữ của 3 class tiện ích, mỗi chữ để làm gì?
  2. Gõ `abc` ở menu → chuyện gì xảy ra từng bước trong getChoice?
  3. Gõ `9` → câu thông báo in ra chính xác là gì?
  4. Vì sao Validation ném lỗi mà không in lỗi?
  5. P0057: username `ab` (quá ngắn) và `admin1` (đã có) — mỗi lỗi do ai kiểm?

## Việc tiếp theo (theo thứ tự)

1. Chấm 5 câu trên → KHUNG khúc 2: `TaskResponseDTO` + `TaskView` + `TaskController` (render 1 lần/luồng)
2. KHUNG khúc 3: `Main` (vòng menu, mỗi case gọi controller 1 lần, Main bắt lỗi + in)
3. Phần riêng P0071: `Task` + `TaskBuilder` (pattern Builder, 4 ý) → `TaskRequestDTO`, `TaskDTO` → phần còn lại
   của Constants/Message → `Validation` (getRequired, getTaskType, getDate, getPlanTime, checkPlanOrder, getId)
   + `FormatUtils` → `TaskRepository` → chạy hết bảng test (HUONG-DAN §5)
4. Thi thử 70 phút tắt tài liệu → review như thầy (soat_checklist.py + vấn đáp 5 câu)
5. Mang lên lớp: slot 1 code + save draft, slot 2 xong + review

## Kế hoạch 8 bài (đã chốt 27/09)

T4 P0071 (150) · T5 P0054 (64), P0073 (100) · T6 P0057 (56), bắt đầu P0070 (150) · T7 xong P0070, bắt đầu
P0072 (150) · T8 xong P0072, P0011 (100) → 770 ✅ · T9 P0056 (70, dự phòng) + sửa reject · T10 đệm.

## Câu cần hỏi thầy

- Có được tải bài đã pass/đã nộp trên PTS về để dùng lại khung (Validation, Main, View) cho bài sau không?
- P0071: addTask nhận DTO đã kiểm ở Main (theo checklist) thay vì 7 chuỗi như đề — được không?
- P0071: cột Time in số giờ (To − From, ví dụ 8.0) đúng ý thầy không?

## Lỗi hay mắc / cần ôn

- (chưa có)

## Nhật ký buổi

| Ngày | Bài | Làm được | Ghi chú |
|---|---|---|---|
| 27/09 | P0071 | Kế hoạch 8 bài, luật 25 mục để chép vở, khung khúc 1 | chưa gõ |
| 01/10 | — | Tạo skill `lab211-kem-hoc` + file tiến độ này | |
| 01/10 | P0071 | **Học lại từ đầu** (người học: "chưa biết gì"): phương pháp 4 tầng Nhớ→Hiểu→Luồng→Soát; chạy thử bài mẫu; bước 1 tạo project + 8 package; khúc 1a = Constants + Message (phần khung) | chờ "xong khúc 1a" + 3 câu |
| 01/10 | KHUNG | Dựng project KHUNG CHUNG (Note, 11 file) + bản đồ khung→8 bài; bắt đầu K1a | chờ "xong K1a" |
