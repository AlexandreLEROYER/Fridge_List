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

        ///TEST///
        /*var liste : ArrayList<Item> = ArrayList<Item>()
        liste.add(Item(1, 5))
        liste.add(Item(2, 3))
        BDD.write(id.getId(), "Liste1", liste)
        var listeUser = ArrayList<Item>()

        BDD.read(id.getId(),"Liste1").observe(this, Observer { listeUserTemp ->
            listeUser = listeUserTemp
            Log.d("youpi", ""+listeUser)
        })

        })*/
        ///FinTest///
        BDD.readIngredient().observe(this, Observer { listeIngredientTemp ->
            Log.d("youpi", "" + BDD.listeIngredientAll)
            suiteProg()
        })
    }
    fun suiteProg() {
        setUpRecyclerViewDeMenu()
        populateRecyclerDeMenu()

        val viewMenu : ImageButton = findViewById(R.id.imageButton3)
        viewMenu.setOnClickListener {
            BDD.read(id.getId(), "frigo").observe(this, Observer { listeUserTemp ->
                val frigoIntent: Intent = Intent(this, FrigoActivity::class.java).apply {
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

            nameList.setPositiveButton("Confirmer",
                DialogInterface.OnClickListener { dialog, which ->
                    var name = nameField.text.toString()

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
                })
            nameList.setNegativeButton("Annuler", DialogInterface.OnClickListener { dialog, which ->
                Toast.makeText(applicationContext, "Miskina", Toast.LENGTH_SHORT).show()
            })
            nameList.show()

            Log.d("TAG", "ListAct")
        }
    }
    private fun setUpRecyclerViewDeMenu() {
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView5)
        val ManaLin = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = ManaLin
        recyclerView.adapter = adapter
    }
    private fun populateRecyclerDeMenu() {
        BDD.findList(id.getId()).observe(this,Observer{ listUserTemp ->
            var listUserTemp2 = listUserTemp
            for(element in listUserTemp){
                if(element == "frigo"){
                    listUserTemp2.remove(element)
                    Log.d("frigo", "present")
                }
            }
            adapter.setData(listUserTemp2)
            Log.d("frigo", "present")
        })
    }

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
interface AlimentAdapterListener {
    fun onUserClicked(name: Item)
}
interface MenuAdapterListener {
    fun onUserClicked(name: String)
}
interface IngredientAdapterListener {
    fun onUserClicked(name: Ingredient)
}
