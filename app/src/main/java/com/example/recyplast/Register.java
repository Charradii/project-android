package com.example.recyplast;

import static java.lang.Integer.parseInt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity implements View.OnClickListener {
    EditText fullName;
    EditText email;
    EditText password;
    EditText codePostal;
    Button register;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        fullName = findViewById(R.id.fullName);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        codePostal = findViewById(R.id.codePostal);
        register = findViewById(R.id.registerButton);

        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        registerUser();
    }

    private void registerUser() {
        String Uname = fullName.getText().toString();
        String Uemail = email.getText().toString();
        String Upassword =password.getText().toString();
        int Ucodepostal = parseInt(codePostal.getText().toString());

        if(Uname.isEmpty()){
            fullName.setError("Full name is required!");
            fullName.requestFocus();
            return;
        }
        if(Uemail.isEmpty()){
            email.setError("Email is required!");
            email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(Uemail).matches()){
            email.setError("Please provide a valid email!");
            email.requestFocus();
            return;
        }
        if(Upassword.isEmpty()){
            password.setError("Password is required!");
            password.requestFocus();
            return;
        }
        if(Ucodepostal==0){
            codePostal.setError("Postal code is required!");
            codePostal.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(Uemail,Upassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user = new User(Uname,Uemail,Upassword,Ucodepostal);
                            FirebaseDatabase.getInstance("https://recyplast-d5bd2-default-rtdb.europe-west1.firebasedatabase.app").getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(Register.this,new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(Register.this, "User has been registred successfully", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(Register.this, "User not created", Toast.LENGTH_LONG).show();

                                    }
                                }
                            });
                        }else{
                            Toast.makeText(Register.this, "User not created2", Toast.LENGTH_LONG).show();

                        }
                    }
                }) ;

        }


}