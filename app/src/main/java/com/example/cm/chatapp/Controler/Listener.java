package com.example.cm.chatapp.Controler;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.example.cm.chatapp.Activites.departments;
import com.example.cm.chatapp.Model.Data;
import com.example.cm.chatapp.Model.PhotoData;
import com.example.cm.chatapp.Model.Users;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by cm on 22/01/2018.
 */

public class Listener {

    Context context;

    public Listener(Context context) {
        this.context = context;
    }

    public void messqge(String title) {
        Toast toast = Toast.makeText(context, title, Toast.LENGTH_LONG);
        View view = toast.getView();
        view.setBackgroundColor(Color.parseColor("#a41382"));
        toast.setGravity(Gravity.BOTTOM, 0, 0);
        toast.show();
    }

    public void LOGIN(final String name, final String password) {
        String path = "https://inexpedient-church.000webhostapp.com/chat_get_clientInfo.php?username=" + name + "&password=" + password;

        AndroidNetworking.get(path)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {

                        JSONObject jsonObject = null;
                        try {

                            jsonObject = response.getJSONObject(0);

                            if (jsonObject.length() == 2) {
                                Intent intent = new Intent(context, departments.class);
                                intent.putExtra("name", name);
                                context.startActivity(intent);
                            } else {


                                String erroe = jsonObject.getString("error");
                                // Toast.makeText(context, erroe, Toast.LENGTH_SHORT).show();
                                messqge(erroe);

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            messqge(e.getMessage());
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        messqge("error");
                    }
                });
    }


    public void SIGNUP(final String name, String pass) {
        String path = "https://inexpedient-church.000webhostapp.com/chat_insert_clientInfo.php";

        AndroidNetworking.post(path)
                .addBodyParameter("username", name)
                .addBodyParameter("password", pass)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        JSONObject jsonObject = null;
                        try {

                            jsonObject = response.getJSONObject(0);

                            if (jsonObject.length() == 2) {
                                String ok = jsonObject.getString("ok");
                                Intent intent = new Intent(context, departments.class);
                                intent.putExtra("name", name);
                                context.startActivity(intent);
                            } else {
                                String erroe = jsonObject.getString("error");
                                // Toast.makeText(context, erroe, Toast.LENGTH_SHORT).show();
                                messqge(erroe);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            messqge(e.getMessage());
                        }
                    }

                    @Override
                    public void onError(ANError error) {


                    }
                });
    }


    public void WRITE_POST( String post, String user_name ) {

        String path = "https://inexpedient-church.000webhostapp.com/chat_write_post.php?post=" + post + "&name=" + user_name;

        AndroidNetworking.get(path)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {

                        JSONObject jsonObject = null;
                        try {

                            jsonObject = response.getJSONObject(0);

                            if (jsonObject.length() == 2) {

                            } else {
                                String erroe = jsonObject.getString("error");
                                // Toast.makeText(context, erroe, Toast.LENGTH_SHORT).show();
                                messqge(erroe);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            messqge(e.getMessage());
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        messqge("error");
                    }
                });
    }


    public ArrayList<Data> READ_POSTS() {
        String path = "https://inexpedient-church.000webhostapp.com/chat_read_posts.php";
        final ArrayList<Data> data = new ArrayList<>();
        AndroidNetworking.get(path)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {

                        JSONObject jsonObject = null;
                        try {
                            data.clear();

                            for (int i = response.length()-1; i >=0 ; i--) {

                                jsonObject = response.getJSONObject(i);

                                data.add(new Data(jsonObject.getString("name"),jsonObject.getString("post")));
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            messqge(e.getMessage());
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        messqge("error");
                    }
                });

        return data;
    }


    public void IMAGES(String name, String img , String des)
    {
        String path = "https://inexpedient-church.000webhostapp.com/chat_insert_img.php?username="+name+"&image="+img+"&description="+des;

        AndroidNetworking.get(path)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {

                        JSONObject jsonObject = null;
                        try {

                            jsonObject = response.getJSONObject(0);

                            if (jsonObject.length() == 2) {

                            } else {
                                String erroe = jsonObject.getString("error");
                               messqge(erroe);
                                messqge(erroe);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            messqge(e.getMessage());
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        messqge("error");
                    }
                });
    }



    public ArrayList<PhotoData> GETIMAGES() {
        String path = "https://inexpedient-church.000webhostapp.com/chat_get_img.php";
        final ArrayList<PhotoData> arrayList = new ArrayList<>();
        AndroidNetworking.post(path)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        JSONObject jsonObject = null;
                        try {

                            for (int i = 0; i <response.length() ; i++) {
                                jsonObject = response.getJSONObject(i);

                                if (jsonObject.length() > 0) {
                                    String name = jsonObject.getString("username");
                                    String img =  jsonObject.getString("image");
                                    String des =  jsonObject.getString("description");
                                    arrayList.add(new PhotoData(des,img,name));

                                } else {

                                    messqge("error");
                                }

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            messqge(e.getMessage());
                        }
                    }

                    @Override
                    public void onError(ANError error) {


                    }
                });

        return arrayList;
    }

     ArrayList<Users> data = new ArrayList<>();

    public ArrayList<Users> GETUSERS()
    {
        String path = "https://inexpedient-church.000webhostapp.com/chat_users.php";

        final ArrayList<Users> data = new ArrayList<>();

        AndroidNetworking.get(path)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {

                        JSONObject jsonObject = null;
                        try {
                            data.clear();

                            for (int i = response.length()-1; i >=0 ; i--) {

                                jsonObject = response.getJSONObject(i);

                                data.add(new Users(jsonObject.getString("username")));
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            messqge(e.getMessage());
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        messqge("error");
                    }
                });


        return data;
    }
}