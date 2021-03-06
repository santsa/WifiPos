package gorrita.com.wifipos.db;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Point;
import android.net.wifi.ScanResult;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gorrita.com.wifipos.AplicationWifi;
import gorrita.com.wifipos.Constants;

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
    private static SQLiteDatabase dbr, dbw;
    private static Cursor c;
    private static  ContentValues values;

    public static void intDB(Context contexto) {
        if (wifiPosDB == null)
            wifiPosDB = new WifiPosDB(contexto);
    }

    public static List<Plane> listPlanes(CharSequence where){
        dbr = null;
        c = null;
        try{
            return _listPlanes(where);
        }
        catch (Exception ex){
            Log.e(TAG.toString(), "listPlanes--->" + ex.getMessage());
            throw ex;
        }
        finally{
            close(dbr, c);
        }
    }

    private static List<Plane> _listPlanes(CharSequence where){

        dbr = wifiPosDB.getReadableDatabase();
        c = dbr.rawQuery("select * from planes " + where, null);
        List<Plane> listPlane = new ArrayList();
        Plane plane = null;
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                plane = loadCursorPlane();
                listPlane.add(plane);
            } while(c.moveToNext());
        }

        return listPlane;
    }

    public static List<PointTraining> listPointTraining(CharSequence where){
        dbr = null;
        c = null;
        try{
            return _listPointTraining(where);
        }
        catch (Exception ex){
            Log.e(TAG.toString(), "listPointTraining--->" + ex.getMessage());
            throw ex;
        }
        finally{
            close(dbr, c);
        }
    }

    private static List<PointTraining>  _listPointTraining(CharSequence where){
        dbr = wifiPosDB.getReadableDatabase();
        c = dbr.rawQuery("select * from POINTTRAININGS " + where, null);
        List<PointTraining> listPointTraining = new ArrayList();
        PointTraining pointTraining = null;
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                pointTraining = loadCursorPointTraining();
                listPointTraining.add(pointTraining);
            } while(c.moveToNext());
        }
        return listPointTraining;
    }

    public static List<PointTrainingWifi> listPointTrainingWifi(CharSequence where){
        dbr = null;
        c = null;
        try{
            return _listPointTrainingWifi(where);
        }
        catch (Exception ex){
            Log.e(TAG.toString(), "listPointTrainingWifi--->" + ex.getMessage());
            throw ex;
        }
        finally{
            close(dbr, c);
        }
    }

    private static List<PointTrainingWifi> _listPointTrainingWifi(CharSequence where){
        dbr = wifiPosDB.getReadableDatabase();
        c = dbr.rawQuery("select * from POINTTRAININGWIFIS " + where, null);
        List<PointTrainingWifi> listPointTrainingWifi = new ArrayList();
        PointTrainingWifi pointTrainingWifi = null;
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                pointTrainingWifi = loadCursorPointTrainingWifi();
                listPointTrainingWifi.add(pointTrainingWifi);
            } while(c.moveToNext());
        }
        return listPointTrainingWifi;
    }

    public static List<Training> listTraining(CharSequence where){
        dbr = null;
        c = null;
        try{

            return _listTraining(where);
        }
        catch (Exception ex){
            Log.e(TAG.toString(), "listTraining--->" + ex.getMessage());
            throw ex;
        }
        finally{
            close(dbr, c);
        }
    }

    private static List<Training> _listTraining(CharSequence where){
        dbr = wifiPosDB.getReadableDatabase();
        c = dbr.rawQuery("select * from TRAININGS " + where, null);
        List<Training> listTraining = new ArrayList();
        Training training = null;
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                training = loadCursorTraining();
                listTraining.add(training);
            } while(c.moveToNext());
        }
        return listTraining;
    }

    public static List<Wifi>  listWifi(CharSequence where){
        dbr = null;
        c = null;
        try{

            return _listWifi(where);
        }
        catch (Exception ex){
            Log.e(TAG.toString(), "listWifi--->" + ex.getMessage());
            throw ex;
        }
        finally{
            close(dbr, c);
        }
    }

    private static List<Wifi> _listWifi(CharSequence where){
        dbr = wifiPosDB.getReadableDatabase();
        c = dbr.rawQuery("select * from WIFIS " + where, null);
        List<Wifi> listWifi = new ArrayList();
        Wifi wifi = null;
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                wifi = loadCursorWifi();
                listWifi.add(wifi);
            } while(c.moveToNext());
        }

        return listWifi;
    }

    private static Plane loadCursorPlane(){
        Plane plane = new Plane();
        plane.setFile(c.getString(1));
        plane.setName(c.getString(2));
        loadCursorComun(c, plane);
        return plane;
    }

    private static PointTraining loadCursorPointTraining(){
        PointTraining pointTraining = new PointTraining();
        pointTraining.setTraining(c.getInt(1));
        pointTraining.setX(c.getDouble(2));
        pointTraining.setY(c.getDouble(3));
        loadCursorComun(c, pointTraining);
        return pointTraining;
    }

    private static PointTrainingWifi loadCursorPointTrainingWifi(){
        PointTrainingWifi pointTrainingWifi = new PointTrainingWifi();
        pointTrainingWifi.setPointtraining(c.getInt(1));
        pointTrainingWifi.setWifi(c.getInt(2));
        pointTrainingWifi.setLevel(c.getInt(3));
        pointTrainingWifi.setTimestamp(c.getLong(4));
        loadCursorComun(c, pointTrainingWifi);
        return pointTrainingWifi;
    }

    private static Training loadCursorTraining(){
        Training training = new Training();
        training.setPlane(c.getInt(1));
        loadCursorComun(c, training);
        return training;
    }

    private static Wifi loadCursorWifi(){
        Wifi wifi = new Wifi();
        wifi.setSSID(c.getString(1));
        wifi.setBSSID(c.getString(2));
        wifi.setCapabilities(c.getString(3));
        wifi.setFrequency(c.getInt(5));
        loadCursorComun(c, wifi);
        return wifi;
    }

    private static void loadCursorComun(Cursor c, ComunDB cm){
        cm.setId(c.getInt(0));
        cm.setDescription(c.getString(c.getColumnIndex(DESCRIPTION.toString())));
        cm.setDataCreated(c.getLong(c.getColumnIndex(DATACREATED.toString())));
        cm.setDataUpdateted(c.getLong(c.getColumnIndex(DATAUPDATED.toString())));
        cm.setActive(c.getInt(c.getColumnIndex(ACTIVE.toString())));
    }

     private static void valuesComun(ComunDB c){
        String desc = c.getDescription()==null?null:c.getDescription().toString();
        values.put("DESCRIPTION", desc);
        values.put("DATACREATED", c.getDataCreated());
        values.put("DATAUPDATED", c.getDataUpdateted());
        values.put("ACTIVE", c.getActive());
    }

    private static void loadContentValues(){
        if (values!=null)
            values.clear();
        else
            values = new ContentValues();
    }

    private static void saveLoadPlane(Plane p){
        loadContentValues();
        values.put("FILE", p.getFile().toString());
        values.put("NAME", p.getName().toString());
        valuesComun(p);
    }

    private static int insertPlane(Plane p){
        saveLoadPlane(p);
        int idPlane = (int) dbr.insert("PLANES", null, values);
        return idPlane;
    }

    private static int updatePlane(Plane p, String whereClause, String[] whereArgs){
        saveLoadPlane(p);
        //int filasAfectadas = (int) db.update("PLANES", values, "id = ?", new String[]{String.valueOf(p.getId())});
        int filasAfectadas = (int) dbr.update("PLANES", values, whereClause, whereArgs);
        return filasAfectadas;
    }

    private static void saveLoadPointTraining(PointTraining p){
        loadContentValues();
        values.put("TRAINING", p.getTraining());
        values.put("X", p.getX());
        values.put("Y", p.getY());
        valuesComun(p);
    }

    private static int insertPointTraining(PointTraining p){
        saveLoadPointTraining(p);
        int idPointTraining = (int) dbr.insert("POINTTRAININGS", null, values);
        return idPointTraining;
    }

    public static PointTraining insertPointTraining(AplicationWifi aplicationWifi, Double x, Double y, Integer activo) {
        PointTraining pointTraining = new PointTraining(aplicationWifi.getTraining().getId(), x, y);
        loadComun(pointTraining, null, null, System.currentTimeMillis(), activo);
        int idPointTraining = insertPointTraining(pointTraining);
        pointTraining.setId(idPointTraining);
        return pointTraining;
    }

    private static int updatePointTraining(PointTraining p, String whereClause, String[] whereArgs){
        saveLoadPointTraining(p);
        int filasAfectadas = (int) dbr.update("POINTTRAININGS", values, whereClause, whereArgs);
        return filasAfectadas;
    }

    private static void saveLoadPointTrainingWifi(PointTrainingWifi p){
        loadContentValues();
        values.put("POINTTRAINING", p.getPointtraining());
        values.put("WIFI", p.getWifi());
        values.put("level", p.getLevel());
        values.put("timestamp", p.getTimestamp());
        valuesComun(p);
    }

    private static int insertPointTrainingWifi(PointTrainingWifi p){
        saveLoadPointTrainingWifi(p);
        int idPointTrainingWifi = (int) dbr.insert("POINTTRAININGWIFIS", null, values);
        return idPointTrainingWifi;
    }

    private static PointTrainingWifi insertPointTrainingWifi(PointTraining p, Wifi w, Integer level, Long timestamp){
        PointTrainingWifi pointTrainingWifi = new PointTrainingWifi(p.getId(), w.getId(), level, timestamp);
        loadComun(pointTrainingWifi, null, null, System.currentTimeMillis(), 1);
        int tPointTrainingWifi = insertPointTrainingWifi(pointTrainingWifi);
        pointTrainingWifi.setId(tPointTrainingWifi);
        return pointTrainingWifi;
    }

    private static int updatePointTrainingWifi(PointTrainingWifi p, String whereClause, String[] whereArgs){
        saveLoadPointTrainingWifi(p);
        //int filasAfectadas = (int) db.update("PLANES", values, "id = ?", new String[]{String.valueOf(p.getId())});
        int filasAfectadas = (int) dbr.update("POINTTRAININGWIFIS", values, whereClause, whereArgs);
        return filasAfectadas;
    }

    private static void saveLoadTraining(Training t){
        loadContentValues();
        values.put("PLANE", t.getPlane());
        valuesComun(t);
    }

    private static int insertTraining(Training t){
        saveLoadTraining(t);
        int idTraining = (int) dbr.insert("TRAININGS", null, values);
        return idTraining;
    }

    public static Training insertTraining(AplicationWifi aplicationWifi, int activo){
        Training training = new Training(aplicationWifi.getPlane().getId());
        loadComun(training, null, null, System.currentTimeMillis(), activo);
        int idTraining = insertTraining(training);
        training.setId(idTraining);
        return training;
    }

    private static int updateTraining(Training p, String whereClause, String[] whereArgs){
        saveLoadTraining(p);
        //int filasAfectadas = (int) db.update("PLANES", values, "id = ?", new String[]{String.valueOf(p.getId())});
        int filasAfectadas = (int) dbr.update("TRAININGS", values, whereClause, whereArgs);
        return filasAfectadas;
    }

    private static void saveLoadWifi(Wifi w){
        loadContentValues();
        values.put("BSSID", w.getBSSID());
        values.put("capabilities", w.getCapabilities());
        values.put("frequency", w.getFrequency());
        valuesComun(w);
    }

    private static int insertWifi(Wifi w){
        saveLoadWifi(w);
        int idWifi= (int) dbr.insert("WIFIS", null, values);
        return idWifi;
    }

    private static Wifi insertWifi(ScanResult scanResult){
        Wifi wifi = new Wifi(scanResult.SSID, scanResult.BSSID, scanResult.capabilities, scanResult.frequency);
        loadComun(wifi, null, null, System.currentTimeMillis(), 1);
        int idWifi = insertWifi(wifi);
        wifi.setId(idWifi);
        return wifi;
    }

    private static int updateWifi(Wifi w, String whereClause, String[] whereArgs){
        saveLoadWifi(w);
        //int filasAfectadas = (int) db.update("PLANES", values, "id = ?", new String[]{String.valueOf(p.getId())});
        int filasAfectadas = (int) dbr.update("WIFIS", values, whereClause, whereArgs);
        return filasAfectadas;
    }

    private static void loadComun(ComunDB cm, Integer id, CharSequence description, Long datacreated, Integer active){
        cm.setId(id);
        cm.setDescription(description);
        cm.setDataCreated(datacreated);
        cm.setDataUpdateted(null);
        cm.setActive(active);
    }


    public static void initTrainingPointTraining(AplicationWifi aplicationWifi, boolean newTraining, Point size){
        try {
            dbr = wifiPosDB.getWritableDatabase();
            dbr.beginTransaction();
        if(newTraining){
            Training training = WifiPosManager.insertTraining(aplicationWifi, 0);
            aplicationWifi.setTraining(training);
        }

            Map<CharSequence,PointTraining> mapPointTrainings = new HashMap<CharSequence,PointTraining>();
            mapPointTrainings.put(Constants.X0Y0, WifiPosManager.insertPointTraining(aplicationWifi, 0.0 - 10, 0.0 - 10, 0));
            mapPointTrainings.put(Constants.X0YN, WifiPosManager.insertPointTraining(aplicationWifi, 0.0 - 10, (double) size.y - 100, 0));
            mapPointTrainings.put(Constants.XNY0, WifiPosManager.insertPointTraining(aplicationWifi, (double) size.x - 60, 0.0 - 10, 0));
            mapPointTrainings.put(Constants.XNYN, WifiPosManager.insertPointTraining(aplicationWifi, (double) size.x - 60, (double) size.y - 100, 0));
            aplicationWifi.setPointTrainings(mapPointTrainings);

        } catch (Exception e) {
            aplicationWifi.setTraining(null);
            aplicationWifi.getPointTrainings().clear();
            Log.e(TAG.toString(), "initTrainingPointTraining--->" + e.getMessage());
            throw e;
        } finally {
            values = null;
            try {
                dbr.endTransaction();
            }catch(Exception ex){
                Log.e(TAG.toString(), "initTrainingPointTraining.finally.db.endTransaction()--->" + ex.getMessage());
            }
            close(dbw, null);
            close(dbr, null);
        }
    }

    public static void savePoint(Activity act,List<ScanResult> lstScanResult, AplicationWifi aplicationWifi,
                                 PointTraining pointTraining) {
        try {

            dbr = wifiPosDB.getWritableDatabase();
            dbr.beginTransaction();
            int filasAfectadas = 0;
            if(pointTraining.getActive() == 0) {
                pointTraining.setActive(1);
                pointTraining.setDataUpdateted(System.currentTimeMillis());
                filasAfectadas = updatePointTraining(pointTraining, "id = ?", new String[]{String.valueOf(pointTraining.getId())});
            }
            //wifi comprobar si existeix un wifi amb la mateixa MAC
            List<Wifi> lstWifiSaveorUpdate = new ArrayList<Wifi>();
            List<ScanResult> lstScanResultSaveorUpdate = new ArrayList<ScanResult>();
            for (ScanResult scanResult : lstScanResult) {
                List<Wifi> lstWifi = _listWifi(" WHERE BSSID = '" + scanResult.BSSID + "' AND ACTIVE = 1");
                Wifi wifi;
                if (lstWifi.isEmpty()) {
                    wifi = insertWifi(scanResult);
                }
                else{
                    wifi = lstWifi.get(0);
                    filasAfectadas = updateWifi(wifi, "id = ?", new String[]{String.valueOf(wifi.getId())});
                    Log.i("updateWifi", "BSSID:" + wifi.getBSSID() + " filas:" + filasAfectadas);
                }
                lstWifiSaveorUpdate.add(wifi);
            }
            //pointsTrainingWifi de eixe punt
            List<PointTrainingWifi> lstPontsTrainingWifi = _listPointTrainingWifi(
                    " WHERE POINTTRAINING = " + pointTraining.getId() + " AND ACTIVE = 1");
            int i = 0;
            for (Wifi wifi:lstWifiSaveorUpdate) {
                ScanResult scanResult =lstScanResult.get(i);
                if(!lstPontsTrainingWifi.isEmpty()) {
                    boolean update = false;
                    PointTrainingWifi p;
                    for (PointTrainingWifi pointTrainingWifi : lstPontsTrainingWifi) {
                        if (pointTrainingWifi.getWifi() == wifi.getId()) {
                            pointTrainingWifi.setDataUpdateted(System.currentTimeMillis());
                            filasAfectadas = updatePointTrainingWifi(pointTrainingWifi, "id = ?", new String[]{String.valueOf(pointTrainingWifi.getId())});
                            p = pointTrainingWifi;
                            update = true;
                            break;
                        }
                    }
                    if (!update) {
                        insertPointTrainingWifi(pointTraining, wifi, scanResult.level, scanResult.timestamp);
                    }
                }
                else
                    insertPointTrainingWifi(pointTraining, wifi, scanResult.level, scanResult.timestamp);
                i++;
            }
            //int index = aplicationWifi.getPointTrainings().lastIndexOf(pointTraining);
            //aplicationWifi.getPointTrainings().get(index).setActive(1);
            activatePointTraining(aplicationWifi, pointTraining);
            if(!aplicationWifi.configureTraining(act, false)){
                Training training = aplicationWifi.getTraining();
                training.setDataUpdateted(System.currentTimeMillis());
                filasAfectadas = updateTraining(aplicationWifi.getTraining(),"id = ?", new String[]{String.valueOf(aplicationWifi.getTraining().getId())});
            }
            dbr.setTransactionSuccessful();

        } catch (Exception e) {
            aplicationWifi.getTraining().setActive(0);
            aplicationWifi.getPointTrainings().clear();
            Log.e(TAG.toString(), "savePoint--->" + e.getMessage());
            throw e;
        } finally {
            values = null;
            try {
                dbr.endTransaction();
            }catch(Exception ex){
                Log.e(TAG.toString(), "savePoint.finally.db.endTransaction()--->" + ex.getMessage());
            }
            close(dbw, null);
            close(dbr, null);
        }
    }

    private static void activatePointTraining(AplicationWifi aplicationWifi, PointTraining pointTraining){
        pointTraining.setTraining(1);
        for (Map.Entry<CharSequence, PointTraining> entryPointtraining: aplicationWifi.getPointTrainings().entrySet()) {
            PointTraining p = entryPointtraining.getValue();
            if (p.equals(pointTraining)) {
                aplicationWifi.getPointTrainings().put(entryPointtraining.getKey(), pointTraining);
                return;
            }
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
        WifiPosManager.wifiPosDB = wifiPosDB;
    }

}
