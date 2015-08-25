package com.rukiasoft.rulerapps.myjokedisplay;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A placeholder fragment containing a simple view.
 */
public class JokeDisplayActivityFragment extends Fragment {

    private TextView joke_display;

    public JokeDisplayActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_joke_display, container, false);
        joke_display = (TextView)view.findViewById(R.id.joke_display);
        return view;
    }

    public void setJoke_display(String joke){
        if(joke_display != null){
            joke_display.setText(joke);
        }
    }
}
