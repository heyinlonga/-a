package com.example.myapplication2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.bumptech.glide.Glide;
import com.example.myapplication2.adapter.BanAdapter;
import com.example.myapplication2.bean.BannerBeans;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private Button mButton;
    private RecyclerView mRlv;
    private ArrayList<BannerBeans.DataDTO.BannerDTO> mBan;
    private LinearLayoutHelper linearLayoutHelper;
    private VirtualLayoutManager virtualLayoutManager;
    private DelegateAdapter adapter;
    private Banner ban;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton = findViewById(R.id.button);
        ban = findViewById(R.id.ban);
        mRlv = findViewById(R.id.rlv);
        mRlv.setLayoutManager(new LinearLayoutManager(this));
        mBan = new ArrayList<>();



        virtualLayoutManager = new VirtualLayoutManager(this);
        RecyclerView.RecycledViewPool pool = new RecyclerView.RecycledViewPool();
        mRlv.setRecycledViewPool(pool);
        pool.setMaxRecycledViews(0,10);
        linearLayoutHelper = new LinearLayoutHelper(1);
        adapter = new DelegateAdapter(virtualLayoutManager);
        initBan();

    }

    private void initBan() {
        new Retrofit.Builder()
                .baseUrl(ApiService.URL_Ban)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService.class)
                .getBan()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BannerBeans>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull BannerBeans bannerBeans) {
                        mBan.addAll(bannerBeans.getData().getBanner());
                        BanAdapter banAdapter = new BanAdapter(mBan, MainActivity.this, linearLayoutHelper);
                        adapter.addAdapter(banAdapter);
                        mRlv.setAdapter(adapter);
                        /*List<BannerBeans.DataDTO.BannerDTO> banner = bannerBeans.getData().getBanner();
                        Glide.with(MainActivity.this).load(banner.get(0).getImage_url());*/

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}