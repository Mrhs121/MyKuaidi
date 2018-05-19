package com.hs.mykuaidi.myview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hs.mykuaidi.myinterface.SwipeListener;

/**
 * Created by 黄晟 on 2017/1/17.
 */

public class SwipeToExitLinerLayout extends LinearLayout implements View.OnTouchListener, GestureDetector.OnGestureListener {
    private Context mContext;
    GestureDetector mygesture = new GestureDetector(this);
    private final int SWIPE_MIN_WIDTH = 450; // 滑动的最小距离
    private SwipeListener listener;

    public void setContext(Context context)
    {
        mContext = context;
        this.setOnTouchListener(this);
        listener = (SwipeListener)context;
    }
    public SwipeToExitLinerLayout(Context context) {
        super(context);
        //this.setOnTouchListener(this);
    }

    public SwipeToExitLinerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        //this.setOnTouchListener(this);
    }

    public SwipeToExitLinerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //this.setOnTouchListener(this);
    }

    @Override
    public void setOnScrollChangeListener(OnScrollChangeListener l) {
        Log.i("Look——Activity", "setOnScrollChangeListener");
        super.setOnScrollChangeListener(l);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int xstart=0;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xstart = (int) event.getX();
                Log.i("Look——Activity", "放下滑动 xstart = " + xstart);
                break;
            case MotionEvent.ACTION_UP:
                int xlast = (int) event.getX();
                Log.i("Look——Activity", "抬起滑动 xlast = " + xlast);
                int xend = xlast-xstart;
                Log.i("Look——Activity", "最终滑动距离 xend = " + xend);
                if(xend > SWIPE_MIN_WIDTH) {
                    Toast.makeText(mContext,"滑动退出",Toast.LENGTH_SHORT).show();
                    listener.onSwipeListener();
                } else {
                    Toast.makeText(mContext,"距离不过",Toast.LENGTH_SHORT).show();
                }
                break;
        }

        return true;
        //return super.onTouchEvent(event);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return mygesture.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.i("Look——Activity","e1="+e1.getX()+",e2="+e2.getX()+"距离="+(e2.getX()-e1.getX()));
        return false;
    }
}
