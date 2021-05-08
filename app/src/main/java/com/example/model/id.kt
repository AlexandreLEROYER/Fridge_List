package com.example.model

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.fridge_list.MainActivity
import java.util.*

const val PREFS_FILENAME = "com.example.fridge_list.prefs"

class id : AppCompatActivity() {

    fun receiveId(ActivityMain : Activity) : String? {

        val sharedPreferences : SharedPreferences = ActivityMain.getSharedPreferences("PREFERENCE", Context.MODE_PRIVATE)
        var idUser : String = sharedPreferences.getString("identifiant", "-1").toString()

        if (idUser == "-1") {
            idUser = createId()
            val editor = sharedPreferences.edit()
            editor.putString("identifiant", idUser)
            editor.apply()
            Log.d("idUser", "On vient de le crée")
        }
        else {
            Log.d("idUser", "On le recup")
        }
        Log.d("idUser", idUser)
        return idUser
    }

    private fun createId(): String {
        val ourUUID = UUID.randomUUID().toString()
        Log.d("UUID", ourUUID)
        return ourUUID
    }


}