package gorrita.com.wifipos.db;

/**
 * Created by salva on 21/08/15.
 */
public class Training extends Comun{

    public static final String createTable =
                    " CREATE TABLE TRAININGS (" +
                    " ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " PLANE INTEGER," +
                    " DESCRIPTION TEXT," +
                    " DATACREATED LONG NOT NULL DEFAULT " + System.currentTimeMillis() + " ," +
                    " DATAUPDATED LONG NOT NULL DEFAULT " + System.currentTimeMillis() + " ," +
                    " ACTIVE INTEGER NOT NULL DEFAULT 1, " +
                    " FOREIGN KEY (PLANE) REFERENCES PLANES (ID)" +
                    " )";

    public Training() {}

    public Training(Integer plane) {
        this.plane = plane;
    }

    private Integer plane;

    public Integer getPlane() {
        return plane;
    }

    public void setPlane(Integer plane) {
        this.plane = plane;
    }
}
