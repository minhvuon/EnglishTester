package com.example.englishtester;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.Objects;

import static com.example.englishtester.LoginActivity.checkLogin;
import static com.example.englishtester.MainActivity.questionArrayList;
import static com.example.englishtester.MainActivity.checkAnswerEl;

public class FragmentHome extends Fragment {

    Button btnB1, btnB2, btnC, btnA0, btnA1, btnA2, btnTest, btnLogout;
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_home_fragment, container, false);

        setWidget();
        setOnClick(btnB1, "B1");
        setOnClick(btnB2, "B2");
        setOnClick(btnA0, "A0");
        setOnClick(btnA1, "A1");
        setOnClick(btnA2, "A2");
        setOnClick(btnC, "C");
        setOnClick(btnTest, "T");

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "LOG OUT", Toast.LENGTH_SHORT).show();
                checkLogin = 1;
                startActivity(new Intent(getActivity(), DelayActivity.class));
                Objects.requireNonNull(getActivity()).finish();
            }
        });

        return view;
    }

    void setWidget() {
        btnB1 = view.findViewById(R.id.btn_test_b1);
        btnB2 = view.findViewById(R.id.btn_test_b2);
        btnA0 = view.findViewById(R.id.btn_test_a0);
        btnA1 = view.findViewById(R.id.btn_test_a1);
        btnA2 = view.findViewById(R.id.btn_test_a2);
        btnC = view.findViewById(R.id.btn_test_c);
        btnTest = view.findViewById(R.id.btn_test);
        btnLogout = view.findViewById(R.id.btn_logout);
    }

    void setOnClick(View view, final String loai) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                questionArrayList.clear();
                checkAnswerEl = 0;
                Intent intent = new Intent(getActivity(), TimeActivity.class);
                intent.putExtra("typeQuestion", loai);
                startActivity(intent);
            }
        });
    }

}