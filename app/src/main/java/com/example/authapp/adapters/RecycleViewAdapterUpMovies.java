package com.example.authapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.authapp.Model.Movies;
import com.example.authapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecycleViewAdapterUpMovies extends RecyclerView.Adapter<RecycleViewAdapterUpMovies.MyViewHolder> {

    private Context context;
    private List<Movies> mlist;

    public RecycleViewAdapterUpMovies(Context context, List<Movies> mlist) {
        this.context = context;
        this.mlist = mlist;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v;
        LayoutInflater inflater = LayoutInflater.from(context);
        v = inflater.inflate(R.layout.cardview_item_movie, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.m_title.setText(mlist.get(position).getTitle());
        Picasso.get().load(mlist.get(position).getThumbnail_url()).into(holder.m_thumbnail);

        //setting up listener
        holder.m_cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });


    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView m_title;
        ImageView m_thumbnail;
        CardView m_cardView;

        public MyViewHolder(View itemView){
            super(itemView);

            m_title = itemView.findViewById(R.id.uMovie_title_id);
            m_thumbnail = itemView.findViewById(R.id.uMovie_img_id);
            m_cardView = itemView.findViewById(R.id.upm_cardView);


        }

    }

}
