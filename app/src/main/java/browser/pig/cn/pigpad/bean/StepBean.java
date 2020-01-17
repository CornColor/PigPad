package browser.pig.cn.pigpad.bean;

import java.util.List;

import cn.my.library.net.BaseBean;

/**
 * created by dan
 */
public class StepBean extends BaseBean {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<StepABean> list;

        public List<StepABean> getList() {
            return list;
        }

        public void setList(List<StepABean> list) {
            this.list = list;
        }
    }

    public static class StepABean{
        private String step_id;
        private String product_id;
        private String step_num;
        private String step_text;
        private String step_img;
        private String step_voice;
        private String product_name;
        private String product_video;
        private String remarks;


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
