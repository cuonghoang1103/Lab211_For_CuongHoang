# J1.S.P0081 — Bees

> Bài OOP "đủ bộ": lớp cha `abstract Bee` giữ **mọi luật chung**, 3 lớp con chỉ khai **ngưỡng chết**.
> Hai chỗ thầy hay bắt: **`health` không có setter** (đóng gói) và **trừ % của máu HIỆN TẠI**, không
> phải của 100. Có thêm một bài học số thực (§2.2) — học kỹ, đây là chỗ ăn điểm.

| | |
|---|---|
| Loại / LOC | Short Assignment · 90 LOC · 2 slot |
| Project | `HE176322_J1SP0081_Bees` |
| Chạy | NetBeans: **File ▸ Open Project** → **F6** |
| Lớp chạy | `main.Main` |
| Kiểm tự động | `python3 _tools/verify.py J1SP0081` → 6 kịch bản × 2 locale |

---

## 1. Đề bài nói gì

- 3 loại ong: **Worker** (chết khi máu < 70%), **Queen** (< 20%), **Drone** (< 50%).
- Mỗi con có `health` kiểu số thực = **100** lúc sinh, **đọc được nhưng không ghi được từ ngoài**.
- `Damage(int percent)` (0..100): trừ `percent`% của **máu hiện tại**. Ong đã chết: máu **đứng yên**, gọi `Damage()` vẫn không lỗi.
- 1 danh sách **30 con**: 10 Worker, 10 Queen, 10 Drone.
- Menu: **1** tạo lại danh sách và in ra · **2** tấn công: mỗi con một số ngẫu nhiên **khác nhau** trong `[0, 80]`, gọi `Damage()`, in lại · **0** thoát.

Màn hình đề (bảng rút gọn):

```
============ BEE SIMULATION ============
1. Create bee list
2. Attack bees
0. Exit
========================================
Your choice: 1
New bee list created: 10 Workers, 10 Queens, 10 Drones.
No  Type    Health     Status
------------------------------
1  Worker  100.00 %   Alive
...
30  Drone   100.00 %   Alive
------------------------------
Alive: 30   Dead: 0
Your choice: 2
Attacking all bees (random damage 0-80% each)...
No  Type     Dmg   Health     Status
-------------------------------------
1  Worker    12   88.00 %    Alive
2  Worker    45   55.00 %    Dead
...
-------------------------------------
Alive: 20   Dead: 10
```

**Đề bắt buộc:**

| Thứ | Đề viết | Bài này đặt ở |
|---|---|---|
| `abstract Bee` với `health` (double, = 100), `Damage(percent : int) : void`, `getHealth() : double`, `isDead() : boolean` | hình UML của đề | `model/Bee.java` — **giữ đúng chữ `Damage` viết hoa** |
| `Worker`, `Queen`, `Drone` extends `Bee`, mỗi lớp ngưỡng riêng | Function 1 | `model/`, hàm `getThreshold()` trả 70 / 20 / 50 |
| Không có setter cho `health` | *"there must be no public setter"* | không có `setHealth` |
| 1 collection 30 con | Function 2 | `repository/BeeRepository` (`ArrayList<Bee>`) |
| Ngẫu nhiên `[0, 80]` **cho từng con** | Function 3 | `service/BeeService.attackBees` |

---

## 2. Kiến thức cần biết

### 2.1 Luật `Damage` — chạy tay ví dụ của đề

`newHealth = currentHealth × (100 − percent) / 100`, chết khi `health < ngưỡng`:

| Lệnh (Worker, ngưỡng 70) | Máu trước | Tính | Máu sau | Chết? |
|---|---|---|---|---|
| `new Worker()` | – | – | 100.00 | không |
| `Damage(20)` | 100 | 100 × 80 / 100 | 80.00 | không |
| `Damage(20)` | 80 | 80 × 80 / 100 | **64.00** (không phải 60!) | **có** — 64 < 70 |
| `Damage(50)` | 64 | đã chết → `return` | 64.00 | có (máu đứng yên) |

### 2.2 Bài học số thực — vì sao viết `health * (100 - percent) / 100.0`

Đề viết `health × (1 − percent/100)`. Về toán hai cách **bằng nhau**; trên máy **không**:

| Cách viết | Queen 100%, `Damage(80)` | In ra | So với 20 | Kết quả |
|---|---|---|---|---|
| `health * (1 - 80 / 100.0)` | `1 - 0.8 = 0.19999999999999996` → máu `19.999999999999996` | `20.00 %` | **< 20** | **Dead** ❌ (đề: ngưỡng là *dưới* 20%) |
| `health * (100 - 80) / 100.0` | `100 * 20 = 2000.0`, `2000.0 / 100.0 = 20.0` | `20.00 %` | = 20 | **Alive** ✅ |

