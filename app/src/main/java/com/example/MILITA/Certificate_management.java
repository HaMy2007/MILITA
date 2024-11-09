package com.example.MILITA;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Certificate_management extends AppCompatActivity {
    private RecyclerView recyclerView;
    private CertificateAdapter certificateAdapter;
    private List<Certificate> certificateList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_certificate_management);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        certificateList = new ArrayList<>();

        certificateList.add(new Certificate("Certificate 1", "2023", "Organization A", "School A", "Description of Certificate 1", "20/07/2020"));
        certificateList.add(new Certificate("Certificate 2", "2022", "Organization B", "School B", "Description of Certificate 2", "20/07/2020"));
        certificateList.add(new Certificate("Certificate 3", "2022", "Organization B", "School B", "Description of Certificate 2", "20/07/2020"));
        certificateList.add(new Certificate("Certificate 4", "2022", "Organization B", "School B", "Description of Certificate 2", "20/07/2020"));
        certificateList.add(new Certificate("Certificate 5", "2022", "Organization B", "School B", "Description of Certificate 2", "20/07/2020"));
        certificateList.add(new Certificate("Certificate 6", "2022", "Organization B", "School B", "Description of Certificate 2", "20/07/2020"));

        certificateAdapter = new CertificateAdapter(certificateList);
        recyclerView.setAdapter(certificateAdapter);
    }
}