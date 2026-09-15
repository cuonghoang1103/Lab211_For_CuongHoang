# J1.L.P0023 — Fruit Shop (Product and Shopping)

> **Bài dài (Long Assignment)**, chấm theo **% hoàn thành**. Muốn lấy trọn LOC cần:
> - làm đủ 3 chức năng (Create Fruit · View orders · Shopping);
> - kiểm được **tồn kho**;
> - dùng **đúng ArrayList + Hashtable** như đề bắt;
> - vẽ và giải thích được **ERD**.

| | |
|---|---|
| Loại / LOC | Long Assignment · 350 LOC · 5 slot |
| Project | `HE176322_J1LP0023_FruitShop` |
| Chạy | NetBeans: **File ▸ Open Project** → chọn thư mục → **F6** |
| Lớp chạy | `main.Main` |
| Kiểm tự động | `python3 _tools/verify.py --netbeans J1LP0023` → 3 kịch bản × 2 locale |

> ⚠️ Thầy dặn: bài này **bắt có ERD** (và L.P0022 bắt đủ SOLID) — nên nhiều bạn né. Đã chọn thì học kỹ mục 2.4.

---

## 1. Đề bài nói gì

| Phần | Đề yêu cầu |
|---|---|
| Menu | `FRUIT SHOP SYSTEM` · 1 Create Fruit · 2 View orders · 3 Shopping (for buyer) · 4 Exit |
| Create Fruit (chủ shop) | Fruit có **Fruit Id, Fruit Name, Price, Quantity, Origin**. Tạo xong mỗi quả hỏi `Do you want to continue (Y/N)?` — N thì về menu và **in mọi Fruit đã tạo** |
| View orders (chủ shop) | Mỗi khách: `Customer: <tên>` → bảng `Product \| Quantity \| Price \| Amount` → `Total: 7$` |
| Shopping (khách) | In `List of Fruit` → khách chọn item → `You selected: Coconut` → `Please input quantity:` → `Do you want to order now (Y/N)` — N quay lại danh sách, Y in giỏ + `Total` rồi hỏi `Input your name:` |

**Đề bắt buộc** (Technical Requirements + Guidelines):

| Đề viết | Bài này đặt ở |
|---|---|
| *"Only use ArrayList and HashTable to store data"* | `FruitRepository`: `ArrayList<Fruit>` · `OrderRepository`: `Hashtable<String, ArrayList<Item>>` + `ArrayList<String>` · giỏ: `ArrayList<Item> cart` trong `OrderService` |
| Slot 1: *"create Fruit class… Using ArrayList to store the Fruit"* | `model/Fruit.java` (đủ 5 field) · `repository/FruitRepository.java` |
| Slot 2: *"ArrayList to store items… HashTable to store order… hashTable.set(<customer name>, <list of items bought>)"* | `OrderService.cart` · `OrderRepository.addOrder(customerName, items)` = `orders.put(tên, danh sách)` |
| Câu hỏi `Do you want to continue (Y/N)?` và `Do you want to order now (Y/N)` | `Message.ASK_CONTINUE`, `Message.ASK_ORDER_NOW` — chép **đúng chữ** (câu thứ hai đề không có dấu `?`) |
| Tiền dạng `2$`, `6$` | `ShopView.formatMoney` → `DecimalFormat("0.##")` + `"$"` |

---

## 2. Kiến thức cần biết

### 2.1 `Hashtable` — đề bắt, và nó khác `HashMap` ở đâu

```java
private Hashtable<String, ArrayList<Item>> orders = new Hashtable<>();
orders.put("Marry Carie", items);      // đề: hashTable.set(<customer name>, <list of items bought>)
orders.get("Marry Carie");             // → danh sách item của khách, hoặc null nếu chưa mua
```

| | `Hashtable` | `HashMap` |
|---|---|---|
| Có từ | JDK 1.0 (lớp "cổ") | JDK 1.2 (Collections Framework) |
| Đồng bộ (thread-safe) | ✅ mọi hàm `synchronized` | ❌ |
| Khoá / giá trị `null` | ❌ ném `NullPointerException` | ✅ được 1 khoá null |
| Thứ tự khi duyệt | **không** giữ thứ tự thêm | **không** giữ (muốn giữ dùng `LinkedHashMap`) |

