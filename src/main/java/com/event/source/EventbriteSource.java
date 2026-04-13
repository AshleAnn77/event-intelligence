package com.event.source;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.event.model.Event;

public class EventbriteSource implements EventSource {

    private static final String API_URL =
            "https://www.eventbriteapi.com/v3/events/search/?q=business&location.address=India";

    private static final String API_KEY = "YOUR_API_KEY"; // Replace if available

    @Override
    public List<Event> fetchEvents() {

        List<Event> events = new ArrayList<>();

        try {
            URL url = new URL(API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + API_KEY);

            int responseCode = conn.getResponseCode();

            if (responseCode != 200) {
                System.out.println("Eventbrite API not accessible (Skipping...)");
                return events;
            }

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream())
            );

            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();

            JSONObject json = new JSONObject(response.toString());
            JSONArray eventArray = json.getJSONArray("events");

            for (int i = 0; i < eventArray.length(); i++) {

                JSONObject e = eventArray.getJSONObject(i);

                String title = e.getJSONObject("name").optString("text", "No Title");
                String description = e.getJSONObject("description").optString("text", "");
                String dateStr = e.getJSONObject("start").optString("local", "");

                LocalDate date = LocalDate.now();

                if (!dateStr.isEmpty()) {
                    try {
                        date = LocalDate.parse(dateStr.substring(0, 10));
                    } catch (Exception ignored) {}
                }

                double price = 0.0; // Default (can improve later)

                List<String> tags = generateTags(title);

                Event event = new Event(
                        title,
                        description,
                        "Eventbrite",
                        "India",
                        date,
                        "B2B",
                        tags,
                        price
                );

                events.add(event);
            }

        } catch (Exception e) {
            System.out.println("Error fetching Eventbrite data:");
            e.printStackTrace();
        }

        return events;
    }

    // 🔥 TAG GENERATION
    private List<String> generateTags(String text) {

        List<String> tags = new ArrayList<>();
        text = text.toLowerCase();

        // Industry
        if (text.contains("pharma")) {
            tags.add("pharma");
            tags.add("healthcare");
        }
        if (text.contains("finance") || text.contains("bank")) {
            tags.add("finance");
            tags.add("banking");
        }
        if (text.contains("tech") || text.contains("ai")) {
            tags.add("technology");
            tags.add("IT");
        }
        if (text.contains("startup")) {
            tags.add("startup");
            tags.add("entrepreneurship");
        }

        // Event type
        if (text.contains("expo")) tags.add("expo");
        if (text.contains("summit")) tags.add("summit");
        if (text.contains("conference")) tags.add("conference");
        if (text.contains("networking")) tags.add("networking");

        // Default
        if (tags.isEmpty()) {
            tags.add("business");
        }

        return tags;
    }
}