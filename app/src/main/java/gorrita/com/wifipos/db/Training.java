package gorrita.com.wifipos.db;

/**
 * Created by salva on 21/08/15.
 */
public class Training extends Comun{

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
