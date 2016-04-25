package practicalventurino.myseneca.ca.sugartracker;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

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

        //Toast.makeText(AddActivity.this, "Value added: " + value, Toast.LENGTH_SHORT).show();

        textField.setText("");

        // End this Activity and go back to the previous one!
        finish();
    }

}
