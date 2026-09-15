# J1.S.P0082 — Playing Cards

> Bài **thiết kế lớp** (Card, Deck) — không có nhập liệu, nhưng **vẫn phải MVC** như mọi bài (thầy:
> *"không có cấu trúc → không review"*, QUY-TAC-THAY §2).

| | |
|---|---|
| Loại / LOC | Short Assignment · 60 LOC · 1 slot |
| Project | `HE176322_J1SP0082_PlayingCards` |
| Chạy | NetBeans: **File ▸ Open Project** → **F6** |
| Lớp chạy | `main.Main` |
| Kiểm tự động | `python3 _tools/verify.py J1SP0082` → 3 kịch bản × 2 locale |

---

## 1. Đề bài nói gì

- Lớp **Card**: 2 thuộc tính `private` **rank**, **suit**; constructor đặt cả hai; `getRank()`,
  `getSuit()`; `toString()` ra chữ kiểu **"Ace of Spades"**; **bất biến** (không cần setter).
- Lớp **Deck**: giữ 52 lá trong mảng hoặc danh sách; **constructor dựng 52 lá bằng 2 vòng lặp lồng
  nhau** (ngoài: 4 chất, trong: 13 bậc); có hàm hiển thị; **tuỳ chọn** `shuffle()` và `deal()`.
- Chương trình thử: tạo 1 bộ bài, in 52 lá.

Màn hình đề (chép đúng chữ, giữa bị lược):

```
========= DECK OF CARDS =========
Deck created with 52 cards.
1. 2 of Clubs
...
13. Ace of Clubs
14. 2 of Diamonds
...
52. Ace of Spades
=================================
Total: 52 cards
```

**Đề bắt buộc:**

| Thứ | Đề viết | Bài này đặt ở |
|---|---|---|
| Lớp `Card` (rank, suit, `getRank`, `getSuit`, `toString`) | Function 1 + sơ đồ lớp | `model/Card.java` |
| Lớp `Deck`, dựng bằng **2 vòng lồng nhau** | Function 2 | `model/Deck.java` — constructor `Deck()` |
| `display()` của Deck | sơ đồ lớp | `view/DeckView.display()` (model không được in — §9) |
| `shuffle()` · `deal()` (tuỳ chọn) | Function 2, Guidelines | `DeckService.shuffle` (private) · `model/Deck.deal()` |
| rank/suit "tốt nhất là enum" | Guidelines | `constants/Rank.java`, `constants/Suit.java` |

---

## 2. Kiến thức cần biết

### 2.1 Enum — vì sao rank/suit là enum

| Kiểu | `new Card("Purple", "11")` | Duyệt đủ 13 bậc |
|---|---|---|
| `String` | biên dịch **được** → lá bài vô nghĩa | phải tự viết mảng chuỗi |
| **enum** | **không biên dịch được** — chỉ có 13 bậc × 4 chất | `Rank.values()` |

Mỗi hằng enum mang **chữ in ra màn hình**: `TEN("10")`, `QUEEN("Queen")`, `SPADES("Spades")`.
Thứ tự khai báo **chính là** thứ tự in: `values()` trả `TWO, THREE, …, ACE`.

### 2.2 Dựng bộ bài — 2 vòng lồng nhau (chạy tay)

```java
for (Suit suit : Suit.values()) {        // ngoài: 4 lần
    for (Rank rank : Rank.values()) {    // trong: 13 lần mỗi chất
        cards.add(new Card(rank, suit));
    }
}
```

| suit (vòng ngoài) | rank (vòng trong) | Lá thêm vào | Số thứ tự in |
|---|---|---|---|
| CLUBS | TWO … ACE | 2 of Clubs … Ace of Clubs | 1 … 13 |
| DIAMONDS | TWO … ACE | 2 of Diamonds … Ace of Diamonds | 14 … 26 |
| HEARTS | TWO … ACE | 2 of Hearts … Ace of Hearts | 27 … 39 |
| SPADES | TWO … ACE | 2 of Spades … Ace of Spades | 40 … 52 |

→ 4 × 13 = **52**, đúng từng số thứ tự của màn hình đề (13 = Ace of Clubs, 14 = 2 of Diamonds…).

