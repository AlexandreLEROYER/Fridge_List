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
import androidx.appcompat.widget.AppCompatDrawableManager.get
import androidx.lifecycle.Observer
import com.example.controler.BDD
import com.example.model.Ingredient
import com.example.model.Item
import com.example.model.id
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

const val EXTRA_NAME = "com.example.fridge_list.NAME"
const val EXTRA_FRIGO = "com.example.fridge_list.FRIGO"
const val EXTRA_LIST = "com.example.fridge_list.LIST"

data class Aliment(val name: String,val quantite: String)

class MainActivity : AppCompatActivity(), AlimentAdapterListener {
    private val adapter = AlimentAdapter(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu)
        setUpRecyclerView()
        populateRecycler()

        id.receiveId(this)

        ///TEST///
        /*var liste : ArrayList<Item> = ArrayList<Item>()
        liste.add(Item(1, 5))
        liste.add(Item(2, 3))
        BDD.write(id.getId(), "Liste1", liste)
        var listeUser = ArrayList<Item>()
        var listeIngredient = ArrayList<Ingredient>()
        BDD.read(id.getId(),"Liste1").observe(this, Observer { listeUserTemp ->
            listeUser = listeUserTemp
            Log.d("youpi", ""+listeUser)
        })
        BDD.readIngredient().observe(this, Observer { listeIngredientTemp ->
            listeIngredient = listeIngredientTemp
            Log.d("youpi", ""+listeIngredient)
        })*/
        ///FinTest///

        val viewMenu : ImageButton = findViewById(R.id.imageButton3)
        viewMenu.setOnClickListener {
            BDD.read(id.getId(),"frigo").observe(this, Observer { listeUserTemp ->
                val frigoIntent : Intent = Intent(this, FrigoActivity::class.java).apply {
                    putExtra(EXTRA_FRIGO, listeUserTemp)
                }
                startActivity(frigoIntent)
            })
        }

        val viewList : ImageButton = findViewById(R.id.floatingActionButton)
        viewList.setOnClickListener {
            val nameList : AlertDialog.Builder = AlertDialog.Builder(this)
            nameList.setTitle("Nom de la nouvelle liste")
            nameList.setMessage("Rentrez le nom de votre nouvelle liste")

            val nameField : EditText = EditText(this)
            nameField.hint = "Nouvelle Liste"
            nameField.inputType = InputType.TYPE_CLASS_TEXT
            nameList.setView(nameField)

            nameList.setPositiveButton("Confirmer", DialogInterface.OnClickListener { dialog, which ->
                var name = nameField.text.toString()

                if (name == "") {
                    Toast.makeText(applicationContext, "Il faut donner un nom gros", Toast.LENGTH_SHORT).show()
                }
                else{
                    BDD.read(id.getId(),name).observe(this, Observer { listeUserTemp ->
                        val listIntent : Intent = Intent(this, ListActivity::class.java).apply {
                            putExtra(EXTRA_LIST, listeUserTemp)
                            putExtra(EXTRA_NAME, name)
                        }
                        startActivity(listIntent)
                        Toast.makeText(applicationContext, "Liste créée bg", Toast.LENGTH_SHORT).show()
                    })
                }
            })
            nameList.setNegativeButton("Annuler", DialogInterface.OnClickListener { dialog, which ->
                Toast.makeText(applicationContext, "Miskina", Toast.LENGTH_SHORT).show()
            })
            nameList.show()

            Log.d("TAG", "ListAct")
        }
    }
    private fun setUpRecyclerView() {
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView5)
        recyclerView.layoutManager = GridLayoutManager(this, 3)
        recyclerView.adapter = adapter
    }
    private fun populateRecycler() {
        val aliments = getList()
        adapter.setData(aliments)
    }
    private fun getList(): ArrayList<Aliment> {
        val aliments = arrayListOf<Aliment>()
        aliments.add(Aliment("Patate","12"))

        aliments.add(Aliment("Haricots","200g"))
        aliments.add(Aliment("Fromage","100"))

        /*for (i in 0..100) {
            val name = "tomate"
            val quantite = "12"
            aliments.add(Aliment(name, quantite))
        }
        */
        return aliments
    }
    override fun onUserClicked(aliment: Aliment) {
        Toast.makeText(this, "You cliked on : ${aliment.name}", Toast.LENGTH_SHORT).show()

        println("onpasseici")
    }
}
interface AlimentAdapterListener {
    fun onUserClicked(name: Aliment)
}