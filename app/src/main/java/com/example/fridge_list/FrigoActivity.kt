package com.example.fridge_list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import com.example.controler.BDD
import com.example.model.Item
import com.example.model.id

class FrigoActivity : AppCompatActivity() {

    lateinit var listeUser : ArrayList<Item>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.liste)

        listeUser = intent.getParcelableArrayListExtra<Item>(EXTRA_FRIGO) as ArrayList<Item>
        Log.d("maliste", ""+listeUser)

        val returnMenu : ImageButton = findViewById(R.id.floatingActionButton2)
        returnMenu.setOnClickListener {
            BDD.write(id.getId(), "frigo", listeUser)
            val mainIntent : Intent = Intent(this, MainActivity::class.java)
            startActivity(mainIntent)
            Log.d("TAG", "FrigoAct")
        }

        findViewById<TextView>(R.id.textView2).apply {
            text = "Frigo"
        }
    }
}