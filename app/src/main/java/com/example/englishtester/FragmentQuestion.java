package com.example.englishtester;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.englishtester.model.Question;

import static com.example.englishtester.LoginActivity.urlGetDataB1;
import static com.example.englishtester.MainActivity.questionArrayList;

public class FragmentQuestion extends Fragment {

    View view;
    TextView txtNumberPage, txtQuestion;
    RadioButton rabA,rabB,rabC,rabD;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_fragment_question, container, false);

        setWidget();

        setData();

        return view;
    }

    void setWidget(){
        txtNumberPage = view.findViewById(R.id.tvNum);
        txtQuestion = view.findViewById(R.id.tvQuestion);
        rabA = view.findViewById(R.id.radA);
        rabB = view.findViewById(R.id.radB);
        rabC = view.findViewById(R.id.radC);
        rabD = view.findViewById(R.id.radD);

    }

    void setData(){
        Bundle bundle = getArguments();
        int pageNumber = bundle.getInt("pageNumber");
        try {
            Question question = questionArrayList.get(pageNumber);
            txtQuestion.setText(question.getQuestion());
            rabA.setText(question.getAnswerA());
            rabB.setText(question.getAnswerB());
            rabC.setText(question.getAnswerC());
            rabD.setText(question.getAnswerD());

//            Toast.makeText(getActivity(), question.toString(), Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();

        }


        txtNumberPage.setText(Integer.toString(pageNumber+1));
    }


}