package com.rishabh.livenewsapi;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NewsAdaptor extends ArrayAdapter {
    List list = new ArrayList();
    Context ctx;
    public NewsAdaptor(Context context, int resource){
        super(context,resource);
        this.ctx = context;
    }

    public void add(NewsData object) {
        super.add(object);
        list.add(object);

    }

    @Override
    public int getCount() {
        return list.size();
    }


    @Override
    public Object getItem(int position) {
        return list.get(position);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row;
        row = convertView;
        final NewsAdaptor.DataHolder dataHolder;
        if(row == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.row_layout,parent,false);
            dataHolder = new NewsAdaptor.DataHolder();
            dataHolder.tx_author= (TextView) row.findViewById(R.id.tx_author);
            dataHolder.tx_title = (TextView)row.findViewById(R.id.tx_title);
            dataHolder.tx_des = (TextView)row.findViewById(R.id.tx_description);
            dataHolder.news_img = (ImageView)row.findViewById(R.id.news_img);
            dataHolder.tx_news_card = (CardView)row.findViewById(R.id.newsCard);
            row.setTag(dataHolder);
        }
        else {
            dataHolder = (NewsAdaptor.DataHolder) row.getTag();
        }

        NewsData newsData =(NewsData) this.getItem(position);
        try {
            dataHolder.tx_author.setText(newsData.getAuthor().substring(0, 20));
        }catch (Exception e){

        }
        dataHolder.tx_title.setText(newsData.getTitle());
        dataHolder.tx_des.setText(newsData.getDes());
        dataHolder.tx_weblink = newsData.getUrl();
        dataHolder.urlToImg = newsData.getUrlToImage();
//        try {
//            URL url = new URL(dataHolder.urlToImg);
//            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//            dataHolder.news_img.setImageBitmap(bmp);
//        }catch (Exception e){
//
//        }
        dataHolder.tx_news_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(dataHolder.tx_weblink));
                getContext().startActivity(i);
            }
        });
        Glide.with(ctx).load(newsData.getUrlToImage()).thumbnail(0.1f).into(dataHolder.news_img);

        return row;
    }

    static class DataHolder{
        TextView tx_title,tx_author,tx_des,tx_url;
        CardView tx_news_card;
        ImageView news_img;
        String tx_weblink,urlToImg;

    }
}
