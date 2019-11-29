package com.example.remindfeedback.Network;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Set;

public class AddCookiesInterceptor implements Interceptor {
    SharedPreferences preferences;
    Context context;

    public  AddCookiesInterceptor(Context context){
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        preferences = context.getSharedPreferences("USERSIGN", 0);
        Request.Builder builder = chain.request().newBuilder();
        // Preference에서 cookies를 가져오는 작업을 수행
        builder.addHeader("Cookie", "connect.sid="+preferences.getString("Cookie",""));
        // Web,Android,iOS 구분을 위해 User-Agent세팅
        builder.removeHeader("User-Agent").addHeader("User-Agent", "Android");
        Log.e("test",builder.build().toString() );
        return chain.proceed(builder.build());
    }
}