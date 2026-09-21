# J1.L.P0013 — The Vehicle Management

> **Bài dài (Long Assignment)** · 500 LOC. Đề chia sẵn LOC cho từng chức năng (mỗi cái khoảng 50 LOC). Muốn lấy trọn điểm cần:
> - **cấu trúc dữ liệu đúng** (Function 0: *"Classes, abstract classes, Interfaces"*);
> - **đọc/ghi file** chạy thật;
> - trả lời được câu *"thêm một loại xe mới thì sửa ở đâu?"*, vì đề nói *"The program must be designed so that adding a new vehicle is easy"*.

| | |
|---|---|
| Loại / LOC | Long Assignment · 500 LOC |
| Project | `HE176322_J1LP0013_VehicleManagement` |
| Chạy | NetBeans: **File ▸ Open Project** → chọn thư mục → **F6** (file `vehicles.txt` nằm cạnh `build.xml`) |
| Lớp chạy | `main.Main` |
| Kiểm tự động | `python3 _tools/verify.py --netbeans J1LP0013` → 3 kịch bản × 2 locale |

---

## 1. Đề bài nói gì

Menu chính in **đúng từng chữ** danh sách chức năng của đề (kể cả `4. Delete vehicle ID` và dòng `Others- Quit`):

```
===== VEHICLE MANAGEMENT =====
1. Load data from file
2. Add new vehicle
3. Update vehicle by ID
4. Delete vehicle ID
5. Search vehicle
6. Show vehicle list
7. Store data to file
Others- Quit
==============================
```

| Function | Đề yêu cầu | Bài này |
|---|---|---|
| 0 · Cấu trúc dữ liệu | *"Classes, abstract classes, Interfaces. Use only one collection"* | `Vehicle` (abstract) ← `Car`, `Motorbike`; interface `ISoundable`; **một** `ArrayList<Vehicle> vehicleList` trong `VehicleRepository` |
| 1 · Load | đọc hết `vehicles.txt` vào collection | `Main.readDataFile` (đọc tệp qua `FileUtils`) → `VehicleService.loadData` → `VehicleRepository.replaceAll` |
| 2 · Add | submenu (Car/Motorbike), **kiểm ràng buộc**, hỏi tiếp hay về menu | `Main.addVehicles` → `VehicleService.addVehicle` |
| 3 · Update | nhập id; không có thì **"Vehicle does not exist"**; **bỏ trống = giữ cũ**; kiểm ràng buộc; in kết quả | `Main.inputUpdate` → `VehicleService.updateVehicle` |
| 4 · Delete | theo id, **hỏi xác nhận**, báo success/fail | `Main.inputDelete` → `VehicleService.deleteVehicle` |
| 5.1 · Search by name | tên **chứa** chuỗi, in **giảm dần** | `VehicleService.searchByName` + `VehicleNameComparator` |
| 5.2 · Search by id | id trùng khớp | `VehicleService.searchById` |
| 6.1 · Show all | in cả show room | `VehicleService.getAllVehicles` |
| 6.2 · Show by price | giá **giảm dần**; xe máy thì gọi **`makeSound`** ("Tin tin tin") | `VehiclePriceComparator` + `ISoundable` |
| 7 · Store | ghi collection ra file | `VehicleService.storeData` → `VehicleRepository.storeToFile` (ghi qua `FileUtils`) |
| Others | Quit | nhánh `default` của `Main.main`: **số nào khác 1–7** cũng thoát (`Goodbye.`) |

**Thuộc tính đề cho:**

| Loại | Chung | Riêng |
|---|---|---|
| Car | id, name, color, price, brand | **type** (sport, travel…), **year of manufacture** |
| Motorbike | id, name, color, price, brand | **speed**, **require license** + hàm **`makeSound`** |

Câu cuối đề: *"All errors must be handled, not accepted interrupt the program."*
Nghĩa là mọi lỗi (gõ sai, thiếu file, dòng hỏng) chỉ hiện một câu thông báo, chương trình chạy tiếp.

---

## 2. Kiến thức cần biết

### 2.1 Abstract class + interface — đề bắt cả hai

```java
public abstract class Vehicle { id, name, color, price, brand … }        // "là một chiếc xe"
public class Car extends Vehicle { carType, yearOfManufacture }
public class Motorbike extends Vehicle implements ISoundable { speed, requireLicense }
public interface ISoundable { String makeSound(); }                      // "kêu được"
```

| | `Vehicle` (abstract class) | `ISoundable` (interface) |
|---|---|---|
| Nói gì | **là gì**: mọi xe đều có 5 field chung | **làm được gì**: thứ này kêu được |
| Có field, có code? | có (5 field, `toDataLine`) | không, chỉ có hợp đồng |
| Lớp con có bao nhiêu | chỉ `extends` **một** | `implements` **nhiều** được |
| Vì sao bài dùng | để **một** `ArrayList<Vehicle>` chứa cả Car lẫn Motorbike | để mục 6.2 hỏi *"xe này kêu được không?"* (`instanceof ISoundable`), không hỏi *"có phải Motorbike không?"* — mai thêm xe tải có còi thì chỉ cần `implements ISoundable` |

Tên interface bắt đầu bằng `I` vì tờ checklist mục **1.3** bắt vậy (bản cũ tên `Soundable`).

### 2.2 Đa hình — mỗi xe tự trả lời

