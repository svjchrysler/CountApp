package com.count.svjchrysler.count;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.edtCod)
    EditText edtCod;

    @BindView(R.id.imgCar)
    ImageView imgCar;

    @BindView(R.id.imgPerson)
    ImageView imgPerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sincronizar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sincronizar:
                sincronizar();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void sincronizar() {
        Toast.makeText(MainActivity.this, "Sincronizar", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.imgCar)
    public void cars(View v) {
        if (!edtCod.getText().toString().trim().equals("")) {
            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(100);
            Intent intentCar = new Intent(this, CountCarActivity.class);
            Helper.nombreEncuestador = edtCod.getText().toString();
            startActivity(intentCar);
        } else {
            Toast.makeText(MainActivity.this, "Introducir nombre", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.imgPerson)
    public void people(View v) {
        if (!edtCod.getText().toString().equals("")) {
            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(100);
            Helper.nombreEncuestador = edtCod.getText().toString();
            Intent intentPeople = new Intent(this, CountPeopleActivity.class);
            startActivity(intentPeople);
        } else {
            Toast.makeText(MainActivity.this, "Introducir nombre", Toast.LENGTH_SHORT).show();
        }
    }
}
