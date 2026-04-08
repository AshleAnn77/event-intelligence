package com.event;

import java.util.List;

import com.event.model.Event;
import com.event.repository.EventRepository;
import com.event.service.DuplicateRemover;
import com.event.service.EventFetcher;

public class Main {

    public static void main(String[] args) {

        System.out.println("Fetching events...\n");

        // Step 1: Fetch events
        EventFetcher fetcher = new EventFetcher();
        List<Event> events = fetcher.fetchAllEvents();

        // Step 2: Remove duplicates
        DuplicateRemover remover = new DuplicateRemover();
        events = remover.removeDuplicates(events);

        System.out.println("After removing duplicates: " + events.size());

        // Step 3: Save to database
        EventRepository repo = new EventRepository();
        repo.saveEvents(events);

        System.out.println("✅ Events saved to database!");
    }
}