| Lời gọi | `Car` trả | `Motorbike` trả |
|---|---|---|
| `getType()` | `CAR` | `MOTORBIKE` |
| `getDetails()` | `Type: Travel, Year: 2020` | `Speed: 150.0km/h, License: Yes` |
| `getDetailData()` (ghi file) | `Travel,2020` | `150.0,true` |
| `makeSound()` | — (không `implements ISoundable`) | `Tin tin tin` |

`VehicleService.convertToRow` gọi `vehicle.getDetails()` **mà không cần biết** đó là xe gì.

### 2.3 Template Method — `Vehicle.toDataLine()`

```java
public final String toDataLine() {          // khung CỐ ĐỊNH của một dòng file
    return String.join(Constants.DATA_SEPARATOR, getType().getCode(), id, name, color,
            String.valueOf(price), brand, getDetailData());   // ← bước lớp con điền
}
```

`String.join` đặt dấu phẩy giữa các cột, không cần cộng chuỗi bằng `+` (tờ checklist **3.8**).
Mỗi dòng `vehicles.txt` có **8 cột**: `loại, id, tên, màu, giá, hãng, chi tiết 1, chi tiết 2`.

```
CAR,C001,Camry,Black,35000.0,Toyota,Travel,2020
MOTORBIKE,M001,Exciter 150,Blue,2500.0,Yamaha,150.0,true
```

### 2.4 Đọc / ghi file

| Việc | Code | Vì sao |
|---|---|---|
| Ai đọc tệp | `Main.readDataFile` gọi `FileUtils.readLines(Constants.DATA_FILE)`, đặt vào `requestDTO.lineList` | tờ checklist 1.1: *"đọc từ file … thực hiện ở Main"* |
| Ai ghi tệp | `VehicleRepository.storeToFile` gọi `FileUtils.writeLines` | repository giữ dữ liệu; `FileUtils` lo phần đọc/ghi dòng |
| Đọc từng dòng | `BufferedReader` + `readLine()` tới khi `null` | đọc theo dòng, nhanh |
| Đóng file chắc chắn | `try (BufferedReader r = …) { … }` (try-with-resources) | lỗi giữa chừng vẫn đóng; trên Windows file không đóng thì bị khoá và lần ghi sau hỏng |
| Tiếng Việt | `InputStreamReader(…, StandardCharsets.UTF_8)` | không phụ thuộc bảng mã của máy |
| Ghi **đè** | `new FileOutputStream(fileName)` | ghi nối (append) thì mỗi lần Store file dài gấp đôi, lần Load sau toàn id trùng |
| Tách cột | `line.split(",", -1)` → `String[] partArray` | `-1` giữ cả cột rỗng cuối dòng; không có thì dòng `…,Pickup,` bị tính thiếu cột |
| Dòng hỏng | `VehicleFactory.createFromLine` trả `null` → `VehicleService.loadData` đếm `skipped` | **một dòng hỏng chỉ mất một dòng**, không mất cả file |
| Kiểm dữ liệu file | dùng **lại** `Validation.checkText/checkNumber` | file sửa tay sai luật cũng bị chặn như gõ phím |

### 2.5 Sắp xếp giảm dần — Comparator

```java
// VehiclePriceComparator: giá cao trước
int byPrice = Double.compare(second.getPrice(), first.getPrice());   // đảo first/second = giảm dần
if (byPrice == 0) return first.getId().compareToIgnoreCase(second.getId());   // hoà thì theo id
```

⚠️ **Không** viết `(int) (second.getPrice() - first.getPrice())`: 35000.5 và 35000.0 ra `0` (bị coi là bằng nhau), và hiệu lớn thì tràn `int`.

### 2.6 Update — "bỏ trống thì giữ cũ"

`VehicleRequestDTO` dùng **`Double`, `Integer`, `Boolean`** (lớp bọc), không dùng `double/int/boolean`:
bỏ trống thì trường là `null`, và `VehicleFactory.applyChanges` chỉ gán những trường **khác null**.
Kiểu nguyên thuỷ không có giá trị "không gõ gì".

### 2.7 Ràng buộc tự đặt (đề bảo *"perform a requirements analysis step"*)

| Ô | Luật | Thông báo |
|---|---|---|
| id | 1 chữ cái + 3 chữ số (`C001`), không trùng (không phân biệt hoa thường) | `ID must be one letter followed by 3 digits, for example C001.` · `Vehicle ID c001 already exists.` (báo khi đã nhập đủ các ô, xe không được thêm) |
| name / brand | 2–30 / 2–20 ký tự chữ, số, cách, gạch ngang | `Name must be …` / `Brand must be …` |
| color | 2–15 chữ cái | `Color must be 2 to 15 letters.` |
| price | số > 0 (0.01 … 1 tỷ) | `Price must be a positive number.` |
| type (Car) | Sport / Travel / Family / Pickup (gõ thường vẫn nhận, lưu `Travel`) | `Type must be one of: …` |
| year (Car) | số **nguyên** 1900–2100 | `Year of manufacture must be from 1900 to 2100.` |
| speed (Motorbike) | 1–400 km/h | `Speed must be a number from 1 to 400 km/h.` |
| license | Y / N | `Please enter Y or N.` |
| menu chính | số nguyên; 1–7 là chức năng, số khác là Quit | `Please enter a number.` |
| submenu | 1–3 | `Please choose from 1 to 3.` |

