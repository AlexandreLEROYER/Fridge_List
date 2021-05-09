package com.example.fridge_list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import com.example.controler.saveList
import com.example.model.Item
import com.example.model.id

class ListActivity : AppCompatActivity() {

    lateinit var liste : ArrayList<Item>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.liste)

        val name = intent.getStringExtra(EXTRA_NAME)
        val titleList = findViewById<TextView>(R.id.textView2).apply {
            text = name
        }

        val returnMenu : ImageButton = findViewById(R.id.floatingActionButton2)
        returnMenu.setOnClickListener {
            val mainIntent : Intent = Intent(this, MainActivity::class.java)
            startActivity(mainIntent)
            Log.d("TAG", "FrigoAct")
        }


    }
    fun recupList () {
        var liste : ArrayList<Item>
    }
}