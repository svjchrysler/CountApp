package com.count.svjchrysler.count;

import android.content.Intent;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import xyz.hanks.library.SmallBang;

public class MainActivity extends AppCompatActivity{

    @BindView(R.id.edtCod)
    EditText edtCod;

    @BindView(R.id.imgCar)
    ImageView imgCar;

    @BindView(R.id.imgPerson)
    ImageView imgPerson;

    private SmallBang smallBang;
    //Vibrator vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        smallBang = SmallBang.attach2Window(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.imgCar)
    public void cars(View v) {
        smallBang.bang(v);
        Intent intentCar = new Intent(this, CountCarActivity.class);
        startActivity(intentCar);
        //vibrator.vibrate(3000);
    }

    @OnClick(R.id.imgPerson)
    public void people(View v) {
        smallBang.bang(v);
        Intent intentPeople = new Intent(this, CountPeopleActivity.class);
        startActivity(intentPeople);
        //vibrator.vibrate(3000);
    }
}
