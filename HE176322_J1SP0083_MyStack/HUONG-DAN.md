# J1.S.P0083 — MyStack (Stack)

> Bài nhỏ nhưng thầy soi kỹ **đóng gói**: stack chỉ được đổi qua `push`/`pop` — không ai được chèn
> vào giữa. Và **pop/get khi stack rỗng không được văng chương trình**.

| | |
|---|---|
| Loại / LOC | Short Assignment · 40 LOC · 1 slot |
| Project | `HE176322_J1SP0083_MyStack` |
| Chạy | NetBeans: **File ▸ Open Project** → **F6** |
| Lớp chạy | `main.Main` |
| Kiểm tự động | `python3 _tools/verify.py J1SP0083` → 6 kịch bản × 2 locale |
| Tờ checklist giấy của thầy | 25 mục — bài này đạt thế nào: **mục 10** |

---

## 1. Đề bài nói gì

> **Đề gốc thầy phát** (`J1.S.P0083.txt`) chỉ nói: lớp `MyStack` có thuộc tính `stackValues` và 3 hàm
> `push()`, `pop()`, `get()`, viết ứng dụng *"demo how stack works"*; Guidelines **NA**. Menu, màn
> hình và cách xử lý stack rỗng dưới đây lấy từ **bản đề mở rộng** trên trang CodeLab — không trái
> đề gốc.

- Tạo lớp **`MyStack`** có thuộc tính `stackValues` và 3 hàm `push()`, `pop()`, `get()`.
- Stack là **LIFO** — vào sau ra trước; mọi thao tác ở **đỉnh** (top).
- Chương trình demo có menu: **1.Push · 2.Pop · 3.Get(peek) · 4.Display · 0.Exit**.
- `pop()`/`get()` trên stack rỗng: **báo rõ ràng**, không crash.

Màn hình đề (chép đúng chữ — menu chỉ in **một lần** lúc đầu):

```
======= STACK DEMO (MyStack) =======
1.Push   2.Pop   3.Get(peek)   4.Display   0.Exit
====================================
Choose: 1
Enter value: 10
Pushed 10.   Stack (top -> bottom): [10]
Choose: 3
Get (top) = 30           (not removed)
Choose: 2
Popped 30.   Stack (top -> bottom): [20, 10]
Choose: 0
Goodbye!
```

**Đề bắt buộc:**

| Thứ | Đề viết | Bài này đặt ở |
|---|---|---|
| Lớp `MyStack`, thuộc tính `stackValues` | *"Create the MyStack class"* | `model/MyStack.java`, field `private ArrayList<Integer> stackValueList` — dòng ngay trên có `// brief: stackValues` (đổi tên vì checklist 1.5, xem mục 9) |
| `push()` · `pop()` · `get()` | *"Methods"* | `MyStack` (hành vi của chính cái stack) — giữ **đúng tên** đề |
| `isEmpty()` · `size()` | *"Helper members such as ..."* (bản mở rộng) | `MyStack.isEmpty()` · `MyStack.countValues()` (tên mở đầu bằng động từ — checklist 1.4) |
| Stack rỗng | *"print a clear message (or throw a handled exception)"* | `MyStack.pop/get` ném `Exception("Stack is empty.")`, `Main` bắt và in |

---

## 2. Kiến thức cần biết

### 2.1 Stack chạy tay — bảng của đề

`ArrayList` với **đỉnh = phần tử CUỐI** (chỉ số `size() - 1`):

| Thao tác | `stackValueList` (đáy → đỉnh) | In ra (đỉnh → đáy) | Trả về |
|---|---|---|---|
| `push(10)` | `[10]` | `[10]` | – |
| `push(20)` | `[10, 20]` | `[20, 10]` | – |
| `push(30)` | `[10, 20, 30]` | `[30, 20, 10]` | – |
| `get()` | `[10, 20, 30]` | `[30, 20, 10]` | **30** (không xoá) |
| `pop()` | `[10, 20]` | `[20, 10]` | **30** |
| `pop()` | `[10]` | `[10]` | **20** |

→ Lưu **đáy trước**, in **đỉnh trước**: `toString()` chép ra list mới rồi `Collections.reverse`.

### 2.2 Vì sao đỉnh ở CUỐI list, không ở đầu

