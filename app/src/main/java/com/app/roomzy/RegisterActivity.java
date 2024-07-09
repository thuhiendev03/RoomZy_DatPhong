package com.app.roomzy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    EditText mEmail, mPassword, mFullName;
    RelativeLayout mLoginBtn;
    Button mRegisterBtn;
    FirebaseAuth mAuth;

    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initializations();
        clickListeners();
    }

    private void initializations() {
        mAuth = FirebaseAuth.getInstance();
        mEmail = findViewById(R.id.registerEmail);
        mFullName = findViewById(R.id.registerName);
        mPassword = findViewById(R.id.registerPassword);
        mLoginBtn = findViewById(R.id.loginBtn);
        mRegisterBtn = findViewById(R.id.registerBtn);
        sharedPref = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
    }

    private void clickListeners() {
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmail.getText().toString();
                String name = mFullName.getText().toString();
                String password = mPassword.getText().toString();

                if (email.isEmpty() || name.isEmpty() || password.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    registerUser(email, password, name);
                }
            }
        });
    }

    private void registerUser(String email, String password, String name) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Đăng ký thành công, lưu thông tin người dùng vào Realtime Database
                            String userId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

                            DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Users").child(userId);

                            HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put("name", name);
                            hashMap.put("email", email);
                            hashMap.put("profile", "default");
                            hashMap.put("user_type", "staff");
                            hashMap.put("online", "false");
                            hashMap.put("userId", userId);

                            dbRef.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
//                                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
//                                        startActivity(intent);
//                                        finish();
                                        saveUserInfo(userId);
                                        navigateToMainActivity();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.e("DatabaseError", e.toString());
                                }
                            });
                        } else {
                            // Nếu đăng ký thất bại, hiển thị thông báo lỗi
                            Log.w("registerError", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void saveUserInfo(String userId) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("user_id", userId);
        editor.apply();
    }
    private void navigateToMainActivity() {
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
