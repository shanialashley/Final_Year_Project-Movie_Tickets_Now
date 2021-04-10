package com.example.authapp.adapters;

import android.annotation.SuppressLint;
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
    private TimeItemClickListener listener;

    public RecycleViewScheduleAdapter(Context context, List<TimeOfMovie> tlist, TimeItemClickListener listener) {
        this.context = context;
        this.tlist = tlist;
        this.listener = listener;
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

    }

    @Override
    public int getItemCount() {
        return tlist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView ts_time;
        CardView ts_cardV;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ts_time = itemView.findViewById(R.id.d_time);
            ts_cardV = itemView.findViewById(R.id.time_cardV);
            ts_cardV.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceAsColor")
                @Override
                public void onClick(View v) {

                    ts_cardV.setCardBackgroundColor(R.color.light_orange);
                    listener.onTimeClick(tlist.get(getAdapterPosition()));

                }
            });
        }
    }

}
