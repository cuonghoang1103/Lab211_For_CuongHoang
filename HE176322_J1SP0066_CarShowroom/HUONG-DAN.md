# J1.S.P0066 — Car Showroom

> Bài **enum + ngoại lệ tự tạo**. Ba chỗ thầy hay bắt: **`CarException extends Exception`** (kế
> thừa — đề gọi là `ExceptionCar`, tờ checklist 1.3 bắt tên class exception **kết thúc** bằng
> `Exception`), vì sao **"no color" là một hằng enum** chứ không phải `null`, và **hàm `checkCar` 4
> tham số** của đề — em giữ tên, gói dữ liệu vào **DTO** (thầy cấm 3 tham số).

| | |
|---|---|
| Loại / LOC | Short Assignment · 63 LOC · 1 slot |
| Project | `HE176322_J1SP0066_CarShowroom` |
| Chạy | NetBeans: **File ▸ Open Project** → **F6** |
| Lớp chạy | `main.Main` |
| Kiểm tự động | `python3 _tools/verify.py J1SP0066` → 6 kịch bản × 2 locale |

---

## 1. Đề bài nói gì

| Xe | Màu (cùng vị trí với giá) | Giá $ | Bán vào |
|---|---|---|---|
| AUDI | WHITE, YELLOW, ORANGE | 5500, 3000, 4500 | FRIDAY, SUNDAY, MONDAY |
| MERCEDES | GREEN, BLUE, PURPLE | 5000, 6000, 8500 | TUESDAY, SATURDAY, WEDNESDAY |
| BMW | PINK, RED, BROWN | 2500, 3000, 3500 | MONDAY, SUNDAY, THURSDAY |

- Nhập **Name, Color, Price, Today** → kiểm yêu cầu có khớp xe đang bán không → `Sell Car` hoặc
  `Can’t sell Car` + lý do → hỏi `Do you want find more?(Y/N):`.
- Xe **không sơn** ("no color") được **giảm $100**; khách chọn thêm option thì **cộng thêm** vào giá.

**Đề bắt buộc:**

| Thứ | Đề viết | Bài này đặt ở |
|---|---|---|
| `checkCar` | `public Car checkCar(Car car, String color, Day day, String price) throws ExceptionCar` | `service/ShowroomService.checkCar(CarRequestDTO)` — **giữ tên + kiểu trả `Car`**; ném `CarException` (= `ExceptionCar` của đề) |
| `getPrices`, `getColors`, `getDaySells` | *"return the List of sale information"* | `constants/Car` (enum) — trả `ArrayList` (bản sao của field `colorList`, `priceList`, `daySellList`) |
| `Car getCar(String)`, `Day getDay(String)`, `Color getColor(String)` | trả `null` khi không khớp | hàm `static` trong từng enum |
| Lớp `ExceptionCar` | *"inherits Exception class; pass the message content to the constructor"* | `exceptions/CarException.java` — dòng ngay trên khai báo có `// brief: ExceptionCar` (đổi tên vì checklist 1.3, xem mục 9) |
| 6 thông báo | `Car break` · `Color Car does not exist` · `Price greater than zero` · `Price is digit` · `Car can't sell today` · `Can’t sell Car` | `constants/Message` — **chép đúng từng chữ** |

---

## 2. Kiến thức cần biết

### 2.1 Luật kiểm — đúng thứ tự màn hình của đề

| Bước | Kiểm | Sai thì ném |
|---|---|---|
| 1 | tên là một `Car`? (`getCar` ≠ `null`) | `Car break` |
| 2 | màu là một `Color` **và** xe có màu đó (hoặc `NO_COLOR`) | `Color Car does not exist` |
| 3 | giá là số? | `Price is digit` |
| 4 | giá > 0? | `Price greater than zero` |
| 5 | giá ≥ giá bán (strategy) | `Price is not enough` |
| 6 | ngày là một `Day` **và** xe bán ngày đó | `Car can't sell today` |

Yêu cầu sai **nhiều chỗ** cùng lúc chỉ in **lý do đầu tiên** — nên thứ tự quan trọng.

### 2.2 Giá bán — chạy tay ví dụ của đề

