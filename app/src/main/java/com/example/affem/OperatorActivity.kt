package com.example.affem

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build

import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.affem.databinding.ActivityOperator1Binding
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date


class OperatorActivity : AppCompatActivity() {
    private var recAdapter: RecAdapter? = null
    companion object {
        private var mDBHelper: DBHelper? = null
        @SuppressLint("StaticFieldLeak")
        lateinit var binding: ActivityOperator1Binding
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOperator1Binding.inflate(layoutInflater)
        setContentView(binding.root)
        mDBHelper = DBHelper(this)
        init()
        binding.button3.setOnClickListener {
            getEquip()
            recAdapter!!.setItemsEnabled(true)
        }
        binding.BackOA.setOnClickListener {
            finish()
        }
        binding.button4.setOnClickListener {
            val dataList = recAdapter!!.itemsList // Используйте items, если используете ListAdapter
            saveDataToTextFile(this, dataList)
            val intent = Intent(this@OperatorActivity, ResActivity::class.java)
            startActivity(intent)
        }
    }
    private fun getEquip(){
        val dataList = mDBHelper!!.getDataFromDatabase()
        Log.e("pppp", "${dataList.size}")
        recAdapter?.addItems(dataList)
    }
    private fun init(){
        binding.equipListOA.layoutManager = LinearLayoutManager(this)
        recAdapter = RecAdapter()
        binding.equipListOA.adapter = recAdapter
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun saveDataToTextFile(context: Context, dataList: List<ItemsViewModel>) {
        val fileName = getCurrentDate()
        try {
            val file = File(context.filesDir, "$fileName.txt") // Создание файла в директории приложения
            val writer = FileWriter(file)
            val bufferedWriter = BufferedWriter(writer)

            for (data in dataList) {
                val line = "${data.id}, ${data.title}, ${data.malf1}, ${data.status1},${data.malf2}, ${data.status2},${data.malf3}, ${data.status3},${data.malf4}, ${data.status4},${data.malf5}, ${data.status5},${data.malf6},${data.status1}"
                bufferedWriter.write(line)
                bufferedWriter.newLine() // Добавьте переход на новую строку между объектами
            }

            bufferedWriter.close()
            writer.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun getCurrentDate(): String {
        val currentDate = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yy") // Формат даты, например, "2023-12-06"
        return currentDate.format(formatter)
    }


}