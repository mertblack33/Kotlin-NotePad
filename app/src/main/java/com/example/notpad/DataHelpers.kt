package com.example.notpad

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

val data_name = "datatable"
val table_name = "notapp"
val col_title = "titlename"
val col_text = "textname"
val id = "id"

class DataHelpers(var context: Context) : SQLiteOpenHelper(context, data_name, null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        var createTable = "CREATE TABLE $table_name (" +
                "$id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$col_title VARCHAR," +
                "$col_text VARCHAR)"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    fun insertdata(adapter: Dataadapter) {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(col_title, adapter.title)
        cv.put(col_text, adapter.text)
        var ex = db.insert(table_name, null, cv)
        if (ex == (-1).toLong()) {
            Toast.makeText(context, "Hata", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(context, "Başarılı", Toast.LENGTH_LONG).show()
        }
        db.close() // Veritabanı bağlantısını kapat
    }

    fun readData(): Pair<MutableList<String>, MutableList<String>> {
        var titlelists: MutableList<String> = ArrayList()
        val textlists: MutableList<String> = ArrayList()
        val db = this.readableDatabase
        val query = "SELECT * FROM $table_name"
        val answer = db.rawQuery(query, null)
        if (answer.moveToFirst()) {
            do {
                val titleindex = answer.getColumnIndex(col_title)
                val textindex = answer.getColumnIndex(col_text)
                if (titleindex != -1) {
                    val title = answer.getString(titleindex)
                    titlelists.add(title)
                }
                if (textindex != -1) {
                    val text = answer.getString(textindex)
                    textlists.add(text)
                }
            } while (answer.moveToNext()) // Döngüdeki hatayı düzeltmek için moveToNext() kullanılmalı
        }
        answer.close()
        db.close()
        return Pair(titlelists, textlists)
    }

    fun deleteDataByName(name: String): Boolean {
        val db = this.writableDatabase
        val result = db.delete(table_name, "$col_title=?", arrayOf(name))
        db.close()
        return result != -1
    }

    fun updateData(oldName: String, newName: String, newText: String): Boolean {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(col_title, newName)
        cv.put(col_text, newText)
        val result = db.update(table_name, cv, "$col_title=?", arrayOf(oldName))
        db.close()
        return result != -1
    }
}
