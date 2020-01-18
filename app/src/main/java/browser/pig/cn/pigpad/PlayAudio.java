package browser.pig.cn.pigpad;

import android.content.Context;

import cn.jzvd.JzvdStd;

/**
 * created by dan
 */
public class PlayAudio extends JzvdStd {


    public PlayAudio(Context context) {
        super(context);
    }

    @Override
    public void onAutoCompletion() {
        super.onAutoCompletion();
        startVideo();
    }
}
