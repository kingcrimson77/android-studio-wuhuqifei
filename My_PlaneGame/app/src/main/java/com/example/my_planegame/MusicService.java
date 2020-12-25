package com.example.my_planegame;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class MusicService extends Service {
    static boolean isplay=true;//播放状态
    MediaPlayer mediaPlayer;
    public MusicService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        //创建 mediaPlayer对象，加载音乐
        mediaPlayer = MediaPlayer.create(this,R.raw.game);
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(!mediaPlayer.isPlaying()){
            mediaPlayer.start();//播放音乐
            isplay = mediaPlayer.isPlaying();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        mediaPlayer.stop();//停止播放
        isplay = mediaPlayer.isPlaying();
        mediaPlayer.release();
        super.onDestroy();
    }
}
