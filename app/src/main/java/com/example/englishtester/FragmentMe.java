package com.example.englishtester;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.englishtester.model.Account;
import com.example.englishtester.model.Result;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.englishtester.LoginActivity.accountArrayList;
import static com.example.englishtester.LoginActivity.urlData;

public class FragmentMe extends Fragment {

    TextView tvName, tvEmail, tvLevel;
    ImageView imageView;
    RatingBar rate_A0, rate_A1, rate_A2, rate_B1, rate_B2, rate_C;
    View view;
    public ArrayList<Result> resultArrayList;
    public String urlgetData = urlData + "getscore.php";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_me_fragment, container, false);
        setWidget();
        resultArrayList = new ArrayList<>();
        if (!accountArrayList.isEmpty()) {
            Account account = accountArrayList.get(0);
            tvName.setText(account.getUserName());
            tvEmail.setText(account.getEmail());
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!resultArrayList.isEmpty()) {
                    for (int i = 0; i < resultArrayList.size(); i++) {
                        setRate(resultArrayList.get(i));
                    }
                }
            }
        });

        resultArrayList.clear();
        getDataScore(urlgetData);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                imageView.performClick();
            }
        }, 1000);

        return view;
    }


    void getDataScore(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                resultArrayList.add(new Result(
                                        jsonObject.getString("STT"),
                                        jsonObject.getString("nametype"),
                                        jsonObject.getString("score")
                                ));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Error, please try again!", Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_account", accountArrayList.get(0).getID());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    @SuppressLint("SetTextI18n")
    void setRate(Result result) {
        String type = result.getType();
        int score = Integer.parseInt(result.getScore());
        if (type.equals("T")) {
            if (score <= 0) {
                tvLevel.setText("Failed");
            } else {
                if (score <= 20) {
                    tvLevel.setText("A0");
                } else {
                    if (score <= 40) {
                        tvLevel.setText("A1");
                    } else {
                        if (score <= 60) {
                            tvLevel.setText("A2");
                        } else {
                            if (score <= 80) {
                                tvLevel.setText("B1");
                            } else {
                                if (score <= 90) {
                                    tvLevel.setText("B2");
                                } else {
                                    if (score <= 100) {
                                        tvLevel.setText("C");
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (type.equals("A0")) {
            rate_A0.setRating(setScore(score));
        }
        if (type.equals("A1")) {
            rate_A1.setRating(setScore(score));
        }
        if (type.equals("A2")) {
            rate_A2.setRating(setScore(score));
        }
        if (type.equals("B1")) {
            rate_B1.setRating(setScore(score));
        }
        if (type.equals("B2")) {
            rate_B2.setRating(setScore(score));
        }
        if (type.equals("C")) {
            rate_C.setRating(setScore(score));
        }
    }

    public float setScore(int score) {
        float rate = 0;
        if (score <= 20) {
            rate = 1;
        } else {
            if (score <= 40) {
                rate = 2;
            } else {
                if (score <= 60) {
                    rate = 3;
                } else {
                    if (score <= 80) {
                        rate = 4;
                    } else {
                        if (score <= 90) {
                            rate = (float) 4.5;
                        } else {
                            if (score <= 100) {
                                rate = 5;
                            }
                        }
                    }
                }
            }
        }
        return rate;
    }

    void setWidget() {
        tvName = view.findViewById(R.id.tv_name_user);
        tvEmail = view.findViewById(R.id.tv_email_user);
        rate_A0 = view.findViewById(R.id.rate_a0);
        rate_A1 = view.findViewById(R.id.rate_a1);
        rate_A2 = view.findViewById(R.id.rate_a2);
        rate_B1 = view.findViewById(R.id.rate_b1);
        rate_B2 = view.findViewById(R.id.rate_b2);
        rate_C = view.findViewById(R.id.rate_c);
        tvLevel = view.findViewById(R.id.tv_level);
        imageView = view.findViewById(R.id.image);
    }
}