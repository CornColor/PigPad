package cn.my.library.utils.db;

import android.content.Context;


import org.greenrobot.greendao.AbstractDao;


public class DbHelper {
    private static final String DB_NAME = "woosport.db";//数据库名称
    private static DbHelper instance;


    private DbHelper() {

    }

    public static DbHelper getInstance() {
        if (instance == null) {
            synchronized (DbHelper.class) {
                if (instance == null) {
                    instance = new DbHelper();
                }
            }
        }
        return instance;
    }

    public void init(Context context) {

    }

    public void init(Context context, String dbName) {

    }


}
