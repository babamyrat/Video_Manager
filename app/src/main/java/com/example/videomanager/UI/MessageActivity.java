package com.example.videomanager.UI;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.videomanager.R;
import com.example.videomanager.model.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class MessageActivity extends AppCompatActivity {
private TextView username;
private ImageView imageView;
private FirebaseUser  fuser;
private DatabaseReference reference;
private Intent intent;
private RecyclerView recyclerView;
private EditText msg_editText;
ImageButton sendBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        //Widgets
        imageView = findViewById(R.id.imageView_profile);
        username = findViewById(R.id.usernamey);
        recyclerView = findViewById(R.id.recyclerViewMessage);
        msg_editText = findViewById(R.id.text_content);
        sendBTN = findViewById(R.id.btn_send);

        //begin all codes here
        intent = getIntent();
        String userid = intent.getStringExtra("userid");

        fuser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("MyUsers").child(userid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserModel user = snapshot.getValue(UserModel.class);
                username.setText(user.getUsername());

                    Glide.with(MessageActivity.this)
                            .load(user.getImageURL())
                            .placeholder(R.drawable.ic_launcher_background)
                            .into(imageView);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        sendBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mgs = msg_editText.getText().toString();
                if (!mgs.equals("")){
                    sendMessage(fuser.getUid(), userid, mgs);
                } else {
                    Toast.makeText(MessageActivity.this, "Please send a nom empty message!", Toast.LENGTH_SHORT).show();
                }
                msg_editText.setText("");
            }
        });

    }

    private void sendMessage(String sender, String receiver, String message) {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);

    }

    @Override
    public void setSupportActionBar(@Nullable androidx.appcompat.widget.Toolbar toolbar) {
//        super.setSupportActionBar(toolbar);
    }
}