# J1.S.P0069 — Write & Read File

> Bài đọc/ghi file **vẫn phải làm MVC** (thầy: *"không có cấu trúc → không review"*). Hàm đọc/ghi
> file nằm ở `utils/FileUtils` — đúng chữ Guide: utils *"chứa các functions dùng chung như validate,
> **đọc/ghi file**… phải dùng static method"*.

| | |
|---|---|
| Loại / LOC | Short Assignment · 33 LOC · 1 slot |
| Project | `HE176322_J1SP0069_WriteReadFile` |
| Chạy | NetBeans: **File ▸ Open Project** → **F6** |
| Lớp chạy | `main.Main` |
| Kiểm tự động | `python3 _tools/verify.py J1SP0069` → 7 kịch bản × 2 locale |

---

## 1. Đề bài nói gì

- Hỏi *có muốn ghi file không* (Y/N hoặc y/n). **Có** → nhập đường dẫn, nhập nội dung nhiều dòng,
  gõ `save` hoặc `SAVE` để kết thúc → ghi ra file. **Không** → sang bước đọc.
- Hỏi *có muốn đọc file không*. **Có** → nhập đường dẫn → in nội dung + `Read file successfully.`
  **Không** → thoát.

Màn hình đề (chép đúng chữ):

```
============ Writer Program ===============
Do you want to write file? (Y/N or y/n):Y
Please enter file path: test.txt
Save file with content <save> or <SAVE>
Please enter file content:
Content file
save
Do you want to read file? (Y/N or y/n):Y
Please enter file path: test.txt
Content file
Read file successfully.
```

**Đề bắt buộc:**

| Thứ | Đề viết | Bài này đặt ở |
|---|---|---|
| `public boolean writeFile(String path, String content)` | "status of file writing operation" | `utils/FileUtils.writeFile` — **static** |
| `public String readFile(String path)` | "content of file" | `utils/FileUtils.readFile` — **static** |
| Ghi dùng `File`, `FileWriter`, `BufferedWriter` | Hint | `FileUtils.writeFile` |
| Đọc dùng `File`, `FileReader` | Hint | `FileUtils.readFile` |

---

## 2. Kiến thức cần biết

### 2.1 Ghi file — 3 lớp đề chỉ định

```java
File file = new File(path);                                   // "địa chỉ" của file
try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
    writer.write(content);                                    // ghi vào bộ đệm
    return true;                                              // đóng writer = đẩy bộ đệm xuống đĩa
} catch (IOException e) {
    return false;                                             // thư mục không có, file chỉ đọc…
}
```

| Lớp | Việc |
|---|---|
| `File` | chỉ là **đường dẫn** — tạo `File` chưa đụng tới đĩa |
| `FileWriter` | ghi **từng ký tự** xuống file (mở file = xoá nội dung cũ) |
| `BufferedWriter` | **bọc** `FileWriter`, gom chữ vào bộ đệm rồi ghi một lần — nhanh hơn |

**try-with-resources** (`try (…) {}`) tự `close()` kể cả khi lỗi. Quên `close()` thì bộ đệm chưa
xuống đĩa → file **rỗng**.

### 2.2 Đọc file — `FileReader.read()` từng ký tự

```java
int character = reader.read();          // trả mã ký tự, hoặc -1 khi hết file
while (character != Constants.END_OF_FILE) {
    content.append((char) character);
    character = reader.read();
}
```

Chạy tay với file chứa `Hi` + xuống dòng + `Yo`:

| Lần `read()` | Trả về | `content` |
|---|---|---|
| 1 | `'H'` | `H` |
| 2 | `'i'` | `Hi` |
| 3 | `'\n'` | `Hi⏎` |
| 4 | `'Y'` | `Hi⏎Y` |
| 5 | `'o'` | `Hi⏎Yo` |
| 6 | `-1` | **dừng** |

Đọc từng ký tự nên file ra **đúng y** như lúc ghi (cả dấu xuống dòng).

### 2.3 Nhập nội dung nhiều dòng tới chữ `save`

`Main.inputContent`: đọc từng dòng; dòng nào **không** phải `save`/`SAVE` thì nối vào `StringBuilder`,
xuống dòng (`System.lineSeparator()`) chỉ đặt **giữa** hai dòng (cờ `firstLine`).

| API | Dùng làm gì |
|---|---|
| `File.isFile()` | đường dẫn là **file có thật** (không phải thư mục, không phải chưa có) |
| `StringBuilder.append` | nối chuỗi trong vòng lặp không tạo chuỗi mới mỗi lần |
| `equalsIgnoreCase` | `Y`/`y`, `N`/`n` |

