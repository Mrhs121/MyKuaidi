package com.hs.mykuaidi.dbUtil;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.hs.mykuaidi.util.MyUtils;

/**
 * Created by 黄晟 on 2016/10/5.
 */

public class kuaidiSQLiteOpenHelper extends SQLiteOpenHelper {

    private Context mContext;

    public kuaidiSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "kuaidi.db", null , 1);
        mContext = context;
    }

    public kuaidiSQLiteOpenHelper(Context context) {
        super(context, "kuaidi.db", null , 3);
        mContext = context;
    }

    /**
     * 数据库字段 id kuaididanhao  beizhu date
     * @param sqLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.i("sqliteopenhelpr","新建表");
        sqLiteDatabase.execSQL("create table kuaidi (id integer primary key autoincrement,kuaididanhao varchar(50),beizhu varchar(50),date varchar(50),type varchar(20),json varchar(100))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("alter table kuaidi add json varchar(100)");
        new MyUtils(mContext).ToastTool("更新数据库表结构成功");
        Log.i("sqliteopenhelpr","更新表结构");
    }
}
