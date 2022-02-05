package com.example.y3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.AbsListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    RecyclerView recyclerView;

    ArrayList<String> personName = new ArrayList<>();
    ArrayList<String> emailids = new ArrayList<>();
    ArrayList<String> mobileNumbers = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        //// recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        try {
            JSONObject obj = new JSONObject(loadJSONfromAssets());
            JSONArray userArray = obj.getJSONArray("users");
            for (int i=0; i<userArray.length(); i++){
                JSONObject userDetail = userArray.getJSONObject(i);
                personName.add(userDetail.getString("name"));
                emailids.add(userDetail.getString("email"));
                JSONObject contact = userDetail.getJSONObject("contact");
                mobileNumbers.add(contact.getString("mobile"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        CustomAdapter customAdapter = new CustomAdapter( personName, emailids, mobileNumbers, MainActivity.this);
        recyclerView.setAdapter(customAdapter);

    }

    private String loadJSONfromAssets() {
        String json = null;
        try {
            InputStream is = getAssets().open("users_list.txt");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return json;
    }
}