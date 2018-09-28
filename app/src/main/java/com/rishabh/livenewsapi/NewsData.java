package com.rishabh.livenewsapi;

public class NewsData {
    String author,title,urlToImage,url,des;

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public NewsData(String author, String title, String urlToImage, String url, String des){
        this.author = author;
        this.title = title;
        this.urlToImage = urlToImage;
        this.url = url;
        this.des = des;

    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
