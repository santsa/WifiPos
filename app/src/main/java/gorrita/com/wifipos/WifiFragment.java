package gorrita.com.wifipos;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.net.DhcpInfo;
import android.net.Uri;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

/**
 * A fragment with a Google +1 button.
 * Activities that contain this fragment must implement the
 * {@link WifiFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WifiFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WifiFragment extends DialogFragment {

    //private OnFragmentInteractionListener mListener;
    private EditText mEditText;
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WifiFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WifiFragment newInstance(String param1, String param2) {
        WifiFragment fragment = new WifiFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public WifiFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wifi, container);
        mEditText = (EditText) view.findViewById(R.id.txt_your_name);
        String title = getArguments().getString("title", "Enter Name");
        getDialog().setTitle(title);
        // Show soft keyboard automatically
        mEditText.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        listWifiDhcp();
        listWifiScan();
        return view;

    }

    private void listWifiDhcp(){
        WifiManager wifi = (WifiManager) this.getActivity()
                .getSystemService(Context.WIFI_SERVICE);
        DhcpInfo dhcp = wifi.getDhcpInfo();
        byte[] quads = new byte[4];
        for (int k = 0; k < 4; k++)
            quads[k] = (byte) ((dhcp.ipAddress >> k * 8) & 0xFF);
        String ownIp = "";
        try {
            ownIp = InetAddress.getByAddress(quads).getHostAddress();
        } catch (UnknownHostException uhe) {
            Log.e("MultiCoopGameActivity", "UnknownHostException occured: "
                    + uhe.getMessage());
        }
    }

    private void listWifiScan(){
        WifiManager wifi = (WifiManager) this.getActivity()
                .getSystemService(Context.WIFI_SERVICE);
        try {
            List<ScanResult> list=wifi.getScanResults();
            StringBuffer scanList=new StringBuffer();
            if (list != null) {
                for (int i=list.size() - 1; i >= 0; i--) {
                    final ScanResult scanResult=list.get(i);
                    if (scanResult == null) {
                        continue;
                    }
                    if (TextUtils.isEmpty(scanResult.SSID)) {
                        continue;
                    }
                    scanList.append(scanResult.SSID + " ");
                }
            }
            mEditText.setText(scanList);
        } catch (Exception uhe) {
            Log.e("MultiCoopGameActivity", "UnknownHostException occured: "
                    + uhe.getMessage());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        /*if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }*/
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            //mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }

}
