package browser.pig.cn.pigpad;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import cn.my.library.utils.util.CommonUtil;
import cn.my.library.utils.util.StringUtils;

import static android.view.View.GONE;

/**
 * created by dan
 */
public class StepFragment extends Fragment{
    private ImageView iv_bg;
    private LinearLayout ll_yuyin;

    private AudioPlayView audioPlayView;

    private String audio;
    private String bg;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        audio =  bundle.getString("audio");
        bg = bundle.getString("bg");

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step,null);
        iv_bg = view.findViewById(R.id.iv_bg);
        ll_yuyin = view.findViewById(R.id.ll_yuyin);
        audioPlayView = view.findViewById(R.id.play);
        audioPlayView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AudioPlay.getInstance().play(audio, new AudioPlay.OnAudioPlayListener() {
                    @Override
                    public void onAudioPlayFinish() {

                    }

                    @Override
                    public void onAudioPlayError() {

                    }
                });
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Glide.with(getActivity()).load(bg).into(iv_bg);
        if(!StringUtils.isEmpty(audio)){
            ll_yuyin.setVisibility(View.VISIBLE);
        }else {
            ll_yuyin.setVisibility(GONE);

        }



    }
}
