
package com.martin.proektnazadaca;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class AdapterVoProcessAktivnostiVolonter extends RecyclerView.Adapter<AdapterVoProcessAktivnostiVolonter.MyViewHolder> {
    static Context context;

    private float rateValue;

    ArrayList<Aktivnost> listTasks;

    DatabaseReference reference;

    User lice;


    View v;

    public AdapterVoProcessAktivnostiVolonter(Context context, ArrayList<Aktivnost> listTasks) {
        this.context = context;
        this.listTasks = listTasks;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        v = LayoutInflater.from(context).inflate(R.layout.item_volonter3, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Aktivnost aktivnost = listTasks.get(position);
        holder.UserName.setText(aktivnost.getLiceImeAktivnost());
        holder.Email.setText(aktivnost.getEmailLiceAktivnost());
        holder.Phone.setText(aktivnost.getTelefonLiceAktivnost());
        holder.Address.setText(aktivnost.getLokacijaAktivnost());
        holder.DateOfPost.setText(aktivnost.getTimeOfRecordCreation());
        holder.DueTime.setText(aktivnost.getKraenRokAktivnost());
        holder.AktivityName.setText(aktivnost.getImeAktivnost());
        holder.AktivityDetails.setText(aktivnost.getOpisAktivnost());
        holder.AktivnostRecuring.setText(aktivnost.getRecuringAktivnost());
        holder.AktivnostUrgency.setText(aktivnost.getUrgencyAktivnost());
        holder.HideEmail.setVisibility(GONE);
        holder.HidePhone.setVisibility(GONE);
        holder.Email.setVisibility(GONE);
        holder.Phone.setVisibility(GONE);

        holder.nazad.setOnClickListener(back);

        if(aktivnost.getStatus().equals("Пријавен волонтер")){
            holder.Status.setText("Се чека одговор од: "+ String.valueOf(holder.UserName.getText()));
        } else if(aktivnost.getStatus().equals("Закажана активност")){
            holder.Status.setText("Активноста е закажана");
            holder.Phone.setVisibility(VISIBLE);
            holder.Email.setVisibility(VISIBLE);
            holder.HideEmail.setVisibility(VISIBLE);
            holder.HidePhone.setVisibility(VISIBLE);
        } else{
            holder.Status.setText("Активноста е завршена");
            holder.izvestaj.setVisibility(VISIBLE);
            holder.izvestaj.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    HashMap hashMap = new HashMap();
                    HashMap hashMap2 = new HashMap();
                    String liceId = aktivnost.getId();
                    hashMap.put("volonter", "0");
                    hashMap2.put("id", "0");
                    reference = FirebaseDatabase.getInstance().getReference("Tasks");
                    reference.child(aktivnost.getKey()).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                        @Override
                        public void onSuccess(Object o) {
                            Log.d("test", "ok e 1");
                        }
                    });
                    reference.child(aktivnost.getKey()).updateChildren(hashMap2).addOnSuccessListener(new OnSuccessListener() {

                        @Override
                        public void onSuccess(Object o) {
                            Log.d("test", "ok e 2");
                        }
                    });
                    Intent intent = new Intent(context, Zavrsi_Aktivnost.class);
                    intent.putExtra("id", liceId);
                    context.startActivity(intent);
                }
            });


        }


    }


    @Override
    public int getItemCount() {
        return listTasks.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView UserName, Email, Phone, Address, DateOfPost, DueTime, AktivityName, AktivityDetails, Status, AktivnostRecuring, AktivnostUrgency, HideEmail, HidePhone;
        Button izvestaj, nazad;
        String key, id;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            UserName = (TextView) itemView.findViewById(R.id.aktivnost_username4);
            Address = (TextView) itemView.findViewById(R.id.aktivnost_location4);
            Email = (TextView) itemView.findViewById(R.id.aktivnost_email4);
            Phone = (TextView) itemView.findViewById(R.id.aktivnost_telefon4);
            DateOfPost = (TextView) itemView.findViewById(R.id.aktivnost_postavena4);
            DueTime = (TextView) itemView.findViewById(R.id.aktivnost_rok4);
            AktivityName = (TextView) itemView.findViewById(R.id.aktivnost_name4);
            AktivityDetails = (TextView) itemView.findViewById(R.id.aktivnost_details4);
            Status = (TextView) itemView.findViewById(R.id.status4);
            AktivnostRecuring = (TextView) itemView.findViewById(R.id.aktivnost_r4);
            AktivnostUrgency = (TextView) itemView.findViewById(R.id.aktivnost_u4);
            HideEmail = (TextView) itemView.findViewById(R.id.hide_email4);
            HidePhone = (TextView) itemView.findViewById(R.id.hide_phone4);
            izvestaj = (Button) itemView.findViewById(R.id.izvestaj4);
            nazad = (Button)itemView.findViewById(R.id.nazad8);

        }
    }

    private View.OnClickListener back = new View.OnClickListener() {
        public void onClick(View v) {
            // do something when the button is clicked
            Intent myIntent = new Intent(context, VolonterActivity.class);
            context.startActivity(myIntent);
        }
    };
}
