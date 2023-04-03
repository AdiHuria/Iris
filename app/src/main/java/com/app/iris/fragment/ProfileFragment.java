package com.app.iris.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.iris.Adapter.ProfileAdapter;
import com.app.iris.LoginActivity;
import com.app.iris.R;
import com.app.iris.interfaces.ButtonClickInterface;
import com.app.iris.model.Profile;
import com.app.iris.model.Setting;
import com.app.iris.utils.MyUtils;

import java.util.ArrayList;
import java.util.List;


public class ProfileFragment extends Fragment implements View.OnClickListener, ButtonClickInterface {

    private ProfileAdapter profileAdapter;
    private RecyclerView rv_profile;
    private LinearLayout ll_main;
    private List<Profile> mainModelList = new ArrayList<>();
    private Context mContext;
    NestedScrollView scrollView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View layout_view = inflater.inflate(R.layout.fragment_profile, container, false);
        mContext = getContext();

        if (getActivity() != null) {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(getActivity(), R.color.white));
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        rv_profile = layout_view.findViewById(R.id.rv_profile);
        scrollView = layout_view.findViewById(R.id.scrollView);

        scrollView.setSmoothScrollingEnabled(true);
        rv_profile.setNestedScrollingEnabled(false);

        mainModelList = new ArrayList<>();
        TypedArray titleArray = getResources().obtainTypedArray(R.array.profile_title);
        TypedArray subTitleArray = getResources().obtainTypedArray(R.array.profile_sub_title);
        TypedArray profileSlugArray = getResources().obtainTypedArray(R.array.profile_slug);
        for (int pos = 0; pos < titleArray.length(); pos++) {
            Profile model = new Profile();
            model.setTitle(titleArray.getString(pos));
            model.setSubTitle(subTitleArray.getString(pos));
            model.setProfileSlug(profileSlugArray.getString(pos));
            mainModelList.add(model);
        }

        LinearLayoutManager layoutManager1 = new LinearLayoutManager(mContext);
        rv_profile.setLayoutManager(layoutManager1);

        profileAdapter = new ProfileAdapter(mContext, mainModelList, this);
        rv_profile.setAdapter(profileAdapter);

        return layout_view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

    @Override
    public void onButtonClick(int position) {
        if (mainModelList != null) {
            switch (mainModelList.get(position).getProfileSlug()) {
                case "log_out":
                    MyUtils.savePreferences(MyUtils.TOKEN, "", getActivity());
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    getActivity().finishAffinity();
                    break;
            }
        }

    }
}
