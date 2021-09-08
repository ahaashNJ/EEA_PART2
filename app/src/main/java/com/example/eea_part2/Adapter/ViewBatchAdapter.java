package com.example.eea_part2.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eea_part2.API.API;
import com.example.eea_part2.API.CallAPI;
import com.example.eea_part2.BatchList;
import com.example.eea_part2.ClassroomList;
import com.example.eea_part2.Model.Batch;
import com.example.eea_part2.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewBatchAdapter extends RecyclerView.Adapter<ViewBatchAdapter.ViewHolder> {

    List<Batch> batchList;
    SharedPreferences sharedPreference;
    Context mContext;

    public ViewBatchAdapter(List<Batch> batchList, Context context) {

        this.batchList = batchList;
        this.mContext = context;
        this.sharedPreference = context.getSharedPreferences("sharedPreference", Context.MODE_PRIVATE);
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
        Integer id = batch.getBatchId();

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = sharedPreference.getString("token", null);
                String jwtToken = "Bearer " +name;

                Call<Void> deleteBatch = API.getRetrofit().create(CallAPI.class).deleteBatch(batch.getBatchId(), jwtToken);
                deleteBatch.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.isSuccessful()){
                            Intent intent = new Intent(view.getContext(), BatchList.class);
                            mContext.startActivity(intent);
                            Toast.makeText(mContext, "Batch Successfully Deleted", Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(mContext, "Cannot Delete Batch", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(mContext, "Error Occurred While Deleting", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return batchList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView batchName;
        TextView startDate;
        TextView endDate;
        Button delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            batchName = itemView.findViewById(R.id.batch);
            startDate = itemView.findViewById(R.id.startDate);
            endDate = itemView.findViewById(R.id.endDate);
            delete = itemView.findViewById(R.id.deleteBatch);
        }
    }
}
