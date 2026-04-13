package com.event.model;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class Event {

    private String id;
    private String title;
    private String description;
    private String source;
    private String location;
    private LocalDate date;
    private String type;
    private List<String> tags;
    private double entryPrice;
    private double score;

    // Constructor
    public Event(String title, String description, String source,
                 String location, LocalDate date, String type,
                 List<String> tags, double entryPrice) {

        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.description = description;
        this.source = source;
        this.location = location;
        this.date = date;
        this.type = type;
        this.tags = tags;
        this.entryPrice = entryPrice;
        this.score = 0.0; // default
    }

    // Getters
    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getSource() { return source; }
    public String getLocation() { return location; }
    public LocalDate getDate() { return date; }
    public String getType() { return type; }
    public List<String> getTags() { return tags; }
    public double getEntryPrice() { return entryPrice; }
    public double getScore() { return score; }

    // Setter for score (for AI later)
    public void setScore(double score) {
        this.score = score;
    }
}