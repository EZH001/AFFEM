package com.example.affem

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.affem.databinding.ActivityAdminBinding
import com.example.affem.databinding.ActivityOperator1Binding

class AdminActivity : AppCompatActivity() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        private var mDBHelper: DBHelper? = null
        @SuppressLint("StaticFieldLeak")
        lateinit var binding: ActivityAdminBinding
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mDBHelper = DBHelper(this)
        binding.BackAA.setOnClickListener {
            finish()
        }
        binding.addBtn.setOnClickListener {
            val title: String = binding.titleEquipAA.text.toString().trim()
            if (title == "") Toast.makeText(this, "Заполните поле", Toast.LENGTH_SHORT).show()
            else {
                mDBHelper!!.insertData(title)
                binding.titleEquipAA.text = null
            }
        }
        binding.goBtn.setOnClickListener {
            val intent = Intent(this@AdminActivity, OperatorActivity::class.java)
            startActivity(intent)
        }
    }
}