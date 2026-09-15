# J1.S.P0056 — Worker Management

> Bài CRUD có **thêm nghiệp vụ** (tăng/giảm lương, lưu lịch sử) → khung giống P0055 nhưng có thêm
> tầng `service`. Đây là bài để khoe **Strategy**: tăng lương và giảm lương là hai "cách điều chỉnh"
> thay thế được cho nhau.

| | |
|---|---|
| Loại / LOC | Short Assignment · 70 LOC · 1 slot |
| Project | `HE176322_J1SP0056_WorkerManagement` |
| Chạy | NetBeans: **File ▸ Open Project** → chọn thư mục → **F6** |
| Lớp chạy | `main.Main` |
| Kiểm tự động | `python3 _tools/verify.py J1SP0056` → 6 kịch bản × 2 locale |

---

## 1. Đề bài nói gì

- Menu 5 mục: **Add Worker · Up salary · Down salary · Display Information salary · Exit**.
- Công nhân có 5 thông tin: `Code`, `Name`, `Age`, `Salary`, `work location`.
- **Add**: code không trống, không trùng; tuổi **18–50**; lương **> 0**.
- **Up / Down**: nhập code (phải tồn tại) và số tiền (**> 0**) → cộng/trừ lương **và lưu lịch sử**.
- **Display**: bảng mọi lần điều chỉnh: `Code Name Age Salary Status Date`, **sắp theo code**.

**Đề bắt buộc** (mục Guidelines):

| Thứ | Đề viết | Bài này đặt ở |
|---|---|---|
| `addWorker(Worker worker) throws Exception` | *"in startup code"* | `WorkerService.addWorker(WorkerRequestDTO)` |
| `changeSalary(SalaryStatus status, String code, double amount)` | 3 tham số | `WorkerService.changeSalary(SalaryRequestDTO)` — **giữ tên, gói 3 giá trị vào DTO** |
| `List<SalaryHistory> getInfomationSalary()` | *"sort by id"* | `WorkerService.getInfomationSalary()` — giữ **đúng chính tả của đề** (`Infomation`) |
| Enum `SalaryStatus` (UP/DOWN) | có trong chữ ký | `constants/SalaryStatus.java` (Guide: Constants chứa *"hằng số, enum"*) |

---

## 2. Kiến thức cần biết

### 2.1 Chạy tay ví dụ của đề

| Bước | Gõ | Lương W 1 | Lương W 3 | Dòng lịch sử thêm vào |
|---|---|---|---|---|
| 1 | Add `W 1`, lương 1000 | 1000 | — | — |
| 2 | Add `W 3`, lương 1400 | 1000 | 1400 | — |
| 3 | Up `W 1` +100 | **1100** | 1400 | `W 1 … 1100 UP` |
| 4 | Up `W 1` +400 | **1500** | 1400 | `W 1 … 1500 UP` |
| 5 | Down `W 3` −100 | 1500 | **1300** | `W 3 … 1300 DOWN` |

→ Bảng in ra **đúng 3 dòng của đề**. Để dòng 1 vẫn là 1100 sau khi lương thành 1500, `SalaryHistory`
phải **chép** con số lương vào chính nó (field `salary`), không đọc lại từ `Worker`.

### 2.2 Sắp xếp "ổn định" (stable)

`histories.sort(Comparator.comparing(history -> history.getWorker().getCode()))` — sắp theo code.
`ArrayList.sort` là **stable**: hai dòng cùng code **giữ thứ tự cũ** (thứ tự thời gian) → W 1 1100 luôn
đứng trước W 1 1500. Không cần sắp lần hai theo ngày.

### 2.3 Java dùng trong bài

| API | Dùng làm gì |
|---|---|
| `LocalDate.now()` | ngày hôm nay (Java 8 có sẵn `java.time`) |
| `DateTimeFormatter.ofPattern("dd/MM/yyyy")` | in ngày dạng `23/06/2015` như đề |
| `new DecimalFormat("0.##", new DecimalFormatSymbols(Locale.US))` | `FormatUtils.formatMoney`: in `1100` như đề (số tròn **không** có `.0`), `999.5` khi có lẻ; **Locale.US** để máy tiếng Việt không in `999,5` |
| `enum SalaryStatus { UP, DOWN }` | `status.name()` ra chữ `UP`/`DOWN` in ở cột Status |
| `LinkedHashMap<String, Worker>` | tra công nhân theo code 1 bước + giữ thứ tự nhập |

