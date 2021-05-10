package com.google.firebase.referencecode.database.kotlin

//import com.google.firebase.referencecode.database.kotlin.models.Post
//import com.google.firebase.referencecode.database.models.User
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


abstract class ReadAndWriteSnippets {

    private val TAG = "ReadAndWriteSnippets"

    // [START declare_database_ref]
    private lateinit var database: DatabaseReference

    fun initializeDbRef() {
        // [START initialize_database_ref]
        database = Firebase.database.reference
    }

    // [START rtdb_write_new_user]
    fun write() {
        database.child("user").child("user1").setValue("mdr")
    }

}