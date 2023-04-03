package com.app.iris;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.os.Build.VERSION.SDK_INT;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.app.iris.servie.DownloadNotificationService;
import com.app.iris.utils.MyUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MusicPlayerActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    TextView tv_start_duration, tv_end_duration;
    private ImageView iv_close, iv_play, iv_pause;
    MediaPlayer mPlayer;
    SeekBar seek_bar;
    private ImageView iv_download;
    private LinearLayout ll_backward, ll_forward;
    Handler mHandler = new Handler();
    private ActivityResultLauncher<String> someActivityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(MusicPlayerActivity.this, R.color.colorBlue4));

        mPlayer = null;
        iv_download = findViewById(R.id.iv_download);
        ll_forward = findViewById(R.id.ll_forward);
        ll_backward = findViewById(R.id.ll_backward);
        tv_start_duration = findViewById(R.id.tv_start_duration);
        tv_end_duration = findViewById(R.id.tv_end_duration);
        seek_bar = findViewById(R.id.seekBar);
        iv_close = findViewById(R.id.iv_close);
        iv_play = findViewById(R.id.iv_play);
        iv_pause = findViewById(R.id.iv_pause);

        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;

        }
        // Initialize a new media player instance
        mPlayer = new MediaPlayer();

        // Set the media player audio stream type
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        //Try to play music/audio from url
        try {
            // Set the audio data source
            //  mPlayer.setDataSource( R.raw.calm_chill_beautiful);
            mPlayer = MediaPlayer.create(this, R.raw.calm_chill_beautiful);
            //   mPlayer = MediaPlayer.create(this, R.raw.naina);
            //  mPlayer.prepare();
            tv_end_duration.setText("" + MyUtils.milliSecondsToTimer(mPlayer.getDuration()));
            // mPlayer.start();

            // Inform user for audio streaming
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                if (mPlayer != null) {
                    mPlayer.release();
                    mPlayer = null;
                }
                iv_play.setVisibility(View.VISIBLE);
                iv_pause.setVisibility(View.GONE);
            }
        });

        someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                new ActivityResultCallback<Boolean>() {
                    @Override
                    public void onActivityResult(Boolean result) {
                        if (result) {
                            callAction();
                            MyUtils.v("onActivityResult: PERMISSION GRANTED");
                        } else {
                            MyUtils.showPermissionAlert(MusicPlayerActivity.this);
                            MyUtils.v("onActivityResult: PERMISSION DENIED");
                        }
                    }
                });

        iv_close.setOnClickListener(this);
        iv_play.setOnClickListener(this);
        iv_pause.setOnClickListener(this);
        ll_backward.setOnClickListener(this);
        ll_forward.setOnClickListener(this);
        seek_bar.setOnSeekBarChangeListener(this);
        iv_download.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
                onBackPressed();
                break;
            case R.id.iv_download:
                if (SDK_INT >= Build.VERSION_CODES.R) {
                    if (MyUtils.checkPermission(MusicPlayerActivity.this)) {
                        callAction();
                    } else {
                        MyUtils.requestPermission(MusicPlayerActivity.this);
                    }
                } else {
                    if (SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(MusicPlayerActivity.this, READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            someActivityResultLauncher.launch(WRITE_EXTERNAL_STORAGE);
                        } else {
                            callAction();
                        }
                    } else {
                        callAction();
                    }

                }
                break;
            case R.id.ll_backward:
                if (mPlayer != null) {
                    int seekBackwardTime = 15000;
                    int currentPosition = mPlayer.getCurrentPosition();
                    if (currentPosition - seekBackwardTime >= 0) {
                        mPlayer.seekTo(currentPosition - seekBackwardTime);
                    } else {
                        mPlayer.seekTo(0);
                    }
                }
                break;
            case R.id.ll_forward:
                if (mPlayer != null) {
                    int seekForwardTime = 15000;
                    int currentPosition = mPlayer.getCurrentPosition();
                    if (currentPosition + seekForwardTime <= mPlayer.getDuration()) {
                        mPlayer.seekTo(currentPosition + seekForwardTime);
                    } else {
                        mPlayer.seekTo(mPlayer.getDuration());
                    }
                }
                break;
            case R.id.iv_pause:
                iv_pause.setVisibility(View.GONE);
                iv_play.setVisibility(View.VISIBLE);
                if (mPlayer != null) {
                    mPlayer.pause();
                }
                break;
            case R.id.iv_play:
                iv_play.setVisibility(View.GONE);
                iv_pause.setVisibility(View.VISIBLE);
                // tvSongTitle.setText(meditationLibrariesList.get(position).getTitle());
                updateProgressBar();
                if (mPlayer != null && !mPlayer.isPlaying()) {
                    mPlayer.start();
                } else {
                    // The audio url to play
                    if (mPlayer != null) {
                        mPlayer.stop();
                        mPlayer.release();
                        mPlayer = null;

                    }
                    // Initialize a new media player instance
                    mPlayer = new MediaPlayer();

                    // Set the media player audio stream type
                    mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    //Try to play music/audio from url
                    try {
                        // Set the audio data source
                        //  mPlayer.setDataSource( R.raw.calm_chill_beautiful);
                        mPlayer = MediaPlayer.create(this, R.raw.calm_chill_beautiful);
                      //  mPlayer.prepare();
                        tv_end_duration.setText("" + MyUtils.milliSecondsToTimer(mPlayer.getDuration()));
                        mPlayer.start();

                        // Inform user for audio streaming
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (SecurityException e) {
                        e.printStackTrace();
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    }
                }

                mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        if (mPlayer != null) {
                            mPlayer.release();
                            mPlayer = null;
                        }
                        iv_play.setVisibility(View.VISIBLE);
                        iv_pause.setVisibility(View.GONE);
                    }
                });
                break;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        if (mPlayer != null) {
            mHandler.removeCallbacks(mUpdateTimeTask);
        }
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if (mPlayer != null) {
            mHandler.removeCallbacks(mUpdateTimeTask);

            int totalDuration = mPlayer.getDuration();
            int currentPosition = MyUtils.progressToTimer(seekBar.getProgress(), totalDuration);

            // forward or backward to certain seconds
            mPlayer.seekTo(currentPosition);
            updateProgressBar();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        boolean isNotBackground = MyUtils.isAppIsInBackground(MusicPlayerActivity.this);

        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        boolean isScreenOn;
        if (SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            isScreenOn = powerManager.isInteractive();
        } else {
            isScreenOn = powerManager.isScreenOn();
        }

        if (!isScreenOn) {
            // The screen has been locked
            // do stuff...
        } else if (mPlayer != null && mPlayer.isPlaying() && !isNotBackground) {
            onExitMusic();
        }

    }

    public void updateProgressBar() {
        mHandler.postDelayed(mUpdateTimeTask, 100);
    }

    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            if (mPlayer != null) {
                long totalDuration = mPlayer.getDuration();
                long currentDuration = mPlayer.getCurrentPosition();


                int progress = (int) MyUtils.getProgressPercentage(currentDuration, totalDuration);
                // Displaying Total Duration time
                // tvSongDuration.setText(""+utils.milliSecondsToTimer(totalDuration));
                // Displaying time completed playing
                tv_start_duration.setText("" + MyUtils.milliSecondsToTimer(currentDuration));

                // Updating progress bar
                seek_bar.setProgress(progress);

                // Running this thread after 100 milliseconds
                mHandler.postDelayed(this, 100);
            }
        }
    };

    public void onExitMusic() {
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
    }

    private void callAction() {
        InputStream inStream = getResources().openRawResource(R.raw.calm_chill_beautiful);
        byte[] music;
        // music = new byte[inStream.available()];
/*
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();

            int nRead;
            byte[] data = new byte[16384];

            while ((nRead = inStream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }

            music =  buffer.toByteArray();
*/

        Intent intent = new Intent(MusicPlayerActivity.this, DownloadNotificationService.class);
        //   intent.putExtra("drawableModelList",  (Serializable) mainModelList);
//            intent.putExtra("byteArray", music);
        intent.putExtra("fileName", "calm_chill_beautiful");
        intent.putExtra("file_type", "mp3");
        startService(intent);
    }

    public byte[] inputStreamToByteArray(InputStream inStream) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[8192];
        int bytesRead;
        while ((bytesRead = inStream.read(buffer)) > 0) {
            baos.write(buffer, 0, bytesRead);
        }
        return baos.toByteArray();
    }

}