package com.example.myapplication2;

import com.example.myapplication2.bean.BannerBeans;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiService {
    //todo https://cdwan.cn/api/index
    String URL_Ban = "https://cdwan.cn/api/";

    @GET("index")
    Observable<BannerBeans> getBan();
}
