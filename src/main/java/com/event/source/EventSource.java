package com.event.source;

import java.util.List;

import com.event.model.Event;

public interface EventSource {
    List<Event> fetchEvents();
}