`0.8` không biểu diễn chính xác được trong nhị phân; nhân **trước** giữ phép tính trên số nguyên thêm
một bước. Và `100.0` (không phải `100`) để phép chia là chia **số thực**. Ví dụ của đề
(100 → 80 → 64) vẫn đúng tuyệt đối.

### 2.3 Java dùng trong bài

| API | Dùng làm gì |
|---|---|
| `random.nextInt(81)` (`MAX_DAMAGE + 1`) | số nguyên trong `[0, 80]` — `nextInt(n)` cho `[0, n)` nên phải **+1** |
| `String.format(Locale.US, "%.2f %%", h)` | `88.00 %` — `%%` in dấu `%`; `Locale.US` để máy tiếng Việt không in `88,00` |
| `enum BeeType` + `values()` | duyệt 3 loại theo thứ tự Worker → Queen → Drone |

---

## 3. Thiết kế

```
HE176322_J1SP0081_Bees/src/
├── constants/  Message.java           menu, câu kết quả, nhãn cột, tên loại ong
│               Constants.java         số menu, 10 con/loại, 100, 80, ngưỡng, format cột
│               BeeType.java           enum WORKER, QUEEN, DRONE
├── model/      Bee (abstract)         health (chỉ get) · isDead · Damage · getThreshold · getType
│               Worker, Queen, Drone   ngưỡng + tên
├── dto/        BeeResponseDTO         1 dòng: no, type, damage, health, dead
│               ColonyResponseDTO      cả bảng: các dòng + aliveCount + deadCount
├── repository/ BeeRepository          giữ ArrayList<Bee>: clear · add · get · isEmpty
├── service/    BeeFactory             BeeType ──► new đúng lớp con
│               BeeService             tạo đàn, tấn công, đếm sống/chết
├── controller/ BeeController          service ──► view
├── view/       BeeView                2 bảng + dòng tổng kết
├── utils/      Validation             getChoice
└── main/       Main                   menu + Scanner
```

| Câu hỏi thiết kế | Trả lời |
|---|---|
| Sao có cả `repository` lẫn `service`? | Guide: repository *"chứa data … CRUD đơn giản"* → giữ 30 con, xoá/thêm/đọc. Tấn công ngẫu nhiên + đếm sống/chết là *"tính toán nghiệp vụ ngoài CRUD"* → service. Luồng đúng Guide: **Controller ↔ Service ↔ Repository ↔ Model**. |
| Sao `Damage` ở model mà không ở service? | Nó là **hành vi của chính con ong** và là nơi duy nhất đổi được `health` (private, không setter). Service chỉ **chọn số** rồi gọi. |
| Sao không có `RequestDTO`? | Người dùng chỉ gõ số menu, số đó quyết định **gọi hàm nào** của controller — không có dữ liệu nào cần mang vào. |

**Luồng Attack:**

```
Main: chọn 2 ──► controller.attackBees()
   controller ──► service.attackBees()
                    ├─ repository.isEmpty()? → throw "There is no bee list yet. Choose 1 first."
                    ├─ for mỗi con: damage = random.nextInt(81); bee.Damage(damage); toRow(i+1, bee) + damage
                    └─ toColony(rows): đếm sống/chết
   controller ──► view.setColony(c) ──► view.displayAttack()
Main: catch (Exception e) → in e.getMessage()
```

### 3.1 Design Pattern — **Template Method**

| Yếu tố | Trong bài này |
|---|---|
| **Name** | Template Method (nhóm Behavioral) |
| **Problem** | 3 loại ong có **cùng** luật (máu, trừ %, đứng yên khi chết, so ngưỡng) và chỉ khác **một con số**. Viết `isDead()`/`Damage()` ở cả 3 lớp = 3 chỗ để sai, 3 chỗ phải sửa. |
| **Solution** | `Bee` = **AbstractClass**: `isDead()` (`health < getThreshold()`) và `Damage()` (kiểm `isDead()` trước) là **template method**, viết 1 lần. `getThreshold()` = **primitive operation** `protected abstract`. `Worker`, `Queen`, `Drone` = **ConcreteClass**: chỉ trả 70 / 20 / 50 (và tên). |
| **Consequences** | ✅ Luật viết **1 lần**; thêm loại ong = 1 lớp con 2 hàm (**O**). ✅ Không lớp con nào đụng được `health`. ❌ Lớp con phụ thuộc chặt khung của lớp cha: đổi luật chung là đổi cho cả 3. |