Không ô chữ nào cho gõ **dấu phẩy**, vì dấu phẩy là ký tự phân cách cột trong file.

---

## 3. Thiết kế

```
HE176322_J1LP0013_VehicleManagement/
├── vehicles.txt                       5 xe mẫu (đọc bằng Function 1)
└── src/
    ├── constants/  Message.java        mọi câu chữ, 4 menu (menu chính đúng chữ đề)
    │               Constants.java      số menu, regex, giới hạn, cột file, định dạng bảng
    │               VehicleType.java    «enum» CAR / MOTORBIKE: chữ trong file, nhãn, số submenu
    │               TextField.java      «enum» ID, NAME, COLOR, BRAND, CAR_TYPE, KEYWORD: prompt + regex + lỗi
    │               NumberField.java    «enum» PRICE, YEAR, SPEED: prompt + khoảng + lỗi
    ├── model/      ISoundable.java     «interface» makeSound()
    │               Vehicle.java        «abstract» 5 field chung + Template Method toDataLine
    │               Car.java            extends Vehicle
    │               Motorbike.java      extends Vehicle implements ISoundable
    ├── dto/        VehicleRequestDTO   main ──► controller: ô đã gõ (null = giữ cũ), confirmed, lineList
    │               VehicleResponseDTO  controller ──► view: message + vehicle + vehicleList
    │               VehicleRowDTO       một dòng bảng (+ sound) nằm trong VehicleResponseDTO
    ├── repository/ VehicleRepository   MỘT ArrayList<Vehicle> vehicleList + CRUD + ghi tệp qua FileUtils
    ├── service/    VehicleService      service DUY NHẤT controller gọi: Function 1–7
    │               VehicleFactory      Factory: loại → class; dựng xe từ phím và từ 1 dòng tệp
    │               VehicleNameComparator   Strategy: tên Z→A
    │               VehiclePriceComparator  Strategy: giá cao→thấp
    ├── controller/ VehicleController   Facade: DTO → service → view (1 lần render mỗi luồng)
    ├── view/       VehicleView         field responseDTO + setResponseDTO + display()
    ├── utils/      Validation          kiểm dạng (dùng cho cả phím lẫn file)
    │               FileUtils           đọc/ghi dòng (UTF-8, try-with-resources)
    └── main/       Main                final + private constructor; 4 menu, Scanner, đọc tệp, vòng hỏi lại
```

| Lớp | Làm gì | Không được làm |
|---|---|---|
| `Main` | in menu/câu nhắc/lỗi, **đọc bàn phím**, **validate** (qua `Validation`), **đọc `vehicles.txt`** (qua `FileUtils`), gói DTO; mỗi luồng gọi controller **1 lần** | import model/view/service/repository |
| `VehicleController` | DTO → `VehicleService` → `view.setResponseDTO(…)` + `view.display()` **1 lần** | Scanner, `System.out`, static, import model |
| `VehicleService` | luật (id trùng, id không có, show room trống), tìm, sắp xếp, đổi model → `VehicleRowDTO`, đếm dòng hỏng | in ra, đọc phím, đọc tệp |
| `VehicleFactory` | biết loại nào dùng class nào; dựng xe từ DTO và từ 1 dòng tệp; `applyChanges` | in ra |
| `VehicleRepository` | giữ collection, CRUD đơn giản, ghi tệp qua `FileUtils` | kiểm luật, đọc tệp, in ra |
| `Vehicle`, `Car`, `Motorbike` | mô tả xe, tự trả chi tiết / âm thanh | **in** (vì thế `makeSound` **trả** chuỗi, view in) |
| `VehicleView` | in đúng những gì có trong `responseDTO` | nhận dữ liệu qua tham số |
| `Validation`, `FileUtils` | kiểm dạng; đọc/ghi dòng | biết gì về xe |

**Luồng chung (mọi chức năng):**

```
Main (nhập + validate + đọc tệp) ──RequestDTO──► VehicleController ──► VehicleService ──► VehicleRepository ──► Vehicle/Car/Motorbike
                                                        │
                                                        └──VehicleResponseDTO──► VehicleView.setResponseDTO(…) → display()   (1 lần / luồng)
lỗi nghiệp vụ: service throw new Exception(Message.X) ──► Main catch → System.out.println(e.getMessage())
```

Mỗi `case` của menu chính, và mỗi mục của 3 submenu (Car / Motorbike, 5.1 / 5.2, 6.1 / 6.2), là **một luồng**: gọi controller đúng một lần.

**Luồng Update** — chỗ duy nhất có thêm một lần gọi **chỉ để kiểm**:

```
Main: đọc id (sai dạng → hỏi lại)
      controller.findVehicleType(dto)     ← CHỈ ĐỂ KIỂM, không render:
          không có id → service throw "Vehicle does not exist" → Main in → về menu
          có → trả CAR / MOTORBIKE để Main hỏi đúng ô riêng
Main: "Leave a field blank to keep the current value."
      đọc từng ô: trống → null · sai → hỏi lại · đúng → giá trị
      controller.updateVehicle(dto)       ← lần gọi chính của luồng
          └─► service: factory.applyChanges(vehicle, dto)   chỉ gán ô khác null
          └─► repository.updateVehicle(vehicle)
          └─► responseDTO: "Update successfully!" + dòng xe mới → view.display() một lần
```

