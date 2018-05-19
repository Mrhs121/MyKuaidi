package com.hs.mykuaidi.myBroadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.hs.mykuaidi.Main2Activity;

/**
 * Created by 黄晟 on 2016/10/20.
 */

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intent1 = new Intent(context, Main2Activity.class);
        Bundle bundle = new Bundle();
        bundle.putString("kuaididanhao",intent.getStringExtra("kuaididanhao"));
        bundle.putString("beizhu",intent.getStringExtra("beizhu"));
        bundle.putString("type",intent.getStringExtra("type"));
        intent1.putExtras(bundle);
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent1);
    }
}
