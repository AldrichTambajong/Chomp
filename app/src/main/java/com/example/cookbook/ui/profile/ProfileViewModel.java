package com.example.cookbook.ui.profile;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.ktx.Firebase;

import java.util.List;

public class ProfileViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private final MutableLiveData<String> numFriends;
    private final MutableLiveData<String> numCookbooks;
    private StringBuffer sb = new StringBuffer();
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseFirestore firestoreDb = FirebaseFirestore.getInstance();


    public ProfileViewModel() {
        mText = new MutableLiveData<>();
        numFriends = new MutableLiveData<>();
        numCookbooks = new MutableLiveData<>();

        CollectionReference ref = firestoreDb.collection("Users");
        ref.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()){
                                if(document.getId().equals(auth.getCurrentUser().getUid())){
                                    numFriends.setValue(Integer.toString(((List<String>)document.get("friends")).size()));
                                    numCookbooks.setValue(Integer.toString(((List<String>)document.get("cookbooks")).size()));
                                    mText.setValue(document.get("name").toString());
                                }
                            }
                        }
                    }
                });
    }

    public LiveData<String> getText() {
        return mText;
    }
    public LiveData<String> getNumFriends() {
        return numFriends;
    }
    public LiveData<String> getNumCookbooks() {
        return numCookbooks;
    }
}