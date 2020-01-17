package browser.pig.cn.pigpad;

import android.media.MediaPlayer;

import java.io.IOException;

/**
 * created by dan
 * 音频播放
 */
public class AudioPlay {
    private int STATUS_PLAY = 1;//默认状态
    private int STATUS_PASUE = 2;//默认状态
    private int STATUS_STOP = 3;//默认状态
    private static AudioPlay mInstance;
    private MediaPlayer mediaPlayer;
    private  int status =  STATUS_STOP;

    public static AudioPlay getInstance(){
        if(mInstance == null){
            synchronized (AudioPlay.class){
                if(mInstance == null){
                    mInstance = new AudioPlay();
                }
            }
        }
        return  mInstance;
    }

    public void play(String url, final OnAudioPlayListener onAudioPlayListener){
        if(mediaPlayer == null){
            status = STATUS_PLAY;
            mediaPlayer = new MediaPlayer();
            mediaPlayer.reset();
            try {
                mediaPlayer.setDataSource(url);
                mediaPlayer.prepareAsync();
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mp.start();
                    }
                });
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        status =  STATUS_STOP;
                        onAudioPlayListener.onAudioPlayFinish();
                        stop();


                    }
                });
                mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                    @Override
                    public boolean onError(MediaPlayer mp, int what, int extra) {
                        onAudioPlayListener.onAudioPlayError();
                        stop();
                        return false;
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
                status =  STATUS_STOP;
                stop();
                onAudioPlayListener.onAudioPlayError();
            }


        }else {
            if(status == STATUS_PASUE){
                mediaPlayer.start();
                status = STATUS_PLAY;
            }else if(status == STATUS_PLAY){
                stop();
                status = STATUS_PLAY;
                mediaPlayer = new MediaPlayer();
                mediaPlayer.reset();
                try {
                    mediaPlayer.setDataSource(url);
                    mediaPlayer.prepareAsync();
                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            mp.start();
                        }
                    });
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            status =  STATUS_STOP;
                            onAudioPlayListener.onAudioPlayFinish();
                            stop();


                        }
                    });
                    mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                        @Override
                        public boolean onError(MediaPlayer mp, int what, int extra) {
                            onAudioPlayListener.onAudioPlayError();
                            stop();
                            return false;
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                    status =  STATUS_STOP;
                    stop();
                    onAudioPlayListener.onAudioPlayError();
                }
            }

        }

    }


    public void pause(){
        if(mediaPlayer != null){
            status = STATUS_PASUE;
            mediaPlayer.pause();
        }
    }

    public void stop(){
        if(mediaPlayer != null){
            status = STATUS_STOP;
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public boolean isPlay(){
        if(mediaPlayer!= null){
            return mediaPlayer.isPlaying();
        }
        return false;
    }


    public interface OnAudioPlayListener{
        void  onAudioPlayFinish();
        void onAudioPlayError();
    }



}
