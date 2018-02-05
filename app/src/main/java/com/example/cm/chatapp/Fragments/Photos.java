package com.example.cm.chatapp.Fragments;


import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cm.chatapp.Controler.Listener;
import com.example.cm.chatapp.Controler.PhotoAdapter;
import com.example.cm.chatapp.Model.PhotoData;
import com.example.cm.chatapp.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class Photos extends Fragment {


    View v;
    CircleImageView userimg;
    FloatingActionButton send;
    ListView listView;
    ArrayList<PhotoData> arrayList;
    PhotoAdapter photoAdapter;
    Uri uri;
    String name;
    String encode;
    Listener listener;
    Bitmap btmap;
    public Photos() {

    }

    public void init()
    {

        send = v.findViewById(R.id.photoFragment_send);
        userimg = v.findViewById(R.id.photoFragment_imageview_camera);
        listView = v.findViewById(R.id.photoFragment_list);
        arrayList = new ArrayList<>();
        listener = new Listener(getActivity());
        name=getArguments().getString("username").toString();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_photos, container, false);
        v = view;
        init();

        new Thread(new Runnable() {
            @Override
            public void run() {
                arrayList = listener.GETIMAGES();
                photoAdapter = new PhotoAdapter(getActivity(),R.layout.images,arrayList);
                listView.setAdapter(photoAdapter);
            }
        }).start();



                userimg.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent,10);
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setView(R.layout.img_dialog);
                        final AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                        FloatingActionButton ok = alertDialog.findViewById(R.id.imgdialog_button_ok);
                        final EditText editText = alertDialog.findViewById(R.id.imgdialog_edittext_des);

                        ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {


                                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                                btmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
                                encode = Base64.encodeToString(byteArrayOutputStream.toByteArray(),Base64.DEFAULT);
                                //Log.i("aaaa",encode);
                                //listener.IMAGES(name,encode,editText.getText().toString());
                                if(encode.length()==0)
                                {
                                    Toast.makeText(getActivity(), "Select Image", Toast.LENGTH_LONG).show();
                                }else
                                {
                                    listener.IMAGES(getArguments().getString("username"),"img",editText.getText().toString());
                                    arrayList.clear();
                                    arrayList = listener.GETIMAGES();
                                    photoAdapter = new PhotoAdapter(getActivity(),R.layout.images,arrayList);
                                    listView.setAdapter(photoAdapter);

                                }

                                alertDialog.dismiss();
                            }
                        });
                    }
                });



        return v;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==10)
        {

            uri = data.getData();
            if(uri.equals("") || uri!=null)
            {
                try {
                    btmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),uri);
                    userimg.setImageBitmap(btmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else
            {
                Toast.makeText(getActivity(), "Not Selected Imaage", Toast.LENGTH_SHORT).show();
            }

        }
    }


}
