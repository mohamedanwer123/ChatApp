package com.example.cm.chatapp.Fragments;


import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.example.cm.chatapp.Controler.Listener;
import com.example.cm.chatapp.Model.Code;
import com.example.cm.chatapp.Model.Data;
import com.example.cm.chatapp.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class news extends Fragment {

    EditText post;
    FloatingActionButton floatingActionButton;
    ListView listView;
   public static String username;
    Adapter adapter;
    ArrayList<Data> arrayList;
    Listener listener;
    View v,a;

    public news() {
        // Required empty public constructor
    }

    public void read()
    {
        arrayList = listener.READ_POSTS();
        post.setText("");
        adapter = new com.example.cm.chatapp.Controler.Adapter(getActivity(),R.layout.posts,arrayList);
        listView.setAdapter((ListAdapter) adapter);
    }

    public void init()
    {
        post = v. findViewById(R.id.newsFragment_edittext);
        floatingActionButton = v.findViewById(R.id.newsFragment_button);
        listView = v.findViewById(R.id.newsFragment_listview);
        listener = new Listener(getActivity());
        username = getArguments().getString("username");
        arrayList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        AndroidNetworking.initialize(getActivity());
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        v = view;
        init();
        read();
        font();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = post.getText().toString();
                if(data.length()!=0)
                {
                    arrayList.clear();
                    listener.WRITE_POST(data,username );
                    arrayList = listener.READ_POSTS();
                    //arrayList.add(new Data(username,data,new Code().hashCode()));
                    post.setText("");
                    adapter = new com.example.cm.chatapp.Controler.Adapter(getActivity(),R.layout.posts,arrayList);
                    listView.setAdapter((ListAdapter) adapter);
                }


            }
        });



        return v;
    }

    public void font()
    {
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(),"font.ttf");
        post.setTypeface(typeface);
    }

}
