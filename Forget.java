package com.example.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

public class Forget extends AppCompatActivity {

    EditText Email;
    Button Sent;
    AwesomeValidation awesomeValidation;
TextView mTextViewSignUp;
TextView mTextViewSignIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);

        awesomeValidation=new AwesomeValidation(ValidationStyle.BASIC);
        mTextViewSignIn=(TextView)findViewById(R.id.view_SignIn);

        mTextViewSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ForgetIntent=new Intent(Forget.this,MainActivity.class);
                startActivity(ForgetIntent);
            }
        });
        mTextViewSignUp=(TextView)findViewById(R.id.view_SingUp);
        mTextViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Forget1Intent=new Intent(Forget.this,Register.class);
                startActivity(Forget1Intent);
            }
        });
        updateUI2();
    }
    private void updateUI2 (){
        Email=(EditText)findViewById(R.id.Mail);
        Sent=(Button)findViewById(R.id.liniry2);
        awesomeValidation.addValidation(Forget.this,R.id.Mail,android.util.Patterns.EMAIL_ADDRESS,R.string.Emaill);

        Sent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (awesomeValidation.validate()){
                    Toast.makeText(Forget.this,"Data Received Succesfully",Toast.LENGTH_SHORT);

                }
                else{
                    Toast.makeText(Forget.this,"Error",Toast.LENGTH_SHORT);
                }

            }
        });

    }
}
