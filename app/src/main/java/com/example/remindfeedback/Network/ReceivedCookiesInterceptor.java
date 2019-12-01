package com.example.remindfeedback.Network;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import okhttp3.Interceptor;
import okhttp3.Response;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.HashSet;

public class ReceivedCookiesInterceptor implements Interceptor {
    SharedPreferences preferences;
    Context context;

    public  ReceivedCookiesInterceptor(Context context){
        this.context = context;
    }
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            HashSet<String> cookies = new HashSet<>();
            for (String header : originalResponse.headers("Set-Cookie")) {
                cookies.add(header);
            }
            // Preference에 cookies를 넣어주는 작업을 수행
            String[] arr = cookies.toString().split(";");
            String[] arr2 = arr[0].split("=");
            Log.e("cookieInterceptor", arr2[1]);
            preferences = context.getSharedPreferences("USERSIGN", 0);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("Cookie", arr2[1]);
            editor.commit();
        }

        return originalResponse;
    }
}