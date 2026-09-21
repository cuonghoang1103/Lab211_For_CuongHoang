# J1.S.P0052 — East Asia Countries

> Bài **kế thừa + đa hình**: `EastAsiaCountries extends Country`, gọi `super(...)`, ghi đè `display()`.
> Hai chỗ thầy chắc chắn hỏi: **vì sao field là `protected`** (đề bắt — phải giải thích được), và
> **vì sao `display()` trả `String` chứ không in** (luật Guide: model không được in).

| | |
|---|---|
| Loại / LOC | Short Assignment · 69 LOC · 1 slot |
| Project | `HE176322_J1SP0052_EastAsiaCountries` |
| Chạy | NetBeans: **File ▸ Open Project** → **F6** |
| Lớp chạy | `main.Main` |
| Kiểm tự động | `python3 _tools/verify.py J1SP0052` → 5 kịch bản × 2 locale |
| `.lint-allow` | `protected_ok=countryCode,countryName,totalArea` — 3 field **đề khai `protected`** |

---

## 1. Đề bài nói gì

- Lớp **`Country`**: `protected String countryCode`, `protected String countryName`,
  `protected float totalArea`; **2 constructor** (có/không tham số); get/set đủ; hàm **`display()`**.
- Lớp **`EastAsiaCountries extends Country`**: thêm `private String countryTerrain`; constructor
  có tham số dùng **`super`**; **ghi đè `display()`**.
- Lớp **`ManageEastAsiaCountries`** làm 5 chức năng: nhập (tối đa **11** nước) · hiện nước **vừa nhập** ·
  tìm theo tên · hiện danh sách **sắp theo tên tăng dần** · thoát.
- Kiểm: **Total area > 0**.

**Đề bắt buộc:**

| Thứ | Đề viết | Bài này đặt ở |
|---|---|---|
| `addCountryInformation(EastAsiaCountries country) throws Exception` | Function 1 | `ManageEastAsiaCountries` — **đúng chữ ký đề**; service/controller có hàm cùng tên nhận DTO |
| `EastAsiaCountries getRecentlyEnteredInformation() throws Exception` | Function 2 | `ManageEastAsiaCountries` — **đúng chữ ký đề** |
| `EastAsiaCountries[] searchInformationByName(String name) throws Exception` | Function 3 | `ManageEastAsiaCountries` — **đúng chữ ký đề** |
| `EastAsiaCountries[] sortInformationByAscendingOrder() throws Exception` | Function 4 | `CountryService` (thuật toán → service) — trả `CountryResponseDTO` (các dòng trong `rowList`) |
| `display()` ở `Country`, ghi đè ở `EastAsiaCountries` | *"display information of one country"* | `model/` — **trả `String`**, `CountryView` in |

---

## 2. Kiến thức cần biết

### 2.1 Kế thừa + `super` + ghi đè — chạy tay `display()`

```java
Country c = new EastAsiaCountries("VN", "Viet Nam", 331698f, "Nice");
c.display();
```

| Bước | Chạy ở đâu | Kết quả |
|---|---|---|
| 1 | `new EastAsiaCountries(...)` → `super(code, name, area)` | 3 field `protected` của `Country` được gán |
| 2 | gán `countryTerrain = "Nice"` | field `private` của lớp con |
| 3 | `c.display()` — biến kiểu **`Country`** | Java chọn bản của **đối tượng thật** = `EastAsiaCountries.display()` (**đa hình**) |
| 4 | trong đó gọi `super.display()` | `"VN              Viet Nam        331698.0        "` (3 cột × 16 ký tự) |
| 5 | `String.format(Constants.TERRAIN_FORMAT, super.display(), countryTerrain)` (`"%s%s"`: 3 cột cha rồi cột Terrain — không cộng chuỗi) | `"VN              Viet Nam        331698.0        Nice"` |

`331698.0`: `float` in bằng `%s` dùng `Float.toString` — ra đúng dạng bảng của đề (và **không đổi
theo locale**, khác `%.1f`). Lưu ý: số ≥ 10 triệu sẽ in dạng `1.0E7`.

### 2.2 Sắp xếp theo tên — bubble sort, không phân biệt hoa thường

