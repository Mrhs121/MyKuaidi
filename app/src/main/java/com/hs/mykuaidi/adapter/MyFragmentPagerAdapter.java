package com.hs.mykuaidi.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

import com.hs.mykuaidi.fragment.MainActivity_fragment;
import com.hs.mykuaidi.fragment.Wodekuaidi_fragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 黄晟 on 2016/10/4.
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;
    private List<String> tagList;
    private final FragmentManager mFm;
    private String[] mTitles = new String[]{"查 询", "快 递", "我 的"};

    public MyFragmentPagerAdapter(FragmentManager fm, Context context, FragmentManager mfm) {
        super(fm);
        this.mFm = mfm;
        this.mContext = context;
        tagList = new ArrayList<>();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //"android:switcher:" + viewId + ":" + index;
        //tagList.add(makeFragmentName(container.getId(), getItemId(position)));
        String tag = "android:switcher:" + container.getId() + ":" + (int) getItemId(position);
        tagList.add(tag);
        Log.i("MyFraasdfas", "进入");
        return super.instantiateItem(container, position);
    }

    // main_frag 回调函数 更新 雷彪
    public void update(int item) {
        Fragment fragment = mFm.findFragmentByTag(tagList.get(item));
        if (fragment != null) {
            switch (item) {
                case 0:
                    break;
                case 1:
                    ((Wodekuaidi_fragment) fragment).update();
                    break;
            }
        }
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                MainActivity_fragment mainActivity_fragment = new MainActivity_fragment(mContext);
                return mainActivity_fragment;
            case 1:
                Wodekuaidi_fragment wode = new Wodekuaidi_fragment(mContext);
                return wode;

        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    public CharSequence getPageTitle(int position) {

        return mTitles[position % mTitles.length];
    }

}