package com.example.magmaa.pages.Login;

import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.magmaa.API_Mange.ApiService;
import com.example.magmaa.API_Mange.WebServiceClient;
import com.example.magmaa.R;
import com.example.magmaa.API_Mange.SessionMangment;
import com.example.magmaa.pages.ForAnyOne.AnyOneActivity;
import com.example.magmaa.pages.ForgetPassActivity;
import com.example.magmaa.pages.MainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    Toast t;
    EditText mEmail,  mPass ,mUserName  ;
    Button mLoginBtn;
    TextView mForgetPass ;
    SessionMangment mSessionMangment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
    }
    private void initViews() {
        mSessionMangment=new SessionMangment(this);
        mUserName = findViewById(R.id.username);
        mEmail = findViewById(R.id.email_et);
        mPass = findViewById(R.id.pass_et);
        mLoginBtn = findViewById(R.id.login_btn);
        mForgetPass = findViewById(R.id.forget_pass);
        mLoginBtn.setOnClickListener(this);
        mForgetPass.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                if (validateParams()){
                    callLoginApi();
                }
                break;
            case R.id.forget_pass:
                startActivity(new Intent(LoginActivity.this, ForgetPassActivity.class));
                mPass.setText("");
                break;
        }
    }
    boolean validateParams() {
        if (mUserName.getText().toString().isEmpty()) {
            t=Toast.makeText(LoginActivity.this, "من فضلك ادخل الاسم بشكل صحيح", Toast.LENGTH_LONG);
            t.show();
            return false;
        } else if (mEmail.getText().toString().isEmpty() || notValidEmail() ) {
            t=Toast.makeText(LoginActivity.this, "من فضلك ادخل بريدك الالكترونى بشكل صحيح", Toast.LENGTH_LONG);
            t.show();
            return false;
        }else if (mPass.getText().toString().isEmpty()) {
            t=Toast.makeText(LoginActivity.this, "من فضلك ادخل رمز المرور بشكل صحيح", Toast.LENGTH_LONG);
            t.show();
            return false;
        }  else if (mPass.getText().toString().length() < 6) {
            t=Toast.makeText(LoginActivity.this, "من فضلك ادخل رمز المرور اكبر من 6 حروف", Toast.LENGTH_LONG);
            t.show();
            return false;
        } else {
            return true;
        }
    }
    private boolean notValidEmail() {
        String emailInput = mEmail.getText().toString().trim();
        String emailPattern = "^[_a-z]+[0-9]+@(google|yahoo|gmail)(\\.com)$";
       if (emailInput.matches(emailPattern)) {
            return false;
        } else {
            return true ;
        }
    }
    public void onBackPressed() {
        AnyOneActivity.setC();
        if (t != null)
            t.cancel();
        startActivity(new Intent(LoginActivity.this , AnyOneActivity.class ));
        super.onBackPressed();
    }
    void callLoginApi() {
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("انتظر لحظة ...");
        progressDialog.show();
        ApiService apiService = WebServiceClient.getRetrofit().create(ApiService.class);
        Call<LoginResponse> call = apiService.login(
                mEmail.getText().toString(),
                mPass.getText().toString());
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                //progressDialog.show();
                if (response.body() != null){
                    mSessionMangment.createLoginSession(true,mUserName.getText().toString()
                            ,mEmail.getText().toString());
                    t=Toast.makeText(LoginActivity.this, "تم تسجيل الدخول بنجاح", Toast.LENGTH_SHORT);
                    t.show();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    mUserName.setText("");
                    mEmail.setText("");
                    mPass.setText("");
                    progressDialog.dismiss();
                } else {
                    Toast.makeText(LoginActivity.this, "فشل تسجيل الدخول", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            }
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.e("body", "فشل تسجيل");
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, "فشل تسجيل الدخول", Toast.LENGTH_LONG).show();

            }
        });
    }
}
