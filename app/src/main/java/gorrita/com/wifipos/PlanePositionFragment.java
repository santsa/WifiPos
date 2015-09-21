package gorrita.com.wifipos;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;
import android.view.View;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gorrita.com.wifipos.db.*;


public class PlanePositionFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private View view;
    private ImageView imageView;
    private ImageView imagePosition;
    private UpdatePosition updatePosition;
    private WeakReference<UpdatePosition> asyncTaskWeakRef;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PlanePositionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PlanePositionFragment newInstance(String param1, String param2) {
        PlanePositionFragment fragment = new PlanePositionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public PlanePositionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        try{
            super.onCreate(savedInstanceState);
            setHasOptionsMenu(true);
            if (getArguments() != null) {
                mParam1 = getArguments().getString(ARG_PARAM1);
                mParam2 = getArguments().getString(ARG_PARAM2);
            }
        }
        catch (Exception ex){
            Log.e(this.getClass().getName(), "onCreate--->" + ex.getMessage());
            throw ex;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try {
            // Inflate the layout for this fragment
            view = inflater.inflate(R.layout.fragment_plane_position, container, false);
            loadImageResource();
            ((AplicationWifi)getActivity().getApplication()).setFirst(false);
            imagePosition = (ImageView) view.findViewById(R.id.positionPlane);
            //updatePosition = new UpdatePosition();
            //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
            //CharSequence chrDefaultValueRefesh = getText(R.string.default_number_refresh);
            //Float defaultValueRefesh = prefs.getFloat("refresh", Float.valueOf(chrDefaultValueRefesh.toString()));
            //updatePosition.execute((defaultValueRefesh*1000));
            setRetainInstance(true);
            startNewAsyncTask();
            return view;
        }
        catch (Exception ex){
            Log.e(this.getClass().getName(), "onCreateView--->" + ex.getMessage());
            throw ex;
        }
    }

    private void startNewAsyncTask() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        CharSequence chrDefaultValueRefesh = getText(R.string.default_number_refresh);
        Float valueRefesh = Float.valueOf(prefs.getString("refresh", chrDefaultValueRefesh.toString()));
        if (!isAsyncTaskPendingOrRunning()) {
            updatePosition = new UpdatePosition(this);
            asyncTaskWeakRef = new WeakReference<UpdatePosition>(updatePosition);
            asyncTaskWeakRef.get().execute((valueRefesh * 1000));
        }
        else{
            asyncTaskWeakRef.get().exit = false;
            asyncTaskWeakRef.get().execute((valueRefesh * 1000));
        }
    }

    private boolean isAsyncTaskPendingOrRunning() {
        return this.asyncTaskWeakRef != null &&
                this.asyncTaskWeakRef.get() != null &&
                !this.asyncTaskWeakRef.get().getStatus().equals(AsyncTask.Status.FINISHED);
    }

    private void loadImageResource(){
        try {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
            params.setMargins(0, 0, 0, 0);
            imageView = (ImageView) view.findViewById(R.id.planePosition);
            imageView.setLayoutParams(params);
            if (((AplicationWifi)getActivity().getApplication()).getPlane()!=null) {
                CharSequence chrFile = ((AplicationWifi) getActivity().getApplication()).getPlane().getFile();
                Integer res = null;
                //Display display = this.getActivity().getWindowManager().getDefaultDisplay();
                if (chrFile != null) {
                    if (chrFile.toString().startsWith("0x"))
                        res = Integer.parseInt(chrFile.subSequence(2, chrFile.length()).toString(), 16);
                    else
                        res = Integer.parseInt(chrFile.toString(), 16);
                    switch (res) {
                        case 0x7f02007f:
                            imageView.setImageResource(R.drawable.plane1);
                            return;
                    }
                }
            }
            Toast.makeText(getActivity(), getString(R.string.plane_not_exist), Toast.LENGTH_SHORT).show();
            imageView.setImageResource(android.R.drawable.ic_delete);
        }catch (Exception e){
            Log.e(this.getClass().getName(), "loadImageResource--->" + e.getMessage());
            throw e;
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        try {
            super.onDetach();
            mListener = null;
            asyncTaskWeakRef.get().exit = true;
            //updatePosition.exit = true;
            asyncTaskWeakRef.get().cancel(true);
            //updatePosition.cancel(true);
        } catch (ClassCastException e) {
            Log.e(this.getClass().getName(), "onDetach--->" + e.getMessage());;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_plane_position, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent;
        switch (id) {

            case R.id.accion_position_exit:
                intent = new Intent(this.getActivity(), MainActivity.class);
                startActivity(intent);
                onDetach();
                break;
            case R.id.accion_position_training:
                intent = new Intent(this.getActivity(), PlaneTrainingActivity.class);
                startActivity(intent);
                onDetach();
                break;
            default:
                Toast.makeText(this.getActivity(), getString(android.R.string.unknownName), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    public WeakReference<UpdatePosition> getAsyncTaskWeakRef() {
        return asyncTaskWeakRef;
    }

    public void setAsyncTaskWeakRef(WeakReference<UpdatePosition> asyncTaskWeakRef) {
        this.asyncTaskWeakRef = asyncTaskWeakRef;
    }

    private class UpdatePosition extends AsyncTask<Float, Float, Void> {

        AplicationWifi aplicationWifi;
        Map<PointTraining, List<PointTrainingWifi>> mapPointsTraining;
        Float x;
        Float y;
        Boolean exit;

        private WeakReference<Fragment> fragmentWeakRef;

        private UpdatePosition (PlanePositionFragment fragment) {
            this.fragmentWeakRef = new WeakReference<Fragment>(fragment);
        }

        @Override
        protected  void onPreExecute(){
            try{
                mapPointsTraining = new HashMap<PointTraining, List<PointTrainingWifi>>();
                exit = false;
                aplicationWifi = (AplicationWifi) getActivity().getApplication();
                List<PointTraining> lstPoinsTraining = aplicationWifi.getPointTrainings();
                for(PointTraining pointTraining: lstPoinsTraining) {
                    CharSequence where = " WHERE POINTTRAINING = " + pointTraining.getId();
                    List<PointTrainingWifi> listPointTrainingWifi = WifiPosManager.listPointTrainingWifi(where);
                    if (!listPointTrainingWifi.isEmpty())
                        mapPointsTraining.put(pointTraining, listPointTrainingWifi);
                }
                //SystemClock.sleep(5000);
            } catch (ClassCastException e) {
                Log.e(this.getClass().getName(), "onPreExecute--->" + e.getMessage());
            }
        }

        @Override
        protected Void doInBackground(Float... params) {
            while (!exit){
                SystemClock.sleep((long)Math.round(params[0]));
                publishProgress(x,y);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Float... params){
            x = imagePosition.getX();
            y = imagePosition.getY();
            if (x+20 < 0)
                imagePosition.setX(view.getWidth());
            else if (x+20 > view.getWidth())
                imagePosition.setX(0);
            else
                imagePosition.setX(x+20);
            if (y + 20 < 0)
                imagePosition.setY(view.getHeight());

            if (y + 20 > view.getHeight())
                imagePosition.setY(0);
            else
                imagePosition.setY(y + 20);
        }

        @Override
        protected void onPostExecute(Void response) {
            super.onPostExecute(response);
            if (this.fragmentWeakRef.get() != null) {

            }
        }

    }

}
