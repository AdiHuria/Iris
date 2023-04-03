package com.app.iris.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.app.iris.R;
import com.app.iris.interfaces.ButtonClickInterface;
import com.app.iris.model.Profile;

import java.util.List;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ViewHolder> {
    private Context mContext;
    private List<Profile> mApplyLeaveList;
    private ButtonClickInterface mButtonClickInterface;

    public ProfileAdapter(Context context, List<Profile> applyLeaveList, ButtonClickInterface buttonClickInterface) {
        this.mContext = context;
        this.mApplyLeaveList = applyLeaveList;
        this.mButtonClickInterface = buttonClickInterface;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.single_raw_profile, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.tv_title.setText(mApplyLeaveList.get(position).getTitle());
        holder.tv_sub_title.setText(mApplyLeaveList.get(position).getTitle());

        holder.rl_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mButtonClickInterface.onButtonClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mApplyLeaveList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        //private CardView card_view;
        private TextView tv_title, tv_sub_title;
        private RelativeLayout rl_main;

        public ViewHolder(View view) {
            super(view);
            //  card_view = view.findViewById(R.id.card_view);

            tv_sub_title = view.findViewById(R.id.tv_sub_title);
            rl_main = view.findViewById(R.id.rl_main);
            tv_title = view.findViewById(R.id.tv_title);
        }
    }
}