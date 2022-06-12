package com.dotplays.slide8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.dotplays.slide8.model.Photo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {


    String wallpaper = "https://4kwallpapers.com/?page=";
    ListView ls;
    ImageAdapter imageAdapter;
    ArrayList<Photo> arrayList;
    int page = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ls = findViewById(R.id.list);
        arrayList = new ArrayList<>();
        imageAdapter = new ImageAdapter(arrayList);
        getData(page);
        ls.setAdapter(imageAdapter);
        ls.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int i, int totalItemsCount) {
                page++;
                getData(page);
                return false;
            }
        });
        ls.setOnItemClickListener((parent, view, position, id) -> {
            Intent i = new Intent(MainActivity.this, SubActivity.class);
            i.putExtra("data", arrayList.get(position));
            startActivity(i);
        });
    }


    public void getData(int page) {

        AsyncTask task = new AsyncTask() {
            // xu ly trong thread
            @Override
            protected Object doInBackground(Object[] objects) {
                try {
                    URL url = new URL(wallpaper + page);
                    HttpURLConnection connection =
                            (HttpURLConnection) url.openConnection();
                    InputStream stream = connection.getInputStream();
                    String duLieu = "";
                    Scanner scanner = new Scanner(stream);
                    while (scanner.hasNext()) {
                        duLieu += scanner.nextLine();
                    }
                    return duLieu;
                } catch (Exception e) {
                    return e.getMessage();
                }

            }

            // cap nhat du lieu vao UI (giao dien)
            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                // Nap html vao doi tuong Jsoup
                ArrayList<Photo> subs = new ArrayList<>();
                Document document = Jsoup.parse((String) o);
                Elements elements = document.select("a");
                for (int i = 0; i < elements.size(); i++) {
                    Log.e("---", "----------------");
                    String detail = elements.get(i).attr("href");
                    Log.e("AAA",detail);
                    Elements imgs = elements.get(i).getElementsByTag("img");
                    if (imgs.size() > 0) {
                        Element img = imgs.get(0);
                        String url = img.attr("data-cfsrc");
                        Photo photo = new Photo();
                        photo.setUrlThumb(url);
                        photo.setLinkDetail(detail);
                        subs.add(photo);
                    }
                    //Log.e("ABC", elements.get(i).html());
                    //Log.e("============","==============================");
                }
                arrayList.addAll(subs);
                imageAdapter.notifyDataSetChanged();
            }
        };
        // thuc thi thread
        task.execute();
    }

}