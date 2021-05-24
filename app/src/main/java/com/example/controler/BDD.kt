package com.example.controler

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.model.Ingredient
import com.example.model.Item
import com.google.firebase.database.FirebaseDatabase
import kotlin.collections.ArrayList

class BDD {

    companion object {

        //Connexion base de donnée
        var database: FirebaseDatabase = FirebaseDatabase.getInstance("https://fridge-list-7c029-default-rtdb.europe-west1.firebasedatabase.app/")
        var ref = database.reference
        var listeIngredientAll = ArrayList<Ingredient>()

        fun write(idUser: String, nameList: String, liste: ArrayList<Item>) {
            ref.child("user").child(idUser).child(nameList).removeValue()
            ref.child("user").child(idUser).child(nameList).setValue(liste)
        }

        //Lecture de la base de donnée à travers un LiveData (lecture de liste)
        fun read(idUser: String, nameList: String) : LiveData<ArrayList<Item>> {
            var listeUser : MutableLiveData<ArrayList<Item>> = MutableLiveData()
            var listeUserTemp : ArrayList<Item> = ArrayList<Item>()
            ref.child("user").child(idUser).child(nameList).get().addOnSuccessListener {
                for (child in it.children){
                    var item : Item? = child.getValue(Item::class.java)
                    listeUserTemp.add(item!!)
                }
                listeUser.postValue(listeUserTemp)
            }
            return listeUser
        }

        //Lecture de la base de donnée à travers un LiveData (lecture des ingrédients)
        fun readIngredient() : LiveData<ArrayList<Ingredient>> {
            var listeIngredient : MutableLiveData<ArrayList<Ingredient>> = MutableLiveData()
            var listeUserTemp = ArrayList<Ingredient>()

            ref.child("fruit").get().addOnSuccessListener {
                for (child in it.children){
                    var ingredientFruit : Ingredient? = child.getValue(Ingredient::class.java)
                    listeUserTemp.add(ingredientFruit!!)
                }
                ref.child("legume").get().addOnSuccessListener {
                    for (child in it.children) {
                        var ingredientLegume: Ingredient? = child.getValue(Ingredient::class.java)
                        listeUserTemp.add(ingredientLegume!!)
                    }
                    listeIngredientAll = listeUserTemp
                    listeIngredient.postValue(listeUserTemp)
                }
            }
            return listeIngredient
        }

        fun remove(idUser: String, nameList: String) {
            ref.child("user").child(idUser).child(nameList).removeValue()
        }

        //Recupération des noms des listes de l'utilisateur
        fun findList(idUser: String) : LiveData<ArrayList<String>> {
            var listeUser : MutableLiveData<ArrayList<String>> = MutableLiveData()
            var listeUserTemp = ArrayList<String>()

            ref.child("user").child(idUser).get().addOnSuccessListener {
                for(child in it.children){
                    listeUserTemp.add(child.key.toString())
                }
                listeUser.postValue(listeUserTemp)
            }
            return listeUser
        }

        //Recupération d'un ingrédient à travers un id
        fun findName(id: Int) : Ingredient {
            for(ingredient in listeIngredientAll){
                if(ingredient.id == id){
                    return ingredient
                }
            }
            return Ingredient()
        }
    }
}