package com.example.fridge_list

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.controler.saveList
import com.example.model.Item
import com.example.model.id

class FrigoActivity : AppCompatActivity() {

    lateinit var liste : ArrayList<Item>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.liste)

        val returnMenu : ImageButton = findViewById(R.id.floatingActionButton2)
        returnMenu.setOnClickListener {
            val mainIntent : Intent = Intent(this, MainActivity::class.java)
            startActivity(mainIntent)
            Log.d("TAG", "FrigoAct")
        }

        val titleList = findViewById<TextView>(R.id.textView2).apply {
            text = "Frigo"
        }
    }

    fun recupList () {
        var liste : ArrayList<Item>
    }
}