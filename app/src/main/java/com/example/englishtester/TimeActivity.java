package com.example.englishtester;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.englishtester.adapter.CheckAnswerAdapter;
import com.example.englishtester.model.Question;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.englishtester.LoginActivity.urlGetDataB1;
import static com.example.englishtester.MainActivity.questionArrayList;
import static com.example.englishtester.MainActivity.TYPE_QUESTION;
import static com.example.englishtester.MainActivity.checkAnswerEl;

public class TimeActivity extends AppCompatActivity {


    Button btnStart, btnListQues, btnSubmit, btnScoce;
    TextView txtTimer;
    ViewPager viewPager;
    CountDownTimer countDownTimer;
    DataViewN viewPagerAdapter;
    private long timeSeconds = 150000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);

        setWidget();
        dialog_Notification();
        Intent intent = getIntent();
        TYPE_QUESTION = intent.getStringExtra("typeQuestion");
        if (questionArrayList.isEmpty()) {
            getDataB1(urlGetDataB1);
        }

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setOffscreenPageLimit(1);
                viewPagerAdapter = new DataViewN(getSupportFragmentManager());
                viewPager.setOffscreenPageLimit(1);
                viewPager.setAdapter(viewPagerAdapter);
                btnStart.setVisibility(View.GONE);
                btnListQues.setVisibility(View.VISIBLE);
                btnSubmit.setVisibility(View.VISIBLE);
                startTimer();

            }
        });
        btnScoce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent1 = new Intent(TimeActivity.this, ShowScoreActivity.class);
                intent1.putExtra("arr_question", questionArrayList);
                startActivity(intent1);
                finish();
            }
        });
        btnListQues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogListQuestion();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer();
            }
        });

    }

    void Result() {
        if (viewPager.getCurrentItem() >= 5)
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 5);
        else if (viewPager.getCurrentItem() < 5)
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 5);
        btnSubmit.setVisibility(View.GONE);
        btnScoce.setVisibility(View.VISIBLE);

    }

    void dialog_Notification() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(TimeActivity.this);
        dialog.setTitle("Notification");
        dialog.setMessage("This test has 10 question, the test time is 15 minutes.");

        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialog.show();
    }

    public static class DataViewN extends FragmentPagerAdapter {
        public DataViewN(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return FragmentQuestion.create(position, checkAnswerEl);
        }

        @Override
        public int getCount() {
            return questionArrayList.size();
        }
    }

    void checkAnswer() {
        final Dialog dialog = new Dialog(this);

        dialog.setContentView(R.layout.check_answer_dialog);

        CheckAnswerAdapter answerAdapter = new CheckAnswerAdapter(questionArrayList, this);
        GridView gvlsQuestion = (GridView) dialog.findViewById(R.id.gvlsQuestion);
        gvlsQuestion.setAdapter(answerAdapter);
        gvlsQuestion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                viewPager.setCurrentItem(i);
                dialog.dismiss();
            }
        });
        Button btnCancel, btnSubmitXN;
        btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
        btnSubmitXN = (Button) dialog.findViewById(R.id.btnSubmitXN);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnSubmitXN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswerEl = 1;
                Result();
                dialog.dismiss();
                countDownTimer.cancel();
            }
        });
        dialog.show();
    }


    void startTimer() {
        countDownTimer = new CountDownTimer(timeSeconds, 1000) {
            @Override
            public void onTick(long l) {
                timeSeconds = l;
                updateTimer();
            }

            @Override
            public void onFinish() {
                checkAnswerEl = 1;
                Result();
            }
        }.start();
    }

    private void updateTimer() {
        int minutes = (int) timeSeconds / 60000;
        int seconds = (int) timeSeconds % 60000 / 1000;

        String timeLeftText;
        timeLeftText = "" + minutes + ":";
        if (seconds < 10) {
            timeLeftText += "0";
        }
        timeLeftText += seconds;
        txtTimer.setText(timeLeftText);
    }

    void setWidget() {
        btnStart = findViewById(R.id.btn_start);
        viewPager = findViewById(R.id.view_pager);
        btnListQues = findViewById(R.id.btn_list_ques);
        btnSubmit = findViewById(R.id.btn_submit);
        txtTimer = findViewById(R.id.tvTimer);
        btnScoce = findViewById(R.id.btn_scoce);
    }

    void getDataB1(String url) {
        RequestQueue requestQueue = Volley.newRequestQueue(TimeActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                questionArrayList.add(new Question(
                                        jsonObject.getString("STT"),
                                        jsonObject.getString("cauhoi"),
                                        jsonObject.getString("dapanA"),
                                        jsonObject.getString("dapanB"),
                                        jsonObject.getString("dapanC"),
                                        jsonObject.getString("dapanD"),
                                        jsonObject.getString("ketqua"),
                                        jsonObject.getString("theloai"),
                                        ""
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
                        Toast.makeText(TimeActivity.this, "Error, please try again!", Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("type", TYPE_QUESTION);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    void dialogListQuestion() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_list_question);
        Button btnQues1 = (Button) dialog.findViewById(R.id.btn_1);
        setDialogListQuestionButton(btnQues1, 0, dialog);
        Button btnQues2 = (Button) dialog.findViewById(R.id.btn_2);
        setDialogListQuestionButton(btnQues2, 1, dialog);
        Button btnQues3 = (Button) dialog.findViewById(R.id.btn_3);
        setDialogListQuestionButton(btnQues3, 2, dialog);
        Button btnQues4 = (Button) dialog.findViewById(R.id.btn_4);
        setDialogListQuestionButton(btnQues4, 3, dialog);
        Button btnQues5 = (Button) dialog.findViewById(R.id.btn_5);
        setDialogListQuestionButton(btnQues5, 4, dialog);
        Button btnQues6 = (Button) dialog.findViewById(R.id.btn_6);
        setDialogListQuestionButton(btnQues6, 5, dialog);
        Button btnQues7 = (Button) dialog.findViewById(R.id.btn_7);
        setDialogListQuestionButton(btnQues7, 6, dialog);
        Button btnQues8 = (Button) dialog.findViewById(R.id.btn_8);
        setDialogListQuestionButton(btnQues8, 7, dialog);
        Button btnQues9 = (Button) dialog.findViewById(R.id.btn_9);
        setDialogListQuestionButton(btnQues9, 8, dialog);
        Button btnQues10 = (Button) dialog.findViewById(R.id.btn_10);
        setDialogListQuestionButton(btnQues10, 9, dialog);
        dialog.show();
    }

    void setDialogListQuestionButton(View view, final int pos, final Dialog dia) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setOffscreenPageLimit(1);
                viewPagerAdapter = new DataViewN(getSupportFragmentManager());
                viewPager.setCurrentItem(pos);

                btnStart.setVisibility(View.GONE);
                dia.cancel();
            }
        });
    }

}