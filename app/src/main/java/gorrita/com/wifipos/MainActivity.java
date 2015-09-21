package gorrita.com.wifipos;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import gorrita.com.wifipos.db.Plane;
import gorrita.com.wifipos.db.PointTraining;
import gorrita.com.wifipos.db.Training;
import gorrita.com.wifipos.db.WifiPosManager;


public class MainActivity extends Activity {

    private AplicationWifi aplicationWifi;

     @Override
    protected void onCreate(Bundle savedInstanceState) {
         try{
             super.onCreate(savedInstanceState);
             setContentView(R.layout.activity_main);
             WifiPosManager.intDB(this);
             //Plane p = WifiPosManager.listPlanes();
             this.registerReceiver(this.WifiStateChangedReceiver,
                     new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION));
             configureButtons();
             Bundle extra = this.getIntent().getExtras();
             if (extra != null) {
                 if (extra.containsKey(String.valueOf(R.string.wifi)) &&
                         extra.getBoolean(String.valueOf(R.string.wifi)))
                     this.open();
             }
         }catch(Exception e){
             Log.e(this.getClass().getName(),"onCreate--->" + e.getMessage());
         }
    }

        private void configureButtons(){
            Button btnIni = (Button)findViewById(R.id.btnIni);
            btnIni.setOnClickListener(new View.OnClickListener() {
                public void onClick(View arg0) {
                    MainActivity.this.open();
                }
            });
            Button btnSettings = (Button)findViewById(R.id.settings);
            btnSettings.setOnClickListener(new View.OnClickListener() {
                public void onClick(View arg0) {
                    Intent i = new Intent(MainActivity.this, PreferencesActivity.class);
                    startActivity(i);
                }
            });
            Button btnAbout = (Button)findViewById(R.id.about);
            btnAbout.setOnClickListener(new View.OnClickListener() {
                public void onClick(View arg0) {
                    Intent i = new Intent(MainActivity.this, AboutActivity.class);
                    startActivity(i);
                }
            });
            Button btnExit = (Button)findViewById(R.id.exit);
            btnExit.setOnClickListener(new View.OnClickListener() {
                public void onClick(View arg0) {
                    MainActivity.this.finish();
                }
            });
        }


    private void open(){
        try {
            //AplicationWifi aplicationWifi = (AplicationWifi)this.getApplication();
            //ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            //NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            final WifiManager wifiManager = (WifiManager) getBaseContext().getSystemService(Context.WIFI_SERVICE);

            //if (!wifi.isConnected()) {
            //if(wifiManager.getWifiState() != WifiManager.WIFI_STATE_ENABLED){
                 AlertDialog.Builder builder = new AlertDialog.Builder(this)
                        .setTitle(R.string.wifi)
                        .setMessage(R.string.msgWifi)
                        .setPositiveButton(R.string.enableWifi,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        //Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                                        //startActivity(intent);
                                        try {
                                            if (wifiManager.getWifiState() != WifiManager.WIFI_STATE_ENABLED)
                                                wifiManager.setWifiEnabled(true);
                                            if (initPlane()) {
                                                Intent intent;
                                                if (configureTraining()) {
                                                    intent = new Intent(MainActivity.this, PlaneTrainingActivity.class);
                                                } else {
                                                    intent = new Intent(MainActivity.this, PlanePositionActivity.class);
                                                }
                                                startActivity(intent);
                                            }
                                        }
                                        catch (Exception ex){
                                            Log.e(this.getClass().getName(),"AlertDialog.Builder.onClick-->" + ex.getMessage());
                                        }
                                    }
                                })
                         .setNeutralButton(android.R.string.cancel,
                                 new DialogInterface.OnClickListener() {
                                     @Override
                                     public void onClick(DialogInterface dialog,
                                                         int which) {
                                     }
                                 })
                        .setNegativeButton(R.string.disableWifi,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        wifiManager.setWifiEnabled(false);
                                        Toast.makeText(MainActivity.this, "WIFI STATE DISABLED", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                });
                builder.show();
        }catch(Exception e){
            Log.e(this.getClass().getName(),"open--->" + e.getMessage());
        }
    }

    private CharSequence file = "0x7f02007f";

    private boolean initPlane(){
        aplicationWifi = (AplicationWifi) getApplication();
        if(aplicationWifi.getPlane() == null) {
            List<Plane> lstPlane = WifiPosManager.listPlanes(" where FILE ='" + file + "' AND ACTIVE = 1");
            if (lstPlane == null && lstPlane.isEmpty())
                return true;
            aplicationWifi.setPlane(lstPlane.get(0));
        }
        if(aplicationWifi.getPlane() != null) {
            List<Training> lstTraining = WifiPosManager.listTraining(
                    " WHERE PLANE = " + aplicationWifi.getPlane().getId()  + " AND ACTIVE = 1");
            if(!lstTraining.isEmpty()) {
                aplicationWifi.setTraining(lstTraining.get(0));
                if(aplicationWifi.getPointTrainings()==null || aplicationWifi.getPointTrainings().isEmpty()) {
                    List<PointTraining> lstPointTrainings = WifiPosManager.listPointTraining(
                            " WHERE TRAINING = " + aplicationWifi.getTraining().getId() + " AND ACTIVE = 1");
                    aplicationWifi.setPointTrainings(lstPointTrainings);
                }
                else{
                    insertPointTraining(false);
                }
            }
            else{
                insertPointTraining(true);
            }
        }
        else{
            Toast.makeText(this, getString(R.string.plane_not_exist), Toast.LENGTH_SHORT).show();
            return false;
        }
        //}
        return true;
    }

    private void insertPointTraining(boolean newTraining){
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        WifiPosManager.initTrainingPointTraining(aplicationWifi, newTraining, size);
    }

    private boolean configureTraining(){
        aplicationWifi = (AplicationWifi) getApplication();
        if (aplicationWifi.getTraining().getActive() == 0)
            return true;
        if(aplicationWifi.getPointTrainings()!=null && aplicationWifi.getPointTrainings().size() >= 4) {
            for (PointTraining pointTraining:aplicationWifi.getPointTrainings()){
                if(pointTraining.getActive() == 0)
                    return true;
            }
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            return prefs.getBoolean("training", true);
        }
        return true;
    }

    @Override
    protected void onPause() {
        try {
            super.onPause();
            //unregisterReceiver(WifiStateChangedReceiver);
        }catch(Exception e){
            Log.e(this.getClass().getName(),"onPause--->" + e.getMessage());
        }
    }

    @Override
    protected void onDestroy() {
        try {
            super.onDestroy();
            unregisterReceiver(WifiStateChangedReceiver);
        }catch(Exception e){
            Log.e(this.getClass().getName(),"onDestroy--->" + e.getMessage());
        }
    }

    private BroadcastReceiver WifiStateChangedReceiver = new BroadcastReceiver(){

        @Override
        public void onReceive(Context context, Intent intent) {
            try{
                int extraWifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE ,
                        WifiManager.WIFI_STATE_UNKNOWN);
                switch(extraWifiState){
                    case WifiManager.WIFI_STATE_DISABLED:
                        try{
                            //Toast.makeText(MainActivity.this, "WIFI STATE DISABLED", Toast.LENGTH_SHORT).show();
                            aplicationWifi = (AplicationWifi)context.getApplicationContext();
                            if (!aplicationWifi.isFirst()) {
                                aplicationWifi.setFirst(true);
                                Intent i = new Intent(context,MainActivity.class);
                                i.putExtra(String.valueOf(R.string.wifi), true);
                                //i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                context.startActivity(i);
                            }
                        }catch(Exception e){
                            Log.e(this.getClass().getName(),getString(R.string.wifi_state_disabled) + "--->" + e.getMessage());
                        }
                        break;
                    case WifiManager.WIFI_STATE_DISABLING:
                        Toast.makeText(MainActivity.this, getString(R.string.wifi_state_disabling), Toast.LENGTH_SHORT).show();
                        break;
                    case WifiManager.WIFI_STATE_ENABLED:
                        Toast.makeText(MainActivity.this, getString(R.string.wifi_state_enabled), Toast.LENGTH_SHORT).show();
                        break;
                    case WifiManager.WIFI_STATE_ENABLING:
                        Toast.makeText(MainActivity.this, getString(R.string.wifi_state_enabling), Toast.LENGTH_SHORT).show();
                        break;
                    case WifiManager.WIFI_STATE_UNKNOWN:
                        Toast.makeText(MainActivity.this, getString(R.string.wifi_state_unknown), Toast.LENGTH_SHORT).show();
                        break;
                }
            }catch(Exception e){
                Log.e(this.getClass().getName(),"onReceive--->" + e.getMessage());
            }
        }};

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_plane_training, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
