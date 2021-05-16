package com.example.fridge_list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.SearchView
import androidx.lifecycle.Observer
import com.example.controler.BDD
import com.example.model.Ingredient
import com.example.model.id

class IngredientsActivity : AppCompatActivity() {

    var listeSearch = ArrayList<Ingredient>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ingredients)

        var espoire = BDD.findName(1)
        Log.d("Leboncul", ""+espoire.nom)

        listeSearch = search("ma")
        Log.d("youpi", ""+listeSearch)

        val returnMenu : ImageButton = findViewById(R.id.floatingActionButton2)
        returnMenu.setOnClickListener {
            val mainIntent : Intent = Intent(this, MainActivity::class.java)
            startActivity(mainIntent)
            Log.d("TAG", "IngreAct")
        }

        var search : SearchView = findViewById(R.id.search)
    }

    fun search(name : String) : ArrayList<Ingredient> {
        var listeSearch = ArrayList<Ingredient>()
        for (ingredient in BDD.listeIngredientAll){
            if (ingredient.nom.startsWith(name)){
                listeSearch.add(ingredient)
            }
        }
        return listeSearch
    }
}