package gorrita.com.wifipos;

import android.app.Application;
import android.util.Log;

import gorrita.com.wifipos.db.Plane;

/**
 * Created by salva on 06/08/15.
 */


public class AplicationWifi extends Application {

    private boolean wifi;
    private boolean first;
    private Plane plane;
    //private WifiBD wifiBD;
    //private Activi

    @Override
    public void onCreate(){
        try {
            super.onCreate();
            wifi = true;
            first = true;
            plane = null;
            Log.i(this.getClass().getName(), "onCreate");
        }catch (Exception e){
            Log.e(this.getClass().getName(), "onCreate--->" + e.getMessage());
        }
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

}
