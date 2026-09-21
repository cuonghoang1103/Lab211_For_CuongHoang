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
| Kiểm tự động | `python3 _tools/verify.py --netbeans J1LP0023` → 3 kịch bản × 2 locale · `python3 _tools/soat_checklist.py HE176322_J1LP0023_FruitShop` → **0 vi phạm** tờ checklist 25 mục |

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
| *"Only use ArrayList and HashTable to store data"* | `FruitRepository`: `ArrayList<Fruit> fruitList` · `OrderRepository`: `Hashtable<String, ArrayList<Item>> orderMap` + `ArrayList<String> customerNameList` + giỏ `ArrayList<Item> cartList` |
| Slot 1: *"create Fruit class… Using ArrayList to store the Fruit"* | `model/Fruit.java` (đủ 5 field) · `repository/FruitRepository.java` |
| Slot 2: *"ArrayList to store items… HashTable to store order… hashTable.set(<customer name>, <list of items bought>)"* | `OrderRepository.cartList` · `OrderRepository.addOrder(customerName, itemList)` = `orderMap.put(tên, danh sách)` |
| Câu hỏi `Do you want to continue (Y/N)?` và `Do you want to order now (Y/N)` | `Message.ASK_CONTINUE`, `Message.ASK_ORDER_NOW` — chép **đúng chữ** (câu thứ hai đề không có dấu `?`) |
| Tiền dạng `2$`, `6$` | `ShopView.formatMoney` → `DecimalFormat("0.##")` rồi `String.format(Constants.MONEY_FORMAT, …)` (`"%s$"`) |

---

## 2. Kiến thức cần biết

### 2.1 `Hashtable` — đề bắt, và nó khác `HashMap` ở đâu

```java
private Hashtable<String, ArrayList<Item>> orderMap;   // Hashtable là một Map ⇒ tên kết thúc bằng Map (1.5)
orderMap.put("Marry Carie", itemList);  // đề: hashTable.set(<customer name>, <list of items bought>)
orderMap.get("Marry Carie");            // → danh sách item của khách, hoặc null nếu chưa mua
```

| | `Hashtable` | `HashMap` |
|---|---|---|
| Có từ | JDK 1.0 (lớp "cổ") | JDK 1.2 (Collections Framework) |
| Đồng bộ (thread-safe) | ✅ mọi hàm `synchronized` | ❌ |
| Khoá / giá trị `null` | ❌ ném `NullPointerException` | ✅ được 1 khoá null |
| Thứ tự khi duyệt | **không** giữ thứ tự thêm | **không** giữ (muốn giữ dùng `LinkedHashMap`) |

**Hệ quả thiết kế:** vì `Hashtable` **không nhớ ai mua trước**, `OrderRepository` giữ thêm
`ArrayList<String> customerNameList`. View orders in theo danh sách này, nên *Marry Carie* luôn đứng trước *John Smith*.
Đây vẫn là ArrayList, không vi phạm đề.

### 2.2 Kiểm tồn kho — "còn lại = tồn − đã nằm trong giỏ"

`OrderService.addToCart` (dữ liệu lấy từ repository, **phép tính** ở service):

