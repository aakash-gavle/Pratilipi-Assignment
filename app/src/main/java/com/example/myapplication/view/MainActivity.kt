package com.example.myapplication.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.example.myapplication.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpFragment()
    }

    private fun setUpFragment(){
        val fragmentManager: FragmentManager=supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.fragmentLayout, ScannerFragment()).commit()
    }
}