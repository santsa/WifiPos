package gorrita.com.wifipos;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import gorrita.com.wifipos.db.PointTraining;

/**
 * Created by sgorrita on 22/09/15.
 */
public class Comun {

    public static byte[] convertToBytes(Object object) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = new ObjectOutputStream(bos);
        out.writeObject(object);
        return bos.toByteArray();
    }

    public static Object convertFromBytes(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInput in = new ObjectInputStream(bis);
        return in.readObject();
    }

    public static boolean configureTraining(Activity activity, boolean prefer){
        AplicationWifi aplicationWifi = (AplicationWifi) activity.getApplication();
        if(aplicationWifi.getPointTrainings()!=null && aplicationWifi.getPointTrainings().size() >= 4) {
            for (PointTraining pointTraining:aplicationWifi.getPointTrainings()){
                if(pointTraining.getActive() == 0) {
                    aplicationWifi.getTraining().setActive(0);
                    return true;
                }
            }
            aplicationWifi.getTraining().setActive(1);
            if (prefer) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
                boolean pref = prefs.getBoolean("training", false);
                return pref;
            }
            return false;
        }
        aplicationWifi.getTraining().setActive(0);
        return true;
    }

}
