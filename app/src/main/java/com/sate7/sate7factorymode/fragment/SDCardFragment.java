package com.sate7.sate7factorymode.fragment;

import android.os.Bundle;
import android.os.Environment;

import androidx.annotation.Nullable;

import com.sate7.sate7factorymode.R;
import com.sate7.sate7factorymode.XLog;

import java.io.File;

public class SDCardFragment extends HintFragment {
    String SDPath = "/storage/sdcard1";

    @Override
    public String setTestContent() {
        return "T卡测试";
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        File sdFile = new File(SDPath);
        XLog.d("onActivityCreated ... " + sdFile.exists() + "," + sdFile.length());
        if (sdFile.exists() && sdFile.length() > 0 && sdFile.canRead() && sdFile.canWrite()) {
            info.setText(R.string.sd_ok);
        } else {
            info.setText(R.string.sd_fail);
        }
    }
}
