package com.example.cookbook.ui.cookbooks;

import static android.os.Build.VERSION_CODES.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.cookbook.databinding.FragmentCookbooksBinding;
import com.example.cookbook.databinding.FragmentHomeBinding;
import com.example.cookbook.ui.cookbooks.CookbooksViewModel;
import com.example.cookbook.ui.home.HomeViewModel;

public class CookbooksFragment extends Fragment {

    private FragmentCookbooksBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CookbooksViewModel cookbooksViewModel =
                new ViewModelProvider(this).get(CookbooksViewModel.class);

        binding = FragmentCookbooksBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textCookbooks;
        cookbooksViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}