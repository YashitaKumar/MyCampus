package com.example.mycampus.Models;

public class DhabbaItemModel {
    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public DhabbaItemModel(String item, int price) {
        this.item = item;
        this.price = price;
    }

    String item;
    int price;

    public DhabbaItemModel() {
    }
}
