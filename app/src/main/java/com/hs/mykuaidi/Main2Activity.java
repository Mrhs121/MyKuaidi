package com.hs.mykuaidi;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.hs.mykuaidi.adapter.MyFragmentPagerAdapter;
import com.hs.mykuaidi.myinterface.FragmentListener;
import com.hs.mykuaidi.myservice.CheckUpdateService;
import com.hs.mykuaidi.util.KuaiDiNameUtil;
import com.hs.mykuaidi.util.MyUtils;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.Timer;
import java.util.TimerTask;

public class Main2Activity extends FragmentActivity implements FragmentListener {

    private final String TGA = "MAIN_ACTIVITY";
    private ViewPager viewPager;
    private TextView chaxunkuaidi;
    private TextView chaxunkuaidi2;
    private TextView wodekuaidi;
    private LinearLayout topbar;
    private LinearLayout topbar2;
    private MyFragmentPagerAdapter adapter;
    private Boolean isExit = false;

    private TextView slidingAbout;
    private Switch aSwitch;
    private SlidingMenu menu;
    private TabLayout tabLayout;
    private ImageButton caidan;

    int topbarWidth;
    int topbarHeight;
    int topbar2Width;
    int topbar2Height;
    int[] topbarColors = new int[]{0xffff5e23,0xff5885e7};
    int[] topbarColors2 = new int[]{Color.YELLOW,Color.BLUE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mian_fragment);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        // 加入了slidingmenu之后 要添加这个标志  要不然不能用沉浸式的状态栏了
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        startService();
        initView();
        adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), Main2Activity.this, getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(topbarWidth == 0) {
                    return;
                }
                //topbar.setBackground(getDrawable(position,positionOffset));
                //topbar2.setBackground(getDrawable(position,positionOffset));
                Log.i(TGA,"positionOffset = "+positionOffset);

            }


            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        //查询
                        chaxunkuaidi.setTextColor(ContextCompat.getColor(Main2Activity.this, R.color.lightblue));

                        Drawable drawable_cha = ContextCompat.getDrawable(Main2Activity.this, R.drawable.serch_n);
                        drawable_cha.setBounds(0, 0, drawable_cha.getMinimumWidth(), drawable_cha.getMinimumHeight());
                        Drawable drawable_wode = ContextCompat.getDrawable(Main2Activity.this, R.drawable.my);
                        drawable_wode.setBounds(0, 0, drawable_wode.getMinimumWidth(), drawable_wode.getMinimumHeight());

                        chaxunkuaidi.setCompoundDrawables(null, drawable_cha, null, null);
                        wodekuaidi.setCompoundDrawables(null, drawable_wode, null, null);

                        chaxunkuaidi2.setText("查询快递");
                        wodekuaidi.setTextColor(ContextCompat.getColor(Main2Activity.this, R.color.gray));

//                        topbar.setBackgroundColor(ContextCompat.getColor(Main2Activity.this, R.color.origin));
//                        topbar2.setBackgroundColor(ContextCompat.getColor(Main2Activity.this, R.color.origin));

                        break;
                    case 1:
                        //我的
                        chaxunkuaidi.setTextColor(ContextCompat.getColor(Main2Activity.this, R.color.gray));
                        chaxunkuaidi2.setText("我的快递");
                        wodekuaidi.setTextColor(ContextCompat.getColor(Main2Activity.this, R.color.lightblue));

                        Drawable drawable_cha2 = ContextCompat.getDrawable(Main2Activity.this, R.drawable.serch_ss);
                        drawable_cha2.setBounds(0, 0, drawable_cha2.getMinimumWidth(), drawable_cha2.getMinimumHeight());
                        Drawable drawable_wode2 = ContextCompat.getDrawable(Main2Activity.this, R.drawable.my_s);
                        drawable_wode2.setBounds(0, 0, drawable_wode2.getMinimumWidth(), drawable_wode2.getMinimumHeight());

                        wodekuaidi.setCompoundDrawables(null, drawable_wode2, null, null);
                        chaxunkuaidi.setCompoundDrawables(null, drawable_cha2, null, null);
