package com.example.cm.chatapp.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.cm.chatapp.Activites.ChatRomm;
import com.example.cm.chatapp.Controler.Listener;
import com.example.cm.chatapp.Controler.UsersAdapter;
import com.example.cm.chatapp.Model.Users;
import com.example.cm.chatapp.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Chat extends Fragment {


    public Chat() {

    }

    public  String chatname(String s1 , String s2)
    {

        String n = "";

        if(s1.equals(s2))
        {
            n=s1+s2 ;
        }else
        {
            int min;
            if(s1.length() > s2.length())
            {
                min=s2.length();
            }else
            {
                min = s1.length();
            }

            for (int i = 0; i < min; i++) {

                if((int) s1.charAt(i) > (int) s2.charAt(i) )
                {

                    n=s1+s2;
                    break;
                }else if((int) s1.charAt(i) < (int) s2.charAt(i) )
                {

                    n=s2+s1;
                    break;
                }

            }

            if(n.equals(""))
            {

                if(s1.length() > s2.length())
                {
                    n=s1+s2;
                }else
                {
                    n=s2+s1;
                }

            }
        }

        return n;
    }

    ArrayList<Users> arrayList;
    String name;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        arrayList = new ArrayList<>();
        name = getArguments().getString("username");

      View  v =inflater.inflate(R.layout.fragment_chat, container, false);
        final ListView listView = v.findViewById(R.id.chatfragment_lv);


                Listener listener = new Listener(getActivity());
                arrayList = listener.GETUSERS();

                UsersAdapter usersAdapter = new UsersAdapter(getActivity(),R.layout.users,arrayList);
                listView.setAdapter(usersAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                       Intent intent = new Intent(getActivity(), ChatRomm.class);
                       String sss = chatname(name, arrayList.get(i).getUsername());
                       intent.putExtra("chatname",sss);
                       intent.putExtra("guest",arrayList.get(i).getUsername());
                       intent.putExtra("username",name);
                       startActivity(intent);

                    }
                });


        return v;
    }

}