```java
Item cartItem = orderRepository.findCartItem(fruit.getFruitId());   // dòng giỏ của quả này, hoặc null
int inCart = (cartItem == null) ? 0 : cartItem.getQuantity();      // ngoặc quanh điều kiện (3.3)
int left = fruit.getQuantity() - inCart;
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

**Vì sao phải trừ "trong giỏ"?** Tồn kho chỉ bị trừ **lúc đặt hàng** (`saveOrder`). Nếu không tính phần đã
nằm trong giỏ, khách có thể chọn Orange 5 → N → Orange 5 nữa, và giỏ có 10 quả trong khi kho chỉ có 5.

### 2.3 Gộp dòng cùng loại quả — trong giỏ và trong đơn cũ

Chọn Pear 2 → N → Pear 3 thì giỏ có **một** dòng `Pear 5`, không phải hai dòng (kịch bản C): `addToCart` thấy
`findCartItem` trả về dòng cũ thì chỉ tăng số lượng. Khách đặt hàng lần hai **cùng tên** cũng được dồn vào đơn cũ
(*Anna* mua Mango 1, sau đó Mango 2 → đơn `Mango 3`) nhờ `mergeItems`:

```java
for (Item cartItem : cartList) {
    oldItem = findItem(mergedList, cartItem.getFruitId());   // oldItem khai báo đầu hàm = null (2.6)
    if (oldItem != null) {                                    // đã có dòng của quả này
        oldItem.setQuantity(oldItem.getQuantity() + cartItem.getQuantity());
    } else {
        mergedList.add(cartItem);                             // quả mới: thêm dòng
    }
}
```

### 2.4 ERD — thầy bắt vẽ

```
┌──────────────────┐ 1          n ┌──────────────────┐ n          1 ┌──────────────────────┐
│ Fruit            │──────────────│ Item             │──────────────│ Order                │
├──────────────────┤  một quả nằm ├──────────────────┤ một đơn gồm  ├──────────────────────┤
│ PK fruitId       │  trong nhiều │ FK fruitId       │ nhiều dòng   │ PK customerName      │
│    fruitName     │  dòng hàng   │    fruitName     │              │    itemList          │
│    price         │              │    price         │              │    total (tính ra)   │
│    quantity (tồn)│              │    quantity (mua)│              └──────────────────────┘
│    origin        │              │    amount = p × q│
└──────────────────┘              └──────────────────┘
```

| Thực thể ERD | Trong code | Ghi chú |
|---|---|---|
| **Fruit** | `model/Fruit.java`, lưu trong `ArrayList<Fruit>` | khoá `fruitId`, so **không phân biệt hoa thường** (`f001` = `F001`) |
| **Item** | `model/Item.java` | **chép** `fruitName` và `price` lúc mua (snapshot): sau này chủ shop đổi giá thì đơn cũ vẫn đúng số tiền đã trả. `amount = p × q` là thuộc tính **tính ra**: `OrderService.calculateAmount` tính (tính tiền là nghiệp vụ), model không tính |
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
│               Constants.java          số menu, item 0, tồn tối thiểu, Y/N, định dạng cột + tiền (MONEY_FORMAT)
├── model/      Fruit.java              5 field của đề (JavaBean)
│               Item.java               1 dòng hàng: fruitId, fruitName, price, quantity (không tự tính tiền)
├── dto/        FruitRequestDTO         5 ô chủ shop gõ                    main ──► controller
│               OrderRequestDTO         item, số lượng, tên khách          main ──► controller
│               ShopResponseDTO         câu trả lời của MỘT luồng:         controller ──► view
│                                       message · stockList · fruitList · cart · orderList
│               FruitResponseDTO        1 dòng bảng quả   ┐
│               ItemResponseDTO         1 dòng giỏ / đơn  ├ các dòng nằm trong ShopResponseDTO
│               OrderResponseDTO        cả giỏ / cả đơn: itemList + total ┘
├── repository/ FruitRepository         ArrayList<Fruit> fruitList
│               OrderRepository         Hashtable<tên, ArrayList<Item>> orderMap + ArrayList<String> customerNameList
│                                       + giỏ ArrayList<Item> cartList
├── service/    FruitService            luật chủ shop: trùng ID, tạo, liệt kê (trả ShopResponseDTO)
│               OrderService            luật khách: tồn kho, giỏ, TÍNH TIỀN (amount, total), đặt hàng, xem đơn
├── controller/ ShopController          Facade: service ──► view.setResponseDTO + display() (1 lần / luồng)
├── view/       ShopView                thuộc tính responseDTO + setResponseDTO + display() không tham số
├── utils/      Validation              kiểm DẠNG dữ liệu gõ (static, final, private ctor)
└── main/       Main                    final + private ctor; menu + Scanner + validate; mỗi luồng gọi controller 1 lần
```

