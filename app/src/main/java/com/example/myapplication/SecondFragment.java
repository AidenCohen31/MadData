package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.graphics.Color;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.SwitchCompat;
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


        // View view = binding.getRoot();

        return binding.getRoot();

    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    //CheckBox checkBox = (CheckBox) getView().findViewById(R.id.checkBox);


        SwitchCompat checkBox1 = (SwitchCompat) view.findViewById(R.id.checkBox1);

        binding.checkBox1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View buttonView) {
                boolean isChecked = ((SwitchCompat) buttonView).isChecked();
                TextView tvSwitchYes = (TextView) view.findViewById(R.id.tvSwitchYes);
                TextView tvSwitchNo = (TextView) view.findViewById(R.id.tvSwitchNo);


                if (isChecked) {
                    // Run extra code when checkbox is checked
                    tvSwitchYes.setTextColor(Color.parseColor("#4282DC"));
                    tvSwitchNo.setTextColor(Color.rgb(255,255,255));

                    Toast.makeText(getContext(), "Updates enabled", Toast.LENGTH_SHORT).show();
                } else {
                    tvSwitchYes.setTextColor(Color.rgb(255,255,255));
                    tvSwitchNo.setTextColor(Color.parseColor("#4282DC"));
                    // Run code when checkbox is unchecked
                    Toast.makeText(getContext(), "Updates disabled", Toast.LENGTH_SHORT).show();
                }
            }});

        binding.button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });




    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}