package com.sate7.sate7factorymode.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sate7.sate7factorymode.FactoryTestApp;
import com.sate7.sate7factorymode.MainActivity;
import com.sate7.sate7factorymode.R;
import com.sate7.sate7factorymode.XLog;
import com.sate7.sate7factorymode.fragment.BaseFragment;
import com.sate7.sate7factorymode.util.SharedPreferencesHelper;

public class TestAdapter extends RecyclerView.Adapter implements SharedPreferences.OnSharedPreferenceChangeListener {

    private Context mContext;

    public TestAdapter(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TestHolder(LayoutInflater.from(mContext).inflate(R.layout.test_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        TestHolder testHolder = (TestHolder) holder;
        testHolder.title.setText(FactoryTestApp.mTestList.get(position).getTitle());

        FactoryTestApp.mTestList.get(position).getTitle();
        testHolder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XLog.d("TestHolder ... " + FactoryTestApp.mTestList.get(position).getTitle());
                handlerTestItemClicked(FactoryTestApp.mTestList.get(position));
            }
        });

        if (SharedPreferencesHelper.isSuccess(mContext, FactoryTestApp.mTestList.get(position).getTitle())) {
            testHolder.state.setImageResource(R.mipmap.test_success);
        } else {
            testHolder.state.setImageResource(R.mipmap.test_failed);
        }
    }

    private void handlerTestItemClicked(TestBean bean) {
        XLog.d("handlerTestItemClicked ... " + (mContext instanceof MainActivity) + bean.getFragmentName());
        MainActivity mainActivity = (MainActivity) mContext;
        try {
            Class baseFragmentClass = Class.forName("com.sate7.sate7factorymode.fragment." + bean.getFragmentName());
            BaseFragment baseFragment = (BaseFragment) baseFragmentClass.newInstance();
            mainActivity.goTo(baseFragment, bean);
            mainActivity.exitAutoTest();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("startFragment", "Exception :" + e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return FactoryTestApp.mTestList.size();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        XLog.d("onSharedPreferenceChanged ... " + key + "," + sharedPreferences.getAll() + "," + Thread.currentThread().getName());
        notifyDataSetChanged();
    }


    @Override
    public void onViewAttachedToWindow(@NonNull RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        SharedPreferencesHelper.listenChange(mContext, this);
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        SharedPreferencesHelper.unListenChange(mContext, this);
    }

    private class TestHolder extends RecyclerView.ViewHolder {
        Button title;
        ImageView state;

        public TestHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.test_title);
            state = itemView.findViewById(R.id.test_state);
        }
    }
}
