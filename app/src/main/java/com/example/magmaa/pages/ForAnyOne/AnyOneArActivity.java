package com.example.magmaa.pages.ForAnyOne;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.magmaa.API_Mange.ApiService;
import com.example.magmaa.API_Mange.WebServiceClient;
import com.example.magmaa.Words.ArabicWordsResponse;
import com.example.magmaa.R;
import com.example.magmaa.pages.Commisson.CommissionCatResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnyOneArActivity extends AppCompatActivity {
    SearchView mSearch;
    TextView mWord ,mDescreption;
    Toast t;
    //to spinner
    TextView mtext;
    private Spinner spinner;
    ArrayList<CommissionCatResponse> mList;
    ArrayAdapter<String> mAdapter;
    List<String> spinnerArray = new ArrayList<>();
    //to search
    ArrayList<ArabicWordsResponse> mSearchList;
    List<String> searchArray ;
    //to commission
    String slectCom="";
    List<String> comm;
    List<String> word;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_any_one_ar);
        init();
        fetchListSearch();
        initSpinner();
    }
    private void init() {
        mtext = findViewById(R.id.text);
        mWord = findViewById(R.id.ar_word);
        mDescreption = findViewById(R.id.descr_word);
        mSearch = findViewById(R.id.search);
        //To Search
        mSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(searchArray.contains(query) ){

                     for (int i = 0; i < searchArray.size(); i++){
                        if(searchArray.get(i).toString().equals(query) &&word.get(i).equals(slectCom)) {
                            mWord.setText(mSearchList.get(i).getDefinition().toString());
                            mDescreption.setText(mSearchList.get(i).getTerms().toString());
                            break;
                        }
                        else
                        if(i == searchArray.size()-1){
                            t= Toast.makeText(AnyOneArActivity.this, "لا توجد نتائج بحث فى هذة اللجنة", Toast.LENGTH_LONG);
                            t.show();
                            mWord.setText("معني الكلمة");
                            mDescreption.setText("الوصف الخاص بها");
                        }
                    }
                }else{
                    getLang(query);
                }
                if (query.isEmpty() ) {
                    t = Toast.makeText(AnyOneArActivity.this, "يجب ادخال الكلمة باللغة العربية", Toast.LENGTH_LONG);
                    t.show();
                }
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                // adapter.getFilter().filter(newText);
                return false;
            }
        });
    }
    private void initSpinner() {
        spinner =findViewById(R.id.select_commusion);
        mList = new ArrayList<>();
        mAdapter = new ArrayAdapter<String>(AnyOneArActivity.this ,android.R.layout.simple_spinner_item ,spinnerArray);
        fetchJSON();
        spinner.setAdapter(mAdapter);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(mAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String vItem = parent.getItemAtPosition(position).toString();
                if (position >= 0) {
                    t=Toast.makeText(AnyOneArActivity.this, "تم اختيار اللجنة : " + vItem, Toast.LENGTH_SHORT);
                    t.show();
                    mtext.setText("اللجنة :"+vItem);
                        for (int i = 0; i < comm.size(); i++) {
                            if(position == i) {
                                slectCom=comm.get(i).toString();
                            break;
                            }
                        }
                    }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    private void fetchJSON(){
        ApiService apiService = WebServiceClient.getRetrofit().create(ApiService.class);
        Call<List<CommissionCatResponse>> call = apiService.getAllCom();
        call.enqueue(new Callback<List<CommissionCatResponse>>() {
            @Override
            public void onResponse(Call<List<CommissionCatResponse>> call, Response<List<CommissionCatResponse>> response) {
                 comm = new ArrayList<>();
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.i("onSuccess", response.body().toString());
                        mList = (ArrayList<CommissionCatResponse>) response.body();
                        for (int i = 0; i < mList.size(); i++){
                            mAdapter.add(mList.get(i).getName().toString());
                            comm.add(mList.get(i).getName().toString());
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<List<CommissionCatResponse>> call, Throwable t) {
            }
        });
    }
    private void getLang(String txt){
        boolean isEnglish = txt.matches("[A-Za-z0-9]+");
        if (isEnglish)
            t= Toast.makeText(AnyOneArActivity.this , "يجب ادخال الكلمة باللغة العربية",Toast.LENGTH_LONG);
        else
            t= Toast.makeText(AnyOneArActivity.this , "لا توجد نتائج بحث",Toast.LENGTH_LONG);
        t.show();
        mWord.setText("معني الكلمة");
        mDescreption.setText("الوصف الخاص بها");
    }
    public void fetchListSearch(){
        mSearchList = new ArrayList<>();
        searchArray = new ArrayList<>();
        word = new ArrayList<>();
        ApiService apiService = WebServiceClient.getRetrofit().create(ApiService.class);
        Call<List<ArabicWordsResponse>> call = apiService.getAllArWord();
        call.enqueue(new Callback<List<ArabicWordsResponse>>() {
            @Override
            public void onResponse(Call<List<ArabicWordsResponse>> call, Response<List<ArabicWordsResponse>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.e("onSuccess", response.body().toString());
                        mSearchList = (ArrayList<ArabicWordsResponse>) response.body();
                        for (int i = 0; i < mSearchList.size(); i++){
                            searchArray.add(mSearchList.get(i).getArabicWord().toString());
                            word.add(mSearchList.get(i).getCommission().toString());

                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<List<ArabicWordsResponse>> call, Throwable t) {
                Log.e("onFailure", t.getMessage());
            }
        });
    }

    public void onBackPressed() {
        AnyOneActivity.setC();
        if (t != null)
            t.cancel();
        startActivity(new Intent(AnyOneArActivity.this , AnyOneActivity.class ));
        super.onBackPressed();
    }
}


