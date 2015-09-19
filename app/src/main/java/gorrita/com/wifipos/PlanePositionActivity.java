package gorrita.com.wifipos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.Toast;

public class PlanePositionActivity extends Activity implements OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plane_position);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_plane_position, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent;
        switch (id) {

            case R.id.accion_position_exit:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.accion_position_training:
                intent = new Intent(this, PlaneTrainingActivity.class);
                startActivity(intent);
                break;
            default:
                Toast.makeText(this, getString(android.R.string.unknownName), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void openDialog(MotionEvent event) {

    }

    @Override
    public void closeDialog(int numPointTraining) {

    }
}