### 3.2 Design Pattern — **Factory** (Simple Factory)

| Yếu tố | Trong bài này |
|---|---|
| **Name** | Factory — dạng **Simple Factory**; GoF gọi biến thể dùng lớp con ghi đè hàm tạo là *Factory Method* |
| **Problem** | Tạo 10 con **mỗi loại** — cần một chỗ biết "loại nào → `new` lớp nào", để service không phải `new Worker()`, `new Queen()` rải rác. |
| **Solution** | `BeeType` = khoá loại. `BeeFactory.createBee(BeeType)` = **Creator** (`switch` → `new`), trả kiểu trừu tượng `Bee` = **Product**; `Worker/Queen/Drone` = **ConcreteProduct**. `BeeService.createBees` = **Client**: 2 vòng `for` lồng (`BeeType.values()` × 10). |
| **Consequences** | ✅ Thêm loại `Guard` = 1 hằng enum + 1 lớp + 1 `case`; service, controller, view **không đổi**. ❌ Thêm 2 file so với `new` thẳng. |

Controller còn là **Facade**: `Main` chỉ biết `createBees()` và `attackBees()`.

### 3.3 SOLID trong bài

| Nguyên lý | Ở đâu |
|---|---|
| **S** | `Bee` giữ luật máu · `BeeRepository` giữ đàn · `BeeService` tấn công/đếm · `BeeFactory` tạo · `BeeView` in |
| **O** | thêm loại ong không sửa `Bee`, `BeeService`, view |
| **L** | `Worker/Queen/Drone` thay được `Bee` ở mọi chỗ; không lớp con nào đổi nghĩa `Damage` |
| **D** | `BeeService` làm việc với `Bee` (trừu tượng); chỉ factory biết lớp cụ thể |
| **I** | không có interface — không áp dụng |

---

## 4. Code từng bước

> Thầy: *"code cái **model trước rồi đến data**"*.

| Bước | File | Việc |
|---|---|---|
| 1 | `model/Bee.java` | field `private double health`; constructor `protected` đặt 100; `getHealth` (**không** set); `isDead`; **`Damage`**; `protected abstract getThreshold`; `abstract getType`; `toString` |
| 2 | `model/Worker.java`, `Queen.java`, `Drone.java` | constructor rỗng `public` + 2 hàm override |
| 3 | `dto/BeeResponseDTO.java`, `ColonyResponseDTO.java` | JavaBean |
| 4 | `repository/BeeRepository.java` | `clearBees` · `addBee` · `getBees` (bản sao list) · `isEmpty` |
| 5 | `constants/BeeType.java` | enum 3 hằng, đúng thứ tự Worker → Queen → Drone |
| 6 | `service/BeeFactory.java` | `createBee(type)` |
| 7 | `service/BeeService.java` | `createBees` · `attackBees` · `toRow` · `toColony` (2 cái sau `private`) |
| 8 | `view/BeeView.java` | `setColony` · `displayNewColony` · `displayAttack` + 3 hàm phụ `private` |
| 9 | `controller/BeeController.java` | 2 hàm |
| 10 | `constants/Message.java`, `Constants.java` | chữ, ngưỡng, format cột **đo từ đề** |
| 11 | `utils/Validation.java` | `getChoice` (tách 2 lỗi) |
| 12 | `main/Main.java` | menu `while` + `switch` + `inputChoice` |

**Bẫy hay gặp:**

1. Trừ % của **100** (`health -= percent`) → Worker 20 rồi 20 ra 60 thay vì 64.
2. Kiểm "đã chết" **sau** khi trừ → ong chết vẫn bị trừ tiếp.
3. `random.nextInt(80)` → không bao giờ ra 80; phải `nextInt(80 + 1)`.
4. Random **một lần** cho cả đàn → mọi con cùng loại cùng máu — sai đề (*"a different random value … for each bee"*).
5. `health * (1 - percent / 100.0)` → Queen trúng 80 thành "chết ở 20.00 %" (§2.2).
6. `percent / 100` (chia nguyên) → luôn ra 0.

---

## 5. Test trước khi gọi thầy

