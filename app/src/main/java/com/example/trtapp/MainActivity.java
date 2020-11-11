package com.example.trtapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.trtapp.Models.PreUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    private Button mDriver, mCustomer;
    private ProgressDialog loadingBar;
    private TextView adminLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDriver = (Button) findViewById(R.id.driver);
        mCustomer = (Button) findViewById(R.id.customer);
        adminLink = findViewById(R.id.adminLink);
        loadingBar = new ProgressDialog(this);
        Paper.init(this);

        startService(new Intent(MainActivity.this, onAppKilled.class));
        mDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DriverLoginActivity.class);
                startActivity(intent);
                return;
            }
        });

        mCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CustomerLoginActivity.class);
                startActivity(intent);
                return;
            }
        });

        adminLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AdminLoginActivity.class);
                startActivity(intent);
                return;
            }
        });

        String UserEmail = Paper.book().read(PreUser.UserEmail);
        String UserPasswordKey = Paper.book().read(PreUser.UserPasswordKey);
        String UserType = Paper.book().read(PreUser.UserType);

        if (UserEmail != "" && UserPasswordKey != "")
        {
            if (!TextUtils.isEmpty(UserEmail)  &&  !TextUtils.isEmpty(UserPasswordKey))
            {
                loadingBar.setTitle("Already Logged in");
                loadingBar.setMessage("Please wait.....");
                loadingBar.setCanceledOnTouchOutside(false);
                loadingBar.show();

                AllowAccess(UserEmail, UserPasswordKey,UserType);
            }
        }

    }

    private void AllowAccess(String userEmail, String userPasswordKey,String type) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        loadingBar.dismiss();
        if (type.equals("Customer")) {
            if (user != null) {
                Intent intent = new Intent(MainActivity.this, CustomerMapActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        } else if (type.equals("Driver")) {
            if (user != null) {
                    Intent intent = new Intent(MainActivity.this, DMapActivity.class);
                    startActivity(intent);
                    finish();
                    return;
            }
        }else if (type.equals("Admin")) {
            if (user != null) {
                Intent intent = new Intent(MainActivity.this, RequestListActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        }
    }
}