| Lớp | Làm gì | Không được làm |
|---|---|---|
| `Main` | menu, **đọc bàn phím + validate**, gói DTO, mỗi luồng gọi controller **1 lần**, bắt lỗi in `e.getMessage()` | import model/view/service/repository |
| `ShopController` | nhận DTO → service → `shopView.setResponseDTO(r)` + `display()` **1 lần** | Scanner, `System.out`, static, import model |
| `FruitService`, `OrderService` | luật nghiệp vụ + tính tiền, `throw new Exception(Message.X)`, đổi model ⇄ DTO | in ra, đọc phím |
| `FruitRepository`, `OrderRepository` | giữ **dữ liệu** (quả, giỏ, đơn), CRUD đơn giản | kiểm luật, tính tiền, in ra |
| `Fruit`, `Item` | mô tả đối tượng | Scanner, in, static |
| `ShopView` | in đúng những gì có trong `responseDTO` | tính toán nghiệp vụ, nhận dữ liệu qua tham số |
| `Validation` | "có phải số không? > 0 không? Y hay N?" | luật về quả/đơn |

**Luồng chung (mọi chức năng):**

```
Main (nhập + validate) ──RequestDTO──► ShopController ──► FruitService / OrderService ──► FruitRepository / OrderRepository ──► Fruit / Item
                                             │
                                             └──ShopResponseDTO──► ShopView.setResponseDTO(…) → display()   (1 lần / luồng)
lỗi nghiệp vụ: service throw new Exception(Message.X) ──► Main catch → System.out.println(e.getMessage())
```

Mỗi `case` của menu, mỗi **vòng** của màn tạo quả, và mỗi **bước** của màn Shopping là **một luồng**: gọi controller
đúng một lần, view render nhiều nhất một lần.

**Luồng Create** (case 1):

```
Main.createFruits: vòng while (mỗi vòng = 1 quả)
   Fruit ID: → checkFruitId(controller, dto) → controller.checkFruitId   ← CHỈ ĐỂ KIỂM (không render, không lưu):
                    trống → "This field must not be empty." · trùng → "Fruit ID F001 already exists." → hỏi lại NGAY
   name / price / quantity / origin (sai dạng → hỏi lại tại chỗ)
   createFruit(controller, dto) → controller.createFruit   ← luồng của vòng: "Fruit F001 has been created." (1 lần)
   "Do you want to continue (Y/N)?"  Y → vòng mới · N → thoát vòng
case 1 tiếp: controller.displayFruits()                   ← luồng "N": "List of Fruit:" + bảng có Quantity (1 lần)
```

**Luồng Shopping** (case 3) — một **menu con**: danh sách quả chính là menu, `0` là "về màn hình chính":

```
Main.buyFruits: vòng while (mỗi vòng = 1 lần chọn quả)
   chooseItem:        controller.displayFruitList()   ← "List of Fruit" (1 lần); shop trống → throw "There is no fruit in the shop yet."
                                                         trả về số quả để Main kiểm item 0..n và hỏi lại NGAY
   item 0  → cancelShopping: controller.cancelShopping()   ← giỏ có hàng: "Your order has been cancelled." → về menu
   item k  → selectFruit:    controller.selectFruit(dto)   ← "You selected: Coconut" (1 lần)
             Please input quantity: (sai dạng → hỏi lại tại chỗ)
             addToCart:      controller.addToCart(dto)     ← service: left = tồn − trong giỏ; thiếu → throw, Main in lỗi,
                                                              vòng mới (danh sách lại); đủ → vào giỏ (không render)
             "Do you want to order now (Y/N)"   N → vòng mới
             Y → inputCustomerName: controller.displayCart()  ← giỏ + Total (1 lần), rồi "Input your name: "
                 saveOrder:  controller.saveOrder(dto)     ← trừ tồn → orderMap.put / gộp → xoá giỏ → "Thank you …" (1 lần)
```

### 3.1 Design Pattern trong bài

