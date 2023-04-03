package com.app.iris.fragment;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.iris.Adapter.AvailableDateAdapter;
import com.app.iris.MainActivity;
import com.app.iris.MusicPlayerActivity;
import com.app.iris.R;
import com.app.iris.interfaces.ButtonClickInterface;
import com.app.iris.model.DayModel;
import com.app.iris.reciver.MyNotificationPublisher;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class ReminderFragment extends Fragment implements View.OnClickListener, ButtonClickInterface {

    private AvailableDateAdapter availableDateAdapter;
    private RecyclerView rv_available_date;
    private TextView tv_no_thanks,tv_save;
    private List<DayModel> dayModelList = new ArrayList<>();
    NestedScrollView scrollView;
    private Context mContext;
    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
    private final static String default_notification_channel_id = "default" ;
    final Calendar myCalendar = Calendar. getInstance () ;
    TimePicker timePicker;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View layout_view = inflater.inflate(R.layout.fragment_reminder, container, false);
        mContext = getContext();

        if (getActivity() != null) {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(getActivity(), R.color.white));
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        rv_available_date = layout_view.findViewById(R.id.rv_available_date);
        scrollView = layout_view.findViewById(R.id.scrollView);
        tv_no_thanks = layout_view.findViewById(R.id.tv_no_thanks);
        tv_save = layout_view.findViewById(R.id.tv_save);
        timePicker = layout_view.findViewById(R.id.timePicker1);

        scrollView.setSmoothScrollingEnabled(true);
        rv_available_date.setNestedScrollingEnabled(false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                }
            });
        }

        SimpleDateFormat format = new SimpleDateFormat("EEE");
        SimpleDateFormat format2 = new SimpleDateFormat("dd");
        SimpleDateFormat format3 = new SimpleDateFormat("MM/dd/yyyy");

        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.SUNDAY);
        String todayDate = format2.format(calendar.getTime());
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);

        String[] days = new String[7];
        for (int pos = 0; pos < 7; pos++) {
            days[pos] = format.format(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            DayModel model = new DayModel();
            model.setDate(format2.format(calendar.getTime()));
            model.setDay(format.format(calendar.getTime()));
            model.setFullDate(format3.format(calendar.getTime()));
            if (todayDate.equals(format2.format(calendar.getTime())))
                model.setSelected(true);
            else
                model.setSelected(false);
            dayModelList.add(model);
        }

        rv_available_date.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        availableDateAdapter = new AvailableDateAdapter(getActivity(), dayModelList, this);
        rv_available_date.setAdapter(availableDateAdapter);

        tv_no_thanks.setOnClickListener(this);
        tv_save.setOnClickListener(this);
        return layout_view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_no_thanks:
                startActivity(new Intent(getActivity(), MusicPlayerActivity.class));
                break;
            case R.id.tv_save:
                //setDate(v);
                scheduleNotification(getNotification("5 second delay"), 5000);
                break;
        }
    }

    @Override
    public void onButtonClick(int position) {

        if (dayModelList != null && dayModelList.size() > 0) {

        }
    }

    private void scheduleNotification(Notification notification, int delay) {

        Intent notificationIntent = new Intent(getActivity(), MyNotificationPublisher.class);
        notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }

    private Notification getNotification(String content) {
        Notification.Builder builder = new Notification.Builder(getActivity());
        builder.setContentTitle("Scheduled Notification");
        builder.setContentText(content);
        builder.setSmallIcon(R.drawable.ic_launcher_foreground);
        return builder.build();
    }

    private void scheduleNotification (Notification notification , long delay) {
        Intent notificationIntent = new Intent( getActivity(), MyNotificationPublisher. class ) ;
        notificationIntent.putExtra(MyNotificationPublisher. NOTIFICATION_ID , 1 ) ;
        notificationIntent.putExtra(MyNotificationPublisher. NOTIFICATION , notification) ;
        PendingIntent pendingIntent = PendingIntent. getBroadcast ( getActivity(), 0 , notificationIntent , PendingIntent. FLAG_UPDATE_CURRENT ) ;
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context. ALARM_SERVICE ) ;
        assert alarmManager != null;
        alarmManager.set(AlarmManager. ELAPSED_REALTIME_WAKEUP , delay , pendingIntent) ;
    }
   /* private Notification getNotification (String content) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder( getActivity(), default_notification_channel_id ) ;
        builder.setContentTitle( "Scheduled Notification" ) ;
        builder.setContentText(content) ;
        builder.setSmallIcon(R.drawable. ic_launcher_foreground ) ;
        builder.setAutoCancel( true ) ;
        builder.setChannelId( NOTIFICATION_CHANNEL_ID ) ;
        return builder.build() ;
    }*/
    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet (DatePicker view , int year , int monthOfYear , int dayOfMonth) {
            myCalendar .set(Calendar. YEAR , year) ;
            myCalendar .set(Calendar. MONTH , monthOfYear) ;
            myCalendar .set(Calendar. DAY_OF_MONTH , dayOfMonth) ;
            updateLabel() ;
        }
    } ;
    public void setDate (View view) {
        new DatePickerDialog(
                getActivity(), date ,
                myCalendar .get(Calendar. YEAR ) ,
                myCalendar .get(Calendar. MONTH ) ,
                myCalendar .get(Calendar. DAY_OF_MONTH )
        ).show() ;
    }
    private void updateLabel () {
        String myFormat = "dd/MM/yy" ; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat , Locale. getDefault ()) ;
        Date date = myCalendar .getTime() ;
       // btnDate .setText(sdf.format(date)) ;
       // scheduleNotification(getNotification( btnDate .getText().toString()) , date.getTime()) ;
        scheduleNotification(getNotification( sdf.format(date)) , date.getTime()) ;
    }
}
