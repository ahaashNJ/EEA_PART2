package com.example.eea_part2.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eea_part2.Model.User;
import com.example.eea_part2.R;

import java.util.List;

public class ViewLecturerAdapter  extends RecyclerView.Adapter<ViewLecturerAdapter.ViewHolder>{

    List<User> userList;

    public ViewLecturerAdapter(List<User> userList) {
        this.userList = userList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            firstName = itemView.findViewById(R.id.firstName);
            lastName = itemView.findViewById(R.id.lastName);
            email = itemView.findViewById(R.id.emailAddress);
            contactNumber = itemView.findViewById(R.id.contactNumber);
            userType = itemView.findViewById(R.id.userType);
        }
    }
}
