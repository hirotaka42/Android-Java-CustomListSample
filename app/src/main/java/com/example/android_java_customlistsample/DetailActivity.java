package com.example.android_java_customlistsample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;

public class DetailActivity extends AppCompatActivity {

    // ネットからダウンロードしたイメージ
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String author = intent.getStringExtra("author");
        String price = intent.getStringExtra("price");
        String img = intent.getStringExtra("img");
        String asin = intent.getStringExtra("asin");
        String summary = intent.getStringExtra("summary");
        String release = intent.getStringExtra("release");
        String publisher = intent.getStringExtra("publisher");
        // THUMBZZZ, TZZZZZZZ, MZZZZZZZ, LZZZZZZZ
        String url = "www.amazon.co.jp/dp/";
        String _base_img = "http://images-jp.amazon.com/images/P/";
        String thumb_s = ".09.TZZZZZZZ.jpg";
        String thumb_m = ".09.MZZZZZZZ.jpg";
        String thumb_l = ".09.LZZZZZZZ.jpg";
        String thumb_main = ".09.MAIN._SCLZZZZZZZ_.jpg";
        String img_s = _base_img+asin+thumb_s;
        String img_m = _base_img+asin+thumb_m;
        String img_l = _base_img+asin+thumb_l;
        String img_main = _base_img+asin+thumb_main;



        TextView tvTitle = findViewById(R.id.tvTitle);
        TextView tvPrice = findViewById(R.id.tvPrice);
        TextView tvRelease = findViewById(R.id.tvRelease);
        TextView tvAuthor = findViewById(R.id.tvAuthor);
        TextView tvSummary = findViewById(R.id.tvSummary);
        TextView tvUrl = findViewById(R.id.tvUrl);
        TextView tvAsin = findViewById(R.id.tvAsin);
        TextView tvPublisher = findViewById(R.id.tvPublisher);
        // イメージURL
        // http://images-jp.amazon.com/images/P/B093PXKD2Z.09.MZZZZZZZ.jpg
        ImageView ivThumb = findViewById(R.id.ivThumb);


        tvTitle.setText(title);
        tvPrice.setText("¥" + price );
        tvRelease.setText(release);
        tvAuthor.setText(author);
        tvSummary.setText(summary);
        tvUrl.setText(url+asin);
        tvAsin.setText(asin);
        tvPublisher.setText(publisher);

        // img URLから画像データの取得
        // 参考 - Androidのweb開発 - URLを使いwebリソースにアクセスする
        // https://javait.hatenablog.com/entry/2016/01/10/140409
        // サブスレッド

        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL(img_main);
                    InputStream is = url.openStream();
                    // URLのInputStreamからイメージ読み取り
                    bitmap = BitmapFactory.decodeStream(is);
                    // イメージ表示
                    ivThumb.setImageBitmap(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();


        Button btBack = findViewById(R.id.btBack);
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // summary 概要表示領域に設定することで
        // スクロール Viewを実装する
        tvSummary.setMovementMethod(new ScrollingMovementMethod());
    }
}