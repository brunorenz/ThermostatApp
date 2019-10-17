package it.android.bruno65.utility;

import android.content.Context;

import it.android.bruno65.bean.DataContainer;
import it.android.bruno65.network.HttpManager;
import it.android.bruno65.network.ManageGetConfiguration;
import it.android.bruno65.network.ManageGetCurrentData;
import it.android.bruno65.tempapp.R;

public class ThermostatServerManager extends HttpManager {
    private static ThermostatServerManager server;
    private DataContainer container;
    private String serverUrl;

    private ThermostatServerManager(Context context) {
        if (context != null)
            this.context = context;
    }

    private Context context;

    public static ThermostatServerManager getInstance(Context context) {
        if (server == null) server = new ThermostatServerManager(context);
        return server;
    }

    public DataContainer getContainer() {
        if (container == null) container = new DataContainer();
        return container;
    }


    public void getConfiguration(String intent) {
        String url = createUrl(context.getResources().getString(R.string.GETCONF));
        callHttpGet(url, new ManageGetConfiguration(context, getContainer(), intent));
    }

    public void getCurrentThermostatData(String intent) {
        String url = createUrl(context.getResources().getString(R.string.GETDATA));
        callHttpGet(url, new ManageGetCurrentData(context, getContainer(), intent));
    }

    private String createUrl(String function) {
        if (serverUrl == null) serverUrl = context.getResources().getString(R.string.TermServerURL);
        return serverUrl + function;
    }
}
