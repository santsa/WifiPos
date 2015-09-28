package gorrita.com.wifipos;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import gorrita.com.wifipos.db.Plane;
import gorrita.com.wifipos.db.PointTraining;
import gorrita.com.wifipos.db.Training;

/**
 * Created by salva on 06/08/15.
 */


public class AplicationWifi extends Application {

    private boolean wifi;
    private boolean first;
    private Plane plane;
    private Training training;
    private Map<CharSequence,PointTraining> pointTrainings;
    private int heightPoint;
    private int widthPoint;
    private Point size;

    @Override
    public void onCreate(){
        try {
            super.onCreate();
            wifi = true;
            first = true;
            plane = null;
            training = null;
            pointTrainings = null;
            heightPoint = 0;
            widthPoint = 0;
            Log.i(this.getClass().getName(), "onCreate");
        }catch (Exception e){
            Log.e(this.getClass().getName(), "onCreate--->" + e.getMessage());
        }
    }

    public byte[] convertToBytes(Object object) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = new ObjectOutputStream(bos);
        out.writeObject(object);
        return bos.toByteArray();
    }

    public Object convertFromBytes(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInput in = new ObjectInputStream(bis);
        return in.readObject();
    }

    public boolean configureTraining(Activity activity,boolean prefer){
        if(pointTrainings!=null && pointTrainings.size() >= 4) {
            for (PointTraining pointTraining:pointTrainings.values()){
                if(pointTraining.getActive() == 0) {
                    training.setActive(0);
                    return true;
                }
            }
            training.setActive(1);
            if (prefer) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
                boolean pref = prefs.getBoolean("training", false);
                return pref;
            }
            return false;
        }
        training.setActive(0);
        return true;
    }

    public List<ScanResult> scanAVGScanResults(WifiManager wifi, int scans){

        List<List<ScanResult>> listsWifiScanAll =  new ArrayList<List<ScanResult>>();
        for (int i = 0; i < scans; i++) {
            wifi.startScan();
            List<ScanResult> listWifiScan = wifi.getScanResults();
            if (listWifiScan != null)
                listsWifiScanAll.add(i, listWifiScan);
            else
                return null;
        }

        List<ScanResult> listWifiScanAll = listsWifiScanAll.get(0);
        for (ScanResult sc : listWifiScanAll) {
            int level = Math.abs(sc.level);
            for (int i = 1; i < listsWifiScanAll.size(); i++) {
                level += scanResultLevel(sc, listsWifiScanAll.get(i));
            }
            sc.level = (-level / listsWifiScanAll.size());
        }
        return listsWifiScanAll.get(0);
    }

    private int scanResultLevel (ScanResult sc, List<ScanResult> listWifiScan){
        for(ScanResult s: listWifiScan){
            if(sc.BSSID.equals(s.BSSID)) {
                if(Math.abs(s.level) < 0)
                    return Math.abs(s.level);
                else
                    return Math.abs(sc.level);
            }
        }
        return Math.abs(sc.level);
    }

    public boolean isWifi() {
        return wifi;
    }

    public void setWifi(boolean wifi) {
        this.wifi = wifi;
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public Plane getPlane() {
        return plane;
    }

    public void setPlane(Plane plane) {
        this.plane = plane;
    }

    public Training getTraining() {
        return training;
    }

    public void setTraining(Training training) {
        this.training = training;
    }

    public Map<CharSequence, PointTraining> getPointTrainings() {
        return pointTrainings;
    }

    public void setPointTrainings(Map<CharSequence, PointTraining> pointTrainings) {
        this.pointTrainings = pointTrainings;
    }

    public int getHeightPoint() {
        return heightPoint;
    }

    public void setHeightPoint(int heightPoint) {
        this.heightPoint = heightPoint;
    }

    public int getWidthPoint() {
        return widthPoint;
    }

    public void setWidthPoint(int widthPoint) {
        this.widthPoint = widthPoint;
    }

    public Point getSize() {
        return size;
    }

    public void setSize(Point size) {
        this.size = size;
    }
}
