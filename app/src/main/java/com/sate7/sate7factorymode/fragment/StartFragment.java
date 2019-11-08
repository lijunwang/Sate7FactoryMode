package com.sate7.sate7factorymode.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sate7.sate7factorymode.MainActivity;
import com.sate7.sate7factorymode.R;
import com.sate7.sate7factorymode.data.TestAdapter;

public class StartFragment extends BaseFragment {
    private RecyclerView recyclerView;
    private TestAdapter adapter;
    private GridLayoutManager layoutManager;
    private Button autoTest;

    @Override
    public int setContentView() {
        return R.layout.start_fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        autoTest = view.findViewById(R.id.auto_test);
        autoTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).startAutoTest();
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new TestAdapter(getContext());
        layoutManager = new GridLayoutManager(getContext(), 4);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean needShowResultButton() {
        return false;
    }
}
