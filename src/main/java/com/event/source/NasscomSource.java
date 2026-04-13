package com.event.source;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.event.model.Event;

public class NasscomSource implements EventSource {

    @Override
    public List<Event> fetchEvents() {

        List<Event> events = new ArrayList<>();

        try {
            Document doc = Jsoup.connect("https://nasscom.in/events")
                    .userAgent("Mozilla/5.0")
                    .get();

            Elements titles = doc.select("h2, h3");

            for (Element title : titles) {

                String originalText = title.text();

                if (originalText == null || originalText.isEmpty()) continue;

                String text = originalText.toLowerCase();

                // ❌ Step 1: Remove irrelevant events (TITLE ONLY)
                if (text.contains("comedy") ||
                        text.contains("kids") ||
                        text.contains("marathon") ||
                        text.contains("meetup") ||
                        text.contains("party") ||
                        text.contains("drawing") ||
                        text.contains("music") ||
                        text.contains("travel") ||
                        text.contains("sports")) {
                    continue;
                }

                // 🔹 Get full container
                Element container = title.closest("div");

                String description = "";
                String location = "India";

                if (container != null) {

                    String fullText = container.text();

                    // Clean text
                    description = fullText.replaceAll("\\s+", " ").trim();

                    // 🔥 Extract location using pattern (AFTER YEAR)
                    location = extractFullLocation(fullText);
                }

                // 🔹 Combine title + description
                String combinedText = (originalText + " " + description).toLowerCase();

                // ✅ Step 2: Relevant filtering (TITLE + DESCRIPTION)
                if (combinedText.contains("expo") ||
                        combinedText.contains("summit") ||
                        combinedText.contains("conference") ||
                        combinedText.contains("business") ||
                        combinedText.contains("startup") ||
                        combinedText.contains("technology") ||
                        combinedText.contains("industry") ||
                        combinedText.contains("trade")) {

                    LocalDate date = LocalDate.now(); // next step we fix
                    double price = 0.0;

                    List<String> tags = generateTags(combinedText);

                    Event event = new Event(
                            originalText,
                            description,
                            "NASSCOM",
                            location, // ✅ FULL LOCATION
                            date,
                            "B2B",
                            tags,
                            price
                    );

                    events.add(event);
                }
            }

        } catch (Exception e) {
            System.out.println("Error fetching NASSCOM:");
            e.printStackTrace();
        }

        return events;
    }

    // 🔥 EXTRACT FULL LOCATION (MAIN LOGIC)
    private String extractFullLocation(String text) {

    // Remove date like "17 Feb 2026"
    String cleaned = text.replaceAll("\\d{1,2}\\s+[A-Za-z]{3}\\s+\\d{4}", "").trim();

    // Remove extra spaces
    cleaned = cleaned.replaceAll("\\s+", " ");

    // 🔥 Now extract only last meaningful part (location)
    String[] parts = cleaned.split(",");

    if (parts.length >= 2) {
        return parts[parts.length - 2].trim() + ", " + parts[parts.length - 1].trim();
    }

    return cleaned;
}

    // 🔥 TAG GENERATION
    private List<String> generateTags(String text) {

        List<String> tags = new ArrayList<>();
        text = text.toLowerCase();

        if (text.contains("pharma")) {
            tags.add("pharma");
            tags.add("healthcare");
        }
        if (text.contains("bank") || text.contains("nbfc")) {
            tags.add("finance");
            tags.add("banking");
        }
        if (text.contains("manufacturing")) {
            tags.add("manufacturing");
        }
        if (text.contains("tech") || text.contains("technology")) {
            tags.add("technology");
            tags.add("IT");
        }
        if (text.contains("startup")) {
            tags.add("startup");
            tags.add("entrepreneurship");
        }

        if (text.contains("expo")) tags.add("expo");
        if (text.contains("summit")) tags.add("summit");
        if (text.contains("conference")) tags.add("conference");
        if (text.contains("networking")) tags.add("networking");

        if (tags.isEmpty()) {
            tags.add("business");
        }

        return tags;
    }
}