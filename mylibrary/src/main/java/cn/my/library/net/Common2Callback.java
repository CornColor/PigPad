package cn.my.library.net;

import com.google.gson.Gson;
import com.lzy.okgo.callback.AbsCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;

import cn.my.library.utils.util.StringUtils;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * created by dan
 */
public abstract class Common2Callback<T> extends AbsCallback<T> {
    private Type type;
    private Class<T> clazz;

    public Common2Callback(Type type) {
        this.type = type;
    }

    public Common2Callback(Class<T> clazz) {
        this.clazz = clazz;
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
        if(type!= null)data = gson.fromJson(json,type);
        if(clazz != null)data = gson.fromJson(json,clazz);
        return data;
    }


}
