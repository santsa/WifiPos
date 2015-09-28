package gorrita.com.wifipos;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
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

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import gorrita.com.wifipos.db.PointTraining;
import gorrita.com.wifipos.db.PointTrainingWifi;
import gorrita.com.wifipos.db.Wifi;
import gorrita.com.wifipos.db.WifiPosManager;


public class PlanePositionFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private View view;
    private ImageView imageView;
    private ImageView imagePosition;
    private UpdatePosition updatePosition;
    private WeakReference<UpdatePosition> asyncTaskWeakRef;

    public static PlanePositionFragment newInstance(String param1, String param2) {
        PlanePositionFragment fragment = new PlanePositionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public PlanePositionFragment() {

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

            view = inflater.inflate(R.layout.fragment_plane_position, container, false);
            loadImageResource();
            ((AplicationWifi)getActivity().getApplication()).setFirst(false);
            imagePosition = (ImageView) view.findViewById(R.id.positionPlane);
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
            clear();
        } catch (ClassCastException e) {
            Log.e(this.getClass().getName(), "onDetach--->" + e.getMessage());;
        }
    }

    private void clear(){
        asyncTaskWeakRef.get().exit = true;
        asyncTaskWeakRef.get().cancel(true);
        asyncTaskWeakRef.get().listWifiScan.clear();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_plane_position, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent;
        ActivityOptions actOps = null;
        switch (id) {
            case R.id.accion_position_exit:
                intent = new Intent(this.getActivity(), MainActivity.class);
                actOps = ActivityOptions.makeCustomAnimation(this.getActivity(),
                        R.anim.replace_inside_rigth, R.anim.replace_inside_left);
                startActivity(intent, actOps.toBundle());
                clear();
                break;
            case R.id.accion_position_training:
                intent = new Intent(this.getActivity(), PlaneTrainingActivity.class);
                actOps = ActivityOptions.makeCustomAnimation(this.getActivity(),
                        R.anim.replace_inside_rigth,R.anim.replace_inside_left);
                startActivity(intent, actOps.toBundle());
                clear();
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
        Map<PointTraining, Map<Wifi,PointTrainingWifi>> mapPointsTraining;
        Boolean exit;
        WifiManager wifi;
        List<ScanResult> listWifiScan;
        private WeakReference<Fragment> fragmentWeakRef;

        private UpdatePosition (PlanePositionFragment fragment) {
            this.fragmentWeakRef = new WeakReference<Fragment>(fragment);
        }

        @Override
        protected  void onPreExecute(){
            try{
                mapPointsTraining = new HashMap<PointTraining, Map<Wifi,PointTrainingWifi>>();
                exit = false;
                aplicationWifi = (AplicationWifi) getActivity().getApplication();
                Map<CharSequence,PointTraining> mapPoinsTraining = aplicationWifi.getPointTrainings();
                for(PointTraining pointTraining: mapPoinsTraining.values()) {
                    CharSequence where = " WHERE POINTTRAINING = " + pointTraining.getId() + " AND ACTIVE = 1";
                    List<PointTrainingWifi> listPointTrainingWifi = WifiPosManager.listPointTrainingWifi(where);
                    Map<Wifi,PointTrainingWifi> mapPointsTrainingWifi = new HashMap<Wifi,PointTrainingWifi>();
                    for (PointTrainingWifi p:  listPointTrainingWifi){
                        where = " WHERE ID = " + p.getWifi() + " AND ACTIVE = 1";
                        List<Wifi> listWifi = WifiPosManager.listWifi(where);
                        if(!listWifi.isEmpty()){
                            mapPointsTrainingWifi.put(listWifi.get(0),p);
                        }
                    }
                    if(!mapPointsTrainingWifi.isEmpty())
                        mapPointsTraining.put(pointTraining, mapPointsTrainingWifi);
                }
                wifi = (WifiManager) getActivity().getSystemService(Context.WIFI_SERVICE);
                //SystemClock.sleep(5000);
            } catch (ClassCastException e) {
                Log.e(this.getClass().getName(), "onPreExecute--->" + e.getMessage());
            }
        }

        private Map<CharSequence,Object[]> comunWifis(){

            Map<CharSequence,Object[]> mapComunWifis = new HashMap<CharSequence,Object[]>();
            for(CharSequence key : Constants.KEYPOINTSDUAL){
                CharSequence prefix = key.subSequence(0, 4);
                CharSequence sufix = key.subSequence(4, key.length());
                PointTraining p1 = aplicationWifi.getPointTrainings().get(prefix);
                PointTraining p2 = aplicationWifi.getPointTrainings().get(sufix);
                Map<Wifi,PointTrainingWifi> map1 = mapPointsTraining.get(p1);
                Map<Wifi,PointTrainingWifi> map2 = mapPointsTraining.get(p2);
                Set<Wifi> setWifi1 = map1.keySet();
                Set<Wifi> setWifi2 = map2.keySet();
                setWifi1.retainAll(setWifi2);
                Object[] lstComun = new Object[3];
                Map<Integer,ScanResult> mapScanComun = stractScanResultComun(setWifi1);
                lstComun[0] = mapScanComun;
                Set<Wifi> setWifiComun = stractWifiComun(setWifi1, mapScanComun);
                Map<Wifi,PointTrainingWifi> mapComun1 = loadWifisComun(map1, setWifiComun);
                lstComun[1] = mapComun1;
                Map<Wifi,PointTrainingWifi> mapComun2 = loadWifisComun(map2, setWifiComun);
                lstComun[2] = mapComun2;
                mapComunWifis.put(key,lstComun);
            }
            return mapComunWifis;
        }

        private Map<Integer,ScanResult> stractScanResultComun(Set<Wifi> setWifi){
            Map<Integer,ScanResult> mapScanComun = new HashMap<Integer,ScanResult> ();
            for (Wifi w : setWifi) {
                for(ScanResult scan :listWifiScan){
                    if(scan.BSSID.equals(w.getBSSID())) {
                        mapScanComun.put(w.getId(), scan);
                        break;
                    }
                }
            }
            return mapScanComun;
        }

        private Set<Wifi> stractWifiComun(Set<Wifi> setWifi, Map<Integer,ScanResult> mapScanComun){
            Set<Wifi> setWifiComun = new HashSet<Wifi>();
            for (Integer id: mapScanComun.keySet()) {
                for (Wifi w : setWifi) {
                    if(w.getId().equals(id)){
                        setWifiComun.add(w);
                    }
                }
            }
            return setWifiComun;
        }

        private Map<Wifi,PointTrainingWifi> loadWifisComun(Map<Wifi,PointTrainingWifi> map,Set<Wifi> setWifi){
            Map<Wifi, PointTrainingWifi> mapComun = new HashMap<Wifi, PointTrainingWifi> ();
            for (Wifi w : setWifi) {
                mapComun.put(w,map.get(w));
            }
            return mapComun;
        }

        private Point position;
        private List<Point> positionFinal;

        @Override
        protected Void doInBackground(Float... params) {
            positionFinal = new ArrayList<Point>();
            while (!exit){
                long sleep = (long)Math.round(params[0])-300;
                for(int i = 0; i<3; i++){
                    wifiScan();
                    Map<CharSequence,Object[]> mapComunWifis = comunWifis();
                    position = calculatePosition(mapComunWifis);
                    positionFinal.add(position);
                    SystemClock.sleep(100);
                }
                SystemClock.sleep(sleep);
                position = finePosition(positionFinal,0);
                publishProgress();
            }
            return null;
        }

        private Point calculatePosition(Map<CharSequence,Object[]> mapComunWifis){
            List<Point> lstPointsAll = new ArrayList<Point>();
            for(CharSequence key : Constants.KEYPOINTSDUAL){
                Object[] obs = mapComunWifis.get(key);
                Map<Integer,ScanResult> mapScanComun = (Map<Integer, ScanResult>) obs[0];
                Map<Wifi,PointTrainingWifi> wifi1 = (Map<Wifi, PointTrainingWifi>) obs[1];
                Map<Wifi,PointTrainingWifi> wifi2 = (Map<Wifi, PointTrainingWifi>) obs[2];
                List<Point> lstPoints = new ArrayList<Point>();
                for (Map.Entry<Wifi,PointTrainingWifi> entryWifi: wifi1.entrySet()) {
                    Wifi w1 = entryWifi.getKey();
                    PointTrainingWifi p1 = entryWifi.getValue();
                    PointTrainingWifi p2 = wifi2.get(w1);
                    ScanResult sr = mapScanComun.get(w1.getId());
                    Point p = calculatePosWifi(w1, p1, p2, sr, key);
                    lstPoints.add(p);
                }
                Point pointFine = finePosition(lstPoints,0);
                lstPointsAll.add(pointFine);
            }
            Point pointFineAll = finePosition(lstPointsAll,0);
            return pointFineAll;
        }

        //Extraure x,e y

        private Point calculatePosWifi(Object... args){
            Wifi w1 = (Wifi) args[0];
            PointTrainingWifi pw1 = (PointTrainingWifi) args[1];
            PointTrainingWifi pw2 = (PointTrainingWifi) args[2];
            ScanResult sr = (ScanResult) args[03];
            CharSequence key = (CharSequence) args[4];
            CharSequence prefix = key.subSequence(0, 4);
            CharSequence sufix = key.subSequence(4, key.length());
            Map<CharSequence,PointTraining> mapPoinsTraining = aplicationWifi.getPointTrainings();
            PointTraining p1 = mapPoinsTraining.get(prefix);
            PointTraining p2 = mapPoinsTraining.get(sufix);
            Double difx = Math.abs(p1.getX() - p2.getX());
            Double dify = Math.abs(p1.getY() - p2.getY());
            Integer difLevelp1p2 = Math.abs(Math.abs(pw1.getLevel())-Math.abs(pw2.getLevel()));
            Integer difLevelp1Sr = Math.abs(Math.abs(pw1.getLevel())-Math.abs(sr.level));
            Integer difLevelp2Sr = Math.abs(Math.abs(pw2.getLevel())-Math.abs(sr.level));
            Double x = 0.0;
            Double y = 0.0;
            if(difx > 0){
                x = calcPos (difx/2, difLevelp1p2, difLevelp1Sr, difLevelp2Sr);
            }
            if(dify > 0){
                y = calcPos (dify/2, difLevelp1p2, difLevelp1Sr, difLevelp2Sr);
            }
            Point pos = new Point();
            pos.set(x.intValue(),y.intValue());
            return pos;
        }

        private double calcPos (Double dif, Integer difLevelp1p2, Integer difLevelp1Sr, Integer difLevelp2Sr){
            double coefPixelLevel = difLevelp1p2 < 3? dif:dif/difLevelp1p2;
            double coord1 = difLevelp1Sr == 0? dif:difLevelp1Sr * coefPixelLevel;
            double coord2 = difLevelp1Sr == 0? dif:difLevelp2Sr * coefPixelLevel;
            return (coord1 + coord2)/2;
        }

        private Point finePosition(List<Point> lstPoints, int sum){
            int x = 0;
            int y = 0;
            for(Point point: lstPoints){
                x+=point.x;
                y+=point.y;
            }
            x = x/(lstPoints.size() + sum);
            y = y/(lstPoints.size() + sum);
            Point point = new Point();
            point.set(x,y);
            return point;
        }

        @Override
        protected void onProgressUpdate(Float... params){
            //x = imagePosition.getX();
            //y = imagePosition.getY();
            Point size = aplicationWifi.getSize();
            if (position.x < 0-10)
                imagePosition.setX(0-10);
            else if (position.x > size.x-60)
                imagePosition.setX(size.x-60);
            else
                imagePosition.setX(position.x);

            if (position.y < 0-10)
                imagePosition.setY(0 -10);
            if (position.y > size.y-100)
                imagePosition.setY(size.y-100);
            else
                imagePosition.setY(position.y);
        }

        private void wifiScan(){
            try {
                //wifi.startScan();
                //listWifiScan = wifi.getScanResults();
                AplicationWifi aplicationWifi = (AplicationWifi) getActivity().getApplication();
                listWifiScan = aplicationWifi.scanAVGScanResults(wifi, 5);
            }catch (Exception ex){
                listWifiScan.clear();
                Log.e(this.getClass().getName(), "listWifiScan--->" + ex.getMessage());
            }
        }

        @Override
        protected void onPostExecute(Void response) {
            super.onPostExecute(response);
            if (this.fragmentWeakRef.get() != null) {

            }
        }

    }

}
