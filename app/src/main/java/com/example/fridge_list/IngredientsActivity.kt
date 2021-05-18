package com.example.fridge_list

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import android.widget.*
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
        val name = intent.getStringExtra(EXTRA_NAME).toString()
        if(name == "frigo"){
            listeUser = intent.getParcelableArrayListExtra<Item>(EXTRA_FRIGO) as ArrayList<Item>
        }else{
            listeUser = intent.getParcelableArrayListExtra<Item>(EXTRA_LIST) as ArrayList<Item>
        }

        val returnList : ImageButton = findViewById(R.id.floatingActionButton2)
        returnList.setOnClickListener {
            if(name == "frigo"){
                BDD.write(id.getId(), "frigo", listeUser)
                val Activite : Intent = Intent(this, FrigoActivity::class.java).apply {
                    putExtra(EXTRA_FRIGO, listeUser)
                }
                startActivity(Activite)
            }
            else{
                BDD.write(id.getId(), name, listeUser)
                val Activite : Intent = Intent(this, ListActivity::class.java).apply {
                    putExtra(EXTRA_LIST, listeUser)
                    putExtra(EXTRA_NAME, name)
                }
                startActivity(Activite)
            }
        }
        var texte: EditText = findViewById(R.id.edittext)
        texte.addTextChangedListener(textwatcher)
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
    private fun populateRecyclerDeIngredient(listerecherche : ArrayList<Ingredient> = ArrayList<Ingredient>()) {
        if(listerecherche == ArrayList<Ingredient>()) {
            adapter.setData(getList())
        } else {
            adapter.setData(listerecherche)
        }

    }
    private fun getList(): ArrayList<Ingredient> {
        return BDD.listeIngredientAll
    }
    private val textwatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            //
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            populateRecyclerDeIngredient(search(s.toString()))
        }

        override fun afterTextChanged(s: Editable?) {
            //
        }
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
                if(qtField.text.toString() != ""){
                    var qt = Integer.parseInt(qtField.text.toString())

                    if (qt == 0) {
                        Toast.makeText(applicationContext,
                            "Tu n'as rien ajouté",
                            Toast.LENGTH_SHORT).show()
                    } else {
                        var x = 0
                        for(i in listeUser){
                            if(i.id == ingredient.id){
                                i.qt = i.qt + qt
                                x = 1
                            }
                        }
                        if (x == 0){
                            listeUser.add(Item(ingredient.id, qt))
                        }
                    }
                }
            })
        qtList.setNegativeButton("Annuler", DialogInterface.OnClickListener { dialog, which ->
            Toast.makeText(applicationContext, "Miskina", Toast.LENGTH_SHORT).show()
        })
        qtList.show()
    }

}
