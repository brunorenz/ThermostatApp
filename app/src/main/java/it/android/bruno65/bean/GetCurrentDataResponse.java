package it.android.bruno65.bean;

public class GetCurrentDataResponse extends BaseResponse {
    TempALLDTO data;

    public TempALLDTO getData() {
        return data;
    }

    public void setData(TempALLDTO data) {
        this.data = data;
    }
}
