package com.example.joudialfattal.skillsexchange;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;


public class SenderReceiver extends AsyncTask<String,Void,String>{

    Context ctx;
    String urlAdress;
    String query;
    ListView listView;
    ImageView noData, noNetwork;
    ProgressDialog progressDialog;
    View ProgressView ;
    public SenderReceiver(Context ctx, String urlAdress, ListView listView,View progressView ,  String query, ImageView... imageViews){
        this.ctx=ctx;
        this.urlAdress=urlAdress;
        this.listView=listView;
        this.query=query;
        this.noData=imageViews[0];
        this.noNetwork=imageViews[1];
        this.ProgressView = progressView ;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.d("Yamen","pre");
        progressDialog=new ProgressDialog(ctx);
        progressDialog.setTitle("Search");
        progressDialog.setMessage("Searching... Please Wait!");
        progressDialog.show();
    }



    @Override
    protected String doInBackground(String... params) {
        return this.sendAndReceive();
    }


    @Override
    protected void onPostExecute(String s) {
        ProgressView.setVisibility(View.GONE);
        super.onPostExecute(s);
        Log.d("Yamen", "post : "+ s);
        progressDialog.dismiss();


        // if (s.trim().matches("No Matches Found")){

        listView.setAdapter(null);
/*
        if (s.length() < 0){
            Log.d("Joudi","s = "+s);
            Toast.makeText(ctx, "No Results Found", Toast.LENGTH_LONG).show();
            noData.setVisibility(View.VISIBLE);
        }else{
           Log.d("Yamen","s = "+s);
            Parser parser = new Parser(ctx,s,listView);
            parser.execute();
        }*/
        if (s != null) {
            if (!s.contains("null")) {
                if (s.length() > 0) {

                    Parser parser = new Parser(ctx, s, listView);
                    parser.execute();
                }
            } else {
                noNetwork.setVisibility(View.INVISIBLE);
                noData.setVisibility(View.VISIBLE);

            } //data = "[{\"post_title\":\"title1\"},{\"post_title\":\"title2\"}]";

        } else {
            Log.d("Yamen", "2nd else");
            noNetwork.setVisibility(View.INVISIBLE);
            noData.setVisibility(View.VISIBLE);

        }
    }

    private String sendAndReceive(){

        HttpURLConnection connection = Connector.connection(urlAdress);


        if (connection == null){
            return null;
        }

        try {
            String urlParameters = "post_title="+this.query;
            connection.setDoOutput(true);
            OutputStreamWriter outputStream = new OutputStreamWriter(connection.getOutputStream());
            outputStream.write(urlParameters);
            outputStream.flush();
            BufferedWriter bufferedWriter = new BufferedWriter((outputStream));// write it to the network
            bufferedWriter.write(new DataBackager(query).packageData());
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            //response
            int responseCode = connection.getResponseCode();
            //decode
            if (responseCode == connection.HTTP_OK){

                //return data
                InputStream inputStream = connection.getInputStream();
                //read data
                BufferedReader bufferedReader = new BufferedReader(( new InputStreamReader(inputStream)));
                String line;
                StringBuffer response = new StringBuffer();
                if (bufferedReader != null){
                    while ((line= bufferedReader.readLine()) != null)
                    {
                        response.append(line + "\n");
                    }
                }else {
                    return null;
                }

                return response.toString();

            }else{
                return  String.valueOf(responseCode);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