| Đỉnh ở | `push` / `pop` | Hệ quả |
|---|---|---|
| **cuối** (`add(v)`, `remove(size-1)`) | không dời phần tử nào | **O(1)** |
| đầu (`add(0, v)`, `remove(0)`) | dời **mọi** phần tử 1 ô | O(n) mỗi lần |

### 2.3 Java dùng trong bài

| API | Dùng làm gì | Bẫy |
|---|---|---|
| `list.remove(list.size() - 1)` | lấy + xoá đỉnh | với `ArrayList<Integer>`, `remove(int)` là **xoá theo chỉ số**; `remove(Integer)` mới là xoá theo giá trị |
| `Collections.reverse(list)` | đảo để in đỉnh trước | đảo **bản sao** (`topFirstList`), không đảo `stackValueList` thật |
| `new ArrayList<>(list)` | bản sao | getter trả bản sao → bên ngoài không chèn vào giữa được |
| `Integer.parseInt` | đổi chuỗi → số | `"1.5"`, `"abc"`, `""`, `99999999999` đều ném `NumberFormatException` |

---

## 3. Thiết kế

```
HE176322_J1SP0083_MyStack/src/
├── constants/  Message.java           menu, prompt, câu kết quả (chép từ đề)
│               Constants.java         số menu 0..4
├── model/      MyStack.java           stackValueList (// brief: stackValues) +
│                                      push/pop/get/isEmpty/countValues/toString
├── dto/        StackRequestDTO        value    (main ──► controller)
│               StackResponseDTO       message  (controller ──► view): dòng cần in
├── repository/ StackRepository        giữ 1 MyStack sống suốt vòng menu; soạn dòng kết quả
├── controller/ StackController        repository ──► view (setResponseDTO + display 1 lần)
├── view/       StackView              field responseDTO + display() không tham số
├── utils/      Validation             getInt, getChoice
└── main/       Main                   final + private Main(); Scanner, menu, bắt lỗi
```

| Lớp | Làm gì | Vì sao ở đây |
|---|---|---|
| `MyStack` | quy tắc LIFO | Guide: model giữ *"thuộc tính và function của đối tượng"*; push/pop/get **là** hành vi của stack |
| `StackRepository` | giữ stack giữa các lần chọn menu; lấy `myStack.toString()` soạn dòng kết quả vào DTO | Guide: repository *"chứa data … CRUD đơn giản"*: push = thêm, pop = xoá, get/display = đọc; Guide model: *"Cần output gì thì thêm hàm toString() để trả lại repository -> controller sẽ nhận kết quả và truyền vào view"* |
| `StackController` | điều hướng | không Scanner, không print, **không import model** |
| `StackView` | in **một** dòng `responseDTO.getMessage()` | chỉ view và main được in; nhận dữ liệu qua **thuộc tính** |
| `Validation` | chuỗi → số, hoặc ném lỗi | `final`, constructor `private`, hàm `static` |

Không có `service/`: bài không có tính toán nghiệp vụ ngoài CRUD — đúng câu checklist 1.1
*"Nếu có nghiệp vụ tính toán thì cần thêm Services"* (không có thì Controller → Repository → Model).

| Câu hỏi thiết kế | Trả lời |
|---|---|
| **Sao bài có repository?** | Checklist 1.1: *"Bắt buộc phải có repository"*. Ở bài này repository là **nơi giữ dữ liệu** của chương trình — cái stack (`private MyStack myStack`) sống suốt vòng menu — và làm CRUD đơn giản trên nó. |
| **View nhận dữ liệu thế nào?** | Qua **thuộc tính**: `StackView` có field `private StackResponseDTO responseDTO` + `setResponseDTO(...)`; `display()` **không tham số** in `responseDTO.getMessage()`. Mỗi hàm controller gọi `setResponseDTO` rồi `display()` **đúng 1 lần** (checklist 1.1: *"rendering … chỉ được gọi 1 lần cho 1 luồng xử lý"*). |
| **Validate ở đâu?** | Chỉ ở **`Main`**: `inputChoice` → `Validation.getChoice`, `inputValue` → `Validation.getInt`. Giá trị đã sạch mới vào `StackRequestDTO`. Controller/repository không đọc bàn phím. |

**Luồng chạy:** Main → RequestDTO → Controller → Repository → Model; ResponseDTO → View **1 lần**.

