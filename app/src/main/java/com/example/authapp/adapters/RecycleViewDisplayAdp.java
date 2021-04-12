package com.example.authapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.authapp.Model.Movies;
import com.example.authapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecycleViewDisplayAdp extends RecyclerView.Adapter<RecycleViewDisplayAdp.MyViewHolder> {

    private final Context context;
    private final List<Movies> mlist;
    private final List<String> movieIdList;

    public RecycleViewDisplayAdp(Context context, List<Movies> mlist, List<String> mIdList) {
        this.context = context;
        this.mlist = mlist;
        this.movieIdList = mIdList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        LayoutInflater inflater = LayoutInflater.from(context);
        v = inflater.inflate(R.layout.single_movie_ds, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.ds_id.setText(movieIdList.get(position));
        holder.ds_t.setText(mlist.get(position).getTitle());
        holder.ds_descript.setText(mlist.get(position).getDescription());
        holder.ds_g.setText(mlist.get(position).getGenre());
        holder.ds_r.setText(mlist.get(position).getRating());
        holder.ds_s.setText(mlist.get(position).getStarring());
        holder.ds_dir.setText(mlist.get(position).getDirectors());
        holder.ds_t_link.setText(mlist.get(position).getTrailer_link());
        holder.ds_type.setText(mlist.get(position).getType());

        Picasso.get().load(mlist.get(position).getThumbnail_url()).into(holder.ds_thumb);
        Picasso.get().load(mlist.get(position).getCover_url()).into(holder.ds_cover);
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView ds_id, ds_t, ds_descript, ds_g, ds_l, ds_r, ds_s, ds_dir, ds_t_link, ds_type;
        ImageView ds_thumb, ds_cover;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            ds_id = itemView.findViewById(R.id.ds_id);
            ds_t = itemView.findViewById(R.id.ds_title);
            ds_descript= itemView.findViewById(R.id.ds_description);
            ds_g = itemView.findViewById(R.id.ds_genre);
            ds_l = itemView.findViewById(R.id.ds_length);
            ds_r = itemView.findViewById(R.id.ds_rating);
            ds_s = itemView.findViewById(R.id.ds_starring);
            ds_dir = itemView.findViewById(R.id.ds_directors);
            ds_t_link = itemView.findViewById(R.id.ds_trailer_link);
            ds_type = itemView.findViewById(R.id.ds_type);
            ds_thumb = itemView.findViewById(R.id.ds_thumbnail_img);
            ds_cover = itemView.findViewById(R.id.ds_cover_img);

        }
    }

}
