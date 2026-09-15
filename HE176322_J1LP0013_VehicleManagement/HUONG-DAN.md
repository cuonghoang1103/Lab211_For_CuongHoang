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

| Function | Đề yêu cầu | Bài này |
|---|---|---|
| 0 · Cấu trúc dữ liệu | *"Classes, abstract classes, Interfaces. Use only one collection"* | `Vehicle` (abstract) ← `Car`, `Motorbike`; interface `Soundable`; **một** `ArrayList<Vehicle>` |
| 1 · Load | đọc hết `vehicles.txt` vào collection | `VehicleFileService.loadFromFile` |
| 2 · Add | submenu (Car/Motorbike), **kiểm ràng buộc**, hỏi tiếp hay về menu | `Main.addVehicles` → `VehicleService.addVehicle` |
| 3 · Update | nhập id; không có thì **"Vehicle does not exist"**; **bỏ trống = giữ cũ**; kiểm ràng buộc; in kết quả | `Main.updateVehicle` → `VehicleService.updateVehicle` |
| 4 · Delete | theo id, **hỏi xác nhận**, báo success/fail | `Main.deleteVehicle` → `VehicleController.deleteVehicle` |
| 5.1 · Search by name | tên **chứa** chuỗi, in **giảm dần** | `VehicleService.searchByName` + `VehicleNameComparator` |
| 5.2 · Search by id | id trùng khớp | `VehicleService.findVehicle` |
| 6.1 · Show all | in cả show room | `VehicleService.getAllVehicles` |
| 6.2 · Show by price | giá **giảm dần**; xe máy thì gọi **`makeSound`** ("Tin tin tin") | `VehiclePriceComparator` + `Soundable` |
| 7 · Store | ghi collection ra file | `VehicleFileService.storeToFile` |
| Others | Quit | menu 8 |

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
public class Motorbike extends Vehicle implements Soundable { speed, requireLicense }
public interface Soundable { String makeSound(); }                       // "kêu được"
```

| | `Vehicle` (abstract class) | `Soundable` (interface) |
|---|---|---|
| Nói gì | **là gì**: mọi xe đều có 5 field chung | **làm được gì**: thứ này kêu được |
| Có field, có code? | có (5 field, `toDataLine`) | không, chỉ có hợp đồng |
| Lớp con có bao nhiêu | chỉ `extends` **một** | `implements` **nhiều** được |
| Vì sao bài dùng | để **một** `ArrayList<Vehicle>` chứa cả Car lẫn Motorbike | để mục 6.2 hỏi *"xe này kêu được không?"* (`instanceof Soundable`), không hỏi *"có phải Motorbike không?"* — mai thêm xe tải có còi thì chỉ cần `implements Soundable` |

### 2.2 Đa hình — mỗi xe tự trả lời

| Lời gọi | `Car` trả | `Motorbike` trả |
|---|---|---|
| `getType()` | `CAR` | `MOTORBIKE` |
| `getDetails()` | `Type: Travel, Year: 2020` | `Speed: 150.0km/h, License: Yes` |
| `getDetailData()` (ghi file) | `Travel,2020` | `150.0,true` |
| `makeSound()` | — (không `implements Soundable`) | `Tin tin tin` |

`VehicleService.toResponse` gọi `vehicle.getDetails()` **mà không cần biết** đó là xe gì.

### 2.3 Template Method — `Vehicle.toDataLine()`

```java
public final String toDataLine() {          // khung CỐ ĐỊNH của một dòng file
    return getType().getCode() + "," + id + "," + name + "," + color + ","
            + price + "," + brand + "," + getDetailData();   // ← bước lớp con điền
}
```

Mỗi dòng `vehicles.txt` có **8 cột**: `loại, id, tên, màu, giá, hãng, chi tiết 1, chi tiết 2`.

```
CAR,C001,Camry,Black,35000.0,Toyota,Travel,2020
MOTORBIKE,M001,Exciter 150,Blue,2500.0,Yamaha,150.0,true
```

### 2.4 Đọc / ghi file

| Việc | Code | Vì sao |
|---|---|---|
| Đọc từng dòng | `BufferedReader` + `readLine()` tới khi `null` | đọc theo dòng, nhanh |
| Đóng file chắc chắn | `try (BufferedReader r = …) { … }` (try-with-resources) | lỗi giữa chừng vẫn đóng; trên Windows file không đóng thì bị khoá và lần ghi sau hỏng |
| Tiếng Việt | `InputStreamReader(…, StandardCharsets.UTF_8)` | không phụ thuộc bảng mã của máy |
| Ghi **đè** | `new FileOutputStream(fileName)` | ghi nối (append) thì mỗi lần Store file dài gấp đôi, lần Load sau toàn id trùng |
| Tách cột | `line.split(",", -1)` | `-1` giữ cả cột rỗng cuối dòng; không có thì dòng `…,Pickup,` bị tính thiếu cột |
| Dòng hỏng | `parseLine` trả `null` → đếm `skipped` | **một dòng hỏng chỉ mất một dòng**, không mất cả file |
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
| id | 1 chữ cái + 3 chữ số (`C001`), không trùng (không phân biệt hoa thường) | `ID must be one letter followed by 3 digits, for example C001.` · `Vehicle ID c001 already exists.` |
| name / brand | 2–30 / 2–20 ký tự chữ, số, cách, gạch ngang | `Name must be …` / `Brand must be …` |
| color | 2–15 chữ cái | `Color must be 2 to 15 letters.` |
| price | số > 0 (0.01 … 1 tỷ) | `Price must be a positive number.` |
| type (Car) | Sport / Travel / Family / Pickup (gõ thường vẫn nhận, lưu `Travel`) | `Type must be one of: …` |
| year (Car) | số **nguyên** 1900–2100 | `Year of manufacture must be from 1900 to 2100.` |
| speed (Motorbike) | 1–400 km/h | `Speed must be a number from 1 to 400 km/h.` |
| license | Y / N | `Please enter Y or N.` |

Không ô chữ nào cho gõ **dấu phẩy**, vì dấu phẩy là ký tự phân cách cột trong file.

---

## 3. Thiết kế

```
HE176322_J1LP0013_VehicleManagement/
├── vehicles.txt                       5 xe mẫu (đọc bằng Function 1)
└── src/
    ├── constants/  Message.java        mọi câu chữ, 4 menu
    │               Constants.java      số menu, regex, giới hạn, cột file, định dạng bảng
    │               VehicleType.java    «enum» CAR / MOTORBIKE: chữ trong file, nhãn, số submenu
    │               TextField.java      «enum» ID, NAME, COLOR, BRAND, CAR_TYPE, KEYWORD: prompt + regex + lỗi
    │               NumberField.java    «enum» PRICE, YEAR, SPEED: prompt + khoảng + lỗi
    ├── model/      Soundable.java      «interface» makeSound()
    │               Vehicle.java        «abstract» 5 field chung + Template Method toDataLine
    │               Car.java            extends Vehicle
    │               Motorbike.java      extends Vehicle implements Soundable
    ├── dto/        VehicleRequestDTO   main ──► controller (Double/Integer/Boolean: null = giữ cũ)
    │               VehicleResponseDTO  1 dòng bảng ──► view (+ sound)
    │               FileResponseDTO     số xe đọc/ghi + số dòng hỏng
    ├── repository/ VehicleRepository   MỘT ArrayList<Vehicle> + cờ "đã đổi mà chưa lưu"
    ├── service/    VehicleFactory      Factory: loại → class; applyChanges (null = giữ)
    │               VehicleService      add / update / delete / search / sort
    │               VehicleFileService  Function 1 và 7
    │               VehicleNameComparator   Strategy: tên Z→A
    │               VehiclePriceComparator  Strategy: giá cao→thấp
    ├── controller/ VehicleController   Facade + nơi lắp ráp
    ├── view/       VehicleView         bảng, "Tin tin tin", câu thông báo
    ├── utils/      Validation          kiểm dạng (dùng cho cả phím lẫn file)
    │               FileUtils           đọc/ghi dòng (UTF-8, try-with-resources)
    └── main/       Main                4 menu + Scanner + các vòng hỏi lại
