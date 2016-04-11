package practicalventurino.myseneca.ca.sugartracker;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

        Log.v(MySQLiteHelper.class.getName(), "Database: " + myDBHelper.getDatabaseName());


        //myDBHelper.createUser("john", 200);

        //Log.v(MySQLiteHelper.class.getName(), "User limit is: " + myDBHelper.getLimit());



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

        Toast.makeText(AddActivity.this, "Value added: " + value, Toast.LENGTH_SHORT).show();

        textField.setText("");
    }

}
