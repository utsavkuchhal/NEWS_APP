package com.utsav.sqliteexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;

import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static final String API_KEY = "bcc657afdd334ff4af1b914be4ae3549";
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<Article> articles = new ArrayList<>();
    private Adapter adapter;
    private String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        swipeRefreshLayout = findViewById(R.id.refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LoadJSON("","in",API_KEY);
            }
        });
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        LoadJSON("","in",API_KEY);
    }

    public void LoadJSON(String query,String country, String key){

        swipeRefreshLayout.setRefreshing(true);

        Call<News> call;

        if (query.length() > 0)
        {
            call = ApiClient.getInstance().getapi().getspecificdata(query,API_KEY);
        }else
        {
            call = ApiClient.getInstance().getapi().getNews(country,key);
        }

        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if(response.isSuccessful() && response.body().getArticles() != null)
                {
                    swipeRefreshLayout.setRefreshing(false);
                 articles.clear();
                 articles = response.body().getArticles();
                 adapter = new Adapter(articles,MainActivity.this);
                 recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(MainActivity.this,t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public String getCountry(){
        Locale locale = Locale.getDefault();
        String country = locale.getCountry();
        return country.toLowerCase();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater  = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        MenuItem serachmenuitem = menu.findItem(R.id.action_search);

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("Search Latest News...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.length() > 2)
                {
                    LoadJSON(query,"in",API_KEY);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                LoadJSON(newText,"in",API_KEY);
                return false;
            }
        });

        serachmenuitem.getIcon().setVisible(false,false);


        return true;
    }
}
