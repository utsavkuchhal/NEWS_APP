package com.utsav.sqliteexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;

public class Detailed extends AppCompatActivity {

    TextView tvtitle,tvscource,tvtime,tvdesc,tvauthor;
    WebView webView;
    ImageView imageView;
    ProgressBar loader;
    String scource;
    public static String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        tvtitle = findViewById(R.id.total);
        tvauthor = findViewById(R.id.author);

        tvtime = findViewById(R.id.publishedat);
        webView = findViewById(R.id.window);

        loader = findViewById(R.id.progress);
        loader.setVisibility(View.VISIBLE);
        imageView = findViewById(R.id.img);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String time = intent.getStringExtra("time");
        scource = intent.getStringExtra("scource");

        String imageURL = intent.getStringExtra("imageURL");
         url = intent.getStringExtra("url");
        String desc = intent.getStringExtra("desc");
        String auth = intent.getStringExtra("author");

        tvtitle.setText(title);
        tvtime.setText(Utils.DateFormat(time));
        tvauthor.setText(auth);

        Picasso.with(Detailed.this).load(imageURL).into(imageView);

        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);

        if(webView.isShown())
        {
            loader.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_news,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.viewweb)
        {
            Intent intent  = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
            return true;
        }else if(id == R.id.share){
                try {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plan");
                    intent.putExtra(Intent.EXTRA_SUBJECT,scource);
                    intent.putExtra(Intent.EXTRA_TEXT,url);
                    startActivity(Intent.createChooser(intent,"Share from the news app"));
                }catch (Exception e)
                {
                    Toast.makeText(this,e.getMessage(), Toast.LENGTH_SHORT).show();
                }
        }
        return super.onOptionsItemSelected(item);
    }
}
