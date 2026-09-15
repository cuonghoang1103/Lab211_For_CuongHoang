# J1.S.P0008 — Letter and Character Count

> Bài xử lý chuỗi, vẫn **đủ MVC**. Đếm **từ** và **ký tự** ngay trong `CountService`, mỗi việc một hàm nhỏ:
> `countUnits` (đếm) và `splitCharacters` (tách ký tự).

| | |
|---|---|
| Loại / LOC | Short Assignment · 50 LOC · 1 slot |
| Project | `HE176322_J1SP0008_LetterCharacterCount` |
| Chạy | NetBeans: **File ▸ Open Project** → **F6** |
| Lớp chạy | `main.Main` |
| Kiểm tự động | `python3 _tools/verify.py J1SP0008` → 5 kịch bản × 2 locale |

---

## 1. Đề bài nói gì

- Nhập một chuỗi (**content**).
- In **số lần xuất hiện của từng từ** và **số lần xuất hiện của từng ký tự**.
- Guidelines gợi ý tách từ bằng **`StringTokenizer`** (gói `java.util`).

Màn hình đề (ảnh):

```
Enter your content:
hello world
{hello=1, world=1}
{w=1, d=1, e=1, r=1, o=2, l=3, h=1}
```

Chương trình này in (cùng số đếm, thứ tự **xuất hiện đầu tiên** — lý do ở mục 9):

```
Enter your content:
hello world
{hello=1, world=1}
{h=1, e=1, l=3, o=2, w=1, r=1, d=1}
```

**Đề bắt buộc:**

| Thứ | Đề viết | Bài này đặt ở |
|---|---|---|
| Tách từ bằng `StringTokenizer` | Guidelines | `model/Content.getWords` |
| Dòng 1: đếm từng **từ** | ảnh `{hello=1, world=1}` | `CountService.countUnits(words)` |
| Dòng 2: đếm từng **ký tự**, không đếm dấu cách | ảnh (không có ký tự trống) | `CountService.splitCharacters` + `countUnits` |
| Định dạng `{khoá=số, …}` | ảnh | `LinkedHashMap.toString()` |
| Prompt | `Enter your content:` (xuống dòng) | `Message.INPUT_CONTENT` |

---

## 2. Kiến thức cần biết

### 2.1 `StringTokenizer` — tách từ

```java
StringTokenizer tokenizer = new StringTokenizer(text, " \t\n\r\f");
while (tokenizer.hasMoreTokens()) {
    words.add(tokenizer.nextToken());
}
```

| Chuỗi | `StringTokenizer` | `text.split(" ")` |
|---|---|---|
| `hello world` | `hello`, `world` | `hello`, `world` |
| `a    b` (4 dấu cách) | `a`, `b` — **nhiều dấu cách = 1 chỗ ngắt** | `a`, `""`, `""`, `""`, `b` — ra chuỗi rỗng |
| `the cat⇥the hat` (có TAB) | `the`, `cat`, `the`, `hat` | `the`, `cat⇥the`, `hat` |

→ Đó là lý do đề gợi ý `StringTokenizer`.

### 2.2 Đếm bằng map — chạy tay `hello world`

Ký tự (sau khi bỏ dấu cách): `h e l l o w o r l d`

| Ký tự đọc | Có trong map chưa? | Map sau bước |
|---|---|---|
| h | chưa → 1 | `{h=1}` |
| e | chưa → 1 | `{h=1, e=1}` |
| l | chưa → 1 | `{h=1, e=1, l=1}` |
| l | **có** → 1+1 | `{h=1, e=1, l=2}` |
| o | chưa → 1 | `{…, l=2, o=1}` |
| w | chưa → 1 | `{…, o=1, w=1}` |
| o | **có** → 2 | `{…, o=2, w=1}` |
| r | chưa → 1 | `{…, w=1, r=1}` |
| l | **có** → 3 | `{h=1, e=1, l=3, o=2, w=1, r=1}` |
| d | chưa → 1 | `{h=1, e=1, l=3, o=2, w=1, r=1, d=1}` ✓ |

### 2.3 `HashMap` / `LinkedHashMap` / `TreeMap` — in ra theo thứ tự nào

| Lớp | Thứ tự khi in | `hello world`, dòng ký tự (JDK 8) |
|---|---|---|
| `HashMap` | theo **ô băm** — không đảm bảo, **đổi giữa các bản Java** | `{r=1, d=1, e=1, w=1, h=1, l=3, o=2}` |
| **`LinkedHashMap`** (bài này) | thứ tự **thêm vào lần đầu** | `{h=1, e=1, l=3, o=2, w=1, r=1, d=1}` |
| `TreeMap` | tăng dần theo khoá | `{d=1, e=1, h=1, l=3, o=2, r=1, w=1}` |

