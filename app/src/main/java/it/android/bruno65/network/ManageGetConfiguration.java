package it.android.bruno65.network;

import android.content.Context;
import android.util.Log;

import java.io.IOException;

import it.android.bruno65.bean.ConfigDTO;
import it.android.bruno65.bean.DataContainer;
import it.android.bruno65.bean.GetConfigurationResponse;
import it.android.bruno65.utility.RESTUtil;
import okhttp3.Call;
import okhttp3.Response;

public class ManageGetConfiguration extends BaseHttpCallback {
    public ManageGetConfiguration(Context context, DataContainer dataContainer,String intent) {
        super(context, dataContainer, intent);
    }


    @Override
    public void onHttpResponse(Call call, Response response) throws IOException {

        String myResponse = response.body().string();
        Log.d("TermpApp", myResponse);
        try {
            GetConfigurationResponse resp = RESTUtil.jsonDeserialize(myResponse, GetConfigurationResponse.class);
            displayError(response, resp.getError());
            DataContainer c = getContainer();
            for (ConfigDTO cf : resp.getData()) {
                Log.d("TermApp", "Trovato dispositivo " + cf.getLocation());
                c.getConfigurationData().put(cf.get$loki(), cf);
                //container.getConfigurationData().put(c.get)
            }
        } catch (Exception e) {
            Log.e("TermApp", "Errore deserializzazione JSON", e);
        }


    }
}
