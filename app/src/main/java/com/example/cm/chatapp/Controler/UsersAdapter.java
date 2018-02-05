package com.example.cm.chatapp.Controler;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.cm.chatapp.Model.Users;
import com.example.cm.chatapp.R;

import java.util.ArrayList;

/**
 * Created by cm on 27/01/2018.
 */

public class UsersAdapter extends ArrayAdapter {

    Context context;
    int resource;
    ArrayList<Users> arrayList;

    public UsersAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Users> objects) {
        super(context, resource, objects);

        this.context = context;
        this.resource = resource;
        this.arrayList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        convertView = LayoutInflater.from(context).inflate(resource,parent,false);

        TextView textView = convertView.findViewById(R.id.users_xml_username);
        textView.setText(arrayList.get(position).getUsername());

        return convertView;
    }
}
