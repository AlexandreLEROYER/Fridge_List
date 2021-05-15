package com.example.fridge_list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.lifecycle.Observer
import com.example.controler.BDD
import com.example.model.Ingredient

class IngredientsActivity : AppCompatActivity() {

    var listeIngredient = ArrayList<Ingredient>()
    var listeSearch = ArrayList<Ingredient>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ingredients)

        BDD.readIngredient().observe(this, Observer { listeIngredientTemp ->
            listeIngredient = listeIngredientTemp
        })
        listeSearch = search("ma")
        Log.d("youpi", ""+listeSearch)

        var search : SearchView = findViewById(R.id.search)
    }

    fun search(name : String) : ArrayList<Ingredient> {
        var listeSearch = ArrayList<Ingredient>()
        for (ingredient in listeIngredient){
            if (ingredient.nom.startsWith(name)){
                listeSearch.add(ingredient)
            }
        }
        return listeSearch
    }
}