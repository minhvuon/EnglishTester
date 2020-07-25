package com.example.englishtester;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.englishtester.model.Question;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.englishtester.LoginActivity.accountArrayList;
import static com.example.englishtester.LoginActivity.urlInsertBXH;
import static com.example.englishtester.MainActivity.TYPE_QUESTION;

import java.util.Map;

public class ShowScoreActivity extends AppCompatActivity {
    ArrayList<Question> arrayList;
    int numNoAns, numTrue, numFalse, totalScore = 0;
    TextView tvTrue, tvTotalScore, tvResult, tvNoAns, tvFailse;
    Button btnAglain, btnShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_score);

        Intent intent = getIntent();
        arrayList = (ArrayList<Question>) intent.getExtras().getSerializable("arr_question");

        setWidget();
        checkAns();

        btnAglain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveSever(urlInsertBXH);
                finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }

    @SuppressLint("SetTextI18n")
    public void checkAns() {
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).getYouAnswer().equals("")) {
                numNoAns++;
            } else if (arrayList.get(i).getRightAnswer().equals(arrayList.get(i).getYouAnswer())) {
                numTrue++;
            } else {
                numFalse++;
            }
        }

        tvTrue.setText("" + numTrue);
        totalScore = numTrue * 10;
        tvTotalScore.setText("" + totalScore);
        tvNoAns.setText("" + numNoAns);
        tvFailse.setText("" + numFalse);
        if (TYPE_QUESTION.equals("T")){
            if (totalScore <= 0){
                tvResult.setText("FAILED");
                tvResult.setTextColor(Color.RED);
            }else {
                if (totalScore <= 20){
                    tvResult.setText("A0");
                    tvResult.setTextColor(Color.BLUE);
                }else {
                    if (totalScore <= 40){
                        tvResult.setText("A1");
                        tvResult.setTextColor(Color.BLUE);
                    }else {
                        if (totalScore <= 60){
                            tvResult.setText("A2");
                            tvResult.setTextColor(Color.BLUE);
                        }else {
                            if (totalScore <= 80){
                                tvResult.setText("B1");
                                tvResult.setTextColor(Color.BLUE);
                            }else {
                                if (totalScore <= 90){
                                    tvResult.setText("B2");
                                    tvResult.setTextColor(Color.BLUE);
                                }else {
                                    if (totalScore <= 100){
                                        tvResult.setText("C");
                                        tvResult.setTextColor(Color.BLUE);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }else{
            if (totalScore >= 70) {
                tvResult.setTextColor(Color.BLUE);
                tvResult.setText("PASSED");
            } else {
                tvResult.setText("FAILED");
                tvResult.setTextColor(Color.RED);
            }
        }

    }

    public void setWidget() {
        tvTrue = (TextView) findViewById(R.id.tvTrue);
        tvTotalScore = (TextView) findViewById(R.id.tvTotalPoint);
        tvResult = (TextView) findViewById(R.id.tvPassNo);
        btnAglain = (Button) findViewById(R.id.btnAgain);
        btnShare = (Button) findViewById(R.id.btnShare);
        tvNoAns = (TextView) findViewById(R.id.tvNoAns);
        tvFailse = (TextView) findViewById(R.id.tvFailse);
    }

    public void SaveSever(String url) {
        String id_type = "7";
        if (TYPE_QUESTION.equals("A0")) {
            id_type = "1";
        }
        if (TYPE_QUESTION.equals("A1")) {
            id_type = "2";
        }
        if (TYPE_QUESTION.equals("A2")) {
            id_type = "3";
        }
        if (TYPE_QUESTION.equals("B1")) {
            id_type = "4";
        }
        if (TYPE_QUESTION.equals("B2")) {
            id_type = "5";
        }
        if (TYPE_QUESTION.equals("C")) {
            id_type = "6";
        }
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        final String finalId_type = id_type;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("oke")) {
                    Toast.makeText(ShowScoreActivity.this, "Saved success", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ShowScoreActivity.this, response, Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ShowScoreActivity.this, "Error, please try again!", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("idtype", finalId_type);
                params.put("score", String.valueOf(totalScore).trim());
                params.put("idacc", accountArrayList.get(0).getID());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}