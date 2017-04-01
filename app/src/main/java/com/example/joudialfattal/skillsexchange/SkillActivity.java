package com.example.joudialfattal.skillsexchange;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.joudialfattal.skillsexchange.Beans.Skill;

public class SkillActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skill);
        TextView id = (TextView) findViewById(R.id.skillId);
        TextView title = (TextView) findViewById(R.id.skillTitle);
        //TextView content = (TextView) findViewById(R.id.skillContent);
        TextView date= (TextView) findViewById(R.id.skillDate);

        Intent fromList = getIntent();
        Skill skill = (Skill) fromList.getSerializableExtra("skill");
        id.setText("ID: "+skill.getId());
        title.setText("Title: " + skill.getTitle());
        //content.setText("description: " + skill.getContent());
        date.setText("Date: " + skill.getDate());
    }
}
