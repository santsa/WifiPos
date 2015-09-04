package gorrita.com.wifipos.db;

import android.content.ContentValues;
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
    private static SQLiteDatabase db;
    private static Cursor c;
    private static  ContentValues values;

    public static void intDB(Context contexto) {
        if (wifiPosDB == null)
            wifiPosDB = new WifiPosDB(contexto);
    }

    public static List<Plane>  listPlanes(CharSequence where){
        db = null;
        c = null;
        try{
            db = wifiPosDB.getReadableDatabase();
            c = db.rawQuery("select * from planes " + where, null);
            List<Plane> listPlane = new ArrayList();
            Plane plane = null;
            if (c.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros
                do {
                    plane = loadPlane();
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
        db = null;
        c = null;
        try{
            db = wifiPosDB.getReadableDatabase();
            c = db.rawQuery("select * from POINTTRAININGS " + where, null);
            List<PointTraining> listPointTraining = new ArrayList();
            PointTraining pointTraining = null;
            if (c.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros
                do {
                    pointTraining = loadPointTraining();
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
        db = null;
        c = null;
        try{
            db = wifiPosDB.getReadableDatabase();
            c = db.rawQuery("select * from POINTTRAININGWIFIS " + where, null);
            List<PointTrainingWifi> listPointTrainingWifi = new ArrayList();
            PointTrainingWifi pointTrainingWifi = null;
            if (c.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros
                do {
                    pointTrainingWifi = loadPointTrainingWifi();
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
        db = null;
        c = null;
        try{
            db = wifiPosDB.getReadableDatabase();
            c = db.rawQuery("select * from TRAININGS " + where, null);
            List<Training> listTraining = new ArrayList();
            Training training = null;
            if (c.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros
                do {
                    training = loadTraining();
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
        db = null;
        c = null;
        try{
            db = wifiPosDB.getReadableDatabase();
            c = db.rawQuery("select * from WIFIS " + where, null);
            List<Wifi> listWifi = new ArrayList();
            Wifi wifi = null;
            if (c.moveToFirst()) {
                //Recorremos el cursor hasta que no haya más registros
                do {
                    wifi = loadWifi();
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

    private static Plane loadPlane(){
        Plane plane = new Plane();
        plane.setFile(c.getString(1));
        plane.setName(c.getString(2));
        loadComun(c, plane);
        return plane;
    }

    private static PointTraining loadPointTraining(){
        PointTraining pointTraining = new PointTraining();
        pointTraining.setTraining(c.getInt(1));
        pointTraining.setX(c.getDouble(2));
        pointTraining.setY(c.getDouble(3));
        loadComun(c, pointTraining);
        return pointTraining;
    }

    private static PointTrainingWifi loadPointTrainingWifi(){
        PointTrainingWifi pointTrainingWifi = new PointTrainingWifi();
        pointTrainingWifi.setPointtraining(c.getInt(1));
        pointTrainingWifi.setWifi(c.getInt(2));
        loadComun(c, pointTrainingWifi);
        return pointTrainingWifi;
    }

    private static Training loadTraining(){
        Training training = new Training();
        training.setPlane(c.getInt(1));
        loadComun(c, training);
        return training;
    }

    private static Wifi loadWifi(){
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

    private static void update(Comun c){
        SQLiteDatabase db = null;
        try{
            db = wifiPosDB.getWritableDatabase();
            if (c instanceof Plane)
                updatePlane((Plane) c, null, null);
            else if (c instanceof Plane)
                updatePointTraining((PointTraining) c, null, null);
            else if (c instanceof Plane)
                updatePointTrainingWifi((PointTrainingWifi) c, null, null);
            else if (c instanceof Plane)
                updateTraining((Training) c, null, null);
            else if (c instanceof Plane)
                updateWifi((Wifi) c, null, null);
        }
        catch (Exception ex){
            Log.e(TAG.toString(), "update--->" + ex.getMessage());
            throw ex;
        }
        finally{
            close(db, null);
        }
    }

    private static void insert(Comun c){

        SQLiteDatabase db = null;
        values = null;
        try{
            db = wifiPosDB.getWritableDatabase();
            values = new ContentValues();
            if (c instanceof Plane)
                insertPlane((Plane) c);
            else if (c instanceof Plane)
                insertPointTraining((PointTraining) c);
            else if (c instanceof Plane)
                insertPointTrainingWifi((PointTrainingWifi) c);
            else if (c instanceof Plane)
                insertTraining((Training) c);
            else if (c instanceof Plane)
                insertWifi((Wifi) c);
        }
        catch (Exception ex){
            Log.e(TAG.toString(), "insert--->" + ex.getMessage());
            throw ex;
        }
        finally{
            close(db, null);
        }
    }
/*
    private static CharSequence prefixInsert = "INSERT INTO * VALUES ( null,";

    private static StringBuilder initInsert (CharSequence table){
        StringBuilder sql = new StringBuilder();
        sql.append(prefixInsert.toString().replace("*", table));
        return sql;
    }

    " DESCRIPTION TEXT," +
            " DATACREATED LONG NOT NULL DEFAULT " + System.currentTimeMillis() + " ," +
            " DATAUPDATED LONG NOT NULL DEFAULT " + System.currentTimeMillis() + " ," +
            " ACTIVE INTEGER NOT NULL DEFAULT 1 " +
            " )"

    private static void endInsert(StringBuilder sql, Comun c){
        CharSequence chr = c.getDescription() == null ? " , null": " , '" +c.getDescription()+"'";
        sql.append(chr);
        if (c.getDataCreated()!=null)
            sql.append(" , " + c.getDataCreated());
        if ()
        chr = c.getDataCreated() == null ? " ,null": " , " +c.getDescription();
        chr = c.getDescription() == null ? " ,null": " , " +c.getDescription();
        chr = c.getDescription() == null ? " ,null": " , " +c.getDescription();
    }
*/
    private static void valuesComun(Comun c){
        values.put("DESCRIPTION", c.getDescription().toString());
        values.put("DATACREATED", c.getDataCreated());
        values.put("DATAUPDATED", c.getDataUpdateted());
        values.put("ACTIVE", c.getActive());
    }

    private static void saveLoadPlane(Plane p){
        values.put("FILE", p.getFile().toString());
        values.put("NAME", p.getName().toString());
        valuesComun(p);
    }

    private static int insertPlane(Plane p){
        saveLoadPlane(p);
        int idPlane = (int) db.insert("PLANES", null, values);
        return idPlane;
    }

    private static int updatePlane(Plane p, String whereClause, String[] whereArgs){
        saveLoadPlane(p);
        //int filasAfectadas = (int) db.update("PLANES", values, "id = ?", new String[]{String.valueOf(p.getId())});
        int filasAfectadas = (int) db.update("PLANES", values, whereClause, whereArgs);
        return filasAfectadas;
    }

    private static void saveLoadPointTraining(PointTraining p){
        values.put("TRAINING", p.getTraining());
        values.put("X", p.getX());
        values.put("Y", p.getY());
        valuesComun(p);
    }

    private static int insertPointTraining(PointTraining p){
        saveLoadPointTraining(p);
        int idPointTraining = (int) db.insert("POINTTRAININGS", null, values);
        return idPointTraining;
    }

    private static int updatePointTraining(PointTraining p, String whereClause, String[] whereArgs){
        saveLoadPointTraining(p);
        //int filasAfectadas = (int) db.update("PLANES", values, "id = ?", new String[]{String.valueOf(p.getId())});
        int filasAfectadas = (int) db.update("POINTTRAININGS", values, whereClause, whereArgs);
        return filasAfectadas;
    }

    private static void saveLoadPointTrainingWifi(PointTrainingWifi p){
        values.put("POINTTRAINING", p.getPointtraining());
        values.put("WIFI", p.getWifi());
        valuesComun(p);
    }

    private static int insertPointTrainingWifi(PointTrainingWifi p){
        saveLoadPointTrainingWifi(p);
        int idPointTrainingWifi = (int) db.insert("POINTTRAININGWIFIS", null, values);
        return idPointTrainingWifi;
    }

    private static int updatePointTrainingWifi(PointTrainingWifi p, String whereClause, String[] whereArgs){
        saveLoadPointTrainingWifi(p);
        //int filasAfectadas = (int) db.update("PLANES", values, "id = ?", new String[]{String.valueOf(p.getId())});
        int filasAfectadas = (int) db.update("POINTTRAININGWIFIS", values, whereClause, whereArgs);
        return filasAfectadas;
    }

    private static void saveLoadTraining(Training t){
        values.put("PLANE", t.getPlane());
        valuesComun(t);
    }

    private static int insertTraining(Training t){
        saveLoadTraining(t);
        int idTraining = (int) db.insert("TRAININGS", null, values);
        return idTraining;
    }

    private static int updateTraining(Training p, String whereClause, String[] whereArgs){
        saveLoadTraining(p);
        //int filasAfectadas = (int) db.update("PLANES", values, "id = ?", new String[]{String.valueOf(p.getId())});
        int filasAfectadas = (int) db.update("TRAININGS", values, whereClause, whereArgs);
        return filasAfectadas;
    }

    private static void saveLoadWifi(Wifi w){
        values.put("BSSID", w.getBSSID());
        values.put("capabilities", w.getCapabilities());
        values.put("level", w.getLevel());
        values.put("frequency", w.getFrequency());
        values.put("timestamp", w.getTimestamp());
        values.put("seen", w.getSeen());
        values.put("isAutoJoinCandidate", w.getIsAutoJoinCandidate());
        valuesComun(w);
    }

    private static int insertWifi(Wifi w){
        saveLoadWifi(w);
        int idTraining = (int) db.insert("WIFIS", null, values);
        return idTraining;
    }

    private static int updateWifi(Wifi w, String whereClause, String[] whereArgs){
        saveLoadWifi(w);
        //int filasAfectadas = (int) db.update("PLANES", values, "id = ?", new String[]{String.valueOf(p.getId())});
        int filasAfectadas = (int) db.update("WIFIS", values, whereClause, whereArgs);
        return filasAfectadas;
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
