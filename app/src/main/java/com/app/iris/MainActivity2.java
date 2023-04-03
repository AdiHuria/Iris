package com.app.iris;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.app.iris.Adapter.HomeAdapter;
import com.app.iris.fragment.HomeFragment;
import com.app.iris.fragment.ProfileFragment;
import com.app.iris.fragment.SettingFragment;
import com.app.iris.model.MainModel;
import com.app.iris.utils.MyUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener {

    private HomeAdapter homeAdapter;
    private RecyclerView rv_main;
    private LinearLayout ll_main;
    private List<MainModel> mainModelList = new ArrayList<>();
    private TextView tv_no_data,tv_welcome;
    private BottomNavigationView mMainNV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_2);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(MainActivity2.this, R.color.white));

        mMainNV = findViewById(R.id.navigation);

        mMainNV.getMenu().getItem(1).setChecked(true);
        mMainNV.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                String name = "";
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(getResources().getColor(R.color.colorLightOrange));
             //   mMainNV.setItemBackground(getResources().getDrawable(R.drawable.rounded_top_visible_line));
                switch (item.getItemId()) {
                    case R.id.item_setting:
                       // homeCall();
                        selectedFragment = new SettingFragment();
                        break;
                    case R.id.item_home:
                      //  calenderCall();
                        selectedFragment = new HomeFragment();
                        break;
                    case R.id.item_profile:
                        selectedFragment = new ProfileFragment();
                        break;
                }

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.main_fragment_container, selectedFragment);
                Bundle bundle = new Bundle();
                bundle.putString("user_type", "user");
                selectedFragment.setArguments(bundle);
                transaction.commit();

                return true;
            }
        });


        Fragment currentFragment = new HomeFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_fragment_container, currentFragment);
        transaction.commit();

       /* Map<String, String> myMap = new HashMap<>();
        myMap.put("One", "A");
        myMap.put("Two", "B");
        myMap.put("Three", "C");

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            Map<String, String> result = myMap.entrySet()
                    .stream()
                    .filter(map -> map.getValue().equals("AB"))
                    .collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue()));

            MyUtils.v("Check filter value"+ result);
        }*/

    }

    public void switchFragment(Fragment fragment, boolean isAddBackStack, String tag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_fragment_container, fragment, tag);
        if (isAddBackStack) transaction.addToBackStack(null);
        transaction.commit();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_start:
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
        }
    }
}