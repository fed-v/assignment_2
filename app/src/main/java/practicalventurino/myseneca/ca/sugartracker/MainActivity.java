package practicalventurino.myseneca.ca.sugartracker;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //////////////////////////////////////////////////
        /// QUERY THE DB TO SEE IF THERE ARE USERS
        MySQLiteHelper myDB = new MySQLiteHelper(this);

        // If there isn't a user, go create one!
        if(myDB.getUserCount() == 0) {
            Intent intent = new Intent(getApplicationContext(), CreateProfileActivity.class);
            startActivity(intent);
        } else {
            displayUserInfo(TimeSpan.DAILY);
        }


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Change to add sugar Activity!
                Intent intent = new Intent(getApplicationContext(), AddActivity.class);
                startActivity(intent);

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.deleteUser) {

            // Delete User for debugging!
            /*MySQLiteHelper myDB = new MySQLiteHelper(this);
            myDB.deleteTable();
            Toast.makeText(getApplicationContext(), "User deleted! ", Toast.LENGTH_SHORT).show();*/
            return true;

        }

        return super.onOptionsItemSelected(item);
    }

    /** Called when the activity has become visible. */
    @Override
    protected void onResume() {
        super.onResume();

        // if no user, don't update UI
        MySQLiteHelper myDB = new MySQLiteHelper(this);
        if(myDB.getUserCount() > 0) {
            displayUserInfo(TimeSpan.DAILY);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_daily) {
            displayUserInfo(TimeSpan.DAILY);
        } else if (id == R.id.nav_weekly) {
            displayUserInfo(TimeSpan.WEEKLY);
        } else if (id == R.id.nav_monthly) {
            displayUserInfo(TimeSpan.MONTHLY);
        } else if (id == R.id.nav_share) {
            Toast.makeText(MainActivity.this, "Share on social media!", Toast.LENGTH_SHORT).show();
        }else if (id == R.id.nav_send) {
            Toast.makeText(MainActivity.this, "Send via email!", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    enum TimeSpan {
        DAILY, WEEKLY, MONTHLY
    }

    protected void displayUserInfo(TimeSpan timeSpan) {

        //Toast.makeText(getApplicationContext(), "Show user info for: " + timeSpan, Toast.LENGTH_SHORT).show();

        MySQLiteHelper myDB = new MySQLiteHelper(this);

        // Query the db
        int sugarLimit = myDB.getDailySugarLimit();
        int currentAmount = myDB.getDailySum();
        String userName = myDB.getUserName();

        // Calculate Progress
        int progress;
        switch (timeSpan) {
            case DAILY: progress = (100 * currentAmount) / sugarLimit;
                        break;
            case WEEKLY: sugarLimit = sugarLimit * 7;
                         progress = ((100 * currentAmount) / sugarLimit);
                         break;
            case MONTHLY: sugarLimit = sugarLimit * 30;
                          progress = ((100 * currentAmount) / sugarLimit);
                          break;
            default: progress = 0;
        }

        // Display Current Amount
        TextView currentAmountField = (TextView) findViewById(R.id.currentAmount);
        currentAmountField.setText(currentAmount + " gr");

        // Display Daily Limit
        TextView dailyLimitField = (TextView) findViewById(R.id.limitField);
        dailyLimitField.setText(sugarLimit + "gr");

        // Display Daily Limit
        TextView homeTitle = (TextView) findViewById(R.id.homeTitle);
        homeTitle.setText(userName.substring(0, 1).toUpperCase() + userName.substring(1) + "'s " + timeSpan.toString().toLowerCase() + " intake is: ");

        // Source: http://stackoverflow.com/questions/3904579/how-to-capitalize-the-first-letter-of-a-string-in-java


        // Display Progress Bar
        ProgressBar mProgress = (ProgressBar) findViewById(R.id.progressBar);

        if (progress < 100) {
            mProgress.setProgress(progress);
            mProgress.getProgressDrawable().setColorFilter(Color.DKGRAY, android.graphics.PorterDuff.Mode.SRC_IN);
        } else {
            mProgress.setProgress(100);
            mProgress.getProgressDrawable().setColorFilter(
                    Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);

            // Source: http://stackoverflow.com/questions/2020882/how-to-change-progress-bars-progress-color-in-android
        }

    }

}
