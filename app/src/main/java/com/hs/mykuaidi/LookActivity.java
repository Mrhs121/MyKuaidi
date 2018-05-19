package com.hs.mykuaidi;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.hs.mykuaidi.adapter.MyAdapter;
import com.hs.mykuaidi.animation.MyAnimation;
import com.hs.mykuaidi.dbUtil.KuaidiDao;
import com.hs.mykuaidi.httpTools.HttpUtil;
import com.hs.mykuaidi.jsonTools.JsonTools;
import com.hs.mykuaidi.model.Kuaidi;
import com.hs.mykuaidi.myinterface.SwipeListener;
import com.hs.mykuaidi.myview.SwipeToExitLinerLayout;
import com.hs.mykuaidi.util.MyUtils;

import java.util.ArrayList;
import java.util.List;

public class LookActivity extends AppCompatActivity implements SwipeListener{


    private TextView beizhu_tv;
    private TextView kuaididanhao_tv;
    private TextView type_tv;
    private ListView looklistview;
    private ImageView look_back;
    private ImageButton look_meun;
    private LinearLayout look_back_LinearLayout;
    private List<Kuaidi> list;

    private final String url2 = "http://www.kuaidi100.com/query?type=";
    private final String url3 = "&postid=";
    private final String typeurl = "http://www.kuaidi100.com/autonumber/autoComNum?text=";
    private String jsonstring = "";

    private PopupMenu popup;

    private MyAdapter myAdapter;
    private PullToRefreshListView mPullToRefreshListView;

    private String id;
    private SwipeToExitLinerLayout linerLayout;

    public Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            if (message.what == 0x0001) {
                myAdapter = new MyAdapter(LookActivity.this, (List<Kuaidi>) message.obj);
                looklistview.setLayoutAnimation(MyAnimation.getListAnim());
                //looklistview.setAdapter(new MyAdapter(LookActivity.this, (List<Kuaidi>) message.obj));
                looklistview.setAdapter(myAdapter);
            } else if (message.what == 0x0002) {
                Toast.makeText(LookActivity.this, "亲！查询失败，请删除此备注", Toast.LENGTH_LONG).show();
            } else if (message.what == 0x0003) {
                Toast.makeText(LookActivity.this, "未连接到网络", Toast.LENGTH_SHORT).show();
            } else if (message.what == 0x0004) {
                Toast.makeText(LookActivity.this,"更新成功",Toast.LENGTH_SHORT).show();
            } else if (message.what == 0x0005) {
                Toast.makeText(LookActivity.this,"更新失败",Toast.LENGTH_SHORT).show();
            } else if (message.what == 0x0006) {
                Toast.makeText(LookActivity.this,"未连接到网络",Toast.LENGTH_SHORT).show();
            }
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look);
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        initView();
        id = kuaididanhao_tv.getText().toString().trim();
        Bundle bundle = getIntent().getExtras();
        String beizhu = bundle.getString("beizhu");
        String kuaididanhao = bundle.getString("kuaididanhao");
        String type = bundle.getString("type");

        beizhu_tv.setText(beizhu);
        kuaididanhao_tv.setText(kuaididanhao);
        type_tv.setText(type);
        netWork();

