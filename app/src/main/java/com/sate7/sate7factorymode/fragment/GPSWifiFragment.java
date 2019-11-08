package com.sate7.sate7factorymode.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sate7.sate7factorymode.R;
import com.sate7.sate7factorymode.XLog;

import java.util.List;

public class GPSWifiFragment extends BaseFragment {
    private TextView info;
    private LocationManager locationManager;
    private WifiManager wifiManager;
    private String locationProvider;

    @Override
    public int setContentView() {
        return R.layout.fragment_gps_wifi;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        info = view.findViewById(R.id.test_info);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        XLog.d("Wifi GPS onDestroyView ... ");
        if (testBean != null && testBean.getTitle().equals("Wifi")) {
            getActivity().unregisterReceiver(wifiBroadcastReceiver);
        }
        if (!isWifiOnWhenInit) {
            wifiManager.setWifiEnabled(false);
        }

        if (locationManager != null) {
            locationManager.removeUpdates(mLocationListener);
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        XLog.d("onActivityCreated ww ... " + testBean);
        wifiManager = (WifiManager) getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (testBean != null && testBean.getTitle().equals("Wifi")) {
            info.setTextSize(30);
            info.setText("正在搜索wifi ...");
            registerWifi();
            isWifiOnWhenInit = wifiManager.isWifiEnabled();
            if (!isWifiOnWhenInit) {
                wifiManager.setWifiEnabled(true);
                wifiManager.startScan();
            }
        } else if (testBean != null && testBean.getTitle().equals("GPS")) {
            info.setTextSize(30);
            info.setText("正在GPS定位 ...");
            info.setPadding(20, 0, 0, 0);
            info.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
            locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            Criteria criteria;
            criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_FINE);
            criteria.setAltitudeRequired(false);
            criteria.setBearingRequired(false);
            criteria.setCostAllowed(true);
            criteria.setPowerRequirement(Criteria.POWER_HIGH);
            locationProvider = locationManager.getBestProvider(criteria, true);
            locationManager.requestLocationUpdates(locationProvider, 3000, 0,
                    mLocationListener);
            /*mGpsStatusListener = new GpsStatus.Listener() {
                public void onGpsStatusChanged(int event) {
                    GpsStatus status = locationManager.getGpsStatus(null);
                }
            };*/
        }
    }


//    private GpsStatus.Listener mGpsStatusListener;
    private LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            XLog.d("onLocationChanged ..." + location);
            info.setText(getResources().getString(R.string.location, location.getLongitude(), location.getLatitude()));
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            XLog.d("onStatusChanged ..." + provider);
        }

        @Override
        public void onProviderEnabled(String provider) {
            XLog.d("onProviderEnabled ..." + provider);
        }

        @Override
        public void onProviderDisabled(String provider) {
            XLog.d("onProviderDisabled ..." + provider);
        }
    };

    private void registerWifi() {
        wifiIntentFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);//监听wifi是开关变化的状态
        wifiIntentFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);//监听wifiwifi连接状态广播
        wifiIntentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);//监听wifi列表变化（开启一个热点或者关闭一个热点）
        getActivity().registerReceiver(wifiBroadcastReceiver, wifiIntentFilter);
    }

    private WifiBroadcastReceiver wifiBroadcastReceiver = new WifiBroadcastReceiver();
    private IntentFilter wifiIntentFilter = new IntentFilter();
    private StringBuilder stringBuilder = new StringBuilder();
    private boolean isWifiOnWhenInit = false;

    private class WifiBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            XLog.d("onReceive ... " + intent.getAction());
            if (WifiManager.SCAN_RESULTS_AVAILABLE_ACTION.equals(intent.getAction())) {
                List<ScanResult> resultList = wifiManager.getScanResults();
                stringBuilder.delete(0, stringBuilder.length());
                for (ScanResult result : resultList) {
                    XLog.d("SCAN_RESULTS_AVAILABLE_ACTION ... " + result);
                    stringBuilder.append("Wifi名称:" + result.SSID + "\t\t信号强度:" + result.level + "\n");
                }
                info.setTextSize(20);
                info.setText(stringBuilder.toString());
                info.setGravity(Gravity.LEFT);
                info.setPadding(20, 0, 0, 0);
            }
        }
    }

}