| Nhập | Giá bán tính ra | So sánh | Kết quả |
|---|---|---|---|
| BMW · no color · 2400 · THURSDAY | min(2500, 3000, 3500) − 100 = **2400** | 2400 ≥ 2400 | `Sell Car` |
| BMV · … | — | bước 1 | `Car break` |
| BMW · color · … | `getColor("color")` = `null` | bước 2 | `Color Car does not exist` |
| BMW · no color · -200 | — | bước 4 | `Price greater than zero` |
| BMW · no color · a | — | bước 3 | `Price is digit` |
| BMW · no color · 2400 · FRIDAY | 2400 | bước 6: BMW không bán thứ Sáu | `Car can't sell today` |
| BMW · BROWN · 3500 · SUNDAY | giá ở **cùng vị trí** với BROWN = 3500 | 3500 ≥ 3500 | `Sell Car` |

→ Ví dụ đầu của đề **chứng minh** "giảm $100" là trừ vào **giá rẻ nhất** (2500 − 100 = 2400).

### 2.3 Java dùng trong bài

| API | Dùng làm gì |
|---|---|
| `enum` có field + constructor | mỗi xe mang sẵn 3 danh sách của nó |
| `values()`, `name()` | duyệt mọi hằng enum để tìm theo chữ (không dùng `valueOf` vì nó **ném lỗi** thay vì trả `null`) |
| `equalsIgnoreCase` | gõ `bmw`, `no COLOR` vẫn khớp |
| `ArrayList.indexOf` / `get` | tìm vị trí màu → lấy giá cùng vị trí |
| `Collections.min` | giá rẻ nhất của xe |
| `String.matches(regex)` | giá phải là **số thật** — `Double.parseDouble` còn nhận cả `NaN`, `1e4`, `5d` |
| `class X extends Exception` | ngoại lệ tự tạo, **checked** — compiler buộc `main` phải bắt |

---

## 3. Thiết kế

```
HE176322_J1SP0066_CarShowroom/src/
├── constants/  Car, Color, Day       3 enum của đề (+ getCar/getColor/getDay static)
│               Message, Constants    câu chữ · $100, regex giá, Y/N, ORDER_FORMAT
├── exceptions/ CarException          extends Exception (đề: ExceptionCar — dòng trên có // brief:)
├── model/      CarOrder              yêu cầu của khách đã đọc đúng: car, color, day, price
├── repository/ CarOrderRepository    orderList: các đơn đã bán + addOrder (CRUD đơn giản)
├── dto/        CarRequestDTO         car, color, day (enum/null) + price (chữ) — main ──► controller
│               CarResponseDTO        message ("Sell Car") — controller ──► view
├── service/    IPriceStrategy        «interface» giá bán thấp nhất
│               ShowroomPriceStrategy luật giá của đề (màu → giá; no color → rẻ nhất − 100)
│               ShowroomService       checkCar (Context) + ghi đơn bán được vào repository
├── controller/ ShowroomController    cắm strategy; service ──► ResponseDTO ──► view (1 lần)
├── view/       ShowroomView          field responseDTO + setResponseDTO + display() in "Sell Car"
├── utils/      Validation            checkPrice, checkYesNo
└── main/       Main                  final + private Main(); Scanner, vòng Y/N, in 2 dòng từ chối
```

| Câu hỏi thiết kế | Trả lời |
|---|---|
| Sao enum nằm ở `constants`? | Guide: Constants *"chứa hằng số, **enum**"*. Kho xe của showroom **cố định** → là hằng số. |
| Sao bài có `repository`? | Tờ checklist 1.1: *"Bắt buộc phải có repository"* — repository chỉ chứa data + CRUD đơn giản. Data của bài: **các đơn đã bán** (`orderList` trong `CarOrderRepository`, hàm `addOrder`). Kiểm yêu cầu là **nghiệp vụ** → `ShowroomService`; bán được thì service mới gọi `addOrder`. Kho xe (màu, giá, ngày) là **hằng số của đề** nên nằm trong enum `Car`. |
| View nhận dữ liệu thế nào? | Qua **thuộc tính**: `ShowroomView` có field `private CarResponseDTO responseDTO` + `setResponseDTO(...)` + `display()` **không tham số**. Controller đặt `message = "Sell Car"` rồi gọi `display()` **đúng 1 lần** mỗi luồng (tờ checklist 1.1). |
| Validate ở đâu? | **Main**: đổi chữ → enum bằng `getCar/getColor/getDay` (Hint của đề: sai thì `null`) và kiểm câu Y/N (`Validation.checkYesNo`, sai thì hỏi lại). Phần còn lại nằm trong **`checkCar`** vì **đề bắt**: *"It validates the information … if not it throws the ExceptionCar"* — giá do `Validation.checkPrice` đọc, sai thì ném `CarException` (xem mục 9). |
| Ai in `Can’t sell Car`? | `Main` — bắt `CarException` và in `Message.CANT_SELL` rồi `e.getMessage()` (tờ checklist/Guide: lỗi được bắt và in ở main). |

