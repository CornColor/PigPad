package browser.pig.cn.pigpad.net;

import android.content.Intent;


import com.google.gson.Gson;
import com.lzy.okgo.callback.AbsCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;

import cn.my.library.net.BaseBean;
import cn.my.library.utils.util.StringUtils;
import okhttp3.Response;
import okhttp3.ResponseBody;


/**
 * created by dan
 */
public abstract class CommonCallback<T extends BaseBean> extends AbsCallback<T> {
    private Type type;
    private Class<T> clazz;

    public CommonCallback(Type type) {
        this.type = type;
    }

    public CommonCallback(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public void onSuccess(com.lzy.okgo.model.Response<T> response) {
        if("0".equals(response.body().code)){
           onSuccess(response.body());
        }else {
            onFailure(response.body().code,response.body().msg);
        }
    }

    @Override
    public void onError(com.lzy.okgo.model.Response<T> response) {
        super.onError(response);
        onFailure("-111111","加载失败，请稍后重试");
    }

    @Override
    public T convertResponse(Response response) throws IOException{
        ResponseBody body = response.body();
        if(body == null){
            return null;
        }
        T data = null;
        String json = body.string();
        Gson gson = new Gson();
        try{
            if(!StringUtils.isEmpty(json)){
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(json);
                    if (jsonObject.getInt("code") != 0) {
                        if(jsonObject.getInt("code") == 1001){

                            onFailure(jsonObject.getInt("code")+"",jsonObject.getString("msg"));

                        }else {
                            onFailure(jsonObject.getInt("code")+"",jsonObject.getString("msg"));
                        }


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }catch (Exception e){

        }

        if(type!= null)data = gson.fromJson(json,type);
        if(clazz != null)data = gson.fromJson(json,clazz);
        return data;
    }

    /**
     * 用于返回错误信息
     * @param s
     */
    public abstract void onFailure(String code,String s);

    public abstract void onSuccess(T t);
}
