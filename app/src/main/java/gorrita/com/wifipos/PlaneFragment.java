package gorrita.com.wifipos;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.List;

import gorrita.com.wifipos.db.Plane;
import gorrita.com.wifipos.db.PointTraining;
import gorrita.com.wifipos.db.Training;
import gorrita.com.wifipos.db.WifiPosManager;


public class PlaneFragment extends Fragment implements View.OnTouchListener/*, View.OnLongClickListener*/ {

    public static final String TAG = "PlaneFragment";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private OnFragmentInteractionListener mListener;
    private String mParam1;
    private String mParam2;

    private View view;
    private ImageView imageView;
    private CharSequence file = "0x7f02007f";


    public static PlaneFragment newInstance(Bundle arguments) {
        try{
            PlaneFragment fragment = new PlaneFragment();
            if(arguments != null) {
                fragment.setArguments(arguments);
            }
            return fragment;
        }
        catch (Exception ex){
            Log.e("PlaneFragment", "newInstance--->" + ex.getMessage());
            throw ex;
        }
    }

    public PlaneFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        try{
            super.onCreate(savedInstanceState);
            AplicationWifi aplicationWifi = (AplicationWifi)getActivity().getApplication();
            aplicationWifi.setFirst(false);
            if (aplicationWifi.getHeightPoint() == 0) {
                DisplayMetrics displaymetrics = new DisplayMetrics();
                getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
                aplicationWifi.setHeightPoint((int) displaymetrics.ydpi);
                aplicationWifi.setWidthPoint((int) displaymetrics.xdpi);
            }

            if(aplicationWifi.getPlane() == null) {
                List<Plane> lstPlane= WifiPosManager.listPlanes(" where FILE ='" + file + "' AND ACTIVE = 1");
                if(lstPlane != null) {
                    aplicationWifi.setPlane(lstPlane.get(0));
                    List<Training> lstTraining = WifiPosManager.listTraining(
                            " WHERE PLANE = " + aplicationWifi.getPlane().getId()  + " AND ACTIVE = 1");
                    if(!lstTraining.isEmpty()) {
                        aplicationWifi.setTraining(lstTraining.get(0));
                        if(aplicationWifi.getPointTrainings()==null || aplicationWifi.getPointTrainings().isEmpty()) {
                            List<PointTraining> lstPointTrainings = WifiPosManager.listPointTraining(
                                    " WHERE TRAINING = " + aplicationWifi.getTraining().getId() + " AND ACTIVE = 1");
                            aplicationWifi.setPointTrainings(lstPointTrainings);
                        }
                    }
                }
            }
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

    //private static final CharSequence res = "android.resource://";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try {
            // Inflate the layout for this fragment
            view = inflater.inflate(R.layout.fragment_plane, container, false);
            loadImageResource();
            //imageView.setOnLongClickListener(this);
            loadPointTrainings();
            return view;
        }
        catch (Exception ex){
            Log.e(this.getClass().getName(), "onCreateView--->" + ex.getMessage());
            throw ex;
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

    private void addImagePosition(PointTraining pointTraining){
        try {
            final FrameLayout fl = (FrameLayout) view.findViewById(R.id.fragment_ini);
            ImageView imageViewPoint = new ImageView(getActivity());
            //ImageView imageViewPoint = (ImageView) view.findViewById(R.id.pointtraining);
            imageViewPoint.setImageResource(android.R.drawable.star_on);
            AplicationWifi aplicationWifi = (AplicationWifi) getActivity().getApplication();
            FrameLayout.LayoutParams parms = new FrameLayout.LayoutParams
                    (aplicationWifi.getWidthPoint() / 3, aplicationWifi.getWidthPoint() / 3);
            imageViewPoint.setLayoutParams(parms);
            imageViewPoint.setX(pointTraining.getX().floatValue());
            imageViewPoint.setY(pointTraining.getY().floatValue());
            fl.addView(imageViewPoint);
        }catch (Exception ex){
            Log.e(this.getClass().getName(), "addImagePosition--->" + ex.getMessage());
            throw ex;
        }
    }

    private void loadImageResource(){
        try {
            imageView = (ImageView) view.findViewById(R.id.plane);
            CharSequence chrFile = ((AplicationWifi)getActivity().getApplication()).getPlane().getFile();
            Integer res = null;
            //Display display = this.getActivity().getWindowManager().getDefaultDisplay();
            if (chrFile != null) {
                if (chrFile.toString().startsWith("0x"))
                    res = Integer.parseInt(chrFile.subSequence(2, chrFile.length()).toString(), 16);
                else
                    res = Integer.parseInt(chrFile.toString(), 16);
                switch (res) {
                    case 0x7f02007f:
                        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                                FrameLayout.LayoutParams.MATCH_PARENT,
                                FrameLayout.LayoutParams.MATCH_PARENT);
                        params.setMargins(0, 0, 0, 0);
                        imageView.setImageResource(R.drawable.plane1);
                        imageView.setLayoutParams(params);
                        imageView.setOnTouchListener(this);
                        break;
                    default:
                        Toast.makeText(getActivity(), getString(R.string.plane_not_exist), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                        break;
                }
            } else {
                Toast.makeText(getActivity(), getString(R.string.plane_not_exist), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
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
        if(event.getAction() == MotionEvent.ACTION_UP) {
            //Toast.makeText(v.getContext(), event.toString(), Toast.LENGTH_SHORT).show();
            mListener.openDialog(event);
        }
        return true;
    }

}
