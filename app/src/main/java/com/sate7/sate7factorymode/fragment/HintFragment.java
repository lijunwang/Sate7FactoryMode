package com.sate7.sate7factorymode.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sate7.sate7factorymode.R;
import com.sate7.sate7factorymode.XLog;

public class HintFragment extends BaseFragment {
    protected TextView info;

    @Override
    public int setContentView() {
        return R.layout.fragment_hint;
    }


    public String setTestContent() {
        return "";
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        info = view.findViewById(R.id.hint);
        XLog.d("HintFragment onViewCreated ..." + info);
        if (!TextUtils.isEmpty(setTestContent())) {
            info.setText(setTestContent());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