### 2.3 Trộn Fisher–Yates (đề gợi ý "a Fisher–Yates loop")

Đi từ lá **trên cùng** (`i = 51`) xuống `i = 1`: chọn ngẫu nhiên `j` trong `[0, i]`, đổi chỗ `i` với `j`.

| i | j ngẫu nhiên (ví dụ) | Làm gì |
|---|---|---|
| 51 | 7 | đổi lá 51 ↔ lá 7 → lá 51 đã "chốt" |
| 50 | 50 | đổi với chính nó (được phép) |
| … | … | … |
| 1 | 0 | đổi lá 1 ↔ lá 0 → xong |

Mỗi lá chạm 1 lần → **O(n)**; mọi thứ tự 52! có xác suất như nhau.

### 2.4 `deal()` lấy lá ở **cuối** danh sách

`ArrayList.remove(size - 1)` không phải dời phần tử nào; `remove(0)` dời cả 51 lá. "Lá trên cùng" là
đầu nào do mình quy ước — chọn đầu rẻ.

### 2.5 Java dùng trong bài

| API | Dùng làm gì |
|---|---|
| `Suit.values()`, `Rank.values()` | mảng các hằng enum theo thứ tự khai báo |
| `new Random().nextInt(i + 1)` | số ngẫu nhiên trong `[0, i]` |
| `ArrayList.set / get / remove` | đổi chỗ, lấy, rút lá |
| `String.format("%d. %s", n, card)` | dòng `1. 2 of Clubs` |

---

## 3. Thiết kế

```
HE176322_J1SP0082_PlayingCards/src/
├── model/      Card                   rank + suit (enum), toString "Ace of Spades"
│               Deck                   ArrayList<Card>, constructor 2 vòng lồng, size/getCard/swap/deal
├── dto/        DeckResponseDTO        52 chữ + tay bài + số lá còn  (controller ──► view)
├── service/    DeckService            dựng bộ · trộn Fisher–Yates · chia bài
├── controller/ DeckController         cắm Fisher–Yates vào service; service ──► view
├── view/       DeckView               in màn hình (thay Deck.display của đề)
├── constants/  Suit, Rank             enum 4 chất, 13 bậc
│               Message, Constants     câu chữ · HAND_SIZE = 5
└── main/       Main                   gọi controller 1 lần
```

| Câu hỏi thiết kế | Trả lời |
|---|---|
| Sao **không có** `utils/Validation` và `RequestDTO`? | Chương trình **không đọc bàn phím** (đề: *"as simple as creating a deck … and displaying"*). Validation chỉ để kiểm chuỗi người dùng gõ; RequestDTO chở dữ liệu main → controller — ở đây không có gì để chở. Tạo lớp rỗng là trừu tượng thừa (ghi chú slide 26 SOLID). |
| Sao có `service` mà không có `repository`? | Không có menu thêm/sửa/xoá, không lưu gì qua các lần chạy. Trộn/chia là **nghiệp vụ** → service. |
| Sao Deck **không tự trộn**? | Trộn là **nghiệp vụ** (có nhiều cách, đề nêu 2) → nằm ở `DeckService`; `Deck` chỉ cho phép `swap`. |
| Controller có đụng `Card`/`Deck` không? | Không — Guide: controller *"chỉ import DTO, View, Service"*; service đổi Card thành chữ trước khi trả. |

**Luồng chạy:**

```
Main ──► controller.showDeck()
   controller ──► service.createDeck()
                     ├─ new Deck()                   → 52 lá (2 vòng lồng)
                     ├─ response.setCards(toTexts)   ← chụp TRƯỚC khi trộn
                     ├─ shuffle(deck)                ← Fisher–Yates
                     ├─ 5 × deck.deal()              → tay bài
                     └─ response.setRemaining(47)
   controller ──► view.setResponse(response) ──► view.display()
```

### 3.1 Design Pattern trong bài

| Pattern | Ở đâu |
|---|---|
| **MVC** — thầy gọi là "MVC JSP" | controller điều hướng (như Servlet) · view hiển thị (như trang JSP) · model là JavaBean |
| **Facade** | controller: `Main` chỉ gọi `controller.showDeck()`, không biết service/model/view phía sau |

