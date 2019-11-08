package com.sate7.sate7factorymode.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sate7.sate7factorymode.MainActivity;
import com.sate7.sate7factorymode.R;
import com.sate7.sate7factorymode.XLog;
import com.sate7.sate7factorymode.data.TestBean;
import com.sate7.sate7factorymode.util.SharedPreferencesHelper;

public abstract class BaseFragment extends Fragment implements View.OnClickListener {
    public abstract int setContentView();

    protected TestBean testBean;
    protected ViewGroup buttonsContainer;

    public void setTestBean(TestBean bean) {
        testBean = bean;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.fragment_base, null);
        ViewGroup content = root.findViewById(R.id.content);
        LayoutInflater.from(getContext()).inflate(setContentView(), content, true);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.success).setOnClickListener(this);
        view.findViewById(R.id.fail).setOnClickListener(this);
        view.findViewById(R.id.result_container).setVisibility(needShowResultButton() && testBean != null && !testBean.isFullscreen() ? View.VISIBLE : View.GONE);
        getActivity().setTitle(testBean == null ? getResources().getString(R.string.test_all) : testBean.getTitle());
        buttonsContainer = view.findViewById(R.id.result_container);
    }

    public void showResultButtons() {
        buttonsContainer.setVisibility(View.VISIBLE);
    }

    public void hideResultButtons() {
        buttonsContainer.setVisibility(View.GONE);
    }

    public boolean isResultShowing() {
        return buttonsContainer.getVisibility() == View.VISIBLE;
    }

    @Override
    public void onClick(View view) {
        XLog.d("onClick ..." + view.getId());
        switch (view.getId()) {
            case R.id.success:
                handlerResult(true);
                break;
            case R.id.fail:
                handlerResult(false);
                break;
            default:
                break;
        }
    }

    private void handlerResult(boolean success) {
        if (testBean != null) {
            SharedPreferencesHelper.saveResult(getContext(), testBean.getTitle(), success);
        }
        MainActivity mainActivity = (MainActivity) getActivity();
        mainActivity.goToMain();
    }


    public boolean needShowResultButton() {
        return true;
    }
}
