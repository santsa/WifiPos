package gorrita.com.wifipos;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;



public class PlaneFragment extends Fragment implements View.OnTouchListener/*, View.OnLongClickListener*/ {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private OnFragmentInteractionListener mListener;
    private String mParam1;
    private String mParam2;

    private ImageView imageView;

    // TODO: Rename and change types and number of parameters
    public static PlaneFragment newInstance(String param1, String param2) {
        PlaneFragment fragment = new PlaneFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public PlaneFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AplicationWifi aplicationWifi = (AplicationWifi)getActivity().getApplication();
        aplicationWifi.setFirst(false);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try {
            // Inflate the layout for this fragment
            Drawable drawPlano = getResources().getDrawable(R.drawable.plane1);
            View view = inflater.inflate(R.layout.fragment_plane, container, false);
            imageView = (ImageView) view.findViewById(R.id.plane);
            imageView.setImageDrawable(drawPlano);
            imageView.setOnTouchListener(this);
            //imageView.setOnLongClickListener(this);
            return view;
        }
        catch (Exception ex){
            Log.e(this.getClass().getName(), "onCreateView--->" + ex.getMessage());
            throw ex;
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        /*if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }*/
    }

    @Override
    public void onAttach(Activity act) {
        super.onAttach(act);
        try {
            //mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            Log.e(this.getClass().getName(), e.getMessage());
            throw new ClassCastException(act.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_UP) {
            //TODO: mostrar el cuadre dialog capturant x,y y un desplegable amb els wifis
            //AplicationWifi aplicationWifi = (AplicationWifi)getActivity().getApplication();
            //WifiManager wifi = (WifiManager) this.getActivity().getSystemService(Context.WIFI_SERVICE);
            //boolean enabled = wifi.isWifiEnabled();
            Toast.makeText(v.getContext(), event.toString(), Toast.LENGTH_SHORT).show();
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
}
