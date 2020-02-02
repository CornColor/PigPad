package browser.pig.cn.pigpad;

/**
 * 作者：liuyutao
 * 创建时间：2020/1/31 下午10:45
 * 类描述：
 * 修改人：
 * 修改内容:
 * 修改时间：
 */
public class MessageBus {
    private int type;

    public MessageBus(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
