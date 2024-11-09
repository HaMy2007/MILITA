package com.example.MILITA;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;

public class CertificateAdapter extends RecyclerView.Adapter<CertificateAdapter.CertificateViewHolder> {

    private List<Certificate> certificateList;

    public CertificateAdapter(List<Certificate> certificateList) {
        this.certificateList = certificateList;
    }

    @Override
    public CertificateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.certificate_item, parent, false);
        return new CertificateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CertificateViewHolder holder, int position) {
        Certificate certificate = certificateList.get(position);

        holder.nameTextView.setText(certificate.getName());
        holder.sessionTextView.setText(certificate.getSession());
        holder.dateTextView.setText(certificate.getDateCreate());
    }

    @Override
    public int getItemCount() {
        return certificateList.size();
    }

    public static class CertificateViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView;
        TextView sessionTextView;
        TextView dateTextView;


        public CertificateViewHolder(View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.certificate_name);
            sessionTextView = itemView.findViewById(R.id.certificate_session);
            dateTextView = itemView.findViewById(R.id.certificate_date);

        }
    }
}
