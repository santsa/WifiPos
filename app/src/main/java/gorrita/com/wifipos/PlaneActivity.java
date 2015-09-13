package gorrita.com.wifipos;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;


public class PlaneActivity extends Activity implements OnFragmentInteractionListener{

    WifiFragment wifiFragment;
    private CharSequence file = "0x7f02007f";
    PlaneFragment planeFragment;

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
            setContentView(R.layout.activity_plane);
            //PlaneFragment planeFragment = (PlaneFragment) getFragmentManager().findFragmentById(R.id.plane_fragment);
            newInstanceFragmentPlane();
        }
        catch(Exception e){
            Log.e(this.getClass().getName(), "onCreate--->" + e.getMessage());
        }
    }

    private void newInstanceFragmentPlane(){
        String id = "planeFragment ";
        Bundle arguments = new Bundle();
        arguments.putString("id", id);
        planeFragment = PlaneFragment.newInstance(arguments);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(android.R.id.content, planeFragment, PlaneFragment.TAG);
        ft.commit();
    }

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
