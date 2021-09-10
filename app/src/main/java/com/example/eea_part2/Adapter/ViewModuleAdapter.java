package com.example.eea_part2.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eea_part2.API.API;
import com.example.eea_part2.API.CallAPI;
import com.example.eea_part2.AddModule;
import com.example.eea_part2.AddTimetable;
import com.example.eea_part2.BatchList;
import com.example.eea_part2.Model.Batch;
import com.example.eea_part2.Model.Module;
import com.example.eea_part2.Model.ModuleDTO;
import com.example.eea_part2.ModuleList;
import com.example.eea_part2.R;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewModuleAdapter extends RecyclerView.Adapter<ViewModuleAdapter.ViewHolder>{

    List<ModuleDTO> moduleList;
    Context context;
    List<Batch> list = new ArrayList<>();
    SharedPreferences sharedPreference;


    public ViewModuleAdapter(List<ModuleDTO> moduleList, Context context) {
        this.moduleList = moduleList;
        this.context = context;
        this.sharedPreference = context.getSharedPreferences("sharedPreference", Context.MODE_PRIVATE);

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.module_card_view, parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ModuleDTO module = moduleList.get(position);
        holder.moduleName.setText(module.getModuleName());
        holder.lecturerEmail.setText(module.getLecturer());

        for(Batch batch: module.getBatchList()){
            holder.batchList.setText(batch.getBatchName());
        }

        holder.scheduleLecture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), AddTimetable.class);


                ModuleDTO moduleDTO = new ModuleDTO();
                moduleDTO.setModuleName(module.getModuleName());
                moduleDTO.setLecturer(module.getLecturer());

                for(Batch batch: module.getBatchList()){
                    list.add(batch);
                }
                moduleDTO.setBatchList(list);
                intent.putExtra("Lecture", moduleDTO);
                view.getContext().startActivity(intent);
            }
        });

        holder.deleteModule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = sharedPreference.getString("token", null);
                String jwtToken = "Bearer " +name;

                Call<Void> deleteModule = API.getRetrofit().create(CallAPI.class).deleteBatch(module.getModuleId(), jwtToken);

                deleteModule.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.isSuccessful()){
                            Intent intent = new Intent(view.getContext(), ModuleList.class);
                            context.startActivity(intent);
                            Toast.makeText(context, "Module Successfully Deleted", Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(context, "Cannot Delete Module", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(context, "Error Occurred While Deleting", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return moduleList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView moduleName;
        TextView lecturerEmail;
        TextView batchList;
        Button scheduleLecture;
        Button deleteModule;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            moduleName = itemView.findViewById(R.id.moduleNames);
            lecturerEmail = itemView.findViewById(R.id.lecturerEmail);
            batchList = itemView.findViewById(R.id.batchList);
            scheduleLecture = itemView.findViewById(R.id.scheduleLecture);
            deleteModule = itemView.findViewById(R.id.deleteModule);
        }

    }
}
