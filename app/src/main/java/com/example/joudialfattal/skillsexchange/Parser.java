package com.example.joudialfattal.skillsexchange;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.joudialfattal.skillsexchange.Beans.Skill;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;


public class Parser extends AsyncTask <Void, Void,Integer> {

    Context ctx;
    ListView listView;
    String data;
    ArrayList<Skill> skills = new ArrayList<>();
    adapter_items adapter;
    ArrayList<String> titles = new ArrayList<>();

    public Parser(Context ctx, String data, ListView listView) {
        this.ctx = ctx;
        this.data=data;
        this.listView = listView;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Integer doInBackground(Void... params) {
        return this.parse();
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        if (integer == 1) {
           adapter = new adapter_items(skills , ctx) ;

//          CustomAdapter mAdapter = new CustomAdapter(ctx,skills);
            adapter.notifyDataSetChanged();
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    Intent toSkillActivity = new Intent(ctx,SkillActivity.class);
//                    toSkillActivity.putExtra("skill",skills.get(position));
//                    ctx.startActivity(toSkillActivity);
                }});
        } else {
            Toast.makeText(ctx, "Unable to Parse", Toast.LENGTH_LONG).show();
        }
    }


    private int parse() {
        try {
            Log.d("Jou","data");
            //JSONArray ja = new JSONArray(data);
            //JSONObject jo = null;
            JSONObject jObject = new JSONObject(data);
            JSONArray ja = jObject.getJSONArray("result");
            titles.clear();
            skills.clear();
            for (int i = 0; i < ja.length(); i++) {
                        JSONObject jo = ja.getJSONObject(i);
                        //jo = ja.getJSONObject(i);
                        String id = jo.getString("ID");
                        String title = jo.getString("post_title");
                        String content = jo.getString("post_content");




                String substr = "src=";
                String substr2 = "width";
                String after = content.substring(content.indexOf(substr) + substr.length());
                Log.e("parse" , "after  :"+ after );
                String img = ""  ;
                try{
                    String before = after.substring(0, after.indexOf(substr2));
//
                    before = before.substring(1);
                    before = before.substring(0,(before.length()-2));
                    Log.e("parseb" , "before  :"+ before );
                    img=before ;

                }catch (Exception e){

                }






                String date = jo.getString("post_date");
                        Skill skill = new Skill();
                        skill.setTitle(title);
                        skill.setId(id);
                        skill.setImg(img);
                        //skill.setContent(content);
                        skill.setDate(date);
                        skills.add(skill);
                        titles.add(title);
                    }
            return 1;
        } catch (JSONException e) {
            Log.d("Jou", e.getMessage());
            return 0;
        }
    }
//    private class CustomAdapter extends BaseAdapter{
//        private ArrayList<Skill> skills;
//        private Context context;
//        public CustomAdapter(Context context,ArrayList<Skill> skills){
//            this.skills = skills;
//            this.context=context;
//        }
//        @Override
//        public int getCount() {
//            return skills.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return position;
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return position;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            View v = inflater.inflate(R.layout.skill_custom_row, null);
//            TextView skillTitle = (TextView) v.findViewById(R.id.skillTitle);
//            skillTitle.setText(skills.get(position).getTitle());
//            Log.d("Yamen","text = "+skills.get(position).getTitle())
//            return null;
//        }
//    }


    class  adapter_items extends BaseAdapter {
        ArrayList<Skill> skills ;

        Context context ;
        LayoutInflater layoutInflater ;

        public adapter_items(ArrayList<Skill> skills, Context context) {
            this.skills = skills;
            this.context = context;
            layoutInflater = LayoutInflater.from(context) ;
        }

        @Override
        public int getCount() {
            return skills.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            Skill current = skills.get(i) ;
            if (view == null) {
                view = layoutInflater.inflate(R.layout.list_item0, viewGroup, false);
                TextView title = (TextView) view.findViewById(R.id.text1);
                title.setText(current.getTitle());
                TextView date = (TextView) view.findViewById(R.id.text1_id);
                date.setText(current.getDate());
                ImageView cover = (ImageView) view.findViewById(R.id.cover);

                Glide.with(context)
                        .load( current.getImg())
                        .centerCrop()
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(cover);

            }
            return view;
        }
    }
}