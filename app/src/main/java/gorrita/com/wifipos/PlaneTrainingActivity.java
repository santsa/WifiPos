package gorrita.com.wifipos;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.Toast;

import gorrita.com.wifipos.db.PointTraining;


public class PlaneTrainingActivity extends Activity implements OnFragmentInteractionListener{

    WifiFragment wifiFragment;
    private CharSequence file = "0x7f02007f";
    PlaneTrainingFragment planeFragment;

    @Override
    public void openDialog(MotionEvent event) {
        try {

            PointTraining pointTraining = selectedPointTraining(event);
            if(pointTraining != null) {
                wifiFragment = WifiFragment.newInstance(this, event, pointTraining);
                wifiFragment.show(getFragmentManager(), "dialog");
            }
            else{
                Toast.makeText(this, getString(R.string.point_far), Toast.LENGTH_LONG).show();
            }
        }catch(Exception e){
            Log.e(this.getClass().getName(), "openDialog--->" + e.getMessage());
        }
    }

    private PointTraining selectedPointTraining(MotionEvent event){
        AplicationWifi aplicationWifi = (AplicationWifi) getApplication();
        for (PointTraining pointTraining: aplicationWifi.getPointTrainings().values()){
            if ((Math.abs(pointTraining.getX() - event.getX()) < 150)
                    && (Math.abs(pointTraining.getY() - event.getY()) < 150)) {
                return pointTraining;
            }
        }
        return null;
    }

    @Override
    public void closeDialog() {
        AplicationWifi aplicationWifi = (AplicationWifi) getApplication();
        if(aplicationWifi.getTraining().getActive() == 1) {
            invalidateOptionsMenu();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_plane_training);
            //PlaneTrainingFragment planeFragment = (PlaneTrainingFragment) getFragmentManager().findFragmentById(R.id.plane_fragment);
            newInstanceFragmentPlane();
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
        AplicationWifi aplicationWifi = (AplicationWifi) getApplication();
        if(aplicationWifi.getTraining().getActive() == 1) {
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
        ActivityOptions actOps = null;
        switch (id){

            case R.id.action_settings:
                intent = new Intent(this, PreferencesActivity.class);
                actOps = ActivityOptions.makeCustomAnimation(this, R.anim.preferences_in,
                        R.anim.preferences_out);
                startActivity(intent, actOps.toBundle());
                break;
            case R.id.accion_exit:
                intent = new Intent(this, MainActivity.class);
                actOps = ActivityOptions.makeCustomAnimation(this, R.anim.replace_inside_rigth,
                        R.anim.replace_inside_left);
                startActivity(intent, actOps.toBundle());
                break;
            case Constants.ACTION_POSITION:
                intent = new Intent(this, PlanePositionActivity.class);
                actOps = ActivityOptions.makeCustomAnimation(this,R.anim.replace_inside_left,
                        R.anim.replace_inside_rigth);
                startActivity(intent, actOps.toBundle());
                break;
            default:
                Toast.makeText(this, getString(android.R.string.unknownName), Toast.LENGTH_SHORT).show();

        }
        return super.onOptionsItemSelected(item);
    }

}
