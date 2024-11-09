package com.example.MILITA;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Student_management extends AppCompatActivity {
    private RecyclerView recyclerView;
    private StudentAdapter adapter;
    private List<Student> studentList;
    Button btnFilter, btnAddStu, btnDelStu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_student_management);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btnFilter = findViewById(R.id.btnFilter);
        btnAddStu = findViewById(R.id.btnAddStu);
        btnDelStu = findViewById(R.id.btnDelStu);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        studentList = new ArrayList<>();
        studentList.add(new Student("John Doe", "12345", "22050201"));
        studentList.add(new Student("Jane Smith", "54321", "22050201"));
        studentList.add(new Student("Jane Smith", "54321", "22050201"));
        studentList.add(new Student("Jane Smith", "54321", "22050201"));
        studentList.add(new Student("Jane Smith", "54321", "22050201"));
        studentList.add(new Student("Jane Smith", "54321", "22050201"));

        adapter = new StudentAdapter(studentList);
        recyclerView.setAdapter(adapter);

        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view.getId() == R.id.btnFilter) {
                    Intent intent = new Intent(Student_management.this, Student_profile.class);
                    startActivity(intent);
                }
            }
        });
        btnAddStu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view.getId() == R.id.btnAddStu) {
                    Intent intent = new Intent(Student_management.this, Add_student.class);
                    startActivity(intent);
                }
            }
        });
        btnDelStu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view.getId() == R.id.btnDelStu) {
                    Intent intent = new Intent(Student_management.this, Certificate_management.class);
                    startActivity(intent);
                }
            }
        });
    }
}