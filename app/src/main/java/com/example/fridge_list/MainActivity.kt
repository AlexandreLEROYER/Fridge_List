package com.example.fridge_list

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.model.ItemAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var itemAdapter: ItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu)

        itemAdapter = ItemAdapter(mutableListOf())


    }
}