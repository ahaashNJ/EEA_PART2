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
import com.example.eea_part2.Model.User;
import com.example.eea_part2.R;
import com.example.eea_part2.ViewStudents;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewStudentsAdapter extends RecyclerView.Adapter<ViewStudentsAdapter.ViewHolder>{

    List<User> userList;
    SharedPreferences sharedPreference;
    Context context;

    public ViewStudentsAdapter(List<User> userList, Context context) {
        this.userList = userList;
        this.sharedPreference = context.getSharedPreferences("sharedPreference", Context.MODE_PRIVATE);
        this.context = context;
    }

    @NonNull
    @Override
    public ViewStudentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.student_user_card, parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        User user = userList.get(position);
        holder.firstName.setText(user.getFirstName());
        holder.lastName.setText(user.getLastName());
        holder.email.setText(user.getEmail());
        holder.contactNumber.setText(user.getContactNumber());
        holder.userType.setText(user.getUserType());
        holder.batchId.setText(user.getBatchId());
        holder.deleteStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = sharedPreference.getString("token", null);
                String jwtToken = "Bearer " +name;

                Call<Void> deleteStudent = API.getRetrofit().create(CallAPI.class).deleteUser(user.getEmail(), jwtToken);

                deleteStudent.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.isSuccessful()){
                            Intent intent = new Intent(view.getContext(), ViewStudents.class);
                            context.startActivity(intent);
                            Toast.makeText(context, "Student Successfully Deleted", Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(context, "Cannot Delete Student", Toast.LENGTH_LONG).show();
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
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView firstName;
        TextView lastName;
        TextView email;
        TextView contactNumber;
        TextView userType;
        TextView batchId;
        Button deleteStudent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            firstName = itemView.findViewById(R.id.firstName);
            lastName = itemView.findViewById(R.id.lastName);
            email = itemView.findViewById(R.id.emailAddress);
            contactNumber = itemView.findViewById(R.id.contactNumber);
            userType = itemView.findViewById(R.id.userType);
            batchId = itemView.findViewById(R.id.batchId);
            deleteStudent = itemView.findViewById(R.id.deleteStudent);
        }
    }
}
