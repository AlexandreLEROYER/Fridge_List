package com.example.fridge_list

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.controler.BDD
import com.example.model.Item
import com.example.model.id

//Classe similaire à FrigoActivity
class ListActivity : AppCompatActivity(), ItemAdapterListener {

    var listeUser : ArrayList<Item> = ArrayList<Item>()
    private val adapter = ItemAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.liste)

        //Recupération de la liste recherchée en base de donnée et de son nom
        listeUser = intent.getParcelableArrayListExtra<Item>(EXTRA_LIST) as ArrayList<Item>

        val name = intent.getStringExtra(EXTRA_NAME).toString()
        findViewById<TextView>(R.id.textView2).apply {
            text = name
        }
        //Créaction de la recycleView
        setUpRecyclerViewDeList()
        populateRecyclerDeList()

        val returnMenu : ImageButton = findViewById(R.id.floatingActionButton2)
        returnMenu.setOnClickListener {
            BDD.write(id.getId(), name, listeUser)
            val mainIntent = Intent(this, MainActivity::class.java)
            startActivity(mainIntent)
        }

        //Suppression de la liste
        val btnDel : ImageButton = findViewById(R.id.floatingActionButton3)
        btnDel.setOnClickListener {
            BDD.remove(id.getId(), name)
            val mainIntent : Intent = Intent(this, MainActivity::class.java)
            startActivity(mainIntent)
        }

        //Ajout ingrédients
        val btnIngre : ImageButton = findViewById(R.id.floatingActionButton5)
        btnIngre.setOnClickListener {
            val ingredientintent : Intent = Intent(this, IngredientsActivity::class.java).apply {
                putExtra(EXTRA_LIST, listeUser)
                putExtra(EXTRA_NAME, name)
            }
            startActivity(ingredientintent)
        }

        //Transfert liste vers frigo
        val miseAuFrigoButton : ImageButton = findViewById(R.id.buttonMiseAuFrigo)
        miseAuFrigoButton.setOnClickListener {
            var listTemp = listeUser
            BDD.read(id.getId(),"frigo").observe(this, Observer { listeUserTemp ->
                val listeusertemp = (listeUserTemp + listeUser)          // on concatène
                    .groupBy { it.id }                  // on regroupe par nom
                    .values                               // on prend les valeurs
                    .map {                                // pour chaque liste
                        it.reduce {                       // on additionne les qt
                                ingre, item -> Item(item.id, ingre.qt + item.qt)
                        }
                    }
                BDD.write(id.getId(), "frigo", listeusertemp as ArrayList<Item>) // obligé de repasser en arraylist d'items
            })

        }

    }

    private fun setUpRecyclerViewDeList() {
        val recyclerView: RecyclerView = findViewById(R.id.recyclerdeliste)
        recyclerView.layoutManager = GridLayoutManager(this, 3)
        recyclerView.adapter = adapter
    }

    private fun populateRecyclerDeList() {
        adapter.setData(listeUser)
    }

    //Modification ou suppression d'un item dans la liste
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




