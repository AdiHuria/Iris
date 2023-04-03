package com.app.iris.fragment;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.iris.Adapter.MeditationAdapter;
import com.app.iris.MainActivity2;
import com.app.iris.R;
import com.app.iris.interfaces.ButtonClickInterface;
import com.app.iris.model.MainModel;

import java.util.ArrayList;
import java.util.List;


public class MeditationFragment extends Fragment implements View.OnClickListener, ButtonClickInterface {

    private MeditationAdapter mainAdapter;
    private RecyclerView rv_main;
    private LinearLayout ll_main;
    private List<MainModel> mainModelList = new ArrayList<>();
    private TextView tv_no_data, tv_welcome;
    NestedScrollView scrollView;
    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View layout_view = inflater.inflate(R.layout.fragment_meditation, container, false);
        mContext = getContext();

        rv_main = layout_view.findViewById(R.id.rv_main);
        tv_welcome = layout_view.findViewById(R.id.tv_welcome);

        scrollView = layout_view.findViewById(R.id.scrollView);

        scrollView.setSmoothScrollingEnabled(true);
        rv_main.setNestedScrollingEnabled(false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                }
            });
        }


        TypedArray titleArray = getResources().obtainTypedArray(R.array.meditation_title);
        TypedArray colorArray = getResources().obtainTypedArray(R.array.meditation_color);
        TypedArray colorTextArray = getResources().obtainTypedArray(R.array.meditation_text_color);
        TypedArray iconsArray = getResources().obtainTypedArray(R.array.meditation_icons);
        mainModelList = new ArrayList<>();
        for (int pos = 0; pos < titleArray.length(); pos++) {
            MainModel model = new MainModel();
            model.setTitle(titleArray.getString(pos));
           // model.setSlug_name(slugArray.getString(pos));
            //model.setColors(colorArray.getString(pos));
            model.setColorsText(Color.parseColor(colorTextArray.getString(pos)));
            model.setColors(Color.parseColor(colorArray.getString(pos)));
            model.setIcons(iconsArray.getDrawable(pos));
            mainModelList.add(model);
        }

        LinearLayoutManager layoutManager1 = new GridLayoutManager(mContext, 2);
        rv_main.setLayoutManager(layoutManager1);

        mainAdapter = new MeditationAdapter(mContext, mainModelList, this);
        rv_main.setAdapter(mainAdapter);

        return layout_view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

    @Override
    public void onButtonClick(int position) {

        if (mainModelList != null && mainModelList.size() > 0) {
            Fragment selectedFragment = new ReminderFragment();
            ((MainActivity2) mContext).switchFragment(selectedFragment, true, "EventDetailFragment");
        }
    }
}
