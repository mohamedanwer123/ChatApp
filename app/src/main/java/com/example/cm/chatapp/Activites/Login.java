package com.example.cm.chatapp.Activites;

import android.Manifest;
import android.annotation.SuppressLint;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.example.cm.chatapp.Controler.Listener;
import com.example.cm.chatapp.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;


public class Login extends AppCompatActivity {

    EditText username,password;
    Button login,signup;
    long time=0;
    Listener listener;
    CircleImageView img;
    ImageView camera;
    public static Uri uri;
    public  static  String clinetname;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        permession();
        font();
        SharedPreferences sharedPreferences = getSharedPreferences("info",Context.MODE_PRIVATE);
        username.setText(sharedPreferences.getString("name","0"));
        password.setText(sharedPreferences.getString("pass","0"));
        clinetname = username.getText().toString();
        listener = new Listener(Login.this);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new loginTask().execute();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            new  singnupTask().execute();

            }
        });


        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,100);
            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void permession()
    {
        requestPermissions(new String[]{Manifest.permission.INTERNET,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},123);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100)
        {
            uri = data.getData();
            img.setImageURI(uri);

        }
    }



    public void init()
    {
        getSupportActionBar().setCustomView(R.layout.login_bar);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        username = findViewById(R.id.loginactivity_edittext_username);
        password = findViewById(R.id.loginactivity_edittext_password);
        login = findViewById(R.id.loginactivity_button_login);
        signup = findViewById(R.id.loginactivity_button_Signin);

        AndroidNetworking.initialize(Login.this);

        img = findViewById(R.id.loginactivity_img);
        camera = findViewById(R.id.loginactivity_camera);

    }

    public void font()
    {
        @SuppressLint("WrongViewCast") View view = getLayoutInflater().inflate(R.layout.login_bar,(ViewGroup) findViewById(R.id.loginbar_linearlayout));
        Typeface typeface = Typeface.createFromAsset(getAssets(),"font.ttf");
        TextView bar_title = view.findViewById(R.id.loginbar_title);
        bar_title.setTypeface(typeface);
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onBackPressed() {
        if(time>System.currentTimeMillis())
        {
            finishAffinity();
        }else
        {
           messqge("Press again");
            time = System.currentTimeMillis()+3000;
        }
    }

    public void login()
    {
        String name = username.getText().toString().toLowerCase().trim();
        String pass = password.getText().toString().toLowerCase().trim();

        if(name.length()==0 || pass.length()==0)
        {
            messqge("Please Enter Your Data");
        }else
        {
            listener.LOGIN(name,pass);
            SharedPreferences sharedPreferences = getSharedPreferences("info",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("name",name);
            editor.putString("pass",name);
            editor.apply();
        }

    }

    public void messqge(String title)
    {
        Toast toast = Toast.makeText(this, title, Toast.LENGTH_SHORT);
        View view = toast.getView();
        view.setBackgroundColor(Color.parseColor("#a41382"));
        toast.setGravity(Gravity.BOTTOM,0,0);
        toast.show();
    }


   public class loginTask extends AsyncTask<Void,Void , Void>
    {

        @Override
        protected Void doInBackground(Void... voids) {
            login();
            return null;
        }
    }

    public class singnupTask extends AsyncTask<Void,Void , Void>
    {
        AlertDialog.Builder builder;
         AlertDialog alertDialog;
        EditText name;
         EditText pass ;
        FloatingActionButton floatingActionButton;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            builder = new AlertDialog.Builder(Login.this);
            builder.setView(R.layout.singup_dialog);
            builder.setCancelable(false);
            alertDialog = builder.create();
            alertDialog.show();
            name = alertDialog.findViewById(R.id.singupdialog_edittext_username);
            pass = alertDialog.findViewById(R.id.singupdialog_edittext_password);
            floatingActionButton = alertDialog.findViewById(R.id.singupdialog_button_Signup);
          //  clinetname = name.getText().toString();

        }

        @Override
        protected Void doInBackground(Void... voids) {




            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String n = name.getText().toString().toLowerCase().trim();
                    String p = pass.getText().toString().toLowerCase().trim();

                    if(n.length()==0 || p.length()==0)
                    {
                        messqge("please enter your Data");
                    }else
                    {
                        listener.SIGNUP(n,p);
                        SharedPreferences sharedPreferences = getSharedPreferences("info",Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("name",n);
                        editor.putString("pass",p);
                        editor.apply();
                    }
                    alertDialog.dismiss();
                }
            });

            return null;
        }

    }


}
