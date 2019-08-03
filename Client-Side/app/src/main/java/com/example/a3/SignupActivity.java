package com.example.a3;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class SignupActivity extends AppCompatActivity {

    private String firstName, surName, email, address, userName, password, passwordConfirm, builderEmail, builderUserName, dateOfBirth, signUpDate, hashValue;
    private String gender = "Male";
    private Date DoB = new Date();
    private int height, weight, postCode, levelOfActivity, stepspermile;
    private EditText etEmail, etUserName;
    private int userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        final EditText etFirstName = (EditText) findViewById(R.id.firstName);
        final EditText etSurName = (EditText) findViewById(R.id.surName);
        final EditText etEmail = (EditText) findViewById(R.id.email);
        final EditText etHeight = (EditText) findViewById(R.id.height);
        final EditText etWeight = (EditText) findViewById(R.id.weight);
        final EditText etAddress = (EditText) findViewById(R.id.address);
        final EditText etPostCode = (EditText) findViewById(R.id.postCode);
        final EditText etStepsPerMile = (EditText) findViewById(R.id.stepsPerMile);
        EditText etUserName = (EditText) findViewById(R.id.username);
        final EditText etPassword = (EditText) findViewById(R.id.password);
        final EditText etPasswordConfirm = (EditText) findViewById(R.id.passwordConfirm);


        RadioGroup rg = (RadioGroup) findViewById(R.id.radioGender);


        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioMale:
                        gender = "Male";
                        break;
                    case R.id.radioFemale:
                        gender = "Female";
                        break;
                }
            }
        });


        final Calendar myCalendar = Calendar.getInstance();

        final EditText edittext = (EditText) findViewById(R.id.DoB);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "yyyy-MM-dd"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                edittext.setText(sdf.format(myCalendar.getTime()));
                DoB = myCalendar.getTime();
            }

        };


        edittext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(SignupActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        Button btn = (Button) findViewById(R.id.submit);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String builderFirstName = new StringBuilder(etFirstName.getText()).toString();
                if (builderFirstName.isEmpty()) {
                    etFirstName.setError("Mandatory field..!!");
                    return;
                }
                firstName = builderFirstName;
                String builderSurName = new StringBuilder(etSurName.getText()).toString();
                if (builderSurName.isEmpty()) {
                    etSurName.setError("Mandatory field..!!");
                    return;
                }
                surName = builderSurName;
                builderEmail = new StringBuilder(etEmail.getText()).toString();
                if (builderEmail.isEmpty()) {
                    etEmail.setError("Mandatory field..!!");
                    return;
                }
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                if (!builderEmail.matches(emailPattern)) {
                    etEmail.setError("Please enter a valid email address");
                    return;
                }


                String builderHeight = new StringBuilder(etHeight.getText()).toString();
                if (builderHeight.isEmpty()) {
                    etHeight.setError("Mandatory field..!!");
                    return;
                }


                height = Integer.valueOf(builderHeight);
                String builderWeight = new StringBuilder(etWeight.getText()).toString();
                if (builderWeight.isEmpty()) {
                    etWeight.setError("Mandatory field..!!");
                    return;
                }
                weight = Integer.valueOf(builderWeight);
                String builderAddress = new StringBuilder(etAddress.getText()).toString();
                if (builderAddress.isEmpty()) {
                    etAddress.setError("Mandatory field..!!");
                    return;
                }
                address = builderAddress;
                String builderPostCode = new StringBuilder(etPostCode.getText()).toString();
                if (builderPostCode.isEmpty()) {
                    etPostCode.setError("Mandatory field..!!");
                    return;
                }
                postCode = Integer.valueOf(builderPostCode);
                String builderStepsPerMile = new StringBuilder(etStepsPerMile.getText()).toString();
                if (builderStepsPerMile.isEmpty()) {
                    etStepsPerMile.setError("Mandatory field..!!");
                    return;
                }
                stepspermile = Integer.valueOf(builderStepsPerMile);
                builderUserName = new StringBuilder(etUserName.getText()).toString();
                if (builderUserName.isEmpty()) {
                    etUserName.setError("Mandatory field..!!");
                    return;
                }
                userName = builderUserName;
                String builderPassword = new StringBuilder(etPassword.getText()).toString();
                if (builderPassword.isEmpty()) {
                    etPassword.setError("Mandatory field..!!");
                    return;
                }
                String builderPasswordConfirm = new StringBuilder(etPasswordConfirm.getText()).toString();
                if (builderPasswordConfirm.isEmpty()) {
                    etPasswordConfirm.setError("Mandatory field..!!");
                    return;
                }

                if (!builderPassword.equals(builderPasswordConfirm)) {
                    etPasswordConfirm.setError("Please enter same password..!!");
                    return;
                } else {
                    password = builderPassword;
                    // Post logic
                    hashValue = "";
                    try {
                        MessageDigest md = MessageDigest.getInstance("MD5");
                        byte[] messageDigest = md.digest(builderPassword.getBytes());
                        BigInteger no = new BigInteger(1, messageDigest);
                        hashValue = no.toString(16);
                        while (hashValue.length() < 32) {
                            hashValue = "0" + hashValue;
                        }
                    } catch (NoSuchAlgorithmException e) {
                        throw new RuntimeException(e);
                    }

                    //to be removed.
                      /*  SharedPreferences userIdCount = getApplicationContext().getSharedPreferences("user_id", Context.MODE_PRIVATE);
                    SharedPreferences.Editor spEditor = userIdCount.edit();
                    spEditor.putInt("user_id", 5);
                    spEditor.apply();*/

                    SharedPreferences sharedPreferences = getSharedPreferences("user_id", Context.MODE_PRIVATE);
                    userId = sharedPreferences.getInt("user_id", 0);
                    userId++;
                    SharedPreferences.Editor spEditor2 = sharedPreferences.edit();
                    spEditor2.putInt("user_id", userId);
                    spEditor2.apply();

                    Date currentTime = Calendar.getInstance().getTime();

                    String pattern = "yyyy-MM-dd";
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

                    if (DoB.compareTo(currentTime) > 0)
                        edittext.setError("Date of Birth cannot be in future");
                    else {


                        dateOfBirth = simpleDateFormat.format(DoB);
                        signUpDate = simpleDateFormat.format(currentTime);

                        UserNamePostAsyncTask userNamePostAsyncTask = new UserNamePostAsyncTask();
                        userNamePostAsyncTask.execute(builderUserName);


                    }

                }
            }
        });


        List<Integer> list = new ArrayList<Integer>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        //Button addButton = (Button) findViewById(R.id.addButton);
        final Spinner sActivity = (Spinner) findViewById(R.id.levelOfActivitySpinner);
        final ArrayAdapter<Integer> spinnerAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, list);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sActivity.setAdapter(spinnerAdapter);

        sActivity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedActivity = parent.getItemAtPosition(position).toString();
                if (selectedActivity != null) {
                    Toast.makeText(parent.getContext(), " Level of activity selected is" + selectedActivity, Toast.LENGTH_LONG).show();
                    levelOfActivity = Integer.parseInt(selectedActivity);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


    }

    private class PostAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dateOfBirth = null;
            Date signupdate = null;
            try {
                dateOfBirth = sdf.parse(params[4]);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            try {
                signupdate = sdf.parse(params[12]);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            NewUser newUser = new NewUser(Integer.valueOf(params[0]), params[1], params[2], params[3], dateOfBirth, Integer.valueOf(params[5]),
                    Integer.valueOf(params[6]), params[7], params[8], Integer.valueOf(params[9]), Integer.valueOf(params[10]), Integer.valueOf(params[14]));

            NewCredentials newCredentials = new NewCredentials(Integer.valueOf(params[0]), params[11], signupdate, params[13]);
            restClient.createNewUser(newUser);
            restClient.createNewCredential(newCredentials);
            return "Sign up successful";
        }

        @Override
        protected void onPostExecute(String response) {
            TextView tvFeedback = (TextView) findViewById(R.id.feedback);
            tvFeedback.setText(response);

            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }

    private class EmailPostAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {

            String status = restClient.getEmailStatus(params[0]);
            return status;
        }

        @Override
        protected void onPostExecute(String response) {

            etEmail = (EditText) findViewById(R.id.email);

            if (response.equalsIgnoreCase("Email Found")) {
                etEmail.setError("Email already taken!");
                return;
            } else {
                email = builderEmail;

                PostAsyncTask postAsyncTask = new PostAsyncTask();
                postAsyncTask.execute(Integer.toString(userId), firstName, surName, email, dateOfBirth, Integer.toString(height),
                        Integer.toString(weight), gender, address, Integer.toString(postCode),
                        Integer.toString(levelOfActivity), userName, signUpDate, hashValue, Integer.toString(stepspermile));
            }

        }
    }

    private class UserNamePostAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {

            String status = restClient.getUserNameStatus(params[0]);
            return status;
        }

        @Override
        protected void onPostExecute(String response) {

            etUserName = (EditText) findViewById(R.id.username);

            if (response.equalsIgnoreCase("User name Found")) {
                etUserName.setError("User name already taken!");
                return;
            } else {
                userName = builderUserName;

                EmailPostAsyncTask emailPostAsyncTask = new EmailPostAsyncTask();
                emailPostAsyncTask.execute(builderEmail);
            }

        }
    }


}
