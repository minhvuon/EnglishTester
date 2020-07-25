package com.example.englishtester.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.englishtester.R;
import com.example.englishtester.model.Question;

import java.util.ArrayList;

public class CheckAnswerAdapter extends BaseAdapter {
    ArrayList lsData;
    LayoutInflater inflater;

    public CheckAnswerAdapter(ArrayList lsData, Context context) {
        this.lsData = lsData;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return lsData.size();
    }

    @Override
    public Object getItem(int i) {
        return lsData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private static class ViewHolder{ TextView tvNumAns,tvYouAns;
    }
    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Question question = (Question)getItem(i);
        ViewHolder viewHolder;
        if(view == null){
            viewHolder = new ViewHolder();
            view = inflater.inflate(R.layout.item_grv_dialog,null);
            viewHolder.tvNumAns = (TextView)view.findViewById(R.id.tvNumAns);
            viewHolder.tvYouAns = (TextView)view.findViewById(R.id.tvAnswer);
            view.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder)view.getTag();
        }
        int position = i + 1;
        viewHolder.tvNumAns.setText("Answer "+ position +":  ");
        viewHolder.tvYouAns.setText(question.getYouAnswer());
        return view;
    }
}
