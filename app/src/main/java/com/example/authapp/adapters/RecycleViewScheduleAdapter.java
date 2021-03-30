package com.example.authapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.authapp.Model.TimeOfMovie;
import com.example.authapp.R;

import java.util.List;

public class RecycleViewScheduleAdapter extends RecyclerView.Adapter<RecycleViewScheduleAdapter.MyViewHolder> {

    private Context context;
    private List<TimeOfMovie> tlist;

    public RecycleViewScheduleAdapter(Context context, List<TimeOfMovie> tlist) {
        this.context = context;
        this.tlist = tlist;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        LayoutInflater inflater = LayoutInflater.from(context);
        v = inflater.inflate(R.layout.time_item, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.ts_time.setText(tlist.get(position).getT());
        holder.ts_cardV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return tlist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView ts_time;
        CardView ts_cardV;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ts_time = itemView.findViewById(R.id.d_time);
            ts_cardV = itemView.findViewById(R.id.time_cardV);
        }
    }

}
