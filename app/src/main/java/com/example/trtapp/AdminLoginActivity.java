package com.example.trtapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trtapp.Models.PreUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import io.paperdb.Paper;

public class AdminLoginActivity extends AppCompatActivity {

    private EditText mEmail, mPassword;
    private Button mLogin;
    private TextView login_link,register_link;
    private String accessType="login";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        mAuth = FirebaseAuth.getInstance();
        loadingBar = new ProgressDialog(this);
        Paper.init(this);

        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user != null){
                    Intent intent = new Intent(AdminLoginActivity.this,RequestListActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }
            }
        };

        login_link = (TextView) findViewById(R.id.login_link);
        register_link = (TextView) findViewById(R.id.register_link);

        login_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                mLogin.setText("Login");
                login_link.setVisibility(View.INVISIBLE);
                register_link.setVisibility(View.VISIBLE);
                accessType = mLogin.getText().toString().toLowerCase();
            }
        });

        register_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                mLogin.setText("Register");
                login_link.setVisibility(View.VISIBLE);
                register_link.setVisibility(View.INVISIBLE);
                accessType = mLogin.getText().toString().toLowerCase();
            }
        });

        mEmail = (EditText) findViewById(R.id.email);
        mPassword = (EditText) findViewById(R.id.password);

        mLogin = (Button) findViewById(R.id.login);

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = mEmail.getText().toString();
                final String password = mPassword.getText().toString();

                if (TextUtils.isEmpty(email))
                {
                    Toast.makeText(AdminLoginActivity.this, "Email is empty!", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(password))
                {
                    Toast.makeText(AdminLoginActivity.this, "Please write your password...", Toast.LENGTH_SHORT).show();
                }
                else{
                    loadingBar.setTitle("Account processing.....");
                    loadingBar.setMessage("Please wait, while we are checking the credentials.");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();
                    if(accessType.toLowerCase().equals("login")){
                        AllowAccessToAccount(email, password,"Admin");
                    }else{
                        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(AdminLoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(!task.isSuccessful()){
                                    Toast.makeText(AdminLoginActivity.this, "sign up error", Toast.LENGTH_SHORT).show();
                                }else{
                                    String user_id = mAuth.getCurrentUser().getUid();
                                    DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child("Admin").child(user_id);
                                    current_user_db.setValue(email);
                                }
                            }
                        });
                    }
                    loadingBar.dismiss();
                }
            }
        });
    }

    private void AllowAccessToAccount(String email, String password, String type) {
        Paper.book().write(PreUser.UserEmail, email);
        Paper.book().write(PreUser.UserPasswordKey,password);
        Paper.book().write(PreUser.UserType,type);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(AdminLoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    Toast.makeText(AdminLoginActivity.this, "sign in error", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(firebaseAuthListener);
    }
}
