package browser.pig.cn.pigpad.bean;

import java.util.List;

import cn.my.library.net.BaseBean;

/**
 * created by dan
 */
public class GoodsListBean extends BaseBean {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean{
        private List<GoodsBean> list;

        public List<GoodsBean> getList() {
            return list;
        }

        public void setList(List<GoodsBean> list) {
            this.list = list;
        }
    }

}