**Luồng Pop:**

```
Main: chọn 2 ──► controller.pop()                         (1 lần cho case 2)
                   ├─ repository.pop() ──► myStack.pop()   rỗng? throw "Stack is empty."
                   │                   └─► toResponse(String.format(POPPED, value, myStack.toString()))
                   └─ view.setResponseDTO(responseDTO); view.display()
                                                 → "Popped 30.   Stack (top -> bottom): [20, 10]"
Main: catch (Exception e) → in e.getMessage()        (khi rỗng)
```

### 3.1 Design Pattern — **Facade** (+ MVC)

| Yếu tố | Trong bài này |
|---|---|
| **Name** | Facade (nhóm Structural) — cùng với khung **MVC** bắt buộc |
| **Problem** | `Main` cần làm 4 việc với stack, nhưng phía sau có 3 lớp (repository, model, view). Nếu `Main` tự gọi từng lớp thì nó phải biết cả model — trái luật Guide. |
| **Solution** | `StackController` = **Facade**: 4 cửa `push(requestDTO)`, `pop()`, `get()`, `displayStack()`. Bên trong nó gọi `StackRepository` (→ `MyStack`) và `StackView` = các **subsystem classes**. |
| **Consequences** | ✅ `Main` chỉ biết 1 lớp; đổi cách lưu (mảng thay `ArrayList`) không đụng `Main`. ❌ Controller thêm một tầng gọi qua. |

**Nói thật với thầy:** bài này không có "họ đối tượng" hay "nhiều thuật toán", nên em **không nhét**
Strategy/Factory/Template cho có — ghi chú slide 26 SOLID của thầy cảnh báo trừu tượng hoá sớm
(YAGNI). Nếu thầy hỏi thêm: `MyStack` bọc `ArrayList` và chỉ lộ `push/pop/get` — ý giống
**Adapter** (đổi "giao diện list" thành "giao diện stack"), nhưng em không tạo interface riêng cho nó.

### 3.2 SOLID trong bài

| Nguyên lý | Ở đâu |
|---|---|
| **S** | `MyStack` chỉ lo LIFO · `StackRepository` giữ dữ liệu · `StackView` in · `Validation` kiểm |
| **O** | thêm menu "Size" = thêm 1 hàm repository + 1 hàm controller; view **không đổi** (vẫn in `message`) |
| **L/I/D** | không có lớp cha/interface trong bài — không áp dụng (không tạo cho có) |

---

## 4. Code từng bước

> Thầy: *"code cái **model trước rồi đến data**"*.

| Bước | File | Việc |
|---|---|---|
| 1 | `model/MyStack.java` | field `stackValueList` (dòng trên: `// brief: stackValues`) + constructor rỗng + get/set (trả **bản sao**) + `push` · `pop` · `get` · `isEmpty` · `countValues` · `toString` |
| 2 | `dto/StackRequestDTO.java`, `StackResponseDTO.java` | JavaBean: constructor rỗng + get/set (`value`; `message`) |
| 3 | `repository/StackRepository.java` | field `myStack` (tạo trong constructor) · `push(requestDTO)` · `pop()` · `get()` · `getStack()` · `toResponse(message)` (private) |
| 4 | `view/StackView.java` | field `responseDTO` + `setResponseDTO` + `display()` **không tham số** |
| 5 | `controller/StackController.java` | 4 hàm `push`, `pop`, `get`, `displayStack`; mỗi hàm: repository → `setResponseDTO` → `display()` 1 lần |
| 6 | `constants/Message.java`, `Constants.java` | chép chữ từ đề (**3 dấu cách** sau `Pushed 10.`, **11 dấu cách** trước `(not removed)`) |
| 7 | `utils/Validation.java` | `getInt`, `getChoice` (tách 2 lỗi; `if ((choice < min) \|\| (choice > max))`) |
| 8 | `main/Main.java` | `public final class Main` + `private Main()`; in menu **1 lần**, vòng `while` + `switch`, `inputChoice`, `inputValue`, `push` |

**Bẫy hay gặp:**

