package com.example.finalyearproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener {
    private TextView banner, registerUser;
    private EditText editTextName, editTextDateOfBirth, editTextEmail, editTextPassword, editTextPhoneNumber;
    private ProgressBar progressBar;

    private boolean hasCar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        mAuth = FirebaseAuth.getInstance();


        banner = (TextView) findViewById(R.id.banner);
        banner.setOnClickListener(this);


        registerUser = (Button) findViewById(R.id.registerUser);
        registerUser.setOnClickListener(this);

        editTextName = (EditText) findViewById(R.id.name);
        editTextDateOfBirth = (EditText) findViewById(R.id.dateOfbBirth);
        editTextEmail=(EditText)  findViewById(R.id.email);
        editTextPassword=(EditText) findViewById(R.id.password);
        editTextPhoneNumber=(EditText) findViewById(R.id.phone_number);

        progressBar = (ProgressBar) findViewById(R.id.progessBar);

    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.banner:
                startActivity(new Intent(this,MainActivity.class));
                break;
            case R.id.registerUser:
            registerUser();
            break;
        }
    }
    private void registerUser(){
        String email= editTextEmail.getText().toString().trim();
        String password= editTextPassword.getText().toString().trim();
        String name= editTextName.getText().toString().trim();
        String dateOfBirth= editTextDateOfBirth.getText().toString().trim();
        String phoneNumber = editTextPhoneNumber.getText().toString().trim();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        if (name.isEmpty()){
            editTextName.setError("Name is Reguired!");
            editTextName.requestFocus();
            return;
        }
        if(dateOfBirth.isEmpty()){
            editTextDateOfBirth.setError("Age is Requiered");
            editTextDateOfBirth.requestFocus();
            return;
        }
        if(email.isEmpty()){
             editTextEmail.setError("Email is requiered");
             editTextEmail.requestFocus();
             return;

        }
        if (phoneNumber.isEmpty()) {
            editTextPhoneNumber.setError("Phone number is requiered");
            editTextPhoneNumber.requestFocus();
            return;
        }


        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please provide valid email");
            editTextEmail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            editTextPassword.setError("Password is requiered!");
            editTextPassword.requestFocus();
            return;
        }
        if(password.length()<6){
            editTextPassword.setError("Minimum password length should be 6 characters!");
            editTextPassword.requestFocus();
            return;
        }

        Map<String, Object> user = new HashMap<>();
        user.put("name", name);
        user.put("birthDate", dateOfBirth);
        user.put("car", hasCar);
        user.put("phone", phoneNumber);

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.i("User created", "createUserWithEmail:success");
                            Toast.makeText(getApplicationContext(), "Account Registered with sucess", Toast.LENGTH_LONG).show();
                            db.collection("users").document(email)
                                    .set(user)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Log.i("User created in db", "createUserFirestore:success");
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.i("User not created in db", "createUserFirestore:failure");
                                        }
                                    });
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            Log.i("Error", task.getException().toString());
                            Toast.makeText(getApplicationContext(), "Account Registered without sucess", Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()) {
            case R.id.car:
                if (checked) {
                    this.hasCar = true;
                }
                break;
            case R.id.no_car:
                if (checked) {
                    this.hasCar = false;
                }
                break;
        }
        Toast.makeText(getApplicationContext(), "Has car: " + this.hasCar, Toast.LENGTH_LONG).show();
    }
}