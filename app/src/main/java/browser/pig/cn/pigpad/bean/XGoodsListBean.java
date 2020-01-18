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
        private List<GoodsListBean.GoodsBean> products;
        private int updatecode;

        public List<GoodsListBean.GoodsBean> getProducts() {
            return products;
        }


        public void setProducts(List<GoodsListBean.GoodsBean> products) {
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
