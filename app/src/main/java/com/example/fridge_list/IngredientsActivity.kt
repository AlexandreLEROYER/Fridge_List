package com.example.fridge_list

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.controler.BDD
import com.example.model.Ingredient
import com.example.model.Item
import com.example.model.id

class IngredientsActivity : AppCompatActivity(), IngredientAdapterListener  {

    var listeSearch = ArrayList<Ingredient>()
    var listeUser = ArrayList<Item>()
    private val adapter = IngredientAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ingredients)
        setUpRecyclerViewDeIngredient()

        populateRecyclerDeIngredient()
        listeUser = intent.getParcelableArrayListExtra<Item>(EXTRA_LIST) as ArrayList<Item>
        val name = intent.getStringExtra(EXTRA_NAME).toString()

        listeSearch = search("ma")
        Log.d("youpi", ""+listeSearch)

        val returnList : ImageButton = findViewById(R.id.floatingActionButton2)
        returnList.setOnClickListener {
            val ListIntent : Intent = Intent(this, ListActivity::class.java).apply {
                putExtra(EXTRA_LIST, listeUser)
                putExtra(EXTRA_NAME, name)
            }
            startActivity(ListIntent)
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

    private fun setUpRecyclerViewDeIngredient() {
        val recyclerView: RecyclerView = findViewById(R.id.recycleringredient)
        recyclerView.layoutManager = GridLayoutManager(this, 3)
        recyclerView.adapter = adapter
    }
    private fun populateRecyclerDeIngredient() {
        adapter.setData(getList())
    }
    private fun getList(): ArrayList<Ingredient> {
        return BDD.listeIngredientAll
    }
    override fun onUserClicked(ingredient: Ingredient) {
        val qtList : AlertDialog.Builder = AlertDialog.Builder(this)
        qtList.setTitle("Quantité")
        qtList.setMessage("Rentrez une quantité")

        val qtField : EditText = EditText(this)
        qtField.hint = "0"
        qtField.inputType = InputType.TYPE_CLASS_NUMBER
        qtList.setView(qtField)

        qtList.setPositiveButton("Ajouter",
            DialogInterface.OnClickListener { dialog, which ->
                var qt = Integer.parseInt(qtField.text.toString())

                if (qt == 0) {
                    Toast.makeText(applicationContext,
                        "Tu n'as rien ajouté",
                        Toast.LENGTH_SHORT).show()
                } else {
                    listeUser.add(Item(ingredient.id, qt))
                    Log.d("nnn", ""+listeUser)
                }
            })
        qtList.setNegativeButton("Annuler", DialogInterface.OnClickListener { dialog, which ->
            Toast.makeText(applicationContext, "Miskina", Toast.LENGTH_SHORT).show()
        })
        qtList.show()
    }

}
