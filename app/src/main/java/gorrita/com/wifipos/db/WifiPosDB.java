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
        db.execSQL(Constants.CREATETABLEPLANES.toString());
        db.execSQL(Constants.CREATETABLEWIFIS.toString());
        db.execSQL(Constants.CREATETABLETRAININGS.toString());
        db.execSQL(Constants.CREATETABLEPOINTTRAININGS.toString());
        db.execSQL(Constants.CREATETABLEPOINTTRAININGWIFIS.toString());
        db.execSQL("INSERT INTO PLANES VALUES ( null, '0x7f02007f', 'pla prova', " +
                "'primer pla de prova'," + System.currentTimeMillis() + " ," + System.currentTimeMillis() + " , 1)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if(oldVersion < newVersion) {
            db.execSQL(Constants.DROPTABLEPOINTTRAININGWIFIS.toString());
            db.execSQL(Constants.DROPTABLEPOINTTRAININGS.toString());
            db.execSQL(Constants.DROPTABLETRAININGS.toString());
            db.execSQL(Constants.DROPTABLEPLANES.toString());
            db.execSQL(Constants.DROPTABLEWIFIS.toString());
            onCreate(db);
        }
    }

}
