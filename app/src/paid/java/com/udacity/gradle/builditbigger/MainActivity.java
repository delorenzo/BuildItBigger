package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.jdelorenzo.JokeTeller;
import com.jdelorenzo.jokedisplaylibrary.JokeDisplayActivity;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements OnJokeLoaded {

    @Bind (R.id.progress_bar) android.support.v4.widget.ContentLoadingProgressBar loadingProgressBar;
    Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view){
        JokeTeller jokeTeller = new JokeTeller();
        loadingProgressBar.show();
        new RetrieveJokeTask(this).execute(getString(R.string.deployed_endpoint_address));
    }

    public void onJokeLoaded(String jokeText) {
        loadingProgressBar.hide();
        if (jokeText.equals("")) {
            if (mToast != null) {
                mToast.cancel();
            }
            mToast = Toast.makeText(this, getString(R.string.joke_retrieval_failure_msg), Toast.LENGTH_SHORT);
            mToast.show();
        }
        Intent intent = new Intent(this, JokeDisplayActivity.class);
        intent.putExtra(JokeDisplayActivity.ARG_JOKETEXT, jokeText);
        startActivity(intent);
    }
}