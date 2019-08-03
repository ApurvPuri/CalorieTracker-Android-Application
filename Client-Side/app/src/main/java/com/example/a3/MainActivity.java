package com.example.a3;

import android.app.Fragment;
import android.app.FragmentManager;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mapquest.mapping.MapQuest;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import static com.mapbox.mapboxsdk.Mapbox.getApplicationContext;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    static int calorieGoal;
    static int totalSteps;
    static int totalCalConsumed, totalCalBurned;
    static int userId;
    private String categories;
    private String selectedCategory = "";
    private String foodItems = "";
    static  StepsDatabase db = null;

    public StepsDatabase getDb() {
        return db;
    }

    public void setDb(StepsDatabase db) {
        this.db = db;
    }

    public int getTotalCalConsumed() {
        return totalCalConsumed;
    }

    public void setTotalCalConsumed(int totalCalConsumed) {
        this.totalCalConsumed = totalCalConsumed;
    }

    public int getTotalCalBurned() {
        return totalCalBurned;
    }

    public void setTotalCalBurned(int totalCalBurned) {
        this.totalCalBurned = totalCalBurned;
    }

    public String getSelectedCategory() {
        return selectedCategory;
    }

    public void setSelectedCategory(String selectedCategory) {
        this.selectedCategory = selectedCategory;
    }


    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }



    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private String userName;

    public int getTheUserId() {
        return userId;
    }

    public void setTheUserId(int userId) {
        this.userId = userId;
    }

    public void setCalorieGoal(int calorieGoal) {
        this.calorieGoal = calorieGoal;
    }

    public int getCalorieGoal() {
        return calorieGoal;
    }

    public int getTotalSteps() {
        return totalSteps;
    }



    public void setTotalSteps(int totalSteps) {
        this.totalSteps = totalSteps;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MapQuest.start(getApplicationContext());

        FoodCatogoryAsyncTask foodCategory = new FoodCatogoryAsyncTask();
        foodCategory.execute();





        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        getSupportActionBar().setTitle("Calorie Tracker");
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, new DashboardFragment()).commit();
        /*SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("login_flag", Context.MODE_PRIVATE);
        SharedPreferences.Editor spEditor = sharedPreferences.edit();
        spEditor.putString("login_flag", "False");
        spEditor.apply();
        String message = sharedPreferences.getString("login_flag", null);*/


        // if (!message.equalsIgnoreCase("True")) {
           /* Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);*/
        //  } else if (message.equalsIgnoreCase("True")) {
        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        User aUser = bundle.getParcelable("user1");
        userName = aUser.getName();

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Fragment nextFragment = null;
        switch (id) {
            case R.id.nav_dashboard:
                nextFragment = new DashboardFragment();
                break;
            case R.id.nav_steps:
                nextFragment = new StepsFragment();
                break;
            case R.id.nav_my_daily_diet:
                nextFragment = new MyDailyDietFragment();
                break;
            case R.id.nav_calorie_tracker:
                nextFragment = new CalorieTrackerFragment();
                break;
            case R.id.nav_report:
                nextFragment = new ReportFragment();
                break;
            case R.id.nav_maps:
                nextFragment = new MapsFragment();
                break;
            case R.id.nav_logout:
                nextFragment = new LogoutFragment();
                break;

        }
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, nextFragment).commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private class FoodCatogoryAsyncTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            String allCategories = restClient.findAllCategories();
            allCategories = allCategories.substring(1, allCategories.length() - 1);
            allCategories = allCategories.replaceAll("[,]", "");
            return allCategories;
        }

        @Override
        protected void onPostExecute(String result) {
            categories = result;

        }
    }



}