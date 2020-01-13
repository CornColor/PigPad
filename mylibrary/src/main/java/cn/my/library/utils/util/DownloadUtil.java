package cn.my.library.utils.util;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloadQueueSet;
import com.liulishuo.filedownloader.FileDownloader;


import java.io.File;
import java.util.ArrayList;
import java.util.List;



/**
 * created by dan
 */
public class DownloadUtil {
    public static final int JUST_LOAD = 0;//正在下载
    public static final int START = 1;//开始下载
    public static final int SUCCESS = 2;//下载成功
    public static final int ERROR = 3;//下载失败




    /**
     * 文件下载
     * @param context
     * @param dir 下载文件存储路径
     * @param fileNames 文件的名称集合
     * @param urls  网络地址
     * @param loadListener 下载监听
     */
    public static void   fileDownLoads(Context context, String dir, String []fileNames, String []urls, final OnDownLoadListener loadListener){
       FileDownloadListener fileDownloadListener = new FileDownloadListener() {
            @Override
            protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {

            }

            @Override
            protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {

            }

            @Override
            protected void completed(BaseDownloadTask task) {
                loadListener.onCompleted(task.getPath());

            }

            @Override
            protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {

            }

            @Override
            protected void error(BaseDownloadTask task, Throwable e) {
                loadListener.onError(task.getPath());
            }

            @Override
            protected void warn(BaseDownloadTask task) {

            }
        };

        FileDownloadQueueSet queueSet = new FileDownloadQueueSet(fileDownloadListener);
        List<BaseDownloadTask> tasks = new ArrayList<>();
        for (int i = 0;i < urls.length;i++){
            tasks.add(FileDownloader.getImpl().create(urls[i]).setPath(FilePathUtil.getFilePath(context,dir)+File.separator+fileNames[i]));
        }
        queueSet.disableCallbackProgressTimes();
        queueSet.downloadSequentially(tasks);
        queueSet.start();

    }




    /**
     * 文件下载
     * @param context
     * @param dir 下载文件存储路径
     * @param fileName 文件的名称
     * @param url  网络地址
     * @param loadListener 下载监听
     */
    public static int  fileDownLoad(Context context, String dir, String fileName, String url, final OnDownLoadListener loadListener){
        int downloadId = FileDownloader.getImpl().create(url)
                .setPath(FilePathUtil.getFilePath(context,dir)+File.separator+fileName)
                .setListener(new FileDownloadListener() {
                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        Log.e("下载","1");
                    }

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        loadListener.onDownLoadProgress(soFarBytes,totalBytes);

                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        loadListener.onCompleted(task.getPath());

                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        Log.e("下载","2");
                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        loadListener.onError(e.getMessage());

                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {
                        Log.e("下载","3");
                    }
                }).start();

        return downloadId;

    }






    /**
     * 文件下载
     * @param context
     * @param path 下载文件存储路径
     * @param url  网络地址
     * @param loadListener 下载监听
     */
    public static int  fileDownLoad(Context context, String path, String url, final OnDownLoadListener loadListener){
        int downloadId = FileDownloader.getImpl().create(url)
                .setPath(path)
                .setListener(new FileDownloadListener() {
                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {

                    }

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        loadListener.onDownLoadProgress(soFarBytes,totalBytes);

                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        loadListener.onCompleted(task.getPath());

                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {

                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        loadListener.onError(e.getMessage());

                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {

                    }
                }).start();

        return downloadId;

    }

    public static void stopAll(){
        FileDownloader.getImpl().pauseAll();
    }

    public static void stop(int downloadId){
  //      FileDownloader.getImpl().pause(downloadId);
    }
   public interface OnDownLoadListener{
        void onDownLoadProgress(int soFarBytes, int totalBytes);
        void onCompleted(String path);
        void onError(String e);
    }




}
