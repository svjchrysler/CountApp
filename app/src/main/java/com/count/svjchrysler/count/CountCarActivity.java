package com.count.svjchrysler.count;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CountCarActivity extends AppCompatActivity {

    @BindView(R.id.txtvCronometro)
    TextView txtvcronometro;

    @BindView(R.id.imgParticular)
    ImageView imgParticular;

    @BindView(R.id.imgBicicleta)
    ImageView imgBicicleta;

    @BindView(R.id.imgPublico)
    ImageView imgPublico;

    @BindView(R.id.imgRepartidor)
    ImageView imgRepartidor;

    @BindView(R.id.imgMotoclicleta)
    ImageView imgMotocicleta;

    @BindView(R.id.imgTaxi)
    ImageView imgTaxi;

    @BindView(R.id.txtparticular)
    TextView txtParticular;

    @BindView(R.id.txtbicicleta)
    TextView txtBicicleta;

    @BindView(R.id.txtpublico)
    TextView txtPublico;

    @BindView(R.id.txtrepartidor)
    TextView txtRepartidor;

    @BindView(R.id.txtmotoclicleta)
    TextView txtMotocicleta;

    @BindView(R.id.txttaxi)
    TextView txtTaxi;

    private CountDownTimer timer;
    private Integer countParticular, countBicicleta, countPublico, countRepartidor, countMotocicleta, countTaxi;
    private boolean sw = false, swVentana = false, swTerminado = false;
    private DBHandler dbHandler = new DBHandler(this);
    private int[] datos = new int[6];
    private Spinner spinner;
    long hora, minutos, segundos, segundosTotales;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_car);
        this.setTitle(R.string.datos_generales);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        configVariables();
    }

    private void configVariables() {
        countBicicleta = 0;
        countMotocicleta = 0;
        countParticular = 0;
        countPublico = 0;
        countRepartidor = 0;
        countTaxi = 0;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflate = getMenuInflater();
        inflate.inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.spinner);
        spinner = (Spinner) MenuItemCompat.getActionView(item);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.opciones_cars, android.R.layout.simple_spinner_dropdown_item);
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
            case "Bicicleta":
                inicializar(1, -1);
                break;
            case "Taxi":
                inicializar(2, -1);
                break;
            case "Motocicleta":
                inicializar(3, -1);
                break;
            case "Particular":
                inicializar(4, -1);
                break;
            case "Publico":
                inicializar(5, -1);
                break;
            case "Repartidor":
                inicializar(6, -1);
                break;

        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("bicicleta", countBicicleta);
        outState.putInt("taxi", countTaxi);
        outState.putInt("motocicleta", countMotocicleta);
        outState.putInt("particular", countParticular);
        outState.putInt("publico", countPublico);
        outState.putInt("repartidor", countRepartidor);
        outState.putInt("seconds", (int) segundosTotales);
        outState.putBoolean("sw", swVentana);
        if (sw)
            timer.cancel();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        countBicicleta = savedInstanceState.getInt("bicicleta");
        countTaxi = savedInstanceState.getInt("taxi");
        countMotocicleta = savedInstanceState.getInt("motocicleta");
        countParticular = savedInstanceState.getInt("particular");
        countPublico = savedInstanceState.getInt("publico");
        countRepartidor = savedInstanceState.getInt("repartidor");

        txtBicicleta.setText("Total: " + countBicicleta);
        txtTaxi.setText("Total: " + countTaxi);
        txtMotocicleta.setText("Total: " + countMotocicleta);
        txtParticular.setText("Total: " + countParticular);
        txtPublico.setText("Total: " + countPublico);
        txtRepartidor.setText("Total: " + countRepartidor);
        swVentana = savedInstanceState.getBoolean("sw");
        initCronometro(savedInstanceState.getInt("seconds"));
    }

    @Override
    protected void onStop() {
        if (sw) {
            initCronometro((int) segundosTotales);
        }
        super.onStop();
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
                    txtvcronometro.setText(min + ":" + seg);
                } else {
                    txtvcronometro.setText(minutos + ":" + segundos);
                }
            }

            @Override
            public void onFinish() {
                txtvcronometro.setText("Terminado");
                swTerminado = true;
                tomarNota();
            }
        };

        timer.start();
    }

    private void animacion() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(50);
    }

    private void inicializar(int param, int n) {
        if (sw) {
            switch (param) {
                case 1:
                    if (countBicicleta > 0 || n > 0) {
                        countBicicleta += n;
                        animacion();
                        txtBicicleta.setText("Total: " + countBicicleta.toString());
                    }

                    break;
                case 2:
                    if (countTaxi > 0 || n > 0) {
                        countTaxi += n;
                        animacion();
                        txtTaxi.setText("Total: " + countTaxi.toString());
                    }
                    break;
                case 3:
                    if (countMotocicleta > 0 || n > 0) {
                        countMotocicleta += n;
                        animacion();
                        txtMotocicleta.setText("Total: " + countMotocicleta.toString());
                    }
                    break;
                case 4:
                    if (countParticular > 0 || n > 0) {
                        countParticular += n;
                        animacion();
                        txtParticular.setText("Total: " + countParticular.toString());
                    }
                    break;
                case 5:
                    if (countPublico > 0 || n > 0) {
                        countPublico += n;
                        animacion();
                        txtPublico.setText("Total: " + countPublico.toString());
                    }
                    break;
                case 6:
                    if (countRepartidor > 0 || n > 0) {
                        countRepartidor += n;
                        animacion();
                        txtRepartidor.setText("Total: " + countRepartidor.toString());
                    }
                    break;
            }
        } else {
            initCronometro(600);
            horafechainicio();
            inicializar(param, n);
        }
    }

    private void tomarNota() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(CountCarActivity.this);
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
                datos[0] = countBicicleta;
                datos[1] = countTaxi;
                datos[2] = countMotocicleta;
                datos[3] = countParticular;
                datos[4] = countPublico;
                datos[5] = countRepartidor;

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
                    dbHandler.addCar(datos, nota, calle1, calle2, calle3, temperatura, condiciones);
                    startActivity(new Intent(CountCarActivity.this, CountCarActivity.class));
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

    @OnClick(R.id.imgBicicleta)
    public void countBicicleta() {
        swVentana = true;
        inicializar(1, 1);
    }

    @OnClick(R.id.imgTaxi)
    public void countTaxi() {
        swVentana = true;
        inicializar(2, 1);
    }

    @OnClick(R.id.imgMotoclicleta)
    public void countMotocicleta() {
        swVentana = true;
        inicializar(3, 1);
    }

    @OnClick(R.id.imgParticular)
    public void countParticular() {
        swVentana = true;
        inicializar(4, 1);
    }

    @OnClick(R.id.imgPublico)
    public void countPublico() {
        swVentana = true;
        inicializar(5, 1);
    }

    @OnClick(R.id.imgRepartidor)
    public void countRepartidor() {
        swVentana = true;
        inicializar(6, 1);
    }

}
