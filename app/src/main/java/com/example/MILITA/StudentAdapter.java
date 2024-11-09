package com.example.MILITA;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    private List<Student> studentList;

    public StudentAdapter(List<Student> studentList) {
        this.studentList = studentList;
    }

    @Override
    public StudentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.student_item, parent, false);
        return new StudentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(StudentViewHolder holder, int position) {
        Student student = studentList.get(position);
        holder.userName.setText(student.getName());
        holder.id.setText(student.getId());
        holder.classStudent.setText(student.getClassStudent());
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public class StudentViewHolder extends RecyclerView.ViewHolder {
        public TextView userName, id, classStudent;

        public StudentViewHolder(View view) {
            super(view);
            userName = view.findViewById(R.id.tvUserName);
            id = view.findViewById(R.id.tvId);
            classStudent = view.findViewById(R.id.tvClass);
        }
    }
}

