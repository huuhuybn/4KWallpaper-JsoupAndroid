package com.dotplays.slide8;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ListView;
import android.widget.TextView;

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

    String link = "https://vnexpress.net/";

    String wallpaper = "https://4kwallpapers.com/";

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = findViewById(R.id.web);

        String data = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<body>\n" +
                "\n" +
                "<p><b>This text is bold</b></p>\n" +
                "<p><i>This text is italic</i></p>\n" +
                "<p>This is<sub> subscript</sub> and <sup>superscript</sup></p>\n" +
                "\n" +
                "</body>\n" +
                "</html>\n";

        //webView.loadData(data, "text/html", "utf-8");

        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(link);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (webView.canGoBack()) {
            webView.goBack();
        }
    }

    public void getData(View view) {
        TextView tvText = findViewById(R.id.tvText);

        //B1 : mo luong moi , new thread
        AsyncTask task = new AsyncTask() {
            // xu ly trong thread
            @Override
            protected Object doInBackground(Object[] objects) {
                try {
                    URL url = new URL(wallpaper);
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
                ArrayList<String> arrayList = new ArrayList<>();
                Document document = Jsoup.parse((String) o);
                Elements elements = document.select("a");
                for (int i = 0; i < elements.size(); i++) {
                    Elements imgs = elements.get(i).getElementsByTag("img");
                    if (imgs.size() > 0){
                        Element img = imgs.get(0);
                        String url = img.attr("data-cfsrc");
                        Log.e("URL",url);
                        arrayList.add(url);
                    }
                    //Log.e("ABC", elements.get(i).html());
                    //Log.e("============","==============================");
                }
                ListView ls = findViewById(R.id.list);
                ImageAdapter imageAdapter = new ImageAdapter(arrayList);
                ls.setAdapter(imageAdapter);

                tvText.setText((String) o);

            }
        };
        // thuc thi thread
        task.execute();
    }

}