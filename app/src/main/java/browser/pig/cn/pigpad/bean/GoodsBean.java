package browser.pig.cn.pigpad.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * created by dan
 */
@Entity
public class GoodsBean {
    @Id(autoincrement = true)
    private Long id;
    private String product_id;
    private String product_name;
    private String product_video;
    private String remarks;
    private String product_icon;
    private boolean isSelect = false;
    private boolean isHidLine = false;






    @Generated(hash = 925826455)
    public GoodsBean(Long id, String product_id, String product_name,
            String product_video, String remarks, String product_icon,
            boolean isSelect, boolean isHidLine) {
        this.id = id;
        this.product_id = product_id;
        this.product_name = product_name;
        this.product_video = product_video;
        this.remarks = remarks;
        this.product_icon = product_icon;
        this.isSelect = isSelect;
        this.isHidLine = isHidLine;
    }

    @Generated(hash = 1806305570)
    public GoodsBean() {
    }






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

    public boolean getIsHidLine() {
        return this.isHidLine;
    }

    public void setIsHidLine(boolean isHidLine) {
        this.isHidLine = isHidLine;
    }

    public boolean getIsSelect() {
        return this.isSelect;
    }

    public void setIsSelect(boolean isSelect) {
        this.isSelect = isSelect;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
