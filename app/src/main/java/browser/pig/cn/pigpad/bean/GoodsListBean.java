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
    public static class GoodsBean{
        private String product_id;
        private String product_name;
        private String product_video;
        private String remarks;
        private String product_icon;
        private boolean isSelect = false;
        private boolean isHidLine = false;

        public boolean isHidLine() {
            return isHidLine;
        }

        public void setHidLine(boolean hidLine) {
            isHidLine = hidLine;
        }

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public String getProduct_icon() {
            return product_icon;
        }

        public void setProduct_icon(String product_icon) {
            this.product_icon = product_icon;
        }

        public String getProduct_id() {
            return product_id;
        }

        public void setProduct_id(String product_id) {
            this.product_id = product_id;
        }

        public String getProduct_name() {
            return product_name;
        }

        public void setProduct_name(String product_name) {
            this.product_name = product_name;
        }

        public String getProduct_video() {
            return product_video;
        }

        public void setProduct_video(String product_video) {
            this.product_video = product_video;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }
    }
}
