package com.drteam.truongpq.drfit.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.drteam.truongpq.drfit.MainActivity;
import com.drteam.truongpq.drfit.R;
import com.drteam.truongpq.drfit.utilities.MySharedPreferences;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText edtName, edtHeight, edtWeight, edtAge;
    private Button btn_next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().hide();
        init();
    }

    private void init() {
        edtName = (EditText) findViewById(R.id.edt_name);
        edtHeight = (EditText) findViewById(R.id.edt_height);
        edtAge = (EditText) findViewById(R.id.edt_age);
        edtWeight = (EditText) findViewById(R.id.edt_weight);
        btn_next = (Button) findViewById(R.id.btn_next);
        btn_next.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:
                if (edtName.getText().toString().isEmpty() || edtHeight.getText().toString().isEmpty() || edtWeight.getText().toString().isEmpty() || edtAge.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Please complete all field!", Toast.LENGTH_SHORT).show();
                } else {
                    MySharedPreferences.setName(this, edtName.getText().toString());
                    MySharedPreferences.setHeight(this, Integer.parseInt(edtHeight.getText().toString()));
                    MySharedPreferences.setWeight(this, Integer.parseInt(edtWeight.getText().toString()));
                    MySharedPreferences.setAge(this, Integer.parseInt(edtAge.getText().toString()));
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                }
                break;
        }
    }
}
