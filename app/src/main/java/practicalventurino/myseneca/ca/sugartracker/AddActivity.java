package practicalventurino.myseneca.ca.sugartracker;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {

    //private Object View;
    private MySQLiteHelper myDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        myDBHelper = new MySQLiteHelper(this);

        // Get data from database
        Cursor rs = myDBHelper.getUserData();
        rs.moveToFirst();

        // Get values
        //String name = rs.getString(rs.getColumnIndex("name"));
        int limit = rs.getInt(rs.getColumnIndex("limitK"));

        Log.v(MySQLiteHelper.class.getName(), "User limit is: " + limit);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public void checkLimit(){

        MySQLiteHelper myDB = new MySQLiteHelper(this);

        // Query the db
        int sugarLimit = myDB.getDailySugarLimit();
        int currentAmount = myDB.getDailySum();

        if(currentAmount >= sugarLimit){

            Log.v("addAmount", "LIMIT REACHED!");

            // Source: http://www.tutorialspoint.com/android/android_notifications.htm

            // Send notification
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
            mBuilder.setSmallIcon(R.drawable.ic_menu_send);
            mBuilder.setContentTitle("Sugar limit has been reached!");
            mBuilder.setContentText("Enough sugar for today, buddy.");

            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            // notificationID allows you to update the notification later on.
            mNotificationManager.notify(99999, mBuilder.build());

        }

        // End this Activity and go back to the previous one!
        finish();

    }

    public void addAmount(View view) {

        //Toast.makeText(AddActivity.this, "Add button clicked!", Toast.LENGTH_SHORT).show();

        // Get input value
        EditText textField = null;
        int value = 0;
        try {
            textField = (EditText) findViewById(R.id.editText);
            value = Integer.parseInt(textField.getText().toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        myDBHelper = new MySQLiteHelper(this);
        myDBHelper.addAmount(value);

        // Empty field
        textField.setText("");

        // Validate the limit
        checkLimit();

    }

}
