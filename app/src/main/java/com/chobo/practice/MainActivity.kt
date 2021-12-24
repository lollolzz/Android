package com.chobo.practice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tv: TextView = findViewById(R.id.tv_hello)
        val btn: Button = findViewById(R.id.btn_kor)


        btn.setOnClickListener {
            tv.text = "안녕"
        }
        
    }
}