| Pattern | Ở đâu | Name · Problem · Solution · Consequences |
|---|---|---|
| **Facade** (GoF, Structural) — pattern chính | `ShopController` | **Problem:** Main mà tự gọi 2 service, 2 repository và view thì phải biết hết cách chúng nối với nhau. **Solution:** Main chỉ thấy **một cửa** gồm `createFruit`, `addToCart`, `saveOrder`…; controller tự gọi service rồi đưa kết quả sang view. **Consequences:** ✅ đổi bên trong (thêm service, đổi kho) mà Main không đổi. ❌ controller dễ phình nếu nhét luật vào; ở bài này luật nằm hết ở service |
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
| 2 | `model/Item.java` | 4 field + get/set (thành tiền do service tính) |
| 3 | `dto/*` (6 lớp) | JavaBean; `OrderResponseDTO` có `ArrayList<ItemResponseDTO> itemList`; `ShopResponseDTO` gom `message`, `stockList`, `fruitList`, `cart`, `orderList` |
| 4 | `repository/FruitRepository` | `countFruits`, `findById` (bỏ qua hoa thường), `findByItemNumber` (item 1 = index 0), `addFruit`, `findAll` (bản sao) |
| 5 | `repository/OrderRepository` | `orderMap` + `customerNameList` + `cartList`; `countOrders`, `findByCustomer`, `addOrder`, `updateOrder`, `findAllCustomers`, `findCart`, `findCartItem`, `addCartItem`, `clearCart` |
| 6 | `service/FruitService` | `checkFruitId`, `createFruit`, `getStock`, `getFruitList` + `private convertToRowList / convertToRow` |
| 7 | `service/OrderService` | `selectFruit`, `addToCart`, `getCart`, `saveOrder`, `cancelShopping`, `getAllOrders` + `private mergeItems / findItem / convertToOrder / calculateAmount` |
| 8 | `view/ShopView` | field `responseDTO` + `setResponseDTO` + `display()` **không tham số**; `private displayStockList / displayFruitList / displayCart / displayOrderList / displayOrder / formatMoney` |
| 9 | `controller/ShopController` | constructor lắp **một** `FruitRepository` cho cả hai service; mỗi hàm: service → `setResponseDTO` → `display()` **1 lần** (`checkFruitId` chỉ để kiểm; `addToCart` không có gì để in) |
| 10 | `constants/Message`, `Constants` | gõ dần khi các bước trên cần; mỗi hằng một dòng comment, cách nhau 1 dòng trống |
| 11 | `utils/Validation` | `getText/getInt/getChoice/getPrice/getStock/getOrderQuantity/getYesNo`; `final` + private constructor |
| 12 | `main/Main` | `final` + private constructor; menu `while` + `switch`; `createFruits` (vòng while); `buyFruits` (menu con) + các hàm bước `chooseItem`, `selectFruit`, `addToCart`, `inputCustomerName`, `saveOrder`, `cancelShopping` |

**Bẫy hay gặp**

1. **Trừ tồn ngay khi bỏ vào giỏ.** Khách chọn 0 để huỷ thì hàng biến mất khỏi kho. Bài này chỉ trừ trong `saveOrder`.
2. **Quên tính phần trong giỏ** khi kiểm tồn (mục 2.2).
3. **Duyệt `Hashtable` để in đơn** thì thứ tự khách bị xáo. Duyệt `customerNameList` (ArrayList).
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
| 17 | 3 | `Y`, tên trống, rồi `Anna` | bảng giỏ (tiêu đề và dòng **thụt 1 dấu cách** như đề) + `Total` · `This field must not be empty.` · `Thank you Anna, your order has been saved.` |
| 18 | 3 | cùng quả chọn 2 lần | giỏ chỉ **1 dòng**, số lượng cộng dồn |
| 19 | 2 | — | từng khách, **đúng thứ tự mua**, dòng đánh số `1.`, `2.`, `Total`; **một dòng trống giữa hai khách** (như đề) |
| 20 | 1 → N sau khi bán | — | cột Quantity đã **giảm** |
| 21 | giá `1.25`, `0.1` | — | `1.25$`, `0.1$`, `0.1 × 3` = `0.3$` (không phải `0.30000000000000004$`) |

---

## 6. Debug

