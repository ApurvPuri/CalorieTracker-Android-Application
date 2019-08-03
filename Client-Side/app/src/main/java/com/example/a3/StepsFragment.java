package com.example.a3;

import android.app.Fragment;
import android.arch.persistence.room.Room;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.Calendar;
import java.util.List;

public class StepsFragment extends Fragment{
    private View vSteps;
    private Button bSubmitSteps;
    private TextView tvFeedback;
    StepsDatabase db = null;
    private int totalStepsToBeAddedToServer = 0;
    private Button bEditSteps;
    private Button bShowSteps;
    private EditText etSteps;
    private TextView tvShowSteps;
    private EditText etEditSteps;
    private TextView tvEditFeedback;
    private Button bTotalSteps;
    private TextView tvTotalStepsFeedback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) { // Set Variables and listeners
        vSteps = inflater.inflate(R.layout.fragment_steps, container, false);
        etSteps = (EditText) vSteps.findViewById(R.id.steps);
        bSubmitSteps = (Button) vSteps.findViewById(R.id.submitSteps);
        tvFeedback = (TextView) vSteps.findViewById(R.id.stepsFeedback);
        bShowSteps = (Button) vSteps.findViewById(R.id.showStepsButton);
        tvShowSteps = (TextView) vSteps.findViewById(R.id.showSteps);
        etEditSteps = (EditText) vSteps.findViewById(R.id.editSteps);
        bEditSteps = (Button) vSteps.findViewById(R.id.submitEditSteps);
        tvEditFeedback = (TextView) vSteps.findViewById(R.id.editStepsFeedback);
        bTotalSteps = (Button) vSteps.findViewById(R.id.totalSteps);
        tvTotalStepsFeedback = (TextView) vSteps.findViewById(R.id.totalStepsFeedback);



        db = Room.databaseBuilder(getActivity(), StepsDatabase.class, "StepsDatabase").fallbackToDestructiveMigration().build();

        ((MainActivity) getActivity()).setDb(db);

        TotalSteps totalSteps = new TotalSteps();
        totalSteps.execute();

        bSubmitSteps.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                InsertDatabase addDatabase = new InsertDatabase();
                String steps = etSteps.getText().toString();
                addDatabase.execute(steps);
            }
        });

        bShowSteps.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ReadDatabase showDatabase = new ReadDatabase();
                String steps = etSteps.getText().toString();
                showDatabase.execute();
            }
        });

        bEditSteps.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                UpdateDatabase updateDatabase = new UpdateDatabase();
                String editSteps = etEditSteps.getText().toString();
                updateDatabase.execute(editSteps);
            }
        });

        bTotalSteps.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TotalSteps totalSteps = new TotalSteps();
                totalSteps.execute();
            }
        });


        return vSteps;
    }




    private class InsertDatabase extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            if (!(etSteps.getText().toString().isEmpty())) {
                //String[] details = editText.getText().toString().split(" ");
                String time = Calendar.getInstance().getTime().toString();
                if (params.length == 1) {
                    Steps steps = new Steps(Integer.valueOf(params[0]), time);
                    long id = db.stepsDao().insert(steps);
                    return (id + " " + params[0] + " " + time);
                } else return "";
            } else return "";
        }

        @Override
        protected void onPostExecute(String details) {
            tvFeedback.setText("Added Record: " + details);
        }
    }

    private class ReadDatabase extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            List<Steps> steps = db.stepsDao().getAll();
            if (!(steps.isEmpty() || steps == null)) {
                String allSteps = "";
                for (Steps temp : steps) {
                    String stepsstr = ("\n" + temp.getId() + " - " + temp.getTotalSteps() + " - " + temp.getTime());
                    allSteps = allSteps + stepsstr;
                }
                return allSteps;
            } else return "";
        }

        @Override
        protected void onPostExecute(String details) {
            tvShowSteps.setText(details);
        }
    }

    private class UpdateDatabase extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            Steps steps = null;
            String[] details = etEditSteps.getText().toString().split(" ");
            String time = Calendar.getInstance().getTime().toString();
            if (details.length == 2) {
                try {
                    int id = Integer.parseInt(details[0]);
                    steps = db.stepsDao().findByID(id);
                    steps.setTotalSteps(Integer.parseInt(details[1]));
                    steps.setTime(time);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    return "";
                }
            }
            if (steps != null) {
                db.stepsDao().updateUsers(steps);
                return (details[0] + " " + details[1] + " " + time);
            }
            return "";
        }

        @Override
        protected void onPostExecute(String details) {
            tvEditFeedback.setText("Updated details: " + details);
            if (details.isEmpty())
                tvEditFeedback.setText("Please follow the correct input pattern");
        }
    }

    private class TotalSteps extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            List<Steps> steps = db.stepsDao().getAll();
            if (!(steps.isEmpty() || steps == null)) {
                int totalSteps = 0;
                for (Steps temp : steps) {
                    int step = temp.getTotalSteps();
                    totalSteps = totalSteps + step;
                }
                return Integer.toString(totalSteps);
            } else return "";
        }

        @Override
        protected void onPostExecute(String totalSteps) {
            if ( !totalSteps.isEmpty()) {
                tvTotalStepsFeedback.setText("Total Steps taken today: " + totalSteps);
                totalStepsToBeAddedToServer = Integer.valueOf(totalSteps);
                MainActivity m = (MainActivity) getActivity();
                m.setTotalSteps(totalStepsToBeAddedToServer);
            }
        }
    }



}