package com.example.affem

import android.annotation.SuppressLint
import android.content.Intent
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.affem.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        private var mDBHelper: DBHelper? = null
        private var mDb: SQLiteDatabase? = null
        @SuppressLint("StaticFieldLeak")
        lateinit var binding: ActivityMainBinding
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //val assetCopier = AssetCopier(applicationContext, "MainDb.db", "MainDb.db")
        mDBHelper = DBHelper(this)
//
//        try {
//            mDBHelper!!.updateDataBase()
//        } catch (mIOException: IOException) {
//            throw Error("UnableToUpdateDatabase")
//        }

        try {
            mDb = mDBHelper!!.writableDatabase
        } catch (mSQLException: SQLException) {
            throw mSQLException
        }
        binding.InformOA.setOnClickListener {
            val intent = Intent(this@MainActivity, AboutActivity::class.java)
            startActivity(intent)
        }
    }

        @SuppressLint("SuspiciousIndentation")
        fun login(view: View){
        val loginText: TextView = findViewById(R.id.loginLA)
        val passwordText: EditText = findViewById(R.id.passwordLA)
        if (loginText.text.toString() == "" || passwordText.text.toString() == "")
            Toast.makeText(this, "Все поля должны быть заполнены", Toast.LENGTH_SHORT).show()
        val login: String = loginText.text.toString().trim()
        val password: String = passwordText.text.toString().trim()
        val checkUser = mDBHelper!!.userExist(login, password)
        if (checkUser){
            Toast.makeText(this, "Успешно", Toast.LENGTH_SHORT).show()
            val checkRole = mDBHelper!!.getRoleByLogin(login)
                if (checkRole == "admin"){
                    val intent  = Intent(this@MainActivity, AdminActivity::class.java)
                    startActivity(intent)
                }
            else {
                    val intent = Intent(this@MainActivity, OperatorActivity::class.java)
                    startActivity(intent)
            }
        }
        else Toast.makeText(this, "Неправильный логин или пароль", Toast.LENGTH_SHORT).show()
    }
}