| Việc | Cách làm |
|---|---|
| Breakpoint | dòng `int left = fruit.getQuantity() - inCart;` trong `OrderService.addToCart` |
| Chạy | **Ctrl+F5**, tạo Orange tồn 5, chọn 3 → Orange → gõ 8 |
| Quan sát | tab **Variables**: `fruit.quantity = 5`, `inCart = 0`, `left = 5`, `requestDTO.quantity = 8` → **F8** thấy nhảy vào `throw` |
| Bước vào | breakpoint ở vòng `for (Item item : cartList)` trong `saveOrder` → **F8** từng vòng, xem `fruit.quantity` giảm |
| Xem Hashtable | trong `OrderRepository.addOrder`, mở `orderMap` ở Variables: thấy cặp `"Marry Carie" → ArrayList[2]` |

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
| Vậy làm sao in khách theo thứ tự? | `ArrayList<String> customerNameList` ghi tên lúc **lần đầu** mua; `OrderService.getAllOrders` duyệt list này rồi `get` từ Hashtable, view in theo đúng thứ tự đó. |
| Tra đơn một khách tốn bao nhiêu? | `Hashtable.get` trung bình **O(1)** (băm tên ra vị trí). Tìm quả theo ID là O(n) vì duyệt ArrayList. |
| Khách gõ `anna` rồi `Anna`? | Hai khoá khác nhau (Hashtable phân biệt hoa thường), nên thành hai khách. Muốn gộp thì xem mục 8. |
| Sao khai báo `ArrayList`/`Hashtable` mà không `List`/`Map`? | Thầy: dùng kiểu **cụ thể**; đề cũng gọi đích danh ArrayList và HashTable. |

### Access modifier, static, tham số

| Câu hỏi | Trả lời mẫu |
|---|---|
| Hàm nào `private`? | `mergeItems`, `findItem`, `convertToOrder`, `calculateAmount`, `convertToRowList`, `convertToRow` (chỉ service dùng), `displayStockList`…`formatMoney` (chỉ view dùng), mọi hàm nhập / hàm bước trong `Main` (`private static`). |
| `static` ở đâu? | Chỉ ở: `Validation` (Guide *"phải dùng static method"*), hằng trong `Message`/`Constants`, và **hàm** trong `Main`. Không có **biến** static nào. |
| Bỏ `static` ở hàm của `Main`? | `main` là static nên không gọi được hàm thường; phải `new Main()` rồi gọi qua đối tượng. |
| Hàm nào quá 2 tham số? | `Validation.getChoice(input, min, max)` — utils được 3, đúng mẫu Guide. Constructor `Fruit(...)` 5 tham số được miễn. Còn lại ≤ 2: dữ liệu gói vào `FruitRequestDTO`/`OrderRequestDTO`. |
| `displayFruitList()` trả `int`? | Main cần biết có mấy item để kiểm `0..count` và hỏi lại **ngay** (không in lại danh sách); nó không đụng model, chỉ nhận một con số. View vẫn chỉ render 1 lần. |

### Luồng & thuật toán