**Hệ quả thiết kế:** vì `Hashtable` **không nhớ ai mua trước**, `OrderRepository` giữ thêm
`ArrayList<String> customerNames`. View orders in theo danh sách này, nên *Marry Carie* luôn đứng trước *John Smith*.
Đây vẫn là ArrayList, không vi phạm đề.

### 2.2 Kiểm tồn kho — "còn lại = tồn − đã nằm trong giỏ"

`OrderService.addToCart`:

```java
int left = fruit.getQuantity() - countInCart(fruit.getFruitId());
if (left <= 0)                        → "Coconut is out of stock."
if (requestDTO.getQuantity() > left)  → "Only 5 Orange left in stock."
```

Chạy tay kịch bản A (Coconut tồn 10, Orange tồn 5):

| Khách làm | Tồn | Trong giỏ | `left` | Kết quả |
|---|---|---|---|---|
| Chọn Coconut, gõ 3, trả lời N | 10 | 0 | 10 | vào giỏ: Coconut 3 |
| Chọn Orange, gõ 8 | 5 | 0 | 5 | `Only 5 Orange left in stock.` — **không** vào giỏ, quay lại danh sách |
| Chọn Orange, gõ 2, trả lời Y | 5 | 0 | 5 | giỏ: Coconut 3, Orange 2 → `Total: 12$` → đặt hàng: Coconut còn **7**, Orange còn **3** |
| Khách 2 chọn Coconut, gõ 8 | 7 | 0 | 7 | `Only 7 Coconut left in stock.` |

**Vì sao phải trừ "trong giỏ"?** Tồn kho chỉ bị trừ **lúc đặt hàng** (`placeOrder`). Nếu không tính phần đã
nằm trong giỏ, khách có thể chọn Orange 5 → N → Orange 5 nữa, và giỏ có 10 quả trong khi kho chỉ có 5.

### 2.3 Gộp dòng cùng loại quả — `mergeItem`

Chọn Pear 2 → N → Pear 3 thì giỏ có **một** dòng `Pear 5`, không phải hai dòng (kịch bản C).
Khách đặt hàng lần hai **cùng tên** cũng được dồn vào đơn cũ (*Anna* mua Mango 1, sau đó Mango 2 → đơn `Mango 3`).

```java
for (Item item : items) {
    if (item.getFruitId().equalsIgnoreCase(newItem.getFruitId())) {  // đã có dòng của quả này
        item.setQuantity(item.getQuantity() + newItem.getQuantity());
        return;
    }
}
items.add(newItem);                                                  // quả mới: thêm dòng
```

### 2.4 ERD — thầy bắt vẽ

```
┌──────────────────┐ 1          n ┌──────────────────┐ n          1 ┌──────────────────────┐
│ Fruit            │──────────────│ Item             │──────────────│ Order                │
├──────────────────┤  một quả nằm ├──────────────────┤ một đơn gồm  ├──────────────────────┤
│ PK fruitId       │  trong nhiều │ FK fruitId       │ nhiều dòng   │ PK customerName      │
│    fruitName     │  dòng hàng   │    fruitName     │              │    items (ArrayList) │
│    price         │              │    price         │              │    total (tính ra)   │
│    quantity (tồn)│              │    quantity (mua)│              └──────────────────────┘
│    origin        │              │    amount = p × q│
└──────────────────┘              └──────────────────┘
```

| Thực thể ERD | Trong code | Ghi chú |
|---|---|---|
| **Fruit** | `model/Fruit.java`, lưu trong `ArrayList<Fruit>` | khoá `fruitId`, so **không phân biệt hoa thường** (`f001` = `F001`) |
| **Item** | `model/Item.java` | **chép** `fruitName` và `price` lúc mua (snapshot): sau này chủ shop đổi giá thì đơn cũ vẫn đúng số tiền đã trả |
| **Order** | **không** có class riêng: `Hashtable<String, ArrayList<Item>>` | khoá là tên khách, giá trị là danh sách item — đúng dòng `hashTable.set(...)` đề ghi |

`Order` không có class vì đề bắt dữ liệu nằm trong Hashtable. Nếu thầy muốn có class `Order`, xem mục 8.

