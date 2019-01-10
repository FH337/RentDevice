package com.bca.rentdevice;

import android.bluetooth.BluetoothClass;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RentActivity extends AppCompatActivity  {
    TextView head;
    String key,name,CodeReturn;
    DatabaseReference userdb,devicedb;
    EditText searchdev;
    Button rentexit;
    private RecyclerView mRecyclerView;
    private ListAdapter DevAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SwipeRefreshLayout swiperefresh;


    private  ArrayList<Devices> devicelist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent);
        key = getIntent().getExtras().getString("keyuser");
        name = getIntent().getExtras().getString("name");
        rentexit =(Button)findViewById(R.id.extrent);
        head = findViewById(R.id.headname);
        userdb = FirebaseDatabase.getInstance().getReference().child("Users").child(key);
        devicedb =FirebaseDatabase.getInstance().getReference().child("RentDevices");
        head.setText(name);
        searchdev =(EditText) findViewById(R.id.search);
        swiperefresh = findViewById(R.id.swipe_refresh);
        devicelist = new ArrayList<>();



// BB Zone
        devicelist.add(new Devices("0","BB Curve Hitam","PMB 003","-","0","Available","https://firebasestorage.googleapis.com/v0/b/rentdevice-bca.appspot.com/o/bbcurve.jpg?alt=media&token=0e7b70e0-4370-48ed-9e66-717884b01192"));
        devicelist.add(new Devices("1","BB Curve Hitam","PMB 051","-","0","Available","https://firebasestorage.googleapis.com/v0/b/rentdevice-bca.appspot.com/o/bbcurve.jpg?alt=media&token=0e7b70e0-4370-48ed-9e66-717884b01192"));
        devicelist.add(new Devices("2","BB Bold Hitsm","PMB 054","-","0","Available","https://firebasestorage.googleapis.com/v0/b/rentdevice-bca.appspot.com/o/bbbold.jpg?alt=media&token=3a650579-b991-47da-9a19-849121342e9c"));
        devicelist.add(new Devices("3","BB10 Hitam","PMB058","-","0","Available","https://firebasestorage.googleapis.com/v0/b/rentdevice-bca.appspot.com/o/bb10.jpg?alt=media&token=b584e04f-7019-443b-b574-35c0dfa2873e"));
        devicelist.add(new Devices("4","BB Silver","Kode 33","-","0","Available","https://firebasestorage.googleapis.com/v0/b/rentdevice-bca.appspot.com/o/bbsilver.jpg?alt=media&token=d789f050-2879-4953-aec2-7f202b283c02"));
        devicelist.add(new Devices("5","BB10 Pasport Hitam","PMB 059","-","0","Available","https://firebasestorage.googleapis.com/v0/b/rentdevice-bca.appspot.com/o/bbpasport.jpg?alt=media&token=98e29fa7-3ae1-496a-9305-bac267271038"));
        devicelist.add(new Devices("6","BB Hitam","-","-","0","Available","https://firebasestorage.googleapis.com/v0/b/rentdevice-bca.appspot.com/o/bbdev.jpg?alt=media&token=8f04a97b-055d-425e-8d6e-f722a38c9043"));
 //Nokia Zone
        devicelist.add(new Devices("7","Nokia Lumia 520 Hitam","PMB 024","-","0","Available","https://firebasestorage.googleapis.com/v0/b/rentdevice-bca.appspot.com/o/lumia520.jpg?alt=media&token=2d7937ea-13e8-49d8-81f8-a54dc9851d50"));
        devicelist.add(new Devices("8","Nokia Carl Zeis Hitam","PM  B 054","-","0","Available","https://firebasestorage.googleapis.com/v0/b/rentdevice-bca.appspot.com/o/lumiacz.jpg?alt=media&token=87851e8b-d63e-4ec5-b4ba-8484d5105951"));
        devicelist.add(new Devices("9","Nokia Lumia 925 Hitam","PMB 064","-","0","Available","https://firebasestorage.googleapis.com/v0/b/rentdevice-bca.appspot.com/o/lumia925.jpg?alt=media&token=d0f03d5b-5f70-4021-b555-1e44dd7ad4a2"));

