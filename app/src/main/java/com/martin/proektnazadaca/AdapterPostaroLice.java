
package com.martin.proektnazadaca;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterPostaroLice extends RecyclerView.Adapter<AdapterPostaroLice.MyViewHolder> {
    Context context;

    ArrayList<Aktivnost> listAktivnosts;

    public AdapterPostaroLice(Context context, ArrayList<Aktivnost> listAktivnosts) {
        this.context = context;
        this.listAktivnosts = listAktivnosts;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//        Taskovi taskovi = listAktivnosts.get(position);
        holder.imeAktivnost.setText(listAktivnosts.get(position).getImeAktivnost());
        holder.opisAktivnost.setText(listAktivnosts.get(position).getOpisAktivnost());
        holder.kraenRokAktivnost.setText(listAktivnosts.get(position).getKraenRokAktivnost());
        holder.lokacijaAktivnost.setText(listAktivnosts.get(position).getLokacijaAktivnost());
        holder.liceImeAktivnost.setText(listAktivnosts.get(position).getLiceImeAktivnost());
        holder.emailLiceAktivnost.setText(listAktivnosts.get(position).getEmailLiceAktivnost());
        holder.urgencyAktivnost.setText(listAktivnosts.get(position).getUrgencyAktivnost());
        holder.recuringAktivnost.setText(listAktivnosts.get(position).getRecuringAktivnost());
        holder.telefonLiceAktivnost.setText(listAktivnosts.get(position).getTelefonLiceAktivnost());
        holder.aktivnost_postavena.setText(listAktivnosts.get(position).getTimeOfRecordCreation());

        holder.lokacijaAktivnost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?q=loc:"+listAktivnosts.get(position).getLokacijaAktivnost()));
                context.startActivity(browserIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listAktivnosts.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView imeAktivnost, opisAktivnost, kraenRokAktivnost, lokacijaAktivnost, liceImeAktivnost, emailLiceAktivnost, telefonLiceAktivnost, urgencyAktivnost, recuringAktivnost, aktivnost_postavena;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);


            imeAktivnost = (TextView) itemView.findViewById(R.id.aktivnost_name);
            opisAktivnost = (TextView) itemView.findViewById(R.id.aktivnost_details);
            kraenRokAktivnost = (TextView) itemView.findViewById(R.id.aktivnost_rok);
            lokacijaAktivnost = (TextView) itemView.findViewById(R.id.aktivnost_location);
            liceImeAktivnost = (TextView) itemView.findViewById(R.id.aktivnost_username);
            emailLiceAktivnost = (TextView) itemView.findViewById(R.id.aktivnost_email);
            telefonLiceAktivnost = (TextView) itemView.findViewById(R.id.aktivnost_telefon);
            urgencyAktivnost = (TextView) itemView.findViewById(R.id.aktivnost_u);
            recuringAktivnost = (TextView) itemView.findViewById(R.id.aktivnost_r);
            aktivnost_postavena =(TextView) itemView.findViewById(R.id.aktivnost_postavena);
        }
    }
}
