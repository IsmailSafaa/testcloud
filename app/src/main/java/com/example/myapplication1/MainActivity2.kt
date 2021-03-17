package com.example.myapplication1
import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main2.*

class MainActivity2 : AppCompatActivity() {
    lateinit var db: FirebaseFirestore
    lateinit var progressDialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

         db = FirebaseFirestore.getInstance()
        progressDialog= ProgressDialog(this)
        progressDialog.setMessage("Loading ....")
        progressDialog.setCancelable(false)
        progressDialog.show()
        getUsers()
    }


    private fun getUsers(){
        val user=mutableListOf<model>()
        db.collection("User")
            .get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot){

                    user.add(
                        model(document.getString("id"),document.getString("name"),document.getString("number"),document.getString("address")
                        )
                    )

                    rvUsers.layoutManager = LinearLayoutManager(this,
                        LinearLayoutManager.VERTICAL,false)
                    rvUsers.setHasFixedSize(true)
                    val restAdapter = Adapter(this,user)
                    rvUsers.adapter=restAdapter
                    progressDialog.dismiss()
                }

            }
            .addOnFailureListener { exception ->
                Log.e("saf", exception.message!!)
                progressDialog.dismiss()
            }
    }
}