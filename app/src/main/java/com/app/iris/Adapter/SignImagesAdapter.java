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
import com.app.iris.interfaces.ButtonClickInterface;
import com.app.iris.model.TextToSignModel;

import java.util.List;

public class SignImagesAdapter extends RecyclerView.Adapter<SignImagesAdapter.ViewHolder> {
    private Context mContext;
    private List<TextToSignModel> mApplyLeaveList;
    private ButtonClickInterface mButtonClickInterface;

    public SignImagesAdapter(Context context, List<TextToSignModel> applyLeaveList) {
        this.mContext = context;
        this.mApplyLeaveList = applyLeaveList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.single_raw_sign_images, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {


        holder.iv_image.setImageDrawable(mApplyLeaveList.get(position).getIcons());
    }

    @Override
    public int getItemCount() {
        return mApplyLeaveList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_image;
        private LinearLayout ll_main;

        public ViewHolder(View view) {
            super(view);
            iv_image = view.findViewById(R.id.iv_image);
        }
    }
}