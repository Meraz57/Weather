package com.example.weather

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import com.example.weather.databinding.ActivityMainBinding
import com.example.weather.ui.Login

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnagree=findViewById<LinearLayout>(R.id.btnAgree)
        btnagree.setOnClickListener{


        }

    }
}