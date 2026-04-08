package com.event.service;

import java.util.ArrayList;
import java.util.List;

import com.event.model.Event;
import com.event.source.EventSource;
import com.event.source.SourceManager;

public class EventFetcher {

    private List<EventSource> sources;

    public EventFetcher() {
        sources = SourceManager.getSources();
    }

    public List<Event> fetchAllEvents() {

        List<Event> allEvents = new ArrayList<>();

        for (EventSource source : sources) {
            List<Event> events = source.fetchEvents();
            allEvents.addAll(events);
        }

        return allEvents;
    }
}