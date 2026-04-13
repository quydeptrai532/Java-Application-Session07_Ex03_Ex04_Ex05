package com.rikkeifood.model;

import java.util.List;

public class Combo {
    private String name;
    private List<String> itemList; // Lưu danh sách các món được check
    private String bannerPath;

    public Combo() {
    }

    public Combo(String name, List<String> itemList, String bannerPath) {
        this.name = name;
        this.itemList = itemList;
        this.bannerPath = bannerPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getItemList() {
        return itemList;
    }

    public void setItemList(List<String> itemList) {
        this.itemList = itemList;
    }

    public String getBannerPath() {
        return bannerPath;
    }

    public void setBannerPath(String bannerPath) {
        this.bannerPath = bannerPath;
    }
}