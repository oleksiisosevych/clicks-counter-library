package com.oleksiisosevych.flickrimagesbrowsermvp.data.models;

public class EventStat {
    private String eventId;
    private int count;

    public EventStat(String eventId, int count) {
        this.eventId = eventId;
        this.count = count;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
