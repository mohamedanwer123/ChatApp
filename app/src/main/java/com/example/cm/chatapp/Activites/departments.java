package com.example.cm.chatapp.Activites;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cm.chatapp.Fragments.Chat;
import com.example.cm.chatapp.Fragments.Photos;
import com.example.cm.chatapp.Fragments.news;
import com.example.cm.chatapp.R;

public class departments extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_departments);
        init();
        Fragment news = new news();
        load_page(news);
        itemselected();
      //  font();
    }

    public void init()
    {
        drawerLayout = findViewById(R.id.drawerlayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(departments.this,drawerLayout,R.string.open,R.string.close);
        navigationView = findViewById(R.id.navigation);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Chat..");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        name = getIntent().getStringExtra("name").toString();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }

        int id = item.getItemId();

        switch (id)
        {
            case R.id.logout :
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void itemselected()
    {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.news:
                        Fragment news = new news();
                        load_page(news);
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.phptos :
                        Fragment photo = new Photos();
                        load_page(photo);
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.chat :
                        Fragment chat = new Chat();
                        load_page(chat);
                        drawerLayout.closeDrawers();
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.logout,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void load_page(Fragment fragment)
    {
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.popBackStack();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("username",name);
        fragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.frame,fragment);
        fragmentTransaction.commit();

    }



}
