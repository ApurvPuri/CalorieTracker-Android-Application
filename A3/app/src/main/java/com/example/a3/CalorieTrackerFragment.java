package com.example.a3;

import android.app.AlarmManager;
import android.app.Fragment;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.mapbox.mapboxsdk.Mapbox.getApplicationContext;

public class CalorieTrackerFragment extends Fragment {
    private View vCalorieTracker;
    private Button bSubmitReportService;
    private TextView tvGoal, tvStepsTaken, tvCalConsumed, tvCalBurned, tvSubmitFeedback;
    private int calGoal;
    private int totalSteps;
    private int userid;
    private int totalCalConsumed, totalCalBurned;
    private AlarmManager alarmMgr;
    private Intent alarmIntent;
    private PendingIntent pendingIntent;
    private StepsDatabase db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) { // Set Variables and listeners
        vCalorieTracker = inflater.inflate(R.layout.fragment_calorie_tracker, container, false);
        bSubmitReportService = (Button) vCalorieTracker.findViewById(R.id.bSubmitReportService);
        tvGoal = (TextView) vCalorieTracker.findViewById(R.id.tvGoal);
        tvStepsTaken = (TextView) vCalorieTracker.findViewById(R.id.tvStepsTaken);
        tvCalConsumed = (TextView) vCalorieTracker.findViewById(R.id.tvCalConsumed);
        tvCalBurned = (TextView) vCalorieTracker.findViewById(R.id.tvCalBurned);
        tvSubmitFeedback = (TextView) vCalorieTracker.findViewById(R.id.tvSubmitFeedback);
        db = ((MainActivity) getActivity()).getDb();
        final Calendar myCalendar = Calendar.getInstance();

        Date consumptionDate = myCalendar.getTime();

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String sConsumtionDate = simpleDateFormat.format(consumptionDate);
        userid = ((MainActivity) getActivity()).getTheUserId();
        PostAsyncTaskCalConsumed postAsyncTaskCalConsumed = new PostAsyncTaskCalConsumed();
        postAsyncTaskCalConsumed.execute(Integer.toString(userid), sConsumtionDate);

        PostAsyncTaskCalBurned postAsyncTaskCalBurned = new PostAsyncTaskCalBurned();
        postAsyncTaskCalBurned.execute(Integer.toString(userid));


        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 0);

        alarmMgr = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        alarmIntent = new Intent(getActivity(), ScheduledIntentService.class);
        pendingIntent = PendingIntent.getService(getActivity(), 0, alarmIntent, 0);
        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

        bSubmitReportService.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getActivity().startService(alarmIntent);



            }
        });


        return vCalorieTracker;
    }


    private class PostAsyncTaskCalConsumed extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {


            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date consumptionDate = null;
            try {
                consumptionDate = sdf.parse(params[1]);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            return restClient.findCalConsumed(Integer.valueOf(params[0]), params[1]);
        }

        @Override
        protected void onPostExecute(String response) {

            ((MainActivity) getActivity()).setTotalCalConsumed(Integer.valueOf(response));
            calGoal = ((MainActivity) getActivity()).getCalorieGoal();
            totalSteps = ((MainActivity) getActivity()).getTotalSteps();

            tvGoal.setText("Your calorie goal for today: " + Integer.toString(calGoal));
            tvStepsTaken.setText("Total steps taken today: " + Integer.toString(totalSteps));

            tvCalConsumed.setText("Your total calorie consumption for today: " + response);

            totalCalConsumed = Integer.valueOf(response);
        }
    }

    private class PostAsyncTaskCalBurned extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {

            int calBurnedAtRest = Integer.valueOf(restClient.findCalBurnedAtRest(Integer.valueOf(params[0])));
            String sCalBurnedPerStep = restClient.findCalBurnedPerStep(Integer.valueOf(params[0]));
            double calBurnedPerStep = Double.valueOf(sCalBurnedPerStep);

            Double totalBurned = calBurnedAtRest + (calBurnedPerStep * totalSteps);

            return Integer.toString(totalBurned.intValue());


        }

        @Override
        protected void onPostExecute(String response) {

            ((MainActivity) getActivity()).setTotalCalBurned(Integer.valueOf(response));

            tvCalBurned.setText("Your total calories burned today: " + response);
            totalCalBurned = Integer.valueOf(response);
        }
    }




}