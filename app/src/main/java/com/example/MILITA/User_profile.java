package com.example.MILITA;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.MILITA.R;
import com.google.firebase.firestore.FirebaseFirestore;

import de.hdodenhof.circleimageview.CircleImageView;

public class User_profile extends AppCompatActivity {
    String avatarUrl;
    Button btnSetting, btnLogout;
    CircleImageView avatar_user;
    TextView username, tv_name, tv_birthday, tv_email, tv_status, tv_phone;
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnSetting = findViewById(R.id.btnSetting);
        btnLogout = findViewById(R.id.btnLogout);
        avatar_user = findViewById(R.id.avatar_user);
        tv_birthday = findViewById(R.id.tv_birthday);
        tv_name = findViewById(R.id.tv_name);
        username = findViewById(R.id.username);
        tv_email = findViewById(R.id.tv_email);
        tv_status = findViewById(R.id.tv_status);
        tv_phone = findViewById(R.id.tv_phone);

        db = FirebaseFirestore.getInstance();

        db.collection("users").document("user1").get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        avatarUrl = documentSnapshot.getString("avatar");
                        String avatar = documentSnapshot.getString("avatar");
                        String birthday = documentSnapshot.getString("birthday");
                        String email = documentSnapshot.getString("email");
                        String name = documentSnapshot.getString("name");
                        String phone = documentSnapshot.getString("phone");
                        String status = documentSnapshot.getString("status");

                        username.setText(name);
                        tv_name.setText(name);
                        tv_birthday.setText(birthday);
                        tv_email.setText(email);
                        tv_phone.setText(phone);
                        tv_status.setText(status);

                        Glide.with(this)
                                .load(avatar) // avatar là URL của ảnh từ Firestore
                                .placeholder(R.drawable.user)
                                .error(R.drawable.avatar_error)
                                .into(avatar_user);
                    } else {
                        Log.d("FirestoreData", "Document không tồn tại.");
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("FirestoreData", "Lỗi khi lấy dữ liệu", e);
                });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view.getId() == R.id.btnLogout) {
                    Intent intent = new Intent(User_profile.this, Student_management.class);
                    startActivity(intent);
                }
            }
        });
        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view.getId() == R.id.btnSetting) {
                    Intent intent = new Intent(User_profile.this, Edit_information.class);
                    intent.putExtra("username", username.getText().toString());
                    intent.putExtra("birthday", tv_birthday.getText().toString());
                    intent.putExtra("email", tv_email.getText().toString());
                    intent.putExtra("phone", tv_phone.getText().toString());
                    intent.putExtra("status", tv_status.getText().toString());
                    intent.putExtra("avatar_url", avatarUrl);
                    startActivity(intent);
                }
            }
        });
    }
}