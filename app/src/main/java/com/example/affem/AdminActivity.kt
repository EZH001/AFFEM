package com.example.affem

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.affem.databinding.ActivityAdminBinding

class AdminActivity : AppCompatActivity() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        private var mDBHelper: DBHelper? = null
        @SuppressLint("StaticFieldLeak")
        lateinit var binding: ActivityAdminBinding
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mDBHelper = DBHelper(this)
        binding.BackAA.setOnClickListener {
            finish()
        }
        binding.addBtn.setOnClickListener {
            val title: String = binding.titleEquipAA.text.toString().trim()
            val malf1: String = binding.mafl1AA.text.toString().trim()
            val malf2: String = binding.mafl2AA.text.toString().trim()
            val malf3: String = binding.mafl3AA.text.toString().trim()
            val malf4: String = binding.mafl4AA.text.toString().trim()
            val malf5: String = binding.mafl5AA.text.toString().trim()
            val malf6: String = binding.mafl6AA.text.toString().trim()
            if (title == "" || malf1 == "") Toast.makeText(this, "Введите название и хотя бы 1 неполадку", Toast.LENGTH_SHORT).show()
            else {
                mDBHelper!!.insertData(title, malf1, malf2, malf3, malf4, malf5, malf6)
                binding.titleEquipAA.text = null
                binding.mafl1AA.text = null
                binding.mafl2AA.text = null
                binding.mafl3AA.text = null
                binding.mafl4AA.text = null
                binding.mafl5AA.text = null
                binding.mafl6AA.text = null
                Toast.makeText(this, "Оборудование успешно добавлено", Toast.LENGTH_SHORT).show()
            }
        }
        binding.goBtn.setOnClickListener {
            val intent = Intent(this@AdminActivity, OperatorActivity::class.java)
            startActivity(intent)
        }
    }
}