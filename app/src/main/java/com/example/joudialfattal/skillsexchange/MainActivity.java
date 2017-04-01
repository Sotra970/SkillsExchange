package com.example.joudialfattal.skillsexchange;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.joudialfattal.skillsexchange.Beans.UserModel;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

public class MainActivity extends AppCompatActivity {

    String url;
    EditText emailText, passText;
     Button loginbtn  ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final TextView registerText = (TextView) findViewById(R.id.registertext);
        loginbtn = (Button) findViewById(R.id.login);


        registerText.setOnClickListener(
                new TextView.OnClickListener() {
                    public void onClick(View view){
                        Intent intent = new Intent(MainActivity.this, Register.class);
                        startActivity(intent);
                    }
                }
            );

        loginbtn.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View view) {
                        loginbtn.setClickable(false);

                        //check connection
                        ConnectivityManager cm = (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
                        NetworkInfo netInfo = cm.getNetworkInfo(0);
                        url = "http://skillsexchangecyprus.com/SEC/SkillsLogin.php";

                        //Check fields
                        LinearLayout relativeLayout = (LinearLayout) findViewById(R.id.activity_main);

                        final TextView alert = (TextView)findViewById(R.id.alert);
                        emailText = (EditText) findViewById(R.id.email);
                        passText = (EditText) findViewById(R.id.password);

                        if (emailText.getText().toString().trim().isEmpty() || passText.getText().toString().trim().isEmpty()) {
                            Snackbar snackbar = Snackbar.make(relativeLayout, "Fill In Empty Fields", Snackbar.LENGTH_LONG);
                            snackbar.show();
                            loginbtn.setClickable(true);
                        } else {
                            new BackgroundTasks(alert).execute(url);
                        }
                    }});}


                class BackgroundTasks extends AsyncTask <String, Void, String> {
                    TextView alert;

                    public BackgroundTasks(TextView textview) {
                        this.alert = textview;
                    }

                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        alert.setVisibility(View.VISIBLE);
                        alert.setText("Loading...");

                    }
                    String emtxt= emailText.getText().toString();
                    String passtxt= passText.getText().toString();

                    @Override
                    protected String doInBackground(String... strings) {
                        String result = "";

                        try {
                            URL url = new URL(strings[0]);
                            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                            StrictMode.setThreadPolicy(policy);

                            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                            urlConnection.setDoOutput(true);

                            ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();

                            final EditText emailText = (EditText) findViewById(R.id.email);
                            final EditText passText = (EditText) findViewById(R.id.password);



                            params.add(new BasicNameValuePair("email", emtxt));
                            params.add(new BasicNameValuePair("password", passtxt));

                            OutputStream os = urlConnection.getOutputStream();
                            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                            writer.write(getQuery(params));
                            writer.flush();
                            writer.close();
                            os.close();

                            InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
                            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                            StringBuilder builder = new StringBuilder();

                            String inputString;
                            while ((inputString = bufferedReader.readLine()) != null) {
                                builder.append(inputString);
                            }
                            result = String.valueOf(builder.toString());
                            urlConnection.disconnect();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return result;
                    }

                    @NonNull
                    private String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException {
                        StringBuilder out = new StringBuilder();
                        boolean first = true;

                        for (NameValuePair pair : params) {
                            if (first)
                                first = false;
                            else
                                out.append("&");

                            out.append(URLEncoder.encode(pair.getName(), "UTF-8"));
                            out.append("=");
                            out.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
                        }
                        return out.toString();
                    }

                    @Override
                    protected void onPostExecute(String temp) {

                        if (temp.trim().matches("Empty")) {
                            alert.setText("Invalid username or password");
                        } else {
                            alert.setVisibility(View.GONE);
                            Log.e("user_login" , temp );
                            try {
                                JSONObject jsonArray = new JSONObject(temp) ;
                                UserModel userModel = new UserModel();
                                userModel.setId(jsonArray.getString("ID"));
                                userModel.setName(jsonArray.getString("user_login"));
                                new MyPreferenceManager(getApplicationContext()).storeUser(userModel);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            Intent intent = new Intent(MainActivity.this, FindSkill.class);
                            startActivity(intent);
                            MainActivity.this.finish();

                            loginbtn.setClickable(true);

                        }
                    }
                }




}

