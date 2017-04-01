package com.example.joudialfattal.skillsexchange;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

public class AboutUs extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_us);
        View close = findViewById(R.id.close) ;
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                supportFinishAfterTransition();
            }
        });
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
                return true;
            case R.id.findskill:
                if (item.isChecked())
                    item.setChecked(false);
                else
                    item.setChecked(true);
                intent = new Intent(AboutUs.this, FindSkill.class);
                startActivity(intent);
                return true;
            case R.id.contactus:
                if (item.isChecked())
                    item.setChecked(false);
                else
                    item.setChecked(true);
                intent = new Intent(AboutUs.this, ContactUs.class);
                startActivity(intent);
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