```

| Lớp | Làm gì | Không được làm |
|---|---|---|
| `Main` | in menu, **đọc bàn phím**, gói DTO | import model/view/service/repository |
| `VehicleController` | lắp ráp; DTO → service → view | Scanner, `System.out`, static, import model |
| `VehicleService`, `VehicleFileService` | luật, sắp xếp, đổi model ⇄ DTO, file ⇄ model | in ra, đọc phím |
| `VehicleFactory` | biết loại nào dùng class nào | in ra |
| `VehicleRepository` | giữ collection, CRUD, cờ `changed` | kiểm luật |
| `Vehicle`, `Car`, `Motorbike` | mô tả xe, tự trả chi tiết / âm thanh | **in** (vì thế `makeSound` **trả** chuỗi, view in) |
| `Validation`, `FileUtils` | kiểm dạng; đọc/ghi dòng | biết gì về xe |

**Luồng Update:**

```
Main: đọc id (sai dạng → hỏi lại) ──► controller.findVehicle(dto)
          └─► service.requireVehicle: không có → throw "Vehicle does not exist" → về menu
          └─► view in dòng xe hiện tại
Main: "Leave a field blank to keep the current value."
      đọc từng ô: trống → null · sai → hỏi lại · đúng → giá trị
      ô riêng theo loại (found.getVehicleType())
      controller.updateVehicle(dto)
          └─► factory.applyChanges(vehicle, dto)   chỉ gán ô khác null
          └─► repository.updateVehicle(vehicle)    changed = true
          └─► view "Update successfully!" + dòng xe mới
