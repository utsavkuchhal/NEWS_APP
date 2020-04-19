package com.utsav.sqliteexample;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestFutureTarget;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private List<Article> articles;
    Context context;

    public Adapter(List<Article> articles, Context context) {
        this.articles = articles;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title, desc, author, published_at, scource, time;
        ImageView imageView;
        CardView cardView;
        ProgressBar progressBar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.total);
            desc = itemView.findViewById(R.id.desc);
            author = itemView.findViewById(R.id.author);
            published_at = itemView.findViewById(R.id.publishedat);
            scource = itemView.findViewById(R.id.scource);
            progressBar = itemView.findViewById(R.id.progress_load_photo);
            imageView = itemView.findViewById(R.id.img);
            time  = itemView.findViewById(R.id.samay);
            cardView = itemView.findViewById(R.id.cardview);

        }
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {
            final Article a = articles.get(position);

            final String urli = a.getUrl();

            RequestOptions requestOptions = new RequestOptions();
            requestOptions.centerCrop();
            requestOptions.placeholder(Utils.getRandomDrawbleColor());
            requestOptions.error(Utils.getRandomDrawbleColor());
            requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);

            Glide.with(context).load(a.getUrlToImage()).apply(requestOptions);

            holder.title.setText(a.getTitle());
            holder.author.setText(a.getAuthor());
            holder.desc.setText(a.getDescription());
            holder.scource.setText(a.getSource().getName());
            Picasso.with(context).load(a.getUrlToImage()).into(holder.imageView);
            holder.time.setText(" \u2022 " + Utils.DateToTimeFormat(a.getPublishedAt()));
            holder.published_at.setText(Utils.DateFormat(a.getPublishedAt()));
            holder.progressBar.setVisibility(View.GONE);

            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,Detailed.class);
                    intent.putExtra("title",a.getTitle());
                    intent.putExtra("scource",a.getSource().getName());
                    intent.putExtra("time",a.getPublishedAt());
                    intent.putExtra("author",a.getAuthor());
                    intent.putExtra("imageURL",a.getUrlToImage());
                    intent.putExtra("url",urli);
                    intent.putExtra("desc",a.getDescription());

                    context.startActivity(intent);

                }
            });


    }

    @Override
    public int getItemCount() {
        return articles.size();
    }
}
