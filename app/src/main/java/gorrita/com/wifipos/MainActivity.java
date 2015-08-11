package gorrita.com.wifipos;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends Activity {

     @Override
    protected void onCreate(Bundle savedInstanceState) {
         try{
             super.onCreate(savedInstanceState);
             setContentView(R.layout.activity_main);
             Button btnIni = (Button)findViewById(R.id.btnIni);

             this.registerReceiver(this.WifiStateChangedReceiver,
                     new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION));
             btnIni.setOnClickListener(new View.OnClickListener() {
                public void onClick(View arg0) {
                    MainActivity.this.open();
                }
             });
             Bundle extra = this.getIntent().getExtras();
             if (extra != null) {
                 if (extra.containsKey(String.valueOf(R.string.wifi)) &&
                         extra.getBoolean(String.valueOf(R.string.wifi)))
                     this.open();
             }
         }catch(Exception e){
             Log.e("MainActivity.onCreate", e.getMessage());
         }
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
                                        if (wifiManager.getWifiState() != WifiManager.WIFI_STATE_ENABLED)
                                            wifiManager.setWifiEnabled(true);
                                        Intent intent = new Intent(MainActivity.this, PlaneActivity.class);
                                        startActivity(intent);
                                    }
                                })
                         .setNeutralButton(android.R.string.cancel,
                                 new DialogInterface.OnClickListener() {
                                     @Override
                                     public void onClick(DialogInterface dialog,
                                                         int which) {
                                         finish();
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
            /*} else {
                Intent intent = new Intent(MainActivity.this, PlaneActivity.class);
                startActivity(intent);
            }*/
        }catch(Exception e){
            Log.e("MainActivity.open", e.getMessage());
        }
    }

    private BroadcastReceiver WifiStateChangedReceiver
            = new BroadcastReceiver(){

        @Override
        public void onReceive(Context context, Intent intent) {

            int extraWifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE ,
                    WifiManager.WIFI_STATE_UNKNOWN);
            switch(extraWifiState){
                case WifiManager.WIFI_STATE_DISABLED:
                    try{
                        Toast.makeText(MainActivity.this, "WIFI STATE DISABLED", Toast.LENGTH_SHORT).show();
                        AplicationWifi aplicationWifi = (AplicationWifi)context.getApplicationContext();
                        if (!aplicationWifi.isFirst()) {
                            aplicationWifi.setFirst();
                            Intent inte = new Intent(context.getApplicationContext(), PlaneActivity.class);
                            inte.putExtra(String.valueOf(R.string.wifi), true);
                            startActivity(intent);
                        }
                    }catch(Exception e){
                        Log.e("WIFI_STATE_DISABLED", e.getMessage());
                    }
                    break;
                case WifiManager.WIFI_STATE_DISABLING:
                    Toast.makeText(MainActivity.this, "WIFI_STATE_DISABLING", Toast.LENGTH_SHORT).show();
                    break;
                case WifiManager.WIFI_STATE_ENABLED:
                    Toast.makeText(MainActivity.this, "WIFI_STATE_ENABLED", Toast.LENGTH_SHORT).show();
                    break;
                case WifiManager.WIFI_STATE_ENABLING:
                    Toast.makeText(MainActivity.this, "WIFI_STATE_ENABLING", Toast.LENGTH_SHORT).show();
                    break;
                case WifiManager.WIFI_STATE_UNKNOWN:
                    Toast.makeText(MainActivity.this, "WIFI_STATE_UNKNOWN", Toast.LENGTH_SHORT).show();
                    break;
            }
        }};

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_plane, menu);
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
