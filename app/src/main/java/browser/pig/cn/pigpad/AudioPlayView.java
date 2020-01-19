package browser.pig.cn.pigpad;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 * created by dan
 */
public class AudioPlayView extends RelativeLayout {

    private Context context;
    private TextView tv_play_time;
    private ImageView iv_play_laba;
    private AnimationDrawable animationDrawable;
    private RelativeLayout rl_audio_bg;
    public AudioPlayView(Context context) {
        super(context);
        initView(context);
    }

    public AudioPlayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public AudioPlayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context){
        this.context = context;
        View.inflate(context, R.layout.layout_audio_play,this);
         tv_play_time = findViewById(R.id.tv_play_time);
        iv_play_laba = findViewById(R.id.iv_play_laba);
        rl_audio_bg = findViewById(R.id.rl_audio_bg);
    }

    public void setPlayTime(String time){
        tv_play_time.setVisibility(VISIBLE);
        tv_play_time.setText(time);
    }



    /**
     * 播放
     */
    public void play(){
        iv_play_laba.setImageResource(R.drawable.animation_yuyin);
        animationDrawable = (AnimationDrawable) iv_play_laba.getDrawable();
        animationDrawable.start();

    }

    /**
     * 停止播放
     */
    public void stop(){
        if(animationDrawable != null){
            animationDrawable.stop();
            animationDrawable = null;
        }
        iv_play_laba.setImageResource(R.drawable.y3);

    }



}
