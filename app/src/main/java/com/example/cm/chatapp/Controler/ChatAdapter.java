package com.example.cm.chatapp.Controler;

import android.content.Context;


import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.cm.chatapp.Activites.Login;
import com.example.cm.chatapp.Model.ChatData;
import com.example.cm.chatapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by cm on 28/01/2018.
 */

public class ChatAdapter extends ArrayAdapter {

    Context context;
    int resource;
    ArrayList<ChatData> arrayList;
    public ChatAdapter(@NonNull Context context, int resource, @NonNull ArrayList<ChatData> arrayList) {
        super(context, resource, arrayList);

        this.context = context;
        this.resource = resource;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        convertView = LayoutInflater.from(context).inflate(resource,parent,false);

        TextView message = convertView.findViewById(R.id.chat_component_xml_message);
        ImageView image = convertView.findViewById(R.id.chat_component_xml_image);
        VideoView video = convertView.findViewById(R.id.chat_component_xml_video);

        int msg_flag=1 ,img_flag=1,video_flag=1;

        if(arrayList.get(position).getUsername().equals(Login.clinetname))
        {
            message.setBackgroundResource(R.drawable.sender);


        }else
        {
            message.setBackgroundResource(R.drawable.reciver);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.LEFT;
            params.topMargin = 10;
            params.rightMargin = 40;
            params.bottomMargin = 10;
            params.leftMargin=20;
            message.setLayoutParams(params);
            image.setLayoutParams(params);
           // video.setLayoutParams(params);


        }

        if(arrayList.get(position).getUriimg().equals("") || arrayList.get(position).getUrivideo().equals("")|| arrayList.get(position).getMessage().equals(""))
        {
           if(arrayList.get(position).getUriimg().equals(""))
           {
               image.setVisibility(View.GONE);
                img_flag=-1;
           }

            if(arrayList.get(position).getUrivideo().equals(""))
            {
                video.setVisibility(View.GONE);
                video_flag=-1;

            }

            if(arrayList.get(position).getMessage().equals(""))
            {
                message.setVisibility(View.GONE);
                msg_flag=-1;
            }

            if(img_flag!=-1)
            {
                image.setVisibility(View.VISIBLE);
                Picasso.with(context).load(arrayList.get(position).getUriimg()).into(image);
            }

            if(video_flag!=-1)
            {

                video.setVisibility(View.VISIBLE);
                video.setVideoURI(Uri.parse(arrayList.get(position).getUrivideo()));
                video.setMediaController(new MediaController(context));
                video.requestFocus();
                video.start();
                Toast.makeText(context, "Done ", Toast.LENGTH_LONG).show();


            }

            if(msg_flag!=-1)
            {
                message.setVisibility(View.VISIBLE);
               message.setText(arrayList.get(position).getMessage());
            }
        }else
        {
            message.setVisibility(View.VISIBLE);
            image.setVisibility(View.VISIBLE);
            video.setVisibility(View.VISIBLE);

            message.setText(arrayList.get(position).getMessage());
            Picasso.with(context).load(arrayList.get(position).getUriimg()).into(image);
            video.setVideoURI(Uri.parse(arrayList.get(position).getUrivideo()));
            video.setMediaController(new MediaController(context));
            video.requestFocus();
            video.start();
        }
        return convertView;

    }
}