**Luồng một yêu cầu:**

```
Main: đọc 4 dòng → getCar / getColor / getDay (null nếu sai) → CarRequestDTO
      controller.checkCar(requestDTO)               ← gọi controller ĐÚNG 1 lần
         └─ service.checkCar(requestDTO)
               ├─ car/color sai            → throw CarException
               ├─ Validation.checkPrice()  → throw CarException (digit / > 0)
               ├─ new CarOrder(...) (model) → priceStrategy.getAskingPrice(order) → không đủ? throw
               ├─ ngày sai                 → throw CarException
               └─ carOrderRepository.addOrder(order)      ← repository lưu đơn đã bán
         └─ responseDTO.setMessage("Sell Car") → view.setResponseDTO(...) → view.display()  (1 lần)
Main: catch (CarException e) → in "Can’t sell Car" + e.getMessage()
Main: inputFindMore → Y lặp, N dừng, khác → "Please input Y or N."
```

### 3.1 Design Pattern — **Strategy** (luật giá)

| Yếu tố | Trong bài này |
|---|---|
| **Name** | Strategy (nhóm Behavioral) |
| **Problem** | Luật giá là thứ **dễ bị đổi nhất**: đề chỉ nói *"giảm $100"*, *"option cộng thêm"*. Thầy bảo *"xe không sơn giảm 200"* hay *"cuối tuần tăng giá"* mà luật nằm cứng trong `checkCar` thì phải mở service ra sửa. |
| **Solution** | `IPriceStrategy` = **Strategy** (`getAskingPrice(CarOrder)`). `ShowroomPriceStrategy` = **ConcreteStrategy**. `ShowroomService` = **Context**: giữ `IPriceStrategy` nhận qua **constructor**. `ShowroomController` **chọn**: `new ShowroomService(new ShowroomPriceStrategy())`. |
| **Consequences** | ✅ Luật giá mới = **1 class mới** + sửa 1 dòng controller; `checkCar` không đổi (**O**, **D**). ❌ Thêm 2 file so với viết thẳng 3 dòng tính giá. |

Còn có: **Facade** — `ShowroomController` là một cửa cho `Main`. Và `CarException` là cách
**kế thừa** để mang lý do từ chối về `Main` mà không cần `if` lồng nhau.

### 3.2 SOLID trong bài

| Nguyên lý | Ở đâu |
|---|---|
| **S** | enum giữ dữ liệu xe · `CarOrderRepository` giữ đơn đã bán · `ShowroomService` kiểm · `ShowroomPriceStrategy` tính giá · `Validation` đọc số · view in · main nhập |
| **O** | luật giá mới không sửa `ShowroomService` |
| **L** | `CarException` dùng được mọi chỗ cần `Exception`; mọi `IPriceStrategy` thay được nhau |
| **I** | `IPriceStrategy` chỉ 1 hàm |
| **D** | `ShowroomService` phụ thuộc interface `IPriceStrategy`, nhận qua constructor |

---

## 4. Code từng bước

> Thầy: *"code cái **model trước rồi đến data**"*. Bài này "dữ liệu gốc" là 3 enum, nên gõ enum ngay sau model.

