package gorrita.com.wifipos.db;

/**
 * Created by salva on 21/08/15.
 */
public class PointTraining extends Comun{

    public PointTraining() {}

    public PointTraining(Integer training, Double x, Double y) {
        Training = training;
        this.x = x;
        this.y = y;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PointTraining that = (PointTraining) o;

        return Training.equals(that.Training);

    }

    @Override
    public int hashCode() {
        return Training.hashCode();
    }
}
