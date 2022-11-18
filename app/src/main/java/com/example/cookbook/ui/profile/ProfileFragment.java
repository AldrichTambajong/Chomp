package com.example.cookbook.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.cookbook.databinding.FragmentHomeBinding;
import com.example.cookbook.databinding.FragmentProfileBinding;
import com.example.cookbook.ui.home.HomeViewModel;
import com.example.cookbook.ui.profile.ProfileViewModel;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProfileViewModel profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textProfile;
        final TextView numFriends = binding.numFriends;
        final TextView numCookbooks = binding.numCookbooks;

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