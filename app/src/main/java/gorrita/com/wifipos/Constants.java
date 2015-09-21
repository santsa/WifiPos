package gorrita.com.wifipos;

/**
 * Created by sgorrita on 6/09/15.
 */
public class Constants {

    public static final CharSequence PLANEFRAGMENTTAG = "PlaneTrainingFragment";
    public static final int ACTION_POSITION = 90000000;

    public static final CharSequence DATABASE_NAME = "WifiPos";
    public static int VERSION = 1;

    public static final CharSequence createTablePlanes =
            " CREATE TABLE PLANES (" +
                    " ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " FILE TEXT NOT NULL," +
                    " NAME TEXT NOT NULL," +
                    " DESCRIPTION TEXT," +
                    " DATACREATED LONG NOT NULL DEFAULT " + System.currentTimeMillis() + " ," +
                    " DATAUPDATED LONG NOT NULL DEFAULT " + System.currentTimeMillis() + " ," +
                    " ACTIVE INTEGER NOT NULL DEFAULT 1 " +
                    " )";
    public static final CharSequence dropTablePlanes = "DROP TABLE IF EXISTS PLANES";

    public static final CharSequence createTablePointTrainings =
            " CREATE TABLE POINTTRAININGS (" +
                    " ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " TRAINING INTEGER," +
                    " X REAL NOT NULL," +
                    " Y REAL NOT NULL," +
                    " DESCRIPTION TEXT," +
                    " DATACREATED LONG NOT NULL DEFAULT " + System.currentTimeMillis() + " ," +
                    " DATAUPDATED LONG NOT NULL DEFAULT " + System.currentTimeMillis() + " ," +
                    " ACTIVE INTEGER NOT NULL DEFAULT 1, " +
                    " FOREIGN KEY (TRAINING) REFERENCES TRAININGS (ID)" +
                    " )";

    public static final CharSequence dropTablePointTrainings = "DROP TABLE IF EXISTS POINTTRAININGS";

    public static final CharSequence createTablePointTrainingWifis =
            " CREATE TABLE POINTTRAININGWIFIS (" +
                    " ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " POINTTRAINING INTEGER," +
                    " WIFI INTEGER," +
                    " level INTEGER NOT NULL," +
                    " timestamp LONG," +
                    " DESCRIPTION TEXT," +
                    " DATACREATED LONG NOT NULL DEFAULT " + System.currentTimeMillis() + " ," +
                    " DATAUPDATED LONG NOT NULL DEFAULT " + System.currentTimeMillis() + " ," +
                    " ACTIVE INTEGER NOT NULL DEFAULT 1, " +
                    " FOREIGN KEY (POINTTRAINING) REFERENCES POINTTRAININGS (POINTTRAINING)," +
                    " FOREIGN KEY (WIFI) REFERENCES WIFIS (ID)" +
                    " )";

    public static final CharSequence dropTablePointTrainingWifis = "DROP TABLE IF EXISTS POINTTRAININGWIFIS";

    public static final CharSequence createTableTrainings =
            " CREATE TABLE TRAININGS (" +
                    " ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " PLANE INTEGER," +
                    " DESCRIPTION TEXT," +
                    " DATACREATED LONG NOT NULL DEFAULT " + System.currentTimeMillis() + " ," +
                    " DATAUPDATED LONG NOT NULL DEFAULT " + System.currentTimeMillis() + " ," +
                    " ACTIVE INTEGER NOT NULL DEFAULT 1, " +
                    " FOREIGN KEY (PLANE) REFERENCES PLANES (ID)" +
                    " )";

    public static final CharSequence dropTableTrainings = "DROP TABLE IF EXISTS TRAININGS";

    public static final CharSequence createTableWifis =
            " CREATE TABLE WIFIS (" +
                    " ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " SSID TEXT," +
                    " BSSID TEXT NOT NULL," +
                    " capabilities TEXT," +
                    " frequency INTEGER, " +
                    " DESCRIPTION TEXT," +
                    " DATACREATED LONG NOT NULL DEFAULT " + System.currentTimeMillis() + " ," +
                    " DATAUPDATED LONG NOT NULL DEFAULT " + System.currentTimeMillis() + " ," +
                    " ACTIVE INTEGER NOT NULL DEFAULT 1 " +
                    " )";

    public static final CharSequence dropTableWifis = "DROP TABLE IF EXISTS WIFIS";

}
