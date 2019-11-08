package com.sate7.sate7factorymode.fragment;

import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sate7.sate7factorymode.R;
import com.sate7.sate7factorymode.XLog;
import com.sate7.sate7factorymode.view.OnItemTestCompleteListener;
import com.sate7.sate7factorymode.view.TouchPadView;

public class TouchTestFragment extends BaseFragment implements OnItemTestCompleteListener {
    private final String TAG_IN_TOUCH_TEST = "touch_test";
    private TouchPadView touchPadView;
    @Override
    public int setContentView() {
        return R.layout.fragment_touch;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Settings.System.putInt(getContext().getContentResolver(), TAG_IN_TOUCH_TEST, 1);
        touchPadView = view.findViewById(R.id.touchPadView);
        touchPadView.setOnItemTestListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Settings.System.putInt(getContext().getContentResolver(), TAG_IN_TOUCH_TEST, 0);
    }

    @Override
    public void onItemComplete() {
        showResultButtons();
    }
}