---

## 3. Thiết kế

```
HE176322_J1SP0069_WriteReadFile/src/
├── model/       TextFile              1 file: path + content (JavaBean)
├── dto/         FileRequestDTO        path, content     (main ──► controller)
│                FileResponseDTO       content đọc được  (controller ──► view)
├── repository/  TextFileRepository    saveFile / loadFile — lớp DUY NHẤT biết dữ liệu nằm trong file
├── controller/  FileController        writeFile / readFile — điều hướng
├── view/        FileView              in nội dung + "Read file successfully."
├── constants/   Message, Constants    câu chữ; Y, N, save, SAVE, -1
├── utils/       FileUtils             writeFile / readFile (đề bắt) — static
│                Validation            getYesNo, getNonBlank, isSaveCommand — static
└── main/        Main                  Scanner, 2 câu hỏi, gọi controller
```

| Lớp | Vì sao ở đó |
|---|---|
| `FileUtils` | Guide: đọc/ghi file là hàm dùng chung ở `utils`, static |
| `TextFileRepository` | "kho dữ liệu" của bài chính là **file**: ghi = Create, đọc = Read. Controller không được gọi utils đọc/ghi thẳng mà đi qua repository (Controller ↔ Repository ↔ Model) |
| `TextFile` | model mô tả đối tượng của bài: một file có đường dẫn + nội dung |
| Không có `service/` | bài không có tính toán nghiệp vụ nào ngoài đọc/ghi |

**Luồng đọc file:**

```
Main: inputPath ──► FileRequestDTO ──► controller.readFile(dto)
   controller ──► repository.loadFile(dto)
                    ├─ FileUtils.readFile(path)   → chuỗi (hoặc ném "File does not exist.")
                    ├─ new TextFile(path, content)
                    └─ return new FileResponseDTO(content)
   controller ──► view.setResponse(r) ──► view.display()
Main: catch → in e.getMessage()
```

### 3.1 Design Pattern trong bài

| Pattern | Name · Problem · Solution · Consequences |
|---|---|
| **MVC** (thầy: "MVC JSP") | **Problem**: nhập, đọc/ghi, in trộn trong `main`. **Solution**: `FileController` ~ Servlet, `FileView` ~ JSP, `TextFile` ~ JavaBean; dữ liệu đi bằng DTO. **Consequences**: đổi cách in chỉ sửa `FileView`; đổi nhiều file hơn viết một cục. |
| **Facade** | **Problem**: `Main` phải biết repository, view, thứ tự gọi. **Solution**: `FileController.writeFile/readFile` là **một cửa**. **Consequences**: `Main` gọn; controller phải chỉ điều hướng. |
| **Decorator** (GoF, **dùng** trong Java IO) | **Problem**: muốn thêm bộ đệm cho việc ghi mà không sửa `FileWriter`. **Solution**: `BufferedWriter` (**Decorator**) bọc `FileWriter` (**ConcreteComponent**), cả hai cùng là `Writer` (**Component**) — `new BufferedWriter(new FileWriter(file))`. **Consequences**: ghép tính năng bằng cách bọc, không kế thừa; nhưng phải nhớ đóng lớp ngoài cùng. |
| **Repository** (mẫu kiến trúc, không thuộc 23 GoF) | **Problem**: nhiều nơi cùng đụng file. **Solution**: chỉ `TextFileRepository` gọi `FileUtils`. **Consequences**: đổi sang lưu CSDL chỉ sửa 1 lớp. |

> Không có Strategy: bài không có thuật toán nào có bản thay thế. Nhét Strategy vào là "trừu tượng
> hoá sớm" mà ghi chú slide 26 SOLID cảnh báo.

Thầy bảo *"thêm chức năng ghi nối vào cuối file"* → xem mục 8.

---

## 4. Code từng bước

> Thầy: *"code cái **model trước rồi đến data**"*.

