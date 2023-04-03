/*
 * Copyright (C) 2016 Ronald Martin <hello@itsronald.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Last modified 10/12/16 11:22 PM.
 */

package com.app.iris.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.app.iris.ScrollingActivity;
import com.app.iris.R;
import com.app.iris.Welcome2Activity;


/**
 * A simple {@link Fragment} subclass that displays its page number in a ViewPager.
 * <p>
 * <p>
 * Use the {@link PageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PageFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_TEXT = "param1";
    private static final String ARG_TEXT2 = "param2";
    private static final String ARG_Pos = "ARG_Pos";
    private static int mPos = 0;
    static RelativeLayout ly_main;
    static ImageView images;
    @Nullable
    private String pageText,subTitle;
    static Context mContext;
    public PageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param pageText Parameter 1.
     * @return A new instance of fragment PageFragment.
     */
    public static PageFragment newInstance(@NonNull final String pageText,String sub_title,int pos) {
        PageFragment fragment = new PageFragment();

        Bundle args = new Bundle();
        args.putString(ARG_TEXT, pageText);
        args.putString(ARG_TEXT2, sub_title);
        args.putInt(ARG_Pos, pos);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pageText = getArguments().getString(ARG_TEXT);
            subTitle = getArguments().getString(ARG_TEXT2);
            mPos = getArguments().getInt(ARG_Pos);
            mContext = getActivity();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //View view = inflater.inflate(R.layout.fragment_page, container, false);
        View view = inflater.inflate(R.layout.single_raw_viewpager, container, false);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        TextView sub_title = (TextView) view.findViewById(R.id.sub_title);
        images = (ImageView) view.findViewById(R.id.images);
        TextView tv_go = (TextView) view.findViewById(R.id.tv_go);
        ly_main = (RelativeLayout) view.findViewById(R.id.ly_main);
        switch (mPos){
            case 0:
                tv_go.setVisibility(View.GONE);
                ly_main.setBackgroundColor(getResources().getColor(R.color.colorLightPink));
                images.setImageResource(R.drawable.scrolling_1);
                break;
            case 1:
                tv_go.setVisibility(View.GONE);
                ly_main.setBackgroundColor(getResources().getColor(R.color.colorLightGreen));
                images.setImageResource(R.drawable.scrolling_2);
                break;
            case 2:
                tv_go.setVisibility(View.VISIBLE);
                images.setImageResource(R.drawable.scrolling_3);
                ly_main.setBackgroundColor(getResources().getColor(R.color.colorBlue2));
                tv_go.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getActivity(), Welcome2Activity.class));
                        getActivity().finishAffinity();
                    }
                });
                break;
        }
        if (tv_title != null) {
            tv_title.setText(pageText);
        }
        if (sub_title != null) {
            sub_title.setText(subTitle);
        }
        return view;
    }

    public static void updateBg(int pos){
        switch (pos){
            case 0:
                ly_main.setBackgroundColor(mContext.getResources().getColor(R.color.colorLightPink));
                images.setImageResource(R.drawable.scrolling_1);
                break;
            case 1:
                ly_main.setBackgroundColor(mContext.getResources().getColor(R.color.colorLightGreen));
                images.setImageResource(R.drawable.scrolling_2);
                break;
            case 2:
                ly_main.setBackgroundColor(mContext.getResources().getColor(R.color.colorBlue2));
                images.setImageResource(R.drawable.scrolling_3);
                break;
        }
    }
}
