package me.synology.wookoo.shuttle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {
    EditText IDText,passWordText,passWordReText,companyText,nameText,phoneText;
    Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        IDText = findViewById(R.id.editTextRegisterID);
        passWordText = findViewById(R.id.editTextRegisterPassword);
        passWordReText = findViewById(R.id.editTextRegisterPasswordRe);
        companyText = findViewById(R.id.editTextRegisterCompany);
        nameText = findViewById(R.id.editTextRegisterName);
        phoneText = findViewById(R.id.editTextRegisterPhone);
        registerBtn = findViewById(R.id.registerBtn);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String id = IDText.getText().toString();
                String pw = passWordText.getText().toString();
                String pwRe = passWordReText.getText().toString();
                String company = companyText.getText().toString();
                String name = nameText.getText().toString();
                String phone = phoneText.getText().toString();




                String idRegex = "^[a-z]{1}[a-z0-9_]{5,10}$";
                if(!id.matches(idRegex)){
                    Toast.makeText(RegisterActivity.this,"아이디는 소문자와 숫자로 이뤄진 6~11자리로 설정해야 합니다.",Toast.LENGTH_SHORT).show();
                    return;
                }
                String passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d~!@#$%^&*()+|=]{8,12}$";
                if(!pw.matches(passwordRegex)){
                    Toast.makeText(RegisterActivity.this,"비밀번호는 숫자 + 영어가 포함된 최소 8~12 자리입니다.",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!pw.equals(pwRe)){
                    Toast.makeText(RegisterActivity.this,"재입력한 비밀번호가 일치하지 않습니다",Toast.LENGTH_SHORT).show();
                    return;
                }
                String companyRegex = "^[0-9]{6}";
                if(!company.matches(companyRegex)){
                    Toast.makeText(RegisterActivity.this,"업체 번호는 6자리 숫자입니다",Toast.LENGTH_SHORT).show();
                    return;
                }
                String nameRegex = "^[가-힣]{2,4}$";
                if(!name.matches(nameRegex)){
                    Toast.makeText(RegisterActivity.this,"정확한 이름을 입력해주세요",Toast.LENGTH_SHORT).show();
                    return;
                }
                String phoneRegex = "^010([0-9]{4})([0-9]{4})$";
                if (!phone.matches(phoneRegex)){
                    Toast.makeText(RegisterActivity.this,"전화번호를 확인해주세요",Toast.LENGTH_SHORT).show();
                    return;
                }

                JSONObject input = new JSONObject();
                try{

                    input.put("id",id);
                    input.put("company",company);
                    input.put("password",pw);
                    input.put("phone",phone);
                    input.put("name",name);
                    input.put("type",2);

                }
                catch (Exception e){

                }
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://192.168.1.2:8000")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                RetrofitAPI r = retrofit.create(RetrofitAPI.class);
                Log.d("넣은거",input.toString());
                Call<registerDATA> call = r.register(input);
                call.enqueue(new Callback<registerDATA>() {
                    @Override
                    public void onResponse(Call<registerDATA> call, Response<registerDATA> response) {
                        registerDATA r = response.body();
                        Log.d("rr",r.getStatus()+"");
                    }

                    @Override
                    public void onFailure(Call<registerDATA> call, Throwable t) {
                        Log.d("rt",t.getMessage()+"");
                    }
                });







            }
        });





    }
}