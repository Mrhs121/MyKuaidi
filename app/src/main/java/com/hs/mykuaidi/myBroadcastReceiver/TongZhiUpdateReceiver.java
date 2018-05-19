package com.hs.mykuaidi.myBroadcastReceiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.hs.mykuaidi.R;

/**
 * Created by 黄晟 on 2016/10/20.
 */

/**
 * 广播接收器  将更新信息发送到通知栏
 */
public class TongZhiUpdateReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("TongZhiUpdateReceiver", "接受成功" + intent.getStringExtra("beizhu"));
        //Toast.makeText(context, "来自广播的消息----> " + intent.getStringExtra("beizhu") + "---" + intent.getStringExtra("msg"), Toast.LENGTH_SHORT).show();
        NotificationManager mNM = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent1 = new Intent(context, NotificationReceiver.class);
        intent1.putExtra("kuaididanhao",intent.getStringExtra("kuaididanhai"));
        intent1.putExtra("beizhu",intent.getStringExtra("beizhu"));
        intent1.putExtra("type",intent.getStringExtra("type"));
        PendingIntent contentIntent = PendingIntent.getBroadcast(context, 0, intent1, 0);
        String msg  = intent.getStringExtra("beizhu")+"-->"+intent.getStringExtra("msg");
        //Notification n = new Notification(R.drawable.kuaidi_icon,intent.getStringExtra("beizhu")+"-->"+intent.getStringExtra("msg") , System.currentTimeMillis());
        Notification n = new Notification.Builder(context).setSmallIcon(R.drawable.kuaidi_icon)
                .setContentText(msg)
                .setContentTitle("亲！快递又离你更近了哦！")
                .setDefaults(Notification.DEFAULT_SOUND)
                .setAutoCancel(true)
                .setContentIntent(contentIntent)
                .build();
        mNM.notify(1001,n);
    }
}
