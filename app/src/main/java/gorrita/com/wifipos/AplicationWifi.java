package gorrita.com.wifipos;

import android.app.Application;
import android.util.Log;

/**
 * Created by salva on 06/08/15.
 */


public class AplicationWifi extends Application {

    private boolean wifi;
    private boolean first;
    //private WifiBD wifiBD;
    //private Activi

    public void onCreate(){
        wifi = true;
        first = true;
        Log.i(this.getClass().getName(), "onCreate");
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
/*
    public WifiBD getWifiBD() {
        return wifiBD;
    }

    public void setWifiBD(WifiBD wifiBD) {
        this.wifiBD = wifiBD;
    }
*/
}
