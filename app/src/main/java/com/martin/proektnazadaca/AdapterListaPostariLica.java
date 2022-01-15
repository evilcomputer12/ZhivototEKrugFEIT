
package com.martin.proektnazadaca;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.maps.android.SphericalUtil;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterListaPostariLica extends RecyclerView.Adapter<AdapterListaPostariLica.MyViewHolder> {
    Context context;

    ArrayList<Users> listUsers;



    View v;

    public AdapterListaPostariLica(Context context, ArrayList<Users> listUsers) {
        this.context = context;
        this.listUsers = listUsers;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        v = LayoutInflater.from(context).inflate(R.layout.postari_lica_list, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Users users = listUsers.get(position);
        String fullName = users.fname1+" "+users.lname1;
        String address = users.llocation1;
        String profilePic = users.lprofpic1;
        String getType = users.lptype1;
        String getRating = users.rating;
        String distance = users.dist;
        String uid = users.uid;
        Uri uri = Uri.parse(profilePic);
        Picasso.get().load(uri).into(holder.ProfilePic);
        holder.UserName.setText(fullName);
        holder.Address.setText(address);
        holder.Distance.setText(distance);
        if(getRating != null) {
            float val = Float.parseFloat(getRating);
            holder.ratingBar.setRating(val);
        }

        if(getType.equals("Волонтер")){
            v.setVisibility(View.GONE);
        }
        holder.ProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UserProfileDetail.class);
                intent.putExtra("profpic", profilePic);
                intent.putExtra("uname", fullName);
                intent.putExtra("dist", distance);
                intent.putExtra("addr", address);
                intent.putExtra("rate", getRating);
                intent.putExtra("uid", uid);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return listUsers.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView UserName, Distance, Address;
        CircleImageView ProfilePic;
        RatingBar ratingBar;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ProfilePic = (CircleImageView) itemView.findViewById(R.id.profPic);
            UserName = (TextView) itemView.findViewById(R.id.flname);
            Address = (TextView) itemView.findViewById(R.id.addresas);
            Distance = (TextView) itemView.findViewById(R.id.distance);
            ratingBar = (RatingBar) itemView.findViewById(R.id.rating);

        }
    }



}
