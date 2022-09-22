package com.example.opgg_test.presenter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.opgg_test.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}