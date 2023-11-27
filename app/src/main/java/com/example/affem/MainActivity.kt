package com.example.affem

import android.annotation.SuppressLint
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        private var mDBHelper: DBHelper? = null
        private var mDb: SQLiteDatabase? = null
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        mDBHelper = DBHelper(this)

        mDb = try {
            mDBHelper!!.writableDatabase
        } catch (mSQLException: SQLException) {
            throw mSQLException
        }
    }

        fun login(view: View){
        val loginText: TextView = findViewById(R.id.loginLA)
        val passwordText: EditText = findViewById(R.id.passwordLA)
            val test: TextView = findViewById(R.id.taskMA)
        if (loginText.text.toString() == "" || passwordText.text.toString() == "")
            Toast.makeText(this, "Все поля должны быть заполнены", Toast.LENGTH_SHORT).show()
        val login: String = loginText.text.toString().trim()
        val password: String = passwordText.text.toString().trim()
        val checkUser = mDBHelper!!.CheckUser(login, password)
        if (checkUser == true){
            Toast.makeText(this, "Успешно", Toast.LENGTH_SHORT).show()
            var role = mDBHelper!!.CheckRole()
            test.text = role}
        else Toast.makeText(this, "Не успешно", Toast.LENGTH_SHORT).show()

//            val checkRole = mDBHelper!!.CheckRole(login, password)
//            if (checkRole == true)
//                Toast.makeText(this, "Права администратора", Toast.LENGTH_SHORT).show()
//            else Toast.makeText(this, "Права оператора", Toast.LENGTH_SHORT).show()

    }
}