| Câu hỏi | Trả lời mẫu |
|---|---|
| Tồn kho bị trừ lúc nào, vì sao? | Trong `saveOrder`, **sau khi** khách gõ tên. Chọn 0 để huỷ thì kho không mất gì. |
| Làm sao không bán quá tồn? | `left = tồn − số đã trong giỏ` (mục 2.2), kiểm **mỗi lần** bỏ vào giỏ. |
| Sao Shopping gọi controller nhiều lần — trái luật "1 lần"? | Màn Shopping là **menu con**: danh sách quả là menu, mỗi lần khách gõ (item, số lượng, Y, tên) mà chương trình phải trả lời **ngay** là một luồng. Mỗi luồng là **một hàm bước** trong `Main` gọi controller **đúng 1 lần** (`chooseItem`, `selectFruit`, `addToCart`, `inputCustomerName`, `saveOrder`, `cancelShopping`), mỗi hàm controller render **nhiều nhất 1 lần** — giống các menu con của mẫu L.P0013. Ghi rõ ở mục "Hỏi thầy". |
| Sao bài có repository? | Tờ checklist 1.1: *"Bắt buộc phải có repository"*. Dữ liệu (quả, giỏ, đơn) nằm hết trong `FruitRepository` / `OrderRepository` với CRUD đơn giản; kiểm tồn và tính tiền ở service. |
| View nhận dữ liệu thế nào? | Qua **thuộc tính**: `private ShopResponseDTO responseDTO` + `setResponseDTO(...)`, rồi `display()` **không tham số** — đúng code mẫu của thầy. `display()` in `message`, `stockList`, `fruitList`, `cart`, `orderList` — phần nào `null` thì bỏ. Hết `showMessage(String)` và các `displayX(list)` có tham số. |
| Validate ở đâu? | **Dạng** dữ liệu (số, > 0, không âm, không trống, Y/N, item 0..n) ở `Main` qua `utils/Validation`, hỏi lại tại chỗ. Luật cần **dữ liệu** (ID trùng, hết hàng, không đủ hàng, chưa có đơn/quả) ở service, `throw new Exception(Message.X)`. ID trùng phải báo **ngay** sau ô ID nên có một lần gọi **chỉ để kiểm** (`checkFruitId`). |
| Giỏ hàng nằm đâu? Tiền tính ở đâu? | Giỏ là **dữ liệu** ⇒ `OrderRepository.cartList` (đề: *"ArrayList to store items"*). Thành tiền từng dòng (`calculateAmount`) và `Total` (`convertToOrder`) là **nghiệp vụ** ⇒ `OrderService`; model `Item` không tự tính. |
| Sao `sc.nextLine()` đặt ngoài `try`? | `try` chỉ nên bọc chỗ có thể sai do **người gõ** (`Validation`). Hết input là lỗi **khác**; để nó bay ra thì chương trình dừng, thay vì quay vô hạn. |
| Độ phức tạp đặt hàng? | O(k·n): mỗi dòng giỏ (k) tìm quả theo ID trong kho (n). Bài nhỏ nên đủ; muốn O(k) thì thêm `Hashtable<id, Fruit>`. |

---

## 8. Thầy đổi yêu cầu tại chỗ

| Thầy bảo | Sửa file | Không phải đụng |
|---|---|---|
| Ẩn quả hết hàng khỏi danh sách khách | `FruitService`: hàm lọc `quantity > 0` cho Shopping. ⚠️ item number phải đi theo danh sách **đã lọc** | view, main |
| Update / Delete fruit | `FruitRepository.updateFruit/deleteFruit` + `FruitService` + 1 case menu + `Message` | `OrderService` (Item đã chép tên + giá) |
| Tên khách không phân biệt hoa thường | `OrderService.saveOrder`: dùng `customerName.toLowerCase()` làm khoá; giữ tên gốc để in | repository |
| Giảm giá 10% khi Total > 100$ | **Strategy**: `service/IDiscountStrategy` (interface, tên bắt đầu bằng `I`) + `NoDiscount`, `TenPercentDiscount`; `OrderService.convertToOrder` gọi `discount.applyDiscount(total)` | view, main |
| Có class `Order` (ngày mua, tổng) | `model/Order` (customerName, `ArrayList<Item>`, date) → `Hashtable<String, Order>` | view (vẫn nhận `OrderResponseDTO`) |
| Lưu vào file | đọc: `Main` đọc tệp qua `utils/FileUtils.readLines` → RequestDTO (`lineList`) → `controller.loadData` → repository parse + lưu (tờ checklist: đọc file ở **Main**); ghi: `FileUtils.writeLines` do repository gọi | service, view |
| Sắp đơn theo tên khách | `OrderService.getAllOrders`: sắp bản sao `findAllCustomers()` | view |

---

## 9. Chỗ khác với đề / bản cũ

