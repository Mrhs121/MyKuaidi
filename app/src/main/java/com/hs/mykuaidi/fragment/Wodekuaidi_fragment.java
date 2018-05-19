package com.hs.mykuaidi.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.hs.mykuaidi.LookActivity_MaterialDesign;
import com.hs.mykuaidi.R;
import com.hs.mykuaidi.adapter.WodeAdapter;
import com.hs.mykuaidi.animation.MyAnimation;
import com.hs.mykuaidi.dbUtil.KuaidiDao;
import com.hs.mykuaidi.model.Beizhu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 黄晟 on 2016/9/23.
 */
@SuppressLint("ValidFragment")
public class Wodekuaidi_fragment extends android.support.v4.app.Fragment {

    private ListView listView;
    private LinearLayout ll,ll1;
    private List<Beizhu> list;
    private Context mContext;
    private KuaidiDao mDao;
    private FloatingActionButton fab_add;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_wode, null);
        initView(v);
        if(list.size() == 0) {
            ll.setVisibility(View.VISIBLE);
        }
        listView.setLayoutAnimation(MyAnimation.getListAnim());
        listView.setAdapter(new WodeAdapter(getActivity(), list));
        //listview 单机事件 点击跳转activity 查看详细的快递界面
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                list = mDao.findAllBeizhu();
                Beizhu beizhu = list.get(i);
                //Toast.makeText(mContext, "你点击的内容为" + beizhu.toString(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                //intent.setClass(mContext, LookActivity.class);
                intent.setClass(mContext, LookActivity_MaterialDesign.class);
                Bundle bundle = new Bundle();
                bundle.putString("beizhu", beizhu.getBeizhu());
                bundle.putString("kuaididanhao", beizhu.getKuaididanhao());
                bundle.putString("type", beizhu.getType());
                intent.putExtras(bundle);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
                //不销毁当前的activity
                //getActivity().finish();

            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                list = mDao.findAllBeizhu();
                Beizhu beizhu = list.get(i);
                deldialog(beizhu.getKuaididanhao(), beizhu.getBeizhu());
                return true;
            }
        });

        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v,"添加快递",Snackbar.LENGTH_LONG).show();
            }
        });

        return v; //这里的v一定要跟前面的v一样 否侧返回的view就不是上面已经修改过的view了
    }

    // fragment恢复的时候回调的方法
    @Override
    public void onResume() {
        super.onResume();
        //Toast.makeText(mContext,"onResume恢复",Toast.LENGTH_SHORT).show();
        list = mDao.findAllBeizhu();
        Log.i("update", list.toString());
        listView.setLayoutAnimation(MyAnimation.getListAnim());
        listView.setAdapter(new WodeAdapter(getActivity(), list));
    }

    private void deldialog(final String kuaididanhao, final String beizhu) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);  //先得到构造器
        builder.setTitle("提示"); //设置标题
        builder.setMessage("你确定要删除这个快递吗？"); //设置内容
        //builder.setIcon(R.drawable.jinggao);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() { //设置确定按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mDao.delTheBeizhu(kuaididanhao, beizhu);
                Toast.makeText(mContext, "删除成功", Toast.LENGTH_SHORT).show();
                update();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() { //设置取消按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        //参数都设置完成了，创建并显示出来
        builder.create().show();
    }

    @SuppressLint("ValidFragment")
    public Wodekuaidi_fragment(Context context) {
        this.mContext = context;
        list = new ArrayList<>();
        mDao = new KuaidiDao(this.mContext);
        list = mDao.findAllBeizhu();
    }

    /**
     * viewpager 滑动过程中fragment的更新操作
     */
    public void update() {
        list = mDao.findAllBeizhu();
        if (list.size()>0) {
            ll.setVisibility(View.GONE);
        } else {
            ll.setVisibility(View.VISIBLE);
        }
        Log.i("update", list.toString());
        listView.setLayoutAnimation(MyAnimation.getListAnim());
        listView.setAdapter(new WodeAdapter(getActivity(), list));
    }

    public void initView(View v) {
        listView = (ListView) v.findViewById(R.id.wode_listview);
        ll = (LinearLayout)v.findViewById(R.id.frame_ll);
        ll1 = (LinearLayout)v.findViewById(R.id.frame_ll1);
        fab_add = (FloatingActionButton)v.findViewById(R.id.fab_add);

    }

}
