package com.udacity.gradle.builditbigger;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    protected SwipeRefreshLayout refreshLayout;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        refreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.swipe_container);
        ((MainActivity)getActivity()).setRefreshLayout(refreshLayout);
        ((MainActivity)getActivity()).disableRefreshLayoutSwipe();

        return root;
    }
}
