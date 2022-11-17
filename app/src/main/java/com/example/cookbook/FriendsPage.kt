package com.example.cookbook

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.cookbook.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

const val TAG = "FIRESTORE"
class FriendsPage: AppCompatActivity() {

    private var binding : ActivitySignInBinding? = null
    private lateinit var auth: FirebaseAuth
    val firestoreDb = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_friends_page)

        auth = Firebase.auth

        val button = findViewById<Button>(R.id.retrieve)
        val dataView = findViewById<TextView>(R.id.dataView)

        val ref = firestoreDb.collection("Users")
        ref.get()
            .addOnSuccessListener{
                val result: StringBuffer = StringBuffer()
                for(document in it) {
                    if(document.id == auth.currentUser?.uid){
                        result.append(document.data.getValue("name"))
                        Log.d(TAG, "${document.id} => ${document.data}")
                    }
                }
                dataView.text = result
                dataView.visibility = View.VISIBLE
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }

//        button.setOnClickListener{
//            val ref = firestoreDb.collection("Users")
//            ref.get()
//                .addOnSuccessListener{
//                    val result: StringBuffer = StringBuffer()
//                    for(document in it) {
//                        result.append(document.data.getValue("firstName")).append(" ")
//                            .append(document.data.getValue("lastName"))
//                        Log.d(TAG, "${document.id} => ${document.data}")
//                    }
//                    dataView.text = result
//                    dataView.visibility = View.VISIBLE
//                }
//                .addOnFailureListener { exception ->
//                    Log.w(TAG, "Error getting documents: ", exception)
//                }
//        }
    }
}