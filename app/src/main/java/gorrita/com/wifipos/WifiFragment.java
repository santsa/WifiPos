package gorrita.com.wifipos;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
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
    private List<ScanResult> listWifiScanSave;
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
            getDialog().setCancelable(true);
            getDialog().setCanceledOnTouchOutside(true);
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
            listViewWifi.setEmptyView(view.findViewById(R.id.empty_list_wifi));
            listViewWifi.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
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
            btnSaveWifi.setEnabled(false);
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
            if (position == 0) {
                onAllChecksClick(v, item.isChecked());
            }
            else{
                //item.toggle();
                boolean check = item.isChecked();
                item.setChecked(item.isChecked());
            }
            btnSaveWifiEnabledDisabbled();

        }catch (Exception ex){
            Log.e(this.getClass().getName(), "onListItemClick--->" + ex.getMessage());
        }
    }

    //TODO: COMPROBAR QUE NO SELECCIONE ELS QUE TINGUEN EL ERROR O LA POTENCIA SIGA 0
    public void onAllChecksClick (View view, boolean selected) {
        try {
            int count = listViewWifi.getCount();
            for (int i = 0; i < count; i++) {
                //CheckedTextView item = (CheckedTextView) listViewWifi.getSelectedItem();
                listViewWifi.setItemChecked(i, selected);
            }
        }catch (Exception ex){
            Log.e(this.getClass().getName(), "onAllChecksClick--->" + ex.getMessage());
        }
    }

    //TODO: ELIMINAR TOTS ELS WIFIS QUE NO ESTIGUEN SELECCIONATS
    private void btnSaveWifiEnabledDisabbled() {
        try{
            SparseBooleanArray resultArray = listViewWifi.getCheckedItemPositions();
            int size = resultArray.size();
            for (int i = 0; i < size; i++) {
                if (resultArray.valueAt(i)) {
                    btnSaveWifi.setEnabled(true);
                    Log.i("CodecTestActivity", listViewWifi.getAdapter().getItem(resultArray.keyAt(i)).toString());
                    return;
                }
            }
            btnSaveWifi.setEnabled(false);
        }catch (Exception ex){
            Log.e(this.getClass().getName(), "onSelectedChecksClick--->" + ex.getMessage());
        }
    }

    //TODO: SAVE ALL
    public void onSelectedChecksClick () {
        try{
            SparseBooleanArray resultArray = listViewWifi.getCheckedItemPositions();
            listWifiScanSave = new ArrayList<ScanResult>();
            int size = resultArray.size();
            for (int i = 1; i < size; i++)
                if (resultArray.valueAt(i)) {
                    listWifiScanSave.add(listWifiScan.get(resultArray.keyAt(i)));
                    Log.i("CodecTestActivity", listViewWifi.getAdapter().getItem(resultArray.keyAt(i)).toString());
                }
        }catch (Exception ex){
            Log.e(this.getClass().getName(), "onSelectedChecksClick--->" + ex.getMessage());
        }
    }

    private void listWifiScan(){
        WifiManager wifi = (WifiManager) this.getActivity()
                .getSystemService(Context.WIFI_SERVICE);
        try {
            List<ScanResult> listWifiScanAll = wifi.getScanResults();
            listWifi = new ArrayList<String>();
            if (listWifiScanAll != null) {
                StringBuffer strWifi;
                listWifiScan = new ArrayList<ScanResult>();
                for (ScanResult scanResult:listWifiScanAll) {
                    if (scanResult == null || scanResult.level >= 0)
                        continue;
                    strWifi = new StringBuffer();
                    if (TextUtils.isEmpty(scanResult.SSID))
                        strWifi.append("....");
                    else
                        strWifi.append(scanResult.SSID);
                    strWifi.append("--");
                    strWifi.append( scanResult.BSSID);
                    strWifi.append("--");
                    strWifi.append(scanResult.level);
                    strWifi.append(" dBm");
                    listWifi.add(strWifi.toString());
                    listWifiScan.add(scanResult);
                }
                if (listWifiScan.size() > 0){
                    strWifi = new StringBuffer();
                    strWifi.append(getText(R.string.select_all));
                    listWifi.add(0,strWifi.toString());
                    listWifiScan.add(0,null);
                }
            }
        }catch (Exception ex){
            listWifi.clear();
            listWifiScan.clear();
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
            listWifi.clear();
            Log.e(this.getClass().getName(), "loadWifiScan--->" + ex.getMessage());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
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
