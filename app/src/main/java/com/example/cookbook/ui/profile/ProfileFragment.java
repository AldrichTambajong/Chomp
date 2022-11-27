package com.example.cookbook.ui.profile;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookbook.databinding.FragmentHomeBinding;
import com.example.cookbook.databinding.FragmentProfileBinding;
import com.example.cookbook.ui.home.HomeViewModel;
import com.example.cookbook.ui.profile.ProfileViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.io.File;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private ArrayList<String> list;
    private ArrayList<String> cookbookNames = new ArrayList<String>();
    private ArrayList<String> cookbookImgs = new ArrayList<String>();
    private RecyclerView rv;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseFirestore firestoreDb = FirebaseFirestore.getInstance();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProfileViewModel profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textProfile;
        final TextView numFriends = binding.numFriends;
        final TextView numCookbooks = binding.numCookbooks;
        TextView textView3 = binding.cookbookHeader;
        rv = binding.cookbooksRecycler;

        CollectionReference ref = firestoreDb.collection("Users");
        ref.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()){
                                if(document.getId().equals(auth.getCurrentUser().getUid())){
                                    list = (ArrayList<String>) document.get("cookbooks");

                                    firestoreDb.collection("Cookbooks").get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task2) {
                                                    if(task2.isSuccessful()){
                                                        for(QueryDocumentSnapshot doc2:task2.getResult()){
                                                            if(list.contains(doc2.getId())){
                                                                cookbookNames.add(doc2.get("name").toString());
                                                                cookbookImgs.add(doc2.get("photo").toString());
                                                            }
                                                        }
                                                        //init recycler
                                                        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
                                                        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
                                                        rv.setLayoutManager(llm);
//                                                        rv.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL,false));
                                                        rv.setAdapter(new RecyclerViewHolder(cookbookNames, cookbookImgs,getActivity()));
                                                    }
                                                }
                                            });
                                    Log.d("TAG",list.toString());
                                }
                            }
                        }
                    }
                });

        profileViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        profileViewModel.getNumFriends().observe(getViewLifecycleOwner(),numFriends::setText);
        profileViewModel.getNumCookbooks().observe(getViewLifecycleOwner(),numCookbooks::setText);

        numFriends.setVisibility(View.VISIBLE);
        numCookbooks.setVisibility(View.VISIBLE);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}