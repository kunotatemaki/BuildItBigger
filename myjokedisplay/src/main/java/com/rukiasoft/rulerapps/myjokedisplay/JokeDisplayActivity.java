package com.rukiasoft.rulerapps.myjokedisplay;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class JokeDisplayActivity extends AppCompatActivity {
    public static String JOKE_AS_INTENT = "com.rukiasoft.rulerapps.myjokedisplay.jokedisplayactivity.joke";
    private String jokeToShow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_display);
        Intent intent = getIntent();
        if(intent != null && intent.hasExtra(JOKE_AS_INTENT)){
            jokeToShow = intent.getStringExtra(JOKE_AS_INTENT);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_joke_display, menu);
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

    @Override
    public void onResume(){
        super.onResume();
        if(jokeToShow != null){
            JokeDisplayActivityFragment fragment = (JokeDisplayActivityFragment) getFragmentManager().findFragmentById(R.id.fragment_for_displaying_joke);
            fragment.setJoke_display(jokeToShow);
        }
    }
}
