package com.sate7.sate7factorymode.fragment;

import android.os.Build;

import com.sate7.sate7factorymode.XLog;

import java.lang.reflect.Method;

public class VersionFragment extends HintFragment {
    @Override
    public String setTestContent() {
        return getBuildDate();
    }

    private String getBuildDate() {
        try {
            Class systemProperty = Class.forName("android.os.SystemProperties");
            Method get = systemProperty.getDeclaredMethod("get", String.class);
            return (String) get.invoke(null, "ro.build.date");
        } catch (Exception e) {
            e.printStackTrace();
            XLog.d("getBuildDate Exception:" + e.getMessage());
        }
        return "null";
    }
}
