package it.android.bruno65.tempapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import it.android.bruno65.bean.ConfigDTO;
import it.android.bruno65.bean.DataContainer;
import it.android.bruno65.bean.TempDTO;
import it.android.bruno65.utility.ThermostatServerManager;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static String MainActivityIntent = MainActivity.class.getName();
    private TextView textTemp;
    private TextView textTempDec;
    private TextView textHumidity;
    private TextView textHumidityDec;
    private TextView textLigth;
    private TextView textPressure;
    private TextView textDetectionTime;
    private ImageView imageStatus;
    private ThermostatServerManager thermostatManager;
    //
    private BroadcastReceiver broadcastReceiver;

    private void initGUI() {
        textTemp = findViewById(R.id.textViewTemp);
        textTempDec = findViewById(R.id.textViewTempDec);
        textHumidity = findViewById(R.id.textViewHumidity);
        textHumidityDec = findViewById(R.id.textViewHumidityDec);
        textLigth = findViewById(R.id.textLight);
        textPressure = findViewById(R.id.textPressure);
        textDetectionTime = findViewById(R.id.textDetectionTime);
        imageStatus = findViewById(R.id.imageStatus);
    }

    private void updateGUI() {
        DataContainer dc = thermostatManager.getContainer();
        if (dc.getCurrentIdDisp() == 0) {
            textTemp.setText("--");
            textTempDec.setText(".-");
            textHumidity.setText("--");
            textHumidityDec.setText(".-");
            textPressure.setText("----.-- mbar");
            textLigth.setText("-- %");
            textDetectionTime.setText("N/A");
            setTitle("Not connected!");
        } else {
            ConfigDTO cfg = dc.getCurrentConfiguration();
            TempDTO temp = dc.getCurrentData();
            setTitle(temp.getLocation());
            String st[] = formatThemperature(temp.getTemperature());
            textTemp.setText(st[0]);
            textTempDec.setText("." + st[1]);
            st = formatThemperature(temp.getHumidity());
            textHumidity.setText(st[0]);
            textHumidityDec.setText("." + st[1]);
            textPressure.setText(formatPressure(temp.getPressure()));
            if (cfg.getFlagLightSensor() == 1) {
                st = formatThemperature(temp.getLight());
                textLigth.setText(String.format("%s.%s %%", st[0], st[1]));
            } else textLigth.setText("N/A");
            String det = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(temp.getTmsUpd());
            textDetectionTime.setText(det);
            if (dc.getThermStatus() == 0)
                imageStatus.setImageResource(R.drawable.ic_radiator_off);
            else
                imageStatus.setImageResource(R.drawable.ic_radiator_on);
            /*
            int s = thermostatManager.getContainer().getConfigurationData().size();
            if (s < 2) {
                ActionMenuItemView mi = findViewById(R.id.action_switch);
                mi.setEnabled(false);
            }
            */

        }

    }

    private void initRefreshButton() {
        /*
        final ImageButton button = findViewById(R.id.refreshData);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                thermostatManager.getCurrentThermostatData(MainActivityIntent);
            }
        });
        */
    }

    private String formatPressure(float f) {
        String ns = String.format("%07.02f mBar", f);
        return ns;
    }

    private String[] formatThemperature(float f) {
        String[] s = new String[2];
        String ns = String.format("%03d", Float.valueOf(f * 10).intValue());
        s[1] = ns.substring(ns.length() - 1);
        s[0] = ns.substring(0, ns.length() - 1);
        return s;
    }

    private void manageBroadcastReceiver(boolean start) {
        if (start) {
            Log.d("TermApp", "Create broadCastreceiver from");
            broadcastReceiver = createBroadcastReceiver();
            LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, new IntentFilter(MainActivityIntent));
        } else {
            Log.d("TermApp", "Destroy broadCastreceiver from");
            if (broadcastReceiver != null)
                LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
        }
    }

    private void initThermostatServerManager() {
        thermostatManager = ThermostatServerManager.getInstance(getBaseContext());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                thermostatManager.getCurrentThermostatData(MainActivityIntent);
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        // init gui
        initGUI();
        // init thermostat server manager
        initThermostatServerManager();
        // add intent listener
        manageBroadcastReceiver(true);
        // read date from server
        thermostatManager.getConfiguration(null);
        thermostatManager.getCurrentThermostatData(MainActivityIntent);
        // update gui
        updateGUI();
        initRefreshButton();
    }

    @Override
    protected void onDestroy() {
        manageBroadcastReceiver(false);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_switch) {
            thermostatManager.getContainer().nextId();
            updateGUI();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Update MainAtcivity data
     *
     * @return
     */
    private BroadcastReceiver createBroadcastReceiver() {
        return new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("TermApp", "BroadCasRreceiver onReceive..");
                //DataContainer dc = ThermostatServerManager.getInstance(null).getContainer();
                updateGUI(); //dc.getCurrentData());
            }
        };
    }
}
