package com.app.iris.servie;

import android.app.DownloadManager;
import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.app.iris.R;
import com.app.iris.fragment.ConvertedSignLangFragment;
import com.app.iris.model.TextToSignModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class DownloadNotificationService extends IntentService {

    public DownloadNotificationService() {
        super("Service");
    }

    private NotificationCompat.Builder notificationBuilder;
    private NotificationManager notificationManager;


    @Override
    protected void onHandleIntent(Intent intent) {
        //   List<TextToSignModel> drawableModelList = (List<TextToSignModel>) intent.getSerializableExtra("drawableModelList");
        //String byteArray = intent.getStringExtra("byteArray");

        byte[] b = intent.getExtras().getByteArray("byteArray");
        String fileName = intent.getStringExtra("fileName");
        String file_type = intent.getStringExtra("file_type");

        //int index = intent.getIntExtra("index",0);
        //
        //  boolean isDownload = intent.getBooleanExtra("isDownload", false);
        //if (isDownload) {
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel("id", "an", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setDescription("no sound");
            notificationChannel.setSound(null, null);
            notificationChannel.enableLights(false);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.enableVibration(false);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        notificationBuilder = new NotificationCompat.Builder(this, "id")
                //.setSmallIcon(android.R.drawable.stat_sys_download)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Download")
                .setContentText("Downloading...")
                .setDefaults(0)
                .setAutoCancel(true);
        notificationManager.notify(0, notificationBuilder.build());
        //   }
        //   initRetrofit(downloadURL, fileName, isDownload);
        if (file_type.equals("mp3")) {
            try {
                fileName = fileName + ".mp3";
                downloadMp3(fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (file_type.equals("image")) {
            fileName = fileName + ".jpg";
            Bitmap bmp = BitmapFactory.decodeByteArray(b, 0, b.length);
            try {
                downloadImage(bmp, fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    private void downloadImage(Bitmap bitmap, String fileName) throws IOException {

        int count;
        byte data[] = new byte[1024 * 4];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        int fileSize = bitmap.getRowBytes() * bitmap.getHeight();

        // long fileSize = body.contentLength();
        InputStream inputStream = new ByteArrayInputStream(baos.toByteArray());
        //File outputFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "");

        //File pdfFolder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "");
        String myfolder = Environment.getExternalStorageDirectory() + "/" + "iris" + "/" + "files";
        File pdfFolder = new File(myfolder);
        if (!pdfFolder.exists()) {
            pdfFolder.mkdirs();
        }
        String fileFinalPath = pdfFolder.getAbsolutePath() + "/" + fileName;


        OutputStream outputStream = new FileOutputStream(fileFinalPath);
        long total = 0;
        boolean downloadComplete = false;
        //int totalFileSize = (int) (fileSize / (Math.pow(1024, 2)));

        while ((count = inputStream.read(data)) != -1) {

            total += count;
            int progress = (int) ((double) (total * 100) / (double) fileSize);

            //if (isDownload)
            updateNotification(progress);
            outputStream.write(data, 0, count);
            downloadComplete = true;
        }
//        if (isDownload)
//            onDownloadComplete(downloadComplete, fileFinalPath);
//        else
//            sendProgressUpdate(downloadComplete, fileFinalPath);
        onDownloadComplete(downloadComplete, fileFinalPath);
        outputStream.flush();
        outputStream.close();
        inputStream.close();

    }

    private void downloadMp3(String fileName) throws IOException {
        /*InputStream inStream = getResources().openRawResource(R.raw.calm_chill_beautiful);
        int count;
        byte data[] = new byte[1024 * 4];
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        int nRead;

        while ((nRead = inStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }

        data =  buffer.toByteArray();
        int fileSize = inStream.available();*/


        String myfolder = Environment.getExternalStorageDirectory() + "/" + "iris" + "/" + "files";
        File pdfFolder = new File(myfolder);
        if (!pdfFolder.exists()) {
            pdfFolder.mkdirs();
        }
        String fileFinalPath = pdfFolder.getAbsolutePath() + "/" + fileName;

        CopyRAWtoSDCard(fileFinalPath + fileName);

/*
        OutputStream outputStream = new FileOutputStream(fileFinalPath);
        long total = 0;
        boolean downloadComplete = false;
        //int totalFileSize = (int) (fileSize / (Math.pow(1024, 2)));

        while ((count = inStream.read(data)) != -1) {

            total += count;
            int progress = (int) ((double) (total * 100) / (double) fileSize);

            //if (isDownload)
                updateNotification(progress);
            outputStream.write(data, 0, count);
            downloadComplete = true;
        }
//        if (isDownload)
//            onDownloadComplete(downloadComplete, fileFinalPath);
//        else
//            sendProgressUpdate(downloadComplete, fileFinalPath);
        onDownloadComplete(downloadComplete, fileFinalPath);
        outputStream.flush();
        outputStream.close();
        inStream.close();
*/

    }

    private void updateNotification(int currentProgress) {


        notificationBuilder.setProgress(100, currentProgress, false);
        notificationBuilder.setContentText("Downloaded: " + currentProgress + "%");
        notificationManager.notify(0, notificationBuilder.build());
    }


    private void sendProgressUpdate(boolean downloadComplete, String fileFinalPath) {

        Intent intent = new Intent(ConvertedSignLangFragment.PROGRESS_UPDATE);
        intent.putExtra("downloadComplete", downloadComplete);
        intent.putExtra("fileFinalPath", fileFinalPath);
        LocalBroadcastManager.getInstance(DownloadNotificationService.this).sendBroadcast(intent);
    }

    private void onDownloadComplete(boolean downloadComplete, String fileFinalPath) {
        sendProgressUpdate(downloadComplete, fileFinalPath);
        Intent myTestIntent = new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS);

        //  startActivity(new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1251, myTestIntent, PendingIntent.FLAG_ONE_SHOT);
        notificationBuilder.setContentIntent(pendingIntent);

        notificationManager.cancel(0);
        notificationBuilder.setProgress(0, 0, false);
        notificationBuilder.setContentText("Download Complete " + fileFinalPath);
        notificationManager.notify(0, notificationBuilder.build());

    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        notificationManager.cancel(0);
    }


    private void CopyRAWtoSDCard(String path) throws IOException {
        InputStream inStream = getResources().openRawResource(R.raw.calm_chill_beautiful);
        FileOutputStream out = new FileOutputStream(path);
        byte[] buff = new byte[1024];
        int read = 0;
        long total = 0;
        boolean downloadComplete = false;
        int fileSize = inStream.available();
        try {
            while ((read = inStream.read(buff)) > 0) {
                out.write(buff, 0, read);

                total += read;
                int progress = (int) ((double) (total * 100) / (double) fileSize);
                updateNotification(progress);
                downloadComplete = true;
            }
            onDownloadComplete(downloadComplete, path);

        } finally {
            inStream.close();
            out.close();
        }
    }

}
