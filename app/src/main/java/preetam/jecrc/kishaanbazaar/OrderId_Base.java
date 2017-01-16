package preetam.jecrc.kishaanbazaar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by preetam on 3/25/16.
 */
class OrderId_Base {
    private static final String KEY_ROWID = "_id";
    private static final String KEY_NAME = "name";
    private static final String KEY_ORDER_ID = "address_one";
    private static final String KEY_NUMBER = "mobile_number";

    private static final String DATABASE_NAME = "Order_Id";
    private static final String DATABASE_TABLE = "KisanBazar_OrderId";
    private static final int DATABASE_VERSION = 1;
    private final Context OurContext;
    private DbHelper OurHelper;
    private SQLiteDatabase OurDataBase;

    OrderId_Base(Context c) {
        OurContext = c;

    }

    public OrderId_Base open() throws SQLiteException {
        OurHelper = new DbHelper(OurContext);
        OurDataBase = OurHelper.getWritableDatabase();
        return this;

    }

    void close() throws SQLiteException {
        OurHelper.close();

    }

    long createEntry(String name, String order_id, String number) throws SQLiteException {

        ContentValues cv = new ContentValues();
        cv.put(KEY_NAME, name);
        cv.put(KEY_ORDER_ID, order_id);
        cv.put(KEY_NUMBER, number);
        return OurDataBase.insert(DATABASE_TABLE, null, cv);

    }




    /*
    public String getTeacher(long li) throws SQLiteException {
        String columns[] = {KEY_ROWID, KEY_ROLL, KEY_CLASSID, KEY_SUB,
                KEY_TEACHER_ID};
        Cursor c = OurDataBase.query(DATABASE_TABLE, columns, KEY_ROWID + "="
                + li, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
            String roll1 = c.getString(4);
            return roll1;

        }
        return null;
    }

     */

    List<Information_my_order> getAll() {

        String columns[] = {KEY_ROWID, KEY_NAME, KEY_ORDER_ID, KEY_NUMBER};
        Cursor c = OurDataBase.query(DATABASE_TABLE, columns, null, null, null,
                null, null);
        List<Information_my_order> list = new ArrayList<>();
        Information_my_order order;
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            order = new Information_my_order();
            order.name = c.getString(1);
            order.order_id = c.getString(2);
            order.number = c.getString(3);
            list.add(order);
        }
        c.close();

        return list;
    }

    private static class DbHelper extends SQLiteOpenHelper {

        DbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);

        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (" + KEY_ROWID
                    + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_NAME
                    + " TEXT NOT NULL, " + KEY_ORDER_ID + " TEXT NOT NULL, "
                    + KEY_NUMBER + " TEXT NOT NULL);");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP IF TABLE EXISTS " + DATABASE_TABLE);
            onCreate(db);

        }

    }

}