| Chỗ | Đề / bản cũ | Bài này | Lý do |
|---|---|---|---|
| Menu | đề in `- Create Fruit`… (mất số) | `1. Create Fruit`… + câu ` (Please choose 1 to create product, …)` — **kể cả dấu cách đầu dòng** như đề | số là thứ người dùng gõ |
| Dòng trống trước menu, `Goodbye.` | đề không ghi | có | giữ đúng bản tham chiếu |
| Bảng sau Create | đề: *"display all Fruits what are created"* | bảng thêm cột **Quantity** | chủ shop cần thấy tồn; bảng của khách vẫn đúng 4 cột đề |
| Bảng giỏ | bản tham chiếu có dấu cách đầu dòng; bản 21/09 bỏ đi (ghi nhầm "đề không có") | **có**: ` Product \| Quantity \| Price \| Amount` và mỗi dòng giỏ thụt 1 dấu cách; `Total:` sát lề | đề (`.docx`, đối chiếu 22/09) viết đúng như vậy |
| View orders | bản tham chiếu và bản 21/09: các khách liền nhau | **một dòng trống giữa hai khách** | màn hình đề có dòng trống đó |
| Thông báo lỗi | đề không ghi | `Message.*` (21 câu, liệt kê ở mục 5) | đề chỉ vẽ happy case |
| Lưu dữ liệu | bản cũ ghi `fruits.txt`, `orders.txt` | chỉ trong bộ nhớ | đề: *"Only use ArrayList and HashTable to store data"*. Vì thế `verify.py` dùng 3 kịch bản riêng (`REPLACE_REFERENCE = True`); kịch bản A gõ đúng phím của bản cũ |
| Kiến trúc | bản cũ: `entity/bo/ui`, Scanner static trong `Validator`, controller in ra | MVC Guide: Scanner chỉ ở `main`, in ở `view`, luật ở `service`, collection ở `repository` | luật thầy |
| Giỏ hàng | bản cũ: class `Cart`; bản 21/09: `ArrayList<Item> cart` trong `OrderService` | `ArrayList<Item> cartList` trong `OrderRepository` | đề: *"Using ArrayList to store items"*; giỏ là **dữ liệu** ⇒ repository (tờ checklist 1.1) |

### Chỗ khác với bản trước (21/09 → 22/09, theo tờ checklist giấy)

| Chỗ | Bản trước | Bản này | Lý do (tờ checklist) |
|---|---|---|---|
| View | `displayStock(list)`, `displayFruitList(list)`, `displayCart(dto)`, `displayOrders(list)`, `showMessage(String)` | **một** thuộc tính `responseDTO` + `setResponseDTO` + `display()` không tham số | 1.1: *View nhận qua thuộc tính*, *render 1 lần/luồng* |
| ResponseDTO | chỉ có DTO từng dòng | thêm `ShopResponseDTO` (câu trả lời của cả luồng); `OrderResponseDTO.items` → `itemList` | 1.1 *"Nên để ResponseDTO giống ví dụ"*; 1.5 |
| Controller | tự dựng câu (`String.format(Message…)`) rồi `showMessage` | service trả `ShopResponseDTO`, controller chỉ `setResponseDTO` + `display()` 1 lần | 1.1 |
| Giỏ, tiền | giỏ là field `cart` của `OrderService`; `Item.getAmount()` tính tiền trong model | giỏ `cartList` ở `OrderRepository`; `OrderService.calculateAmount` + `convertToOrder` tính tiền | 1.1 repository chỉ chứa data, nghiệp vụ tính toán ở service |
| `Main` Shopping | `shopping()` gọi thẳng 6 hàm controller trong một vòng | `buyFruits` là menu con; mỗi bước là một hàm gọi controller 1 lần | 1.1 *mỗi luồng gọi controller 1 lần* |
| `Main` | `public class Main`, `int choice = …`, `String line = …`, `OrderRequestDTO dto = …` khai báo giữa khối | `public final class Main` + `private Main()`; mọi biến khai báo đầu khối và khởi tạo | 3.4, 2.6, 3.7 |
| Tên | `placeOrder`, `viewOrders`, `fruits`, `orders`, `customerNames`, `cart`, `items`, `rows`, `dto` | `saveOrder`, `displayOrders`, `fruitList`, `orderMap`, `customerNameList`, `cartList`, `itemList`, `rowList`, `requestDTO` | 1.4 động từ, 1.5 List/Map |
| Định dạng | field/hằng dính nhau, `choice < min \|\| choice > max`, `formatMoney` cộng chuỗi `+` | 1 dòng trống trước mọi comment/giữa field; `(choice < min) \|\| (choice > max)`; `String.format(Constants.MONEY_FORMAT, …)` | 2.8, 3.3, 3.8 |
| Màn hình | xem 3 dòng đầu bảng trên (câu dưới menu, bảng giỏ, dòng trống giữa hai khách) | khớp từng ký tự với đề | yêu cầu khớp 100% đề; `_tools/tests/J1LP0023.py` cập nhật đúng 3 chỗ đó |

