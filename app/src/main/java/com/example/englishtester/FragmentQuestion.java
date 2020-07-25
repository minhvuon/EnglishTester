package com.example.englishtester;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.example.englishtester.model.Question;

import static com.example.englishtester.MainActivity.checkAnswerEl;
import static com.example.englishtester.MainActivity.questionArrayList;

public class FragmentQuestion extends Fragment {

    public static final String KEY_PAGE = "pageNumber";
    public static final String KEY_CHECKANSWER = "checkAnswer";
    View view;
    TextView txtNumberPage, txtQuestion;
    RadioButton rabA, rabB, rabC, rabD;
    RadioGroup radioGroup;
    int pageNumber, checkAnswer;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_fragment_question, container, false);

        assert getArguments() != null;
        pageNumber = getArguments().getInt(KEY_PAGE);
        checkAnswer = getArguments().getInt(KEY_CHECKANSWER);
        setWidget();

        setData();
        checkQuestion();
        ficColor();

        return view;
    }


    public static FragmentQuestion create(int numPage,int checkAns)
    {
        FragmentQuestion fragmentQuestion = new FragmentQuestion();
        Bundle bundle = new Bundle();
        bundle.putInt("pageNumber", numPage);
        bundle.putInt("checkAnswer",checkAns);
        fragmentQuestion.setArguments(bundle);
        return fragmentQuestion;
    }


    private void checkQuestion() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                getItemm(pageNumber).choiceID = i;
                getItemm(pageNumber).setYouAnswer(getChoiceFromID(i));
            }
        });
    }

    void ficColor(){
        if (checkAnswerEl !=0) {
            rabA.setClickable(false);
            rabB.setClickable(false);
            rabC.setClickable(false);
            rabD.setClickable(false);
            getCheckAns(questionArrayList.get(pageNumber).getRightAnswer(), questionArrayList.get(pageNumber).getYouAnswer());
        }
    }

    private void getCheckAns(String answer, String your_answer) {
        View view = checkYourAnswer(your_answer);
        if (answer.equals("A")) {
            if (!your_answer.equals("A") && !your_answer.equals("")){
                view.setBackgroundColor(Color.RED);
            }
            rabA.setBackgroundColor(Color.GREEN);
        } else if (answer.equals("B")) {
            if (!your_answer.equals("B") && !your_answer.equals("")){
                view.setBackgroundColor(Color.RED);
            }
            rabB.setBackgroundColor(Color.GREEN);
        } else if (answer.equals("C")) {
            if (!your_answer.equals("C") && !your_answer.equals("")){
                view.setBackgroundColor(Color.RED);
            }
            rabC.setBackgroundColor(Color.GREEN);
        } else {
            if (!your_answer.equals("D") && !your_answer.equals("")){
                view.setBackgroundColor(Color.RED);
            }
            rabD.setBackgroundColor(Color.GREEN);
        }
    }

    private View checkYourAnswer(String your_answer){
        if (your_answer.equals("A")) {
            return rabA;
        }else {
            if (your_answer.equals("B")){
                return rabB;
            }
            else {
                if (your_answer.equals("C")){
                    return rabC;
                }
            }
        }
        return rabD;
    }

    public Question getItemm(int position) {
        return questionArrayList.get(position);
    }

    private String getChoiceFromID(int ID) {
        if (ID == R.id.radA) {
            return "A";
        } else if (ID == R.id.radB) {
            return "B";
        } else if (ID == R.id.radC) {
            return "C";
        } else if (ID == R.id.radD) {
            return "D";
        } else {
            return "";
        }
    }

    void setWidget() {
        txtNumberPage = view.findViewById(R.id.tvNum);
        txtQuestion = view.findViewById(R.id.tvQuestion);
        radioGroup = view.findViewById(R.id.radGroup);
        rabA = view.findViewById(R.id.radA);
        rabB = view.findViewById(R.id.radB);
        rabC = view.findViewById(R.id.radC);
        rabD = view.findViewById(R.id.radD);
    }

    @SuppressLint("SetTextI18n")
    void setData() {
        try {
            final Question question = questionArrayList.get(pageNumber);
            txtQuestion.setText(question.getQuestion());
            rabA.setText(question.getAnswerA());
            rabB.setText(question.getAnswerB());
            rabC.setText(question.getAnswerC());
            rabD.setText(question.getAnswerD());


        } catch (Exception e) {
            Toast.makeText(getActivity(), "Error, please try again!", Toast.LENGTH_SHORT).show();

        }
        txtNumberPage.setText(Integer.toString(pageNumber + 1));
    }

}