Vì sao được gọi thêm: đề viết *"If vehicle does not exist, the notification 'Vehicle does not exist'. Otherwise, user can start input new information"* — phải báo **ngay sau id**, trước các ô khác; và Main phải biết xe là Car hay Motorbike mới hỏi đúng ô riêng.

**Luồng Delete** — một lần gọi, câu xác nhận nằm trong DTO:

```
Main: id → "Delete vehicle C001? (Y/N): " → dto.confirmed
controller.deleteVehicle(dto) → service: N → "Delete cancelled." · Y + có id → "Delete successfully!" · Y + không có → "Delete failed!"
```

### 3.1 Design Pattern trong bài

**1. Factory** (Creational) — pattern chính, trả lời câu *"adding a new vehicle is easy"*

| Yếu tố | Trong bài |
|---|---|
| **Problem** | Xe được tạo ở **2 chỗ**: gõ phím (Function 2) và đọc file (Function 1). Nếu mỗi chỗ tự `if … new Car() else new Motorbike()` thì thêm loại mới phải sửa 2 chỗ, và dễ quên một chỗ. |
| **Solution** | `VehicleFactory.createVehicle(dto)` là **nơi duy nhất** có `new Car()` / `new Motorbike()` (`switch` theo `VehicleType`). Cả `VehicleService.addVehicle` lẫn `VehicleFactory.createFromLine` (một dòng tệp) đều đi qua nó. |
| **Consequences** | ✅ Thêm loại: 1 class model + 1 hằng enum + 1 `case` factory (mục 8). ❌ Thêm một lớp trung gian. |

**2. Template Method** (Behavioral) — `Vehicle.toDataLine()` (mục 2.3).

**3. Strategy** (Behavioral) — `Comparator<Vehicle>`

| Yếu tố | Trong bài |
|---|---|
| **Problem** | Hai thứ tự khác nhau: tên giảm dần (5.1), giá giảm dần (6.2). Thầy hay bảo *"đổi sang tăng dần"*. |
| **Solution** | `VehicleNameComparator`, `VehiclePriceComparator` = ConcreteStrategy. `VehicleService` giữ 2 biến **kiểu interface** `Comparator<Vehicle>` và gọi `Collections.sort(sortedList, priceOrder)`. |
| **Consequences** | ✅ Đổi thứ tự thì thay class, không sửa vòng lặp nào. ❌ 2 file nhỏ. |

**Các pattern khác:** **Facade** (`VehicleController`) · **Repository** (`VehicleRepository`) · **DTO** · **MVC**.
Interface `ISoundable` là **thiết kế theo khả năng**: `VehicleService.getAllByPriceDescending` hỏi `instanceof ISoundable`, không hỏi `instanceof Motorbike`.

### 3.2 SOLID trong bài

| Chữ | Ở đâu |
|---|---|
| **S** | `VehicleService` (luật), `VehicleFactory` (tạo xe), `VehicleRepository` (giữ + ghi), `VehicleView` (in), `FileUtils` (đọc/ghi dòng), `Validation` (kiểm dạng), `Main` (nhập) |
| **O** | thêm thứ tự sắp = thêm Comparator; thêm xe kêu được = `implements ISoundable`, view không đổi |
| **L** | mọi chỗ nhận `Vehicle` chạy đúng với `Car` lẫn `Motorbike` (bảng, file, sắp giá) |
| **I** | `ISoundable` chỉ 1 hàm; `Car` không bị ép viết `makeSound` |
| **D** | `VehicleService` giữ `Comparator<Vehicle>` (trừu tượng), không giữ class sắp cụ thể; controller chỉ biết `VehicleService`, không biết repository |

---

## 4. Code từng bước

> Thầy: *"code cái **model trước rồi đến data**"*.

| Bước | File | Việc |
|---|---|---|
| 1 | `constants/VehicleType.java` | enum 2 loại + `findByCode` + `findByMenuChoice` |
| 2 | `model/ISoundable.java` | interface 1 hàm |
| 3 | `model/Vehicle.java` | abstract, 5 field, get/set, 3 hàm abstract, `final toDataLine` |
| 4 | `model/Car.java`, `Motorbike.java` | field riêng, `@Override` `getType/getDetails/getDetailData`; Motorbike thêm `makeSound` |
| 5 | `constants/TextField`, `NumberField`, `Message`, `Constants` | luật từng ô ở **một** chỗ |
| 6 | `dto/*` | JavaBean; request dùng lớp bọc + `confirmed` + `lineList`; response có `message`, `vehicle`, `vehicleList` |
| 7 | `repository/VehicleRepository` | `vehicleList` + CRUD + `replaceAll` + `storeToFile` |
| 8 | `utils/Validation`, `FileUtils` | `isBlank/getInt/getChoice/checkText/checkNumber/getYesNo/checkBoolean`; `readLines/writeLines` |
| 9 | `service/VehicleFactory` | `createVehicle`, `createFromLine`, `applyChanges` |
| 10 | `service/VehicleNameComparator`, `VehiclePriceComparator` | `compare` + hoà thì theo id |
| 11 | `service/VehicleService` | Function 1–7, mỗi hàm trả một `VehicleResponseDTO` (riêng `findVehicleType` trả loại xe) |
| 12 | `view/VehicleView` | field `responseDTO`, `setResponseDTO`, `display()`; hàm riêng `displayList/displayHeader/formatRow` |
| 13 | `controller/VehicleController` | constructor tạo service + view; mỗi hàm public = một luồng: gọi service → `setResponseDTO` → `display()` |
| 14 | `main/Main` | menu chính + 3 submenu + `readDataFile` + các hàm `input*` / `inputNew*` |

