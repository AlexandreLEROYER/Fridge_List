package com.example.fridge_list

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.controler.BDD
import com.example.model.Ingredient
import com.example.model.Item
import com.example.model.id

const val EXTRA_NAME = "com.example.fridge_list.NAME"
const val EXTRA_FRIGO = "com.example.fridge_list.FRIGO"
const val EXTRA_LIST = "com.example.fridge_list.LIST"

class MainActivity : AppCompatActivity(), MenuAdapterListener{
    private val adapter = MenuAdapter(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu)
        id.receiveId(this)

        //Recupération de la liste des ingrédients
        BDD.readIngredient().observe(this, Observer { listeIngredientTemp ->
            suiteProg()
        })
    }

    //Suite du programme une fois la liste recupérée
    fun suiteProg() {
        setUpRecyclerViewDeMenu()
        populateRecyclerDeMenu()

        //Bouton frigo
        val viewMenu : ImageButton = findViewById(R.id.imageButton3)
        viewMenu.setOnClickListener {
            BDD.read(id.getId(), "frigo").observe(this, Observer { listeUserTemp ->
                val frigoIntent: Intent = Intent(this, FrigoActivity::class.java).apply {
                    putExtra(EXTRA_FRIGO, listeUserTemp)
                }
                startActivity(frigoIntent)
            })
        }

        //Bouton créaction liste avec une pop-up pour le nom
        val viewList : ImageButton = findViewById(R.id.floatingActionButton)
        viewList.setOnClickListener {
            val nameList : AlertDialog.Builder = AlertDialog.Builder(this)
            nameList.setTitle("Nom de la nouvelle liste")
            nameList.setMessage("Rentrez le nom de votre nouvelle liste")

            //Récupération du nom
            val nameField : EditText = EditText(this)
            nameField.hint = "Nouvelle Liste"
            nameField.inputType = InputType.TYPE_CLASS_TEXT
            nameList.setView(nameField)

            nameList.setPositiveButton("Confirmer",
                DialogInterface.OnClickListener { dialog, which ->
                    var name = nameField.text.toString()
                    if(name.contains(".") || name.contains("#") || name.contains("[") || name.contains("]")) {
                        Toast.makeText(applicationContext,
                            "Le nom ne peut pas contenir . , # , [ ou ]",
                            Toast.LENGTH_LONG).show()
                    }else{
                        if (name == "") {
                            Toast.makeText(applicationContext,
                                "Il faut donner un nom gros",
                                Toast.LENGTH_SHORT).show()
                        } else {
                            BDD.read(id.getId(), name).observe(this, Observer { listeUserTemp ->
                                val listIntent: Intent = Intent(this, ListActivity::class.java).apply {
                                    putExtra(EXTRA_LIST, listeUserTemp)
                                    putExtra(EXTRA_NAME, name)
                                }
                                startActivity(listIntent)
                                Toast.makeText(applicationContext, "Liste créée bg", Toast.LENGTH_SHORT)
                                    .show()
                            })
                        }
                    }
                })
            nameList.setNegativeButton("Annuler", DialogInterface.OnClickListener { dialog, which ->
                Toast.makeText(applicationContext, "Miskina", Toast.LENGTH_SHORT).show()
            })
            nameList.show()
        }
    }

    private fun setUpRecyclerViewDeMenu() {
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView5)
        val ManaLin = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = ManaLin
        recyclerView.adapter = adapter
    }

    private fun populateRecyclerDeMenu() {
        BDD.findList(id.getId()).observe(this, Observer { listUserTemp ->
            //On supprimme la liste frigo de la recycleView (bouton frigo déjà présent)
            if (listUserTemp.contains("frigo")) {
                listUserTemp.remove("frigo")
            }
            adapter.setData(listUserTemp)
        })
    }

    //On lance la liste cliquée
    override fun onUserClicked(list: String) {
        BDD.read(id.getId(), list).observe(this, Observer { listeUserTemp ->
            val listIntent: Intent = Intent(this, ListActivity::class.java).apply {
                putExtra(EXTRA_LIST, listeUserTemp)
                putExtra(EXTRA_NAME, list)
            }
            startActivity(listIntent)
        })
    }

}
interface ItemAdapterListener {
    fun onUserClicked(name: Item)
}
interface MenuAdapterListener {
    fun onUserClicked(name: String)
}
interface IngredientAdapterListener {
    fun onUserClicked(name: Ingredient)
}
