package cn.my.library.utils.util;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

/**
 * created by dan
 * 播放音效
 */
public class PlaySound implements SoundPool.OnLoadCompleteListener {
    private volatile static PlaySound mInstance = null;
    private int mSoundId = -2;
    private  SoundPool soundPool;
    private int mStreamID = 1;
    private Context context;
    private PlaySound(Context context){
        this.context = context;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            SoundPool.Builder builder = new SoundPool.Builder();
            builder.setMaxStreams(1);
            //AudioAttributes是一个封装音频各种属性的方法
            AudioAttributes.Builder attrBuilder = new AudioAttributes.Builder();
            //设置音频流的合适的属性
            attrBuilder.setLegacyStreamType(AudioManager.STREAM_MUSIC);
            //加载一个AudioAttributes
            builder.setAudioAttributes(attrBuilder.build());
            soundPool = builder.build();
        }else {
            soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        }
        soundPool.setOnLoadCompleteListener(this);
    }
    public static PlaySound getInstance(Context context){
        if(mInstance == null){
            synchronized (PlaySound.class){
                if(mInstance == null){
                    mInstance = new PlaySound(context);
                }
            }
        }
        return mInstance;
    }

    public void play(int rId){
          mSoundId = soundPool.load(context,rId,1);
    }

    @Override
    public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
        if (soundPool != null) {
            mStreamID = soundPool.play(mSoundId,1, 1, 1, -1, 1.0f);
        }

    }
}