**Bẫy hay gặp**

1. **Hai `ArrayList`** (một cho Car, một cho Motorbike): trái đề *"only one collection"*, và sắp theo giá không trộn được hai loại.
2. **Load mà cộng thêm** vào collection cũ: bấm Load 2 lần là mọi xe bị nhân đôi. `replaceAll` xoá rồi mới nạp.
3. **Ghi file nối đuôi** (append): xem mục 2.4.
4. **`makeSound` in thẳng trong model**: trái Guide (model không in). Bài này cho nó **trả** chuỗi, `VehicleView` in.
5. **So giá bằng phép trừ ép `int`**: xem mục 2.5.
6. **Update gán cả ô trống**: tên xe thành chuỗi rỗng. Phải để `null` = giữ.
7. **`sc.nextLine()` trong `try`**: khi hết input, vòng hỏi lại quay vô hạn. Bài này đọc dòng trước `try`.
8. **Controller gọi view 2 lần** (vd `showMessage` rồi `displayVehicle`): tờ checklist chỉ cho render **1 lần mỗi luồng** — gom câu thông báo và dòng xe vào **một** `VehicleResponseDTO`.
9. **Main gọi controller trong vòng hỏi lại** (bản cũ kiểm id trùng ngay trong vòng nhập id): mỗi luồng chỉ gọi controller 1 lần; lời gọi nằm trong hàm riêng không có vòng lặp (`addVehicle`, `searchByName`, `showAll`…).

---

## 5. Test trước khi gọi thầy

> Thầy: *"test tất cả các happy case cũng như hiển thị đủ các message validation"*.

| # | Chọn | Gõ | Phải thấy |
|---|---|---|---|
| 1 | menu | `abc` / trống | `Please enter a number.` (số nào cũng nhận: 1–7 là chức năng, số khác là Quit) |
| 2 | 6 → `4` | — | `Please choose from 1 to 3.` |
| 3 | 6 → 1 / 6 → 2 khi chưa Load | — | `The show room is empty.` |
| 4 | 1 | — | `Loaded 5 vehicle(s) from vehicles.txt` |
| 5 | 6 → 1 | — | 5 dòng **đúng thứ tự file** + `Total: 5 vehicle(s)` |
| 6 | 6 → 2 | — | Ranger 48,000 → Camry → Civic → Exciter + **`Tin tin tin`** → Vision + **`Tin tin tin`** |
| 7 | 5 → 1 | `i` | Vision, Exciter 150, Civic (tên **Z→A**) |
| 8 | 5 → 1 | `zzz` / trống | `No vehicle found.` / `Search text must not be empty.` |
| 9 | 5 → 2 | `c002` / `C999` / `c1` | dòng Ranger (id gõ thường vẫn ra) / `Vehicle does not exist` / lỗi dạng id |
| 10 | 2 → 1 | id `c1`; tên `A`; màu `Bl4ck`; giá `-5`, `abc`, `NaN`; hãng `!`; type `Racing`; năm `1800`, `2020.5` | từng câu lỗi ở mục 2.7, hỏi lại **đúng ô đó** |
| 11 | 2 → 2 | id đã có (`c001`), các ô còn lại đúng | nhập hết các ô rồi `Vehicle ID c001 already exists.` (xe không được thêm) → `Add another vehicle? (Y/N):` |
| 12 | 2 → 2 | speed `500`; license `maybe` | `Speed must be …` · `Please enter Y or N.` |
| 13 | sau khi thêm | `x`, rồi `n` | `Please enter Y or N.` → về menu |
| 14 | 3 | `C001` khi chưa có | `Vehicle does not exist` **ngay sau id**, về menu |
| 15 | 3 | `c001` → trống, `Silver`, trống, trống, `sport`, trống | `Update successfully!` + dòng xe — chỉ màu và type đổi (`Sport`) |
| 16 | 3 | giá `abc` rồi trống | `Price must be a positive number.` rồi giữ giá cũ |
| 17 | 4 | `Z001` → `y` / `M001` → `n` / `m001` → `q`, `y` | `Delete failed!` / `Delete cancelled.` / `Please enter Y or N.` rồi `Delete successfully!` |
| 18 | 7 rồi 1 | — | `Stored 4 vehicle(s) …` rồi `Loaded 4 vehicle(s) …` |
| 19 | `8`, `0`, `9` | — | `Goodbye.` — dòng `Others- Quit` của đề; không hỏi lưu, muốn giữ thay đổi thì chọn 7 trước |
| 20 | sửa tay `vehicles.txt`: dòng rác, license `maybe`, id trùng, dòng trống | 1 | chỉ nạp dòng đúng + `3 damaged line(s) were ignored.` (dòng trống không tính) |
| 21 | xoá `vehicles.txt` rồi 1 | — | `Data file vehicles.txt does not exist.` |

---

## 6. Debug

