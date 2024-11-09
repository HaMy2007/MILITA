package com.example.MILITA;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.MILITA.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Edit_information extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_PICK_IMAGE = 2;
    private FirebaseStorage storage;
    private Uri selectedImageUri;
    private FirebaseFirestore db;
    Button btnCapture, btnSave;
    TextView username;
    CircleImageView profile_image;
    EditText et_username, et_birthday, et_email, et_phone, et_status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_information);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnCapture = findViewById(R.id.btnCapture);
        btnSave = findViewById(R.id.btnSave);
        username = findViewById(R.id.username);
        profile_image = findViewById(R.id.profile_image);
        et_username = findViewById(R.id.et_username);
        et_birthday = findViewById(R.id.et_birthday);
        et_email = findViewById(R.id.et_email);
        et_phone = findViewById(R.id.et_phone);
        et_status = findViewById(R.id.et_status);
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();

        Intent intent = getIntent();
        if (intent != null) {
            username.setText(intent.getStringExtra("username"));
            et_username.setText(intent.getStringExtra("username"));
            et_birthday.setText(intent.getStringExtra("birthday"));
            et_email.setText(intent.getStringExtra("email"));
            et_phone.setText(intent.getStringExtra("phone"));
            et_status.setText(intent.getStringExtra("status"));

            String avatarUrl = intent.getStringExtra("avatar_url");
            if (avatarUrl != null) {
                Glide.with(this)
                        .load(avatarUrl)
                        .placeholder(R.drawable.user)
                        .error(R.drawable.avatar_error)
                        .into(profile_image);
            }
        }
        btnSave.setOnClickListener(v -> saveDataToFirestore());
        btnCapture.setOnClickListener(v -> showImagePickerDialog());
    }
    private void showImagePickerDialog() {
        String[] options = {"Chụp ảnh", "Chọn từ thư viện"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Chọn ảnh")
                .setItems(options, (dialog, which) -> {
                    if (which == 0) {
                        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                    } else if (which == 1) {
                        Intent pickPhotoIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(pickPhotoIntent, REQUEST_PICK_IMAGE);
                    }
                })
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE && data != null && data.getData() != null) {
                Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
                selectedImageUri = getImageUri(imageBitmap);
                profile_image.setImageURI(selectedImageUri);
            } else if (requestCode == REQUEST_PICK_IMAGE && data != null && data.getData() != null) {
                selectedImageUri = data.getData();
                if (selectedImageUri != null) {
                    profile_image.setImageURI(selectedImageUri);
                } else {
                    Toast.makeText(this, "Lỗi khi chọn ảnh. Vui lòng thử lại!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private Uri getImageUri(Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "Title", null);
        return Uri.parse(path);
    }

    private void saveDataToFirestore() {
        String username = et_username.getText().toString();
        String birthday = et_birthday.getText().toString();
        String email = et_email.getText().toString();
        String phone = et_phone.getText().toString();
        String status = et_status.getText().toString();

        if (username.isEmpty() || email.isEmpty() || phone.isEmpty() || birthday.isEmpty() || status.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> userData = new HashMap<>();
        userData.put("name", username);
        userData.put("birthday", birthday);
        userData.put("email", email);
        userData.put("phone", phone);
        userData.put("status", status);

        if (selectedImageUri != null) {
//            storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference().child("avatars/" + username + ".jpg");
            storageRef.putFile(selectedImageUri)
                    .addOnSuccessListener(taskSnapshot -> storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        String imageUrl = uri.toString();
                        userData.put("avatar", imageUrl);
                        updateFirestoreData(userData);
                    }))
                    .addOnFailureListener(e -> Toast.makeText(Edit_information.this, "Lỗi khi tải ảnh lên: " + e.getMessage(), Toast.LENGTH_SHORT).show());
        } else {
            updateFirestoreData(userData);
        }
    }

    private void updateFirestoreData(Map<String, Object> userData) {
        db.collection("users").document("user1")
                .update(userData)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(Edit_information.this, "Thông tin đã được cập nhật", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Edit_information.this, User_profile.class));
                    finish();
                })
                .addOnFailureListener(e -> Toast.makeText(Edit_information.this, "Cập nhật thất bại: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}