package com.overlake.ftc.ftcrobothub.activites;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.overlake.ftc.ftcrobothub.R;
import com.overlake.ftc.ftcrobothub.routes.HomeRoute;
import com.overlake.ftc.ftcrobothub.webserver.Router;
import com.overlake.ftc.ftcrobothub.webserver.WebServer;

import java.io.IOException;

public class ServerActivity extends AppCompatActivity
{
    private WebServer webServer;
    private ConstraintLayout layout;
    private FloatingActionButton startButton;
    private TextView serverStatusText;
    private TextView ipText;
    private final IntentFilter intentFilter = new IntentFilter();
    private BroadcastReceiver broadcastReceiver;
    private Channel channel;
    private WifiP2pManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);
        webServer = new WebServer(8000);

        this.startButton = findViewById(R.id.serverToggle);
        this.serverStatusText = findViewById(R.id.serverStatus);
        this.layout = findViewById(R.id.layout);
        this.ipText = findViewById(R.id.ip);
        setIpAccess();

        initializeStartButton();

        Router router = webServer.getRouter();
        router.addRoute(new HomeRoute());

        initNetworkMonitor();
    }

    public void initializeStartButton() {
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isConnectedToWifi()) {
                    if (!webServer.isListenting()) {
                        webServer.listen();
                        serverStatusText.setVisibility(View.VISIBLE);
                        startButton.setBackgroundTintList(ContextCompat.getColorStateList(ServerActivity.this, R.color.colorGreen));
                        Toast.makeText(getBaseContext(), "Started web server", Toast.LENGTH_SHORT).show();
                    } else {
                        webServer.stopListening();
                        serverStatusText.setVisibility(View.INVISIBLE);
                        startButton.setBackgroundTintList(ContextCompat.getColorStateList(ServerActivity.this, R.color.colorRed));
                        Toast.makeText(getBaseContext(), "Stopping web server", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Snackbar.make(layout, getString(R.string.wifi_message), Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean isConnectedToWifi() {
        return true;
    }

    private void setIpAccess() {
        ipText.setText(getIpAccess());
    }

    private String getIpAccess() {
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        int ipAddress = wifiManager.getConnectionInfo().getIpAddress();
        final String formattedIpAddress = String.format("%d.%d.%d.%d", (ipAddress & 0xff), (ipAddress >> 8 & 0xff), (ipAddress >> 16 & 0xff), (ipAddress >> 24 & 0xff));
        return "http://" + formattedIpAddress + ":" + 8000;
    }

    private void initNetworkMonitor() {
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

        manager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        channel = manager.initialize(this, getMainLooper(), null);
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                handleRecievedBroadcast(context, intent);
            }
        };
    }

    @Override
    public void onResume() {
        super.onResume();
        reciever = new WifiDirectBroadcastReciever
    }

    private void handleRecievedBroadcast(Context context, Intent intent) {
        String action = intent.getAction();
        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
            int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
            if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
                setIsWifiP2pEnabled(true);
            } else {
                activity.setIsWifiP2pEnabled(false);
            }
        } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
            // The peer list has changed! We should probably do something about
            // that.
        } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {
            // Connection state changed! We should probably do something about
            // that.
        } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {
            DeviceListFragment fragment = (DeviceListFragment) activity.getFragmentManager()
                    .findFragmentById(R.id.frag_list);
            fragment.updateThisDevice((WifiP2pDevice) intent.getParcelableExtra(
                    WifiP2pManager.EXTRA_WIFI_P2P_DEVICE));

        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webServer.isListenting()) {
                new AlertDialog.Builder(this)
                    .setTitle(R.string.warning)
                    .setMessage(R.string.dialog_exit_message)
                    .setPositiveButton(getResources().getString(android.R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    })
                    .setNegativeButton(getResources().getString(android.R.string.cancel), null)
                    .show();
            } else {
                finish();
            }
            return true;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webServer.stop();
    }
}