### 2.5 Java dùng trong bài

| API | Dùng làm gì |
|---|---|
| `ArrayList.add/get/size` | kho quả, giỏ hàng, danh sách tên khách |
| `Hashtable.put/get` | đơn hàng theo tên khách |
| `new ArrayList<>(list)` | trả **bản sao** từ repository để lớp khác không thêm/xoá trộm |
| `DecimalFormat("0.##", DecimalFormatSymbols.getInstance(Locale.US))` | `2` → `2`, `2.5` → `2.5`, `0.30000000000000004` → `0.3`; `Locale.US` giữ dấu **chấm** trên máy tiếng Việt |
| `Double.isNaN / isInfinite` | `Double.parseDouble("NaN")` và `"Infinity"` **không** ném lỗi — phải tự chặn |
| `String.format("%-16s %8d")` | căn cột bảng |

---

## 3. Thiết kế

```
HE176322_J1LP0023_FruitShop/src/
├── constants/  Message.java            mọi câu chữ màn hình
│               Constants.java          số menu, item 0, tồn tối thiểu, Y/N, định dạng cột + tiền
├── model/      Fruit.java              5 field của đề (JavaBean)
│               Item.java               1 dòng hàng: fruitId, fruitName, price, quantity + getAmount()
├── dto/        FruitRequestDTO         5 ô chủ shop gõ          main ──► controller
│               OrderRequestDTO         item, số lượng, tên khách main ──► controller
│               FruitResponseDTO        1 dòng bảng quả           controller ──► view
│               ItemResponseDTO         1 dòng giỏ / đơn          controller ──► view
│               OrderResponseDTO        cả giỏ / cả đơn + total   controller ──► view
├── repository/ FruitRepository         ArrayList<Fruit>
│               OrderRepository         Hashtable<tên, ArrayList<Item>> + ArrayList<tên>
├── service/    FruitService            luật chủ shop: trùng ID, tạo, liệt kê
│               OrderService            luật khách: giỏ, tồn kho, đặt hàng, xem đơn
├── controller/ ShopController          Facade: service ──► view
├── view/       ShopView                in 4 loại bảng + câu thông báo
├── utils/      Validation              kiểm DẠNG dữ liệu gõ (static)
└── main/       Main                    menu + Scanner + các vòng hỏi lại
```

| Lớp | Làm gì | Không được làm |
|---|---|---|
| `Main` | menu, **đọc bàn phím**, gói DTO, bắt lỗi in `e.getMessage()` | import model/view/service/repository |
| `ShopController` | nhận DTO → service → view | Scanner, `System.out`, static, import model |
| `FruitService`, `OrderService` | luật nghiệp vụ, đổi model ⇄ DTO | in ra, đọc phím |
| `FruitRepository`, `OrderRepository` | giữ collection, thêm/tìm | kiểm luật, in ra |
| `Fruit`, `Item` | mô tả đối tượng | Scanner, in, static |
| `ShopView` | in | tính toán nghiệp vụ |
| `Validation` | "có phải số không? > 0 không? Y hay N?" | luật về quả/đơn |

**Luồng Shopping:**

```
Main: controller.startShopping()            → shop trống? throw "There is no fruit in the shop yet."
  ┌─► count = controller.displayFruitList()  → view in "List of Fruit" (3 cột + giá)
  │   item  = hỏi 0..count                    → 0: controller.cancelShopping() → về menu
  │   controller.selectFruit(dto)             → view in "You selected: Coconut"
  │   qty   = hỏi số > 0
  │   controller.addToCart(dto)               → service: left = tồn − trong giỏ; thiếu → throw ─┐
  │   "Do you want to order now (Y/N)"                                                         │
  ├── N ◄──────────────────────────────────────────────────────────── in lỗi, quay lại ◄──────┘
  └── Y: controller.displayCart()             → view in giỏ + Total
         tên = hỏi (không trống)
         controller.placeOrder(dto)           → service: trừ tồn → Hashtable.put / gộp → xoá giỏ
                                              → view "Thank you Marry Carie, your order has been saved."
```

### 3.1 Design Pattern trong bài

