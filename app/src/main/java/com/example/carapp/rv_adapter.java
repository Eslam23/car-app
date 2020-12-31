package com.example.carapp;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class rv_adapter extends RecyclerView.Adapter<rv_adapter.carViewHolder > {
private ArrayList<Cars> all_car =new ArrayList<>();
private OnRecyclerViewItemClickListener listener;

    public ArrayList<Cars> getAll_car() {
        return all_car;
    }

    public void setAll_car(ArrayList<Cars> all_car) {
        this.all_car = all_car;
    }

    public rv_adapter(ArrayList<Cars> allcars, OnRecyclerViewItemClickListener listener) {
        this.all_car = allcars;
        this.listener = listener;
    }

    @NonNull
    @Override
    public carViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_car_layout,null,false);
        carViewHolder carHolder =new carViewHolder(v);
        return carHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull carViewHolder holder, int position) {
       Cars car= all_car.get(position);
       if(car.getImage()!=null && !car.getImage().isEmpty()) {
           holder.image.setImageURI(Uri.parse((car.getImage())));
       }
       else
       {
           holder.image.setImageResource(R.drawable.car2);
       }
           holder.model.setText(car.getModel());
           holder.color.setText(car.getColor());
           holder.distance.setText(String.valueOf(car.getDbl()));
           holder.car_id=car.getId();
    }

    @Override
    public int getItemCount() {
        return all_car.size();
    }

    class carViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView model;
        TextView color;
        TextView distance;
        int car_id ;


        public carViewHolder(@NonNull View itemView) {
              super(itemView);
              image =itemView.findViewById(R.id.custom_car_iv);
            model=itemView.findViewById(R.id.custom_car_tv_model);
            color =itemView.findViewById(R.id.custom_car_tv_color);
            distance =itemView.findViewById(R.id.custom_car_tv_distance);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //الفكره هنا انا مش عارف اجيب ال context  فعملت ال interface عشان اعمل call back فهوه كده هيروح ينفذ الفانكشن اللي في صفحه الماين فهيعمل ال intent من هناك عشان هناك عارف هيروح منين ولفين
                    listener.onItemClick(car_id);
                    //المشكله في السطرين اللي فوق دول وفي الداتا بيز
                }
            });
          }

      }
}
