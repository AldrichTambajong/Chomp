package com.example.cookbook.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cookbook.databinding.FragmentHomeBinding;
import com.example.cookbook.databinding.FragmentProfileBinding;
import com.example.cookbook.ui.home.HomeViewModel;
import com.example.cookbook.ui.profile.ProfileViewModel;

import java.io.File;
import java.util.ArrayList;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private ArrayList<String> list;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProfileViewModel profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textProfile;
        final TextView numFriends = binding.numFriends;
        final TextView numCookbooks = binding.numCookbooks;
        final RecyclerView rv = binding.cookbooksRecycler;

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

    class cookbookListAdapter extends RecyclerView.Adapter<cookbookListAdapter.Holder>{

        @NonNull
        @Override
        public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public int getItemCount() {
            return 0;
        }

        @Override
        public void onBindViewHolder(@NonNull Holder holder, int position) {

        }

        class Holder extends RecyclerView.ViewHolder{

            public Holder(@NonNull View itemView) {
                super(itemView);
            }
        }

    }

}