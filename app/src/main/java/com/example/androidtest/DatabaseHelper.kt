package com.example.androidtest

import android.annotation.SuppressLint
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "steps.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "users"
        private const val COLUMN_ID = "id"
        private const val COLUMN_IME = "ime"
        private const val COLUMN_BROJ_KORAKA = "broj_koraka"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_IME TEXT,
                $COLUMN_BROJ_KORAKA INTEGER
            )
        """
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    // Dodavanje novog korisnika u bazu
    fun addUser(ime: String, brojKoraka: Int) {
        val db = writableDatabase

        // Prvo provjerimo postoji li već korisnik u bazi
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME WHERE $COLUMN_IME = ?", arrayOf(ime))

        if (cursor.count == 0) { // Ako korisnik ne postoji, dodajemo ga
            val insertQuery = "INSERT INTO $TABLE_NAME ($COLUMN_IME, $COLUMN_BROJ_KORAKA) VALUES ('$ime', $brojKoraka)"
            db.execSQL(insertQuery)
            Log.d("DBHelper", "Dodano ime: $ime, Koraci: $brojKoraka")
        } else {
            Log.d("DBHelper", "Korisnik $ime već postoji u bazi.")
        }

        cursor.close()
        db.close()
    }

    // Dohvat svih korisnika koji su napravili 10 koraka
    @SuppressLint("Range")
    fun getUsersWith10Steps(): List<String> {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT $COLUMN_IME FROM $TABLE_NAME WHERE $COLUMN_BROJ_KORAKA = 10", null)
        val users = mutableListOf<String>()

        if (cursor.moveToFirst()) {
            do {
                val ime = cursor.getString(cursor.getColumnIndex(COLUMN_IME))
                users.add(ime)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()

        return users
    }

    // Brisanje svih korisnika
    fun deleteAllUsers() {
        val db = writableDatabase
        db.execSQL("DELETE FROM $TABLE_NAME")
        db.close()
    }
}