| Bước | File | Việc |
|---|---|---|
| 1 | `model/TextFile.java` | 2 field `private` + constructor rỗng + constructor đủ + get/set + `toString` |
| 2 | `dto/FileRequestDTO.java`, `FileResponseDTO.java` | JavaBean: constructor rỗng + get/set |
| 3 | `utils/FileUtils.java` | `writeFile` (File + FileWriter + BufferedWriter), `readFile` (File + FileReader) |
| 4 | `repository/TextFileRepository.java` | `saveFile`, `loadFile` |
| 5 | `constants/Message.java`, `Constants.java` | câu chữ đề; `Y`, `N`, `save`, `SAVE`, `-1` |
| 6 | `view/FileView.java` | `setResponse`, `display` |
| 7 | `controller/FileController.java` | `writeFile` (false → throw), `readFile` |
| 8 | `utils/Validation.java` | `getYesNo`, `getNonBlank`, `isSaveCommand` |
| 9 | `main/Main.java` | `inputYesNo`, `inputPath`, `inputContent`, `writeFile`, `readFile` |

**Bẫy hay gặp:**

1. Không đóng writer → file rỗng. Dùng **try-with-resources**.
2. Nối `"\n"` **trước mỗi** dòng → file có dòng trống ở đầu. Chỉ nối **giữa** hai dòng.
3. `new FileReader("khong_co.txt")` ném `FileNotFoundException` — kiểm `file.isFile()` trước để báo
   đúng câu `File does not exist.`
4. Hai câu hỏi Y/N **độc lập**: trả N câu đầu vẫn phải hỏi câu đọc.

---

## 5. Test trước khi gọi thầy

| # | Gõ | Phải thấy |
|---|---|---|
| 1 | `Y` / `test.txt` / `Content file` / `save` / `Y` / `test.txt` | `Content file` rồi `Read file successfully.` |
| 2 | `x` ở câu hỏi ghi | `Please answer Y or N.` rồi hỏi lại |
| 3 | đường dẫn để trống / toàn dấu cách | `Path must not be empty.` rồi hỏi lại |
| 4 | nội dung nhiều dòng, có dòng trống, có dòng `Save is a word`, kết thúc `SAVE` | đọc lại ra **đủ** các dòng, `Save is a word` là nội dung |
| 5 | `maybe` ở câu hỏi đọc | `Please answer Y or N.` |
| 6 | ghi vào `no_such_folder/out.txt` | `Could not write the file.` rồi vẫn hỏi đọc |
| 7 | `N` / `Y` / `missing.txt` | `File does not exist.` |
| 8 | `N` / `N` | chỉ 2 câu hỏi rồi thoát |
| 9 | gõ `save` ngay | file rỗng; đọc lại in dòng trống + `Read file successfully.` |
| 10 | đọc `test` (một **thư mục**) | `File does not exist.` |

---

## 6. Debug

| Việc | Cách làm |
|---|---|
| Breakpoint | dòng `content.append((char) character);` trong `FileUtils.readFile` |
| Chạy | **Ctrl+F5**, ghi file `a.txt` nội dung `Hi`, rồi đọc `a.txt` |
| Quan sát | tab **Variables**: `character` (mã số: `72` = `H`), `content` lớn dần |
| Bước | **F8** từng vòng; tới `-1` thấy thoát `while`. Ở `TextFileRepository.loadFile` bấm **F7** vào `FileUtils.readFile` |
| Lỗi ghi | breakpoint `return false;` trong `writeFile`, ghi vào `no_such_folder/x.txt` → thấy nhảy vào `catch` |

---

## 7. Câu hỏi thầy hay hỏi

### OOP / access modifier / static / kiểu trả về

