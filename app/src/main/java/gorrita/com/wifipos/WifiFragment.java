package gorrita.com.wifipos;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.net.Uri;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class WifiFragment extends DialogFragment {

    //private OnFragmentInteractionListener mListener;
    private EditText mEditText;
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_EVENT = "event";
    private static final String ARG_PARAM2 = "param2";

    private MotionEvent event;
    private String mParam2;

    private EditText editWifiX;
    private EditText editWifiY;
    private ListView listViewWifi;
    private List<ScanResult> listWifiScan;
    private List<String> listWifi;
    private Button btnSaveWifi;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param event Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WifiFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WifiFragment newInstance(MotionEvent event, String param2) {
        try{
            WifiFragment fragment = new WifiFragment();
            Bundle args = new Bundle();

            args.putParcelable(ARG_EVENT, event);
            args.putString(ARG_PARAM2, param2);
            fragment.setArguments(args);
            return fragment;
        } catch (ClassCastException e) {
            Log.e("WifiFragment", "newInstance--->" + e.getMessage());
            throw e;
        }
    }

    public WifiFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            event = getArguments().getParcelable(ARG_EVENT);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try{
            // Inflate the layout for this fragment
            View view = inflater.inflate(R.layout.fragment_wifi, container);
            //String title = getArguments().getString("title", "Enter Name");
            getDialog().setTitle(R.string.Title_new_point);
            // Show soft keyboard automatically
            getDialog().getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
            init(view);
            listWifiScan();
            loadWifiScan(view);
            initButtonSave(view);
            return view;
        } catch (ClassCastException e) {
            Log.e(this.getClass().getName(), "onCreateView--->" + e.getMessage());
            throw e;
        }
    }

    private void init(View view){
        try{
            editWifiX = (EditText)view.findViewById(R.id.edit_x);
            Float point = event.getX();
            editWifiX.setText(point.toString());
            editWifiY = (EditText)view.findViewById(R.id.edit_y);
            point = event.getY();
            editWifiY.setText(point.toString());
            listViewWifi = (ListView)view.findViewById(R.id.list_wifi);
            listViewWifi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    onListItemClick(view, position, id);
                }
            });
        } catch (ClassCastException e) {
            Log.e(this.getClass().getName(), "init--->" + e.getMessage());
            throw e;
        }
    }

    private void initButtonSave(View view){
        if (listWifiScan != null) {
            btnSaveWifi = (Button) view.findViewById(R.id.btn_Save_wifi);
            btnSaveWifi.setText(android.R.string.ok);
            btnSaveWifi.setOnClickListener(new View.OnClickListener() {
                public void onClick(View arg0) {
                    onSelectedChecksClick();
                }
            });
        }
        else
            btnSaveWifi.setVisibility(View.GONE);
    }


    private void onListItemClick(View v,int position,long id){
        try {
            CheckedTextView item = (CheckedTextView) v;
            item.setChecked(!item.isChecked());
            if (position == 0)
                onAllChecksClick(v, !item.isChecked());
        }catch (Exception ex){
            Log.e(this.getClass().getName(), "onListItemClick--->" + ex.getMessage());
        }
    }

    //TODO: SELECTED ALL
    public void onAllChecksClick (View view, boolean selected) {
        try {
            int count = listViewWifi.getCount();
            for (int i = 0; i < count; i++) {
                CheckedTextView item = (CheckedTextView) listViewWifi.getSelectedItem();
                listViewWifi.setItemChecked(i, selected);
            }
        }catch (Exception ex){
            Log.e(this.getClass().getName(), "onAllChecksClick--->" + ex.getMessage());
        }
    }

    //TODO: SAVE ALL
    public void onSelectedChecksClick () {
        try{
            SparseBooleanArray resultArray = listViewWifi.getCheckedItemPositions();
            int size = resultArray.size();
            for (int i = 0; i < size; i++)
                if (resultArray.valueAt(i))
                    Log.e("CodecTestActivity", listViewWifi.getAdapter().getItem(resultArray.keyAt(i)).toString());
        }catch (Exception ex){
            Log.e(this.getClass().getName(), "onSelectedChecksClick--->" + ex.getMessage());
        }
    }

    private void listWifiScan(){
        WifiManager wifi = (WifiManager) this.getActivity()
                .getSystemService(Context.WIFI_SERVICE);
        try {
            listWifiScan = wifi.getScanResults();
            listWifi = new ArrayList<String>();
            if (listWifiScan != null) {
                StringBuffer strWifi = new StringBuffer();
                strWifi.append(R.string.select_all);
                listWifi.add(strWifi.toString());
                for (int i = listWifiScan.size() - 1; i >= 0; i--) {
                    strWifi = new StringBuffer();
                    final ScanResult scanResult = listWifiScan.get(i);
                    /*if (scanResult == null) {
                        continue;
                    }*/
                    if (TextUtils.isEmpty(scanResult.SSID))
                        strWifi.append("....");
                    else
                        strWifi.append(scanResult.SSID);
                    strWifi.append(scanResult.BSSID + " " + scanResult.level + " dBm");
                    listWifi.add(strWifi.toString());
                }
            }
        }catch (Exception ex){
                Log.e(this.getClass().getName(), "listWifiScan--->" + ex.getMessage());
        }
    }

    private void loadWifiScan(View view){
        try {
            ArrayAdapter<String> adaptador =
                    new ArrayAdapter<String>(getActivity(),
                            android.R.layout.simple_list_item_checked, listWifi);
            listViewWifi.setAdapter(adaptador);
        }catch (Exception ex){
            Log.e(this.getClass().getName(), "loadWifiScan--->" + ex.getMessage());
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
            Log.e(this.getClass().getName(), "onAttach--->" + e.getMessage());
            throw e;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //mListener = null;
    }

}
