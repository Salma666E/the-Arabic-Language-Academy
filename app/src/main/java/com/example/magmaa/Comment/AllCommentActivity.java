package com.example.magmaa.Comment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.magmaa.API_Mange.ApiService;
import com.example.magmaa.API_Mange.SessionMangment;
import com.example.magmaa.API_Mange.WebServiceClient;
import com.example.magmaa.R;
import com.example.magmaa.UserResponse;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AllCommentActivity extends AppCompatActivity {
    EditText mCommen;
    TextView mAllComments;
    Button mSend;
    String userId="";
    ArrayList<UserResponse> mList;
    SessionMangment mSH;
    Intent intent ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_comment);
        ///////////////////////////////////////////////////////////////////////
        Log.e("onCreate","userId: "+ userId);
        getUserId();
        Log.e("intro", "userId: "+ userId);
        ////////////////////////////////////////////////////////////////////////
        intro();
    }
    private void intro() {
        intent = getIntent();
        mSH =new SessionMangment(AllCommentActivity.this);
        mCommen = findViewById(R.id.comment);
        mAllComments = findViewById(R.id.all_comments);
        mSend=findViewById(R.id.submit);
        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertComment();
            }
        });
        mAllComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AllCommentActivity.this, ShowAllCommentActivity.class);
                i.putExtra("userId",userId);
                i.putExtra("idWord",intent.getStringExtra("idWord"));
                i.putExtra("nameWord",intent.getStringExtra("nameWord"));
                startActivity(i);
            }
        });
    }
    private void insertComment() {
        ApiService apiService = WebServiceClient.getRetrofit().create(ApiService.class);
        Call<AllCommentResponse> call ;
        AllCommentResponse comment ;
        Log.e("", "insertComment: "+ getIntent().getStringExtra("nameWord"));
        Log.e("","idWord  "+getIntent().getStringExtra("idWord")+" userId:  "+userId);
        if(getIntent().getStringExtra("nameWord").matches("[A-Za-z0-9]+")) { // to cs_word
            comment = new AllCommentResponse(mCommen.getText().toString(), Integer.parseInt(userId), Integer.parseInt(intent.getStringExtra("idWord")));
            call = apiService.saveCommentCs(Integer.parseInt(userId) , Integer.parseInt(intent.getStringExtra("idWord")), comment);
        }
        else {  // to ar_word
            comment = new AllCommentResponse(mCommen.getText().toString(), Integer.parseInt(userId), intent.getStringExtra("idWord"));
            call = apiService.saveCommentAr(Integer.parseInt(userId) , Integer.parseInt(intent.getStringExtra("idWord")), comment);
        }
        call.enqueue(new Callback<AllCommentResponse>() {
            @Override
            public void onResponse(Call<AllCommentResponse> call, Response<AllCommentResponse> response) {
                Toast.makeText(AllCommentActivity.this, "تم ارسال التعليق", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFailure(Call<AllCommentResponse> call, Throwable t) {
                Toast.makeText(AllCommentActivity.this, "فشل ارسال التعليق", Toast.LENGTH_SHORT).show();
            }
        });
    }
    // to get id for this user
    private void getUserId() {
        ApiService apiService = WebServiceClient.getRetrofit().create(ApiService.class);
        Call<List<UserResponse>> call = apiService.getAllUser();
        call.enqueue(new Callback<List<UserResponse>>() {
            @Override
            public void onResponse(Call<List<UserResponse>> call, Response<List<UserResponse>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.i("onSuccess", response.body().toString());
                        mList = (ArrayList<UserResponse>) response.body();
                        for (int i = 0; i < mList.size(); i++) {
                            if(mList.get(i).getEmail().equals(mSH.getUserDetails().get(mSH.KEY_EMAIL))) {
                                userId = mList.get(i).getId().toString();
                                Log.e("getUserId", "onResponse: +++++++++++++ "+ userId);
                                break;
                            }
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<List<UserResponse>>call, Throwable t) {
                Log.e("error_connection  ", "  onFailure  : " + t.getMessage()+"->"+t.getCause()+" Call"+call.toString());
            }
        });
    }
}
