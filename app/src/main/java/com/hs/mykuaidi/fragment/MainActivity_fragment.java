package com.hs.mykuaidi.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.hs.mykuaidi.R;
import com.hs.mykuaidi.adapter.MyAdapter;
import com.hs.mykuaidi.animation.MyAnimation;
import com.hs.mykuaidi.dbUtil.KuaidiDao;
import com.hs.mykuaidi.httpTools.HttpUtil;
import com.hs.mykuaidi.jsonTools.JsonTools;
import com.hs.mykuaidi.model.Kuaidi;
import com.hs.mykuaidi.myinterface.FragmentListener;
import com.hs.mykuaidi.util.KuaiDiNameUtil;
import com.hs.mykuaidi.util.MyUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class MainActivity_fragment extends android.support.v4.app.Fragment {

    private Context mContext;
    private MyUtils myUtils;
    private KuaidiDao mKuaidiDao;

    private ListView listView;
    private EditText editText;
    private Button button;
    private Button addBtn;

    //private final String url = "http://www.kuaidi100.com/query?type=tiantian&postid=";
    private final String url2 = "http://www.kuaidi100.com/query?type=";
    private final String url3 = "&postid=";
    private final String typeurl = "http://www.kuaidi100.com/autonumber/autoComNum?text=";
    private String mJsonString = "";
    private String mType;
    private List<Kuaidi> list;
    private Boolean isCanSave = false;
    private FragmentListener listener;

    public Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            if (message.what == 0x0001) {
                listView.setLayoutAnimation(MyAnimation.getListAnim());
                listView.setAdapter(new MyAdapter(getActivity(), (List<Kuaidi>) message.obj));
                ifWantToSaveThe();
            } else if (message.what == 0x0002) {
                Toast.makeText(getActivity(), "亲！您输入的快递单号有误哦，请重新输入", Toast.LENGTH_LONG).show();
            } else if (message.what == 0x0003) {
                Toast.makeText(getActivity(), "设备未连接到网络，请开启网络连接", Toast.LENGTH_LONG).show();
            }
            return false;
        }
    });

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.activity_main, null);
        initView(v);

        // 查询按钮
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideJianpan(view);
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        //如果联网
                        if (new MyUtils(mContext).isNetWorkConnected()) {
                            final String id = editText.getText().toString().trim();   //快递单号码
                            String typejson = HttpUtil.getJsonConteny(typeurl + id);  //通过快单得到公司的名字的json数据
                            String type = JsonTools.getKuaidiType(typejson);      //解析快递名称
                            MainActivity_fragment.this.mType = type;
                                 Log.i("MainActivity_f", type);
                            // final String finalurl = url + id;
                            final String finalurl = url2 + type + url3 + id;
                                 Log.i("MainActivity_f", finalurl);
                            MainActivity_fragment.this.mJsonString = HttpUtil.getJsonConteny(finalurl); //得到完整的快递信息  到哪了
                                 Log.i("MainActivity_f", mJsonString);
                            // 检查json数据是否正确
                            if (JsonTools.idIsOk(MainActivity_fragment.this.mJsonString, id)) {
                                //将快递单号保存下来
                                isCanSave = true;
                                myUtils.saveHistoryToSharedPreferences(id);
                                MainActivity_fragment.this.list = JsonTools.getDataByJson(MainActivity_fragment.this.mJsonString);
                                Message msg = new Message();
                                msg.what = 0x0001;
                                msg.obj = MainActivity_fragment.this.list;
                                handler.sendMessage(msg);
                            } else {
                                isCanSave = false;
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
            } // on结束
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //如果查询成功 即可保存在数据库中
                if (isCanSave) {
                    setDialog();
                } else {
                    Toast.makeText(mContext, "亲！查询成功后才能添加哦！", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Log.i("MainFrag", "进入");
        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (FragmentListener) activity;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void ifWantToSaveThe() {
        Log.i("MainFrag", "进入ifWantToSaveThe");
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);  //先得到构造器
        LayoutInflater inflaterDl = LayoutInflater.from(mContext);
        View v = inflaterDl.inflate(R.layout.savetishi, null);
        final AlertDialog dialog = builder.create();
        dialog.setView(v,0,0,0,0);
        Button save_canel = (Button) v.findViewById(R.id.save_canel);
        Button save_yes = (Button) v.findViewById(R.id.save_yes);
        save_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDialog();
                dialog.dismiss();
            }
        });
        save_canel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
        WindowManager.LayoutParams params =
                dialog.getWindow().getAttributes();
        params.width = 900;
       // params.height = 490 ;
        dialog.getWindow().setAttributes(params);
    }



    /**
     * 保存备忘录的窗口
     */
    private void setDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);  //先得到构造器
        LayoutInflater inflaterDl = LayoutInflater.from(mContext);
        View view = inflaterDl.inflate(R.layout.add_dialog, null);
        // builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.setView(view);

        final EditText beizhuet = (EditText) view.findViewById(R.id.dialog_beizhu);
        final EditText type = (EditText) view.findViewById(R.id.dialog_type);

        type.setText(KuaiDiNameUtil.getName(MainActivity_fragment.this.mType));
        final String newTime = JsonTools.getNewDate(MainActivity_fragment.this.mJsonString);
        Button queding = (Button) view.findViewById(R.id.queding);
        Button quxiao = (Button) view.findViewById(R.id.quxiao);
        queding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideJianpan(view);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String date = sdf.format(new java.util.Date());

                //  如果数据控有
                if (mKuaidiDao.isHave(editText.getText().toString().trim())) {
                    Toast.makeText(mContext, "你已经保存过这个快递啦!!", Toast.LENGTH_SHORT).show();
                } else {
                    if (beizhuet.getText().toString().equals("") || type.getText().toString().equals("")) {
                        Toast.makeText(mContext, "备注不能为空", Toast.LENGTH_SHORT).show();
                    } else {
                        //mKuaidiDao.add(editText.getText().toString().trim(),beizhuet.getText().toString(),date,type.getText().toString());
                        mKuaidiDao.addAndJson(editText.getText().toString().trim(),
                                beizhuet.getText().toString(),
                                date,
                                KuaiDiNameUtil.getName(type.getText().toString().trim()),
                                newTime);
                        Toast.makeText(mContext, "保存成功", Toast.LENGTH_SHORT).show();
                        //对数据进行更新   关键操作
                        if (listener != null) {
                            listener.onFragmentClickListener(1);
                        }
                        dialog.dismiss();
                    }
                }


            }
        });

        quxiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "你点了取消", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        // builder.create().show();
        dialog.show();
//        dialog.show();
//        dialog.getWindow().setContentView(view);
//        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
//                String date=sdf.format(new java.util.Date());
//                mKuaidiDao.add(editText.getText().toString().trim(),beizhuet.getText().toString(),date,type.getText().toString());
//                //对数据进行更新
//                if (listener != null) {
//                    listener.onFragmentClickListener(1);
//                }
//            }
//        });
//        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(mContext, "你点了取消", Toast.LENGTH_SHORT).show();
//            }
//        });
        //builder.setView(view);
        //builder.create().show();

    }

    @SuppressLint("ValidFragment")
    public MainActivity_fragment(Context context) {
        this.mContext = context;
        myUtils = new MyUtils(this.mContext);
        mKuaidiDao = new KuaidiDao(this.mContext);
    }

    public void initView(View v) {
        listView = (ListView) v.findViewById(R.id.listview);
        editText = (EditText) v.findViewById(R.id.et_cha);
        editText.setText(myUtils.getHistoryToSharedPreferences());
        button = (Button) v.findViewById(R.id.btn_cha);
        list = new ArrayList<>();
        addBtn = (Button) v.findViewById(R.id.addbtn);
    }

    public void hideJianpan(View v) {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0); //隐藏
    }
}
