package com.hs.mykuaidi.dbUtil;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteAbortException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.hs.mykuaidi.model.Beizhu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 黄晟 on 2016/10/5.
 */

public class KuaidiDao {
    private kuaidiSQLiteOpenHelper kOpHelper;

    public KuaidiDao(Context context) {
        kOpHelper = new kuaidiSQLiteOpenHelper(context);
    }

    public void add(String kuaididanhao, String beizhu, String date,String type) {
        SQLiteDatabase db = kOpHelper.getWritableDatabase();
        String sql = "insert into kuaidi (kuaididanhao,beizhu,date,type) values(?,?,?,?)";
        db.execSQL(sql, new Object[]{kuaididanhao, beizhu, date,type});
        db.close();
    }
    //415015183403

    public List<Beizhu> findAllBeizhu() {
        SQLiteDatabase db = kOpHelper.getReadableDatabase();
        String sql = "select * from kuaidi";
        List<Beizhu> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql,null);
        while (cursor.moveToNext()) {
            String mkuaididanhao = cursor.getString(cursor.getColumnIndex("kuaididanhao"));
            String mbeizhu  = cursor.getString(cursor.getColumnIndex("beizhu"));
            String mdate = cursor.getString(cursor.getColumnIndex("date"));
            String mtype = cursor.getString(cursor.getColumnIndex("type"));
            Beizhu beizhu = new Beizhu(mbeizhu,mkuaididanhao,mdate,mtype);
            list.add(beizhu);
        }
        cursor.close();
        db.close();
        return list;
    }

    /**
     * 用于后台服务
     * @return
     */
    public List<Beizhu> findAllBeizhuAndNewTime() {
        SQLiteDatabase db = kOpHelper.getReadableDatabase();
        String sql = "select * from kuaidi";
        List<Beizhu> list = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql,null);
        while (cursor.moveToNext()) {
            String mkuaididanhao = cursor.getString(cursor.getColumnIndex("kuaididanhao"));
            String mbeizhu  = cursor.getString(cursor.getColumnIndex("beizhu"));
            String mdate = cursor.getString(cursor.getColumnIndex("date"));
            String mtype = cursor.getString(cursor.getColumnIndex("type"));
            String mNewTime = cursor.getString(cursor.getColumnIndex("json"));
            Beizhu beizhu = new Beizhu(mbeizhu,mkuaididanhao,mdate,mtype,mNewTime);
            list.add(beizhu);
        }
        cursor.close();
        db.close();
        return list;
    }

    public Boolean isHave(String id) {
        SQLiteDatabase db = kOpHelper.getReadableDatabase();
        String sql = "select * from kuaidi where kuaididanhao="+id;
        Cursor cursor = db.rawQuery(sql,null);
        if(cursor.moveToNext()) {
             return true;
        }
        return false;
    }

    public void upDateForBackService(String kuaididanhao,String json) throws SQLException{
        Log.i("upDateForBackService","进入");
        SQLiteDatabase db = kOpHelper.getWritableDatabase();
        String sql = "update kuaidi set json='"+json+"' where kuaididanhao="+kuaididanhao;
        db.execSQL(sql);
    }

    public int delTheBeizhu(String kuaididanhao,String beizhu) {
        SQLiteDatabase db = kOpHelper.getWritableDatabase();
        String sql = "delete from kuaidi where kuaididanhao="+kuaididanhao+" and beizhu='"+beizhu+"'";
        try {
            db.execSQL(sql);
        } catch (SQLiteAbortException e) {
            e.printStackTrace();
        }
        return 1;
    }

    /**
     * 将数据和json一起保存导数据  为了在后台监测数据是否更新
     * @param kuaididanhao
     * @param beizhu
     * @param date
     * @param type
     */
    public void addAndJson(String kuaididanhao, String beizhu, String date,String type,String newTime) {
        SQLiteDatabase db = kOpHelper.getWritableDatabase();
        String sql = "insert into kuaidi (kuaididanhao,beizhu,date,type,json) values(?,?,?,?,?)";
        db.execSQL(sql,new Object[]{kuaididanhao, beizhu, date,type,newTime});
        db.close();
    }

}