| Câu hỏi | Trả lời mẫu |
|---|---|
| 4 tính chất OOP ở đâu? | **Đóng gói**: `path`, `content` `private` trong `TextFile` + get/set. **Kế thừa**: `BufferedWriter`, `FileWriter` đều `extends Writer`; mọi lớp `extends Object`. **Đa hình**: `TextFile.toString()` có `@Override`; `BufferedWriter` nhận **bất kỳ** `Writer` nào (biến kiểu cha). **Trừu tượng**: `Main` gọi `controller.readFile(dto)` mà không biết dữ liệu nằm trong file. |
| Sao `writeFile`/`readFile` là `static`? | Không dùng dữ liệu riêng của đối tượng: cùng đường dẫn → cùng file. Guide: utils *"phải dùng static method"*. |
| **Bỏ `static` thì sao?** | `FileUtils.writeFile(...)` báo lỗi biên dịch. Phải bỏ `private` constructor, trong `TextFileRepository` tạo `FileUtils fileUtils = new FileUtils();` rồi gọi `fileUtils.writeFile(...)`. |
| Static ở `Main`? | Chỉ **hàm** (`inputYesNo`, `inputPath`…) vì `main` là static; Scanner là **biến cục bộ** — Guide cấm biến static. |
| `writeFile` sao trả `boolean`? | Đề: *"Return: status of file writing operation"* — chỉ cần được/không. |
| `readFile` sao trả `String` mà không `void`? | Hàm đọc **không in** (utils không được in); nó trả chuỗi để view in. |
| Hàm nào `public`, vì sao? | Hàm của controller/repository/view/utils được **lớp khác gọi** → `public`. Hàm nhập trong `Main` chỉ `main()` gọi → `private`. Mọi field `private`. Constructor `Message`/`Constants`/`Validation`/`FileUtils` `private` để không ai `new`. |
| Sao `readFile` ném Exception còn `writeFile` trả false? | `writeFile` theo **chữ ký đề** (trả status). `readFile` đề bắt trả nội dung — không còn chỗ báo lỗi nên dùng Exception mang câu của `Message`. |
| **Tại sao `ArrayList` mà không `List`? Khác nhau thế nào?** | Bài này **không dùng** collection nào (nội dung là `String`). Nhưng quy tắc chung của em: `List` là **interface** (hợp đồng `add/get/size`), `ArrayList` là **lớp cài đặt** bằng mảng động. Em khai báo đúng kiểu cụ thể `ArrayList<X> x = new ArrayList<>()` (thầy dặn), chỉ giữ `List` khi **đề bắt chữ ký**. |

### SOLID

| Nguyên lý | Ở đâu |
|---|---|
| **S** | `FileUtils` chỉ chuyển chữ ↔ file · `TextFileRepository` chỉ lưu/lấy · `FileView` chỉ in · `Validation` chỉ kiểm |
| **O** | thêm "ghi nối cuối file" = thêm hàm mới ở `FileUtils` + repository, không sửa hàm cũ |
| **D** (một phần) | `Main` chỉ biết controller + DTO; không biết file được đọc bằng lớp nào |

---

## 8. Thầy đổi yêu cầu tại chỗ

| Thầy bảo | Sửa file | Không đụng |
|---|---|---|
| Ghi **nối** vào cuối file | `FileUtils.writeFile`: `new FileWriter(file, true)` | mọi file khác |
| Báo "Write file successfully." sau khi ghi | `Message` + `FileController.writeFile` gọi `fileView.showMessage(...)` (thêm hàm vào view) | `Main`, `FileUtils` |
| Đọc từng **dòng** (BufferedReader) | chỉ `FileUtils.readFile` | repository, controller |
| Nhận `Save`, `sAvE`… cũng là lệnh dừng | `Validation.isSaveCommand`: `equalsIgnoreCase` | mọi file khác |
| In số ký tự đã đọc | `FileResponseDTO` thêm field, repository gán, `FileView` in thêm dòng | `FileUtils`, `Main` |

---

## 9. Chỗ khác với đề / bản cũ

| Chỗ | Đề / bản cũ | Bài này | Lý do |
|---|---|---|---|
| `writeFile`, `readFile` | đề: `public` (không static), không nói lớp | `public static` trong `utils/FileUtils` | Guide: đọc/ghi file ở utils, **phải static** |
| Kiến trúc | bản cũ: `bo/FileManager`, Scanner trong `Validator` | MVC theo Guide; Scanner chỉ ở `main` | luật thầy |
| `writeFile` | bản cũ: `void` + ném IOException | trả `boolean` | **đúng chữ ký đề** ("status") |
| `readFile` khi thiếu file | bản cũ: trả `null` | ném Exception `File does not exist.` | theo khung Guide (lỗi đi bằng Exception mang câu của `Message`) — câu in ra **giữ nguyên** |
| Đọc | bản cũ: `BufferedReader.readLine()` | `FileReader.read()` từng ký tự | đề chỉ định `FileReader`; giữ nguyên dấu xuống dòng |
| Lỗi ghi | bản cũ: `Could not write the file: <lỗi hệ điều hành>` | `Could not write the file.` | `writeFile` trả `boolean` nên không mang chi tiết; câu hệ điều hành khác nhau giữa Windows/macOS |
| Lệnh dừng | bản cũ: mọi kiểu hoa/thường (`Save` cũng dừng) | **đúng** `save` hoặc `SAVE` | màn hình đề: *"<save> or <SAVE>"* |
| Câu `Please answer Y or N.`, `Path must not be empty.`, `File does not exist.` | đề không ghi | giữ đúng như bản cũ | đề im lặng → giữ màn hình bản cũ đã kiểm |
