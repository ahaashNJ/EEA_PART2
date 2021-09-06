package com.example.eea_part2.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eea_part2.Model.Batch;
import com.example.eea_part2.R;

import java.util.List;

public class ViewBatchAdapter extends RecyclerView.Adapter<ViewBatchAdapter.ViewHolder> {

    List<Batch> batchList;

    public ViewBatchAdapter(List<Batch> batchList) {
        this.batchList = batchList;
    }

    @NonNull
    @Override
    public ViewBatchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.batch_card_view, parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewBatchAdapter.ViewHolder holder, int position) {

        Batch batch = batchList.get(position);
        holder.batchName.setText(batch.getBatchName());
        holder.startDate.setText(batch.getStartDate());
        holder.endDate.setText(batch.getEndDate());
    }

    @Override
    public int getItemCount() {
        return batchList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView batchName;
        TextView startDate;
        TextView endDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            batchName = itemView.findViewById(R.id.batch);
            startDate = itemView.findViewById(R.id.startDate);
            endDate = itemView.findViewById(R.id.endDate);
        }
    }
}
