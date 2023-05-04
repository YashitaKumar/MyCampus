package com.example.mycampus.AllClubRelatedModels;

public class EventsModel {
    String eventName,eventClub,eventTime,eventDate,eventPic,eventVenue;
    int eventLikes;

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventClub() {
        return eventClub;
    }

    public void setEventClub(String eventClub) {
        this.eventClub = eventClub;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventPic() {
        return eventPic;
    }

    public void setEventPic(String eventPic) {
        this.eventPic = eventPic;
    }

    public String getEventVenue() {
        return eventVenue;
    }

    public void setEventVenue(String eventVenue) {
        this.eventVenue = eventVenue;
    }

    public int getEventLikes() {
        return eventLikes;
    }

    public void setEventLikes(int eventLikes) {
        this.eventLikes = eventLikes;
    }

    public EventsModel(String eventName, String eventClub, String eventTime, String eventDate, String eventPic, String eventVenue, int eventLikes) {
        this.eventName = eventName;
        this.eventClub = eventClub;
        this.eventTime = eventTime;
        this.eventDate = eventDate;
        this.eventPic = eventPic;
        this.eventVenue = eventVenue;
        this.eventLikes = eventLikes;
    }

    public EventsModel() {
    }
}