`["Viet Nam", "Indonesia", "japan"]`:

| Lượt | So sánh (`compareToIgnoreCase`) | Mảng |
|---|---|---|
| 1 | Viet Nam > Indonesia → đổi | Indonesia, Viet Nam, japan |
| 1 | Viet Nam > japan → đổi | Indonesia, japan, Viet Nam |
| 2 | Indonesia < japan → giữ | không đổi lần nào → **dừng** |

Dùng `compareTo` (phân biệt hoa thường) thì `"japan"` xếp **sau** `"Viet Nam"` vì chữ thường có mã lớn hơn — sai.

### 2.3 Java dùng trong bài

| API | Dùng làm gì |
|---|---|
| `extends`, `super(...)`, `super.display()` | kế thừa, gọi constructor cha, dùng lại hàm cha |
| `@Override` | compiler kiểm chữ ký hàm ghi đè |
| `String.format("%-16s", …)` | căn cột 16 ký tự |
| `Arrays.copyOf(arr, n)` | cắt mảng kết quả / sao chép trước khi sắp |
| `toLowerCase().contains(...)` | tìm "chứa", không phân biệt hoa thường |
| `Float.parseFloat` + `isNaN/isInfinite` | đọc diện tích; chặn `NaN`, `Infinity` |

---

## 3. Thiết kế

```
HE176322_J1SP0052_EastAsiaCountries/src/
├── model/      Country                    3 field protected + 2 ctor + get/set + display() trả String
│               EastAsiaCountries          extends Country + countryTerrain + display() @Override
├── dto/        CountryRequestDTO          code, name, area, terrain, searchName (main ──► controller)
│               CountryResponseDTO         message ("Successful") + rowList (dòng bảng) (controller ──► view)
├── repository/ ManageEastAsiaCountries    countryArray 11 ô + add / getRecently / search / getAll (chữ ký đề)
├── service/    ISortStrategy              «interface» sort(EastAsiaCountries[] countryArray)
│               NameAscendingSortStrategy  bubble sort theo tên, bỏ qua hoa thường
│               CountryService             DTO ↔ model, luật area > 0, sắp (Context); trả CountryResponseDTO
├── controller/ CountryController          cắm repository + strategy vào service; mỗi luồng render view 1 lần
├── view/       CountryView                field responseDTO + setResponseDTO() + display() KHÔNG tham số
├── constants/  Message, Constants         câu chữ; MAX_COUNTRIES = 11, định dạng cột
├── utils/      Validation                 getChoice, getNonBlank, getTotalArea
└── main/       Main                       final + private Main(); menu + Scanner + mọi validate
```

| Câu hỏi thiết kế | Trả lời |
|---|---|
| `ManageEastAsiaCountries` là tầng gì? | Đề đặt tên lớp này làm "nơi quản lý dữ liệu" → đúng vai **repository** (giữ mảng + thao tác dữ liệu đơn giản). Nhờ đó 3 hàm đề giữ **đúng chữ ký** (repository được làm việc với model). |
| Sao sort ở `service` chứ không ở repository? | Guide: nghiệp vụ ngoài CRUD (**thuật toán**) → service. |
| Sao controller không gọi thẳng repository? | Guide: *"Controller <-> Services <-> Repository"*: có service thì dữ liệu đi qua service. Controller chỉ **tạo** repository để đưa vào service. |
| Sao bài **có repository**? | Tờ checklist 1.1: *"Bắt buộc phải có repository"*. Ở bài này repository chính là lớp đề đặt tên `ManageEastAsiaCountries`: giữ `countryArray` (11 ô) + CRUD đơn giản; **không** tính toán, **không** in. |
| View nhận dữ liệu thế nào? | Qua **thuộc tính**, không qua tham số (tờ checklist 1.1): `CountryView` có field `responseDTO`, controller gọi `setResponseDTO(responseDTO)` rồi `display()` — **1 lần cho 1 luồng**. Option 1 set `message` = `Successful`; option 2/3/4 set `rowList` (các dòng bảng). |
| Validate ở đâu? | Ở **Main** qua `utils/Validation` (tờ checklist 1.1: *"Toàn bộ việc nhập dữ liệu/Validate … thực hiện ở Main"*): `getChoice`, `getNonBlank`, `getTotalArea`. Service chỉ **giữ lại** luật `area > 0` cho chắc — ném `Exception(Message.INVALID_AREA)`, không in. |
| Sao `display()` trả `String`? | Guide cấm model in. Tên hàm giữ đúng đề; model trả chữ → service bỏ vào DTO → **view in**. |

