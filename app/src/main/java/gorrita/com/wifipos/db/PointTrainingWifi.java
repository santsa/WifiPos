package gorrita.com.wifipos.db;

/**
 * Created by salva on 21/08/15.
 */
public class PointTrainingWifi extends Comun{

    public PointTrainingWifi() {}

    public PointTrainingWifi(Integer pointtraining, Integer wifi, Integer level, Long timestamp) {
        this.pointtraining = pointtraining;
        this.wifi = wifi;
        this.level = level;
        this.timestamp = timestamp;
    }

    private Integer pointtraining;
    private Integer wifi;
    private Integer level;
    private Long timestamp;

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

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

}
