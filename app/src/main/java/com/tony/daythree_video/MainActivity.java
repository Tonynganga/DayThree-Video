package com.tony.daythree_video;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    TextInputLayout Semail, Spass;
    ProgressDialog mLoadingBar;
    Button btn_signup;
    FirebaseAuth mAuth;
    TextView alreadyreg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Semail = findViewById(R.id.email);
        Spass = findViewById(R.id.pwd);
        mLoadingBar = new ProgressDialog(this);
        btn_signup = findViewById(R.id.btn_submit);
        mAuth = FirebaseAuth.getInstance();
        alreadyreg = findViewById(R.id.alreadyregistered);

        alreadyreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LogIn.class));
            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUp();
            }
        });

    }

    private void SignUp() {

        mLoadingBar.setTitle("LogIn");
        mLoadingBar.setMessage("Logging In");
        mLoadingBar.show();
        String email = Semail.getEditText().getText().toString();
        String password = Spass.getEditText().getText().toString();

        if (email.isEmpty() && !email.endsWith("@gmail.com")){
            Toast.makeText(this, "Enter Valid Email", Toast.LENGTH_SHORT).show();
        }else  if (password.isEmpty() && password.length() < 6){
            Toast.makeText(this, "Password must be Atleast 5 Characters", Toast.LENGTH_SHORT).show();
        }else {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                mLoadingBar.dismiss();
                                Semail.getEditText().setText("");
                                Spass.getEditText().setText("");
                                Toast.makeText(MainActivity.this, "SignUp Successful", Toast.LENGTH_SHORT).show();
                            }else {
                                mLoadingBar.dismiss();
                                Semail.getEditText().setText("");
                                Spass.getEditText().setText("");
                                Toast.makeText(MainActivity.this, task.toString(), Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
        }
    }
}