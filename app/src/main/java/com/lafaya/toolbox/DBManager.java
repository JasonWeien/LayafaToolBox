package com.lafaya.toolbox;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JeffYoung on 2016/11/6.
 **/
public class DBManager {
    private DBHelper helper;
    private SQLiteDatabase db;

    public DBManager(Context context) {
        helper = new DBHelper(context);
        //因为getWritableDatabase内部调用了mContext.openOrCreateDatabase(mName, 0, mFactory);
        //所以要确保context已初始化,我们可以把实例化DBManager的步骤放在Activity的onCreate里
        db = helper.getWritableDatabase();
    }

    /**
     * add automaticdoors
     * @param autodoors
     */
    public void add(List<AutoDoor> autodoors) {
        db.beginTransaction();  //开始事务
        try {
            for (AutoDoor autodoor : autodoors) {
                db.execSQL("INSERT INTO automatic door VALUES(null, ?, ?, ?)", new Object[]{autodoor.name, autodoor.btname, autodoor.info});
            }
            db.setTransactionSuccessful();  //设置事务成功完成
        } finally {
            db.endTransaction();    //结束事务
        }
    }

    /**
     * update person's bluetoothname
     * @param autodoor
     */
    public void updateBtname(AutoDoor autodoor) {
        ContentValues cv = new ContentValues();
        cv.put("btname", autodoor.btname);
        db.update("autodoor", cv, "name = ?", new String[]{autodoor.name});
    }

    /**
     * query all automaticdoors, return list
     * @return List<AutomaticDoor>
     */
    public List<AutoDoor> query() {
        ArrayList<AutoDoor> autodoors = new ArrayList<AutoDoor>();
        Cursor c = queryTheCursor();
        while (c.moveToNext()) {
            AutoDoor autodoor = new AutoDoor();
            autodoor._id = c.getInt(c.getColumnIndex("_id"));
            autodoor.name = c.getString(c.getColumnIndex("name"));
            autodoor.btname = c.getString(c.getColumnIndex("btname"));
            autodoor.info = c.getString(c.getColumnIndex("info"));
            autodoors.add(autodoor);
        }
        c.close();
        return autodoors;
    }

    /**
     * query all automaticdoors, return cursor
     * @return  Cursor
     */
    public Cursor queryTheCursor() {
        Cursor c = db.rawQuery("SELECT * FROM autodoor", null);
        return c;
    }

    /**
     * close database
     */
    public void closeDB() {
        db.close();
    }

}
