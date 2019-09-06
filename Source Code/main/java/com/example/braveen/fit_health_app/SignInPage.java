package com.example.braveen.fit_health_app;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInPage extends AppCompatActivity {

    EditText editEmailTxt;
    EditText editPasswordTxt;
    Button passBtn;
    Button signInBtn;
    Button signUpBtn;
    int setPassType = 0;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_page);

        firebaseAuth = FirebaseAuth.getInstance();

        editEmailTxt = findViewById(R.id.SigninPageEmailTxt);
        editPasswordTxt = findViewById(R.id.SigninPagePasswordTxt);
        passBtn = findViewById(R.id.SigninPagePasswordEye);
        signInBtn = findViewById(R.id.SigninPageSigninBtn);
        signUpBtn = findViewById(R.id.SignupPageSignupBtn);
    }

    public void SignupUser(View view){
        Intent intent = new Intent(this, SignUpPage.class);
        startActivity(intent);
    }

    public void SigninPasswordVisibility(View view){
        editPasswordTxt = findViewById(R.id.SigninPagePasswordTxt);
        passBtn = findViewById(R.id.SigninPagePasswordEye);
        if (setPassType == 0){
            editPasswordTxt.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            setPassType = 1;
            passBtn.setBackgroundResource(R.drawable.visibility_on);
        }
        else{
            editPasswordTxt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            setPassType = 0;
            passBtn.setBackgroundResource(R.drawable.visibility_off);
        }
    }

    public void SigninUser(View view){
        String email = editEmailTxt.getText().toString().trim();
        String password = editPasswordTxt.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please enter the email!", Toast.LENGTH_SHORT).show();
            return;
        }

        else if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please enter the password!", Toast.LENGTH_SHORT).show();
            return;
        }

        else if (TextUtils.getTrimmedLength(password) < 6){
            Toast.makeText(this, "Your password must have at least 6 characters!", Toast.LENGTH_SHORT).show();
            return;
        }

        else{
            firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        if (firebaseAuth.getCurrentUser().isEmailVerified()){
                            Toast.makeText(SignInPage.this, "Successfully Signed In", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignInPage.this,GoToPage.class);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(SignInPage.this, "Please verify your email!", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(SignInPage.this, "Sign In failed!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}