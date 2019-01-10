package com.bca.rentdevice;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.DeviceHolder> {
    private ArrayList<Devices> devlist;
    private OnItemClickListener clickListener;




    public interface OnItemClickListener{
        void onReturnClick(int position);
        void onRentClick(int position);

    }

    public  void setOnItemClickListener(OnItemClickListener listener){
        clickListener = listener;
    }



    public static class DeviceHolder extends RecyclerView.ViewHolder{

        public TextView devicename,renter,statuscard;
        public ImageView imgdev;
        public Button retdev,rentdev;

        public DeviceHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            imgdev = itemView.findViewById(R.id.imgDev);
            retdev = itemView.findViewById(R.id.retdev);
            rentdev = itemView.findViewById(R.id.rentdev);
            devicename = itemView.findViewById(R.id.devname);
            renter = itemView.findViewById(R.id.rentercard);
            statuscard = itemView.findViewById(R.id.statscard);

            retdev.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION ){
                            listener.onReturnClick(position);

                        }

                    }

                }
            });


            rentdev.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION ){

                            listener.onRentClick(position);

                        }
                    }

                }
            });

        }
    }

    public ListAdapter( ArrayList<Devices> devicelist){
            devlist = devicelist;


    }


    @NonNull
    @Override
    public DeviceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
          View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.device_item,parent,false);
          DeviceHolder dh = new DeviceHolder(v, clickListener);
          return dh;
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceHolder holder, int position) {
            Devices currentItem = devlist.get(position);
            Picasso.with(holder.itemView.getContext())
                    .load(currentItem.getImg())
                    .into(holder.imgdev);
            holder.devicename.setText(currentItem.getDevicename());
            holder.renter.setText(currentItem.getRenter());
            holder.statuscard.setText(currentItem.getStatuscard());
            if(holder.statuscard.getText().equals("Available")){

                holder.statuscard.setTextColor(Color.parseColor("#0fdb12"));//green text
                holder.retdev.getBackground().setColorFilter( 0xFF54545b, PorterDuff.Mode.MULTIPLY);//grey button
                holder.retdev.setClickable(false);
                holder.rentdev.getBackground().setColorFilter(0xFF0ddd0d, PorterDuff.Mode.MULTIPLY);//green button
                holder.rentdev.setClickable(true);
            }
            else
            {
                holder.statuscard.setTextColor(Color.parseColor("#f91d04"));//red text
                holder.rentdev.getBackground().setColorFilter(0xFF54545b, PorterDuff.Mode.MULTIPLY);//grey button
                holder.rentdev.setClickable(false);
                holder.retdev.getBackground().setColorFilter( 0xFFdd0d0d, PorterDuff.Mode.MULTIPLY);//red button
                holder.retdev.setClickable(true);

                //0xFFD2D8e0 lighter grey
                //0xFF54545b darker grey


            }


    }

    @Override
    public int getItemCount() {
        return devlist.size();
    }

    public void filterList(ArrayList<Devices> filteredlist){
            devlist = filteredlist;
            notifyDataSetChanged();


    }


}