1. `pop()` trên stack rỗng gọi `remove(-1)` → `IndexOutOfBoundsException` văng chương trình. Phải kiểm `isEmpty()` **trước**.
2. Trả `-1` khi rỗng → không phân biệt được với số `-1` người dùng đã push (test #5 push `-7`).
3. Getter trả thẳng `stackValueList` → lớp khác `getStackValueList().add(0, 99)` chèn vào đáy, phá LIFO. Trả **bản sao**.
4. In đáy trước (`[10, 20, 30]`) — đề in **đỉnh trước** `[30, 20, 10]`.
5. Khai báo `String line = sc.nextLine();` **trong** vòng lặp → sai checklist 2.6/3.7. Khai báo
   `String line = "";` ở **đầu** hàm, trong vòng lặp chỉ gán `line = sc.nextLine();`.

---

## 5. Test trước khi gọi thầy

| # | Gõ | Phải thấy |
|---|---|---|
| 1 | `1` `10`, `1` `20`, `1` `30` | `Pushed 30.   Stack (top -> bottom): [30, 20, 10]` |
| 2 | `3` | `Get (top) = 30           (not removed)` — stack **không đổi** |
| 3 | `2`, `2` | `Popped 30. ... [20, 10]` rồi `Popped 20. ... [10]` (LIFO) |
| 4 | `4` | `Stack (top -> bottom): [10]` |
| 5 | stack rỗng: `2`, `3` | `Stack is empty.` (hai lần, không crash) |
| 6 | stack rỗng: `4` | `Stack (top -> bottom): []` |
| 7 | menu: `x`, *(Enter trống)*, `2.5` | `You must input a number.` |
| 8 | menu: `9`, `-1` | `Value must be between 0 and 4.` |
| 9 | value: `abc`, *(trống)*, `1.5`, `99999999999` | `You must input a number.` rồi hỏi lại `Enter value:` |
| 10 | value: `-7` rồi `2` | `Pushed -7. ... [-7]` rồi `Popped -7.   Stack (top -> bottom): []` |
| 11 | `0` | `Goodbye!` |

---

## 6. Debug

| Việc | Cách làm |
|---|---|
| Breakpoint | dòng `if (isEmpty()) {` trong `MyStack.pop` |
| Chạy | **Ctrl+F5**, push `10`, `20`, rồi chọn `2` |
| Bước | từ `StackController.pop` bấm **F7** vào `stackRepository.pop()`, **F7** tiếp vào `myStack.pop()`; **F8** qua `remove(...)` |
| Quan sát | tab **Variables**: `stackValueList` giảm từ 2 còn 1 phần tử; giá trị trả về là 20 |
| Xem dòng kết quả | breakpoint `stackView.display();` trong `StackController.pop` — mở `responseDTO` → `message` = `Popped 20.   Stack (top -> bottom): [10]` **trước** khi in |
| Kịch bản rỗng | pop tới rỗng, pop thêm lần nữa: F8 nhảy vào `throw`, **F5** → rơi vào `catch` trong `Main` in `Stack is empty.` |

---

## 7. Câu hỏi thầy hay hỏi

### OOP — "chỉ vào code"

| Câu hỏi | Trả lời mẫu |
|---|---|
| 4 tính chất OOP ở đâu? | **Đóng gói**: `stackValueList` `private`, getter trả bản sao → chỉ đổi được qua `push/pop`. **Kế thừa**: `MyStack` `extends Object` ngầm và ghi đè `toString()`. **Đa hình**: `toString()` có `@Override` — `myStack.toString()` chạy bản của `MyStack` (đỉnh trước). **Trừu tượng**: `Main` gọi `controller.pop()` mà không biết bên trong là `ArrayList`. |
| LIFO là gì? | Last In First Out — thứ vào **sau cùng** ra **đầu tiên**; ví dụ chồng đĩa. |
| Stack rỗng thì `pop` trả gì? | Không trả — **ném** `Exception("Stack is empty.")`; `Main` bắt và in. Trả số đặc biệt thì lẫn với dữ liệu thật. |

### ArrayList hay List?

| Câu hỏi | Trả lời mẫu |
|---|---|
| Sao khai `ArrayList<Integer>` mà không `List<Integer>`? | `List` là **interface** (hợp đồng: có thứ tự, lấy theo chỉ số). `ArrayList` là **lớp cài đặt** bằng mảng động: lấy/xoá **ở cuối** O(1) — đúng thứ stack cần. Em khai kiểu cụ thể vì em **chọn** nó có lý do. |
| Khác nhau thế nào? | `List x = new ArrayList<>()` → sau đổi sang `LinkedList` chỉ sửa vế phải. `ArrayList x` → dùng được cả hàm riêng của `ArrayList` (`ensureCapacity`, `trimToSize`). Hành vi chạy **như nhau**. |
| Sao `Integer` mà không `int`? | Collection chỉ chứa **đối tượng**; `int` được tự đóng hộp (autoboxing) thành `Integer`. |

### Access modifier, static, kiểu trả về

| Thành phần | Vì sao |
|---|---|
| mọi field `private` | đóng gói — không lớp nào đụng thẳng |
| `MyStack.push/pop/get` `public` | repository gọi |
| `MyStack.isEmpty/countValues` `public` | đề gọi là *"helper members"* của lớp — là hợp đồng công khai của stack (`isEmpty` đang được `pop/get` dùng; `countValues` — `size()` của đề — hiện không ai gọi, giữ vì đề nêu) |
| `MyStack.getStackValueList/setStackValueList` `public` | JavaBean (MVC JSP); cả hai **chép** list để không phá LIFO |
| `StackRepository.toResponse` `private` | chỉ repository dựng DTO |
| `StackView.setResponseDTO` + `display()` `public` | controller (lớp khác) gọi; `display()` **không tham số** — dữ liệu đã nằm trong field `responseDTO` |
| `Main` `final` + `private Main()` | checklist 3.4: lớp chỉ có hàm static phải `final` + constructor `private` |
| hàm `Main.inputChoice/inputValue/push` `private static` | chỉ `main` gọi; `static` vì `main` là static, và Guide *"cấm static với biến, có thể dùng với hàm"* |
| `Validation.getInt/getChoice` `public static` | Guide: utils *"phải dùng static method"*; không dùng dữ liệu đối tượng nào |
| hằng `Message/Constants` `public static final` | dùng chung, không đổi, gọi qua tên lớp |
| **Bỏ `static` ở `Validation`?** | `Validation.getInt(...)` báo lỗi biên dịch; phải bỏ constructor `private`, tạo `new Validation()` trong `Main` rồi gọi qua đối tượng. |
| `pop()` trả `int` | stack chứa số nguyên, đề: *"remove and return"* |
| `push()` trả `void` | không có gì cần trả; kết quả đọc lại qua `toString()` |
| `isEmpty()` trả `boolean` | câu hỏi có/không |
| `pop()` có `throws Exception` | lỗi "rỗng" đi lên tới `Main` — nơi duy nhất được in cùng view |

---

## 8. Thầy đổi yêu cầu tại chỗ

| Thầy bảo | Sửa file | Không đụng |
|---|---|---|
| Dùng **mảng** thay `ArrayList` | chỉ `MyStack` (`int[] stackValueArray` + `int top = -1`; push: `stackValueArray[++top] = value`; pop: `return stackValueArray[top--]`; thêm lỗi "Stack is full." vào `Message`) — tên mảng kết thúc `Array` (checklist 1.5) | repository, controller, view, main |
| In lại menu mỗi vòng | `Main`: dời `System.out.println(Message.MENU)` vào **trong** `while` | mọi file khác |
| Thêm menu **5. Size** | `Message.MENU` + 1 câu `SIZE`, `Constants` (`MENU_SIZE`, max 5), `StackRepository.getSize()` (trả `toResponse(String.format(Message.SIZE, myStack.countValues()))`), `StackController.displaySize()`, `case` ở `Main` | `MyStack` (đã có `countValues()`), `StackView`, 2 DTO |
| Stack chứa **chữ** | `MyStack` `ArrayList<String>`, `StackRequestDTO` `String value`, `Message` `%d`→`%s`, bỏ `getInt` ở `Main` (dùng chuỗi) | controller, view |
| Giới hạn giá trị 0..100 | `Validation.getInt` + 1 câu lỗi trong `Message` | các lớp còn lại |

---

## 9. Chỗ khác với đề / bản cũ

| Chỗ | Đề / bản cũ | Bài này | Lý do |
|---|---|---|---|
| Prompt nhập | bản cũ `Value to push: ` | `Enter value: ` | **đúng màn hình đề** |
| Dòng kết quả | bản cũ `Pushed 10`, `Top is 30`, `Stack (bottom -> top)` | `Pushed 10.   Stack (top -> bottom): [10]`, `Get (top) = 30           (not removed)` | đúng màn hình đề → test `REPLACE_REFERENCE = True` |
| Thoát | bản cũ `Bye` | `Goodbye!` | đúng đề |
| Menu | bản cũ in lại mỗi vòng | in **1 lần** lúc đầu | màn hình mẫu của đề chỉ có menu ở đầu |
| Display khi rỗng | bản cũ `Stack is empty.` | `Stack (top -> bottom): []` | đề không nói; `[]` khớp với dòng sau khi pop hết (`Popped -7. ... []`) |
| Câu lỗi (`You must input a number.`, `Value must be between 0 and 4.`, `Stack is empty.`) | đề không cho chữ | giữ chữ của bản cũ | đề chỉ nói *"a clear message"* |
| Kiến trúc | `entity/ui/utils`, Scanner trong `Validator` | MVC theo Guide, Scanner **chỉ ở `main`** | luật thầy |
| Setter `setStackValues` | đề không có | có (chép list) — nay tên `setStackValueList` | JavaBean — MVC JSP (QUY-TAC-THAY §9 V10) |
| **Tên thuộc tính đề đặt** | đề: `stackValues` | `stackValueList`, dòng ngay trên có `// brief: stackValues` | **đề đặt `stackValues`, tờ checklist 1.5 bắt biến kiểu collection kết thúc bằng `List` → hỏi thầy nếu thầy muốn giữ tên đề** (chỉ cần đổi lại tên field + getter/setter trong `MyStack`) |
| `size()` | bản mở rộng gợi ý `size()` | `countValues()` | checklist 1.4 — tên method mở đầu bằng động từ; đề chỉ gợi ý (*"such as"*) |
| View | bản trước: `setResponse(...)` + 4 hàm `displayPush/displayPop/displayTop/display` | field `responseDTO` + `setResponseDTO(...)` + **một** `display()` không tham số | checklist 1.1 — View nhận qua thuộc tính (ResponseDTO), render 1 lần/luồng |
| `StackResponseDTO` | bản trước: `value` + `stack`, view tự `String.format` | một field `message` — dòng cần in, repository soạn từ `myStack.toString()` | mỗi luồng in đúng 1 dòng; Guide: *"toString() để trả lại repository -> controller … truyền vào view"* |
| Hàm option 4 của controller | bản trước: `display()` | `displayStack()` | tránh trùng tên với `StackView.display()` khi đọc code |
| `Main` | bản trước: `public class Main`, `String line` khai trong vòng lặp, biến `dto` | `final` + `private Main()`, `String line = "";` ở đầu hàm, biến `requestDTO` | checklist 3.4, 2.6, 3.7 |
| `Validation.getChoice` | bản trước: `choice < min \|\| choice > max` | `(choice < min) \|\| (choice > max)` | checklist 3.3 — ngoặc riêng cho từng phép so sánh |

---

## 10. Tờ checklist 25 mục — bài này đạt thế nào

| Mục | Chỉ vào đâu |
|---|---|
| **1.1** MVC + repository | `repository/StackRepository.java` giữ `private MyStack myStack`; mỗi hàm của `StackController` gọi `stackView.setResponseDTO(...)` rồi `stackView.display()` **1 lần**; mỗi `case` của `Main` gọi controller **1 lần**; `StackView` không có hàm nào nhận tham số trừ setter |
| **1.5** tên biến | `stackValueList` (+ `// brief: stackValues`), `topFirstList`, `requestDTO`, `responseDTO`; không còn `ID` |
| **2.6 / 3.7** khai báo đầu block + khởi tạo | `Main.main` (`int choice = 0;` trước vòng lặp, trong vòng chỉ `choice = inputChoice(sc);`), `inputChoice`/`inputValue` (`String line = "";`) |
| **2.8** dòng trống | trước mọi comment (kể cả comment hằng trong `Message`, `Constants`), giữa các `case`, sau vùng khai báo biến, sau mỗi `}` |
| **3.3** ngoặc tường minh | `Validation.getChoice`: `if ((choice < min) \|\| (choice > max))` |
| **3.4** lớp chỉ có static | `Main`, `Validation`, `Message`, `Constants`: `final` + constructor `private` |
