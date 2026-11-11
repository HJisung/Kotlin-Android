package com.androidbook.bookmarket

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MemoDatabase(context: Context) : SQLiteOpenHelper(context, "Memo.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE MemoTABLE (id INTEGER PRIMARY KEY AUTOINCREMENT, date TEXT, title TEXT, content TEXT)")
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS MemoTABLE")
        onCreate(db)
    }
    fun save(id: String?, date: String, title: String, content: String) {
        val values = ContentValues().apply {
            put("date", date)
            put("title", title)
            put("content", content)
        }
        if (id == null) writableDatabase.insert("MemoTABLE", null, values)
        else writableDatabase.update("MemoTABLE", values, "id = ?", arrayOf(id))
    }
    fun delete(id: String) { writableDatabase.delete("MemoTABLE", "id = ?", arrayOf(id)) }
    fun read(query: String): ArrayList<HashMap<String, String>> {
        val items = ArrayList<HashMap<String, String>>()
        readableDatabase.query("MemoTABLE", arrayOf("id", "date", "title", "content"),
            "content LIKE ?", arrayOf("%$query%"), null, null, "id DESC").use { cursor ->
            while (cursor.moveToNext()) {
                val row = HashMap<String, String>()
                listOf("id", "date", "title", "content").forEachIndexed { i, key -> row[key] = cursor.getString(i).orEmpty() }
                items.add(row)
            }
        }
        return items
    }
}