// Oppo Zone
        devicelist.add(new Devices("10","Oppo F1S","CTS-PBS","-","0","Available","https://firebasestorage.googleapis.com/v0/b/rentdevice-bca.appspot.com/o/oppof1s.jpg?alt=media&token=634096f5-0e5f-47d6-8f1e-049549bd46db"));
        devicelist.add(new Devices("11","Oppo F5","PMB 102","-","0","Available","https://firebasestorage.googleapis.com/v0/b/rentdevice-bca.appspot.com/o/oppof5red.jpg?alt=media&token=99afb61d-e2ba-4934-9249-507472785fd9"));
        devicelist.add(new Devices("12","Oppo F7","PMB 112","-","0","Available","https://firebasestorage.googleapis.com/v0/b/rentdevice-bca.appspot.com/o/oppof7.jpg?alt=media&token=22e9b479-6e74-4cce-a0d9-9a58ec432551"));
        devicelist.add(new Devices("13","Oppo F5 Youth","PMB 103","-","0","Available","https://firebasestorage.googleapis.com/v0/b/rentdevice-bca.appspot.com/o/oppof5youth.jpg?alt=media&token=18377e98-ab17-47a3-889c-a0a62a52d8f7"));

//Motorola Zone
        devicelist.add(new Devices("14","Motorola Hitam","PMB 065","-","0","Available","https://firebasestorage.googleapis.com/v0/b/rentdevice-bca.appspot.com/o/motorolla.jpg?alt=media&token=212bb695-96a2-463d-adad-8c053d9873ee"));
        devicelist.add(new Devices("15","Motorola Silver","-","-","0","Available","https://firebasestorage.googleapis.com/v0/b/rentdevice-bca.appspot.com/o/motosilver.jpg?alt=media&token=cf1464d6-9862-4b24-8dc5-a031a0a1f3d0"));

//HTC Zone
        devicelist.add(new Devices("16","HTC One Putih","PMB 004","-","0","Available","https://firebasestorage.googleapis.com/v0/b/rentdevice-bca.appspot.com/o/htcputih.jpg?alt=media&token=793c0809-32cd-423d-be58-caa41f287e6b"));
        devicelist.add(new Devices("17","HTC One Hitam","PMB 110","-","0","Available","https://firebasestorage.googleapis.com/v0/b/rentdevice-bca.appspot.com/o/htchitam.jpg?alt=media&token=37915b8b-d4f5-4410-9914-d815d171d970"));

//Sony Erricson
        devicelist.add(new Devices("18","Sony Ericsson Silver","PMB 025","-","0","Available","https://firebasestorage.googleapis.com/v0/b/rentdevice-bca.appspot.com/o/sonyerrsilver.jpg?alt=media&token=d9d662a3-5f83-4685-bec2-33f6024bd973"));


//Hisense Zone
        devicelist.add(new Devices("19","Hisense Smartfren CDMA C35","PMB 067","-","0","Available","https://firebasestorage.googleapis.com/v0/b/rentdevice-bca.appspot.com/o/hisense.jpg?alt=media&token=8afe504c-8458-45a9-983f-4a1f6512cb68"));

//Polytron Zone
        devicelist.add(new Devices("20","Polytron 4G 500 Hitam","-","-","0","Available","https://firebasestorage.googleapis.com/v0/b/rentdevice-bca.appspot.com/o/polytron4g500.jpg?alt=media&token=e37361e4-daa2-4678-8394-7a52f00857a8"));
        devicelist.add(new Devices("21","Polytron P520 Putih","-","-","0","Available","https://firebasestorage.googleapis.com/v0/b/rentdevice-bca.appspot.com/o/polytronp520.png?alt=media&token=ec9aa21a-f8e7-4696-845e-427b82ea3de2"));
        devicelist.add(new Devices("22","Polytron 4G 501 Silver","-","-","0","Available","https://firebasestorage.googleapis.com/v0/b/rentdevice-bca.appspot.com/o/polytron4g501.jpg?alt=media&token=0271546d-c8ac-4824-8641-a41820605af0"));


//Nexus Zone
        devicelist.add(new Devices("23","Nexus 4 Hitam","-","-","0","Available","https://firebasestorage.googleapis.com/v0/b/rentdevice-bca.appspot.com/o/nexus4black.jpg?alt=media&token=fbdd425e-93b5-429a-9c72-c2c2419555e3"));
        devicelist.add(new Devices("24","Nexus 6 Hitam","-","-","0","Available","https://firebasestorage.googleapis.com/v0/b/rentdevice-bca.appspot.com/o/nexus6.jpg?alt=media&token=8fd1d0fe-db9d-43f3-822d-0d30eb1d14b1"));

