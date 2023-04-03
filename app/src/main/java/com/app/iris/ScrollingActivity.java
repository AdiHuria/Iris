package com.app.iris;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.iris.fragment.PageFragment;
import com.itsronald.widget.ViewPagerIndicator;

public class ScrollingActivity extends AppCompatActivity {

    //   HomeAdapter chartsAdapter;
    // ViewPager viewPager;
    // private DotsIndicator dotsIndicator;
    private RelativeLayout rl_main;
    Window window;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);

        rl_main = findViewById(R.id.rl_main);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));

        window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(ScrollingActivity.this, R.color.colorLightPink));

        final ViewPager.LayoutParams layoutParams = new ViewPager.LayoutParams();
        layoutParams.width = RelativeLayout.LayoutParams.MATCH_PARENT;
        layoutParams.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = Gravity.BOTTOM;


        final ViewPagerIndicator viewPagerIndicator = new ViewPagerIndicator(this);
        viewPager.addView(viewPagerIndicator, layoutParams);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                        window.setStatusBarColor(ContextCompat.getColor(ScrollingActivity.this, R.color.colorLightPink));

                        PageFragment.updateBg(position);
                        rl_main.setBackgroundColor(getResources().getColor(R.color.colorLightPink));
                        break;
                    case 1:
                        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                        window.setStatusBarColor(ContextCompat.getColor(ScrollingActivity.this, R.color.colorLightGreen));

                        PageFragment.updateBg(position);
                        rl_main.setBackgroundColor(getResources().getColor(R.color.colorLightGreen));
                        break;
                    case 2:
                        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                        window.setStatusBarColor(ContextCompat.getColor(ScrollingActivity.this, R.color.colorBlue2));

                        PageFragment.updateBg(position);
                        rl_main.setBackgroundColor(getResources().getColor(R.color.colorBlue2));
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private class PagerAdapter extends FragmentPagerAdapter {

        private static final int NUM_PAGES = 3;

        private PagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }

        @Override
        public Fragment getItem(int position) {
            String title = "", subTitle = "";
            switch (position) {
                case 0:
                    title = getResources().getString(R.string.title_1);
                    subTitle = getResources().getString(R.string.sub_title_1);
                    break;
                case 1:
                    title = getResources().getString(R.string.title_2);
                    subTitle = getResources().getString(R.string.sub_title_2);
                    break;
                case 2:
                    title = getResources().getString(R.string.title_3);
                    subTitle = getResources().getString(R.string.sub_title_3);
                    break;
            }
            return PageFragment.newInstance(title, subTitle, position);
        }
    }
}