> Bài chỉ 60 LOC nên **không thêm lớp pattern GoF** — ghi chú slide SOLID của thầy cảnh báo *"trừu tượng hoá sớm … vi phạm YAGNI"*. Vòng trộn Fisher–Yates nằm gọn trong **một hàm** `shuffle` của `DeckService`.

**Thầy hỏi "dùng pattern gì cho dễ mở rộng?"** → trả lời đủ 4 yếu tố GoF (slide *"Elements of a Design Pattern"*):

| Yếu tố | Trả lời |
|---|---|
| **Name** | Strategy (nhóm Behavioral) |
| **Problem** | có nhiều cách trộn (Fisher–Yates, `Collections.shuffle`, cắt đôi xen kẽ…) — đề nêu 2 cách |
| **Solution** | tách `interface ShuffleStrategy { void shuffle(Deck deck); }`; mỗi cách làm là 1 lớp `implements` nó (`FisherYatesShuffleStrategy`…); `DeckService` nhận strategy qua constructor |
| **Consequences** | ➕ thêm cách làm = thêm 1 lớp, service đứng yên (**O**) · ➖ thêm 2 file — chỉ đáng khi có từ 2 cách trở lên |

### 3.2 SOLID trong bài

| Nguyên lý | Ở đâu |
|---|---|
| **S** | `Card` mô tả 1 lá · `Deck` giữ bộ bài · `DeckService` dựng + trộn + chia · `DeckView` in |
| **O** | đổi cách trộn chỉ sửa **một hàm** `shuffle`; thêm rank/suit chỉ sửa enum |
| **L** | bài chưa có lớp con riêng — chỉ `extends Object` |
| **I** | không có interface — bài chưa cần |
| **D** | `Main` chỉ phụ thuộc controller |

---

## 4. Code từng bước

> Thầy: *"code cái **model trước rồi đến data**"* (V8). Hai enum là kiểu của field model nên gõ ngay
> trước model.

| Bước | File | Việc |
|---|---|---|
| 1 | `constants/Suit.java`, `Rank.java` | enum: hằng + field `label` + constructor `private` + `getLabel()` |
| 2 | `model/Card.java` | 2 field `private` + constructor rỗng + `Card(rank, suit)` + 2 getter + `toString` |
| 3 | `model/Deck.java` | `ArrayList<Card>` + constructor **2 vòng lồng** + `size/getCard/getCards/swap/deal` |
| 4 | `dto/DeckResponseDTO.java` | JavaBean 3 field |
| 5 | `service/DeckService.java` | `createDeck()` + `toTexts` (private) |
| 6 | `view/DeckView.java` | `setResponse` · `display` |
| 7 | `controller/DeckController.java` | `new DeckService()`; `showDeck()` |
| 8 | `constants/Message.java`, `Constants.java` | câu chữ, `HAND_SIZE` (gõ dần khi cần) |
| 9 | `main/Main.java` | gọi `controller.showDeck()` trong `try` |

**Bẫy hay gặp:**

1. Đảo 2 vòng (ngoài rank, trong suit) → vẫn 52 lá nhưng **sai thứ tự** (2 of Clubs, 2 of Diamonds…).
2. Liệt kê bộ bài **sau khi** trộn → không ra đúng màn hình đề. Phải chụp chữ **trước** `shuffle`.
3. `getCards()` trả thẳng `cards` → nơi khác `add` được lá thứ 53. Trả **bản sao**.
4. Fisher–Yates viết `random.nextInt(i)` (thiếu `+ 1`) → lá không bao giờ được ở yên chỗ — trộn lệch.

---

## 5. Test trước khi gọi thầy

Bài không có nhập liệu nên không có message validation nào hiện được bằng bàn phím.

