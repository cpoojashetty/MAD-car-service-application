package com.example.sphcarservicing;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class spViewNotifications_Adapter extends RecyclerView.Adapter{

    ArrayList<spViewNotifications_Model> UserViewAppointment_Model_ArrayList;

    ItemClickListener itemClickListener;
    LayoutInflater inflater;

    public spViewNotifications_Adapter(ArrayList<spViewNotifications_Model> UserViewAppointment_Model_ArrayList1
            , Context context, ItemClickListener itemClickListener1) {
        UserViewAppointment_Model_ArrayList = UserViewAppointment_Model_ArrayList1;
        inflater = LayoutInflater.from(context);
        itemClickListener = itemClickListener1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.sp_search_appointment_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder)holder).serviceProviderName.setText(UserViewAppointment_Model_ArrayList.get(position).getSpName());
        ((ViewHolder)holder).serviceProviderAddress.setText(UserViewAppointment_Model_ArrayList.get(position).getSpAddress());
        ((ViewHolder)holder).AppointDate.setText(UserViewAppointment_Model_ArrayList.get(position).getBdate());
        ((ViewHolder)holder).AppointType.setText(UserViewAppointment_Model_ArrayList.get(position).getBtype());
        ((ViewHolder)holder).Service.setText(UserViewAppointment_Model_ArrayList.get(position).getservices());
    }

    @Override
    public int getItemCount() {
        return UserViewAppointment_Model_ArrayList.size();
    }

    String getItem(int id){
        return UserViewAppointment_Model_ArrayList.get(id).getBookingId();
    }
    String getItem1(int id){
        return UserViewAppointment_Model_ArrayList.get(id).getBdate();
    }
    String getItem2(int id){
        return UserViewAppointment_Model_ArrayList.get(id).getservices();
    }
    String getItem3(int id){
        return UserViewAppointment_Model_ArrayList.get(id).getSpemail();
    }
    String getItem4(int id){
        return UserViewAppointment_Model_ArrayList.get(id).getUemail();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView serviceProviderName;
        TextView serviceProviderAddress;
        TextView AppointDate;
        TextView AppointType;
        TextView Service;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            serviceProviderName = itemView.findViewById(R.id.userservicedname);
            serviceProviderAddress = itemView.findViewById(R.id.useremail);
            AppointDate = itemView.findViewById(R.id.txtbox);
            AppointType = itemView.findViewById(R.id.AppointDate);
            Service = itemView.findViewById(R.id.Service);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(itemClickListener!=null)
                        itemClickListener.onItemClick(view,getAdapterPosition());

                }
            });
        }
    }

    public interface ItemClickListener {
        void onItemClick(View view,int position);
    }
}
