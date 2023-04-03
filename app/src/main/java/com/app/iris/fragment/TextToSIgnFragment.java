package com.app.iris.fragment;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.iris.Adapter.MeditationAdapter;
import com.app.iris.Adapter.ProfileAdapter;
import com.app.iris.MainActivity2;
import com.app.iris.R;
import com.app.iris.model.MainModel;
import com.app.iris.model.Profile;
import com.app.iris.model.TextToSignModel;

import java.util.ArrayList;
import java.util.List;

public class TextToSIgnFragment extends Fragment implements View.OnClickListener {

    private Context mContext;
    private TextView tv_try_indian_now, tv_try_american_now;
    RelativeLayout rl_back;
    private ScrollView scrollView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View layout_view = inflater.inflate(R.layout.fragment_text_to_sign, container, false);
        mContext = getContext();
        tv_try_indian_now = layout_view.findViewById(R.id.tv_try_indian_now);
        tv_try_american_now = layout_view.findViewById(R.id.tv_try_american_now);
        rl_back = layout_view.findViewById(R.id.rl_back);
        scrollView = layout_view.findViewById(R.id.scrollView);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            int colorCode = bundle.getInt("colorCode", 0);
            scrollView.setBackgroundColor(colorCode);

            if(getActivity() !=null){
                Window window = getActivity().getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(colorCode);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }



        tv_try_indian_now.setOnClickListener(this);
        tv_try_american_now.setOnClickListener(this);
        rl_back.setOnClickListener(this);
        return layout_view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_try_indian_now:
                Fragment selectedFragment = new IndianSignLangFragment();
                Bundle bundle = new Bundle();
                bundle.putString("action_perform", "indian");
                selectedFragment.setArguments(bundle);
                ((MainActivity2) mContext).switchFragment(selectedFragment, true, "IndianSignLangFragment");
                break;
            case R.id.tv_try_american_now:
                Fragment selectedFragment1 = new IndianSignLangFragment();
                Bundle bundle1 = new Bundle();
                bundle1.putString("action_perform", "american");
                selectedFragment1.setArguments(bundle1);
                ((MainActivity2) mContext).switchFragment(selectedFragment1, true, "IndianSignLangFragment");
                break;
            case R.id.rl_back:
                getParentFragmentManager().popBackStack();
                break;
        }
    }
}