---

## 3. Thiết kế

```
HE176322_J1SP0056_WorkerManagement/src/
├── model/       Worker                  5 thuộc tính (JavaBean)
│                SalaryHistory           worker + lương SAU điều chỉnh + status + ngày
├── dto/         WorkerRequestDTO        5 ô của Add         (main ──► controller)
│                SalaryRequestDTO        status, code, amount (main ──► controller)
│                SalaryHistoryResponseDTO 1 dòng bảng        (controller ──► view)
├── repository/  WorkerRepository        LinkedHashMap công nhân + ArrayList lịch sử (CRUD)
├── service/     WorkerService           "Management" của đề: addWorker, changeSalary, getInfomationSalary
│                SalaryStrategy          «interface» adjust(salary, amount)
│                IncreaseSalaryStrategy  salary + amount
│                DecreaseSalaryStrategy  salary − amount
├── controller/  WorkerController        Facade: service ↔ view
├── view/        WorkerView              in bảng + câu thông báo
├── constants/   Message, Constants, SalaryStatus (enum)
├── utils/       Validation              getText, getChoice, getInt, getDouble
│                FormatUtils             formatMoney: "0.##" → 1100 / 999.5
└── main/        Main                    menu + Scanner
```

| Lớp | Làm gì | Vì sao ở đây |
|---|---|---|
| `WorkerRepository` | lưu + tìm (`isExistWorker`, `addWorker`, `findWorker`, `addHistory`, `getHistories`) | Guide: repository = **giữ dữ liệu + CRUD** |
| `WorkerService` | luật của đề (tuổi, lương > 0, cộng/trừ, sắp xếp) | Guide: *"tính toán nghiệp vụ ngoài CRUD"* → service |
| `Validation` | chỉ kiểm **dạng** (có phải số không) | luật "18–50" là nghiệp vụ, đề đặt nó sau `addWorker … throws Exception` |

**Luồng Up salary** (đúng một chiều Controller ↔ Service ↔ Repository ↔ Model):

```
Main: inputSalary(sc, UP) ──► SalaryRequestDTO ──► controller.changeSalary(dto)
   controller ──► service.changeSalary(dto)
                     ├─ repository.findWorker(code)        → null? throw "Code [..] does not exist."
                     ├─ amount <= 0?                        → throw "Amount of money must be > 0"
                     ├─ strategyMap.get(UP).adjust(...)     → IncreaseSalaryStrategy
                     ├─ newSalary <= 0?                     → throw "Salary must be greater than 0"
                     └─ worker.setSalary + repository.addHistory(new SalaryHistory(..., LocalDate.now()))
   controller ──► view.showMessage("Salary has been adjusted.")
Main: catch (Exception e) → in e.getMessage()
```

### 3.1 Design Pattern — **Strategy** (+ MVC, Facade, Repository)

| Yếu tố | Trong bài này |
|---|---|
| **Name** | Strategy (nhóm Behavioral) |
| **Problem** | Option 2 và 3 giống hệt nhau (tìm công nhân, kiểm số tiền, ghi lịch sử) — **chỉ khác phép tính**. Viết `if (status == UP) … else …` thì mỗi kiểu điều chỉnh mới (thưởng %, phạt…) lại phải mở `changeSalary` ra sửa. |
| **Solution** | `SalaryStrategy` = **Strategy** (`double adjust(double salary, double amount)`). `IncreaseSalaryStrategy`, `DecreaseSalaryStrategy` = **ConcreteStrategy**. `WorkerService` = **Context**: giữ `HashMap<SalaryStatus, SalaryStrategy> strategyMap` (điền trong constructor) và gọi `strategyMap.get(status).adjust(...)` mà không biết là cộng hay trừ. |
| **Consequences** | ✅ Thêm kiểu điều chỉnh = thêm 1 hằng enum + **1 class** + 1 dòng `put` — thân `changeSalary` đứng yên (**O**pen/Closed); cả hai kiểu dùng chung đúng một đoạn kiểm lỗi và ghi lịch sử. ❌ Thêm 3 file so với một `if/else`. |

| Pattern khác | Ở đâu | Nói gọn |
|---|---|---|
| **MVC** (JSP) | `WorkerController` ~ Servlet · `WorkerView` ~ JSP · `Worker`, `SalaryHistory` ~ JavaBean | thầy bắt |
| **Facade** | `WorkerController` | `Main` chỉ thấy 3 hàm, không biết service/repository/strategy/view |
| **Repository** | `WorkerRepository` | mọi truy cập dữ liệu ở một lớp — đổi `LinkedHashMap` → `ArrayList` chỉ sửa 1 file |

