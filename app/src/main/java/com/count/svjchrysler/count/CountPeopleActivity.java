package com.count.svjchrysler.count;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import butterknife.BindView;
import xyz.hanks.library.SmallBang;

public class CountPeopleActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imgHombre, imgNinia, imgMujer, imgAbuelo;
    private TextView txtvCronometro;
    private TextView txtHombre, txtNinia, txtMujer, txtAbuelo;

    private CountDownTimer timer;
    private Integer countHombre, countNinia, countMujer, countAbuelo;

    private boolean sw = false, swventana = false;
    private SmallBang mSmallBang;
    private int[] datos = new int[4];

    DBHandler dbHandler = new DBHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_people);
        this.setTitle(R.string.datos_generales);
        mSmallBang = SmallBang.attach2Window(this);
        init();
    }

    private void init() {
        configComponents();
        configEventos();
        iniciarVariables();
        tomarNota();
    }

    private void iniciarVariables() {
        countHombre = 0;
        countNinia = 0;
        countMujer = 0;
        countAbuelo = 0;
    }

    private void configEventos() {
        imgHombre.setOnClickListener(this);
        imgNinia.setOnClickListener(this);
        imgMujer.setOnClickListener(this);
        imgAbuelo.setOnClickListener(this);
    }

    private void configComponents() {
        imgHombre = (ImageView) findViewById(R.id.imgHombre);
        imgNinia = (ImageView) findViewById(R.id.imgNinia);
        imgMujer = (ImageView) findViewById(R.id.imgMujer);
        imgAbuelo = (ImageView) findViewById(R.id.imgAbuelo);

        txtvCronometro = (TextView) findViewById(R.id.txtvCronometro);
        txtHombre = (TextView) findViewById(R.id.txthombre);
        txtNinia = (TextView) findViewById(R.id.txtninia);
        txtMujer = (TextView) findViewById(R.id.txtmujer);
        txtAbuelo = (TextView) findViewById(R.id.txtabuelo);
    }

    private void cargarInformacion(int param, View v) {
        if (sw) {
            switch (param) {
                case 1:
                    countHombre++;
                    mSmallBang.bang(v);
                    txtHombre.setText("Total: " + countHombre.toString());
                    break;
                case 2:
                    countNinia++;
                    mSmallBang.bang(v);
                    txtNinia.setText("Total: " + countNinia.toString());
                    break;
                case 3:
                    countMujer++;
                    mSmallBang.bang(v);
                    txtMujer.setText("Total: " + countMujer.toString());
                    break;
                case 4:
                    countAbuelo++;
                    mSmallBang.bang(v);
                    txtAbuelo.setText("Total: " + countAbuelo.toString());
                    break;
            }
        } else {
            initCronometro();
            cargarInformacion(param, v);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK && swventana) {
            advertenciaClose();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void advertenciaClose() {
        final AlertDialog.Builder alertAdvertencia = new AlertDialog.Builder(this);
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

    private void initCronometro() {
        sw = true;
        timer = new CountDownTimer(60 * 1000, 100) {
            @Override
            public void onTick(long millisUntilFinished) {

                long hora, minutos, segundos;
                long segundosTotales = millisUntilFinished / 1000;

                hora = segundosTotales / 3600;
                minutos = (segundosTotales - (hora * 3600)) / 60;
                segundos = segundosTotales - ((hora * 3600) + (minutos * 60));
                txtvCronometro.setText(minutos + ":" + segundos);
            }

            @Override
            public void onFinish() {
                txtvCronometro.setText("Terminado");
                tomarNota();
            }
        };

        timer.start();
    }

    private void tomarNota() {
        /*AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Observaciones");
        alert.setMessage("Descripcion");
        alert.setCancelable(false);
        // Set an EditText view to get user input
        final EditText input = new EditText(this);
        alert.setView(input);
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                if (!input.getText().toString().equals("")) {
                    datos[0] = countHombre;
                    datos[1] = countNinia;
                    datos[2] = countMujer;
                    datos[3] = countAbuelo;
                    dbHandler.addPerson(datos, input.getText().toString().trim());
                    Intent intent = new Intent(CountPeopleActivity.this, CountPeopleActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    tomarNota();
                }
            }
        });
        alert.show();*/

        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.dialog, null);

        final EditText edtCalle1 = (EditText) dialogLayout.findViewById(R.id.edtCalle1);
        final EditText edtCalle2 = (EditText) dialogLayout.findViewById(R.id.edtCalle2);
        final EditText edtCalle3 = (EditText) dialogLayout.findViewById(R.id.edtCalle3);
        final EditText edtTemperatura = (EditText) dialogLayout.findViewById(R.id.edtTemperatura);
        final Spinner spCondiciones = (Spinner) dialogLayout.findViewById(R.id.spCondicionesClimaticas);
        final Button btnEnviar = (Button) dialogLayout.findViewById(R.id.btnEnviar);
        final Button btnCancelar = (Button) dialogLayout.findViewById(R.id.btnCancelar);

        ArrayAdapter<CharSequence> adapter;
        adapter = ArrayAdapter.createFromResource(this, R.array.condiciones, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spCondiciones.setAdapter(adapter);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String calle1 = edtCalle1.getText().toString();
                String calle2 = edtCalle2.getText().toString();
                String calle3 = edtCalle3.getText().toString();
                String temperatura = edtTemperatura.getText().toString();
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(CountPeopleActivity.this);
        builder.setView(dialogLayout);
        builder.show();
    }

    @Override
    public void onClick(View v) {
        swventana = true;
        switch (v.getId()) {
            case R.id.imgHombre:
                cargarInformacion(1, v);
                break;
            case R.id.imgNinia:
                cargarInformacion(2, v);
                break;
            case R.id.imgMujer:
                cargarInformacion(3, v);
                break;
            case R.id.imgAbuelo:
                cargarInformacion(4, v);
                break;
        }
    }
}
