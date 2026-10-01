# KHUNG CHUNG LAB211 — học thuộc 1 lần, dùng cho 8 bài

Project mẫu `HE176322_KHUNG_NoteManagement`: chương trình ghi chú 4 chức năng (Add · Delete · Display · Exit).
Rút gọn từ P0071 (bỏ ngày/giờ/enum/Builder), giữ NGUYÊN khung. 11 file, đủ 8 package, chạy được.
Đã kiểm 01/10/2026: `javac --release 8` OK · chạy đủ ca đúng/sai · `soat_checklist.py` 0 vi phạm · `lint.py` sạch.

**Đây là mẫu để NHÌN.** Bài tự gõ: `BAI-LAM/HE176322_KHUNG_NoteManagement`.

## Thứ tự học (5 khúc)

| Khúc | File | Ý chính |
|---|---|---|
| K1a | `constants/Constants`, `constants/Message` | bảng dán tường — công thức 3 chữ: final · private constructor · static |
| K1b | `utils/Validation` | dụng cụ kiểm chữ gõ vào: sai thì **ném** lỗi, không in |
| K2 | `model/Note`, `dto/NoteRequestDTO`, `dto/NoteResponseDTO` | JavaBean: field private + ctor rỗng + getter/setter |
| K3 | `repository/NoteRepository` | kho: ArrayList · ID = cuối + 1 · xoá theo ID · không có thì `throw` |
| K4 | `view/NoteView`, `controller/NoteController` | controller: gọi kho → set response → display **1 lần** |
| K5 | `main/Main` | vòng menu · mỗi case gọi controller 1 lần · Main bắt lỗi và in |

Sau K5: F6 chạy → debug đi luồng 1 lần Add (F7/F8) → thi thử: gõ lại cả khung **40 phút, không nhìn**.

## Khung → 8 bài: mỗi bài đổi gì

| Bài | Giữ nguyên | Đổi / thêm |
|---|---|---|
| P0071 Task | cả khung | Note→Task; model 8 field + **Builder**; `enum TaskType`; Validation + getTaskType/getDate/getPlanTime/checkPlanOrder; `TaskDTO` (dòng bảng); `FormatUtils` |
| P0054 Contact | cả khung | Contact + **Builder**; tách first/last name; phone 7 dạng; xoá trả `boolean` → `No found contact` |
| P0073 Expense | cả khung | đọc/ghi **tệp** (`FileUtils`, `DateUtils`); có tổng ⇒ **service** |
| P0056 Worker | cả khung | **service** + Strategy tăng/giảm lương; `SalaryHistory` |
| P0057 User | cả khung | `Account`; `FileUtils` ghi nối `user.dat`; kiểm trùng username ở repository |
| P0070 Ebank | Constants/Message/Validation/DTO/View/Controller | **không có vòng menu** (đăng nhập 1 lượt); service `Ebank`; `CaptchaUtils`, `LanguageUtils` |
| P0072 Login MD5 | cả khung | `MD5Utils`; Builder; đăng nhập + đổi mật khẩu |
| P0011 Đổi hệ | cả khung | **service** + Strategy; repository giữ số đang đổi |
