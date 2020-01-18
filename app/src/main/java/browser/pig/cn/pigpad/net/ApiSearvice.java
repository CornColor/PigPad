package browser.pig.cn.pigpad.net;



/**
 * created by dan
 */
public interface ApiSearvice {
    String HOST = "http://zyyboss.shangmid.com/api/";

    /**
     * 产品列表
     */
    String GOODS_LIST = HOST+"product";

    /**
     * 步骤
     */
    String GOODS_STEP = HOST+"step";

    /**
     * 心跳
     */
    String XINTIAO = HOST+"device/heartbeat";
    /**
     * 版本
     */
    String VERSION = HOST+"padsystem/version";


}
