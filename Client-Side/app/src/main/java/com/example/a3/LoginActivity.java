package com.example.a3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.*;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button btn = (Button) findViewById(R.id.loginButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserAsyncTask getUser = new UserAsyncTask();
                getUser.execute();
            }
        });
        Button btn2 = (Button) findViewById(R.id.signupButton);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }

    private class UserAsyncTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            EditText editTextName = (EditText) findViewById(R.id.loginName);
            String builderName = new StringBuilder(editTextName.getText()).toString();
            EditText editTextPassword = (EditText) findViewById(R.id.loginPassword);
            String builderPassword = new StringBuilder(editTextPassword.getText()).toString();
            String hashValue = "";
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
            String result = restClient.authenticate(builderName, hashValue);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            TextView resultTextView = (TextView) findViewById(R.id.tvResult);
            resultTextView.setText(result);

            if (result.equalsIgnoreCase("logged in")) {
                /*SharedPreferences sharedPreferences = getSharedPreferences("login_flag", Context.MODE_PRIVATE);
                SharedPreferences.Editor spEditor = sharedPreferences.edit();
                spEditor.putString("login_flag", "True");
                spEditor.apply();*/
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                Bundle bundle = new Bundle();
                String nameBuilder = new StringBuilder(((EditText) findViewById(R.id.loginName)).getText()).toString();
                String passwordBuilder = new StringBuilder(((EditText) findViewById(R.id.loginPassword)).getText()).toString();
                User user1 = new User(passwordBuilder, nameBuilder);
                bundle.putParcelable("user1", user1);
                intent.putExtras(bundle);
                startActivity(intent);
            } else {
                resultTextView = (TextView) findViewById(R.id.tvResult);
                resultTextView.setText("Wrong Credentials");
            }

        }
    }


}
