package com.example.affem

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build

import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.affem.databinding.ActivityOperator1Binding


class OperatorActivity : AppCompatActivity() {
    private var recAdapter: RecAdapter? = null
    companion object {
        private var mDBHelper: DBHelper? = null
        @SuppressLint("StaticFieldLeak")
        lateinit var binding: ActivityOperator1Binding
    }
    @SuppressLint("NotifyDataSetChanged")
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
            val dataList = recAdapter!!.itemsList
            recAdapter!!.saveDataToTextFile(this, dataList)
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
        recAdapter = RecAdapter(this)
        binding.equipListOA.adapter = recAdapter
    }

}