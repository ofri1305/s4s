package com.example.try2.objects;

public class Meme {
    private String uriToImage;
    private String userName;

    public String getUriToImage() {
        return uriToImage;
    }

    public void setUriToImage(String uriToImage) {
        this.uriToImage = uriToImage;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Meme(String urlToMeme, String userName) {
        this.uriToImage = urlToMeme;
        this.userName = userName;
    }

    public Meme() {
    }
}
