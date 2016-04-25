package practicalventurino.myseneca.ca.sugartracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.TableRow;

import java.util.ArrayList;
import java.util.List;

public class MySQLiteHelper extends SQLiteOpenHelper {


    //////////////////////////////////////
    // Create my database
    /////////////////////////////////////

    private static final String DATABASE_NAME = "user.db";
    private static final String TABLE = "users";
    private static final int DATABASE_VERSION = 1;


    //////////////////////////////////////
    // Constructor
    /////////////////////////////////////

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    //////////////////////////////////////
    // Method called when object is instantiated
    /////////////////////////////////////

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(
                "create table users " +
                        "(_id integer primary key autoincrement, name text not null, limitK integer not null, sumK integer default 0)"
        );


        Log.v(MySQLiteHelper.class.getName(), "Database created!");
    }


    //////////////////////////////////////
    // Method called when version is changed
    /////////////////////////////////////

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }

    // Sourece:
    // http://www.androidhive.info/2011/11/android-sqlite-database-tutorial/

    public void addAmount(int amount) {

        Cursor rs = this.getData();
        rs.moveToFirst();

        int sum = rs.getInt(rs.getColumnIndex("limitK")) + amount;

        ContentValues values = new ContentValues();
        values.put("limitK", sum);

        SQLiteDatabase db = this.getReadableDatabase();
        db.update(TABLE, values, null, null);

    }

    public void createUser(String userName, int limit) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", userName);
        contentValues.put("limitK", limit);
        db.insert("users", null, contentValues);

    }

    //////////////////////////////////////
    // Getters
    /////////////////////////////////////

    public int getLimit() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery( "select * from users", null );
        return cursor.getInt(cursor.getColumnIndex("limitK"));
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from users", null );
        return res;
    }

    public int getSum() {
        String query = "SELECT sum FROM " + TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.close();
        return cursor.getInt(3);
    }

    public void deleteTable() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(TABLE, null, null);
    }

}