package gorrita.com.wifipos;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

import gorrita.com.wifipos.db.Plane;
import gorrita.com.wifipos.db.WifiPosManager;


public class PlaneFragment extends Fragment implements View.OnTouchListener/*, View.OnLongClickListener*/ {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private OnFragmentInteractionListener mListener;
    private AplicationWifi aplicationWifi;
    private String mParam1;
    private String mParam2;

    private ImageView imageView;
    private CharSequence file = "0x7f02007f";

    public static PlaneFragment newInstance(String param1, String param2) {
        try{
            PlaneFragment fragment = new PlaneFragment();
            Bundle args = new Bundle();
            args.putString(ARG_PARAM1, param1);
            args.putString(ARG_PARAM2, param2);
            fragment.setArguments(args);
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
            aplicationWifi = (AplicationWifi)getActivity().getApplication();
            aplicationWifi.setFirst(false);
            if(aplicationWifi.getPlane() == null) {
                List<Plane> lstPlane= WifiPosManager.listPlanes(" where FILE ='" + file + "'");
                if(lstPlane != null)
                    aplicationWifi.setPlane(lstPlane.get(0));
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
            View view = inflater.inflate(R.layout.fragment_plane, container, false);
            imageView = (ImageView) view.findViewById(R.id.plane);
            loadImageResource();
            imageView.setOnTouchListener(this);
            //imageView.setOnLongClickListener(this);
            return view;
        }
        catch (Exception ex){
            Log.e(this.getClass().getName(), "onCreateView--->" + ex.getMessage());
            throw ex;
        }
    }

    private void loadImageResource(){
        CharSequence chrFile = aplicationWifi.getPlane().getFile();
        Integer res = null;
        if(chrFile != null) {
            if(chrFile.toString().startsWith("0x"))
                res = Integer.parseInt(chrFile.subSequence(2,chrFile.length()).toString(), 16);
            else
                res = Integer.parseInt(chrFile.toString(), 16);
            switch (res) {
                case 0x7f02007f:
                    imageView.setImageResource(R.drawable.plane1);
                    break;
                default:
                    Toast.makeText(getActivity(), getString(R.string.plane_not_exist), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                    break;
            }
        }
        else{
            Toast.makeText(getActivity(), getString(R.string.plane_not_exist), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
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
