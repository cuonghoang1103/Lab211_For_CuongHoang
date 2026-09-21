# Source LAB211 — HE176322 (Fall 2026, thầy Nguyễn Văn An)

> **54 project** NetBeans, viết theo đúng kiến trúc `Guide.xlsx` của thầy + lời thầy dặn trên lớp. Mỗi project: code đã **biên dịch ở chế độ Java 8**, **chạy thật** với kịch bản gõ phím (đủ happy case + mọi thông báo lỗi), so màn hình **từng ký tự** dưới 2 locale, và qua bộ kiểm luật thầy.

> ⛔ **Đang sửa cả 54 bài theo tờ checklist giấy thầy phát 21/09/2026** — đã đạt **44/54**. Cột *Tờ 25 mục*: ✅ = đã sửa và kiểm (Java 8 + chạy đúng đề + 0 vi phạm 25 mục); ⏳ = còn kiến trúc cũ (thiếu repository, View nhận tham số…) — **đừng gõ theo bài ⏳**.

## Đọc theo thứ tự này

| # | File | Để làm gì |
|---|---|---|
| 1 | [`TO-CHECKLIST-THAY.md`](TO-CHECKLIST-THAY.md) | **Tờ checklist giấy 25 mục** — thầy review bằng đúng tờ này; chuẩn cao nhất |
| 2 | [`QUY-TAC-THAY.md`](QUY-TAC-THAY.md) | Luật của thầy — **mỗi dòng có nguồn nguyên văn** (mục 0: chỗ tờ giấy chặt hơn Guide) |
| 3 | [`CACH-REVIEW.md`](CACH-REVIEW.md) | 5 cửa review, ngân hàng câu hỏi vấn đáp, debug, danh sách kiểm trước khi giơ tay |
| 4 | `HE176322_J1SP0070_EbankLogin/` | **Mẫu chuẩn theo tờ checklist (25/25)**: Main validate, repository, View nhận ResponseDTO |
| 5 | `HE176322_J1SP0001_BubbleSort/` | Khuôn cho bài thuật toán nhỏ: repository giữ mảng, service chạy thuật toán |
| 6 | `HE176322_J1SP0054_ContactManagement/` | Khuôn cho bài quản lý: một ResponseDTO (câu kết quả + danh sách dòng) |
| 7 | `HUONG-DAN.md` trong từng project | Đề · kiến thức · thiết kế · code từng bước · test · debug · câu hỏi |

## Tải một bài về máy

Kho này là **source thật** của 54 bài, mỗi thư mục là một project NetBeans mở được ngay.