**Thầy bảo "thêm tăng lương theo %":** thêm `BONUS` vào `SalaryStatus`, tạo `PercentSalaryStrategy`
(`return salary + salary * amount / 100;`), thêm `strategyMap.put(SalaryStatus.BONUS, new PercentSalaryStrategy());`
+ mục menu. `changeSalary` **không sửa dòng nào**.

### 3.2 SOLID

| Nguyên lý | Ở đâu |
|---|---|
| **S** | `Worker` mô tả · `WorkerRepository` lưu · `WorkerService` luật · `WorkerView` in · `Validation` kiểm dạng |
| **O** | kiểu điều chỉnh mới không sửa `changeSalary` (3.1) |
| **L** | `IncreaseSalaryStrategy`/`DecreaseSalaryStrategy` thay nhau được chỗ `SalaryStrategy` |
| **I** | `SalaryStrategy` chỉ 1 hàm `adjust` |
| **D** | `changeSalary` phụ thuộc interface `SalaryStrategy`, không phụ thuộc lớp cụ thể |

---

## 4. Code từng bước

> Thầy: *"code cái **model trước rồi đến data**"*.

| Bước | File | Việc |
|---|---|---|
| 1 | `constants/SalaryStatus.java` | `enum { UP, DOWN }` (model cần nó) |
| 2 | `model/Worker.java` | 5 field `private` + ctor rỗng + ctor đủ + get/set + `toString` |
| 3 | `model/SalaryHistory.java` | `Worker worker`, `double salary`, `SalaryStatus status`, `LocalDate date` |
| 4 | `dto/` 3 lớp | `WorkerRequestDTO`, `SalaryRequestDTO`, `SalaryHistoryResponseDTO` (+ `toString` định dạng cột) |
| 5 | `repository/WorkerRepository.java` | `LinkedHashMap` + `ArrayList` + 5 hàm CRUD |
| 6 | `service/SalaryStrategy` + 2 lớp con | mỗi lớp 1 dòng `return` |
| 7 | `service/WorkerService.java` | constructor `put` 2 strategy; `addWorker`, `changeSalary`, `getInfomationSalary`, `toResponse` |
| 8 | `constants/Message.java`, `Constants.java` | prompt/lỗi **chép đúng**; `MIN_AGE=18`, `MAX_AGE=50`, định dạng |
| 9 | `view/WorkerView.java`, `controller/WorkerController.java` | `display`, `showMessage`; 3 hàm điều hướng |
| 10 | `utils/Validation.java`, `main/Main.java` | `inputWorker`/`inputSalary` trả DTO → `switch` gọi controller **1 lần** |

**Bẫy hay gặp:**

1. `SalaryHistory` đọc lương từ `Worker` → mọi dòng cũ hiện lương **hôm nay** (sai ví dụ của đề).
2. Kiểm tuổi/lương **ngay lúc nhập** → sai màn hình tham chiếu: đề đặt các luật đó trong `addWorker … throws Exception`, nên lỗi hiện **sau khi nhập đủ 5 ô**.
3. Trừ lương thành ≤ 0 mà vẫn lưu → phải tính `newSalary` và **kiểm trước** khi `setSalary`.
4. `"Enter Salary: "` (Add) **có** dấu cách cuối, `"Enter Salary:"` (Up/Down) **không** — chép đúng từng cái.

---

## 5. Test trước khi gọi thầy

