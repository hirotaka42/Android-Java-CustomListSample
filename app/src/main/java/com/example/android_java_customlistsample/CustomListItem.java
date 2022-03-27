package com.example.android_java_customlistsample;

public class CustomListItem {
    String Title;
    String Ranking;
    String Price;
    String Release;
    String ImgURL;


    public  CustomListItem(String title,String ranking,String price,String release,String imgurl){
        Title = title;
        Ranking = ranking;
        Price = price;
        Release = release;
        ImgURL = imgurl;
    }

    public String getTitle() {
        return Title;
    }

    public String getRanking() {
        return Ranking;
    }

    public String getPrice() {
        return Price;
    }

    public String getRelease() {
        return Release;
    }

    public String getImgURL() {
        return ImgURL;
    }
}
