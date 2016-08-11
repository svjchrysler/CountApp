package com.count.svjchrysler.count;

import android.content.Intent;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtCod;
    private ImageView imgPerson, imgCar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        configComponents();
    }

    private void configComponents() {
        edtCod = (EditText) findViewById(R.id.edtCod);
        imgCar = (ImageView) findViewById(R.id.imgCar);
        imgPerson = (ImageView) findViewById(R.id.imgPerson);
        configEvent();
    }

    private void configEvent() {
        imgCar.setOnClickListener(this);
        imgPerson.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgCar:
                Intent intent = new Intent(this, CountCarActivity.class);
                startActivity(intent);
                break;
            case R.id.imgPerson:
                break;
        }
    }
}
