package org.techtown.kongmo01;

import com.squareup.okhttp.ResponseBody;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

public interface ApiService {
    public static final String API_URL = "http://jsonplaceholder.typicode.com/";

    @GET("comments")
    Call<ResponseBody>getComment(@Query("postId")int postId);
}