| Bước | File | Việc |
|---|---|---|
| 1 | `model/CarOrder.java` | 4 field `private` (mỗi field một comment, cách nhau 1 dòng trống) + constructor rỗng + constructor đủ + get/set + `toString` (`String.format`) |
| 2 | `constants/Day`, `Color`, `Car` | hằng enum (cách nhau 1 dòng trống) + field + constructor + `getX(String)` static; `Car` có 3 field `colorList/priceList/daySellList` và 3 hàm `getColors/getPrices/getDaySells` |
| 3 | `repository/CarOrderRepository.java` | `orderList` + `addOrder(CarOrder)` |
| 4 | `dto/CarRequestDTO`, `CarResponseDTO` | request: car, color, day (enum), price (`String`); response: `message` — get/set |
| 5 | `exceptions/CarException.java` | `// brief: ExceptionCar` ngay trên khai báo; `extends Exception`, constructor `super(message)` |
| 6 | `constants/Message`, `Constants` | 6 thông báo của đề, prompt; `NO_COLOR_DISCOUNT`, `PRICE_PATTERN`, `ORDER_FORMAT` |
| 7 | `utils/Validation.java` | `checkPrice` (ném `CarException`), `checkYesNo` |
| 8 | `service/IPriceStrategy`, `ShowroomPriceStrategy` | interface + luật giá |
| 9 | `service/ShowroomService.java` | `checkCar` 6 bước + `addOrder` khi bán được + `isPaintable` private |
| 10 | `view/ShowroomView`, `controller/ShowroomController` | field `responseDTO` + `setResponseDTO` + `display()`; `checkCar(requestDTO)` đặt message rồi `display()` 1 lần |
| 11 | `main/Main.java` | `final` + `private Main()`; biến khai báo đầu block; tiêu đề, vòng `while`, `inputRequest`, `inputFindMore`, `catch (CarException)` |

**Bẫy hay gặp:**

1. `Color.valueOf("no color")` → **ném** `IllegalArgumentException` (và không bao giờ khớp vì tên hằng là `NO_COLOR`). Phải tự duyệt `values()` so với nhãn.
2. `Double.parseDouble("NaN")` **không ném lỗi** → NaN so sánh luôn `false` → xe được bán với giá "NaN". Kiểm bằng regex trước.
3. Trả thẳng `colorList` (list gốc) từ enum → ai cũng `add` thêm màu cho **mọi** BMW được. Trả **bản sao**.
4. Hai dấu nháy khác nhau: `Can’t sell Car` (nháy cong) và `Car can't sell today` (nháy thẳng) — đề viết thế, chép đúng.

---

## 5. Test trước khi gọi thầy

| # | Gõ (Name / Color / Price / Today) | Phải thấy |
|---|---|---|
| 1 | `BMV` / `no color` / `2400` / `THURSDAY` | `Can’t sell Car` · `Car break` |
| 2 | `BMW` / `no color` / `2400` / `THURSDAY` | `Sell Car` |
| 3 | `BMW` / `color` / `2400` / `THURSDAY` | `Color Car does not exist` |
| 4 | `BMW` / `GREEN` / `3000` / `MONDAY` | `Color Car does not exist` (màu có thật nhưng BMW không có) |
| 5 | `BMW` / `no color` / `-200` / `THURSDAY` | `Price greater than zero` |
| 6 | `BMW` / `no color` / `a` / `THURSDAY` | `Price is digit` |
| 7 | `BMW` / `RED` / `NaN` (hoặc `1e4`, để trống) / `MONDAY` | `Price is digit` |
| 8 | `BMW` / `no color` / `2400` / `FRIDAY` | `Car can't sell today` |
| 9 | `AUDI` / `YELLOW` / `2900` / `FRIDAY` | `Price is not enough` |
| 10 | `MERCEDES` / `no color` / `4900` / `TUESDAY` | `Sell Car` (5000 − 100) |
| 11 | `bmw` / `NO COLOR` / `2400` / `thursday` | `Sell Car` (không phân biệt hoa thường) |
| 12 | Y/N: để trống, `maybe` | `Please input Y or N.` rồi hỏi lại; `y` tiếp, `N` thoát |

---

## 6. Debug

| Việc | Cách làm |
|---|---|
| Breakpoint | dòng `if (car == null)` trong `ShowroomService.checkCar` |
| Chạy | **Ctrl+F5**, nhập `BMW` / `no color` / `2400` / `THURSDAY` |
| Quan sát | **Variables**: `requestDTO` → `car = BMW`, `color = NO_COLOR`, `price = "2400"` |
| Bước | **F8** qua từng `if`; ở `priceStrategy.getAskingPrice(order)` bấm **F7** → nhảy vào `ShowroomPriceStrategy` (đa hình qua interface), xem `Collections.min(priceList)` = 2500 |
| Xem ngoại lệ | nhập `BMV`: F8 đến `throw` → F8 tiếp thấy nhảy vào `catch (CarException e)` ở `Main` |

