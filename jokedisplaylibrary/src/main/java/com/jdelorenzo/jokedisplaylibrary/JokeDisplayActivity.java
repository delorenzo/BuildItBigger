package com.jdelorenzo.jokedisplaylibrary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class JokeDisplayActivity extends AppCompatActivity {
    public static final String ARG_JOKETEXT = "jokeText";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
}
