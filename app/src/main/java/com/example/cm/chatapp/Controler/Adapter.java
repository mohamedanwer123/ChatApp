package com.example.cm.chatapp.Controler;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cm.chatapp.Fragments.Share;
import com.example.cm.chatapp.Fragments.news;
import com.example.cm.chatapp.Model.Data;
import com.example.cm.chatapp.R;

import java.util.ArrayList;

/**
 * Created by cm on 23/01/2018.
 */

public class Adapter extends ArrayAdapter {

    Context context ;
    int resource;
    ArrayList<Data> arrayList;

    public Adapter(@NonNull Context context, int resource, @NonNull ArrayList<Data> arrayList) {

        super(context, resource, arrayList);
        this.context = context;
        this.resource = resource;
        this.arrayList = arrayList;
    }

    Button button;

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource,parent,false);
        TextView username = convertView.findViewById(R.id.posts_xml_textview_name);
        TextView post = convertView.findViewById(R.id.posts_xml_textview_post);

        username.setText(arrayList.get(position).getUser_name());
        post.setText(arrayList.get(position).getPost());
        final Button button = convertView.findViewById(R.id.posts_xml_button_share);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                button.setTextColor(Color.parseColor("#bf0000"));

                String data = arrayList.get(position).getPost();
                Fragment fragment = new Share();
                Bundle bundle = new Bundle();
                bundle.putString("post",data);
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = ((Activity) context).getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frame,fragment);
                fragmentTransaction.commit();
        }
        });
        return convertView;
    }

    public Button btn()
    {
        return button;
    }
}
