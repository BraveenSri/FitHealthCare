package com.example.braveen.fit_health_app;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class SignUpPage extends AppCompatActivity {

    EditText editEmailTxt;
    EditText editPassword1Txt;
    EditText editPassword2Txt;
    Button pass1Btn;
    Button pass2Btn;
    Button signUpBtn;
    Button verifyBtn;
    int setPass1Type = 0;
    int setPass2Type = 0;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        firebaseAuth = FirebaseAuth.getInstance();

        editEmailTxt = findViewById(R.id.SignupPageEmailTxt);
        editPassword1Txt = findViewById(R.id.SignupPagePassword1Txt);
        editPassword2Txt = findViewById(R.id.SignupPagePassword2Txt);
        pass1Btn = findViewById(R.id.SignupPagePassword1Eye);
        pass2Btn = findViewById(R.id.SignupPagePassword2Eye);
        signUpBtn = findViewById(R.id.SignupPageSignupBtn);
        verifyBtn = findViewById(R.id.SignupPageVerifyBtn);

        signUpBtn.setVisibility(View.VISIBLE);
        verifyBtn.setVisibility(View.INVISIBLE);
    }

    public void SigninUser(View view){
        Intent intent = new Intent(this, SignInPage.class);
        startActivity(intent);
    }

    public void SignupPassword1Visibility(View view){
        if (setPass1Type == 0){
            editPassword1Txt.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            setPass1Type = 1;
            pass1Btn.setBackgroundResource(R.drawable.visibility_on);

        }
        else{
            editPassword1Txt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            setPass1Type = 0;
            pass1Btn.setBackgroundResource(R.drawable.visibility_off);
        }
    }

    public void SignupPassword2Visibility(View view){
        if (setPass2Type == 0){
            editPassword2Txt.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            setPass2Type = 1;
            pass2Btn.setBackgroundResource(R.drawable.visibility_on);

        }
        else{
            editPassword2Txt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            setPass2Type = 0;
            pass2Btn.setBackgroundResource(R.drawable.visibility_off);
        }
    }

    public void SignupUser(View view){
        String email = editEmailTxt.getText().toString().trim();
        String password1 = editPassword1Txt.getText().toString().trim();
        String password2 = editPassword2Txt.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            Toast.makeText(this, "Please enter the email!", Toast.LENGTH_SHORT).show();
            return;
        }

        else if (TextUtils.isEmpty(password1)){
            Toast.makeText(this, "Please enter the password!", Toast.LENGTH_SHORT).show();
            return;
        }

        else if (TextUtils.isEmpty(password2)){
            Toast.makeText(this, "Please enter the password!", Toast.LENGTH_SHORT).show();
            return;
        }

        else if ((TextUtils.getTrimmedLength(password1) < 6) || (TextUtils.getTrimmedLength(password2) < 6)){
            Toast.makeText(this, "Your password must have at least 6 characters!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.equals(password1,password2)){
            firebaseAuth.createUserWithEmailAndPassword(email, password1).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        Toast.makeText(SignUpPage.this, "Sign Up failed!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(SignUpPage.this, "Successfully Signed Up", Toast.LENGTH_SHORT).show();
                        signUpBtn.setVisibility(View.INVISIBLE);
                        verifyBtn.setVisibility(View.VISIBLE);
                        editPassword2Txt.setVisibility(View.INVISIBLE);
                        pass2Btn.setVisibility(View.INVISIBLE);
                    }
                }
            });
        }
        else {
            Toast.makeText(this, "Password doesn't match!", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    public void VerifyUser(View view){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.sendEmailVerification().addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(SignUpPage.this, "Verification email sent to " + firebaseAuth.getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();
                    setPass1Type = 0;
                    setPass2Type = 0;
                    signUpBtn.setVisibility(View.VISIBLE);
                    verifyBtn.setVisibility(View.INVISIBLE);
                    editPassword2Txt.setVisibility(View.VISIBLE);
                    pass2Btn.setVisibility(View.VISIBLE);
                    editEmailTxt.setText("");
                    editPassword1Txt.setText("");
                    editPassword2Txt.setText("");
                }
                else{
                    Toast.makeText(SignUpPage.this, "Failed to send verification!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
