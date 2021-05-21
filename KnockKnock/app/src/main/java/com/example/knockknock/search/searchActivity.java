package com.example.knockknock.search;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.knockknock.R;
import com.example.knockknock.Restaurant;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class searchActivity extends AppCompatActivity {


    private ListView listView = null;
    private searchAdapter adapter;
    private List<Restaurant> restaurantList;
    private EditText editSearch ;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        Toast.makeText(getApplicationContext(), "searchActivity 실행",Toast.LENGTH_SHORT).show();

        editSearch = (EditText)findViewById(R.id.search);
        listView = (ListView)findViewById(R.id.restaurantlist);

        restaurantList = new ArrayList<Restaurant>();


        adapter = new searchAdapter(getApplicationContext(), restaurantList);
        listView.setAdapter(adapter);

        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = editSearch.getText().toString();
                //search(text);
            }
        });
        EditText editTextFilter = (EditText)findViewById(R.id.editTextFilter);
        editTextFilter.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable edit) {
                String filterText = edit.toString();
                if(filterText.length() > 0){
                    listView.setFilterText(filterText);
                }else{
                    listView.clearTextFilter();
                }

            }
        });
        try{

            JSONObject jsonObject = new JSONObject(intent.getStringExtra("restaurantList"));


            JSONArray jsonArray = jsonObject.getJSONArray("response");
            int count = 0;

            String res_name;

            //JSON 배열 길이만큼 반복문을 실행
            while(count < jsonArray.length()){
                //count는 배열의 인덱스를 의미
                JSONObject object = jsonArray.getJSONObject(count);

                res_name = object.getString("res_name");
            }


        }catch(Exception e){
            e.printStackTrace();
        }







    }
}
