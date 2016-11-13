package com.muliamaulana.diloevent;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    private String mpost_key;
    private EditText nameField, emailField, phoneField, passwordField;
    private Button btnSignUp, btnSignIn;

    private MaterialDialog.Builder builderLoading;
    private MaterialDialog loading;

    private FirebaseAuth mAuth;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mpost_key = getIntent().getExtras().getString("event-id");
        nameField = (EditText) findViewById(R.id.name_field);
        emailField = (EditText) findViewById(R.id.email_field);
        phoneField = (EditText) findViewById(R.id.phone_field);
        passwordField = (EditText) findViewById(R.id.password_field);

        btnSignIn = (Button) findViewById(R.id.btn_signin);
        btnSignUp = (Button) findViewById(R.id.btn_signup);

        builderLoading = new MaterialDialog.Builder(this)
                .title("Registering User")
                .content("Please wait...")
                .progress(true, 0).canceledOnTouchOutside(false);
        loading = builderLoading.build();

        mAuth = FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance().getReference("user");

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mulaiDaftar();
            }
        });
    }

    private void mulaiDaftar() {
        final String name = nameField.getText().toString().trim();
        final String email = emailField.getText().toString().trim();
        String password = passwordField.getText().toString().trim();
        final String phone = phoneField.getText().toString().trim();

        if (TextUtils.isEmpty(name)){
            nameField.setError("Please enter your name");
            return;
        }
        if (TextUtils.isEmpty(email)){
            emailField.setError("Please enter your email");
            return;
        }
        if (!isValidEmail(email)) {
            emailField.setError("Invalid email address");
            return;
        }
        if (TextUtils.isEmpty(phone)){
            phoneField.setError("Please enter your phone number");
        }
        if (!isValidPhoneNumber(phone)){
            phoneField.setError("Invalid phone number");
        }
        if (TextUtils.isEmpty(password)){
            passwordField.setError("Please enter your password");
            return;
        }
        if (!isValidPassword(password)){
            passwordField.setError("Minimum 6 character");
            return;
        }

        loading.show();
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    FirebaseUser mUser = mAuth.getCurrentUser();
                    String user_id= mAuth.getCurrentUser().getUid();
                    mDatabase.child(mUser.getUid()).child("Nama").setValue(name);
                    mDatabase.child(mUser.getUid()).child("Phone").setValue(phone);
                    mDatabase.child(mUser.getUid()).child("Email Address").setValue(email);
                    mDatabase.child(mUser.getUid()).child("User ID").setValue(user_id);
                    mDatabase.child(mUser.getUid()).child("Event diikuti").setValue(mpost_key);

                    loading.dismiss();

                    startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                    finish();
                }
                else {
                    register_gagal();
                }
            }
        });
    }

    private void register_gagal() {
        new MaterialDialog.Builder(this)
                .content("Oops...  Ada yang salah. Registrasi gagal")
                .positiveText("Coba Lagi")
                .negativeText("Batal").canceledOnTouchOutside(false)
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                    }
                })
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        mulaiDaftar();
                    }
                })
                .show();
    }

    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    private boolean isValidPhoneNumber(String phone) {
        return  phone != null && phone.length() >= 11;
    }
    private boolean isValidPassword(String password) {
        return password != null && password.length() >= 6;
    }
}
