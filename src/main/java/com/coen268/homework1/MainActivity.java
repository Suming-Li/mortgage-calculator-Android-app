package com.coen268.homework1;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText amount;
    private SeekBar seekBar;
    private RadioGroup radioG;
    private CheckBox checkBox;
    private TextView payment, interestVal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */

        initializeVariables();

        interestVal.setText("" + (float)(seekBar.getProgress() / 10.0));
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            float progressVal;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressVal = (float) (progress / 10.0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                interestVal.setText("" + progressVal);
            }
        });

        /*
        radioG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged (RadioGroup radioG, int checkedId) {
                RadioButton rb = (RadioButton) radioG.findViewById(checkedId);
                String month = (String)rb.getText();
                int monthV = Integer.parseInt(month);
                Toast.makeText(MainActivity.this,
                        monthV+"", Toast.LENGTH_SHORT).show();
            }
        });
        */

        Button calculate = (Button) findViewById(R.id.button);
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (amount.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Enter Amount Borrowed", Toast.LENGTH_SHORT).show();
                    return;
                }

                float amountInput = Float.parseFloat(amount.getText().toString());
                float amountVal = (float)(Math.round(amountInput * 100))/100;

                float rateVal = Float.parseFloat(interestVal.getText().toString()) / 1200;

                int checkedId = radioG.getCheckedRadioButtonId();
                RadioButton checkedRB = (RadioButton) findViewById(checkedId);
                int monthVal = Integer.parseInt(checkedRB.getText().toString()) * 12;

                float taxes = 0;
                if (checkBox.isChecked()) {
                    float f = amountVal / 1000;
                    taxes = (float)(Math.round(f * 100))/100;
                }

                float paymentVal;
                if (rateVal == 0) {
                    float f = amountVal / monthVal + taxes;
                    paymentVal = (float)(Math.round(f * 100))/100;
                } else {
                    double a = 1 - Math.pow(1 + rateVal, - monthVal);
                    double b = amountVal * rateVal / a + taxes;
                    paymentVal = (float)(Math.round(b * 100))/100;
                }

                payment.setText(Float.toString(paymentVal));

                //Toast.makeText(MainActivity.this, amountVal + "- " + rateVal + "- " + monthVal + "- " + taxes + "- " + paymentVal, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initializeVariables() {
        amount = (EditText) findViewById(R.id.amount);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        interestVal = (TextView) findViewById(R.id.interestVal);
        radioG = (RadioGroup) findViewById(R.id.radioGroup);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        payment = (TextView) findViewById(R.id.payment);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
