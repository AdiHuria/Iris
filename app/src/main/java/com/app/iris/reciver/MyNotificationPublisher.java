package com.app.iris.reciver;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.app.iris.R;
import com.app.iris.fragment.ReminderFragment;

public class MyNotificationPublisher extends BroadcastReceiver {
   public static String NOTIFICATION_ID = "notification-id";
   public static String NOTIFICATION = "notification";

   public void onReceive(Context context, Intent intent) {
     NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
         NotificationChannel notificationChannel = new NotificationChannel("id", "an", NotificationManager.IMPORTANCE_HIGH);
         notificationChannel.setDescription("no sound");
         notificationChannel.setSound(null, null);
         notificationChannel.enableLights(false);
         notificationChannel.setLightColor(Color.BLUE);
         notificationChannel.enableVibration(false);
         notificationManager.createNotificationChannel(notificationChannel);
      }

      NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, "id")
              //.setSmallIcon(android.R.drawable.stat_sys_download)
              .setSmallIcon(R.drawable.ic_launcher_foreground)
              .setContentTitle("Test Alaram")
              .setContentText("This is alarm testing")
              .setDefaults(0)
              .setAutoCancel(true);
      notificationManager.notify(0, notificationBuilder.build());
      Toast.makeText(context, "THis is test message", Toast.LENGTH_SHORT).show();
      /*NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

      Notification notification = intent.getParcelableExtra(NOTIFICATION);
      int id = intent.getIntExtra(NOTIFICATION_ID, 0);
      notificationManager.notify(id, notification);*/
   }
}