//Vivo Zone
        devicelist.add(new Devices("25","Vivo","PMB 107","-","0","Available","https://firebasestorage.googleapis.com/v0/b/rentdevice-bca.appspot.com/o/Vivo.jpg?alt=media&token=fd1fda12-200e-422f-b104-0da1ffb6d9de"));
        devicelist.add(new Devices("26","Vivo Funtouch","PMB 125","-","0","Available","https://firebasestorage.googleapis.com/v0/b/rentdevice-bca.appspot.com/o/vivofun.jpg?alt=media&token=f859811e-49b4-40ee-bae6-e7d7dd30c854"));


//Infinix Zero
        devicelist.add(new Devices("27","Infinix Zero 5","PMB 123","-","0","Available","https://firebasestorage.googleapis.com/v0/b/rentdevice-bca.appspot.com/o/infinixzero5.jpg?alt=media&token=15a6af95-79d2-4722-b7ed-4d23cccd9393"));

//Asus Zone
        devicelist.add(new Devices("28","Asus Zenfone 2 Merah","-","-","0","Available","https://firebasestorage.googleapis.com/v0/b/rentdevice-bca.appspot.com/o/zenfone2.jpg?alt=media&token=37c87bbf-943d-41b7-9331-19567bf9eb98"));
        devicelist.add(new Devices("29","Asus X018D Biru","-","-","0","Available","https://firebasestorage.googleapis.com/v0/b/rentdevice-bca.appspot.com/o/asusx018dbiru.jpg?alt=media&token=f01d5165-c90d-4068-9210-b9d83f8ba21d"));

//Mi Zone
        devicelist.add(new Devices("30","Redmi Note 4","CTS-PBS","-","0","Available","https://firebasestorage.googleapis.com/v0/b/rentdevice-bca.appspot.com/o/minote4.jpg?alt=media&token=1f150364-5b69-4afd-8069-e469461b16f0"));
        devicelist.add(new Devices("31","MI 8","CTS-PBS","-","0","Available","https://firebasestorage.googleapis.com/v0/b/rentdevice-bca.appspot.com/o/mi8biru.jpg?alt=media&token=6bb4cce1-f405-4d1e-8b9d-4f5a99b35a30"));



// Samsung Zone
        devicelist.add(new Devices("32","Samsung Galaxy J5","CTS-PBS","-","0","Available","https://firebasestorage.googleapis.com/v0/b/rentdevice-bca.appspot.com/o/samsungj5.jpg?alt=media&token=26788c29-e775-44d4-9a0b-c8c806719647"));
        devicelist.add(new Devices("33","Samsung Galaxy S8+","CTS-PBS","-","0","Available","https://firebasestorage.googleapis.com/v0/b/rentdevice-bca.appspot.com/o/samsungs8.jpg?alt=media&token=350ad465-de94-406f-b8a7-77c7b9ad13d7"));
        devicelist.add(new Devices("34","Samsung Note 1","-","-","0","Available","https://firebasestorage.googleapis.com/v0/b/rentdevice-bca.appspot.com/o/note1.jpg?alt=media&token=4266a9ee-704c-4c53-995c-0356abb32a45"));
        devicelist.add(new Devices("35","Samsung Galaxy Tab Hitam","-","-","0","Available","https://firebasestorage.googleapis.com/v0/b/rentdevice-bca.appspot.com/o/tabhitam.jpg?alt=media&token=18808da4-996a-4db2-bb03-29c8e5d9e19d"));
        devicelist.add(new Devices("36","Samsung S4 Putih","-","-","0","Available","https://firebasestorage.googleapis.com/v0/b/rentdevice-bca.appspot.com/o/s4putih.jpeg?alt=media&token=fa1898cc-d11d-49f6-9a78-78182d819ec3"));
        devicelist.add(new Devices("37","Samsung Galaxy Tab A Silver","-","-","0","Available","https://firebasestorage.googleapis.com/v0/b/rentdevice-bca.appspot.com/o/samstabAsilver.jpg?alt=media&token=6d9f6211-4c77-42cc-8f67-4370f0f2a284"));

