package com.count.svjchrysler.count;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.SpanWatcher;


public class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "dbencuesta";
    private static final String TABLE_CARS = "CategoryCars";
    private static final String TABLE_PEOPLE = "CategoryPeople";

    //FIELD SHARE
    private static final String KEY_ID_ECUESTADOR = "idEncuestador";
    private static final String KEY_NOTA = "nota";

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
                + KEY_ID_ECUESTADOR + " INTEGER,"
                + KEY_PARTICULAR + " INTEGER NULL,"
                + KEY_BICICLETA + " INTEGER NULL,"
                + KEY_MOTOCICLETA + " INTEGER NULL,"
                + KEY_TAXI + " INTEGER NULL,"
                + KEY_PUBLICO + " INTEGER NULL,"
                + KEY_REPARTIDOR + " INTEGER NULL,"
                + KEY_NOTA + " VARCHAR(150)" + ")";

        db.execSQL(CREATE_CARS_TABLE);

        String CREATE_PEOPLE_TABLE = "CREATE TABLE " + TABLE_PEOPLE + "("
                + KEY_ID_PEOPLE + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_ID_ECUESTADOR + " INTEGER NULL,"
                + KEY_HOMBRE + " INTEGER NULL,"
                + KEY_NINIA + " INTEGER NULL,"
                + KEY_MUJER + " INTEGER NULL,"
                + KEY_ANCIANO + " INTEGER NULL,"
                + KEY_NOTA + " VARCHAR(150)" + ")";

        db.execSQL(CREATE_PEOPLE_TABLE);

    }

    public void addCar(int[] datos, String nota) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_BICICLETA, datos[0]);
        values.put(KEY_TAXI, datos[1]);
        values.put(KEY_MOTOCICLETA, datos[2]);
        values.put(KEY_PARTICULAR, datos[3]);
        values.put(KEY_PUBLICO, datos[4]);
        values.put(KEY_REPARTIDOR, datos[5]);
        values.put(KEY_NOTA, nota);

        db.insert(TABLE_CARS, null, values);
    }

    public void addPerson(int [] datos, String nota) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();

        content.put(KEY_HOMBRE, datos[0]);
        content.put(KEY_NINIA, datos[1]);
        content.put(KEY_MUJER, datos[2]);
        content.put(KEY_ANCIANO, datos[3]);

        content.put(KEY_NOTA, nota);

        db.insert(TABLE_PEOPLE, null, content);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            db.execSQL("DROP TABLE IF EXISTS" + TABLE_CARS);
            db.execSQL("DROP TABLE IF EXISTS" + TABLE_PEOPLE);
            onCreate(db);
    }
}
