package com.example.finalyearproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public TextView register, forgotPassword;
    private static User user;
    private EditText emailEditText, passwordEditText;
    private Button signInButton;
    private FirebaseAuth mAuth;
    private boolean success = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        emailEditText = (EditText) findViewById(R.id.email);
        passwordEditText = (EditText) findViewById(R.id.password);

        register = (TextView) findViewById(R.id.register);
        register.setOnClickListener(this);
        signInButton = (Button) findViewById(R.id.signIn);
        signInButton.setOnClickListener(this);
        forgotPassword = (TextView) findViewById(R.id.forgotPassword);
        forgotPassword.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String name = user.getDisplayName();
            String email = user.getEmail();

            boolean emailVerified = user.isEmailVerified();

            String uid = user.getUid();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.register:
                startActivity(new Intent(this, RegisterUser.class));
                break;
            case R.id.signIn:
                signIn();
                break;
            case R.id.forgotPassword:
                startActivity(new Intent(this, ForgotPassword.class));
                break;
        }

    }

    public void signIn() {
        String email= emailEditText.getText().toString().trim();
        String password= passwordEditText.getText().toString().trim();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        if (email.isEmpty()){
            emailEditText.setError("Email is Required!");
            emailEditText.requestFocus();
            return;
        }
        if(password.isEmpty()){
            passwordEditText.setError("Password is Required");
            passwordEditText.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Login com sucesso", Toast.LENGTH_LONG).show();
                            DocumentReference docRef = db.collection("users").document(email);
                            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        if (document.exists()) {
                                            Log.i(document.getData().toString(), "retrieveUserFirestore:success");
                                            user = new User(document.getString("name"), document.getString("birthDate"), document.getString("email"), document.getString("phone"), document.getBoolean("car"));
                                            if (document.getBoolean("car")) {
                                                startActivity(new Intent(getApplication(), ShareRide.class));
                                            } else {
                                                startActivity(new Intent(getApplication(), FindRide.class));
                                            }
                                            success = true;
                                        } else {
                                            Log.i("User does not exist", "retrieveUserFirestore:failure");
                                        }
                                    } else {
                                        Log.i("Error retrieving user", "retrieveUserFirestore:failure");
                                    }
                                }
                            });
                            FirebaseUser user = mAuth.getCurrentUser();

                        } else {
                            Toast.makeText(getApplicationContext(), "Login without sucess", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public static User getUser() {
        return user;
    }
}