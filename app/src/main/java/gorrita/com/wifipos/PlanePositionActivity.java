package gorrita.com.wifipos;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;

public class PlanePositionActivity extends Activity implements OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plane_position);
    }


    @Override
    public void openDialog(MotionEvent event) {

    }

    @Override
    public void closeDialog() {

    }
}
