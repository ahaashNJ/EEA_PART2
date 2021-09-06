package com.example.eea_part2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eea_part2.Model.Batch;
import com.example.eea_part2.Model.ModuleDTO;
import com.example.eea_part2.R;

import java.util.ArrayList;
import java.util.List;

public class ViewMyModulesAdapter extends RecyclerView.Adapter<ViewMyModulesAdapter.ViewHolder> {

    List<ModuleDTO> moduleList;
    Context context;
    List<Batch> list = new ArrayList<>();

    public ViewMyModulesAdapter(List<ModuleDTO> moduleList) {
        this.moduleList = moduleList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.my_module_card_view, parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewMyModulesAdapter.ViewHolder holder, int position) {
        ModuleDTO module = moduleList.get(position);
        holder.moduleName.setText(module.getModuleName());
        holder.lecturerEmail.setText(module.getLecturer());

        for(Batch batch: module.getBatchList()){
            holder.batchList.setText(batch.getBatchName());
        }
    }

    @Override
    public int getItemCount() {
        return moduleList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView moduleName;
        TextView lecturerEmail;
        TextView batchList;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            moduleName = itemView.findViewById(R.id.moduleNames);
            lecturerEmail = itemView.findViewById(R.id.lecturerEmail);
            batchList = itemView.findViewById(R.id.batchList);
        }
    }
}