```

### 3.1 Design Pattern trong bài

**1. Factory** (Creational) — pattern chính, trả lời câu *"adding a new vehicle is easy"*

| Yếu tố | Trong bài |
|---|---|
| **Problem** | Xe được tạo ở **2 chỗ**: gõ phím (Function 2) và đọc file (Function 1). Nếu mỗi chỗ tự `if … new Car() else new Motorbike()` thì thêm loại mới phải sửa 2 chỗ, và dễ quên một chỗ. |
| **Solution** | `VehicleFactory.createVehicle(dto)` là **nơi duy nhất** có `new Car()` / `new Motorbike()` (`switch` theo `VehicleType`). Cả `VehicleService.addVehicle` lẫn `VehicleFileService.parseLine` đều gọi nó. |
| **Consequences** | ✅ Thêm loại: 1 class model + 1 hằng enum + 1 `case` factory (mục 8). ❌ Thêm một lớp trung gian. |

**2. Template Method** (Behavioral) — `Vehicle.toDataLine()` (mục 2.3).

**3. Strategy** (Behavioral) — `Comparator<Vehicle>`

| Yếu tố | Trong bài |
|---|---|
| **Problem** | Hai thứ tự khác nhau: tên giảm dần (5.1), giá giảm dần (6.2). Thầy hay bảo *"đổi sang tăng dần"*. |
| **Solution** | `VehicleNameComparator`, `VehiclePriceComparator` = ConcreteStrategy. `VehicleService` giữ 2 biến **kiểu interface** `Comparator<Vehicle>` và gọi `Collections.sort(list, priceOrder)`. |
| **Consequences** | ✅ Đổi thứ tự thì thay class, không sửa vòng lặp nào. ❌ 2 file nhỏ. |

**Các pattern khác:** **Facade** (`VehicleController`) · **Repository** (`VehicleRepository`) · **DTO** · **MVC**.
Interface `Soundable` là **thiết kế theo khả năng**: `VehicleService.toResponse` hỏi `instanceof Soundable`, không hỏi `instanceof Motorbike`.

### 3.2 SOLID trong bài

| Chữ | Ở đâu |
|---|---|
| **S** | `VehicleService` (luật), `VehicleFileService` (file), `VehicleFactory` (tạo), `VehicleRepository` (lưu), `VehicleView` (in), `FileUtils` (đọc/ghi dòng), `Validation` (kiểm dạng) |
| **O** | thêm thứ tự sắp = thêm Comparator; thêm xe kêu được = `implements Soundable`, view không đổi |
| **L** | mọi chỗ nhận `Vehicle` chạy đúng với `Car` lẫn `Motorbike` (bảng, file, sắp giá) |
| **I** | `Soundable` chỉ 1 hàm; `Car` không bị ép viết `makeSound` |
| **D** | 2 service nhận repository + factory **qua constructor**; `VehicleService` giữ `Comparator<Vehicle>` (trừu tượng) |

---

## 4. Code từng bước

> Thầy: *"code cái **model trước rồi đến data**"*.

| Bước | File | Việc |
|---|---|---|
| 1 | `constants/VehicleType.java` | enum 2 loại + `fromCode` + `fromMenuChoice` |
| 2 | `model/Soundable.java` | interface 1 hàm |
| 3 | `model/Vehicle.java` | abstract, 5 field, get/set, 3 hàm abstract, `final toDataLine` |
| 4 | `model/Car.java`, `Motorbike.java` | field riêng, `@Override` `getType/getDetails/getDetailData`; Motorbike thêm `makeSound` |
| 5 | `constants/TextField`, `NumberField`, `Message`, `Constants` | luật từng ô ở **một** chỗ |
| 6 | `dto/*` | JavaBean; request dùng lớp bọc |
| 7 | `repository/VehicleRepository` | ArrayList + CRUD + `replaceAll` + cờ `changed` |
| 8 | `utils/Validation`, `FileUtils` | `getChoice/checkText/checkNumber/getYesNo/checkBoolean`; `readLines/writeLines` |
| 9 | `service/VehicleFactory` | `createVehicle`, `applyChanges` |
| 10 | `service/VehicleNameComparator`, `VehiclePriceComparator` | `compare` + hoà thì theo id |
| 11 | `service/VehicleService`, `VehicleFileService` | Function 2–6, Function 1 và 7 |
| 12 | `view/VehicleView` | `displayVehicle`, `displayList`, `displaySoundList`, `showMessage` |
| 13 | `controller/VehicleController` | constructor dùng chung **một** repository + **một** factory cho 2 service |
| 14 | `main/Main` | menu chính + 3 submenu + các hàm `input*` / `inputNew*` |

**Bẫy hay gặp**

1. **Hai `ArrayList`** (một cho Car, một cho Motorbike): trái đề *"only one collection"*, và sắp theo giá không trộn được hai loại.
2. **Load mà cộng thêm** vào collection cũ: bấm Load 2 lần là mọi xe bị nhân đôi. `replaceAll` xoá rồi mới nạp.
3. **Ghi file nối đuôi** (append): xem mục 2.4.
4. **`makeSound` in thẳng trong model**: trái Guide (model không in). Bài này cho nó **trả** chuỗi, `VehicleView` in.
5. **So giá bằng phép trừ ép `int`**: xem mục 2.5.
6. **Update gán cả ô trống**: tên xe thành chuỗi rỗng. Phải để `null` = giữ.
7. **`sc.nextLine()` trong `try`**: khi hết input, vòng hỏi lại quay vô hạn. Bài này đọc dòng trước `try`.

---

## 5. Test trước khi gọi thầy

> Thầy: *"test tất cả các happy case cũng như hiển thị đủ các message validation"*.

| # | Chọn | Gõ | Phải thấy |
|---|---|---|---|
| 1 | menu | `abc`, `9` | `Please choose from 1 to 8.` |
| 2 | 6 → 1 / 6 → 2 khi chưa Load | — | `The show room is empty.` |
| 3 | 1 | — | `Loaded 5 vehicle(s) from vehicles.txt` |
| 4 | 6 → 1 | — | 5 dòng **đúng thứ tự file** + `Total: 5 vehicle(s)` |
| 5 | 6 → 2 | — | Ranger 48,000 → Camry → Civic → Exciter + **`Tin tin tin`** → Vision + **`Tin tin tin`** |
| 6 | 5 → 1 | `i` | Vision, Exciter 150, Civic (tên **Z→A**) |
| 7 | 5 → 1 | `zzz` / trống | `No vehicle found.` / `Search text must not be empty.` |
| 8 | 5 → 2 | `c002` / `C999` / `c1` | dòng Ranger (id gõ thường vẫn ra) / `Vehicle does not exist` / lỗi dạng id |
| 9 | 2 → 1 | id `c1`; tên `A`; màu `Bl4ck`; giá `-5`, `abc`, `NaN`; hãng `!`; type `Racing`; năm `1800`, `2020.5` | từng câu lỗi ở mục 2.7, hỏi lại **đúng ô đó** |
| 10 | 2 → 2 | id đã có (`c001`) | `Vehicle ID c001 already exists.` và hỏi lại id |
| 11 | 2 → 2 | speed `500`; license `maybe` | `Speed must be …` · `Please enter Y or N.` |
| 12 | sau khi thêm | `x`, rồi `n` | `Please enter Y or N.` → về menu |
| 13 | 3 | `X999` | `Vehicle does not exist` |
| 14 | 3 | `c001` → trống, `Silver`, trống, trống, `sport`, trống | `Update successfully!` — chỉ màu và type đổi (`Sport`) |
| 15 | 3 | giá `abc` rồi trống | `Price must be a positive number.` rồi giữ giá cũ |
| 16 | 4 | `Z001` / `M001` → `n` / `m001` → `y` | `Vehicle does not exist` / `Delete cancelled.` / `Delete successfully!` |
| 17 | 7 rồi 1 | — | `Stored 4 vehicle(s) …` rồi `Loaded 4 vehicle(s) …` |
| 18 | 8 khi có thay đổi | `n` / `y` | hỏi `There are unsaved changes. Store them before quitting? (Y/N):` → không ghi / ghi rồi `Goodbye.` |
| 19 | sửa tay `vehicles.txt`: thiếu cột, giá `abc`, loại `TRUCK`, license `yes`, id trùng | 1 | chỉ nạp dòng đúng + `5 damaged line(s) were ignored.` |
| 20 | xoá `vehicles.txt` rồi 1 | — | `Data file vehicles.txt does not exist.` |

---

## 6. Debug

| Việc | Cách làm |
|---|---|
| Thấy đa hình | breakpoint `row.setDetails(vehicle.getDetails());` trong `VehicleService.toResponse` → **F7**: lúc vào `Car.getDetails`, lúc vào `Motorbike.getDetails` |
| Thấy Factory | breakpoint `switch (requestDTO.getVehicleType())` trong `VehicleFactory.createVehicle`, chạy Function 1: mỗi dòng file dừng một lần |
| Dòng hỏng | breakpoint `return null;` trong `catch` của `VehicleFileService.parseLine`, sửa file cho giá = `abc` → Variables: `line`, `e.getMessage()` |
| Sắp giá | breakpoint trong `VehiclePriceComparator.compare` → xem `byPrice` âm/dương |
| Update giữ cũ | breakpoint `if (requestDTO.getName() != null)` trong `applyChanges` → bỏ trống tên thì thấy `null` và nhánh bị bỏ qua |

---

## 7. Câu hỏi thầy hay hỏi

### OOP

| Câu hỏi | Trả lời mẫu |
|---|---|
| 4 tính chất OOP ở đâu? | **Đóng gói**: field `private` + get/set; repository trả **bản sao** list. **Kế thừa**: `Car`, `Motorbike extends Vehicle`. **Đa hình**: `getType/getDetails/getDetailData` chạy bản của lớp thật; `Collections.sort` gọi `compare` qua kiểu `Comparator`. **Trừu tượng**: `Vehicle` abstract, `Soundable` interface. |
| Abstract class khác interface? | Mục 2.1. Một câu: abstract class là **"là gì"** (có field, code chung); interface là **"làm được gì"** (hợp đồng, implements nhiều). |
| Sao `Vehicle` abstract? | Show room không có "chiếc xe chung chung"; mỗi xe là Car hoặc Motorbike. Compiler chặn `new Vehicle()`. |
| Sao `toDataLine` `final`? | Để lớp con không đổi được thứ tự 6 cột đầu, vì Load đọc theo đúng thứ tự đó. |
| Sao `makeSound` trả `String` mà không in? | Guide: model **không** in. Màn hình vẫn đúng `Tin tin tin` như đề, chỉ khác là view in. |
| `instanceof` có trái đa hình không? | `instanceof Soundable` hỏi **khả năng** (interface), không hỏi class. `VehicleFactory.applyChanges` dùng `instanceof Car/Motorbike` vì DTO chung phải chép vào **field riêng**. Đó là chỗ duy nhất, và nó nằm trong factory. |

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
| `static` ở đâu? | `Validation`, `FileUtils` (utils — Guide bắt), hằng trong `Message`/`Constants`, `VehicleType.fromCode/fromMenuChoice` (làm việc trên cả enum), **hàm** trong `Main`. Không có biến static. |
| `protected` ở đâu? | `Vehicle()` (chỉ lớp con gọi) và `getDetailData()` (bước lớp con điền cho Template Method). |
| Hàm nào quá 2 tham số? | `Validation.getChoice(input, min, max)` (utils được 3). Constructor enum `NumberField(…6 tham số)` được miễn. Còn lại ≤ 2: prompt, regex, lỗi được gói trong enum `TextField`/`NumberField`, nên `Main.inputText(sc, field)` chỉ cần 2. |
| Sao có enum `TextField`/`NumberField`? | Có 9 ô nhập, mỗi ô 2 kiểu (thêm / sửa). Không gói luật vào enum thì phải viết 18 vòng hỏi lại gần giống nhau, hoặc truyền 4 tham số. |

---

## 8. Thầy đổi yêu cầu tại chỗ

| Thầy bảo | Sửa file | Không phải đụng |
|---|---|---|
| **Thêm loại Truck** (tải trọng, có còi) | ① `model/Truck extends Vehicle implements Soundable` ② `VehicleType.TRUCK("TRUCK", "Truck", 3)` ③ 1 `case` trong `VehicleFactory.createVehicle` + nhánh `instanceof Truck` trong `applyChanges` ④ 1 `case` trong `VehicleFileService.readDetails` ⑤ 1 `case` trong `Main.inputFields/inputChanges` + dòng menu | `VehicleService`, `VehicleView`, repository, 2 Comparator — mục 6.2 **tự** kêu vì Truck là `Soundable` |
| 6.2 tăng dần | đảo `first`/`second` trong `VehiclePriceComparator` (hoặc thêm class mới) | mọi file khác |
| Tìm theo hãng | `TextField.BRAND` đã có; thêm `searchByBrand` ở service + 1 dòng submenu | model, file |
| Tự Load khi mở chương trình | gọi `controller.loadFromFile()` trước vòng `while` trong `Main.main` (bọc `try`) | service |
| Hỏi trước khi Load đè thay đổi | trong `Main`: nếu `controller.hasUnsavedChanges()` thì hỏi Y/N rồi mới Load | service |
| Tên được chứa dấu phẩy | đổi `Constants.DATA_SEPARATOR` sang `;` hoặc `\|` + sửa `NAME_PATTERN` | code đọc/ghi (đều dùng hằng) |

---

## 9. Chỗ khác với đề / bản cũ

| Chỗ | Đề / bản cũ | Bài này | Lý do |
|---|---|---|---|
| Quit | đề: *"Others- Quit"* | menu **8. Quit**; số khác 1–8 thì báo lỗi | gõ nhầm `9` không được làm mất việc đang làm |
| `makeSound` | đề: *"print out the message"* | **trả** `"Tin tin tin"`, view in | Guide: model không in; màn hình giống hệt |
| Search by name | *"(descending)"* | tên **Z→A**, hoà thì theo id | đề không nói giảm theo gì; tên là cột đang tìm |
| Ràng buộc từng ô | đề: *"constraints must be checked"* nhưng không liệt kê | mục 2.7 (giữ như bản tham chiếu) | *"perform a requirements analysis step"* |
| Id trùng khi thêm | bản cũ: báo lỗi rồi hỏi "Add another?" | báo lỗi và **hỏi lại id** | không bắt người dùng chọn lại menu |
| Prompt khi sửa | bản cũ: `Name [Ranger]: ` | `New name: ` sau khi đã in dòng xe | giữ hàm nhập 2 tham số (luật thầy); giá trị cũ đã in ngay trên |
| Hỏi lưu khi Quit | đề không có | chỉ hỏi khi có thay đổi chưa lưu | tránh mất dữ liệu do bấm nhầm |
| Kiểm tự động | 5 kịch bản tham chiếu nối nhau qua file | 3 kịch bản riêng, mỗi cái đọc `vehicles.txt` mẫu | `verify.py` chạy mỗi kịch bản trong thư mục mới |
| Kiến trúc | bản cũ: `entity/bo/ui`, Scanner static trong `Validator`, controller in ra, `List<Vehicle>` | MVC Guide + Factory + Template Method + Strategy, `ArrayList<Vehicle>` | luật thầy |
