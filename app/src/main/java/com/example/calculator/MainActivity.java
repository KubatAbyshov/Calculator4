package com.example.calculator;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TextView resultField;
    TextView numberField;
    Double operand = null;
    String lastOperation = "=";
    Button clear, send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultField = (TextView) findViewById(R.id.resultField);
        numberField = (TextView) findViewById(R.id.numberField);
        clear = (Button) findViewById(R.id.clean);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vibrate();
                numberField.setText("");
            }
        });
        addInRecycler();
    }

    private void addInRecycler() {
        send = findViewById(R.id.btn_addInRecycler);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("key", resultField.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("OPERATION", lastOperation);

        if (operand != null)
            outState.putDouble("OPERAND", operand);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        lastOperation = savedInstanceState.getString("OPERATION");
        operand = savedInstanceState.getDouble("OPERAND");
        resultField.setText(operand + " " + lastOperation);
    }

    public void Vibrate() {
        Vibrator v = (Vibrator) getSystemService(this.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {

            v.vibrate(50);
        }
    }

    public void onNumberClick(View view) {
        Vibrate();
        Button button = (Button) view;
        numberField.append(button.getText());

        if (lastOperation.equals("=") && operand != null) {
            operand = null;
        }
    }

    public void onOperationClick(View view) {
        Vibrate();
        Button button = (Button) view;
        String op = button.getText().toString();
        String number = numberField.getText().toString();

        if (number.length() > 0) {
            number = number.replace(',', '.');
            try {
                performOperation(Double.valueOf(number), op);
            } catch (NumberFormatException ex) {
                numberField.setText("");
            }
        }
        lastOperation = op;
    }

    private void performOperation(Double number, String operation) {
        if (operand == null) {
            operand = number;
        } else {
            if (lastOperation.equals("=")) {
                lastOperation = operation;

            }

            switch (lastOperation) {
                case "=":
                    operand = number;
                    break;
                case "÷":
                    if (number == 0) {
                        operand = 0.0;
                    } else {
                        operand /= number;
                    }
                    break;

                case "%":
                    number /= 100;
                    operand *= number;
                    break;
                case "×":
                    operand *= number;
                    break;
                case "+":
                    operand += number;
                    break;
                case "-":
                    operand -= number;
                    break;

            }
        }
        resultField.setText(operand.toString().replace('.', ','));
        numberField.setText("");
    }

    public void onFirstPage(View v) {
        Vibrate();
        Intent intent = new Intent(this, FirstPage.class);
        startActivity(intent);
        overridePendingTransition(R.anim.animation_enter, R.anim.animation_leave);
    }


}
