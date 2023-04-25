package com.example.mycampus.Models;

import java.util.List;

public class DhabbaModel {
    public DhabbaModel() {
    }

    String itemName,itemPic;
    List<DhabbaItemModel> dhabbaItemModelList;

    public DhabbaModel(String itemName, String itemPic, List<DhabbaItemModel> dhabbaItemModelList) {
        this.itemName = itemName;
        this.itemPic = itemPic;
        this.dhabbaItemModelList = dhabbaItemModelList;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemPic() {
        return itemPic;
    }

    public void setItemPic(String itemPic) {
        this.itemPic = itemPic;
    }

    public List<DhabbaItemModel> getDhabbaItemModelList() {
        return dhabbaItemModelList;
    }

    public void setDhabbaItemModelList(List<DhabbaItemModel> dhabbaItemModelList) {
        this.dhabbaItemModelList = dhabbaItemModelList;
    }
}
