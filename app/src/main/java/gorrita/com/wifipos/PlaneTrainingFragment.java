package gorrita.com.wifipos;

import android.app.Activity;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

import gorrita.com.wifipos.db.Plane;
import gorrita.com.wifipos.db.PointTraining;
import gorrita.com.wifipos.db.Training;
import gorrita.com.wifipos.db.WifiPosManager;


public class PlaneTrainingFragment extends Fragment implements View.OnTouchListener/*, View.OnLongClickListener*/ {

    private static final CharSequence MODETRAINING = "modeTraining";

    private OnFragmentInteractionListener mListener;
    private String mParam1;
    private String mParam2;

    private View view;
    private ImageView imageView;
    private CharSequence file = "0x7f02007f";
    private Boolean training = true;


    public static PlaneTrainingFragment newInstance(Bundle arguments) {
        try{
            PlaneTrainingFragment fragment = new PlaneTrainingFragment();
            if(arguments != null) {
                fragment.setArguments(arguments);
            }
            return fragment;
        }
        catch (Exception ex){
            Log.e("PlaneTrainingFragment", "newInstance--->" + ex.getMessage());
            throw ex;
        }
    }

    public PlaneTrainingFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        try{
            super.onCreate(savedInstanceState);
            AplicationWifi aplicationWifi = (AplicationWifi)getActivity().getApplication();
            if (aplicationWifi.getHeightPoint() == 0) {
                DisplayMetrics displaymetrics = new DisplayMetrics();
                getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
                aplicationWifi.setHeightPoint((int) displaymetrics.ydpi);
                aplicationWifi.setWidthPoint((int) displaymetrics.xdpi);
            }
        }
        catch (Exception ex){
            Log.e(this.getClass().getName(), "onCreate--->" + ex.getMessage());
            throw ex;
        }
    }

    //private static final CharSequence res = "android.resource://";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try {
            // Inflate the layout for this fragment
            view = inflater.inflate(R.layout.fragment_plane_training, container, false);
            //configureTraining();
            loadImageResource();
            //imageView.setOnLongClickListener(this);
            loadPointTrainings();
            ((AplicationWifi)getActivity().getApplication()).setFirst(false);
            return view;
        }
        catch (Exception ex){
            Log.e(this.getClass().getName(), "onCreateView--->" + ex.getMessage());
            throw ex;
        }
    }

    private void configureTraining(){
        AplicationWifi aplicationWifi = (AplicationWifi)getActivity().getApplication();
        training = true;
        if(aplicationWifi.getPointTrainings()!=null && !aplicationWifi.getPointTrainings().isEmpty()) {
            if (aplicationWifi.isFirst()) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                training = prefs.getBoolean("training", true);
                return;
            }
            if (getArguments() != null) {
                training = getArguments().getBoolean(MODETRAINING.toString());
            }
        }
    }

    private void loadPointTrainings(){
        AplicationWifi aplicationWifi = (AplicationWifi)getActivity().getApplication();
        if (aplicationWifi.getPointTrainings()!=null){
            for (PointTraining pointTraining : aplicationWifi.getPointTrainings()) {
                addImagePosition(pointTraining);
            }
        }
    }
/*
    public void reloadPointTrainings(int numPointTraining){
        try{
            AplicationWifi aplicationWifi = (AplicationWifi)getActivity().getApplication();
            if (aplicationWifi.getPointTrainings().isEmpty()) {
                List<PointTraining> lstPointTrainings = WifiPosManager.listPointTraining(
                        " WHERE TRAINING = " + aplicationWifi.getTraining().getId() + " AND ACTIVE = 1");
                aplicationWifi.setPointTrainings(lstPointTrainings);
            }
            else if (aplicationWifi.getPointTrainings().size() > numPointTraining){
                PointTraining pointTraining = aplicationWifi.getPointTrainings().get(numPointTraining);
                addImagePosition(pointTraining);
                view = this.getView();
            }
        }catch (Exception ex){
            Log.e(this.getClass().getName(), "reloadPointTrainings--->" + ex.getMessage());
            throw ex;
        }
    }
*/
    private void addImagePosition(PointTraining pointTraining){
        try {
            final FrameLayout fl = (FrameLayout) view.findViewById(R.id.fragment_training);
            ImageView imageViewPoint = new ImageView(getActivity());
            //ImageView imageViewPoint = (ImageView) view.findViewById(R.id.pointtraining);
            imageViewPoint.setImageResource(android.R.drawable.star_on);
            AplicationWifi aplicationWifi = (AplicationWifi) getActivity().getApplication();
            FrameLayout.LayoutParams parms = new FrameLayout.LayoutParams
                    (aplicationWifi.getWidthPoint() / 3, aplicationWifi.getWidthPoint() / 3);
            imageViewPoint.setLayoutParams(parms);
            imageViewPoint.setX(pointTraining.getX().floatValue());
            imageViewPoint.setY(pointTraining.getY().floatValue());
            imageViewPoint.setId(pointTraining.getId());
            fl.addView(imageViewPoint);
        }catch (Exception ex){
            Log.e(this.getClass().getName(), "addImagePosition--->" + ex.getMessage());
            throw ex;
        }
    }

    private void loadImageResource(){
        try {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
            params.setMargins(0, 0, 0, 0);
            imageView = (ImageView) view.findViewById(R.id.planeTraining);
            imageView.setLayoutParams(params);
            imageView.setOnTouchListener(this);
            if (((AplicationWifi)getActivity().getApplication()).getPlane()!=null) {
                CharSequence chrFile = ((AplicationWifi) getActivity().getApplication()).getPlane().getFile();
                Integer res = null;
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
    public void onAttach(Activity act) {
        super.onAttach(act);
        try {
            mListener = (OnFragmentInteractionListener) act;
        } catch (ClassCastException e) {
            Log.e(this.getClass().getName(), "onAttach--->" + e.getMessage());
            throw new ClassCastException(act.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int id = v.getId();
        if(event.getAction() == MotionEvent.ACTION_UP) {
            //Toast.makeText(v.getContext(), event.toString(), Toast.LENGTH_SHORT).show();
            mListener.openDialog(event);
        }
        return true;
    }

    public OnFragmentInteractionListener getmListener() {
        return mListener;
    }

    public void setmListener(OnFragmentInteractionListener mListener) {
        this.mListener = mListener;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    @Nullable
    @Override
    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }
}
