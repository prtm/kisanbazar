package preetam.jecrc.kishaanbazaar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by preetam on 3/25/16.
 */
class DataBasehelper {
    private static final String KEY_ROWID = "_id";
    private static final String KEY_NAME = "name";
    private static final String KEY_ADDRONE = "address_one";
    private static final String KEY_ADDRTWO = "address_two";
    private static final String KEY_STATE = "state";
    private static final String KEY_CITY = "city";
    private static final String KEY_POSTAL = "postal";
    private static final String KEY_NUMBER = "mobile_number";

    private static final String DATABASE_NAME = "Address_Store";
    private static final String DATABASE_TABLE = "KisanBazar_Addr";
    private static final int DATABASE_VERSION = 1;
    private final Context OurContext;
    private DbHelper OurHelper;
    private SQLiteDatabase OurDataBase;

    DataBasehelper(Context c) {
        OurContext = c;

    }

    public DataBasehelper open() throws SQLiteException {
        OurHelper = new DbHelper(OurContext);
        OurDataBase = OurHelper.getWritableDatabase();
        return this;

    }

    void close() throws SQLiteException {
        OurHelper.close();

    }

    long createEntry(String name, String addrone, String addrtwo,
                     String state, String city, String postal, String number) throws SQLiteException {

        ContentValues cv = new ContentValues();
        cv.put(KEY_NAME, name);
        cv.put(KEY_ADDRONE, addrone);
        cv.put(KEY_ADDRTWO, addrtwo);
        cv.put(KEY_STATE, state);
        cv.put(KEY_CITY, city);
        cv.put(KEY_POSTAL, postal);
        cv.put(KEY_NUMBER, number);
        return OurDataBase.insert(DATABASE_TABLE, null, cv);

    }

    void deleteAll() throws SQLiteException {
        String columns[] = {KEY_ROWID, KEY_NAME, KEY_ADDRONE, KEY_ADDRTWO,
                KEY_STATE, KEY_CITY, KEY_POSTAL, KEY_NUMBER};
        Cursor c = OurDataBase.query(DATABASE_TABLE, columns, null, null, null,
                null, null);
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            OurDataBase.delete(DATABASE_TABLE, KEY_ROWID, null);
        }
        c.close();
    }

    String getName() throws SQLiteException {
        String columns[] = {KEY_ROWID, KEY_NAME, KEY_ADDRONE, KEY_ADDRTWO,
                KEY_STATE, KEY_CITY, KEY_POSTAL, KEY_NUMBER};
        Cursor c = OurDataBase.query(DATABASE_TABLE, columns, null, null, null,
                null, null);
        String result = "";
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            result = result + c.getString(1);
        }
        c.close();
        return result;
    }

    String getmobileN() throws SQLiteException {
        String columns[] = {KEY_ROWID, KEY_NAME, KEY_ADDRONE, KEY_ADDRTWO,
                KEY_STATE, KEY_CITY, KEY_POSTAL, KEY_NUMBER};
        Cursor c = OurDataBase.query(DATABASE_TABLE, columns, null, null, null,
                null, null);
        String result = "";
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            result = result + c.getString(7);
        }
        c.close();
        return result;
    }


    String getAll() throws SQLiteException {
        String columns[] = {KEY_ROWID, KEY_NAME, KEY_ADDRONE, KEY_ADDRTWO,
                KEY_STATE, KEY_CITY, KEY_POSTAL, KEY_NUMBER};
        Cursor c = OurDataBase.query(DATABASE_TABLE, columns, null, null, null,
                null, null);
        String result = "";
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            result = result + "Name:" + c.getString(1)
                    + ",Address:" + c.getString(2)
                    + ",\t" + c.getString(3)
                    + ",State:"
                    + c.getString(4) + ",City:" + c.getString(5) + ",Postal code:" + c.getString(6) + ",Delivery Mobile Number:" + c.getString(7);
        }
        c.close();
        return result;
    }


    private static class DbHelper extends SQLiteOpenHelper {

        DbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);

        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (" + KEY_ROWID
                    + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_NAME
                    + " TEXT NOT NULL, " + KEY_ADDRONE + " TEXT NOT NULL, "
                    + KEY_ADDRTWO + " TEXT NOT NULL, " + KEY_STATE
                    + " TEXT NOT NULL, " + KEY_CITY
                    + " TEXT NOT NULL, " + KEY_POSTAL + " TEXT NOT NULL, " + KEY_NUMBER + " TEXT NOT NULL);");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP IF TABLE EXISTS " + DATABASE_TABLE);
            onCreate(db);

        }

    }

}