| # | Làm | Phải thấy |
|---|---|---|
| 1 | **F6** | dòng 1 `========= DECK OF CARDS =========`, dòng 2 `Deck created with 52 cards.` |
| 2 | nhìn các số mốc | `1. 2 of Clubs` · `13. Ace of Clubs` · `14. 2 of Diamonds` · `26. Ace of Diamonds` · `27. 2 of Hearts` · `39. Ace of Hearts` · `40. 2 of Spades` · `52. Ace of Spades` |
| 3 | cuối phần đề | `=================================` rồi `Total: 52 cards` |
| 4 | phần tuỳ chọn | `--- After shuffling, dealing 5 cards ---`, 5 dòng `- <lá>` **khác nhau**, `Cards left in the deck: 47` |
| 5 | chạy lại **F6** | 5 lá chia ra **khác** lần trước (đã trộn thật) |
| 6 | `Message.EMPTY_DECK` | chỉ xảy ra khi `deal()` quá 52 lần — thử: sửa `HAND_SIZE = 53` → in `The deck is empty.` (nhớ sửa lại 5) |

---

## 6. Debug

| Việc | Cách làm |
|---|---|
| Breakpoint | dòng `cards.add(new Card(rank, suit));` trong constructor `Deck()` |
| Chạy | **Ctrl+F5** |
| Quan sát | tab **Variables**: `suit`, `rank`, `cards.size()` tăng 1 mỗi lần **F5** (Continue) |
| Chứng minh vòng lồng | sau 13 lần F5, `suit` đổi từ `CLUBS` sang `DIAMONDS`, `rank` quay về `TWO` |
| Trộn | breakpoint `deck.swap(i, j);` trong `DeckService.shuffle` — xem `i` giảm dần, `j ≤ i` |
| Vào hàm trộn | ở `DeckService.createDeck` bấm **F7** vào `shuffle(deck)` → vào vòng Fisher–Yates |

---

## 7. Câu hỏi thầy hay hỏi

### OOP / thiết kế

| Câu hỏi | Trả lời mẫu |
|---|---|
| 4 tính chất OOP ở đâu? | **Đóng gói**: `rank`, `suit`, `cards` là `private`; `Deck.getCards()` trả bản sao. **Kế thừa**: mọi lớp ngầm `extends Object`, em ghi đè `toString()`. **Đa hình**: `Card.toString()` có `@Override` — in cả bộ bài tự gọi đúng bản của `Card`. **Trừu tượng**: `Main` chỉ gọi `controller.showDeck()`. |
| Quan hệ Deck – Card? | **Composition** (hình thoi đặc trong sơ đồ đề): Deck tự tạo 52 lá, lá không tồn tại riêng ngoài bộ bài trong chương trình này. Bội số 1 — 52. |
| Sao Card **không có setter**? | Đề: *"A card is immutable once created — no setters are required"*. Không ai biến được Ace of Spades thành 2 of Clubs giữa chừng. |
| Không có setter thì còn là JavaBean không? | Có đủ phần thầy yêu cầu (V10): field `private`, **constructor rỗng `public`**, getter. Constructor rỗng cho lá đầu tiên của bộ mới (`2 of Clubs`) để lá bài không bao giờ "rỗng". Setter bỏ **vì đề bắt bất biến** — thầy muốn JavaBean đủ get/set thì thêm `setRank/setSuit` là xong. |
| Sao `Deck` không có `display()` như sơ đồ đề? | Guide: model *"không được … output (printf)"*. Hiển thị là việc của `DeckView.display()`. |
| `deal()` trả `Card` vì sao? | Nó **rút** một lá và đưa lá đó cho người gọi — đúng sơ đồ đề `deal() : Card`. |
| `shuffle` trả `void` vì sao? | Nó sắp lại **chính** bộ bài được truyền vào (tham chiếu), không tạo gì mới. |
| `size()` trả `int`? | Là số đếm. |
| `swap`, `getCard` sao `public`? | `DeckService` (lớp khác, package khác) gọi chúng. `toTexts` chỉ `DeckService` dùng → `private`. |
| Có chữ `static` nào? | Chỉ ở `constants` (hằng `Message`, `Constants`) và `main()`. Enum không cần `static` tự viết. `Main` không có biến static. Bỏ `static` ở `Message.TITLE` thì `Message.TITLE` báo lỗi biên dịch — phải `new Message()` mà constructor `private`. |
| Constructor enum sao `private`? | Chỉ các hằng khai báo sẵn được tồn tại; không ai `new Rank(...)` được. (Enum ngầm định đã private — em ghi rõ cho dễ đọc.) |
| **Sao `ArrayList<Card>` mà không `List<Card>`** (sơ đồ đề ghi `List<Card>`)? | `List` là **interface** (hợp đồng: add/get/remove), `ArrayList` là **lớp cài đặt** bằng mảng động — `get(i)`/`set(i)` theo chỉ số **O(1)**, rút ở cuối O(1); `LinkedList` cài cùng hợp đồng bằng danh sách liên kết, `get(i)` phải đi từng nút O(n). Fisher–Yates `swap` truy cập theo chỉ số liên tục → em **cần** mảng động, nên khai báo đúng `ArrayList`. Đề cho phép *"Card[52] or a List<Card>"*. |
| Sao không dùng `Card[52]`? | Mảng cố định không "rút" lá được — `deal()` phải tự giữ biến đếm. `ArrayList` tự co lại. |

