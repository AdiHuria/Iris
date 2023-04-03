package com.app.iris.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.app.iris.R;
import com.app.iris.interfaces.ButtonClickInterface;
import com.app.iris.model.DayModel;

import java.util.List;


public class AvailableDateAdapter extends RecyclerView.Adapter<AvailableDateAdapter.ViewHolder> {
    private Activity mActivity;
    Context context;
    private List<DayModel> mDayDateModelList;
    private ButtonClickInterface mAdapterClickInterface;

    public AvailableDateAdapter(Activity activity, List<DayModel> dayDateModelList, ButtonClickInterface adapterClickInterface) {
        this.mActivity = activity;
        this.mDayDateModelList = dayDateModelList;
        this.mAdapterClickInterface = adapterClickInterface;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sigle_date_available_view, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        //holder.tv_date.setText(mDayDateModelList.get(position).getDate());
        holder.tv_day.setText(mDayDateModelList.get(position).getDay());

        /*LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((int) mActivity.getResources().getDimension(R.dimen._45sdp),
                (int) mActivity.getResources().getDimension(R.dimen._60sdp));

        int margin = (int) mActivity.getResources().getDimension(R.dimen._5sdp);
        if (position == 0 || position == mDayDateModelList.size()) {
            layoutParams.setMargins(0, margin, margin, 0);
        } else {
            layoutParams.setMargins(margin, margin, margin, margin);
        }
        holder.ll_main.setLayoutParams(layoutParams);
*/
        if (mDayDateModelList.get(position).isSelected()) {
            holder.ll_main.setBackgroundResource(R.drawable.circle_marun);
            holder.tv_day.setTextColor(mActivity.getResources().getColor(R.color.white));
        } else {
            holder.ll_main.setBackgroundResource(R.drawable.circle_marun_border);
            holder.tv_day.setTextColor(mActivity.getResources().getColor(R.color.colorTextHint));
        }


        holder.ll_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                    selectedList.clear();
//                    selectedList.add(position);
                for (int pos = 0; pos < mDayDateModelList.size(); pos++) {
                    if (pos == position) {
                        mDayDateModelList.get(pos).setSelected(true);
                    } else {
                        mDayDateModelList.get(pos).setSelected(false);
                    }
                }

                notifyDataSetChanged();
                mAdapterClickInterface.onButtonClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDayDateModelList.size();
    }

    public void update(List<DayModel> dayDateModelList) {
        this.mDayDateModelList = dayDateModelList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_day;
        private LinearLayout ll_main;

        public ViewHolder(View view) {
            super(view);
            tv_day = view.findViewById(R.id.tv_day);
            ll_main = view.findViewById(R.id.ll_main);
        }
    }


}
