package com.example.englishtester;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
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
import static com.example.englishtester.MainActivity.questionArrayList;

public class FragmentQuestion extends Fragment{

    View view;
    TextView txtNumberPage, txtQuestion;
    RadioButton rabA,rabB,rabC,rabD;
    RadioGroup radioGroup;
    int pageNumber;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_fragment_question, container, false);

        setWidget();
        Bundle bundle = getArguments();
        assert bundle != null;
        pageNumber = bundle.getInt("pageNumber");
        setData();

        checkQuestion();

        return view;
    }

    private void checkQuestion() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.radA:
                        answerArrayList.set(pageNumber, new Answer(Integer.toString(pageNumber+1), "A"));
                        break;
                    case R.id.radB:
                        answerArrayList.set(pageNumber, new Answer(Integer.toString(pageNumber+1), "B"));
                        break;
                    case R.id.radC:
                        answerArrayList.set(pageNumber, new Answer(Integer.toString(pageNumber+1), "C"));
                        break;
                    case R.id.radD:
                        answerArrayList.set(pageNumber, new Answer(Integer.toString(pageNumber+1), "D"));
                        break;
                }
            }
        });
    }

    void setWidget(){
        txtNumberPage = view.findViewById(R.id.tvNum);
        txtQuestion = view.findViewById(R.id.tvQuestion);
        rabA = view.findViewById(R.id.radA);
        rabB = view.findViewById(R.id.radB);
        rabC = view.findViewById(R.id.radC);
        rabD = view.findViewById(R.id.radD);
        radioGroup = view.findViewById(R.id.radGroup);
    }

    @SuppressLint("SetTextI18n")
    void setData(){
        try {
            Question question = questionArrayList.get(pageNumber);
            txtQuestion.setText(question.getQuestion());
            rabA.setText(question.getAnswerA());
            rabB.setText(question.getAnswerB());
            rabC.setText(question.getAnswerC());
            rabD.setText(question.getAnswerD());
        }catch (Exception e){
            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();

        }
        txtNumberPage.setText(Integer.toString(pageNumber+1));
    }

}