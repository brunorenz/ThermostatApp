package it.android.bruno65.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataContainer {
    int currentIdDisp;
    int thermStatus;
    int thermMeasureType;
    Map<Integer, TempDTO> tempData;
    Map<Integer, ConfigDTO> configurationData;

    public int getCurrentIdDisp() {
        if (currentIdDisp == 0 && !getConfigurationData().isEmpty()) {
            for (ConfigDTO cfg : configurationData.values()) {
                if (cfg.getFlagReleTemp() == 1) {
                    currentIdDisp = cfg.get$loki();
                    break;
                }
            }
            if (currentIdDisp == 0)
                currentIdDisp = getConfigurationData().values().iterator().next().get$loki();
        }
        return currentIdDisp;
    }

    public void nextId() {
        if (getConfigurationData().size() > 1) {
            int currId = getCurrentIdDisp();
            List<Integer> l = new ArrayList(getConfigurationData().keySet());
            int pos = l.indexOf(currId) + 1;
            if (pos >= getConfigurationData().size()) pos = 0;
            currentIdDisp = l.get(pos);
        }
    }

    /*
    public void setCurrentIdDisp(int currentIdDisp) {
        this.currentIdDisp = currentIdDisp;
    }
    */
    public Map<Integer, TempDTO> getTempData() {
        if (tempData == null) tempData = new HashMap<>();
        return tempData;
    }

    public Map<Integer, ConfigDTO> getConfigurationData() {
        if (configurationData == null) configurationData = new HashMap<>();
        return configurationData;
    }

    public ConfigDTO getCurrentConfiguration() {
        ConfigDTO cfg = getConfigurationData().get(getCurrentIdDisp());
        if (cfg == null) {
            cfg = new ConfigDTO();
            cfg.setLocation("Not Available");
            cfg.setMacAddress("Not Available");
        }
        return cfg;
    }

    public TempDTO getCurrentData() {
        TempDTO cfg = getTempData().get(getCurrentIdDisp());
        if (cfg == null) {
            cfg = new TempDTO();
            cfg.setLocation("Not Available");
        }
        return cfg;

    }

    public int getThermStatus() {
        return thermStatus;
    }

    public void setThermStatus(int thermStatus) {
        this.thermStatus = thermStatus;
    }

    public int getThermMeasureType() {
        return thermMeasureType;
    }

    public void setThermMeasureType(int thermMeasureType) {
        this.thermMeasureType = thermMeasureType;
    }
}
