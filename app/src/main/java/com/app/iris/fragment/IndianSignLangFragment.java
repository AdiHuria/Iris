package com.app.iris.fragment;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.app.iris.Adapter.ProfileAdapter;
import com.app.iris.MainActivity2;
import com.app.iris.R;
import com.app.iris.model.Profile;
import com.app.iris.model.TextToSignModel;

import java.util.ArrayList;
import java.util.List;


public class IndianSignLangFragment extends Fragment implements View.OnClickListener {
    private Context mContext;
    private TextView tv_welcome, tv_convert;
    private RelativeLayout rl_back;
    private EditText et_sign_text;
    private List<TextToSignModel> mainModelList = new ArrayList<>();
    String action_perform ="";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View layout_view = inflater.inflate(R.layout.fragment_indian_sign_lang, container, false);
        mContext = getContext();

        if (getActivity() != null) {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(getActivity(), R.color.colorLightGreen4));
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        //  scrollView = layout_view.findViewById(R.id.scrollView);

        tv_convert = layout_view.findViewById(R.id.tv_convert);
        et_sign_text = layout_view.findViewById(R.id.et_sign_text);
        tv_welcome = layout_view.findViewById(R.id.tv_welcome);
        rl_back = layout_view.findViewById(R.id.rl_back);
        Bundle bundle = getArguments();
        if (bundle != null) {
            action_perform = bundle.getString("action_perform");
            if (action_perform.equals("indian")) {
                tv_welcome.setText(getResources().getString(R.string.indian_sign));
            } else if (action_perform.equals("american")) {
                tv_welcome.setText(getResources().getString(R.string.american_sign));
            }

            //  int colorCode = bundle.getInt("colorCode", 0);
            //  scrollView.setBackgroundColor(colorCode);
        }


        rl_back.setOnClickListener(this);
        tv_convert.setOnClickListener(this);

        return layout_view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                getParentFragmentManager().popBackStack();
                break;
            case R.id.tv_convert:
                String text = et_sign_text.getText().toString();
                if(!TextUtils.isEmpty(text)){
                    Fragment selectedFragment = new ConvertedSignLangFragment();
                    Bundle bundle1 = new Bundle();
                    bundle1.putString("action_perform", action_perform);
                    bundle1.putString("converted_text", text);
                    selectedFragment.setArguments(bundle1);
                    ((MainActivity2) mContext).switchFragment(selectedFragment, true, "ConvertedSignLangFragment");
                }
                break;
        }
    }
}