| Việc | Cách làm |
|---|---|
| Thấy đa hình | breakpoint `row.setDetails(vehicle.getDetails());` trong `VehicleService.convertToRow` → **F7**: lúc vào `Car.getDetails`, lúc vào `Motorbike.getDetails` |
| Thấy Factory | breakpoint `switch (requestDTO.getVehicleType())` trong `VehicleFactory.createVehicle`, chạy Function 1: mỗi dòng file dừng một lần |
| Dòng hỏng | breakpoint `return null;` trong `catch` của `VehicleFactory.createFromLine`, sửa file cho giá = `abc` → Variables: `line`, `e.getMessage()` |
| Sắp giá | breakpoint trong `VehiclePriceComparator.compare` → xem `byPrice` âm/dương |
| Update giữ cũ | breakpoint `if (requestDTO.getName() != null)` trong `applyChanges` → bỏ trống tên thì thấy `null` và nhánh bị bỏ qua |
| Render 1 lần | breakpoint `vehicleView.display();` trong một hàm của `VehicleController` → Variables: `responseDTO` có `message`, `vehicle`, `vehicleList` nào khác `null` thì `display()` in phần đó |

---

## 7. Câu hỏi thầy hay hỏi

### OOP

| Câu hỏi | Trả lời mẫu |
|---|---|
| 4 tính chất OOP ở đâu? | **Đóng gói**: field `private` + get/set; repository trả **bản sao** list. **Kế thừa**: `Car`, `Motorbike extends Vehicle`. **Đa hình**: `getType/getDetails/getDetailData` chạy bản của lớp thật; `Collections.sort` gọi `compare` qua kiểu `Comparator`. **Trừu tượng**: `Vehicle` abstract, `ISoundable` interface. |
| Abstract class khác interface? | Mục 2.1. Một câu: abstract class là **"là gì"** (có field, code chung); interface là **"làm được gì"** (hợp đồng, implements nhiều). |
| Sao `Vehicle` abstract? | Show room không có "chiếc xe chung chung"; mỗi xe là Car hoặc Motorbike. Compiler chặn `new Vehicle()`. |
| Sao `toDataLine` `final`? | Để lớp con không đổi được thứ tự 6 cột đầu, vì Load đọc theo đúng thứ tự đó. |
| Sao `makeSound` trả `String` mà không in? | Guide + tờ checklist 1.1: model **không** in. Màn hình vẫn đúng `Tin tin tin` như đề, chỉ khác là view in. |
| `instanceof` có trái đa hình không? | `instanceof ISoundable` hỏi **khả năng** (interface), không hỏi class. `VehicleFactory.applyChanges` dùng `instanceof Car/Motorbike` vì DTO chung phải chép vào **field riêng**. Đó là chỗ duy nhất, và nó nằm trong factory. |

### Kiến trúc & tờ checklist

| Câu hỏi | Trả lời mẫu |
|---|---|
| Sao bài có repository? | Tờ checklist 1.1: *"Bắt buộc phải có repository"*. `VehicleRepository` giữ **một** `ArrayList<Vehicle> vehicleList` và CRUD đơn giản (`findById`, `addVehicle`, `updateVehicle`, `deleteVehicle`, `findAll`, `replaceAll`) + ghi tệp qua `FileUtils`. Luật (id trùng, tìm theo tên, sắp) nằm ở `VehicleService`: Controller → Service → Repository → Model. |
| View nhận dữ liệu thế nào? | Qua **thuộc tính**: `private VehicleResponseDTO responseDTO` + `setResponseDTO(…)`, rồi `display()` **không tham số**. Controller gọi `setResponseDTO` rồi `display()` đúng 1 lần mỗi luồng; `display()` in `message`, rồi `vehicle` (header + 1 dòng), rồi `vehicleList` (bảng + total) — phần nào `null` thì bỏ. |
| Validate ở đâu? | Ở `Main`, qua `utils/Validation`: mỗi ô hỏi lại tới khi đúng dạng. Luật cần dữ liệu (id trùng, id không có, show room trống, không tìm thấy) thì `VehicleService` `throw new Exception(Message.X)`, `Main` bắt và in `e.getMessage()`. |
| Đọc tệp ở đâu? | `Main.readDataFile`: `FileUtils.readLines(Constants.DATA_FILE)` → `requestDTO.setLineList(…)` → `controller.loadData` → `VehicleService.loadData` dựng từng xe bằng `VehicleFactory.createFromLine` → `VehicleRepository.replaceAll`. Ghi tệp (Function 7): `VehicleRepository.storeToFile` gọi `FileUtils.writeLines`. |
| Sao Update gọi controller 2 lần? | Lần 1 `findVehicleType` **chỉ để kiểm** (ném lỗi, không render): đề bắt báo `Vehicle does not exist` ngay sau id, và Main cần biết Car/Motorbike để hỏi đúng ô. Lần 2 `updateVehicle` là lần render duy nhất của luồng. |
| Sao thêm xe mà id trùng lại báo sau cùng? | Mỗi luồng chỉ gọi controller 1 lần (Guide), và phần Add của đề không bắt kiểm id ngay. Kiểm id trùng cần dữ liệu nên chỉ service làm được — nó làm trong lần gọi `addVehicle`. |
| Sao Delete hết `Vehicle does not exist`? | Đề phần Delete chỉ bắt *"confirm message"* rồi *"Show the result of the delete: success or fail"*. Một lần gọi: id không có thì kết quả là `Delete failed!`. |

### Collection, file, thuật toán

