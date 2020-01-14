package browser.pig.cn.pigpad;

import cn.my.library.net.BaseBean;

/**
 * created by dan
 */
public class GoodsBean extends BaseBean {
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
}
