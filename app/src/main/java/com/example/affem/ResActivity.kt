package com.example.affem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.affem.databinding.ActivityResBinding

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

    private fun init(){
        binding.recListRes.layoutManager = LinearLayoutManager(this)
        recAdapter = RecAdapter(this)
        binding.recListRes.adapter = recAdapter
    }

    private fun getEquip(){
        val dataList =  recAdapter!!.loadDataFromTextFile(this)
        recAdapter?.addItems(dataList)
        recAdapter!!.updateData(dataList)
    }

}