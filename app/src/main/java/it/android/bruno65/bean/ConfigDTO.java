package it.android.bruno65.bean;

import java.util.Date;

public class ConfigDTO {
    String macAddress;
    String location;
    int $loki;
    Date lastAccess;
    int status;
    int tempMeasure;
    int flagLcd;
    int flagLightSensor;
    int flagMotionSensor;
    int flagReleTemp;
    int flagReleLight;

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int get$loki() {
        return $loki;
    }

    public void set$loki(int $loki) {
        this.$loki = $loki;
    }

    public Date getLastAccess() {
        return lastAccess;
    }

    public void setLastAccess(Date lastAccess) {
        this.lastAccess = lastAccess;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTempMeasure() {
        return tempMeasure;
    }

    public void setTempMeasure(int tempMeasure) {
        this.tempMeasure = tempMeasure;
    }

    public int getFlagLcd() {
        return flagLcd;
    }

    public void setFlagLcd(int flagLcd) {
        this.flagLcd = flagLcd;
    }

    public int getFlagLightSensor() {
        return flagLightSensor;
    }

    public void setFlagLightSensor(int flagLightSensor) {
        this.flagLightSensor = flagLightSensor;
    }

    public int getFlagMotionSensor() {
        return flagMotionSensor;
    }

    public void setFlagMotionSensor(int flagMotionSensor) {
        this.flagMotionSensor = flagMotionSensor;
    }

    public int getFlagReleTemp() {
        return flagReleTemp;
    }

    public void setFlagReleTemp(int flagReleTemp) {
        this.flagReleTemp = flagReleTemp;
    }

    public int getFlagReleLight() {
        return flagReleLight;
    }

    public void setFlagReleLight(int flagReleLight) {
        this.flagReleLight = flagReleLight;
    }
}
