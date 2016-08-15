package com.count.svjchrysler.count;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.media.Image;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import butterknife.BindView;
import xyz.hanks.library.SmallBang;
import xyz.hanks.library.SmallBangListener;

public class CountCarActivity extends AppCompatActivity implements View.OnClickListener {

    private SmallBang mSmallBang;
    public TextView txtvcronometro;
    private CountDownTimer timer;
    private ImageView imgParticular, imgBicicleta, imgPublico, imgRepartidor, imgMotocicleta, imgTaxi;
    private TextView txtParticular, txtBicicleta, txtPublico, txtRepartidor, txtMotocicleta, txtTaxi;

    private Integer countParticular, countBicicleta, countPublico, countRepartidor, countMotocicleta, countTaxi;
    private boolean sw = false, swVentana = false;

    DBHandler dbHandler = new DBHandler(this);

    int[] datos = new int[6];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_car);
        this.setTitle(R.string.datos_generales);
        mSmallBang = SmallBang.attach2Window(this);
        init();
    }

    private void init() {
        configVariables();
        configComponents();
        configEventos();
    }

    private void configVariables() {
        countBicicleta = 0;
        countMotocicleta = 0;
        countParticular = 0;
        countPublico = 0;
        countRepartidor = 0;
        countTaxi = 0;
    }

    private void configEventos() {
        imgTaxi.setOnClickListener(this);
        imgMotocicleta.setOnClickListener(this);
        imgRepartidor.setOnClickListener(this);
        imgBicicleta.setOnClickListener(this);
        imgParticular.setOnClickListener(this);
        imgPublico.setOnClickListener(this);
    }

    private void configComponents() {
        txtvcronometro = (TextView) findViewById(R.id.txtvCronometro);

        imgParticular = (ImageView) findViewById(R.id.imgParticular);
        imgBicicleta = (ImageView) findViewById(R.id.imgBicicleta);
        imgPublico = (ImageView) findViewById(R.id.imgPublico);
        imgRepartidor = (ImageView) findViewById(R.id.imgRepartidor);
        imgMotocicleta = (ImageView) findViewById(R.id.imgMotoclicleta);
        imgTaxi = (ImageView) findViewById(R.id.imgTaxi);

        txtParticular = (TextView) findViewById(R.id.txtparticular);
        txtBicicleta = (TextView) findViewById(R.id.txtbicicleta);
        txtPublico = (TextView) findViewById(R.id.txtpublico);
        txtRepartidor = (TextView) findViewById(R.id.txtrepartidor);
        txtMotocicleta = (TextView) findViewById(R.id.txtmotoclicleta);
        txtTaxi = (TextView) findViewById(R.id.txttaxi);
    }

    private void initCronometro() {
        sw = true;
        timer = new CountDownTimer(600 * 1000, 100) {
            @Override
            public void onTick(long millisUntilFinished) {

                long hora, minutos, segundos;
                long segundosTotales = millisUntilFinished / 1000;

                hora = segundosTotales / 3600;
                minutos = (segundosTotales - (hora * 3600)) / 60;
                segundos = segundosTotales - ((hora * 3600) + (minutos * 60));
                txtvcronometro.setText(minutos + ":" + segundos);
            }

            @Override
            public void onFinish() {
                txtvcronometro.setText("Terminado");
                tomarNota();
            }
        };

        timer.start();
    }

    private void inicializar(int param, View v) {
        if (sw) {
            switch (param) {
                case 1:
                    countBicicleta++;
                    mSmallBang.bang(v);
                    txtBicicleta.setText("Total: " + countBicicleta.toString());
                    break;
                case 2:
                    countTaxi++;
                    mSmallBang.bang(v);
                    txtTaxi.setText("Total: " + countTaxi.toString());
                    break;
                case 3:
                    countMotocicleta++;
                    mSmallBang.bang(v);
                    txtMotocicleta.setText("Total: " + countMotocicleta.toString());
                    break;
                case 4:
                    countParticular++;
                    mSmallBang.bang(v);
                    txtParticular.setText("Total: " + countParticular.toString());
                    break;
                case 5:
                    countPublico++;
                    mSmallBang.bang(v);
                    txtPublico.setText("Total: " + countPublico.toString());
                    break;
                case 6:
                    countRepartidor++;
                    mSmallBang.bang(v);
                    txtRepartidor.setText("Total: " + countRepartidor.toString());
                    break;
            }
        } else {
            initCronometro();
            inicializar(param, v);
        }
    }

    private void tomarNota() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Observaciones");
        alert.setMessage("Descripcion");

        alert.setCancelable(false);
        final EditText input = new EditText(this);
        alert.setView(input);

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if (!input.getText().toString().equals("")) {
                    datos[0] = countBicicleta;
                    datos[1] = countTaxi;
                    datos[2] = countMotocicleta;
                    datos[3] = countParticular;
                    datos[4] = countPublico;
                    datos[5] = countRepartidor;
                    dbHandler.addCar(datos, input.getText().toString().trim());
                    Intent intent = new Intent(CountCarActivity.this, CountCarActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    tomarNota();
                }
            }
        });
        alert.show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK && swVentana) {
            adverticiaSalir();
        }
        return super.onKeyDown(keyCode, event);
    }

    public void adverticiaSalir() {
        AlertDialog.Builder alertAdvertencia = new AlertDialog.Builder(this);
        alertAdvertencia.setTitle("Advertencia");
        alertAdvertencia.setMessage("Seguro que quieres salir se borrara tu información?");
        alertAdvertencia.setCancelable(false);

        alertAdvertencia.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tomarNota();
            }
        });

        alertAdvertencia.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alertAdvertencia.show();
    }

    @Override
    protected void onStop() {
        finish();
        super.onStop();
    }

    @Override
    public void onClick(View v) {
        swVentana = true;
        switch (v.getId()) {
            case R.id.imgBicicleta:
                inicializar(1, v);
                break;
            case R.id.imgTaxi:
                inicializar(2, v);
                break;
            case R.id.imgMotoclicleta:
                inicializar(3, v);
                break;
            case R.id.imgParticular:
                inicializar(4, v);
                break;
            case R.id.imgPublico:
                inicializar(5, v);
                break;
            case R.id.imgRepartidor:
                inicializar(6, v);
                break;
        }
    }
}