Cả ba đều là `Map` (cùng hợp đồng `put/get/containsKey`), chỉ khác cách cài bên trong.

### 2.4 Java dùng trong bài

| API | Dùng làm gì |
|---|---|
| `StringTokenizer` · `hasMoreTokens` · `nextToken` | tách từ |
| `LinkedHashMap<String, Integer>` · `containsKey` · `get` · `put` | đếm |
| `map.toString()` | ra đúng dạng `{hello=1, world=1}` của đề |
| `String.charAt(i)`, `String.valueOf(char)` | lấy từng ký tự, đổi thành chuỗi 1 chữ |

---

## 3. Thiết kế

```
HE176322_J1SP0008_LetterCharacterCount/src/
├── model/      Content                 chuỗi đã nhập (JavaBean) + getWords() bằng StringTokenizer
├── dto/        CountRequestDTO         content                         (main ──► controller)
│               CountResponseDTO        ArrayList<String> các dòng kết quả (controller ──► view)
├── service/    CountService            countContent · countUnits · splitCharacters
├── controller/ CountController         chọn: [đếm từ, đếm ký tự]; service ──► view
├── view/       CountView               in từng dòng kết quả
├── constants/  Message.java            prompt + thông báo lỗi
│               Constants.java          WORD_DELIMITERS, FIRST_COUNT = 1
├── utils/      Validation              getContent(chuỗi) → chuỗi không rỗng hoặc ném lỗi
└── main/       Main                    Scanner + gọi controller 1 lần
```

| Câu hỏi thiết kế | Trả lời |
|---|---|
| Sao `getWords()` nằm ở model? | "Chuỗi này gồm những từ nào" là **tính chất của chính nội dung** — cả hai lần đếm đều cần, viết một chỗ. Model được có hành vi của chính nó (Guide: *"thuộc tính và function của đối tượng"*). |
| Sao đếm ký tự lại dùng khoá `String`, không `Character`? | Để **cả hai** cách đếm trả cùng một kiểu `LinkedHashMap<String, Integer>` → dùng chung lớp cha. In ra vẫn y hệt: `{h=1, …}`. |
| Sao response là **chuỗi**, không phải map? | View chỉ in; không cần biết đếm bằng cấu trúc gì. |
| Sao không có `repository`? | Không lưu gì, không CRUD. Đếm là **tính toán nghiệp vụ** → `service`. |

**Luồng chạy:**

```
Main: đọc content (hỏi lại khi trống) ──► CountRequestDTO ──► controller.countContent(dto)
   controller ──► service.countContent(dto)
                     ├─ content = new Content(text)
                     ├─ countUnits(content.getWords())        ← đếm từ
                     └─ countUnits(splitCharacters(content))  ← đếm ký tự
                            response.addResult(map.toString())
   controller ──► view.setResponse(response) ──► view.display()
```

### 3.1 Design Pattern trong bài

| Pattern | Ở đâu |
|---|---|
| **MVC** — thầy gọi là "MVC JSP" | controller điều hướng (như Servlet) · view hiển thị (như trang JSP) · model là JavaBean |
| **Facade** | controller: `Main` chỉ gọi `controller.countContent(dto)`, không biết service/model/view phía sau |

> Bài chỉ 50 LOC nên **không thêm lớp pattern GoF** — ghi chú slide SOLID của thầy cảnh báo *"trừu tượng hoá sớm … vi phạm YAGNI"*. Hai lần đếm dùng chung **một hàm** `countUnits`; khác nhau chỉ ở cách tách (từ / ký tự).

**Thầy hỏi "dùng pattern gì cho dễ mở rộng?"** → trả lời đủ 4 yếu tố GoF (slide *"Elements of a Design Pattern"*):

| Yếu tố | Trả lời |
|---|---|
| **Name** | Strategy (nhóm Behavioral) |
| **Problem** | nhiều cách đếm (từ, ký tự, nguyên âm…) cùng một khung "tách → đếm" |
| **Solution** | tách `abstract class CountStrategy` (khung `count()` = **Template Method**) hoặc `interface`; mỗi cách làm là 1 lớp `implements` nó (`WordCountStrategy`, `CharacterCountStrategy`); `CountService` giữ danh sách strategy và chạy lần lượt |
| **Consequences** | ➕ thêm cách làm = thêm 1 lớp, service đứng yên (**O**) · ➖ thêm 2 file — chỉ đáng khi có từ 2 cách trở lên |

### 3.2 SOLID trong bài

| Nguyên lý | Ở đâu |
|---|---|
| **S** | `Content` giữ chuỗi + tách từ · `CountService` đếm · `CountView` in · `Validation` kiểm |
| **O** | đếm thêm một thứ = thêm 1 hàm tách + 1 dòng trong `countContent`; hàm `countUnits` đứng yên |
| **L** | bài chưa có lớp con riêng — chỉ `extends Object` |
| **I** | không có interface — bài chưa cần |
| **D** | `Main` chỉ phụ thuộc controller + DTO |

