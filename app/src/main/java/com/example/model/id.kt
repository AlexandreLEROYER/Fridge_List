package com.example.model

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.fridge_list.MainActivity
import java.util.*

class id : AppCompatActivity() {

    companion object {

        var idUser: String = "-1"

        fun receiveId(ActivityMain: Activity) {

            val sharedPreferences: SharedPreferences =
                ActivityMain.getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
            this.idUser = sharedPreferences.getString("identifiant", "-1").toString()

            //Vérification de l'existance de l'id -> Si non existant, on le crée
            if (this.idUser == "-1") {
                this.idUser = createId()
                val editor = sharedPreferences.edit()
                editor.putString("identifiant", this.idUser)
                editor.apply()
            }
        }

        private fun createId(): String {
            return UUID.randomUUID().toString() //Créaction id
        }

        fun getId(): String {
            return this.idUser
        }
    }
}