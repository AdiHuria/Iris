package com.app.iris.fragment;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.app.Activity.RESULT_OK;
import static android.os.Build.VERSION.SDK_INT;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.speech.RecognizerIntent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.app.iris.Adapter.ProfileAdapter;
import com.app.iris.MusicPlayerActivity;
import com.app.iris.R;
import com.app.iris.model.Profile;
import com.app.iris.utils.MyUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class SpeechToTextFragment extends Fragment implements View.OnClickListener {

    private ScrollView scrollView;
    private ImageView iv_speech;
    private TextView tv_text, tv_download_text;
    private List<Profile> mainModelList = new ArrayList<>();
    private Context mContext;
    RelativeLayout rl_back;
    private ActivityResultLauncher<String> someActivityResultLauncher;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View layout_view = inflater.inflate(R.layout.fragment_speech_to_text, container, false);
        mContext = getContext();
        tv_download_text = layout_view.findViewById(R.id.tv_download_text);
        iv_speech = layout_view.findViewById(R.id.iv_speech);
        tv_text = layout_view.findViewById(R.id.tv_text);
        rl_back = layout_view.findViewById(R.id.rl_back);
        scrollView = layout_view.findViewById(R.id.scrollView);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            int colorCode = bundle.getInt("colorCode", 0);
            scrollView.setBackgroundColor(colorCode);

            if (getActivity() != null) {
                Window window = getActivity().getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(colorCode);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }

        iv_speech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Need to speak");
                try {
                    startActivityForResult(intent, 101);
                } catch (ActivityNotFoundException a) {
                    Toast.makeText(getActivity(),
                            "Sorry your device not supported",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                new ActivityResultCallback<Boolean>() {
                    @Override
                    public void onActivityResult(Boolean result) {
                        if (result) {
                            callAction();
                            MyUtils.v("onActivityResult: PERMISSION GRANTED");
                        } else {
                            MyUtils.showPermissionAlert(getActivity());
                            MyUtils.v("onActivityResult: PERMISSION DENIED");
                        }
                    }
                });

        rl_back.setOnClickListener(this);
        tv_download_text.setOnClickListener(this);
        return layout_view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 101: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    tv_text.setText((CharSequence) result.get(0));
                }
                break;
            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_back:
                getParentFragmentManager().popBackStack();
                break;
            case R.id.tv_download_text:

                if (SDK_INT >= Build.VERSION_CODES.R) {
                    if (MyUtils.checkPermission(getActivity())) {
                        callAction();
                    } else {
                        MyUtils.requestPermission(getActivity());
                    }
                } else {
                    if (SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(getActivity(), READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            someActivityResultLauncher.launch(WRITE_EXTERNAL_STORAGE);
                        } else {
                            callAction();
                        }
                    } else {
                        callAction();
                    }

                }
                break;
        }
    }

    private void callAction() {
        String textData = tv_text.getText().toString();
        if (!TextUtils.isEmpty(textData))
            writeToFile(textData);

    }

    private void writeToFile(String content) {
        try {
            //  File file = new File(Environment.getExternalStorageDirectory() + "/test.txt");
            String myfolder = Environment.getExternalStorageDirectory() + "/" + "iris" + "/" + "files";
            File pdfFolder = new File(myfolder);
            if (!pdfFolder.exists()) {
                pdfFolder.mkdirs();
            }
            String fileFinalPath = pdfFolder.getAbsolutePath() + "/" + "speech_to_text_" + System.currentTimeMillis() / 1000+".txt";
            FileWriter writer = new FileWriter(fileFinalPath);
            writer.append(content);
            writer.flush();
            writer.close();
            Toast.makeText(mContext, "File save successfully.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
