package com.app.iris;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.iris.Adapter.HomeAdapter;
import com.app.iris.interfaces.ButtonClickInterface;
import com.app.iris.model.MainModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ButtonClickInterface {

    private HomeAdapter homeAdapter;
    private RecyclerView rv_main;
    private LinearLayout ll_main;
    private List<MainModel> mainModelList = new ArrayList<>();
    private TextView tv_no_data,tv_welcome;
    NestedScrollView scrollView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);

        rv_main = findViewById(R.id.rv_main);
        tv_welcome = findViewById(R.id.tv_welcome);

        tv_welcome.setText("Welcome back, "+"Roh!");
        scrollView = findViewById(R.id.scrollView);

        scrollView.setSmoothScrollingEnabled(true);
        rv_main.setNestedScrollingEnabled(false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                }
            });
        }

        mainModelList = new ArrayList<>();
        TypedArray titleArray = getResources().obtainTypedArray(R.array.title);
        TypedArray subTitleArray = getResources().obtainTypedArray(R.array.sub_title);
        TypedArray colorArray = getResources().obtainTypedArray(R.array.colors);
        for (int pos = 0; pos < titleArray.length(); pos++) {
            MainModel model = new MainModel();
            model.setTitle(titleArray.getString(pos));
            model.setSubTitle(subTitleArray.getString(pos));
            //model.setColors(colorArray.getString(pos));
            model.setColors(Color.parseColor(colorArray.getString(pos)));
            mainModelList.add(model);
        }

        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this);
        rv_main.setLayoutManager(layoutManager1);

        homeAdapter = new HomeAdapter(this, mainModelList,MainActivity.this);
        rv_main.setAdapter(homeAdapter);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_start:
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }
    }

    @Override
    public void onButtonClick(int position) {

    }
}