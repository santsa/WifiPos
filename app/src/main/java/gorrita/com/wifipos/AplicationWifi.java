package gorrita.com.wifipos;

import android.app.Application;
import android.util.Log;

import java.util.List;

import gorrita.com.wifipos.db.Plane;
import gorrita.com.wifipos.db.PointTraining;
import gorrita.com.wifipos.db.Training;

/**
 * Created by salva on 06/08/15.
 */


public class AplicationWifi extends Application {

    private boolean wifi;
    private boolean first;
    private Plane plane;
    private Training training;
    private List<PointTraining> pointTrainings;
    private int insertados;
    private int heightPoint;
    private int widthPoint;

    @Override
    public void onCreate(){
        try {
            super.onCreate();
            wifi = true;
            first = true;
            plane = null;
            training = null;
            pointTrainings = null;
            insertados = 0;
            heightPoint = 0;
            widthPoint = 0;
            Log.i(this.getClass().getName(), "onCreate");
        }catch (Exception e){
            Log.e(this.getClass().getName(), "onCreate--->" + e.getMessage());
        }
    }

    public boolean isWifi() {
        return wifi;
    }

    public void setWifi(boolean wifi) {
        this.wifi = wifi;
    }

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public Plane getPlane() {
        return plane;
    }

    public void setPlane(Plane plane) {
        this.plane = plane;
    }

    public Training getTraining() {
        return training;
    }

    public void setTraining(Training training) {
        this.training = training;
    }

    public List<PointTraining> getPointTrainings() {
        return pointTrainings;
    }

    public void setPointTrainings(List<PointTraining> pointTrainings) {
        this.pointTrainings = pointTrainings;
    }

    public int getInsertados() {
        return insertados;
    }

    public void setInsertados(int insertados) {
        this.insertados = insertados;
    }

    public int getHeightPoint() {
        return heightPoint;
    }

    public void setHeightPoint(int heightPoint) {
        this.heightPoint = heightPoint;
    }

    public int getWidthPoint() {
        return widthPoint;
    }

    public void setWidthPoint(int widthPoint) {
        this.widthPoint = widthPoint;
    }

}
