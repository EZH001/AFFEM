package com.example.affem

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.affem.databinding.ActivityResBinding
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class ResActivity : AppCompatActivity() {
    companion object{
        lateinit var binding: ActivityResBinding
        private var recAdapter: RecAdapter? = null
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()

        binding.button.setOnClickListener {
            getEquip()
            recAdapter!!.setItemsEnabled(false)
        }
    }

    fun readDataFromFile(context: Context, fileName: String): ArrayList<ItemsViewModel> {
        val dataList = ArrayList<ItemsViewModel>()
        try {
            val inputStream = openFileInput("07-12-23.txt")
            val reader = BufferedReader(InputStreamReader(inputStream))
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                // Создайте объект ItemsViewModel и добавьте его в dataList
                val itemsViewModel = parseLineToItemsViewModel(line.orEmpty())
                dataList.add(itemsViewModel)
            }
            reader.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return dataList
    }

    private fun init(){
        ResActivity.binding.recListRes.layoutManager = LinearLayoutManager(this)
        recAdapter = RecAdapter()
        ResActivity.binding.recListRes.adapter = recAdapter
    }

    private fun getEquip(){
        val dataList = readDataFromFile(this, "06-12-23.txt")

        val adapter = RecAdapter()
        adapter.addItems(dataList)

        binding.recListRes.layoutManager = LinearLayoutManager(this)
        binding.recListRes.adapter = adapter
    }
    fun parseLineToItemsViewModel(line: String): ItemsViewModel {
        // Разделите строку на части, используя подходящий разделитель (например, запятая)
        val parts = line.split(",")

        // Создайте объект ItemsViewModel и установите значения из разделенных частей
        return ItemsViewModel(
            id = parts[0].toInt(),
            title = parts[1],
            malf1 = parts[2],
            status1 = parts[3],
            malf2 = parts[4],
            status2 = parts[5],
            malf3 = parts[6],
            status3 = parts[7],
            malf4 = parts[8],
            status4 = parts[9],
            malf5 = parts[10],
            status5 = parts[11],
            malf6 = parts[12],
            status6 = parts[13],
        )
    }
}