//Google Pixel
        devicelist.add(new Devices("38","Google Pixel","-","-","0","Available","https://firebasestorage.googleapis.com/v0/b/rentdevice-bca.appspot.com/o/googlepixel.jpeg?alt=media&token=cb79e083-f4da-42d4-9fbc-53c821e3e352"));


//Iphone Zone
        devicelist.add(new Devices("39","Iphone 6 Putih","-","-","0","Available","https://firebasestorage.googleapis.com/v0/b/rentdevice-bca.appspot.com/o/iphone6.jpg?alt=media&token=8ed45751-6669-422c-8c93-ba9f61dc316b"));
        devicelist.add(new Devices("40","Iphone 6S Gold","-","-","0","Available","https://firebasestorage.googleapis.com/v0/b/rentdevice-bca.appspot.com/o/iphone6sgold.jpg?alt=media&token=9bc71a29-9f56-4bff-9b18-4ee87d72e8f0"));
        devicelist.add(new Devices("41","Iphone X","-","-","0","Available","https://firebasestorage.googleapis.com/v0/b/rentdevice-bca.appspot.com/o/iphonexx.jpg?alt=media&token=249bb67a-a8bc-4155-9942-311c785860f1"));
        devicelist.add(new Devices("42","Iphone 7+ Hitam","-","-","0","Available","https://firebasestorage.googleapis.com/v0/b/rentdevice-bca.appspot.com/o/iphone7%2Bhitam.jpg?alt=media&token=64fbc21c-55c5-410a-b5cb-757eb70e035b"));
        devicelist.add(new Devices("43","Iphone 5S ","-","-","0","Available","https://firebasestorage.googleapis.com/v0/b/rentdevice-bca.appspot.com/o/iphone5shitam.jpg?alt=media&token=5ffe1c64-76f9-4bb7-b4e2-d1b91d4713bf"));
        devicelist.add(new Devices("44","Iphone XR","-","-","0","Available","https://firebasestorage.googleapis.com/v0/b/rentdevice-bca.appspot.com/o/iphonexrhtm.jpg?alt=media&token=065572b1-0e3e-475b-8bc5-7885b23c9db8"));
        devicelist.add(new Devices("45","Iphone 4S Hitam","-","-","0","Available","https://firebasestorage.googleapis.com/v0/b/rentdevice-bca.appspot.com/o/iphone4s.jpg?alt=media&token=dfb4065e-675b-4b03-b5a4-9e580a804615"));
        devicelist.add(new Devices("46","Iphone 6+","-","-","0","Available","https://firebasestorage.googleapis.com/v0/b/rentdevice-bca.appspot.com/o/iphone6plus.jpg?alt=media&token=daa4c029-dcf1-4bf2-8525-20f69cfd0958"));