---

## 7. Câu hỏi thầy hay hỏi

### OOP

| Câu hỏi | Trả lời mẫu |
|---|---|
| 4 tính chất OOP ở đâu? | **Kế thừa**: `CarException extends Exception`; `ShowroomPriceStrategy implements IPriceStrategy`. **Đóng gói**: field `private` trong `CarOrder`, `CarRequestDTO`, 3 danh sách `private final` trong `Car` và chỉ trả **bản sao**. **Đa hình**: `ShowroomService` gọi `priceStrategy.getAskingPrice(...)` trên biến kiểu interface; `catch (CarException e)` rồi `e.getMessage()` là hàm kế thừa từ `Throwable`. **Trừu tượng**: `IPriceStrategy` chỉ nói "cho giá bán", không nói cách tính. |
| Sao đổi `ExceptionCar` thành `CarException`? | Tờ checklist 1.3: tên class exception **kết thúc** bằng `Exception`. Dòng ngay trên khai báo có `// brief: ExceptionCar` để thầy đối chiếu đề. Thầy muốn giữ tên đề thì **Refactor ▸ Rename** lại là xong. |
| Vì sao `CarException` extends `Exception` mà không `RuntimeException`? | Bị từ chối là **kết quả bình thường** của việc kiểm, không phải lỗi lập trình. Checked exception bắt compiler **ép** nơi gọi phải `catch` hoặc `throws`. |
| Sao "no color" là hằng `NO_COLOR` mà không phải `null`? | Đề cho nó là **một lựa chọn** có luật riêng (giảm $100). `null` để dành đúng việc đề giao: *"không phải Color"*. |
| Enum là gì, khác class thế nào? | Enum là class có **số đối tượng cố định** (AUDI, MERCEDES, BMW), tạo sẵn khi nạp lớp; constructor luôn `private`, không `new` được. |

### Access modifier, static, kiểu trả về

| Câu hỏi | Trả lời mẫu |
|---|---|
| `getCar/getColor/getDay` sao `static`? | Được gọi **trước** khi có hằng enum nào trong tay (đi từ chữ → enum), nên phải gọi qua **tên enum**: `Car.getCar(...)`. Đề bắt đúng các hàm này; enum ở `constants`, nơi thầy cho phép static. **Bỏ static** thì phải có sẵn một hằng mới gọi được, kiểu `Car.AUDI.getCar("BMW")` — vô nghĩa. |
| `getColors()` sao `public` mà không `static`? | Mỗi xe có danh sách **riêng** → hàm của **đối tượng** (`Car.BMW.getColors()`); `public` vì service và strategy gọi. |
| `isPaintable` sao `private`? | Chỉ `checkCar` dùng. |
| Field trong enum sao `private final`? | `private`: đóng gói; `final`: dữ liệu showroom không đổi khi chạy. |
| Field tên `colorList` mà hàm vẫn `getColors`? | Tờ checklist 1.5: biến kiểu collection kết thúc bằng `List`, mảng bằng `Array` (tham số constructor `colorArray`, `priceArray`, `daySellArray`). `getColors/getPrices/getDaySells` là **tên hàm đề bắt** — luật đặt tên biến không áp cho tên hàm. |
| `Main` sao `final` và có `private Main()`? | Tờ checklist 3.4: class chỉ có hàm static thì phải `final` + private constructor (không ai `new Main()` được). |
| `inputRequest`, `inputFindMore` sao `private static`? | `private`: chỉ `Main` dùng. `static`: gọi thẳng từ `main()` (static); Guide cho phép *static với hàm* trong main. |
| `Validation.checkPrice` sao `static`? Bỏ đi thì sao? | Không dùng dữ liệu đối tượng. Bỏ `static` → lỗi biên dịch ở `Validation.checkPrice(...)`; phải bỏ `private` constructor và `new Validation()` ở nơi gọi. |
| `checkCar` trả `Car` mà controller không dùng — sao không `void`? | **Đề bắt** kiểu trả `Car` ("return the Car Enum if matched"). Controller chỉ cần biết "không ném lỗi = khớp"; thầy muốn in tên xe thì đã có sẵn giá trị trả về. |
| `checkYesNo` trả `boolean`? | Có/không, điều khiển vòng `while`. |
| `getAskingPrice` trả `double`? | Giá có thể có số lẻ (`4899.99`). |
| **Sao `ArrayList` mà không `List`?** | `List` là **interface** (hợp đồng), `ArrayList` là **lớp cài đặt** bằng mảng động — lấy theo vị trí nhanh, đúng việc em làm (`priceList.get(car.getColors().indexOf(color))`). Đề viết *"return the List"* nhưng không ghi chữ ký; em khai rõ kiểu em thật sự dùng. |

