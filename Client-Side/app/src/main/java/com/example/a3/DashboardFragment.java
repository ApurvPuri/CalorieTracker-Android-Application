package com.example.a3;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.Calendar;
import java.util.Date;

public class DashboardFragment extends Fragment implements View.OnClickListener {
    private View vDashboard;
    private Button btnCalGoal;
    private TextView tvCalFeedback, tvTime, tvDisplayName;
    private String userName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) { // Set Variables and listeners
        vDashboard = inflater.inflate(R.layout.fragment_dashboard, container, false);
        btnCalGoal = (Button) vDashboard.findViewById(R.id.addCalGoal);
        tvDisplayName = (TextView) vDashboard.findViewById(R.id.displayName);
        Date currentTime = Calendar.getInstance().getTime();
        String strTime = currentTime.toString();
        tvTime = (TextView) vDashboard.findViewById(R.id.timeView);
        tvTime.setText(strTime);
        userName = ((MainActivity) this.getActivity()).getUserName();
        UserAsyncTask displayName = new UserAsyncTask();
        displayName.execute();
        btnCalGoal.setOnClickListener(this);
        return vDashboard;
    }

    @Override
    public void onClick(View v) {
        EditText editTextCalGoal = (EditText) vDashboard.findViewById(R.id.calGoal);
        tvCalFeedback = (TextView) vDashboard.findViewById(R.id.calFeedback);
        String builderCalGoal = new StringBuilder(editTextCalGoal.getText()).toString();
        int calorieGoal = Integer.parseInt(builderCalGoal);
        if (calorieGoal <= 0)
            editTextCalGoal.setError("Please enter valid goal..!!");
        else {
            ((MainActivity) this.getActivity()).setCalorieGoal(calorieGoal);
            tvCalFeedback.setText("Today's goal: " + calorieGoal);
        }
    }



    private class UserAsyncTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            String userId = restClient.findUserId(userName);
            String result = restClient.findFirstName(userId);
            ((MainActivity) getActivity()).setTheUserId(Integer.valueOf(userId));
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            tvDisplayName = (TextView) vDashboard.findViewById(R.id.displayName);
            String finalMessage = "Welcome " + result + " Hope you are doing good today!!";
            tvDisplayName.setText(finalMessage);

        }
    }
}