---

## 10. Tờ checklist 25 mục — bài này đạt thế nào

`python3 _tools/soat_checklist.py HE176322_J1LP0023_FruitShop` → **0 VI PHẠM**; còn lại chỉ "rủi ro" chấp nhận được: `String[] args` của `main` và tham số setter/constructor trùng tên field (`this.x = x`).

| Mục | Chỉ vào đâu |
|---|---|
| **1.1** MVC + repository | `repository/FruitRepository`, `OrderRepository` (bắt buộc có; giữ quả, giỏ, đơn); `ShopController` không import `model`, mỗi hàm `setResponseDTO` + `display()` **1 lần**; `ShopView` nhận `responseDTO` qua setter; `Main` nhập + validate, mỗi luồng gọi controller 1 lần (`checkFruitId` chỉ để kiểm, lý do ở mục 3) |
| **1.5** tên collection / Id | `fruitList`, `cartList`, `customerNameList`, `itemList`, `rowList`, `mergedList`, `orderList`, `stockList`, `orderMap`; không tên biến/hàm nào chứa `ID` (chỉ hằng `INPUT_FRUIT_ID`, `ID_EXISTS`) |
| **2.6 / 3.7** khai báo đầu khối + khởi tạo | `Main.main`: `running = true`, `choice = 0`; mọi `Main.inputX`: `String line = ""` rồi trong vòng chỉ gán; `OrderService.saveOrder`: `Fruit fruit = null`; `convertToOrder`: `row = null`, `total = 0`; `Validation.getPrice`: `double price = 0` |
| **2.8** dòng trống | giữa các hằng/field (cả `Message`, `Constants`, DTO), sau vùng khai báo biến, trước mọi comment đứng sau dòng code, giữa các `case`, sau `}` trước câu lệnh tiếp |
| **3.3** ngoặc | `Validation.getChoice`: `if ((choice < min) \|\| (choice > max))`; `OrderService.addToCart`: `(cartItem == null) ? 0 : …` |
| **3.4** lớp chỉ có static | `Main`, `Validation`, `Constants`, `Message`: `final` + `private` constructor |
| **3.8** cộng chuỗi | tiền `String.format(Constants.MONEY_FORMAT, …)`; mọi dòng bảng `String.format(Constants.*_ROW_FORMAT, …)` |

## 11. Hỏi thầy

- **Shopping (case 3) gọi controller nhiều lần.** Đề bắt màn hình xen kẽ: danh sách → chọn → `You selected` → số lượng → câu hỏi Y/N → giỏ → tên. Bài coi mỗi bước là một luồng của **menu con** (mỗi bước 1 lần gọi, 1 lần render). Nếu thầy muốn cả Shopping chỉ 1 lần gọi thì phải gom hết đầu vào rồi mới in (lệch màn hình đề) — nên hỏi thầy chấp nhận cách nào.
- `displayFruitList()` vừa render vừa **trả số quả** cho Main kiểm item `0..n`; nếu thầy không muốn controller trả gì về Main thì tách một hàm **chỉ để kiểm** `countFruits()`.
- Dấu cách đầu câu `(Please choose…`, bảng giỏ thụt 1 dấu cách, dòng trống giữa hai khách là chép **đúng từng ký tự** của đề (`.docx`).
