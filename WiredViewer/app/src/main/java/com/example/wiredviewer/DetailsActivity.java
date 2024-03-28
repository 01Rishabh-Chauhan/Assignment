package com.example.wiredviewer;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class DetailsActivity extends AppCompatActivity {
    // private ImageView imageView;
    // private TextView titleTextView, detailTextView;
    // private String detailString;
    private WebView webView;
    private ProgressBar progressBar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        webView=findViewById(R.id.webview);
        progressBar2 = findViewById(R.id.progressBar2);

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar2.setVisibility(View.GONE);
                progressBar2.startAnimation(AnimationUtils.loadAnimation(DetailsActivity.this, android.R.anim.fade_out));
            }
        });

        webView.loadUrl("https://www.wired.com/");
        /*
        imageView = findViewById(R.id.imageView);
        titleTextView = findViewById(R.id.textView);
        // detailTextView=findViewById(R.id.detailsTextView);
        titleTextView.setText(getIntent().getStringExtra("title"));
        Picasso.get().load(getIntent().getStringExtra("image")).into(imageView);
        */
        Content content = new Content();
        content.execute();
    }

    @Override
    public void onBackPressed() {
        if(webView.canGoBack()){
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    public class Content extends AsyncTask<Void, Void, Void> {
        Content() { }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar2.setVisibility(View.VISIBLE);
            progressBar2.startAnimation(AnimationUtils.loadAnimation(
                    DetailsActivity.this, android.R.anim.fade_in));
            //detailTextView.setText(detailString);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            /*
            String baseUrl = "https://www.wired.com/";
            String detailUrl = getIntent().getStringExtra("detailsUrl");
            String url = baseUrl + detailUrl;
            try {
                Document doc = Jsoup.connect(url).get();
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Elements data=doc.select("div.updatesDetail__article__content").select("p");
            // detailString=data.text();
            */
            return null;
        }
    }
}