| Cách | Làm gì |
|---|---|
| Tải cả kho | nút **Code ▸ Download ZIP** ở đầu trang GitHub |
| Tải **một bài** | dán link thư mục bài đó vào [download-directory.github.io](https://download-directory.github.io/) |
| Dùng git | `git clone https://github.com/cuonghoang1103/Lab211_For_CuongHoang.git` |

Trong mỗi thư mục bài có: `src/` (code), `HUONG-DAN.md` (giải thích + câu vấn đáp), `man-hinh-chay.png` và `man-hinh-chay.txt` (**màn hình chạy thật** của kịch bản kiểm), `nbproject/` + `build.xml` (để NetBeans mở được).

> ⚠️ Tên thư mục mang mã số **HE176322**. Nộp bài của mình thì đổi thành mã số của bạn (thầy bắt đặt tên `RollNo_ExcerciseNo_Description`).

## Mở một project trong NetBeans

1. **File ▸ Open Project** → chọn thư mục `HE176322_...` (có biểu tượng cốc cà phê).
2. **F6** để chạy (lớp chạy: `main.Main`) · **Shift+F11** Clean and Build · **Ctrl+F5** Debug.
3. Vào phòng lab là **tự gõ lại** — máy lab khoá mạng, không mang được code vào. Tập gõ lại ở nhà theo mục 4 của `HUONG-DAN.md` cho tới khi không cần nhìn.

## Lộ trình đủ 750 LOC

- **P0055 không tính LOC** (bài làm quen buổi 1).
- Thầy khuyên **tránh** J1.L.P0022 (Candidate — phải đủ SOLID) và J1.L.P0023 (hoa quả — phải có ERD).
- LOC kỳ trước được **giữ lại**, nhưng **không được làm lại bài đã pass** → xem PTS trước khi chọn.
- Review tối đa **3 bài/slot**, chọn tối đa **5 bài** một lúc.

| Lộ trình | Các bài | Tổng LOC |
|---|---|---|
| **A — nhiều bài ngắn, cùng họ** (dễ quen tay) | P0001 · P0002 · P0003 · P0053 · P0010 · P0006 · P0004 · P0005 (họ sắp xếp/tìm kiếm) + P0061 · P0080 · P0081 (họ OOP kế thừa) + P0083 · P0009 · P0060 | 755 |
| **B — ít bài, bài to** | P0071 · P0072 · P0085 · P0070 · P0079 · P0011 | 830 |

## Mục lục 54 project (xếp theo LOC)

Mọi project đều là **MVC** và controller đóng vai **Facade**. Cột *Pattern thêm* chỉ ghi pattern có **cấu trúc thật trong code** (bài nhỏ ≤ 60 LOC cố ý không thêm lớp pattern).

| LOC | Mã | Đề | Độ khó | Package | File | Pattern thêm | Tờ 25 mục | Hướng dẫn |
|---|---|---|---|---|---|---|---|---|
| 21 | `J1.S.P0060` | [Calculate the total amount spent by a user through the bills](HE176322_J1SP0060_CalculateBill/HUONG-DAN.md) | Dễ | constants · controller · dto · main · model · repository · service · utils · view | 12 | — | ✅ | ✅ |
| 25 | `J1.S.P0063` | [Input and display Person Info](HE176322_J1SP0063_PersonInfo/HUONG-DAN.md) | Dễ | constants · controller · dto · main · model · repository · service · utils · view | 12 | — | ✅ | ✅ |
| 26 | `J1.S.P0062` | [Create a program to analyze file path](HE176322_J1SP0062_AnalyzeFilePath/HUONG-DAN.md) | Dễ | constants · controller · dto · main · model · repository · service · utils · view | 11 | — | ✅ | ✅ |
| 30 | `J1.S.P0064` | [Check data format](HE176322_J1SP0064_CheckDataFormat/HUONG-DAN.md) | Dễ | constants · controller · dto · main · model · repository · service · utils · view | 11 | — | ✅ | ✅ |
| 33 | `J1.S.P0069` | [Input, sort and display student information](HE176322_J1SP0069_WriteReadFile/HUONG-DAN.md) | Dễ | constants · controller · dto · main · model · repository · utils · view | 11 | — | ✅ | ✅ |
| 37 | `J1.S.P0068` | [Input, sort and display student information](HE176322_J1SP0068_StudentCollectionSort/HUONG-DAN.md) | Dễ | constants · controller · dto · main · model · repository · service · utils · view | 13 | Strategy | ✅ | ✅ |
| 39 | `J1.S.P0067` | [Analyze the user input string](HE176322_J1SP0067_AnalyzeString/HUONG-DAN.md) | Dễ | constants · controller · dto · main · model · repository · service · utils · view | 11 | — | ✅ | ✅ |
| 40 | `J1.S.P0001` | [Bubble sort algorithm](HE176322_J1SP0001_BubbleSort/HUONG-DAN.md) | Dễ | constants · controller · dto · main · model · repository · service · utils · view | 11 | — | ✅ | ✅ |
| 40 | `J1.S.P0002` | [Selection sort algorithm](HE176322_J1SP0002_SelectionSort/HUONG-DAN.md) | Dễ | constants · controller · dto · main · model · repository · service · utils · view | 11 | — | ✅ | ✅ |
| 40 | `J1.S.P0003` | [Insertion sort algorithm](HE176322_J1SP0003_InsertionSort/HUONG-DAN.md) | Dễ | constants · controller · dto · main · model · repository · service · utils · view | 11 | — | ✅ | ✅ |
| 40 | `J1.S.P0083` | [Stacks](HE176322_J1SP0083_MyStack/HUONG-DAN.md) | Dễ | constants · controller · dto · main · model · repository · utils · view | 10 | — | ✅ | ✅ |
| 42 | `J1.S.P0053` | [Sort one-dimensional array with bubble sort algorithm](HE176322_J1SP0053_BubbleSortMenu/HUONG-DAN.md) | Dễ | constants · controller · dto · main · model · repository · service · utils · view | 11 | — | ✅ | ✅ |
| 42 | `J1.S.P0061` | [Create a program to calculate perimeter and area](HE176322_J1SP0061_ShapeCalculator/HUONG-DAN.md) | Dễ | constants · controller · dto · main · model · repository · service · utils · view | 14 | — | ✅ | ✅ |
| 48 | `J1.S.P0058` | [Write program dictionary](HE176322_J1SP0058_Dictionary/HUONG-DAN.md) | Trung bình | constants · controller · dto · main · model · repository · utils · view | 11 | — | ✅ | ✅ |
| 50 | `J1.S.P0008` | [Letter and character count](HE176322_J1SP0008_LetterCharacterCount/HUONG-DAN.md) | Trung bình | constants · controller · dto · main · model · repository · service · utils · view | 11 | — | ✅ | ✅ |
| 50 | `J1.S.P0009` | [Fibonacci](HE176322_J1SP0009_Fibonacci/HUONG-DAN.md) | Trung bình | constants · controller · dto · main · model · repository · service · view | 10 | — | ✅ | ✅ |
| 50 | `J1.S.P0010` | [Linear search](HE176322_J1SP0010_LinearSearch/HUONG-DAN.md) | Trung bình | constants · controller · dto · main · model · repository · service · utils · view | 11 | — | ✅ | ✅ |
| 56 | `J1.S.P0057` | [User management system](HE176322_J1SP0057_UserManagement/HUONG-DAN.md) | Trung bình | constants · controller · dto · main · model · repository · utils · view | 11 | — | ✅ | ✅ |
| 60 | `J1.S.P0082` | [Playing cards](HE176322_J1SP0082_PlayingCards/HUONG-DAN.md) | Trung bình | constants · controller · dto · main · model · repository · service · view | 12 | — | ✅ | ✅ |
| 60 | `J1.S.P0084` | [Large number](HE176322_J1SP0084_LargeNumber/HUONG-DAN.md) | Trung bình | constants · controller · dto · main · model · repository · service · utils · view | 11 | — | ✅ | ✅ |
| 61 | `J1.S.P0051` | [Develop a computer program](HE176322_J1SP0051_CalculatorBMI/HUONG-DAN.md) | Trung bình | constants · controller · dto · main · model · repository · service · utils · view | 14 | — | ✅ | ✅ |
| 63 | `J1.S.P0066` | [Car showroom](HE176322_J1SP0066_CarShowroom/HUONG-DAN.md) | Trung bình | constants · controller · dto · exceptions · main · model · repository · service · utils · view | 17 | Strategy | ✅ | ✅ |
| 64 | `J1.S.P0054` | [Develop the Contact Management Program](HE176322_J1SP0054_ContactManagement/HUONG-DAN.md) | Trung bình | constants · controller · dto · main · model · repository · utils · view | 11 | Builder | ✅ | ✅ |
| 69 | `J1.S.P0052` | [Write a program to manage the geographic](HE176322_J1SP0052_EastAsiaCountries/HUONG-DAN.md) | Trung bình | constants · controller · dto · main · model · repository · service · utils · view | 14 | Strategy | ✅ | ✅ |
| 70 | `J1.S.P0004` | [Quick sort algorithm](HE176322_J1SP0004_QuickSort/HUONG-DAN.md) | Trung bình | constants · controller · dto · main · model · repository · service · utils · view | 13 | Strategy | ✅ | ✅ |
| 70 | `J1.S.P0005` | [Merge sort algorithm](HE176322_J1SP0005_MergeSort/HUONG-DAN.md) | Trung bình | constants · controller · dto · main · model · repository · service · utils · view | 13 | Strategy | ✅ | ✅ |
| 70 | `J1.S.P0006` | [Binary search algorithm](HE176322_J1SP0006_BinarySearch/HUONG-DAN.md) | Trung bình | constants · controller · dto · main · model · repository · service · utils · view | 13 | Strategy | ✅ | ✅ |
| 70 | `J1.S.P0007` | [Undirected graphs representation](HE176322_J1SP0007_UndirectedGraph/HUONG-DAN.md) | Trung bình | constants · controller · dto · main · model · repository · service · utils · view | 12 | — | ✅ | ✅ |
| 70 | `J1.S.P0056` | [Program to manage worker information](HE176322_J1SP0056_WorkerManagement/HUONG-DAN.md) | Trung bình | constants · controller · dto · main · model · repository · service · utils · view | 17 | Strategy | ✅ | ✅ |
| 70 | `J1.S.P0065` | [Check data format](HE176322_J1SP0065_StudentMarkStatistics/HUONG-DAN.md) | Trung bình | constants · controller · dto · main · model · repository · service · utils · view | 16 | Strategy, Builder | ✅ | ✅ |
| 72 | `J1.S.P0050` | [Solving the equation, find the square numbers, even numbers,](HE176322_J1SP0050_EquationSolver/HUONG-DAN.md) | Trung bình | constants · controller · dto · main · model · repository · service · utils · view | 14 | Template Method | ✅ | ✅ |
| 73 | `J1.S.P0055` | [Doctor management program](HE176322_J1SP0055_DoctorManagement/HUONG-DAN.md) | Trung bình | constants · controller · dto · main · model · repository · utils · view | 10 | — | ✅ | ✅ |
| 73 | `J1.S.P0059` | [The program handles files](HE176322_J1SP0059_FileProcessing/HUONG-DAN.md) | Trung bình | constants · controller · dto · main · model · repository · service · utils · view | 15 | Strategy | ✅ | ✅ |
| 90 | `J1.S.P0080` | [Shapes](HE176322_J1SP0080_Shapes/HUONG-DAN.md) | Trung bình | constants · controller · dto · main · model · repository · service · view | 20 | Factory | ✅ | ✅ |
| 90 | `J1.S.P0081` | [Bees](HE176322_J1SP0081_Bees/HUONG-DAN.md) | Trung bình | constants · controller · dto · main · model · repository · service · utils · view | 16 | Factory | ✅ | ✅ |
| 100 | `J1.S.P0011` | [Change base number system (16, 10, 2) program](HE176322_J1SP0011_ChangeBaseNumber/HUONG-DAN.md) | Khó | constants · controller · dto · main · model · repository · service · utils · view | 14 | Strategy | ✅ | ✅ |
| 100 | `J1.S.P0073` | [Program to manage expense, name Handy Expense](HE176322_J1SP0073_HandyExpense/HUONG-DAN.md) | Khó | constants · controller · dto · main · model · repository · service · utils · view | 14 | — | ✅ | ✅ |
| 100 | `J1.S.P0074` | [Write a calculator program (from DCPS’s project)](HE176322_J1SP0074_MatrixCalculator/HUONG-DAN.md) | Khó | constants · controller · dto · main · model · repository · service · utils · view | 18 | Factory | ✅ | ✅ |
| 100 | `J1.S.P0075` | [Handle file program (extraction from CBDT project)](HE176322_J1SP0075_HandleFile/HUONG-DAN.md) | Khó | constants · controller · dto · main · model · repository · service · utils · view | 14 | — | ✅ | ✅ |
| 100 | `J1.S.P0076` | [Building module csv file format](HE176322_J1SP0076_FormatCSV/HUONG-DAN.md) | Khó | constants · controller · dto · main · model · repository · service · utils · view | 16 | — | ✅ | ✅ |
| 100 | `J1.S.P0077` | [Writing module to list and search file by content (CBDT proj](HE176322_J1SP0077_SearchFileByContent/HUONG-DAN.md) | Khó | constants · controller · dto · main · model · repository · service · utils · view | 14 | — | ✅ | ✅ |
| 100 | `J1.S.P0078` | [Create a program to copy file](HE176322_J1SP0078_CopyFile/HUONG-DAN.md) | Khó | constants · controller · dto · exceptions · main · model · repository · service · utils · view | 14 | — | ✅ | ✅ |
| 130 | `J1.S.P0079` | [Create a program to zip and unzip file (project CBDT)](HE176322_J1SP0079_ZipUnzip/HUONG-DAN.md) | Khó | constants · controller · dto · main · model · service · utils · view | 14 | Template Method | ⏳ | ✅ |
| 150 | `J1.S.P0070` | [Login system of the Tien Phong Bank’s Ebank](HE176322_J1SP0070_EbankLogin/HUONG-DAN.md) | Khó | constants · controller · dto · main · model · repository · service · utils · view | 13 | — | ✅ | ✅ |
| 150 | `J1.S.P0071` | [Task management program of CCRM project](HE176322_J1SP0071_TaskManagement/HUONG-DAN.md) | Khó | constants · controller · dto · main · model · repository · utils · view | 14 | Builder | ✅ | ✅ |
| 150 | `J1.S.P0072` | [Write a login function uses MD5 encryption for passwords (se](HE176322_J1SP0072_LoginMD5/HUONG-DAN.md) | Khó | constants · controller · dto · main · model · repository · utils · view | 12 | Builder | ⏳ | ✅ |
| 150 | `J1.S.P0085` | [Employee management system](HE176322_J1SP0085_EmployeeManagement/HUONG-DAN.md) | Khó | constants · controller · dto · main · model · repository · service · utils · view | 14 | Strategy, Builder | ⏳ | ✅ |
| 200 | `J1.L.P0015` | [Asset Management- Upgrade](HE176322_J1LP0015_AssetManagementEmployee/HUONG-DAN.md) | Khó | constants · controller · dto · main · model · repository · service · utils · view | 33 | Strategy, Template Method | ⏳ | ✅ |
| 350 | `J1.L.P0021` | [Create a Java console program to manage students](HE176322_J1LP0021_StudentManagement/HUONG-DAN.md) | Khó | constants · controller · dto · main · model · repository · service · utils · view | 17 | Strategy | ⏳ | ✅ |
| 350 | `J1.L.P0022` | [Create a Java console program to manage Candidates of compan](HE176322_J1LP0022_CandidateManagement/HUONG-DAN.md) | Khó | constants · controller · dto · main · model · repository · service · utils · view | 26 | Strategy, Factory, Template Method | ⏳ | ✅ |
| 350 | `J1.L.P0023` | [Create a Java console program to manage a Fruit Shop (Produc](HE176322_J1LP0023_FruitShop/HUONG-DAN.md) | Khó | constants · controller · dto · main · model · repository · service · utils · view | 17 | — | ⏳ | ✅ |
| 450 | `J1.L.P0025` | [Create a Java console program to normalize text](HE176322_J1LP0025_NormalizeText/HUONG-DAN.md) | Khó | constants · controller · dto · main · model · repository · service · utils · view | 25 | — | ⏳ | ✅ |
| 500 | `J1.L.P0013` | [The Vehicle Management](HE176322_J1LP0013_VehicleManagement/HUONG-DAN.md) | Khó | constants · controller · dto · main · model · repository · service · utils · view | 22 | Strategy, Factory, Template Method | ⏳ | ✅ |
| 500 | `J1.L.P0014` | [Asset Management](HE176322_J1LP0014_AssetManagementManager/HUONG-DAN.md) | Khó | constants · controller · dto · main · model · repository · service · utils · view | 33 | Strategy, Template Method | ⏳ | ✅ |

Tổng LOC chuẩn của 54 project: **6034**.

## Bộ công cụ `_tools/`

| Lệnh | Làm gì |
|---|---|
| `python3 _tools/verify.py` | kiểm **mọi** project: Java 8 + chạy kịch bản + so màn hình + luật thầy |
| `python3 _tools/verify.py --netbeans J1SP0055` | kiểm 1 bài + build y như NetBeans *Clean and Build* |
| `python3 _tools/lint.py <thư mục project>` | chỉ kiểm luật thầy — chạy lên **project em tự gõ** |
| `python3 _tools/soat_checklist.py <thư mục project>` | soát **25 mục tờ checklist giấy** — 0 VI PHAM mới điền đủ "O" |
| `_tools/tests/<Mã>.py` | kịch bản gõ phím + màn hình mong đợi của từng bài |
