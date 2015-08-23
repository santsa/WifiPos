package gorrita.com.wifipos.db;

/**
 * Created by salva on 21/08/15.
 */
public class PointTraining extends Comun{

    public static final String createTable =
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

    private Integer Training;
    private Double x;
    private Double y;

    public Integer getTraining() {
        return Training;
    }

    public void setTraining(Integer training) {
        Training = training;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }
}
