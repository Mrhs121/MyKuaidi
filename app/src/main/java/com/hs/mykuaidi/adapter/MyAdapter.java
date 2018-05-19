package com.hs.mykuaidi.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hs.mykuaidi.R;
import com.hs.mykuaidi.model.Kuaidi;

import java.util.List;

/**
 * Created by 黄晟 on 2016/9/3.
 */
public class MyAdapter extends BaseAdapter {
    private Context context;
    private List<Kuaidi> list;

    public MyAdapter(Context context, List<Kuaidi> list) {
        this.context = context;
        this.list = list;
    }

    public void gengXin(List<Kuaidi> newlist) {
        this.list = newlist;
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

        View v = View.inflate(MyAdapter.this.context, R.layout.list_item, null);
        /*
        TextView date = (TextView)v.findViewById(R.id.time_nianyueri);
        TextView shijian = (TextView)v.findViewById(R.id.time_shijian);
        TextView cont = (TextView)v.findViewById(R.id.dizhi);
        if(i==0) {
            date.setTextColor(Color.BLUE);
            shijian.setTextColor(Color.BLUE);
            cont.setTextColor(Color.BLUE);
        }
        date.setText(list.get(i).getTime());
        shijian.setText(list.get(i).getShijian());
        cont.setText(list.get(i).getContext());
        */
        ViewHolder vh;
        if (view == null) {
            vh = new ViewHolder();
//            view = View.inflate(MyAdapter.this.context, R.layout.list_item, null);
//            vh.mDate = (TextView) view.findViewById(R.id.time_nianyueri);
//            vh.mShijian = (TextView) view.findViewById(R.id.time_shijian);
//            vh.mCont = (TextView) view.findViewById(R.id.dizhi);
            view = View.inflate(MyAdapter.this.context, R.layout.card_list_item, null);
            vh.mDate = (TextView) view.findViewById(R.id.card_date);
            vh.mShijian = (TextView) view.findViewById(R.id.card_shijian);
            vh.mCont = (TextView) view.findViewById(R.id.card_dizhi);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }
        if (i == 0) {
            vh.mDate.setTextColor(Color.BLUE);
            vh.mShijian.setTextColor(Color.BLUE);
            vh.mCont.setTextColor(Color.BLUE);
        }
        vh.mDate.setText(list.get(i).getTime());
        vh.mShijian.setText(list.get(i).getShijian());
        vh.mCont.setText(list.get(i).getContext());

        return view;
    }
    class ViewHolder {
        TextView mDate;
        TextView mShijian;
        TextView mCont;
    }
}