### Kiến trúc

| Câu hỏi | Trả lời mẫu |
|---|---|
| Đề ghi `checkCar(Car, String, Day, String)` — sao em nhận 1 DTO? | Thầy: *"không được truyền 3 tham số 1 hàm"*; Guide: *"truyền data vào controller thông qua DTO param"*. 4 giá trị nằm trong `CarRequestDTO`. **Tên hàm, kiểu trả `Car`, `throws ExceptionCar` giữ nguyên.** |
| Sao `Validation.checkPrice` ném `CarException` chứ không `Exception`? | Đề cho giá là một phần của `checkCar`, mà mọi lời từ chối của `checkCar` là `ExceptionCar`. |
| Sao sai tên/màu/giá không hỏi lại mà in "Can’t sell Car"? | Màn hình đề in **từ chối** rồi hỏi Y/N — đó là **kết quả kiểm**, không phải lỗi gõ. Chỉ câu Y/N là hỏi lại. |

---

## 8. Thầy đổi yêu cầu tại chỗ

| Thầy bảo | Sửa | Không phải đụng |
|---|---|---|
| Xe không sơn giảm **$200** | chỉ `Constants.NO_COLOR_DISCOUNT` | mọi file khác |
| Luật giá khác (vd cuối tuần +10%) | thêm `WeekendPriceStrategy implements IPriceStrategy` + 1 dòng trong `ShowroomController` | `ShowroomService`, `Main`, view |
| Thêm xe **TOYOTA** | thêm 1 hằng trong `Car` (+ màu mới trong `Color` nếu có) | service, controller, main |
| In thêm giá khi bán được | thêm field (tên xe, giá) vào `CarResponseDTO`, service trả thêm, `display()` in thêm | `Main` |
| Bỏ hỏi lại Y/N | `Main.inputFindMore` | mọi file khác |

---

## 9. Chỗ khác với đề / bản cũ

