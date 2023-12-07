package com.example.affem

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import kotlin.properties.Delegates


class DBHelper(context: Context) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    companion object {
        private const val DB_NAME = "MainDb.db"
        private var DB_PATH = ""
        private const val DB_VERSION = 10
        const val TABLE_NAME: String = "users"
        const val COLUMN_ID: String = "_id"
        const val COLUMN_LOGIN: String = "login"
        const val COLUMN_PASSWORD: String = "password"
        const val COLUMN_ROLE: String = "role"
        const val TABLE_NAME1: String = "equipment"
        const val COLUMN_TITLE: String = "title"
        private const val CREATE_TABLE_NEW =
            "CREATE TABLE $TABLE_NAME1 (" +
                    "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "$COLUMN_TITLE TEXT NOT NULL);"
    }

    private var mDataBase: SQLiteDatabase? = null
    private var mContext: Context? = null
    private var mNeedUpdate = false

    private fun copyDataBase() {
        if (!checkDataBase()) {
            this.readableDatabase
            close()
            try {
                copyDBFile()
            } catch (mIOException: IOException) {
                throw Error("ErrorCopyingDataBase")
            }
        }
    }
    private fun checkDataBase(): Boolean {
        val dbFile = File(DB_PATH + DB_NAME)
        return dbFile.exists()
    }

//    @Throws(IOException::class)
//    fun updateDataBase() {
//        if (mNeedUpdate) {
//            val dbFile = File(DB_PATH + DB_NAME)
//            if (dbFile.exists()) dbFile.delete()
//            copyDataBase()
//            mNeedUpdate = false
//        }
//    }
    @Throws(IOException::class)
    private fun copyDBFile() {
        val mInput = mContext!!.assets.open(DB_NAME)
        val mOutput: OutputStream = FileOutputStream(DB_PATH + DB_NAME)
        val mBuffer = ByteArray(1024)
        var mLength: Int
        while (mInput.read(mBuffer).also { mLength = it } > 0) mOutput.write(mBuffer, 0, mLength)
        mOutput.flush()
        mOutput.close()
        mInput.close()
    }

    @Synchronized
    override fun close() {
        if (mDataBase != null) mDataBase!!.close()
        super.close()
    }

    override fun onCreate(db: SQLiteDatabase) {
        //db.execSQL(CREATE_TABLE_NEW)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion < 10) {
            // Добавьте изменения схемы, связанные с новой версией
            db!!.execSQL(CREATE_TABLE_NEW)
        }
    }

    @SuppressLint("Recycle")
    fun userExist(login: String, password: String): Boolean {
        val db = writableDatabase
        val cursor = db.rawQuery(
            "Select * from users where login = ? and password = ?",
            arrayOf(login, password)
        )
        return cursor.count > 0
    }

    @SuppressLint("Recycle")
    fun getRoleByLogin(login: String): String {
        var role by Delegates.notNull<String>()
        val db = writableDatabase
        db.use { db ->
            val selectQuery = "SELECT $COLUMN_ROLE FROM $TABLE_NAME WHERE $COLUMN_LOGIN = ?"
            val cursor: Cursor = db.rawQuery(selectQuery, arrayOf(login))
            if (cursor.moveToFirst()) {
                role = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ROLE))
            }
        }
        return role
    }

    @SuppressLint("Range")
    fun getDataFromDatabase(): ArrayList<ItemsViewModel> {
        val dataList: ArrayList<ItemsViewModel> = ArrayList()
        val selectQuery = "SELECT * FROM equipment"
        val db = readableDatabase
        val cursor: Cursor?
        try {
            cursor = db.rawQuery(selectQuery, null)
        }
        catch (e: Exception){
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var id: Int
        var malf1: String
        var malf2: String?
        var malf3: String?
        var malf4: String?
        var malf5: String?
        var malf6: String?
        var title: String?
        var status1: String
        var status2: String
        var status3: String
        var status4: String
        var status5: String
        var status6: String
        if (cursor.moveToFirst()){
            do {
                id = cursor.getInt(cursor.getColumnIndex("_id"))
                title = cursor.getString(cursor.getColumnIndex("title"))
                malf1 = cursor.getString(cursor.getColumnIndex("malf1"))
                status1 = cursor.getString(cursor.getColumnIndex("status1"))
                malf2 = cursor.getString(cursor.getColumnIndex("malf2"))
                status2 = cursor.getString(cursor.getColumnIndex("status2"))
                malf3 = cursor.getString(cursor.getColumnIndex("malf3"))
                status3 = cursor.getString(cursor.getColumnIndex("status3"))
                malf4 = cursor.getString(cursor.getColumnIndex("malf4"))
                status4 = cursor.getString(cursor.getColumnIndex("status4"))
                malf5 = cursor.getString(cursor.getColumnIndex("malf5"))
                status5 = cursor.getString(cursor.getColumnIndex("status5"))
                malf6 = cursor.getString(cursor.getColumnIndex("malf6"))
                status6 = cursor.getString(cursor.getColumnIndex("status6"))
                val item = ItemsViewModel(id = id, title = title, malf1 = malf1, status1 = status1, malf2 = malf2, status2 = status2, malf3 = malf3,
                    status3 = status3, malf4 = malf4, status4 = status4, malf5 = malf5,status5 = status5 ,malf6 = malf6, status6 = status6)
                dataList.add(item)
            }while (cursor.moveToNext())
        }
        return dataList
        }
    fun insertData(title: String, malf1: String,malf2: String, malf3: String,malf4: String,malf5: String,malf6: String) {
        val values = ContentValues().apply {
            put(COLUMN_TITLE, title)
            put("malf1", malf1)
            put("status1", false)
            put("malf2", malf2)
            put("status2", false)
            put("malf3", malf3)
            put("status3", false)
            put("malf4", malf4)
            put("status4", false)
            put("malf5", malf5)
            put("status5", false)
            put("malf6", malf6)
            put("status6", false)
        }
        val db = writableDatabase
        try {
            db.insert(TABLE_NAME1, null, values)
        } catch (e: Exception) {
            // Обработка ошибок при вставке данных
            e.printStackTrace()
        } finally {
            db.close()
        }
    }

    fun clearTable(tableName: String) {
        val db = writableDatabase
        try {
            // Удаляем все строки из указанной таблицы
            db.delete(tableName, null, null)
        } catch (e: Exception) {
            // Обработка ошибок при удалении данных
            e.printStackTrace()
        } finally {
            db.close()
        }
    }

    }

