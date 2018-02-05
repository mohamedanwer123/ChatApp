package com.example.cm.chatapp.Activites;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cm.chatapp.Controler.ChatAdapter;
import com.example.cm.chatapp.Fragments.Chat;
import com.example.cm.chatapp.Model.Block;
import com.example.cm.chatapp.Model.ChatData;
import com.example.cm.chatapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatRomm extends AppCompatActivity {

    String chatName,guestName;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ScrollView scrollView;
    String check,color;

    ListView listView;
    CircleImageView gallary , camera , video;
    EditText message;
    FloatingActionButton send;
    ArrayList<ChatData> arrayList;
    ChatAdapter chatAdapter;
    Uri  img_uri=null,video_uri=null;
    String msg="";
    String username;

    int flag=0;

    int x;
    String n;
    LinearLayout linearLayout;

    DatabaseReference databaseReference;
    StorageReference storageReference;
    MenuItem block_item,unblock_item;
    Menu menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_romm);
        init();
         check = sharedPreferences.getString("chat_name",guestName);
         color = sharedPreferences.getString("color","#230c70");

        if(!check.equals(guestName))
        {
            default_ChatName(check,color);
        }else
        {
            default_ChatName(guestName,color);
        }

        gallary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag == -1)
                {
                    Toast.makeText(ChatRomm.this, "Can not Chose 2 Media", Toast.LENGTH_SHORT).show();
                }else
                {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent,200);
                    flag=1;
                }

            }
        });

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(flag == -1)
                {
                    Toast.makeText(ChatRomm.this, "Can not Chose 2 Media", Toast.LENGTH_SHORT).show();
                }else
                {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent,100);
                    flag=1;
                }

            }
        });

        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (flag == 1)
                {
                    Toast.makeText(ChatRomm.this, "Can not Chose 2 Media", Toast.LENGTH_SHORT).show();
                }else
                {
                    flag = -1;
                    Intent intent = new Intent();
                    intent.setType("video/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent,"Select Video"),300);
                }

            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {

                final ChatData chatData = new ChatData();
                chatData.setUsername(username);
                chatData.setMessage(message.getText().toString());

                if(img_uri!=null)
                {
                    final ProgressDialog progressDialog = new ProgressDialog(ChatRomm.this);
                    progressDialog.setTitle("Image in Sending");
                    progressDialog.setProgress(0);
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.show();

                    storageReference.child(chatName+"Images/"+System.currentTimeMillis()+getImageExtension(img_uri)).putFile(img_uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            chatData.setUriimg(taskSnapshot.getDownloadUrl().toString());
                            chatData.setUrivideo("");
                            databaseReference.child(chatName).push().setValue(chatData);
                            img_uri=null;
                            video_uri = null;
                            message.setText("");
                            Toast.makeText(ChatRomm.this, "Image Send successful", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {

                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                            progressDialog.setMax((int) taskSnapshot.getBytesTransferred() * 100);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(ChatRomm.this, "Error", Toast.LENGTH_LONG).show();
                        }
                    });
                }else if(video_uri!=null)
                {

                    final ProgressDialog progressDialog = new ProgressDialog(ChatRomm.this);
                    progressDialog.setTitle("Video in Sending");
                    progressDialog.setProgress(0);
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.show();

                    storageReference.child(chatName+"Videos/"+System.currentTimeMillis()+getImageExtension(video_uri)).putFile(video_uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            chatData.setUriimg("");
                            chatData.setUrivideo(taskSnapshot.getDownloadUrl().toString());
                            databaseReference.child(chatName).push().setValue(chatData);
                            img_uri=null;
                            video_uri = null;
                            message.setText("");
                            Toast.makeText(ChatRomm.this, "Video Send successful", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {

                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                            progressDialog.setMax((int) taskSnapshot.getBytesTransferred() * 100);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(ChatRomm.this, "Error", Toast.LENGTH_LONG).show();
                        }
                    });
                }else
                {
                    chatData.setUrivideo("");
                    chatData.setUriimg("");
                    databaseReference.child(chatName).push().setValue(chatData);
                    img_uri=null;
                    video_uri = null;
                    message.setText("");

                }

                flag = 0;



            }
        });

        databaseReference.child(chatName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                arrayList.clear();
                for(DataSnapshot d : dataSnapshot.getChildren())
                {
                    arrayList.add(d.getValue(ChatData.class));
                }
                chatAdapter = new ChatAdapter(ChatRomm.this,R.layout.chat_component,arrayList);
                listView.setAdapter(chatAdapter);
                listView.setSelection(arrayList.size()-1);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        databaseReference.child(chatName+"block").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
             Block block = new Block();
               for (DataSnapshot d : dataSnapshot.getChildren())
                {
                    block.setState(d.getValue(Block.class).getState());
                    block.setName(d.getValue(Block.class).getName());
                }

                x = block.getState();
                n= block.getName();

                block_item = menu.findItem(R.id.block);
                unblock_item = menu.findItem(R.id.unblock);

                if(x==-1)
                {
                    linearLayout.setVisibility(View.GONE);
                    if(!n.equals(username))
                    {

                        block_item.setEnabled(false);
                        unblock_item.setEnabled(false);
                    }else
                    {
                        block_item.setEnabled(false);
                        unblock_item.setEnabled(true);
                    }

                }else
                {
                    linearLayout.setVisibility(View.VISIBLE);
                    unblock_item.setEnabled(false);
                    block_item.setEnabled(true);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



    public String getImageExtension(Uri uri)
    {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode==200)
        {
            img_uri = data.getData();
        }

        //camera
        if(requestCode==100 && resultCode==RESULT_OK)
        {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
            String path = MediaStore.Images.Media.insertImage(ChatRomm.this.getContentResolver(),bitmap,"",null);
            img_uri = Uri.parse(path);

        }

        if(requestCode==300 && resultCode==RESULT_OK)
        {
          video_uri = data.getData();

        }
    }

    public void init()
    {
        chatName = getIntent().getStringExtra("chatname");
        guestName = getIntent().getStringExtra("guest");
        Toast.makeText(this, guestName, Toast.LENGTH_SHORT).show();
        username = getIntent().getStringExtra("username");
        Toast.makeText(this, username, Toast.LENGTH_SHORT).show();
        scrollView = findViewById(R.id.chatroom_layout);
        sharedPreferences  = getSharedPreferences(chatName, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        listView = findViewById(R.id.chatroom_listview);
        send = findViewById(R.id.chatroom_send);
        gallary = findViewById(R.id.chatroom_gallary);
        camera = findViewById(R.id.chatroom_camera);
        video = findViewById(R.id.chatroom_video);
        message = findViewById(R.id.chatroom_message);
        arrayList = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();
         linearLayout = findViewById(R.id.chat_room_layout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.chat_options,menu);
        this.menu = menu;
        return super.onCreateOptionsMenu(menu);

    }

    public void default_ChatName(String s , String color)
    {

        getSupportActionBar().setTitle(s);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        scrollView.setBackgroundColor(Color.parseColor(color));
        editor.putString("chat_name",s);
        editor.putString("color",color);
        editor.apply();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id)
        {
            case  R.id.change_chat_name:
                CHANGE_CHAT_NAME();
                break;

            case  R.id.chatColor :
                CHAT_COLOR();
                break;

            case  R.id.block:

                Block block = new Block(-1,username);
                databaseReference.child(chatName+"block").push().setValue(block);
                break;

            case  R.id.unblock:
                Block block2 = new Block(1,username);
                databaseReference.child(chatName+"block").push().setValue(block2);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    public void CHAT_COLOR()
    {
        AlertDialog.Builder builder2 = new AlertDialog.Builder(ChatRomm.this);
        builder2.setView(R.layout.chat_color_dialog);
        final AlertDialog alertDialog2 = builder2.create();
        alertDialog2.show();
        CircleImageView blue,sky,darkgray,red,gray;
        blue = alertDialog2.findViewById(R.id.chat_color_xml_blue);
        sky = alertDialog2.findViewById(R.id.chat_color_xml_sky);
        darkgray = alertDialog2.findViewById(R.id.chat_color_xml_darkgray);
        red = alertDialog2.findViewById(R.id.chat_color_xml_red);
        gray = alertDialog2.findViewById(R.id.chat_color_xml_gray);

        blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scrollView.setBackgroundColor(Color.parseColor("#230c70"));
              /*  editor.putString("color","#230c70");
                editor.apply();*/
                default_ChatName(check,"#230c70");
                alertDialog2.dismiss();
            }
        });

        sky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scrollView.setBackgroundColor(Color.parseColor("#037ef3"));
              /*  editor.putString("color","#037ef3");
                editor.apply();*/
                default_ChatName(check,"#037ef3");
                alertDialog2.dismiss();
            }
        });

        darkgray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scrollView.setBackgroundColor(Color.parseColor("#52565e"));
               /* editor.putString("color","#52565e");
                editor.apply();*/
                default_ChatName(check,"#52565e");
                alertDialog2.dismiss();
            }
        });

        red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scrollView.setBackgroundColor(Color.parseColor("#d20962"));
                /*editor.putString("color","#d20962");
                editor.apply();*/
                default_ChatName(check,"#d20962");
                alertDialog2.dismiss();
            }
        });

        gray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scrollView.setBackgroundColor(Color.parseColor("#caccd1"));
                default_ChatName(check,"#caccd1");
                alertDialog2.dismiss();
                //editor.putString("color","#caccd1");
                //editor.apply();
            }
        });
    }


    public void CHANGE_CHAT_NAME()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(ChatRomm.this);
        builder.setView(R.layout.change_chat_name_dialog);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        final EditText editText = alertDialog.findViewById(R.id.change_chat_name_xml_name);
        FloatingActionButton floatingActionButton = alertDialog.findViewById(R.id.change_chat_name_xml_ok);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = editText.getText().toString();
                if(s.length()!=0)
                {
                    getSupportActionBar().setTitle(s);
                    getSupportActionBar().setDisplayShowHomeEnabled(true);
                    default_ChatName(s,color);
                    alertDialog.dismiss();
                }else
                {
                    alertDialog.dismiss();
                }
            }
        });
    }
}