**Luồng chung** (mọi option): `Main` (nhập + validate) ──RequestDTO──► `Controller` ──► `Service`
──► `Repository` ──► `Model`; kết quả quay về thành **một** `CountryResponseDTO` ──► `View`
(`setResponseDTO` + `display()` **1 lần**). Lỗi: tầng dưới `throw new Exception(Message.X)` → **Main** in
`e.getMessage()`.

**Luồng Function 4:**

```
Main ──► controller.sortInformationByAscendingOrder()          (1 lần gọi controller)
   └─ service.sortInformationByAscendingOrder()
        ├─ repository.getAllCountries()        → BẢN SAO countryArray (rỗng? throw)
        ├─ sortStrategy.sort(countryArray)     ← NameAscendingSortStrategy
        └─ toResponse(countryArray)            → for (Country country : …) rowList.add(country.display())
                                                 ← đa hình: chạy bản EastAsiaCountries
   └─ view.setResponseDTO(responseDTO) ──► view.display()      (render 1 lần)
```

**Option 1 gọi controller 2 lần — có lý do.** `Main.inputCountry` gọi `controller.checkFull()` **chỉ để
kiểm** (ném `The list already has 11 countries.`, không render) **trước** 4 câu hỏi, rồi case 1 mới gọi
`controller.addCountryInformation(requestDTO)` (lưu + render 1 lần). Màn hình chạy chuẩn (kịch bản tham
chiếu đã đối chiếu đề) báo đầy **ngay** khi chọn 1 lần thứ 12, không bắt gõ 4 câu rồi mới vứt đi — muốn
giữ đúng màn hình đó thì phải hỏi controller trước khi hỏi người dùng (giống `checkExistDoctor` trong mẫu
P0055 của thầy). Nếu thầy đòi tuyệt đối 1 lần gọi: bỏ `checkFull()`, chấp nhận hỏi đủ 4 câu rồi mới báo
đầy (lỗi vẫn do `ManageEastAsiaCountries.addCountryInformation` ném).

### 3.1 Design Pattern — **Strategy** (thứ tự sắp xếp)

| Yếu tố | Trong bài này |
|---|---|
| **Name** | Strategy (nhóm Behavioral) |
| **Problem** | Đề sắp theo **tên tăng dần**; thầy rất hay đổi: *"sắp theo diện tích"*, *"tên giảm dần"*. Viết cứng trong service thì mỗi lần đổi phải mở service ra sửa. |
| **Solution** | `ISortStrategy` = **Strategy** (`sort(EastAsiaCountries[] countryArray)`). `NameAscendingSortStrategy` = **ConcreteStrategy**. `CountryService` = **Context**: nhận strategy qua **constructor**, gọi `sortStrategy.sort(countryArray)`. `CountryController` **chọn**: `new CountryService(new ManageEastAsiaCountries(), new NameAscendingSortStrategy())`. |
| **Consequences** | ✅ Thứ tự mới = **1 class mới** + sửa 1 dòng controller (**O**, **D**). ❌ Thêm 2 file. |

Còn có: **Facade** (`CountryController`), **Repository** (`ManageEastAsiaCountries`), và cặp
**kế thừa/ghi đè** `Country` → `EastAsiaCountries` (Template-kiểu nhẹ: lớp con dùng `super.display()` rồi thêm cột).

### 3.2 SOLID trong bài

| Nguyên lý | Ở đâu |
|---|---|
| **S** | `Country` mô tả nước · repository giữ mảng · `NameAscendingSortStrategy` sắp · service điều phối · view in |
| **O** | thêm thứ tự sắp không sửa `CountryService`; thêm loại nước (`SouthAsiaCountries`) không sửa `Country` |
| **L** | `EastAsiaCountries` thay được `Country` ở vòng `for (Country country : countryArray)` trong `toResponse` — `display()` vẫn trả một dòng bảng hợp lệ |
| **I** | `ISortStrategy` chỉ 1 hàm |
| **D** | `CountryService` phụ thuộc interface `ISortStrategy`, nhận qua constructor |

