package it.android.bruno65.bean;

import java.util.Date;

public class TempDTO {
    int idDisp;
    String location;
    float temperature;
    float pressure;
    float humidity;
    float light;
    Date tmsUpd;
    int flagReleTemp;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getPressure() {
        return pressure;
    }

    public void setPressure(float pressure) {
        this.pressure = pressure;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public float getLight() {
        return light;
    }

    public void setLight(float light) {
        this.light = light;
    }

    public int getIdDisp() {
        return idDisp;
    }

    public void setIdDisp(int idDisp) {
        this.idDisp = idDisp;
    }

    public Date getTmsUpd() {
        return tmsUpd;
    }

    public void setTmsUpd(Date tmsUpd) {
        this.tmsUpd = tmsUpd;
    }

    public int getFlagReleTemp() {
        return flagReleTemp;
    }

    public void setFlagReleTemp(int flagReleTemp) {
        this.flagReleTemp = flagReleTemp;
    }
}
