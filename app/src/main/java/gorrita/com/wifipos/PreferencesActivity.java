package gorrita.com.wifipos;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by sgorrita on 17/09/15.
 */
public class PreferencesActivity extends PreferenceActivity {

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new PreferencesFragment()).commit();
    }
}