| Pattern | Ở đâu | Name · Problem · Solution · Consequences |
|---|---|---|
| **Facade** (GoF, Structural) — pattern chính | `ShopController` | **Problem:** Main mà tự gọi 2 service, 2 repository và view thì phải biết hết cách chúng nối với nhau. **Solution:** Main chỉ thấy **một cửa** gồm `createFruit`, `addToCart`, `placeOrder`…; controller tự gọi service rồi đưa kết quả sang view. **Consequences:** ✅ đổi bên trong (thêm service, đổi kho) mà Main không đổi. ❌ controller dễ phình nếu nhét luật vào; ở bài này luật nằm hết ở service |
| **Dependency Injection** (qua constructor) | `ShopController()` tạo **một** `FruitRepository` rồi đưa cho **cả hai** service | nếu mỗi service tự `new FruitRepository()` thì khách mua xong, bảng tồn của chủ shop **không giảm** (hai kho khác nhau). Truyền qua constructor thì cả hai dùng chung một kho |
| **Repository** (mẫu kiến trúc, không thuộc 23 GoF) | `FruitRepository`, `OrderRepository` | chỉ một nơi đụng vào `ArrayList`/`Hashtable`; đổi sang lưu file thì sửa đúng 2 lớp này |
| **DTO** | package `dto` | Main và view không bao giờ cầm `Fruit`/`Item` thật, nên không sửa trộm được tồn kho |
| **MVC** ("MVC JSP" của thầy) | controller ~ Servlet, view ~ JSP, model ~ JavaBean | tách phần nhập, xử lý và in |

> Nếu thầy hỏi *"có pattern hành vi nào không?"*: bài này không cần. Nếu thầy muốn thì thêm được
> **Strategy** cho giảm giá — xem mục 8, dòng "Giảm giá". Đừng nhận là có sẵn.

### 3.2 SOLID trong bài

| Nguyên lý | Ở đâu |
|---|---|
| **S** | `Fruit`/`Item` chỉ mô tả · repository chỉ lưu · `FruitService` chỉ lo việc chủ shop · `OrderService` chỉ lo việc khách · `ShopView` chỉ in · `Validation` chỉ kiểm dạng |
| **O** | thêm cột vào bảng: sửa `Constants.*_ROW_FORMAT` + `ShopView`; service không đổi |
| **L** | (không có kế thừa — bài không cần; nói thật nếu thầy hỏi) |
| **I** | Main chỉ gọi hàm controller cần cho từng bước; view nhận DTO chỉ chứa đúng cột nó in |
| **D** | service nhận repository **qua constructor** (không tự `new`), controller là nơi lắp ráp |

---

## 4. Code từng bước

> Thầy: *"code cái **model trước rồi đến data**"*.

| Bước | File | Việc |
|---|---|---|
| 1 | `model/Fruit.java` | 5 field `private` + ctor rỗng + ctor đủ + get/set |
| 2 | `model/Item.java` | 4 field + `getAmount()` = `price * quantity` |
| 3 | `dto/*` (5 lớp) | JavaBean; `OrderResponseDTO` có `ArrayList<ItemResponseDTO> items` |
| 4 | `repository/FruitRepository` | `countFruits`, `findById` (bỏ qua hoa thường), `findByItemNumber` (item 1 = index 0), `addFruit`, `findAll` (bản sao) |
| 5 | `repository/OrderRepository` | `Hashtable` + `ArrayList<String>`; `countOrders`, `findByCustomer`, `addOrder`, `updateOrder`, `findAllCustomers` |
| 6 | `service/FruitService` | `checkFruitId`, `createFruit`, `getAllFruits` + `private toResponse` |
| 7 | `service/OrderService` | `startShopping`, `selectFruit`, `addToCart`, `getCart`, `placeOrder`, `cancelShopping`, `getAllOrders` + `private countInCart / mergeItem / toResponse` |
| 8 | `view/ShopView` | `displayStock`, `displayFruitList`, `displayCart`, `displayOrders`, `showMessage` + `private formatMoney` |
| 9 | `controller/ShopController` | constructor lắp **một** `FruitRepository` cho cả hai service; mỗi hàm: service → view |
| 10 | `constants/Message`, `Constants` | gõ dần khi các bước trên cần |
| 11 | `utils/Validation` | `getText/getInt/getChoice/getPrice/getStock/getOrderQuantity/getYesNo` |
| 12 | `main/Main` | menu `while` + `switch`; `createFruits` (do-while); `shopping` (while + 0/N/Y) |

