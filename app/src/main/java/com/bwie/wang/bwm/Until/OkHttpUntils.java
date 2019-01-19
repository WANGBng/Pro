package com.bwie.wang.bwm.Until;

import android.content.SharedPreferences;
import android.text.TextUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.content.Context.MODE_PRIVATE;

public class OkHttpUntils<T> {
    /**
     * 外网访问，域名为：http://mobile.bwstudent.com/
     * 网址域名。
     */
//    private final String BASE_PATH = "http://172.17.8.100/small/";
    private final String BASE_PATH = "http://mobile.bwstudent.com/small/";
    /**
     * 1.单例
     */
    private static OkHttpUntils instance;
    private final OkHttpClient mClient;
    private final Retrofit mRetrofit;
    private final BaseApis mBaseApis;

    public static OkHttpUntils getInstance() {
        if (instance == null) {
            synchronized (OkHttpUntils.class) {
                instance = new OkHttpUntils();
            }
        }
        return instance;
    }

    public OkHttpUntils() {
        //2.日志拦截
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //3.客户端
        mClient = new OkHttpClient.Builder()
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(new Interceptor() {
                    @Override               //链
                    public Response intercept(Chain chain) throws IOException {
                        //拿到请求：
                        Request request = chain.request();
                        //取出保存的userID、sessionID
                        SharedPreferences shared = BaseApplication.getApplication().getSharedPreferences("spDemo", MODE_PRIVATE);
                        String userId = shared.getString("userId", "");
                        String sessionId = shared.getString("sessionId", "");
                        //重新构造请求
                        Request.Builder newBuilder = request.newBuilder();
                        //把原来请求的参数原样放进去
                        newBuilder.method(request.method(), request.body());
                        //添加特殊的userId,sessionId
                        if (!TextUtils.isEmpty(userId) && !TextUtils.isEmpty(sessionId)) {
                            newBuilder.addHeader("userId", userId);
                            newBuilder.addHeader("sessionId", sessionId);
                        }
                        //打包
                        Request build = newBuilder.build();
                        //返回response
                        return chain.proceed(build);
                    }
                })
                .build();
        //4.retorfit
        mRetrofit = new Retrofit.Builder().client(mClient)
                .baseUrl(BASE_PATH)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        mBaseApis = mRetrofit.create(BaseApis.class);
    }

    /**
     * 5.接口
     */
    public interface CallBack {
        void fail(String error);

        void success(String data);
    }

    private CallBack mCallBack;

    public void setCallBack(CallBack callBack) {
        mCallBack = callBack;
    }

    /**
     * 6.Observer
     */
    private Observer getObserver(final CallBack mCallBack) {
        Observer observer = new Observer<ResponseBody>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                if (mCallBack != null) {
                    mCallBack.fail(e.getMessage());
                }
            }


            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String string = responseBody.string();
                    if (mCallBack != null) {
                        mCallBack.success(string);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (mCallBack != null) {
                        mCallBack.fail(e.getMessage());
                    }
                }
            }

        };
        return observer;
    }

    /**
     * 7.get请求
     */
    public void get(String path, CallBack mCallBack) {
        mBaseApis.get(path).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(mCallBack));
    }

    /**
     * 8.post请求
     */
    public void post(String path, Map<String, String> map, CallBack mCallBack) {
        if (map == null) {
            map = new HashMap<>();
        }
        mBaseApis.post(path, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(mCallBack));

    }

    /**
     * 9.put表单请求：
     * 1.先将Map<String,String>类型切换成Map<String,RequestBody>
     */
    public Map<String, RequestBody> generateRequestBody(Map<String, String> requestDataMap) {
        Map<String, RequestBody> requestBodyMap = new HashMap<>();
        for (String key : requestDataMap.keySet()) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/from-data"),
                    requestDataMap.get(key) == null ? "" : requestDataMap.get(key));
            requestBodyMap.put(key, requestBody);
        }
        return requestBodyMap;
    }

    public OkHttpUntils put(String path, Map<String, RequestBody> map, CallBack callBack) {
        if (map == null) {
            map = new HashMap<>();
        }
        mBaseApis.put(path, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(callBack));
        return instance;
    }
}