| # | Gõ | Phải thấy |
|---|---|---|
| 1 | `x`, *(Enter trống)* | `You must input a number.` |
| 2 | `3`, `-1` | `Please choose from 0 to 2.` |
| 3 | `2` khi chưa tạo | `There is no bee list yet. Choose 1 first.` |
| 4 | `1` | `New bee list created: 10 Workers, 10 Queens, 10 Drones.` + 30 dòng `100.00 %   Alive` + `Alive: 30   Dead: 0` |
| 5 | `2` | 30 dòng, cột `Dmg` 0..80 khác nhau, `Health` = 100 − Dmg, Worker < 70 là `Dead`, Queen < 20, Drone < 50 |
| 6 | `2` thêm vài lần | con còn sống: máu nhân tiếp; con **đã chết: máu đứng yên** dù cột `Dmg` vẫn có số |
| 7 | `1` sau khi tấn công | cả đàn quay về `100.00 %`, `Alive: 30   Dead: 0` |
| 8 | Queen trúng đúng `80` (nếu gặp) | `20.00 %   Alive` |
| 9 | `0` | `Goodbye.` |

`verify.py` kiểm #5–#7 bằng cách **tính lại** từng con từ cột `Dmg` (máu thật bằng số thực của
Python, cùng chuẩn IEEE với Java), so trạng thái **chính xác**.

---

## 6. Debug

| Việc | Cách làm |
|---|---|
| Breakpoint | dòng `if (isDead()) {` trong `Bee.Damage` |
| Chạy | **Ctrl+F5**, chọn `1` rồi `2` |
| Bước | từ `BeeService.attackBees` bấm **F7** vào `bee.Damage(damage)`; trong `Damage` bấm **F7** vào `isDead()` rồi **F7** vào `getThreshold()` → nhảy sang **`Worker.getThreshold`** (vòng 11 sẽ nhảy sang `Queen`) = Template Method + đa hình |
| Quan sát | tab **Variables**: `this` (kiểu thật `Worker`), `health` trước/sau dòng gán, `percent` |
| Ong chết | chọn `2` nhiều lần; khi `isDead()` là `true`, F8 thấy nhảy thẳng tới `return`, `health` không đổi |

---

## 7. Câu hỏi thầy hay hỏi

### OOP

| Câu hỏi | Trả lời mẫu |
|---|---|
| 4 tính chất OOP ở đâu? | **Đóng gói**: `health` `private`, chỉ `getHealth()`, **không setter** — chỉ đổi qua `Damage()`. **Kế thừa**: `Worker/Queen/Drone extends Bee`. **Đa hình**: `bee.Damage()`/`bee.isDead()` trong vòng lặp của `BeeService` — `getThreshold()` chạy bản của lớp thật. **Trừu tượng**: `abstract class Bee`, `abstract getThreshold()`, `abstract getType()`. |
| Sao không có `setHealth`? | Đề: *"not writable externally … no public setter"*. Có setter thì ai cũng hồi sinh được ong chết, luật "đứng yên khi chết" vô nghĩa. Đây là chỗ em **cố ý** lệch khỏi JavaBean — đề thắng. |
| Sao `Damage` viết hoa, trái convention? | Tên **đề bắt** (*"Damage() method"*, UML của đề). Em giữ đúng tên; ở dự án thật sẽ là `damage`. |
| Constructor `Bee()` sao `protected`? | `Bee` là abstract, không ai `new Bee()` được; chỉ constructor lớp con gọi `super()`. `protected` nói đúng điều đó. |
| `getThreshold()` sao `protected` mà `getType()` `public`? | `getThreshold` chỉ `isDead()` (trong `Bee`) và lớp con cần. `getType` thì `BeeService` (lớp khác) gọi để điền cột Type. |
| Ong chết gọi `Damage(90)` có lỗi không? | Không — đề bắt *"must still be invokable without error"*: `if (isDead()) return;` đứng **trước** mọi kiểm tra. |
| `Damage(150)` trên ong sống? | Ném `IllegalArgumentException("Damage percent must be between 0 and 100.")` — lỗi lập trình, menu không bao giờ tạo ra (chỉ random 0..80). |

### Access modifier, static, kiểu trả về

