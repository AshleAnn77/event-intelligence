package com.event.service;

import java.util.*;

import com.event.model.Event;

public class DuplicateRemover {

    public List<Event> removeDuplicates(List<Event> events) {

        Map<String, Event> uniqueEvents = new HashMap<>();

        for (Event e : events) {

            // Normalize title
            String normalizedTitle = e.getTitle()
                    .toLowerCase()
                    .replaceAll("[^a-z0-9]", "");

            // Unique key
            String key = normalizedTitle + "_" + e.getDate();

            if (!uniqueEvents.containsKey(key)) {
                uniqueEvents.put(key, e);
            }
        }

        return new ArrayList<>(uniqueEvents.values());
    }
}