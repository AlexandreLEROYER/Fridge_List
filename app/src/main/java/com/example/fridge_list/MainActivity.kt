package com.example.fridge_list

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.model.ItemAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var itemAdapter: ItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
<<<<<<<<< Temporary merge branch 1
        setContentView(R.layout.menu)

        itemAdapter = ItemAdapter(mutableListOf())


=========
        setContentView(R.layout.action_de_liste)
>>>>>>>>> Temporary merge branch 2
    }
}