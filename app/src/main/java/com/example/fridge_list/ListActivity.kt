package com.example.fridge_list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.controler.BDD
import com.example.model.Item
import com.example.model.id

class ListActivity : AppCompatActivity(), AlimentAdapterListener {

    var listeUser : ArrayList<Item> = ArrayList<Item>()
    private val adapter = AlimentAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.liste)
        setUpRecyclerViewDeList()
        populateRecyclerDeList()
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

        val btnDel : ImageButton = findViewById(R.id.floatingActionButton3)
        btnDel.setOnClickListener {
            BDD.remove(id.getId(), name)
            val mainIntent : Intent = Intent(this, MainActivity::class.java)
            startActivity(mainIntent)
            Log.d("TAG", "FrigoAct")
        }
        val btnIngre : ImageButton = findViewById(R.id.floatingActionButton5)
        btnIngre.setOnClickListener {
            val ingredientintent : Intent = Intent(this, IngredientsActivity::class.java)
            startActivity(ingredientintent)
            Log.d("TAG", "IngreAct")
        }

    }

    private fun setUpRecyclerViewDeList() {
        val recyclerView: RecyclerView = findViewById(R.id.recyclerdeliste)
        recyclerView.layoutManager = GridLayoutManager(this, 3)
        recyclerView.adapter = adapter
    }
    private fun populateRecyclerDeList() {
        val aliments = getList()
        adapter.setData(aliments)
    }
    private fun getList(): ArrayList<Item> {
        val aliments = ArrayList<Item>()
        aliments.add(Item(50,1100))
        aliments.add(Item(52,150))
        aliments.add(Item(51,210))

        return aliments
    }
    override fun onUserClicked(item: Item) {
        Toast.makeText(this, "You cliked on : ${item.id}", Toast.LENGTH_SHORT).show()

        println("onpasseici")
    }

}




