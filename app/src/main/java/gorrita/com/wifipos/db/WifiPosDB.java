package gorrita.com.wifipos.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class WifiPosDB extends SQLiteOpenHelper {

    //private static WifiBD ourInstance;

    public WifiPosDB(Context context){
        super(context, "WifiPos", null, 1);
        //super(contexto, nombre, factory, version);
    }

 @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Plane.createTable);
        db.execSQL(Wifi.createTable);
        db.execSQL(Training.createTable);
        db.execSQL(PointTraining.createTable);
        db.execSQL(PointTrainingWifi.createTable);
        db.execSQL("INSERT INTO PLANES VALUES ( null, 'gorrita.com.wifipos/plane1.gif', 'pla prova', " +
                "'primer pla de prova'," + System.currentTimeMillis() + " ," + System.currentTimeMillis() + " , 1)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion)
            onCreate(db);
    }

}
