package browser.pig.cn.pigpad.db;

import android.content.Context;

import org.greenrobot.greendao.AbstractDao;

import browser.pig.cn.pigpad.bean.GoodsBean;
import browser.pig.cn.pigpad.bean.StepABean;


public class DbHelper {
    private static final String DB_NAME = "woosport.db";//数据库名称
    private static DbHelper instance;
    private DaoMaster.DevOpenHelper mHelper;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private static DBManager<GoodsBean,Long> goodsBeanLongDBManager;
    private static DBManager<StepABean,Long> stepABeanLongDBManager;
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

    public void init(Context context,String dbName){
        mHelper = new DaoMaster.DevOpenHelper(context,dbName,null);
        mDaoMaster = new DaoMaster(mHelper.getWritableDatabase());
        mDaoSession =  mDaoMaster.newSession();

    }

    public void init(Context context){
        mHelper = new DaoMaster.DevOpenHelper(context,DB_NAME,null);
        mDaoMaster = new DaoMaster(mHelper.getWritableDatabase());
        mDaoSession =  mDaoMaster.newSession();

    }

    public DBManager<GoodsBean,Long> goodsBeanLongDBManager(){
        if(goodsBeanLongDBManager == null){
            goodsBeanLongDBManager = new DBManager<GoodsBean, Long>() {
                @Override
                public AbstractDao<GoodsBean, Long> getAbstractDao() {
                    return mDaoSession.getGoodsBeanDao();
                }
            };
        }
        return goodsBeanLongDBManager;
    }

    public DBManager<StepABean,Long> stepABeanLongDBManager(){
        if(stepABeanLongDBManager == null){
            stepABeanLongDBManager = new DBManager<StepABean, Long>() {
                @Override
                public AbstractDao<StepABean, Long> getAbstractDao() {
                    return mDaoSession.getStepABeanDao();
                }
            };
        }
        return stepABeanLongDBManager;
    }
}
