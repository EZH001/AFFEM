package com.example.affem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.affem.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity() {
    companion object{
        lateinit var binding: ActivityAboutBinding
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.button5.setOnClickListener {
            binding.textView.setText(R.string.about_text)
        }
        binding.button6.setOnClickListener {
            binding.textView.setText(R.string.spravka_text)
        }
        binding.BackOA.setOnClickListener {
            finish()
        }
    }
}