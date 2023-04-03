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
import com.app.iris.model.MainModel;

import java.util.List;

public class MeditationAdapter extends RecyclerView.Adapter<MeditationAdapter.ViewHolder> {
    private Context mContext;
    private List<MainModel> mApplyLeaveList;
    private ButtonClickInterface mButtonClickInterface;

    public MeditationAdapter(Context context, List<MainModel> applyLeaveList, ButtonClickInterface buttonClickInterface) {
        this.mContext = context;
        this.mApplyLeaveList = applyLeaveList;
        this.mButtonClickInterface = buttonClickInterface;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.single_raw_meditation, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        /*
        holder.card_view.setBackgroundColor(colors);
       float radius = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, mContext.getResources().getDimension(R.dimen._15sdp), mContext.getResources().getDisplayMetrics());

        holder.card_view.setRadius(mContext.getResources().getDimension(R.dimen._15sdp));*/

        GradientDrawable shape = new GradientDrawable();
        shape.setCornerRadius(mContext.getResources().getDimension(R.dimen._15sdp));
        int colors = mApplyLeaveList.get(position).getColors();
        shape.setColor(colors);
        int textColors = mApplyLeaveList.get(position).getColorsText();
        holder.ll_main.setBackground(shape);
        holder.tv_try_now.setText(mApplyLeaveList.get(position).getTitle());
        holder.tv_try_now.setTextColor(textColors);
        holder.ll_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mButtonClickInterface.onButtonClick(position);
            }
        });
        holder.iv_image.setImageDrawable(mApplyLeaveList.get(position).getIcons());
    }

    @Override
    public int getItemCount() {
        return mApplyLeaveList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        //private CardView card_view;
        private TextView tv_try_now;
        private ImageView iv_image;
        private LinearLayout ll_main;

        public ViewHolder(View view) {
            super(view);
            //  card_view = view.findViewById(R.id.card_view);
            ll_main = view.findViewById(R.id.ll_main);
            tv_try_now = view.findViewById(R.id.tv_try_now);
            iv_image = view.findViewById(R.id.iv_image);
        }
    }
}