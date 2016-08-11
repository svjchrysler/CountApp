package com.count.svjchrysler.count;

import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.widget.LinearLayout;

public class CountCarActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_car);
        init();
    }

    private void init() {
        configComponents();
    }

    private void configComponents() {

    }
}
