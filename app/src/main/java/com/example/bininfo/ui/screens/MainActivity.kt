package com.example.bininfo.ui.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bininfo.databinding.ActivityMainBinding
import com.example.bininfo.utilits.replaceFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        replaceFragment(BinFragment(), false)
    }
}