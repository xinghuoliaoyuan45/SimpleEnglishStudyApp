package com.mxy.englishstudy.http;

import com.example.http.HttpUtils;

import com.mxy.englishstudy.bean.book.BookBean;
import com.mxy.englishstudy.bean.book.BookDetailBean;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by mxy on 17/4/21.
 * 网络请求类（一个接口一个方法）
 * 豆瓣图书 retrofit
 */
public interface HttpClient {

    class Builder {
        public static HttpClient getDouBanService() {
            return HttpUtils.getInstance().getDouBanServer(HttpClient.class);
        }
    }


    /**
     * 根据tag获取图书
     *
     * @param tag   搜索关键字
     * @param count 一次请求的数目 最多100
     */

    @GET("v2/book/search")
    Observable<BookBean> getBook(@Query("tag") String tag, @Query("start") int start, @Query("count") int count);

    @GET("v2/book/{id}")
    Observable<BookDetailBean> getBookDetail(@Path("id") String id);


}