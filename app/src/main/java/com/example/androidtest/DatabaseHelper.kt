package com.example.androidtest

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class StepDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "steps.db"
        private const val DATABASE_VERSION = 1
        const val TABLE_NAME = "steps"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_STEPS = "step_count"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT,
                $COLUMN_STEPS INTEGER
            )
        """.trimIndent()
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertUser(name: String, steps: Int) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, name)
            put(COLUMN_STEPS, steps)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getUsersWith10Steps(): List<String> {
        val users = mutableListOf<String>()
        val db = readableDatabase
        val cursor = db.query(
            TABLE_NAME, arrayOf(COLUMN_NAME), "$COLUMN_STEPS = ?", arrayOf("10"),
            null, null, null
        )
        while (cursor.moveToNext()) {
            users.add(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)))
        }
        cursor.close()
        db.close()
        return users
    }

    fun clearUsers() {
        val db = writableDatabase
        db.delete(TABLE_NAME, null, null)
        db.close()
    }
}
