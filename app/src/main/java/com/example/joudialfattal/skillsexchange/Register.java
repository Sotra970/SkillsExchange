package com.example.joudialfattal.skillsexchange;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import android.os.Handler;
import java.util.logging.LogRecord;


public class Register extends AppCompatActivity {

    String url;
    EditText name,email,pass;
    Button registerBtn;
    TextView alert;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        registerBtn = (Button) findViewById(R.id.register);
        registerBtn.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View view) {

                        ConnectivityManager cm = (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
                        NetworkInfo netInfo = cm.getNetworkInfo(0);
                        url="http://skillsexchangecyprus.com/SEC/SkillsRegister.php";

                        LinearLayout relativeLayout = (LinearLayout) findViewById(R.id.regLayout);
                        name = (EditText) findViewById(R.id.namereg);
                        email = (EditText) findViewById(R.id.emailreg);
                        pass = (EditText) findViewById(R.id.passwordreg);
                        alert = (TextView)findViewById(R.id.alert);

                        if (name.getText().toString().matches("")||email.getText().toString().matches("")||pass.getText().toString().matches("")) {
                            Snackbar snackbar = Snackbar
                                    .make(relativeLayout, "Fill in Empty Fields", Snackbar.LENGTH_LONG);
                            snackbar.show();
                        } else {
                            new BackgroundTasks(alert).execute(url);
                        }
                        }});}

                    class BackgroundTasks extends AsyncTask<String, Void, String> {
                        TextView alert;

                        public BackgroundTasks(TextView textview) {
                            this.alert = textview;
                        }

                        @Override
                        protected void onPreExecute() {
                            super.onPreExecute();
                            alert.setVisibility(View.VISIBLE);
                            alert.setText("Saving....");
                        }

                        String nametxt = name.getText().toString();
                        String emtxt = email.getText().toString();
                        String passtxt = pass.getText().toString();

                        @Override
                        protected String doInBackground(String... strings) {
                            String task = "";
                            try {
                                URL url = new URL(strings[0]);
                                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                                StrictMode.setThreadPolicy(policy);

                                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                                urlConnection.setDoOutput(true);

                                ArrayList<NameValuePair> params = new ArrayList<>();
                                params.add(new BasicNameValuePair("nameReg", nametxt));
                                params.add(new BasicNameValuePair("emailReg", emtxt));
                                params.add(new BasicNameValuePair("passwordReg", passtxt));

                                OutputStream os = urlConnection.getOutputStream();
                                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                                writer.write(getQuery(params));
                                writer.flush();
                                writer.close();
                                os.close();
                                InputStream stream = new BufferedInputStream(urlConnection.getInputStream());
                                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
                                StringBuilder builder = new StringBuilder();

                                String inputString;
                                while ((inputString = bufferedReader.readLine()) != null) {
                                    builder.append(inputString);
                                }

                                task = String.valueOf(builder.toString());

                                urlConnection.disconnect();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            return task;
                        }

                        @NonNull
                        private String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException {
                            StringBuilder result = new StringBuilder();
                            boolean first = true;

                            for (NameValuePair pair : params) {
                                if (first)
                                    first = false;
                                else
                                    result.append("&");

                                result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
                                result.append("=");
                                result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
                            }

                            return result.toString();
                        }


                        @Override
                        protected void onPostExecute(final String temp) {

                            if (temp.trim().matches("Success")) {
                                                alert.setText("Successfully saved");
                                            } else {
                                                alert.setVisibility(View.VISIBLE);
                                                alert.setText("This email already exists");
                                            }

                            int finishTime = 5;
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                public void run() {
                                    Register.this.finish();
                                }
                            }, finishTime * 1000);

                                        }
                                    }

                                }








