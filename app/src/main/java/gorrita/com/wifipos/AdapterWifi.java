package gorrita.com.wifipos;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by salva on 13/08/15.
 */

public class AdapterWifi extends  RecyclerView.Adapter<AdapterWifi.ViewHolder>  {

    private LayoutInflater inflador;
    protected List<ScanResult> listWifiScan;

    public AdapterWifi(Context contexto, List<ScanResult> listWifiScan) {
        inflador = (LayoutInflater) contexto
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.listWifiScan = listWifiScan;
    }

    //Creamos nuestro ViewHolder, con los tipos de elementos a modificar
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView wifi;
        public ViewHolder(View itemView) {
            super(itemView);
            wifi = (TextView) itemView.findViewById(R.id.lbl_wifi_selector);
        }
    }
    // Creamos el ViewHolder con las vista de un elemento sin personalizar
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        try {
            // Inflamos la vista desde el xml
            View v = inflador.inflate(R.layout.wifi_selector, null);
            return new ViewHolder(v);
        }catch(Exception e){
            Log.e(this.getClass().getName(), "onCreateViewHolder--->" + e.getMessage());
            throw e;
        }
    }

    // Usando como base el ViewHolder y lo personalizamos
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        try {
            ScanResult wifi = listWifiScan.get(position);
            StringBuilder strWifi = new StringBuilder();
            if (TextUtils.isEmpty(wifi.SSID))
                strWifi.append(wifi.SSID);
            else
                strWifi.append("....");
            strWifi.append(wifi.BSSID + " " + wifi.level + " dBm " + wifi.capabilities);
            holder.wifi.setText(strWifi);
        }catch(Exception e){
            Log.e(this.getClass().getName(), "onBindViewHolder--->" + e.getMessage());
        }
    }

    // Indicamos el número de elementos de la lista
    @Override public int getItemCount() {
        return listWifiScan.size();
    }
}

