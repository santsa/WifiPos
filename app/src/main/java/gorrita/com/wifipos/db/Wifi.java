package gorrita.com.wifipos.db;

/**
 * Created by salva on 21/08/15.
 */
public class Wifi extends Comun{

    public static final String createTable =
            " CREATE TABLE WIFIS (" +
            " ID INTEGER PRIMARY KEY AUTOINCREMENT," +
            " SSID TEXT," +
            " BSSID TEXT NOT NULL," +
            " capabilities TEXT," +
            " level INTEGER NOT NULL," +
            " frequency INTEGER, " +
            " timestamp LONG," +
            " seen LONG," +
            " isAutoJoinCandidate INTEGER," +
            " DESCRIPTION TEXT," +
            " DATACREATED LONG NOT NULL DEFAULT " + System.currentTimeMillis() + " ," +
            " DATAUPDATED LONG NOT NULL DEFAULT " + System.currentTimeMillis() + " ," +
            " ACTIVE INTEGER NOT NULL DEFAULT 1 " +
            " )";

    private String SSID;
    private String BSSID;
    private String capabilities;
    private Integer level;
    private Integer frequency;
    private Long timestamp;
    private Long seen;
    private Integer isAutoJoinCandidate;

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

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Long getSeen() {
        return seen;
    }

    public void setSeen(Long seen) {
        this.seen = seen;
    }

    public Integer getIsAutoJoinCandidate() {
        return isAutoJoinCandidate;
    }

    public void setIsAutoJoinCandidate(Integer isAutoJoinCandidate) {
        this.isAutoJoinCandidate = isAutoJoinCandidate;
    }
}