**Bẫy hay gặp**

1. **Trừ tồn ngay khi bỏ vào giỏ.** Khách chọn 0 để huỷ thì hàng biến mất khỏi kho. Bài này chỉ trừ trong `placeOrder`.
2. **Quên tính phần trong giỏ** khi kiểm tồn (mục 2.2).
3. **Duyệt `Hashtable` để in đơn** thì thứ tự khách bị xáo. Duyệt `customerNames` (ArrayList).
4. **Hai `new FruitRepository()`** (mỗi service một cái) thì bán xong mà tồn không giảm.
5. **`sc.nextLine()` nằm trong `try`**: khi hết input (Ctrl+D, hoặc file test thiếu dòng), `catch (Exception)` nuốt luôn
   `NoSuchElementException` và vòng hỏi lại quay **vô hạn**. Bài này đọc dòng **trước** `try`; `try` chỉ bọc phần kiểm tra.
6. Nhập giá `NaN` hoặc `Infinity`: `parseDouble` chấp nhận, nên phải chặn bằng `Double.isNaN/isInfinite`.

---

## 5. Test trước khi gọi thầy

> Thầy: *"test tất cả các happy case cũng như hiển thị đủ các message validation"*.

| # | Chọn | Gõ | Phải thấy |
|---|---|---|---|
| 1 | menu | `abc`, rồi `9` | `You must input a number.` · `Please choose from 1 to 4.` |
| 2 | 2 khi chưa ai mua | — | `There is no order yet.` |
| 3 | 3 khi chưa có quả | — | `There is no fruit in the shop yet.` |
| 4 | 1 | ID trống / tên trống / origin trống | `This field must not be empty.` và hỏi lại **đúng ô đó** |
| 5 | 1 | giá `abc`, `NaN`, `Infinity` | `You must input a number.` |
| 6 | 1 | giá `0`, `-3` | `Price must be greater than 0.` |
| 7 | 1 | số lượng `1.5` / `-1` | `You must input a number.` / `Quantity must not be negative.` |
| 8 | 1 | ID `a1` khi đã có `A1` | `Fruit ID a1 already exists.` và hỏi lại ID |
| 9 | 1 | trả lời `x`, rồi `y`/`n` thường | `Please enter Y or N.`; chữ thường vẫn nhận |
| 10 | 1 → N | — | `List of Fruit:` + bảng **có cột Quantity** (tồn kho) |
| 11 | 3 | item `abc`, `5` (có 2 quả) | `You must input a number.` · `Please choose from 0 to 2.` |
| 12 | 3 | `0` khi giỏ trống | về menu, **không** in gì thêm |
| 13 | 3 | quả tồn 0 | `Lemon is out of stock.` và quay lại danh sách |
| 14 | 3 | số lượng `0` / `abc` | `Quantity must be greater than 0.` / `You must input a number.` |
| 15 | 3 | muốn nhiều hơn tồn | `Only 1 Mango left in stock.` (tồn − phần đã trong giỏ) |
| 16 | 3 | có hàng trong giỏ rồi chọn `0` | `Your order has been cancelled.` |
| 17 | 3 | `Y`, tên trống, rồi `Anna` | bảng giỏ + `Total` · `This field must not be empty.` · `Thank you Anna, your order has been saved.` |
| 18 | 3 | cùng quả chọn 2 lần | giỏ chỉ **1 dòng**, số lượng cộng dồn |
| 19 | 2 | — | từng khách, **đúng thứ tự mua**, dòng đánh số `1.`, `2.`, `Total` |
| 20 | 1 → N sau khi bán | — | cột Quantity đã **giảm** |
| 21 | giá `1.25`, `0.1` | — | `1.25$`, `0.1$`, `0.1 × 3` = `0.3$` (không phải `0.30000000000000004$`) |

---

## 6. Debug

