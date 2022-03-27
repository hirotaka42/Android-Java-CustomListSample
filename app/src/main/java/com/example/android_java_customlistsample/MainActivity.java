package com.example.android_java_customlistsample;


import androidx.annotation.UiThread;
import androidx.annotation.WorkerThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.os.HandlerCompat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    // メンバー変数
    ListView lvBook;
    // データを追加していく配列の定義
    List<Map<String, String>> bookList = new ArrayList<>();
    ArrayList<CustomListItem> arrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 画面部品ListViewを取得
        lvBook = findViewById(R.id.lvBook);

        CustomListAdapter customListAdapter = new CustomListAdapter(this,R.layout.customlist_item,arrayList);

        //Itemに対する 紐付け設定
        lvBook.setAdapter(customListAdapter);
        // リストタップのリスナクラス登録。
        lvBook.setOnItemClickListener(new ListItemClickListener());

        String url = "http://192.168.0.210:3000/";
        //urlの読み込み(受け取り)
        receiveBookList(url);

    }

    /**
     * URLの取得処理を行う
     *
     * @param urlStr
     */
    @UiThread
    private void receiveBookList(String urlStr) {
        Handler handler = HandlerCompat.createAsync(Looper.getMainLooper());
        BookListReceiver receiver = new BookListReceiver(handler, urlStr);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(receiver);
    }

    /**
     * 非同期で天気情報にアクセスする
     */
    private class BookListReceiver implements Runnable {

        private final Handler _handler;
        private final String _urlStr;

        public BookListReceiver(Handler handler, String urlStr) {
            _handler = handler;
            _urlStr = urlStr;
        }

        @WorkerThread
        @Override
        public void run() {
            HttpURLConnection con = null;
            InputStream is = null;
            String result = "";    // URLから取得したJSON文字列を格納するためのresult
            try {
                URL url = new URL(_urlStr);
                con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.connect();
                is = con.getInputStream();
                result = is2String(is);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (SocketTimeoutException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (con != null) {
                    con.disconnect();
                }
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
//            Log.d("test",result);
            BookListPostExecutor postExecutor = new BookListPostExecutor(result);
            _handler.post(postExecutor);
        }
    }

    /**
     * 非同期でURL情報を取得した後にUIスレッドでその情報を表示する
     */
    private class BookListPostExecutor implements Runnable {


        // 取得したURL情報JSON文字列
        private final String _result;

        public BookListPostExecutor(String result) {
            _result = result;
        }

        @UiThread
        @Override
        public void run() {
            try {
                JSONArray rootJSON = new JSONArray(_result);

//                Toast.makeText(
//                        getApplicationContext(),
//                        rootJSON.toString(),
//                        Toast.LENGTH_SHORT).show();

                for (int i = 0; i < rootJSON.length(); i++) {
                    JSONObject book = rootJSON.getJSONObject(i);

                    Map<String, String> map = new HashMap<>();
                    map.put("ranking", book.getString("ranking"));
                    map.put("title", book.getString("title"));
                    map.put("author", book.getString("author"));
                    map.put("price", book.getString("price"));
                    map.put("img", book.getString("img"));
                    map.put("asin", book.getString("asin"));
                    map.put("url", book.getString("url"));
                    map.put("summary", book.getString("summary"));
                    map.put("release", book.getString("release"));
                    map.put("publisher", book.getString("publisher"));
                    bookList.add(map);

                    arrayList.add(new CustomListItem(book.getString("title"),book.getString("ranking"),
                            book.getString("price"),book.getString("release"),book.getString("img")));

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            //更新
            CustomListAdapter adapter = (CustomListAdapter) lvBook.getAdapter();
            adapter.notifyDataSetChanged();

        }
    }

    // InputStream から 文字列に変換する
    private String is2String(InputStream is) throws IOException {
        BufferedReader reader = new BufferedReader((new InputStreamReader(is, "UTF-8")));
        StringBuilder sb = new StringBuilder();
        char[] b = new char[1024];
        int line;
        while (0 <= (line = reader.read(b))) {
            sb.append(b, 0, line);
        }
        return sb.toString();
    }

    // リストがタップされた時の処理が記述されたメンバクラス。
    private class ListItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            showDetail(position);
        }
    }


    private void showDetail(int position) {
        Map<String, String> item = bookList.get(position);

        Intent intent = new Intent(getApplicationContext(), DetailActivity.class);

        intent.putExtra("title", item.get("title"));
        intent.putExtra("author", item.get("author"));
        intent.putExtra("price", item.get("price"));
        intent.putExtra("img", item.get("img"));
        intent.putExtra("asin", item.get("asin"));
        intent.putExtra("summary", item.get("summary"));
        intent.putExtra("release", item.get("release"));
        intent.putExtra("publisher", item.get("publisher"));
        startActivity(intent);
    }


}
