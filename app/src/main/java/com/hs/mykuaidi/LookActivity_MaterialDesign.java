package com.hs.mykuaidi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by 黄晟 on 2017/8/8.
 */

public class LookActivity_MaterialDesign extends AppCompatActivity{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_material);
        //透明状态栏
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        //透明导航栏
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        Bundle bundle = getIntent().getExtras();
        String beizhu = bundle.getString("beizhu");
        String kuaididanhao = bundle.getString("kuaididanhao");
        String type = bundle.getString("type");
        Log.i("MaterialDesign","beizhu:"+beizhu+"  单号："+kuaididanhao+"  公司"+type);

    }
}