| Câu hỏi | Trả lời mẫu |
|---|---|
| Sao `ArrayList` mà không `LinkedList`? | Bài chủ yếu duyệt, tìm, sắp và lấy theo chỉ số (`set(i, …)` khi update). ArrayList lấy theo chỉ số O(1). |
| Độ phức tạp? | Tìm theo id O(n) · tìm theo tên O(n) + sắp O(k log k) · sắp giá O(n log n) (`Collections.sort` là merge sort). |
| Sao `split(",", -1)`? | Mục 2.4. |
| File hỏng một dòng thì sao? | Dòng đó bị bỏ và được đếm (`N damaged line(s) were ignored.`); các dòng khác vẫn nạp. |
| Bấm Load khi đang có thay đổi chưa lưu? | File **thay** collection: thay đổi mất. Đề không nói gì; muốn chặn thì xem mục 8. |
| Sao năm tối đa là hằng 2100 mà không `Year.now()`? | Đề không cho luật. Dùng năm hiện tại thì câu lỗi đổi mỗi năm; showroom cũng hay bán xe đời năm sau. |

### Access modifier, static, tham số

| Câu hỏi | Trả lời mẫu |
|---|---|
| `static` ở đâu? | `Validation`, `FileUtils` (utils — Guide bắt), hằng trong `Message`/`Constants`, `VehicleType.findByCode/findByMenuChoice` (làm việc trên cả enum), **hàm** trong `Main`. Không có biến static. |
| `protected` ở đâu? | `Vehicle()` (chỉ lớp con gọi) và `getDetailData()` (bước lớp con điền cho Template Method). |
| Hàm nào quá 2 tham số? | `Validation.getChoice(input, min, max)` (utils được 3). Constructor enum `NumberField(…6 tham số)` được miễn. Còn lại ≤ 2: prompt, regex, lỗi được gói trong enum `TextField`/`NumberField`, nên `Main.inputText(sc, field)` chỉ cần 2; `Main.addVehicle(controller, requestDTO)` nhận DTO đã nhập sẵn. |
| Sao có enum `TextField`/`NumberField`? | Có 9 ô nhập, mỗi ô 2 kiểu (thêm / sửa). Không gói luật vào enum thì phải viết 18 vòng hỏi lại gần giống nhau, hoặc truyền 4 tham số. |

### Tờ checklist 25 mục — bài này đạt thế nào

| Mục | Chỉ vào đâu |
|---|---|
| **1.1** MVC + repository | `repository/VehicleRepository` (bắt buộc có); `VehicleController` chỉ import `constants/dto/service/view`, không import `model`; `VehicleView` nhận `responseDTO` qua setter, `display()` không tham số, gọi **1 lần/luồng**; mỗi luồng trong `Main` gọi controller 1 lần (`inputUpdate` thêm `findVehicleType` **chỉ để kiểm**, lý do ở mục 3); `Main.readDataFile` đọc tệp qua `FileUtils` |
| **1.3** interface / exception | `ISoundable` (bản cũ `Soundable`); bài không tự viết lớp exception (dùng `Exception(Message.X)`) |
| **1.4** tên method | mọi tên mở đầu bằng động từ: `findByCode`/`findByMenuChoice` (bản cũ `fromCode`/`fromMenuChoice`), `convertToRow`, `formatLoadMessage`; `Main.submitVehicle` cũ nay là `addVehicle` |
| **1.5** tên collection / Id | `vehicleList`, `loadedList`, `foundList`, `sortedList`, `rowList`, `lineList`, `partArray`; không tên biến/hàm nào chứa `ID` (chỉ hằng `COL_ID`, `INVALID_ID`, `TextField.ID`) |
| **2.6 / 3.7** khai báo đầu block + khởi tạo | `Main.main`: `requestDTO = null`, `running = true`, `choice = 0`; mọi `Main.inputX`: `String line = ""` rồi trong vòng lặp chỉ gán; `Main.inputChanges`: `Double year = null` ở đầu hàm; `Validation`: `int choice = 0`, `double value = 0` |
| **2.8** dòng trống | giữa các field/hằng (cả `Constants`, `Message`, DTO, enum), sau vùng khai báo biến, trước mọi comment đứng sau dòng code, giữa các `case`, sau `}` trước câu lệnh tiếp |
| **3.3** ngoặc | `Validation.getChoice`: `if ((choice < min) \|\| (choice > max))`; `checkNumber`: `field.isWholeNumber() && (value != Math.floor(value))`; `VehicleService.loadData`: `(vehicle == null) \|\| isLoaded(…)`; `Main.inputChanges`: `(year == null) ? null : year.intValue()` |
| **3.4** lớp chỉ có static | `Main`, `Validation`, `FileUtils`, `Constants`, `Message`: `final` + `private` constructor |
| **3.8** cộng chuỗi | `Vehicle.toDataLine`, `Car/Motorbike.getDetailData` dùng `String.join` (bản cũ `+`); `VehicleFactory.applyCarChanges` dùng `StringBuilder` |

---

## 8. Thầy đổi yêu cầu tại chỗ

