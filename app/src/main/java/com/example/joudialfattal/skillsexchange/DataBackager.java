package com.example.joudialfattal.skillsexchange;


import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Iterator;


public class DataBackager {

    String query;

    public DataBackager(String query) {
        this.query = query;
    }

    public String packageData() {

        JSONObject jsonObject = new JSONObject();
        StringBuffer queryString = new StringBuffer();

        try {
            jsonObject.put("Query", query);
            Boolean firstValue = true;
            Iterator iterator = jsonObject.keys();
            String key = "";
            ArrayList<String> values = new ArrayList<String>();
            while (iterator.hasNext()){
                key = iterator.next().toString();
                values.add(jsonObject.getString(key));
            }



                return queryString.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