searchdev.addTextChangedListener(new TextWatcher() {
    @Override
    public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
                filter(editable.toString());
    }
});


        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                updateList();
                swiperefresh.setRefreshing(false);

            }
        });

        swiperefresh.setColorSchemeColors(
            getResources().getColor(R.color.colorref1),
            getResources().getColor(R.color.colorref2),
            getResources().getColor(R.color.colorref3),
            getResources().getColor(R.color.colorref4)
                );


        //devicedb.setValue(devicelist); //jika db kosong

        updateList(); //update isi list
        buildRecyclerView(); //make recycler view

        rentexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userdb.child("status").setValue("0");
                Intent back = new Intent(RentActivity.this,LoginActivity.class);
                back.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(back);
                RentActivity.this.finish();
            }
        });

    }  //limit on create

    @Override
    public void onBackPressed() {
        userdb.child("status").setValue("0");
        Intent back = new Intent(RentActivity.this,LoginActivity.class);
        startActivity(back);
        finish();
    }


    public void filter (final String search){
        final ArrayList<Devices> filteredList = new ArrayList<>();

        for (Devices item : devicelist){
            if (item.getDevicename().toLowerCase().contains(search.toLowerCase()))
            {
                filteredList.add(item);
            }

          
        }

        mRecyclerView = findViewById(R.id.devlist);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        DevAdapter = new ListAdapter(filteredList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(DevAdapter);


        DevAdapter.setOnItemClickListener(new ListAdapter.OnItemClickListener() {
            @Override
            public void onReturnClick(int position) {
                int positionnew = Integer.parseInt(filteredList.get(position).getOriposition());
                String renter = devicelist.get(positionnew).getRenter();
                final String childpos = String.valueOf(positionnew);

                if( name.equals(renter)) // sesuai renter
                {
                    Toast.makeText(RentActivity.this,"Device RETURNED",Toast.LENGTH_SHORT).show();
                    devicedb.child(childpos).child("codereturn").setValue("-");
                    devicedb.child(childpos).child("statusdb").setValue("0");
                    devicedb.child(childpos).child("statuscard").setValue("Available");
                    devicedb.child(childpos).child("renter").setValue("-");

                    devicelist.get(positionnew).changeOnReturn("-","Available");
                    DevAdapter.notifyItemChanged(positionnew);
                    searchdev.setText("");
                    updateList();
                }
                else
                {
                    Toast.makeText(RentActivity.this,"Not Eligible for Returning",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onRentClick(int position) {


                int positionnew = Integer.parseInt(filteredList.get(position).getOriposition());
                String checkstats=devicelist.get(positionnew).getStatuscard();
                final String childpos = String.valueOf(positionnew);
                if(checkconnection(RentActivity.this)) {
                    if (checkstats.equals("Available")) {
                        devicedb.child(childpos).child("codereturn").setValue("-");
                        devicedb.child(childpos).child("statusdb").setValue("1");
                        devicedb.child(childpos).child("statuscard").setValue("Rented");
                        devicedb.child(childpos).child("renter").setValue(name);
                        Toast.makeText(RentActivity.this,"Device Rent Success",Toast.LENGTH_SHORT).show();
                        devicelist.get(positionnew).changeOnRent(name,"Rented");
                        DevAdapter.notifyItemChanged(positionnew);
                        searchdev.setText("");
                        updateList();
                    } else {
                        Toast.makeText(RentActivity.this, "Device not Available to Rent", Toast.LENGTH_SHORT).show();
                    }
                }

                else
                {
                    Toast.makeText(RentActivity.this, "NO INTERNET ACCESS", Toast.LENGTH_SHORT).show();
                }


            }
        });




    }





    public void buildRecyclerView(){

        mRecyclerView = findViewById(R.id.devlist);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        DevAdapter = new ListAdapter(devicelist);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(DevAdapter);





        DevAdapter.setOnItemClickListener(new ListAdapter.OnItemClickListener() {
            @Override
            public void onReturnClick(int position) {
                int positionnew = Integer.parseInt(devicelist.get(position).getOriposition());
                Log.d ("Isi position new :", String.valueOf(positionnew));
                String renter = devicelist.get(positionnew).getRenter();
                final String childpos = String.valueOf(positionnew);

                if( name.equals(renter)) // sesuai renter
                {
                    Toast.makeText(RentActivity.this,"Device RETURNED",Toast.LENGTH_SHORT).show();
                    devicedb.child(childpos).child("codereturn").setValue("-");
                    devicedb.child(childpos).child("statusdb").setValue("0");
                    devicedb.child(childpos).child("statuscard").setValue("Available");
                    devicedb.child(childpos).child("renter").setValue("-");

                    devicelist.get(positionnew).changeOnReturn("-","Available");
                    DevAdapter.notifyItemChanged(positionnew);
                }
                   else
                {
                    Toast.makeText(RentActivity.this,"Not Eligible for Returning",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onRentClick(int position) {
               int positionnew = Integer.parseInt(devicelist.get(position).getOriposition());
               Log.d ("Isi position new :", String.valueOf(positionnew));
                String checkstats=devicelist.get(positionnew).getStatuscard();
                final String childpos = String.valueOf(positionnew);
                if(checkconnection(RentActivity.this)) {
                    if (checkstats.equals("Available")) {
                        devicedb.child(childpos).child("codereturn").setValue("-");
                        devicedb.child(childpos).child("statusdb").setValue("1");
                        devicedb.child(childpos).child("statuscard").setValue("Rented");
                        devicedb.child(childpos).child("renter").setValue(name);
                        Toast.makeText(RentActivity.this,"Device Rent Success",Toast.LENGTH_SHORT).show();

                        devicelist.get(positionnew).changeOnRent(name,"Rented");
                        DevAdapter.notifyItemChanged(positionnew);
                    } else {
                        Toast.makeText(RentActivity.this, "Device not Available to Rent", Toast.LENGTH_SHORT).show();
                    }
                }

                else
                {
                    Toast.makeText(RentActivity.this, "NO INTERNET ACCESS", Toast.LENGTH_SHORT).show();
                }


            }
        });


    }


    public void showDialogRent(final int position, final ArrayList<Devices>devicelist){

        LayoutInflater inflater = this.getLayoutInflater();
        final String childpos = String.valueOf(position);

        int randomPIN = (int) (Math.random() * 9000) + 1000;//random 4 digit
        CodeReturn = String.valueOf(randomPIN);

        View titleRent = inflater.inflate(R.layout.rent_dialog_title,null);
        AlertDialog.Builder builder = new AlertDialog.Builder(RentActivity.this).setCustomTitle(titleRent);
        View dialogRent = getLayoutInflater().inflate(R.layout.rent_dialog,null);

        TextView code = (TextView)dialogRent.findViewById(R.id.retcode);
        Button ok = (Button)dialogRent.findViewById(R.id.rentbut);

        builder.setView(dialogRent);
        final AlertDialog dialog = builder.create();

        code.setText(CodeReturn);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View view) {
                devicedb.child(childpos).child("codereturn").setValue(CodeReturn);
                devicedb.child(childpos).child("statusdb").setValue("1");
                devicedb.child(childpos).child("statuscard").setValue("Rented");
                devicedb.child(childpos).child("renter").setValue(name);
                Toast.makeText(RentActivity.this,"Device Rent Success",Toast.LENGTH_SHORT).show();
                devicelist.get(position).changeOnRent(name,"Rented");
                DevAdapter.notifyItemChanged(position);
                dialog.dismiss();


            }
        });

        dialog.show();

    }

    public void showDialogReturn(final int position, final ArrayList<Devices>devicelist){
        LayoutInflater inflater = this.getLayoutInflater();
        final String childpos = String.valueOf(position);


        View titleView = inflater.inflate(R.layout.custom_dialog_title, null);

        final AlertDialog.Builder builder = new AlertDialog.Builder(RentActivity.this).setCustomTitle(titleView);
        View dialogview = getLayoutInflater().inflate(R.layout.return_dialog,null);

        final EditText input = (EditText)dialogview.findViewById(R.id.inputcoderet);
        Button confirm = dialogview.findViewById(R.id.confirm);
        Button cancel  = dialogview.findViewById(R.id.cancel);
        final Query readcode = devicedb.child(childpos).child("codereturn");

        builder.setView(dialogview);
        final AlertDialog dialog = builder.create();

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               dialog.dismiss();
            }
        });//cancel clicked

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            readcode.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.getValue().equals(input.getText().toString()))
                    {
                        Toast.makeText(RentActivity.this,"Device RETURNED",Toast.LENGTH_SHORT).show();
                        devicedb.child(childpos).child("codereturn").setValue("-");
                        devicedb.child(childpos).child("statusdb").setValue("0");
                        devicedb.child(childpos).child("statuscard").setValue("Available");
                        devicedb.child(childpos).child("renter").setValue("-");

                        devicelist.get(position).changeOnReturn("-","Available");
                        DevAdapter.notifyItemChanged(position);
                        dialog.dismiss();


                    }
                    else
                    {
                       Toast.makeText(RentActivity.this,"Wrong Code",Toast.LENGTH_SHORT).show();

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


            }
        });

        dialog.show();

    }

    public void updateList() {

       // devicelist = new ArrayList<>();

        devicedb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                devicelist = new ArrayList<>();
                for (DataSnapshot list : dataSnapshot.getChildren()) {
                    Devices devices = list.getValue(Devices.class);
                    devicelist.add(devices);
                }
                buildRecyclerView();
                swiperefresh.setRefreshing(false);
            }




            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public static boolean checkconnection(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    @Override
    protected void onPause() {
        userdb.child("status").setValue("0");
        Intent back = new Intent(RentActivity.this,LoginActivity.class);
        startActivity(back);
        finish();
        super.onPause();
    }


    @Override
    protected void onStop() {
        userdb.child("status").setValue("0");
        super.onStop();
    }
}