| # | Chọn | Gõ | Phải thấy |
|---|---|---|---|
| 1 | menu | `9` rồi `abc` | `Please choose from 1 to 5.` rồi `You must input a number.` |
| 2 | 4 | (chưa có gì) | `No salary has been adjusted yet.` |
| 3 | 1 | `W 1`/`Nghia`/`20`/`1000`/`Hanoi` | `Worker [W 1] has been added.` |
| 4 | 1 | `W 1` lần nữa | `Code [W 1] already exists.` (sau khi nhập đủ 5 ô) |
| 5 | 1 | Code để trống | `Code cannot be null.` |
| 6 | 1 | Age `17`, `51`, rồi `60` | `Age must be in range 18 to 50` (18 và 50 được nhận) |
| 7 | 1 | Age `abc` | `You must input a number.` rồi hỏi lại Age |
| 8 | 1 | Salary `abc` / `0` | `You must input a number.` / `Salary must be greater than 0` |
| 9 | 2 | `W2` (không có) | `Code [W2] does not exist.` |
| 10 | 2 | `W 1` / `-5` hoặc `0` | `Amount of money must be > 0` |
| 11 | 2 | amount `xyz` | `You must input a number.` rồi hỏi lại |
| 12 | 3 | `W 1` / `2000` (lương còn ≤ 0) | `Salary must be greater than 0`, lương giữ nguyên |
| 13 | 2, 2, 3 | ví dụ mục 2.1 | `Salary has been adjusted.` ×3 |
| 14 | 4 | | 3 dòng như đề (`1100`, `1500`, `1300` — không `.0`), **ngày hôm nay** dạng `dd/MM/yyyy`, W 1 trước W 3 |
| 15 | 5 | | `Goodbye.` |

---

## 6. Debug

| Việc | Cách làm |
|---|---|
| Breakpoint | dòng `double newSalary = strategy.adjust(...)` trong `WorkerService.changeSalary` |
| Chạy | **Ctrl+F5**, thêm `W 1`, chọn 2 (Up) |
| Quan sát | **Variables**: `requestDTO` (status/code/amount), `worker.salary`, `strategy` — thấy kiểu thật là `IncreaseSalaryStrategy` |
| Bước | **F7** vào `adjust` → nhảy vào `IncreaseSalaryStrategy` (đa hình qua interface); làm lại với option 3 → nhảy vào `DecreaseSalaryStrategy` |
| Lỗi | nhập code sai: **F8** thấy `worker == null` → `throw` → rơi vào `catch` trong `Main` |

---

## 7. Câu hỏi thầy hay hỏi

### OOP

| Câu hỏi | Trả lời mẫu |
|---|---|
| 4 tính chất OOP ở đâu? | **Đóng gói**: field `private` trong `Worker`, `SalaryHistory`, DTO + get/set. **Kế thừa**: `IncreaseSalaryStrategy implements SalaryStrategy`; mọi lớp `extends Object`, em ghi đè `toString()`. **Đa hình**: `strategy.adjust(...)` — biến kiểu interface, chạy bản của lớp thật; `println(history)` gọi `toString()` của DTO. **Trừu tượng**: interface `SalaryStrategy` chỉ nói "điều chỉnh được"; `Main` gọi `controller.changeSalary(dto)` không biết dữ liệu nằm đâu. |
| Sao `SalaryHistory` giữ `Worker` mà lại **chép** `salary`? | Tên/tuổi không đổi → tham chiếu là đủ. Lương đổi mỗi lần điều chỉnh → phải chép, không thì dòng cũ hiện số mới. |

### Access modifier / static / kiểu trả về — **từng chỗ không-private**

| Chỗ | Vì sao |
|---|---|
| mọi **field** | `private` — chỉ đi qua get/set hoặc qua repository/service |
| get/set của model, DTO | `public` — service, view, main (qua DTO) gọi từ **package khác** |
| `WorkerRepository` 5 hàm | `public` — `WorkerService` (package `service`) gọi |
| `WorkerService` 3 hàm đề bắt | `public` — `WorkerController` gọi; `toResponse` **private** vì chỉ service dùng |
| `adjust` trong strategy | `public` — hàm của interface luôn public |
| `WorkerController` 3 hàm, `WorkerView.display/showMessage/setHistoryList` | `public` — `Main` / controller gọi |
| hàm nhập trong `Main` (`inputChoice`, `inputWorker`…) | `private static` — chỉ `main` dùng; `static` vì `main` là static (Guide: *"có thể dùng static với hàm"*) |
| `Validation.*`, `FormatUtils.formatMoney` | `public static` — Guide: utils *"phải dùng static method"*; không dùng dữ liệu đối tượng nào |
| hằng trong `Message`, `Constants` | `public static final` — một bản dùng chung, không đổi |

