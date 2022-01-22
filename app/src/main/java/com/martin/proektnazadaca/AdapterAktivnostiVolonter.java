
package com.martin.proektnazadaca;

import static android.view.View.GONE;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterAktivnostiVolonter extends RecyclerView.Adapter<AdapterAktivnostiVolonter.MyViewHolder> {
    static Context context;

    private float rateValue;

    ArrayList<Aktivnost> listTasks;

    DatabaseReference reference;

    User lice;


    View v;

    public AdapterAktivnostiVolonter(Context context, ArrayList<Aktivnost> listTasks) {
        this.context = context;
        this.listTasks = listTasks;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        v = LayoutInflater.from(context).inflate(R.layout.item_volonter1, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Aktivnost aktivnost = listTasks.get(position);
        holder.UserName.setText(aktivnost.getLiceImeAktivnost());
        holder.Email.setText(aktivnost.getEmailLiceAktivnost());
        holder.Phone.setText(aktivnost.getTelefonLiceAktivnost());
        holder.Address.setText(aktivnost.getLokacijaAktivnost());
        holder.DateOfPost.setText(aktivnost.getTimeOfRecordCreation());
        holder.DueTime.setText(aktivnost.getKraenRokAktivnost());
        holder.AktivityName.setText(aktivnost.getImeAktivnost());
        holder.AktivityDetails.setText(aktivnost.getOpisAktivnost());
        holder.Status.setText(aktivnost.getStatus());
        holder.AktivnostRecuring.setText(aktivnost.getRecuringAktivnost());
        holder.AktivnostUrgency.setText(aktivnost.getUrgencyAktivnost());
        holder.HideEmail.setVisibility(GONE);
        holder.HidePhone.setVisibility(GONE);
        holder.Email.setVisibility(GONE);
        holder.Phone.setVisibility(GONE);
        holder.aktivnost = aktivnost;
        holder.key1 = aktivnost.key;
        String bla = aktivnost.key;
        holder.id1 = aktivnost.getId();

        holder.Address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "bla", Toast.LENGTH_SHORT).show();
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?q=loc:"+aktivnost.getLokacijaAktivnost()));
                context.startActivity(browserIntent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return listTasks.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView UserName, Email, Phone, Address, DateOfPost, DueTime, AktivityName, AktivityDetails, Status, AktivnostRecuring, AktivnostUrgency, HideEmail, HidePhone;
        Button Prijavuvanje;
        Aktivnost aktivnost;
        String key1, id1;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            UserName = (TextView) itemView.findViewById(R.id.aktivnost_username2);
            Address = (TextView) itemView.findViewById(R.id.aktivnost_location2);
            Email = (TextView) itemView.findViewById(R.id.aktivnost_email2);
            Phone = (TextView) itemView.findViewById(R.id.aktivnost_telefon2);
            DateOfPost = (TextView) itemView.findViewById(R.id.aktivnost_postavena2);
            DueTime = (TextView) itemView.findViewById(R.id.aktivnost_rok2);
            AktivityName = (TextView) itemView.findViewById(R.id.aktivnost_name2);
            AktivityDetails = (TextView) itemView.findViewById(R.id.aktivnost_details2);
            Status = (TextView) itemView.findViewById(R.id.status2);
            AktivnostRecuring = (TextView) itemView.findViewById(R.id.aktivnost_r2);
            AktivnostUrgency = (TextView) itemView.findViewById(R.id.aktivnost_u2);
            HideEmail = (TextView) itemView.findViewById(R.id.hide_email2);
            HidePhone = (TextView) itemView.findViewById(R.id.hide_phone2);
            Prijavuvanje = (Button) itemView.findViewById(R.id.prijava);
            Prijavuvanje.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        prijavivuvanjeAktivnost(key1);
                }
            });

        }
    }

    public static void prijavivuvanjeAktivnost(String id) {
        Log.d("demo", "onClick: fired " + id);
        String bla2 = id;
        DatabaseReference reference2;
        HashMap status, volonterId;
        status = new HashMap();
        volonterId = new HashMap();
        FirebaseUser volonter;
        String volonterID;
        Aktivnost aktivnost;


        volonter = FirebaseAuth.getInstance().getCurrentUser();
        reference2 = FirebaseDatabase.getInstance().getReference("Users");
        volonterID = volonter.getUid();

        status.put("status", "Пријавен волонтер");

        volonterId.put("volonter", volonterID);

        reference2 = FirebaseDatabase.getInstance().getReference("Tasks");
        DatabaseReference finalReference = reference2;
        DatabaseReference finalReference1 = reference2;
        reference2. child(id).updateChildren(status).addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                finalReference1.child(id).updateChildren(volonterId).addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {

                        Toast.makeText(context, "Се пријавите за активноста", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, VolonterAktivnostVoProcess.class);
                        context.startActivity(intent);

                    }
                });


            }
        });


    }
}
