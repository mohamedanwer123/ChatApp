package com.example.cm.chatapp.Fragments;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cm.chatapp.Controler.Listener;
import com.example.cm.chatapp.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Share extends Fragment {

    TextView textView;
    FloatingActionButton share;

    public Share() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_share, container, false);
        textView = view.findViewById(R.id.share_fragment_textview_post);
        final String post = getArguments().getString("post");
        textView.setText(post);
        share = view.findViewById(R.id.share_fragment_button_share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Listener listener = new Listener(getActivity());
                listener.WRITE_POST(post, new news().username);
            }
        });


        return view;
    }

}



