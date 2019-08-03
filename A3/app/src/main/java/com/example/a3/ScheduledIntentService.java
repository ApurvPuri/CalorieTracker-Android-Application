package com.example.a3;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ScheduledIntentService extends IntentService {
    StepsDatabase db = null;

    public ScheduledIntentService() {
        super("ScheduledIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        db = MainActivity.db;
        final Calendar myCalendar = Calendar.getInstance();

        Date consumptionDate = myCalendar.getTime();

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String sConsumptionDate = simpleDateFormat.format(consumptionDate);

        PostAsyncTaskAddReport postAsyncTaskAddReport = new PostAsyncTaskAddReport();
        postAsyncTaskAddReport.execute(sConsumptionDate);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }


    private class PostAsyncTaskAddReport extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date consumptionDate = null;
            try {
                consumptionDate = sdf.parse(params[0]);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            NewUser newUser = new NewUser(MainActivity.userId);

            int totalCalConsumed = MainActivity.totalCalConsumed;
            int totalCalBurned = MainActivity.totalCalBurned;
            int totalSteps = MainActivity.totalSteps;
            int calGoal = MainActivity.calorieGoal;


            //to be removed.
                        /*SharedPreferences reportIdCount = getActivity().getSharedPreferences("report_id", Context.MODE_PRIVATE);
                    SharedPreferences.Editor spEditor = reportIdCount.edit();
                    spEditor.putInt("report_id", 5);
                    spEditor.apply();*/

            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("report_id", Context.MODE_PRIVATE);
            int reportId = sharedPreferences.getInt("report_id", 0);
            reportId++;
            SharedPreferences.Editor spEditor2 = sharedPreferences.edit();
            spEditor2.putInt("report_id", reportId);
            spEditor2.apply();

            NewReport newReport = new NewReport(reportId, consumptionDate, totalCalConsumed, totalCalBurned, totalSteps, calGoal, newUser);

            restClient.createNewReport(newReport);
            return "Details Submitted";
        }

        @Override
        protected void onPostExecute(String response) {


            DeleteDatabase deleteDatabase = new DeleteDatabase();
            deleteDatabase.execute();

        }

    }

    private class DeleteDatabase extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            if (!(db == null)) {
                db.stepsDao().deleteAll();
                MainActivity.totalSteps = 0;
                MainActivity.calorieGoal = 0;
                return null;
            }
            return null;
        }

        protected void onPostExecute(Void param) {
            //textView_delete.setText("All data was deleted");
        }
    }
}
