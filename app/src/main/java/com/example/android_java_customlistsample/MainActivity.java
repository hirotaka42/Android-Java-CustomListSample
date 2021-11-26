package com.example.android_java_customlistsample;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // key --> name   , price
        // valu -> タイトル, 価格
        ArrayList <Map<String, String>> bookList = new ArrayList<>();
        // HashMap ー＞ javaの企画
        Map<String, String> map = new HashMap<>();
        map.put("name","進撃の巨人");
        map.put("price","495");
        map.put("release","2021/02/15");
        bookList.add(map);
        // 別オブジェクトとしてMapを作成する //これがないとListが上書きされるので注意
        map = new HashMap<>();
        map.put("name","食戟のソーマ");
        map.put("price","495");
        map.put("release","2021/02/15");
        bookList.add(map);
        map = new HashMap<>();
        map.put("name","Dr.Stone");
        map.put("price","495");
        map.put("release","2021/02/15");
        bookList.add(map);
        map = new HashMap<>();
        map.put("name","食いしん坊");
        map.put("price","495");
        map.put("release","2021/02/15");
        bookList.add(map);
        map = new HashMap<>();
        map.put("name","鬼滅の刃");
        map.put("price","495");
        map.put("release","2021/02/15");
        bookList.add(map);
        map = new HashMap<>();
        map.put("name","Re:ゼロから始める異世界生活");
        map.put("price","495");
        map.put("release","2021/02/15");
        bookList.add(map);
        map = new HashMap<>();
        map.put("name","この素晴らしき世界に祝福を!");
        map.put("price","495");
        map.put("release","2021/02/15");
        bookList.add(map);
        map = new HashMap<>();
        map.put("name","Working");
        map.put("price","495");
        map.put("release","2021/02/15");
        bookList.add(map);
        map = new HashMap<>();
        map.put("name","NARUTO-ナルト-");
        map.put("price","495");
        map.put("release","2021/02/15");
        bookList.add(map);

        String[] from = {"name", "price","release"};
        // データの関連付け
        int[] to = { R.id.lvName, R.id.lvPrice, R.id.lvRelease };
        SimpleAdapter adapter = new SimpleAdapter(this, bookList,
                R.layout.samplelist_item, from, to);

        //


        // レイアウトからリストビューを取得
        ListView lvBook = findViewById(R.id.sample_listview);
        lvBook.setAdapter(adapter);
        // クリックされたときのOnClickListener画面遷移
        lvBook.setOnItemClickListener(new ListItemClickListener());

    }

    private  class ListItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Map<String , String> item = (Map) adapterView.getItemAtPosition(i);
            // getItemAtPosition() 位置を指定して項目のデータを取得する
            // 受け入れ先が(Map) item なので (Map)でキャストしてあげる
            String name = item.get("name");
            String price = item.get("price");
            // サンクス画面 //タッチされた項目のデータを引数に設定
            showThanks(name,price);
        }
    }

    private void showThanks(String name, String price) {
        Intent intent = new Intent(this, ThanksActivity.class);
        // サンクス画面に送るデータ 上で作成したintent(ThanksActivity.class)を使用
        // Key Vaultの形でデータを送ってあげる
        intent.putExtra("name",name);
        // priceは項目により違うので引数でもらってくる
        intent.putExtra("price",price);
        // 表示
        startActivity(intent);
    }
}