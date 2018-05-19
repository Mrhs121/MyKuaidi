package com.hs.mykuaidi.myservice;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.hs.mykuaidi.dbUtil.KuaidiDao;
import com.hs.mykuaidi.httpTools.HttpUtil;
import com.hs.mykuaidi.jsonTools.JsonTools;
import com.hs.mykuaidi.model.Beizhu;
import com.hs.mykuaidi.util.MyUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 黄晟 on 2016/10/19.
 */

/**
 * 后台检查更新的服务  如果有更新发送广播  同时也是广播发送器
 */

public class CheckUpdateService extends Service {

    private final String url2 = "http://www.kuaidi100.com/query?type=";
    private final String url3 = "&postid=";
    private final String typeurl = "http://www.kuaidi100.com/autonumber/autoComNum?text=";
    private String jsonstring = "";
    private KuaidiDao mDao;
    private List<Beizhu> list;

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            if (message.what == 0x0001) {
                Toast.makeText(getApplicationContext(), "结束服务", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "有更新,通知栏提示", Toast.LENGTH_SHORT).show();
            }
            return false;
        }
    });

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    // 创建的时候回调
    @Override
    public void onCreate() {
        super.onCreate();
        list = new ArrayList<>();
        mDao = new KuaidiDao(this);
        Log.i("Service", "创建");
    }

    // 销毁的时候回调
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("Service", "销毁服务");
        Message msg = new Message();
        msg.what = 0x0001;
        handler.sendMessage(msg);
    }

    // 启动的时候回调
    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        Log.i("Service", "启动");
        list = mDao.findAllBeizhuAndNewTime();//所以备忘录的集合
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 如果联网的话 则进行检查更新
                if (new MyUtils(getApplicationContext()).isNetWorkConnected()) {
                    for (int i = 0; i < list.size(); i++) {
                        Beizhu b = list.get(i);
                        String oldTime = b.getNewTime();  //  旧的状态
                        String id = b.getKuaididanhao();  // 快递单号码
                        String type = b.getType();
                        final String finalurl = url2 + type + url3 + id;
                        CheckUpdateService.this.jsonstring = HttpUtil.getJsonConteny(finalurl);
                        String newTime = JsonTools.getNewDate(CheckUpdateService.this.jsonstring);  // 新的状态
                        if (!oldTime.equals(newTime)) {
                            mDao.upDateForBackService(id, newTime);
                            Intent intent2 = new Intent();
                            intent2.setAction("com.hs.action.UPDTAE_BROADCAST");
                            intent2.putExtra("beizhu", b.getBeizhu());
                            intent2.putExtra("kuaididanhao",b.getKuaididanhao());
                            intent2.putExtra("type",b.getType());
                            intent2.putExtra("msg", "真的有更新啦！");
                            sendBroadcast(intent2);
                        }
                    }
                } else {
                    Log.i("ChaeckService", "没联网");
                }
            }
        }).start();
        return START_STICKY;
    }
}
