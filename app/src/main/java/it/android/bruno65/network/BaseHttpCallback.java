package it.android.bruno65.network;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.io.IOException;

import it.android.bruno65.bean.DataContainer;
import it.android.bruno65.bean.Error;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public abstract class BaseHttpCallback implements Callback {
    String intent;
    Context context;
    DataContainer dataContainer;

    protected DataContainer getContainer() {
        return dataContainer;
    }

    protected abstract void onHttpResponse(Call call, Response response) throws IOException;

    public BaseHttpCallback(Context context, DataContainer dataContainer, String intent) {
        this.context = context;
        this.intent = intent;
        this.dataContainer = dataContainer;
    }

    @Override
    public void onFailure(Call call, IOException e) {
        Log.d("TermpApp", "Failed !!", e);
        call.cancel();
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        onHttpResponse(call, response);
        if (intent != null)
            LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent(intent));

    }

    protected void displayError(Response response, Error error) {
        if (response != null) {
            Log.d("TermApp", "HTTP Response : " + response.toString());
        }
        if (error != null) {
            Log.d("TermApp", "TermServeerResp : " + error.getCode() + " - " + error.getMessage());
        }
    }
}
