package com.example.magmaa.API_Mange;

import com.example.magmaa.UserResponse;
import com.example.magmaa.Comment.AllCommentResponse;
import com.example.magmaa.Words.ArabicWordsResponse;
import com.example.magmaa.Words.CsWordResponse;
import com.example.magmaa.pages.Login.LoginResponse;
import com.example.magmaa.LogoutResponse;
import com.example.magmaa.pages.ShowPost.PostCatResponse;
import com.example.magmaa.pages.Commisson.CommissionCatResponse;
import com.example.magmaa.pages.GeneralMeeting.GMeetingCatResponse;
import com.example.magmaa.pages.Meeting.MeetingCatResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {

    @GET("meetings")
    Call<List<MeetingCatResponse>> getAllMeeting();

    @GET("general__meetings")
    Call<List<GMeetingCatResponse>> getAllGMeeting();

    @GET("commissions")
    Call<List<CommissionCatResponse>> getAllCom();

    @GET("posts")
    Call<List<PostCatResponse>> getAllPost();

    @GET("users")
    Call<List<UserResponse>> getAllUser();

    @GET("arabic__words")
    Call<List<ArabicWordsResponse>> getAllArWord();

    @GET("words")
    Call<List<CsWordResponse>> getAllCsWord();

    @FormUrlEncoded
    @POST("auth/login")
    Call<LoginResponse> login(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("auth/logout")
    Call<LogoutResponse> logout(@Field("token") String token);

    @GET("comments")
    Call<List<AllCommentResponse>> getAllComment();

    //to Delete Comment
    @DELETE("comments/{id}")
    Call<Void> deleteComment(@Path("id") int commentId);

    //to Update AR Comment
    @PUT("arabic-comments/{id}")
    Call<AllCommentResponse> updateCommentAr(@Path("id") int userId  , @Body AllCommentResponse upComment);

    //to insert new Comment of Ar_word
    @POST("arabic-comments/{id}/{name}")
    Call<AllCommentResponse> saveCommentAr(@Path("id") int userId , @Path("name") int wordId , @Body AllCommentResponse comment );

    //to insert new Comment of Cs_word
    @POST("word-comments/{id}/{wid}")
    Call<AllCommentResponse> saveCommentCs(@Path("id") int userId , @Path("wid") int wordId , @Body AllCommentResponse comment );
    //to Update CS Comment
    @PUT("word-comments/{id}")
    Call<AllCommentResponse> updateCommentCs(@Path("id") int userId  , @Body AllCommentResponse upComment);

    @GET("word-comments/{wordid}")
    Call<List<AllCommentResponse>> getAllCommentOnThisWordCs(@Path("wordid") int wordid);

    @GET("arabic-comments/{wordid}")
    Call<List<AllCommentResponse>> getAllCommentOnThisWordAr(@Path("wordid") int wordid);

}




