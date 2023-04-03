package com.app.iris.fragment;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.iris.Adapter.SettingAdapter;
import com.app.iris.R;
import com.app.iris.model.Setting;

import java.util.ArrayList;
import java.util.List;


public class SettingFragment extends Fragment implements View.OnClickListener {

    private SettingAdapter settingAdapter;
    private RecyclerView rv_main;
    private LinearLayout ll_main;
    private List<Setting> mainModelList = new ArrayList<>();
    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View layout_view = inflater.inflate(R.layout.fragment_setting, container, false);
        mContext = getContext();

        if(getActivity() !=null){
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(getActivity(), R.color.white));
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }


        rv_main = layout_view.findViewById(R.id.rv_main);

        mainModelList = new ArrayList<>();

        TypedArray titleArray = getResources().obtainTypedArray(R.array.settings);
        for (int pos = 0; pos < titleArray.length(); pos++) {
            Setting model = new Setting();
            model.setTitle(titleArray.getString(pos));
            mainModelList.add(model);
        }

        LinearLayoutManager layoutManager1 = new LinearLayoutManager(mContext);
        rv_main.setLayoutManager(layoutManager1);

        settingAdapter = new SettingAdapter(mContext, mainModelList);
        rv_main.setAdapter(settingAdapter);

        return layout_view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }
}
