
package com.martin.proektnazadaca;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterPostaroLice extends RecyclerView.Adapter<AdapterPostaroLice.MyViewHolder> {
    static Context context;



    ArrayList<Aktivnost> listAktivnosts;

    public AdapterPostaroLice(Context context, ArrayList<Aktivnost> listAktivnosts) {
        this.context = context;
        this.listAktivnosts = listAktivnosts;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_volonter, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Aktivnost aktivnost = listAktivnosts.get(position);
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
        holder.status.setText(listAktivnosts.get(position).getStatus());
        holder.aktivnost = aktivnost;
        holder.key = aktivnost.getKey();
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

        TextView imeAktivnost, opisAktivnost, kraenRokAktivnost, lokacijaAktivnost, liceImeAktivnost, emailLiceAktivnost, telefonLiceAktivnost, urgencyAktivnost, recuringAktivnost, aktivnost_postavena, status;
        Button povekje;
        String key;

        Aktivnost aktivnost;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imeAktivnost = (TextView) itemView.findViewById(R.id.aktivnost_name1);
            opisAktivnost = (TextView) itemView.findViewById(R.id.aktivnost_details1);
            kraenRokAktivnost = (TextView) itemView.findViewById(R.id.aktivnost_rok1);
            lokacijaAktivnost = (TextView) itemView.findViewById(R.id.aktivnost_location1);
            liceImeAktivnost = (TextView) itemView.findViewById(R.id.aktivnost_username1);
            emailLiceAktivnost = (TextView) itemView.findViewById(R.id.aktivnost_email1);
            telefonLiceAktivnost = (TextView) itemView.findViewById(R.id.aktivnost_telefon1);
            urgencyAktivnost = (TextView) itemView.findViewById(R.id.aktivnost_u1);
            recuringAktivnost = (TextView) itemView.findViewById(R.id.aktivnost_r1);
            aktivnost_postavena =(TextView) itemView.findViewById(R.id.aktivnost_postavena1);
            status = (TextView) itemView.findViewById(R.id.status1);
            povekje = (Button) itemView.findViewById(R.id.povekje1);
            povekje.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    otvoriAktivnost(key);
                }
            });



        }
        public static void otvoriAktivnost(String key){
            Log.d("demo", "onClick: fired " + key);
            String bla = key;
            Intent intent = new Intent(context, ManagerActivity.class);
            intent.putExtra("id", key);
            context.startActivity(intent);
        }
    }
}
