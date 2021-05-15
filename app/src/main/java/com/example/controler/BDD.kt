package com.example.controler

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.model.Item
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.getValue
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class BDD {

    companion object {

        var database: FirebaseDatabase = FirebaseDatabase.getInstance("https://fridge-list-7c029-default-rtdb.europe-west1.firebasedatabase.app/")
        var ref = database.reference

        fun write(idUser: String, nameList: String, liste: ArrayList<Item>) {
            ref.child("user").child(idUser).child(nameList).removeValue()
            ref.child("user").child(idUser).child(nameList).setValue(liste)
        }

        fun read(idUser: String, nameList: String) : LiveData<ArrayList<Item>> {
            var listeUser : MutableLiveData<ArrayList<Item>> = MutableLiveData()
            var listeUserTemp : ArrayList<Item> = ArrayList<Item>()
            ref.child("user").child(idUser).child(nameList).get().addOnSuccessListener {
                for (child in it.children){
                    var item : Item? = child.getValue(Item::class.java)
                    listeUserTemp.add(item!!)
                    Log.d("yass", ""+listeUser)
                }
                listeUser.postValue(listeUserTemp)
            }
            return listeUser
        }

        fun remove(idUser: String, nameList: String) {
            ref.child("user").child(idUser).child(nameList).removeValue()
        }

        fun findName(id: Int) : String {
            var name : String = ""
            var chemin : String = ""
            chemin = if (id < 50)
                "legume"
            else
                "fruit"

            ref.child(chemin).child(id.toString()).get().addOnSuccessListener {
                name = it.value.toString()
                Log.d("idItem", it.value.toString())
            }.addOnFailureListener {
                name = "Error"
                Log.d("idItem", "Echec")
            }
            return name
        }
    }

}