package com.example.affem

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.io.File


class DBHelper(context: Context) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    companion object {
        private const val DB_NAME = "MainDb.db"
        private var DB_PATH = ""
        private const val DB_VERSION = 1
    }
    private var mDataBase: SQLiteDatabase? = null
    private var mNeedUpdate = false

    private fun checkDataBase(): Boolean {
        val dbFile = File(DB_PATH + DB_NAME)
        return dbFile.exists()
    }


    @Throws(SQLException::class)
    fun openDataBase(): Boolean {
        mDataBase =
            SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.CREATE_IF_NECESSARY)
        return mDataBase != null
    }

    @Synchronized
    override fun close() {
        if (mDataBase != null) mDataBase!!.close()
        super.close()
    }

    override fun onCreate(db: SQLiteDatabase) {

    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (newVersion > oldVersion) mNeedUpdate = true
    }


    @SuppressLint("Recycle")
    fun CheckUser(login: String, password: String): Boolean {
        val MyDB = this.writableDatabase
        val cursor = MyDB.rawQuery("Select * from users where login = ? and password = ?", arrayOf(login, password))
        return cursor.count > 0
    }

    @SuppressLint("Recycle")
    fun CheckRole1(login: String, password: String): Boolean{
        val MyDb = this.writableDatabase
//        val cursor = MyDb.rawQuery("Select *, 'admin' As role from users", arrayOf(role))
        val cursor: Cursor = MyDb.query(
            "users", arrayOf<String>("login", "password"),
            "role = ?", arrayOf<String>("admin"),
            null, null, null
        )
        return cursor.count > 0
    }
    fun CheckRole(): String?{
        val MyDb = this.writableDatabase
        var role: String? = null
        val query: Cursor = MyDb.rawQuery("SELECT * FROM users", null)
            role = query.getString(3)
        return role
    }
}