| Chỗ | Đề / bản cũ | Bài này | Lý do |
|---|---|---|---|
| Chữ ký `checkCar` | `checkCar(Car car, String color, Day day, String price)` | `checkCar(CarRequestDTO requestDTO)` — giữ tên, `Car`; ném `CarException` (= `ExceptionCar` của đề) | thầy cấm 3+ tham số (V4); Guide dùng DTO |
| Tham số `color` | chữ ký ghi `String`, phần Input ghi *"Enum Color"* + Hint *"getColor… enter null"* | `Color` (enum/null) trong DTO | theo Input + Hint: main đổi chữ → enum bằng `getColor` |
| `Price is not enough` | Đề không có câu này | có (kế thừa bản cũ) | đề nói giá khách chọn phải khớp giá xe; thiếu giá thì không thể "Sell Car" |
| Trả `List` | Hint: *"return the List"* | `ArrayList` (bản sao) | thầy dặn khai kiểu cụ thể (V5); bản sao chống sửa dữ liệu enum |
| Câu Y/N | Bản cũ: khác `Y` là thoát | chỉ nhận Y/N, khác thì `Please input Y or N.` hỏi lại | tránh gõ nhầm là thoát mất; đề không nói |
| Giá "NaN", "1e4" | Bản cũ nhận (parseDouble) | `Price is digit` | "digit" = chữ số; regex `Constants.PRICE_PATTERN` |
| Kiến trúc | `entity/bo/ui` | MVC theo Guide; enum ở `constants`, `CarException` ở `exceptions` | luật thầy |
| **Tên lớp ngoại lệ** | đề: `ExceptionCar` | `CarException`, dòng ngay trên khai báo có `// brief: ExceptionCar` | **đề đặt `ExceptionCar`, tờ checklist 1.3 bắt tên class exception kết thúc bằng `Exception` → hỏi thầy nếu thầy muốn giữ tên đề** |
| Interface luật giá | bản cũ: `PriceStrategy` | `IPriceStrategy` | checklist 1.3: interface bắt đầu bằng `I` (tên em đặt, không phải đề) |
| Repository | bản cũ: không có | `CarOrderRepository` (`orderList`, `addOrder`) | checklist 1.1: *"Bắt buộc phải có repository"* |
| View | bản cũ: `showMessage(String)` | field `responseDTO` + `setResponseDTO` + `display()` không tham số | checklist 1.1: View nhận qua thuộc tính, render 1 lần/luồng |
| Tên biến collection / mảng | bản cũ: `colors`, `prices`, `daySells` | `colorList`, `priceList`, `daySellList`; tham số `colorArray`, `priceArray`, `daySellArray` | checklist 1.5 |
| `Main` | bản cũ: `public class Main`, biến khai báo giữa block | `public final class Main` + `private Main()`; biến gom đầu block, khởi tạo luôn | checklist 3.4, 2.6, 3.7 |
| **Kiểm giá trong `checkCar`** | tờ checklist 1.1: validate ở `Main` | giữ trong `checkCar` (service gọi `Validation.checkPrice`) | **đề bắt** `checkCar(…, String price)` *"validates the information … throws the ExceptionCar"*; đề báo lỗi theo thứ tự tên → màu → giá → ngày sau khi nhập đủ 4 dòng. **Hỏi thầy** nếu thầy muốn `Main` tự kiểm giá (khi đó `BMV` + giá `a` sẽ báo `Price is digit` thay vì `Car break`) |
| Câu Y/N sau lời từ chối | hình đề là các **ô rời** (ô nhập → ô kết quả): ô `Sell Car` có `Do you want find more?(Y/N):Y`, ô từ chối chỉ có 2 dòng | hỏi Y/N sau **mọi** lần kiểm (như bản cũ, không đổi) | hình đề không cho thấy sau lời từ chối làm gì. **Hỏi thầy** nếu thầy muốn từ chối xong nhập lại ngay (không hỏi Y/N) — chỉ sửa vòng `while` trong `Main` |

---

## 10. Tờ checklist 25 mục — bài này đạt thế nào

| Mục | Chỗ trong code |
|---|---|
| **1.1** MVC + repository | `repository/CarOrderRepository` (`orderList`, `addOrder`). Luồng: `Main` → `CarRequestDTO` → `ShowroomController` → `ShowroomService` → `CarOrderRepository` → `CarOrder`. Controller không import `model`; `ShowroomView` nhận `CarResponseDTO` qua field, `display()` gọi 1 lần/luồng; lỗi `throw new CarException(Message.X)`, `Main` in `e.getMessage()` |
| **1.3** tên class | `CarException` (đề: `ExceptionCar`, có `// brief:`), `IPriceStrategy` |
| **1.5** tên biến | `colorList`, `priceList`, `daySellList`, `orderList`; `colorArray`, `priceArray`, `daySellArray`; `requestDTO`, `responseDTO` |
| **2.6 / 3.7** khai báo đầu block + khởi tạo | `ShowroomService.checkCar` (`double price = 0;`, `CarOrder order = null;`), `Validation.checkPrice` (`double price = 0;`), `Main.main` (`CarRequestDTO requestDTO = null;`), `Main.inputFindMore` (`String line = "";`) |
| **2.8** dòng trống | trước mọi comment (cả comment field và hằng enum trong `Car/Color/Day`), sau vùng khai báo biến, sau `}` của mỗi khối trước câu lệnh tiếp |
| **3.3** ngoặc | `ShowroomService`: `if ((color == null) \|\| ...)`, `if ((day == null) \|\| ...)`, `isPaintable`: `(color == Color.NO_COLOR) \|\| ...`; `Validation`: `(input == null) ? "" : input.trim()` |
| **3.4** class chỉ có static | `public final class Main` + `private Main()`; `Validation`, `Constants`, `Message` cũng `final` + private constructor |
| **3.8** không cộng chuỗi | `CarOrder.toString()` dùng `String.format(Constants.ORDER_FORMAT, ...)` |
