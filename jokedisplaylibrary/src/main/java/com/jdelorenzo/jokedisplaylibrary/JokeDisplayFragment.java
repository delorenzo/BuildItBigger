package com.jdelorenzo.jokedisplaylibrary;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link JokeDisplayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JokeDisplayFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_JOKETEXT = "jokeText";
    private String mJokeText;

    public JokeDisplayFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param jokeText The joke text to display.
     * @return A new instance of fragment JokeDisplayFragment.
     */
    public static JokeDisplayFragment newInstance(String jokeText) {
        JokeDisplayFragment fragment = new JokeDisplayFragment();
        Bundle args = new Bundle();
        args.putString(ARG_JOKETEXT, jokeText);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mJokeText = getArguments().getString(ARG_JOKETEXT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_joke_display, container, false);
        TextView jokeTextView = (TextView) rootView.findViewById(R.id.joke_text_view);
        jokeTextView.setText(mJokeText);
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(ARG_JOKETEXT, mJokeText);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            mJokeText = savedInstanceState.getString(ARG_JOKETEXT, "");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