| Thầy bảo | Sửa file | Không phải đụng |
|---|---|---|
| **Thêm loại Truck** (tải trọng, có còi) | ① `model/Truck extends Vehicle implements ISoundable` ② `VehicleType.TRUCK("TRUCK", "Truck", 3)` ③ 1 `case` trong `VehicleFactory.createVehicle` + nhánh `instanceof Truck` trong `applyChanges` ④ 1 `case` trong `VehicleFactory.readDetails` ⑤ 1 `case` trong `Main.inputVehicle/inputChanges` + dòng submenu | `VehicleService`, `VehicleView`, `VehicleController`, repository, 2 Comparator — mục 6.2 **tự** kêu vì Truck là `ISoundable` |
| 6.2 tăng dần | đảo `first`/`second` trong `VehiclePriceComparator` (hoặc thêm class mới) | mọi file khác |
| Tìm theo hãng | `TextField.BRAND` đã có; thêm `searchByBrand` ở service + controller + 1 dòng submenu + 1 hàm trong `Main` | model, file |
| Tự Load khi mở chương trình | gọi `readDataFile()` rồi `controller.loadData(…)` trước vòng `while` trong `Main.main` (bọc `try`) | service |
| Hỏi lưu trước khi Quit / trước khi Load đè | thêm cờ `changed` vào `VehicleRepository` (bật ở add/update/delete, tắt ở load/store) + hàm kiểm ở service/controller; `Main` hỏi Y/N trước (một lần gọi **chỉ để kiểm**, ghi lý do) | model, view |
| Tên được chứa dấu phẩy | đổi `Constants.DATA_SEPARATOR` sang `;` hoặc `\|` + sửa `NAME_PATTERN` | code đọc/ghi (đều dùng hằng) |

---

## 9. Chỗ khác với đề / bản cũ

| Chỗ | Đề / bản cũ | Bài này | Lý do |
|---|---|---|---|
| Quit | đề: *"Others- Quit"*; bản 15/09: menu **8. Quit**, số khác 1–8 báo lỗi | bản 21/09: menu in đúng dòng `Others- Quit`; **số nào khác 1–7** cũng thoát (`Goodbye.`); chữ không phải số thì `Please enter a number.` | đối chiếu đề từng chữ; chữ không phải số là lỗi nhập nên hỏi lại (*"All errors must be handled"*) |
| Chữ menu | bản 15/09: `4. Delete vehicle by ID`, `1. Search by name` | `4. Delete vehicle ID`, `1. Search by name(descending)` — đúng chữ đề | đối chiếu đề |
| `makeSound` | đề: *"print out the message"* | **trả** `"Tin tin tin"`, view in; có comment `// brief:` ở `ISoundable` | Guide + tờ checklist 1.1: model không in; màn hình giống hệt. **Nên hỏi thầy** nếu thầy muốn `makeSound` tự in |
| Search by name | *"(descending)"* | tên **Z→A**, hoà thì theo id | đề không nói giảm theo gì; tên là cột đang tìm |
| Ràng buộc từng ô | đề: *"constraints must be checked"* nhưng không liệt kê | mục 2.7 (giữ như bản tham chiếu) | *"perform a requirements analysis step"* |
| Id trùng khi thêm | bản tham chiếu: báo lỗi rồi hỏi "Add another?"; bản 15/09: báo lỗi và **hỏi lại id** ngay | bản 21/09: nhập đủ các ô rồi báo `Vehicle ID c001 already exists.`, xe không được thêm, rồi hỏi `Add another vehicle? (Y/N):` | Guide: mỗi luồng gọi controller 1 lần; hỏi lại id ngay phải gọi controller trong vòng hỏi lại, mà đề phần Add không bắt kiểm ngay |
| Prompt khi sửa | bản tham chiếu: `Name [Ranger]: `; bản 15/09: in dòng xe hiện tại rồi `New name: ` | bản 21/09: `New name: …` ngay sau câu hướng dẫn, **không** in dòng xe trước; dòng xe mới in sau `Update successfully!` | giữ hàm nhập 2 tham số (luật thầy); lần gọi kiểm id không được render (render 1 lần/luồng) |
| Delete | bản 15/09: in dòng xe, hỏi `Delete this vehicle? (Y/N):`; id không có → `Vehicle does not exist` | bản 21/09: hỏi `Delete vehicle C001? (Y/N):` rồi `Delete successfully!` / `Delete failed!` (id không có) / `Delete cancelled.` | một lần gọi controller; đề chỉ bắt *"confirm message"* + *"success or fail"* |
| Hỏi lưu khi Quit | đề không có; bản 15/09: hỏi khi có thay đổi chưa lưu | bản 21/09: **bỏ** — Quit chỉ in `Goodbye.` | đề chỉ ghi *"Others- Quit"*; hỏi thì Quit phải gọi controller 2 lần (kiểm + lưu). Muốn có lại: mục 8 |
| Kiểm tự động | 5 kịch bản tham chiếu nối nhau qua file | 3 kịch bản riêng, mỗi cái đọc `vehicles.txt` mẫu (cập nhật theo màn hình 21/09) | `verify.py` chạy mỗi kịch bản trong thư mục mới |
| Kiến trúc | bản cũ: `entity/bo/ui`, Scanner static trong `Validator`, controller in ra, `List<Vehicle>`; bản 15/09: `VehicleFileService` tự đọc tệp, View có `showMessage/displayList/displaySoundList` nhận tham số, controller render 2 lần | MVC Guide + tờ checklist: Main đọc tệp, **một** `VehicleService`, View nhận `VehicleResponseDTO` qua thuộc tính, `ArrayList<Vehicle> vehicleList` | luật thầy |
| Tên theo tờ checklist | bản 15/09: `Soundable`, `fromCode/fromMenuChoice`, `vehicles`, `parts`, `lines` | `ISoundable`, `findByCode/findByMenuChoice`, `vehicleList`, `partArray`, `lineList` | mục 1.3, 1.4, 1.5 |
