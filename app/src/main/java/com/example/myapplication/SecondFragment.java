package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
<<<<<<< HEAD
=======
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
>>>>>>> 2c81c6611e6c2058cae2d8c84c122fe5778a8a1e

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.databinding.FragmentSecondBinding;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
<<<<<<< HEAD
=======

        // View view = binding.getRoot();

>>>>>>> 2c81c6611e6c2058cae2d8c84c122fe5778a8a1e
        return binding.getRoot();

    }

<<<<<<< HEAD
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
=======
    //CheckBox checkBox = (CheckBox) getView().findViewById(R.id.checkBox);

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CheckBox checkBox1 = (CheckBox) view.findViewById(R.id.checkBox1);

        checkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Run extra code when checkbox is checked
                    Toast.makeText(getContext(), "Updates enabled", Toast.LENGTH_SHORT).show();
                } else {
                    // Run code when checkbox is unchecked
                    Toast.makeText(getContext(), "Updates disabled", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.button1.setOnClickListener(new View.OnClickListener() {
>>>>>>> 2c81c6611e6c2058cae2d8c84c122fe5778a8a1e
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });
<<<<<<< HEAD
=======


    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();


>>>>>>> 2c81c6611e6c2058cae2d8c84c122fe5778a8a1e
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}