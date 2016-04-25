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

public class CreateProfileActivity extends AppCompatActivity {

    //private Object View;
    private MySQLiteHelper myDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);





        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void createUser(View view) {

        //Toast.makeText(getApplicationContext(), "Create button clicked!", Toast.LENGTH_SHORT).show();

        // Get input values
        String nameValue = "";
        try {
            EditText textField = (EditText) findViewById(R.id.nameInput);
            nameValue = textField.getText().toString();
            textField.setText("");
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*int ageValue = 0;
        try {
            EditText textField2 = (EditText) findViewById(R.id.ageInput);
            ageValue = Integer.parseInt(textField2.getText().toString());
            textField2.setText("");
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }*/

        int limitValue = 0;
        try {
            EditText textField3 = (EditText) findViewById(R.id.limitInput);
            limitValue = Integer.parseInt(textField3.getText().toString());
            textField3.setText("");
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        myDBHelper = new MySQLiteHelper(this);
        myDBHelper.createUser(nameValue, limitValue);

        // End this Activity and go back to the previous one!
        finish();
    }

}
