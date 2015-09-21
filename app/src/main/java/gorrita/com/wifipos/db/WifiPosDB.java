package gorrita.com.wifipos.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import gorrita.com.wifipos.Constants;

public class WifiPosDB extends SQLiteOpenHelper {



    public WifiPosDB(Context context){
        super(context, Constants.DATABASE_NAME.toString(), null, Constants.VERSION);
        //super(contexto, nombre, factory, version);
    }

 @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Constants.createTablePlanes.toString());
        db.execSQL(Constants.createTableWifis.toString());
        db.execSQL(Constants.createTableTrainings.toString());
        db.execSQL(Constants.createTablePointTrainings.toString());
        db.execSQL(Constants.createTablePointTrainingWifis.toString());
        db.execSQL("INSERT INTO PLANES VALUES ( null, '0x7f02007f', 'pla prova', " +
                "'primer pla de prova'," + System.currentTimeMillis() + " ," + System.currentTimeMillis() + " , 1)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if(oldVersion < newVersion) {
            db.execSQL(Constants.dropTablePointTrainingWifis.toString());
            db.execSQL(Constants.dropTablePointTrainings.toString());
            db.execSQL(Constants.dropTableTrainings.toString());
            db.execSQL(Constants.dropTablePlanes.toString());
            db.execSQL(Constants.dropTableWifis.toString());
            onCreate(db);
        }
    }

}
