package com.app.iris.fragment;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.iris.Adapter.ProfileAdapter;
import com.app.iris.R;
import com.app.iris.model.Profile;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class TextToSpeechFragment extends Fragment implements View.OnClickListener {

    private ProfileAdapter profileAdapter;
    private ImageView iv_speech;
    RelativeLayout rl_back;
    private List<Profile> mainModelList = new ArrayList<>();
    private Context mContext;
    private EditText et_text;
    TextToSpeech t1;
    private ScrollView scrollView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View layout_view = inflater.inflate(R.layout.fragment_text_to_speech, container, false);
        mContext = getContext();
        et_text = layout_view.findViewById(R.id.et_text);
        rl_back = layout_view.findViewById(R.id.rl_back);
        iv_speech = layout_view.findViewById(R.id.iv_speech);
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

        t1=new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                }
            }
        });

        iv_speech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak = et_text.getText().toString();
              //  Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        rl_back.setOnClickListener(this);

        return layout_view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                getParentFragmentManager().popBackStack();
                break;
        }
    }
}
