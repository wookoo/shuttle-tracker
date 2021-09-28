package me.synology.wookoo.shuttle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {
    EditText IDText,passWordText,passWordReText,primaryText,nameText,phoneText;
    Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        IDText = findViewById(R.id.editTextRegisterID);
        passWordText = findViewById(R.id.editTextRegisterPassword);
        passWordReText = findViewById(R.id.editTextRegisterPasswordRe);
        primaryText = findViewById(R.id.editTextRegisterPrimary);
        nameText = findViewById(R.id.editTextRegisterName);
        phoneText = findViewById(R.id.editTextRegisterPhone);
        registerBtn = findViewById(R.id.registerBtn);

        



    }
}