---

## 4. Code từng bước

> Thầy: *"code cái **model trước rồi đến data**"*.

| Bước | File | Việc |
|---|---|---|
| 1 | `model/Country.java` | 3 field **`protected`** (đề) + 2 constructor + get/set + `display()` trả `String` |
| 2 | `model/EastAsiaCountries.java` | `extends Country` + `private countryTerrain` + ctor rỗng + ctor có `super(...)` + get/set + `@Override display()` |
| 3 | `dto/CountryRequestDTO`, `CountryResponseDTO` | JavaBean; ResponseDTO có `message` + `rowList` |
| 4 | `repository/ManageEastAsiaCountries.java` | `countryArray` 11 ô + `count` + `isFull` + 3 hàm đề + `getAllCountries` |
| 5 | `service/ISortStrategy`, `NameAscendingSortStrategy` | interface (tên bắt đầu bằng `I`) + bubble sort |
| 6 | `service/CountryService.java` | `checkFull`, 4 hàm cùng tên đề (trả `CountryResponseDTO`), `toResponse(countryArray)` |
| 7 | `view/CountryView`, `controller/CountryController` | view: field `responseDTO` + `setResponseDTO` + `display()`; controller: mỗi hàm `setResponseDTO` rồi `display()` 1 lần |
| 8 | `constants/Message`, `Constants` | menu, prompt, lỗi; `MAX_COUNTRIES`, `COUNTRY_FORMAT`, `TERRAIN_FORMAT` |
| 9 | `utils/Validation.java` | `getChoice`, `getNonBlank`, `getTotalArea` |
| 10 | `main/Main.java` | `final` + `private Main()`; menu + `inputText/inputArea` + `inputCountry` (gọi `checkFull` trước) + `inputSearch`; mỗi case gọi controller 1 lần |
| 11 | `.lint-allow` | `protected_ok=countryCode,countryName,totalArea` |

**Bẫy hay gặp:**

1. Sắp **trực tiếp** mảng gốc → "nước vừa nhập" (phần tử cuối) bị đổi. Sắp **bản sao**.
2. Quên `super(...)` trong constructor lớp con → 3 field cha để `null`/`0`.
3. Ghi đè `display()` mà gõ lại cả 3 `%-16s` → một ngày lệch cột với lớp cha. Dùng `super.display()`.
4. `Float.parseFloat("NaN")` không ném lỗi và `NaN <= 0` là `false` → lọt kiểm. Chặn riêng.

---

## 5. Test trước khi gọi thầy

| # | Chọn | Gõ | Phải thấy |
|---|---|---|---|
| 1 | 2 hoặc 4 | (chưa nhập) | `There is no country in the list.` |
| 2 | 1 | `VN` / `Viet Nam` / `331698` / `Nice` | `Successful` |
| 3 | 1 | area `abc` | `You must input a number.` rồi hỏi lại |
| 4 | 1 | area `-5`, `0` | `Total area must be greater than 0.` |
| 5 | 1 | area `NaN`, `Infinity` | `You must input a number.` |
| 6 | 1 | code/name/terrain trống | `This field must not be blank.` rồi hỏi lại |
| 7 | 2 | (đã nhập VN rồi IDN) | bảng 1 dòng **IDN** (nước nhập sau cùng) |
| 8 | 3 | `Viet Nam`, `nam`, `VIET` | dòng VN |
| 9 | 3 | `Japan` (không có) | `No country found with the name [Japan].` |
| 10 | 4 | (IDN, VN, LA, japan) | tăng dần theo tên, bỏ qua hoa thường |
| 11 | 1 | lần thứ 12 | `The list already has 11 countries.` **ngay**, không hỏi 4 câu |
| 12 | menu | `abc`, trống, `0`, `6` | `Please choose an option from 1 to 5.` |

---

## 6. Debug

