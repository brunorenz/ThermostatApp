package it.android.bruno65.network;

import android.content.Context;
import android.util.Log;

import java.io.IOException;

import it.android.bruno65.bean.DataContainer;
import it.android.bruno65.bean.GetCurrentDataResponse;
import it.android.bruno65.bean.TempDTO;
import it.android.bruno65.utility.RESTUtil;
import okhttp3.Call;
import okhttp3.Response;

public class ManageGetCurrentData extends BaseHttpCallback {
    public ManageGetCurrentData(Context context, DataContainer dataContainer, String intent) {
        super(context, dataContainer, intent);
    }


    @Override
    public void onHttpResponse(Call call, Response response) throws IOException {

        String myResponse = response.body().string();
        Log.d("TermpApp", myResponse);
        try {
            GetCurrentDataResponse resp = RESTUtil.jsonDeserialize(myResponse, GetCurrentDataResponse.class);
            displayError(response, resp.getError());
            DataContainer c = getContainer();
            c.setThermMeasureType(resp.getData().getTempMeasure());
            c.setThermStatus(resp.getData().getStatus());
            for (TempDTO cf : resp.getData().getTemp()) {
                Log.d("TermApp", "Trovato dispositivo " + cf.getLocation());
                c.getTempData().put(cf.getIdDisp(), cf);
                //container.getConfigurationData().put(c.get)
            }
        } catch (Exception e) {
            Log.e("TermApp", "Errore deserializzazione JSON", e);
        }


    }
}
