package gorrita.com.wifipos;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.Toast;


public class PlaneTrainingActivity extends Activity implements OnFragmentInteractionListener{

    WifiFragment wifiFragment;
    private CharSequence file = "0x7f02007f";
    PlaneTrainingFragment planeFragment;

    @Override
    public void openDialog(MotionEvent event) {
        try {
            wifiFragment = WifiFragment.newInstance(event, "");
            wifiFragment.show(getFragmentManager(), "dialog");
        }catch(Exception e){
            Log.e(this.getClass().getName(), "openDialog--->" + e.getMessage());
        }
    }

    @Override
    public void closeDialog(int numPointTraining) {
        newInstanceFragmentPlane();
        //planeFragment.reloadPointTrainings(numPointTraining);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_plane_training);
            //PlaneTrainingFragment planeFragment = (PlaneTrainingFragment) getFragmentManager().findFragmentById(R.id.plane_fragment);
            //newInstanceFragmentPlane();
        }
        catch(Exception e){
            Log.e(this.getClass().getName(), "onCreate--->" + e.getMessage());
        }
    }

    private void newInstanceFragmentPlane(){
        Bundle arguments = new Bundle();
        planeFragment = PlaneTrainingFragment.newInstance(arguments);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(android.R.id.content, planeFragment, Constants.PLANEFRAGMENTTAG.toString());
        ft.commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        AplicationWifi aplicationWifi = (AplicationWifi) getApplication();;
        if(aplicationWifi.getPointTrainings()!=null && !aplicationWifi.getPointTrainings().isEmpty()) {
            menu.add(Menu.NONE, Constants.ACTION_POSITION, Menu.NONE, R.string.tittle_position)
                    .setIcon(android.R.drawable.ic_menu_directions);
        }
        getMenuInflater().inflate(R.menu.menu_plane_training, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Intent intent;
        switch (id){

            case R.id.action_settings:
                intent = new Intent(this, PreferencesActivity.class);
                startActivity(intent);
                break;
            case R.id.accion_exit:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case Constants.ACTION_POSITION://R.id.action_position:
                intent = new Intent(this, PlanePositionActivity.class);
                startActivity(intent);
                break;
            default:
                Toast.makeText(this, getString(android.R.string.unknownName), Toast.LENGTH_SHORT).show();

        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean p(){
        AplicationWifi aplicationWifi = (AplicationWifi) getApplication();;
        if(aplicationWifi.getPointTrainings()!=null && !aplicationWifi.getPointTrainings().isEmpty()) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            return prefs.getBoolean("training", true);
        }
        return true;
    }

}
