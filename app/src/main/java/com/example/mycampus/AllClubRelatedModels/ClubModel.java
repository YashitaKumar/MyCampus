package com.example.mycampus.AllClubRelatedModels;

import java.util.List;

public class ClubModel {
    String name;
    String descp;
    String clubPic;

    public ClubModel(String name, String descp, String clubPic, List<HeadsModels> headsModelsList) {
        this.name = name;
        this.descp = descp;
        this.clubPic = clubPic;
        this.headsModelsList = headsModelsList;
    }

    public String getShortdescp() {
        return shortdescp;
    }

    public void setShortdescp(String shortdescp) {
        this.shortdescp = shortdescp;
    }

    String shortdescp;
    List<HeadsModels> headsModelsList;
    List<EventsModel> eventsModelList;

    public List<EventsModel> getEventsModelList() {
        return eventsModelList;
    }

    public void setEventsModelList(List<EventsModel> eventsModelList) {
        this.eventsModelList = eventsModelList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescp() {
        return descp;
    }

    public void setDescp(String descp) {
        this.descp = descp;
    }

    public String getClubPic() {
        return clubPic;
    }

    public void setClubPic(String clubPic) {
        this.clubPic = clubPic;
    }

    public List<HeadsModels> getHeadsModelsList() {
        return headsModelsList;
    }

    public void setHeadsModelsList(List<HeadsModels> headsModelsList) {
        this.headsModelsList = headsModelsList;
    }

    public ClubModel() {
    }

    public ClubModel(String name, String clubPic, String shortdescp) {
        this.name = name;
        this.clubPic = clubPic;
        this.shortdescp = shortdescp;
    }

}
