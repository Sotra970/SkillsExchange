package com.example.joudialfattal.skillsexchange;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class FindSkill extends AppCompatActivity {


    SearchView searchView;
    ListView listView;
    ImageView noData, noNetwork;
    String urlAdress;
    View progressView ;
    EditText keyword_input ;
    View search_view , search_close , search_done , search_open  , search_view_content;



    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar toolbar;
    private NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_skill);
        progressView = findViewById(R.id.progressBar);
        listView = (ListView) findViewById(R.id.searchList);
        noData = (ImageView) findViewById(R.id.nodata);
        noNetwork = (ImageView) findViewById(R.id.nonetwork);
        urlAdress = "http://skillsexchangecyprus.com/SEC/ss.php";
        search_open = findViewById(R.id.main_toolbar_search);
        search_close = findViewById(R.id.search_close);
        search_view = findViewById(R.id.search_view);
        search_done = findViewById(R.id.search_done);
        search_view_content = findViewById(R.id.search_view_content);
        keyword_input = (EditText) findViewById(R.id.keyword_input);

        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setUpDrawerToggel();
        setupNavigtionDrawer();
        SenderReceiver sr = new SenderReceiver(FindSkill.this, urlAdress, listView, progressView, "", noData, noNetwork);
        sr.execute("");

        search_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search_view.clearAnimation();
                search_view.clearFocus();
                search_view.setVisibility(View.VISIBLE);
                search_view_content.setAlpha(0);
                ScaleAnimation scaleAnimation = new ScaleAnimation(0f,1f,1f,1f, Animation.RELATIVE_TO_SELF,1.0f, Animation.RELATIVE_TO_SELF, 0.5f);
                scaleAnimation.setDuration(500);
                scaleAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
                search_view.startAnimation(scaleAnimation);
                search_view_content.animate().alpha(1).setStartDelay(400).setDuration(400).setInterpolator(new FastOutSlowInInterpolator()).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationCancel(Animator animation) {
                        search_view.setScaleX(1);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        search_view.setScaleX(1);
                    }
                });
            }
        });



        search_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                keyword_input.setText("");
                keyword_input.setError(null);

                        search_view_content.animate().alpha(0).setDuration(400).setInterpolator(new FastOutSlowInInterpolator());


                final ScaleAnimation scaleAnimation = new ScaleAnimation(1f,0.000001f,1f,1f, Animation.RELATIVE_TO_SELF,1.0f, Animation.RELATIVE_TO_SELF, 0.5f);
                scaleAnimation.setDuration(400);
                scaleAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
                scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        search_view.setVisibility(View.GONE);
                        search_view.clearAnimation();
                        search_view_content.setAlpha(0);
                        search_view.setScaleX(0.0000001f);

                        SenderReceiver sr = new SenderReceiver(FindSkill.this, urlAdress, listView, progressView, "", noData, noNetwork);
                        sr.execute("");


                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                search_view.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        search_view.startAnimation(scaleAnimation);
                    }
                },400);

            }


        });


        search_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (keyword_input.getText().toString().trim().isEmpty())
                    keyword_input.setError("please enter a keyword");
                else {
                    keyword_input.setError(null);

                    SenderReceiver sr = new SenderReceiver(FindSkill.this, urlAdress, listView, progressView, keyword_input.getText().toString(), noData, noNetwork);
                    sr.execute("");
                }
            }
        });




    }


    private void setupNavigtionDrawer() {
//        replaceFragment(new CategoriesFragment());
        setUpDrawerToggel();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                item.setCheckable(true);
                mDrawerLayout.closeDrawers();

                Intent intent;
                    switch (item.getItemId()) {
                        case R.id.aboutus:
                            if (item.isChecked())
                                item.setChecked(false);
                            else
                                item.setChecked(true);
                            intent = new Intent(FindSkill.this, AboutUs.class);
                            startActivity(intent);
                            return true;
                        case R.id.findskill:
                            if (item.isChecked())
                                item.setChecked(false);
                            else
                                item.setChecked(true);
                            return true;
                        case R.id.contactus:
                            if (item.isChecked())
                                item.setChecked(false);
                            else
                                item.setChecked(true);
                            intent = new Intent(FindSkill.this, ContactUs.class);
                            startActivity(intent);
                            return true;
                        case R.id.logout:
                            if (item.isChecked())
                                item.setChecked(false);
                            else
                                item.setChecked(true);
                            AlertDialog.Builder builder =new AlertDialog.Builder(FindSkill.this);
                            builder.setTitle("Logout Confirmation");
                            builder.setMessage("Sure you want to logout?").setCancelable(false);
                            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // what to do if YES is tapped
                                  new MyPreferenceManager(getApplicationContext()).clear();
                                }})
                                    .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }});
                            builder.show();

                }


                return true;
            }
        });

    }



    private void setUpDrawerToggel() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                toolbar, R.string.drawer_open, R.string.drawer_close){

        } ;

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.addDrawerListener(mDrawerToggle);

    }
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }
}










