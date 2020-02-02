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
    private String iffullscreen;
    private String fullscreen_Interval;
    private String cycleindex;

    private String product_updatecode;











    @Generated(hash = 2009371284)
    public GoodsBean(Long id, String product_id, String product_name,
            String product_video, String remarks, String product_icon,
            boolean isSelect, boolean isHidLine, String iffullscreen,
            String fullscreen_Interval, String cycleindex, String product_updatecode) {
        this.id = id;
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
    }

    @Generated(hash = 1806305570)
    public GoodsBean() {
    }

    public GoodsBean(String product_id, String product_name,
                     String product_video, String remarks, String product_icon,
                     String iffullscreen,
                     String fullscreen_Interval, String cycleindex, String product_updatecode) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.product_video = product_video;
        this.remarks = remarks;
        this.product_icon = product_icon;
        this.iffullscreen = iffullscreen;
        this.fullscreen_Interval = fullscreen_Interval;
        this.cycleindex = cycleindex;
        this.product_updatecode = product_updatecode;
    }












    public String getProduct_updatecode() {
        return product_updatecode;
    }

    public void setProduct_updatecode(String product_updatecode) {
        this.product_updatecode = product_updatecode;
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

    public String getCycleindex() {
        return this.cycleindex;
    }

    public void setCycleindex(String cycleindex) {
        this.cycleindex = cycleindex;
    }

    public String getFullscreen_Interval() {
        return this.fullscreen_Interval;
    }

    public void setFullscreen_Interval(String fullscreen_Interval) {
        this.fullscreen_Interval = fullscreen_Interval;
    }

    public String getIffullscreen() {
        return this.iffullscreen;
    }

    public void setIffullscreen(String iffullscreen) {
        this.iffullscreen = iffullscreen;
    }

    @Override
    public String toString() {
        return "GoodsBean{" +
                "id=" + id +
                ", product_id='" + product_id + '\'' +
                ", product_name='" + product_name + '\'' +
                ", product_video='" + product_video + '\'' +
                ", remarks='" + remarks + '\'' +
                ", product_icon='" + product_icon + '\'' +
                ", isSelect=" + isSelect +
                ", isHidLine=" + isHidLine +
                ", iffullscreen='" + iffullscreen + '\'' +
                ", fullscreen_Interval='" + fullscreen_Interval + '\'' +
                ", cycleindex='" + cycleindex + '\'' +
                '}';
    }
}
