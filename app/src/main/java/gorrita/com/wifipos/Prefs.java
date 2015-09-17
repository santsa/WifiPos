package gorrita.com.wifipos;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by sgorrita on 17/09/15.
 */
public class Prefs extends PreferenceActivity {

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.addPreferencesFromResource(R.xml.prefs);
    }
}
