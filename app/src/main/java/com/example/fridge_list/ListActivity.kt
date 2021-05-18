package com.example.fridge_list

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
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

        listeUser = intent.getParcelableArrayListExtra<Item>(EXTRA_LIST) as ArrayList<Item>
        Log.d("Listtt", ""+listeUser)

        val name = intent.getStringExtra(EXTRA_NAME).toString()
        findViewById<TextView>(R.id.textView2).apply {
            text = name
        }
        setUpRecyclerViewDeList()
        populateRecyclerDeList()

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
            val ingredientintent : Intent = Intent(this, IngredientsActivity::class.java).apply {
                putExtra(EXTRA_LIST, listeUser)
                putExtra(EXTRA_NAME, name)
            }
            startActivity(ingredientintent)
            Log.d("TAG", "IngreAct")
        }
        /*val viewMenu : ImageButton = findViewById(R.id.imageButton6)
        viewMenu.setOnClickListener {
            var listTemp = listeUser
            BDD.read(id.getId(),"frigo").observe(this, Observer { listeUserTemp ->
                for(elem in listeUserTemp){
                    listTemp.add(elem)
                }
                BDD.write(id.getId(), "frigo", listTemp)
            })

        }*/

    }

    private fun setUpRecyclerViewDeList() {
        val recyclerView: RecyclerView = findViewById(R.id.recyclerdeliste)
        recyclerView.layoutManager = GridLayoutManager(this, 3)
        recyclerView.adapter = adapter
    }


    private fun populateRecyclerDeList() {
        adapter.setData(listeUser)
    }

    override fun onUserClicked(item: Item) {
        val qtList : AlertDialog.Builder = AlertDialog.Builder(this)
        qtList.setTitle("Quantité")
        qtList.setMessage("Rentrez une quantité")

        val qtField : EditText = EditText(this)
        qtField.hint = item.qt.toString()
        qtField.inputType = InputType.TYPE_CLASS_NUMBER
        qtList.setView(qtField)

        qtList.setPositiveButton("Appliquer",
            DialogInterface.OnClickListener { dialog, which ->
                if(qtField.text.toString() != ""){
                    var qt = Integer.parseInt(qtField.text.toString())

                    if (qt == 0) {
                        listeUser.remove(item)
                        Toast.makeText(applicationContext,
                            "Tu as supprimé l'ingrédient",
                            Toast.LENGTH_SHORT).show()
                    }
                    if (qt != null){
                        item.qt = qt
                    }
                    setUpRecyclerViewDeList()
                    populateRecyclerDeList()
                }
            })
        qtList.setNegativeButton("Annuler", DialogInterface.OnClickListener { dialog, which ->
            Toast.makeText(applicationContext, "Miskina", Toast.LENGTH_SHORT).show()
        })
        qtList.setNeutralButton("Supprimer",
            DialogInterface.OnClickListener { dialog, which ->
                listeUser.remove(item)
                setUpRecyclerViewDeList()
                populateRecyclerDeList()
            })
        qtList.show()
    }

}