| Thành phần | Vì sao |
|---|---|
| mọi field `private` | đóng gói |
| `getHealth/isDead/Damage/getType` `public` | `BeeService` gọi |
| constructor rỗng `public` ở `Worker/Queen/Drone` và 2 DTO | JavaBean (MVC JSP); factory gọi |
| `BeeRepository.clearBees/addBee/getBees/isEmpty` `public` | `BeeService` gọi |
| `BeeService.toRow/toColony`, `BeeView.displaySummary/formatHealth/formatStatus` `private` | chỉ dùng trong chính lớp đó |
| `Main.inputChoice` `private static` | chỉ `main` gọi; Guide cho phép static **hàm** ở main |
| `Validation.getChoice` `public static` | Guide: utils *"phải dùng static method"*. **Bỏ static** → phải bỏ constructor `private` và `new Validation()` trong `Main` |
| `getHealth` trả `double` | trừ % để lại số lẻ (88 × 0.55 = 48.4) |
| `isDead` trả `boolean` | câu hỏi có/không |
| `Damage` trả `void` | nó **đổi** con ong; không có gì cần trả |
| `getThreshold` trả `int` | đề cho ngưỡng nguyên 70/20/50 |
| `getBees()` trả **bản sao** list | service được đánh (cùng đối tượng ong) nhưng không thêm/xoá lén khỏi đàn |

### ArrayList hay List?

| Câu hỏi | Trả lời mẫu |
|---|---|
| Sao `ArrayList<Bee>` mà không `List<Bee>`? | `List` là interface (hợp đồng), `ArrayList` là lớp cài đặt bằng mảng động — lấy theo vị trí nhanh, thêm cuối nhanh, đúng cách em dùng (số thứ tự = vị trí). Đề cho *"a list or array"*, em chọn và khai rõ kiểu em dùng. |
| Khác nhau khi chạy? | Không. Khác ở chỗ khai `List` thì đổi sang `LinkedList` chỉ sửa vế phải; khai `ArrayList` thì dùng được hàm riêng của nó. |
| Sao lưu `Bee` chứ không `Worker`? | Một danh sách chứa **cả 3 loại** — chỉ kiểu cha chung mới chứa được, và vòng lặp nhờ đa hình không cần biết con nào loại nào. |

---

## 8. Thầy đổi yêu cầu tại chỗ

| Thầy bảo | Sửa file | Không đụng |
|---|---|---|
| Thêm loại **Guard** (chết < 40%) | `model/Guard` (mới), `BeeType` (+1), `BeeFactory` (+1 case), `Constants.GUARD_THRESHOLD`, `Message.TYPE_GUARD` (+ câu `CREATED`) | `Bee`, `BeeService`, controller, view, main |
| Đổi ngưỡng Queen thành 30% | chỉ `Constants.QUEEN_THRESHOLD` | mọi file khác |
| 20 con mỗi loại | `Constants.BEES_PER_TYPE` | mọi file khác |
| Random 0..100 | `Constants.MAX_DAMAGE` (câu `Attacking…` tự đổi theo) | mọi file khác |
| Thêm menu **3. Heal** (+10 máu cho ong sống) | `Bee.heal()`, `BeeService.healBees`, `BeeController.healBees`, `BeeView`, `Message.MENU`, `Constants` (menu), `case` ở `Main` | 3 lớp con, repository |

---

## 9. Chỗ khác với đề / bản cũ

| Chỗ | Đề / bản cũ | Bài này | Lý do |
|---|---|---|---|
| Công thức | đề: `health × (1 − percent/100)` | `health * (100 - percent) / 100.0` | cùng toán học, nhưng tránh lỗi làm tròn làm Queen "chết ở 20.00 %" (§2.2) — giữ nguyên bài học của bản cũ |
| Độ rộng cột | bản cũ `%-3s %-7s %10s` | đo từ mẫu đề: `1  Worker  100.00 %   Alive` | **màn hình đề** thắng (kịch bản bản cũ chỉ kiểm bằng biểu thức nên vẫn chạy chung) |
| Dòng trống giữa các mục menu | mẫu đề có (do trình bày tài liệu) | không in | đề không nói; bản cũ cũng không in |
| Câu lỗi `You must input a number.` · `Please choose from 0 to 2.` · `There is no bee list yet. Choose 1 first.` · `Goodbye.` | đề không cho chữ | giữ chữ bản cũ | đề im lặng |
| `Damage` ngoài 0..100 | bản cũ ném lỗi, chữ viết cứng | ném `IllegalArgumentException` với câu trong `Message` | không viết chuỗi cứng ngoài constants; không có đường nào từ menu tới đây |
| Tạo ong | bản cũ `new Worker()` trong vòng lặp | `BeeFactory` theo `BeeType` | Design Pattern (QUY-TAC-THAY §9 V7) |
| `health` không setter, `Bee` không constructor rỗng `public` | JavaBean đòi get/set | đề cấm setter; `Bee` là abstract | đề thắng; lớp abstract không tạo được nên luật JavaBean không áp |
| Kiến trúc | `entity/bo/ui`, Scanner trong `Validator` | MVC theo Guide, Scanner **chỉ ở `main`** | luật thầy |
