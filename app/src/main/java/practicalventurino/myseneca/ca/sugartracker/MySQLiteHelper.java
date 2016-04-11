package practicalventurino.myseneca.ca.sugartracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MySQLiteHelper extends SQLiteOpenHelper {


    //////////////////////////////////////
    // Create my database
    /////////////////////////////////////

    public static final String TABLE = "user";
    public static final String COLUMN_ID = "_id";   // The Primary key MUST be called _id
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_LIMIT = "limit";
    public static final String COLUMN_SUM = "sum";

    private static final String DATABASE_NAME = "user.db";
    private static final int DATABASE_VERSION = 1;


    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_NAME
            + " integer not null, " + COLUMN_LIMIT
            + " integer not null, " + COLUMN_SUM
            + " text not null);";



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
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
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

        /*int sum = 0;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.getSum();
        cursor.close();

        sum = cursor.getInt(3) + amount;*/


        Log.v(MySQLiteHelper.class.getName(), "Value added: " + amount);

    }


    public void createUser(String userName, int age, int limit) {

        /*
        // Get an instance of your database for writing
        SQLiteDatabase db = this.getWritableDatabase();

        // Set values to be stored
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, userName); // User Name
        values.put(COLUMN_LIMIT, limit); // Sugar Limit
        values.put(COLUMN_SUM, 0); // Current amount

        // Inserting Row
        db.insert(TABLE, null, values);
        db.close();
        */
    }

    //////////////////////////////////////
    // Getters
    /////////////////////////////////////

    public int getLimit() {
        String query = "SELECT limit FROM " + TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.close();
        return cursor.getInt(2);
    }

    public int getSum() {
        String query = "SELECT sum FROM " + TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.close();
        return cursor.getInt(3);
    }

}