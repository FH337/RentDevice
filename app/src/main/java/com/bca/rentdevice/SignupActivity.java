package com.bca.rentdevice;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SignupActivity extends AppCompatActivity {


    Button back;
    EditText pinfield,userfield;
    Button submit,help;

    private FirebaseAuth signAuth;
    private ProgressBar progbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        pinfield = findViewById(R.id.pinfield);
        userfield = findViewById(R.id.userfield);
        signAuth = FirebaseAuth.getInstance();
        progbar = findViewById(R.id.progBar);
        help = (Button)findViewById(R.id.help);

        userfield.requestFocus();

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();

            }
        });

        submit = (Button)findViewById(R.id.submitdb);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RegisterUser();

            }
        });

        back = (Button)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent back = new Intent(SignupActivity.this,LoginActivity.class);
                back.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(back);
                SignupActivity.this.finish();
            }
        });


    }

    @Override
    public void onBackPressed() {
        Intent back = new Intent(SignupActivity.this,LoginActivity.class);
        back.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(back);
        SignupActivity.this.finish();
    }

    public void RegisterUser(){
        String lowerCase = "(.*[a-z].*)";
        String digit = "(.*\\d.*)";
        String symbol = "(.*[:?!@#$%^&*()].*)";
        final String emailauth =userfield.getText().toString().trim()+"@gmail.com";
        final String passauth ="123456";
        final String name = userfield.getText().toString().trim();
        final String password = pinfield.getText().toString().trim();
        final String status = "0";


        if (name.isEmpty()) {
            Toast.makeText(SignupActivity.this, "Fill Username Field", Toast.LENGTH_SHORT).show();
            userfield.requestFocus();
            return;
        }
        if (name.matches(digit)) {
            Toast.makeText(SignupActivity.this, "Only Caps Character" , Toast.LENGTH_SHORT).show();
            userfield.requestFocus();
            return;
        }
        if (name.matches(lowerCase)) {
            Toast.makeText(SignupActivity.this, "Only Caps Character" , Toast.LENGTH_SHORT).show();
            userfield.requestFocus();
            return;
        }
        if (name.matches(symbol)) {
            Toast.makeText(SignupActivity.this, "Only Caps Character" , Toast.LENGTH_SHORT).show();
            userfield.requestFocus();
            return;
        }



        if (name.length() != 3) {
            Toast.makeText(SignupActivity.this, "3 Characters Required", Toast.LENGTH_SHORT).show();
            userfield.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            Toast.makeText(SignupActivity.this, "Fill Password Field", Toast.LENGTH_SHORT).show();
            pinfield.requestFocus();
            return;
        }

        if (password.length() != 4) {
            Toast.makeText(SignupActivity.this, "4 Digit PIN Required", Toast.LENGTH_SHORT).show();
            pinfield.requestFocus();
            return;
        }

        progbar.setVisibility(View.VISIBLE);
        signAuth.createUserWithEmailAndPassword(emailauth, passauth)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            Users user = new Users(
                                    name,
                                    password,
                                    status
                            );

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    progbar.setVisibility(View.GONE);
                                    if (task.isSuccessful()) {
                                        Toast.makeText(SignupActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(SignupActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        } else {
                            progbar.setVisibility(View.GONE);
                            Toast.makeText(SignupActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }


    public void openDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this,R.style.MyDialogTheme);
        alertDialogBuilder.setTitle("Rules SIGNUP");
        alertDialogBuilder.setMessage("1.Username inisial nama 3 huruf kapital\n\n2.PIN 4 digit angka");


        alertDialogBuilder.setPositiveButton("OK",null);

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();


    }




}
