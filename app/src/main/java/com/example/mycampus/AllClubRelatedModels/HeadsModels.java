package com.example.mycampus.AllClubRelatedModels;

public class HeadsModels {
    String headName,headPic,headDesg;

    public String getHeadName() {
        return headName;
    }

    public void setHeadName(String headName) {
        this.headName = headName;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public String getHeadDesg() {
        return headDesg;
    }

    public void setHeadDesg(String headDesg) {
        this.headDesg = headDesg;
    }

    public HeadsModels() {
    }

    public HeadsModels(String headName, String headPic, String headDesg) {
        this.headName = headName;
        this.headPic = headPic;
        this.headDesg = headDesg;
    }
}
