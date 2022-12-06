package com.example.cookbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class RecipeAddForm extends AppCompatActivity {

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseFirestore firestoreDb = FirebaseFirestore.getInstance();
    private EditText nameInput;
    private EditText ingredientsInput;
    private EditText instructionInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_add_form);

        nameInput = findViewById(R.id.nameInput);
        ingredientsInput = findViewById(R.id.ingredientsInput);
        instructionInput = findViewById(R.id.instructionsInput);
    }

    public void add(View view){
        Map<String,Object> data = new HashMap<>();
        data.put("ingredients",ingredientsInput.getText().toString());
        data.put("instructions", instructionInput.getText().toString());

        firestoreDb.collection("Users").document(auth.getCurrentUser().getUid())
                .update("recipes", FieldValue.arrayUnion(nameInput.getText().toString()));

        firestoreDb.collection("Recipes").document(nameInput.getText().toString())
                .set(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error writing document", e);
                    }
                });
        Intent intent = new Intent(RecipeAddForm.this, MainActivity.class);
        startActivity(intent);
    }
}