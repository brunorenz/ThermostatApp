package it.android.bruno65.bean;

import java.util.ArrayList;
import java.util.List;

public class GetConfigurationResponse extends BaseResponse {
    List<ConfigDTO> data;

    public List<ConfigDTO> getData() {
        if (data == null) data = new ArrayList<>();
        return data;
    }

    public void setData(List<ConfigDTO> data) {
        this.data = data;
    }
}
