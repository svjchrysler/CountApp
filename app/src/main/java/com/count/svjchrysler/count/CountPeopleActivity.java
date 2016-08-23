package com.count.svjchrysler.count;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CountPeopleActivity extends AppCompatActivity {

    @BindView(R.id.imgHombre)
    ImageView imgHombre;

    @BindView(R.id.imgNinia)
    ImageView imgNinia;

    @BindView(R.id.imgMujer)
    ImageView imgMujer;

    @BindView(R.id.imgAbuelo)
    ImageView imgAbuelo;

    @BindView(R.id.txtvCronometro)
    TextView txtvCronometro;

    @BindView(R.id.txthombre)
    TextView txtHombre;

    @BindView(R.id.txtninia)
    TextView txtNinia;

    @BindView(R.id.txtmujer)
    TextView txtMujer;

    @BindView(R.id.txtabuelo)
    TextView txtAbuelo;

    private CountDownTimer timer;
    private Integer countHombre, countNinia, countMujer, countAbuelo;
    private boolean sw = false, swventana = false, swTerminado = false, swCronometro = false;
    private int[] datos = new int[4];
    private Spinner spinner;
    private DBHandler dbHandler = new DBHandler(this);
    private long hora, minutos, segundos, segundosTotales;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_people);
        this.setTitle(R.string.datos_generales);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        iniciarVariables();
    }

    @Override
    protected void onStop() {
        if (sw) {
            initCronometro((int) segundosTotales);
        }
        super.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("abuelo", countAbuelo);
        outState.putInt("ninia", countNinia);
        outState.putInt("mujer", countMujer);
        outState.putInt("hombre", countHombre);
        outState.putBoolean("sw", swventana);
        outState.putInt("seconds", (int) segundosTotales);
        if (sw)
            timer.cancel();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        countAbuelo = savedInstanceState.getInt("abuelo");
        countHombre = savedInstanceState.getInt("hombre");
        countMujer = savedInstanceState.getInt("mujer");
        countNinia = savedInstanceState.getInt("ninia");

        txtAbuelo.setText("Total: " + countAbuelo);
        txtHombre.setText("Total: " + countHombre);
        txtMujer.setText("Total: " + countMujer);
        txtNinia.setText("Total: " + countNinia);

        swventana = savedInstanceState.getBoolean("sw");

        initCronometro(savedInstanceState.getInt("seconds"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflate = getMenuInflater();
        inflate.inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.spinner);
        spinner = (Spinner) MenuItemCompat.getActionView(item);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.opciones_people, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.remove:
                optionSelecionado();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void optionSelecionado() {
        switch (spinner.getSelectedItem().toString()) {
            case "Hombre":
                cargarInformacion(1, -1);
                break;
            case "Mujer":
                cargarInformacion(3, -1);
                break;
            case "Anciano":
                cargarInformacion(4, -1);
                break;
            case "Niño":
                cargarInformacion(2, -1);
                break;
        }
    }

    private void iniciarVariables() {
        countHombre = 0;
        countNinia = 0;
        countMujer = 0;
        countAbuelo = 0;
    }

    private void animacion() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(50);
    }

    private void cargarInformacion(int param, int n) {
        if (sw) {
            switch (param) {
                case 1:
                    if (countHombre > 0 || n > 0) {
                        countHombre += n;
                        animacion();
                        txtHombre.setText("Total: " + countHombre.toString());
                    }

                    break;
                case 2:
                    if (countNinia > 0 || n > 0) {
                        countNinia += n;
                        animacion();
                        txtNinia.setText("Total: " + countNinia.toString());
                    }
                    break;
                case 3:
                    if (countMujer > 0 || n > 0) {
                        countMujer += n;
                        animacion();
                        txtMujer.setText("Total: " + countMujer.toString());
                    }
                    break;
                case 4:
                    if (countAbuelo > 0 || n > 0) {
                        countAbuelo += n;
                        animacion();
                        txtAbuelo.setText("Total: " + countAbuelo.toString());
                    }
                    break;
            }
        } else {
            initCronometro(600);
            horafechainicio();
            cargarInformacion(param, n);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && swventana) {
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

    private void initCronometro(int n) {
        sw = true;
        timer = new CountDownTimer(n * 1000, 100) {
            @Override
            public void onTick(long millisUntilFinished) {

                segundosTotales = millisUntilFinished / 1000;

                hora = segundosTotales / 3600;
                minutos = (segundosTotales - (hora * 3600)) / 60;
                segundos = segundosTotales - ((hora * 3600) + (minutos * 60));
                if (minutos < 10 || segundos < 10) {
                    String min = String.valueOf(minutos), seg = String.valueOf(segundos);
                    if (minutos < 10)
                        min = "0" + min;
                    if (segundos < 10)
                        seg = "0" + seg;
                    txtvCronometro.setText(min + ":" + seg);
                } else {
                    txtvCronometro.setText(minutos + ":" + segundos);
                }
            }

            @Override
            public void onFinish() {
                txtvCronometro.setText("Terminado");
                swTerminado = true;
                tomarNota();
            }
        };

        timer.start();
    }

    private void tomarNota() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(CountPeopleActivity.this);
        builder.setCancelable(false);

        final LayoutInflater inflater = getLayoutInflater();
        final View dialogLayout = inflater.inflate(R.layout.activity_dialog, null);

        final EditText edtCalle1 = (EditText) dialogLayout.findViewById(R.id.edtCalle1);
        final EditText edtCalle2 = (EditText) dialogLayout.findViewById(R.id.edtCalle2);
        final EditText edtCalle3 = (EditText) dialogLayout.findViewById(R.id.edtCalle3);
        final EditText edtTemperatura = (EditText) dialogLayout.findViewById(R.id.edtTemperatura);
        final EditText edtNota = (EditText) dialogLayout.findViewById(R.id.edtNota);
        final Spinner spCondiciones = (Spinner) dialogLayout.findViewById(R.id.spCondicionesClimaticas);

        String[] condiciones = {"Soleado", "Parcialmente Nublado", "Totalmente Nublado",
                "Parcialmete Lluvioso", "Luvioso"};

        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, condiciones);
        spCondiciones.setAdapter(adapter);

        builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                datos[0] = countHombre;
                datos[1] = countNinia;
                datos[2] = countMujer;
                datos[3] = countAbuelo;

                String nota = edtNota.getText().toString();
                String calle1 = edtCalle1.getText().toString().trim();
                String calle2 = edtCalle2.getText().toString().trim();
                String calle3 = edtCalle3.getText().toString().trim();
                String temperatura = edtTemperatura.getText().toString().trim();
                String condiciones = spCondiciones.getSelectedItem().toString();

                if (calle1.equals("") || calle2.equals("") || calle3.equals("") || temperatura.equals("") || condiciones.equals("")) {
                    tomarNota();
                } else {
                    timer.cancel();
                    dbHandler.addPerson(datos, nota, calle1, calle2, calle3, temperatura, condiciones);
                    startActivity(new Intent(CountPeopleActivity.this, CountPeopleActivity.class));
                    finish();
                }
            }
        });

        if (!swTerminado) {
            builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
        }

        builder.setView(dialogLayout);
        builder.show();
    }

    private void horafechainicio() {
        DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        Helper.hora_actual = hourFormat.format(new java.util.Date()).toString();
        Helper.fecha_actual = dateFormat.format(new java.util.Date()).toString();
    }

    @OnClick(R.id.imgHombre)
    public void countHombre() {
        swventana = true;
        cargarInformacion(1, 1);
    }

    @OnClick(R.id.imgNinia)
    public void countNinia() {
        swventana = true;
        cargarInformacion(2, 1);
    }

    @OnClick(R.id.imgMujer)
    public void countMujer() {
        swventana = true;
        cargarInformacion(3, 1);
    }

    @OnClick(R.id.imgAbuelo)
    public void countAbuelo() {
        swventana = true;
        cargarInformacion(4, 1);
    }
}
