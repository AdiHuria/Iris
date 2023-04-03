package com.app.iris.Adapter;


import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.app.iris.R;
import com.app.iris.model.Setting;

import java.util.List;

public class SettingAdapter extends RecyclerView.Adapter<SettingAdapter.ViewHolder> {
    private Context mContext;
    private List<Setting> mApplyLeaveList;

    public SettingAdapter(Context context, List<Setting> applyLeaveList) {
        this.mContext = context;
        this.mApplyLeaveList = applyLeaveList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.single_raw_setting, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.tv_title.setText(mApplyLeaveList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return mApplyLeaveList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        //private CardView card_view;
        private TextView tv_title;

        public ViewHolder(View view) {
            super(view);
            //  card_view = view.findViewById(R.id.card_view);

            tv_title = view.findViewById(R.id.tv_title);
        }
    }
}