| Câu hỏi | Trả lời mẫu |
|---|---|
| **Bỏ `static` ở `Validation.getInt` thì sao?** | `Validation.getInt(...)` báo lỗi biên dịch. Muốn chạy: bỏ `private` constructor, tạo `Validation v = new Validation();` trong `Main` rồi gọi `v.getInt(...)`. |
| `changeSalary` trả `boolean`? | Đề ghi *"Return values: Status of adjusted"*; lỗi thì đi bằng `throw`. |
| `getInfomationSalary` trả `ArrayList<SalaryHistoryResponseDTO>`, không `List<SalaryHistory>`? | Controller **không được import model** (Guide) → trả DTO. |
| **Sao `ArrayList`/`LinkedHashMap` mà không `List`/`Map`?** | `List`, `Map` là **interface** (hợp đồng). `ArrayList` là lớp cài bằng **mảng động** (lấy theo chỉ số nhanh); `HashMap` bảng băm (tra khoá nhanh, không giữ thứ tự); `LinkedHashMap` kế thừa `HashMap` + **giữ thứ tự nhập**. Em khai báo đúng kiểu vì code dựa vào tính chất cụ thể đó (lịch sử theo thứ tự thời gian, tra theo code). |
| `SalaryStatus` sao là enum, không `boolean`? | `changeSalary(UP, …)` đọc ra nghĩa; `true` phải đoán. Enum chỉ có đúng 2 giá trị hợp lệ. |
| Sao `changeSalary` nhận DTO? | Thầy: *"không được truyền 3 tham số 1 hàm"* → gói `status, code, amount` vào `SalaryRequestDTO`. |

### Kiến trúc

| Câu hỏi | Trả lời mẫu |
|---|---|
| Sao có cả service lẫn repository? | Guide: repository = CRUD; *"Nếu có các tính toán nghiệp vụ ngoài CRUD thì cần thêm class Services"* — tăng/giảm lương, sắp xếp là nghiệp vụ. |
| Controller gọi repository không? | Không — luồng `Controller ↔ Service ↔ Repository ↔ Model` (Guide). |
| Độ phức tạp sắp xếp? | TimSort `O(n log n)`, stable. |

---

## 8. Thầy đổi yêu cầu tại chỗ

| Thầy bảo | Sửa | Không đụng |
|---|---|---|
| Thêm kiểu điều chỉnh (thưởng %) | `SalaryStatus`, 1 class strategy mới, 1 dòng `put` trong constructor `WorkerService`, `Message.MENU`, `Constants`, 1 `case` ở `Main` | `changeSalary`, repository, view |
| Sắp bảng theo tên | 1 dòng `Comparator` trong `getInfomationSalary` | mọi file khác |
| Tuổi 18–60 | `Constants.MAX_AGE` | mọi file khác |
| Hiện cả work location trong bảng | `SalaryHistoryResponseDTO` (field + format), `Constants.ROW/HEADER_FORMAT`, `Message` (nhãn), `WorkerService.toResponse`, `WorkerView` (header) | `Main`, controller, repository |

---

## 9. Chỗ khác với đề / bản cũ

| Chỗ | Đề / bản cũ | Bài này | Lý do |
|---|---|---|---|
| `changeSalary(status, code, amount)` | 3 tham số | `changeSalary(SalaryRequestDTO)` | luật V4 — giữ tên hàm |
| `addWorker(Worker worker)` | nhận model | `addWorker(WorkerRequestDTO)` | Guide: dữ liệu vào controller qua DTO |
| `List<SalaryHistory> getInfomationSalary()` | trả model | `ArrayList<SalaryHistoryResponseDTO>` | controller không được import model; V5 kiểu cụ thể |
| Lớp `Management` | tên gợi ý của đề | `WorkerService` | vai "tính toán nghiệp vụ" theo Guide |
| Lương trong bảng | đề in `1100`; bản tham chiếu in `1100.0` | `1100` (mẫu `"0.##"`: số tròn không có phần lẻ, `999.5` khi có) | **màn hình đề thắng**; vì vậy test đặt `REPLACE_REFERENCE = True` và chép lại 3 kịch bản tham chiếu với lương viết như đề |
| Menu | đề không đánh số | `1. Add Worker` … prompt `Enter your choice:` | giữ đúng bản tham chiếu |
| Cột Date | ngày cố định trong đề | **ngày hôm nay** | lịch sử ghi lúc điều chỉnh; test dùng hàm kiểm (chấp nhận hôm nay hoặc hôm qua nếu chạy qua nửa đêm) |
| Trừ lương thành ≤ 0 | đề không nói | từ chối: `Salary must be greater than 0` | cùng luật "lương > 0" của Add |
| Kiến trúc | `entity/bo/ui`, Scanner trong `Validator`, `if/else` theo dấu | MVC Guide + **Strategy** | luật thầy (V7) |
