package com.example.user.fragmentbacktask.activity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.MediaController;
import android.widget.VideoView;

import com.example.user.fragmentbacktask.R;
import com.example.user.fragmentbacktask.view.CustomVideoView;

import androidx.appcompat.app.AppCompatActivity;

public class VideoPlayerActivity extends AppCompatActivity implements MediaPlayer.OnErrorListener,MediaPlayer.OnCompletionListener{

    private static final String TAG = "VideoPlayerActivity";
    private Uri mUri;
    private int mPositionWhenPaused = -1;
    private CustomVideoView mVideoView;
    private MediaController mMediaController;
    private VideoView videoView;

    private Context mContext;

    private  AudioManager am;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        mVideoView = (CustomVideoView)findViewById(R.id.video_view);
        mUri = Uri.parse(Environment.getExternalStorageDirectory() + "/Touch_The_Sky.mp4");

        //Create media controller，组件可以控制视频的播放，暂停，回复，seek等操作，不需要你实现
        mMediaController = new MediaController(this);
        mVideoView.setMediaController(mMediaController);

        mContext = this;
    }
    public void onStart() {
        // Play Video
        mVideoView.setVideoURI(mUri);
        mVideoView.start();

        super.onStart();
    }

    public void onPause() {
        // Stop video when the activity is pause.
        mPositionWhenPaused = mVideoView.getCurrentPosition();
        videoView.stopPlayback();

        super.onPause();
    }

    public void onResume() {
        // Resume video player
        if(mPositionWhenPaused >= 0) {
            mVideoView.seekTo(mPositionWhenPaused);
            mPositionWhenPaused = -1;
        }

        super.onResume();
    }


    private void AudioTest(){

        /**
         * 1.设置语音播放模式，语音播放声音调整，响应硬件耳机(BroadcastReceive "MEDIA_BUTTON")
         * 2.语音播放焦点(短暂焦点和永久焦点)，处理失去焦点(AudioManager.OnAudioFocusChangeListener)
         * 3.检测硬件设备，当耳机或蓝牙拔出时(BroadcastReceive AudioManager.ACTION_AUDIO_BECOMING_NOISY)
         */
        am = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);

        //请求获取焦点  短暂焦点锁定  永久焦点
        int result = am.requestAudioFocus(afChangeListener,
                AudioManager.STREAM_MUSIC,
                // Request permanent focus.
                AudioManager.AUDIOFOCUS_GAIN
        );


        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            //am.unregisterMediaButtonEventReceiver(RemoteControlReceiver);
            // Start playback.

        }

        //检测播放设备
        if (am.isBluetoothA2dpOn()) {
            // Adjust output for Bluetooth.
        } else if (am.isSpeakerphoneOn()) {
            // Adjust output for Speakerphone.
        } else if (am.isWiredHeadsetOn()) {
            // Adjust output for headsets
        } else {
            // If audio plays and noone can hear it, is it still playing?
        }

        //A、设置声音模式
        //声音模式
        am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        //静音模式
        am.setRingerMode(AudioManager.RINGER_MODE_SILENT);
        //震动模式
        am.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);

        //B、调整声音大小
        // （当传入的第一个参数为 AudioManager.ADJUST_LOWER 时，可将音量调小一个单位，
        // 传入 AudioManager.ADJUST_RAISE 时，则可以将音量调大一个单位。）
        //减少声音音量
        am.adjustVolume(AudioManager.ADJUST_LOWER,  0);
        //调大声音音量
        am.adjustVolume(AudioManager.ADJUST_RAISE, 0);
        /**
         * 参数1：声音类型，可取为STREAM_VOICE_CALL（通话）、STREAM_SYSTEM（系统声音）、STREAM_RING（铃声）
         * 、STREAM_MUSIC（音乐）、STREAM_ALARM（闹铃声）
          参数2：调整音量的方向，可取ADJUST_LOWER（降低）、ADJUST_RAISE（升高）、ADJUST_SAME
         参数3：可选的标志位 0
         */
        //am.setStreamVolume();
        //降低音量，调出系统音量控制
        am.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_LOWER,
                AudioManager.FX_FOCUS_NAVIGATION_UP);
        //增加音量，调出系统音量控制
        am.adjustStreamVolume(AudioManager.STREAM_MUSIC,AudioManager.ADJUST_RAISE,
                AudioManager.FX_FOCUS_NAVIGATION_UP);

        //当前音量
        int currentVolume =am.getStreamVolume(AudioManager.STREAM_MUSIC);

        //控制那个接收器会响应,防止其他应用响应

        //一个比较好的注册与取消监听的方法是当程序获取与失去音频焦点的时候进行操作。
        ComponentName ctn = new ComponentName(getPackageName(),RemoteControlReceiver.class.getName());
       am.registerMediaButtonEventReceiver(ctn);
//        am.unregisterMediaButtonEventReceiver(receiver);

        myNoisyAudioStreamReceiver = new NoisyAudioStreamReceiver();
    }

    private NoisyAudioStreamReceiver myNoisyAudioStreamReceiver;

    //ACTION_AUDIO_BECOMING_NOISY  当有线耳机被拔出或者蓝牙设备断开连接的时候
    //停止播放
    private class NoisyAudioStreamReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (AudioManager.ACTION_AUDIO_BECOMING_NOISY.equals(intent.getAction())) {
                // Pause the playback
            }
        }
    }

    private IntentFilter intentFilter = new IntentFilter(AudioManager.ACTION_AUDIO_BECOMING_NOISY);

    private void startPlayback() {
        registerReceiver(myNoisyAudioStreamReceiver, intentFilter);
    }

    private void stopPlayback() {
        unregisterReceiver(myNoisyAudioStreamReceiver);
    }


    //处理失去焦点
    private AudioManager.OnAudioFocusChangeListener afChangeListener = new  AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
                // Pause playback
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // Resume playback
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
//            am.unregisterMediaButtonEventReceiver(RemoteControlReceiver);
                am.abandonAudioFocus(afChangeListener);
                // Stop playback

            }
            //Ducking是一个特殊的机制使得允许音频间歇性的短暂播放
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                // Lower the volume
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // Raise it back to normal
            }
        }

    };

    /** 使用硬件控制音频播放
     * <receiver android:name=".RemoteControlReceiver">
     <intent-filter>
     <action android:name="android.intent.action.MEDIA_BUTTON" />
     </intent-filter>
     </receiver>
     */
    /**
     * Receiver需要判断这个广播是来自哪个按钮的操作，Intent在EXTRA_KEY_EVENT中包含了KEY的信息，
     * 同样KeyEvent类包含了一列KEYCODE_MEDIA_*的静态变量来表示不同的媒体按钮，
     * such as KEYCODE_MEDIA_PLAY_PAUSE and KEYCODE_MEDIA_NEXT.
     */

    class RemoteControlReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Intent.ACTION_MEDIA_BUTTON.equals(intent.getAction())) {
                KeyEvent event = (KeyEvent)intent.getParcelableExtra(Intent.EXTRA_KEY_EVENT);
                if (KeyEvent.KEYCODE_MEDIA_PLAY == event.getKeyCode()) {
                    // Handle key press.
                } else if(KeyEvent.KEYCODE_MEDIA_NEXT == event.getKeyCode()){

                }
            }
        }

    }


    @Override
    public void onCompletion(MediaPlayer mp) {
      this.finish();
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }
}
