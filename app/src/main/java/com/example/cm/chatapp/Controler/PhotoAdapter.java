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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cm.chatapp.Fragments.Share;
import com.example.cm.chatapp.Model.Data;
import com.example.cm.chatapp.Model.PhotoData;
import com.example.cm.chatapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by cm on 23/01/2018.
 */

public class PhotoAdapter extends ArrayAdapter {

    Context context ;
    int resource;
    ArrayList<PhotoData> arrayList;

    public PhotoAdapter(@NonNull Context context, int resource, @NonNull ArrayList<PhotoData> arrayList) {

        super(context, resource, arrayList);
        this.context = context;
        this.resource = resource;
        this.arrayList = arrayList;
    }



    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource,parent,false);
        TextView name  = convertView.findViewById(R.id.images_xml_textview_name);
        TextView des = convertView.findViewById(R.id.images_xml_textview_describtion);
        ImageView imageView = convertView.findViewById(R.id.images_xml_imagevies_img);
        name.setText(arrayList.get(position).getName());
        des.setText(arrayList.get(position).getDescribtion());
        Toast.makeText(context, ""+arrayList.get(position).getUri(), Toast.LENGTH_SHORT).show();
        String path = "https://inexpedient-church.000webhostapp.com/images/"+arrayList.get(position).getUri();
        Picasso.with(context).load(path).into(imageView);
        return convertView;
    }

}
