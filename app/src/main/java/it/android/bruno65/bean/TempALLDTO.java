package it.android.bruno65.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TempALLDTO {
    int tempMeasure;
    int status;
    List<TempDTO> temp;
    Date now;

    public int getTempMeasure() {
        return tempMeasure;
    }

    public void setTempMeasure(int tempMeasure) {
        this.tempMeasure = tempMeasure;
    }

    public List<TempDTO> getTemp() {
        if (temp == null) temp = new ArrayList<>();
        return temp;
    }

    public void setTemp(List<TempDTO> temp) {
        this.temp = temp;
    }

    public Date getNow() {
        return now;
    }

    public void setNow(Date now) {
        this.now = now;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
