package it.android.bruno65.network;

import android.util.Log;

import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Callback;
import okhttp3.CipherSuite;
import okhttp3.ConnectionSpec;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.TlsVersion;

public class HttpManager {

    private static final long CONNECT_TIMEOUT = 20000;   // 2 seconds
    private static final long READ_TIMEOUT = 20000;      // 2 seconds
    private static OkHttpClient okHttpClient = null;

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    protected Request getPostRequest(String url, String postBody) {
        RequestBody body = RequestBody.create(JSON, postBody);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        return request;
    }

    protected Request getGetRequest(String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        return request;
    }

    protected void callHttpGet(String url, Callback cb) {
        callHttp(getGetRequest(url), cb);
    }

    protected void callHttpPost(String url, String postBody, Callback cb) {
        callHttp(getPostRequest(url, postBody), cb);
    }

    protected void callHttp(Request request, Callback cb) {
        Log.d("TermApp", request.toString());
        buildClient().newCall(request).enqueue(cb);
    }

    /**
     * static public void httpPost(String url, String postBody) {
     * <p>
     * OkHttpClient client = new OkHttpClient();
     * //.header("Authorization", "replace this text with your token")
     * <p>
     * client.newCall(request).enqueue(new Callback() {
     *
     * @Override public void onFailure(Call call, IOException e) {
     * call.cancel();
     * }
     * @Override public void onResponse(Call call, Response response) throws IOException {
     * <p>
     * String myResponse = response.body().string();
     * Log.d("TermpApp", myResponse);
     * }
     * });
     * }
     **/
    private OkHttpClient buildClient() {
        if (okHttpClient != null) return okHttpClient;

        ConnectionSpec spec = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                .tlsVersions(TlsVersion.TLS_1_2)

                .cipherSuites(

                        CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384)
                .build();
/*
                .cipherSuites(
                        CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384,
                        CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                        CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256,
                        CipherSuite.TLS_DHE_RSA_WITH_AES_256_GCM_SHA384,
                        CipherSuite.TLS_RSA_WITH_AES_128_GCM_SHA256,
                        CipherSuite.TLS_RSA_WITH_AES_256_GCM_SHA384)

 */
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)
                .connectionSpecs(Collections.singletonList(spec));
/*
                .hostnameVerifier(new HostnameVerifier() {
                                      @Override
                                      public boolean verify(String hostname, SSLSession session) {
                                          if (hostname.equals("65bruno.ddns.it"))
                                              return true;
                                          else
                                              return false;
                                      }
                                  }
 */
        /**
         // Logging interceptor
         HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
         httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
         okHttpClientBuilder.addInterceptor(httpLoggingInterceptor);

         // custom interceptor for adding header and NetworkMonitor sliding window
         okHttpClientBuilder.addInterceptor(new Interceptor() {
        @Override public Response intercept(Chain chain) throws IOException {
        // Add whatever we want to our request headers.
        Request request = chain.request().newBuilder().addHeader("Accept", "application/json").build();
        Response response;
        try {
        response = chain.proceed(request);
        } catch (SocketTimeoutException | UnknownHostException e) {
        e.printStackTrace();
        throw new IOException(e);
        }
        return response;
        }
        });
         **/
        //okHttpClientBuilder.
        okHttpClient = okHttpClientBuilder.build();
        return okHttpClient;
    }
}
