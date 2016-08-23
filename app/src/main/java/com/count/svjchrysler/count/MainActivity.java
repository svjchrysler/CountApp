package com.count.svjchrysler.count;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.count.svjchrysler.count.Models.CategoryCar;
import com.count.svjchrysler.count.Models.CategoryPerson;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

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

    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        dbHandler = new DBHandler(this);
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

        if (isConnectedMobile(this) || isConnectedWifi(this)) {
            if (isOnline(this)) {
                LinkedList<CategoryCar> listCars = dbHandler.getCars();
                LinkedList<CategoryPerson> listPeople = dbHandler.getPeople();

                for (int i = 0; i < listPeople.size(); i++) {
                    cargarInformacionPeople(listPeople.get(i));
                }

                for (int i=0;i<listCars.size();i++){
                    cargarInformacionCars(listCars.get(i));
                }

                if (!swsincronizacion)
                    dbHandler.deleteTable();

                Toast.makeText(MainActivity.this, "Gracias por utilizar nuestro sistema :)", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "No esta conecta a internet", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(MainActivity.this, "Porfavor conectarse a internet", Toast.LENGTH_SHORT).show();
        }

    }

    public boolean isConnectedWifi(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
    }

    public boolean isOnline(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
    }

    public boolean isConnectedMobile(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE;
    }

    private void cargarInformacionCars(final CategoryCar car) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Subiendo su informacion");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Helper.URL_CAR_STORE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Error En el Servidor volver a intentarlo", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("nombre", car.getNombre());
                params.put("particular", String.valueOf(car.getParticular()));
                params.put("bicicleta", String.valueOf(car.getBicicleta()));
                params.put("motocicleta", String.valueOf(car.getMotocicleta()));
                params.put("taxi", String.valueOf(car.getTaxi()));
                params.put("publico", String.valueOf(car.getPublico()));
                params.put("repartidor", String.valueOf(car.getRepartidor()));
                params.put("relevamiento", car.getRelevamiento());
                params.put("lateral_a", car.getLateral_a());
                params.put("lateral_b", car.getLateral_b());
                params.put("temperatura", car.getTemperatura());
                params.put("condiciones", car.getCondiciones());
                params.put("inicio", car.getInicio());
                params.put("fin", car.getFin());
                params.put("fecha", car.getFecha());
                params.put("nota", car.getNota());
                return params;
            }
        };

        RequestQueue request = Volley.newRequestQueue(this);
        request.add(stringRequest);
    }

    private Boolean swsincronizacion = false;

    private void cargarInformacionPeople(final CategoryPerson person) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Subiendo su informacion");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Helper.URL_PERSON_STORE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        swsincronizacion = true;
                        Toast.makeText(MainActivity.this, "Error En el Servidor volver a intentarlo", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("nombre", person.getNombre());
                params.put("hombre", String.valueOf(person.getHombre()));
                params.put("ninia", String.valueOf(person.getNinia()));
                params.put("mujer", String.valueOf(person.getMujer()));
                params.put("anciano", String.valueOf(person.getAnciano()));
                params.put("relevamiento", person.getRelevamiento());
                params.put("lateral_a", person.getLateral_a());
                params.put("lateral_b", person.getLateral_b());
                params.put("temperatura", person.getTemperatura());
                params.put("condiciones", person.getCondiciones());
                params.put("inicio", person.getInicio());
                params.put("fin", person.getFin());
                params.put("fecha", person.getFecha());
                params.put("nota", person.getNota());
                return params;
            }
        };

        RequestQueue request = Volley.newRequestQueue(this);
        request.add(stringRequest);
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
