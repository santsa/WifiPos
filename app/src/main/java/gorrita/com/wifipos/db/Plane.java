package gorrita.com.wifipos.db;


public class Plane extends Comun{

    public static final String createTable =
                    " CREATE TABLE PLANES (" +
                    " ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " FILE TEXT NOT NULL," +
                    " NAME TEXT NOT NULL," +
                    " DESCRIPTION TEXT," +
                    " DATACREATED LONG NOT NULL DEFAULT " + System.currentTimeMillis() + " ," +
                    " DATAUPDATED LONG NOT NULL DEFAULT " + System.currentTimeMillis() + " ," +
                    " ACTIVE INTEGER NOT NULL DEFAULT 1 " +
                    " )";

    public Plane() {}

    public Plane(CharSequence file, CharSequence name) {
        this.file = file;
        this.name = name;
    }

    private CharSequence file;
    private CharSequence name;

    public CharSequence getFile() {
        return file;
    }

    public void setFile(CharSequence file) {
        this.file = file;
    }

    public CharSequence getName() {
        return name;
    }

    public void setName(CharSequence name) {
        this.name = name;
    }
}
