package com.example.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.graphics.Color;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.SwitchCompat;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.databinding.FragmentSecondBinding;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import android.util.Log;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SecondFragment extends Fragment {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private FragmentSecondBinding binding;
    private final OkHttpClient client = new OkHttpClient();

    private static final String BASE_URL = "https://api.metamug.com";
    //OkHttpClient client = new OkHttpClient();
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentSecondBinding.inflate(inflater, container, false);


        // View view = binding.getRoot();

        return binding.getRoot();

    }

    public static String makePostRequest(String stringUrl, String payload,
                                         Context context) throws IOException {
        URL url = new URL(stringUrl);
        HttpURLConnection uc = (HttpURLConnection) url.openConnection();
        String line;
        StringBuffer jsonString = new StringBuffer();

        uc.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        uc.setRequestMethod("POST");
        uc.setDoInput(true);
        uc.setInstanceFollowRedirects(false);
        uc.connect();
        OutputStreamWriter writer = new OutputStreamWriter(uc.getOutputStream(), "UTF-8");
        writer.write(payload);
        writer.close();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(uc.getInputStream()));
            while((line = br.readLine()) != null){
                jsonString.append(line);
            }
            br.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        uc.disconnect();
        return jsonString.toString();
    }



    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    //CheckBox checkBox = (CheckBox) getView().findViewById(R.id.checkBox);
        ((ImageView) getView().findViewById(R.id.main_image)).setImageBitmap(getArguments().getParcelable("photo"));

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

                // implement here
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

                StrictMode.setThreadPolicy(policy);

                // Get image
                Bitmap mainphoto = getArguments().getParcelable("photo");

                // parse to base 64
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                mainphoto.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);

                // Create a SimpleDateFormat object with the desired format
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                // Get the current date and time
                Date currentDate = new Date();
                String timename = dateFormat.format(currentDate);


                try {


                String url = "http://maddata-backend.herokuapp.com/add";

                    int id = 1;
                    String name = timename;
                    String file = encoded;



                    OkHttpClient client = new OkHttpClient();

                    RequestBody formBody = new FormBody.Builder()
                            .add("id", "1")
                            .add("name", timename)
                            .add("file", encoded)
                            .build();
                    Request request = new Request.Builder()
                            .url(url)
                            .post(formBody)
                            .build();

                    try (Response response = client.newCall(request).execute()) {
                        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                        System.out.println(response.body().string());
                    }



                } catch (Exception e) {
                    throw new RuntimeException(e);
                }





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