package cn.my.library.ui.base;

/**
 * created by dan
 */
public interface BaseView {
    /**
     * 展示吐司
     */
    void showToast(String msg);

    /**
     * 展示进度弹框
     * @param msg
     */
    void showProgressDialog(String msg);

    /**
     * 取消进度
     */
    void cancelProgressDialog();
    /**
     * 展示普通弹框
     * @param msg
     */
    void showDialog(String... msg);

}
