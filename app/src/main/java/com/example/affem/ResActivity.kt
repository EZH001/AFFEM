package com.example.affem

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.affem.databinding.ActivityResBinding
import java.io.File

class ResActivity : AppCompatActivity() {
    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var binding: ActivityResBinding
        @SuppressLint("StaticFieldLeak")
        private var recAdapter: RecAdapter? = null
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        binding = ActivityResBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        binding.BackAA.setOnClickListener {
            finish()
        }
        binding.button.setOnClickListener {
            val fileName: String = binding.fileNameET.text.toString().trim()
            if (fileName.isEmpty()) {
                Toast.makeText(this, "Пожалуйста, введите имя файла", Toast.LENGTH_SHORT).show()
            } else {
                val file = File(this.filesDir, "$fileName.txt")

                if (file.exists()) {
                    val dataList = recAdapter!!.loadDataFromTextFile(this, fileName)
                    recAdapter?.addItems(dataList)
                    recAdapter!!.updateData(dataList)
                    recAdapter!!.setItemsEnabled(false)
                } else {
                    Toast.makeText(this, "Файл не существует", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun init(){
        binding.recListRes.setItemViewCacheSize(13)
        binding.recListRes.layoutManager = LinearLayoutManager(this)
        recAdapter = RecAdapter(this)
        binding.recListRes.adapter = recAdapter
    }
}