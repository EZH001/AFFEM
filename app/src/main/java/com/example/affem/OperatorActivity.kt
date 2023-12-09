package com.example.affem

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.affem.databinding.ActivityOperator1Binding


class OperatorActivity : AppCompatActivity() {
    private var recAdapter: RecAdapter? = null
    companion object {
        @SuppressLint("StaticFieldLeak")
        private var mDBHelper: DBHelper? = null
        @SuppressLint("StaticFieldLeak")
        lateinit var binding: ActivityOperator1Binding
    }
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @SuppressLint("NotifyDataSetChanged")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        binding = ActivityOperator1Binding.inflate(layoutInflater)
        setContentView(binding.root)
        mDBHelper = DBHelper(this)
        init()
        binding.button3.setOnClickListener {
            getEquip()
            recAdapter!!.setItemsEnabled(true)
            binding.button3.visibility = View.GONE
            binding.saveBtn.visibility = View.VISIBLE
            binding.button4.visibility = View.VISIBLE
        }
        binding.BackOA.setOnClickListener {
            finish()
        }
        binding.button4.setOnClickListener {
            val intent = Intent(this@OperatorActivity, ResActivity::class.java)
            startActivity(intent)
        }
        binding.saveBtn.setOnClickListener {
            val dataList = recAdapter!!.itemsList
            recAdapter!!.saveDataToTextFile(this, dataList)
            Toast.makeText(this, "Отчет сохранен " + recAdapter!!.getCurrentDate(), Toast.LENGTH_SHORT).show()
        }
    }
    private fun getEquip(){
        val dataList = mDBHelper!!.getDataFromDatabase()
        Log.e("pppp", "${dataList.size}")
        recAdapter?.addItems(dataList)
    }
    private fun init(){
        binding.equipListOA.setItemViewCacheSize(13)
        binding.equipListOA.layoutManager = LinearLayoutManager(this)
        recAdapter = RecAdapter(this)
        binding.equipListOA.adapter = recAdapter
    }

}