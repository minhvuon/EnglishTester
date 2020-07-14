package com.example.englishtester;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.englishtester.model.Question;

import java.util.ArrayList;

public class FragmentHome extends Fragment {

    Button btnB1;
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.activity_home_fragment, container, false);

         setWidget();

         btnB1.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent = new Intent(getActivity(), TimeActivity.class);
//                 intent.putExtra("typeQuestion", "B1");
                 startActivity(intent);
             }
         });

        return view;
    }

    void setWidget(){
        btnB1 = view.findViewById(R.id.btn_test_b1);

    }
}