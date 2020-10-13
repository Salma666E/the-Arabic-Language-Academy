package com.example.magmaa.pages.ForAnyOne;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.magmaa.API_Mange.ApiService;
import com.example.magmaa.API_Mange.WebServiceClient;
import com.example.magmaa.R;
import com.example.magmaa.Words.CsWordResponse;
import com.example.magmaa.pages.Commisson.CommissionCatResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnyOneCSActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {
    private static TextToSpeech tts;
    private static Button btnSpeak ;
    Toast t ;
    SearchView  mSearch;
    TextView mArWord,mDefination;
    //to spinner
    TextView mtext;
    private Spinner spinner;
    ArrayList<CommissionCatResponse> mList;
    ArrayAdapter<String> mAdapter;
    List<String> spinnerArray = new ArrayList<>();
    //to search
    ArrayList<CsWordResponse> mSearchList;
    List<String> searchArray ;
    //to commission
    String slectCom="";
    List<String> comm;
    List<String> word;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_any_one_cs);
        init();
        fetchListSearch();
        initSpinner();
    }
    private void init() {
        tts = new TextToSpeech(getBaseContext() ,this);
        mtext = findViewById(R.id.text);
        mArWord = findViewById(R.id.arb_word);
        mDefination = findViewById(R.id.def_word);
        btnSpeak = (Button) findViewById(R.id.btnSpeak);
        spinner = findViewById(R.id.select_commusion);
        mSearch = (SearchView) findViewById(R.id.search);
        //To Search
        mSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(searchArray.contains(query) ){
                    for (int i = 0; i < searchArray.size(); i++){
                        if(searchArray.get(i).toString().equals(query) &&word.get(i).equals(slectCom)) {
                            mArWord.setText(mSearchList.get(i).getArabicWord().toString());
                            mDefination.setText(mSearchList.get(i).getDefinition().toString());
                            break;
                        }
                        else
                            if(i == searchArray.size()-1){
                            t= Toast.makeText(AnyOneCSActivity.this, "لا توجد نتائج بحث فى هذة اللجنة", Toast.LENGTH_LONG);
                            t.show();
                            mArWord.setText("الكلمه العربي");
                            mDefination.setText("التعريف الخاص بها");
                        }
                    }
                }
                else
                    getLang(query);
                if (query.isEmpty() ) {
                    t=Toast.makeText(AnyOneCSActivity.this, "يجب ادخال المصطلح العلمى باللغة الانجليزية", Toast.LENGTH_LONG);
                    t.show();
                }
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                //show for information to this word
                // adapter.getFilter().filter(newText);
                return false;
            }
        });
    }
    private void getLang(String txt){
        boolean isEnglish = txt.matches("[A-Za-z0-9]+");
        if (isEnglish)
            t=Toast.makeText(AnyOneCSActivity.this , "لا توجد نتائج بحث",Toast.LENGTH_LONG);
        else
            t=Toast.makeText(AnyOneCSActivity.this , "يجب ادخال الكلمة باللغة الانجليزية",Toast.LENGTH_LONG);
        t.show();
        mArWord.setText("الكلمه العربي");
        mDefination.setText("التعريف الخاص بها");
    }
    private void initSpinner() {
        spinner =findViewById(R.id.select_commusion);
        mList = new ArrayList<>();
        mAdapter = new ArrayAdapter<String>(AnyOneCSActivity.this ,android.R.layout.simple_spinner_item ,spinnerArray);
        fetchJSON();
        spinner.setAdapter(mAdapter);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(mAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String vItem = parent.getItemAtPosition(position).toString();
                if (position >= 0) {
                    t=Toast.makeText(AnyOneCSActivity.this, "تم اختيار اللجنة : " + vItem, Toast.LENGTH_SHORT);
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
    public void fetchListSearch(){
        mSearchList = new ArrayList<>();
        searchArray = new ArrayList<>();
        word = new ArrayList<>();
        ApiService apiService = WebServiceClient.getRetrofit().create(ApiService.class);
        Call<List<CsWordResponse>> call = apiService.getAllCsWord();
        call.enqueue(new Callback<List<CsWordResponse>>() {
            @Override
            public void onResponse(Call<List<CsWordResponse>> call, Response<List<CsWordResponse>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.e("onSuccess", response.body().toString());
                        mSearchList = (ArrayList<CsWordResponse>) response.body();
                        for (int i = 0; i < mSearchList.size(); i++){
                            searchArray.add(mSearchList.get(i).getEnglishWord().toString());
                            word.add(mSearchList.get(i).getCommission().toString());
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<List<CsWordResponse>> call, Throwable t) {
                Log.e("onFailure", t.getMessage());
            }
        });
    }
    @Override
    public void onDestroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }
    //Speak Out Method to speak the text
    private void speakOut() {
        tts.speak( mSearch.getQuery().toString(), TextToSpeech.QUEUE_FLUSH, null);
    }
    @Override
    public void onInit(int status) {
        int result = tts.setLanguage(Locale.US);// Set Language
        if (status == TextToSpeech.SUCCESS) {
            //Language is not Supported
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                t=Toast.makeText(AnyOneCSActivity.this, "يجب الادخال باللغة الانجيزية", Toast.LENGTH_LONG);
                t.show();
            } else {
                btnSpeak.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        speakOut();
                    }
                });
            }
        } else {
            t=Toast.makeText(AnyOneCSActivity.this, "يجب الادخال باللغة الانجيزية", Toast.LENGTH_LONG);
            t.show();
        }
    }
    public void onBackPressed() {
        AnyOneActivity.setC();
        if (t != null)
            t.cancel();
        startActivity(new Intent(AnyOneCSActivity.this , AnyOneActivity.class ));
        super.onBackPressed();
    }
}