package com.example.cookbook

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.cookbook.databinding.ActivitySignInBinding
import com.google.firebase.firestore.FirebaseFirestore

const val TAG = "FIRESTORE"
class FriendsPage: AppCompatActivity() {

    private var binding : ActivitySignInBinding? = null
    val firestoreDb = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_friends_page)

        val button = findViewById<Button>(R.id.retrieve)
        val dataView = findViewById<TextView>(R.id.dataView)

        button.setOnClickListener{
            val ref = firestoreDb.collection("Users")
            ref.get()
                .addOnSuccessListener{
                    val result: StringBuffer = StringBuffer()
                    for(document in it) {
                        result.append(document.data.getValue("firstName")).append(" ")
                            .append(document.data.getValue("lastName"))
                        Log.d(TAG, "${document.id} => ${document.data}")
                    }
                    dataView.text = result
                    dataView.visibility = View.VISIBLE
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents: ", exception)
                }
        }
    }
}