package com.joey.sqlitedemo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建时间： 2017/10/23.
 * 创 建 人：   joey.
 * 功能描述：
 */

public class LianxirenOperator {
    private LianxirenOpenHelper dbHelper;
    private SQLiteDatabase db;

    public LianxirenOperator(Context context) {
        dbHelper = new LianxirenOpenHelper(context, "lxrData", null, 1);
        db = dbHelper.getWritableDatabase();
    }

    // 添加联系人
    public void add(LianxirenBean lxr) {
        db.execSQL("insert into lxrData values(?,?,?)",
                new Object[] { lxr.getName(), lxr.getNumber(), lxr.getIntroduce() });

    }

    // 修改联系人
    public void update(LianxirenBean lxr) {
        db.execSQL("update lxrData set number=?,introduce=? where name=?",
                new Object[] { lxr.getNumber(), lxr.getIntroduce(), lxr.getName() });
    }

    // 删除联系人
    public void delete(String name) {
        db.execSQL("delete from lxrData where name=?", new String[] { name });
    }

    // 删除所有联系人
    public void deleteAll() {
        db.execSQL("delete from lxrData", new String[] {});
    }

    // 查询联系人
    public LianxirenBean queryOne(String name) {
        LianxirenBean lxr = new LianxirenBean();
        Cursor c = db.rawQuery("select * from lxrData where name= ?", new String[] { name });
        while (c.moveToNext()) {
            lxr.setName(c.getString(0));
            lxr.setNumber(c.getString(1));
            lxr.setIntroduce(c.getString(2));
        }
        c.close();
        return lxr;
    }



    // 查询所有的联系人信息
    public List<LianxirenBean> queryMany() {
        ArrayList<LianxirenBean> lxrs = new ArrayList<LianxirenBean>();
        Cursor c = db.rawQuery("select * from lxrData", null);
        while (c.moveToNext()) {
            LianxirenBean lxr = new LianxirenBean();
            lxr.setName(c.getString(0));
            lxr.setNumber(c.getString(1));
            lxr.setIntroduce(c.getString(2));
            lxrs.add(lxr);
        }
        c.close();
        return lxrs;
    }

    // 检验用户名是否已存在
    public boolean CheckIsDataAlreadyInDBorNot(String value) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String Query = "Select * from lxrData where name =?";
        Cursor cursor = db.rawQuery(Query, new String[] { value });
        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }

    // 判断信息是否已经存在
    public boolean Dataexist(String name, String number, String introduce) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String Query = "Select * from lxrData where name =? and number=? and introduce=?";
        Cursor cursor = db.rawQuery(Query, new String[] { name, number, introduce });
        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }
}
