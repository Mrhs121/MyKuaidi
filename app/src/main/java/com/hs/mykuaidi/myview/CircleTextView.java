package com.hs.mykuaidi.myview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by 黄晟 on 2016/11/17.
 */

public class CircleTextView extends TextView {

    private Context mContext;

    public CircleTextView(Context context) {
        super(context);
    }

    public CircleTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(9);
        //设置抗锯齿
        paint.setAntiAlias(true);
        //设置画得一个半径，然后比较长和宽，确定半径
        int r = getMeasuredWidth()>getMeasuredHeight()?getMeasuredWidth():getMeasuredHeight();
        RectF rectF = new RectF();
        rectF.set(getPaddingLeft(),getPaddingTop(),r-getPaddingRight(),r-getPaddingBottom());
        canvas.drawArc(rectF,0,360,false,paint);

    }
}
