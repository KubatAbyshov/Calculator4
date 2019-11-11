package com.example.calculator;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.util.Log;
import android.view.View;
import android.os.Vibrator;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;


public class FirstPage extends AppCompatActivity {
    private static final int INTENT_KEY = 100;
    private RecyclerView recyclerView;
    private MainAdapter adapter;
    private String resultNum;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);
        recyclerView = findViewById(R.id.recyclerview);

        adapter = new MainAdapter();
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
    }

    public void Vibrate() {
        Vibrator v = (Vibrator) getSystemService(this.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            v.vibrate(50);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }


    public void onOpenActivity(View view) {
        Vibrate();
        Intent intent = new Intent(this, MainActivity.class);
        startActivityForResult(intent, INTENT_KEY);
        overridePendingTransition(R.anim.animation_enter_right, R.anim.animation_leave_right);
    }

    public void onShare(View v) {
        Vibrate();
        if (resultNum != null) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {

                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, adapter.data.toString());
                shareIntent.setType("text/plain");
                if (shareIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(shareIntent);
                }
            }
        } else {
            Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == INTENT_KEY && data != null) {
            resultNum = data.getStringExtra("key");
            Log.e("TAG", "onActivityResult: " + resultNum);
            adapter.addText(resultNum);
        }
    }
}
