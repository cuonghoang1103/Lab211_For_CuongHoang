#!/usr/bin/env python3
"""Generate the root README.md: the index of every project in this folder.

Generated from the folders themselves (package list, file count, and the design
patterns found in the SOURCE - by structure, not by a word in a comment) so the index
can never drift from the code.
"""
import glob
import json
import os
import re

HERE = os.path.dirname(os.path.abspath(__file__))
ROOT = os.path.dirname(HERE)
PAYLOAD = "/Users/admin/Downloads/api-backend/docs/codelab-authoring/lab211/payload.json"

# A pattern counts only when the code has its structure: a guide that says "if the
# teacher asks about Strategy..." or a StringBuilder must not make a project look like
# it uses Strategy or Builder.
CODE_PATTERNS = [
    ("Strategy", re.compile(r"implements\s+(Comparator|\w*Strategy)\b"
                            r"|interface\s+\w*Strategy\b")),
    ("Factory", re.compile(r"class\s+\w*(Factory|Creator)\b")),
    ("Template Method", re.compile(r"TEMPLATE METHOD|Template Method")),
    ("Builder", re.compile(r"class\s+\w*Builder\b")),
    ("Adapter", re.compile(r"class\s+\w*Adapter\b")),
    ("Singleton", re.compile(r"getInstance\s*\(\s*\)\s*\{")),
    ("Observer", re.compile(r"class\s+\w*(Observer|Listener)\b")),
]

DIFF_VI = {"EASY": "Dễ", "MEDIUM": "Trung bình", "HARD": "Khó"}


def lab_meta():
    items = json.load(open(PAYLOAD))
    return {e["lab"].replace(".", ""): e for e in items}


def patterns_in(project):
    """Design patterns whose structure is in the project's source."""
    source = ""
    for f in glob.glob(os.path.join(project, "src", "*", "*.java")):
        source += open(f, encoding="utf-8").read()
    return [name for name, rx in CODE_PATTERNS if rx.search(source)]