//        look_back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(LookActivity.this, "返回", Toast.LENGTH_SHORT).show();
//                LookActivity.this.finish();
//            }
//        });
        look_back_LinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LookActivity.this, "返回", Toast.LENGTH_SHORT).show();
                LookActivity.this.finish();
            }
        });
        mPullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                if(new MyUtils(LookActivity.this).isNetWorkConnected()) {
                    String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
                            DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                    // Update the LastUpdatedLabel
                    refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                    // Do work to refresh the list here.
                    new GetDataTask().execute();
                } else {
                    Toast.makeText(LookActivity.this,"未连接到网络",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    /**
     * 异步加载数据
     */
    private class GetDataTask extends AsyncTask<Void, Void, String[]> {


        @Override
        protected String[] doInBackground(Void... params) {
            // Simulates a background job.
            try {
                if (new MyUtils(LookActivity.this).isNetWorkConnected()) {

                    // 从网络得到数据
                    final String id = kuaididanhao_tv.getText().toString().trim();
                    String typejson = HttpUtil.getJsonConteny(typeurl + id);  //接口返回的快递公司的json
                    String type = JsonTools.getKuaidiType(typejson);      //解析快递名称
                    Log.i("MainActivity_f", type);
                    // final String finalurl = url + id;
                    final String finalurl = url2 + type + url3 + id;
                    Log.i("MainActivity_f", finalurl);
                    LookActivity.this.jsonstring = HttpUtil.getJsonConteny(finalurl);
                    Log.i("MainActivity_f", jsonstring);
                    if (JsonTools.idIsOk(LookActivity.this.jsonstring, id)) {
                        LookActivity.this.list = JsonTools.getDataByJson(LookActivity.this.jsonstring);
                        LookActivity.this.myAdapter.gengXin(LookActivity.this.list);
                        Message message = new Message();
                        message.what = 0x0004;
                        handler.sendMessage(message);
                    } else {
                        LookActivity.this.myAdapter.gengXin(LookActivity.this.list);
                        Message message = new Message();
                        message.what = 0x0005;
                        handler.sendMessage(message);
                    }
                } else {
                    LookActivity.this.myAdapter.gengXin(LookActivity.this.list);
                    Message message = new Message();
                    message.what = 0x0006;
                    handler.sendMessage(message);
                }
            } catch (Exception e) {
            }
            return new String[]{" ", ""};
        }

        @Override
        protected void onPostExecute(String[] result) {
            LookActivity.this.myAdapter.notifyDataSetChanged();
            mPullToRefreshListView.onRefreshComplete();
        }
    }

    public void netWork() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (new MyUtils(LookActivity.this).isNetWorkConnected()) {

                    final String id = kuaididanhao_tv.getText().toString().trim();
                    String typejson = HttpUtil.getJsonConteny(typeurl + id);  //接口返回的快递公司的json
                    String type = JsonTools.getKuaidiType(typejson);      //解析快递名称
                    Log.i("MainActivity_f", type);
                    // final String finalurl = url + id;
                    final String finalurl = url2 + type + url3 + id;
                    Log.i("MainActivity_f", finalurl);
                    LookActivity.this.jsonstring = HttpUtil.getJsonConteny(finalurl);
                    Log.i("MainActivity_f", jsonstring);
                    if (JsonTools.idIsOk(LookActivity.this.jsonstring, id)) {
                        LookActivity.this.list = JsonTools.getDataByJson(LookActivity.this.jsonstring);
                        Message msg = new Message();
                        msg.what = 0x0001;
                        msg.obj = LookActivity.this.list;
                        handler.sendMessage(msg);
                    } else {
                        Message msg2 = new Message();
                        msg2.what = 0x0002;
                        handler.sendMessage(msg2);
                    }
                } else {
                    Message msg3 = new Message();
                    msg3.what = 0x0003;
                    handler.sendMessage(msg3);
                }
            }
        }).start();
    }
//    public void onPopupButtonClick(View button) {
//        Snackbar.make(button, "取消", Snackbar.LENGTH_LONG)
//                .setAction("刷新", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Toast.makeText(LookActivity.this, "刷新", Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .setAction("退出", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Toast.makeText(LookActivity.this, "退出", Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .setAction("取消", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Toast.makeText(LookActivity.this, "取消", Toast.LENGTH_SHORT).show();
//                    }
//                }).show();
//    }
    public void onPopupButtonClick(View button) {
        // 创建PopupMenu对象
        popup = new PopupMenu(this, button);
        // 将R.menu.popup_menu菜单资源加载到popup菜单中
        getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
        // 为popup菜单的菜单项单击事件绑定事件监听器
        popup.setOnMenuItemClickListener(
                new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.exit:
                                // 隐藏该对话框
                                popup.dismiss();
                                break;
                            case R.id.del:
                                KuaidiDao mDao = new KuaidiDao(LookActivity.this);
                                mDao.delTheBeizhu(kuaididanhao_tv.getText().toString(), beizhu_tv.getText().toString());
                                Toast.makeText(LookActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                                LookActivity.this.finish();
                                break;
                            case R.id.shuaxin:
                                netWork();
                                Toast.makeText(LookActivity.this, "刷新成功", Toast.LENGTH_SHORT).show();
                                break;
//                            default:
//                                // 使用Toast显示用户单击的菜单项
//                                Toast.makeText(LookActivity.this,
//                                        "您单击了【" + item.getTitle() + "】菜单项"
//                                        , Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    }
                });
        popup.show();
    }

    public void initView() {
        beizhu_tv = (TextView) findViewById(R.id.look_beizhu);
        kuaididanhao_tv = (TextView) findViewById(R.id.look_kuaididanhao);
        type_tv = (TextView) findViewById(R.id.look_type);
        //looklistview = (ListView) findViewById(R.id.look_listview);
        look_back = (ImageView) findViewById(R.id.look_back);
        look_meun = (ImageButton) findViewById(R.id.look_meun);
        linerLayout = (SwipeToExitLinerLayout)findViewById(R.id.activity_look);
       linerLayout.setContext(LookActivity.this);
        list = new ArrayList<>();
        mPullToRefreshListView = (PullToRefreshListView) findViewById(R.id.look_listview);
        mPullToRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        looklistview = mPullToRefreshListView.getRefreshableView();
        look_back_LinearLayout = (LinearLayout)findViewById(R.id.look_back_LinearLayout);
    }

    @Override
    public void onSwipeListener() {
        Toast.makeText(LookActivity.this, "返回", Toast.LENGTH_SHORT).show();
        LookActivity.this.finish();
    }
}
