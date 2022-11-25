package com.example.cookbook

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
//import android.databinding.ViewDataBinding;
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.IdpResponse
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.example.cookbook.databinding.ActivitySignInBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding

    // Firebase instance variables
    private lateinit var auth: FirebaseAuth
    val firestoreDb = FirebaseFirestore.getInstance()

    // moves the code on to the next steps after the authentication
    private val signIn: ActivityResultLauncher<Intent> =
        registerForActivityResult(FirebaseAuthUIActivityResultContract(), this::onSignInResult)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // View Binding
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize FirebaseAuth
        auth = Firebase.auth
    }

    public override fun onStart() {
        super.onStart()
        //Kicks off the FirebaseUI sign in flow.
        // If there is no signed in user, launch FirebaseUI
        // Otherwise head to MainActivity
        if (Firebase.auth.currentUser == null) {
            // Sign in with FirebaseUI
            val signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setLogo(R.drawable.logo)
                .setTheme(R.style.LoginTheme)

                .setAvailableProviders(listOf(
                    AuthUI.IdpConfig.EmailBuilder().build(),
                    AuthUI.IdpConfig.GoogleBuilder().build(),
                ))
                .build()
            signIn.launch(signInIntent)
        } else {
            addToDb()

        }
    }

    private fun signIn() {
        // TODO: implement
    }
    //Method to handle the sign in result.
    //If the result of the sign in was successful, execution continues to MainActivity
    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        if (result.resultCode == RESULT_OK) {
            Log.d(TAG, "Sign in successful!")

            addToDb()
        } else {
            Toast.makeText(
                this,
                "There was an error signing in",
                Toast.LENGTH_LONG).show()

            val response = result.idpResponse
            if (response == null) {
                Log.w(TAG, "Sign in canceled")
            } else {
                Log.w(TAG, "Sign in error", response.error)
            }
        }
    }

    private fun goToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun addToDb(){
        val user = auth.currentUser

        if (user != null) {
            firestoreDb.collection("Users")
                .document(user.uid)
                .get()
                .addOnCompleteListener{task ->
                    if(task.isSuccessful){
                        if(task.result.exists()){
                            goToMainActivity()
                        }
                    }
                    else{
                        val data = hashMapOf(
                            "email" to user.email,
                            "name" to user.displayName,
                            "friends" to ArrayList<String>(),
                            "cookbooks" to ArrayList<String>(),
                            "recipes" to ArrayList<String>(),
                            "avatar" to ""
                        )

                        firestoreDb.collection("Users").document(user.uid)
                            .set(data)
                            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
                            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
                        goToMainActivity()
                    }
                }
        }
    }

    companion object {
        private const val TAG = "SignInActivity"
    }
}
