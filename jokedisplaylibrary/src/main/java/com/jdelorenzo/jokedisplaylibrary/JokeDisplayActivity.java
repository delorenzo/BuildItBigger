package com.jdelorenzo.jokedisplaylibrary;

import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class JokeDisplayActivity extends AppCompatActivity {
    public static final String ARG_JOKETEXT = "jokeText";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        if (savedInstanceState == null && getIntent().getExtras() != null) {
            String jokeText = getIntent().getExtras().getString(ARG_JOKETEXT, "");
            setContentView(R.layout.activity_joke_display);
            JokeDisplayFragment fragment = JokeDisplayFragment.newInstance(jokeText);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.display_joke_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent upIntent = NavUtils.getParentActivityIntent(this);
                //it's possible another app started this activity, so create a new task when
                //navigating up
                if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                    TaskStackBuilder.create(this)
                            .addNextIntentWithParentStack(upIntent)
                            .startActivities();
                }
                else {
                    NavUtils.navigateUpFromSameTask(this);
                }
        }
        return super.onOptionsItemSelected(item);
    }
}
