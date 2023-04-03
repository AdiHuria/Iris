package com.app.iris.fragment;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.iris.Adapter.HomeAdapter;
import com.app.iris.MainActivity2;
import com.app.iris.R;
import com.app.iris.interfaces.ButtonClickInterface;
import com.app.iris.model.MainModel;
import com.app.iris.utils.MyUtils;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements View.OnClickListener, ButtonClickInterface {

    private HomeAdapter homeAdapter;
    private RecyclerView rv_main;
    private LinearLayout ll_main;
    private List<MainModel> mainModelList = new ArrayList<>();
    private TextView tv_no_data, tv_welcome;
    NestedScrollView scrollView;
    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View layout_view = inflater.inflate(R.layout.fragment_home, container, false);
        mContext = getContext();

        if(getActivity() !=null){
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(getActivity(), R.color.white));
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        rv_main = layout_view.findViewById(R.id.rv_main);
        tv_welcome = layout_view.findViewById(R.id.tv_welcome);

        tv_welcome.setText("Welcome back, " + MyUtils.getUserName(mContext)+"!");
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


        TypedArray titleArray = getResources().obtainTypedArray(R.array.title);
        TypedArray subTitleArray = getResources().obtainTypedArray(R.array.sub_title);
        TypedArray colorArray = getResources().obtainTypedArray(R.array.colors);
        TypedArray slugArray = getResources().obtainTypedArray(R.array.sub_slug_name);
        TypedArray iconsArray = getResources().obtainTypedArray(R.array.home_icons);
        mainModelList = new ArrayList<>();
        for (int pos = 0; pos < titleArray.length(); pos++) {
            MainModel model = new MainModel();
            model.setTitle(titleArray.getString(pos));
            model.setSubTitle(subTitleArray.getString(pos));
            model.setSlug_name(slugArray.getString(pos));
            //model.setColors(colorArray.getString(pos));
            model.setColors(Color.parseColor(colorArray.getString(pos)));
            model.setIcons(iconsArray.getDrawable(pos));
            mainModelList.add(model);
        }

        LinearLayoutManager layoutManager1 = new LinearLayoutManager(mContext);
        rv_main.setLayoutManager(layoutManager1);

        homeAdapter = new HomeAdapter(mContext, mainModelList, this);
        rv_main.setAdapter(homeAdapter);

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
            Fragment selectedFragment = null;
            switch (mainModelList.get(position).getSlug_name()) {
                case "text_to_speech":
                    selectedFragment = new TextToSpeechFragment();
                    break;
                case "speech_to_sign":
                    break;
                case "sign_to_text":
                    selectedFragment = new TextToSIgnFragment();
                    break;
                case "speech_to_text":
                    selectedFragment = new SpeechToTextFragment();
                    break;
                case "meditation":
                    selectedFragment = new MeditationFragment();
                    break;
            }
            if (selectedFragment != null) {
                Bundle bundle = new Bundle();
                bundle.putInt("colorCode", mainModelList.get(position).getColors());
                selectedFragment.setArguments(bundle);
                ((MainActivity2) mContext).switchFragment(selectedFragment, true, "EventDetailFragment");
            }
        }
    }
}
