package browser.pig.cn.pigpad.bean;

import java.util.List;

import cn.my.library.net.BaseBean;

/**
 * created by dan
 */
public class VersionBean extends BaseBean {

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
         private String version;
         private String address;

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }


}