---

## 4. Code từng bước

> Thầy: *"code cái **model trước rồi đến data**"*.

| Bước | File | Việc |
|---|---|---|
| 1 | `model/Content.java` | `private String text` + constructor rỗng + constructor đủ + get/set + `getWords()` (StringTokenizer) + `toString` |
| 2 | `dto/CountRequestDTO.java`, `CountResponseDTO.java` | JavaBean; response có `addResult` |
| 3 | `service/CountService.java` | `countContent(dto)`; `countUnits` (đếm, `private`); `splitCharacters` (tách ký tự, `private`) |
| 4 | `view/CountView.java` | `setResponse` · `display` |
| 5 | `controller/CountController.java` | `new CountService()`; `countContent(dto)` |
| 6 | `constants/Message.java`, `Constants.java` | prompt, lỗi; delimiters, `FIRST_COUNT` |
| 7 | `utils/Validation.java` | `getContent(String)` |
| 8 | `main/Main.java` | `inputContent` (vòng hỏi lại) + gọi controller **1 lần** |

**Bẫy hay gặp:**

1. Dùng `split(" ")` → nhiều dấu cách sinh **từ rỗng** `""=3`.
2. Đếm ký tự trên **cả chuỗi** mà quên bỏ dấu cách → xuất hiện ` =1` trong dòng 2.
3. `counts.put(unit, counts.get(unit) + 1)` khi **chưa có** khoá → `get` trả `null` → `NullPointerException`. Phải `containsKey` trước.
4. Dùng `HashMap` rồi ngạc nhiên thứ tự in "lộn xộn" và khác ảnh — xem 2.3.

---

## 5. Test trước khi gọi thầy

| # | Gõ | Phải thấy |
|---|---|---|
| 1 | *(Enter trống)* | `Input must not be empty.` rồi hỏi lại `Enter your content:` |
| 2 | `   ` (toàn dấu cách) | `Input must not be empty.` |
| 3 | `hello world` | `{hello=1, world=1}` · `{h=1, e=1, l=3, o=2, w=1, r=1, d=1}` |
| 4 | `a    b` | `{a=1, b=1}` · `{a=1, b=1}` (không có từ rỗng) |
| 5 | `Hello hello, world!` | `{Hello=1, hello,=1, world!=1}` · `{H=1, e=2, l=5, o=3, h=1, ,=1, w=1, r=1, d=1, !=1}` |
| 6 | `the cat` TAB `the hat` | `{the=2, cat=1, hat=1}` · `{t=4, h=3, e=2, c=1, a=2}` |

---

## 6. Debug

| Việc | Cách làm |
|---|---|
| Breakpoint | dòng `if (counts.containsKey(unit))` trong `CountService.countUnits` |
| Chạy | **Ctrl+F5**, nhập `hello world` |
| Quan sát | **Variables**: `unit`, mở `counts` xem map lớn dần như bảng 2.2 |
| Bước | **F5** mỗi vòng: lần gọi `countUnits` thứ nhất đếm từ (`hello`, `world`), lần thứ hai đếm ký tự (`h`, `e`, …) |

---

## 7. Câu hỏi thầy hay hỏi

### Thuật toán / Java

| Câu hỏi | Trả lời mẫu |
|---|---|
| Sao dùng `StringTokenizer`? | Đề gợi ý; và nó coi **nhiều khoảng trắng liền nhau là một** chỗ ngắt (bảng 2.1). |
| Sao `LinkedHashMap` mà không `HashMap`? | `HashMap` in theo ô băm — **không đảm bảo thứ tự**, đổi giữa bản Java (ảnh đề chụp từ JDK 7, JDK 8 in khác). `LinkedHashMap` **kế thừa `HashMap`** + nhớ thứ tự thêm → kết quả luôn giống nhau, dòng 1 khớp ảnh. |
| `Map` với `LinkedHashMap` khác gì? Sao không khai `Map`? | `Map` là **interface** (hợp đồng), `LinkedHashMap` là **lớp cài**. Em khai đúng kiểu cụ thể vì code **phụ thuộc thật** vào thứ tự mà chỉ `LinkedHashMap` đảm bảo. |
| `ArrayList` với `List`? | `List` interface; `ArrayList` cài bằng mảng động — thêm cuối, duyệt theo thứ tự nhanh. |
| Độ phức tạp? | Duyệt mỗi ký tự một lần, mỗi thao tác map ~`O(1)` → **O(n)**. |
| Hoa/thường có tính là một không? | Không — `Hello` và `hello` là 2 từ (đề không nói gộp). Muốn gộp: mục 8. |

