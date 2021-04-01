package com.example.authapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.authapp.Model.DateOfWeek;
import com.example.authapp.R;

import java.util.List;

public class RecycleViewAdapterCalendar extends RecyclerView.Adapter<RecycleViewAdapterCalendar.MyViewHolder>{

    private Context context;
    private List<DateOfWeek> wlist;
    private DateItemClickListener clickListener;



    public RecycleViewAdapterCalendar(Context context, List<DateOfWeek> wlist, DateItemClickListener listener) {
        this.context = context;
        this.wlist = wlist;
        this.clickListener = listener;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v;
        LayoutInflater inflater = LayoutInflater.from(context);
        v = inflater.inflate(R.layout.custom_calendar_item, parent, false);

        return new MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.c_date_num.setText(wlist.get(position).getDate_num());
        holder.c_date_week.setText(wlist.get(position).getDate_week());


    }

    @Override
    public int getItemCount() {
        return wlist.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView c_date_num, c_date_week;
        CardView ts_cardView;

        public MyViewHolder(View itemView){
            super(itemView);

            c_date_num = itemView.findViewById(R.id.c_date_num);
            c_date_week = itemView.findViewById(R.id.c_date_week);
            ts_cardView = itemView.findViewById(R.id.c_cardView);
            ts_cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                                clickListener.onClick(wlist.get(getAdapterPosition()));


                }
            });

        }
    }
}


