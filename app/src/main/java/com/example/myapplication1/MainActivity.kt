package com.example.myapplication1

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    lateinit var db: FirebaseFirestore
    lateinit var progressDialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

      db = FirebaseFirestore.getInstance()




        save.setOnClickListener {
            var name = Name.text.toString()
            var number = Number.text.toString()
            var address = Address.text.toString()
            var id = java.util.UUID.randomUUID().toString()
            addUserToDb(id,name, number, address)
            Name.setText("")
            Number.setText("")
            Address.setText("")

        }
        Icon.setOnClickListener {
            var i= Intent(this,
                MainActivity2::class.java)
            startActivity(i)
        }



    }

    fun addUserToDb(id:String,name:String,number:String,address:String){
        val user= hashMapOf("id" to id,"name" to name,"number" to number,"address" to address)
        db.collection("User")
            .add(user)
            .addOnSuccessListener {documentReference->
                Toast.makeText(applicationContext,"User added successfully", Toast.LENGTH_SHORT).show()

            }
            .addOnFailureListener { exception ->
                Toast.makeText(applicationContext,"User does not added", Toast.LENGTH_SHORT).show()

            }
    }
}