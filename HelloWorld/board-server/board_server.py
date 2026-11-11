"""Local development server for the BookMarket chapter 14 example."""
from datetime import datetime
from http.server import BaseHTTPRequestHandler, ThreadingHTTPServer
from pathlib import Path
from urllib.parse import parse_qs, urlparse
import json
import sqlite3

DATABASE = Path(__file__).with_name("board.db")


def initialize():
    with sqlite3.connect(DATABASE) as db:
        db.execute("CREATE TABLE IF NOT EXISTS board (no INTEGER PRIMARY KEY AUTOINCREMENT, id TEXT NOT NULL, title TEXT NOT NULL, content TEXT NOT NULL, date TEXT NOT NULL)")
        if db.execute("SELECT COUNT(*) FROM board").fetchone()[0] == 0:
            db.executemany("INSERT INTO board (id, title, content, date) VALUES (?, ?, ?, ?)", [
                ("bookuser", "배송 문의", "주문한 도서의 배송 일정을 알고 싶습니다.", "2026-11-11"),
                ("reader", "도서 문의", "도서 재입고 예정일을 알려 주세요.", "2026-11-11"),
                ("bookuser", "주문 확인", "주문 내역을 확인하고 싶습니다.", "2026-11-11"),
            ])


class BoardHandler(BaseHTTPRequestHandler):
    def respond(self, status, payload):
        body = json.dumps(payload, ensure_ascii=False).encode("utf-8")
        self.send_response(status)
        self.send_header("Content-Type", "application/json; charset=utf-8")
        self.send_header("Content-Length", str(len(body)))
        self.end_headers()
        self.wfile.write(body)

    def do_GET(self):
        if urlparse(self.path).path != "/selectBoard.php":
            return self.respond(404, {"error": "Not found"})
        with sqlite3.connect(DATABASE) as db:
            db.row_factory = sqlite3.Row
            rows = [dict(row) for row in db.execute("SELECT no, id, title, content, date FROM board ORDER BY no DESC")]
        self.respond(200, {"success": 1, "result": rows})

    def do_POST(self):
        if urlparse(self.path).path != "/insertBoard.php":
            return self.respond(404, {"error": "Not found"})
        length = int(self.headers.get("Content-Length", "0"))
        if length > 65536:
            return self.respond(413, {"success": 0})
        data = parse_qs(self.rfile.read(length).decode("utf-8"))
        values = [data.get(key, [""])[0].strip() for key in ("id", "title", "content")]
        if not all(values):
            return self.respond(400, {"success": 0, "error": "All fields are required"})
        with sqlite3.connect(DATABASE) as db:
            db.execute("INSERT INTO board (id, title, content, date) VALUES (?, ?, ?, ?)", values + [datetime.now().strftime("%Y-%m-%d %H:%M:%S")])
        self.respond(200, {"success": 1})


if __name__ == "__main__":
    initialize()
    ThreadingHTTPServer(("127.0.0.1", 8080), BoardHandler).serve_forever()
