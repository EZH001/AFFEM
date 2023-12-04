package com.example.affem

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.affem.databinding.ActivityOperator1Binding

class OperatorActivity : AppCompatActivity() {
    private var recAdapter: RecAdapter? = null
    companion object {
        private var mDBHelper: DBHelper? = null
        @SuppressLint("StaticFieldLeak")
        lateinit var binding: ActivityOperator1Binding
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOperator1Binding.inflate(layoutInflater)
        setContentView(binding.root)
        mDBHelper = DBHelper(this)
        init()
        binding.button3.setOnClickListener {
            getEquip()
        }
        binding.BackOA.setOnClickListener {
            finish()
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
}