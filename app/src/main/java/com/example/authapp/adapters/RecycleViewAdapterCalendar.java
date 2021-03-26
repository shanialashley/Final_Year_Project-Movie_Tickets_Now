package com.example.authapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.authapp.R;

import java.sql.Date;
import java.util.List;

public class RecycleViewAdapterCalendar extends RecyclerView.Adapter<RecycleViewAdapterCalendar.MyViewHolder>{

    private Context context;
    private List<Date> mlist;

    public RecycleViewAdapterCalendar(Context context, List<Date> mlist) {
        this.context = context;
        this.mlist = mlist;
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

        //noinspection deprecation
        holder.c_date_num.setText(mlist.get(position).getDate());
        //noinspection deprecation
        holder.c_date_week.setText(mlist.get(position).getDate());

    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView c_date_num, c_date_week;

        public MyViewHolder(View itemView){
            super(itemView);

            c_date_num = itemView.findViewById(R.id.c_date_num);
            c_date_week = itemView.findViewById(R.id.c_date_week);

        }
    }
}
