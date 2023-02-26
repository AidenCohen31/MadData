package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.databinding.FragmentFirstBinding;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.xml.transform.Result;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FirstFragment extends Fragment {
    private static final int REQUEST_CODE = 22;

    private OkHttpClient client = new OkHttpClient();


    private FragmentFirstBinding binding;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        GridLayout fp = binding.filePreview;


        String url = "http://maddata-backend.herokuapp.com/files";

        String responseStr = "";
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            Response response = client.newCall(request).execute();
            responseStr = response.body().string();
            JSONObject jsonObj = new JSONObject(responseStr);
            JSONArray arr = jsonObj.getJSONArray("files");
            for(int i = 0; i < arr.length(); i++){
                System.out.println(arr);
                JSONObject c = arr.getJSONObject(i);
                String name = c.getString("name");
                String data = c.getString("data");
                FragmentContainerView fcv = new FragmentContainerView(getActivity());
                fcv.setId(i + 121);
                fp.addView(fcv);
                ThirdFragment tf = new ThirdFragment();
                Bundle bundle = new Bundle();
                bundle.putString("name" , name);
                bundle.putString("data" , data.substring(2,data.length() - 1));
                tf.setArguments(bundle);
                fragmentTransaction.add(fp.getChildAt(i).getId(), tf, "fragment" + i);
            }
            fragmentTransaction.commit();

        } catch(Exception e){
            System.out.println(e.getStackTrace());
            throw new RuntimeException(e);
        }

        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                startActivityForResult(cameraIntent,REQUEST_CODE);



            }
        });


    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getActivity();
        if(requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK){
            Bitmap photo = (Bitmap) data.getExtras().get("data");

            Bundle bundle = new Bundle();
            bundle.putParcelable("photo", photo);

            NavHostFragment.findNavController(FirstFragment.this)
                    .navigate(R.id.action_FirstFragment_to_SecondFragment, bundle);

        }
        else{
            Toast.makeText(getActivity(), "cancelled", Toast.LENGTH_SHORT).show();
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}