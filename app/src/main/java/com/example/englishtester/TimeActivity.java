package com.example.englishtester;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.englishtester.adapter.ViewPagerAdapter;
import com.example.englishtester.model.Question;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.englishtester.LoginActivity.urlGetDataB1;
import static com.example.englishtester.MainActivity.questionArrayList;

public class TimeActivity extends AppCompatActivity {
    Button btnStart;
    Toolbar toolbar;
    FragmentManager fragmentManager;
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);

        btnStart = findViewById(R.id.btn_start);
        viewPager = findViewById(R.id.view_pager);

        if (questionArrayList.isEmpty()){
            getDataB1(urlGetDataB1);
        }
//        Toast.makeText(TimeActivity.this, questionArrayList.size(), Toast.LENGTH_SHORT).show();


//        Intent intent = getIntent();
//        TYPE_QUESTION = intent.getStringExtra("typeQuestion");
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setOffscreenPageLimit(1);
                viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
                viewPager.setAdapter(viewPagerAdapter);
                viewPager.setCurrentItem(0);
                btnStart.setVisibility(View.INVISIBLE);
            }
        });


    }
    void getDataB1(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(TimeActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("cauhoi");
                            for (int i = 0; i < jsonArray.length(); i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                questionArrayList.add(new Question(
                                        jsonObject.getString("STT"),
                                        jsonObject.getString("cauhoi"),
                                        jsonObject.getString("theloai"),
                                        jsonObject.getString("dapanA"),
                                        jsonObject.getString("dapanB"),
                                        jsonObject.getString("dapanC"),
                                        jsonObject.getString("dapanD"),
                                        jsonObject.getString("ketqua")
                                ));
                                //Toast.makeText(TimeActivity.this, questionArrayList.toString(), Toast.LENGTH_SHORT).show();

                            }


                        }catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(TimeActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }

}