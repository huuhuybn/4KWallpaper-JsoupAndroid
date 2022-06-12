package com.dotplays.slide8;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

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

public class SubActivity extends AppCompatActivity {

    Photo p;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        p = (Photo) getIntent().getSerializableExtra("data");
        Log.e("ads",p.getLinkDetail());
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.show();
        AsyncTask task = new AsyncTask() {
            // xu ly trong thread
            @Override
            protected Object doInBackground(Object[] objects) {
                try {
                    URL url = new URL(p.getLinkDetail());
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
                dialog.dismiss();
                // Nap html vao doi tuong Jsoup
                Log.e("AAA",o.toString());
                Document document = Jsoup.parse((String) o);



            }
        };
        // thuc thi thread
        task.execute();
    }


    public void download(View view) {


    }
}