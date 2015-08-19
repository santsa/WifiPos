package gorrita.com.wifipos;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;


public class PlaneActivity extends Activity implements OnFragmentInteractionListener{

    WifiFragment wifiFragment;

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
    public void closeDialog() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_plane);
            PlaneFragment plano = (PlaneFragment) getFragmentManager().findFragmentById(R.id.plane_fragment);
            //plano.setmListener(this);
        }
        catch(Exception e){
            Log.e(this.getClass().getName(), "onCreate--->" + e.getMessage());
        }
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
