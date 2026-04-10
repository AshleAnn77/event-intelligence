package com.event;

import java.util.List;

import com.event.model.Event;
import com.event.repository.EventRepository;
import com.event.service.DuplicateRemover;
import com.event.service.EventFetcher;

public class Main {

    public static void main(String[] args) {

        try {
            System.out.println("Fetching events...\n");

            // Step 1: Fetch events
            EventFetcher fetcher = new EventFetcher();
            List<Event> events = fetcher.fetchAllEvents();

            if (events == null || events.isEmpty()) {
                System.out.println("No events fetched. Exiting...");
                return;
            }

            // Step 2: Remove duplicates
            DuplicateRemover remover = new DuplicateRemover();
            events = remover.removeDuplicates(events);

            System.out.println("After removing duplicates: " + events.size());

            // Step 3: Save to database
            EventRepository repo = new EventRepository();
            repo.saveEvents(events);

            System.out.println("✅ Events saved to Railway database!");

        } catch (Exception e) {
            System.out.println("❌ Something went wrong:");
            e.printStackTrace();
        }
    }
}