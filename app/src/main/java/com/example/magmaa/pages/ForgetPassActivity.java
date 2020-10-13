package com.example.magmaa.pages;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.magmaa.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPassActivity extends AppCompatActivity {
    EditText mEmail;
    Button mSend;
    Toast t;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);
        mEmail=findViewById(R.id.email);
        mSend=findViewById(R.id.sent);
        mAuth = FirebaseAuth.getInstance();
        final ProgressDialog progressDialog = new ProgressDialog(ForgetPassActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("يتم التحقق ...");
        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                if(mEmail.getText().toString().isEmpty()){
                    t=Toast.makeText(ForgetPassActivity.this, "من فضلك ادخل بريدك الالكترونى بشكل صحيح", Toast.LENGTH_SHORT);
                    t.show();
                }
                else {
                    mAuth.sendPasswordResetEmail(mEmail.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        t=Toast.makeText(ForgetPassActivity.this,"تم ارسال كلمة المرور الجديدة إلى "+mEmail.getText().toString(),
                                                Toast.LENGTH_SHORT);
                                        t.show();
                                        progressDialog.dismiss();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            t=Toast.makeText(ForgetPassActivity.this, "هذا البريد الالكترونى غير مسجل لدينا", Toast.LENGTH_SHORT);
                            t.show();
                            progressDialog.dismiss();
                        }
                    });
                    mAuth.useAppLanguage();
                }
            }
        });
    }
    public void onBackPressed() {
        if (t != null)
            t.cancel();
        super.onBackPressed();
    }
}