//                        topbar.setBackgroundColor(ContextCompat.getColor(Main2Activity.this, R.color.blue));
//                        topbar2.setBackgroundColor(ContextCompat.getColor(Main2Activity.this, R.color.blue));
                        break;
                    case 2:
                        chaxunkuaidi2.setText("我的账户");
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });


        wodekuaidi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(1);
            }
        });
        chaxunkuaidi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(0);
            }
        });

        //  关于按钮
        slidingAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menu.toggle();
                //Toast.makeText(Main2Activity.this, "你点击了关于", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setClass(Main2Activity.this, About_Activity.class);
                startActivity(intent);
            }
        });

        //  服务开关  开启之后将true写入 sharepf里面   关闭之后 将false写入  下次开启app的时候根据这个选择开或者不开服务
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                  //  如果打开
                    new MyUtils(Main2Activity.this).saveSet(b);
                    startService();
                    //Toast.makeText(Main2Activity.this, "打开", Toast.LENGTH_SHORT).show();
                } else {
                    new MyUtils(Main2Activity.this).saveSet(b);
                    stopService();
                    //Toast.makeText(Main2Activity.this, "关闭", Toast.LENGTH_SHORT).show();
                }
            }
        });
        caidan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu.toggle();
            }
        });

    }

    public void initView() {
        KuaiDiNameUtil.init();
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        chaxunkuaidi = (TextView) findViewById(R.id.chaxunkuaidi);
        chaxunkuaidi2 = (TextView) findViewById(R.id.chaxunkuaidi2);
        wodekuaidi = (TextView) findViewById(R.id.wodekuaidi);
        topbar = (LinearLayout) findViewById(R.id.topbar);
        topbar2 = (LinearLayout) findViewById(R.id.topbar2);
        tabLayout = (TabLayout)findViewById(R.id.tablayout);
        caidan = (ImageButton)findViewById(R.id.main_caidan);
        WindowManager wm = (WindowManager) getSystemService(
                Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        topbarWidth = dm.widthPixels;
        //topbarWidth = topbar.getWidth();
        topbarHeight = topbar.getLayoutParams().height;
        topbar2Width = topbar2.getWidth();
        topbar2Height = topbar2.getHeight();
        Log.i(TGA,topbarWidth+"----"+topbarHeight);

        menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        menu.setTouchModeAbove(SlidingMenu.LEFT);
        //menu.setShadowWidthRes(R.dimen.shadow_width);
        //menu.setShadowDrawable(R.drawable.shadow);
        menu.setBehindOffsetRes(R.dimen.slidingmeun_left);
        menu.setFadeDegree(0.35f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        menu.setMenu(R.layout.slidingmenu);
        slidingAbout = (TextView) menu.findViewById(R.id.about);
        aSwitch = (Switch) menu.findViewById(R.id.switchbtn);
        aSwitch.setChecked(new MyUtils(Main2Activity.this).getSet());

    }
    public  int dip2px(float dipValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 标题颜色渐变函数
     * @param position 当前标题的下标
     * @param positionOffset  指示器的偏移量
     * @return
     */
    protected Drawable getDrawable(int position, float positionOffset) {
        Bitmap bitmap = Bitmap.createBitmap(topbarWidth, topbarHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();

        // 渐变的颜色是两种颜色混合
        int color1 = topbarColors[(int) Math.ceil(position + positionOffset)];
        Log.i(TGA,""+color1+"---"+topbarColors[0]+"--"+topbarColors[1]);

        paint.setColor(color1);
        paint.setAlpha((int) (255 * positionOffset));
        Log.i(TGA,"setAlpha 1= "+(255 * positionOffset));
        canvas.drawPaint(paint);

        paint.setColor(topbarColors[position]);
        paint.setAlpha((int) (255 * (1 - positionOffset)));
        Log.i(TGA,"setAlpha 2 = "+(255 * positionOffset));
        canvas.drawPaint(paint);

        BitmapDrawable drawable = new BitmapDrawable(getResources(), bitmap);
        return drawable;
    }

    @Override
    public void onFragmentClickListener(int item) {
        adapter.update(item);
        adapter.update(item);
    }

    /**
     * 点击两次返回键推出程序
     */
    public void exitByTwoClick() {
        Timer tEXit = null;
        //既是当isExit为false时
        if (!isExit) {
            isExit = true;
            Toast.makeText(Main2Activity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            tEXit = new Timer();
            tEXit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false;
                }
            }, 2000); //2秒之后没点返回键的话 就取消推出
        } else {
            Main2Activity.this.finish();
        }
    }

    public void onBackPressed() {
        exitByTwoClick();
    }

    private void startService() {
        // 如果服务不在运行   并且设置状态为开启的时候  则开启服务
        if (!isServiceRunning() && new MyUtils(Main2Activity.this).getSet()) {
            Intent intent = new Intent(Main2Activity.this, CheckUpdateService.class);
            final PendingIntent pi = PendingIntent.getService(Main2Activity.this, 0, intent, 0);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Service.ALARM_SERVICE);
            // 每5分钟启动一次
            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, 0,60*60 * 1000, pi);
            Toast.makeText(Main2Activity.this, "重新开启服务", Toast.LENGTH_SHORT).show();
        } else if(isServiceRunning()){
            //  如果服务正在运行的状况下
            Toast.makeText(Main2Activity.this, "服务已在运行中,无需开启", Toast.LENGTH_SHORT).show();
        }else if(!isServiceRunning() && !new MyUtils(Main2Activity.this).getSet()) {
            // 如果服务不在运行 并且设置的是关闭状态下 不开启服务
            Toast.makeText(Main2Activity.this,"服务处于关闭状态,可在侧滑栏中开启",Toast.LENGTH_SHORT).show();
        }
    }

    private void stopService() {
        Intent intent = new Intent(Main2Activity.this, CheckUpdateService.class);
        final PendingIntent pi = PendingIntent.getService(Main2Activity.this, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Service.ALARM_SERVICE);
        alarmManager.cancel(pi);
        stopService(intent);
        Toast.makeText(Main2Activity.this, "已关闭服务", Toast.LENGTH_SHORT).show();
    }

    /**
     * 判断 检查更新的服务是否在运行
     *
     * @return
     */
    private boolean isServiceRunning() {
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if ("com.hs.mykuaidi.myservice.CheckUpdateService".equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
