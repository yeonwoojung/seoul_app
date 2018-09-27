package org.techtown.kongmo01;

import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import android.app.Activity;
import android.os.StrictMode;
import android.widget.TextView;

import com.squareup.okhttp.ResponseBody;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.net.URL;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity {
    Retrofit retrofit;
    ApiService apiService;
    ImageView imageView;
    BitmapDrawable bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.back48dp);

        StrictMode.ThreadPolicy old = StrictMode.getThreadPolicy();
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder(old)
                .permitDiskWrites()
                .build());

        Resources res = getResources();
        bitmap = (BitmapDrawable) res.getDrawable(R.drawable.baseline_arrow_back_ios_black_48dp);
        int bitmapWidth = bitmap.getIntrinsicWidth();
        int bitmapHeight = bitmap.getIntrinsicHeight();

        imageView.setImageDrawable(bitmap);
        imageView.getLayoutParams().width = bitmapWidth;
        imageView.getLayoutParams().height = bitmapHeight;

        retrofit = new Retrofit.Builder().baseUrl(ApiService.API_URL).build();
        apiService = retrofit.create(ApiService.class);

        Call<ResponseBody> comment = apiService.getComment(1);
        comment.enqueue(new Callback<ResponseBody>(){
            @Override
            public void onResponse(Response<ResponseBody> response, Retrofit retrofit) {
                try{
                    String result = response.body().string();
                    Log.v("Test",result);
                    try{
                        JSONArray jsonArray = new JSONArray(result);
                        int postId;
                        int id;
                        String name;
                        String mail;
                        String body;
                        for(int i = 0 ; i<jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            postId = jsonObject.getInt("postId");
                            id = jsonObject.getInt("id");
                            name = jsonObject.getString("name");
                            mail = jsonObject.getString("email");
                            body = jsonObject.getString("body");
                            Log.v("Test",jsonObject.toString());
                        }
                    } catch (JSONException e){
                        e.printStackTrace();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }

        });

    }
}
