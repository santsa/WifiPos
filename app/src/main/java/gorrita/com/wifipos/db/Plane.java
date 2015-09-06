package gorrita.com.wifipos.db;


public class Plane extends Comun{

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