### OOP / access modifier / static

| Câu hỏi | Trả lời mẫu |
|---|---|
| 4 tính chất OOP ở đâu? | **Đóng gói**: `text` `private` trong `Content`, chỉ lộ `getWords()`. **Kế thừa**: mọi lớp ngầm `extends Object`; các lớp ghi đè `toString()`. **Đa hình**: `toString()` có `@Override`; `countUnits(...).toString()` chạy bản của `LinkedHashMap`, ra `{hello=1, world=1}`. **Trừu tượng**: `Main` chỉ gọi `controller.countContent(dto)`. |
| `countUnits`, `splitCharacters` sao `private`? | Chỉ `countContent` trong **cùng lớp** gọi (thầy V2: chỉ `public` khi lớp khác gọi). `countContent` `public` vì `CountController` (lớp khác) gọi. |
| Abstract class khác interface? | Interface chỉ là **hợp đồng**; abstract class chứa được **code chung** + field. Bài này chưa cần cả hai. Nếu tách pattern (mục 3.1) thì chọn abstract class, vì hai cách đếm chung nguyên phần "tăng số" (Template Method). |
| `getWords` trả `ArrayList<String>` vì sao? | Nơi gọi cần **các từ theo thứ tự** để duyệt. |
| `Validation.getContent` sao static? Bỏ đi thì sao? | Không dùng dữ liệu đối tượng. Bỏ `static` → gọi `Validation.getContent(...)` lỗi biên dịch; phải bỏ `private` constructor, `new Validation()` trong `Main`. |
| `inputContent` sao `private static`? | `main` static chỉ gọi thẳng được hàm static; `private` vì chỉ `Main` dùng. Guide: static được cho **hàm** ở main. |
| Sao `Content` có constructor rỗng? | **MVC kiểu JSP**: model là JavaBean — constructor rỗng `public` + get/set. |

---

## 8. Thầy đổi yêu cầu tại chỗ

| Thầy bảo | Sửa | Không đụng |
|---|---|---|
| Đếm thêm **nguyên âm** / **chữ số** | 1 hàm tách mới (như `splitCharacters`) + 1 dòng `response.addResult(...)` trong `countContent` | view, main, model |
| **Không phân biệt** hoa thường | `Content.getWords`: `tokenizer.nextToken().toLowerCase()` | mọi file khác |
| Chỉ đếm **chữ cái** (bỏ số, dấu câu) | `CountService.splitCharacters`: chỉ thêm khi `Character.isLetter(ch)` | mọi file khác |
| In **sắp theo khoá** | `CountService.countUnits`: `TreeMap` thay `LinkedHashMap` (đổi kiểu trả về) | view, main |
| Coi `,` `.` là dấu ngắt từ | `Constants.WORD_DELIMITERS` thêm `",."` | mọi file khác |
| In thêm **tổng số từ** | `CountService` thêm dòng `words.size()` vào response | view (vẫn in từng dòng) |

---

## 9. Chỗ khác với đề / lời giải cũ

| Chỗ | Đề / bản cũ | Bài này | Lý do |
|---|---|---|---|
| **Thứ tự dòng ký tự** | Ảnh: `{w=1, d=1, e=1, r=1, o=2, l=3, h=1}` | `{h=1, e=1, l=3, o=2, w=1, r=1, d=1}` — **cùng số đếm** | Thứ tự ảnh là thứ tự ô băm của `HashMap` **JDK 7** (đã tính: w,d,e,r,o,l,h rơi vào ô 0,2,3,5,9,10,14). Máy lab JDK 8 in `HashMap` là `{r=1, d=1, e=1, w=1, h=1, l=3, o=2}` và dòng từ là `{world=1, hello=1}` — **không JDK 8 nào tái tạo được ảnh**. `LinkedHashMap` cho thứ tự ổn định, và dòng 1 khớp ảnh đúng từng chữ. |
| Chuỗi trống | Đề không nói | `Input must not be empty.` rồi hỏi lại | câu của lời giải cũ; thầy đòi "đủ message validation" |
| Nội dung in | Bản cũ in 4 **tổng** (`Characters (with spaces): 16`, `Letters: 10`, `Words: 3`…) | map đếm **từng từ** và **từng ký tự** | đúng ảnh đề |
| Prompt | Bản cũ: tiêu đề + `Please input a string: ` | `Enter your content:` (xuống dòng) | đúng ảnh đề |
| Dấu câu | Đề không nói | thuộc về từ (`hello,`) và được đếm như ký tự | `StringTokenizer` mặc định chỉ ngắt ở khoảng trắng |
| Kiến trúc | `bo/ui/utils`, Scanner trong `Validator` | MVC theo Guide | luật thầy |