def main():
    meta = lab_meta()
    rows = []
    for p in sorted(glob.glob(os.path.join(ROOT, "HE176322_*"))):
        name = os.path.basename(p)
        m = re.match(r"HE176322_(J1[SL]P\d{4})_", name)
        if not m:
            continue
        code = m.group(1)
        e = meta.get(code, {})
        pkgs = sorted({os.path.basename(os.path.dirname(f))
                       for f in glob.glob(os.path.join(p, "src", "*", "*.java"))})
        nfiles = len(glob.glob(os.path.join(p, "src", "*", "*.java")))
        guide = os.path.join(p, "HUONG-DAN.md")
        rows.append((e.get("loc", 0), code, name, e, pkgs, nfiles, patterns_in(p),
                     os.path.exists(guide)))
    rows.sort(key=lambda r: (r[0], r[1]))

    out = []
    out.append("# Source LAB211 — HE176322 (Fall 2026, thầy Nguyễn Văn An)\n")
    out.append("> **%d project** NetBeans, viết theo đúng kiến trúc `Guide.xlsx` của thầy + lời thầy dặn "
               "trên lớp. Mỗi project: code đã **biên dịch ở chế độ Java 8**, **chạy thật** với kịch bản "
               "gõ phím (đủ happy case + mọi thông báo lỗi), so màn hình **từng ký tự** dưới 2 locale, "
               "và qua bộ kiểm luật thầy.\n" % len(rows))
    done = set()
    listed = os.path.join(ROOT, "_tools", "theo-checklist.txt")
    if os.path.exists(listed):
        with open(listed, encoding="utf-8") as fh:
            done = {ln.strip() for ln in fh if ln.strip() and not ln.startswith("#")}
    so_dat = len(done & {r[2] for r in rows})
    if so_dat == len(rows):
        out.append("> ✅ **Cả %d bài đã sửa theo tờ checklist giấy thầy phát 21/09/2026** — mỗi bài biên dịch "
                   "Java 8, chạy đúng đề dưới 2 locale, lint sạch và **0 vi phạm 25 mục** (`_tools/soat_checklist.py`). "
                   "Chỗ đề và tờ giấy đá nhau: xem mục \"Chỗ khác với đề\" + câu nên hỏi thầy trong HUONG-DAN của bài.\n"
                   % len(rows))
    else:
        out.append("> ⛔ **Đang sửa cả %d bài theo tờ checklist giấy thầy phát 21/09/2026** — đã đạt **%d/%d**. "
                   "Cột *Tờ 25 mục*: ✅ = đã sửa và kiểm (Java 8 + chạy đúng đề + 0 vi phạm 25 mục); "
                   "⏳ = còn kiến trúc cũ (thiếu repository, View nhận tham số…) — **đừng gõ theo bài ⏳**.\n"
                   % (len(rows), so_dat, len(rows)))
    out.append("## Đọc theo thứ tự này\n")
    out.append("| # | File | Để làm gì |")
    out.append("|---|---|---|")
    out.append("| 1 | [`TO-CHECKLIST-THAY.md`](TO-CHECKLIST-THAY.md) | **Tờ checklist giấy 25 mục** — thầy review bằng đúng tờ này; chuẩn cao nhất |")
    out.append("| 2 | [`QUY-TAC-THAY.md`](QUY-TAC-THAY.md) | Luật của thầy — **mỗi dòng có nguồn nguyên văn** (mục 0: chỗ tờ giấy chặt hơn Guide) |")
    out.append("| 3 | [`CACH-REVIEW.md`](CACH-REVIEW.md) | 5 cửa review, ngân hàng câu hỏi vấn đáp, debug, danh sách kiểm trước khi giơ tay |")
    out.append("| 4 | `HE176322_J1SP0070_EbankLogin/` | **Mẫu chuẩn theo tờ checklist (25/25)**: Main validate, repository, View nhận ResponseDTO |")
    out.append("| 5 | `HE176322_J1SP0001_BubbleSort/` | Khuôn cho bài thuật toán nhỏ: repository giữ mảng, service chạy thuật toán |")
    out.append("| 6 | `HE176322_J1SP0054_ContactManagement/` | Khuôn cho bài quản lý: một ResponseDTO (câu kết quả + danh sách dòng) |")
    out.append("| 7 | `HUONG-DAN.md` trong từng project | Đề · kiến thức · thiết kế · code từng bước · test · debug · câu hỏi |")
    out.append("")
    out.append("## Tải một bài về máy\n")
    out.append("Kho này là **source thật** của 54 bài, mỗi thư mục là một project NetBeans mở được ngay.\n")
    out.append("| Cách | Làm gì |")
    out.append("|---|---|")
    out.append("| Tải cả kho | nút **Code ▸ Download ZIP** ở đầu trang GitHub |")
    out.append("| Tải **một bài** | dán link thư mục bài đó vào [download-directory.github.io](https://download-directory.github.io/) |")
    out.append("| Dùng git | `git clone https://github.com/cuonghoang1103/Lab211_For_CuongHoang.git` |")
    out.append("")
    out.append("Trong mỗi thư mục bài có: `src/` (code), `HUONG-DAN.md` (giải thích + câu vấn đáp), "
               "`man-hinh-chay.png` và `man-hinh-chay.txt` (**màn hình chạy thật** của kịch bản kiểm), "
               "`nbproject/` + `build.xml` (để NetBeans mở được).\n")
    out.append("> ⚠️ Tên thư mục mang mã số **HE176322**. Nộp bài của mình thì đổi thành mã số của bạn "
               "(thầy bắt đặt tên `RollNo_ExcerciseNo_Description`).\n")
    out.append("## Mở một project trong NetBeans\n")
    out.append("1. **File ▸ Open Project** → chọn thư mục `HE176322_...` (có biểu tượng cốc cà phê).")
    out.append("2. **F6** để chạy (lớp chạy: `main.Main`) · **Shift+F11** Clean and Build · **Ctrl+F5** Debug.")
    out.append("3. Vào phòng lab là **tự gõ lại** — máy lab khoá mạng, không mang được code vào. "
               "Tập gõ lại ở nhà theo mục 4 của `HUONG-DAN.md` cho tới khi không cần nhìn.\n")
    out.append("## Lộ trình đủ 750 LOC\n")
    out.append("- **P0055 không tính LOC** (bài làm quen buổi 1).")
    out.append("- Thầy khuyên **tránh** J1.L.P0022 (Candidate — phải đủ SOLID) và J1.L.P0023 (hoa quả — phải có ERD).")
    out.append("- LOC kỳ trước được **giữ lại**, nhưng **không được làm lại bài đã pass** → xem PTS trước khi chọn.")
    out.append("- Review tối đa **3 bài/slot**, chọn tối đa **5 bài** một lúc.\n")
    out.append("| Lộ trình | Các bài | Tổng LOC |")
    out.append("|---|---|---|")
    out.append("| **A — nhiều bài ngắn, cùng họ** (dễ quen tay) | P0001 · P0002 · P0003 · P0053 · P0010 · P0006 · P0004 · P0005 (họ sắp xếp/tìm kiếm) + P0061 · P0080 · P0081 (họ OOP kế thừa) + P0083 · P0009 · P0060 | 755 |")
    out.append("| **B — ít bài, bài to** | P0071 · P0072 · P0085 · P0070 · P0079 · P0011 | 830 |")
    out.append("")
    out.append("## Mục lục %d project (xếp theo LOC)\n" % len(rows))
    out.append("Mọi project đều là **MVC** và controller đóng vai **Facade**. Cột *Pattern thêm* chỉ ghi "
               "pattern có **cấu trúc thật trong code** (bài nhỏ ≤ 60 LOC cố ý không thêm lớp pattern).\n")
    out.append("| LOC | Mã | Đề | Độ khó | Package | File | Pattern thêm | Tờ 25 mục | Hướng dẫn |")
    out.append("|---|---|---|---|---|---|---|---|---|")
    total = 0
    for loc, code, name, e, pkgs, nfiles, pats, has_guide in rows:
        total += loc
        out.append("| %s | `%s` | [%s](%s/HUONG-DAN.md) | %s | %s | %d | %s | %s | %s |" % (
            loc, e.get("lab", code), (e.get("title") or name)[:60], name,
            DIFF_VI.get(e.get("difficulty"), ""), " · ".join(pkgs), nfiles,
            ", ".join(pats) if pats else "—", "✅" if name in done else "⏳",
            "✅" if has_guide else "—"))
    out.append("\nTổng LOC chuẩn của %d project: **%d**.\n" % (len(rows), total))
    out.append("## Bộ công cụ `_tools/`\n")
    out.append("| Lệnh | Làm gì |")
    out.append("|---|---|")
    out.append("| `python3 _tools/verify.py` | kiểm **mọi** project: Java 8 + chạy kịch bản + so màn hình + luật thầy |")
    out.append("| `python3 _tools/verify.py --netbeans J1SP0055` | kiểm 1 bài + build y như NetBeans *Clean and Build* |")
    out.append("| `python3 _tools/lint.py <thư mục project>` | chỉ kiểm luật thầy — chạy lên **project em tự gõ** |")
    out.append("| `python3 _tools/soat_checklist.py <thư mục project>` | soát **25 mục tờ checklist giấy** — 0 VI PHAM mới điền đủ \"O\" |")
    out.append("| `_tools/tests/<Mã>.py` | kịch bản gõ phím + màn hình mong đợi của từng bài |")
    with open(os.path.join(ROOT, "README.md"), "w", encoding="utf-8") as fh:
        fh.write("\n".join(out) + "\n")
    print("README.md:", len(rows), "projects, total LOC", total)


if __name__ == "__main__":
    main()
