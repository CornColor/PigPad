package browser.pig.cn.pigpad.bean;

import java.util.List;

import cn.my.library.net.BaseBean;

/**
 * 作者：liuyutao
 * 创建时间：2020/1/31 下午1:42
 * 类描述：
 * 修改人：
 * 修改内容:
 * 修改时间：
 */
public class Products extends BaseBean{

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

    public static class GoodsBean{
        private String product_id;
        private String product_name;
        private String product_video;
        private String remarks;
        private String product_icon;
        private boolean isSelect = false;
        private boolean isHidLine = false;
        private String iffullscreen;
        private String fullscreen_Interval;
        private String cycleindex;




        private String product_updatecode;

        private List<StepBean> steps;


        public GoodsBean(String product_id, String product_name, String product_video, String remarks, String product_icon, boolean isSelect, boolean isHidLine, String iffullscreen, String fullscreen_Interval, String cycleindex, String product_updatecode, List<StepBean> steps) {
            this.product_id = product_id;
            this.product_name = product_name;
            this.product_video = product_video;
            this.remarks = remarks;
            this.product_icon = product_icon;
            this.isSelect = isSelect;
            this.isHidLine = isHidLine;
            this.iffullscreen = iffullscreen;
            this.fullscreen_Interval = fullscreen_Interval;
            this.cycleindex = cycleindex;
            this.product_updatecode = product_updatecode;
            this.steps = steps;
        }

        public List<StepBean> getSteps() {
            return steps;
        }

        public void setSteps(List<StepBean> steps) {
            this.steps = steps;
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

        public String getProduct_icon() {
            return product_icon;
        }

        public void setProduct_icon(String product_icon) {
            this.product_icon = product_icon;
        }

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public boolean isHidLine() {
            return isHidLine;
        }

        public void setHidLine(boolean hidLine) {
            isHidLine = hidLine;
        }

        public String getIffullscreen() {
            return iffullscreen;
        }

        public void setIffullscreen(String iffullscreen) {
            this.iffullscreen = iffullscreen;
        }

        public String getFullscreen_Interval() {
            return fullscreen_Interval;
        }

        public void setFullscreen_Interval(String fullscreen_Interval) {
            this.fullscreen_Interval = fullscreen_Interval;
        }

        public String getCycleindex() {
            return cycleindex;
        }

        public void setCycleindex(String cycleindex) {
            this.cycleindex = cycleindex;
        }

        public String getProduct_updatecode() {
            return product_updatecode;
        }

        public void setProduct_updatecode(String product_updatecode) {
            this.product_updatecode = product_updatecode;
        }
    }
    public static class StepBean{
        private String step_id;
        private String product_id;
        private String step_num;
        private String step_text;
        private String step_img;
        private String step_voice;
        private String product_name;
        private String product_video;
        private String remarks;


        public StepBean(String step_id, String product_id, String step_num, String step_text, String step_img, String step_voice, String product_name, String product_video, String remarks) {
            this.step_id = step_id;
            this.product_id = product_id;
            this.step_num = step_num;
            this.step_text = step_text;
            this.step_img = step_img;
            this.step_voice = step_voice;
            this.product_name = product_name;
            this.product_video = product_video;
            this.remarks = remarks;
        }

        public String getStep_id() {
            return step_id;
        }

        public void setStep_id(String step_id) {
            this.step_id = step_id;
        }

        public String getProduct_id() {
            return product_id;
        }

        public void setProduct_id(String product_id) {
            this.product_id = product_id;
        }

        public String getStep_num() {
            return step_num;
        }

        public void setStep_num(String step_num) {
            this.step_num = step_num;
        }

        public String getStep_text() {
            return step_text;
        }

        public void setStep_text(String step_text) {
            this.step_text = step_text;
        }

        public String getStep_img() {
            return step_img;
        }

        public void setStep_img(String step_img) {
            this.step_img = step_img;
        }

        public String getStep_voice() {
            return step_voice;
        }

        public void setStep_voice(String step_voice) {
            this.step_voice = step_voice;
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
