package com.app.iris.fragment;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.os.Build.VERSION.SDK_INT;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.iris.Adapter.MeditationAdapter;
import com.app.iris.Adapter.SignImagesAdapter;
import com.app.iris.R;
import com.app.iris.model.TextToSignModel;
import com.app.iris.servie.DownloadNotificationService;
import com.app.iris.utils.MyUtils;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;


public class ConvertedSignLangFragment extends Fragment implements View.OnClickListener {
    private Context mContext;
    private TextView tv_welcome, tv_download_text,tv_no_data;
    private RelativeLayout rl_back;
    private EditText et_sign_text;
    private List<TextToSignModel> mainModelList = new ArrayList<>();
    //  private ImageView iv_sing_image;
    private int index = -1;
    private RecyclerView rv_main;
    public static final String PROGRESS_UPDATE = "progress_update";
    private ActivityResultLauncher<String> someActivityResultLauncher;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View layout_view = inflater.inflate(R.layout.fragment_converted_sign_lang, container, false);
        mContext = getContext();

        if (getActivity() != null) {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(getActivity(), R.color.colorLightGreen4));
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        //  scrollView = layout_view.findViewById(R.id.scrollView);

        tv_no_data = layout_view.findViewById(R.id.tv_no_data);
        rv_main = layout_view.findViewById(R.id.rv_main);
        tv_download_text = layout_view.findViewById(R.id.tv_download_text);
        et_sign_text = layout_view.findViewById(R.id.et_sign_text);
        //  iv_sing_image = layout_view.findViewById(R.id.iv_sing_image);
        tv_welcome = layout_view.findViewById(R.id.tv_welcome);
        rl_back = layout_view.findViewById(R.id.rl_back);
        Bundle bundle = getArguments();
        if (bundle != null) {
            String action_perform = bundle.getString("action_perform");
            String converted_text = bundle.getString("converted_text");
            List<Character> characterList = null;
            TypedArray signToTextImageImageArray = null;
            TypedArray signToTextArray= null;
            mainModelList = new ArrayList<>();
            if (action_perform.equals("indian")) {
                signToTextImageImageArray = getResources().obtainTypedArray(R.array.sign_to_text_images);
                signToTextArray = getResources().obtainTypedArray(R.array.sign_to_text_tex);
                characterList =  asListUpperCase(converted_text);
                tv_welcome.setText(getResources().getString(R.string.indian_sign));
            } else if (action_perform.equals("american")) {
                signToTextImageImageArray = getResources().obtainTypedArray(R.array.american_sign_to_text_images);
                signToTextArray = getResources().obtainTypedArray(R.array.american_sign_to_text_tex);
                characterList =  asListLowerCase(converted_text);
                tv_welcome.setText(getResources().getString(R.string.american_sign));
            }

            for (int kos = 0; kos < characterList.size(); kos++) {
                // if (characterList.contains(signToTextArray.getString(pos).charAt(0)))
                for (int pos = 0; pos < signToTextImageImageArray.length(); pos++) {
                    TextToSignModel model = new TextToSignModel();
                    model.setTitle(signToTextArray.getString(pos));
                    model.setIcons(signToTextImageImageArray.getDrawable(pos));

                    if (characterList.get(kos).equals(signToTextArray.getString(pos).charAt(0)))
                        mainModelList.add(model);
                }
            }
            MyUtils.v("characterList check "+characterList);
            MyUtils.v("characterList check 1 "+signToTextImageImageArray);
            LinearLayoutManager layoutManager1 = new LinearLayoutManager(mContext);
            rv_main.setLayoutManager(layoutManager1);

            if(mainModelList.size() > 0){
                tv_no_data.setVisibility(View.GONE);
                rv_main.setVisibility(View.VISIBLE);
                SignImagesAdapter signImagesAdapter = new SignImagesAdapter(mContext, mainModelList);
                rv_main.setAdapter(signImagesAdapter);
            }else {
                tv_no_data.setVisibility(View.VISIBLE);
                rv_main.setVisibility(View.GONE);
            }



            /*TextToSignModel indexModel = new TextToSignModel();
            indexModel.setTitle(converted_text);
            index = mainModelList.indexOf(indexModel);
            MyUtils.v("Check index value " + index);
            if (index > -1) {
                iv_sing_image.setImageDrawable(mainModelList.get(index).getIcons());
            }*/




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
            //  int colorCode = bundle.getInt("colorCode", 0);
            //  scrollView.setBackgroundColor(colorCode);
        }

        rl_back.setOnClickListener(this);
        //    tv_download_text.setOnClickListener(this);

        registerReceiver();


        return layout_view;
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
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
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

    /**
     * 
     */
    private void callAction() {
        if (index > -1 && mainModelList != null) {
            Bitmap bitmap = ((BitmapDrawable) mainModelList.get(index).getIcons()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] b = baos.toByteArray();

            Intent intent = new Intent(getActivity(), DownloadNotificationService.class);
            //   intent.putExtra("drawableModelList",  (Serializable) mainModelList);
            intent.putExtra("byteArray", b);
            intent.putExtra("fileName", mainModelList.get(index).getTitle());
            intent.putExtra("index", index);
            mContext.startService(intent);
        }
    }

    private void registerReceiver() {

        LocalBroadcastManager bManager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(PROGRESS_UPDATE);
        bManager.registerReceiver(mBroadcastReceiver, intentFilter);

    }

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals(PROGRESS_UPDATE)) {

                boolean downloadComplete = intent.getBooleanExtra("downloadComplete", false);
                String fileFinalPath = intent.getStringExtra("fileFinalPath");
                //Log.d("API123", download.getProgress() + " current progress");

                if (downloadComplete) {

                }
            }
        }
    };

    public List<Character> asListUpperCase(final String string) {
        return new AbstractList<Character>() {
            public int size() {
                return string.length();
            }

            public Character get(int index) {
                return string.toUpperCase(Locale.ROOT).charAt(index);
            }
        };
    }

    public List<Character> asListLowerCase(final String string) {
        return new AbstractList<Character>() {
            public int size() {
                return string.length();
            }

            public Character get(int index) {
                return string.toLowerCase(Locale.ROOT).charAt(index);
            }
        };
    }
}
