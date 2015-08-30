package gorrita.com.wifipos.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.wifi.ScanResult;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by salva on 23/08/15.
 */
public class WifiPosManager {

    private static final CharSequence TAG = "gorrita.com.wifipos.db.WifiPosManager";
    private static WifiPosDB wifiPosDB;
    private static final CharSequence DESCRIPTION = "DESCRIPTION";
    private static final CharSequence DATACREATED = "DATACREATED";
    private static final CharSequence DATAUPDATED = "DATAUPDATED";
    private static final CharSequence ACTIVE = "ACTIVE";

    public static void intDB(Context contexto) {
        if (wifiPosDB == null)
            wifiPosDB = new WifiPosDB(contexto);
    }

    public static List<Plane>  listPlanes(CharSequence where){
        SQLiteDatabase db = null;
        Cursor c = null;
        try{
            db = wifiPosDB.getReadableDatabase();
            c = db.rawQuery("select * from planes " + where, null);
            List<Plane> listPlane = new ArrayList();
            Plane plane = null;
            if (c.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros
                do {
                    plane = loadPlane(c);
                    listPlane.add(plane);
                } while(c.moveToNext());
            }

            return listPlane;
        }
        catch (Exception ex){
            Log.e(TAG.toString(), "listPlanes--->" + ex.getMessage());
            throw ex;
        }
        finally{
            close(db, c);
        }
    }

    public static List<PointTraining>  listPointTraining(CharSequence where){
        SQLiteDatabase db = null;
        Cursor c = null;
        try{
            db = wifiPosDB.getReadableDatabase();
            c = db.rawQuery("select * from POINTTRAININGS " + where, null);
            List<PointTraining> listPointTraining = new ArrayList();
            PointTraining pointTraining = null;
            if (c.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros
                do {
                    pointTraining = loadPointTraining(c);
                    listPointTraining.add(pointTraining);
                } while(c.moveToNext());
            }

            return listPointTraining;
        }
        catch (Exception ex){
            Log.e(TAG.toString(), "listPointTraining--->" + ex.getMessage());
            throw ex;
        }
        finally{
            close(db, c);
        }
    }

    public static List<PointTrainingWifi>  listPointTrainingWifi(CharSequence where){
        SQLiteDatabase db = null;
        Cursor c = null;
        try{
            db = wifiPosDB.getReadableDatabase();
            c = db.rawQuery("select * from POINTTRAININGWIFIS " + where, null);
            List<PointTrainingWifi> listPointTrainingWifi = new ArrayList();
            PointTrainingWifi pointTrainingWifi = null;
            if (c.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros
                do {
                    pointTrainingWifi = loadPointTrainingWifi(c);
                    listPointTrainingWifi.add(pointTrainingWifi);
                } while(c.moveToNext());
            }

            return listPointTrainingWifi;
        }
        catch (Exception ex){
            Log.e(TAG.toString(), "listPointTrainingWifi--->" + ex.getMessage());
            throw ex;
        }
        finally{
            close(db, c);
        }
    }

    public static List<Training>  listTraining(CharSequence where){
        SQLiteDatabase db = null;
        Cursor c = null;
        try{
            db = wifiPosDB.getReadableDatabase();
            c = db.rawQuery("select * from TRAININGS " + where, null);
            List<Training> listTraining = new ArrayList();
            Training training = null;
            if (c.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros
                do {
                    training = loadTraining(c);
                    listTraining.add(training);
                } while(c.moveToNext());
            }

            return listTraining;
        }
        catch (Exception ex){
            Log.e(TAG.toString(), "listTraining--->" + ex.getMessage());
            throw ex;
        }
        finally{
            close(db, c);
        }
    }

    public static List<Wifi>  listWifi(CharSequence where){
        SQLiteDatabase db = null;
        Cursor c = null;
        try{
            db = wifiPosDB.getReadableDatabase();
            c = db.rawQuery("select * from WIFIS " + where, null);
            List<Wifi> listWifi = new ArrayList();
            Wifi wifi = null;
            if (c.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros
                do {
                    wifi = loadWifi(c);
                    listWifi.add(wifi);
                } while(c.moveToNext());
            }

            return listWifi;
        }
        catch (Exception ex){
            Log.e(TAG.toString(), "listWifi--->" + ex.getMessage());
            throw ex;
        }
        finally{
            close(db, c);
        }
    }

    private static Plane loadPlane(Cursor c){
        Plane plane = new Plane();
        plane.setFile(c.getString(1));
        plane.setName(c.getString(2));
        loadComun(c, plane);
        return plane;
    }

    private static PointTraining loadPointTraining(Cursor c){
        PointTraining pointTraining = new PointTraining();
        pointTraining.setTraining(c.getInt(1));
        pointTraining.setX(c.getDouble(2));
        pointTraining.setY(c.getDouble(3));
        loadComun(c, pointTraining);
        return pointTraining;
    }

    private static PointTrainingWifi loadPointTrainingWifi(Cursor c){
        PointTrainingWifi pointTrainingWifi = new PointTrainingWifi();
        pointTrainingWifi.setPointtraining(c.getInt(1));
        pointTrainingWifi.setWifi(c.getInt(2));
        loadComun(c, pointTrainingWifi);
        return pointTrainingWifi;
    }

    private static Training loadTraining(Cursor c){
        Training training = new Training();
        training.setPlane(c.getInt(1));
        loadComun(c, training);
        return training;
    }

    private static Wifi loadWifi(Cursor c){
        Wifi wifi = new Wifi();
        wifi.setSSID(c.getString(1));
        wifi.setBSSID(c.getString(2));
        wifi.setCapabilities(c.getString(3));
        wifi.setLevel(c.getInt(4));
        wifi.setFrequency(c.getInt(5));
        wifi.setTimestamp(c.getLong(6));
        wifi.setSeen(c.getLong(7));
        wifi.setIsAutoJoinCandidate(c.getInt(8));
        loadComun(c, wifi);
        return wifi;
    }

    private static void loadComun(Cursor c, Comun cm){
        cm.setId(c.getInt(0));
        cm.setDescription(c.getString(c.getColumnIndex(DESCRIPTION.toString())));
        cm.setDataCreated(c.getLong(c.getColumnIndex(DATACREATED.toString())));
        cm.setDataUpdateted(c.getLong(c.getColumnIndex(DATAUPDATED.toString())));
        cm.setActive(c.getInt(c.getColumnIndex(ACTIVE.toString())));
    }

    public static void savePoint(List<ScanResult> wifis){

        //entrenament
        //comprobar si existeix entrenament amb eixe pla
        //si no existeix crearlo
        //carregar el entrenament
        //posarlo a una variable estática

        //punt d'entrenament
        //si anteriorment no hi ha entrenament afegirne un
        //si no
        //comprobar si existeix punt d'entrenament
        //identrenament y veure si les coordenades no difereixen molt
        //si no esta crearlo afegint-li el id del entrenament d'avanç

        //wifi
        //recorrer el bucle
        //comprobar si hi ha wifi per la mac
        //si no hi ha insertar

        //punt d'entrenament-wifi
        //recorrer el bucle
        //insertar nou


        /*SQLiteDatabase db = null;
        db.execSQL("INSERT INTO PLANES VALUES ( null, '0x7f02007f', 'pla prova', " +
                "'primer pla de prova'," + System.currentTimeMillis() + " ," + System.currentTimeMillis() + " , 1)");*/
    }
/*
    public static Training saveTraining(){

    }

    public static Training training(){

    }

    private static Trainig training(){}

    public static void insertTraining(){

    }
*/
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
        WifiPosManager.wifiPosDB = wifiPosDB;
    }
}
