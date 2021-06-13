package com.example.try2.objects;

import android.widget.ImageView;

public class Meme {
    private String urlToMeme;
    private String userName;

    public String getUrlToMeme() {
        return urlToMeme;
    }

    public void setUrlToMeme(String urlToMeme) {
        this.urlToMeme = urlToMeme;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Meme(String urlToMeme, String userName) {
        this.urlToMeme = urlToMeme;
        this.userName = userName;
    }

    public Meme() {
    }
}
