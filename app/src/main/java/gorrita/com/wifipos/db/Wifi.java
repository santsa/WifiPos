package gorrita.com.wifipos.db;

/**
 * Created by salva on 21/08/15.
 */
public class Wifi extends Comun{

    private String SSID;
    private String BSSID;
    private String capabilities;
    private Integer frequency;

    public Wifi(){}

    public Wifi(String SSID, String BSSID, String capabilities, Integer frequency) {
        this.SSID = SSID;
        this.BSSID = BSSID;
        this.capabilities = capabilities;
        this.frequency = frequency;
    }

    public String getSSID() {
        return SSID;
    }

    public void setSSID(String SSID) {
        this.SSID = SSID;
    }

    public String getBSSID() {
        return BSSID;
    }

    public void setBSSID(String BSSID) {
        this.BSSID = BSSID;
    }

    public String getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(String capabilities) {
        this.capabilities = capabilities;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

}