| Việc | Cách làm |
|---|---|
| Breakpoint | dòng `int left = fruit.getQuantity() - countInCart(...)` trong `OrderService.addToCart` |
| Chạy | **Ctrl+F5**, tạo Orange tồn 5, chọn 3 → Orange → gõ 8 |
| Quan sát | tab **Variables**: `fruit.quantity = 5`, `left = 5`, `requestDTO.quantity = 8` → **F8** thấy nhảy vào `throw` |
| Bước vào | breakpoint ở vòng `for (Item item : cart)` trong `placeOrder` → **F8** từng vòng, xem `fruit.quantity` giảm |
| Xem Hashtable | trong `OrderRepository.addOrder`, mở `orders` ở Variables: thấy cặp `"Marry Carie" → ArrayList[2]` |

---

## 7. Câu hỏi thầy hay hỏi

### OOP

| Câu hỏi | Trả lời mẫu |
|---|---|
| 4 tính chất OOP ở đâu? | **Đóng gói**: mọi field `private`, có get/set; repository trả **bản sao** list. **Trừu tượng**: controller chỉ lộ các hàm như `addToCart`, không lộ Hashtable. **Kế thừa**: mọi lớp kế thừa `Object`; bài không cần cây kế thừa riêng. **Đa hình**: `Exception` có nhiều loại, `catch (Exception e)` bắt được `NumberFormatException` do `parseInt` ném, và `getMessage()` chạy bản của lớp thật. Nói thật: bài không cần kế thừa, **đừng bịa**. |
| Sao `Item` chép lại tên và giá, không giữ luôn `Fruit`? | Đơn hàng phải ghi **giá lúc mua**. Nếu giữ tham chiếu `Fruit` thì chủ shop đổi giá xong, mọi đơn cũ đổi tiền theo. |
| Sao không có class `Order`? | Đề bắt `hashTable.set(<customer name>, <list of items bought>)`: đơn **chính là** cặp khoá–giá trị trong Hashtable. |

### Collection

| Câu hỏi | Trả lời mẫu |
|---|---|
| `Hashtable` khác `HashMap`? | Bảng mục 2.1: Hashtable đồng bộ, cấm `null`, có từ JDK 1.0; cả hai đều **không giữ thứ tự**. |
| Vậy làm sao in khách theo thứ tự? | `ArrayList<String> customerNames` ghi tên lúc **lần đầu** mua; view duyệt list này rồi `get` từ Hashtable. |
| Tra đơn một khách tốn bao nhiêu? | `Hashtable.get` trung bình **O(1)** (băm tên ra vị trí). Tìm quả theo ID là O(n) vì duyệt ArrayList. |
| Khách gõ `anna` rồi `Anna`? | Hai khoá khác nhau (Hashtable phân biệt hoa thường), nên thành hai khách. Muốn gộp thì xem mục 8. |
| Sao khai báo `ArrayList`/`Hashtable` mà không `List`/`Map`? | Thầy: dùng kiểu **cụ thể**; đề cũng gọi đích danh ArrayList và HashTable. |

### Access modifier, static, tham số

| Câu hỏi | Trả lời mẫu |
|---|---|
| Hàm nào `private`? | `countInCart`, `mergeItem`, `toResponse` (chỉ service dùng), `formatMoney` (chỉ view dùng), mọi hàm nhập trong `Main` (`private static`). |
| `static` ở đâu? | Chỉ ở: `Validation` (Guide *"phải dùng static method"*), hằng trong `Message`/`Constants`, và **hàm** trong `Main`. Không có **biến** static nào. |
| Bỏ `static` ở hàm của `Main`? | `main` là static nên không gọi được hàm thường; phải `new Main()` rồi gọi qua đối tượng. |
| Hàm nào quá 2 tham số? | `Validation.getChoice(input, min, max)` — utils được 3, đúng mẫu Guide. Constructor `Fruit(...)` 5 tham số được miễn. Còn lại ≤ 2: dữ liệu gói vào `FruitRequestDTO`/`OrderRequestDTO`. |
| `displayFruitList()` trả `int`? | Main cần biết có mấy item để hỏi `0..count`; nó không đụng model, chỉ nhận một con số. |

### Luồng & thuật toán

