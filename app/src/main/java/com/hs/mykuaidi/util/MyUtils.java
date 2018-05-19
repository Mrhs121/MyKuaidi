package com.hs.mykuaidi.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by 黄晟 on 2016/10/4.
 */

public class MyUtils {
    //传一个上下文进来
    private Context mContext;

    public MyUtils (Context context) {
        this.mContext = context;
    }

    public MyUtils() {

    }

    /**
     * 将上次输入的快单号保存下来 历史记录
     * @param kuaididanhao
     */
    public void saveHistoryToSharedPreferences(String kuaididanhao){
        SharedPreferences sp = mContext.getSharedPreferences("History",mContext.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("kuaididanhao",kuaididanhao);
        editor.commit();
    }

    public String getHistoryToSharedPreferences () {
        SharedPreferences sp = mContext.getSharedPreferences("History",Context.MODE_PRIVATE);
        String kuaididanhao = sp.getString("kuaididanhao","");
        return kuaididanhao;
    }

    /**
     * 将开关后台服务的设置保存下来
     * @param b
     */
    public void saveSet(Boolean b) {
        SharedPreferences sp = mContext.getSharedPreferences("Setting",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("openserviceset",b);
        editor.commit();
    }

    /**
     * 得到后台服务开关的设置属性  如果获取不到 默认未关闭的状态
     * @return
     */
    public Boolean getSet() {
        SharedPreferences sp = mContext.getSharedPreferences("Setting",Context.MODE_PRIVATE);
        Boolean b = sp.getBoolean("openserviceset",false);
        return  b;
    }

    public void ToastTool(String s) {
        Toast.makeText(mContext,s,Toast.LENGTH_SHORT).show();
    }

    /**
     * 判断是否联网
     * @return
     */
    public Boolean isNetWorkConnected() {
        ConnectivityManager manager = (ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if(info!=null) {
            Log.i("isNet","返回真");
            return info.isAvailable();
        }
        return false;
    }
}