| Việc | Cách làm |
|---|---|
| Breakpoint | dòng `rowList.add(country.display());` trong `CountryService.toResponse` |
| Chạy | **Ctrl+F5**, nhập 1 nước, chọn 2 |
| Đa hình | **Variables**: `country` khai kiểu `Country` nhưng cột *Type* ghi `EastAsiaCountries`; bấm **F7** → nhảy vào `EastAsiaCountries.display()`, **F7** tiếp vào `super.display()` |
| Sắp xếp | breakpoint ở `if (countryArray[j]...compareToIgnoreCase(...) > 0)` trong `NameAscendingSortStrategy`, xem `i`, `j`, `swapped` |
| View 1 lần | breakpoint ở `countryView.display();` trong `CountryController` — mỗi option dừng **đúng 1 lần** |

---

## 7. Câu hỏi thầy hay hỏi

### OOP

| Câu hỏi | Trả lời mẫu |
|---|---|
| 4 tính chất OOP ở đâu? | **Kế thừa**: `EastAsiaCountries extends Country`, gọi `super(...)`; `NameAscendingSortStrategy implements ISortStrategy`. **Đa hình**: `display()` được **ghi đè**; trong `toResponse`, biến lặp khai kiểu cha `Country country` nhưng chạy bản của lớp con. **Đóng gói**: `countryTerrain` `private`; field cha `protected` — chỉ lớp con và cùng package thấy; bên ngoài đi qua getter. **Trừu tượng**: `ISortStrategy` chỉ nói "sắp được", không nói cách. |
| Overriding khác overloading? | **Override**: lớp con viết lại hàm **cùng chữ ký** của cha (`display()`), chọn lúc **chạy**. **Overload**: cùng tên, **khác tham số** trong một lớp — như 2 constructor của `Country`, chọn lúc **biên dịch**. |
| `super` dùng làm gì ở đây? | `super(code, name, area)`: gọi constructor cha (phải là dòng đầu). `super.display()`: dùng lại hàm cha rồi thêm cột Terrain. |

### Access modifier, static, kiểu trả về

| Câu hỏi | Trả lời mẫu |
|---|---|
| **Sao 3 field của `Country` là `protected` — thầy bảo field phải private?** | **Đề khai đúng như vậy** (`protected String countryCode; …`). `protected` = thấy trong lớp, **cùng package** và **lớp con** — lớp con `EastAsiaCountries` là lý do nó tồn tại. Em vẫn cho mọi lớp **khác** đi qua getter, nên đổi sang `private` sau này không vỡ gì ngoài 1 file. Còn field của em thêm (`countryTerrain`) thì `private`. |
| `countryTerrain` sao `private`? | Chỉ `EastAsiaCountries` dùng; không có lớp con nào cần. |
| `display()` trả `String`, không `void`? | Guide cấm model in (*"không được output ở đây"*, *"cần output gì thì thêm hàm toString()"*). Giữ **tên** đề, đổi thành **trả chữ** — view in. |
| `searchInformationByName` trả mảng, không `ArrayList`? | **Đề bắt** `EastAsiaCountries[]`. Mảng hợp vì tối đa 11 phần tử cố định. |
| `isFull` trả `boolean`? | Có/không. |
| `toResponse` sao `private`? | Chỉ `CountryService` dùng. |
| `checkFull` sao `public`? | Controller gọi (và `Main` gọi qua controller) — là **bước kiểm trước** giống `checkExistDoctor` trong mẫu P0055. Nó chỉ ném lỗi, **không** gọi view. |
| `Main` sao `final` + `private Main()`? | Tờ checklist 3.4: lớp chỉ có hàm static phải có private constructor và khai báo `final` — không ai `new Main()` hay kế thừa `Main`. |
| Sao `Validation` static? Bỏ đi thì sao? | Không dùng dữ liệu đối tượng. Bỏ `static` → lỗi biên dịch ở `Validation.getTotalArea(...)`; phải bỏ `private` constructor, `new Validation()` trong `Main`. |
| Hàm `inputX` trong `Main` sao `private static`? | `private`: chỉ `Main` dùng; `static`: gọi thẳng từ `main()`; Guide cho *static với hàm*, **cấm** static với biến. |
| `totalArea` sao `float`? | **Đề khai** `float`. Validation đọc bằng `Float.parseFloat` luôn — không đọc `double` rồi ép. |
| Mảng khác `ArrayList` thế nào? | Mảng kích thước **cố định**, phải tự đếm `count`; `ArrayList` tự lớn lên. Đề trả `EastAsiaCountries[]` và giới hạn 11 nước → mảng 11 ô là đúng việc. Nếu dùng danh sách em sẽ khai `ArrayList<EastAsiaCountries>` (lớp cụ thể), không khai `List`. |

