package com.bwie.wang.bwm.Until;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;

/**
 * @author lenovo
 *         接口
 */
public interface BaseApis {
    /**
     * get
     *
     * @param path
     * @return
     */
    @GET
    Observable<ResponseBody> get(@Url String path);

    /**
     * post
     *
     * @param path
     * @param map
     * @return
     */
    @POST
    Observable<ResponseBody> post(@Url String path, @QueryMap Map<String, String> map);

    /**
     * 表单post
     *
     * @param path
     * @param requestBodyMap
     * @return
     */
    @Multipart
    @POST
    Observable<ResponseBody> postFormBody(@Url String path, @PartMap Map<String, RequestBody> requestBodyMap);

    @Multipart
    @PUT
    Observable<ResponseBody> put(@Url String path, @PartMap Map<String, RequestBody> requestBodyMap);
}
