package com.example.magmaa.Fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.magmaa.API_Mange.ApiService;
import com.example.magmaa.API_Mange.WebServiceClient;
import com.example.magmaa.Comment.AllCommentActivity;
import com.example.magmaa.Comment.AllCommentResponse;
import com.example.magmaa.R;
import com.example.magmaa.Words.ArabicWordsResponse;
import com.example.magmaa.pages.Commisson.CommissionCatResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class AddCommentFragment extends Fragment implements View.OnClickListener {
     View view;
     Toast t;
     Button mcom_word;
     TextView mSubmit;
     SearchView mSearch;
     TextView mWord ,mDescreption;
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
     List<String> idWords;
     String idWord="" , nameWord="";    //for id word and send by comment_page
     Intent i;
     ListView mcomList ;
     ArrayList<AllCommentResponse> mcommentList ;
     ArrayAdapter comAdapter;
     int count=0 ,countInsert=0;
     public AddCommentFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                          Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_comment, container, false);
        init();
        fetchListSearch();
        initSpinner();
        return view;
    }
    private void init() {
        mtext = view.findViewById(R.id.text);
        mWord = view.findViewById(R.id.ar_word);
        mDescreption = view.findViewById(R.id.descr_word);
        mSearch = view.findViewById(R.id.search);
        mcom_word = view.findViewById(R.id.com_word);
        mSubmit =view.findViewById(R.id.submit);
        mcomList =view.findViewById(R.id.listCom);
        mcomList.setVisibility(GONE);
        mSubmit.setOnClickListener(this);
        mcom_word.setOnClickListener(this);
    }
    private void initSpinner() {
        spinner =view.findViewById(R.id.select_commusion);
        mList = new ArrayList<>();
        mAdapter = new ArrayAdapter<String>(getActivity() ,android.R.layout.simple_spinner_item ,spinnerArray);
        fetchJSON();
        spinner.setAdapter(mAdapter);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(mAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String vItem = parent.getItemAtPosition(position).toString();
                if (position >= 0) {
                    t=Toast.makeText(getActivity(), "تم اختيار اللجنة : " + vItem, Toast.LENGTH_SHORT);
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
            t=Toast.makeText(getActivity() , "يجب ادخال الكلمة باللغة العربية",Toast.LENGTH_LONG);
        else
            t=Toast.makeText(getActivity() , "لا توجد نتائج بحث",Toast.LENGTH_LONG);
        t.show();
        mWord.setText("معني الكلمة");
        mDescreption.setText("الوصف الخاص بها");
    }
    public void fetchListSearch(){
        mSearchList = new ArrayList<>();
        searchArray = new ArrayList<>();
        word = new ArrayList<>();
        idWords = new ArrayList<>();
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
                            idWords.add(mSearchList.get(i).getId().toString());
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
    @Override
    public void onDestroy() {
        if (t != null)
            t.cancel();
        super.onDestroy();
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //To Search
        mSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                countInsert=0;
                count=0;
                mcomList.setVisibility(GONE);
                if(searchArray.contains(query) ){
                    nameWord =query .toString();
                    for (int i = 0; i < searchArray.size(); i++){
                        if(searchArray.get(i).toString().equals(query) &&word.get(i).equals(slectCom)) {
                            mWord.setText(mSearchList.get(i).getDefinition().toString());
                            mDescreption.setText(mSearchList.get(i).getTerms().toString());
                            idWord = idWords.get(i);
                            break;
                        }
                        else
                        if(i == searchArray.size()-1){
                            t=Toast.makeText(getActivity(), "لا توجد نتائج بحث فى هذة اللجنة", Toast.LENGTH_LONG);
                            t.show();
                            mWord.setText("معني الكلمة");
                            mDescreption.setText("الوصف الخاص بها");
                        }
                    }
                    countInsert=1;
                    count=1;
                }else{
                    getLang(query);
                }
                if (query.isEmpty() ) {
                    t=Toast.makeText(getActivity(), "يجب ادخال الكلمة باللغة العربية", Toast.LENGTH_LONG);
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
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit:
                i=new Intent(getContext(), AllCommentActivity.class);
                if(countInsert==1) {
                    i.putExtra("idWord",idWord);
                    i.putExtra("nameWord",nameWord);
                    startActivity(i);
                }
                else {
                    t = Toast.makeText(getActivity(), "يجب ادخال كلمة صحيحه باللغة العربية أولآ", Toast.LENGTH_LONG);
                    t.show();
                }
                break;
            case R.id.com_word:
                if(count==1) {
                    mcomList.setVisibility(VISIBLE);
                    getComments();
                }
                else {
                    t = Toast.makeText(getActivity(), "يجب ادخال كلمة صحيحه باللغة العربية أولآ", Toast.LENGTH_LONG);
                    t.show();
                }
                break;
        }
    }
    private void getComments() {
        comAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1);
        mcommentList = new ArrayList<>();
        ApiService apiService = WebServiceClient.getRetrofit().create(ApiService.class);
        Call<List<AllCommentResponse>> call = apiService.getAllCommentOnThisWordAr(Integer.parseInt(idWord));
        call.enqueue(new Callback<List<AllCommentResponse>>() {
            @Override
            public void onResponse(Call<List<AllCommentResponse>> call, Response<List<AllCommentResponse>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.e("onSuccess", response.body().toString());
                        mcommentList = (ArrayList<AllCommentResponse>) response.body();
                        for (int i = 0; i < mcommentList.size(); i++){
                            comAdapter.add(mcommentList.get(i).getComment().toString());
                        }
                        mcomList.setAdapter(comAdapter);

                    }
                }
            }
            @Override
            public void onFailure(Call<List<AllCommentResponse>> call, Throwable t) {
                Log.e("onFailure", t.getMessage());
            }
        });
    }

}
