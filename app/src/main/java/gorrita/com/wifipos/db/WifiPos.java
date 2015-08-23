package gorrita.com.wifipos.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by salva on 23/08/15.
 */
public class WifiPos {

    private static final CharSequence TAG = "gorrita.com.wifipos.db.WifiPos";
    private static WifiPosDB wifiPosDB;

    public static void intDB(Context contexto) {
        if (wifiPosDB == null)
            wifiPosDB = new WifiPosDB(contexto);
    }

    public static Plane listPlanes(){
        SQLiteDatabase db = null;
        Cursor c = null;
        try{
            db = wifiPosDB.getReadableDatabase();
            c = db.rawQuery("select * from planes", null);
            Plane plane = null;
            if (c.moveToFirst()) {
                plane = new Plane();
                //Recorremos el cursor hasta que no haya más registros
                do {
                    plane.setId(c.getInt(0));
                    plane.setFile(c.getString(1));
                    plane.setName(c.getString(2));
                    plane.setDescription(c.getString(3));
                    plane.setDataCreated(c.getLong(4));
                    plane.setDataUpdateted(c.getLong(5));
                    plane.setActive(c.getInt(6));
                } while(c.moveToNext());
            }

            return plane;
        }
        catch (Exception ex){
            Log.e(TAG.toString(), "listPlanes--->" + ex.getMessage());
            throw ex;
        }
        finally{
            close(db, c);
        }
    }

    private static void close(SQLiteDatabase db, Cursor c){
        if(c != null && !c.isClosed())
            c.close();
        if(db != null && db.isOpen())
            db.close();
    }

    public static WifiPosDB getWifiPosDB() {
        return wifiPosDB;
    }

    public static void setWifiPosDB(WifiPosDB wifiPosDB) {
        WifiPos.wifiPosDB = wifiPosDB;
    }
}
