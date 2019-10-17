package it.android.bruno65.bean;

public abstract class BaseResponse {
    Error error;

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }
}