| Câu hỏi | Trả lời mẫu |
|---|---|
| Tồn kho bị trừ lúc nào, vì sao? | Trong `placeOrder`, **sau khi** khách gõ tên. Chọn 0 để huỷ thì kho không mất gì. |
| Làm sao không bán quá tồn? | `left = tồn − số đã trong giỏ` (mục 2.2), kiểm **mỗi lần** bỏ vào giỏ. |
| Sao Shopping gọi controller nhiều lần — trái luật "1 lần"? | Mỗi bước cần dữ liệu **khách vừa gõ**, mà Scanner chỉ được ở Main. `startShopping` là bước kiểm (giống `checkExistDoctor` của P0055); mỗi hàm sau làm đúng một việc. |
| Sao `sc.nextLine()` đặt ngoài `try`? | `try` chỉ nên bọc chỗ có thể sai do **người gõ** (`Validation`). Hết input là lỗi **khác**; để nó bay ra thì chương trình dừng, thay vì quay vô hạn. |
| Độ phức tạp đặt hàng? | O(k·n): mỗi dòng giỏ (k) tìm quả theo ID trong kho (n). Bài nhỏ nên đủ; muốn O(k) thì thêm `Hashtable<id, Fruit>`. |

---

## 8. Thầy đổi yêu cầu tại chỗ

| Thầy bảo | Sửa file | Không phải đụng |
|---|---|---|
| Ẩn quả hết hàng khỏi danh sách khách | `FruitService`: hàm lọc `quantity > 0` cho Shopping. ⚠️ item number phải đi theo danh sách **đã lọc** | view, main |
| Update / Delete fruit | `FruitRepository.updateFruit/deleteFruit` + `FruitService` + 1 case menu + `Message` | `OrderService` (Item đã chép tên + giá) |
| Tên khách không phân biệt hoa thường | `OrderService.placeOrder`: dùng `customerName.toLowerCase()` làm khoá; giữ tên gốc để in | repository |
| Giảm giá 10% khi Total > 100$ | **Strategy**: `service/DiscountStrategy` (interface) + `NoDiscount`, `TenPercentDiscount`; `OrderService.toResponse` gọi `discount.apply(total)` | view, main |
| Có class `Order` (ngày mua, tổng) | `model/Order` (customerName, `ArrayList<Item>`, date) → `Hashtable<String, Order>` | view (vẫn nhận `OrderResponseDTO`) |
| Lưu vào file | chỉ 2 repository: thêm `load()`/`save()` (xem bản cũ ở mục 9) | service, view, main |
| Sắp đơn theo tên khách | `OrderService.getAllOrders`: sắp bản sao `findAllCustomers()` | view |

---

## 9. Chỗ khác với đề / bản cũ

| Chỗ | Đề / bản cũ | Bài này | Lý do |
|---|---|---|---|
| Menu | đề in `- Create Fruit`… (mất số) | `1. Create Fruit`… + câu `(Please choose 1 to create product, …)` | số là thứ người dùng gõ |
| Dòng trống trước menu, `Goodbye.` | đề không ghi | có | giữ đúng bản tham chiếu |
| Bảng sau Create | đề: *"display all Fruits what are created"* | bảng thêm cột **Quantity** | chủ shop cần thấy tồn; bảng của khách vẫn đúng 4 cột đề |
| Bảng giỏ | bản cũ có dấu cách đầu dòng | không có | màn hình đề không có |
| Thông báo lỗi | đề không ghi | `Message.*` (21 câu, liệt kê ở mục 5) | đề chỉ vẽ happy case |
| Lưu dữ liệu | bản cũ ghi `fruits.txt`, `orders.txt` | chỉ trong bộ nhớ | đề: *"Only use ArrayList and HashTable to store data"*. Vì thế `verify.py` dùng 3 kịch bản riêng (`REPLACE_REFERENCE = True`); kịch bản A gõ đúng phím của bản cũ |
| Kiến trúc | bản cũ: `entity/bo/ui`, Scanner static trong `Validator`, controller in ra | MVC Guide: Scanner chỉ ở `main`, in ở `view`, luật ở `service`, collection ở `repository` | luật thầy |
| Giỏ hàng | bản cũ: class `Cart` | `ArrayList<Item> cart` trong `OrderService` | đề: *"Using ArrayList to store items"*, bớt một lớp |
