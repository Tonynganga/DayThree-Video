package com.tony.daythree_video;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogIn extends AppCompatActivity {
    TextInputLayout email_, pass;
    FirebaseAuth mAuth;
    Button btn_login;
    ProgressDialog mLoadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        email_ = findViewById(R.id.email_login);
        pass = findViewById(R.id.pwd_login);
        mAuth = FirebaseAuth.getInstance();
        btn_login = findViewById(R.id.login);
        mLoadingBar = new ProgressDialog(this);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
    }

    private void signIn() {
        mLoadingBar.setTitle("LogIn");
        mLoadingBar.setMessage("Logging In");
        mLoadingBar.show();
        String email = email_.getEditText().getText().toString();
        String password = pass.getEditText().getText().toString();
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    mLoadingBar.dismiss();
                    Toast.makeText(LogIn.this, "SignIn SuccessFul", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                    intent.putExtra("email",mAuth.getCurrentUser().getEmail());
                    intent.putExtra("uid",mAuth.getCurrentUser().getUid());
                    startActivity(intent);
                }else {
                    mLoadingBar.dismiss();
                    email_.getEditText().setText("");
                    pass.getEditText().setText("");
                    Toast.makeText(LogIn.this, task.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}