package com.hs.mykuaidi.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hs.mykuaidi.R;
import com.hs.mykuaidi.model.Beizhu;

import java.util.List;

/**
 * Created by 黄晟 on 2016/9/23.
 * 功能：快递列表适配器
 */
public class WodeAdapter extends BaseAdapter {

    private Context context;
    private List<Beizhu> list;

    public WodeAdapter(Context context, List<Beizhu> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //View v = View.inflate(WodeAdapter.this.context, R.layout.wode_list_item,null);
        View v = View.inflate(WodeAdapter.this.context, R.layout.wode_list_item_circle, null);
        //  TextView kuaididanhao = (TextView)v.findViewById(R.id.item_kuaididanhao);
//        TextView beizhu = (TextView)v.findViewById(R.id.item_beizhu);
//        TextView type = (TextView)v.findViewById(R.id.item_kuaidigongsi);
//        TextView date = (TextView)v.findViewById(R.id.item_date);
        Log.i("list.size", "大于 0 " + list.size());
        TextView beizhu = (TextView) v.findViewById(R.id.cirle_beizhu);
        //TextView type = (TextView)v.findViewById(R.id.item_kuaidigongsi);
        TextView date = (TextView) v.findViewById(R.id.circle_shijian);
        TextView kuaididanhao = (TextView) v.findViewById(R.id.circle_kuaididanhao);
        TextView circletext = (TextView) v.findViewById(R.id.circletext);
        circletext.setText(list.get(i).getBeizhu().substring(0,1));
        //type.setText("（"+list.get(i).getType()+"）");
        date.setText(list.get(i).getDate());
        kuaididanhao.setText(list.get(i).getKuaididanhao());
        beizhu.setText(list.get(i).getBeizhu());

        return v;
    }
}