### Kiến trúc

| Câu hỏi | Trả lời mẫu |
|---|---|
| Đề option 2 hiện **2 nước** — sao em hiện 1? | Guidelines ghi `EastAsiaCountries getRecentlyEnteredInformation()` — trả **một** nước. Khi màn hình và Guidelines vênh, em theo **Guidelines**: nước nhập **sau cùng**. |
| Sao sắp một bản sao? | `getRecentlyEnteredInformation` = phần tử cuối **theo thứ tự nhập**; sắp mảng gốc là đổi luôn "nước vừa nhập". |
| Sao `requestDTO` trong `main()` khởi tạo `null`? | Mỗi option tạo **RequestDTO mới** trong hàm nhập (`inputCountry`, `inputSearch`) rồi trả về — không option nào dùng lại dữ liệu của option trước. Trước khi chọn option thì chưa có request nào, nên `null`. Vẫn **khai ở đầu hàm + khởi tạo** (tờ checklist 2.6, 3.7); trong vòng lặp chỉ **gán**. |
| Sao `addCountryInformation` kiểm `area > 0` ở service nữa, khi `Validation` đã kiểm? | `Validation` chặn lúc **gõ**; service giữ **luật nghiệp vụ** cho mọi nơi gọi — mai có màn hình khác, luật vẫn đúng. |

### Tờ checklist 25 mục — bài này đạt thế nào

| Mục | Chỉ vào đâu |
|---|---|
| **1.1** MVC + repository | `repository/ManageEastAsiaCountries` (bắt buộc có repository); luồng Controller → `CountryService` → repository → model; `CountryController` không import `model`; `CountryView` nhận `responseDTO` qua setter, `display()` không tham số, gọi **1 lần/luồng**; mỗi case trong `Main.main` gọi controller 1 lần (option 1 thêm `checkFull()` **chỉ để kiểm**, lý do ở mục 3) |
| **1.3** interface `I…` | `service/ISortStrategy` |
| **1.5** tên mảng/list | `countryArray` (repository, service, strategy), `foundArray`, `rowList` (`CountryResponseDTO`, `CountryService.toResponse`) |
| **2.6 / 3.7** khai báo đầu block + khởi tạo | `Main.main`: `requestDTO = null`, `running = true`, `choice = 0` ở đầu hàm, trong vòng lặp chỉ gán; `Main.inputChoice/inputText/inputArea`: `String line = ""`; `Validation.getChoice`: `int choice = 0`; `NameAscendingSortStrategy.sort`: `swapped`, `temp` khai đầu hàm |
| **2.8** dòng trống | giữa các field (mọi lớp), sau vùng khai báo biến, trước mọi comment đứng sau dòng code, giữa các `case`, sau `}` trước câu lệnh tiếp |
| **3.3** ngoặc | `Validation.getChoice`: `if ((choice < min) \|\| (choice > max))`; `getNonBlank`: `(input == null) ? "" : input.trim()`; `NameAscendingSortStrategy`: `i < (size - 1)`, `j < (size - 1 - i)` |
| **3.4** lớp chỉ có static | `Main`, `Validation`, `Constants`, `Message`: `final` + `private` constructor |
| **3.8** cộng chuỗi | `EastAsiaCountries.display()` dùng `String.format(Constants.TERRAIN_FORMAT, …)` |

---

## 8. Thầy đổi yêu cầu tại chỗ

