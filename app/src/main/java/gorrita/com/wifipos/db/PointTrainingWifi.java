package gorrita.com.wifipos.db;

/**
 * Created by salva on 21/08/15.
 */
public class PointTrainingWifi extends Comun{

    public static final String createTable =
            " CREATE TABLE POINTTRAININGWIFIS (" +
            " ID INTEGER PRIMARY KEY AUTOINCREMENT," +
            " POINTTRAINING INTEGER," +
            " WIFI INTEGER," +
            " DESCRIPTION TEXT," +
            " DATACREATED LONG NOT NULL DEFAULT " + System.currentTimeMillis() + " ," +
            " DATAUPDATED LONG NOT NULL DEFAULT " + System.currentTimeMillis() + " ," +
            " ACTIVE INTEGER NOT NULL DEFAULT 1, " +
            " FOREIGN KEY (POINTTRAINING) REFERENCES POINTTRAININGS (POINTTRAINING)," +
            " FOREIGN KEY (WIFI) REFERENCES WIFIS (ID)" +
            " )";

    private Integer pointtraining;
    private Integer wifi;

    public Integer getPointtraining() {
        return pointtraining;
    }

    public void setPointtraining(Integer pointtraining) {
        this.pointtraining = pointtraining;
    }

    public Integer getWifi() {
        return wifi;
    }

    public void setWifi(Integer wifi) {
        this.wifi = wifi;
    }


}
