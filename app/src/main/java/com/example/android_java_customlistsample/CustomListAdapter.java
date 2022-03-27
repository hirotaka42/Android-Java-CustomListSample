package com.example.android_java_customlistsample;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CustomListAdapter extends ArrayAdapter<CustomListItem> {

    private Context mContext;
    private int mResouce;
    // ネットからダウンロードしたイメージ
    Bitmap bitmap;

    public CustomListAdapter(Context context, int resource, ArrayList<CustomListItem> items) {
        super(context, resource, items);
        this.mContext = context;
        this.mResouce = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        convertView = layoutInflater.inflate(mResouce,parent,false);


        // タイトルを設定
        TextView title = convertView.findViewById(R.id.lvTitle);
        // ランキングを設定
        TextView ranking = convertView.findViewById(R.id.lvRanking);
        // プライスを設定
        TextView price = convertView.findViewById(R.id.lvPrice);
        // リリースを設定
        TextView release = convertView.findViewById(R.id.lvRelease);
        // サムネイル画像を設定
        ImageView ivThumbnail = convertView.findViewById(R.id.ivThumbnail);

        title.setText(getItem(position).getTitle());
        ranking.setText(getItem(position).getRanking());
        price.setText(getItem(position).getPrice());
        release.setText(getItem(position).getRelease());

        String mainurl = getItem(position).getImgURL();




        // img URLから画像データの取得
        // 参考 - Androidのweb開発 - URLを使いwebリソースにアクセスする
        // https://javait.hatenablog.com/entry/2016/01/10/140409
        // サブスレッド

        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL(mainurl);
                    InputStream is = url.openStream();
                    // URLのInputStreamからイメージ読み取り
                    bitmap = BitmapFactory.decodeStream(is);
                    // イメージ表示
                    ivThumbnail.setImageBitmap(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();

        return convertView;
    }
}
