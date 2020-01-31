package browser.pig.cn.pigpad.bean;

import java.util.List;

import cn.my.library.net.BaseBean;

/**
 * created by dan
 */
public class XGoodsListBean extends BaseBean {

    private DataBean data;


    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean{
        private List<GoodsBean> products;
        private int updatecode;
        private String update_Interval;

        public String getUpdate_Interval() {
            return update_Interval;
        }

        public void setUpdate_Interval(String update_Interval) {
            this.update_Interval = update_Interval;
        }

        public List<GoodsBean> getProducts() {
            return products;
        }


        public void setProducts(List<GoodsBean> products) {
            this.products = products;
        }

        public int getUpdatecode() {
            return updatecode;
        }

        public void setUpdatecode(int updatecode) {
            this.updatecode = updatecode;
        }
    }

}