### Thuật toán

| Câu hỏi | Trả lời mẫu |
|---|---|
| Vì sao ra đúng 52 lá? | Vòng ngoài 4 lần × vòng trong 13 lần = 52 lần `add`. |
| Fisher–Yates độ phức tạp? | O(n): mỗi vị trí 1 lần swap. |
| Sao `nextInt(i + 1)` mà không `nextInt(52)`? | `nextInt(52)` mỗi bước ("trộn ngây thơ") cho các thứ tự **không đều** xác suất; Fisher–Yates chỉ chọn trong phần chưa chốt `[0, i]`. |
| Trộn bằng thư viện? | `Collections.shuffle(list)` — em viết tay để hiểu. |

---

## 8. Thầy đổi yêu cầu tại chỗ

| Thầy bảo | Sửa | Không đụng |
|---|---|---|
| Chia **7** lá thay 5 | `Constants.HAND_SIZE = 7` | mọi file khác |
| Thêm 2 lá **Joker** (54 lá) | `Rank`/`Suit` không đủ → thêm vòng nhỏ sau 2 vòng lồng trong `Deck()` + hằng mới | controller, view |
| Ace đứng **đầu** (Ace, 2, …, King) | đổi thứ tự khai báo trong `Rank` | `Deck` (vẫn `values()`) |
| Trộn bằng `Collections.shuffle` | `DeckService.shuffle`: lấy danh sách, `Collections.shuffle`, ghi lại (thêm `setCards` vào `Deck`) | `Main`, `DeckView` |
| In bộ bài **sau** khi trộn | `DeckService.createDeck`: đổi thứ tự `setCards` ↔ `shuffle` | view |
| Card có setter (JavaBean đủ) | thêm `setRank`, `setSuit` vào `Card` | mọi file khác |

---

## 9. Chỗ khác với đề / bản cũ

| Chỗ | Đề / bản cũ | Bài này | Lý do |
|---|---|---|---|
| Kiểu rank/suit | sơ đồ đề: `String` | enum `Rank`, `Suit` trong `constants/` | Guidelines: *"(best) as enum types"*; Guide: Constants chứa *"hằng số, enum"* |
| `getRank()` / `getSuit()` | trả `String` | trả `Rank` / `Suit` | đi theo kiểu enum; chữ lấy bằng `getLabel()` |
| `Deck.display()` | trong Deck (model) | `DeckView.display()` | model không được in (Guide) |
| `Deck.shuffle()` | trong Deck | `DeckService.shuffle` (private) | trộn là nghiệp vụ → service; `Deck` chỉ cung cấp `swap` |
| `cards : List<Card>` | sơ đồ đề | `ArrayList<Card>` | thầy dặn kiểu cụ thể (V5); đề cho phép Card[] hoặc List |
| Card không setter + constructor rỗng | đề: bất biến, không setter | giữ bất biến + thêm `Card()` = 2 of Clubs | V10 cần constructor rỗng `public` |
| Phần trộn/chia 5 lá | đề: tuỳ chọn, màn hình không có | có, in **sau** `Total: 52 cards` | giữ nguyên bản tham chiếu đã kiểm; đề cho phép |
| Không có `utils/`, `RequestDTO` | khung chuẩn có | bỏ | không có nhập liệu (xem mục 3) |
| Kiến trúc | bản cũ `entity/ui`, enum lồng trong Card | MVC theo Guide | luật thầy |
