package com.example.joudialfattal.skillsexchange;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
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


public class ContactUs extends AppCompatActivity {

    EditText email,message;
    View send;
    String url;
    TextView alert;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_us);
        View close = findViewById(R.id.close) ;
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                supportFinishAfterTransition();
            }
        });
        send =  findViewById(R.id.sendbtn);
        send.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View view) {

                        ConnectivityManager cm = (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
                        NetworkInfo netInfo = cm.getNetworkInfo(0);
                        url="http://skillsexchangecyprus.com/SEC/contact.php";

                        LinearLayout relativeLayout = (LinearLayout) findViewById(R.id.contactLayout);
                        email = (EditText) findViewById(R.id.youremail);
                        message = (EditText) findViewById(R.id.yourmessage);
                        alert = (TextView)findViewById(R.id.alert);

                        if (email.getText().toString().matches("")||message.getText().toString().matches("")) {
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
                            alert.setText("Saving...");
                        }

                        String emailtxt = email.getText().toString();
                        String msgtxt = message.getText().toString();

                        @Override
                        protected String doInBackground(String... strings) {
                            String task = "";
                            try {
                                URL url = new URL(strings[0]);
                                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                                StrictMode.setThreadPolicy(policy);

                                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                                urlConnection.setDoOutput(true);

                                ArrayList<NameValuePair> info = new ArrayList<>();
                                info.add(new BasicNameValuePair("userEmail", emailtxt));
                                info.add(new BasicNameValuePair("userMsg", msgtxt));

                                OutputStream os = urlConnection.getOutputStream();
                                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                                writer.write(getQuery(info));
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
                        private String getQuery(List<NameValuePair> info) throws UnsupportedEncodingException {
                            StringBuilder result = new StringBuilder();
                            boolean first = true;

                            for (NameValuePair pair : info) {
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
                        protected void onPostExecute(final String res) {

                            if (res.trim().matches("Success")) {
                                alert.setText("Successfully sent :)");
                                //clearing edittexts
                                email.setText("");
                                message.setText("");
                            } else {
                                alert.setVisibility(View.VISIBLE);
                                alert.setText("Failed to send the message!");
                            }
                        }
                    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.aboutus:
                if (item.isChecked())
                    item.setChecked(false);
                else
                    item.setChecked(true);
                    intent = new Intent(ContactUs.this, AboutUs.class);
                    startActivity(intent);
                return true;
            case R.id.findskill:
                if (item.isChecked())
                    item.setChecked(false);
                else
                    item.setChecked(true);
                    intent = new Intent(ContactUs.this, FindSkill.class);
                    startActivity(intent);
                return true;
            case R.id.contactus:
                if (item.isChecked())
                    item.setChecked(false);
                else
                    item.setChecked(true);
                return true;
            case R.id.logout:
                if (item.isChecked())
                    item.setChecked(false);
                else
                    item.setChecked(true);
                AlertDialog.Builder builder =new AlertDialog.Builder(this);
                builder.setTitle("Logout Confirmation");
                builder.setMessage("Sure you want to logout?").setCancelable(false);
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // what to do if YES is tapped
                        finishAffinity();
                        System.exit(0);
                    }})
                        .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }});
                builder.show();

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}


