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

class ListActivity : AppCompatActivity() {

    var listeUser : ArrayList<Item> = ArrayList<Item>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.liste)

        listeUser = intent.getParcelableArrayListExtra<Item>(EXTRA_LIST) as ArrayList<Item>
        Log.d("Listtt", ""+listeUser)

        val name = intent.getStringExtra(EXTRA_NAME).toString()
        findViewById<TextView>(R.id.textView2).apply {
            text = name
        }

        val returnMenu : ImageButton = findViewById(R.id.floatingActionButton2)
        returnMenu.setOnClickListener {
            BDD.write(id.getId(), name, listeUser)
            val mainIntent : Intent = Intent(this, MainActivity::class.java)
            startActivity(mainIntent)
            Log.d("TAG", "FrigoAct")
        }

        val supp : ImageButton = findViewById(R.id.floatingActionButton3)
        supp.setOnClickListener {
            BDD.remove(id.getId(), name)
            val mainIntent : Intent = Intent(this, MainActivity::class.java)
            startActivity(mainIntent)
            Log.d("TAG", "FrigoAct")
        }
        val gofrigo : ImageButton = findViewById(R.id.floatingActionButton5)
        gofrigo.setOnClickListener {
            val ingredientintent : Intent = Intent(this, IngredientsActivity::class.java)
            startActivity(ingredientintent)
            Log.d("TAG", "IngreAct")
        }

    }
    fun recupList () {
        var liste : ArrayList<Item>
    }
}