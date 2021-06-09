package com.example.try2.objects;

public class Material {
    private String urlToFile;
    private String description;
    private String nameOfFile;

    public Material() {
    }

    public Material(String urlToFile, String description,String nameOfFile) {
        this.urlToFile = urlToFile;
        this.description = description;
        this.nameOfFile=nameOfFile;
    }

    public String getNameOfFile() {
        return nameOfFile;
    }

    public void setNameOfFile(String nameOfFile) {
        this.nameOfFile = nameOfFile;
    }

    public String getUrlToFile() {
        return urlToFile;
    }

    public void setUrlToFile(String urlToFile) {
        this.urlToFile = urlToFile;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
