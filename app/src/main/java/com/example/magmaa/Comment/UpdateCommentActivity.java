package com.example.magmaa.Comment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.magmaa.API_Mange.ApiService;
import com.example.magmaa.API_Mange.WebServiceClient;
import com.example.magmaa.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateCommentActivity extends AppCompatActivity {
    String commentMsg;
    EditText mcomment;
    Button mUpdate;
    AllCommentResponse comment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_comment);
        inti();
    }

    private void inti() {
        commentMsg =getIntent().getStringExtra("commentMsg");
        mcomment = findViewById(R.id.comment);
        mUpdate = findViewById(R.id.update);
        mcomment.setText(commentMsg);
        mUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upDateCommentApi();
            }
        });
    }

    private void upDateCommentApi() {
        ApiService apiService = WebServiceClient.getRetrofit().create(ApiService.class);
        Call<AllCommentResponse> call ;
        if(getIntent().getStringExtra("idCsWord").equals("")) { //to Ar word
            comment = new AllCommentResponse(
                    mcomment.getText().toString(),
                    Integer.parseInt(getIntent().getStringExtra("userId")),
                    getIntent().getStringExtra("idArWord"));
            call = apiService.updateCommentAr(
                    Integer.parseInt(getIntent().getStringExtra("idCom")) ,
                    comment);
        }
        else {    // to Cs word
            comment = new AllCommentResponse(
                    mcomment.getText().toString(),
                    Integer.parseInt(getIntent().getStringExtra("userId")),
                    Integer.parseInt(getIntent().getStringExtra("idCsWord")));
            call = apiService.updateCommentCs(
                    Integer.parseInt(getIntent().getStringExtra("idCom")),
                    comment);
        }
        call.enqueue(new Callback<AllCommentResponse>() {
            @Override
            public void onResponse(Call<AllCommentResponse> call, Response<AllCommentResponse> response) {
                Toast.makeText(UpdateCommentActivity.this, "تم تعديل هذا التعليق", Toast.LENGTH_SHORT).show();
                Log.e("", "onResponse: "+ response.message() );
            }
            @Override
            public void onFailure(Call<AllCommentResponse> call, Throwable t) {
                Toast.makeText(UpdateCommentActivity.this, "فشل تعديل هذا التعليق", Toast.LENGTH_SHORT).show();
                Log.e("", "onFailure: "+t.getMessage() );
            }
        });
    }
}
