package com.example.fridge_list

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.controler.BDD
import com.example.model.Item
import com.example.model.id

class FrigoActivity : AppCompatActivity(), AlimentAdapterListener {

    lateinit var listeUser : ArrayList<Item>
    private val adapter = AlimentAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.liste)
        val frigobutton : ImageButton = findViewById(R.id.buttonMiseAuFrigo)
        frigobutton.visibility = View.INVISIBLE
        listeUser = intent.getParcelableArrayListExtra<Item>(EXTRA_FRIGO) as ArrayList<Item>
        Log.d("maliste", ""+listeUser)
        Log.e("test", "Ouiii")

        setUpRecyclerViewDeFrigo()
        populateRecyclerDeFrigo()

        val returnMenu : ImageButton = findViewById(R.id.floatingActionButton2)
        returnMenu.setOnClickListener {
            BDD.write(id.getId(), "frigo", listeUser)
            val mainIntent : Intent = Intent(this, MainActivity::class.java)
            startActivity(mainIntent)
            Log.d("TAG", "FrigoAct")
        }

        val btnIngre : ImageButton = findViewById(R.id.floatingActionButton5)
        btnIngre.setOnClickListener {
            val ingredientintent : Intent = Intent(this, IngredientsActivity::class.java).apply {
                putExtra(EXTRA_FRIGO, listeUser)
                putExtra(EXTRA_NAME, "frigo")
            }
            startActivity(ingredientintent)
            Log.d("TAG", "IngreAct")
        }

        val btnDel : ImageButton = findViewById(R.id.floatingActionButton3)
        btnDel.setOnClickListener {
            BDD.remove(id.getId(), "frigo")
            val mainIntent : Intent = Intent(this, MainActivity::class.java)
            startActivity(mainIntent)
            Log.d("TAG", "FrigoAct")
        }

        findViewById<TextView>(R.id.textView2).apply {
            text = "Frigo"
        }
    }
    private fun setUpRecyclerViewDeFrigo() {
        val recyclerView: RecyclerView = findViewById(R.id.recyclerdeliste)
        recyclerView.layoutManager = GridLayoutManager(this, 3)
        recyclerView.adapter = adapter
    }
    private fun populateRecyclerDeFrigo() {
        //val aliments = getList()
        adapter.setData(listeUser)
        //adapter.setData(aliments)
    }
/*    private fun getList(): ArrayList<Item> {
        val aliments = ArrayList<Item>()
        aliments.add(Item(3,100))
        aliments.add(Item(2,50))
        aliments.add(Item(1,20))
        aliments.add(Item(50,1100))
        aliments.add(Item(52,150))
        aliments.add(Item(51,210))

        return aliments
    }*/
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
                    setUpRecyclerViewDeFrigo()
                    populateRecyclerDeFrigo()
                }
            })
        qtList.setNegativeButton("Annuler", DialogInterface.OnClickListener { dialog, which ->
            Toast.makeText(applicationContext, "Miskina", Toast.LENGTH_SHORT).show()
        })
        qtList.setNeutralButton("Supprimer",
            DialogInterface.OnClickListener { dialog, which ->
                listeUser.remove(item)
                setUpRecyclerViewDeFrigo()
                populateRecyclerDeFrigo()
             })
        qtList.show()
    }
}
