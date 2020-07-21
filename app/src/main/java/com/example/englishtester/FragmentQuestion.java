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

import com.example.englishtester.model.Answer;
import com.example.englishtester.model.Question;

import static com.example.englishtester.MainActivity.answerArrayList;
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
        Toast.makeText(getContext(), Integer.toString(checkAnswerEl), Toast.LENGTH_SHORT).show();

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
                switch (i) {
                    case R.id.radA:
                        answerArrayList.set(pageNumber, new Answer(Integer.toString(pageNumber + 1), "A"));
                        break;
                    case R.id.radB:
                        answerArrayList.set(pageNumber, new Answer(Integer.toString(pageNumber + 1), "B"));
                        break;
                    case R.id.radC:
                        answerArrayList.set(pageNumber, new Answer(Integer.toString(pageNumber + 1), "C"));
                        break;
                    case R.id.radD:
                        answerArrayList.set(pageNumber, new Answer(Integer.toString(pageNumber + 1), "D"));
                        break;
                }
            }
        });
    }

    void ficColor(){
        if (checkAnswerEl !=0) {
            rabA.setClickable(false);
            rabB.setClickable(false);
            rabC.setClickable(false);
            rabD.setClickable(false);
            //Toast.makeText(getActivity(), questionArrayList.get(pageNumber).toString(), Toast.LENGTH_SHORT).show();
            getCheckAns(questionArrayList.get(pageNumber).getRightAnswer());
        }
    }

    private void getCheckAns(String answer) {
        if (answer.equals("A")) {
            rabA.setBackgroundColor(Color.BLUE);
        } else if (answer.equals("B")) {
            rabB.setBackgroundColor(Color.BLUE);
        } else if (answer.equals("C")) {
            rabC.setBackgroundColor(Color.BLUE);
        } else {
            rabD.setBackgroundColor(Color.BLUE);
        }
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
            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();

        }
        txtNumberPage.setText(Integer.toString(pageNumber + 1));
    }

}