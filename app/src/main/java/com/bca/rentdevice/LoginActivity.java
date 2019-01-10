package com.bca.rentdevice;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    Button signup,signin;
    EditText username,pin;
    ProgressBar loginload;
    TextView forget;
    DatabaseReference userdblogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginload = (ProgressBar)findViewById(R.id.prog);
        signin = (Button)findViewById(R.id.signin);
        signup = (Button)findViewById(R.id.signup);
        username = (EditText)findViewById(R.id.username);
        pin = (EditText)findViewById(R.id.pin);
        forget = (TextView)findViewById(R.id.forget);
        userdblogin = FirebaseDatabase.getInstance().getReference().child("Users");


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signup = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(signup);
                finish();
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(checkconnect(LoginActivity.this)){
                    signIn();
                    pin.onEditorAction(EditorInfo.IME_ACTION_DONE);

                }
                else
                {
                    Toast.makeText(LoginActivity.this, "NO INTERNET ACCESS", Toast.LENGTH_SHORT).show();
                }

            }
        });

       /* forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent forget = new Intent(LoginActivity.this,ForgetActivity.class);
                startActivity(forget);
                finish();
            }
        });*/


    }

    @Override
    public void onBackPressed(){
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }

    public void signIn(){
        final String name = username.getText().toString().trim();
        loginload.setVisibility(View.VISIBLE);
        Query query = FirebaseDatabase.getInstance().getReference().child("Users").orderByChild("username").equalTo(username.getText().toString().trim());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // dataSnapshot is the "issue" node with all children with id 0


                    for (DataSnapshot user : dataSnapshot.getChildren()) {
                        // do something with the individual "issues"
                        Users users = user.getValue(Users.class);
                        if(users.status.equals("0")) {
                            if (users.pin.equals(pin.getText().toString().trim())) {
                                loginload.setVisibility(View.INVISIBLE);
                                String key = user.getKey();
                                userdblogin.child(key).child("status").setValue("1");
                                Intent intent = new Intent(LoginActivity.this, RentActivity.class);
                                intent.putExtra("keyuser", key);
                                intent.putExtra("name", name);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                LoginActivity.this.finish();
                            }
                            else {
                                loginload.setVisibility(View.INVISIBLE);
                                Toast.makeText(LoginActivity.this, "Username/Password is wrong", Toast.LENGTH_SHORT).show();
                            } //else pin salah
                        }
                        else
                        {
                            loginload.setVisibility(View.INVISIBLE);
                            Toast.makeText(LoginActivity.this, "User already logged in", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else {
                    loginload.setVisibility(View.INVISIBLE);
                    Toast.makeText(LoginActivity.this, "Username/Password is wrong", Toast.LENGTH_SHORT).show(); // else ga exist
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public static boolean checkconnect(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }
}
