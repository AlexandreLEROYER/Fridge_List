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
import com.example.model.Item
import com.example.model.id

const val EXTRA_NAME = "com.example.fridge_list.NAME"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu)

        val idUser : String? = id().receiveId(this)

        val viewMenu : ImageButton = findViewById(R.id.imageButton3)
        viewMenu.setOnClickListener {
            val frigoIntent : Intent = Intent(this, FrigoActivity::class.java)
            startActivity(frigoIntent)
            Log.d("TAG", "FrigoAct")
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
                    val listIntent : Intent = Intent(this, ListActivity::class.java).apply {
                        putExtra(EXTRA_NAME, name)
                    }
                    startActivity(listIntent)
                    Toast.makeText(applicationContext, "Liste créée bg", Toast.LENGTH_SHORT).show()
                }
            })
            nameList.setNegativeButton("Annuler", DialogInterface.OnClickListener { dialog, which ->
                Toast.makeText(applicationContext, "Miskina", Toast.LENGTH_SHORT).show()
            })
            nameList.show()

            Log.d("TAG", "ListAct")
        }
    }

}