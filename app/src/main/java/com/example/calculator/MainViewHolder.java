package com.example.calculator;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MainViewHolder extends RecyclerView.ViewHolder {

    TextView textView;

    public MainViewHolder(@NonNull View view) {
        super(view);
        textView = itemView.findViewById(R.id.vh_text_view);
    }

    public void onBind(String s) {
        textView.setText(s);
    }
}
