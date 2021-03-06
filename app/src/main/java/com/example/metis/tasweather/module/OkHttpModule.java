package com.example.metis.tasweather.module;

import android.support.annotation.NonNull;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

import static com.example.metis.tasweather.module.ApplicationModule.applicationContext;

public class OkHttpModule {

    private static final int MAX_DISK_CACHE_SIZE = 42 * 1024 * 1024;
    private static final long CONNECTION_TIMEOUT = 15;
    private static final long READ_TIMEOUT = 15;
    private static OkHttpClient sOkHttpClient;

    public static OkHttpClient okHttpClient() {
        if (sOkHttpClient == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.cache(new Cache(applicationContext().getCacheDir(), MAX_DISK_CACHE_SIZE))
                    .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true)
                    .addInterceptor(loggingInterceptor())
                    .addNetworkInterceptor(forceCacheInterceptor());
            sOkHttpClient = builder.build();
        }
        return sOkHttpClient;
    }

    private static Interceptor forceCacheInterceptor() {
        return chain -> {
            Response response = chain.proceed(chain.request());
            CacheControl cacheControl = new CacheControl.Builder()
                    .maxAge(2, TimeUnit.MINUTES)
                    .build();
            return response.newBuilder()
                    .header("Cache-Control", cacheControl.toString())
                    .build();
        };
    }

    @NonNull
    private static HttpLoggingInterceptor loggingInterceptor() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        return loggingInterceptor;
    }
}