| Thầy bảo | Sửa | Không phải đụng |
|---|---|---|
| Sắp theo **diện tích** | thêm `AreaAscendingSortStrategy implements ISortStrategy` + sửa 1 dòng `CountryController` | `CountryService`, model, view, main |
| Sắp tên **giảm dần** | thêm `NameDescendingSortStrategy` (đổi `> 0` thành `< 0`) + 1 dòng controller | như trên |
| Cho nhập **20** nước | chỉ `Constants.MAX_COUNTRIES` (Message dùng `%d`) | mọi file khác |
| Thêm trường `population` | `EastAsiaCountries` (+ `display`), `CountryRequestDTO`, `CountryService.add…`, `Message` (header, prompt), `Constants.HEADER_FORMAT`, `Main` | repository, strategy |
| Tìm **đúng tên** thay vì "chứa" | `ManageEastAsiaCountries.searchInformationByName`: `contains` → `equalsIgnoreCase` | mọi file khác |
| Option 2 hiện **tất cả** đã nhập | service: hàm mới dùng `getAllCountries()` không sắp; controller gọi hàm đó | model, view |

---

## 9. Chỗ khác với đề / bản cũ

| Chỗ | Đề / bản cũ | Bài này | Lý do |
|---|---|---|---|
| `display()` | đề: *"display information"* — bản cũ `void` + `System.out` trong model | **trả `String`**, `CountryView` in | Guide cấm in trong model; giữ tên hàm |
| Field `protected` | đề khai `protected` | giữ `protected` + `.lint-allow` | đề bắt; giải thích ở mục 7 |
| Option 2 | màn hình đề hiện 2 dòng (VN, IDN) | 1 dòng: nước **nhập sau cùng** | Guidelines trả **một** `EastAsiaCountries`; màn hình vs Guidelines → theo Guidelines |
| `addCountryInformation(EastAsiaCountries)` | đề | giữ **đúng** ở repository; service/controller cùng tên nhận `CountryRequestDTO` | Guide: main → controller bằng DTO, controller không đụng model |
| `sortInformationByAscendingOrder()` | trả `EastAsiaCountries[]`, trong `ManageEastAsiaCountries` | ở `CountryService`, trả `CountryResponseDTO` (dòng bảng trong `rowList`) | thuật toán → service; controller không được thấy model |
| `searchInformationByName(String)` | đề | repository giữ đúng; service nhận `CountryRequestDTO` | Guide DTO |
| Tiêu đề menu | text đề `MENU` sát lề | `MENU` thụt 31 dấu cách | giữ nguyên bản cũ (đã khớp đề gốc) |
| Tìm kiếm | đề không nói | "chứa", không phân biệt hoa thường | bản cũ; `nam` tìm ra `Viet Nam` |
| Câu lỗi | đề chỉ có "Total area must be greater than 0" | + `You must input a number.`, `This field must not be blank.`, `There is no country in the list.`, `The list already has 11 countries.`, `No country found with the name [..].`, `Please choose an option from 1 to 5.` | chữ bản cũ |
| Sắp xếp | bản cũ `Arrays.sort` + `Comparator` | Strategy + bubble sort viết tay | thầy đánh giá cao Design Pattern; tự viết giải thích được |
| Kiến trúc | `entity/bo/ui`, Scanner trong `Validator` | MVC theo Guide | luật thầy |
| View (21/09) | bản trước: `CountryView.setCountries(CountryResponseDTO[])` + `showMessage(String)` | field `responseDTO` + `setResponseDTO` + `display()`; `Successful` đi trong `CountryResponseDTO.message` | tờ checklist 1.1: view nhận qua **thuộc tính**, render **1 lần/luồng** |
| Interface (21/09) | bản trước: `SortStrategy` | `ISortStrategy` | tờ checklist 1.3: tên interface bắt đầu bằng `I` |
| Tên mảng (21/09) | bản trước: `countries`, `found`, `rows` | `countryArray`, `foundArray`, `rowList` | tờ checklist 1.5: mảng đuôi `Array`, collection đuôi `List` |
| `Main` (21/09) | bản trước: `inputCountry` đọc + gọi controller 2 lần; biến khai giữa block | case gọi controller 1 lần; `inputCountry` chỉ gọi `checkFull` (kiểm); biến khai đầu hàm + khởi tạo; `final` + `private Main()` | tờ checklist 1.1, 2.6, 3.4, 3.7 |
| `display()` của lớp con (21/09) | bản trước: `super.display() + countryTerrain` | `String.format(Constants.TERRAIN_FORMAT, super.display(), countryTerrain)` | tờ checklist 3.8 (không cộng chuỗi) |
