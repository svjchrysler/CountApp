package com.count.svjchrysler.count;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "dbencuesta";
    private static final String TABLE_CARS = "CategoryCars";
    private static final String TABLE_PEOPLE = "CategoryPeople";

    //FIELD SHARE
    private static final String KEY_NOMBRE_ECUESTADOR = "nombre";
    private static final String KEY_NOTA = "nota";
    private static final String KEY_CALLE_RELEVAMIENTO = "callerelevamiento";
    private static final String KEY_CALLE_LATERALA = "callelaterala";
    private static final String KEY_CALLE_LATERALB = "callelateralb";
    private static final String KEY_TEMPERATURA = "temperatura";
    private static final String KEY_CONDICIONES = "condiciones";
    private static final String KEY_HORA_INICIO = "horainicio";
    private static final String KEY_HORA_FIN = "horafin";
    private static final String KEY_FECHA_INICIO = "fechainicio";
    private static final String KEY_FECHA_FIN = "fechafin";

    //FIELD CATEGORY_CARS
    private static final String KEY_ID_CAR = "id";
    private static final String KEY_PARTICULAR = "particular";
    private static final String KEY_BICICLETA = "bicicleta";
    private static final String KEY_MOTOCICLETA = "motocicleta";
    private static final String KEY_TAXI = "taxi";
    private static final String KEY_PUBLICO = "publico";
    private static final String KEY_REPARTIDOR = "repartidor";

    //FIELD CATEGORY_PEOPLE
    private static final String KEY_ID_PEOPLE = "id";
    private static final String KEY_HOMBRE = "hombre";
    private static final String KEY_NINIA = "ninia";
    private static final String KEY_MUJER = "mujer";
    private static final String KEY_ANCIANO = "anciano";


    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CARS_TABLE = "CREATE TABLE " + TABLE_CARS + "("
                + KEY_ID_CAR + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_NOMBRE_ECUESTADOR + " VARCHAR(200),"
                + KEY_PARTICULAR + " INTEGER NULL,"
                + KEY_BICICLETA + " INTEGER NULL,"
                + KEY_MOTOCICLETA + " INTEGER NULL,"
                + KEY_TAXI + " INTEGER NULL,"
                + KEY_PUBLICO + " INTEGER NULL,"
                + KEY_REPARTIDOR + " INTEGER NULL,"
                + KEY_CALLE_RELEVAMIENTO + " VARCHAR(200),"
                + KEY_CALLE_LATERALA + " VARCHAR(200),"
                + KEY_CALLE_LATERALB + " VARCHAR(200),"
                + KEY_TEMPERATURA + " VARCHAR(50),"
                + KEY_CONDICIONES + " VARCHAR(50),"
                + KEY_HORA_INICIO + " VARCHAR(20),"
                + KEY_HORA_FIN + " VARCHAR(20),"
                + KEY_FECHA_INICIO + " VARCHAR(20),"
                + KEY_FECHA_FIN + " VARCHAR(20),"
                + KEY_NOTA + " VARCHAR(150)" + ")";

        db.execSQL(CREATE_CARS_TABLE);

        String CREATE_PEOPLE_TABLE = "CREATE TABLE " + TABLE_PEOPLE + "("
                + KEY_ID_PEOPLE + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_NOMBRE_ECUESTADOR + " VARCHAR(200),"
                + KEY_HOMBRE + " INTEGER NULL,"
                + KEY_NINIA + " INTEGER NULL,"
                + KEY_MUJER + " INTEGER NULL,"
                + KEY_ANCIANO + " INTEGER NULL,"
                + KEY_CALLE_RELEVAMIENTO + " VARCHAR(200),"
                + KEY_CALLE_LATERALA + " VARCHAR(200),"
                + KEY_CALLE_LATERALB + " VARCHAR(200),"
                + KEY_TEMPERATURA + " VARCHAR(50),"
                + KEY_CONDICIONES + " VARCHAR(50),"
                + KEY_HORA_INICIO + " VARCHAR(20),"
                + KEY_HORA_FIN + " VARCHAR(20),"
                + KEY_FECHA_INICIO + " VARCHAR(20),"
                + KEY_FECHA_FIN + " VARCHAR(20),"
                + KEY_NOTA + " VARCHAR(150)" + ")";

        db.execSQL(CREATE_PEOPLE_TABLE);

    }

    public void addCar(int[] datos, String nota, String calle1, String calle2, String calle3, String temperatura, String condiciones) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_BICICLETA, datos[0]);
        values.put(KEY_TAXI, datos[1]);
        values.put(KEY_MOTOCICLETA, datos[2]);
        values.put(KEY_PARTICULAR, datos[3]);
        values.put(KEY_PUBLICO, datos[4]);
        values.put(KEY_REPARTIDOR, datos[5]);
        values.put(KEY_FECHA_INICIO, Helper.fecha_actual);
        values.put(KEY_HORA_INICIO, Helper.hora_actual);
        values.put(KEY_NOMBRE_ECUESTADOR, Helper.nombreEncuestador);
        values.put(KEY_CALLE_RELEVAMIENTO, calle1);
        values.put(KEY_CALLE_LATERALA, calle2);
        values.put(KEY_CALLE_LATERALB, calle3);
        values.put(KEY_TEMPERATURA, temperatura);
        values.put(KEY_CONDICIONES, condiciones);
        values.put(KEY_NOTA, nota);
        DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        values.put(KEY_FECHA_FIN, dateFormat.format(new java.util.Date()).toString());
        values.put(KEY_HORA_FIN, hourFormat.format(new java.util.Date()).toString());

        db.insert(TABLE_CARS, null, values);
    }

    public void addPerson(int[] datos, String nota, String calle1, String calle2, String calle3, String temperatura, String condiciones) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();

        content.put(KEY_HOMBRE, datos[0]);
        content.put(KEY_NINIA, datos[1]);
        content.put(KEY_MUJER, datos[2]);
        content.put(KEY_ANCIANO, datos[3]);
        content.put(KEY_FECHA_INICIO, Helper.fecha_actual);
        content.put(KEY_HORA_INICIO, Helper.hora_actual);
        content.put(KEY_NOMBRE_ECUESTADOR, Helper.nombreEncuestador);
        content.put(KEY_CALLE_RELEVAMIENTO, calle1);
        content.put(KEY_CALLE_LATERALA, calle2);
        content.put(KEY_CALLE_LATERALB, calle3);
        content.put(KEY_TEMPERATURA, temperatura);
        content.put(KEY_CONDICIONES, condiciones);
        content.put(KEY_NOTA, nota);
        DateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        content.put(KEY_FECHA_FIN, dateFormat.format(new java.util.Date()).toString());
        content.put(KEY_HORA_FIN, hourFormat.format(new java.util.Date()).toString());

        db.insert(TABLE_PEOPLE, null, content);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS" + TABLE_CARS);
            db.execSQL("DROP TABLE IF EXISTS" + TABLE_PEOPLE);
            onCreate(db);
        }
    }
}
